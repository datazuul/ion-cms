<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Types de publications' direct='true'/>
  <template:put name='content' direct='true'> 
		 
	 <img src="images/type-tile.gif" align="middle" border="0"><span class="titre2">Editer l'objet type de publication "<bean:write name="id" scope="request"/>".</span><br><br>
	 <logic:present name="error" scope="request">
			<span class="error"><bean:write name="error" scope="request"/></span><br><br>
	 </logic:present>
	 
	 <html:form action="editType.x">
	 <table width="80%" align="left">
	 	<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;propriétés</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
	 	<tr>
			<td>				 
				 	<table>			
						<tr>
							<td align="right" class="texte" width="60"><b>id : </b></td>
							<td><html:text property="id" size="20" disabled="true"/></td>
							<html:hidden property="id"/>
						</tr>
						<tr>
							<td align="right" class="texte" width="60"><b>description : </b></td>
							<td><html:text property="description" size="20"/></td>							
						</tr>											
						<tr>
							<td align="right" class="texte"></td>
							<td><html:image src="images/valide-fade.gif" style="border:0px" onmouseover="this.src='images/valide.jpg'" onmouseout="this.src='images/valide-fade.gif'"/></td>
						</tr>			
					</table>
					<input type="hidden" name="id" value="<bean:write name="id" scope="request"/>">
					<input type="hidden" name="editSubmit" value="submit"> 				 	 
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;meta-données</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
		<tr>
			<td>
				<% // la partie du formulaire permettant d'editer les metaData n'est pas réutilisable                               %>
				<% // pour éviter de reduire la lisibilité de la JSP cette partie a été déportée dans une classe helper             %>
				<%=org.nextime.ion.admin.helper.MetaDataForm.getForm( (java.util.Hashtable)request.getAttribute("metaData") )%>
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;styles</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
		<tr>
			<td class="texte">
			<br>
				<strong><%=((java.util.Vector)request.getAttribute("styles")).size()%> styles  ont été chargés :</strong> 
				<ul>
					<logic:iterate id="style" name="styles">
						<li><bean:write name="style"/></li>
					</logic:iterate>
				</ul>			
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;opérations</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>		
	</table>	
	</html:form>
		
  </template:put>
</template:insert>
