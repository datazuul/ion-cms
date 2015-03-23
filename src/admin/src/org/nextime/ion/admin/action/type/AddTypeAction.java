package org.nextime.ion.admin.action.type;

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
import org.nextime.ion.framework.config.Config;

import java.io.*;

public class AddTypeAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "type");

		String id = "";

		try {			

			// si on a pas soumis le formulaire
			if (request.getParameter("addSubmit") == null)
				return new ActionForward( mapping.getInput() );

			// creation de l'objet
			TypeForm f = (TypeForm) form;
			Mapping.begin();
			id = f.getId();
			TypePublication u = TypePublication.create(f.getId());
			
			// creation du modele.xml de base
			File file = new File( new File( Config.getInstance().getTypePublicationDirectory(), "models" ), f.getId()+".xml");
			if( !file.exists() ) {
				PrintStream os = new PrintStream( new FileOutputStream(file) );
				os.println( "<!DOCTYPE publication SYSTEM \"modele.dtd\">" );
				os.println( "<publication name=\""+f.getId()+"\"/>");
				os.close();
			}			
			
			// creation du repertoire pour les styles
			File dir = new File( new File( Config.getInstance().getTypePublicationDirectory(), "styles" ), f.getId());
			if( !dir.exists() ) {
				dir.mkdir();
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
