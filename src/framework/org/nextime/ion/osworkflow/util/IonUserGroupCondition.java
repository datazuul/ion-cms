package org.nextime.ion.osworkflow.util;

import com.opensymphony.workflow.*;
import com.opensymphony.workflow.spi.Step;

import java.util.Map;

import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.User;

/**
 * @author gbort
 */
public class IonUserGroupCondition implements Condition {
	public boolean passesCondition(Map inputs, Map args, Map variables) {
		try {
			WorkflowContext context =
				(WorkflowContext) variables.get("context");
			return User.getInstance(context.getCaller()).isInGroup(
				Group.getInstance(args.get("group") + ""));
		} catch (Exception e) {
			return false;
		}
	}
}
