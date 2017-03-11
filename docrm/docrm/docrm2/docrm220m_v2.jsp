<%
/*
@@程式代號 = docrm220m_v2.jsp
@@程式名稱 = 列印回執聯作業
@@程式版本 = V1.000
@@更新日期 = 2015/12/01
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String PROGRAM_CD	= "DOCRM220M_";
String SCREEN_CD	= "DOCRM220M_02";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("editBlock", true, "案件內容", true);
layout.addPortlet("listBlock1", true, "案件歷程", false);
layout.addPortlet("listBlock2", true, "訊息歷程", false);
layout.addPortlet("listBlock3", true, "已夾帶附件", false);
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
	  processEdit            : "../DOCRM220M_Controller.do?command=processEdit"
	 ,processQueryDetail1    : "../DOCRM220M_Controller.do?command=processQueryDetail1"   
	 ,processQueryDetail2    : "../DOCRM220M_Controller.do?command=processQueryDetail2"  
	 ,processQueryDetail3    : "../DOCRM220M_Controller.do?command=processQueryDetail3" 
	 ,processPrint           : "../DOCRM220M_Controller.do?command=processPrint"
	 ,processDownload        : "../DOCRM220M_Controller.do?command=processDownload"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>

});

function processEdit(paramObj, mode){
	_actionMode = 'Edit';
	uiLayout.editLayout();

	var options = {
		dataObj : paramObj
		,url : urlAction.processEdit
		,onAfterSuccess : function(jsonResult, status) {
			_editData = jsonResult;
		
			formUtil.clearFormData($('#editForm'));
			formUtil.bindFormData({
				targetForm : $('#editForm')
				,dataSet : jsonResult.data.editData
				,dataSetOfHtml : jsonResult.data
			});	
			$('#DOCRM_NO').html(jsonResult.data.editData.DOCRM_NO);
			$('#TAX_TP_NM').html(jsonResult.data.editData.TAX_TP_NM);
			$('#DOCRM_TP_NM').html(jsonResult.data.editData.DOCRM_TP_NM);
			$('#ACP_CD_NM').html(jsonResult.data.editData.ACP_CD_NM);
			$('#RDC_NO').html(jsonResult.data.editData.RDC_NO);
			$('#VERIFY_NO').html(jsonResult.data.editData.VERIFY_NO);
			$('#APPLY_NM').html(jsonResult.data.editData.APPLY_NM);
			$('#APPLY_IDN').html(jsonResult.data.editData.APPLY_IDN);
			$('#APPLY_SUBJECT').html(jsonResult.data.editData.APPLY_SUBJECT);
			$('#APPLY_DATE').html(jsonResult.data.editData.APPLY_DATE);
			$('#LMT_DATE').html(jsonResult.data.editData.LMT_DATE);
			$('#CPL_DATE').html(jsonResult.data.editData.CPL_DATE);
			$('#MOBILE_NO').html(jsonResult.data.editData.MOBILE_NO);
			$('#EMAIL').html(jsonResult.data.editData.EMAIL);
			$('#DOCRM_STUS_NM').html(jsonResult.data.editData.DOCRM_STUS_NM);
			$('#ADD_MK_NM').html(jsonResult.data.editData.ADD_MK_NM);
			$('#ADD_STUS_NM').html(jsonResult.data.editData.ADD_STUS_NM);
			$('#DOC_ORG_ID_NM').html(jsonResult.data.editData.DOC_ORG_ID_NM);
			$('#DOC_STF_NM').html(jsonResult.data.editData.DOC_STF_NM);
			processQueryDetail1();
			processQueryDetail2();
			processQueryDetail3();
		}//end onAfterSuccess
	};


	if (mode == 'reset' && _editData != null) {
		options.onAfterSuccess(_editData);
	} else {
		formUtil.submitTo(options);
	}

}

function processQueryDetail1(){

	var options = {
			 formObj : $('#editForm')
			,url : urlAction.processQueryDetail1
			,gridObj : $('#tableGrid1')
			,columnMeta : [
                     { title:'案件狀態', index:'DOCRM_STUS_NM'}					
					,{ title:'移轉機關', index:'TRN_ORG_ID_NM'}
					,{ title:'時間', index:'SD_DATE_TIME'}
				]
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processQueryDetail2(){

	var options = {
			 formObj : $('#editForm')
			,url : urlAction.processQueryDetail2
			,gridObj : $('#tableGrid2')
			,columnMeta : [
                     { title:'訊息狀態', index:'MSG_STUS_NM'}					
					,{ title:'時間', index:'SD_DATE_TIME'}
				]
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processQueryDetail3(){

	var options = {
			 formObj : $('#editForm')
			,url : urlAction.processQueryDetail3
			,gridObj : $('#tableGrid3')
			,columnMeta : [
                     { title:'附件檔名', index:'FILE_NM'}					
					,{ title:'來源', index:'SOURCE_NM'}
					,{ title:'時間', index:'SD_DATE_TIME'}
					,{ title:'進度', index:'FILE_STUS_NM'}
					,{ title:'功能', index:'FUNCTION', dataAttr:"style='text-align:center'"}
				]
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processQueryDetail4(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v3', 'processQueryDetail4', jsonResult);
}

function processPrint()
{
	formUtil.showJasperReport(document.editForm, urlAction.processPrint);
}

function download(){
  	formUtil.download({'MESS_NO':$('#MESS_NO').val()}, urlAction.processDownload);
}

function processBack()
{
	uiFrameset.switchFrame('v1');
}

</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnBack" class="btn" value="回上頁" onclick="processBack();">
	<input type="button" name="btn"  class="btn" value="列印回執聯" onclick="processPrint();">
	<input type="button" name="btn"  class="btn" value="查看申請書" onclick="download();">
</content>

<content tag="editBlock">
	<form id="editForm" name="editForm" method="POST">
		<input type="hidden" name="MESS_NO">
		<table class="table-redmond" width="100%">
        	  <tr>
                <th width="30%" >案件編號</th>
                <td>
                	<label id="DOCRM_NO"></label>
                </td>
              </tr>
              <tr>
                <th>稅目別</th>
                <td>
                	<label id="TAX_TP_NM"></label>
                </td>
              </tr>
              <tr>
                <th>案件種類</th>
                <td>
                	<label id="DOCRM_TP_NM"></label>
                </td>
              </tr>
              <tr>
                <th>登錄機關</th>
                <td>
                	<label id="ACP_CD_NM"></label>
                </td>
              </tr>
              <tr>
                <th>公文文號</th>
                <td>
                	<label id="RDC_NO"></label>
                </td>
              </tr>
              <tr>
                <th>驗證碼</th>
                <td>
                	<label id="VERIFY_NO"></label>
                </td>
              </tr>
              <tr>
                <th>申請人姓名</th>
                <td>
                	<label id="APPLY_NM"></label>
                </td>
              </tr>
              <tr>
                <th>身分證字號</th>
                <td>
                	<label id="APPLY_IDN"></label>
                </td>
              </tr>
              <tr>
                <th>申請主旨</th>
                <td>
                	<label id="APPLY_SUBJECT"></label>
                </td>
              </tr>
              <tr>
                <th>申請日期</th>
                <td>
                	<label id="APPLY_DATE"></label>
                </td>
              </tr>
              <tr>
                <th>預計完成日期</th>
                <td>
                	<label id="LMT_DATE"></label>
                </td>
              </tr>
              <tr>
                <th>完成日期</th>
                <td>
                	<label id="CPL_DATE"></label>
                </td>
              </tr>
              <tr>
                <th>連絡電話</th>
                <td>
                	<label id="MOBILE_NO"></label>
                </td>
              </tr>
              <tr>
                <th>EMAIL郵件</th>
                <td>
                	<label id="EMAIL"></label>
                </td>
              </tr>
              <tr>
                <th>進度</th>
                <td>
                	<label id="DOCRM_STUS_NM"></label>
                </td>
              </tr>
              <tr>
                <th>是否需要補件</th>
                <td>
                	<label id="ADD_MK_NM"></label>
                </td>
              </tr>
              <tr>
                <th>補件狀況</th>
                <td>
                	<label id="ADD_STUS_NM"></label>
                </td>
              </tr>
              <tr>
                <th>辦理機關</th>
                <td>
                	<label id="DOC_ORG_ID_NM"></label>
                </td>
              </tr>
              <tr>
                <th>承辦人</th>
                <td>
                	<label id="DOC_STF_NM"></label>
                </td>
              </tr>
        </table>
	</form>
</content>

<content tag="listBlock1">
	<form id="listForm1" name="listForm1">
		<div id="tableGrid1"></div>
	</form>
</content>

<content tag="listBlock2">
	<form id="listForm2" name="listForm2">
		<div id="tableGrid2"></div>
	</form>
</content>

<content tag="listBlock3">
	<form id="listForm3" name="listForm3">
		<div id="tableGrid3"></div>
	</form>
</content>
