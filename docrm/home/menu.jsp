<%
/*
@@程式代號 = menu.jsp
@@程式名稱 = 選單頁
@@程式版本 = V1.000
@@更新日期 = 2012/05/21
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../global/util/commonFunction.jsp"%>

<%

//第一次進入時, 導向LTSMenuController
if (responseBean == null)
{
	pageContext.forward("../global/LTSMenuController.do?command=processQueryMenu");
	return;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CommonUI.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.js"></script>

<!-- dtree -->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/dtree/dtree.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dtree/dtree.js"></script>

<%
if (responseBean.getStatus() == ResponseBean.Status.ERROR)
{
	//TODO 這裡要改由共用元件來處理
	out.println("錯誤發生：" + responseBean.getErrorStack()[0]);
	return;
}

Map data = (Map)responseBean.getData();
StringBuffer treeHtml = new StringBuffer();

{
	//第一層: 系統別
	List list = (List)((Map)data.get("tree")).get("treedata_menu1");
	for (int n=0; n<list.size(); n++)
	{
		Map map = (Map)list.get(n);
		String SYS_CD = (String)map.get("SYS_CD");
		String SYS_NM = (String)map.get("SYS_NM");
		treeHtml.append("d.add('" + SYS_CD + "', '-1', '" + SYS_CD + SYS_NM + "', '');\r\n");
	}
}

{
	//第二層: 功能
	List list = (List)((Map)data.get("tree")).get("treedata_menu2");
	for (int n=0; n<list.size(); n++)
	{
		Map map = (Map)list.get(n);
		String SYS_CD = (String)map.get("SYS_CD");
		String LEVEL_CODE = (String)map.get("LEVEL_CODE");
		String LEVEL_NM = (String)map.get("LEVEL_NM");
		treeHtml.append("d.add('" + SYS_CD + LEVEL_CODE + "', '" + SYS_CD + "', '" + LEVEL_CODE + "-" + LEVEL_NM + "');\r\n");
	}
}

{
	//第三層: 程式
	List list = (List)((Map)data.get("tree")).get("treedata_menu3");
	for (int n=0; n<list.size(); n++)
	{
		Map map = (Map)list.get(n);
		String SYS_CD = (String)map.get("SYS_CD");
		String LEVEL_CODE = (String)map.get("LEVEL_CODE");
		String LABEL_CD = (String)map.get("LABEL_CD");
		String PRG_CD = (String)map.get("PRG_CD");
		String PRG_NM = (String)map.get("PRG_NM");
		String PRG_URL = "../" + SYS_CD.toLowerCase() + "/" + SYS_CD.toLowerCase() + LEVEL_CODE.toLowerCase() + "/" + PRG_CD.toLowerCase() + "_.jsp";
		PRG_URL = "javascript:openURL('" + PRG_CD + "','" + PRG_URL + "');";
		treeHtml.append("d.add('" + PRG_CD + "', '" + SYS_CD + LEVEL_CODE + "', '" + "["+LABEL_CD+"] " + PRG_NM + "', \"" + PRG_URL + "\");\r\n");
	}
}

System.err.println(treeHtml);
%>
<script type="text/javascript">
function openURL(nodeId, appPath)
{
	//top.titleFrame.initPage(nodeId);
	top.mainFrame.location.href = appPath;

	//點選每個Function時, 關閉其他folder, 僅開啟被選取function所位處的Folder
//	d.closeAll();
//	d.openTo(nodeId, true);
}


//產生選單樹
var d = null;
function createMenu()
{
	d = new dTree('d', '<%=request.getContextPath()%>/resources/js/dtree/');
//	d.config.target = "mainFrame";

	<%=treeHtml.toString()%>

//	d.add(1, -1, '登出', './logout.jsp');
//	d.add(2, -1, '偵錯用', './menu.jsp?_DEBUG=y', '', 'menuFrame');
    document.write(d);
}
</script>

<script type="text/javascript">
//關閉選單
function uiCommonCloseMenu() {
	$("frameset#menu", parent.document).attr("cols", "30,*");
	$("body").css("background", "#FFFFFF");

	$("#menuBaseFixed").hide();
	$("#btnSlideIn").hide();
	$("#btnSlideOut").show();
}

//開啟選單
function uiCommonOpenMenu() {
	$("frameset#menu", parent.document).attr("cols", "240,*");
	$("body").css("background", "#E9E9E9");

	$("#menuBaseFixed").show();
	$("#btnSlideIn").show();
	$("#btnSlideOut").hide();
}

function uiCommonCloseMenu2() {
	var evt = window.event;
	//shiftPressed= evt.shiftKey;
	//altPressed  = evt.altKey;
	ctrlPressed = evt.ctrlKey;
	
	if (ctrlPressed == true)
		window.location.replace("./menu.jsp?_DEBUG=y");
	else
		uiCommonCloseMenu();
}
</script>
<style>
/*override js/dtree/dtree.cs */
.dtree {
	font-family: Times New Roman;
	font-size: 15px;
}
</style>
</head>

<body style='margin: 0px; background:#E9E9E9;'>
	<div id="btnSlideIn" class="btnSlideIn" onmousedown='uiCommonCloseMenu2();' >
		<img src='../resources/css/images/btn_slide_in.gif'> 功 能 選 單
	</div>

	<div id="menuBaseFixed">
		<script type='text/javascript'>
			createMenu();
		</script>
	</div>
	<div id="btnSlideOut" class="btnSlideOut" onclick='javascript:uiCommonOpenMenu();' style="display: none;">
		功<br />能<br />選<br />單
	</div>
</body>
</html>

