package org.nextime.ion.admin.action.user;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class ListUserAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "user");

		try {
			Mapping.begin();
			Vector v = User.listAll();
			Mapping.commit();
			request.setAttribute("users", v);
		} catch (Exception e) {
			Mapping.rollback();			
			throw new ServletException(e);
		}

		return mapping.findForward("view");
	}

}
