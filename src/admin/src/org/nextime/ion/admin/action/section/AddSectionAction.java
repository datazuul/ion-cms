package org.nextime.ion.admin.action.section;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.nextime.ion.admin.form.*;
import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class AddSectionAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "section");

		String id = "";

		try {

			// pour remplir les listes select
			Mapping.begin();
			request.setAttribute("sectionList", Section.listAll());
			Mapping.commit();

			// si on a pas soumis le formulaire
			if (request.getParameter("addSubmit") == null)
				return new ActionForward( mapping.getInput() );

			// creation de l'objet
			SectionForm f = (SectionForm) form;
			Mapping.begin();
			id = f.getId();
			Section parent = null;
			if( !f.getParent().equals("_NULL_") ) {
				parent = Section.getInstance(f.getParent());
			}
			Section u = Section.create( parent, f.getId());
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
