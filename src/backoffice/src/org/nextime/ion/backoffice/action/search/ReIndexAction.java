package org.nextime.ion.backoffice.action.search;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.form.PublishPublicationForm;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.helper.Indexer;
import org.nextime.ion.framework.helper.Searcher;
import org.nextime.ion.framework.mapping.Mapping;

public class ReIndexAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// reindex
		try {
			Mapping.begin();
			Indexer.reIndex();
			Mapping.rollback();
		}
		catch( Exception e ) {
			throw new ServletException(e);
		}		

		// Forward to the next page
		return (mapping.findForward("ok"));
	}

}
