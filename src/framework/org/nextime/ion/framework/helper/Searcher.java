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
import java.util.Vector;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.config.Config;

public class Searcher {

	public static synchronized Vector search(
		String keyWords,
		String indexName) {
		Vector result = new Vector();
		try {
			IndexReader reader =
				IndexReader.open(
					new File(Config.getInstance().getIndexRoot(), indexName));
			QueryParser parser = new QueryParser("content", new StopAnalyzer());
			Query query = parser.parse(keyWords);
			IndexSearcher searcher = new IndexSearcher(reader);
			Hits hits = searcher.search(query);
			for (int i = 0; i < hits.length(); i++) {
				Document doc = hits.doc(i);
				String id = doc.get("id");
				Publication p = Publication.getInstance(id);
				SearchResult sr = new SearchResult(p, Integer.parseInt(doc.get("version")),hits.score(i));
				result.add(sr);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

}
