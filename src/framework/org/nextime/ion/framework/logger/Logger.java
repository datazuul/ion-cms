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

package org.nextime.ion.framework.logger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.nextime.ion.framework.config.Config;

/**
 * Tenue d'un journal de log
 * 
 * @author gbort
 * @version 0.9
 */
public class Logger {

	private static Logger instance;
	private boolean _disableLog;

	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}

	private Logger() {
		BasicConfigurator.configure();
		_disableLog = Config.getInstance().getDisableLog();
	}

	public void log(String message, Object source) {
		if (!_disableLog)
			doLog(message, source.getClass(), Priority.INFO);
	}

	public void error(String message, Object source, Throwable e) {
		if (!_disableLog) {
			doLog(message, source.getClass(), Priority.ERROR);
			e.printStackTrace();
		}
	}

	public void fatal(String message, Object source, Throwable e) {
		if (!_disableLog)
			doLog(message, source.getClass(), Priority.FATAL);
		e.printStackTrace();
	}

	public void log(String message, Class source) {
		if (!_disableLog)
			doLog(message, source, Priority.INFO);	
	}

	public void error(String message, Class source, Throwable e) {
		if (!_disableLog) {
			doLog(message, source, Priority.ERROR);
			e.printStackTrace();
		}
	}

	public void fatal(String message, Class source, Throwable e) {
		if (!_disableLog) {
			doLog(message, source, Priority.FATAL);
			e.printStackTrace();
		}
	}

	private void doLog(String message, Class source, Priority p) {
		Category cat = Category.getInstance(source);
		cat.log(p, message);
	}
}
