package org.nextime.ion.backoffice.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.form.LoginForm;

import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class LogoutAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		request.getSession().removeAttribute("userLogin");
		request.getSession().removeAttribute("treeControlNode");
		request.getSession().removeAttribute("pageInfos");
				
		// Forward to the next page
		return (mapping.findForward("ok"));

	}

}
