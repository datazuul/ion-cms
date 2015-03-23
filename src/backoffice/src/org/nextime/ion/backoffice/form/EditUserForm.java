package org.nextime.ion.backoffice.form;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.apache.regexp.RE;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.bean.PropertyBean;
import org.nextime.ion.backoffice.bean.SectionTypes;
import org.nextime.ion.backoffice.bean.TypeBean;

public class EditUserForm extends ActionForm {

	private String name;
	private String email;
	private String password;
	private String[] groups;

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping, HttpServletRequest)
	 */
	public ActionErrors myValidate(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();	
		if ("".equals(getName()) || getName()==null) {
			ActionError error =
				new ActionError("error.editUser.nameMissing");
			errors.add("name", error);
		}
		if ("".equals(getEmail()) || getEmail()==null) {
			ActionError error =
				new ActionError("error.editUser.emailMissing");
			errors.add("email", error);
		}
		if ("".equals(getPassword()) || getPassword()==null) {
			ActionError error =
				new ActionError("error.editUser.passwordMissing");
			errors.add("password", error);
		}	
		return errors;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping, HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		setName(null);
		setEmail(null);
		setGroups(null);
		setPassword(null);
	}

	
	/**
	 * Returns the email.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Returns the groups.
	 * @return String[]
	 */
	public String[] getGroups() {
		if( groups == null ) {
			return new String[0];
		}
		return groups;
	}


	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the email.
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * Sets the groups.
	 * @param groups The groups to set
	 */
	public void setGroups(String[] groups) {
		this.groups = groups;
	}


	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Returns the password.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Sets the password.
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


}
