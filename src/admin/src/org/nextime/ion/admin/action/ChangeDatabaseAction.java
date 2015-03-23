package org.nextime.ion.admin.action;

import org.nextime.ion.framework.config.*;
import org.nextime.ion.framework.mapping.*;
import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;


public class ChangeDatabaseAction extends Action {


	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
	
		// trouve le path du fichier de config database utilisé
		File f = Config.getInstance().getDatabaseConfigurationFile();
				
		// ecris le contenu du fichier
		PrintStream os = new PrintStream( new FileOutputStream(f) );
		os.println( request.getParameter("content") );
		os.close();
		
		Mapping.getInstance().reset();
	
		request.setAttribute("view", "config");
		return mapping.findForward("success");	
	}


}
