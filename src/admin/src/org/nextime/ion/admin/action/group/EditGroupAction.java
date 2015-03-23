package org.nextime.ion.admin.action.group;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.nextime.ion.admin.form.GroupForm;
import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.business.impl.GroupImpl;
import org.nextime.ion.framework.mapping.Mapping;

public class EditGroupAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "group");

		if( request.getParameter("id") != null ) 
			request.setAttribute("id", request.getParameter("id"));
		String id = request.getAttribute("id")+"";

		try {

			// pour remplir les listes select
			Mapping.begin();
			request.setAttribute("userList", User.listAll());
			Mapping.commit();

			if (request.getParameter("editSubmit") == null) {
				Mapping.begin();

				// initialisation du formulaire				
				Group u = Group.getInstance(id);
				GroupForm f = (GroupForm) form;
				f.setId(u.getId());
				f.setUsers(u.getUsersIds());
				request.setAttribute("metaData", ((GroupImpl) u).getMetaData());

				Mapping.commit();
				return new ActionForward( mapping.getInput() );
			}

			// effectu les modifications 
			Mapping.begin();
			GroupForm f = (GroupForm) form;
			Group u = Group.getInstance(id);
			String[] g = f.getUsers();
			u.resetUsers();
			if (g != null) {
				for (int i = 0; i < g.length; i++) {
					u.addUser(User.getInstance(g[i]));
				}
			}

			// *********************************************************
			// les metaData sont gérées separement car je n'ai
			// aucune idée du nombre et du nom des champs qu'il va
			// y avoir.
			// cette partie est un peu bancale mais en général dans un
			// veritable backoffice le nombre de champs composant le 
			// formulaire est connu et on peut les gérer comme ce que j'ai
			// fait plus haut ( login, password ... )
			// CETTE PARTIE N'EST DONC PAS A REPRENDRE.

			// sauve les metaData 
			Enumeration ps = request.getParameterNames();
			while (ps.hasMoreElements()) {
				String name = ps.nextElement() + "";
				if (name.startsWith("META_")) {
					name = name.substring(5);
					u.setMetaData(name, request.getParameter("META_" + name));
				}
			}
			// efface la metaData si il y a besoin
			String mtd = request.getParameter("metaToDelete");
			if ((mtd + "").trim().equals(""))
				mtd = null;
			if (mtd != null) {
				u.removeMetaData(mtd);
				request.setAttribute("metaData", ((GroupImpl) u).getMetaData());
			}
			// ajoute la metaData si il y a besoin
			String mtd2 = request.getParameter("newMETA");
			if ((mtd2 + "").trim().equals(""))
				mtd2 = null;
			if (mtd2 != null) {
				u.setMetaData(mtd2, "");
				request.setAttribute("metaData", ((GroupImpl) u).getMetaData());
			}
			Mapping.commit();

			// si on a simplement effacé ou ajouté une metadonnée, on retourne au fromulaire
			if (mtd != null || mtd2 != null) {
				return new ActionForward( mapping.getInput() );
			}

			// ***********************************************************

		} catch (Exception e) {
			Mapping.rollback();			
			request.setAttribute("error", e.getMessage());
			return new ActionForward( mapping.getInput() );
		}

		return mapping.findForward("success");
	}

}
