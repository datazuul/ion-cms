/*
 * �ON content management system.
 * Copyright (C) 2002  Guillaume Bort(gbort@msn.com). All rights reserved.
 * 
 * Copyright (c) 2000 The Apache Software Foundation. All rights reserved.
 * Copyright 2000-2002 (C) Intalio Inc. All Rights Reserved.
 * 
 * �ON is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * �ON core framework, �ON content server, �ON backoffice, �ON frontoffice
 * and �ON admin application are parts of �ON and are distributed under
 * same terms of licence.
 * 
 * 
 * �ON includes software developed by the Apache Software Foundation (http://www.apache.org/)
 * and software developed by the Exolab Project (http://www.exolab.org).
 * 
 * �ON is distributed in the hope that it will be useful,
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
import org.nextime.ion.framework.helper.SearchResult;
import org.nextime.ion.framework.helper.Searcher;
import org.nextime.ion.framework.helper.Viewer;
import org.nextime.ion.framework.logger.Logger;

public class SearchPublications implements ObjectSelector {

	public Collection selectObjects(
		Hashtable params,
		HttpServletRequest request,
		HttpServletResponse response)
		throws SelectException {

		try {
			String queryString = (String) params.get("queryString");
			String view = (String) params.get("view");
			String index = (String) params.get("index");

			String currentLocale =
				request.getSession().getAttribute("currentLocale") + "";

			Vector returnResults = new Vector();
			if (queryString.equals("") || index.equals(""))
				return returnResults;
			Vector results = Searcher.search(queryString, index);

			int count = 0;
			for (int i = 0; i < results.size(); i++) {
				SearchResult result = (SearchResult) results.get(i);

				// est ce la version actuellement publi�e ???
				if (result.getVersion()
					== IsOnline.getMostRecentVersion(result.getPublication())) {

					String viewResult = "";
					if (!view.equals("")) {
						viewResult =
							new String(
								Viewer.getView(
									result.getPublication(),
									result.getVersion(),
									view,
									currentLocale));
					}

					SearchPublicationResult spr = new SearchPublicationResult();
					PublicationResult pr = new PublicationResult();
					pr.setPublication(result.getPublication());
					pr.setVersion(result.getVersion());
					pr.setView(viewResult);
					spr.setPublicationResult(pr);
					spr.setSearchResult(result);

					returnResults.add(spr);
				}
			}
			return returnResults;
		} catch (Exception e) {
			Logger.getInstance().error("Erreur du SelectObject", this, e);
			throw new SelectException(e.getMessage());
		}
	}

}
