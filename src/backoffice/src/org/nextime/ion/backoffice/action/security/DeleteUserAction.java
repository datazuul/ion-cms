package org.nextime.ion.backoffice.action.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.form.EditUserForm;
import org.nextime.ion.backoffice.exception.BackofficeSecurityException;
import org.nextime.ion.backoffice.security.SecurityManagerImpl;

import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.PublicationVersion;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class DeleteUserAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);		

		// check if the user is authorized to perform this action
		try {
			Mapping.begin();
			if (!new SecurityManagerImpl().canAdminSecurity(User.getInstance(request.getSession().getAttribute("userLogin")+"")) ) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new BackofficeSecurityException();
		} finally {
			Mapping.rollback();
		}
		
		// user need cancel
		if (request.getParameter("cancel") != null) {
			// Forward to the next page
			return (mapping.findForward("cancel"));
		}


		// retrieve id
		String id =
			(request.getAttribute("id") != null)
				? request.getAttribute("id").toString()
				: request.getParameter("id").toString();
				
		// fill data | first time
		if (request.getParameter("newUser") == null) {
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

			// Forward to the view page
			return (mapping.findForward("view"));
		} 
		
		// all it's ok : delete user
		try {
			Mapping.begin();
			User user = User.getInstance(id);
			User newUser = User.getInstance(request.getParameter("newUser"));
			
			Vector publis = user.listPublications();
			for( int i=0; i<publis.size(); i++ ) {
				PublicationVersion pv = (PublicationVersion)publis.get(i);
				pv.setAuthor(newUser);
				pv.getWorkflow().getVariables().put("author", newUser.getLogin());
			}			
			
			user.remove();
			Mapping.commit();
			
			if( id.equals( request.getSession().getAttribute("userLogin")) ){
				request.getSession().removeAttribute("userLogin");
			}
			
		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		// Forward to the next page
		return (mapping.findForward("ok"));
	}

}
