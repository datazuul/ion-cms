<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<%-- merge form result --%>
<c:import url="/edx/getPublicationXml.jsp?id=${param['id']}&version=${param['version']}" var="old"/>
<c:import url="/edx/formMerger.xsl" var="xsl"/>

<%-- save current xml in session --%>
<c:set var="currentXml" scope="session">

	<x:transform xslt="${xsl}">	
		<nextimeSupaXml>		
			<c:out value="${param['data']}" escapeXml="false"/>
			<data>
				<%
					java.util.Enumeration names = request.getParameterNames();
					while( names.hasMoreElements() ) {
						String name = names.nextElement()+"";
						if( name.startsWith("FIELD_") ) {
							%><<%=name.substring(6)%>><![CDATA[<%=request.getParameter(name)%>]]></<%=name.substring(6)%>><%
						}
					}
				%>							
			</data>
		</nextimeSupaXml>
		<x:param name="locale" value="${param['currentLocale']}"/>
	</x:transform>
	
</c:set>

<%-- redirect --%>
<c:redirect url="../editPublication.x">
	<c:param name="id" value="${param['id']}"/>
	<c:param name="version" value="${param['version']}"/>
	<c:param name="targetLocale" value="${param['targetLocale']}"/>
</c:redirect>
