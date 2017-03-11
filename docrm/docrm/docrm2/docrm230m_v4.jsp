<%
/*
@@程式代號 = docrm230m_v4.jsp
@@程式名稱 = 補件作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<head>
<meta name="decorator" content="template_1_1_main">

<%
String PROGRAM_CD	= "DOCRM230M_";
String SCREEN_CD	= "DOCRM230M_04";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>

<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.setBreadCrumb("補件作業(DOCRM230M_)", "一般", true);
//layout.addPortlet("queryBlock", true, "", false);
//layout.addPortlet("editBlock", true, "修改", true);
layout.addPortlet("listBlock", true, null, false);
Layout.saveLayout(pageContext, layout);
%>

<script type="text/javascript">
var _actionMode	= null;	//畫面目前的模式: Add, Edit
var _mainData	= null; //查詢暫存用
var _editData	= null; //編輯暫存用
var uiFrameset	= parent.uiFrameset; //frameset物件
var uiLayout	= new UiLayout();  //建立區塊切換物件
//--------以上必須存在------------

var urlAction = {
	processQuery        	: "../DOCRM230M_Controller.do?command=processQueryDocTp"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1());

	processQuery();
});

function processQuery()
{
	var options = {
			formObj : $('#listForm')
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,submitCheckFlag : true
			,dataPattern : {
				_radio:"<input type='radio' id='gridCheck' name='gridDeleteCheck' value='@{PHRASE_TITLE}' />"
				}
			,columnMeta : [
			         { title:'', dataPatternKey:'_radio'}
					,{ title:'文件名稱', index:'PHRASE_TITLE'}					
				]
		};
		uiProcess.processQuery(options);
}

function processCheck()
{
	if( $('#listForm input[name=gridDeleteCheck]:checked').length <= 0) {
		alert("未選擇補件文件");
		return;
	}
	
	var arrayValue = new Array();
	$('#listForm input[name=gridDeleteCheck]:checked').each(function(index) {arrayValue.push(this.value);});

	window.returnValue = arrayValue.toString();
	window.close();
}

function processBack()
{
	window.close();
}

</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnBack" class="btn" value="回上頁" onclick="processBack();">
	<input type="button" name="btnSelect" class="btn" value="確認" onclick="processCheck();">
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>

