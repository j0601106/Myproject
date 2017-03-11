<%
/*
@@程式代號 = template_1_2_sub.jsp
@@程式名稱 = 樣板1-2
@@程式版本 = V1.0
@@更新日期 = 2012/04/29
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page import="java.util.Map"%>
<%@ page import="lts.global.core.dao.support.DataSourceContextHolder"%>
<%@ page import="gov.fdc.library.webui.*"%>
<%@ page import="lts.global.core.util.ProgramUtil"%>
<%@ page import="com.acer.util2.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<decorator:usePage id="thePage" />
<%
	out.clear();
%>
<%
	/** 功能名稱 / 機密等級 */
	String program_cd = StringUtil.ifNull((String) request.getAttribute("PROGRAM_CD"), "_");
	String screen_cd = StringUtil.ifNull((String) request.getAttribute("SCREEN_CD"), "_");
	String schemaName = DataSourceContextHolder.getTargetDataSource();	
	Map<String, String> program = ProgramUtil.getProgInfo(schemaName, program_cd, screen_cd);
	/** 功能名稱 / 機密等級 */

	String programInfo = program.get("PROGRAM_NM") + "(" + program_cd + "/" + screen_cd + ")";
//	String ipInfo = request.getLocalAddr()  + "\r\n, " + request.getLocalName();
	String ip = request.getLocalAddr();
	String[] ips = (ip == null) ? new String[] {"none"} : ip.split("\\.");
	String ipInfo = "[" + ips[ips.length-1] + "]";

	//預設PageLayout
	Layout defaultLayout = Layout.createLayout(Layout.LayoutBaseType.DefaultBased);
	defaultLayout.setBreadCrumb("<span title='" + ipInfo + "'>" + programInfo + "</span>", program.get("CLASSIFIED"), true);
	defaultLayout.addPortlet("editBlock", true, StringUtil.ifNull(thePage.getProperty("page.editBlockTitle"), "編輯"), true);

	//decide layout
	Layout userLayout = Layout.loadLayout(pageContext);
	Layout thisLayout = Layout.decide(userLayout, defaultLayout);

	String pageHtml = thisLayout.renderWebpage(thePage);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title><decorator:title default="財稅資料中心" /></title>
	<%@ include file="../../global/util/head.jsp"%>
	
	<!-- template dependency -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/decorators/template_share.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/decorators/template_1_2_sub.js"></script>
	
	<decorator:head />
</head>

<body>
	<div id="debug"></div>
	<div id="messageDialogPanel" style="background-color:#FFFFFF"></div>

	<%=pageHtml %>

	<%=thisLayout.renderHiddenBlock(thePage.getProperty("page.hiddenBlock")) %>

	<div id='hiddenFrame' class='blank' style="clear: both; display: none" align='center'>
		<iframe id="iframeTarget" name="iframeTarget"></iframe>
	</div>
</body>

</html>
