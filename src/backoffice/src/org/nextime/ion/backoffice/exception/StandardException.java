package org.nextime.ion.backoffice.exception;

import javax.servlet.ServletException;

public class StandardException extends ServletException {

	/**
	 * Constructor for StandardException.
	 */
	public StandardException() {
		super();
	}

	/**
	 * Constructor for StandardException.
	 * @param arg0
	 */
	public StandardException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for StandardException.
	 * @param arg0
	 * @param arg1
	 */
	public StandardException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for StandardException.
	 * @param arg0
	 */
	public StandardException(Throwable arg0) {
		super(arg0);
	}

}

