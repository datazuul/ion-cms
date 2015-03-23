package org.nextime.ion.backoffice.action.content;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.form.EditPublicationForm;
import org.nextime.ion.backoffice.exception.BackofficeSecurityException;
import org.nextime.ion.backoffice.security.SecurityManagerImpl;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.config.Config;
import org.nextime.ion.framework.locale.LocaleList;
import org.nextime.ion.framework.mapping.Mapping;

public class EditPublicationAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// get the form
		EditPublicationForm sform = (EditPublicationForm) form;
		ActionErrors errors = sform.myValidate(request);
		
		// set languages list
		request.setAttribute("locales", LocaleList.getInstance().getLocales() );
		request.setAttribute("defaultLocale", LocaleList.getInstance().getLocales().iterator().next() );
		
		// retrieve id
		String id =
			(request.getAttribute("id") != null)
				? request.getAttribute("id").toString()
				: request.getParameter("id").toString();

		// retrieve version
		int version =
			Integer.parseInt(
				(request.getAttribute("version") != null)
					? request.getAttribute("version").toString()
					: request.getParameter("version").toString());
		request.setAttribute("version", version + "");		
		
		// check if publication can be edited
		try {
			Mapping.begin();
			if (!new SecurityManagerImpl()
				.canEditPublication(
					Publication.getInstance(id),
					version,
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

		// user need cancel
		if (request.getParameter("cancel") != null) {
			// Forward to the next page
			return (mapping.findForward("cancel"));
		}

		// fill data | first time
		if (request.getParameter("itsOk") == null) {
			try {
				Mapping.begin();
				Publication publication = Publication.getInstance(id);
				Mapping.rollback();

				request.setAttribute("publication", publication);

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				sform.setDate(formatter.format(publication.getDate()));
				sform.setData(
					publication.getVersion(version).getDataAsString());

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
				Publication publication = Publication.getInstance(id);
				request.setAttribute("publication", publication);
				request.setAttribute(ERROR_KEY, errors);
				Mapping.rollback();
			} catch (Exception e) {
				Mapping.rollback();
				throw new ServletException(e);
			}

			// Forward to the view page
			return (mapping.findForward("view"));
		}

		// all it's ok : update publication 
		try {
			Mapping.begin();
			Publication publication = Publication.getInstance(id);
			publication.getVersion(version).setDataAsString(sform.getData());
			Mapping.commit();
		} catch (Exception e) {
			Mapping.rollback();
			throw new ServletException(e);
		}

		// Forward to the next page
		return (mapping.findForward("ok"));
	}

}
