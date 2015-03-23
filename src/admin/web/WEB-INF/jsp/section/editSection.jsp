<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Sections' direct='true'/>
  <template:put name='content' direct='true'> 
		 
	 <img src="images/section-tile.gif" align="middle" border="0"><span class="titre2">Editer l'objet section "<bean:write name="id" scope="request"/>".</span><br><br>
	 <logic:present name="error" scope="request">
			<span class="error"><bean:write name="error" scope="request"/></span><br><br>
	 </logic:present>
	 
	 <html:form action="editSection.x">
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
							<td align="right" class="texte" valign="top"><b>parent : </b></td>
							<td>
								<html:select property="parent" size="1" style="width=150px">
									<option value="_NULL_">(racine)</option>
									<html:options property="id" labelProperty="id" collection="sectionList"/>
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
			<td class="titre-liste"><img src="images/puce.gif" align="middle">&nbsp;meta-données</td>
		</tr>
		<tr>
			<td bgcolor="#0148B2" height="1"></td>
		</tr>
		<tr>
			<td>				
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
				<img src="images/clef.gif" align="middle">&nbsp;<a href="moveSection.x?id=<bean:write name="id"/>&type=up">monter la section</a><br>
				<img src="images/clef.gif" align="middle">&nbsp;<a href="moveSection.x?id=<bean:write name="id"/>&type=down">descendre la section</a><br>
			</td>
		</tr>		
	</table>	
	</html:form>
		
  </template:put>
</template:insert>
