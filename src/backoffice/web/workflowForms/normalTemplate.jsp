<%@ taglib uri="/WEB-INF/tlds/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>

<html>

<head>
	<title>...</title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">	
</head>

<body class="text">

	<img src="images/work-tile.gif" align="absmiddle">
	<span class="title">
		 execution du workflow 
	</span>	
	<br><br>
	<c:import url="${externalAction}?retour=/contentManagement.x"/>	
	
	
</body>

</html>
