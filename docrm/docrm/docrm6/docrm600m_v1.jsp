<%
/*
@@程式代號 = docrm600m_v1.jsp
@@程式名稱 = 程式名稱-查詢頁
@@程式版本 = V1.000
@@更新日期 = 2016/10/19
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@page import="gov.fdc.framework.core.util.FdcThreadHolder"%>
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
String PROGRAM_CD	= "收件管理系統";
String SCREEN_CD	= "進度統計表";
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
layout.addPortlet("editBlock", true, "", true);
layout.addPortlet("listBlock", true, "", true);
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
	 processMain      : "../DOCRM600M_Controller.do?command=processMain"
	,processQuery     : "../DOCRM600M_Controller.do?command=processQuery"
	,processPrint     : "../DOCRM600M_Controller.do?command=processPrint"
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

function processMain()
{
	$('#YEAR').keyup(function () {
        if (this.value != this.value.replace(/[^0-9\.]/g, '')) {
            this.value = this.value.replace(/[^0-9\.]/g, '');
    }});
	$('input[name=DST]').bind('click', function(){
		processReadOnly();
	});
	$("input[name=DST][value=B]" , $("#editForm")).attr("checked" ,true);
	var DST = $('input[name="DST"]:checked').val();
	$('input[name="DST_CD"]').val(DST);
	$('#DOC_ORG_ID').hide();
	
	var options = {
			url : urlAction.processMain
			,onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;
				formUtil.bindFormData({
					targetForm : $('#editForm')
					,elementOfHtml : ['TAX_TP','DOC_ORG_ID','DOC_ORG_ID2']
					,dataSetOfHtml : jsonResult.data
				});
				//預設系統日
				$('#editForm input[name=YEAR]').val(jsonResult.data.YEAR);
				$('#editForm Select[name=MONTH]').val(jsonResult.data.MONTH);
				//依登入者所屬機關
				var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
				if(orgCd == 'E77'){
					$('#editForm input[name=DST]').attr("disabled",false);
					$('#editForm Select[name=DOC_ORG_ID2]').val(orgCd);
					$('#editForm Select[name=DOC_ORG_ID]').attr("disabled",true);;
				}else{
					$('#editForm input[name=DST]').attr("disabled",true);
					$('#editForm Select[name=DOC_ORG_ID2]').val(orgCd);
					$('#editForm Select[name=DOC_ORG_ID]').val(orgCd);
		 			$('#editForm Select[name=DOC_ORG_ID2]').attr("disabled",true);;
				}
				var msd="";
				msd = $('input[name="DST"]:checked').val()
				if(msd =="A"){
					processQuery()
				}else if(msd == "B"){
					processQuery2();
				}
			} //onAfterSuccess
		};
		formUtil.submitTo(options);
}

function processQuery()
{
	var date ="";
	if ($('#YEAR').val() == '' || $('#MONTH').val() == ''){
		date +="申請年月不得為空白\n" ;
		alert(date);
		return false;
	}
	var msd="";
	var msg="";
	msd = $('input[name="DST"]:checked').val();
	msg = $('select[name="TAX_TP"]').val();
	if(msd == "A"){
	
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			,dataPattern : {
// 			_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
			}
			,columnMeta : [
				 { title:'機關別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'合計申請件數', index:'TOTAL' ,dataAttr:"style='text-align:center'"}
				,{ title:'新申請', index:'STUS0' ,dataAttr:"style='text-align:center'"}
				,{ title:'移轉中', index:'STUS1' ,dataAttr:"style='text-align:center'"}
				,{ title:'已收件', index:'STUS2' ,dataAttr:"style='text-align:center'"}
				,{ title:'待掛文', index:'STUS3' ,dataAttr:"style='text-align:center'"}
				,{ title:'已掛文', index:'STUS4' ,dataAttr:"style='text-align:center'"}
				,{ title:'已完成', index:'STUS5' ,dataAttr:"style='text-align:center'"}
			]};
	uiProcess.processQuery(options);
	}else if(msd =="B" && msg !=""){
		processQuery3()
	}else if(msd =="B" && msg ==""){
		processQuery2()
	}
}

function processQuery2()
{	
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			,dataPattern : {
// 			_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
			}
			,columnMeta : [
				 { title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'合計申請件數', index:'TOTAL' ,dataAttr:"style='text-align:center'"}
				,{ title:'新申請', index:'STUS0' ,dataAttr:"style='text-align:center'"}
				,{ title:'移轉中', index:'STUS1' ,dataAttr:"style='text-align:center'"}
				,{ title:'已收件', index:'STUS2' ,dataAttr:"style='text-align:center'"}
				,{ title:'待掛文', index:'STUS3' ,dataAttr:"style='text-align:center'"}
				,{ title:'已掛文', index:'STUS4' ,dataAttr:"style='text-align:center'"}
				,{ title:'已完成', index:'STUS5' ,dataAttr:"style='text-align:center'"}
			]};
	uiProcess.processQuery(options);
}

function processQuery3()
{	
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			,dataPattern : {
// 			_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
			}
			,columnMeta : [
				 { title:'表單種類', index:'TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'合計申請件數', index:'TOTAL' ,dataAttr:"style='text-align:center'"}
				,{ title:'新申請', index:'STUS0' ,dataAttr:"style='text-align:center'"}
				,{ title:'移轉中', index:'STUS1' ,dataAttr:"style='text-align:center'"}
				,{ title:'已收件', index:'STUS2' ,dataAttr:"style='text-align:center'"}
				,{ title:'待掛文', index:'STUS3' ,dataAttr:"style='text-align:center'"}
				,{ title:'已掛文', index:'STUS4' ,dataAttr:"style='text-align:center'"}
				,{ title:'已完成', index:'STUS5' ,dataAttr:"style='text-align:center'"}
			]};
	uiProcess.processQuery(options);
}

function processReadOnly(){
	var DST = $('#editForm input[name=DST]:checked').val();
	
	if(DST == 'A'){
		$('#editForm Select[name=DOC_ORG_ID2]').attr("disabled",true);
		$("#DOC_ORG_ID2").val('');
	}else{
		$('#editForm Select[name=DOC_ORG_ID2]').attr("disabled",false);
	}
}

function processPrint(){
	formUtil.showJasperReport(document.editForm, urlAction.processPrint);
}

</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnQuery" class="btn" value="查　詢" onclick="processQuery();">
	<input type="button" name="btnPrint"  class="btn" value="匯　出" onclick="processPrint();">
</content>

<content tag="editBlock">
	<form id="editForm" name="editForm" method="POST"
		enctype="multipart/form-data">
			<table class="table-redmond" width="100%">
              <tr>
                <th>申請年月</th>
                <td>
              	 	<input name="YEAR" type ="text" maxlength = "3"></select>
                	<select name="MONTH">
                	<option value = "" >  </option>
                	<option value = "01" > 一月 </option>
                	<option value = "02" > 二月 </option>
                	<option value = "03" > 三月 </option>
                	<option value = "04" > 四月 </option>
                	<option value = "05" > 五月 </option>
                	<option value = "06" > 六月 </option>
                	<option value = "07" > 七月 </option>
                	<option value = "08" > 八月 </option>
                	<option value = "09" > 九月 </option>
                	<option value = "10" > 十月 </option>
                	<option value = "11" > 十一月 </option>
                	<option value = "12" > 十二月 </option>
                	</select>
                </td>
              </tr>
              <tr>
                <th width="20%">機關別</th>
                <td>
                	<input type='radio' name='DST' value="A"/>	全處
                	<input type='radio' name='DST' value="B"/>	單一處
                	<input type='hidden' name='DST_CD' />
                	<select name="DOC_ORG_ID2"></select>
                	<select name="DOC_ORG_ID"></select>
                </td>
              </tr>
              <tr>
                <th width="20%">稅目別</th>
                <td>
                	<select name="TAX_TP"></select>
                </td>
              </tr>
            </table>
	</form>	
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div class='buttons' style='clear: both' align='left'></div>
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>
