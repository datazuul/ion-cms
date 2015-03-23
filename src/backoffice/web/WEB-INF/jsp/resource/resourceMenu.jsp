<!-- Standard Struts Entries -->

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/controls.tld" prefix="controls" %>

<html>

<!-- Standard Content -->

<head>
  <title>tree</title>  
  <link rel="stylesheet" type="text/css" href="styles/tree.css">
  <link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<!-- Body -->

<body bgcolor="white">

<!-- Tree Component -->
<table cellpadding="2" cellspacing="2" width="100%">
	<tr>
		<td valign="top" class="text">
			<img src="images/resource-tile.gif" align="absmiddle">
			<span class="title">
				 ressources
			</span>
			<br><br>
			<c:forEach var="res" items="${resources}">
				<img align="absmiddle" src="images/<c:out value="${res.icon}"/>">
				<c:choose>
					<c:when test="${selectedResources==res.id}">
						<a href="resourceManagement.x?select=<c:out value="${res.id}"/>" target="sub" class="tree-text-selected"><c:out value="${res.label}"/></a><br>
					</c:when>
					<c:otherwise>
						<a href="resourceManagement.x?select=<c:out value="${res.id}"/>" target="sub" class="tree-text"><c:out value="${res.label}"/></a><br>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<br>
			<html:form action="uploadResource.x" enctype="multipart/form-data">
				<span class="title-list"><bean:message key="resources.upload"/> :</span>
				<hr width="100%" size="1" noshade color="#949494">						
				<table>
					<tr>
						<td align="right" class="text"><bean:message key="resources.file"/> : </td>
						<td><html:file property="file" style="width=180"/></td>
					</tr>
					<tr>
						<td align="right" class="text"><bean:message key="resources.name"/> : </td>
						<td><html:text property="name" style="width=180"/></td>
					</tr>
					<tr>
						<td align="right" class="text"></td>
						<td><input type="submit" class="button" value=" envoyer "></td>
					</tr>
				</table>
				<br>
				<center>
					<span class="error"><html:errors/></span>	
				</center>
			</html:form>
		</td>
	</tr>	
</table>  

</body>

<!-- Standard Footer -->

</html>
