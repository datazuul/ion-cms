package org.nextime.ion.backoffice.form;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.nextime.ion.backoffice.bean.Resources;

public class UploadResourceForm extends ActionForm {

	private String name;
	private FormFile file;

	/**
	 * @see org.apache.struts.action.ActionForm#validate(ActionMapping, HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping arg0,
		HttpServletRequest request) {
		
		// set resources directory
        try {
        	request.setAttribute("resources",Resources.getResourceXmlBeans(servlet));
        }
        catch( Exception e ) {
        	e.printStackTrace();
        }
		
		ActionErrors errors = new ActionErrors();
		if (getName() == null) {
			ActionError error =
				new ActionError("error.uploadResource.missingName");
			errors.add("name", error);			
		} else {
			if (getName().trim().equals("")) {
				ActionError error2 =
					new ActionError("error.uploadResource.missingName");
				errors.add("name", error2);
			} else {
				if (getName().indexOf(".") != -1
					|| getName().indexOf("/") != -1
					|| getName().indexOf("\\") != -1
					|| getName().indexOf("%") != -1
					|| getName().indexOf("&") != -1) {
					ActionError error3 =
						new ActionError("error.uploadResource.badName");
					errors.add("name", error3);

				}
			}
		}
		if (getFile().getFileSize() == 0) {
			ActionError error =
				new ActionError("error.uploadResource.missingFile");
			errors.add("file", error);			
		} 
		return errors;
	}

	/**
	 * @see org.apache.struts.action.ActionForm#reset(ActionMapping, HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		setName(null);
		setFile(null);
	}

	/**
	 * Returns the file.
	 * @return File
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the file.
	 * @param file The file to set
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
