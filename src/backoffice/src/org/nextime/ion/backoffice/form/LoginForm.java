package org.nextime.ion.backoffice.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LoginForm extends ActionForm {
	
	private String login;
	private String password;

	/**
	 * Returns the login.
	 * @return String
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * Returns the password.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Sets the login.
	 * @param login The login to set
	 */
	public void setLogin(String login) {
		this.login = (login==null)?null:login.trim();
	}


	/**
	 * Sets the password.
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping, HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if( "".equals(getLogin()) ) {
			ActionError error = new ActionError("error.login.loginMissing");
			errors.add("login",error);
		}
		if( "".equals(getPassword()) ) {
			ActionError error = new ActionError("error.login.passwordMissing");
			errors.add("password",error);
		}
		return errors;
	}


	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping, HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		setLogin(null);
		setPassword(null);
	}


}

