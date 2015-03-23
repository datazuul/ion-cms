<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>
<%@ taglib uri="/WEB-INF/tlds/cache.tld" prefix="cache"%>

<html>
<head>
	<title>viewSection</title>
	<link rel="stylesheet" type="text/css" href="styles/global.css">	
</head>

<body class="text" onload='setBallon("BallonTip");' background="images/fond.gif" style="cursor:default">

<!-- scripts pour les tooltips -->
<div id="BallonTip" style="POSITION:absolute; VISIBILITY:hidden; LEFT:-200px; Z-INDEX: 100" class="text"></div>
<script language="JavaScript" src="scripts/cross.js"></script>
<script language="JavaScript" src="scripts/tooltips.js"></script>
	
	<!-- titre -->
	<img src="images/content-tile.gif" align="absmiddle">
	<span class="title">
		 <bean:message key="viewSection.title"/>"&nbsp;<c:out value="${sectionName}" default="<i>(indéfini)</i>" escapeXml="false"/>&nbsp;"
		 <c:if test="${section.metaData['status']=='offline'}">
		 &nbsp;(offline)
		 </c:if>		 
		 <a href="../section/<c:out value="${section.id}"/>.html" title="visiter cette section sur le site" target="_blank"><img src="images/oeuil.gif" border="0" style="position=relative;top=4px"></a>
	</span>
	<br><br>
	<span style="font-size:11px; font-weight:bold; color:#006699"><c:out value="${sectionDescription}"/></span>
	<br><br><br>
	
<ion:security action="canCreateSection" user="${userLogin}">
	<c:set var="chef" value="yes" scope="page"/>

		<!-- section list -->
		<span class="title-list"><bean:message key="viewSection.sectionTitle"/></span> &nbsp;( <img src="images/create.gif" align="absmiddle" border="0"> &nbsp;<a href="createSection.x?id=<c:out value="${param['id']}"/>" target="sub"><bean:message key="viewSection.createSection"/></a> )
		<hr width="100%" size="1" noshade color="#949494">		
		<c:choose>
			<!-- il y a des sections à afficher -->
			<c:when test="${sectionsSize>0}">
				<table border="0" cellpadding="1" cellspacing="0" width="100%">
					<c:forEach var="section" items="${sections}">
						<tr>
							<!-- icone -->
							<td width="20">
								<c:choose>
									<c:when test="${section.metaData['status']=='offline'}">
										<img src="images/section-offline.gif" style="cursor:help"  align="absmiddle" alt="<bean:message key="general.offlineSection"/>">
									</c:when>
									<c:otherwise>
										<img src="images/section.gif" align="absmiddle">
									</c:otherwise>
								</c:choose>						
							</td>
							<!-- nom (lien) -->
							<td valign="top">
								&nbsp;<a href="editSection.x?id=<c:out value="${section.id}"/>" target="sub"><c:out value="${section.metaData['name']}" default="<i>(indéfinie)</i>" escapeXml="false"/></a>
							</td>
							<!-- move down -->
							<td width="11"><a href="moveSection.x?id=<c:out value="${section.id}"/>&type=down" target="sub"><img src="images/up.gif" border="0"></a></td>
							<!-- move up -->
							<td width="11"><a href="moveSection.x?id=<c:out value="${section.id}"/>&type=up" target="sub"><img src="images/down.gif" border="0"></a></td>
							<!-- supprimer -->
							<form action="deleteSection.x" target="sub" onsubmit="return confirm('supprimer cette section ?')">
							<td width="20" align="right">
								<ion:security action="canDeleteSection" user="${userLogin}" publication="${publication.id}">							
									<c:if test="${section.metaData['system']==null}">	
										<input type="hidden" name="id" value="<c:out value="${section.id}"/>">
										<input type="image" style="cursor:hand;" src="images/poubelle.gif" style="border=0; background-color:transparent">								
									</c:if>
								</ion:security>							
							</td>
							</form>
						</tr>
					</c:forEach>
				</table>
				<br><br>
			</c:when>
			<!-- il n'y a pas de sections à afficher -->
			<c:otherwise>
				<i style="color:#222222"><bean:message key="viewSection.noSections"/></i>
				<br><br><br>
			</c:otherwise>
		</c:choose>
	
</ion:security>
	
<!-- publications list -->
<ion:security section="${section.id}" action="canCreatePublication" user="${userLogin}">
	<c:set var="canCreatePublication" scope="page"/>
