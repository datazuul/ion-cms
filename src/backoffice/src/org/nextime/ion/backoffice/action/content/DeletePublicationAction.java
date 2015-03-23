package org.nextime.ion.backoffice.action.content;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.exception.StandardException;
import org.nextime.ion.backoffice.tree.TreeControl;
import org.nextime.ion.backoffice.tree.TreeControlNode;
import org.nextime.ion.backoffice.tree.WcmTreeBuilder;
import org.nextime.ion.commons.PublicationSorter;
import org.nextime.ion.backoffice.exception.BackofficeSecurityException;
import org.nextime.ion.backoffice.security.SecurityManagerImpl;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.helper.IdGenerator;
import org.nextime.ion.framework.mapping.Mapping;

public class DeletePublicationAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// retrieve selected publication
		String selectedId = request.getParameter("id");
		
		// check if this action is allowed
		try {
			Mapping.begin();
			if (!new SecurityManagerImpl().canDeletePublication(Publication.getInstance(selectedId), User.getInstance(request.getSession().getAttribute("userLogin")+""))) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new BackofficeSecurityException();
		} finally {
			Mapping.rollback();
		}

		try {
			Mapping.begin();

			Publication publication = Publication.getInstance(selectedId);
			Section section = Section.getInstance(request.getParameter("sectionId"));
			PublicationSorter.removePublication(publication,section);
			publication.remove();			

			Mapping.commit();
		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		// Forward to the next page
		return (mapping.findForward("ok"));

	}

}
