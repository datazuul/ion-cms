package org.nextime.ion.osworkflow.util;

import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowContext;

import java.util.Map;

/**
 * @author gbort
 */
public class IonInitWorkflow implements FunctionProvider {
	
	public void execute(Map inputs, Map properties, Map variables) {

		String id = inputs.get("id") + "";
		int version = Integer.parseInt(inputs.get("version") + "");

		variables.put("publicationId", id);
		variables.put("publicationVersion", version+"");
		
	}
	
}
