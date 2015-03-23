<!-- Standard Struts Entries -->

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/controls.tld" prefix="controls" %>

<html>

<!-- Standard Content -->

<head>
  <title>tree</title>  
  <link rel="stylesheet" type="text/css" href="styles/tree.css">
  <link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<!-- Body -->

<body bgcolor="white" style="cursor:default">

<!-- Tree Component -->
<table cellpadding="0" cellspacing="0" width="100%" height="100%">
	<tr>
		<td valign="top">
			<controls:tree tree="treeControlTest"
               action="treeControl.x?tree=${name}"
               style="tree-control"
        	   styleSelected="tree-text-selected"
      		   styleUnselected="tree-text"
			   images="images" />
		</td>
	</tr>
	<tr>
		<td height="10"><a href="setUpTree.x?clean=yes">(actualiser)</a></td>
	</tr>
</table>  

</body>

<!-- Standard Footer -->

</html>
