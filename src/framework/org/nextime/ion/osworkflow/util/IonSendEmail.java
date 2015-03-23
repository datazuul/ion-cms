package org.nextime.ion.osworkflow.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.nextime.ion.framework.business.Group;
import org.nextime.ion.framework.business.User;
import org.nextime.ion.framework.mapping.Mapping;

import com.opensymphony.workflow.FunctionProvider;

/**
 * @author gbort
 */
public class IonSendEmail implements FunctionProvider {

	public void execute(Map inputs, Map args, Map variables) {

		String fromEmail = (String) args.get("fromEmail");
		String fromName = (String) args.get("fromName");
		String toUsers = (String) args.get("toUsers");
		String toGroups = (String) args.get("toGroups");
		String subject = (String) args.get("subject");
		String cc = (String) args.get("cc");
		String m = (String) args.get("message");
		String smtpHost = (String) args.get("smtpHost");

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);

			Session sendMailSession = Session.getInstance(props, null);
			Transport transport = sendMailSession.getTransport("smtp");
			Message message = new MimeMessage(sendMailSession);

			message.setFrom(new InternetAddress(fromEmail, fromName));

			Set toSet = new HashSet();

			// adding users
			if (toUsers == null)
				toUsers = "";
			StringTokenizer st = new StringTokenizer(toUsers, ", ");

			while (st.hasMoreTokens()) {
				String userLogin = st.nextToken();
				User user = User.getInstance(userLogin);
				toSet.add(
					new InternetAddress(
						user.getMetaData("email") + "",
						user.getMetaData("name") + ""));
			}

			// adding groups
			if (toGroups == null)
				toGroups = "";
			st = new StringTokenizer(toGroups, ", ");

			while (st.hasMoreTokens()) {
				String groupName = st.nextToken();
				Group group = Group.getInstance(groupName);
				Vector users = group.listUsers();
				for (int i = 0; i < users.size(); i++) {
					User user = (User) users.get(i);
					toSet.add(
						new InternetAddress(
							user.getMetaData("email") + "",
							user.getMetaData("name") + ""));
				}
			}

			message.setRecipients(
				Message.RecipientType.TO,
				(InternetAddress[]) toSet.toArray(
					new InternetAddress[toSet.size()]));

			if (cc == null)
				cc = "";
			Set ccSet = new HashSet();
			st = new StringTokenizer(cc, ", ");

			while (st.hasMoreTokens()) {
				String user = st.nextToken();
				ccSet.add(new InternetAddress(user));
			}

			message.setRecipients(
				Message.RecipientType.CC,
				(InternetAddress[]) ccSet.toArray(
					new InternetAddress[ccSet.size()]));
			message.setSubject(subject);
			message.setSentDate(new Date());
			message.setContent(m,"text/html");
			message.saveChanges();			

			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
