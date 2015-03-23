<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<%@ taglib uri='/WEB-INF/tlds/struts-html.tld' prefix='html' %>

<template:insert template='../template.jsp'>
  <template:put name='label' content='Sections' direct='true'/>
  <template:put name='content' direct='true'> 
 
  	<img src="images/section-tile.gif" align="middle" border="0"><span class="titre2">Liste des objets sections.</span><br><br>
	
	<table cellpadding="0" cellspacing="2" width="100%">
	 	<tr>
			<td width="20"></td>
			<td class="titre-liste">&nbsp;&nbsp;<b>id</b></td>			
			<td class="titre-liste" width="50">&nbsp;&nbsp;<b>ordre</b></td>	
			<td width="20"></td>			
		</tr>
		<tr>
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>			
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>			
		</tr>
	 <logic:iterate id="section" name="sections" scope="request">
		<tr>
			<td align="center"><a href="editSection.x?id=<bean:write name="section" property="id"/>"><img src="images/puce.gif" align="middle" border="0"></a></td>
			<td class="texte">&nbsp;&nbsp;
				<% for( int i=0; i<((org.nextime.ion.framework.business.Section)section).getLevel(); i++ ) { %>
					<% if (i==((org.nextime.ion.framework.business.Section)section).getLevel()-1 ) { %>
						<img src="images/lp.gif" align="absmiddle" border="0">
					<% } else { %>
						<img src="images/lpb.gif" align="absmiddle" border="0">
					<% } %>			
				<% } %>
				<a href="editSection.x?id=<bean:write name="section" property="id"/>">
					<bean:write name="section" property="id"/>
				</a>&nbsp;&nbsp;</td>			
			<td class="texte">&nbsp;&nbsp;
				<!--<bean:write name="section" property="index"/>--><a href="moveSection.x?id=<bean:write name="section" property="id"/>&type=down"><img src="images/up.gif" border="0" align="absmiddle"></a><a href="moveSection.x?id=<bean:write name="section" property="id"/>&type=up"><img src="images/down.gif" border="0" align="absmiddle"></a>
				&nbsp;&nbsp;
			</td>			
			<td align="center"><a href="delSection.x?id=<bean:write name="section" property="id"/>"><img src="images/croix-fade.gif" onmouseover="this.src='images/croix.gif'" onmouseout="this.src='images/croix-fade.gif'" align="middle" border="0"></a></td>		
		</tr>
		<tr>			
			<td height="1" bgcolor="#949494"></td>			<td height="1" bgcolor="#949494"></td>
			
			<td height="1" bgcolor="#949494"></td>
			<td height="1" bgcolor="#949494"></td>				
		</tr>
	 </logic:iterate>
	 </table>
	 <br><br>
	 <img src="images/etoile.gif" align="absmiddle">&nbsp;&nbsp;<a href="addSection.x">créer un nouvel objet</a>.	 
	 <br><br><br>
	 <span style="color:#4c4c4c;font-size:11px"><b>Site Map :</b></span><br><br>
	 <img src="images/clef.gif" align="middle">&nbsp;<a onclick="window.open('siteMap.x?xml','','width=400,height=450,scrollbars=yes,resizable=yes')" href="#null">voir la carte du site en XML</a>
	 <br><img src="images/clef.gif" align="middle">&nbsp;<a onclick="window.open('siteMap.x?html','','width=400,height=450,scrollbars=yes,resizable=yes')" href="#null">voir la carte du site en HTML</a>
			
	  
  </template:put>
</template:insert>
