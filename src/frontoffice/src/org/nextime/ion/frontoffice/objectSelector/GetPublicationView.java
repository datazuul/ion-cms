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

import org.nextime.ion.common.IsOnline;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.helper.Viewer;
import org.nextime.ion.framework.logger.Logger;

public class GetPublicationView implements ObjectSelector {

	public Collection selectObjects(
		Hashtable params,
		HttpServletRequest request,
		HttpServletResponse response)
		throws SelectException {
		try {
			String publication = (String) params.get("publication");
			String view = (String) params.get("view");

			String currentLocale =
				request.getSession().getAttribute("currentLocale") + "";

			Publication p = Publication.getInstance(publication);

			int version = 1;
			if (params.get("version") == null) {
				version = IsOnline.getMostRecentVersion(p);
			} else {
				version = Integer.parseInt(params.get("version") + "");
			}

			PublicationResult r = new PublicationResult();
			r.setVersion(version);
			r.setPublication(p);
			if (view != null) {
				r.setView(
					new String(
						Viewer.getView(p, version, view, currentLocale)));
			}
			Vector v2 = new Vector();
			v2.add(r);
			return v2;
		} catch (Exception e) {
			Logger.getInstance().error("Erreur du SelectObject", this, e);
			throw new SelectException(e.getMessage());
		}
	}

}
