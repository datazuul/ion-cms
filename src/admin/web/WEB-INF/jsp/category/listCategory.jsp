<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>
<%@ page import='org.nextime.ion.framework.business.*' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Catégories' direct='true'/>
  <template:put name='content' direct='true'> 
  	 
	 <img src="images/category-tile.gif" align="middle" border="0"><span class="titre2">Liste des objets catégories.</span><br><br>
	 
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
	 <logic:iterate id="category" name="categories" scope="request">
		<tr>
			<td align="center"><a href="editCategory.x?id=<bean:write name="category" property="id"/>"><img src="images/puce.gif" align="middle" border="0"></a></td>
			<td class="texte">&nbsp;&nbsp;<a href="editCategory.x?id=<bean:write name="category" property="id"/>"><bean:write name="category" property="id"/></a>&nbsp;&nbsp;</td>			
			<td align="center"><a href="delCategory.x?id=<bean:write name="category" property="id"/>"><img src="images/croix-fade.gif" onmouseover="this.src='images/croix.gif'" onmouseout="this.src='images/croix-fade.gif'" align="middle" border="0"></a></td>		
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>						
		</tr>
	 </logic:iterate>
	 </table>	
	 <br><br>
	 <img src="images/etoile.gif" align="absmiddle">&nbsp;&nbsp;<a href="addCategory.x">créer un nouvel objet</a>. 
	 
	
  </template:put>
</template:insert>
