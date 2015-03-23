<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>

<ion:security action="canAdminResources" user="${userLogin}">
	<c:set value="yes" var="canAdminResources"/>
</ion:security>

<html>

<head>
  <title>viewResources</title>  
  <link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<!-- Body -->

<body bgcolor="white" background="images/fond.gif">

<table cellpadding="3" cellspacing="3">
	<tr>	
		<% int i=0; int j=0; %>		
		<c:forEach var="file" items="${files}">				
			<% if (i>4) { %></tr><tr><% ; i=0; } %>
			<% i++; j++; %>			
			<td valign="top">
				<table width="100" height="120" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" valign="middle" colspan="2" id="C<%=j%>" style="border:1px solid #959595" onmouseover="C<%=j%>.style.border='1px solid black';C2<%=j%>.style.color='black'" onmouseout="C<%=j%>.style.border='1px solid #959595';C2<%=j%>.style.color='#4e4e4e'">
							<a href="resources/<c:out value="${selectedResources}"/>/<c:out value="${file.name}"/>" target="_blank">							
							<c:choose>
								<c:when test="${file.extension=='gif' || file.extension=='jpg' || file.extension=='jpeg'}">
									<img src="resources/<c:out value="${selectedResources}"/>/<c:out value="${file.name}"/>?width=100&height=100" width="100" border="0" style="border:1px solid #ffffff">
								</c:when>
								<c:when test="${file.extension=='pdf'}">
									<img src="images/pdf-file.gif" border="0" style="border:1px solid #ffffff">
								</c:when>
								<c:when test="${file.extension=='doc'}">
									<img src="images/doc-file.gif" border="0" style="border:1px solid #ffffff">
								</c:when>
								<c:when test="${file.extension=='zip'}">
									<img src="images/zip-file.gif" border="0" style="border:1px solid #ffffff">
								</c:when>
								<c:when test="${file.extension=='mp3' || file.extension=='wav' || file.extension=='mid'}">
									<img src="images/audio-file.gif" border="0" style="border:1px solid #ffffff">
								</c:when>
								<c:when test="${file.extension=='avi' || file.extension=='mpeg' || file.extension=='mpg'}">
									<img src="images/video-file.gif" border="0" style="border:1px solid #ffffff">
								</c:when>
								<c:otherwise>
									<img src="images/unknow-file.gif" border="0" style="border:1px solid #ffffff">
								</c:otherwise>
							</c:choose>
							</a></td>
					</tr>
					<tr>
						<td id="C2<%=j%>" class="text" align="center" height="15" style="color:#4e4e4e;"><c:out value="${file.name}"/></td>
						<td width="10" align="right" valign="top">
							<c:if test="${canAdminResources != null}">
								<a href="deleteResource.x?id=<c:out value="${file.name}"/>" target="sub"><img src="images/poubelle.gif" border="0"></a>
							</c:if>
						</td>
					</tr>
				</table>
			</td>				
		</c:forEach>
</tr>
</table>	

</body>

</html>
