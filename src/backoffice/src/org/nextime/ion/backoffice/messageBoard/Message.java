package org.nextime.ion.backoffice.messageBoard;

import java.io.Serializable;
import java.util.Date;

import org.nextime.ion.framework.business.User;

/**
 * 6 janv. 2003
 */
public class Message implements Serializable, Comparable {

	private String poster;
	private Date date;
	private String message;

	/**
	 * Returns the date.
	 * @return Date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns the message.
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the poster.
	 * @return User
	 */
	public String getPoster() {
		return poster;
	}

	/**
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Sets the message.
	 * @param message The message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Sets the poster.
	 * @param poster The poster to set
	 */
	public void setPoster(String poster) {
		this.poster = poster;
	}

	/**
	 * un message est plus petit qu'un autre si il est plus jeune
	 */
	public int compareTo(Object o) {
		try {
			Message m = (Message) o;
			if (m.getDate().before(getDate())) {
				return -1;
			} else {
				return 1;
			}
		} catch (Exception e) {
			return -1;
		}
	}

}
