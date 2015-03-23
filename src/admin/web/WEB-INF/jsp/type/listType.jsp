<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>
<%@ page import='org.nextime.ion.framework.business.*' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Types de publications' direct='true'/>
  <template:put name='content' direct='true'> 
  	 
	 <img src="images/type-tile.gif" align="middle" border="0"><span class="titre2">Liste des objets types de publications.</span><br><br>
	 
	 <table cellpadding="0" cellspacing="2" width="100%">
	 	<tr>
			<td width="20"></td>
			<td class="titre-liste">&nbsp;&nbsp;<b>id</b></td>	
			<td class="titre-liste">&nbsp;&nbsp;<b>model</b></td>
			<td class="titre-liste">&nbsp;&nbsp;<b>styles</b></td>		
			<td width="20"></td>			
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>	
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>		
			<td height="1" bgcolor="#949494"></td>		
		</tr>
	 <logic:iterate id="type" name="types" scope="request">
		<tr>
			<td align="center"><a href="editType.x?id=<bean:write name="type" property="id"/>"><img src="images/puce.gif" align="middle" border="0"></a></td>
			<td class="texte">&nbsp;&nbsp;<a href="editType.x?id=<bean:write name="type" property="id"/>"><bean:write name="type" property="id"/></a>&nbsp;&nbsp;</td>	
			<td class="texte" width="50">&nbsp;&nbsp;<bean:write name="type" property="modelState" filter="false"/>&nbsp;&nbsp;</td>
			<td class="texte" width="50">&nbsp;&nbsp;<bean:write name="type" property="stylesState" filter="false"/>&nbsp;&nbsp;</td>			
			<td align="center"><a href="delType.x?id=<bean:write name="type" property="id"/>"><img src="images/croix-fade.gif" onmouseover="this.src='images/croix.gif'" onmouseout="this.src='images/croix-fade.gif'" align="middle" border="0"></a></td>		
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>						
		</tr>
	 </logic:iterate>
	 </table>	
	 <br><br>
	 <img src="images/etoile.gif" align="absmiddle">&nbsp;&nbsp;<a href="addType.x">créer un nouvel objet</a>. 
	 
	
  </template:put>
</template:insert>