</ion:security>
<c:if test="${canCreatePublication!=null || publicationsSize>0}">
	<span class="title-list"><bean:message key="viewSection.publicationTitle"/></span>	
	<c:if test="${canCreatePublication!=null}">
		&nbsp;( <img src="images/create.gif" align="absmiddle" border="0"> <a href="createPublication.x?id=<c:out value="${param['id']}"/>" target="sub"><bean:message key="viewSection.createPublication"/></a> )
	</c:if>	
	<hr width="100%" size="1" noshade color="#949494"> 	
	<c:choose>
		<!-- il y a des publications à afficher -->
		<c:when test="${publicationsSize>0}">								
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<c:forEach var="publication" items="${publications}">
					<!-- pour chaque version -->
					<c:forEach var="publicationVersion" items="${publication.versions}">
					
						<c:if test="${publicationVersion.version==publication.lastVersion.version || versionDisplayInfos[publication.id]=='true'}">
						
							<ion:security action="canEditPublication" user="${userLogin}" publication="${publicationVersion.publication.id}" version="${publicationVersion.version}">
								<c:set var="canEditPublication" value="yes"/>
							</ion:security> 
							<tr <c:if test="${highlightId==publication.id}">bgcolor="#fce4e6"</c:if>>
								<!-- depliant -->
								<td valign="top" width="13">
								<c:choose>
									<c:when test="${publicationVersion.version==publication.lastVersion.version}">
										<c:choose>
											<c:when test="${publication.lastVersion.version==1}">
												<img src="images/plus-fade.gif" align="absmiddle">
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${versionDisplayInfos[publication.id]=='true'}">
														<a href="viewSection.x?id=<c:out value="${section.id}"/>&collapse=<c:out value="${publication.id}"/>" title="cacher les anciennes versions"><img src="images/moins.gif" align="absmiddle" border="0"></a>
													</c:when>
													<c:otherwise>
														<a href="viewSection.x?id=<c:out value="${section.id}"/>&expand=<c:out value="${publication.id}"/>" title="afficher toutes les versions"><img src="images/plus-normal.gif" align="absmiddle" border="0"></a>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${publicationVersion.version==1}">
												<img src="images/line-end.gif" align="absmiddle" border="0">
											</c:when>
											<c:otherwise>
												<img src="images/line-middle.gif" align="absmiddle" border="0">
											</c:otherwise>
										</c:choose>										
									</c:otherwise>
								</c:choose>
								</td>
								<!-- icone -->
								<td width="22" height="25" align="left">		
									<c:choose>
										<c:when test="${publicationVersion.workflow.metadatas['publicationIcon']!=null}">
											<img style="cursor:help" title="<c:forEach items="${publicationVersion.workflow.currentSteps}" var="step"><c:out value="${step.name}"/></c:forEach>" src="<c:out value="${publicationVersion.workflow.metadatas['publicationIcon']}"/>" align="absmiddle">
										</c:when>
										<c:otherwise>
											<img src="images/publication.gif" align="absmiddle">	
										</c:otherwise>	
									</c:choose>
								</td>
								<!-- nom (lien) -->
								<td class="text" style="color:#5e5e5e;cursor:default">									
									<c:choose>
										<c:when test="${canEditPublication!=null}">
											<a title="formulaire d'édition" href="editPublication.x?id=<c:out value="${publication.id}"/>&version=<c:out value="${publicationVersion.version}"/>" target="sub">
											<c:out value="${publication.metaData['name']}"/> 
											</a>									
										</c:when>
										<c:otherwise>
											<strong style="color:#5e5e5e;"><c:out value="${publication.metaData['name']}"/></strong>
										</c:otherwise>
										(version <c:out value="${publicationVersion.version}"/>)
										<%-- for sorting debugging (<c:out value="${publication.metaData['index']}"/>) --%>
									</c:choose>
									<c:if test="${publicationVersion.version==publication.lastVersion.version && canCreatePublication != null}">
										<a onclick="return confirm('Voulez vous créer une nouvelle version de cette publication ?')" href="newVersion.x?id=<c:out value="${publication.id}"/>" target="sub" title="créer la version <c:out value="${publicationVersion.version+1}"/> de cette publication"><img src="images/newVersion.gif" align="absmiddle" border="0"></a>
									</c:if>													
								</td>
								<!-- move publication -->
								<c:choose>
									<c:when test="${chef!=null}">
										<!-- move down -->
										<td width="11"><a title="monter la publication" href="movePublication.x?pId=<c:out value="${publication.id}"/>&id=<c:out value="${section.id}"/>&type=up"><img src="images/up.gif" border="0"></a></td>
										<!-- move up -->
										<td width="11"><a title="descendre la publication" href="movePublication.x?pId=<c:out value="${publication.id}"/>&id=<c:out value="${section.id}"/>&type=down"><img src="images/down.gif" border="0"></a></td>
									</c:when>
									<c:otherwise>
										<td></td>
										<td></td>
									</c:otherwise>
								</c:choose>								
								<!-- informations -->
								<td width="30" align="right">									
									<a style="cursor:help" href="#infos" onmouseover="showBallon('<span class=text><b>- type : </b> <c:out value="${publication.type.metaData['name']}"/><br><b>- version : </b> <c:out value="${publicationVersion.version}"/><br><b>- auteur : </b> <c:out value="${publicationVersion.author.login}" default="indéfini"/><br><b>- état : </b> <c:out value="${publicationVersion.workflow.currentSteps[0].name}"/></span>', 1, 170, '#f0f0f0');" onmouseout="hideBallon()"><img src="images/infos.gif" border="0"></a>
								</td>								
								<!-- preview -->									
								<td width="30" align="center"><a href="#null" title="prévisualisation" onclick="window.open('previewPublication.x?id=<c:out value="${publication.id}"/>&version=<c:out value="${publicationVersion.version}"/>','preview','width=600,height=400,resizable=yes,scrollbars=yes')"><img src="images/oeuil.gif" border="0"></a></td>
								<!-- history -->									
								<td width="30" align="left">
									<a href="#null" title="afficher l'historique" onclick="window.open('historyPublication.x?id=<c:out value="${publication.id}"/>&version=<c:out value="${publicationVersion.version}"/>','history','width=600,height=250,resizable=yes,scrollbars=yes')"><img src="images/historique.gif" border="0"></a>
								</td>								
								<!-- actions -->				
								<form target="sub" method="post" action="actions.x" id="form_<c:out value="${publicationVersion.id}"/>">
								<td align="right" width="160" valign="top">		
									<input type="hidden" name="retour" value="/contentManagement.x">
									<input type="hidden" name="externalTemplate" value="/workflowForms/normalTemplate.jsp">					
									<input type="hidden" name="id" value="<c:out value="${publication.id}"/>">	
									<input type="hidden" name="version" value="<c:out value="${publicationVersion.version}"/>">							
									<select name="action" onchange="form_<c:out value="${publicationVersion.id}"/>.submit()">
										<option value="nothing">workflow .......................................</option>
										<ion:workflowActions var="actions" publication="${publication.id}" user="${userLogin}" version="${publicationVersion.version}">
											<c:forEach items="${actions}" var="action">
												<option value="<c:out value="${action.id}"/>"><c:out value="${action.name}"/></option>
											</c:forEach>
										</ion:workflowActions>
									</select>					
								</td>								
								</form>															
								<!-- supprimer -->
								<form action="deletePublication.x" target="sub" onsubmit="return confirm('supprimer cette publication ?')">
								<td width="20" align="right" valign="top">
									<c:if test="${publication.lastVersion.version==publicationVersion.version}">
										<%-- hack for 2st <ion:security action="canDeletePublication" user="${userLogin}" publication="${publication.id}">--%>								
											<c:choose>
												<c:when test="${chef!=null}">
													<input type="hidden" name="id" value="<c:out value="${publication.id}"/>">
													<input type="hidden" name="sectionId" value="<c:out value="${section.id}"/>">
													<input style="cursor:hand;" type="image" title="supprimer la publication de cette section" src="images/poubelle.gif" style="border=0; background-color:transparent">								
												</c:when>
												<c:otherwise>
													<img src="images/poubelle-fade.gif">
												</c:otherwise>
											</c:choose>											
										<%--</ion:security>--%>
										<%--<c:if test="${canDelete==null}">
											<img src="images/poubelle-fade.gif">
										</c:if>
										<c:remove var="canDelete" scope="page"/>--%>
									</c:if>				
								</td>
								</form>
							</tr>		
						
						</c:if>
					<c:remove var="canEditPublication"/>
					</c:forEach>
					<tr>
						<td colspan="10" height="1"></td>
					</tr>	
					<tr>
						<td colspan="10" height="1" bgcolor="#bdbdbd"></td>
					</tr>
					<tr>
						<td colspan="10" height="1"></td>
					</tr>					
				</c:forEach>
			</table>
			<br>
			<span class="text" align="left">
				<c:if test="${start>0}">
					<a href="viewSection.x?id=<c:out value="${section.id}"/>&start=<c:out value="${start-pageSize}"/>" style="text-decoration:none">&lt;&lt;</a>
				</c:if>
				<b style="cursor:default; color:#444444">publications <c:out value="${start+1}"/> à <c:out value="${stop+1}"/> sur <c:out value="${publicationsSize}"/></b>
				<c:if test="${(stop+1)<publicationsSize}">
					<a href="viewSection.x?id=<c:out value="${section.id}"/>&start=<c:out value="${stop+1}"/>" style="text-decoration:none">&gt;&gt;</a>
				</c:if>
			</span>
		</c:when>
		<!-- il n'y a pas de publications à afficher -->
		<c:otherwise>
			<i style="color:#222222"><bean:message key="viewSection.noPublications"/></i>
		</c:otherwise>
	</c:choose>
</c:if>
	
</body>

</html>
