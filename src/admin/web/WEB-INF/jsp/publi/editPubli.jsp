<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/tlds/c.tld' prefix='c' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Publications' direct='true'/>
  <template:put name='content' direct='true'> 
		 
	 <img src="images/publi-tile.gif" align="middle" border="0"><span class="titre2">Editer l'objet publication "<bean:write name="id" scope="request"/>".</span><br><br>
	 <logic:present name="error" scope="request">
			<span class="error"><bean:write name="error" scope="request"/></span><br><br>
	 </logic:present>
	 
	 <html:form action="editPubli.x">
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
						<!--
						<tr>
							<td align="right" class="texte"><b>state : </b></td>
							<td><html:text property="state" size="20"/></td>
						</tr>
						-->						
						<tr>
							<td align="right" class="texte"><b>type : </b></td>
							<td>
								<html:select property="type" size="1" style="width=150px" onchange="document.forms('publiForm').editSubmit.value='temp';document.forms('publiForm').submit()">
									<html:options property="id" labelProperty="id" collection="typeList"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td align="right" class="texte" valign="top"><b>categories : </b></td>
							<td>
								<html:select property="categories" multiple="true" size="3">
									<html:options property="id" labelProperty="id" collection="categoryList"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td align="right" class="texte" valign="top"><b>sections : </b></td>
							<td>
								<html:select property="sections" multiple="true" size="3">
									<html:options property="id" labelProperty="path" collection="sectionList"/>
								</html:select>
							</td>
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
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;versions :</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
		<tr>			
			<td class="texte">
				<table cellpadding="0">
					<tr>
						<td class="texte" align="right"><strong>version :</strong></td>
						<td>
							<html:select property="version" size="1" style="width=150px" onchange="document.forms('publiForm').editSubmit.value='changeVersion';document.forms('publiForm').submit()">					
								<html:options property="version" labelProperty="version" collection="versions"/>
							</html:select>	
						</td>
					</tr>
					<tr>			
						<td class="texte" align="right">
							<strong>auteur :</strong> 
						</td>
						<td>
							<!--<html:select property="author" size="1" style="width=150px">
								<option value="_NULL_">(indéfini)</option>
								<html:options property="login" labelProperty="login" collection="userList"/>
							</html:select>-->						
							<html:text property="author" size="20" disabled="true"/>
							<html:hidden property="author"/>
						</td>
					</tr>
					<tr>			
						<td class="texte" align="right">
							<strong>nouvelle version :</strong> 
						</td>
						<td>
							<html:select property="author" size="1" style="width=150px" onchange="newVersionUser.value=this.value">								
								<html:options property="login" labelProperty="login" collection="userList"/>
							</html:select>	
							<input id="newVersionUser" type="hidden">		
							<img src="images/clef.gif" align="middle">&nbsp;<a href="#newVersion" onclick="document.location='newVersion.x?user='+newVersionUser.value+'&id=<bean:write name="id" scope="request"/>'">créer</a>				
						</td>
					</tr>
				</table>							
			</td>
		</tr>		
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;workflow</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
		<tr>
			<td class="texte">
				<table>
					<tr>
						<td class="texte" align="right" width="60">
							<b>id : </b>
						</td>
						<td>
							<html:text property="workflow" disabled="true"/>
							<html:hidden property="workflow"/>
						</td>
					</tr>
					<tr>
						<td class="texte" align="right" width="60">
							<b>state : </b>
						</td>
						<td class="texte">
							<c:forEach items="${steps}" var="step">
									<c:out value="${step.name}"/> (<c:out value="${step.status}"/>)&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td class="texte" align="right" width="60">
							<b>user : </b>
						</td>
						<td>
							<html:select property="workflowUser" size="1" style="width=150px" onchange="document.forms('publiForm').editSubmit.value='temp';document.forms('publiForm').submit()">
								<html:options property="login" labelProperty="login" collection="userList"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="texte" align="right" width="60">
							<b>actions : </b>
						</td>
						<td class="texte">
							<select name="workflowAction" onchange="document.forms('publiForm').editSubmit.value='temp';document.forms('publiForm').submit()">
								<option value="NULL">------------</option>
								<c:forEach items="${actions}" var="action">
									<option value="<c:out value="${action.id}"/>"><c:out value="${action.name}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="texte" align="right" width="60">
							<b>security : </b>
						</td>
						<td class="texte">
							<c:forEach items="${permissions}" var="permission">
									<c:out value="${permission}"/> &nbsp;
							</c:forEach>
						</td>
					</tr>
				</table>				
			</td>
		</tr>
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;données</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
		<tr>
			<td>
				<textarea style="width=380;height=150" name="data"><%=request.getAttribute("data")%></textarea>
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
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;opérations</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
		<tr>
			<td>
				<img src="images/clef.gif" align="middle">&nbsp;<a onclick="window.open('xmlPubli.x?id=<bean:write name="id" scope="request"/>&version=<bean:write name="version" scope="request"/>','','width=800,height=450,scrollbars=yes,resizable=yes')" href="#null">verifier le xml</a>
			</td>
		</tr>
	</table>	
	</html:form>
		
  </template:put>
</template:insert>
