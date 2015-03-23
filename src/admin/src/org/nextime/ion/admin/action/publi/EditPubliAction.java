package org.nextime.ion.admin.action.publi;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.admin.form.PubliForm;
import org.nextime.ion.framework.business.Category;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.TypePublication;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.business.impl.PublicationImpl;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.workflow.Workflow;
import org.nextime.ion.framework.workflow.WorkflowStep;

public class EditPubliAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "publi");

		if (request.getParameter("id") != null)
			request.setAttribute("id", request.getParameter("id"));
		String id = request.getAttribute("id") + "";
		int version = 1;
		if( request.getParameter("version") != null ) {
			version = Integer.parseInt(request.getParameter("version"));
		} 
		request.setAttribute("version",version+"");

		try {

			// pour remplir les listes select
			Mapping.begin();
			request.setAttribute("sectionList", Section.listAll());
			request.setAttribute("categoryList", Category.listAll());
			request.setAttribute("userList", User.listAll());
			request.setAttribute("typeList", TypePublication.listAll());
			request.setAttribute("workflowList", Workflow.listTypes());
			request.setAttribute("versions", Publication.getInstance(id).getVersions());
			Mapping.rollback();

			if (request.getParameter("editSubmit") == null || "changeVersion".equals(request.getParameter("editSubmit"))) {
				Mapping.begin();

				// initialisation du formulaire				
				Publication u = Publication.getInstance(id);
				PubliForm f = (PubliForm) form;
				f.setVersion(version+"");
				f.setId(u.getId());				
				if (u.getVersion(version).getAuthor() != null) {
					f.setAuthor(u.getVersion(version).getAuthor().getLogin());
				}				
				f.setType(u.getType().getId());
				f.setSections(u.getSectionsIds());
				f.setCategories(u.getCategoriesIds());
				request.setAttribute(
					"metaData",
					((PublicationImpl) u).getMetaData());
				request.setAttribute("data", ((PublicationImpl) u).getVersion(version).getDataAsString());
				// workflow management
				String workflowUser = ((User)User.listAll().firstElement()).getLogin();
				f.setWorkflowUser(workflowUser);				
				f.setWorkflow(u.getVersion(version).getWorkflow(User.getInstance(workflowUser)).getId()+" ("+u.getVersion(version).getWorkflow(User.getInstance(workflowUser)).getWorkflowType()+")");
				request.setAttribute("actions", u.getVersion(version).getWorkflow(User.getInstance(workflowUser)).getAvailableActions());
				request.setAttribute("permissions", u.getVersion(version).getWorkflow(User.getInstance(workflowUser)).getPermissions());
				request.setAttribute("steps", u.getVersion(version).getWorkflow(User.getInstance(workflowUser)).getCurrentSteps() );
				
				try {
					System.out.println(((WorkflowStep)u.getVersion(version).getWorkflow(User.getInstance(f.getWorkflowUser())).getCurrentSteps().firstElement()).getName());
				} catch( Exception e ) {
					e.printStackTrace();
				}				
				
				Mapping.commit();
				return new ActionForward(mapping.getInput());
			}

			// effectue les modifications 
			Mapping.begin();
			PubliForm f = (PubliForm) form;
			version = Integer.parseInt(f.getVersion());
			Publication u = Publication.getInstance(id);
//			if (!f.getAuthor().equals("_NULL_")) {
//				u.setAuthor(User.getInstance(f.getAuthor()));
//			} else {
//				u.setAuthor(null);
//			}			
			if (!f.getType().equals(u.getType().getId())) {
				u.getVersion(version).resetData();
			}
			u.changeType(TypePublication.getInstance(f.getType()));
			String[] g = f.getSections();
			u.resetSections();
			if (g != null) {
				for (int i = 0; i < g.length; i++) {
					u.addSection(Section.getInstance(g[i]));
				}
			}
			g = f.getCategories();
			if (g != null) {
				for (int i = 0; i < g.length; i++) {
					u.addCategory(Category.getInstance(g[i]));
				}
			}
			// workflow management
			if( !request.getParameter("workflowAction").equals("NULL") ) {
				u.getVersion(version).getWorkflow(User.getInstance(f.getWorkflowUser())).getAction(Integer.parseInt(request.getParameter("workflowAction"))).execute();
			}
			request.setAttribute("actions", u.getVersion(version).getWorkflow(User.getInstance(f.getWorkflowUser())).getAvailableActions());
			request.setAttribute("permissions", u.getVersion(version).getWorkflow(User.getInstance(f.getWorkflowUser())).getPermissions());
			request.setAttribute("steps", u.getVersion(version).getWorkflow(User.getInstance(f.getWorkflowUser())).getCurrentSteps() );

			
			// *********************************************************
			// Gestion des données

			String data = request.getParameter("data");
			u.getVersion(version).setDataAsString(data);

			request.setAttribute("data", ((PublicationImpl) u).getVersion(version).getDataAsString());

			// *********************************************************	

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
				request.setAttribute(
					"metaData",
					((PublicationImpl) u).getMetaData());
			}
			// ajoute la metaData si il y a besoin
			String mtd2 = request.getParameter("newMETA");
			if ((mtd2 + "").trim().equals(""))
				mtd2 = null;
			if (mtd2 != null) {
				u.setMetaData(mtd2, "");
				request.setAttribute(
					"metaData",
					((PublicationImpl) u).getMetaData());
			}
			Mapping.commit();

			// si on a simplement effacé ou ajouté une metadonnée, on retourne au fromulaire
			if (mtd != null || mtd2 != null) {
				return new ActionForward(mapping.getInput());
			}

			// si on a simplement changé le type, on retourne au fromulaire
			if (request.getParameter("editSubmit").equals("temp")) {
				return new ActionForward(mapping.getInput());
			}

			// ***********************************************************

		} catch (Exception e) {
			Mapping.rollback();
			request.setAttribute("error", e.getMessage());
			return new ActionForward(mapping.getInput());
		}

		return mapping.findForward("success");
	}

}
