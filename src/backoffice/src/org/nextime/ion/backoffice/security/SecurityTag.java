package org.nextime.ion.backoffice.security;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

public class SecurityTag extends TagSupport {

	protected String _action;
	protected String _publication;
	protected String _version;
	protected String _section;
	protected String _user;

	public int doStartTag() throws JspException {
		evaluateExpressions();
		if (check())
			return (EVAL_BODY_INCLUDE);
		else
			return (SKIP_BODY);
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	protected boolean check() {
		try {
			Mapping.begin();
			User user = null;
			try {
				if (getUser() != null) {
					user = User.getInstance(getUser());
				}
			} catch (Exception e) {
			}
			Publication publication = null;
			try {
				if (getPublication() != null) {
					publication = Publication.getInstance(getPublication());
				}
			} catch (Exception e) {
			}
			Section section = null;
			try {
				if (getSection() != null) {
					section = Section.getInstance(getSection());
				}
			} catch (Exception e) {
			}
			if ("canAdminResources".equals(getAction())) {
				return SecurityManagerFactory.getInstance().canAdminResources(
					user);
			}
			if ("canAdminSecurity".equals(getAction())) {
				return SecurityManagerFactory.getInstance().canAdminSecurity(
					user);
			}
			if ("canCreatePublication".equals(getAction())) {
				return SecurityManagerFactory
					.getInstance()
					.canCreatePublication(
					section,
					user);
			}
			if ("canCreateSection".equals(getAction())) {
				return SecurityManagerFactory.getInstance().canCreateSection(
					section,
					user);
			}
			if ("canDeletePublication".equals(getAction())) {
				return SecurityManagerFactory
					.getInstance()
					.canDeletePublication(
					publication,
					user);
			}
			if ("canDeleteSection".equals(getAction())) {
				return SecurityManagerFactory.getInstance().canDeleteSection(
					section,
					user);
			}
			if ("canEditPublication".equals(getAction())) {
				return SecurityManagerFactory.getInstance().canEditPublication(
					publication, Integer.parseInt(getVersion()),
					user);
			}
			if ("canEditSection".equals(getAction())) {
				return SecurityManagerFactory.getInstance().canEditSection(
					section,
					user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Mapping.rollback();
		}
		return false;
	}

	/**
	 * Returns the action.
	 * @return String
	 */
	public String getAction() {
		return _action;
	}

	/**
	 * Returns the publication.
	 * @return String
	 */
	public String getPublication() {
		return _publication;
	}

	/**
	 * Returns the section.
	 * @return String
	 */
	public String getSection() {
		return _section;
	}

	/**
	 * Returns the user.
	 * @return String
	 */
	public String getUser() {
		return _user;
	}

	/**
	 * Sets the action.
	 * @param action The action to set
	 */
	public void setAction(String action) {
		this._action = action;
	}

	/**
	 * Sets the publication.
	 * @param publication The publication to set
	 */
	public void setPublication(String publication) {
		this._publication = publication;
	}

	/**
	 * Sets the section.
	 * @param section The section to set
	 */
	public void setSection(String section) {
		this._section = section;
	}

	/**
	 * Sets the user.
	 * @param user The user to set
	 */
	public void setUser(String user) {
		this._user = user;
	}

	private void evaluateExpressions() throws JspException {
		if (_user != null) {
			_user =
				ExpressionEvaluatorManager.evaluate(
					"user",
					_user,
					Object.class,
					this,
					pageContext)
					+ "";
		}
		if (_publication != null) {
			_publication =
				ExpressionEvaluatorManager.evaluate(
					"publication",
					_publication,
					Object.class,
					this,
					pageContext)
					+ "";
		}
		if (_section != null) {
			_section =
				ExpressionEvaluatorManager.evaluate(
					"section",
					_section,
					Object.class,
					this,
					pageContext)
					+ "";
		}
		if (_version != null) {
			_version =
				ExpressionEvaluatorManager.evaluate(
					"version",
					_version,
					Object.class,
					this,
					pageContext)
					+ "";
		}
	}
	/**
	 * Returns the version.
	 * @return String
	 */
	public String getVersion() {
		return _version;
	}

	/**
	 * Sets the version.
	 * @param version The version to set
	 */
	public void setVersion(String version) {
		this._version = version;
	}

}
