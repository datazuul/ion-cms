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

import java.io.File;
import java.util.Vector;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.helper.Viewer;
import org.nextime.ion.framework.logger.Logger;

/**
 * Document lucene représentant une publication 
 */
public class PublicationDocument {

	public PublicationDocument() {
	}

	public static Document getDocument(Publication p, int version, String indexName) {
		try {
			// créé un nouveau document
			Document doc = new Document();

			// ajoute le champ id
			doc.add(Field.Keyword("id", p.getId()));
			
			// ajoute le champ version
			doc.add(Field.Keyword("version", version+""));
			
			// cherche le style correspondant pour l'indexation
			Style style = ((TypePublicationImpl)p.getType()).getStyleSheet("indexation_"+indexName);

			// recupere le contenu de la publication
			String content = "";	

			if( style != null ) {
				content = new String(Viewer.getView(p,version,"indexation_"+indexName));
			} else {
				Logger.getInstance().log("Pas de style d'indexation "+indexName+" pour le type "+p.getType().getId()+".", PublicationDocument.class);
			}			

			// ajoute le contenu
			doc.add(Field.Text("content", content));

			return doc;
		} catch (Exception e) {
			Logger.getInstance().error(
				"Impossible d'indexer la publication " + p.getId() + ".",
				PublicationDocument.class,
				e);
			return null;
		}
	}
}
