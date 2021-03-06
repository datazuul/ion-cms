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
import org.nextime.ion.backoffice.tree.TreeControl;
import org.nextime.ion.backoffice.tree.TreeControlNode;
import org.nextime.ion.backoffice.exception.BackofficeSecurityException;
import org.nextime.ion.backoffice.security.SecurityManagerImpl;

import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class MoveSectionAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// check if this action is allowed
		try {
			Mapping.begin();
			if (!new SecurityManagerImpl().canCreateSection(Section.getInstance(request.getParameter("id").toString()), User.getInstance(request.getSession().getAttribute("userLogin")+""))) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new BackofficeSecurityException();
		} finally {
			Mapping.rollback();
		}

		// get section
		try {
			Mapping.begin();
			
			Section section = Section.getInstance(request.getParameter("id"));
			TreeControlNode node = ((TreeControl)(request.getSession().getAttribute("treeControlTest"))).findNode(request.getParameter("id"));
			// up or down ?
			if( request.getParameter("type").equals("up") ) {
				section.up();
				node.up();
			} else {
				section.down();
				node.down();
			}
			
			Mapping.commit();
		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		// Forward to the next page
		return (mapping.findForward("ok"));

	}

}
