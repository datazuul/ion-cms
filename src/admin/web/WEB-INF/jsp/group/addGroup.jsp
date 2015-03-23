<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>
<%@ page import='org.nextime.ion.framework.business.*' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Groupes' direct='true'/>
  <template:put name='content' direct='true'> 
  	 
	 <img src="images/group-tile.gif" align="middle" border="0"><span class="titre2">Créer un nouvel objet groupe.</span><br><br>
	 <logic:present name="error" scope="request">
			<span class="error"><bean:write name="error" scope="request"/></span><br><br>
	 </logic:present>
	 
	 <table width="80%" align="left">
	 	<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;propriétés</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
	 	<tr>
			<td>
				 <html:form action="addGroup.x">
				 	<table>			
						<tr>
							<td align="right" class="texte"><b>id : </b></td>
							<td><html:text property="id" size="20"/></td>
						</tr>
						<tr>
							<td align="right" class="texte" valign="top"><b>utilisateurs : </b></td>
							<td>
								<html:select property="users" multiple="true" size="3">
									<html:options property="login" labelProperty="login" collection="userList"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td align="right" class="texte"></td>
							<td><html:image src="images/valide-fade.gif" style="border:0px" onmouseover="this.src='images/valide.jpg'" onmouseout="this.src='images/valide-fade.gif'"/></td>
						</tr>			
					</table>					
					<input type="hidden" name="addSubmit" value="submit"> 
				 </html:form>	 
			</td>
		</tr>
	</table> 
	
  </template:put>
</template:insert>
