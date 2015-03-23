<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>

<c:import url="/edx/getPublicationXml.jsp?id=${param['id']}&version=${param['version']}" var="old"/>
<c:import url="/edx/formMerger.xsl" var="xsl"/>

<html>
	<body>
		<html:form action="editPublication.x">		
			
			<textarea cols="70" rows="15" name="data" style="visibility:hidden; position:absolute">
				<x:transform xslt="${xsl}">
					<nextimeSupaXml>
						<c:out value="${param['data']}" escapeXml="false"/>
						<data>
							<%
								java.util.Enumeration names = request.getParameterNames();
								while( names.hasMoreElements() ) {
									String name = names.nextElement()+"";
									String value = request.getParameter(name);
									
									// hack for € symbol
									value = value.replaceAll( "€", "&amp;euro;" );									
									
									if( name.startsWith("FIELD_") ) {
										%><<%=name.substring(6)%>><![CDATA[<%=value%>]]></<%=name.substring(6)%>><%
									}
								}
							%>							
						</data>
					</nextimeSupaXml>
					<x:param name="locale" value="${param['currentLocale']}"/>
				</x:transform>
			</textarea>
			
			<input type="Hidden" name="id" value="<c:out value="${param['id']}"/>">
			<input type="Hidden" name="version" value="<c:out value="${param['version']}"/>">
			<input type="Hidden" name="retour" value="<c:out value="${param['retour']}"/>">
			<input type="Hidden" name="itsOk" value="itsOk">
			<input type="hidden" name="date" size="12" value="<c:out value="${param['date']}"/>">
			
		</html:form>
	</body>
</html>

<script>
	document.forms(0).submit();
</script>
