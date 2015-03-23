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

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.struts.digester.Digester;

public class ResourceXmlBean {

	private String id;
	private String label;
	private String directory;
	private String icon;
	private static Hashtable roots = new Hashtable();

	protected static ResourceXmlBean parse(InputStream in) throws Exception {
		ResourceXmlBean bean = new ResourceXmlBean();
		Digester digester = new Digester();
		digester.push(bean);
		digester.setValidating(false);
		digester.addObjectCreate(
			"resources-description/resources",
			"org.nextime.ion.frontoffice.bean.ResourceXmlBean");
		digester.addSetProperties("resources-description/resources");
		digester.addSetNext("resources-description/resources", "addResource");
		digester.parse(in);
		return bean;
	}

	protected static ResourceXmlBean getResource(String id) {
		return (ResourceXmlBean) roots.get(id);
	}

	protected static Vector getItems() {
		Enumeration elts = roots.elements();
		Vector retour = new Vector();
		while (elts.hasMoreElements()) {
			retour.add(elts.nextElement());
		}
		return retour;
	}

	public static void addResource(ResourceXmlBean bean) {
		roots.put(bean.getId(), bean);
	}

	/**
	 * Returns the directory.
	 * @return String
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * Returns the icon.
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the label.
	 * @return String
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the directory.
	 * @param directory The directory to set
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	/**
	 * Sets the icon.
	 * @param icon The icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the label.
	 * @param label The label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

}
