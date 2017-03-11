<%
/*
@@程式代號 = docrm610m_v1.jsp
@@程式名稱 = 程式名稱-查詢頁
@@程式版本 = V1.000
@@更新日期 = 2016/11/10
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
String PROGRAM_CD	= "DOCRM610M_";
String SCREEN_CD	= "DOCRM610M_01";
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
		processMain     : "../DOCRM610M_Controller.do?command=processMain"
	,processQuery     : "../DOCRM610M_Controller.do?command=processQuery"
	,processDelete    : "../DOCRM610M_Controller.do?command=processDelete"
	,processPrint    : "../DOCRM610M_Controller.do?command=processPrint"
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
					,elementOfHtml : ['TAX_TP','DOC_ORG_ID']
					,dataSetOfHtml : jsonResult.data
				});
				processReadOnly();
				$('input[name=Organ]').bind('click', function(){
					processReadOnly();
				});
				
				$('#editForm input[name=APPLY_DATE_YEAR]').val(jsonResult.data.APPLY_DATE_YEAR);
				$('#editForm select[name=APPLY_DATE_Mo]').val(jsonResult.data.APPLY_DATE_Mo);
				
			}//end onAfterSuccess			
		};

	if (mode == 'reset' && _mainData != null) {
		options.onAfterSuccess(_mainData);
	} else {
		formUtil.submitTo(options);
	}
	


}

function processSearch(){

	
	var YY = $('#editForm input[name=APPLY_DATE_YEAR]').val();

	var MM = $('#editForm select[name=APPLY_DATE_Mo]').val();

	var mag ="";
	
	if (MM == '' || YY == ''){
		mag +="申請年月不得為空！" ;
		alert(mag);
		return false;
	}
	
	var msd="";
	var msg="";
	msd = $('input[name="Organ"]:checked').val();
	msg = $('select[name="TAX_TP"]').val();
	
	if(msd =="Single" && msg ==""){
		processQuery2()
	}else if(msd =="Single" && msg !=""){
		processQuery3()
	}else{
		processQuery()
	}
}

function processQuery()
{	

	
	
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
				 { title:'機關別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'合計申請件數', index:'DOCRM_APPLY' ,dataAttr:"style='text-align:center'"}
				,{ title:'總處', index:'DOC_ORG_ID0' ,dataAttr:"style='text-align:center'"}
				,{ title:'三民分處', index:'DOC_ORG_ID1' ,dataAttr:"style='text-align:center'"}
				,{ title:'小港分處', index:'DOC_ORG_ID2' ,dataAttr:"style='text-align:center'"}
				,{ title:'前鎮分處', index:'DOC_ORG_ID3' ,dataAttr:"style='text-align:center'"}
				,{ title:'楠梓分處', index:'DOC_ORG_ID4' ,dataAttr:"style='text-align:center'"}
				,{ title:'大寮分處', index:'DOC_ORG_ID5' ,dataAttr:"style='text-align:center'"}
				,{ title:'新興分處', index:'DOC_ORG_ID6' ,dataAttr:"style='text-align:center'"}
				,{ title:'鹽埕分處', index:'DOC_ORG_ID7' ,dataAttr:"style='text-align:center'"}
				,{ title:'左營分處', index:'DOC_ORG_ID8' ,dataAttr:"style='text-align:center'"}
				,{ title:'苓雅分處', index:'DOC_ORG_ID9' ,dataAttr:"style='text-align:center'"}
				,{ title:'鼓山分處', index:'DOC_ORG_ID10' ,dataAttr:"style='text-align:center'"}
				,{ title:'鳳山分處', index:'DOC_ORG_ID11' ,dataAttr:"style='text-align:center'"}
				,{ title:'旗山分處', index:'DOC_ORG_ID12' ,dataAttr:"style='text-align:center'"}
				,{ title:'岡山分處', index:'DOC_ORG_ID13' ,dataAttr:"style='text-align:center'"}
				,{ title:'合計移轉件數', index:'ALL_CHANGE' ,dataAttr:"style='text-align:center'"}
				]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
			}
		};

	uiProcess.processQuery(options);
	
}

function processQuery2()
{	
	
	var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
	if(orgCd !='E77'){
		$("#DOC_ORG_ID").removeAttr("disabled");
	}
	var options = {
			formObj : $('#editForm')
// 			,pagingObj : $('#tableGridPaging')
			,gridObj : $('#tableGrid')
			,dataPattern : {
// 			_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
			}
			,columnMeta : [
				 { title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'合計申請件數', index:'DOCRM_APPLY' ,dataAttr:"style='text-align:center'"}
				,{ title:'總處', index:'DOC_ORG_ID0' ,dataAttr:"style='text-align:center'"}
				,{ title:'三民分處', index:'DOC_ORG_ID1' ,dataAttr:"style='text-align:center'"}
				,{ title:'小港分處', index:'DOC_ORG_ID2' ,dataAttr:"style='text-align:center'"}
				,{ title:'前鎮分處', index:'DOC_ORG_ID3' ,dataAttr:"style='text-align:center'"}
				,{ title:'楠梓分處', index:'DOC_ORG_ID4' ,dataAttr:"style='text-align:center'"}
				,{ title:'大寮分處', index:'DOC_ORG_ID5' ,dataAttr:"style='text-align:center'"}
				,{ title:'新興分處', index:'DOC_ORG_ID6' ,dataAttr:"style='text-align:center'"}
				,{ title:'鹽埕分處', index:'DOC_ORG_ID7' ,dataAttr:"style='text-align:center'"}
				,{ title:'左營分處', index:'DOC_ORG_ID8' ,dataAttr:"style='text-align:center'"}
				,{ title:'苓雅分處', index:'DOC_ORG_ID9' ,dataAttr:"style='text-align:center'"}
				,{ title:'鼓山分處', index:'DOC_ORG_ID10' ,dataAttr:"style='text-align:center'"}
				,{ title:'鳳山分處', index:'DOC_ORG_ID11' ,dataAttr:"style='text-align:center'"}
				,{ title:'旗山分處', index:'DOC_ORG_ID12' ,dataAttr:"style='text-align:center'"}
				,{ title:'岡山分處', index:'DOC_ORG_ID13' ,dataAttr:"style='text-align:center'"}
				,{ title:'合計移轉件數', index:'ALL_CHANGE' ,dataAttr:"style='text-align:center'"}
			]};
	
	uiProcess.processQuery(options);
	
	if(orgCd =='E77'){
		$("#DOC_ORG_ID").attr("disabled", false);	
	}else{
		$("#DOC_ORG_ID").attr("disabled", true);
	}
}


function processQuery3()
{	
	var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
	if(orgCd !='E77'){
		$("#DOC_ORG_ID").removeAttr("disabled");
	}
	var options = {
			formObj : $('#editForm')
// 			,pagingObj : $('#tableGridPaging') 
			,gridObj : $('#tableGrid')
			,dataPattern : {
// 			_checkbox 	: "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='@{PKNO}' />"
//  			,_edit		: "<span class='btn-edit' onclick=\"processEdit({PKNO:'@{PKNO}'})\"></span>"
				// 底層已有預設, 以PKNO為預設; 可再自訂	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
			}
			,columnMeta : [
					 { title:'表單種類', index:'DOCRM_TP_NM' ,dataAttr:"style='text-align:center'"}
				 	,{ title:'合計申請件數', index:'DOCRM_APPLY' ,dataAttr:"style='text-align:center'"}
					,{ title:'總處', index:'DOC_ORG_ID0' ,dataAttr:"style='text-align:center'"}
					,{ title:'三民分處', index:'DOC_ORG_ID1' ,dataAttr:"style='text-align:center'"}
					,{ title:'小港分處', index:'DOC_ORG_ID2' ,dataAttr:"style='text-align:center'"}
					,{ title:'前鎮分處', index:'DOC_ORG_ID3' ,dataAttr:"style='text-align:center'"}
					,{ title:'楠梓分處', index:'DOC_ORG_ID4' ,dataAttr:"style='text-align:center'"}
					,{ title:'大寮分處', index:'DOC_ORG_ID5' ,dataAttr:"style='text-align:center'"}
					,{ title:'新興分處', index:'DOC_ORG_ID6' ,dataAttr:"style='text-align:center'"}
					,{ title:'鹽埕分處', index:'DOC_ORG_ID7' ,dataAttr:"style='text-align:center'"}
					,{ title:'左營分處', index:'DOC_ORG_ID8' ,dataAttr:"style='text-align:center'"}
					,{ title:'苓雅分處', index:'DOC_ORG_ID9' ,dataAttr:"style='text-align:center'"}
					,{ title:'鼓山分處', index:'DOC_ORG_ID10' ,dataAttr:"style='text-align:center'"}
					,{ title:'鳳山分處', index:'DOC_ORG_ID11' ,dataAttr:"style='text-align:center'"}
					,{ title:'旗山分處', index:'DOC_ORG_ID12' ,dataAttr:"style='text-align:center'"}
					,{ title:'岡山分處', index:'DOC_ORG_ID13' ,dataAttr:"style='text-align:center'"}
					,{ title:'合計移轉件數', index:'ALL_CHANGE' ,dataAttr:"style='text-align:center'"}
			]};
	
	uiProcess.processQuery(options);
	
	if(orgCd =='E77'){
		$("#DOC_ORG_ID").attr("disabled", false);	
	}else{
		$("#DOC_ORG_ID").attr("disabled", true);
	}
}


function processReadOnly(){
	
	var orgCd = '<%=FdcThreadHolder.getUserProfile().getOrgid()%>';
	if(orgCd== 'E77'){
		$("#editForm select[name='DOC_ORG_ID']").attr("disabled", false);
		$("#editForm Select[name='DOC_ORG_ID']").val(orgCd);
		var Witch = $('#editForm input[name=Organ]:checked').val();
		
		if(Witch == "All"){
			$("#editForm select[name='DOC_ORG_ID']").attr("disabled", true);
		}else{
			$("#editForm select[name='DOC_ORG_ID']").attr("disabled", false);
			
		} 

	}else{
		
	
		$("#editForm Select[name='DOC_ORG_ID']").val(orgCd);
		$("#editForm Select[name='DOC_ORG_ID']").attr("disabled", true);
		$("input[name=Organ][value=Single]" , $("#editForm")).attr("checked" ,true);
		$("input[name=Organ][value=All]" , $("#editForm")).attr("disabled" ,true);
		
	} 
	
	
 	
	
}

function processPrint(jsonResult)
{
	
	
	formUtil.showJasperReport(document.editForm, urlAction.processPrint);
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
                <th>申請年月</th>
                <td>
                	<input type="hidden" name="PHRASE_TITLE">
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
                <input type="radio" name="Organ" value="All" > 全處  
                <input type="radio" name="Organ" value="Single" checked='checked'> 單一處
                <select name="DOC_ORG_ID" ></select>
                </td>
			    
              </tr>
              
              <tr>  
                <th>稅目別</th>
                <td>
                <select name="TAX_TP"></select>
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
