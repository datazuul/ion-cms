package org.nextime.ion.admin.action.section;


import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import org.nextime.ion.framework.business.*;
import org.nextime.ion.framework.mapping.*;
import org.nextime.ion.framework.helper.*;


public class SiteMapAction extends Action {


	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {	
			
		try {			
			String map = "";
			Mapping.begin();
			if (request.getQueryString().equals("xml") ) {
				map = SiteMap.getXmlMap();
			} else {
				map = SiteMap.getHtmlMap();
			}
			Mapping.rollback();
			request.setAttribute("map",map);
		}
		catch( MappingException e ) {
			Mapping.rollback();
			throw new ServletException(e);
		}
	
		return mapping.findForward("view");	
	}	

}
