<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>

<%response.setContentType("text/xml");%>

<%=request.getAttribute("xml")%>
