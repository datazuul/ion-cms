package org.nextime.ion.admin.action.publi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class NewVersionAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String id = request.getParameter("id");
			String user = request.getParameter("user");
			Mapping.begin();
			Publication.getInstance(id).newVersion(User.getInstance(user));
			Mapping.commit();
			request.setAttribute("id", id);
		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		return mapping.findForward("success");
	}

}
