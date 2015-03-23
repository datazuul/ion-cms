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

package org.nextime.ion.framework.mapping;

import java.util.Enumeration;
import java.util.Hashtable;

import org.exolab.castor.jdo.Database;
import org.nextime.ion.framework.logger.Logger;

/**
 * libere la couche mapping des thread qui ne sont plus actifs
 * 
 * @author gbort
 * @version 0.1
 */
public class MappingCleanerDaemon extends Thread {

	private Mapping _mapping;

	public MappingCleanerDaemon(Mapping mapping) {
		super("Mapping Cleaner");
		_mapping = mapping;
		setDaemon(true);
		start();
	}

	public void run() {
		for (;;) {
			try {
				this.sleep(1000 * 60 * 10);
			} catch (InterruptedException e) {
			}
			Logger.getInstance().log("Clean Mapping", this);
			Hashtable dbs = _mapping.databases;
			Enumeration threads = dbs.keys();
			while (threads.hasMoreElements()) {
				Thread thread = (Thread) threads.nextElement();
				if (!thread.isAlive()) {
					Logger.getInstance().log(
						"Le thread "
							+ thread
							+ " est mort. Destruction de son instance de Database",
						this);
					Database db = (Database) dbs.get(thread);
					try {
						db.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					dbs.remove(thread);
					thread.destroy();
				}
			}
		}
	}

}
