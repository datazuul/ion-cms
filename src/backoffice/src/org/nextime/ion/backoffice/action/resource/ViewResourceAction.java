package org.nextime.ion.backoffice.action.resource;


import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.bean.ResourceXmlBean;
import org.nextime.ion.backoffice.bean.Resources;
import org.nextime.ion.common.ResourceServlet;



public class ViewResourceAction extends BaseAction {

    public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
        throws IOException, ServletException {
        	
        // check if user is correctly logged
        checkUser(request);
        
        // retrieve resources selected
        String id = request.getSession().getAttribute("selectedResources")+"";
        String path = null;
        try {
        	ResourceXmlBean bean = Resources.getResourceXmlBean(servlet,id);
        	path = bean.getDirectory();
        } catch( Exception e ) {
        	throw new ServletException(e);
        }
        
        String realPath = servlet.getServletContext().getRealPath("/");
        File resources = new File(realPath,ResourceServlet.relativePath);
        File tresources = new File(resources,path);
        
        File[] files = tresources.listFiles();
        Vector mfiles = new Vector();
        for( int i=0; i<files.length; i++ ) {
        	mfiles.add(new org.nextime.ion.backoffice.bean.File(files[i]));
        }
        
        request.setAttribute("files",mfiles);
        
        // Forward to the next page
        return (mapping.findForward("view"));

    }

}
