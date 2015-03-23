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
 * ÏON includes software developed by the Apache Software Foundation (http://www.apache.org/)
 * and software developed by the Exolab Project (http://www.exolab.org).
 * 
 * ÏON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package org.nextime.ion.frontoffice.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.tag.common.core.*;
import java.util.*;
import org.nextime.ion.frontoffice.objectSelector.*;

public class SelectObjectsTag extends BodyTagSupport {

	private Hashtable _params;
	private String _type;

	// methodes héritées de ParentParam

	public void addParameter(String name, String value) {
		_params.put(name, value);
	}

	// accesseurs de propriétés

	public void setType(String type) {
		_type = type;
	}

	// methodes héritées de BodyTagSupport

	public int doStartTag() throws JspException {
		_params = new Hashtable();
		return (EVAL_BODY_INCLUDE);
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	public void release() {
		super.release();
		init();
	}

	private void init() {
		_params = new Hashtable();
	}

	public Collection getCollection() throws JspException {
		try {
			Object os = Class.forName(_type).newInstance();
			return ((ObjectSelector) os).selectObjects(
				_params,
				(HttpServletRequest) pageContext.getRequest(),
				(HttpServletResponse) pageContext.getResponse());
		} catch (Exception e) {
			throw new JspException(
				"Impossible d'invoquer le ObjectSelector : " + e.getMessage());
		}
	}
}
