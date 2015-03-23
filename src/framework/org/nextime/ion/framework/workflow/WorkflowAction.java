package org.nextime.ion.framework.workflow;

import java.util.Collections;
import java.util.Map;
import java.util.StringTokenizer;

import org.nextime.ion.framework.mapping.Mapping;

public class WorkflowAction {

	private Workflow workflow;
	private int id;
	private String name;
	private String externalAction;

	protected WorkflowAction(Workflow owner, int id) {
		this.workflow = owner;
		this.id = id;
		StringTokenizer stok =
			new StringTokenizer(
				workflow.getOSWorkflow().getActionName(
					workflow.getWorkflowType(),
					id),
				";");
		name = stok.nextToken();
		if (stok.hasMoreTokens()) {
			externalAction = stok.nextToken();
		}
	}

	public String getName() {
		return name;
	}

	public String getExternalAction() {
		return externalAction;
	}

	public int getId() {
		return id;
	}

	public void execute() throws Exception {
		execute(Collections.EMPTY_MAP);
	}

	public void execute(Map inputs) throws Exception {
		boolean transaction = false;
		try {
			if (!Mapping.getInstance().isTransactionActive()) {
				Mapping.begin();
				transaction = true;
			}
			workflow.getOSWorkflow().doAction(workflow.getId(), id, inputs);
			if (transaction)
				Mapping.commit();
		} catch (Exception e) {
			if (transaction)
				Mapping.rollback();
			throw e;
		}
	}

}
