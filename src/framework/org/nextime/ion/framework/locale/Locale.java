package org.nextime.ion.framework.locale;

/**
 * @author gbort
 */
public class Locale {

	private String name;
	private String locale;

	/**
	 * Returns the locale.
	 * @return String
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the locale.
	 * @param locale The locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + "(" + locale + ")";
	}

}
