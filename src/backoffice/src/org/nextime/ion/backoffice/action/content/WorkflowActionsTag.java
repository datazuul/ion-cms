package org.nextime.ion.backoffice.action.content;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class WorkflowActionsTag extends TagSupport {

	protected String var;
	protected String publication;
	protected String user;
	protected String version;

	public int doStartTag() throws JspException {
		evaluateExpressions();
		try {
			Mapping.begin();
			pageContext.setAttribute(
				var,
				Publication
					.getInstance(publication)
					.getVersion(Integer.parseInt(getVersion()))
					.getWorkflow(User.getInstance(user))
					.getAvailableActions());
		} catch (Exception e) {
			throw new JspException(e);
		} finally {
			Mapping.rollback();
		}
		return (EVAL_BODY_INCLUDE);
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	private void evaluateExpressions() throws JspException {
		if (user != null) {
			user =
				ExpressionEvaluatorManager.evaluate(
					"user",
					user,
					Object.class,
					this,
					pageContext)
					+ "";
		}
		if (publication != null) {
			publication =
				ExpressionEvaluatorManager.evaluate(
					"publication",
					publication,
					Object.class,
					this,
					pageContext)
					+ "";
		}
		if (version != null) {
			version =
				ExpressionEvaluatorManager.evaluate(
					"version",
					version,
					Object.class,
					this,
					pageContext)
					+ "";
		}
	}

	/**
	 * Returns the publication.
	 * @return String
	 */
	public String getPublication() {
		return publication;
	}

	/**
	 * Returns the user.
	 * @return String
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Returns the var.
	 * @return String
	 */
	public String getVar() {
		return var;
	}

	/**
	 * Sets the publication.
	 * @param publication The publication to set
	 */
	public void setPublication(String publication) {
		this.publication = publication;
	}

	/**
	 * Sets the user.
	 * @param user The user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Sets the var.
	 * @param var The var to set
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/**
	 * Returns the version.
	 * @return String
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * @param version The version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
