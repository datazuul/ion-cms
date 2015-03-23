package org.nextime.ion.backoffice.action.resource;

import java.io.IOException;

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

public class ResourceManagementAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		if (request.getParameter("select") != null) {
			request.getSession().setAttribute(
				"selectedResources",
				request.getParameter("select"));

		}

		String selected =
			(String) request.getSession().getAttribute("selectedResources");
		try {
			if (selected == null) {
				selected =
					((ResourceXmlBean) Resources
						.getResourceXmlBeans(servlet)
						.get(0))
						.getId();
			}
			request.getSession().setAttribute("selectedResources", selected);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Forward back to the test page
		return (mapping.findForward("view"));

	}

}
