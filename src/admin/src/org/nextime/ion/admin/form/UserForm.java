package org.nextime.ion.admin.form;

import org.apache.struts.action.*;
import java.util.*;

public class UserForm extends ActionForm {
	
	private String _login;
	private String _password;
	private String[] _groupsId;	
	
	public String getLogin() {
		return _login;
	}
	
	public String getPassword() {
		return _password;
	}
	
	public void setLogin( String value ) {
		_login = value.trim();
	}
	
	public void setPassword( String value ) {
		_password = value;
	}
	
	public String[] getGroups() {
		return _groupsId;
	}
	
	public void setGroups( String[] value ) {
		_groupsId = value;
	}
	
	public void reset() {
		_login = null;
		_password = null;
		_groupsId = new String[0];		
	}

}

