<%
/*
@@程式代號 = docrm230m_v2.jsp
@@程式名稱 = 補件作業
@@程式版本 = V1.000
@@更新日期 = 2015/12/01
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String PROGRAM_CD	= "DOCRM230M_";
String SCREEN_CD	= "DOCRM230M_02";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
//layout.addPortlet("editBlock", true, "新增", true);
layout.addPortlet("listBlock", true, null, false);
Layout.saveLayout(pageContext, layout);
%>

<head>
<meta name="decorator" content="template_1_2_sub">

<%@ include file="../util/pageinit.jsp"%>

<script type="text/javascript">
var _actionMode	= null;	//畫面目前的模式: Add, Edit
var _mainData	= null; //查詢暫存用
var _editData	= null; //編輯暫存用
var uiFrameset	= parent.uiFrameset; //frameset物件
var uiLayout	= new UiLayout();  //建立區塊切換物件
//--------以上必須存在------------

var urlAction = {
	 processQueryN           : "../DOCRM230M_Controller.do?command=processQueryN"    
	,processEditSaveN        : "../DOCRM230M_Controller.do?command=processEditSaveN"  
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate1());
});

function processAdd(){
	processQueryN();
}

function processEditSaveN()
{	
	if( $('#listForm input[name=gridDeleteCheck]:checked').length <= 0) {
		alert("請先選取案件");
		return;
	}
	
	var options = {
		 formObj : $('#listForm')
		,url : urlAction.processEditSaveN
		,onAfterSuccess : function(jsonResult, status) {
			uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
		}
	};
	
	formUtil.submitTo(options);	
}

function processQueryN(){

	var options = {
			 formObj : $('#listForm')
			,url : urlAction.processQueryN
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,submitCheckFlag : true
			,dataPattern : {

				}
			,columnMeta : [
                     { title:'', dataPatternKey:'_checkbox'}
					,{ title:'案件編號', index:'DOCRM_NO'}					
					,{ title:'稅目別', index:'TAX_TP_NM'}
					,{ title:'案件種類', index:'DOCRM_TP_NM'}
					,{ title:'申請人', index:'APPLY_NM'}
					,{ title:'申請日期', index:'APPLY_DATE'}
					,{ title:'進度', index:'DOCRM_STUS_NM'}
					,{ title:'辦理機關', index:'DOC_ORG_ID_NM'}
					,{ title:'承辦人', index:'DOC_STF_NM'}
				]
			,onAfterSuccess : function(jsonResult, status) {
				$('#tableGridPaging').html("<table  class='table-redmond' style='width:100%'><th>共"+jsonResult.data.GRID_RESULT.length+"筆</th></table>");
			}//end onAfterSuccess	
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processBack()
{
	uiFrameset.switchFrame('v1');
}

function clickAll(str){
	$("input[name='gridDeleteCheck']").each(function() {
    	$(this).attr("checked", str);
	});
}

</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnBack" class="btn" value="回上頁" onclick="processBack();">
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div class='buttons' style='clear: both' align='left'>
			<input type="button" class="btn" value="全選" onclick="clickAll(true);">
			<input type="button" class="btn" value="全不選" onclick="clickAll(false);">
			<input type="button" name="btnAddSave"  class="btn" value="存　檔" onclick="processEditSaveN();">
		</div>
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>
