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
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.Persistent;
import org.exolab.castor.xml.Marshaller;
import org.jdom.Document;
import org.nextime.ion.framework.business.Category;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.PublicationVersion;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.TypePublication;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.workflow.Workflow;
import org.nextime.ion.framework.config.Config;
import org.nextime.ion.framework.event.WcmEvent;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;
import org.nextime.ion.framework.xml.XML;

import com.opensymphony.workflow.basic.BasicWorkflow;

/** 
 * Une implementation de Publication
 * @author gbort
 * @version 1.7
 */
public class PublicationImpl
	extends Publication
	implements Persistent, Comparable {

	// propriétés 

	private String _id;
	private Hashtable _metaData;
	private String _name;
	private TypePublication _type;
	private Vector _sections;
	private Vector _categories;
	private Date _date;
	private Vector _versions;
	private String _workflowType;
	private boolean _canBeModified = false;
	private boolean _modified = false;
	private static Vector _listeners = new Vector();

	// pour que la classe soit constructible par castor

	public PublicationImpl() {
		_name = "";
		_metaData = new Hashtable();
		_sections = new Vector();
		_categories = new Vector();
		_type = null;
		_date = null;
		_versions = new Vector();
	}

	public boolean isInSection(Section s) {
		return _sections.contains(s);
	}

	public void removeSection(Section s) {
		_sections.remove(s);
		postChange();
	}

	public void addSection(Section s) {
		if (!_sections.contains(s)) {
			_sections.add(s);
			s.addPublication(this);
		}
		postChange();
	}

	public Vector listSections() {
		return (Vector) getSections().clone();
	}

	public void setDate(Date date) {
		_date = date;
		postChange();
	}

	public Date getDate() {
		return _date;
	}

	public String getFormatedDate() {
		if (_date == null)
			return "";
		String pattern = Config.getInstance().getDateFormatPattern();
		if (pattern == null)
			return _date.toString();
		return new SimpleDateFormat(pattern).format(_date);
	}

	public TypePublication getType() {
		return _type;
	}

	public void removeMetaData(String key) {
		_metaData.remove(key);
		postChange();
	}

	public Object getMetaData(String key) {
		return _metaData.get(key);
	}

	public void setMetaData(String key, Object value) throws MappingException {
		if (!(value instanceof java.io.Serializable)) {
			throw new MappingException(
				value.getClass()
					+ " n'implemente pas l'interface java.io.Serializable");
		}
		_metaData.put(key, value);
		postChange();
	}

	public String getId() {
		return _id;
	}

	public Vector listCategories() {
		return (Vector) _categories.clone();
	}

	public void addCategory(Category c) {
		if (!_categories.contains(c)) {
			_categories.add(c);
			c.addPublication(this);
		}
		postChange();
	}

	public void removeCategory(Category c) {
		_categories.remove(c);
		postChange();
	}

	public boolean isInCategory(Category c) {
		return _categories.contains(c);
	}

	public Hashtable getMetaData() {
		return _metaData;
	}

	public String[] getSectionsIds() {
		String[] sections = new String[_sections.size()];
		for (int i = 0; i < _sections.size(); i++) {
			sections[i] = ((Section) _sections.get(i)).getId();
		}
		return sections;
	}

	public void resetSections() {
		for (int i = 0; i < _sections.size(); i++) {
			Section g = (Section) _sections.get(i);
			g.removePublication(this);
		}
		_sections = new Vector();
		postChange();
	}

	public String[] getCategoriesIds() {
		String[] categories = new String[_categories.size()];
		for (int i = 0; i < _categories.size(); i++) {
			categories[i] = ((Category) _categories.get(i)).getId();
		}
		return categories;
	}

	public void resetCategories() {
		for (int i = 0; i < _categories.size(); i++) {
			Category g = (Category) _categories.get(i);
			g.removePublication(this);
		}
		_categories = new Vector();
		postChange();
	}

	public void changeType(TypePublication type) {
		setType(type);
	}

	public Vector getSections() {
		return _sections;
	}

	public void setSections(Vector v) {
		_sections = v;
		postChange();
	}

	public Vector getCategories() {
		return _categories;
	}

	public void setCategories(Vector v) {
		_categories = v;
		postChange();
	}

	public void setType(TypePublication type) {
		_type = type;
	}

	public void setId(String id) {
		_id = id;
		postChange();
	}

	public void setMetaData(Hashtable ht) {
		_metaData = ht;
	}

	public String getDatePubli() {
		if (getDate() == null)
			return null;
		return getDate().getTime() + "";
	}

	public void setDatePubli(String datePubli) {
		setDate(new Date(Long.parseLong(datePubli)));
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

	// redefinition des méthodes de Object

	public String toString() {
		return "type[PUBLICATION] properties["
			+ _id
			+ ";"
			+ _name
			+ "] metaData"
			+ _metaData
			+ " sections"
			+ _sections;
	}

	public boolean equals(Object o) {
		try {
			return ((PublicationImpl) o).getId().equals(getId());
		} catch (Exception e) {
			return false;
		}
	}

	// implementation de Comparable
	// une publication est inférieure à une 
	// autre si sa date est inférieure

	public int compareTo(Object o) {
		try {
			Publication p = (Publication) o;
			int r = p.getDate().compareTo(getDate());
			if (r == 0)
				return -1;
			if (r < 0)
				return -10;
			if (r > 0)
				return 10;
			return 0;
		} catch (Exception e) {
			return 0;
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
		if (_modified) {
			WcmEvent event = new WcmEvent(this, getId());
			for (int i = 0; i < _listeners.size(); i++) {
				((WcmListener) _listeners.get(i)).objectModified(event);
			}
		}
	}

	public void jdoBeforeRemove() {		
		// remove all versions
		Enumeration v = ((Vector)getVersions().clone()).elements();
		while( v.hasMoreElements() ) {
			try {
				PublicationVersionImpl p = (PublicationVersionImpl)v.nextElement();				
				p.remove();				
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}		
	}

	public void jdoAfterRemove() {			
		// dispatch event
		WcmEvent event = new WcmEvent(this, getId());
		for (int i = 0; i < _listeners.size(); i++) {
			((WcmListener) _listeners.get(i)).objectDeleted(event);
		}
	}

	public void jdoUpdate() {
	}

	public void postLoad() {
		_canBeModified = true;
	}

	public void postChange() {
		if (_canBeModified)
			_modified = true;
	}

	// gestion des évenements

	public static void addListener(WcmListener listener) {
		_listeners.add(listener);
	}

	public static void removeListener(WcmListener listener) {
		_listeners.remove(listener);
	}

	// versions

	public void setWorkflowType(String t) {
		_workflowType = t;
	}

	public String getWorkflowType() {
		return _workflowType;
	}

	public PublicationVersion getVersion(int ver) {
		try {			
			return (PublicationVersion) getVersions().get(getVersions().size()-ver);
		} catch (Exception e) {
			return null;
		}
	}

	public Vector getVersions() {
		Collections.sort(_versions);		
		return _versions;
	}

	public void setVersions(Vector versions) {
		_versions = versions;
	}

	public PublicationVersion getLastVersion() {
		try {
			return (PublicationVersion) getVersions().firstElement();
		} catch (Exception e) {
			return null;
		}
	}

	public void newVersion(User user) throws Exception {

		Document dataToInsert = getType().getModel();
		if (getLastVersion() != null) {
			dataToInsert = getLastVersion().getData();
		}

		PublicationVersionImpl version = new PublicationVersionImpl();
		version.setPublication(this);

		Hashtable ht = new Hashtable();
		ht.put("author", user.getLogin());
		ht.put("id", getId());
		ht.put("version", (getVersions().size() + 1)+"");
		Workflow wf = Workflow.create(user, getWorkflowType(), ht);
		version.setWorkflow(wf);

		version.setAuthor(user);
		version.setData(dataToInsert);
		version.setVersion(getVersions().size() + 1);
		version.setId(getId() + "_v" + version.getVersion());
		Mapping.getInstance().getDb().create(version);
		_versions.add(version);
		postChange();
	}

}
