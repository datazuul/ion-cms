<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<%
	org.nextime.ion.framework.mapping.Mapping.begin();
	org.nextime.ion.backoffice.messageBoard.Message mes = new org.nextime.ion.backoffice.messageBoard.Message();
	mes.setPoster( request.getSession().getAttribute("userLogin")+"" );
	mes.setDate( new java.util.Date() );
	mes.setMessage( request.getParameter("message") );
	org.nextime.ion.backoffice.messageBoard.MessageBoard.getInstance().addMessage(mes);
	org.nextime.ion.framework.mapping.Mapping.rollback();
%>

<c:redirect url="../home.x"/>
