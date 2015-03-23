package org.nextime.ion.admin.action.type;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.config.*;
import org.nextime.ion.framework.mapping.Mapping;

public class DelTypeAction extends Action {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// pour situer la vue
		request.setAttribute("view", "type");

		String id = request.getParameter("id");

		try {
			Mapping.begin();
			TypePublication.getInstance(id).remove();
			// creation du modele.xml de base
			File file = new File( new File( Config.getInstance().getTypePublicationDirectory(), "modeles" ), id+".xml");
			file.delete();
						
			// creation du repertoire pour les styles
			File dir = new File( new File( Config.getInstance().getTypePublicationDirectory(), "styles" ), id);
			if( dir.exists() ) {
				File[] fs = dir.listFiles();
				for( int i=0; i<fs.length; i++ ) {
					fs[i].delete();
				}				
			}
			dir.delete();
			Mapping.commit();
		} catch (Exception e) {
			Mapping.rollback();			
			throw new ServletException(e);
		}

		return mapping.findForward("success");
	}
}
