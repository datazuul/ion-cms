<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>

<html>
<head>
	<title>home</title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">
</head>

<body onload='setBallon("BallonTip");' background="images/fond.gif" style="cursor:default">

<!-- scripts pour les tooltips -->
<div id="BallonTip" style="POSITION:absolute; VISIBILITY:hidden; LEFT:-200px; Z-INDEX: 100" class="text"></div>
<script language="JavaScript" src="scripts/cross.js"></script>
<script language="JavaScript" src="scripts/tooltips.js"></script>	

<table width="100%" height="170" cellpadding="0" cellspacing="0">
	<tr>
		<td width="63%" valign="top" class="text">
			<img src="images/login-tile.gif" align="absmiddle">
			<span class="title" style="font-size:36px"><span style="color:black">back</span>.office</span>
			<br><br>
			<table width="95%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="text">
						<b style="font-size:10px">bienvenu sur le backoffice du site Sonepar</b><br><br>						
Interdum volgus videt, est ubi peccat. Si veteres ita miratur
laudatque poetas, ut nihil anteferat, nihil illis comparet, errat. Si
quaedam nimis antique, si peraque dure dicere credit eos, ignave multa
fatetur, et sapit et mecum facit et Iova iudicat aequo. Non equidem
insector delendave carmina Livi esse reor, memini quae plagosum mihi
parvo Orbilium dictare; sed emendata videri pulchraque et exactis
minimum distantia miror. Inter quae verbum emicuit si forte decorum, et
si versus paulo concinnior unus et alter, iniuste totum ducit venditque poema.
					</td>
				</tr>
			</table>
		</td>
		<td width="2%" align="left">
			<img src="images/gris.gif" height="100%" width="1">
		</td>
		<td width="35%" valign="top">			
			<a href="myProfile.x"><img src="images/user-tile.gif" align="middle" border="0"><bean:message key="home.myProfil"/></a><br>	
			<a href="contentManagement.x" onclick="parent.frames['banner'].document.location='menu.x?tab=1'"><img src="images/content-tile.gif" align="middle" border="0"><bean:message key="home.createContent"/></a><br>
			<a href="resourceManagement.x" onclick="parent.frames['banner'].document.location='menu.x?tab=1'"><img src="images/resource-tile.gif" align="middle" border="0"><bean:message key="home.resources"/></a><br>
			<a href="search.x" onclick="parent.frames['banner'].document.location='menu.x?tab=2'"><img src="images/search-tile.gif" align="middle" border="0"><bean:message key="home.searchContent"/></a><br>	
			<a href="listUsers.x" onclick="parent.frames['banner'].document.location='menu.x?tab=3'"><img src="images/users-tile.gif" align="middle" border="0"><bean:message key="home.users"/></a><br>
		</td>
	</tr>
</table>

<span class="text" style="font-size:18px;font-weight:bold">message board</span>

<div style="overflow-y:auto; height=40%; width=100%; background-color:#F2F3F6; position:relative; top:5px; padding:4px">
	<% pageContext.setAttribute("messages", org.nextime.ion.backoffice.messageBoard.MessageBoard.getInstance().getMessages()); %>
	<% pageContext.setAttribute("currentDate", new java.util.Date()); %>
	<table width="100%" cellpadding="0" cellspacing="0">
		<form action="messageBoard/postMessage.jsp">
			<tr>
				<td style="font-size:10px; font-family:verdana;">
					<nobr><img src="images/fforum.gif" align="absmiddle" style="position:relative; top:1px"><b style="color:#3E6AAB"><c:out value="${userLogin}"/></b> <span style="color:#7991B3">(<fmt:formatDate value="${currentDate}" pattern="dd MMM - HH:mm"/>)</span>
					&nbsp;</nobr>
				</td>
				<td style="font-size:10px; font-family:verdana;">
					<input type="text" name="message" size="65">&nbsp;<input type="submit" class="button" value="poster">
				</td>
				<td height="15"></td>
			</tr>
			<tr>
				<td colspan="3" height="4"></td>
			</tr>
			<tr>
				<td colspan="3" background="images/pt.gif" height="1"></td>
			</tr>
			<tr>
				<td colspan="3" height="4"></td>
			</tr>
		</form>	
		<ion:security action="canAdminResources" user="${userLogin}">
			<c:set value="yes" var="isAdmin" scope="page"/>		
		</ion:security>	
		<% org.nextime.ion.framework.mapping.Mapping.begin(); %>
		<c:forEach var="mes" items="${messages}" varStatus="status">		
			<tr>
				<td style="font-size:10px; font-family:verdana;" valign="top" width="10">
					<c:set var="poster" value="${mes.poster}" scope="page"/>
					<% 	
						pageContext.setAttribute("userPoster",org.nextime.ion.framework.business.User.getInstance(pageContext.getAttribute("poster")+"")); %>
					<nobr><img src="images/fforum.gif" align="absmiddle" style="position:relative; top:1px"><b style="color:#3E6AAB"><a style="color:#3E6AAB;font-size:10px; text-decoration:none;font-family:verdana;" href="mailto:<c:out value="${userPoster.metaData['email']}"/>" title="envoyer un email à <c:out value="${userPoster.metaData['name']}"/>"><c:out value="${mes.poster}"/></a></b> <span style="color:#7991B3">(<fmt:formatDate value="${mes.date}" pattern="dd MMM - HH:mm"/>)</span>
					&nbsp;</nobr>
				</td>
				<td style="font-size:10px; font-family:verdana;" valign="top" align="left">
					<c:out value="${mes.message}"/>					
				</td>
				<td width="10" style="font-size:10px; font-family:verdana;" height="15">
					<c:if test="${(userLogin==mes.poster)||(isAdmin=='yes')}">
						<a onclick="return confirm('voulez vous supprimez ce message ?');" href="messageBoard/deleteMessage.jsp?nb=<c:out value="${status.index}"/>"><img src="images/poubelle-mini.gif" border="0"></a>
					</c:if>
				</td>
			</tr>		
		</c:forEach>
		<% org.nextime.ion.framework.mapping.Mapping.rollback(); %>
	</table>
</div>

</body>

</html>
