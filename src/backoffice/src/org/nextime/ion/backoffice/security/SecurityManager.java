package org.nextime.ion.backoffice.security;

import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;
import org.nextime.ion.framework.business.User;

public interface SecurityManager {

	public boolean canCreatePublication(Section section, User user);

	public boolean canEditPublication(Publication publication, int version, User user);

	public boolean canDeletePublication(Publication publication, User user);

	public boolean canCreateSection(Section section, User user);

	public boolean canEditSection(Section section, User user);

	public boolean canDeleteSection(Section section, User user);

	public boolean canAdminSecurity(User user);

	public boolean canAdminResources(User user);
	
	public boolean canLogIntoBackoffice(User user);
}
