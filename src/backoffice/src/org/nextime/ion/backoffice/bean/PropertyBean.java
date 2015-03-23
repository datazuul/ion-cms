package org.nextime.ion.backoffice.bean;

public class PropertyBean {

	private String label;
	private String name;
	private String regexp;
	private String type;
	private String errorMessage;
	private String required;	
	
	public void setSectionType( TypeBean type ) {
		type.addProperty(this);
	}
	
	/**
	 * Returns the errorMessage.
	 * @return String
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * Returns the label.
	 * @return String
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}


	/**
	 * Returns the regexp.
	 * @return String
	 */
	public String getRegexp() {
		return regexp;
	}


	/**
	 * Returns the required.
	 * @return String
	 */
	public String getRequired() {
		return required;
	}


	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}


	/**
	 * Sets the errorMessage.
	 * @param errorMessage The errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * Sets the label.
	 * @param label The label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}


	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Sets the regexp.
	 * @param regexp The regexp to set
	 */
	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}


	/**
	 * Sets the required.
	 * @param required The required to set
	 */
	public void setRequired(String required) {
		this.required = required;
	}


	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


}
