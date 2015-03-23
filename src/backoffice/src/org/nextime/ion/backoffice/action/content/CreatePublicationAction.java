package org.nextime.ion.backoffice.action.content;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.bean.SectionTypes;
import org.nextime.ion.backoffice.bean.TypeBean;
import org.nextime.ion.backoffice.exception.BackofficeSecurityException;
import org.nextime.ion.backoffice.form.CreatePublicationForm;
import org.nextime.ion.backoffice.security.SecurityManagerImpl;
import org.nextime.ion.commons.PublicationSorter;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.TypePublication;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.helper.IdGenerator;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.workflow.Workflow;

public class CreatePublicationAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// check if this action is allowed
		try {
			Mapping.begin();
			if (!new SecurityManagerImpl()
				.canCreatePublication(
					Section.getInstance(request.getParameter("id").toString()),
					User.getInstance(
						request.getSession().getAttribute("userLogin")
							+ ""))) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new BackofficeSecurityException();
		} finally {
			Mapping.rollback();
		}

		// get the form
		CreatePublicationForm sform = (CreatePublicationForm) form;
		ActionErrors errors = sform.myValidate(request);

		// user need cancel
		if (request.getParameter("cancel") != null) {
			// Forward to the next page
			return (mapping.findForward("cancel"));
		}

		// retrieve section id
		String id = request.getParameter("id").toString();

		// fill data | first time
		if (sform.getName() == null) {
			try {
				Mapping.begin();

				Section section = Section.getInstance(id);
				String template = section.getMetaData("template") + "";
				TypeBean type = SectionTypes.getSectionBean(servlet, template);

				Vector types = new Vector();
				for (int t = 0; t < type.getPublicationTypes().size(); t++) {
					try {
						types.add(
							TypePublication.getInstance(
								type.getPublicationTypes().get(t) + ""));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				request.setAttribute("types", types);
				request.setAttribute("workflows", Workflow.listTypes());

				Mapping.rollback();
			} catch (Exception e) {
				Mapping.rollback();
				throw new ServletException(e);
			}

			// Forward to the view page
			return (mapping.findForward("view"));
		}

		// fill data | errors
		if (errors.size() > 0) {
			try {
				Mapping.begin();

				Section section = Section.getInstance(id);
				String template = section.getMetaData("template") + "";
				TypeBean type = SectionTypes.getSectionBean(servlet, template);

				Vector types = new Vector();
				for (int t = 0; t < type.getPublicationTypes().size(); t++) {
					try {
						types.add(
							TypePublication.getInstance(
								type.getPublicationTypes().get(t) + ""));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				request.setAttribute("types", types);

				request.setAttribute(ERROR_KEY, errors);

				Mapping.rollback();
			} catch (Exception e) {
				Mapping.rollback();
				throw new ServletException(e);
			}

			// Forward to the view page
			return (mapping.findForward("view"));
		}

		// all it's ok : create publication
		try {
			Mapping.begin();

			Section section = Section.getInstance(id);
			String newId = IdGenerator.nextPublicationId();
			TypePublication type = TypePublication.getInstance(sform.getType());
			Publication publi =
				Publication.create(
					User.getInstance(
						request.getSession().getAttribute("userLogin") + ""),
					newId,
					type,
					section.getMetaData("workflow") + "");
			section.addPublication(publi);
			publi.setMetaData("name", sform.getName());
			PublicationSorter.initPublication(publi, section);
			request.setAttribute("id", newId);
			request.setAttribute(
				"version",
				publi.getLastVersion().getVersion() + "");

			Mapping.commit();
		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		// Forward to the next page
		return (mapping.findForward("ok"));
	}

}
