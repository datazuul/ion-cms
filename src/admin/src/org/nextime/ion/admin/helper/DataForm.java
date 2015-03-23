package org.nextime.ion.admin.helper;

import java.util.*;

public class DataForm {

	public static synchronized String getForm( Hashtable ht ) {
		if( ht == null ) ht = new Hashtable();
		String form = "";
		form+="<table>";		
			Enumeration en = ht.keys();
			boolean bouton = false;
		
			while( en.hasMoreElements() ) { 
				String key = en.nextElement()+"";
				bouton = true;
				form+="<tr>";
				form+="<td align='right' valign='top' class='texte' width='60'><b>"+key+" : </b></td>";
				form+="<td>";						
				form+="<textarea name='DATA_"+key+"' cols='40' rows='3'>"+ht.get(key)+"</textarea>";							
				form+="</td>";
				form+="</tr>";	
		 	}
			if ( bouton ) {
				form+="<tr>";
				form+="<td></td>";
				form+="<td><input type='image' style='border:0px' src='images/valide-fade.gif' onmouseover=\"this.src='images/valide.jpg'\" onmouseout=\"this.src='images/valide-fade.gif'\"/></td>";
				form+="</tr>";	
			}				
		form+="</table>";
		return form;
	}

}

