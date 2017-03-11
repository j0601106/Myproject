<%
/*
@@程式代號 = docrm730m_v1.jsp
@@程式名稱 = 程式名稱-查詢頁
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<%@ page import="gov.fdc.framework.core.util.FdcThreadHolder"%>
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
String PROGRAM_CD	= "DOCRM730M_";
String SCREEN_CD	= "DOCRM730M_01";
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
		processMain     : "../DOCRM730M_Controller.do?command=processMain"
	,processQuery     : "../DOCRM730M_Controller.do?command=processQuery"
	,processDelete    : "../DOCRM730M_Controller.do?command=processDelete"
	,processPrint    : "../DOCRM730M_Controller.do?command=processPrint"
	,QUS_RESULT      : "../DOCRM730M_Controller.do?command=QUS_RESULT"
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
					,elementOfHtml : ['TAX_TP','DOC_ORG_ID','QUESTION_ID']
					,dataSetOfHtml : jsonResult.data
				});
				 processReadOnly();
				$('input[name=Organ]').bind('click', function(){
					processReadOnly();
				}); 
				$('#editForm input[name=APPLY_DATE_YEAR]').val(jsonResult.data.APPLY_DATE_YEAR);
				$('#editForm select[name=APPLY_DATE_Mo]').val(jsonResult.data.APPLY_DATE_Mo);
				QUS_RESULT();
			}//end onAfterSuccess			
		};

	if (mode == 'reset' && _mainData != null) {
		options.onAfterSuccess(_mainData);
	} else {
		formUtil.submitTo(options);
	}
}


function processSearch(){

	if($('#editForm select[name=QUESTION_ID]').val()==null){
		alert('請選擇題目');
		return false;
	}

	//QUS_RESULT();
	var RESULT_QA1 =$('#editForm input[name=RESULT_QA1]').val(); 
	var RESULT_QA2 =$('#editForm input[name=RESULT_QA2]').val(); 
	var RESULT_QA3 =$('#editForm input[name=RESULT_QA3]').val(); 
	var RESULT_QA4 =$('#editForm input[name=RESULT_QA4]').val(); 
	var RESULT_QA5 =$('#editForm input[name=RESULT_QA5]').val(); 
	var RESULT_QA6 =$('#editForm input[name=RESULT_QA6]').val(); 
	var RESULT_QA7 =$('#editForm input[name=RESULT_QA7]').val(); 
	var RESULT_QA8 =$('#editForm input[name=RESULT_QA8]').val(); 
//判斷有幾個欄位
	if(RESULT_QA2==""){
		processQuery1()
	}else if(RESULT_QA3 ==""){
		processQuery2()
	}else if(RESULT_QA4 ==""){
		processQuery3()
	}else{
		processQuery()
	}
}

//顯示8個欄位
function processQuery()
{	
	var RESULT_QA1 =$('#editForm input[name=RESULT_QA1]').val(); 
	var RESULT_QA2 =$('#editForm input[name=RESULT_QA2]').val(); 
	var RESULT_QA3 =$('#editForm input[name=RESULT_QA3]').val(); 
	var RESULT_QA4 =$('#editForm input[name=RESULT_QA4]').val(); 
	var RESULT_QA5 =$('#editForm input[name=RESULT_QA5]').val(); 
	var RESULT_QA6 =$('#editForm input[name=RESULT_QA6]').val(); 
	var RESULT_QA7 =$('#editForm input[name=RESULT_QA7]').val(); 
	var RESULT_QA8 =$('#editForm input[name=RESULT_QA8]').val(); 
	
	var A1 = RESULT_QA1+"(當月)";
	var A2 = RESULT_QA2+"(當月)";
	var A3 = RESULT_QA3+"(當月)";
	var A4 = RESULT_QA4+"(當月)";
	var A5 = RESULT_QA5+"(跨月)";
	var A6 = RESULT_QA6+"(跨月)";
	var A7 = RESULT_QA7+"(跨月)";
	var A8 = RESULT_QA8+"(跨月)";
	
	var YY = $('#editForm input[name=APPLY_DATE_YEAR]').val();

	var MM = $('#editForm select[name=APPLY_DATE_Mo]').val();

	var mag ="";
	
	if (MM == '' || YY == ''){
		mag +="結案年月不得為空！" ;
		alert(mag);
		return false;
	}

var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
		if(orgCd !='E77'){
			$("#DOC_ORG_ID").removeAttr("disabled");
		}
		
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			//,pagingObj : $('#tableGridPaging')
			,dataPattern : {
// 				_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂
			}
			,columnMeta : [
				 { title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月結案', index:'Month_End' ,dataAttr:"style='text-align:center'"}
				,{ title:A1, index:'Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:A2, index:'Month2' ,dataAttr:"style='text-align:center'"}
				,{ title:A3, index:'Month3' ,dataAttr:"style='text-align:center'"}
				,{ title:A4, index:'Month4' ,dataAttr:"style='text-align:center'"}
				,{ title:'未回覆(當月)', index:'Month_Zero' ,dataAttr:"style='text-align:center'"}
				,{ title:A5, index:'Next_Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:A6, index:'Next_Month2' ,dataAttr:"style='text-align:center'"}
				,{ title:A7, index:'Next_Month3' ,dataAttr:"style='text-align:center'"}
				,{ title:A8, index:'Next_Month4' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月作答總計', index:'Month_All' ,dataAttr:"style='text-align:center'"}
				]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
				
			}
		};
	uiProcess.processQuery(options);
	 if(orgCd =='E77'){
		$("#DOC_ORG_ID").attr("disabled", false);	
	}else{
		$("#DOC_ORG_ID").attr("disabled", true);
	}
}

//顯示6個欄位
function processQuery3()
{
	var RESULT_QA1 =$('#editForm input[name=RESULT_QA1]').val(); 
	var RESULT_QA2 =$('#editForm input[name=RESULT_QA2]').val(); 
	var RESULT_QA3 =$('#editForm input[name=RESULT_QA3]').val(); 
	var RESULT_QA5 =$('#editForm input[name=RESULT_QA5]').val(); 
	var RESULT_QA6 =$('#editForm input[name=RESULT_QA6]').val(); 
	var RESULT_QA7 =$('#editForm input[name=RESULT_QA7]').val(); 
	
	var A1 = RESULT_QA1+"(當月)";
	var A2 = RESULT_QA2+"(當月)";
	var A3 = RESULT_QA3+"(當月)";
	var A5 = RESULT_QA5+"(跨月)";
	var A6 = RESULT_QA6+"(跨月)";
	var A7 = RESULT_QA7+"(跨月)";
	
	var YY = $('#editForm input[name=APPLY_DATE_YEAR]').val();
	var MM = $('#editForm select[name=APPLY_DATE_Mo]').val();

	var mag ="";
	
	if (MM == '' || YY == ''){
		mag +="結案年月不得為空！" ;
		alert(mag);
		return false;
	}

var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
		if(orgCd !='E77'){
			$("#DOC_ORG_ID").removeAttr("disabled");
		}
		
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			//,pagingObj : $('#tableGridPaging')
			,dataPattern : {
// 				_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂
			}
			,columnMeta : [
				 { title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月結案', index:'Month_End' ,dataAttr:"style='text-align:center'"}
				,{ title:A1, index:'Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:A2, index:'Month2' ,dataAttr:"style='text-align:center'"}
				,{ title:A3, index:'Month3' ,dataAttr:"style='text-align:center'"}
				,{ title:'未回覆(當月)', index:'Month_Zero' ,dataAttr:"style='text-align:center'"}
				,{ title:A5, index:'Next_Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:A6, index:'Next_Month2' ,dataAttr:"style='text-align:center'"}
				,{ title:A7, index:'Next_Month3' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月作答總計', index:'Month_All' ,dataAttr:"style='text-align:center'"}
				]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
				
			}
		};
	uiProcess.processQuery(options);
	 if(orgCd =='E77'){
		$("#DOC_ORG_ID").attr("disabled", false);	
	}else{
		$("#DOC_ORG_ID").attr("disabled", true);
	}
}
//顯示4個欄位
function processQuery2()
{
	var RESULT_QA1 =$('#editForm input[name=RESULT_QA1]').val(); 
	var RESULT_QA2 =$('#editForm input[name=RESULT_QA2]').val(); 
	var RESULT_QA5 =$('#editForm input[name=RESULT_QA5]').val(); 
	var RESULT_QA6 =$('#editForm input[name=RESULT_QA6]').val(); 
	
	var A1 = RESULT_QA1+"(當月)";
	var A2 = RESULT_QA2+"(當月)";
	var A5 = RESULT_QA5+"(跨月)";
	var A6 = RESULT_QA6+"(跨月)";
	
	var YY = $('#editForm input[name=APPLY_DATE_YEAR]').val();
	var MM = $('#editForm select[name=APPLY_DATE_Mo]').val();

	var mag ="";
	
	if (MM == '' || YY == ''){
		mag +="結案年月不得為空！" ;
		alert(mag);
		return false;
	}

var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
		if(orgCd !='E77'){
			$("#DOC_ORG_ID").removeAttr("disabled");
		}
		
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			//,pagingObj : $('#tableGridPaging')
			,dataPattern : {
// 				_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂
			}
			,columnMeta : [
				 { title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月結案', index:'Month_End' ,dataAttr:"style='text-align:center'"}
				,{ title:A1, index:'Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:A2, index:'Month2' ,dataAttr:"style='text-align:center'"}
				,{ title:'未回覆(當月)', index:'Month_Zero' ,dataAttr:"style='text-align:center'"}
				,{ title:A5, index:'Next_Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:A6, index:'Next_Month2' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月作答總計', index:'Month_All' ,dataAttr:"style='text-align:center'"}
				]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
				
			}
		};
	uiProcess.processQuery(options);
	 if(orgCd =='E77'){
		$("#DOC_ORG_ID").attr("disabled", false);	
	}else{
		$("#DOC_ORG_ID").attr("disabled", true);
	}
}
//顯示2個欄位
function processQuery1()
{
	var RESULT_QA1 =$('#editForm input[name=RESULT_QA1]').val();  
	var RESULT_QA5 =$('#editForm input[name=RESULT_QA5]').val(); 
	
	var A1 = RESULT_QA1+"(當月)";
	var A5 = RESULT_QA5+"(跨月)";
	
	var YY = $('#editForm input[name=APPLY_DATE_YEAR]').val();
	var MM = $('#editForm select[name=APPLY_DATE_Mo]').val();

	var mag ="";
	
	if (MM == '' || YY == ''){
		mag +="結案年月不得為空！" ;
		alert(mag);
		return false;
	}

var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
		if(orgCd !='E77'){
			$("#DOC_ORG_ID").removeAttr("disabled");
		}
		
	var options = {
			formObj : $('#editForm')
			,gridObj : $('#tableGrid')
			//,pagingObj : $('#tableGridPaging')
			,dataPattern : {
// 				_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂
			}
			,columnMeta : [
				 { title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月結案', index:'Month_End' ,dataAttr:"style='text-align:center'"}
				,{ title:A1, index:'Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:'未回覆(當月)', index:'Month_Zero' ,dataAttr:"style='text-align:center'"}
				,{ title:A5, index:'Next_Month1' ,dataAttr:"style='text-align:center'"}
				,{ title:'當月作答總計', index:'Month_All' ,dataAttr:"style='text-align:center'"}
				]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
				
			}
		};
	uiProcess.processQuery(options);
	 if(orgCd =='E77'){
		$("#DOC_ORG_ID").attr("disabled", false);	
	}else{
		$("#DOC_ORG_ID").attr("disabled", true);
	}
}

function processPrint(jsonResult)
{
	if($('#editForm select[name=QUESTION_ID]').val()==null){
		alert('請選擇題目');
		return false;
	}
	
	formUtil.showJasperReport(document.editForm, urlAction.processPrint);
}



function processReadOnly(){
	//判斷登入帳號是總處還是單一處
	var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
	if(orgCd== 'E77'){
		$("#editForm select[name='DOC_ORG_ID']").attr("disabled", false);
		
		$("#editForm Select[name='DOC_ORG_ID']").val(orgCd);
	    //判斷radio選全處還是單一處
		var Witch = $('#editForm input[name=Organ]:checked').val();
		
		if(Witch == "All"){
			$("#editForm select[name='DOC_ORG_ID']").attr("disabled", true);
		}else{
			$("#editForm select[name='DOC_ORG_ID']").attr("disabled", false);
			
		} 

	}else{
		//預設分處為登入分處
		$("#editForm Select[name='DOC_ORG_ID']").val(orgCd);
		$("#editForm Select[name='DOC_ORG_ID']").attr("disabled", true);
		$("input[name=Organ][value=Single]" , $("#editForm")).attr("checked" ,true);
		$("input[name=Organ][value=All]" , $("#editForm")).attr("disabled" ,true);
	} 
	
	
 	
	
}

function QUS_RESULT() {
		
	var options = {
			formObj : $('#editForm')
			,url : urlAction.QUS_RESULT
			,uiBlockFlag : false
			,onAfterSuccess : function(jsonResult, status) {
				if (jsonResult.data != null) {
					formUtil.bindFormData({
						targetForm : $('#editForm')
						,elementOfHtml : [ ]
						,dataSetOfHtml : jsonResult.data
					});
					$('#editForm input[name=RESULT_QA1]').val(jsonResult.data.RESULT_QA1);
					$('#editForm input[name=RESULT_QA2]').val(jsonResult.data.RESULT_QA2);
					$('#editForm input[name=RESULT_QA3]').val(jsonResult.data.RESULT_QA3);
					$('#editForm input[name=RESULT_QA4]').val(jsonResult.data.RESULT_QA4);
					$('#editForm input[name=RESULT_QA5]').val(jsonResult.data.RESULT_QA5);
					$('#editForm input[name=RESULT_QA6]').val(jsonResult.data.RESULT_QA6);
					$('#editForm input[name=RESULT_QA7]').val(jsonResult.data.RESULT_QA7);
					$('#editForm input[name=RESULT_QA8]').val(jsonResult.data.RESULT_QA8);
			}
			}
		};
	formUtil.submitTo(options);
}


</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnQuery" class="btn" value="查　詢" onclick="processSearch();">
	<input type="button" name="btnQueryExport" class="btn" value="匯　出" onclick="processPrint();">
</content>

<content tag="editBlock">
	<form id="editForm" name="editForm" method="POST" enctype="multipart/form-data">	 
		<input type="hidden" name="PKNO">
			<table class="table-redmond" width="100%">		
              <tr>  
                <th>結案年月</th>
                <td>
                	<input type="hidden" name="PHRASE_TITLE">
                	<input type="hidden" name="DATE">
                	<input name="APPLY_DATE_YEAR" type="text" siza="3"  maxlength="3" value="" >
                	 <select name="APPLY_DATE_Mo" >
                	 	<option value=""> </option>
                	 	<option value="01">一月</option>
                	 	<option value="02">二月</option>
                	 	<option value="03">三月</option>
                	 	<option value="04">四月</option>
                	 	<option value="05">五月</option>
                	 	<option value="06">六月</option>
                	 	<option value="07">七月</option>
                	 	<option value="08">八月</option>
                	 	<option value="09">九月</option>
                	 	<option value="10">十月</option>
                	 	<option value="11">十一月</option>
                	 	<option  value="12">十二月</option>
                	 </select>
                </td>                
              </tr>
              
              
              <tr>
                <th>機關別</th>
                <td>
                <input type="radio" name="Organ" value="All"> 全處  
                <input type="radio" name="Organ" value="Single" checked="checked"> 單一處
                <select name="DOC_ORG_ID" ></select>
                </td>
			    
              </tr>
              <tr>  
                <th>稅目別</th>
                <td>
                <select name="TAX_TP"></select>
                </td>                
              </tr>  
              <tr>  
                <th>問題</th>
                <td>
                <select name="QUESTION_ID" onchange="QUS_RESULT()"></select>
                <input name="RESULT_QA1" type="hidden">
                <input name="RESULT_QA2" type="hidden">
                <input name="RESULT_QA3" type="hidden">
                <input name="RESULT_QA4" type="hidden">
                <input name="RESULT_QA5" type="hidden">
                <input name="RESULT_QA6" type="hidden">
                <input name="RESULT_QA7" type="hidden">
                <input name="RESULT_QA8" type="hidden">
                </td>                
              </tr>                 
            </table>
            
	</form>	
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
	<!-- 	<div id="tableGridPaging"></div> -->
		<div id="tableGrid"></div>
	</form>
</content>
