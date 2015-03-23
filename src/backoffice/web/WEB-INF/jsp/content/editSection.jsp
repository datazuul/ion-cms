<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<body class="text" background="images/fond.gif">

	<img src="images/section-tile.gif" align="absmiddle">
	<span class="title">
		<bean:message key="editSection.title"/> " <c:out value="${section.metaData['name']}" default="<i>(undefined)</i>" escapeXml="false"/> "
	</span>
	<br>
	<html:form action="editSection.x">
		<input type="Hidden" name="id" value="<c:out value="${section.id}"/>">
		<table>
			<c:forEach var="localeItem" items="${locales}">
				<c:set scope="page" var="nameProperty" value="name_${localeItem.locale}"/>
				<tr>
					<td align="right" class="text"><bean:message key="editSection.label.name"/> (<c:out value="${localeItem.name}"/>) :&nbsp;</td>
					<td valign="middle"><input type="text" name="name_<c:out value="${localeItem.locale}"/>" value="<c:out value="${requestScope[nameProperty]}"/>">&nbsp;</td>
				</tr>
			</c:forEach>
			<tr>
				<td align="right" class="text"><bean:message key="editSection.label.workflow"/> :&nbsp;</td>
				<td class="text">	
					<html:select property="workflow" size="1" style="height:17px;">
						<html:options name="workflows" labelName="workflows"/>
					</html:select>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td align="right" class="text"><bean:message key="editSection.label.status"/> :&nbsp;</td>
				<td>	
					<html:select property="status" size="1" style="height:17px;">
						<html:option value="online">ONLINE</html:option>
						<html:option value="offline">OFFLINE</html:option>
					</html:select>
					&nbsp;<span class="error"><html:errors property="status"/></span>
				</td>
			</tr>
		</table>
		<br>
		<span class="title-list"><bean:message key="editSection.templateList"/></span>&nbsp;<span class="error"><html:errors property="template"/></span>
		<hr width="100%" size="1" noshade color="#949494">	
		<table>
			<c:forEach var="template" items="${types}">
				
					<tr>
						<td align="right" class="text" valign="top" bgcolor="#eeeeee">
							<%-- voir comment on peut faire cà ? on doit pouvoir ... --%>
							<c:set var="tempVar" value="${template.template}" scope="request"/>
							<% String tempVar2 = request.getAttribute("tempVar")+""; // y a des problémes quelque part ???!! %>
							<html:radio property="template" value="<%=tempVar2%>" style="border:0px;background-color:transparent" onclick="document.forms[0].submit()"/>
						</td>
						<td valign="top" class="text" align="left">
							<b><c:out value="${template.name}"/></b> : <br>
							<c:out value="${template.description}"/>
						</td>
					</tr>
				
			</c:forEach>
		</table>
		<c:if test="${type.propertiesNumber>0}">
			<br>
			<span class="title-list"><bean:message key="editSection.propertyList"/></span>&nbsp;<span class="error"><html:errors property="template"/></span>
			<hr width="100%" size="1" noshade color="#949494">	
			<table>
				<c:forEach var="property" items="${type.properties}">					
					<tr>
						<td align="right" class="text">
							<c:out value="${property.label}"/>&nbsp;:&nbsp;
						</td>
						<td class="text" align="left">
							<c:choose>
								<c:when test="${copyMeta==null}">
									<input type="text" name="META_<c:out value="${property.name}"/>" value="<c:out value="${section.metaData[property.name]}"/>">
								</c:when>
								<c:otherwise>
									<c:set var="temp_temp" scope="request" value="${property.name}"/>
									<input type="text" name="META_<c:out value="${property.name}"/>" value="<%=request.getAttribute("META_"+request.getAttribute("temp_temp"))%>">&nbsp;
									<span class="error">
										<c:set var="temp2" value="META_${property.name}" scope="request"/>
										<% String temp23 = request.getAttribute("temp2")+""; %>
										<html:errors property="<%=temp23%>"/>
									</span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>	
		<br><br>
		<input type="submit" name="ok" value="<bean:message key="general.ok"/>" class="button"/>&nbsp;<input type="submit" name="cancel" value="<bean:message key="general.cancel"/>" class="button"/>
	</html:form>

</body>

</html>
