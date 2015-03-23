/*
 * �ON content management system.
 * Copyright (C) 2002  Guillaume Bort(gbort@msn.com). All rights reserved.
 * 
 * Copyright (c) 2000 The Apache Software Foundation. All rights reserved.
 * Copyright 2000-2002 (C) Intalio Inc. All Rights Reserved.
 * 
 * �ON is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * �ON core framework, �ON content server, �ON backoffice, �ON frontoffice
 * and �ON admin application are parts of �ON and are distributed under
 * same terms of licence.
 * 
 * 
 * �ON includes software developed by the Apache Software Foundation (http://www.apache.org/)
 * and software developed by the Exolab Project (http://www.exolab.org).
 * 
 * �ON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package org.nextime.ion.framework.config;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.fop.apps.Driver;
import org.nextime.ion.framework.helper.Indexer;
import org.nextime.ion.framework.helper.Viewer;
import org.nextime.ion.framework.logger.Logger;

/**
 * Apporte les informations de configuration contenues
 * dans le config.properties au reste du framework.
 * Si un champ REQUIRED n'est pas pr�sent dans le fichier
 * de conf une fatal error est d�clench�e.
 *  
 * @author gbort
 * @version 1.0
 */
public class Config {

	private String configDirectoryPath;
	private static Config instance;
	private File _dataBaseConfigurationFile;
	private URL _publicationMappingUrl;
	private String _databaseName;
	private String _dateFormatPattern;
	private File _typePublicationDirectory;
	private File _workflowDirectory;
	private File _htmlCacheDirectory;
	private String _saxDriver;
	private String _indexRoot;
	private String[] _indexNames;
	private boolean _xmlValidationState;
	private boolean _autoIndex;
	private boolean _disableLog;
	private int _foRenderType = -2112;
	private long _lockTimeout = -2112;
	private File _locate;

	private Config() {
		_locate =
			new File(
				this
					.getClass()
					.getClassLoader()
					.getResource("ion-locate.properties")
					.getPath())
				.getParentFile();
		configDirectoryPath =
			new File(				
				(
					(PropertyResourceBundle) ResourceBundle.getBundle(
						"ion-locate")).getString(
					"confDirectory"))
				.toString();
		init();
	}

	public static void init() {
		new Indexer();
		new Viewer();
	}

	/**
	 * Fichier de configuration database.xml pour castor ( Required )
	 */
	public File getDatabaseConfigurationFile() {
		if (_dataBaseConfigurationFile != null)
			return _dataBaseConfigurationFile;
		_dataBaseConfigurationFile =
			new File(configDirectoryPath, "database.xml");
		if (!_dataBaseConfigurationFile.exists()) {
			Logger.getInstance().log(
				"Le fichier WCMConf/database.xml est introuvable dans le classpath courant.",
				this);
			return null;
		}
		return _dataBaseConfigurationFile;
	}

	/**
	 * Format de rendu pour les styles xsl-fo ( PDF, SVG, TXT, PS ) ( Optional )
	 */
	public int getFoRenderType() {
		if (_foRenderType != -2112)
			return _foRenderType;
		String s = "";
		int r = Driver.RENDER_PDF;
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);

