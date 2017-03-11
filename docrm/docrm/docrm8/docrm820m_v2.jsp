<%
/*
@@程式代號 = docrm820m_v2.jsp
@@程式名稱 = 程式名稱-編輯頁
@@程式版本 = V1.000
@@更新日期 = 2016/11/24
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String PROGRAM_CD	= "DOCRM820_";
String SCREEN_CD	= "DOCRM820_02";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("queryBlock", true, "新增", true);
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
	processMain         : "../DOCRM820M_Controller.do?command=processMain"
	,processEdit         : "../DOCRM820M_Controller.do?command=processEdit"
	,processAddSave    	 : "../DOCRM820M_Controller.do?command=processAddSave"		
	,processEditSave     : "../DOCRM820M_Controller.do?command=processEditSave"	
};

var ddl1  = new UiCascadeDDL();	//連動下拉建立

var time = <%= String.valueOf((Integer.valueOf((new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime())))-1911)) + new SimpleDateFormat("MMddHHmm").format(Calendar.getInstance().getTime())%>

var checkT=new Boolean(true);
var checkB=new Boolean(true);
var checkR=new Boolean(true);
var checkS=new Boolean(true);

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate1());
	
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "queryForm")%>
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>

	//processMain();

});

function processMain(mode)
{
	var options = {
			url : urlAction.processMain
			,onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;	
				formUtil.bindFormData({
					targetForm : $('#queryForm')
					,elementOfHtml : ['TAX_TP']
					,dataSetOfHtml : jsonResult.data
				});
				
				processQuery();
			}//end onAfterSuccess			
		};

	if (mode == 'reset' && _mainData != null) {
		options.onAfterSuccess(_mainData);
	} else {
		formUtil.submitTo(options);
	}
}

function processAdd(){
	$('input[name=btnAddSave]').show();
	$('input[name=btnEditSave]').hide();
	processMain();
	
}

function processAddSave()
{	
	var DN = $('#queryForm input[name=DOCRM_TP_NM]').val();//名稱
	var DM = $('#queryForm input[name=DOCRM_TP]').val(); //代碼
	var TT = $('#queryForm select[name=TAX_TP]').val(); //稅目別
	var mag ="";

	if (DN == ''){
		mag +="請輸入名稱！" ;
		alert(mag);
		return false;
	}
	
	if (DM == ''){
		mag +="請輸入代碼！" ;
		alert(mag);
		return false;
	}
	
	if (TT == ''){
		mag +="請輸入稅目別！" ;
		alert(mag);
		return false;
	}
	
	var options = {
		formObj : $('#queryForm')
		,url : urlAction.processAddSave
		,onAfterSuccess : function(jsonResult, status) {
			formUtil.clearFormData($('#queryForm'));
			uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
			$('#DOCRM_TP2').val(jsonResult.data.editData.DOCRM_TP);//檢核
		}
	};
	
	formUtil.submitTo(options);	
}

function processEdit(paramObj, mode)
{
	_actionMode = 'Edit';
	uiLayout.editLayout();
	$('input[name=btnAddSave]').hide();
	$('input[name=btnEditSave]').show();
	
	var options = {
		dataObj : paramObj
		,url : urlAction.processEdit
		,onAfterSuccess : function(jsonResult, status) {
			_editData = jsonResult;
		
			formUtil.clearFormData($('#queryForm'));
			formUtil.bindFormData({
				targetForm : $('#queryForm')
				,dataSet : jsonResult.data.editData
				,elementOfHtml : ['TAX_TP']
				,dataSetOfHtml : jsonResult.data
			});		
			$('#DOCRM_TP2').val(jsonResult.data.editData.DOCRM_TP);//檢核
		}//end onAfterSuccess
	};


	if (mode == 'reset' && _editData != null) {
		options.onAfterSuccess(_editData);
	} else {
		formUtil.submitTo(options);
	}

}

function processEditSave() {
	
	var DN = $('#queryForm input[name=DOCRM_TP_NM]').val();//名稱
	var DM = $('#queryForm input[name=DOCRM_TP]').val(); //代碼
	var TT = $('#queryForm select[name=TAX_TP]').val(); //稅目別
	var mag ="";

	if (DN == ''){
		mag +="請輸入名稱！" ;
		alert(mag);
		return false;
	}
	
	if (DM == ''){
		mag +="請輸入代碼！" ;
		alert(mag);
		return false;
	}
	
	if (TT == ''){
		mag +="請輸入稅目別！" ;
		alert(mag);
		return false;
	}
	
	var options = {
		formObj : $('#queryForm')
		,url : urlAction.processEditSave
		,onAfterSuccess : function(jsonResult, status) {
				formUtil.clearFormData($('#queryForm'));
				uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
		}
	};
	
	formUtil.submitTo(options);
}

function processBack()
{
	uiFrameset.switchFrame('v1');
	$('#PKNO').val("");
	$('#TAX_TP').val("");
	$('#DOCRM_TP_NM').val("");
	$('#DOCRM_TP').val("");
	$('#CREATE_USER_ID').val("");
	$('#CREATE_DATE').val("");
	$('#CREATE_TIME').val("");
	$('#UPDATE_USER_ID').val("");
	$('#UPDATE_DATE').val("");
	$('#UPDATE_TIME').val("");
}


</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnBack" class="btn" value="回上頁" onclick="processBack();">
	<input type="button" name="btnAddSave"  class="btn" value="儲　存" onclick="processAddSave();">
	<input type="button" name="btnEditSave"  class="btn" value="儲　存" onclick="processEditSave();">
</content>

<content tag="queryBlock">
	<form id="queryForm" name="queryForm" method="POST">
	<input type="hidden" name="DOCRM_TP2">
	<input type="hidden" name="PKNO">
	<input name="UPDATE_USER_ID" type="hidden" readonly="readonly"><!-- 異動人員 -->
	<input name="CREATE_USER_ID" type="hidden" readonly="readonly"><!-- 建立人員 -->
		<table class="table-redmond" width="100%">
        	  <tr>
                <th>名稱<font color="#FF0000"><b>＊</b></font></th>
                <td>
                	<input name="DOCRM_TP_NM" type="text" size="30">
                </td>
              </tr>
              <tr>
                <th>代碼<font color="#FF0000"><b>＊</b></font></th>
                <td>
                	<input name="DOCRM_TP" type="text" maxlength="3">
                </td>
              </tr>
              <tr>
                <th>稅目別<font color="#FF0000"><b>＊</b></font></th>
                <td>
                	 <select name="TAX_TP"></select>
                </td>
              </tr>
              <tr>
              <th>建立時間</th>
                <td>
                	<input name="CREATE_DATE" type="text" readonly="readonly">
                	<input name="CREATE_TIME" type="text" readonly="readonly">
                </td>
              </tr>
              <tr>
                <th>異動時間</th>
                <td>
                	<input name='UPDATE_DATE' type='text' readonly="readonly"/>
					<input name='UPDATE_TIME' type='text' readonly="readonly"/>
                </td>
              </tr>
        </table>
	</form>
</content>

