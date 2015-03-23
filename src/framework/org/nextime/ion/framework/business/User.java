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
import org.exolab.castor.jdo.QueryResults;
import org.nextime.ion.framework.business.impl.UserImpl;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;

/** 
 * L'objet <b>User</b> sert à la gestion de la sécurité.
 * Un objet User peut également être définit comme auteur d'une
 * publication.
 *
 * @author gbort
 * @version 1.0
 */
public abstract class User {

	// méthodes de fabrication

	/**
	 * Charge une instance d'un objet User existant correspondant à l'identifiant
	 * passé en paramétre.<i>(correspond à un SELECT)</i>
	 * @param id L'id de l'objet User à charger.
	 * @return Une instance de User.
	 * @throws MappingException si l'objet User est impossible à charger. 
	 * En particulier si l'id ne correspond à aucun objet User en base de donnée.
	 */
	public static User getInstance(String login) throws MappingException {
		try {
			User u =
				(User) Mapping.getInstance().getDb().load(
					UserImpl.class,
					login);
			Logger.getInstance().log(
				"Une instance de l'objet User pour l'id "
					+ login
					+ " à été chargée.",
				User.class);
			return u;
		} catch (Exception e) {
			String message =
				"Impossible de charger une instance de l'objet User pour l'id "
					+ login
					+ ".";
			Logger.getInstance().error(message, User.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Créé un nouvel objet User avec l'id passé en paramétre.<i>(correspond à un INSERT)</i>
	 * @param id l'id de l'objet User à créer.
	 * @return Une instance de User.
	 * @throws MappingException si l'objet User est impossible à créer.
	 * En particulier si un objet User avec le même id existe déjà en base de donnée.
	 */
	public static User create(String login) throws MappingException {
		try {
			UserImpl u = new UserImpl();
			u.setLogin(login);
			Mapping.getInstance().getDb().create(u);
			Logger.getInstance().log(
				"Un objet User pour l'id " + login + " à été crée.",
				User.class);
			return u;
		} catch (Exception e) {
			String message =
				"Impossible de créer l'objet User pour l'id " + login + ".";
			Logger.getInstance().error(message, User.class, e);
			throw new MappingException(message);
		}
	}

	/**
	 * Detruit l'objet User correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * Cette méthode est équivalente à un <code>getInstance(id).remove()</code>.
	 * @param id L'id du User à detruire.
	 * @throws MappingException si le User est impossible à détruire.
	 * En particulier si le getInstance à echoué.
	 */
	public static void remove(String login) throws MappingException {
		try {
			User u = getInstance(login);
			u.remove();
		} catch (Exception e) {
			String message =
				"Etes vous certain qu'un objet User pour l'id "
					+ login
					+ " existe ?";
			Logger.getInstance().error(message, User.class, e);
		}
	}

	/**
	 * Detruit l'objet User correspondant à l'id passé en paramétre.<i>(correspond à un DELETE)</i>
	 * @param id L'id du User à detruire.
	 * @throws MappingException si le User est impossible à détruire.
	 */
	public void remove() throws MappingException {
		try {
			Mapping.getInstance().getDb().remove(this);
			Logger.getInstance().log(
				"L'objet User pour l'id " + getLogin() + " à été detruit.",
				User.class);
		} catch (Exception e) {
			String message =
				"Impossible de detruire l'objet User pour l'id "
					+ getLogin()
					+ ".";
			Logger.getInstance().error(message, User.class, e);
			throw new MappingException(message);
		}
	}

	// méthodes business

	/**
	 * Retourne le login de l'utilisateur.
	 * Le login est utilisé comme identifiant pour l'objet.
	 * @return login du User
	 */
	public abstract String getLogin();

	/**
	 * Retourne le password de l'utilisateur.
	 * @return password de User
	 */
	public abstract String getPassword();

	/**
	 * Définit un nouveau password pour le User
	 * @param value Nouveau password de l'utilisateur.
	 */
	public abstract void setPassword(String value);

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
	 * Ajoute un groupe à l'utilisateur
	 * @param le groupe à ajouter
	 */
	public abstract void addGroup(Group group);

	/**
	 * Retire un groupe à l'utilisateur
	 * @param le groupe à retirer
	 */
	public abstract void removeGroup(Group group);

	/**
	 * Verifie l'appartenance à un groupe
	 * @param le groupe auquel on veut vérifier l'appartenance
	 * @return vrai/faux
	 */
	public abstract boolean isInGroup(Group group);

	/**
	 * Liste les groupes de l'utilisateur.
	 * @return un Vector d'objet Group
	 */
	public abstract Vector listGroups();

	/**
	 * Retire tous les groupes de cet utilisateur.
	 */
	public abstract void resetGroups();

	/**
	 * Retourne un tableau des ids des groupes de ce user.
	 * Plus pratique que listGroups pour créer les formulaires.
	 * @return un tableau de String
	 */
	public abstract String[] getGroupsIds();

	/**
	 * Liste les publications de l'utilisateur ( dont il est l'auteur )
	 * @return un Vector d'objet Publication
	 */
	public abstract Vector listPublications();

	/**
	 * renvoi une liste de tous les utilisateurs
	 */
	public static Vector listAll() {
		Vector v = new Vector();
		try {
			OQLQuery oql =
				Mapping.getInstance().getDb().getOQLQuery(
					"SELECT p FROM org.nextime.ion.framework.business.impl.UserImpl p");
			QueryResults results = oql.execute();
			while (results.hasMore()) {
				v.add(results.next());
			}
		} catch (Exception e) {
			Logger.getInstance().error(
				"erreur lors du listAll de User",
				User.class,
				e);
			return v;
		}
		return v;
	}

	// gestion des évenements

	/**
	 * Ajoute un nouveau listener aux objets du type User.
	 * Il sera averti lors de la création/modification/suppression d'un
	 * objet du type User.
	 */
	public static void addListener(WcmListener listener) {
		UserImpl.addListener(listener);
	}

	/**
	 * Retire un listener
	 * @see #addListener(WCMListener)
	 */
	public static void removeListener(WcmListener listener) {
		UserImpl.removeListener(listener);
	}

}
