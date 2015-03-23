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

package org.nextime.ion.framework.helper;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.QueryResults;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.impl.PublicationDocument;
import org.nextime.ion.framework.business.impl.PublicationImpl;
import org.nextime.ion.framework.config.Config;
import org.nextime.ion.framework.event.WcmEvent;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.mapping.Mapping;

/**
 * Indexe les publications
 * 
 * @author gbort
 * @version 1.0
 */
public class Indexer implements WcmListener {

	static {
		if (Config.getInstance().getAutoIndex()) {
			PublicationImpl.addListener(new Indexer());
		}
	}

	/**
	 * re indexe toutes les publications
	 */
	public static synchronized void reIndex() {
		flush();
		try {
			OQLQuery oql =
				Mapping.getInstance().getDb().getOQLQuery(
					"SELECT p FROM org.nextime.ion.framework.business.impl.PublicationImpl p");
			QueryResults results = oql.execute();
			while (results.hasMore()) {
				Publication p = (Publication) results.next();
				for (int k = 1; k <= p.getVersions().size(); k++) {
					String[] s = Config.getInstance().getIndexNames();
					for (int i = 0; i < s.length; i++) {
						try {
							IndexWriter writer = null;
							if (IndexReader
								.indexExists(
									new File(
										Config.getInstance().getIndexRoot(),
										s[i] + "-work$"))) {
								writer =
									new IndexWriter(
										new File(
											Config.getInstance().getIndexRoot(),
											s[i] + "-work$"),
										new StopAnalyzer(),
										false);
							} else {
								writer =
									new IndexWriter(
										new File(
											Config.getInstance().getIndexRoot(),
											s[i] + "-work$"),
										new StopAnalyzer(),
										true);
							}
							writer.addDocument(
								PublicationDocument.getDocument(p, k, s[i]));
							writer.close();
						} catch (IOException e) {
							Logger.getInstance().error(
								"impossible d'indexer la publication "
									+ p.getId()
									+ " dans l'index "
									+ s[i],
								Indexer.class,
								e);
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.getInstance().error(
				"erreur lors de la reindexation des publications",
				Indexer.class,
				e);
		}
		String[] s = Config.getInstance().getIndexNames();
		for (int i = 0; i < s.length; i++) {
			File from =
				new File(Config.getInstance().getIndexRoot(), s[i] + "-work$");
			File to = new File(Config.getInstance().getIndexRoot(), s[i]);

			File[] files = to.listFiles();
			for (int k = 0; k < files.length; k++) {
				files[k].delete();
			}
			to.delete();
			from.renameTo(to);
		}
	}

	/**
	 * détruit une base d'index
	 */
	public static synchronized void flush(String indexName) {
		try {
			IndexWriter writer =
				new IndexWriter(
					new File(Config.getInstance().getIndexRoot(), indexName),
					new StopAnalyzer(),
					true);
			writer.close();
		} catch (IOException e) {
		}
	}

	/**
	 * détruit toues les bases d'index
	 */
	public static synchronized void flush() {
		String[] s = Config.getInstance().getIndexNames();
		for (int i = 0; i < s.length; i++) {
			flush(s[i]);
		}
	}

	/**
	 * Indexe la publication passée en paramétre
	 */
	public static synchronized void index(Publication p) {
		for (int k = 1; k <= p.getVersions().size(); k++) {
			String[] s = Config.getInstance().getIndexNames();
			for (int i = 0; i < s.length; i++) {
				try {
					IndexWriter writer = null;
					writer =
						new IndexWriter(
							new File(Config.getInstance().getIndexRoot(), s[i]),
							new StopAnalyzer(),
							false);
					writer.addDocument(
						PublicationDocument.getDocument(p, k, s[i]));
					writer.close();
				} catch (IOException e) {
					Logger.getInstance().error(
						"impossible de supprimer la publication "
							+ p.getId()
							+ " de l'index "
							+ s[i],
						Indexer.class,
						e);
				}
			}
		}
	}

	/**
	 * Retire la publication passée en paramétre de l'index
	 */
	public static synchronized void remove(Publication p) {
		String[] s = Config.getInstance().getIndexNames();
		for (int i = 0; i < s.length; i++) {
			Term term = new Term("id", p.getId());
			try {
				IndexReader reader =
					IndexReader.open(
						new File(Config.getInstance().getIndexRoot(), s[i]));
				reader.delete(term);
				reader.close();
			} catch (IOException e) {
				Logger.getInstance().error(
					"impossible de supprimer la publication "
						+ p.getId()
						+ " de l'index "
						+ s[i],
					Indexer.class,
					e);
			}
		}
	}

	// implementation de WcmListener

	/**
	 * pour l'indexation automatique
	 */
	public void objectCreated(WcmEvent event) {
		index((Publication) event.getSource());
	}

	/**
	 * pour l'indexation automatique
	 */
	public void objectModified(WcmEvent event) {
		remove((Publication) event.getSource());
		index((Publication) event.getSource());
	}

	/**
	 * pour l'indexation automatique
	 */
	public void objectDeleted(WcmEvent event) {
		remove((Publication) event.getSource());
	}

}
