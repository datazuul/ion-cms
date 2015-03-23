package org.nextime.ion.backoffice.action.content;

import java.io.IOException;
import java.util.Hashtable;
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

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.PublicationVersion;
import org.nextime.ion.framework.business.TypePublication;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.business.impl.Style;
import org.nextime.ion.framework.business.impl.TypePublicationImpl;
import org.nextime.ion.framework.helper.Viewer;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.workflow.WorkflowAction;

public class ActionsAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		String id =
			(request.getAttribute("id") == null)
				? request.getParameter("id")
				: request.getAttribute("id") + "";
		String sAction =
			(request.getAttribute("action") == null)
				? request.getParameter("action")
				: request.getAttribute("action") + "";
		String sVersion =
			(request.getAttribute("version") == null)
				? request.getParameter("version")
				: request.getAttribute("version") + "";

		int action = Integer.parseInt(sAction);
		int version = Integer.parseInt(sVersion);

		// execute the action
		try {
			Mapping.begin();

			// il y a une action externe à effectuer ?
			PublicationVersion publi =
				Publication.getInstance(id).getVersion(version);

			WorkflowAction Waction =
				publi
					.getWorkflow(
						User.getInstance(
							request.getSession().getAttribute("userLogin")
								+ ""))
					.getAction(action);

			String external = Waction.getExternalAction();

			if (external != null && request.getAttribute("inputs") == null) {
				request.setAttribute("externalAction", external);
				return new ActionForward(request.getParameter("externalTemplate"));
			}

			if (request.getAttribute("inputs") == null) {
				Waction.execute();
			} else {
				Waction.execute((Hashtable) request.getAttribute("inputs"));
			}
			Mapping.commit();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			Mapping.rollback();
		}

		// Forward to the next page
		return new ActionForward(request.getParameter("retour"));

	}

}
