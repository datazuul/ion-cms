package org.nextime.ion.admin.action.section;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.mapping.MappingException;

public class MoveSectionAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		try {
			Mapping.begin();
			Section s = Section.getInstance(request.getParameter("id"));
			if (request.getParameter("type").equals("up")) {
				s.up();
			} else {
				s.down();
			}
			Mapping.commit();
		} catch (MappingException e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		return mapping.findForward("success");
	}
}
