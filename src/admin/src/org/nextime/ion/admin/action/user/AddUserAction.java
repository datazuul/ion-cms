package org.nextime.ion.admin.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.nextime.ion.admin.form.UserForm;
import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class AddUserAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "user");

		String id = "";

		try {

			// pour remplir les listes select
			Mapping.begin();
			request.setAttribute("groupList", Group.listAll());
			Mapping.commit();

			// si on a pas soumis le formulaire
			if (request.getParameter("addSubmit") == null)
				return new ActionForward( mapping.getInput() );

			// creation de l'objet
			UserForm f = (UserForm) form;
			Mapping.begin();
			id = f.getLogin();
			User u = User.create(f.getLogin());
			u.setPassword(f.getPassword());
			String[] g = f.getGroups();
			if (g != null) {
				for (int i = 0; i < g.length; i++) {
					u.addGroup(Group.getInstance(g[i]));
				}
			}
			Mapping.commit();			

		} catch (Exception e) {			
			Mapping.rollback();			
			request.setAttribute("error", e.getMessage());
			return new ActionForward( mapping.getInput() );
		}
		
		// on va passer l'id en request pour aller directement à 
		// la page d'edition
		request.setAttribute("id", id);
		
		return mapping.findForward("success");
	}

}
