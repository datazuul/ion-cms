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
import org.nextime.ion.backoffice.form.CreateUserForm;
import org.nextime.ion.backoffice.form.EditUserForm;
import org.nextime.ion.backoffice.exception.BackofficeSecurityException;
import org.nextime.ion.backoffice.security.SecurityManagerImpl;

import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class CreateUserAction extends BaseAction {

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
			if (!new SecurityManagerImpl().canAdminSecurity(User.getInstance(request.getSession().getAttribute("userLogin")+"")) ) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new BackofficeSecurityException();
		} finally {
			Mapping.rollback();
		}

		// get the form
		CreateUserForm sform = (CreateUserForm) form;
		ActionErrors errors = sform.myValidate(request);

		// user need cancel
		if (request.getParameter("cancel") != null) {
			// Forward to the next page
			return (mapping.findForward("cancel"));
		}

		// fill data | first time
		if (sform.getName() == null) {
			try {
				Mapping.begin();
				Vector groups = Group.listAll();
				Mapping.rollback();

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
			User user = User.create(sform.getLogin());
			user.setMetaData("name", sform.getName());
			user.setMetaData("email", sform.getEmail());
			user.setPassword(sform.getPassword());
			user.resetGroups();
			if (sform.getGroups() != null) {
				for (int i = 0; i < sform.getGroups().length; i++) {
					Group group = Group.getInstance(sform.getGroups()[i]);
					user.addGroup(group);
				}
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
