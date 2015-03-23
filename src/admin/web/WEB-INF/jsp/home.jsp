<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<template:insert template='template.jsp'>
  <template:put name='label' content='Home' direct='true'/>
  
  <template:put name='content' direct='true'> 
  	 <span class="gros-titre">
	 	ion core framework
	 </span>
	 <br><br>
Lorem ipsum dolor sit amet, con sectetuer adipiscing elit, sed diam nonnumy nibh eeuismod tempor inci dunt ut labore et dolore magna ali quam erat volupat. Ut wisi enim ad minim veniam. 
Lorem ipsum dolor sit amet, con sectetuer adipiscing elit, sed diam nonnumy nibh eeuismod tempor inci dunt ut labore et dolore magna ali quam erat volupat. Ut wisi enim ad minim veniam. 
<br><br>
Cette application permet d'administrer les données du framework. Ceci est particulierement utile pendant la phase de développement pour vérifier le bon fonctionnement d'une application.
<br><br><br>
	<table width="90%" cellpadding="0" cellspacing="0">
		<tr>
			<td width="50%" class="texte">
			<center><span class="titre2">Manipulez les objets métier</span></center><br>
			<a href="listUser.x"><img src="images/user-tile.gif" align="middle" border="0">Utilisateurs de l'application.</a><br>	
			<a href="listGroup.x"><img src="images/group-tile.gif" align="middle" border="0">Groupes d'utilisateurs.</a><br>	
			<a href="listPubli.x"><img src="images/publi-tile.gif" align="middle" border="0">Publications.</a><br>	
			<a href="listSection.x"><img src="images/section-tile.gif" align="middle" border="0">Sections.</a><br>
			<a href="listCategory.x"><img src="images/category-tile.gif" align="middle" border="0">Categories de publications.</a><br>
			<a href="listType.x"><img src="images/type-tile.gif" align="middle" border="0">Types de publications.</a><br>
			</td>
			<td width="1px" bgcolor="#272727"></td>
			<td class="texte" valign="top" background="images/fond-help.jpg">
			<center><span class="titre2">Consultez la documentation</span></center><br>
				<table width="90%" align="center" cellpadding="3" cellspacing="3">
					<tr>
						<td class="texte">							
							<a href="#"><img src="images/help-tile.gif" align="middle" border="0">Guide développeur WCM.</a><br>
							<a href="javadoc/index.html" target="_blank"><img src="images/help-tile.gif" align="middle" border="0">Javadoc WCM.</a><br>
							<a href="src/com/sqli/framework/wcm" target="_blank"><img src="images/help-tile.gif" align="middle" border="0">Sources WCM.</a><br>
							Cette application peut servir de base au développement d'un back-office plus complexe. N'hésitez pas à consulter les sources.
						</td>
					</tr>
				</table>			
			</td>
		</tr>
	</table>
<br><br>
<!--<img src="images/config-tile.gif" align="absmiddle">&nbsp;Avant une premiére utilisation vous devez <a href="config.x">configurer</a> cette application.
-->
  </template:put>
  
</template:insert>
