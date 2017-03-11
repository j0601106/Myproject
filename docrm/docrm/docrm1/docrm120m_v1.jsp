<%
/*
@@程式代號 = docrm120m_v1.jsp
@@程式名稱 = 權限設定
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
String PROGRAM_CD	= "DOCRM120M_";
String SCREEN_CD	= "DOCRM120M_01";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>

<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.setBreadCrumb("權限設定(DOCRM120M_)", "一般", true);
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
	 processMain         	: "../DOCRM120M_Controller.do?command=processMain"
	,processQuery        	: "../DOCRM120M_Controller.do?command=processQuery"
	,processDelete       	: "../DOCRM120M_Controller.do?command=processDelete"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "queryForm")%>

	processQuery();
});

function processMain(mode)
{
	var options = {
			url : urlAction.processMain
			,onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;

				formUtil.bindFormData({
					targetForm : $('#queryForm')
					,dataSet : jsonResult.data
					,dataSetOfHtml : jsonResult.data
				});
			}//end onAfterSuccess
		};

	if (mode == 'reset' && _mainData != null) {
		options.onAfterSuccess(_mainData);
	} else {
		formUtil.submitTo(options);
	}
}

function processQuery()
{
	var options = {
			formObj : $('#listForm')
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,submitCheckFlag : true
			,dataPattern : {
				 _checkbox:"<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{GROUP_ID}' />"
				}
			,columnMeta : [
			         { title:'', dataPatternKey:'_checkbox'}
					,{ title:'編輯', dataPatternKey:'_edit'}
					,{ title:'權限名稱', index:'GROUP_NM'}					
					,{ title:'權限代碼', index:'GROUP_ID'}
					,{ title:'建立時間', index:'CREATE_DATE', format:'dYYY/MM/dd'}
					,{ title:'異動時間', index:'UPDATE_DATE', format:'dYYY/MM/dd'}
				]
		};
		uiProcess.processQuery(options);
}

function processAdd()
{
	uiFrameset.switchFrameWithExecute('v2', 'processAdd');
}

function processEdit(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v3', 'processEdit', jsonResult);
}

function processDelete()
{
	uiProcess.processDelete($('#listForm input[name=gridDeleteCheck]:checked'));
}

function clickAll(str){
	$("input[name='gridDeleteCheck']").each(function() {
    	$(this).attr("checked", str);
	});
}

</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnAdd" class="btn" value="新　增" onclick="processAdd();">
</content>

<content tag="queryBlock">
	<form id="queryForm" name="queryForm" method="POST">
		<input type="hidden" name="__ORDER_FIELD" value="PKNO">
		<input type="hidden" name="__ORDER_TYPE" value="DESC">
	</form>
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div class='buttons' style='clear: both' align='left'>
			<input type="button" class="btn" value="全選" onclick="clickAll(true);">
			<input type="button" class="btn" value="全不選" onclick="clickAll(false);">
			<input type="button" name="btnDelete" class="btn" value="刪　除" onclick="processDelete();">
		</div>
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>

