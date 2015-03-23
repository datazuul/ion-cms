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

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class IterateOverTag extends BodyTagSupport {

	private String _var;
	private Collection _collection;
	private Iterator _iterator;
	private IterateStatus _status;

	public IterateOverTag() {
		super();
		init();
	}

	public void setVar(String var) {
		_var = var;
	}

	public int doStartTag() throws JspException {

		Tag t = findAncestorWithClass(this, SelectObjectsTag.class);
		if (t == null)
			throw new JspTagException("Le tag iterateOver doit etre dans un tag selectObjects");

		_collection = ((SelectObjectsTag) t).getCollection();

		if (_collection == null)
			return (SKIP_BODY);
			
		_status = new IterateStatus();
		_status._size = _collection.size();
		_status._index = 0;

		_iterator = _collection.iterator();

		if (_iterator.hasNext()) {
			Object element = _iterator.next();
			if (element == null)
				pageContext.removeAttribute(_var);
			else
				pageContext.setAttribute(_var, element);
			_status._index++;
			pageContext.setAttribute("iterateStatus", _status);
			return (EVAL_BODY_TAG);
		} else
			return (SKIP_BODY);
	}

	public int doAfterBody() throws JspException {

		if (bodyContent != null) {
			JspWriter writer = pageContext.getOut();
			if (writer instanceof BodyContent)
				writer = ((BodyContent) writer).getEnclosingWriter();
			try {
				writer.print(bodyContent.getString());
			} catch (IOException e) {
				throw new JspException(e);
			}
			bodyContent.clearBody();
		}

		if (_iterator.hasNext()) {
			Object element = _iterator.next();
			if (element == null)
				pageContext.removeAttribute(_var);
			else
				pageContext.setAttribute(_var, element);
			_status._index++;
			pageContext.setAttribute("iterateStatus", _status);
			return (EVAL_BODY_TAG);
		} else
			return (SKIP_BODY);

	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	public void release() {
		super.release();
		init();
	}

	private void init() {
		_var = null;
		_status = null;
	}

}
