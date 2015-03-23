<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>

<%
	int nbg = 1;
	if( "home".equals( request.getAttribute("view") ) ) nbg = 1;
	if( "user".equals( request.getAttribute("view") ) ) nbg = 2;
	if( "group".equals( request.getAttribute("view") ) ) nbg = 3;
	if( "publi".equals( request.getAttribute("view") ) ) nbg = 4;
	if( "section".equals( request.getAttribute("view") ) ) nbg = 5;
	if( "category".equals( request.getAttribute("view") ) ) nbg = 6;
	if( "type".equals( request.getAttribute("view") ) ) nbg = 7;
	if( "config".equals( request.getAttribute("view") ) ) nbg = 8;
	if( "help".equals( request.getAttribute("view") ) ) nbg = 9;	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title><template:get name='title'/></title>
	<style>
		.titre {
			font-family:verdana;
			color:#5d5d5d;
			font-size:18px;
			font-weight:bold;			
		}
		.titre-liste {
			font-family:verdana;
			color:#0148B2;
			font-size:10px;
			font-weight:bold;			
		}
		.titre2 {
			font-family:verdana;
			color:black;
			font-size:11px;		
			font-weight:bold;	
		}			
		.gros-titre {
			font-family:verdana;
			color:#272727;
			font-size:28px;
			font-weight:bold;			
		}
		.texte {
			font-family:verdana;
			color:black;
			font-size:9px;
		}
		.error {
			font-family:verdana;
			color:#cc0000;
			font-size:9px;
			font-weight:bold;
		}
		input {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;			
			background-color:white;
			border:1px solid black;
		}
		textarea {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;			
			background-color:white;
			border:1px solid black;
			width=200px;
		}
		select {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;
			border:1px solid black;
			background-color:white;
			width=200px;
		}			
		a {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;
			text-decoration:underline;
		}
		a:hover {
			font-family:verdana;
			color:#717171;
			font-size:9px;
			font-weight:bold;
			text-decoration:underline;
		}
			
</style>
</head>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">

<table width="100%" height="100%" bgcolor="#F5F5F5" cellpadding="0" cellspacing="0">
	<tr>
		<td width="38" valign="top" background="images/fond.gif">
			<table width="38" background="images/fond<%=nbg%>.gif" cellpadding="0" cellspacing="0">
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==1) { %>
						<td align="center"><a href="home.x?view" title="home"><img src="images/home.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="home.x?view" title="home"><img src="images/home-fade.gif" onmouseover="this.src='images/home-high.gif'" onmouseout="this.src='images/home-fade.gif'" border="0"></a></td>
					<% } %>	
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==2) { %>
						<td align="center"><a href="listUser.x" title="home"><img src="images/user.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="listUser.x" title="utilisateurs"><img src="images/user-fade.gif" onmouseover="this.src='images/user-high.gif'" onmouseout="this.src='images/user-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==3) { %>
						<td align="center"><a href="listGroup.x" title="groupes"><img src="images/group.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="listGroup.x" title="groupes"><img src="images/group-fade.gif" onmouseover="this.src='images/group-high.gif'" onmouseout="this.src='images/group-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==4) { %>
						<td align="center"><a href="listPubli.x" title="publications"><img src="images/publi.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="listPubli.x" title="publications"><img src="images/publi-fade.gif" onmouseover="this.src='images/publi-high.gif'" onmouseout="this.src='images/publi-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==5) { %>
						<td align="center"><a href="listSection.x" title="sections"><img src="images/section.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="listSection.x" title="sections"><img src="images/section-fade.gif" onmouseover="this.src='images/section-high.gif'" onmouseout="this.src='images/section-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==6) { %>
						<td align="center"><a href="listCategory.x" title="categories"><img src="images/category.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="listCategory.x" title="categories"><img src="images/category-fade.gif" onmouseover="this.src='images/category-high.gif'" onmouseout="this.src='images/category-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==7) { %>
						<td align="center"><a href="listType.x" title="types de publications"><img src="images/type.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="listType.x" title="types de publications"><img src="images/type-fade.gif" onmouseover="this.src='images/type-high.gif'" onmouseout="this.src='images/type-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==8) { %>
						<td align="center"><a href="config.x" title="configuration"><img src="images/config.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="config.x" title="configuration"><img src="images/config-fade.gif" onmouseover="this.src='images/config-high.gif'" onmouseout="this.src='images/config-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9"></td>
				</tr>
				<tr>					
					<% if (nbg==9) { %>
						<td align="center"><a href="help.x" title="documentation"><img src="images/help.gif" border="0"></a></td>
					<% } else { %>
						<td align="center"><a href="help.x" title="documentation"><img src="images/help-fade.gif" onmouseover="this.src='images/help-high.gif'" onmouseout="this.src='images/help-fade.gif'" border="0"></a></td>
					<% } %>					
				</tr>
				<tr>
					<td height="9">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td background="images/fond-centre.gif" valign="top" width="550">
			 <table width="500">			 	
				<tr>	
					<td valign="middle">						
					</td>				
					<td class="titre" height="40" valign="middle">
						<template:get name='label'/>
					</td>
				</tr>
				<tr>
					<td width="10"></td>
					<td class="texte">
						<template:get name='content'/>
					</td>
				</tr>
			 </table>
		</td>	
		<td background="images/fond-centre3.gif"  width="1"></td>
		<td background="images/fond-centre2.gif" valign="top">
			&nbsp;
		</td>
	</tr>
</table>

</body>
</html>
