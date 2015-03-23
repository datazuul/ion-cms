package org.nextime.ion.framework.workflow;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.config.Config;
import org.nextime.ion.osworkflow.loader.WorkflowFactory;

import com.opensymphony.workflow.AbstractWorkflow;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.config.ConfigLoader;
import com.opensymphony.workflow.spi.Step;

public class Workflow {

	private AbstractWorkflow aworkflow;
	private long id;
	private static String[] types;

	private Workflow(long id, AbstractWorkflow aw) {
		setId(id);
		setOSWorkflow(aw);
	}

	public static Workflow getInstance(long id, User user) {
		BasicWorkflow bwf = new BasicWorkflow(user.getLogin());
		Workflow wf = new Workflow(id, bwf);
		return wf;
	}

	public static Workflow create(User user, String type) throws Exception {
		return create(user,type,null);
	}
	
	public static Workflow create(User user, String type, Map inputs) throws Exception {
		BasicWorkflow bwf = new BasicWorkflow(user.getLogin());
		long id = bwf.createEntry(type);
		Workflow wf = new Workflow(id, bwf);
		bwf.initialize(id, 1, inputs);
		return wf;
	}

	public static void remove(long id) {
	}

	public String getWorkflowType() {
		return aworkflow.getWorkflowName(id);
	}

	public WorkflowAction[] getAvailableActions() {
		int[] actions = aworkflow.getAvailableActions(getId());
		WorkflowAction[] wactions = new WorkflowAction[actions.length];
		for (int i = 0; i < actions.length; i++) {
			wactions[i] = new WorkflowAction(this,actions[i]);
		}
		return wactions;
	}
	
	public WorkflowAction getAction(int id) {
		return new WorkflowAction(this,id);
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	protected void setOSWorkflow(AbstractWorkflow wf) {
		this.aworkflow = wf;
	}

	protected AbstractWorkflow getOSWorkflow() {
		return aworkflow;
	}

	public static String[] listTypes() {
		try {
			if (types == null) {
				WorkflowFactory wff = new WorkflowFactory();
				wff.initDone();
				types = wff.listAllNames();
			}
			return types;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector getPermissions() {		
		List list = aworkflow.getSecurityPermissions(getId());
		Vector retour = new Vector();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			retour.add(it.next());
			
		}
		return retour;
	}
	
	public Hashtable getMetadatas() {
		Hashtable ht = new Hashtable();
		BasicWorkflow mbwf = new BasicWorkflow("smartIonFramework");
		List list = mbwf.getSecurityPermissions(getId());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			String p = it.next()+"";
			if( p.indexOf(":") != -1 ) {
				String key = p.substring(0,p.indexOf(":"));
				String value = p.substring(p.indexOf(":")+1);
				ht.put(key,value);
			}			
		}
		return ht;
	}
	
	public Map getVariables() {		
		return aworkflow.getVariableMap(getId());
	}
	
	public Vector getCurrentSteps() {
		Vector retour = new Vector();
		List list = aworkflow.getCurrentSteps(getId());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Step step = (Step)it.next();
			retour.add(new WorkflowStep(this,step));			
		}
		return retour;
	}
	
	public Vector getHistorySteps() {
		Vector retour = new Vector();
		List list = aworkflow.getHistorySteps(getId());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Step step = (Step)it.next();
			retour.add(new WorkflowStep(this,step));			
		}
		return retour;
	}
	
	public Vector getSteps() {
		Vector retour = new Vector();
		List list = ConfigLoader.getWorkflow(getWorkflowType()).getSteps();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Step step = (Step)it.next();
			retour.add(new WorkflowStep(this,step));			
		}
		return retour;
	}

}
