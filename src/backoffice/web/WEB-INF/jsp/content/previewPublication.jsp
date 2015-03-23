<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<html>
<head>
	<title>Aperçu de la publication</title>
	<link rel="stylesheet" type="text/css" href="styles/template-front.css">
</head>

<body>

<table width="95%">
	<tr>
		<td valign="top">
			<form action="previewPublication.x" method="post" id="form">
				<input type="Hidden" name="id" value="<c:out value="${param['id']}"/>">
				<input type="Hidden" name="version" value="<c:out value="${param['version']}"/>">
				<img src="images/preview-tile.gif" align="absmiddle">
				<span style="font-family:verdana; color:#5d5d5d; font-size:18px; font-weight:bold; position:relative; top:-4px;">
					Aperçu de la publication
				</span>
				<table width="100%">
					<tr>
						<td>
							<span style="font-family:verdana; color:#0148B2; font-size:10px; font-weight:bold;">&nbsp;Style :&nbsp;</span>
							<select name="style" style="font-family:verdana; color:black; font-size:9px; font-weight:bold; border:1px solid black; background-color:#d6e4f3;" onChange="form.submit()">
								<c:forEach var="style" items="${styles}">
									<option value="<c:out value="${style}"/>" <c:if test="${style==param['style']}">selected</c:if>><c:out value="${style}"/></option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;
							<span style="font-family:verdana; color:#0148B2; font-size:10px; font-weight:bold;">&nbsp;Langue :&nbsp;</span>
							<select name="locale" style="font-family:verdana; color:black; font-size:9px; font-weight:bold; border:1px solid black; background-color:#d6e4f3;" onChange="form.submit()">
								<c:forEach var="localeItem" items="${locales}">
									<option value="<c:out value="${localeItem.locale}"/>" <c:if test="${localeItem.locale==param['locale']}">selected</c:if>><c:out value="${localeItem.name}"/></option>
								</c:forEach>
							</select>
							<hr width="100%" size="1" noshade color="#949494">
						</td>
					</tr>					
				</table>			
			</form>	
			<c:out value="${view}" escapeXml="false"/>
		</td>
	</tr>
</table>

</body>

</html>
