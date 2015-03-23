package org.nextime.ion.framework.workflow;

import java.text.SimpleDateFormat;

import org.nextime.ion.framework.config.Config;

import com.opensymphony.workflow.spi.Step;

/**
 * @author gbort
 */
public class WorkflowStep {
	
	private Workflow workflow;
	private Step step;	
	
	protected WorkflowStep(Workflow owner, Step ownerStep) {
		this.workflow = owner;
		this.step = ownerStep;		
	}
	
	public String getName() {
		return workflow.getOSWorkflow().getStepName(workflow.getWorkflowType(), step.getStepId());
	}
	
	public int getId() {
		return step.getStepId();
	}
	
	public String getStatus() {		
		return step.getStatus();	
	}
	
	public String getOwner() {
		return step.getOwner();
	}
	
	public String getStartDate() {
		return new SimpleDateFormat(Config.getInstance().getDateFormatPattern()).format(step.getStartDate());
	}
	
	public String getStartTime() {
		return new SimpleDateFormat("HH:mm:ss").format(step.getStartDate());
	}
	
	public String getFinishDate() {
		return new SimpleDateFormat(Config.getInstance().getDateFormatPattern()).format(step.getFinishDate());
	}
	
	public String getFinishTime() {
		return new SimpleDateFormat("HH:mm:ss").format(step.getFinishDate());
	}

}
