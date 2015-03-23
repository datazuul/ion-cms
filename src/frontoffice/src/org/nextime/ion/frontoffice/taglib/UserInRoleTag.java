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

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class UserInRoleTag extends TagSupport {
	
	protected String _role = null;

    public String getRole() {
        return _role;
    }

    public void setRole(String role) {
        _role = role;
    }
	
	public int doStartTag() throws JspException {
        if (checkRole())
            return (EVAL_BODY_INCLUDE);
        else
            return (SKIP_BODY);
    }
    
    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }
    
    protected boolean checkRole() {
    	return ((HttpServletRequest)pageContext.getRequest()).isUserInRole(_role);
    }
}

