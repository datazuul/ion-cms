<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>
<%@ page import='org.nextime.ion.framework.business.*' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Publications' direct='true'/>
  <template:put name='content' direct='true'> 
  	 
	 <img src="images/publi-tile.gif" align="middle" border="0"><span class="titre2">Créer un nouvel objet publication.</span><br><br>
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
				 <html:form action="addPubli.x">
				 	<table>			
						<tr>
							<td align="right" class="texte"><b>id : </b></td>							
							<td class="texte"><html:text property="id" size="20"/></td>
						</tr>
						<!--
						<tr>
							<td align="right" class="texte"><b>state : </b></td>
							<td><html:text property="state" size="20"/></td>
						</tr>
						-->
						<tr>
							<td align="right" class="texte" valign="top"><b>author : </b></td>
							<td>
								<html:select property="author" size="1" style="width=150px">
									<html:options property="login" labelProperty="login" collection="userList"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td align="right" class="texte" valign="top"><b>workflow : </b></td>
							<td>
								<html:select property="workflow" size="1" style="width=150px">
									<html:options name="workflowList"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td align="right" class="texte" valign="top"><b>type : </b></td>
							<td>
								<html:select property="type" size="1" style="width=150px">
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
					<input type="hidden" name="addSubmit" value="submit"> 
				 </html:form>	 
			</td>
		</tr>
	</table> 
	
  </template:put>
</template:insert>
