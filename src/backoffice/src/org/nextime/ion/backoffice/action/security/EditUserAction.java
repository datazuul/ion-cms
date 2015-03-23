package org.nextime.ion.backoffice.action.security;

import java.io.IOException;
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
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class EditUserAction extends BaseAction {

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


		// get the form
		EditUserForm sform = (EditUserForm) form;
		ActionErrors errors = sform.myValidate(request);

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
		if (sform.getName() == null) {
			try {		
				Mapping.begin();
				User user = User.getInstance(id);
				Vector groups = Group.listAll();
				Mapping.rollback();
				
				sform.setEmail((String)user.getMetaData("email"));
				sform.setName((String)user.getMetaData("name"));
				sform.setGroups(user.getGroupsIds());
				sform.setPassword(user.getPassword());
				request.setAttribute("groups", groups);
				
			} catch (Exception e) {
				Mapping.rollback();
				throw new ServletException(e);
			}

			// Forward to the view page
			return (mapping.findForward("view"));
		}		

		// fill data | errors
		if (errors.size() > 0) {
			try {
				Mapping.begin();				
				Vector groups = Group.listAll();
				Mapping.rollback();
				
				request.setAttribute("groups", groups);
				request.setAttribute(ERROR_KEY, errors);
								
			} catch (Exception e) {
				Mapping.rollback();
				throw new ServletException(e);
			}

			// Forward to the view page
			return (mapping.findForward("view"));
		}

		// all it's ok : update user
		try {
			Mapping.begin();
			User user = User.getInstance(id);
			user.setMetaData("name", sform.getName());
			user.setMetaData("email", sform.getEmail());
			user.setPassword(sform.getPassword());
			user.resetGroups();
			for( int i=0; i<sform.getGroups().length; i++ ) {
				Group group = Group.getInstance(sform.getGroups()[i]);
				user.addGroup(group);
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
