<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>

<form action="choixMDD.x" method="POST">
	<input type="hidden" name="retour" value="<c:out value="${param['retour']}"/>">
	<span class="title-list">choississez le membre du directoire qui devra contrôler</span>	
	<hr width="100%" size="1" noshade color="#949494">	
	<input type="hidden" name="action" value="<c:out value="${param['action']}"/>">
	<input type="hidden" name="version" value="<c:out value="${param['version']}"/>">
	<input type="hidden" name="id" value="<c:out value="${param['id']}"/>">
	<table>
		<c:forEach items="${users}" var="user" varStatus="status">
			<tr>
				<td align="right" class="text" valign="middle" bgcolor="#eeeeee">
					<input <c:if test="${status.first}">checked</c:if> type="radio" name="membreDD" value="<c:out value="${user.login}"/>" style="border:0px;background-color:transparent">
				</td>
				<td class="text">
					<strong>&nbsp;<c:out value="${user.metaData['name']}"/></strong>
				</td>
			</tr>
		</c:forEach>	
	</table>	
	<br><img src="images/blank.gif" height="3"><br> 
	<input type="submit" class="button" value="valider">	
</form>		
