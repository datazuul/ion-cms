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
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.JDOMSource;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.impl.SectionImpl;
import org.nextime.ion.framework.config.Config;
import org.nextime.ion.framework.logger.Logger;
import org.nextime.ion.framework.xml.XML;
import org.nextime.ion.framework.xml.XMLException;

/**
 * Genere la carte du site
 * 
 * @author gbort
 * @version 1.0
 */
public class SiteMap {

	public static org.jdom.Document getXmlMapDoc() {
		try {
			Element root = new Element("site-map");
			Document doc = new Document(root);

			Vector v = Section.listRootSections();

			for (int i = 0; i < v.size(); i++) {
				Section s = (Section) v.get(i);
				computeSection(root, s);
			}

			return doc;
		} catch (Exception e) {
			Logger.getInstance().error(
				"Erreur lors de la création de la SiteMap : " + e.getMessage(),
				SiteMap.class,
				e);
			return null;
		}
	}

	private static void computeSection(Element el, Section s) {
		Element newEl = new Element("section");
		newEl.setAttribute("id", s.getId());
		//newEl.setAttribute("path", s.getPath());
		newEl.setAttribute("publications", s.listPublications().size() + "");
		Enumeration en = ((SectionImpl) s).getMetaData().keys();
		while (en.hasMoreElements()) {
			String key = en.nextElement() + "";
			Element mel = new Element("meta");
			mel.setAttribute("name", key);
			mel.addContent(((SectionImpl) s).getMetaData().get(key) + "");
			newEl.addContent(mel);
		}
		el.addContent(newEl);
		Vector v = s.listSubSections();
		for (int i = 0; i < v.size(); i++) {
			Section s2 = (Section) v.get(i);
			computeSection(newEl, s2);
		}
	}

	/**
	 * rend la siteMap en xml
	 */
	public static String getXmlMap() {
		return XML.getInstance().getString(getXmlMapDoc());
	}

	/**
	 * rend la siteMap en html ( grace a la xsl siteMap.xsl )
	 */
	public static String getHtmlMap() {
		try {
			Transformer transformer =
				TransformerFactory.newInstance().newTransformer(
					new JDOMSource(
						XML.getInstance().readWithoutValidation(
							new File(
								Config
									.getInstance()
									.getTypePublicationDirectory(),
								"siteMap.xsl"))));
			StringWriter sW = new java.io.StringWriter();
			transformer.transform(
				new JDOMSource(getXmlMapDoc()),
				new javax.xml.transform.stream.StreamResult(sW));
			return sW.toString();
		} catch (TransformerException e) {
			return "<b>Erreur de rendu :</b> " + e.getMessageAndLocation();
		} catch (XMLException e) {
			return "<b>Erreur :</b> " + e.getMessage();
		}

	}

}
