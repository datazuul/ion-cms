package org.nextime.ion.framework.business;

import java.util.Hashtable;

import org.jdom.Document;
import org.nextime.ion.framework.workflow.Workflow;

/**
 * @author gbort
 */
public abstract class PublicationVersion {

	public abstract void setData(Document newData);
	
	public abstract void setDataAsString(String newData) throws Exception;

	public abstract void resetData();

	public abstract Document getData();
	
	public abstract String getDataAsString();

	public abstract Workflow getWorkflow(User user);

	public abstract Workflow getWorkflow();

	public abstract void setWorkflow(Workflow wf);

	public abstract Publication getPublication();

	public abstract int getVersion();
	
	public abstract String getXml();
	
	public abstract String getXml(String language);
	
	public abstract User getAuthor();
	
	public abstract void setAuthor(User author);

}
