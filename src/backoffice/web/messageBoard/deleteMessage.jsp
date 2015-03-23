<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<%
	org.nextime.ion.backoffice.messageBoard.Message mes = (org.nextime.ion.backoffice.messageBoard.Message)org.nextime.ion.backoffice.messageBoard.MessageBoard.getInstance().getMessages().get( Integer.parseInt(request.getParameter("nb")+"") );
	org.nextime.ion.backoffice.messageBoard.MessageBoard.getInstance().removeMessage(mes);
%>

<c:redirect url="../home.x"/>
