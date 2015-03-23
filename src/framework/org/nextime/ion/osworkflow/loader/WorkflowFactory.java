package org.nextime.ion.osworkflow.loader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nextime.ion.framework.config.Config;
import org.w3c.dom.*;

import com.opensymphony.workflow.loader.AbstractWorkflowFactory;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.loader.WorkflowLoader;

import javax.xml.parsers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class WorkflowFactory extends AbstractWorkflowFactory {

	private static final Log log = LogFactory.getLog(WorkflowFactory.class);

	private Map workflows;

	class WorkflowConfig {
		public WorkflowConfig(String type, String location) {
			this.type = type;
			this.location = location;
		}

		String type, location;
	}

	public WorkflowDescriptor getWorkflow(String name) {
		WorkflowConfig c = (WorkflowConfig) workflows.get(name);
		if (c == null) {
			throw new RuntimeException("Unknown workflow name");
		}

		File configRoot =
			new File(Config.getInstance().getConfigDirectoryPath());

		try {
			if (c.type.equalsIgnoreCase("URL")) {
				return WorkflowLoader.load(new URL(c.location));
			} else if (c.type.equalsIgnoreCase("file")) {
				c.location = new File(configRoot, c.location).getAbsolutePath();
				return WorkflowLoader.load(new File(c.location));
			} else {
				return WorkflowLoader.load(c.location);
			}
		} catch (Exception e) {
			log.fatal("Error creating workflow descriptor", e);
			throw new RuntimeException(
				"Error in workflow descriptor: " + e.getMessage());
		}
	}

	public void initDone() throws Exception {
		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();
		InputStream is = null;
		try {
			is =
				new FileInputStream(
					new File(
						Config.getInstance().getConfigDirectoryPath(),
						"workflows.xml"));
		} catch (Exception e) {
		}

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = null;

			try {
				db = dbf.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				log.fatal("Error creating document builder", e);
			}

			Document doc = db.parse(is);

			Element root =
				(Element) doc.getElementsByTagName("workflows").item(0);
			workflows = new HashMap();
			NodeList list = root.getElementsByTagName("workflow");
			for (int i = 0; i < list.getLength(); i++) {
				Element e = (Element) list.item(i);
				workflows.put(
					e.getAttribute("name"),
					new WorkflowConfig(
						e.getAttribute("type"),
						e.getAttribute("location")));
			}
		} catch (Exception e) {
			log.fatal("Error reading xml workflow", e);
			throw new RuntimeException(
				"Error in workflow config: " + e.getMessage());
		}
	}
	
	public String[] listAllNames() {
		Set keys = workflows.keySet();		
		Object[] array = keys.toArray();
		String[] retour = new String[array.length];
		for (int i = 0; i < retour.length; i++) {
			retour[i] = array[i]+"";
		}
		return retour;
	}
}
