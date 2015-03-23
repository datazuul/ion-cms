package org.nextime.ion.admin.action.category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.nextime.ion.admin.form.*;
import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.mapping.Mapping;

public class AddCategoryAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "category");

		String id = "";

		try {
						
			// si on a pas soumis le formulaire
			if (request.getParameter("addSubmit") == null)
				return new ActionForward( mapping.getInput() );

			// creation de l'objet
			CategoryForm f = (CategoryForm) form;
			Mapping.begin();
			id = f.getId();
			Category u = Category.create(f.getId());
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
