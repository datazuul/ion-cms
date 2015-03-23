package org.nextime.ion.backoffice.action.content;

import java.io.IOException;
import java.util.Collections;
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
import org.nextime.ion.backoffice.bean.SectionTypes;
import org.nextime.ion.backoffice.bean.TypeBean;
import org.nextime.ion.commons.PublicationSorter;

import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class ViewSectionAction extends BaseAction {
	
	static public final int pageSize = 5;

	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		// check if user is correctly logged
		checkUser(request);		
		
		String highlightId = request.getSession().getAttribute("highlightId")+"";
		request.getSession().removeAttribute("highlightId");
		request.setAttribute("highlightId", highlightId);

		if (request.getSession().getAttribute("pageInfos") == null) {
			request.getSession().setAttribute("pageInfos", new Hashtable());
		}
		if (request.getSession().getAttribute("versionDisplayInfos") == null) {
			request.getSession().setAttribute(
				"versionDisplayInfos",
				new Hashtable());
		}

		if (request.getParameter("expand") != null) {
			Hashtable ht =
				(Hashtable) request.getSession().getAttribute(
					"versionDisplayInfos");
			ht.put(request.getParameter("expand"), "true");
		}
		if (request.getParameter("collapse") != null) {
			Hashtable ht =
				(Hashtable) request.getSession().getAttribute(
					"versionDisplayInfos");
			ht.remove(request.getParameter("collapse"));
		}

		// fill data
		try {
			Mapping.begin();

			Vector sections;
			Vector publications;
			Section section = null;
			
			try {
				section = Section.getInstance(request.getParameter("id"));
			} catch( Exception e ) {
				if(request.getParameter("id").equals("site")) {
					// no content
					response.getOutputStream().println("No content on this server ...");
					response.getOutputStream().flush();
					response.getOutputStream().close();
					return null;
				}
			}

			int startPublications;
			try {
				startPublications =
					Integer.parseInt(request.getParameter("start"));
				(
					(Hashtable)
						(request.getSession().getAttribute("pageInfos"))).put(
					section.getId(),
					new Integer(startPublications));
			} catch (NumberFormatException e) {
				try {
					startPublications =
						(
							(Integer) (((Hashtable) (request
								.getSession()
								.getAttribute("pageInfos")))
							.get(section.getId())))
							.intValue();
				} catch (Exception ex) {
					startPublications = 0;
					(
						(Hashtable)
							(
								request.getSession().getAttribute(
									"pageInfos"))).put(
						section.getId(),
						new Integer(startPublications));

				}
			}

			sections = section.listSubSections();
			publications = PublicationSorter.sortPublications(section);
			
			// ------------------------------

			request.setAttribute("section", section);
			request.setAttribute("sectionName", section.getMetaData("name"));
			try {
				String description =
					SectionTypes
						.getSectionBean(
							servlet,
							section.getMetaData("template") + "")
						.getDescription();
				request.setAttribute("sectionDescription", description);
			} catch (Exception e) {
				//e.printStackTrace();
			}

			// list subsections
			request.setAttribute("sections", sections);
			request.setAttribute("sectionsSize", new Integer(sections.size()));

			Vector publicationPage = new Vector();

			int stopPublications = -1;

			for (int i = startPublications;
				i < startPublications + pageSize;
				i++) {
				if (i < publications.size()) {
					publicationPage.add(publications.get(i));
					stopPublications = i;
				}
			}

			// list publications
			request.setAttribute("pageSize", new Integer(pageSize));
			request.setAttribute("start", new Integer(startPublications));
			request.setAttribute("stop", new Integer(stopPublications));
			request.setAttribute("publications", publicationPage);
			request.setAttribute(
				"publicationsSize",
				new Integer(publications.size()));

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			Mapping.rollback();
		}

		// Forward to the next page
		return (mapping.findForward("view"));

	}

}
