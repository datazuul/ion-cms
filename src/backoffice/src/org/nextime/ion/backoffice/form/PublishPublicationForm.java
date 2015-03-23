package org.nextime.ion.backoffice.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PublishPublicationForm extends ActionForm {

	private String type;
	private String dateBegin;
	private String dateEnd;

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping, HttpServletRequest)
	 */
	public ActionErrors myValidate(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (getType() == null) {
			ActionError error = new ActionError("error.general.missingField");
			errors.add("type", error);
		} else {
			if (getType().equals("P_D")) {
				Date dateBegin = null;
				Date dateEnd = null;
				try {
					dateBegin = formatter.parse(getDateBegin());
				} catch (Exception e) {
					ActionError error =
						new ActionError("error.general.badDate");
					errors.add("date", error);
				}
				if (dateBegin != null) {
					try {
						dateEnd = formatter.parse(getDateEnd());
					} catch (Exception e) {
						ActionError error =
							new ActionError("error.general.badDate");
						errors.add("date", error);
					}
				}
				if (dateBegin != null && dateEnd != null) {
					if (dateEnd.before(dateBegin)) {
						ActionError error =
							new ActionError("error.publishPublication.badEnd");
						errors.add("date", error);
					}
				}
			}
		}
		return errors;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping, HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		setType(null);
		setDateBegin(null);
		setDateEnd(null);
	}

	/**
	 * Returns the dateBegin.
	 * @return String
	 */
	public String getDateBegin() {
		return dateBegin;
	}

	/**
	 * Returns the dateEnd.
	 * @return String
	 */
	public String getDateEnd() {
		return dateEnd;
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the dateBegin.
	 * @param dateBegin The dateBegin to set
	 */
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	/**
	 * Sets the dateEnd.
	 * @param dateEnd The dateEnd to set
	 */
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
