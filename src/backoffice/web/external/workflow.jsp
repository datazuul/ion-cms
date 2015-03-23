<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<% org.nextime.ion.framework.mapping.Mapping.begin(); %>

<c:if test="${param['password']!=null}">
	<%
		try {
			if( org.nextime.ion.framework.business.User.getInstance( request.getParameter("login") ).getPassword().equals( request.getParameter("password") ) ) {
				session.setAttribute("userLogin", request.getParameter("login") );
			}
		} catch( Exception e ) {}
	%>
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>ion - Backoffice</title>
	<link rel="stylesheet" type="text/css" href="../styles/global.css">
	<link rel="stylesheet" type="text/css" href="../styles/template-front.css">
	<link rel="stylesheet" type="text/css" href="styles/global.css">
	<link rel="stylesheet" type="text/css" href="styles/template-front.css">
</head>

<body background="../images/fond.gif"">

	<c:choose>
	
		<c:when test="${param['externalTemplate']!=null}">
			<% org.nextime.ion.framework.mapping.Mapping.rollback(); %>
			<br>
			<script>
				document.body.background="images/fond.gif";
			</script>
			<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">				
				<tr>
					<td align="center">
						<img src="images/login-tile.gif" align="absmiddle">
						<span class="title" style="font-size:36px"><span style="color:black">back</span>.office</span>
						<br><br>
					</td>		
				</tr>
				<tr>
					<td align="left">														
						<c:import url="${externalAction}?retour=/external/workflowEnd.jsp"/>					
					</td>
				</tr>				
			</table>
		</c:when>
		
		<c:when test="${userLogin!=null}">
			<%
				pageContext.setAttribute("publication", org.nextime.ion.framework.business.Publication.getInstance( request.getParameter("publicationId") ) );
				pageContext.setAttribute("locales", org.nextime.ion.framework.locale.LocaleList.getInstance().getLocales() );
			%>
			<c:set var="currentLocale" scope="page" value="fr"/>
			<c:if test="${param['locale']!=null}">
				<c:set var="currentLocale" scope="page" value="${param['locale']}"/>
			</c:if>
			<br>
			<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
				<form action="workflow.jsp">					
					<input type="hidden" name="publicationId" value="<c:out value="${param['publicationId']}"/>">
					<input type="hidden" name="version" value="<c:out value="${param['version']}"/>">
					<tr>
						<td align="center">
							<img src="../images/login-tile.gif" align="absmiddle">
							<span class="title" style="font-size:36px"><span style="color:black">back</span>.office</span>
							<br><br>
						</td>		
					</tr>
					<tr>
						<td>		
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td valign="bottom">
										<span style="font-family:verdana; color:#5d5d5d; font-size:10px; font-weight:bold;">
											aperçu de la publication "<span style="color:black"><c:out value="${publication.metaData['name']}"/></span>" <i style="font-weight:normal">(<c:out value="${publication.versions[param['version']-1].workflow.currentSteps[0].name}"/>)</i>
										</span>
									</td>
									<td align="right">
										<span style="font-family:verdana; color:#0148B2; font-size:10px; font-weight:bold;">&nbsp;Langue :&nbsp;</span>
										<select name="locale" style="font-family:verdana; color:black; font-size:9px; font-weight:bold; border:1px solid black; background-color:#d6e4f3;" onChange="form.submit()">
											<c:forEach var="localeItem" items="${locales}">
												<option value="<c:out value="${localeItem.locale}"/>" <c:if test="${localeItem.locale==param['locale']}">selected</c:if>><c:out value="${localeItem.name}"/></option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>							
							<hr width="100%" size="1" noshade color="#949494">
							<%
								org.nextime.ion.framework.business.Publication publi = (org.nextime.ion.framework.business.Publication)pageContext.getAttribute("publication");
								String view = new String(org.nextime.ion.framework.helper.Viewer.getView( publi, Integer.parseInt(request.getParameter("version")), "detail", pageContext.getAttribute("currentLocale")+"" ));
							%>
							<br>
							<%=view%>							
						</td>
					</tr>
					<tr>
						<td align="center">
							<br><br>
							<%
								org.nextime.ion.framework.workflow.Workflow workflow = publi.getVersion( Integer.parseInt(request.getParameter("version")) ).getWorkflow( org.nextime.ion.framework.business.User.getInstance( session.getAttribute("userLogin")+"" ) );
								org.nextime.ion.framework.workflow.WorkflowAction actions[] = workflow.getAvailableActions();
								for( int i=0; i<actions.length; i++ ) {
							%>
								<input onclick="document.location='../actions.x?externalTemplate=/external/workflow.jsp&retour=/external/workflowEnd.jsp&id=<c:out value="${param['publicationId']}"/>&version=<c:out value="${param['version']}"/>&action=<%=actions[i].getId()%>'" type="button" class="button" name="workflowAction" value="<%=actions[i].getName()%>">&nbsp;&nbsp;
							<%
								}
							%>
							<br><br>
						</td>
					</tr>
				</form>
			</table>
		</c:when>
		<c:otherwise>
			<br>
			<table align="center" border="0" cellpadding="0" cellspacing="0">
				<form action="workflow.jsp">					
					<input type="hidden" name="publicationId" value="<c:out value="${param['publicationId']}"/>">
					<input type="hidden" name="version" value="<c:out value="${param['version']}"/>">
					<tr>
						<td colspan="2">
							<img src="../images/login-tile.gif" align="absmiddle">
							<span class="title" style="font-size:36px"><span style="color:black">back</span>.office</span>
							<br><br>
						</td>		
					</tr>
					<tr>
						<td class="text" width="145" align="right">login : &nbsp;</td>
						<td width="250">
							<input type="text" name="login" style="height:17px;" value="<c:out value="${param['login']}"/>">
							<c:if test="${param['password']!=null}">
								&nbsp;&nbsp;<span class="error"><img src="../images/warning.gif" align="absmiddle"> incorect</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<td align="right" class="text">password : &nbsp;</td>
						<td>
							<input type="password" name="password" style="height:17px;">							
						</td>
					</tr>
					<tr>
						<td align="right" class="text"></td>
						<td height="20" valign="bottom"><input type="submit" class="button" value="entrer"></td>
					</tr>
				</form>
			</table>			
		</c:otherwise>
		
	</c:choose>

</body>
</html>

<% org.nextime.ion.framework.mapping.Mapping.rollback(); %>
