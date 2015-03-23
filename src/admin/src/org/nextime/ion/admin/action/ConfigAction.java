package org.nextime.ion.admin.action;

import org.nextime.ion.framework.config.*;
import org.apache.struts.action.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;


public class ConfigAction extends Action {


	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
	
		// trouve le path du fichier de config database utilisé
		File f = Config.getInstance().getDatabaseConfigurationFile();
		request.setAttribute("pathConfig",f.getAbsolutePath());
		
		// lit le contenu du fichier
		String content = "";
		String line = "";
		BufferedReader is = new BufferedReader( new InputStreamReader( new FileInputStream(f) ) );
		while( line != null ) {
			content+=line+"\r\n";
			line = is.readLine();
		}
		is.close();
		request.setAttribute("content",content);
	
		request.setAttribute("view", "config");
		return mapping.findForward("view");	
	}


}
