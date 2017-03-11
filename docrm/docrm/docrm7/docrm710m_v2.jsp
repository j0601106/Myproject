
<%
	/*
	@@程式代號 = docrm710m_v2.jsp
	@@程式名稱 = 程式名稱-編輯頁
	@@程式版本 = V1.000
	@@更新日期 = 2016/10/17
	@@檢查碼 = 內容由YPM自動產生
	*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	String PROGRAM_CD = "DOCRM710_";
	String SCREEN_CD = "DOCRM710_02";
	initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
	//customize Layout
	Layout layout = Layout.createLayout(Layout.LayoutBaseType.UserBased);
	layout.addPortlet("queryBlock", true, "作答", true);
	Layout.saveLayout(pageContext, layout);
%>

<head>
<meta name="decorator" content="template_1_2_sub">

<%@ include file="../util/pageinit.jsp"%>

<script type="text/javascript">
	var _actionMode = null; //畫面目前的模式: Add, Edit
	var _mainData = null; //查詢暫存用
	var _editData = null; //編輯暫存用
	var uiFrameset = parent.uiFrameset; //frameset物件
	var uiLayout = new UiLayout(); //建立區塊切換物件
	//--------以上必須存在------------

	var urlAction = {
		processMain : "../DOCRM710M_Controller.do?command=processMain",
		processEdit : "../DOCRM710M_Controller.do?command=processEdit",
		processEditSave : "../DOCRM710M_Controller.do?command=processEditSave",
		processQueryA : "../DOCRM710M_Controller.do?command=processQueryA"
	};

	var ddl1 = new UiCascadeDDL(); //連動下拉建立

	var time =
<%=String.valueOf(
					(Integer.valueOf((new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()))) - 1911))
					+ new SimpleDateFormat("MMddHHmm").format(Calendar.getInstance().getTime())%>
	var checkT = new Boolean(true);
	var checkB = new Boolean(true);
	var checkR = new Boolean(true);
	var checkS = new Boolean(true);

	$(document).ready(function() {
		//初始化區塊切換物件
		uiLayout.init(getLayoutOption1(), getBlockTemplate1());
<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "queryForm")%>
	
<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>
	//processMain();

	});

	function processMain(mode) {
		var options = {
			url : urlAction.processMain,
			onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;
				formUtil.bindFormData({
					targetForm : $('#queryForm'),
					elementOfHtml : [ 'TAX_TP' ],
					dataSetOfHtml : jsonResult.data
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

	function processEdit(paramObj, mode) {//傳入編輯頁面
		_actionMode = 'Edit';
		uiLayout.editLayout();

		var options = {
			dataObj : paramObj,
			url : urlAction.processEdit,
			onAfterSuccess : function(jsonResult, status) {
				_editData = jsonResult;
				formUtil.clearFormData($('#queryForm'));
				formUtil.bindFormData({
					targetForm : $('#queryForm'),
					dataSet : jsonResult.data.editData,
					elementOfHtml : [ 'TAX_TP' ],
					dataSetOfHtml : jsonResult.data
				});

				var ADD_STUS = $('#queryForm input[name=ADD_STUS]').val();
				if (ADD_STUS == "未完成") {
					$('input[name=btnAddSave]').hide();
					$('input[name=btnEditSave]').show();
				} else {
					$('input[name=btnAddSave]').hide();
					$('input[name=btnEditSave]').hide();
				}

				$("#table1").empty();//避免點作答時重複跑
				if (table1.rows.length > 1) {
					var nodes = table1.childNodes[0].childNodes;
					for (var i = nodes.length - 1; nodes.length > 1; i--) {
						table1.childNodes[0].removeChild(nodes[i]);
					}
				}
				for (var i = 0; jsonResult.data.GRID_RESULT[i] != null; i++) {
					$('#table1').show();
					var table = $('#table1');
					var row = $("<tr></tr>");
					var td1 = $('<td></td>');
					td1.append($("<div align='left'><label >題目" + (i + 1)
							+ "</label>"));
					var td2 = $('<td></td>');
					td2.append($("<div align='left'><label>"
							+ jsonResult.data.GRID_RESULT[i].QUESTION
							+ "</label>"));

					row.append(td1);
					row.append(td2);

					table.append(row);
					var row = $("<tr></tr>");
					var td1 = $('<td></td>');
					td1.append($("<div align='left'><label ></label>"));
					var td2 = $('<td></td>');
					td2.append($("<div align='left'><label>"
							+ jsonResult.data.GRID_RESULT[i].QUESTION_HTML));

					row.append(td1);
					row.append(td2);

					table.append(row);
				}

				//$("input[name=005][value=1]").attr("checked", true);//給預設值

			}//end onAfterSuccess
		};

		if (mode == 'reset' && _editData != null) {
			options.onAfterSuccess(_editData);
		} else {
			formUtil.submitTo(options);
		}

	}

	function processEditSave() {
		//儲存資料	
		var options = {
			formObj : $('#queryForm'),
			url : urlAction.processEditSave,
			onAfterSuccess : function(jsonResult, status) {
				formUtil.clearFormData($('#queryForm'));
				uiFrameset.switchFrameWithExecute('v1', 'processQuery',
						jsonResult);
			}
		};

		formUtil.submitTo(options);
	}

	function processBack() {
		$('#PKNO').val("");
		$('#TAX_TP').val("");
		uiFrameset.switchFrame('v1');

	}
</script>
</head>

<content tag="topBlock"> <input type="button" name="btnBack"
	class="btn" value="回上頁" onclick="processBack();"> <input
	type="button" name="btnEditSave" class="btn" value="儲　存"
	onclick="processEditSave();"> </content>

<content tag="queryBlock">
<form id="queryForm" name="queryForm" method="POST">
	<input type="hidden" name="PKNO"> <input type="hidden"
		name="DOCRM_NO"> <input type="hidden" name="ADD_STUS">
	<input type="hidden" name="RESPOND_DATE"> <input type="hidden"
		name="CREATE_USER_ID"> <input type="hidden" name="CREATE_DATE">
	<input type="hidden" name="CREATE_TIME">
	<div id="Agent-content" class="portlet-content">
		<table id="table1" class="table-redmond" style='width: 100%'>

		</table>

	</div>
</form>
</content>

