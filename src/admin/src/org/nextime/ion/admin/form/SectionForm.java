package org.nextime.ion.admin.form;

import org.apache.struts.action.*;
import java.util.*;

public class SectionForm extends ActionForm {
	
	private String _id;
	private String _parent;		
	
	public String getId() {
		return _id;
	}
	
	public String getParent() {
		return _parent;
	}
	
	public void setId( String value ) {
		_id = value.trim();
	}
	
	public void setParent( String value ) {
		_parent = value;
	}	
	
	
	public void reset() {
		_id = null;
		_parent = null;				
	}

}

