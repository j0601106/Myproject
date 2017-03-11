<%
/*
@@程式代號 = docrm810m_v2.jsp
@@程式名稱 = 程式名稱-編輯頁
@@程式版本 = V1.000
@@更新日期 = 2016/10/26
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String PROGRAM_CD	= "收件管理系統";
String SCREEN_CD	= "政令宣導維護";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("queryBlock", true, "政令宣導", true);
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
	 processMain         : "../DOCRM810M_Controller.do?command=processMain"	
	,processEdit         : "../DOCRM810M_Controller.do?command=processEdit"
	,processAddSave    	 : "../DOCRM810M_Controller.do?command=processAddSave"		
	,processEditSave     : "../DOCRM810M_Controller.do?command=processEditSave"	
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
});
	function processMain(mode) {
		var options = {
			url : urlAction.processMain,
			onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;
				formUtil.bindFormData({
					targetForm : $('#queryForm'),
					elementOfHtml : [ 'TAX_TP', 'STUS' ],
					dataSetOfHtml : jsonResult.data
				});
			}//end onAfterSuccess		
		};
		formUtil.submitTo(options);
	}

	function processAdd() {
		$('input[name=btnAddSave]').show();
		$('input[name=btnEditSave]').hide();
		$('input[name=CONTENT]').width(500).height(200)
		$("#CONTENT").val('');
		$("#SUBJECT").val('');
		$("#MANDATE_NO").val('');
		$("#CREATE_USER_ID").val('');
		$("#CREATE_DATE").val('');
		$("#CREATE_TIME").val('');
		$("#UPDATE_USER_ID").val('');
		$("#UPDATE_DATE").val('');
		$("#UPDATE_TIME").val('');
		$('#MANDATE_NO').keyup(function() {
			if (this.value != this.value.replace(/[^0-9]/g, '')) {
				this.value = this.value.replace(/[^0-9]/g, '');
			}
		});
		processMain();
	}
	function processAddSave() {
		var msg = "";
		if ($('#CONTENT').val() == '' || $('#SUBJECT').val() == ''
				|| $('#MANDATE_NO').val() == '' || $('#TAX_TP').val() == ''
				|| $('#STUS').val() == '') {
			msg += "欄位皆不得為空白\n";
			alert(msg);
			return false;
		}
		var options = {
			formObj : $('#queryForm'),
			url : urlAction.processAddSave,
			onAfterSuccess : function(jsonResult, status) {
				formUtil.clearFormData($('#queryForm'));
				uiFrameset.switchFrameWithExecute('v1', 'processQuery',
						jsonResult);
			}
		};
		formUtil.submitTo(options);
	}

	function processEdit(paramObj) {
		uiLayout.editLayout();
		$('input[name=btnAddSave]').hide();
		$('input[name=btnEditSave]').show();
		$('input[name=CONTENT]').width(500).height(200)

		var options = {
			dataObj : paramObj,
			url : urlAction.processEdit,
			onAfterSuccess : function(jsonResult, status) {
				_editData = jsonResult;
				formUtil.clearFormData($('#queryForm'));
				formUtil.bindFormData({
					targetForm : $('#queryForm'),
					dataSet : jsonResult.data.editData,
					elementOfHtml : [ 'TAX_TP', 'STUS' ],
					dataSetOfHtml : jsonResult.data
				});
				$('#TAX_TP2').val(jsonResult.data.editData.TAX_TP);
				$('#MANDATE_NO2').val(jsonResult.data.editData.MANDATE_NO);
				$('#MANDATE_NO').keyup(function() {
					if (this.value != this.value.replace(/[^0-9]/g, '')) {
						this.value = this.value.replace(/[^0-9]/g, '');
					}
				});
			}//end onAfterSuccess
		};
		formUtil.submitTo(options);
	}

	function processEditSave() {
		var msg = "";

		if ($('#CONTENT').val() == '' || $('#SUBJECT').val() == ''
				|| $('#MANDATE_NO').val() == '' || $('#TAX_TP').val() == ''
				|| $('#STUS').val() == '') {
			msg += "欄位皆不得為空白\n";
			alert(msg);
			return false;
		}
		var options = {
			formObj : $('#queryForm'),
			url : urlAction.processEditSave,
			onAfterSuccess : function(jsonResult, status) {
				formUtil.clearFormData($('#queryForm'));
				uiFrameset.switchFrameWithExecute('v1', 'processMain',
						jsonResult);
			}
		};
		formUtil.submitTo(options);
	}

	function processBack() {
		uiFrameset.switchFrame('v1');
	}
</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnBack" class="btn" value="回上頁" onclick="processBack();">
	<input type="button" name="btnAddSave"  class="btn" value="存　檔" onclick="processAddSave();">
	<input type="button" name="btnEditSave"  class="btn" value="存　檔" onclick="processEditSave();">
</content>

<content tag="queryBlock">
	<form id="queryForm" name="queryForm" method="POST">
	<input type="hidden" name="TAX_TP2">
	<input type="hidden" name="MANDATE_NO2">
	<input type="hidden" name="PKNO">
		<table class="table-redmond" width="100%">
        	  <tr>
                <th>編號<label style="color: red;">＊</label></th>
                <td>
                	<input name="MANDATE_NO" type="text" maxlength="4">
                </td>
              </tr>
              <tr>
                <th>稅目別<label style="color: red;">＊</label></th>
                <td>
                	<select name="TAX_TP"></select>
                </td>
              </tr>
              <tr>
                <th>主旨<label style="color: red;">＊</label></th>
                <td>
                	<input name="SUBJECT" type="text" size="100" maxlength="60">
                </td>
              </tr>
              <tr>
                <th>內容<label style="color: red;">＊</label></th>
                <td>
                	<textarea name="CONTENT" style="width:500px;height:150px;" maxlength="500"></textarea>
                </td>
              </tr>
              <tr>
                <th>使用狀況<label style="color: red;">＊</label></th>
                <td>
                	<select name="STUS"></select>	
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
                	<input name="UPDATE_DATE" type="text" readonly="readonly">
                	<input name='UPDATE_TIME' type='text' readonly="readonly"/>
                </td>
              </tr>
        </table>
	</form>
</content>

