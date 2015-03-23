<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>


<template:insert template='../template.jsp'>
  <template:put name='label' content='Utilisateurs' direct='true'/>
  <template:put name='content' direct='true'> 
  	 
	 <img src="images/user-tile.gif" align="middle" border="0"><span class="titre2">Créer un nouvel objet utilisateur.</span><br><br>
	 <c:if test="${error != null }">
			<span class="error"><c:out value="${error}"/></span><br><br>
	 </c:if>
	 
	 <table width="80%" align="left">
	 	<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;propriétés</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
	 	<tr>
			<td>
				 <html:form action="addUser.x">
				 	<table>			
						<tr>
							<td align="right" class="texte"><b>login : </b></td>
							<td><html:text property="login" size="20"/></td>
						</tr>
						<tr>
							<td align="right" class="texte"><b>password : </b></td>
							<td><html:text property="password" size="20"/></td>
						</tr>
						<tr>
							<td align="right" class="texte" valign="top"><b>groupes : </b></td>
							<td>
								<html:select property="groups" multiple="true" size="3">
									<html:options property="id" labelProperty="id" collection="groupList"/>
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
