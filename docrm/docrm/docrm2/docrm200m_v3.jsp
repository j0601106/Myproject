<%
/*
@@程式代號 = docrm200m_v3.jsp
@@程式名稱 = 收件及移轉作業
@@程式版本 = V1.000
@@更新日期 = 2015/12/01
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
String PROGRAM_CD	= "DOCRM200M_";
String SCREEN_CD	= "DOCRM200M_03";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("editBlock", true, "案件內容", true);
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
		 processQueryDetail3         : "../DOCRM200M_Controller.do?command=processQueryDetail3"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>

});

function processQueryDetail3(jsonResult){

	$('#FILE_NM').html(jsonResult.FILE_NM);
	$('#SOURCE_NM').html(jsonResult.SOURCE_NM);
	$('#MESS_NO').val(jsonResult.MESS_NO);
	
	var options = {
			 dataObj : jsonResult
			,url : urlAction.processQueryDetail3
			,gridObj : $('#tableGrid')
			,columnMeta : [
                     { title:'附件狀態', index:'FILE_STUS_NM'}					
					,{ title:'時間', index:'SD_DATE_TIME'}
				]
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processBack()
{
	var dataObj = {MESS_NO:$('#MESS_NO').val()};
	uiFrameset.switchFrameWithExecute('v2', 'processEdit', dataObj);
}


</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnBack" class="btn" value="回上頁" onclick="processBack();">
</content>

<content tag="editBlock">
	<form id="editForm" name="editForm" method="POST">
		<input type="hidden" name="MESS_NO">
		<table class="table-redmond" width="100%">
        	  <tr>
                <th>附件名稱</th>
                <td>
                	<label id="FILE_NM"></label>
                </td>
              </tr>
              <tr>
                <th>來源</th>
                <td>
                	<label id="SOURCE_NM"></label>
                </td>
              </tr>
        </table>
	</form>
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div id="tableGrid"></div>
	</form>
</content>
