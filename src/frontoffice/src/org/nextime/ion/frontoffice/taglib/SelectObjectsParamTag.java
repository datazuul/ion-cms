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

package org.nextime.ion.frontoffice.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class SelectObjectsParamTag extends BodyTagSupport {

	private String _value;
	private String _name;

	public SelectObjectsParamTag() {
		super();
		init();
	}

	public int doStartTag() throws JspException {
		evaluateExpressions();
		return super.doStartTag();
	}

	public int doEndTag() throws JspException {
		Tag t = findAncestorWithClass(this, SelectObjectsTag.class);
		if (t == null)
			throw new JspTagException("Le tag param doit etre dans un tag selectObjects");

		if (_name == null && _name.equals(""))
			return EVAL_PAGE;

		SelectObjectsTag parent = (SelectObjectsTag) t;
		String value = _value;
		if (value == null) {
			if (bodyContent == null || bodyContent.getString() == null)
				value = "";
			else
				value = bodyContent.getString().trim();
		}
		parent.addParameter(_name, value);
		return EVAL_PAGE;
	}

	public void release() {
		super.release();
		init();
	}

	public void setValue(String value) {
		_value = value;
	}

	public void setName(String name) {
		_name = name;
	}

	private void init() {
		_value = null;
		_name = null;
	}

	private void evaluateExpressions() throws JspException {
		if (_value != null && !"".equals(_value)) {
			_value =
				ExpressionEvaluatorManager.evaluate(
					"value",
					_value,
					Object.class,
					this,
					pageContext)
					+ "";
		}
	}

}
