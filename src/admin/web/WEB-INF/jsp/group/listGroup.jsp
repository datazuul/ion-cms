<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Groupes' direct='true'/>
  <template:put name='content' direct='true'> 
 
  	<img src="images/group-tile.gif" align="middle" border="0"><span class="titre2">Liste des objets groupes.</span><br><br>
	
	<table cellpadding="0" cellspacing="2" width="100%">
	 	<tr>
			<td width="20"></td>
			<td class="titre-liste">&nbsp;&nbsp;<b>id</b></td>
			<td width="20"></td>			
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>		
		</tr>
	 <logic:iterate id="group" name="groups" scope="request">
		<tr>
			<td align="center"><a href="editGroup.x?id=<bean:write name="group" property="id"/>"><img src="images/puce.gif" align="middle" border="0"></a></td>
			<td class="texte">&nbsp;&nbsp;<a href="editGroup.x?id=<bean:write name="group" property="id"/>"><bean:write name="group" property="id"/></a>&nbsp;&nbsp;</td>
			<td align="center"><a href="delGroup.x?id=<bean:write name="group" property="id"/>"><img src="images/croix-fade.gif" onmouseover="this.src='images/croix.gif'" onmouseout="this.src='images/croix-fade.gif'" align="middle" border="0"></a></td>		
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>				
		</tr>
	 </logic:iterate>
	 </table>
	 <br><br>
	 <img src="images/etoile.gif" align="absmiddle">&nbsp;&nbsp;<a href="addGroup.x">créer un nouvel objet</a>.
	  
  </template:put>
</template:insert>
