package org.nextime.ion.admin.form;

import org.apache.struts.action.*;
import java.util.*;

public class GroupForm extends ActionForm {
	
	private String _id;
	private String _description;
	private String[] _usersId;	
	
	public String getId() {
		return _id;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public void setId( String value ) {
		_id = value.trim();
	}
	
	public void setDescription( String value ) {
		_description = value;
	}
	
	public String[] getUsers() {
		return _usersId;
	}
	
	public void setUsers( String[] value ) {
		_usersId = value;
	}
	
	public void reset() {
		_id = null;
		_description = null;
		_usersId = new String[0];		
	}

}

