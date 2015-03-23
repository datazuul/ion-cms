/*
 * $Header: /cvsroot-fuse/ion-cms/ion0.8/src/backoffice/src/org/nextime/ion/backoffice/action/content/SetUpTreeAction.java,v 1.1 2003/04/23 18:56:01 gbort Exp $
 * $Revision: 1.1 $
 * $Date: 2003/04/23 18:56:01 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.nextime.ion.backoffice.action.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.nextime.ion.backoffice.action.BaseAction;
import org.nextime.ion.backoffice.tree.TreeBuilder;
import org.nextime.ion.backoffice.tree.TreeControl;
import org.nextime.ion.backoffice.tree.TreeControlNode;

/**
 * Test <code>Action</code> sets up  tree control data structure
 * for tree widget
 *
 * @author Jazmin Jonson
 * @author Manveen Kaur
 * @version $Revision: 1.1 $ $Date: 2003/04/23 18:56:01 $
 */

public class SetUpTreeAction extends BaseAction {

	public static final int INIT_PLUGIN_MAX = 10;
	public static final String TREEBUILDER_KEY = "treebuilders";
	public static final String ROOTNODENAME_KEY = "rootnodename";

	// --------------------------------------------------------- Public Methods

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an <code>ActionForward</code> instance describing where and how
	 * control should be forwarded, or <code>null</code> if the response has
	 * already been completed.
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param actionForm The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet exception occurs
	 */
	public ActionForward perform(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {
			
		// check if user is correctly logged
        checkUser(request);

		HttpSession session = request.getSession();
		
		// clean tree
		if( request.getParameter("clean")!=null ) {
			session.removeAttribute("treeControlTest");
		}

		if (session.getAttribute("treeControlTest") == null) {

			ActionServlet servlet = getServlet();

			// Getting init parms from web.xml

			// Get the string to be displayed as root node while rendering the tree
			String rootnodeName = servlet.getInitParameter("rootNodeName");

			String treeBuildersStr =
				"org.nextime.ion.backoffice.tree.WcmTreeBuilder";

			// Make the root node and tree control

			// The root node gets rendered only if its value 
			// is set as an init-param in web.xml

			TreeControlNode root =
				new TreeControlNode(
					"ROOT-NODE",
					"root.gif",
					rootnodeName,
					"setUpTree.x?select=ROOT-NODE",
					"content",
					true);

			TreeControl control = new TreeControl(root);

			if (treeBuildersStr != null) {
				Class treeBuilderImpl;
				TreeBuilder treeBuilderBase;

				ArrayList treeBuilders = new ArrayList(INIT_PLUGIN_MAX);
				int i = 0;
				StringTokenizer st = new StringTokenizer(treeBuildersStr, ",");
				while (st.hasMoreTokens()) {
					treeBuilders.add(st.nextToken().trim());
				}

				if (treeBuilders.size() == 0)
					treeBuilders.add(treeBuildersStr.trim());

				for (i = 0; i < treeBuilders.size(); i++) {

					try {
						treeBuilderImpl =
							Class.forName((String) treeBuilders.get(i));
						treeBuilderBase =
							(TreeBuilder) treeBuilderImpl.newInstance();
						treeBuilderBase.buildTree(control, servlet, request);
					} catch (Throwable t) {
						t.printStackTrace(System.out);
					}
				}
			}

			session = request.getSession();
			session.setAttribute("treeControlTest", control);

			String name = request.getParameter("select");
			if (name != null) {
				control.selectNode(name);				
			}

		}

		return (mapping.findForward("view"));

	}
}
