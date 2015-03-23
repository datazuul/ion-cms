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

package org.nextime.ion.frontoffice.objectSelector;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.jdom.output.DOMOutputter;
import org.nextime.ion.common.IsOnline;
import org.nextime.ion.commons.PublicationSorter;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.impl.PublicationVersionImpl;
import org.nextime.ion.framework.helper.Viewer;
import org.nextime.ion.framework.logger.Logger;
import org.w3c.dom.Document;

/**
 */
public class ListOnlinePublications implements ObjectSelector {

	public Collection selectObjects(
		Hashtable params,
		HttpServletRequest request,
		HttpServletResponse response)
		throws SelectException {
		try {
			String section = (String) params.get("section");
			String view = (String) params.get("view");
			int max = Integer.parseInt((String) params.get("max"));
			String condition = (String) params.get("condition");

			String currentLocale =
				request.getSession().getAttribute("currentLocale") + "";

			Vector v =
				PublicationSorter.sortPublications(
					Section.getInstance(section));
			Vector v2 = new Vector();
			int nb = 0;
			for (int i = 0;(i < v.size() && nb < max); i++) {
				Publication p = (Publication) v.get(i);
				int version = IsOnline.getMostRecentVersion(p);
				if (IsOnline.getStatus(p)) {

					boolean conditionResult = true;
					if (condition != null && !"".equals(condition)) {
						Document doc =
							new DOMOutputter().output(
								((PublicationVersionImpl) p
									.getVersion(version))
									.getXmlDoc());
						XObject o = XPathAPI.eval(doc, condition);
						conditionResult = o.bool();
					}

					if (conditionResult) {
						nb++;
						PublicationResult r = new PublicationResult();
						r.setPublication(p);
						r.setVersion(version);
						if (view != null) {
							r.setView(
								new String(
									Viewer.getView(
										p,
										version,
										view,
										currentLocale)));
						}
						v2.add(r);
					}
				}
			}
			//Mapping.rollback();
			return v2;
		} catch (Exception e) {
			Logger.getInstance().error("Erreur du SelectObject", this, e);
			throw new SelectException(e.getMessage());
		}
	}

}
