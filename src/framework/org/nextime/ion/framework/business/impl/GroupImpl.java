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
import org.exolab.castor.jdo.Persistent;
import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.event.WcmEvent;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.mapping.MappingException;

/** 
 * Une implementation de Group
 * @author gbort
 * @version 1.1
 */
public class GroupImpl extends Group implements Persistent {

	// propriétés

	private String _id;
	private String _description;
	private Hashtable _metaData;
	private Vector _users;
	private static Vector _listeners = new Vector();

	// pour que la classe soit constructible par castor

	public GroupImpl() {
		_description = "";
		_metaData = new Hashtable();
		_users = new Vector();
	}

	// implémentation des méthodes abstraites

	public String getId() {
		return _id;
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

	public Hashtable getMetaData() {
		return _metaData;
	}

	public Vector listUsers() {
		return (Vector) _users.clone();
	}

	public String[] getUsersIds() {
		String[] users = new String[_users.size()];
		for (int i = 0; i < _users.size(); i++) {
			users[i] = ((User) _users.get(i)).getLogin();
		}
		return users;
	}

	public void resetUsers() {
		for (int i = 0; i < _users.size(); i++) {
			User g = (User) _users.get(i);
			g.removeGroup(this);
		}
		_users = new Vector();
	}

	public void addUser(User user) {
		if (!_users.contains(user)) {
			_users.add(user);
			user.addGroup(this);
		}

	}

	public void removeUser(User user) {
		_users.remove(user);
	}

	public boolean isInThisGroup(User user) {
		for (int i = 0; i < _users.size(); i++) {
			if (_users.get(i).equals(user)) {
				return true;
			}
		}
		return false;
	}

	// méthodes non publiées

	public void setId(String value) {
		_id = value;
	}

	public void setMetaData(Hashtable ht) {
		_metaData = ht;
	}

	public Vector getUsers() {
		return _users;
	}

	public void setUsers(Vector v) {
		_users = v;
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

	// redifinition des méthodes de Object

	public String toString() {
		return "type[GROUP] properties["
			+ _id
			+ ";"
			+ _description
			+ "] metaData"
			+ _metaData
			+ " members["
			+ _users.size()
			+ "]";
	}

	public boolean equals(Object o) {
		try {
			return ((GroupImpl) o).getId().equals(getId());
		} catch (Exception e) {
			return false;
		}
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
		WcmEvent event = new WcmEvent(this, getId());
		for (int i = 0; i < _listeners.size(); i++) {
			((WcmListener) _listeners.get(i)).objectCreated(event);
		}
	}

	public void jdoStore(boolean modified) {
		if (modified) {
			WcmEvent event = new WcmEvent(this, getId());
			for (int i = 0; i < _listeners.size(); i++) {
				((WcmListener) _listeners.get(i)).objectModified(event);
			}
		}
	}

	public void jdoBeforeRemove() {
	}

	public void jdoAfterRemove() {
		WcmEvent event = new WcmEvent(this, getId());
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
