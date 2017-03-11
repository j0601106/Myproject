<%
/*
@@程式代號 = ltsaddress_v1.jsp
@@程式名稱 = 標準化地址-街路開窗頁
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%
String PROGRAM_CD	= "LTSADDRESS_";
String SCREEN_CD	= "LTSADDRESS_01";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);

RequestBean requestBean = RequestBean.buildRequestBean(request);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.setBreadCrumb(null, null, false);
layout.addPortlet("listBlock", true, null, false);
Layout.saveLayout(pageContext, layout);
%>

<head>
<meta name="decorator" content="template_1_1_main">

<script type="text/javascript">
var _actionMode	= null;	//畫面目前的模式: Add, Edit
var _mainData	= null; //查詢暫存用
var _editData	= null; //編輯暫存用
var uiFrameset	= parent.uiFrameset; //frameset物件
var uiLayout	= new UiLayout();  //建立區塊切換物件
//--------以上必須存在------------

var urlAction = {
	processQuery        : "../../global/LTSAddressController.do?command=processHOUC010&<%=requestBean.getParameter("_QS")%>"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1());
	
	processQuery();
});

function processMain(mode)
{
}

function processQuery()
{
	var options = {
			formObj : $('#queryForm')
			,gridObj : $('#tableGrid')
//			,pagingObj : $('#tableGridPaging')
			,dataPattern : {
				gridChoose1 : "<input type='button' class='btn' value='帶回' onclick=\"gridChoose1('@{ROAD_NM}')\">"
			}
			,columnMeta : [
				{ title:'帶回', dataPatternKey:'gridChoose1'}
				,{ title:'街路', index:'ROAD_NM'}
			]
			
		};
	uiProcess.processQuery(options);
}

function gridChoose1(stringValue)
{
	uiCustomWindow.callReturn(stringValue);
}

</script>
</head>

<content tag="topBlock">
</content>

<content tag="hiddenBlock">
	<form id="queryForm" name="queryForm" method="POST">

	</form>
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div id="tableGrid"></div>
	</form>
</content>

