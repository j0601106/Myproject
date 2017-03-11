<%
/*
@@程式代號 = docrm230m_v3.jsp
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
String SCREEN_CD	= "DOCRM230M_03";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("editBlock1", true, "新增", true);
layout.addPortlet("editBlock2", true, "修改", true);
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
	 processEdit         : "../DOCRM230M_Controller.do?command=processEdit"  
	,processEditD        : "../DOCRM230M_Controller.do?command=processEditD" 
	,processQueryDetail  : "../DOCRM230M_Controller.do?command=processQueryDetail"       
	,processAddSave    	 : "../DOCRM230M_Controller.do?command=processAddSave"		
	,processEditSaveD    : "../DOCRM230M_Controller.do?command=processEditSaveD"	
	,processEditSaveN2   : "../DOCRM230M_Controller.do?command=processEditSaveN2"
	,processDelete       : "../DOCRM230M_Controller.do?command=processDelete"
	,processSyn          : "../DOCRM230M_Controller.do?command=processSyn"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm1")%>
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm2")%>
	
	formUtil.enableFileUpload('editForm2');

});

function processEdit(jsonResult, mode){
	
	_actionMode = 'Edit';
	uiLayout.editLayout();

	var options = {
		dataObj : jsonResult
		,url : urlAction.processEdit
		,onAfterSuccess : function(jsonResult, status) {
			_editData = jsonResult;
			formUtil.clearFormData($('#editForm1'));
			formUtil.clearFormData($('#editForm2'));
			formUtil.bindFormData({
				targetForm : $('#editForm1')
				,dataSet : jsonResult.data.editData
				,elementOfHtml : []
				,dataSetOfHtml : jsonResult.data
			});		
			$('#DOCRM_NO2').html(jsonResult.data.editData.DOCRM_NO);
			$('#DOCRM_TP_NM').html(jsonResult.data.editData.DOCRM_TP_NM);
			$('#APPLY_NM').html(jsonResult.data.editData.APPLY_NM);
			$('#APPLY_DATE').html(jsonResult.data.editData.APPLY_DATE);
			processQueryDetail({'DOCRM_NO': jsonResult.data.editData.DOCRM_NO});
			$('#editBlock2').hide();
		}//end onAfterSuccess
	};

	if (mode == 'reset' && _editData != null) {
		options.onAfterSuccess(_editData);
	} else {
		formUtil.submitTo(options);
	}
}

function processQueryDetail(jsonResult){

	var options = {
			 dataObj : jsonResult
			,url : urlAction.processQueryDetail
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,dataPattern : {
				_checkbox:"<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{MESS_NO},@{DOC_TP}' />"
			   ,_edit:"<div align='center'><span class='btn-edit' onclick=\"processEditD({PKNO:'@{PKNO}'})\"></span></div>"
				}
			,columnMeta : [
                     { title:'', dataPatternKey:'_checkbox'}
			        ,{ title:'編輯', dataPatternKey:'_edit'}
					,{ title:'文件', index:'DOC_TP'}					
					,{ title:'補件狀況', index:'ADD_STUS_NM'}
					,{ title:'補件日期', index:'CPL_DATE'}
					,{ title:'附件', index:'FILE_LIST'}
				]
			,onAfterSuccess : function(jsonResult, status) {
				
				formUtil.bindFormData({
					targetForm : $('#editForm2')
					,elementOfHtml : ['ADD_STUS']
					,dataSetOfHtml : jsonResult.data
				});
				
				if(jsonResult.data.GRID_RESULT.length == 0){
					//$('#editBlock2').hide();
					$('#listBlock').hide();
				} else {
					//$('#editBlock2').show();
					$('#listBlock').show();
					$('#tableGridPaging').html("<table  class='table-redmond' style='width:100%'><th>共"+jsonResult.data.GRID_RESULT.length+"筆</th></table>");
				}
			}//end onAfterSuccess	
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processEditD(jsonResult, mode){
	
	_actionMode = 'Edit';
	uiLayout.editLayout();

	var options = {
		dataObj : jsonResult
		,url : urlAction.processEditD
		,onAfterSuccess : function(jsonResult, status) {
			_editData = jsonResult;
			formUtil.clearFormData($('#editForm2'));
			formUtil.bindFormData({
				targetForm : $('#editForm2')
				,dataSet : jsonResult.data.editData
				,dataSetOfHtml : jsonResult.data
			});		
			$('#editBlock2').show();
			$('#DOC_TP2').val(jsonResult.data.editData.DOC_TP);
		}//end onAfterSuccess
	};

	if (mode == 'reset' && _editData != null) {
		options.onAfterSuccess(_editData);
	} else {
		formUtil.submitTo(options);
	}
}

function processEditSaveD()
{	
	var dataObj = {'DOCRM_NO':$('#DOCRM_NO').val()}
	var options = {
		 formObj : $('#editForm2')
		,url : urlAction.processEditSaveD
		,onAfterSuccess : function(jsonResult, status) {
			formUtil.clearFormData($('#editForm2'));
			$('#editBlock2').hide();
			uiFrameset.switchFrameWithExecute('v3', 'processEdit', dataObj);
		}
	};
	
	formUtil.submitTo(options);	
}

function processAddSave()
{	
	if($('#DOC_TP').val()==""){
		alert("請輸入文件名稱");
		return false;
	}
	
	var dataObj = {'DOCRM_NO':$('#DOCRM_NO').val()}
	var options = {
		 formObj : $('#editForm1')
		,url : urlAction.processAddSave
		,onAfterSuccess : function(jsonResult, status) {
			formUtil.clearFormData($('#editForm1'));
			uiFrameset.switchFrameWithExecute('v3', 'processEdit', dataObj);
		}
	};
	
	formUtil.submitTo(options);	
}

function processEditSaveN2()
{	
	var options = {
		 formObj : $('#editForm1')
		,url : urlAction.processEditSaveN2
		,onAfterSuccess : function(jsonResult, status) {
			formUtil.clearFormData($('#editForm1'));
			formUtil.clearFormData($('#editForm2'));
			uiFrameset.switchFrameWithExecute('v1', 'processQuery');
		}
	};
	
	formUtil.submitTo(options);	
}

function processSyn()
{	
	var options = {
		 formObj : $('#editForm1')
		,url : urlAction.processSyn
		,onAfterSuccess : function(jsonResult, status) {
			formUtil.clearFormData($('#editForm1'));
			formUtil.clearFormData($('#editForm2'));
			uiFrameset.switchFrameWithExecute('v1', 'processQuery');
		}
	};
	
	formUtil.submitTo(options);	
}

function processOpenWin(str)
{
	var winFeatures ="dialogHeight:800px;dialogWidth:600px;";
	var winre=window.showModalDialog('../docrm2/docrm230m_v4.jsp',"" ,winFeatures);

	if(winre != undefined){
		if(str==1){
			document.editForm1.DOC_TP.value = winre;
		} else {
			document.editForm2.DOC_TP.value = winre;
		}
	}
}

function processBack()
{
	uiFrameset.switchFrame('v1');
}

function processDelete()
{
	uiProcess.processDelete($('#listForm input[name=gridDeleteCheck]:checked'));
	uiFrameset.switchFrameWithExecute('v1', 'processMain');
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
	<input type="button" name="btnEditSave"  class="btn" value="補件完成" onclick="processEditSaveN2();">
	<input type="button" name="btnEditSave"  class="btn" value="同步外網" onclick="processSyn();">
</content>

<content tag="editBlock1">
	<form id="editForm1" name="editForm1" method="POST">
		<input type="hidden" name="DOCRM_NO">
		<input type="hidden" name="MESS_NO">
		<table class="table-redmond" width="100%">
        	  <tr>
                <th>案件號碼</th>
                <th>案件種類</th>
                <th>申請人</th>
                <th>申請日期</th>
                <th>文件</th>
                <th>功能</th>
              </tr>
              <tr>
                <td>
                	<label id="DOCRM_NO2"></label>
                </td>
                <td>
                	<label id="DOCRM_TP_NM"></label>
                </td>
                <td>
                	<label id="APPLY_NM"></label>
                </td>
                <td>
                	<label id="APPLY_DATE"></label>
                </td>
                <td>
                	<input name="DOC_TP" type="text">
                	<img name="btnCOUNTRY_TITLE" class="window-chosen" onclick="processOpenWin('1')">
                </td>
                <td>
                	<input type="button" name="btnAddSave"  class="btn" value="存　檔" onclick="processAddSave();">
                </td>
              </tr>
        </table>
	</form>
</content>

<content tag="editBlock2">
	<form id="editForm2" name="editForm2" method="POST">
		<input type="hidden" name="DOCRM_NO">
		<input type="hidden" name="PKNO">
		<input type="hidden" name="MESS_NO">
		<input type="hidden" name="DOC_TP2">
		<table class="table-redmond" width="100%">
        	  <tr>
                <th>文件</th>
                <th>補件狀態</th>
                <th>上傳</th>
                <th>功能</th>
              </tr>
              <tr>
                <td>
                	<input name="DOC_TP" type="text">
                	<img name="btnCOUNTRY_TITLE" class="window-chosen" onclick="processOpenWin('2')">
                </td>
                <td>
                	<select name="ADD_STUS"></select>
                </td>
                <td>
                	<%=fileUpload("COMP", "", "1")%>
                </td>
                <td>
                	<input type="button" name="btnEditSave"  class="btn" value="存　檔" onclick="processEditSaveD();">
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
			<input type="button" name="btnDelete" class="btn" value="刪　除" onclick="processDelete();">
		</div>
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>
