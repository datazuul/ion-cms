<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ion="http://ion-cms.sourceforge.net/ion-ns">
	<xsl:output method="html"/>
	<xsl:param name="locale">fr</xsl:param>
	<xsl:template match="/publication">
		<table>
			<xsl:apply-templates select="node()[@xml:lang=$locale] | node()[not(@xml:lang)]"/>
		</table>
	</xsl:template>
	<!-- ============= texte =============== -->
	<xsl:template match="node()[@ion:type='text']">
		<tr>
			<td align="right" class="text">
				<b>
					<xsl:value-of select="@ion:label"/> : 
				</b>
			</td>
			<td>
				<input type="text" value="{.}" name="FIELD_{name()}" style="width=400"/>
			</td>
		</tr>
	</xsl:template>
	<!-- ============= texte long =============== -->
	<xsl:template match="node()[@ion:type='large-text']">
		<tr>
			<td align="right" class="text" valign="top">
				<b>
					<xsl:value-of select="@ion:label"/> : 
				</b>
			</td>
			<td>
				<textarea name="FIELD_{name()}" style="width=400;height=50">
					<xsl:value-of select="."/>
				</textarea>
			</td>
		</tr>
	</xsl:template>
	<!-- =============image =============== -->
	<xsl:template match="node()[@ion:type='image']">
		<tr>
			<td align="right" class="text">
				<b>
					<xsl:value-of select="@ion:label"/> : 
				</b>
			</td>
			<td>
				<input type="text" name="FIELD_{name()}" onkeypress="return false" value="{.}" class="text-field" style="width:200px"/>
				&#160;<a href="#popup" onclick="popup_{name()}.style.top=event.clientY+document.body.scrollTop-140;popup_{name()}.style.left=event.clientX-10;popup_{name()}.style.visibility='visible'">parcourir</a>
				<div id="popup_{name()}" style="position:absolute; z-index:99; width:150px; height:100px; border:1px solid black; background-color:#dddddd; visibility:hidden">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="text">
								<b>&#160;resources</b>
							</td>
							<td align="right">
								<a href="#close" onclick="popup_{name()}.style.visibility='hidden'" style="text-decoration:none">X&#160;</a>
							</td>
						</tr>
						<tr>
							<td height="1" bgcolor="black" colspan="2"/>
						</tr>
						<tr>
							<td colspan="2">
								<iframe src="listResource.x?id=images&amp;action=select_{name()}" frameborder="0" marginheight="3" marginwidth="3" width="150"/>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<script language="javascript"><![CDATA[
			function select_]]><xsl:value-of select="name()"/><![CDATA[(value) {
				document.forms[0].FIELD_]]><xsl:value-of select="name()"/><![CDATA[.value=value;
				popup_]]><xsl:value-of select="name()"/><![CDATA[.style.visibility='hidden';
			}
		]]></script>
	</xsl:template>
	<!-- ============= texte html=============== -->
	<xsl:template match="node()[@ion:type='formated-text']">
		<tr>
			<td align="right" class="text" valign="top">
				<b>
					<xsl:value-of select="@ion:label"/> : 
				</b>
			</td>
			<td>
				<textarea name="FIELD_{name()}" style="visibility : hidden; position:absolute; width:1; height:1">
					<xsl:value-of select="."/>
				</textarea>
				<table cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td rowspan="9">
							<iframe frameborder="0" id="iframe_{name()}" marginheight="1" marginwidth="1" width="400" height="200" onblur="document.forms[0].FIELD_{name()}.value=iframe_{name()}.document.all.tags('body')[0].innerHTML"/>
						</td>
						<td>
							<a href="#fastedit" title="mettre en gras" onClick="iframe_{name()}.document.execCommand('bold');iframe_{name()}.focus();">
								<img src="images/bold.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="mettre en italique" onClick="iframe_{name()}.document.execCommand('italic');iframe_{name()}.focus();">
								<img src="images/italic.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="souligner" onClick="iframe_{name()}.document.execCommand('underline');iframe_{name()}.focus();">
								<img src="images/underline.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="titre" onClick="iframe_{name()}.document.selection.createRange().pasteHTML('&lt;h5&gt;'+iframe_{name()}.document.selection.createRange().text+'&lt;/h5&gt;');">
								<img src="images/title.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="liste à puce" onClick="iframe_{name()}.document.execCommand('InsertUnorderedList');iframe_{name()}.focus();">
								<img src="images/puces.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="indentation" onClick="iframe_{name()}.document.execCommand('Indent');iframe_{name()}.focus();">
								<img src="images/indent.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="supprimer l'indentation" onClick="iframe_{name()}.document.execCommand('Outdent');iframe_{name()}.focus();">
								<img src="images/outdent.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="faire un lien" onClick="iframe_{name()}.document.execCommand('createLink');iframe_{name()}.focus();">
								<img src="images/link.gif" border="0"/>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="#fastedit" title="inserer une image" onclick="popup_{name()}.style.top=event.clientY+document.body.scrollTop-140;popup_{name()}.style.left=event.clientX-10;popup_{name()}.style.visibility='visible'">
								<img src="images/img.gif" border="0"/>
							</a>
							<div id="popup_{name()}" style="position:absolute; z-index:99; width:150px; height:100px; border:1px solid black; background-color:#dddddd; visibility:hidden">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="text">
											<b>&#160;resources</b>
										</td>
										<td align="right">
											<a href="#close" onclick="popup_{name()}.style.visibility='hidden'" style="text-decoration:none">X&#160;</a>
										</td>
									</tr>
									<tr>
										<td height="1" bgcolor="black" colspan="2"/>
									</tr>
									<tr>
										<td colspan="2">
											<iframe src="listResource.x?id=images&amp;action=insertImage_{name()}" frameborder="0" marginheight="3" marginwidth="3" width="150"/>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<script language="javascript"><![CDATA[
					// insert an image in the mini-word
					function insertImage_]]><xsl:value-of select="name()"/><![CDATA[(value) {
						iframe_]]><xsl:value-of select="name()"/><![CDATA[.document.execCommand('InsertImage',false,'/resources/images/'+value);
						iframe_]]><xsl:value-of select="name()"/><![CDATA[.focus();						
						popup_]]><xsl:value-of select="name()"/><![CDATA[.style.visibility='hidden';
					}
					iframe_]]><xsl:value-of select="name()"/><![CDATA[.document.designMode='on';
					iframe_]]><xsl:value-of select="name()"/><![CDATA[.document.write( "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"styles/publication-form.css\"></head><body class=\"text-field\">"+document.forms[0].FIELD_]]><xsl:value-of select="name()"/><![CDATA[.value+"</body></html>");
				]]></script>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="text()">
		<!-- hack -->
	</xsl:template>
</xsl:stylesheet>
