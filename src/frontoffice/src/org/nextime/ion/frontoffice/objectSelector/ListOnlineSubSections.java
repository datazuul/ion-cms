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

import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.logger.Logger;

/**
 * Renvoi une collection d'objets Section situées directement
 * sous la Section spécifiée par le parametre "rootSection"
 */
public class ListOnlineSubSections implements ObjectSelector {

	public Collection selectObjects(Hashtable params, HttpServletRequest request, HttpServletResponse response) throws SelectException {
		try {
			String rootSection = (String) params.get("rootSection");
			Section root = Section.getInstance(rootSection);
			Vector v = root.listSubSections();
			Vector result = new Vector();
			for( int i=0; i<v.size(); i++ ) {
					Section s = (Section)v.get(i);
					if( !"offline".equalsIgnoreCase(s.getMetaData("status")+"") ) {
						result.add(s);
					}
			}
			return result;
		} catch (Exception e) {
			Logger.getInstance().error("Erreur du SelectObject", this, e);
			throw new SelectException(e.getMessage());
		}
	}

}
