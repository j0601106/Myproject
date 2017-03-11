<%
/*
@@程式代號 = docrm200m_v1.jsp
@@程式名稱 = 列印回執聯作業
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
String PROGRAM_CD	= "DOCRM220M_";
String SCREEN_CD	= "DOCRM220M_01";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>

<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.setBreadCrumb("列印回執聯作業(DOCRM220M_)", "一般", true);
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
	 processMain         	: "../DOCRM220M_Controller.do?command=processMain"
	,processQuery        	: "../DOCRM220M_Controller.do?command=processQuery"
	,processPrint           : "../DOCRM220M_Controller.do?command=processPrint"
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
					,elementOfHtml : ['TAX_TP', 'DOCRM_TP', 'DOCRM_STUS']
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
				
				}
			,columnMeta : [
			         { title:'編輯', dataPatternKey:'_edit'}
			        ,{ title:'案件編號', index:'DOCRM_NO'}					
					,{ title:'稅目別', index:'TAX_TP_NM'}					
					,{ title:'案件種類', index:'DOCRM_TP_NM'}
					,{ title:'申請人', index:'APPLY_NM'}
					,{ title:'申請日期', index:'APPLY_DATE'}
					,{ title:'完成日期', index:'CPL_DATE'}
					,{ title:'進度', index:'DOCRM_STUS_NM'}
					,{ title:'登錄機關', index:'DOC_ORG_ID_NM'}
					,{ title:'承辦人', index:'DOC_STF_NM'}
					,{ title:'功能', index:'FUNCTION', dataAttr:"style='text-align:center'"}
				]
		};
		uiProcess.processQuery(options);
}

function processPrint(jsonResult)
{
	$('#PKNO').val(jsonResult.PKNO);
	
	formUtil.showJasperReport(document.listForm, urlAction.processPrint);
}

function processEdit(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
}

</script>
</head>

<content tag="topBlock">
		<input type="button" name="btnQuery" class="btn" value="查　詢" onclick="processQuery();">
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
                <th>申請人姓名</th>
                <td>
                	<input name="APPLY_NM" type="text" size="10">
                </td>
			    <th>身分證字號</th>
                <td>
                	<input name="APPLY_IDN" type="text" size="10">
                </td>
              </tr>  
              <tr>
                <th>案件編號</th>
                <td>
                	<input name="DOCRM_NO" type="text" size="10">
                </td>
			    <th>進度</th>
                <td>
                	<select name="DOCRM_STUS"></select>
                </td>
              </tr>  
              <tr>
                <th>申請日期</th>
                <td colspan='3'>
                	<input type="TEXT" name="START_APPLY_DATE" class="calendar"  >至<input type="TEXT" name="END_APPLY_DATE" class="calendar"  >
                </td>
              </tr>  
                                
        </table>
	</form>
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<input type="hidden" name="PKNO" >
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>

