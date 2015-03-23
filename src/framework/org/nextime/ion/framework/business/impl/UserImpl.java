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

package org.nextime.ion.framework.business.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.Persistent;
import org.exolab.castor.jdo.QueryResults;
import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.PublicationVersion;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.event.WcmEvent;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;

/** 
 * Une implementation de User
 * @author gbort
 * @version 1.0
 */
public class UserImpl extends User implements Persistent, Comparable {

	// propriétés

	private String _login;
	private String _password;
	private Hashtable _metaData;
	private Vector _groups;
	private static Vector _listeners = new Vector();

	// pour que la classe soit constructible par castor

	public UserImpl() {
		_password = "";
		_metaData = new Hashtable();
		_groups = new Vector();
	}

	// implémentation des méthodes abstraites

	public String getLogin() {
		return _login;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String value) {
		_password = value;
	}

	public void setMetaData(String key, Object value) throws MappingException {
		if (!(value instanceof java.io.Serializable)) {
			throw new MappingException(
				value.getClass()
					+ " n'implemente pas l'interface java.io.Serializable");
		}
		_metaData.put(key, value);
	}

	public Object getMetaData(String key) {
		return _metaData.get(key);
	}

	public void removeMetaData(String key) {
		_metaData.remove(key);
	}

	public void addGroup(Group group) {
		if (!_groups.contains(group)) {
			_groups.add(group);
			group.addUser(this);
		}
	}

	public void removeGroup(Group group) {
		_groups.remove(group);
	}

	public boolean isInGroup(Group group) {
		for (int i = 0; i < _groups.size(); i++) {
			if (_groups.get(i).equals(group)) {
				return true;
			}
		}
		return false;
	}

	public Vector listGroups() {
		return (Vector) _groups.clone();
	}

	public Vector listPublications() {
		Vector v = new Vector();
		try {
			OQLQuery oql =
				Mapping.getInstance().getDb().getOQLQuery(
					"SELECT p FROM org.nextime.ion.framework.business.impl.PublicationVersionImpl p WHERE author = $1");
			oql.bind(this);
			QueryResults results = oql.execute();
			while (results.hasMore()) {
				PublicationVersion s = (PublicationVersion) results.next();
				v.add(s);
			}
		} catch (Exception e) {
			Logger.getInstance().error(
				"erreur lors du list des publication de l'auteur",
				this,
				e);
			return v;
		}
		return v;
	}

	public String[] getGroupsIds() {
		String[] groups = new String[_groups.size()];
		for (int i = 0; i < _groups.size(); i++) {
			groups[i] = ((Group) _groups.get(i)).getId();
		}
		return groups;
	}

	public void resetGroups() {
		for (int i = 0; i < _groups.size(); i++) {
			Group g = (Group) _groups.get(i);
			g.removeUser(this);
		}
		_groups = new Vector();
	}

	public Hashtable getMetaData() {
		return _metaData;
	}

	// méthodes non publiées

	public void setLogin(String value) {
		_login = value;
	}

	public Vector getGroups() {
		return _groups;
	}

	public void setGroups(Vector v) {
		_groups = v;
	}

	public void setMetaData(Hashtable ht) {
		_metaData = ht;
	}

	// méthodes pour la lecture/écriture du blob de meta données

	public byte[] getMetaDataBytes() throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(_metaData);
		byte[] result = os.toByteArray();
		os.close();
		return result;
	}

	public void setMetaDataBytes(byte[] value) throws Exception {
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(value);
			ObjectInputStream ois = new ObjectInputStream(is);
			Object o = ois.readObject();
			is.close();
			_metaData = (Hashtable) o;
		} catch (NullPointerException e) {
			_metaData = new Hashtable();
		}
	}

	// redéfinition des méthodes de Object

	public String toString() {
		return "type[USER] properties["
			+ _login
			+ ";"
			+ _password
			+ "] metaData"
			+ _metaData
			+ " groups"
			+ _groups;
	}

	public boolean equals(Object o) {
		try {
			return ((UserImpl) o).getLogin().equals(getLogin());
		} catch (Exception e) {
			return false;
		}
	}
	
	// comparer : par ordre alphabetique du login
	
	public int compareTo(Object o) {
		try {
			UserImpl u = (UserImpl)o;
			return u.getLogin().compareTo(getLogin());
		} catch( Exception e ) {
			return -1;
		}
	}

	// méthodes utiles pour le wrapper xml de publications
	// tant que castor ne permet pas de rendre les Hashtables
	// correctement	

	public Vector getMetaDataFields() {
		Enumeration keys = _metaData.keys();
		Vector v = new Vector();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			v.add(new DataField(key + "", _metaData.get(key) + ""));
		}
		return v;
	}

	// heritées de Persitent

	public void jdoPersistent(Database db) {
	}

	public void jdoTransient() {
	}

	public Class jdoLoad(short accessMode) {
		return null;
	}

	public void jdoBeforeCreate(Database db) {
	}

	public void jdoAfterCreate() {
		WcmEvent event = new WcmEvent(this, getLogin());
		for (int i = 0; i < _listeners.size(); i++) {
			((WcmListener) _listeners.get(i)).objectCreated(event);
		}
	}

	public void jdoStore(boolean modified) {
		if (modified) {
			WcmEvent event = new WcmEvent(this, getLogin());
			for (int i = 0; i < _listeners.size(); i++) {
				((WcmListener) _listeners.get(i)).objectModified(event);
			}
		}
	}

	public void jdoBeforeRemove() {
	}

	public void jdoAfterRemove() {
		WcmEvent event = new WcmEvent(this, getLogin());
		for (int i = 0; i < _listeners.size(); i++) {
			((WcmListener) _listeners.get(i)).objectDeleted(event);
		}
	}

	public void jdoUpdate() {
	}

	// gestion des évenements

	public static void addListener(WcmListener listener) {
		_listeners.add(listener);
	}

	public static void removeListener(WcmListener listener) {
		_listeners.remove(listener);
	}

}
