package org.nextime.ion.backoffice.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CreatePublicationForm extends ActionForm {
	
	private String name;
	private String type;
	private String workflow;
	
	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping, HttpServletRequest)
	 */
	public ActionErrors myValidate(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if( "".equals(getName()) ) {
			ActionError error = new ActionError("error.createPublication.nameMissing");
			errors.add("name",error);
		}
		if( "".equals(getType()) ) {
			ActionError error = new ActionError("error.createPublication.typeMissing");
			errors.add("type",error);
		}
		if( "".equals(getWorkflow()) ) {
			ActionError error = new ActionError("error.general.missingField");
			errors.add("workflow",error);
		}
		return errors;
	}


	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping, HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		setName(null);
		setType(null);
		setWorkflow(null);
	}


	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}


	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}


	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * Returns the workflow.
	 * @return String
	 */
	public String getWorkflow() {
		return workflow;
	}

	/**
	 * Sets the workflow.
	 * @param workflow The workflow to set
	 */
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

}

