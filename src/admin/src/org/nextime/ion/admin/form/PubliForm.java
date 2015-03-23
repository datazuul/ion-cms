package org.nextime.ion.admin.form;

import org.apache.struts.action.*;
import java.util.*;

public class PubliForm extends ActionForm {
	
	private String _id;
	private String _name;
	private String[] _sectionsId;
	private String[] _categoriesId;	
	private String _type;
	private String _author;
	private String _state;
	private String _workflow;
	private String _workflowUser;
	private String _version;
	
	public String getId() {
		return _id;
	}
	
	public void setVersion( String value ) {
		_version = value.trim();
	}
	
	public String getVersion() {
		return _version;
	}
	
	public void setId( String value ) {
		_id = value.trim();
	}
	
	public String[] getSections() {
		return _sectionsId;
	}
	
	public void setSections( String[] value ) {
		_sectionsId = value;
	}
	
	public String[] getCategories() {
		return _categoriesId;
	}
	
	public void setCategories( String[] value ) {
		_categoriesId = value;
	}
	
	public String getState() {
		return _state;
	}
	
	public void setState( String value ) {
		_state = value;
	}
	
	public String getWorkflow() {
		return _workflow;
	}
	
	public void setWorkflow( String value ) {
		_workflow = value;
	}
	
	public String getWorkflowUser() {
		return _workflowUser;
	}
	
	public void setWorkflowUser( String value ) {
		_workflowUser = value;
	}
	
	public String getAuthor() {
		return _author;
	}
	
	public void setAuthor( String value ) {
		_author = value;
	}
	
	public String getType() {
		return _type;
	}
	
	public void setType( String value ) {
		_type = value;
	}
	
	public void reset() {
		_id = null;
		_name = null;
		_author = null;
		_type = null;
		_state = "";
		_sectionsId = new String[0];
		_categoriesId = new String[0];		
	}

}

