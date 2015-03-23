package org.nextime.ion.admin.action.type;


import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.mapping.*;


public class ListTypeAction extends Action {


	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
	
		// pour situer la vue
		request.setAttribute("view", "type");
		
		try {
			Mapping.begin();
			Vector v = TypePublication.listAll();	
			Mapping.rollback();
			request.setAttribute("types",v);
		}
		catch( MappingException e ) {
			Mapping.rollback();
			throw new ServletException(e);
		}
	
		return mapping.findForward("view");
	}	

}
