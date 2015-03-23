package org.nextime.ion.admin.action;


import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;


public class HelpAction extends Action {


	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
	
		request.setAttribute("view", "help");
		return mapping.findForward("view");	
	}


}
