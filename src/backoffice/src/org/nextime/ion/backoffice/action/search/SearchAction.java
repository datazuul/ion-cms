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
import org.nextime.ion.framework.config.Config;
import org.nextime.ion.framework.helper.Searcher;
import org.nextime.ion.framework.mapping.Mapping;

public class SearchAction extends BaseAction {

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);

		// get the keywords
		String keyWords = request.getParameter("keyWords");
		
		// put the index list
		request.setAttribute( "indexs", Config.getInstance().getIndexNames() );

		if (keyWords != null) {
			if (!keyWords.trim().equals("")) {
				try {
					Mapping.begin();
					Vector result = Searcher.search(keyWords, request.getParameter("index"));
					if( result.size()>0 ) {
						request.setAttribute("result", result);
					}
					Mapping.rollback();
				} catch (Exception e) {
					Mapping.rollback();
					throw new ServletException(e);
				}
			} else {
				ActionErrors errors = new ActionErrors();
				ActionError error = new ActionError("error.search.noKeyWords");
				errors.add("keys",error);
				request.setAttribute(ERROR_KEY,errors);
			}
		}

		// Forward to the next page
		return (mapping.findForward("view"));
	}

}
