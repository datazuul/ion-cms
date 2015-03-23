<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="html"/>
	<xsl:template match="backoffice-menu">
		<xsl:apply-templates select="menu-bar"/>
	</xsl:template>
	<xsl:template match="menu-bar">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="haut" valign="bottom">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="15">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td width="10" height="15" class="no-tab">&#160;</td>
										<xsl:for-each select="menu">
											<xsl:choose>
												<xsl:when test="@tab=/backoffice-menu/@selectedTab or ('NULL'=/backoffice-menu/@selectedTab and position()=1)">
													<td width="50" class="tab-opened" align="center" onclick="">
														<nobr>&#160;&#160;<a href="{menu-item[1]/@link}" target="sub" onclick="document.location='menu.x?tab={@tab}'" class="tab-link">
																<xsl:value-of select="@label"/>
															</a>&#160;&#160;</nobr>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td width="50" class="tab-closed" align="center" onclick="">
														<nobr>&#160;&#160;<a href="{menu-item[1]/@link}" target="sub" onclick="document.location='menu.x?tab={@tab}'" class="tab-link">
																<xsl:value-of select="@label"/>
															</a>&#160;&#160;</nobr>
													</td>
												</xsl:otherwise>
											</xsl:choose>
											<xsl:choose>
												<xsl:when test="not(position()=last())">
													<td width="10" height="15" class="no-tab">&#160;</td>
												</xsl:when>
												<xsl:when test="position()=last()">
													<td class="no-tab">&#160;</td>
												</xsl:when>
											</xsl:choose>
										</xsl:for-each>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td class="haut" height="45" align="right" valign="middle">
					<!--<span class="logo">ÏON&#160;</span>-->
					<img src="images/logo.gif" alt=""/>
				</td>
			</tr>
			<tr>
				<td height="20" class="long-tab">				
				&#160;&#160;&#160;&#160;&#160;
				<xsl:variable name="myVar" select="/backoffice-menu/@selectedTab"/>
					<xsl:choose>
						<xsl:when test="not($myVar='NULL')">
							<xsl:for-each select="//menu[@tab=$myVar]/menu-item">
								<a href="{@link}" target="sub" class="tab-link">
									<xsl:value-of select="@label"/>
								</a>&#160;&#160;
								<xsl:if test="not(position()=last())">
									<span style="color:black; font-weight:normal">|</span>&#160;&#160;
								</xsl:if>
							</xsl:for-each>
						</xsl:when>
						<xsl:otherwise>
							<xsl:for-each select="//menu[@tab=//menu[1]/@tab]/menu-item">
								<a href="{@link}" target="sub" class="tab-link">
									<xsl:value-of select="@label"/>
								</a>&#160;&#160;
								<xsl:if test="not(position()=last())">
									<span style="color:black; font-weight:normal">|</span>&#160;&#160;
								</xsl:if>
							</xsl:for-each>
						</xsl:otherwise>
					</xsl:choose>
				</td>
				<td height="26" class="long-tab" align="right">
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="center">
								<a href="logout.x" target="sub" onclick="document.location='menu.x?tab=DECONNECT'" class="tab-link">
									<xsl:if test="not(/backoffice-menu/@user='')"> 
										déconnexion (<xsl:value-of select="/backoffice-menu/@user"/>)<br/><img src="images/disconnect.gif" border="0"/>
									</xsl:if>
								</a>
							</td>
							<td width="17">&#160;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="2" bgcolor="black" colspan="2"/>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
