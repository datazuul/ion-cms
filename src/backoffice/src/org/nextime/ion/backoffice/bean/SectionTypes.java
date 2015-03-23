package org.nextime.ion.backoffice.bean;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;
import javax.servlet.http.HttpServlet;

public class SectionTypes {

	private static TypeBean bean;
	
	public static TypeBean getSectionBean(HttpServlet servlet, String template) throws Exception {
		if( bean == null ) {
			FileInputStream fis = new FileInputStream( new File(servlet.getServletContext().getRealPath("../../conf/templates.xml")) );
			bean = TypeBean.parse(fis);
			fis.close();
		}
		return bean.getBean(template);
	}
	
	public static Vector getSectionsBeans(HttpServlet servlet) throws Exception {
		if( bean == null ) {
			FileInputStream fis = new FileInputStream( new File(servlet.getServletContext().getRealPath("../../conf/templates.xml")) );
			bean = TypeBean.parse(fis);
			fis.close();
		}
		return bean.getItems();
	}

}

