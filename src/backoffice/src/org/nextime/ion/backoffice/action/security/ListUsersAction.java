package org.nextime.ion.backoffice.action.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.bean.SectionTypes;
import org.nextime.ion.backoffice.bean.TypeBean;

import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class ListUsersAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// fill data
		try {
			Mapping.begin();

			Vector users = User.listAll();		
			Collections.sort(users);
			Collections.reverse(users);
			request.setAttribute("users",users);			
			
			Mapping.rollback();

		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		} 

		// Forward to the next page
		return (mapping.findForward("view"));

	}

}
