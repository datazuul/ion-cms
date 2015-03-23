<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml" encoding="ISO-8859-1"/>
	<xsl:param name="lang">fr</xsl:param>
	<xsl:template match="*[@*[local-name()='lang']]">
		<xsl:if test="lang($lang)">
			<xsl:copy>
				<xsl:apply-templates select="node() | @*"/>
			</xsl:copy>
		</xsl:if>
	</xsl:template>
	<xsl:template match="/">
		<xsl:apply-templates select="@* | * | text()"/>
	</xsl:template>
	<xsl:template match="*">
		<xsl:copy>
			<xsl:apply-templates select="@* | * | text()"/>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="@*">
		<xsl:copy /> 
	</xsl:template>
	<xsl:template match="text()">
		<xsl:copy /> 
	</xsl:template>
</xsl:stylesheet>
