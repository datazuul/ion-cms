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

	<img src="images/publication-edit-tile.gif" align="absmiddle">
	<span class="title">
		<bean:message key="createPublication.title"/>
	</span>
	<br><br>
	<span class="title-list"><bean:message key="createPublication.description"/></span>	
	<hr width="100%" size="1" noshade color="#949494">		
		<table>
			<html:form action="createPublication.x">
			<input type="Hidden" name="id" value="<c:out value="${param['id']}"/>">
			<tr>
				<td align="right" class="text"><bean:message key="createPublication.label.name"/> :&nbsp;</td>
				<td valign="middle"><html:text property="name"/>&nbsp;<span class="error"><html:errors property="name"/></span></td>
			</tr>
			<tr>
				<td align="right" class="text"><bean:message key="createPublication.label.type"/> :&nbsp;</td>
				<td>	
					<html:select property="type" size="1" style="height:17px;">
						<c:forEach items="${types}" var="type">
							<option value="<c:out value="${type.id}"/>"><c:out value="${type.metaData['name']}" default="${type.id}"/></option>
						</c:forEach>
					</html:select>
					&nbsp;<span class="error"><html:errors property="type"/></span>
				</td>
			</tr>			
		</table>
		<br><br>
		<input type="submit" value="<bean:message key="general.ok"/>" class="button"/>&nbsp;<input type="submit" name="cancel" value="<bean:message key="general.cancel"/>" class="button"/>
	</html:form>

</body>

</html>
