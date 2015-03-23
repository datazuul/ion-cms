package org.nextime.ion.admin.action.section;

import java.io.IOException;
import java.util.Enumeration;

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
import org.nextime.ion.framework.business.impl.*;
import org.nextime.ion.framework.mapping.Mapping;

public class EditSectionAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "section");

		if( request.getParameter("id") != null ) 
			request.setAttribute("id", request.getParameter("id"));
		String id = request.getAttribute("id")+"";

		try {

			// pour remplir les listes select
			Mapping.begin();
			request.setAttribute("sectionList", Section.listAll());
			Mapping.commit();

			if (request.getParameter("editSubmit") == null) {
				Mapping.begin();

				// initialisation du formulaire				
				Section u = Section.getInstance(id);
				SectionForm f = (SectionForm) form;
				f.setId(u.getId());
				f.setParent(u.getParent().getId());				
				request.setAttribute("metaData", ((SectionImpl) u).getMetaData());

				Mapping.commit();
				return new ActionForward( mapping.getInput() );
			}

			// effectu les modifications 
			Mapping.begin();
			SectionForm f = (SectionForm) form;
			Section u = Section.getInstance(id);
			Section parent = null;
			if( !f.getParent().equals("_NULL_") ) {
				parent = Section.getInstance(f.getParent());
			}
			if( parent != u.getParent() ) {
				u.changeParent(parent);	
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
				request.setAttribute("metaData", ((SectionImpl) u).getMetaData());
			}
			// ajoute la metaData si il y a besoin
			String mtd2 = request.getParameter("newMETA");
			if ((mtd2 + "").trim().equals(""))
				mtd2 = null;
			if (mtd2 != null) {
				u.setMetaData(mtd2, "");
				request.setAttribute("metaData", ((SectionImpl) u).getMetaData());
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
