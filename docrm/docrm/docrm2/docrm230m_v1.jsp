<%
/*
@@程式代號 = docrm230m_v1.jsp
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
String SCREEN_CD	= "DOCRM230M_01";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>

<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.setBreadCrumb("補件作業(DOCRM230M_)", "一般", true);
layout.addPortlet("queryBlock", true, "", false);
//layout.addPortlet("editBlock", true, "", true);
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
	 processMain         	: "../DOCRM230M_Controller.do?command=processMain"
	,processQuery        	: "../DOCRM230M_Controller.do?command=processQuery"
	,processEditSaveN2B     : "../DOCRM230M_Controller.do?command=processEditSaveN2B"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "queryForm")%>

	processMain();
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
					,elementOfHtml : ['TAX_TP', 'DOCRM_TP', 'ADD_STUS']
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
			formObj : $('#queryForm')
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,dataPattern : {
				 _checkbox:"<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{DOCRM_NO},@{MESS_NO}' />"
				,_edit:"<div align='center'><span class='btn-edit' onclick=\"processEdit({DOCRM_NO:'@{DOCRM_NO}'})\"></span></div>"
				}
			,columnMeta : [
                     { title:'', dataPatternKey:'_checkbox'}
			        ,{ title:'編輯', dataPatternKey:'_edit'}
					,{ title:'稅目別', index:'TAX_TP_NM'}	
					,{ title:'案件編號', index:'DOCRM_NO'}
					,{ title:'案件種類', index:'DOCRM_TP_NM'}
					,{ title:'申請人', index:'APPLY_NM'}
					,{ title:'申請日期', index:'APPLY_DATE'}
					,{ title:'承辦人', index:'DOC_STF_NM'}
					,{ title:'補件完成日期', index:'ADD_CPL_DATE'}
					,{ title:'案件進度', index:'DOCRM_STUS_NM'}
					,{ title:'補件狀態', index:'ADD_STUS_NM'}
				]
		};
		uiProcess.processQuery(options);
}

function processEditSaveN2B()
{	
	if( $('#listForm input[name=gridDeleteCheck]:checked').length <= 0) {
		alert("請先選取案件");
		return;
	}
	
	var options = {
		 formObj : $('#listForm')
		,url : urlAction.processEditSaveN2B
		,onAfterSuccess : function(jsonResult, status) {
			uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
		}
	};
	
	formUtil.submitTo(options);	
}

function processAdd(){
	uiFrameset.switchFrameWithExecute('v2', 'processAdd', "");
}

function processEdit(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v3', 'processEdit', jsonResult);
}

</script>
</head>

<content tag="topBlock">
		<input type="button" name="btnQuery" class="btn" value="查　詢" onclick="processQuery();">
		<input type="button" name="btnAdd" class="btn" value="新　增" onclick="processAdd();">
		<input type="button" name="btnEditSave"  class="btn" value="補件完成" onclick="processEditSaveN2B();">
</content>

<content tag="queryBlock">
	<form id="queryForm" name="queryForm" method="POST">

		<table class="table-redmond" width="100%">		
              <tr>
                <th>稅目別</th>
                <td>
                	<select name="TAX_TP"></select>
                </td>
			    <th>案件種類</th>
                <td>
                	<select name="DOCRM_TP"></select>
                </td>
              </tr>   
              <tr>
                <th>案件編號</th>
                <td>
                	<input name="APPLY_NM" type="text" size="10">
                </td>
			    <th>補件狀況</th>
                <td>
                	<select name="ADD_STUS"></select>
                </td>
              </tr>                  
        </table>
	</form>
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>

