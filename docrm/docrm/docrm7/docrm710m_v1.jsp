<%
/*
@@程式代號 = docrm710m_v1.jsp
@@程式名稱 = 程式名稱-查詢頁
@@程式版本 = V1.000
@@更新日期 = 2016/10/17
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
String PROGRAM_CD	= "DOCRM710M_";
String SCREEN_CD	= "DOCRM710M_01";
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
	 processMain     : "../DOCRM710M_Controller.do?command=processMain"
	,processQuery     : "../DOCRM710M_Controller.do?command=processQuery"
	,processEdit         : "../DOCRM710M_Controller.do?command=processEdit"	
	,processEditSave     : "../DOCRM710M_Controller.do?command=processEditSave"	
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
					,elementOfHtml : ['TAX_TP','DOCRM_TP','ANS_STUS']
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
				_button : "<input type='button' class='btn' value='作答' onclick=\"processEdit({PKNO:'@{PKNO}',DOCRM_NO:'@{DOCRM_NO}',ADD_STUS:'@{ADD_STUS}'})\">"
				// 底層已有預設, 以PKNO為預設; 可再自訂
			}
			,columnMeta : [
				 { title:'案件編號', index:'DOCRM_NO' ,dataAttr:"style='text-align:center'"}
				,{ title:'稅目別', index:'PHRASE_TITLE' ,dataAttr:"style='text-align:center'"}
				,{ title:'案件種類', index:'DOCRM_TP_NM' ,dataAttr:"style='text-align:center'"}
				,{ title:'申請人', index:'APPLY_NM' ,dataAttr:"style='text-align:center'"}
				,{ title:'申請日期', index:'APPLY_DATE' ,dataAttr:"style='text-align:center'"}
				,{ title:'完成日期', index:'CPL_DATE' ,dataAttr:"style='text-align:center'"}
				,{ title:'作答狀態', index:'ADD_STUS' ,dataAttr:"style='text-align:center'"}					
				,{ title:'辦理機關', index:'DOG_ORG' ,dataAttr:"style='text-align:center'"}
				,{ title:'承辦人', index:'DOC_STF_NM' ,dataAttr:"style='text-align:center'"}
				,{ title:'功能', dataPatternKey:'_button', dataAttr:"style='text-align:center;'"}
				
				]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
			}
		};

	uiProcess.processQuery(options);
}



function processEdit(jsonResult)
{
	
	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
	
}







</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnQuery" class="btn" value="查　詢" onclick="processQuery();">	
</content>

<content tag="editBlock">
	<form id="editForm" name="editForm" method="POST" enctype="multipart/form-data">	 
		<input type="hidden" name="PKNO">
			<table class="table-redmond" width="100%">		
              <tr>
                <th width="20%">稅目別</th>
                <td>
                	<select name="TAX_TP"></select>
                </td>  
              </tr>
              <tr>  
                <th width="20%">案件種類</th>
                <td>
              	 	<select name="DOCRM_TP"></select>
                </td>        
              </tr> 
              <tr>  
                <th>案件編號</th>
                <td>
                	<input type='text' name='DOCRM_NO'/>
                </td>                
              </tr>
              <tr>  
                <th>申請人</th>	
                <td>
                	<input type='text' name='APPLY_NM'/>
                </td>                
              </tr>
              <tr>  
                <th>身分證字號</th>
                <td>
                	<input type='text' name='APPLY_IDN' maxlength="10"/>
                </td>                
              </tr>  
              <tr>  
                <th>狀態</th>
                <td>
                	<select name="ANS_STUS"></select>
                </td>                
              </tr>
               <tr>  
                <th>申請日期</th>
                <td>
                	<input type='hidden' name='APPLY_DATE'/>
                	<input type='text' name="APPLY_DATE_B" class="calendar"  >至<input type='text' name="APPLY_DATE_E" class="calendar"  >
               
                </td>                
              </tr>
            </table>
            
	</form>	
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div class='buttons' style='clear: both' align='left'>
		</div>
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>
