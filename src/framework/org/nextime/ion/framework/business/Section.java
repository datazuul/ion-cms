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
import org.nextime.ion.framework.business.impl.SectionImpl;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;

/** 
 * L'objet <b>Section</b> sert à définir le rubriquage
 * du site. Chaque section a un parent. Si le parent vaut
 * null alors la section est racine ( il peut y avoir plusieurs racines ).
 * Les sections sont ordonnées dans chaque noeud.
 *
 * @author gbort
 * @version 0.9
 */
public abstract class Section {

	// méthodes de fabrication

	/**
	 * Charge une instance d'un objet Section existant correspondant à l'identifiant
	 * passé en paramétre.<i>(correspond à un SELECT)</i>
	 * @param id L'id de l'objet Section à charger.
	 * @return Une instance de Section.
	 * @throws MappingException si l'objet Section est impossible à charger. 
	 * En particulier si l'id ne correspond à aucun objet Section en base de donnée.
	 */
	public static Section getInstance(String id) throws MappingException {
		try {
			Section u =
				(Section) Mapping.getInstance().getDb().load(
					SectionImpl.class,
					id);
			Logger.getInstance().log(
				"Une instance de l'objet Section pour l'id "
					+ id
					+ " à été chargée.",
				Section.class);
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de charger une instance de l'objet Section pour l'id "
					+ id
					+ ".";
			Logger.getInstance().error(message, Section.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Créé un nouvel objet Section avec l'id passé en paramétre.<i>(correspond à un INSERT)</i>
	 * @param id l'id de l'objet Section à créer.
	 * @param parent l'objet Section parent ou null pour la racine
	 * @return Une instance de Section.
	 * @throws MappingException si l'objet Section est impossible à créer.
	 * En particulier si un objet Section avec le même id existe déjà en base de donnée.
	 */
	public static Section create(Section parent, String id)
		throws MappingException {
		try {
			SectionImpl u = new SectionImpl();
			u.setId(id);
			u.setParent(parent);
			u.initIndex();
			Mapping.getInstance().getDb().create(u);
			Logger.getInstance().log(
				"Un objet Section pour l'id " + u.getId() + " à été crée.",
				Section.class);
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de créer l'objet Section pour l'id " + id + ".";
			Logger.getInstance().error(message, Section.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Créé une nouvelle Section sous la section courante.
	 * @param id l'id de la nouvelle section
	 * @return Une instance de Section.
	 * @throws MappingException si le Section est impossible à créer.
	 */
	public Section createSubSection(String id) throws MappingException {
		try {
			SectionImpl u = new SectionImpl();
			u.setId(id);
			u.setParent(this);
			u.initIndex();
			Mapping.getInstance().getDb().create(u);
			Logger.getInstance().log(
				"Un objet Section pour l'id " + u.getId() + " à été crée.",
				Section.class);
			return u;
		} catch (PersistenceException e) {
			String message =
				"Impossible de créer l'objet Section pour l'id " + id + ".";
			Logger.getInstance().error(message, Section.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Detruit l'objet Section correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * Cette méthode est équivalente à un <code>getInstance(id).remove()</code>.
	 * @param id L'id du Section à detruire.
	 * @throws MappingException si le Section est impossible à détruire.
	 * En particulier si le getInstance à echoué.
	 */
	public static void remove(String id) throws MappingException {
		try {
			Section u = getInstance(id);
			u.remove();
		} catch (MappingException e) {
			String message =
				"Etes vous certain qu'un objet Section pour l'id "
					+ id
					+ " existe ?";
			Logger.getInstance().error(message, Section.class, e);
		}
	}

	/**
	 * Detruit l'objet Section correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * @param id L'id du Section à detruire.
	 * @throws MappingException si le Section est impossible à détruire.
	 */
	public void remove() throws MappingException {
		try {
			Mapping.getInstance().getDb().remove(this);
			Vector v;
			if (getParent() == null) {
				v = listRootSections();
			} else {
				v = getParent().listSubSections();
			}
			for (int i = 0; i < v.size(); i++) {
				((SectionImpl) v.get(i)).setIndex(0);
			}
			for (int i = 0; i < v.size(); i++) {
				((SectionImpl) v.get(i)).initIndex();
			}
			//			Vector v2 = listSubSections();
			//			for (int i = 0; i < v2.size(); i++) {
			//				((SectionImpl) v2.get(i)).setParent(getParent());
			//			}
			Logger.getInstance().log(
				"L'objet Section pour l'id " + getId() + " à été detruit.",
				Section.class);
		} catch (PersistenceException e) {
			String message =
				"Impossible de detruire l'objet Section pour l'id "
					+ getId()
					+ ".";
			Logger.getInstance().error(message, Section.class, e);
			throw new MappingException(message);
		}
	}

	// méthodes business

	/**
	 * Retourne l'identifiant de cet objet Section
	 * @return id du Section.
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
	 * Retourne la section parente
	 * @return objet Section ( ou null pour la racine )
	 */
	public abstract Section getParent();

	/**
	 * change le parent de cette Section
	 * @param nouvelle Section parent
	 */
	public abstract void changeParent(Section parent);

	/**
	 * Liste les sous sections.
	 * Les sous sections sont listées dans l'ordre de leur index.
	 * @return un Vector contenant des Section
	 */
	public abstract Vector listSubSections();

	/**
	 * Liste les publications de cette section
	 * @return un Vector contenant des Publication
	 */
	public abstract Vector listPublications();

	/**
	 * Ajoute un objet publication a cette Section
	 */
	public abstract void addPublication(Publication p);

	/**
	 * Retire un objet publication de cette Section
	 */
	public abstract void removePublication(Publication p);

	/**
	 * Verifie si une publication appartient a la Section
	 */
	public abstract boolean isInThisSection(Publication p);

	/**
	 * Retourne un tableau des ids des publications appartenant
	 * à la section. Plus pratique que listPublications pour créer les formulaires.
	 * @return un tableau de String
	 */
	public abstract String[] getPublicationsIds();

	/**
	 * Liste les Section racine.
	 * @return un Vector de Section
	 */
	public static Vector listRootSections() throws MappingException {
		Vector v = new Vector();
		try {
			OQLQuery oql =
				Mapping.getInstance().getDb().getOQLQuery(
					"SELECT s FROM org.nextime.ion.framework.business.impl.SectionImpl s WHERE is_undefined( parent ) order by s.index");
			QueryResults results = oql.execute();
			while (results.hasMore()) {
				Section s = (Section) results.next();
				v.add(s);
			}
		} catch (Exception e) {
			Logger.getInstance().error(
				"erreur lors du list des sections root",
				Section.class,
				e);
			throw new MappingException(e.getMessage());
		}
		return v;
	}

	/**
	 * Liste tous les objets Section
	 * @return un Vector de Section
	 */
	public static Vector listAll() throws MappingException {
		Vector v = new Vector();
		Vector v2 = listRootSections();
		for (int i = 0; i < v2.size(); i++) {
			Section s = (Section) v2.get(i);
			v.add(s);
			SectionImpl.listAllInternal(v, s);
		}
		return v;
	}

	/**
	 * Renvoi une String représentant le chemin de la Section
	 * /parent0/parent1/parent2/id
	 */
	public abstract String getPath();

	/**
	 * Réduit l'index de la section.
	 * Elle sera avant dans les listes.
	 */
	public abstract void up();

	/**
	 * Augmente l'index de la section.
	 * Elle sera aprés dans les listes.
	 */
	public abstract void down();

	/**
	 * Retourne l'index de la section
	 */
	public abstract int getIndex();

	/**
	 * Retourne le niveau de la section.
	 * càd le nombre de section qu'il y a avant d'atteindre la racine.
	 */
	public abstract int getLevel();

	// gestion des évenements

	/**
	 * Ajoute un nouveau listener aux objets du type Section.
	 * Il sera averti lors de la création/modification/suppression d'un
	 * objet du type Section.
	 */
	public static void addListener(WcmListener listener) {
		SectionImpl.addListener(listener);
	}

	/**
	 * Retire un listener
	 * @see #addListener(WCMListener)
	 */
	public static void removeListener(WcmListener listener) {
		SectionImpl.removeListener(listener);
	}

}
