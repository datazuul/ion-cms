package org.nextime.ion.backoffice.bean;

public class File  {
	
	private java.io.File file;	
	
	public File( java.io.File file ) {
		this.file = file;
	}

	/**
	 * Returns the extension.
	 * @return String
	 */
	public String getExtension() {
		try {
			return file.getName().substring(file.getName().lastIndexOf(".")+1).toLowerCase();
		} catch( Exception e ) {
			return "NULL";
		}
	}


	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return file.getName();
	}	

}

