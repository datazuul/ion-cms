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

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.batik.dom.util.HashTable;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.QueryResults;
import org.nextime.ion.framework.business.impl.PublicationImpl;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;
import org.nextime.ion.framework.workflow.Workflow;

/** 
 * L'objet <b>Publication</b> est sans doute le plus important
 * du framework WCM. Il contient une zone de donnée pouvant contenir
 * les champs définis dans le modéle de son Type.<br>
 * Le champ state sert à spécifier l'état de la publication : en redaction
 * en attente de validation, publiée ... modifiez ce champ directement
 * ou faites appel à un framework de workflow si le processus de validation
 * est complexe.<br>
 * Les categories servent à qualifier la publication et les sections à
 * l'organiser.
 *
 * @author gbort
 * @version 0.9
 */
public abstract class Publication {

	// méthodes de fabrication

	/**
	 * Charge une instance d'un objet Publication existant correspondant à l'identifiant
	 * passé en paramétre.<i>(correspond à un SELECT)</i>
	 * @param id L'id de l'objet Publication à charger.
	 * @return Une instance de Publication.
	 * @throws MappingException si l'objet Publication est impossible à charger. 
	 * En particulier si l'id ne correspond à aucun objet Publication en base de donnée.
	 */
	public static Publication getInstance(String id) throws MappingException {
		try {
			Publication u =
				(Publication) Mapping.getInstance().getDb().load(
					PublicationImpl.class,
					id);
			Logger.getInstance().log(
				"Une instance de l'objet Publication pour l'id "
					+ id
					+ " à été chargée.",
				Publication.class);
			((PublicationImpl) u).postLoad();
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de charger une instance de l'objet Publication pour l'id "
					+ id
					+ ".";
			Logger.getInstance().error(message, Publication.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Créé un nouvel objet Publication avec l'id passé en paramétre.<i>(correspond à un INSERT)</i>
	 * @param id l'id de l'objet Publication à créer.
	 * @return Une instance de Publication.
	 * @throws MappingException si l'objet Publication est impossible à créer.
	 * En particulier si un objet Publication avec le même id existe déjà en base de donnée.
	 */
	public static Publication create(User user, String id, TypePublication type, String workflowType)
		throws MappingException {
		try {
			PublicationImpl u = new PublicationImpl();
			((PublicationImpl) u).postLoad();
			u.setId(id);
			u.setType(type);
			u.setDate(new Date());	
			u.setWorkflowType(workflowType);			
			u.newVersion(user);	
			Mapping.getInstance().getDb().create(u);
			u.postLoad();
			Logger.getInstance().log(
				"Un objet Publication pour l'id " + id + " à été crée.",
				Publication.class);			
			return u;
		} catch (Exception e) {
			String message =
				"Impossible de créer l'objet Publication pour l'id " + id + "."+e.getMessage();
			Logger.getInstance().error(message, Publication.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Detruit l'objet Publication correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * Cette méthode est équivalente à un <code>getInstance(id).remove()</code>.
	 * @param id L'id du Publication à detruire.
	 * @throws MappingException si la Publication est impossible à détruire.
	 * En particulier si le getInstance à echoué.
	 */
	public static void remove(String id) throws MappingException {
		try {
			Publication u = getInstance(id);
			u.remove();
		} catch (MappingException e) {
			String message =
				"Etes vous certain qu'un objet Publication pour l'id "
					+ id
					+ " existe ?";
			Logger.getInstance().error(message, Publication.class, e);
		}
	}

	/**
	 * Detruit l'objet Publication correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * @param id L'id du Publication à detruire.
	 * @throws MappingException si la Publication est impossible à détruire.
	 */
	public void remove() throws MappingException {
		try {
			Mapping.getInstance().getDb().remove(this);
			Logger.getInstance().log(
				"L'objet Publication pour l'id " + getId() + " à été detruit.",
				Publication.class);
		} catch (PersistenceException e) {
			String message =
				"Impossible de detruire l'objet Publication pour l'id "
					+ getId()
					+ ".";
			Logger.getInstance().error(message, Publication.class, e);
			throw new MappingException(message);
		}
	}

	// méthodes business

	/**
	 * Retourne l'identifiant de cet objet Publication
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
	 * Définit le type de la publication.
	 * Attention, si il y avait du contenu, celui ci
	 * est détruit.
	 * @param le nouveau type de la Publication
	 */
	public abstract void changeType(TypePublication type);

	/**
	 * Retourne le type de la publication.
	 * @return un objet TypePublication
	 */
	public abstract TypePublication getType();

	/**
	 * Définit la date de cette publication.
	 * Lors du list, les publications sont servies dans l'ordre de
	 * leur date.
	 */
	public abstract void setDate(Date date);

	/** 
	 * Retourne la date
	 */
	public abstract Date getDate();

	/** 
	 * Retourne la date formattée à l'aide du pattern définit 
	 * dans le fichier de config
	 */
	public abstract String getFormatedDate();

	/**
	 * Liste les sections auquelles appartient la publication.
	 * @return Un vector d'objet Section
	 */
	public abstract Vector listSections();

	/**
	 * Ajoute une section à cette publication.
	 * @param La section a laquelle on souhaite que la publication appartienne.
	 */
	public abstract void addSection(Section s);

	/**
	 * Retire la publication d'une section.
	 * @param La section a laquelle on souhaite que la publication n'appartienne plus.
	 */
	public abstract void removeSection(Section s);

	/**
	 * Verifie si la publication appartient a cette section.
	 * @return true/false
	 */
	public abstract boolean isInSection(Section s);

	/**
	 * Renvoi un tableau de String contenant les id des sections.
	 * Plus pratique que listSections pour créer les formulaires.
	 * @return un tableau de String
	 */
	public abstract String[] getSectionsIds();

	/**
	 * Supprime toutes les Sections pour cette publication.
	 */
	public abstract void resetSections();

	/**
	 * Liste les catégories auquelles appartient la publication.
	 * @return Un vector d'objet Category
	 */
	public abstract Vector listCategories();

	/**
	 * Ajoute une catégorie à cette publication.
	 * @param La catégorie a laquelle on souhaite que la publication appartienne.
	 */
	public abstract void addCategory(Category c);

	/**
	 * Retire la publication d'une catégorie.
	 * @param La catégorie a laquelle on souhaite que la publication n'appartienne plus.
	 */
	public abstract void removeCategory(Category c);

	/**
	 * Verifie si la publication appartient a cette catégorie.
	 * @return true/false
	 */
	public abstract boolean isInCategory(Category c);

	/**
	 * renvoi un tableau de String contenant les id des categories
	 * Plus pratique que listCategories pour créer les formulaires.
	 * @return un tableau de String
	 */
	public abstract String[] getCategoriesIds();

	/**
	 * Supprime toutes les Categories pour cette publication.
	 */
	public abstract void resetCategories();	

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
					"SELECT p FROM org.nextime.ion.framework.business.impl.PublicationImpl p");
			QueryResults results = oql.execute();
			while (results.hasMore()) {
				v.add(results.next());
			}
		} catch (Exception e) {
			Logger.getInstance().error(
				"erreur lors du listAll de Publication",
				Publication.class,
				e);
			throw new MappingException(e.getMessage());
		}
		return v;
	}
	
	public abstract PublicationVersion getVersion(int ver);
	
	public abstract Vector getVersions();
	
	public abstract PublicationVersion getLastVersion();
	
	public abstract void newVersion(User user) throws Exception;

	// gestion des évenements

	/**
	 * Ajoute un nouveau listener aux objets du type Publication.
	 * Il sera averti lors de la création/modification/suppression d'un
	 * objet du type Publication.
	 */
	public static void addListener(WcmListener listener) {
		PublicationImpl.addListener(listener);
	}

	/**
	 * Retire un listener
	 * @see #addListener(WCMListener)
	 */
	public static void removeListener(WcmListener listener) {
		PublicationImpl.removeListener(listener);
	}

}
