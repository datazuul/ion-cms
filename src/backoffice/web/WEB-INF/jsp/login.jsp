<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<html>
<head>
	<title>login</title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<body background="images/fond.gif" style="cursor:default">

<html:form action="login">
	<br><br>
	<table align="center" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<img src="images/login-tile.gif" align="absmiddle">
				<span class="title" style="font-size:36px"><span style="color:black">back</span>.office</span>
				<br><br>
			</td>		
		</tr>
		<tr>
			<td class="text" width="145" align="right"><bean:message key="login.login"/> : &nbsp;</td>
			<td width="250"><html:text property="login" style="height:17px;"/>&nbsp;&nbsp;<span class="error"><html:errors property="login"/></span></td>
		</tr>
		<tr>
			<td align="right" class="text"><bean:message key="login.password"/> : &nbsp;</td>
			<td><html:password property="password" style="height:17px;"/>&nbsp;&nbsp;<span class="error"><html:errors property="password"/></span></td>
		</tr>
		<tr>
			<td align="right" class="text"></td>
			<td height="20" valign="bottom"><input type="submit" class="button" value="<bean:message key="login.enter"/>"></td>
		</tr>
	</table>

</html:form>

</body>

</html>
