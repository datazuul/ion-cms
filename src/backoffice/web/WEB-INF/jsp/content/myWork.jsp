<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>

<html>
<head>
	<title>viewSection</title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">	
</head>

<body class="text" onload='setBallon("BallonTip");'>

<!-- scripts pour les tooltips -->
<div id="BallonTip" style="POSITION:absolute; VISIBILITY:hidden; LEFT:-200px; Z-INDEX: 100" class="text"></div>
<script language="JavaScript" src="scripts/cross.js"></script>
<script language="JavaScript" src="scripts/tooltips.js"></script>	
	
	<!-- titre -->
	<img src="images/mywork-tile.gif" align="absmiddle">
	<span class="title">
		 <bean:message key="myWork.title"/> 
	</span><br><br>
				
	
</body>

</html>
