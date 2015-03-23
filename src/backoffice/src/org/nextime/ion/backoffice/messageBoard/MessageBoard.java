package org.nextime.ion.backoffice.messageBoard;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

import javax.servlet.http.HttpServlet;

public class MessageBoard implements Serializable {

	private Vector messages;
	private static MessageBoard instance;
	private static String storePath;

	public Vector getMessages() {
		Collections.sort(messages);
		return messages;
	}

	public void addMessage(Message m) {
		messages.add(m);
		store();
	}

	public void removeMessage(Message m) {
		messages.remove(m);
		store();
	}

	private MessageBoard() {
		messages = new Vector();
	}

	public static MessageBoard getInstance() {
		if (instance == null) {
			try {
				FileInputStream fis = new FileInputStream(storePath);
				ObjectInputStream ois = new ObjectInputStream(fis);
				instance = (MessageBoard) ois.readObject();
			} catch (Exception e) {
				instance = new MessageBoard();
				e.printStackTrace();
			}
		}
		return instance;
	}

	protected static void init(HttpServlet servlet) {
		storePath =
			servlet.getServletContext().getRealPath(
				"/WEB-INF/messageBoard/store");
	}

	private void store() {
		try {
			FileOutputStream fos = new FileOutputStream(storePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
