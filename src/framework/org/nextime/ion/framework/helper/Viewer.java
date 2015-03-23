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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Vector;

import org.apache.fop.apps.Driver;
import org.apache.fop.apps.InputHandler;
import org.apache.fop.apps.XSLTInputHandler;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.impl.PublicationImpl;
import org.nextime.ion.framework.business.impl.PublicationVersionImpl;
import org.nextime.ion.framework.business.impl.Style;
import org.nextime.ion.framework.business.impl.TypePublicationImpl;
import org.nextime.ion.framework.config.Config;
import org.nextime.ion.framework.event.WcmEvent;
import org.nextime.ion.framework.event.WcmListener;
import org.nextime.ion.framework.locale.Locale;
import org.nextime.ion.framework.locale.LocaleList;
import org.nextime.ion.framework.xml.XML;
import org.xml.sax.XMLReader;

/**
 * Génére les vues pour les publications
 * 
 * @author gbort
 * @version 1.0
 */
public class Viewer implements WcmListener {

	static {
		PublicationImpl.addListener(new Viewer());
		cleanCache();
	}

	/**
	 * Retourne la vue pour le style spécifié
	 */
	public static byte[] getView(Publication p, int version, String styleId) {
		return getView(p, version, styleId, null);
	}

	/**
	 * Retourne la vue pour le style spécifié
	 */
	public static byte[] getView(
		Publication p,
		int version,
		String styleId,
		String locale) {
		if (Config.getInstance().getHtmlCacheDirectory() != null) {
			File possibleCache =
				new File(
					Config.getInstance().getHtmlCacheDirectory(),
					generateUniqueId(p, version, styleId, locale));
			if (possibleCache.exists()) {
				try {
					byte[] b = new byte[(int) possibleCache.length()];
					new FileInputStream(possibleCache).read(b);
					return b;
				} catch (Exception e) {
				}
			}
		}
		Style style =
			((TypePublicationImpl) p.getType()).getStyleSheet(styleId);
		Document xsl = style.getDocument();
		Document xml = null;
		if (locale == null) {
			xml = ((PublicationVersionImpl) p.getVersion(version)).getXmlDoc();
		} else {
			xml =
				((PublicationVersionImpl) p.getVersion(version)).getXmlDoc(
					locale);
		}
		if (style.getType() == Style.XSL) {
			String html = XML.getInstance().transform(xml, xsl);
			if (Config.getInstance().getHtmlCacheDirectory() != null) {
				File cache =
					new File(
						Config.getInstance().getHtmlCacheDirectory(),
						generateUniqueId(p, version, styleId, locale));
				try {
					new FileOutputStream(cache).write(html.getBytes());
				} catch (Exception e) {
				}
			}
			return html.getBytes();
		} else {
			try {
				File temp1 = File.createTempFile("temp", "FOP_WCM");
				File temp2 = File.createTempFile("temp", "FOP_WCM");
				new XMLOutputter().output(xml, new FileOutputStream(temp1));
				new XMLOutputter().output(xsl, new FileOutputStream(temp2));
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Driver driver = new Driver();
				driver.setRenderer(Config.getInstance().getFoRenderType());
				InputHandler inputHandler = new XSLTInputHandler(temp1, temp2);
				XMLReader parser = inputHandler.getParser();
				driver.setOutputStream(out);
				driver.render(parser, inputHandler.getInputSource());
				if (Config.getInstance().getHtmlCacheDirectory() != null) {
					File cache =
						new File(
							Config.getInstance().getHtmlCacheDirectory(),
							generateUniqueId(p, version, styleId, locale));
					try {
						new FileOutputStream(cache).write(out.toByteArray());
					} catch (Exception e) {
					}
				}
				temp1.delete();
				temp2.delete();
				return out.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new byte[0];
	}

	private static String generateUniqueId(
		Publication p,
		int version,
		String styleId,
		String locale) {
		return p.getId()
			+ "$"
			+ styleId
			+ "$"
			+ "version"
			+ version
			+ ((locale == null) ? ("") : ("_" + locale))
			+ ".html";
	}

	// implementation de WcmListener

	public void objectCreated(WcmEvent event) {
	}

	public void objectModified(WcmEvent event) {
		Publication p = (Publication) event.getSource();
		if (Config.getInstance().getHtmlCacheDirectory() != null) {
			for (int i = 1; i <= p.getVersions().size(); i++) {
				Vector v = p.getType().listStyles();
				for (int t = 0; t < v.size(); t++) {
					File possibleCache =
						new File(
							Config.getInstance().getHtmlCacheDirectory(),
							generateUniqueId(p, i, v.get(t) + "", null));
					possibleCache.delete();
					Iterator it =
						LocaleList.getInstance().getLocales().iterator();
					while (it.hasNext()) {
						possibleCache =
							new File(
								Config.getInstance().getHtmlCacheDirectory(),
								generateUniqueId(
									p,
									i,
									v.get(t) + "",
									((Locale) it.next()).getLocale()));
						possibleCache.delete();
					}
				}
			}
		}
	}

	public void objectDeleted(WcmEvent event) {
		objectModified(event);
	}

	private static void cleanCache() {
		if (Config.getInstance().getHtmlCacheDirectory() != null) {
			File[] files =
				Config.getInstance().getHtmlCacheDirectory().listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}

}
