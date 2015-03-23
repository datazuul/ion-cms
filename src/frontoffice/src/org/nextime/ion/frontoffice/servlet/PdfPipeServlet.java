package org.nextime.ion.frontoffice.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nextime.ion.framework.locale.Locale;
import org.nextime.ion.framework.locale.LocaleList;
import org.nextime.ion.framework.mapping.Mapping;

/**
 * @author gbort
 */
public class PdfPipeServlet extends HttpServlet {

	public void service(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {

		String requestedId =
			(request.getPathInfo() != null)
				? request.getPathInfo().substring(1)
				: null;
		if (requestedId != null) {
			if (requestedId.indexOf(".") != -1) {
				requestedId =
					requestedId.substring(0, requestedId.indexOf("."));
			}
		}

		int version = Integer.parseInt(request.getParameter("version"));
		String view = request.getParameter("view");

		try {
			Mapping.begin();
			org.nextime.ion.framework.business.Publication p =
				org.nextime.ion.framework.business.Publication.getInstance(
					requestedId);
			byte[] buffer =
				org.nextime.ion.framework.helper.Viewer.getView(
					p,
					version,
					view,
					request.getSession().getAttribute("currentLocale") + "");				
			
			PrintWriter out = new PrintWriter(response.getOutputStream());		
			
			response.setContentType("application/pdf");
			response.setContentLength(buffer.length);
			response.getOutputStream().write(buffer);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException(e);
		} finally {
			Mapping.rollback();
		}
	}

}
