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

	<img src="images/user-tile.gif" align="absmiddle">
	<span class="title">
		<bean:message key="editUser.title"/> " <c:out value="${param['id']}"/> "
	</span>
	<br><br>		
		<span class="title-list"><bean:message key="createUser.properties"/></span>
		<hr width="100%" size="1" noshade color="#949494">	
		<table>
			<html:form action="editUser.x">
			<input type="Hidden" name="id" value="<c:out value="${param['id']}"/>">
			<tr>
				<td align="right" class="text">login :&nbsp;</td>
				<td valign="middle"><input type="Text" disabled value="<c:out value="${param['id']}"/>"></td>
			</tr>
			<tr>
				<td align="right" class="text"><bean:message key="editUser.label.password"/> :&nbsp;</td>
				<td valign="middle"><html:password property="password"/>&nbsp;<span class="error"><html:errors property="password"/></span></td>
			</tr>
			<tr>
				<td align="right" class="text"><bean:message key="editUser.label.name"/> :&nbsp;</td>
				<td valign="middle"><html:text property="name"/>&nbsp;<span class="error"><html:errors property="name"/></span></td>
			</tr>
			<tr>
				<td align="right" class="text"><bean:message key="editUser.label.email"/> :&nbsp;</td>
				<td valign="middle"><html:text property="email"/>&nbsp;<span class="error"><html:errors property="email"/></span></td>
			</tr>
		</table>
		<br><br>
		<span class="title-list"><bean:message key="editUser.groupList"/></span>
		<hr width="100%" size="1" noshade color="#949494">	
		<table cellpadding="1" cellspacing="1">
			<c:forEach var="group" items="${groups}">
				<c:if test="${group.metaData['system']==null}">
					<tr>
						<td align="right" class="text" valign="middle" bgcolor="#eeeeee">
							<%-- voir comment on peut faire cà ? on doit pouvoir ... --%>
							<c:set var="tempVar" value="${group.id}" scope="request"/>
							<% String tempVar2 = request.getAttribute("tempVar")+""; // y a des problémes quelque part ???!! %>
							<html:multibox property="groups" value="<%=tempVar2%>" style="border:0px;background-color:transparent"/>
						</td>
						<td class="text" align="left">
							&nbsp;<b><c:out value="${group.metaData['name']}"/></b> : 
							<c:out value="${group.metaData['description']}"/>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>		
		<br><br>
		<input type="submit" name="ok" value="<bean:message key="general.ok"/>" class="button"/>&nbsp;<input type="submit" name="cancel" value="<bean:message key="general.cancel"/>" class="button"/>
	</html:form>

</body>

</html>