			s = res.getString("foRenderType");
			if (s.equalsIgnoreCase("PDF"))
				r = Driver.RENDER_PDF;
			if (s.equalsIgnoreCase("SVG"))
				r = Driver.RENDER_SVG;
			if (s.equalsIgnoreCase("TXT"))
				r = Driver.RENDER_TXT;
			if (s.equalsIgnoreCase("PS"))
				r = Driver.RENDER_PS;
		} catch (Exception e) {
			r = Driver.RENDER_PDF;
		}
		_foRenderType = r;
		return r;
	}

	public long getLockTimeout() {
		if (_lockTimeout != -2112)
			return _foRenderType;
		_lockTimeout = 60000;
		return _lockTimeout;
	}

	/**
	 * Fichier de mapping utilis� par le wrapper xml ( Required )
	 */
	public URL getPublicationMappingUrl() {
		if (_publicationMappingUrl != null)
			return _publicationMappingUrl;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		File path = new File(configDirectoryPath, "publication-wrapper.xml");
		if (!path.exists()) {
			Logger.getInstance().log(
				"Le fichier WCMConf/publication-wrapper.xml est introuvable dans le classpath courant.",
				this);
			return null;
		}
		try {
			_publicationMappingUrl = path.toURL();
		} catch (Exception e) {
			Logger.getInstance().log(
				"Le fichier WCMConf/publication-wrapper.xml est introuvable dans le classpath courant.",
				this);
			return null;
		}
		return _publicationMappingUrl;
	}

	/**
	 * Nom de la base de donn�e ( Required )
	 */
	public String getDatabaseName() {
		if (_databaseName != null)
			return _databaseName;
		String name = "";
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);

			name = res.getString("databaseName");
		} catch (Exception e) {
			Logger.getInstance().fatal(
				"Le fichier WCMConf/config.properties est introuvable dans le classpath courant ou est mal formatt� ( databaseName introuvable ).",
				this,
				new NullPointerException());
			System.exit(0);
		}
		_databaseName = name;
		return name;
	}

	/**
	 * Pattern a utiliser pour formatter les dates des publications
	 * lors de rendus ( Optional ) ... voir SimpleDateFormat pour les patterns.
	 */
	public String getDateFormatPattern() {
		if (_dateFormatPattern != null)
			return _dateFormatPattern;
		String name = "";
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			name = res.getString("dateFormatPattern");
		} catch (Exception e) {
			name = null;
		}
		_dateFormatPattern = name;
		return name;
	}

	/**
	 * Repertoire contenant les d�finitions des types de publication ( Required )
	 */
	public File getTypePublicationDirectory() {
		if (_typePublicationDirectory != null)
			return _typePublicationDirectory;
		String path = "";
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			path = res.getString("typesPath");
		} catch (Exception e) {
			Logger.getInstance().fatal(
				"Le fichier WCMConf/config.properties est introuvable dans le classpath courant ou est mal formatt� ( typesPath introuvable ).",
				this,
				new NullPointerException());
			System.exit(0);
		}
		File f = new File(configDirectoryPath, path);
		if (!f.exists() || !f.isDirectory()) {
			Logger.getInstance().fatal(
				"Le path indiqu� par la cl� typesPath est incorrect.",
				this,
				new NullPointerException());
			System.exit(0);
		}
		_typePublicationDirectory = f;
		return f;
	}
	
	/**
	 * Repertoire contenant les d�finitions des workflows( Required )
	 */
	public File getWorkflowDirectory() {
		if (_workflowDirectory!= null)
			return _workflowDirectory;
		String path = "";
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			path = res.getString("workflowsPath");
		} catch (Exception e) {
			Logger.getInstance().fatal(
				"Le fichier WCMConf/config.properties est introuvable dans le classpath courant ou est mal formatt� ( typesPath introuvable ).",
				this,
				new NullPointerException());
			System.exit(0);
		}
		File f = new File(configDirectoryPath, path);
		if (!f.exists() || !f.isDirectory()) {
			Logger.getInstance().fatal(
				"Le path indiqu� par la cl� workflowsPath est incorrect.",
				this,
				new NullPointerException());
			System.exit(0);
		}
		_workflowDirectory = f;
		return f;
	}

	/**
	 * Repertoire de travail pour le cache html ( Optional )
	 * Si aucun repertoire n'est pr�cis�, le cache est inactif
	 */
	public File getHtmlCacheDirectory() {
		if (_htmlCacheDirectory != null)
			return _htmlCacheDirectory;
		String path = "";
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			path = res.getString("htmlCache");
		} catch (Exception e) {
			return null;
		}
		File f = new File(configDirectoryPath, path);
		if (!f.exists() || !f.isDirectory()) {
			Logger.getInstance().fatal(
				"Le path indiqu� par la cl� htmlCache est incorrect.",
				this,
				new NullPointerException());
			System.exit(0);
		}
		_htmlCacheDirectory = f;
		return f;
	}

	/**
	 * Driver SAX � utiliser pour parser les xmls ( Optional )
	 * Si aucun driver n'est sp�cifi�, un jeu de driver standard
	 * est essay�.
	 */
	public String getSaxDriver() {
		if (_saxDriver != null)
			return _saxDriver;
		String s = "";
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			s = res.getString("saxDriver");
		} catch (Exception e) {
			s = "_AUTO_";
		}
		_saxDriver = s;
		return s;
	}

	/**
	 * Repertoire de travail pour l'indexation ( Required )
	 */
	public String getIndexRoot() {
		if (_indexRoot != null)
			return _indexRoot;
		String s = "";
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			s = res.getString("indexRoot");
		} catch (Exception e) {
			Logger.getInstance().fatal(
				"Le fichier WCMConf/config.properties est introuvable dans le classpath courant ou est mal formatt� ( indexRoot introuvable ).",
				this,
				new NullPointerException());
			System.exit(0);
		}
		_indexRoot = new File(configDirectoryPath, s).toString();
		return s;
	}

	/**
	 * Noms des bases d'index � utiliser pour l'indexation (Required)
	 */
	public String[] getIndexNames() {
		if (_indexNames != null)
			return _indexNames;
		String[] s = null;
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			String sl = res.getString("indexNames");
			StringTokenizer st = new StringTokenizer(sl, ";");
			s = new String[st.countTokens()];
			int i = -1;
			while (st.hasMoreTokens()) {
				s[++i] = st.nextToken();
			}
		} catch (Exception e) {
			Logger.getInstance().fatal(
				"Le fichier WCMConf/config.properties est introuvable dans le classpath courant ou est mal formatt� ( indexNames introuvable(s) ).",
				this,
				new NullPointerException());
			System.exit(0);
		}
		_indexNames = s;
		return s;
	}

	/**
	 * Pr�cise si les fichiers xml doivent �tre valid�s � leur chargement (Optional) Default:true;
	 */
	public boolean getXmlValidationState() {
		boolean r = true;
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			r = res.getString("xmlValidation").equals("true");
		} catch (Exception e) {
		}
		return r;
	}

	/**
	 * Pr�cise si l'auto indexation des publications est activ�e (Optional) Default:false;
	 */
	public boolean getAutoIndex() {
		boolean r = false;
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			r = res.getString("autoIndex").equals("true");
		} catch (Exception e) {
		}
		return r;
	}

	/**
	 * Desactive les logs pour la mise en production (Optional) Default:false;
	 */
	public boolean getDisableLog() {
		boolean r = false;
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			r = res.getString("disableLog").equals("true");
		} catch (Exception e) {
		}
		return r;
	}

	/**
	 * Desactive le cache pour les TypePublication
	 */
	public boolean getDisableTypeCache() {
		boolean r = false;
		try {
			FileInputStream fis =
				new FileInputStream(
					new File(configDirectoryPath, "config.properties"));
			PropertyResourceBundle res = new PropertyResourceBundle(fis);
			r = res.getString("disableTypeCache").equals("true");
		} catch (Exception e) {
		}
		return r;
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
	/**
	 * Returns the configDirectoryPath.
	 * @return String
	 */
	public String getConfigDirectoryPath() {
		return configDirectoryPath;
	}

}
