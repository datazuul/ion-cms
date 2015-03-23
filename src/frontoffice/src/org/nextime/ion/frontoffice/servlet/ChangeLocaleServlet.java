package org.nextime.ion.frontoffice.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nextime.ion.framework.locale.Locale;
import org.nextime.ion.framework.locale.LocaleList;

/**
 * @author gbort
 */
public class ChangeLocaleServlet extends HttpServlet {

	public void service(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {

		String requestedLocale =
			(request.getPathInfo() != null)
				? request.getPathInfo().substring(1)
				: null;
		if (requestedLocale != null) {
			if (requestedLocale.indexOf(".") != -1) {
				requestedLocale =
					requestedLocale.substring(0, requestedLocale.indexOf("."));
			}
		}

		// check if locale is supported
		Iterator it = LocaleList.getInstance().getLocales().iterator();
		while (it.hasNext()) {
			if (((Locale) it.next()).getLocale().equals(requestedLocale)) {
				request.getSession().setAttribute("currentLocale", requestedLocale);
				//request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale", requestedLocale);
				
			}
		}
		
		request.getRequestDispatcher("/changeLocale.jsp").forward(request,response);
			
	}

}
