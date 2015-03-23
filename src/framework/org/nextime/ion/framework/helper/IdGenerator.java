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

package org.nextime.ion.framework.helper;

import java.util.Vector;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.logger.Logger;

/**
 * Générateur d'id pour les publications et les sections
 *
 * @author gbort
 * @version 1.0
 */
public class IdGenerator {

	/**
	 * Prochain id libre pour une section
	 */
	public static String nextSectionId() {
		try {
			String id = "";
			Vector v = Section.listAll();
			int max = 0;
			for (int i = 0; i < v.size(); i++) {
				Section s = (Section) v.get(i);
				String tid = s.getId();
				if (tid.startsWith("s")) {
					try {
						String ss = tid.substring(1);
						int n = Integer.parseInt(ss);
						if (n >= max)
							max = n + 1;
					} catch (NumberFormatException e) {
						//e.printStackTrace();
					}
				}
			}
			id = "s" + max;
			return id;
		} catch (Exception e) {
			Logger.getInstance().error(
				"Erreur lors de la generation de l'id : " + e.getMessage(),
				IdGenerator.class,
				e);
			return null;
		}
	}

	/**
	 * Prochain id libre pour une publication
	 */
	public static String nextPublicationId() {
		try {
			String id = "";
			Vector v = Publication.listAll();
			int max = 0;
			for (int i = 0; i < v.size(); i++) {
				Publication s = (Publication) v.get(i);
				String tid = s.getId();
				if (tid.startsWith("p")) {
					try {
						String ss = tid.substring(1);
						int n = Integer.parseInt(ss);
						if (n >= max)
							max = n + 1;
					} catch (NumberFormatException e) {
						//e.printStackTrace();
					}
				}
			}
			id = "p" + max;
			return id;
		} catch (Exception e) {
			return null;
		}
	}

}
