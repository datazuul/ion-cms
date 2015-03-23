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

package org.nextime.ion.frontoffice.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nextime.ion.common.IsOnline;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;
import org.nextime.ion.frontoffice.bean.IonStatus;
import org.nextime.ion.frontoffice.bean.SectionTypes;
import org.nextime.ion.frontoffice.exception.IdNotFoundException;
import org.nextime.ion.frontoffice.smartCache.SmartCacheManager;

public class PublicationServlet extends HttpServlet {

	public void service(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {

		try {					

			CommonThings.common(request,response);
			
			// cherche l'identifiant de la publication demandée dans
			// la query string
			String requestedId =
				(request.getPathInfo() != null)
					? request.getPathInfo().substring(1)
					: null;
			if (requestedId != null) {
				if (requestedId.indexOf(".") != -1) {
					requestedId =
						requestedId.substring(0, requestedId.indexOf("."));
				}
			}				
			
			// rollback dirty transaction ??
			if( Mapping.getInstance().isTransactionActive() ) {
				try {
					Mapping.rollback();
				} catch(Exception e) {}
			}
			
			// demarre une transaction pour accéder aux 
			// données du framework 
			Mapping.begin();
			
			// recupére une instance sur la Publication
			Publication publication;
			try {
				publication = Publication.getInstance(requestedId);
			} catch( MappingException e ) {
				throw new IdNotFoundException();
			}
			
			// recupére la section de cette publication
			Section section = (Section)publication.listSections().firstElement();
			
			// créé le bean de status
			IonStatus status = new IonStatus();
			status.setCurrentSection(section);
			status.setCurrentPublication(publication);
			status.setCurrentVersion(  IsOnline.getMostRecentVersion(publication) );
			if( request.getParameter("static")!=null ) status.setIsStatic(true);
			request.setAttribute("ionStatus", status);

			// utilise la metaData "template" de la section
			// pour decider vers quelle vue rediriger le flux
			String template = (String) section.getMetaData("template");
			if (template == null)
				template = "default";	
				
			String jsp = SectionTypes.getSectionBean(this,template).getJsp();
			
			if( request.getParameter("template")!=null ) {
				jsp = request.getParameter("template")+".jsp";
			}

			getServletContext()
				.getRequestDispatcher(
					"/templates/" + jsp)
				.forward(request, response);
				
			Mapping.rollback();			

		} catch (Exception e) {
			Mapping.rollback();			
			//transmet l'exception a tomcat
			throw new ServletException(e);
		}
	}

	protected String getDefaultSectionId() throws MappingException {
		// selectionne la premiére des sections racines		
		Vector rootSections = Section.listRootSections();
		return ((Section) rootSections.get(0)).getId();
	}

}
