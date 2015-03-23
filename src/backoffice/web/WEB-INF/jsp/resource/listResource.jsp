<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<html>

<head>
  <title>viewResources</title>  
  <link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<!-- Body -->

<body bgcolor="#eeeeee">

<table cellpadding="0" cellspacing="2">	
	<tr>			
		<td style="border:1px solid black" width="20" height="20" align="center">				
			<img src="images/nothing.gif" border="0" style="border:1px solid #ffffff" width="20" height="20"></td>						
		<td class="text">&nbsp;<a href="#select" onclick="eval(window.parent.<c:out value="${param['action']}"/>(''))"><bean:message key="general.nothing"/></a></td>
	</tr>		
	<c:forEach var="file" items="${files}">				
		<tr>			
			<td style="border:1px solid black">				
				<a href="resources/<c:out value="${param['id']}"/>/<c:out value="${file.name}"/>" target="_blank">							
					<c:choose>
						<c:when test="${file.extension=='gif' || file.extension=='jpg' || file.extension=='jpeg'}">
							<img src="resources/<c:out value="${param['id']}"/>/<c:out value="${file.name}"/>?width=100&height=100" width="20" height="20" border="0" style="border:1px solid white"></a></td>
						</c:when>
						<c:when test="${file.extension=='pdf'}">
							<img src="images/pdf-file.gif" border="0" style="border:1px solid #ffffff" width="20" height="20"></a></td>
						</c:when>
						<c:when test="${file.extension=='doc'}">
							<img src="images/doc-file.gif" border="0" style="border:1px solid #ffffff" width="20" height="20"></a></td>
						</c:when>
						<c:when test="${file.extension=='zip'}">
							<img src="images/zip-file.gif" border="0" style="border:1px solid #ffffff" width="20" height="20"></a></td>
						</c:when>
						<c:when test="${file.extension=='mp3' || file.extension=='wav' || file.extension=='mid'}">
							<img src="images/audio-file.gif" border="0" style="border:1px solid #ffffff" width="20" height="20"></a></td>
						</c:when>
						<c:when test="${file.extension=='avi' || file.extension=='mpeg' || file.extension=='mpg'}">
							<img src="images/video-file.gif" border="0" style="border:1px solid #ffffff" width="20" height="20"></a></td>
						</c:when>
						<c:otherwise>
							<img src="images/unknow-file.gif" border="0" style="border:1px solid #ffffff" width="20" height="20"></a></td>
						</c:otherwise>
					</c:choose>
			<td class="text">&nbsp;<a href="#select" onclick="eval(window.parent.<c:out value="${param['action']}"/>('<c:out value="${file.name}"/>'))"><c:out value="${file.name}"/></a></td>
		</tr>					
	</c:forEach>
</table>	

</body>

</html>
