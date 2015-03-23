package org.nextime.ion.admin.action.publi;


import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.mapping.*;


public class ListPubliAction extends Action {


	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
	
		// pour situer la vue
		request.setAttribute("view", "publi");
		
		try {
			Mapping.begin();
			Vector v = Publication.listAll();				
			Mapping.rollback();
			request.setAttribute("publis",v);
		}
		catch( MappingException e ) {
			Mapping.rollback();
			throw new ServletException(e);
		}
	
		return mapping.findForward("view");	
	}	

}
