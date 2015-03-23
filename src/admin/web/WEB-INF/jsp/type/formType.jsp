<%@ taglib uri='/WEB-INF/tlds/struts-bean.tld' prefix='bean' %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Publication XML</title>
	<style>
		.titre {
			font-family:verdana;
			color:#5d5d5d;
			font-size:18px;
			font-weight:bold;			
		}
		.titre-liste {
			font-family:verdana;
			color:#0148B2;
			font-size:10px;
			font-weight:bold;			
		}
		.titre2 {
			font-family:verdana;
			color:black;
			font-size:11px;		
			font-weight:bold;	
		}			
		.gros-titre {
			font-family:verdana;
			color:#272727;
			font-size:28px;
			font-weight:bold;			
		}
		.texte {
			font-family:verdana;
			color:black;
			font-size:9px;
		}
		.error {
			font-family:verdana;
			color:#cc0000;
			font-size:9px;
			font-weight:bold;
		}
		input {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;			
			background-color:white;
			border:1px solid black;
		}
		textarea {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;			
			background-color:white;
			border:1px solid black;
			width=200px;
		}
		td {
			font-family:verdana;
			color:black;
			font-size:9px;
		}
		select {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;
			border:1px solid black;
			background-color:white;
			width=200px;
		}			
		a {
			font-family:verdana;
			color:black;
			font-size:9px;
			font-weight:bold;
			text-decoration:underline;
		}
		a:hover {
			font-family:verdana;
			color:#717171;
			font-size:9px;
			font-weight:bold;
			text-decoration:underline;
		}			
</style>
</head>

<body background="images/fond-centre.gif">
<bean:parameter id="id" name="id"/>
<span class="titre">Formulaire du type "<bean:write name="id"/>"</span>
<br><br>
<table width="100%">
	<tr>
		<td width="20"></td>
		<td class="texte" align="left" valign="top">
			<pre class="texte"><bean:write name="form" filter="false"/></pre>
		</td>
	</tr>
</table>


</body>
</html>
