<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<body class="text" background="images/fond.gif" style="cursor:default">

	<img src="images/user-tile.gif" align="absmiddle">
	<span class="title">
		<bean:message key="editUser.myProfile"/>
	</span>
	<br><br>		
		<span class="title-list"><bean:message key="createUser.properties"/></span>
		<hr width="100%" size="1" noshade color="#949494">	
		<table>
			<html:form action="myProfile.x">
			<tr>
				<td align="right" class="text">login :&nbsp;</td>
				<td valign="middle"><input type="Text" disabled value="<c:out value="${userLogin}"/>"></td>
			</tr>
			<tr>
				<td align="right" class="text"><bean:message key="editUser.label.password"/> :&nbsp;</td>
				<td valign="middle"><html:text property="password"/>&nbsp;<span class="error"><html:errors property="password"/></span></td>
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
		<input type="submit" name="ok" value="<bean:message key="general.ok"/>" class="button"/>&nbsp;<input type="submit" name="cancel" value="<bean:message key="general.cancel"/>" class="button"/>
	</html:form>

</body>

</html>
