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

package org.nextime.ion.frontoffice.smartCache;

import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.taglibs.cache.CacheUtil;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.impl.PublicationImpl;
import org.nextime.ion.framework.business.impl.SectionImpl;
import org.nextime.ion.framework.event.WcmEvent;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.mapping.Mapping;

public class SmartCacheManager extends HttpServlet implements WcmListener {

	static {
		PublicationImpl.addListener(new SmartCacheManager());
		SectionImpl.addListener(new SmartCacheManager());
	}
	
	private static ServletContext context;

	/**
	 * @see org.nextime.ion.framework.event.WcmListener#objectCreated(WcmEvent)
	 */
	public void objectCreated(WcmEvent event) {
		cleanCache();
	}

	/**
	 * @see org.nextime.ion.framework.event.WcmListener#objectDeleted(WcmEvent)
	 */
	public void objectDeleted(WcmEvent event) {
		cleanCache();
	}

	/**
	 * @see org.nextime.ion.framework.event.WcmListener#objectModified(WcmEvent)
	 */
	public void objectModified(WcmEvent event) {
		cleanCache();
	}

	protected void cleanCache() {
//		try {			
//			Vector sections = Section.listAll();			
//			for (int i = 0; i < sections.size(); i++) {
//				String id = ((Section) sections.get(i)).getId();
//				context.removeAttribute("org.apache.taglibs.cache.session.caches.section_"+id);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		context.setAttribute("ion_lastUpdate", System.currentTimeMillis()+"");
	}

	/**
	 * @see javax.servlet.Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig arg0) throws ServletException {
		context = arg0.getServletContext();
	}

}
