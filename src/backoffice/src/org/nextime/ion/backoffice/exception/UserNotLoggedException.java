package org.nextime.ion.backoffice.exception;

import javax.servlet.ServletException;

public class UserNotLoggedException extends ServletException {

	/**
	 * Constructor for UserNotLoggedException.
	 */
	public UserNotLoggedException() {
		super();
	}

	/**
	 * Constructor for UserNotLoggedException.
	 * @param arg0
	 */
	public UserNotLoggedException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for UserNotLoggedException.
	 * @param arg0
	 * @param arg1
	 */
	public UserNotLoggedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for UserNotLoggedException.
	 * @param arg0
	 */
	public UserNotLoggedException(Throwable arg0) {
		super(arg0);
	}

}

