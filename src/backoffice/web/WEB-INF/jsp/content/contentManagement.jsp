<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<html>
<head>
	<title>Content Management</title>
</head>

<!-- Body -->
<frameset cols="220,*" frameborder="NO" border="0" framespacing="0">
  <frame name="tree" marginheight="0" marginwidth="0" src="setUpTree.x?select=site" scrolling="auto" style="border-right:1px solid #949494">
  <c:choose>
	  <c:when test="${treeControlTest.selected!=null}">
	  	<frame name="content" src="<c:out value="${treeControlTest.selected.action}"/>" scrolling="auto" marginheight="5" marginwidth="10">
	  </c:when>
	  <c:otherwise>
	  	<frame name="content" src="viewSection.x?id=site" scrolling="auto" marginheight="5" marginwidth="10">
	  </c:otherwise>
  </c:choose>
</frameset>

</html>
