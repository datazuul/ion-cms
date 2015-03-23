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
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;

public class XmlPubliAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String id = request.getParameter("id");
			int version = Integer.parseInt(request.getParameter("version"));
			Mapping.begin();
			Publication p = Publication.getInstance(id);
			Mapping.rollback();
			request.setAttribute("xml", p.getVersion(version).getXml());
		} catch (MappingException e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		return mapping.findForward("view");
	}

}
