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

package org.nextime.ion.framework.business;

import java.util.Hashtable;
import java.util.Vector;

import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;
import org.jdom.Document;
import org.nextime.ion.framework.business.impl.TypePublicationImpl;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;

/** 
 * L'objet <b>TypePublication</b> contient les informations
 * sur les champs de contenu des publications ainsi que sur les differents
 * styles de présentation. Chaque Publication appartient forcément à un et
 * un seul TypePublication.
 *
 * @author gbort
 * @version 1.1
 */
public abstract class TypePublication {

	// méthodes de fabrication

	/**
	 * Charge une instance d'un objet TypePublication existant correspondant à l'identifiant
	 * passé en paramétre.<i>(correspond à un SELECT)</i>
	 * @param id L'id de l'objet TypePublication à charger.
	 * @return Une instance de TypePublication.
	 * @throws MappingException si l'objet TypePublication est impossible à charger. 
	 * En particulier si l'id ne correspond à aucun objet TypePublication en base de donnée.
	 */
	public static TypePublication getInstance(String id)
		throws MappingException {
		try {
			TypePublication u =
				(TypePublication) Mapping.getInstance().getDb().load(
					TypePublicationImpl.class,
					id);
			Logger.getInstance().log(
				"Une instance de l'objet TypePublication pour l'id "
					+ id
					+ " à été chargée.",
				TypePublication.class);
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de charger une instance de l'objet TypePublication pour l'id "
					+ id
					+ ".";
			Logger.getInstance().error(message, TypePublication.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Créé un nouvel objet TypePublication avec l'id passé en paramétre.<i>(correspond à un INSERT)</i>
	 * @param id l'id de l'objet TypePublication à créer.
	 * @return Une instance de TypePublication.
	 * @throws MappingException si l'objet TypePublication est impossible à créer.
	 * En particulier si un objet TypePublication avec le même id existe déjà en base de donnée.
	 */
	public static TypePublication create(String id) throws MappingException {
		try {
			TypePublicationImpl u = new TypePublicationImpl();
			u.setId(id);
			Mapping.getInstance().getDb().create(u);
			Logger.getInstance().log(
				"Un objet TypePublication pour l'id " + id + " à été crée.",
				Group.class);
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de créer l'objet TypePublication pour l'id "
					+ id
					+ ".";
			Logger.getInstance().error(message, TypePublication.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Detruit l'objet TypePublication correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * Cette méthode est équivalente à un <code>getInstance(id).remove()</code>.
	 * @param id L'id du TypePublication à detruire.
	 * @throws MappingException si le TypePublication est impossible à détruire.
	 * En particulier si le getInstance à echoué.
	 */
	public static void remove(String id) throws MappingException {
		try {
			TypePublication u = getInstance(id);
			u.remove();
		} catch (MappingException e) {
			String message =
				"Etes vous certain qu'un objet TypePublication pour l'id "
					+ id
					+ " existe ?";
			Logger.getInstance().error(message, TypePublication.class, e);
		}
	}

	/**
	 * Detruit l'objet TypePublication correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * @param id L'id du TypePublication à detruire.
	 * @throws MappingException si le TypePublication est impossible à détruire.
	 */
	public void remove() throws MappingException {
		try {
			Mapping.getInstance().getDb().remove(this);
			Logger.getInstance().log(
				"L'objet TypePublication pour l'id "
					+ getId()
					+ " à été detruit.",
				Group.class);
		} catch (PersistenceException e) {
			String message =
				"Impossible de detruire l'objet TypePublication pour l'id "
					+ getId()
					+ ".";
			Logger.getInstance().error(message, TypePublication.class, e);
			throw new MappingException(message);
		}
	}

	// méthodes business

	/**
	 * Retourne l'identifiant de cet objet TypePublication
	 * @return id du Publication.
	 */
	public abstract String getId();

	/**
	 * Ajoute une nouvelle meta donnée à l'objet.<br>
	 * Une méta donnée est une paire clé/valeur ( String / Object ) 
	 * @param key clé sevant à identifier la meta donnée.
	 * @param value la valeur de la meta donnée. Peut être tout objet implémentant java.io.Serializable.
	 * @throws MappingException si l'objet que vous tentez d'ajouter comme meta donnée n'est pas Serializable
	 */
	public abstract void setMetaData(String key, Object value)
		throws MappingException;

	/**
	 * Retrouve une meta donnée de l'objet.
	 * @see #setMetaData(String,Object)
	 * @param key clé sevant à identifier la meta donnée.
	 * @return la meta donnée ou null si il n'y en a pas.
	 */
	public abstract Object getMetaData(String key);

	/**
	 * Retourne la Hashtable de meta donnée. 
	 * A utiliser avec précaution en écriture. 
	 * Préférez largement setMetaData()
	 * @return l'objet Hashtable contenant les meta données;
	 */
	public abstract Hashtable getMetaData();

	/**
	 * Detruit une meta donnée de l'objet.<br>
	 * Notez que cette méthode ne fait rien si il
	 * n'y avais pas de meta donnée.
	 * @see #setMetaData(String,Object)
	 * @param key clé sevant à identifier la meta donnée.
	 */
	public abstract void removeMetaData(String key);

	/**
	 * Renvoi la liste des styles disponibles pour ce type
	 * @return un Vetor de String
	 */
	public abstract Vector listStyles();

	/**
	 * renvoi une liste de toutes les objets Publication.
	 * @return un Vector d'objet Publication
	 * @throws MappingException en cas d'erreur lors du SELECT
	 */
	public static Vector listAll() throws MappingException {
		Vector v = new Vector();
		try {
			OQLQuery oql =
				Mapping.getInstance().getDb().getOQLQuery(
					"SELECT p FROM org.nextime.ion.framework.business.impl.TypePublicationImpl p");
			QueryResults results = oql.execute();
			while (results.hasMore()) {
				v.add(results.next());
			}
		} catch (Exception e) {
			Logger.getInstance().error(
				"erreur lors du listAll de TypePublication",
				TypePublication.class,
				e);
			throw new MappingException(e.getMessage());
		}
		return v;
	}
	
	public abstract Document getModel();

	// gestion des évenements

	/**
	 * Ajoute un nouveau listener aux objets du type TypePublication.
	 * Il sera averti lors de la création/modification/suppression d'un
	 * objet du type TypePublication.
	 */
	public static void addListener(WcmListener listener) {
		TypePublicationImpl.addListener(listener);
	}

	/**
	 * Retire un listener
	 * @see #addListener(WCMListener)
	 */
	public static void removeListener(WcmListener listener) {
		TypePublicationImpl.removeListener(listener);
	}
	

}
