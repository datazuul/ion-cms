<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>



<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">	
</head>

<body class="text" onload='setBallon("BallonTip");' background="images/fond.gif">
	<!-- scripts pour les tooltips -->
	<div id="BallonTip" style="POSITION:absolute; VISIBILITY:hidden; LEFT:-200px; Z-INDEX: 100" class="text"></div>
	<script language="JavaScript" src="scripts/cross.js"></script>
	<script language="JavaScript" src="scripts/tooltips.js"></script>

	<img src="images/search-tile.gif" align="absmiddle">
	<span class="title">
		<bean:message key="search.title"/>
	</span>
	<br>
	<form action="search.x" method="POST">
		<table>
			<tr>
				<td align="right" class="text">
					<b>
						<bean:message key="search.keywords"/> :&nbsp;
					</b>
				</td>
				<td>
					<input type="text" name="keyWords" style="width=200">
				</td>
			</tr>
			<tr>
				<td align="right" class="text">
					<b>
						index :&nbsp;
					</b>
				</td>
				<td>
					<select name="index" style="width=120">
						<c:forEach items="${indexs}" var="index">
							<option value="<c:out value="${index}"/>" <c:if test="${index==param['index']}">selected</c:if>><c:out value="${index}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td valign="right">					
				</td>
				<td>
					<input type="submit" value=" rechercher " class="button"/>
				</td>
			</tr>
		</table>			
	</form>	
	
	<!-- publications list -->
	<span class="title-list"><bean:message key="search.result"/></span>
	
	<ion:security action="canCreateSection" user="${userLogin}">
		( <img src="images/clef.gif" align="absmiddle">&nbsp;<a href="reIndex.x"><bean:message key="search.reindex"/></a> )
	</ion:security>
	
	<hr width="100%" size="1" noshade color="#949494"> 	
	<c:choose>
		<!-- il y a des publications à afficher -->
		<c:when test="${result!=null}">											
			<table border="0" cellpadding="0" cellspacing="2" width="600">
				<% java.util.Vector v = new java.util.Vector(); %>
				<c:forEach var="r" items="${result}" varStatus="status">
					<c:set var="tid" value="${r.publication.id}" scope="page"/>
					<% if( !v.contains(pageContext.getAttribute("tid")) ) { %>
						<tr>
							<!-- nom (lien) -->
							<td class="text">							
								<img src="images/pucef.gif" align="absmiddle">							
								<a onclick="parent.frames['banner'].document.location='menu.x?tab=1'" href="highlight.x?id=<c:out value="${r.publication.id}"/>" style="text-decoration:none"><strong style="color:#5e5e5e;">
									<c:out value="${r.publication.metaData['name']}"/> (versions
									<c:forEach var="t" items="${result}">
										<c:if test="${t.publication.id==r.publication.id}">
											<c:if test="${virgule!=null}">, </c:if>
											<c:set var="virgule" value="V"/>
											<c:out value="${t.version}"/>									
										</c:if>
									</c:forEach>)
									<c:remove var="virgule"/>								
									<% v.add( pageContext.getAttribute("tid") ); %>
								</strong></a>
							</td>
						</tr>
					<% } %>
				</c:forEach>
			</table>
		</c:when>
		<!-- il n'y a pas de publications à afficher -->
		<c:otherwise>
			<i>(<bean:message key="search.noResult"/>)</i>
		</c:otherwise>
	</c:choose>	

</body>

</html>
