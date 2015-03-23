package org.nextime.ion.admin.form;

import org.apache.struts.action.*;
import java.util.*;

public class TypeForm extends ActionForm {
	
	private String _id;
	private String _description;
		
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
	
	public void reset() {
		_id = null;
		_description = null;			
	}

}

