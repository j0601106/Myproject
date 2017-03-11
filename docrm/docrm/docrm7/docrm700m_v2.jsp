
<%
/*
@@程式代號 = docrm700m_v2.jsp
@@程式名稱 = 程式名稱-編輯頁
@@程式版本 = V1.000
@@更新日期 = 2016/10/20
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String PROGRAM_CD	= "收件管理系統";
String SCREEN_CD	= "問券調查問券維護";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("queryBlock", true, "題目", true);
layout.addPortlet("listBlock", true, "答案", true);
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
	 processMain         : "../DOCRM700M_Controller.do?command=processMain"	
	,processAddSave    	 : "../DOCRM700M_Controller.do?command=processAddSave"	
	,processEdit         : "../DOCRM700M_Controller.do?command=processEdit"
	,processEditSave     : "../DOCRM700M_Controller.do?command=processEditSave"	
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

function processMain(mode)
{
	var options = {
			url : urlAction.processMain
			,onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;	
				formUtil.bindFormData({
					targetForm : $('#queryForm')
					,elementOfHtml : ['STUS']
					,dataSetOfHtml : jsonResult.data
				});
			}//end onAfterSuccess
		};
		formUtil.submitTo(options);
}

function processAdd(){
	$("#QUESTION").val('');
	$("#QUESTION_ID").val('');
	$("#STUS").val('');
	$("#CREATE_USER_ID").val('');
	$("#CREATE_TIME").val('');
	$("#UPDATE_USER_ID").val('');
	$("#UPDATE_TIME").val('');
	$("#B1").val('');
	$("#B2").val('');
	$("#B3").val('');
	$("#B4").val('');
	$('input[name="B1"]').attr("disabled",false);
	$('input[name="B2"]').attr("disabled",true);
	$('input[name="B3"]').attr("disabled",true);
	$('input[name="B4"]').attr("disabled",true);
	$('#Sat').show();
	$('input[name="Sat"]').attr("checked",true);
	$('input[name=QUESTION_ID]').attr("readonly",false).attr("disabled",false);
	$('input[name=btnAddSave]').show();
	$('input[name=btnEditSave]').hide();
	$('#QUESTION_ID').keyup(function () {
    if (this.value != this.value.replace(/[^0-9]/g, '')) {
    this.value = this.value.replace(/[^0-9]/g, '');
    }});
	$('#B1').keyup(function () {
	    if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'')) {
	    this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'');
	}});
	$('#B2').keyup(function () {
	    if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'')) {
	    this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'');
	}});
	$('#B3').keyup(function () {
	    if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'')) {
	    this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'');
	}});
	$('#B4').keyup(function () {
	    if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'')) {
	    this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g,'');
	}});
	processMain();
}

	function processAddSave() {
		if ($('#STUS').val() == '' || $('#QUESTION_ID').val() == '' 
			|| $('#QUESTION').val() == '') {
			alert("使用狀態、題目、題目代碼不得為空白\n");
			return false;
		}
		if ($('#B1').val() == '' || $('#B2').val() == '') {
		alert("最少輸入兩個答案");
		return false;
		}
		//為了傳值
		var B1 = $('input[name="B1"]').val();
		var B2 = $('input[name="B2"]').val();
		var B3 = $('input[name="B3"]').val();
		var B4 = $('input[name="B4"]').val();
		$('input[name="A1"]').val(B1);
		$('input[name="A2"]').val(B2);
		$('input[name="A3"]').val(B3);
		$('input[name="A4"]').val(B4);
		//防寫入特殊字元
		var txt = new RegExp(
			"[ ,\\`,\\~,\\!,\\@,\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\_,\\‵"
			+ ",\\|,\\:,\\.,\\<,\\>,\\{,\\},\\(,\\),\\',\\;,\\=,\\€,\\┌,\\┬,\\┐,\\〝"
			+ ",\\〞,\\‘,\\’,\\“,\\”,\\『,\\』,\\「,\\├,\\┼,\\┤,\\※,\\〈,\\〉,\\《,\\》"
			+ ",\\【,\\】,\\﹝,\\﹞,\\」,\\└,\\┴,\\┘,\\○,\\●,\\↑,\\↓,\\！,\\：,\\；,\\、,\\─"
			+ ",\\│,\\§,\\←,\\→,\\。,\\，,\\‧,\\？,\"]");
		if (txt.test(B1) || txt.test(B2) || txt.test(B3) || txt.test(B4)) {
			alert("不得輸入特殊字元");
			return false;
		}
		var options = {
			formObj : $('#queryForm'),
			url : urlAction.processAddSave,
			onAfterSuccess : function(jsonResult, status) {
				formUtil.clearFormData($('#queryForm'));
				uiFrameset.switchFrameWithExecute('v1', 'processQuery',jsonResult);
			}
		};
		formUtil.submitTo(options);
	}

	function processEdit(paramObj) {
		var options = {
			dataObj : paramObj,
			url : urlAction.processEdit,
			onAfterSuccess : function(jsonResult, status) {
				_editData = jsonResult;

				formUtil.clearFormData($('#queryForm'));
				formUtil.bindFormData({
					targetForm : $('#queryForm'),
					dataSet : jsonResult.data.editData,
					elementOfHtml : [ 'STUS' ],
					dataSetOfHtml : jsonResult.data
				});
				$('input[name=btnAddSave]').hide();
				$('input[name=btnEditSave]').show();
				//為了傳值進去
				var A1 = $('input[name="A1"]').val();//隱藏欄位
				var A2 = $('input[name="A2"]').val();
				var A3 = $('input[name="A3"]').val();
				var A4 = $('input[name="A4"]').val();
				$('input[name="B1"]').val(A1);//後端丟直給A1~A4後把值丟給畫面B1~B4
				$('input[name="B2"]').val(A2);
				$('input[name="B3"]').val(A3);
				$('input[name="B4"]').val(A4);
				//倘若T710有資料則disabled答案欄位
				var msg = jsonResult.data.ANS;
				if (msg == "Y") {
					$('#Sat').hide();
					$('input[name="B1"]').attr("disabled", true);
					$('input[name="B2"]').attr("disabled", true);
					$('input[name="B3"]').attr("disabled", true);
					$('input[name="B4"]').attr("disabled", true);
				} else {
					$('#Sat').show();
					$('input[name="Sat"]').attr("checked", true);
					$('input[name="B1"]').attr("disabled", false);
					$('input[name="B2"]').attr("disabled", true);
					$('input[name="B3"]').attr("disabled", true);
					$('input[name="B4"]').attr("disabled", true);
				}
				//下面兩段若不存在編輯儲存時SQL無法判斷QUESTION_ID的資料
				var QUESTION_ID = $('input[name="QUESTION_ID"]').val();
				$('input[name="QUESTION_ID2"]').val(QUESTION_ID);//取QUESTION_ID val()
				//只能輸入數字
				$('#QUESTION_ID').keyup(function() {
					if (this.value != this.value.replace(/[^0-9]/g, '')) {
						this.value = this.value.replace(/[^0-9]/g, '');
					}
				});
				$('input[name=QUESTION_ID]').attr("readonly", true).attr("disabled",true);
				//限制輸入
				$('#B1').keyup(function() {if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '')) {
								this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '');
							}});
				$('#B2').keyup(function() {if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '')) {
								this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '');
							}});
				$('#B3').keyup(function() {if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '')) {
								this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '');
							}});
				$('#B4').keyup(function() {if (this.value != this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '')) {
								this.value = this.value.replace(/[^\a-\z\A-\Z0-9u3447-\uFA29]/g, '');
							}});
			}//end onAfterSuccess
		};
		formUtil.submitTo(options);
	}

	function processEditSave() {
		if ($('#STUS').val() == '' || $('#QUESTION_ID').val() == '' 
			|| $('#QUESTION').val() == '') {
			alert("使用狀態、題目、題目代碼不得為空白\n");
			return false;
		}
		if ($('#B1').val() == '' || $('#B2').val() == '') {
			alert("最少輸入兩個答案");
			return false;
		}
		//為了傳值去後端
		var B1 = $('input[name="B1"]').val();
		var B2 = $('input[name="B2"]').val();
		var B3 = $('input[name="B3"]').val();
		var B4 = $('input[name="B4"]').val();
		$('input[name="A1"]').val(B1);
		$('input[name="A2"]').val(B2);
		$('input[name="A3"]').val(B3);
		$('input[name="A4"]').val(B4);
		//防寫入特殊字元
		var txt = new RegExp(
			"[ ,\\`,\\~,\\!,\\@,\#,\\$,\\%,\\^,\\+,\\*,\\&,\\\\,\\/,\\?,\\_,\\‵"
			+ ",\\|,\\:,\\.,\\<,\\>,\\{,\\},\\(,\\),\\',\\;,\\=,\\€,\\┌,\\┬,\\┐,\\〝"
			+ ",\\〞,\\‘,\\’,\\“,\\”,\\『,\\』,\\「,\\├,\\┼,\\┤,\\※,\\〈,\\〉,\\《,\\》"
			+ ",\\【,\\】,\\﹝,\\﹞,\\」,\\└,\\┴,\\┘,\\○,\\●,\\↑,\\↓,\\！,\\：,\\；,\\、,\\─"
			+ ",\\│,\\§,\\←,\\→,\\。,\\，,\\‧,\\？,\"]");
		if (txt.test(B1) || txt.test(B2) || txt.test(B3) || txt.test(B4)) {
			alert("不得輸入特殊字元");
			return false;
		}
		var options = {
			formObj : $('#queryForm'),
			url : urlAction.processEditSave,
			onAfterSuccess : function(jsonResult, status) {
				formUtil.clearFormData($('#queryForm'));
				uiFrameset.switchFrameWithExecute('v1', 'processQuery',jsonResult);
			}};
		formUtil.submitTo(options);
	}

	//radio控制答案內容
	function processSat() {
		var R1 = $('input[name="Sat"]:checked').val();
		$('input[name="B1"]').attr("disabled", true);
		$('input[name="B2"]').attr("disabled", true);
		$('input[name="B3"]').attr("disabled", true);
		$('input[name="B4"]').attr("disabled", true);
		if (R1 == 0) {
			$('input[name="B1"]').val("滿意");
			$('input[name="B2"]').val("尚可");
			$('input[name="B3"]').val("不滿意");
			$('input[name="B4"]').val("");
		} else if (R1 == 1) {
			$('input[name="B1"]').val("是");
			$('input[name="B2"]').val("否");
			$('input[name="B3"]').val("");
			$('input[name="B4"]').val("");
		} else {
			$('input[name="B1"]').attr("disabled", false);
			$('input[name="B1"]').val("");
			$('input[name="B2"]').val("");
			$('input[name="B3"]').val("");
			$('input[name="B4"]').val("");
		}
	}

	//onchange限制逐行輸入
	function processChange() {
		var B1 = $('input[name="B1"]').val();//答案一
		var B2 = $('input[name="B2"]').val();//答案二
		var B3 = $('input[name="B3"]').val();//答案三
		if (B1 != '') {
			$('input[name="B1"]').attr("disabled", false);
			$('input[name="B2"]').attr("disabled", false);
			$('input[name="B3"]').attr("disabled", true);
			$('input[name="B4"]').attr("disabled", true);
			if (B2 != '') {
				$('input[name="B1"]').attr("disabled", false);
				$('input[name="B2"]').attr("disabled", false);
				$('input[name="B3"]').attr("disabled", false);
				$('input[name="B4"]').attr("disabled", true);
				if (B3 != '') {
					$('input[name="B1"]').attr("disabled", false);
					$('input[name="B2"]').attr("disabled", false);
					$('input[name="B3"]').attr("disabled", false);
					$('input[name="B4"]').attr("disabled", false);
				} else {
					$('input[name="B4"]').val("");
					$('input[name="B1"]').attr("disabled", false);
					$('input[name="B2"]').attr("disabled", false);
					$('input[name="B3"]').attr("disabled", false);
					$('input[name="B4"]').attr("disabled", true);
				}
			} else {
				$('input[name="B3"]').val("");
				$('input[name="B4"]').val("");
				$('input[name="B1"]').attr("disabled", false);
				$('input[name="B2"]').attr("disabled", false);
				$('input[name="B3"]').attr("disabled", true);
				$('input[name="B4"]').attr("disabled", true);
			}
		} else {
			$('input[name="B2"]').val("");
			$('input[name="B3"]').val("");
			$('input[name="B4"]').val("");
			$('input[name="B1"]').attr("disabled", false);
			$('input[name="B2"]').attr("disabled", true);
			$('input[name="B3"]').attr("disabled", true);
			$('input[name="B4"]').attr("disabled", true);
		}
	}
	function processBack() {
		uiFrameset.switchFrame('v1');
	}
</script>
</head>

<content tag="topBlock"> <input type="button" name="btnBack"
	class="btn" value="回上頁" onclick="processBack();"> <input
	type="button" name="btnAddSave" class="btn" value="存　檔"
	onclick="processAddSave();"> <input type="button"
	name="btnEditSave" class="btn" value="存　檔" onclick="processEditSave();">
</content>

<content tag="queryBlock">
<form id="queryForm" name="queryForm" method="POST">
	<input type="hidden" name="PKNO">
	<table class="table-redmond" width="100%">
		<tr>
			<th>題目<label style="color: red;">＊</label></th>
			<td>
				<input name="QUESTION" type="text" size="50" maxlength="100">
				<input name="A1" type="hidden"> 
				<input name="A2" type="hidden"> 
				<input name="A3" type="hidden">
				<input name="A4" type="hidden">
			</td>
		</tr>
		<tr>
			<th>題目代碼<label style="color: red;">＊</label></th>
			<td>
				<input name="QUESTION_ID" type="text" value="" maxlength="3">
				<input name="QUESTION_ID2" type="hidden" value="" maxlength="3">
			</td>
		</tr>
		<tr>
			<th>使用狀態<label style="color: red;">＊</label></th>
			<td>
				<select name="STUS"></select>
			</td>
		</tr>
	</table>
</form>
</content>

<content tag="listBlock">
<form id="listForm" name="listForm" method="POST">
	<table class="table-redmond" width="100%">
		<tr>
			<td id="Sat" COLSPAN=2>
			<input type='radio' name='Sat' value="0"onclick="processSat();" />滿意度
			<input type='radio' name='Sat' value="1"onclick="processSat();" />是否 
			<input type='radio'	name='Sat' value="2"onclick="processSat();" checked="true" />自訂 
			<label style="color: red;">＊輸入答案後左鍵(一下)格子外，便可開啟下個格子輸入，依此類推</label></td>
		</tr>
		<tr>
			<th>答案一</th>
			<td>
			<input name="B1" type="text" onchange="processChange();"maxlength="20">
			</td>
		</tr>
		<tr>
			<th>答案二</th>
			<td>
			<input name="B2" type="text" onchange="processChange();"maxlength="20">
			</td>
		</tr>
		<tr>
			<th>答案三</th>
			<td>
			<input name="B3" type="text" onchange="processChange();"maxlength="20">
			</td>
		</tr>
		<tr>
			<th>答案四</th>
			<td>
			<input name="B4" type="text" onchange="processChange();"maxlength="20">
			</td>
		</tr>
	</table>

</form>
</content>
