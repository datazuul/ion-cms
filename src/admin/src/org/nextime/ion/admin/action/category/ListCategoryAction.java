package org.nextime.ion.admin.action.category;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.mapping.*;

public class ListCategoryAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "category");

		try {
			Mapping.begin();
			Vector v = Category.listAll();
			Mapping.rollback();
			request.setAttribute("categories", v);
		} catch (MappingException e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		return mapping.findForward("view");
	}
}
