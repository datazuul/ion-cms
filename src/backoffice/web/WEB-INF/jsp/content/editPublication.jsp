<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">
	<link rel="stylesheet" type="text/css" href="styles/publication-form.css">	
</head>

<body class="text" background="images/fond.gif">
	<script language="JavaScript" src="scripts/calendrier.js"></script>	
	
	<c:set var="currentLocale" scope="page" value="${defaultLocale.locale}"/>
	<c:if test="${param['targetLocale']!=null}">
		<c:set var="currentLocale" scope="page" value="${param['targetLocale']}"/>
	</c:if>
		
	<img src="images/publication-edit-tile.gif" align="absmiddle">
	<span class="title">
		<bean:message key="editPublication.title"/> " <c:out value="${publication.metaData['name']}" default="<i>(undefined)</i>" escapeXml="false"/> " (version <c:out value="${version}"/>)
	</span>
	<br>
	<html:form action="editPublication.x">
		<input type="Hidden" name="currentLocale" value="<c:out value="${currentLocale}"/>">
		<input type="Hidden" name="id" value="<c:out value="${publication.id}"/>">
		<input type="Hidden" name="version" value="<c:out value="${version}"/>">
		<input type="Hidden" name="itsOk" value="itsOk">		
		<span class="title-list">
			<bean:message key="editPublication.data"/>
			&nbsp;&nbsp;|&nbsp;&nbsp;langue :&nbsp;
		</span>		
		
		<%-- locales list --%>
		<select name="targetLocale" onchange="document.forms(0).action='edx/changeLocale.jsp';document.forms(0).submit()" >
			<c:forEach items="${locales}" var="localeItem">
				<option value="<c:out value="${localeItem.locale}"/>" <c:if test="${localeItem.locale==currentLocale}">selected</c:if>><c:out value="${localeItem.name}"/></option>
			</c:forEach>				
		</select>	
		
		<hr width="100%" size="1" noshade color="#949494">
		<span class="error"><html:errors property="data"/></span>
		<%--<html:textarea cols="70" rows="15" property="data" style="visibility:hidden; position:absolute"/>--%>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>					
					<c:import url="/edx/getPublicationXml.jsp?id=${publication.id}&version=${version}" var="xml"/>
					<c:import url="/edx/publi2form.xsl" var="xsl"/>	
					<x:transform xslt="${xsl}" xml="${xml}">
						<x:param name="locale" value="${currentLocale}"/>
					</x:transform>					
				</td>
			</tr>
		</table>
		
		<textarea name="data" style="position:absolute; visibility:hidden">
			<c:out value="${xml}" escapeXml="false"/>
		</textarea>		
		
		<%-- anciennement edition de la date qui ne marche plus de toutes facons <br><br>
		<span class="title-list"><bean:message key="editPublication.properties"/></span>
		<hr width="100%" size="1" noshade color="#949494">
		<table>
			<tr>
				<td align="right" class="text"><bean:message key="editPublication.label.date"/> :&nbsp;</td>				
				<td valign="middle">					
					<html:text property="date" size="12"/><a href="#calendar" style="text-decoration:none">
					<img src="images/calendar.gif" onclick="pop('date','fr');this.style.cursor='hand'" align="absmiddle" border="0"></a>
					<span class="error"><html:errors property="date"/></span>
				</td>
			</tr>			
		</table>--%>
		<br><br><br>
		<input onclick="document.forms(0).action='edx/merge.jsp';return true" type="submit" name="ok" value="<bean:message key="general.ok"/>" class="button"/>&nbsp;<input type="submit" name="cancel" value="<bean:message key="general.cancel"/>" class="button"/>
	</html:form>

</body>

</html>
