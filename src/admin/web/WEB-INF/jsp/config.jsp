<%@ taglib uri='/WEB-INF/tlds/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>
<template:insert template='template.jsp'>
  <template:put name='label' content='Configuration' direct='true'/>
  
  <template:put name='content' direct='true'> 

  	<img src="images/config-tile.gif" align="middle" border="0"><span class="titre2">Configuration.</span><br><br>
  	
	<u>Fichier de configuration database.xml utilisé :</u> <b><bean:write name="pathConfig"/></b><br><br>
<!--
	<form action="changeDatabase.x" method="post">
		<textarea name="content" style="width=450px;height:100px"><bean:write name="content"/></textarea><br>
  		<b>valider </b><input align="absmiddle" type="image" src="images/valide-fade.gif" style="border:0px" onmouseover="this.src='images/valide.jpg'" onmouseout="this.src='images/valide-fade.gif'"/>
	</form>
-->

  </template:put>
  
</template:insert>
