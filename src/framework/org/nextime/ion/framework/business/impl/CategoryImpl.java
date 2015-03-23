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
import org.nextime.ion.framework.business.Category;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.event.WcmEvent;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.mapping.MappingException;

/** 
 * Une implementation de Category
 * @author gbort
 * @version 1.3
 */
public class CategoryImpl extends Category implements Persistent {

	// propriétées

	private String _id;
	private String _description;
	private Hashtable _metaData;
	private Vector _publications;
	private static Vector _listeners = new Vector();

	// pour que la classe soit constructible par castor

	public CategoryImpl() {
		_description = "";
		_metaData = new Hashtable();
		_publications = new Vector();
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

	public void addPublication(Publication p) {
		if (!_publications.contains(p)) {
			_publications.add(p);
			p.addCategory(this);
		}
	}

	public void removePublication(Publication p) {
		_publications.remove(p);
	}

	public boolean isInThisCategory(Publication p) {
		return _publications.contains(p);
	}

	public Vector listPublications() {
		return (Vector) _publications.clone();
	}

	public String[] getPublicationsIds() {
		String[] publications = new String[_publications.size()];
		for (int i = 0; i < _publications.size(); i++) {
			publications[i] = ((Publication) _publications.get(i)).getId();
		}
		return publications;
	}

	public void resetPublications() {
		for (int i = 0; i < _publications.size(); i++) {
			Publication g = (Publication) _publications.get(i);
			g.removeCategory(this);
		}
		_publications = new Vector();
	}

	// méthodes non publiées

	public void setId(String value) {
		_id = value;
	}

	public Vector getPublications() {
		return _publications;
	}

	public void setPublications(Vector v) {
		_publications = v;
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

	// méthodes utiles pour le wrapper xml de publications
	// tant que cator ne permet pas de rendre les Hashtables
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
			+ " publications["
			+ _publications.size()
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
