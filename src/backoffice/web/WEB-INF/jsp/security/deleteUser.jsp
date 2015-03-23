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

	<img src="images/deleteUser-tile.gif" align="absmiddle">
	<span class="title">
		suppression d'un utilisateur
	</span>
	<br><br>
	
	<form action="deleteUser.x">
		<input type="hidden" name="id" value="<c:out value="${param['id']}"/>">
		<span class="title-list">vous devez choisir à qui attribuer les publications de <c:out value="${param['id']}"/> aprés sa suppression</span>
		<hr width="100%" size="1" noshade color="#949494">	
	
		<span class="text">
			Choississez un utilisateur : 
			<select size="1" name="newUser">
				<c:forEach items="${users}" var="user">
					<c:if test="${user.login!=param['id'] && user.metaData['canEdit']==null}">
						<option value="<c:out value="${user.login}"/>"><c:out value="${user.login}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</span>					
		<br><br><br><br>
		<input type="submit" name="ok" value="<bean:message key="general.ok"/>" class="button"/>&nbsp;<input type="submit" name="cancel" value="<bean:message key="general.cancel"/>" class="button"/>
	</form>

</body>

</html>
