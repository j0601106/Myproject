<%
/*
@@程式代號 = docrm830m_v2.jsp
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
String PROGRAM_CD	= "DOCRM830_";
String SCREEN_CD	= "DOCRM830_02";
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
	 processMain         : "../DOCRM830M_Controller.do?command=processMain"
	,processEdit         : "../DOCRM830M_Controller.do?command=processEdit"
	,processAddSave    	 : "../DOCRM830M_Controller.do?command=processAddSave"		
	,processEditSave     : "../DOCRM830M_Controller.do?command=processEditSave"	
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
					,elementOfHtml : ['ORG_ID','ROLE_ID']
					,dataSetOfHtml : jsonResult.data
				});
			}//end onAfterSuccess		
		};
		formUtil.submitTo(options);
}



function processAdd(){

	$('input[name=btnAddSave]').show();
	$('input[name=btnEditSave]').hide();
	processMain();
}
function processAddSave()
{	
	
	var IP = $('#queryForm input[name=USER_IP]').val();//IP
	var ORG = $('#queryForm select[name=ORG_ID]').val(); //機關
	var ROLE_ID = $('#queryForm select[name=ROLE_ID]').val(); //角色
	var mag ="";

	var exp= /^((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))*$/; 
	var reg = IP.match(exp);  
	

	
	if (IP == ''){
		mag +="請輸入IP！" ;
		alert(mag);
		return false;
	}
	
	if(reg==null){  
		mag +="請輸入正確的IP！" ;
		alert(mag);
		return false;  
	}
	if (ORG == ''){
		mag +="請輸入機關別！" ;
		alert(mag);
		return false;
	}
	
	if (ROLE_ID == ''){
		mag +="請輸入角色！" ;
		alert(mag);
		return false;
	}
	
	var options = {
		formObj : $('#queryForm')
		,url : urlAction.processAddSave
		,onAfterSuccess : function(jsonResult, status) {
			formUtil.clearFormData($('#queryForm'));
			uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
		}
	};
	
	formUtil.submitTo(options);	
}





function processEdit(paramObj, mode)
{
	_actionMode = 'Edit';
	$('input[name=btnAddSave]').hide();
	$('input[name=btnEditSave]').show();
	uiLayout.editLayout();
	
	var options = {
		dataObj : paramObj
		,url : urlAction.processEdit
		,onAfterSuccess : function(jsonResult, status) {
			_editData = jsonResult;
		
			formUtil.clearFormData($('#queryForm'));
			formUtil.bindFormData({
				targetForm : $('#queryForm')
				,dataSet : jsonResult.data.editData
				,elementOfHtml : ['ORG_ID','ROLE_ID']
				,dataSetOfHtml : jsonResult.data
			});		
		}//end onAfterSuccess
	};


	if (mode == 'reset' && _editData != null) {
		options.onAfterSuccess(_editData);
	} else {
		formUtil.submitTo(options);
	}

}

function processEditSave() {
	var IP = $('#queryForm input[name=USER_IP]').val();//IP
	var ORG = $('#queryForm select[name=ORG_ID]').val(); //機關
	var ROLE_ID = $('#queryForm select[name=ROLE_ID]').val(); //角色
	var mag ="";

	var exp= /^((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))*$/; 
	var reg = IP.match(exp);  
	

	
	if (IP == ''){
		mag +="請輸入IP！" ;
		alert(mag);
		return false;
	}
	
	if(reg==null){  
		mag +="請輸入正確的IP！" ;
		alert(mag);
		return false;  
	}
	
	if (ORG == ''){
		mag +="請輸入機關別！" ;
		alert(mag);
		return false;
	}
	
	if (ROLE_ID == ''){
		mag +="請輸入角色！" ;
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

	$('#ORG_ID').val("");
	$('#USER_NM').val("");
	$('#USER_IP').val("");
	$('#ROLE_ID').val("");
	$('#CREATE_USER_ID').val("");
	$('#CREATE_DATE').val("");
	$('#CREATE_TIME').val("");
	$('#UPDATE_USER_ID').val("");
	$('#UPDATE_DATE').val("");
	$('#UPDATE_TIME').val("");
	uiFrameset.switchFrame('v1');

}

function processReset(){
	$('#ORG_ID').val("");
	$('#USER_NM').val("");
	$('#USER_IP').val("");
	$('#ROLE_ID').val("");
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
	<input type="button" name="btnAddSave"  class="btn" value="存　檔" onclick="processAddSave();">
	<input type="button" name="btnEditSave"  class="btn" value="存　檔" onclick="processEditSave();">
	<input type="button" name="btnQueryReset" class="btn" value="清　除" onclick="processReset();">
</content>

<content tag="queryBlock">
	<form id="queryForm" name="queryForm" method="POST">
	<input type="hidden" name="PKNO">
	<input name="UPDATE_USER_ID" type="hidden" readonly="readonly"><!-- 異動人員 -->
	<input name="CREATE_USER_ID" type="hidden" readonly="readonly"><!-- 建立人員 -->
		<table class="table-redmond" width="100%">
			  <tr>
                <th colspan="2">承辦人</th>
              </tr>
			  <tr>
                <th>機關別<font color="#FF0000"><b>＊</b></font></th>
                <td>
                	<select name="ORG_ID" ></select>
                </td>
              </tr>
        	  <tr>
                <th>姓名</th>
                <td>
                	<input name="USER_NM" type="text">
                </td>
              </tr>
              <tr>
                <th>IP<font color="#FF0000"><b>＊</b></font></th>
                <td>
                	<input name="USER_IP" type="text">
                </td>
              </tr>
              <tr>
                <th>角色<font color="#FF0000"><b>＊</b></font></th>
                <td>
                	<select name="ROLE_ID"></select>
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

