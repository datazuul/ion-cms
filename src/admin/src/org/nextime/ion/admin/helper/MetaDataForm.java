package org.nextime.ion.admin.helper;

import java.util.*;

public class MetaDataForm {

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
				form+="<td align='right' class='texte' width='60'><b>"+key+" : </b></td>";
				form+="<td>";						
				if ( ht.get(key) instanceof String ) {
					form+="<input type='text' name='META_"+key+"' value='"+ht.get(key)+"' size='20'>&nbsp;<input type='image' style='border:0px' alt='supprimer cette meta-donnée' src='images/croix-fade.gif' border='0' onmouseover=\"this.src='images/croix.gif'\" onmouseout=\"this.src='images/croix-fade.gif'\" onclick=\"metaToDelete.value='"+key+"'\">";							
				} else {
					form+="<input type='text' name='META_"+key+"' disabled value='Objet JAVA' size='20'>&nbsp;<input type='image' style='border:0px' alt='supprimer cette meta-donnée' src='images/croix-fade.gif' border='0' onmouseover=\"this.src='images/croix.gif'\" onmouseout=\"this.src='images/croix-fade.gif'\" onclick=\"metaToDelete.value='"+key+"'\">";							
				}
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
		form+="<span class='texte'><br>utilisez la zone ci-dessous pour créer une nouvelle meta-donnée.</span>";			
		form+="<table>";					
		form+="<tr>";
		form+="<td width='60' align='right' class='texte'><b>nom : </b></td>";
		form+="<td valign='middle'>";
		form+="<input type='text' name='newMETA' size='15'>&nbsp;<input align='middle' type='submit' value='créer' style='cursor:hand;height:17px'>";				
		form+="</td>";
		form+="</tr>";											
		form+="</table>";
		form+="<input type='hidden' id='metaToDelete' name='metaToDelete'>";
		return form;
	}

}

