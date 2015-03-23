package org.nextime.ion.osworkflow.util;

import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowContext;

import java.util.Map;

/**
 * @author gbort
 */
public class IonSetCallerAsAuthor implements FunctionProvider {
	public void execute(Map inputs, Map properties, Map variables) {
		WorkflowContext context = (WorkflowContext) variables.get("context");
		variables.put("author", context.getCaller());
	}
}
