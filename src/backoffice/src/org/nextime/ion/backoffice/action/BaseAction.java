package org.nextime.ion.backoffice.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.exception.UserNotLoggedException;

public class BaseAction extends Action {

	protected void checkUser(HttpServletRequest request)
		throws UserNotLoggedException {
		if (request.getSession().getAttribute("userLogin") == null)
			throw new UserNotLoggedException();
	}

}
