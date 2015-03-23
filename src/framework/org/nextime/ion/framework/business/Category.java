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
import org.nextime.ion.framework.business.impl.CategoryImpl;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;

/** 
 * L'objet <b>Category</b> permet de qualifier le contenu
 * de la publication. Un objet <b>Publication</b> peut appartenir à
 * plusieurs Category et évidemment un objet <b>Category</b>
 * peut regrouper plusieurs Publication.
 * Grace à cet objet vous pouvez proposer au visiteur de consulter
 * d'autres publications de la même categorie.<br>
 * Cet objet peut également servir de lien entre un framework de
 * personnalisation et le framework WCM.
 *
 * @author gbort
 * @version 0.9
 */
public abstract class Category {

	// méthodes de fabrication

	/**
	 * Charge une instance d'un objet Category existant correspondant à l'identifiant
	 * passé en paramétre.<i>(correspond à un SELECT)</i>
	 * @param id L'id de l'objet Category à charger.
	 * @return Une instance de Category.
	 * @throws MappingException si l'objet Category est impossible à charger. 
	 * En particulier si l'id ne correspond à aucun objet Category en base de donnée.
	 */
	public static Category getInstance(String id) throws MappingException {
		try {
			Category u =
				(Category) Mapping.getInstance().getDb().load(
					CategoryImpl.class,
					id);
			Logger.getInstance().log(
				"Une instance de l'objet Category pour l'id "
					+ id
					+ " à été chargée.",
				Category.class);
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de charger une instance de l'objet Category pour l'id "
					+ id
					+ ".";
			Logger.getInstance().error(message, Category.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Créé un nouvel objet Category avec l'id passé en paramétre.<i>(correspond à un INSERT)</i>
	 * @param id l'id de l'objet Category à créer.
	 * @return Une instance de Category.
	 * @throws MappingException si l'objet Category est impossible à créer.
	 * En particulier si un objet Category avec le même id existe déjà en base de donnée.
	 */
	public static Category create(String id) throws MappingException {
		try {
			CategoryImpl u = new CategoryImpl();
			u.setId(id);
			Mapping.getInstance().getDb().create(u);
			Logger.getInstance().log(
				"Un objet Category pour l'id " + id + " à été crée.",
				Category.class);
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de créer l'objet Category pour l'id " + id + ".";
			Logger.getInstance().error(message, Category.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Detruit l'objet Category correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * Cette méthode est équivalente à un <code>getInstance(id).remove()</code>.
	 * @param id L'id du Category à detruire.
	 * @throws MappingException si le Category est impossible à détruire.
	 * En particulier si le getInstance à echoué.
	 */
	public static void remove(String id) throws MappingException {
		try {
			Category u = getInstance(id);
			u.remove();
		} catch (MappingException e) {
			String message =
				"Etes vous certain qu'un objet Category pour l'id "
					+ id
					+ " existe ?";
			Logger.getInstance().error(message, Category.class, e);
		}
	}

	/**
	 * Detruit l'objet Category correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * @param id L'id du Category à detruire.
	 * @throws MappingException si le Category est impossible à détruire.
	 */
	public void remove() throws MappingException {
		try {
			Mapping.getInstance().getDb().remove(this);
			Logger.getInstance().log(
				"L'objet Category pour l'id " + getId() + " à été detruit.",
				Category.class);
		} catch (PersistenceException e) {
			String message =
				"Impossible de detruire l'objet Category pour l'id "
					+ getId()
					+ ".";
			Logger.getInstance().error(message, Category.class, e);
			throw new MappingException(message);
		}
	}

	// méthodes business

	/**
	 * Retourne l'identifiant de cet objet Category
	 * @return id du Category.
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
	 * Detruit une meta donnée de l'objet.<br>
	 * Notez que cette méthode ne fait rien si il
	 * n'y avais pas de meta donnée.
	 * @see #setMetaData(String,Object)
	 * @param key clé sevant à identifier la meta donnée.
	 */
	public abstract void removeMetaData(String key);

	/**
	 * Retourne la Hashtable de meta donnée. 
	 * A utiliser avec précaution en écriture. 
	 * Préférez largement setMetaData()
	 * @return l'objet Hashtable contenant les meta données;
	 */
	public abstract Hashtable getMetaData();

	/**
	 * Ajoute une publication à la catégorie.
	 * @param la publication à ajouter.
	 */
	public abstract void addPublication(Publication p);

	/**
	 * Retire une publication de la catégorie.
	 * @param la publication à retirer.
	 */
	public abstract void removePublication(Publication p);

	/**
	 * Verifie si une publication appartient a cette catégorie.
	 * @param publication de laquelle on veut vérifier l'appartenance
	 * @return true/false
	 */
	public abstract boolean isInThisCategory(Publication p);

	/**
	 * Liste les publications appartenant à la catégorie.
	 * @return un Vector d'objets Publication
	 */
	public abstract Vector listPublications();

	/**
	 * Retourne un tableau des ids des publications appartenant
	 * à la catégorie. Plus pratique que listPublications pour créer les formulaires.
	 * @return un tableau de String
	 */
	public abstract String[] getPublicationsIds();

	/**
	 * Retire tous les utilisateurs de ce groupe.
	 */
	public abstract void resetPublications();

	/**
	 * Liste toutes les objet Category.
	 * @return un Vector d'objets Category
	 * @throws MappingException en cas d'erreur lors du SELECT
	 */
	public static Vector listAll() throws MappingException {
		Vector v = new Vector();
		try {
			OQLQuery oql =
				Mapping.getInstance().getDb().getOQLQuery(
					"SELECT p FROM org.nextime.ion.framework.business.impl.CategoryImpl p");
			QueryResults results = oql.execute();
			while (results.hasMore()) {
				v.add(results.next());
			}
		} catch (Exception e) {
			Logger.getInstance().error(
				"erreur lors du listAll de Category",
				Section.class,
				e);
			throw new MappingException(e.getMessage());
		}
		return v;
	}

	// gestion des évenements

	/**
	 * Ajoute un nouveau listener aux objets du type Category.
	 * Il sera averti lors de la création/modification/suppression d'un
	 * objet du type Category.
	 */
	public static void addListener(WcmListener listener) {
		CategoryImpl.addListener(listener);
	}

	/**
	 * Retire un listener
	 * @see #addListener(WCMListener)
	 */
	public static void removeListener(WcmListener listener) {
		CategoryImpl.removeListener(listener);
	}

}
