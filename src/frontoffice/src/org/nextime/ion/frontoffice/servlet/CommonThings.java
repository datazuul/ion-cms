package org.nextime.ion.frontoffice.servlet;

import java.util.Iterator;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nextime.ion.framework.locale.Locale;
import org.nextime.ion.framework.locale.LocaleList;

/**
 * @author gbort
 */
public class CommonThings {

	public static void common(
		HttpServletRequest request,
		HttpServletResponse response) {			
			
		// locale setting
		String currentLocale = (String)request.getSession().getAttribute("currentLocale");
		if( currentLocale == null ) {
			currentLocale = ((Locale)LocaleList.getInstance().getLocales().iterator().next()).getLocale();
			// check if fallbacklocale is supported
		Iterator it = LocaleList.getInstance().getLocales().iterator();
		while (it.hasNext()) {
			if (((Locale) it.next()).getLocale().equals(request.getLocale().getLanguage())) {
				currentLocale = request.getLocale().getLanguage();
			}
		}
			
			request.getSession().setAttribute("currentLocale",currentLocale);
		}
		//request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale", currentLocale);
				
		// set the localized property of section name
		request.setAttribute("sectionNameProperty", "name_"+currentLocale);
	}

}
