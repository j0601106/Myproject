<%
/*
@@程式代號 = docrm120m_v2.jsp
@@程式名稱 = 程式名稱-編輯頁
@@程式版本 = V1.000
@@更新日期 = 2015/12/01
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String PROGRAM_CD	= "DOCRM120M_";
String SCREEN_CD	= "DOCRM120M_02";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("editBlock", true, "新增", true);
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
	 processQueryDetail  : "../DOCRM120M_Controller.do?command=processQueryDetail"       
	,processAddSave    	 : "../DOCRM120M_Controller.do?command=processAddSave"		
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>

});

function processAdd(){
	processQueryDetail();
}

function processAddSave()
{	
	var dataObj = formUtil.formField2Json($('#listForm input[name=gridDeleteCheck]:checked'));
	dataObj = dataObj.concat({GROUP_ID:$('#GROUP_ID').val(), GROUP_NM:$('#GROUP_NM').val()});

	var options = {
		 dataObj : dataObj
		,url : urlAction.processAddSave
		,onAfterSuccess : function(jsonResult, status) {
			formUtil.clearFormData($('#editForm'));
			uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
		}
	};
	
	formUtil.submitTo(options);	
}

function processQueryDetail(){

	var options = {
			 formObj : $('#listForm')
			,url : urlAction.processQueryDetail
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,submitCheckFlag : true
			,dataPattern : {
				_checkbox:"<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{FUNCTION_ID}' />"
				}
			,columnMeta : [
                     { title:'', dataPatternKey:'_checkbox'}
					,{ title:'程式名稱', index:'FUNCTION_NM'}					
					,{ title:'程式代碼', index:'FUNCTION_ID'}
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
	<input type="button" name="btnAddSave"  class="btn" value="存　檔" onclick="processAddSave();">
</content>

<content tag="editBlock">
	<form id="editForm" name="editForm" method="POST">
		<table class="table-redmond" width="100%">
        	  <tr>
                <th>權限名稱</th>
                <td>
                	<input name="GROUP_NM" type="text">
                </td>
              </tr>
              <tr>
                <th>權限代碼</th>
                <td>
                	<input name="GROUP_ID" type="text">
                </td>
              </tr>
        </table>
	</form>
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div class='buttons' style='clear: both' align='left'>
			<input type="button" class="btn" value="全選" onclick="clickAll(true);">
			<input type="button" class="btn" value="全不選" onclick="clickAll(false);">
		</div>
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>
