package org.nextime.ion.backoffice.security;

import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;

import com.opensymphony.workflow.basic.*;

public class SecurityManagerImpl implements SecurityManager {

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canAdminResources(User)
	 */
	public boolean canAdminResources(User user) {
		try {
			Group admins = Group.getInstance("admins");
			return user.isInGroup(admins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canAdminSecurity(User)
	 */
	public boolean canAdminSecurity(User user) {
		try {
			Group admins = Group.getInstance("admins");
			return user.isInGroup(admins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canCreatePublication(Section, User)
	 */
	public boolean canCreatePublication(Section section, User user) {
		try {
			String workflowType = section.getMetaData("workflow") + "";
			BasicWorkflow bw = new BasicWorkflow(user.getLogin());
			return bw.canInitialize(workflowType, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canCreateSection(Section, User)
	 */
	public boolean canCreateSection(Section section, User user) {
		try {
			Group admins = Group.getInstance("admins");
			return user.isInGroup(admins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canDeletePublication(Publication, User)
	 */
	public boolean canDeletePublication(Publication publication, User user) {
		try {
			Group admins = Group.getInstance("webmasters");
			return user.isInGroup(admins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canDeleteSection(Section, User)
	 */
	public boolean canDeleteSection(Section section, User user) {
		try {
			Group admins = Group.getInstance("admins");
			return user.isInGroup(admins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canEditPublication(Publication, User)
	 */
	public boolean canEditPublication(
		Publication publication,
		int version,
		User user) {
		try {
			Group admins = Group.getInstance("admins");
			if (user.isInGroup(admins))
				return true;
			return publication
				.getVersion(version)
				.getWorkflow(user)
				.getPermissions()
				.contains("canEdit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canEditSection(Section, User)
	 */
	public boolean canEditSection(Section section, User user) {
		try {
			Group admins = Group.getInstance("admins");
			return user.isInGroup(admins);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @see org.nextime.ion.backoffice.security.SecurityManager#canLogIntoBackoffice(User)
	 */
	public boolean canLogIntoBackoffice(User user) {
		try {
			Group admins = Group.getInstance("admins");
			Group contributeurs = Group.getInstance("users");
			Group webmasters = Group.getInstance("webmasters");
			return user.isInGroup(admins) || user.isInGroup(contributeurs) || user.isInGroup(webmasters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
