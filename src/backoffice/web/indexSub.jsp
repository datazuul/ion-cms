<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<!-- cas :                                                 -->
<!-- 1.l'utilisateur vient juste de se logger, alors         -->
<!--   on recharge tous les cadres pour rafraichir           -->
<!--   l'affichage.                                          -->
<!-- 2.l'utilisateur n'est pas loggé, alors on redirige      -->
<!--   vers la page de login.                                -->
<!-- 3.l'utilisateur est loggé, alors on redirige vers       -->
<!--   la premiére rubrique du menu.                         -->
<c:choose>
	<!-- cas 1 -->
	<c:when test="${reload!=null}">
		<script language="JavaScript">
			// recharge tout le cadre
			parent.document.location.reload();
		</script>
	</c:when>
	<!-- cas 2 -->
	<c:when test="${userLogin==null}">		
		<c:redirect url="login.x"/>
	</c:when>
	<!-- cas 3 -->
	<c:otherwise>
		<!-- recherche la page à afficher par defaut dans le menu.xml -->
		<c:import url="/xml/menu.xml" var="xml"/>
		<x:parse xml="${xml}" var="doc"/>			
		<x:set var="defaultUrl" select="string($doc/menu-bar/menu[1]/menu-item[1]/@link)"/>
		<c:redirect url="${defaultUrl}"/>
	</c:otherwise>
</c:choose>
