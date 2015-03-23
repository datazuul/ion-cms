<%@ taglib uri="/WEB-INF/tlds/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/ion.tld" prefix="ion" %>


<form action="commentaire.x" method="POST">
	<span class="title-list">entrez votre commentaire ci-dessous</span>	
	<hr width="100%" size="1" noshade color="#949494">
	<input type="hidden" name="retour" value="<c:out value="${param['retour']}"/>">
	<input type="hidden" name="action" value="<c:out value="${param['action']}"/>">
	<input type="hidden" name="version" value="<c:out value="${param['version']}"/>">
	<input type="hidden" name="id" value="<c:out value="${param['id']}"/>">
	<textarea cols="80" rows="5" name="commentaire"></textarea>		
	<br><img src="images/blank.gif" height="3"><br> 
	<input type="submit" class="button" value="valider">	
</form>		
	
