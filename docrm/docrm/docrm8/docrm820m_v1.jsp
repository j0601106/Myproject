<%
/*
@@程式代號 = docrm820m_v1.jsp
@@程式名稱 = 程式名稱-查詢頁
@@程式版本 = V1.000
@@更新日期 = 2016/11/24
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<head>
<meta name="decorator" content="template_1_2_main">
<style>
.warning{
color:red;
font-family: "新細明體", "Arial Unicode MS";
font-size: 12px;}

.star{
color:red;
}

div.watermark { 
    color: red;  
    background-image: url(sprite.png); 
    background-repeat: no-repeat; 
    background-position: 3px -2669px;
    } 

</style>
<%@ include file="../util/pageinit.jsp"%>

<%
String PROGRAM_CD	= "DOCRM820M_";
String SCREEN_CD	= "DOCRM820M_01";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);

//String CURR_HOST = gov.fdc.library.env.ApEnv.get("YCMjsp_Url.host"); del by meng
%>
<% 
Date local_time = new Date();
String now = local_time.toLocaleString();
int Year = local_time.getYear();
Year = Year - 11;
int Month = local_time.getMonth();
Month = Month + 1;
int Date = local_time.getDate();

String Mon = String.valueOf(Month);
String Day = String.valueOf(Date);

if(Mon.length()==1){
	Mon = "0" + Mon;
}
if(Day.length()==1){
	Day = "0" + Day;
}

String today=Year+"-"+Mon+"-"+Day;

%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("editBlock", true, "帳號設定", true);
layout.addPortlet("listBlock", true, "帳號查詢", true);
Layout.saveLayout(pageContext, layout);
%>

<script type="text/javascript">
var _actionMode	= null;	//畫面目前的模式: Add, Edit
var _mainData	= null; //查詢暫存用
var _editData	= null; //編輯暫存用
var uiFrameset	= parent.uiFrameset; //frameset物件
var uiLayout	= new UiLayout();  //建立區塊切換物件
//--------以上必須存在------------

<%-- //var CURR_HOST = "<%=CURR_HOST%>"; --%>

var urlAction = {
		processMain     : "../DOCRM820M_Controller.do?command=processMain"
	,processQuery     : "../DOCRM820M_Controller.do?command=processQuery"
	,processDelete    : "../DOCRM820M_Controller.do?command=processDelete"
};

var validator;
$(document).ready(function()
{
	//初始化區塊切換物件
		//畫面檢核
	validator = $("#editForm").validate();
	uiLayout.init(getLayoutOption1());
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm")%>
	
	processMain();
});

function processMain(mode)
{
	var options = {
			url : urlAction.processMain
			,onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;	
				formUtil.bindFormData({
					targetForm : $('#editForm')
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

function processQuery()
{
	//processMain();
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,dataPattern : {
// 				_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
 			//,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}',DOCRM_TP:'@{DOCRM_TP}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂
			}
			,columnMeta : [
				 { title:'選取', dataPatternKey:'_checkbox'}
				,{ title:'編輯', dataPatternKey:'_edit' ,dataAttr:"style='text-align:center'"}
				,{ title:'名稱', index:'DOCRM_TP_NM' ,dataAttr:"style='text-align:center'"}
				,{ title:'代碼', index:'DOCRM_TP' ,dataAttr:"style='text-align:center'"}
				,{ title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'建立時間',  indexPattern : '@{CREATE_DATE}'+'&nbsp;'+'@{CREATE_TIME}' ,dataAttr:"style='text-align:center'"}
				]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
			}
		};

	uiProcess.processQuery(options);
}

function processAdd(){
	uiFrameset.switchFrameWithExecute('v2', 'processAdd', "");
}

function processEdit(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
	
}

function processDelete()
{   
	uiProcess.processDelete($('#listForm input[name=gridDeleteCheck]:checked'));
}

function processSelAll()
{
	$("input[name=gridDeleteCheck]").attr('checked', 'true');
}

function processNoSelAll()
{
	$("input[name=gridDeleteCheck]").removeAttr('checked');
}

function processQueryReset(){
	$('#TAX_TP').val("");
	$('#DOCRM_TP_NM').val("");
	$('#DOCRM_TP').val("");
}


</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnQueryReset" class="btn" value="清　除" onclick="processQueryReset();">
	<input type="button" name="btnQuery" class="btn" value="查　詢" onclick="processQuery();">
	<input type="button" name="btnAdd"  class="btn" value="新　增" onclick="processAdd();">
	
</content>

<content tag="editBlock">
	<form id="editForm" name="editForm" method="POST" enctype="multipart/form-data">	 
		<input type="hidden" name="PKNO">
			<table class="table-redmond" width="100%">		
              <tr>
                <th>稅目別</th>
                <td>
                <select name="TAX_TP"></select>
                </td>
			    
              </tr>
              <tr>  
                <th>名稱</th>
                <td>
                	<input name="DOCRM_TP_NM" type="text" size="30"  value="">
                </td>                
              </tr>
              <tr>  
                <th>代碼</th>
                <td>
                	<input name="DOCRM_TP"  type="text" size="10" value="">
                </td>                
              </tr>                       
            </table>
            
	</form>	
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div class='buttons' style='clear: both' align='left'>
			<input type="button" name="btnDelete" class="btn" value="全選" onclick="processSelAll();">
			<input type="button" name="btnDelete" class="btn" value="全不選" onclick="processNoSelAll();">
			<input type="button" name="btnDelete" class="btn" value="刪除" onclick="processDelete();">			
		</div>
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>
