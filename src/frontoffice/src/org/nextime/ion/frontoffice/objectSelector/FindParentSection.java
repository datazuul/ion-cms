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

import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.logger.Logger;

/**
 * Renvoi une collection d'objets Section racines ( dont le parent vaut null )
 */
public class FindParentSection implements ObjectSelector {

	public Collection selectObjects(Hashtable params, HttpServletRequest request, HttpServletResponse response) throws SelectException {
		try {
			Vector v = new Vector();
			int level = Integer.parseInt(params.get("level")+"");
			Section s = Section.getInstance(params.get("section")+"");
			if( level > s.getLevel() ) return v;
			Section parent = s;
			while( parent.getLevel() > level ) {
				parent = parent.getParent();
			}
			v.add(parent);
			return v;
		} catch (Exception e) {
			Logger.getInstance().error("Erreur du SelectObject : "+e.getMessage(),this,e);
			throw new SelectException(e.getMessage());
		}
	}

}
