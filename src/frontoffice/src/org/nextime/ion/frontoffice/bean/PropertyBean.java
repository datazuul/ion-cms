/*
 * ÏON content management system.
 * Copyright (C) 2002  Guillaume Bort(gbort@msn.com). All rights reserved.
 * 
 * Copyright (c) 2000 The Apache Software Foundation. All rights reserved.
 * Copyright 2000-2002 (C) Intalio Inc. All Rights Reserved.
 * 
 * ÏON is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * ÏON core framework, ÏON content server, ÏON backoffice, ÏON frontoffice
 * and ÏON admin application are parts of ÏON and are distributed under
 * same terms of licence.
 * 
 * 
 * ÏON includes software developed by the Apache Software Foundation (http://www.apache.org/)
 * and software developed by the Exolab Project (http://www.exolab.org).
 * 
 * ÏON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package org.nextime.ion.frontoffice.bean;

public class PropertyBean {

	private String label;
	private String name;
	private String regexp;
	private String type;
	private String errorMessage;
	private String required;	
	
	public void setSectionType( TypeBean type ) {
		type.addProperty(this);
	}
	
	/**
	 * Returns the errorMessage.
	 * @return String
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * Returns the label.
	 * @return String
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}


	/**
	 * Returns the regexp.
	 * @return String
	 */
	public String getRegexp() {
		return regexp;
	}


	/**
	 * Returns the required.
	 * @return String
	 */
	public String getRequired() {
		return required;
	}


	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}


	/**
	 * Sets the errorMessage.
	 * @param errorMessage The errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * Sets the label.
	 * @param label The label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}


	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Sets the regexp.
	 * @param regexp The regexp to set
	 */
	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}


	/**
	 * Sets the required.
	 * @param required The required to set
	 */
	public void setRequired(String required) {
		this.required = required;
	}


	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


}
