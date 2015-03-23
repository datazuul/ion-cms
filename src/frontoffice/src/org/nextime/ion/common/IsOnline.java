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

package org.nextime.ion.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.PublicationVersion;
import org.nextime.ion.framework.business.User;

public class IsOnline {
	
	public static String frontUserLogin = "visiteurAnonyme";

	public static boolean getStatus(Publication p) {
		try {
			Vector v = p.getVersions();
			for (int i = 1; i <= v.size(); i++) {
				if (p
					.getVersion(i)
					.getWorkflow(User.getInstance(frontUserLogin))
					.getPermissions()
					.contains("canDisplay"))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean getStatus(Object p) {
		try {
			return getStatus((Publication) p);
		} catch (Exception e) {
			return false;
		}
	}

	public static int getMostRecentVersion(Publication p) {
		try {
			Vector v = p.getVersions();
			for (int i = 0; i < v.size(); i++) {
				PublicationVersion ver = (PublicationVersion) v.get(i);
				if (ver
					.getWorkflow(User.getInstance(frontUserLogin))
					.getPermissions()
					.contains("canDisplay"))
					return ver.getVersion();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
