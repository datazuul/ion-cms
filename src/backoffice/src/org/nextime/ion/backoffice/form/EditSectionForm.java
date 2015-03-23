package org.nextime.ion.backoffice.form;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.apache.regexp.RE;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.nextime.ion.backoffice.bean.PropertyBean;
import org.nextime.ion.backoffice.bean.SectionTypes;
import org.nextime.ion.backoffice.bean.TypeBean;

public class EditSectionForm extends ActionForm {

	private String name;
	private String template;
	private String status;
	private String workflow;

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping, HttpServletRequest)
	 */
	public ActionErrors myValidate(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (getTemplate() == null || "".equals(getTemplate())) {
			ActionError error =
				new ActionError("error.editSection.templateMissing");
			errors.add("template", error);
		}
		// validate metadata
		try {
			Enumeration names = request.getParameterNames();
			// dangerous ?
			TypeBean type = SectionTypes.getSectionBean(null, getTemplate());
			while (names.hasMoreElements()) {
				String name = names.nextElement() + "";
				if (name.startsWith("META_")) {
					String tname = name.substring(5);
					String value = request.getParameter(name);
					PropertyBean prop = type.getProperty(tname);
					if ("".equals(value.trim())) {
						if ("true".equalsIgnoreCase(prop.getRequired())) {
							ActionError error =
								new ActionError("error.editSection.propertyMissing");
							errors.add("META_" + tname, error);
						}
					} else {
						if (prop.getRegexp() != null) {
							RE re = new RE(prop.getRegexp());
							if (!re.match(value)) {
								ActionError error =
									new ActionError("error.editSection.propertyMalformed", prop.getErrorMessage());
								errors.add("META_" + tname, error);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping, HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		setName(null);
		setTemplate(null);
		setStatus(null);
		setWorkflow(null);
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the template.
	 * @return String
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the template.
	 * @param template The template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * Returns the status.
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * @param status The status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the workflow.
	 * @return String
	 */
	public String getWorkflow() {
		return workflow;
	}

	/**
	 * Sets the workflow.
	 * @param workflow The workflow to set
	 */
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

}
