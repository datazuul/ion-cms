package org.nextime.ion.admin.form;

import org.apache.struts.action.*;
import java.util.*;

public class CategoryForm extends ActionForm {
	
	private String _id;
	
	public String getId() {
		return _id;
	}	
	
	public void setId( String value ) {
		_id = value.trim();
	}	
	
	public void reset() {
		_id = null;					
	}

}

