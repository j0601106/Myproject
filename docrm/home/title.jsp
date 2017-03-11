<%
/*
@@程式代號 = title.jsp
@@程式名稱 = 標頭頁
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gov.fdc.framework.core.util.FdcThreadHolder"%>
<%@ page import="gov.fdc.framework.core.common.UserProfile"%>
<%@ page import="com.acer.util.DateUtil"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.acer.util.DateUtil"%>
<% 
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Pragma" content="no-cache"> 
	<meta http-equiv="Cache-Control" content="no-cache"> 
	<meta http-equiv="Expires" content="0"> 
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>高雄市稅捐稽徵處</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CommonUI.css" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fdc.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.js"></script>
<%
	String currentDate = DateUtil.doW2FDate(DateUtil.getSysWDate(), "");

	//--- 登入者資訊
	String orgInfo = "";
	String userInfo = "尚未登入";
	String agentInfo = "";
	
	UserProfile up = FdcThreadHolder.getUserProfile();
	if (up != null)
	{
		String userName = up.getUsernm() == null ? "" : up.getUsernm();
		String userId = up.getUserid() == null ? "" : up.getUserid();
		userInfo = userName + userId;
	
		orgInfo = up.getOrg() + " " + up.getSection() + " " + up.getDivision();
	
		//代理人
		String agentUserId = up.getCV01();
		if (agentUserId != null && !agentUserId.equals("")) {
			com.mitac.cas.parsing.agentParsing aps = new com.mitac.cas.parsing.agentParsing();
			aps.init(up.getAgentid());
			String agentUserName = aps.getuserNameByUserId(agentUserId);
	
			agentInfo = "，目前代理：" + agentUserName + "(" + agentUserId + ")";
		}
	//	agentUserId = "XX00009999";
	//	String agentUserName = "圈圈圈";
	//	agentInfo = "，目前代理：" + agentUserName + "(" + agentUserId + ")";
	}
	//--- 登入者資訊
	
	//--- 版本資訊	
	String fwInfo = "";
	String glInfo = "";
	String wsInfo = "";
	try {
		InputStream is = this.getClass().getResourceAsStream("/META-INF/_lts_framework_version.properties"); 
		if( is != null ) {
			Properties p = new Properties();
			p.load( is );
			Gson g = new Gson();
			fwInfo = g.toJson(p);
			is.close();
		}
	} catch(Throwable ignore) { 
		ignore.printStackTrace();
	}
	
	try {
		InputStream is = this.getClass().getResourceAsStream("/META-INF/_lts_ltsGlobal_version.properties"); 
		if( is != null ) {
			Properties p = new Properties();
			p.load( is );
			Gson g = new Gson();
			glInfo = g.toJson(p);
			is.close();
		}
	} catch(Throwable ignore) { 
		ignore.printStackTrace();
	}
	
	try {
		InputStream is = this.getClass().getResourceAsStream("/META-INF/_lts_webResource_version.properties"); 
		if( is != null ) {
			Properties p = new Properties();
			p.load( is );
			Gson g = new Gson();	
			wsInfo = g.toJson(p);
			is.close();
		}
	} catch(Throwable ignore) { 
		ignore.printStackTrace();
	}
	//--- 版本資訊
%>
<script type="text/javascript">
/*
	var fwInfo = '<%=fwInfo %>';
	var glInfo = '<%=glInfo %>';
	var wsInfo = '<%=wsInfo %>';
*/
//將打開的頁頭收闔
function uiCommonCloseHeader() {
	$("frameset#main", parent.document).attr("rows", "5,*");

	$("#headerBase").hide();
	$("#btnHeaderIn").hide();
	$("#btnHeaderOut").show();
}

//將收闔的頁頭打開
function uiCommonOpenHeader() {
	$("frameset#main", parent.document).attr("rows", "60,*");

	$("#headerBase").show();
	$("#btnHeaderIn").show();
	$("#btnHeaderOut").hide();
}

//由menu.jsp, click選項時賦予值
var PROGRAM_CD = '';

//由menu.jsp呼叫
function initPage(progCd)
{
	PROGRAM_CD = progCd;
}
</script>

<script type="text/javascript">
//---------- 三顆按鈕 --------------
function openKM()
{
	alert('開啟KM:' + PROGRAM_CD);
//	window.open(....);
}

function openVersion()
{
	var myWindow = null;
	var winWidth = 480;
	var winHeight = 300;
	var winLeft = (screen.width - winWidth) / 2;
	var winTop = (screen.height - winHeight) / 2;
	if (winLeft < 0) {
		winLeft = 0;
	}
	if (winTop < 0) {
		winTop = 0;
	}
	var param = 'width=' +winWidth + ',height=' + winHeight +
				',location=0,menubar=0,scrollbars=yes,resizable=yes,status=0,toolbar=0,top=' +
				winTop + ',left=' + winLeft;
	var url = 'http://<%=gov.fdc.library.env.ApEnv.get("tax.host") %>/ypm/ypm/YPMVersionController.do?command=processQuery&FUNC_CD=' + PROGRAM_CD;
	myWindow = window.open(url, 'YPMVersion', param);
	myWindow.focus();
}

function openHelp()
{
	alert('開啟Help:' + PROGRAM_CD);
//	window.open(....);
}

function processLogout()
{
	parent.location.href = "<%=request.getContextPath()%>/home/logout.jsp";
}
//---------- 三顆按鈕 --------------
</script>

<style>  
	body, div, table {  
		overflow: hidden;
		padding-top:0px;
		padding-bottom:0px;
		padding-right:0px;
		padding-left:0px;
		margin-top:0px;
		margin-bottom:0px;
		margin-right:0px;
		margin-left:0px;
    };
    </style>
</head>

<body>
	<div id="headerBase">
		<div id="headerBg"></div>
		
		<div class="userProfile">
			<ul>
				<li id="headerToday">民國<%=currentDate%></li>
				<li id="headerUserInfo">使用者：<%=userInfo%> <input type="button" name="btnLogout" class="btn" style="background-color: transparent; border: 0;　width:40px; height:20px;" value="登出" onclick="processLogout();"></li>
			</ul>
		</div>

		<div class="helpArea">
			<ul>

				<!-- 折行會讓圖示有間距 -->
<!-- 				<li style='text-align: right'><a href='#' onclick='javascript:openKM();'><img style='border: 0px;' -->
<!-- 						src='../resources/image/s_km.jpg'></a><a href='#' onclick='javascript:openVersion();'><img -->
<!-- 						style='border: 0px;' src='../resources/image/s_version.gif'></a><a href='#' onclick='javascript:openHelp();'><img -->
<!-- 						style='border: 0px;' src='../resources/image/s_help.gif'></a></li> -->
			</ul>
		</div>
	</div>

	<div id="btnHeaderOut" class="btnHeaderOut" onclick='uiCommonOpenHeader()' style="display:none;" ></div>
	<div id="btnHeaderIn" class="btnHeaderIn" onclick='uiCommonCloseHeader()'></div>
</body>
</html>
