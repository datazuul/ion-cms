package org.nextime.ion.backoffice.action.content;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.bean.SectionTypes;
import org.nextime.ion.backoffice.bean.TypeBean;
import org.nextime.ion.backoffice.form.EditSectionForm;
import org.nextime.ion.backoffice.tree.TreeControl;
import org.nextime.ion.backoffice.tree.TreeControlNode;
import org.nextime.ion.backoffice.exception.BackofficeSecurityException;
import org.nextime.ion.backoffice.security.SecurityManagerImpl;

import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.locale.Locale;
import org.nextime.ion.framework.locale.LocaleList;
import org.nextime.ion.framework.mapping.Mapping;
import org.nextime.ion.framework.workflow.Workflow;

public class EditSectionAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// set the locales list
		request.setAttribute("locales", LocaleList.getInstance().getLocales());

		// retrieve id
		String id =
			(request.getAttribute("id") != null)
				? request.getAttribute("id").toString()
				: request.getParameter("id").toString();

		// user need cancel
		if (request.getParameter("cancel") != null) {
			// on efface la section si elle n'a pas été initialisée
			try {
				Mapping.begin();
				Section section = Section.getInstance(id);
				if (section.getMetaData("name") == null) {
					section.remove();
				}
				// retrieve selected section
				TreeControl tree =
					(TreeControl) request.getSession().getAttribute(
						"treeControlTest");
				TreeControlNode node = tree.findNode(id);
				node.remove();
				Mapping.commit();
			} catch (Exception e) {
				e.printStackTrace();
				Mapping.rollback();
			}
			// Forward to the next page
			return (mapping.findForward("cancel"));
		}

		// check if this action is allowed
		try {
			Mapping.begin();
			if (!new SecurityManagerImpl()
				.canEditSection(
					Section.getInstance(id),
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
		EditSectionForm sform = (EditSectionForm) form;
		ActionErrors errors = sform.myValidate(request);

		// fill data | first time
		if (sform.getStatus() == null) {
			try {
				Mapping.begin();
				Section section = Section.getInstance(id);
				Mapping.rollback();

				request.setAttribute("section", section);

				// add localized names properties
				Enumeration keys = section.getMetaData().keys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement() + "";
					if (key.startsWith("name_")) {
						request.setAttribute(key, section.getMetaData(key));
					}
				}

				sform.setTemplate(section.getMetaData("template") + "");
				sform.setStatus(section.getMetaData("status") + "");
				sform.setWorkflow(section.getMetaData("workflow") + "");
				request.setAttribute(
					"types",
					SectionTypes.getSectionsBeans(servlet));
				request.setAttribute(
					"type",
					SectionTypes.getSectionBean(
						servlet,
						section.getMetaData("template") + ""));
				request.setAttribute("workflows", Workflow.listTypes());
			} catch (Exception e) {
				Mapping.rollback();
				throw new ServletException(e);
			}

			// Forward to the view page
			return (mapping.findForward("view"));
		}

		// fill data | template has changed
		if (request.getParameter("ok") == null) {
			try {
				Mapping.begin();
				Section section = Section.getInstance(id);
				Mapping.rollback();

				// add localized names properties
				Enumeration keys = section.getMetaData().keys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement() + "";
					if (key.startsWith("name_")) {
						request.setAttribute(key, request.getParameter(key));
					}
				}
				keys = request.getParameterNames();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement() + "";
					if (key.startsWith("name_")) {
						request.setAttribute(key, request.getParameter(key));
					}
				}

				request.setAttribute("section", section);
				request.setAttribute(
					"types",
					SectionTypes.getSectionsBeans(servlet));
				request.setAttribute(
					"type",
					SectionTypes.getSectionBean(servlet, sform.getTemplate()));
				request.setAttribute("workflows", Workflow.listTypes());
				request.setAttribute(ERROR_KEY, errors);
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
				Mapping.rollback();

				// add localized names properties
				Enumeration keys = section.getMetaData().keys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement() + "";
					if (key.startsWith("name_")) {
						request.setAttribute(key, request.getParameter(key));
					}
				}
				keys = request.getParameterNames();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement() + "";
					if (key.startsWith("name_")) {
						request.setAttribute(key, request.getParameter(key));
					}
				}

				request.setAttribute("section", section);
				request.setAttribute(
					"types",
					SectionTypes.getSectionsBeans(servlet));
				request.setAttribute(
					"type",
					SectionTypes.getSectionBean(servlet, sform.getTemplate()));
				request.setAttribute(ERROR_KEY, errors);
				request.setAttribute("workflows", Workflow.listTypes());
				// copy metadata
				Enumeration names = request.getParameterNames();
				request.setAttribute("copyMeta", "true");
				while (names.hasMoreElements()) {
					String name = names.nextElement() + "";
					if (name.startsWith("META_")) {
						request.setAttribute(name, request.getParameter(name));
					}
				}
			} catch (Exception e) {
				Mapping.rollback();
				throw new ServletException(e);
			}

			// Forward to the view page
			return (mapping.findForward("view"));
		}

		// all it's ok : update section
		try {
			Mapping.begin();

			Section section = Section.getInstance(id);

			// set names properties
			Enumeration keys = request.getParameterNames();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement() + "";
				if (key.startsWith("name_")) {
					section.setMetaData(key, request.getParameter(key));
				}
			}
			// default locale
			String sDl =
				((Locale) LocaleList
					.getInstance()
					.getLocales()
					.iterator()
					.next())
					.getLocale();
			section.setMetaData("name", section.getMetaData("name_" + sDl));

			section.setMetaData("template", sform.getTemplate());
			section.setMetaData("status", sform.getStatus());
			section.setMetaData("workflow", sform.getWorkflow());
			// update metadata
			Enumeration names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement() + "";
				if (name.startsWith("META_")) {
					section.setMetaData(
						name.substring(5),
						request.getParameter(name));
				}
			}
			// update the tree ...
			TreeControlNode node =
				(
					(TreeControl) request.getSession().getAttribute(
						"treeControlTest")).findNode(
					request.getParameter("id"));
			node.setLabel(section.getMetaData("name") + "");
			String img = "section.gif";
			if ("offline".equals(section.getMetaData("status"))) {
				img = "section-offline.gif";
			}
			node.setIcon(img);

			Mapping.commit();
		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		// Forward to the next page
		return (mapping.findForward("ok"));
	}

}
