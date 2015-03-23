<%@ taglib uri="/WEB-INF/tlds/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/tlds/cache.tld" prefix="cache"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>ion menu</title>
	<link rel="stylesheet" type="text/css" href="styles/menu.css">
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">

	<c:set var="tab" value="${param['tab']}"/>
	<c:if test="${tab==null}">
		<c:set var="tab" value="NULL"/>
	</c:if>
	<c:if test="${userLogin==null}">
		<c:set var="tab" value="NOT_CONNECTED"/>
	</c:if>	

	<!-- smart menu from menu.xml with cache ... -->
	<cache:cache scope="application" name="menus" key="${tab}_${userLogin}">
		   
		<c:import url="/xml/menu.xml" var="xml"/>
		<c:import url="/xml/menu.xsl" var="xsl"/>
		<x:transform xslt="${xsl}">		
			<backoffice-menu selectedTab="<c:out value="${tab}"/>" user="<c:out value="${userLogin}"/>">
				<c:out value="${xml}" escapeXml="false"/>						
			</backoffice-menu>
		</x:transform>
		
	</cache:cache>

</body>
</html>
