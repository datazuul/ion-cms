<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Publications' direct='true'/>
  <template:put name='content' direct='true'> 
 
  	<img src="images/publi-tile.gif" align="middle" border="0"><span class="titre2">Liste des objets publications.</span><br><br>
	
	<table cellpadding="0" cellspacing="2" width="100%">
	 	<tr>
			<td width="20"></td>
			<td class="titre-liste">&nbsp;&nbsp;<b>id</b></td>			
			<td class="titre-liste">&nbsp;&nbsp;<b>type</b></td>
			<td class="titre-liste">&nbsp;&nbsp;<b>versions</b></td>			
			<td width="20"></td>			
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>				
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>					
			<td height="1" bgcolor="#949494"></td>		
		</tr>
	 <c:forEach var="publi" items="${publis}">
		<tr>
			<td align="center"><a href="editPubli.x?id=<c:out value="${publi.id}"/>"><img src="images/puce.gif" align="middle" border="0"></a></td>
			<td class="texte">&nbsp;&nbsp;<a href="editPubli.x?id=<c:out value="${publi.id}"/>"><c:out value="${publi.id}"/></a>&nbsp;&nbsp;</td>
			<td class="texte">&nbsp;&nbsp;<c:out value="${publi.type.id}"/>&nbsp;&nbsp;</td>	
			<td class="texte">&nbsp;&nbsp;<c:out value="${publi.lastVersion.version}" escapeXml="false"/>&nbsp;&nbsp;</td>
			<td align="center"><a href="delPubli.x?id=<c:out value="${publi.id}"/>"><img src="images/croix-fade.gif" onmouseover="this.src='images/croix.gif'" onmouseout="this.src='images/croix-fade.gif'" align="middle" border="0"></a></td>				
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>				
			<td height="1" bgcolor="#949494"></td>			
		</tr>
	 </c:forEach>
	 </table>
	 <br><br>
	 <img src="images/etoile.gif" align="absmiddle">&nbsp;&nbsp;<a href="addPubli.x">créer un nouvel objet</a>.
	  
  </template:put>
</template:insert>
