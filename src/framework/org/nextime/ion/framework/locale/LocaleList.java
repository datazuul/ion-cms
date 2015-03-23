package org.nextime.ion.framework.locale;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.nextime.ion.framework.config.Config;

/**
 * @author gbort
 */
public class LocaleList {

	private Vector locales = new Vector();
	private static LocaleList instance = null;

	protected LocaleList() {
	}

	public static LocaleList getInstance() {
		try {
			File file =
				new File(
					Config.getInstance().getConfigDirectoryPath(),
					"locales.xml");
			if (instance == null) {
				instance = parse(new FileInputStream(file));
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Collection getLocales() {
		return Collections.unmodifiableCollection(locales);
	}

	public void addLocale(Locale locale) {
		locales.add(locale);
	}

	protected static LocaleList parse(InputStream in) throws Exception {
		LocaleList bean = new LocaleList();
		Digester digester = new Digester();
		digester.push(bean);
		digester.setValidating(false);
		digester.addObjectCreate(
			"locales/locale",
			"org.nextime.ion.framework.locale.Locale");
		digester.addSetProperties("locales/locale");
		digester.addSetNext("locales/locale", "addLocale");
		digester.parse(in);
		return bean;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return locales.toString();
	}

}
