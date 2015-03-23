package org.nextime.ion.backoffice.messageBoard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 6 janv. 2003
 */
public class MessageServlet extends HttpServlet {

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		MessageBoard.init(this);
	}

}
