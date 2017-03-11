<%
/*
@@程式代號 = template_frame_1_main.jsp
@@程式名稱 = 樣板iframe
@@程式版本 = V1.0
@@更新日期 = 2012/04/29
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<decorator:usePage id="thePage" />
<%
	out.clear();
%>
<%
	String frameSetLayout = thePage.getProperty("page.frameSetLayout") == null ? "" : thePage.getProperty("page.frameSetLayout");

	String frameSet = thePage.getProperty("page.frameSet") == null ? "[{frameName:'v1', frameSrc:'"+(request.getContextPath() + "/blank.jsp")+"'}]" : thePage.getProperty("page.frameSet");
	String frameSetDebugFlag = thePage.getProperty("page.frameSetDebugFlag") == null ? "false" : thePage.getProperty("page.frameSetDebugFlag");
	
	String hasFrameSetLayout = "false";
	if (!frameSetLayout.trim().equals("")) {
		hasFrameSetLayout = "true";
	}
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><decorator:title default="財稅資料中心" /></title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CommonUI.css" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fdc.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/stringUtil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiCommon.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiFrameset.js"></script>
	
	<script type="text/javascript">
		var uiFrameset = null;
		$(document).ready(function(){
			uiFrameset = new UiFrameset('fdcFrameSet', <%=frameSetDebugFlag%>);

			var hasFrameSetLayout = <%=hasFrameSetLayout%>;
			
			if (!hasFrameSetLayout)
			{
				$("#fdcFrameSet").html("");
				uiFrameset.addFrame(<%=frameSet%>);
			}
			else
			{
				
				var frameHtmlMap = uiFrameset.composeFrameHtml(<%=frameSet%>);
				framesetLayout = stringUtil.replacePattern($("#fdcFrameSet").html(), frameHtmlMap);
				$("#fdcFrameSet").html(framesetLayout);
			}
			
			uiFrameset.switchFrame('v1');
		});
		
		function getUiFrameset() {
			return uiFrameset;
		}
	</script>
</head>

<body>
<div id="fdcFrameSetPanel">
	<span id="fdcFrameSetDebugPanel"></span>
	<span id="fdcFrameSet"><%=frameSetLayout %></span>
</div>
</body>
</html>
