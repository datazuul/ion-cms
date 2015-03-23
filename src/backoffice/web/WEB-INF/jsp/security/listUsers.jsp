<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>

<ion:security action="canAdminSecurity" user="${userLogin}">
	<c:set value="yes" var="canAdminSecurity"/>
</ion:security>

<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<body class="text" background="images/fond.gif">

	<img src="images/users-tile.gif" align="absmiddle">
	<span class="title">
		<bean:message key="listUsers.title"/>
	</span>
	<br><br>
	<span class="title-list"><bean:message key="listUsers.list"/></span> 
	<c:if test="${canAdminSecurity!=null}">
		( <img src="images/create.gif" align="absmiddle" border="0"> <a href="createUser.x" target="sub"><bean:message key="listUsers.create"/></a> )
	</c:if>
	<hr width="100%" size="1" noshade color="#949494">
	<table border="0" cellpadding="0" cellspacing="2" width="100%">
		<c:forEach var="user" items="${users}">
			<tr>				
				<!-- login (lien) -->
				<td width="300" class="text">
					<img src="images/puce.gif">&nbsp;
					<c:choose>
						<c:when test="${canAdminSecurity!=null&&user.metaData['canEdit']!='false'}">
							<a href="editUser.x?id=<c:out value="${user.login}"/>" target="sub" style="position:relative;top:-2px"><c:out value="${user.login}"/></a>
						</c:when>							
						<c:otherwise>
							<c:out value="${user.login}"/>
						</c:otherwise>
					</c:choose>						
				</td>
				<!-- nom complet -->
				<td width="300" class="text">
					<c:out value="${user.metaData['name']}" default="<em>(sans nom)</em>" escapeXml="false"/>
				</td>
				<!-- email -->
				<td class="text" width="300">
					<c:choose>
						<c:when test="${user.metaData['email']!=null}">
							<a href="mailto:<c:out value="${user.metaData['email']}"/>"><c:out value="${user.metaData['email']}" default="<em>(sans email)</em>" escapeXml="false"/></a>
						</c:when>
						<c:otherwise>						
							<c:out value="${user.metaData['email']}" default="<em>(sans email)</em>" escapeXml="false"/>
						</c:otherwise>
					</c:choose>
				</td>
				<!-- groupes -->
				<td class="text" width="400">	
					<% boolean virgule=false; %>				
					<c:forEach var="group" items="${user.groups}">
						<% if( virgule ) { %>,&nbsp;<%}%><c:out value="${group.metaData['name']}"/><%virgule=true;%>
					</c:forEach>
				</td>
				<!-- supprimer -->
				<td align="right" height="20">
					<c:if test="${canAdminSecurity!=null&&user.metaData['canEdit']!='false'}">
						<a onclick="return confirm('voulez-vous supprimer cet utilisateur ?');" href="deleteUser.x?id=<c:out value="${user.login}"/>" target="sub"><img src="images/poubelle.gif" border="0"></a>
					</c:if>
				</td>			
			</tr>
			<tr>
				<td height="1" bgcolor="#dddddd"></td>
				<td height="1" bgcolor="#dddddd"></td>
				<td height="1" bgcolor="#dddddd"></td>
				<td height="1" bgcolor="#dddddd"></td>
				<td height="1" bgcolor="#dddddd"></td>
			</tr>
		</c:forEach>
	</table>	

</body>

</html>
