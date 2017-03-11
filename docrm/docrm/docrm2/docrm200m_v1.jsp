<%
/*
@@程式代號 = docrm200m_v1.jsp
@@程式名稱 = 收件及移轉作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="../../global/util/commonFunction.jsp"%>
<head>
<meta name="decorator" content="template_1_1_main">

<%
String PROGRAM_CD	= "DOCRM200M_";
String SCREEN_CD	= "DOCRM200M_01";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>

<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.setBreadCrumb("收件及轉移作業(DOCRM200M_)", "一般", true);
layout.addPortlet("queryBlock", true, "", false);
//layout.addPortlet("editBlock", true, "", true);
layout.addPortlet("listBlock", true, null, false);
Layout.saveLayout(pageContext, layout);
%>

<script type="text/javascript">
var _actionMode	= null;	//畫面目前的模式: Add, Edit
var _mainData	= null; //查詢暫存用
var _editData	= null; //編輯暫存用
var uiFrameset	= parent.uiFrameset; //frameset物件
var uiLayout	= new UiLayout();  //建立區塊切換物件
//--------以上必須存在------------

var urlAction = {
	 processMain         	: "../DOCRM200M_Controller.do?command=processMain"
	,processQuery        	: "../DOCRM200M_Controller.do?command=processQuery"
	,processAcceptDocrm     : "../DOCRM200M_Controller.do?command=processAcceptDocrm"
	,processTranferDocrm    : "../DOCRM200M_Controller.do?command=processTranferDocrm"
	,processGenTxtByDocrm   : "../DOCRM310M_Controller.do?command=processGenTxtByDocrm"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "queryForm")%>

	processMain();
});

function processMain(mode)
{
	var options = {
			url : urlAction.processMain
			,onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;

				formUtil.bindFormData({
					targetForm : $('#queryForm')
					,dataSet : jsonResult.data
					,elementOfHtml : ['TAX_TP', 'DOCRM_TP']
					,dataSetOfHtml : jsonResult.data
				});
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
	var options = {
			formObj : $('#queryForm')
			,gridObj : $('#tableGrid')
			,pagingObj : $('#tableGridPaging')
			,inlineEdit : true
			,dataPattern : {
				
				}
			,columnMeta : [
			         { title:'編輯', dataPatternKey:'_edit'}
					,{ title:'稅目別', index:'TAX_TP_NM'}					
					,{ title:'案件種類', index:'DOCRM_TP_NM'}
					,{ title:'申請人', index:'APPLY_NM'}
					,{ title:'申請日期', index:'APPLY_DATE', format:'dYYY/MM/dd'}
					,{ title:'進度', index:'DOCRM_STUS_NM'}
					,{ title:'登錄機關', index:'ACP_CD_NM'}
					,{ title:'辦理機關', index:'DOC_ORG_ID',  editType:'select', editSource:'DOC_ORG_ID_SELECT', dataAttr:"style='text-align:center'"}
					,{ title:'功能', index:'FUNCTION', dataAttr:"style='text-align:center'"}
					
				]
		};
		uiProcess.processQuery(options);
}

function processEdit(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v2', 'processEdit', jsonResult);
}

function acceptDocrm(valObj, str){
	
	var trObj = valObj.parent().parent();
	var dataObj = {DOC_ORG_ID:trObj.find('select[name=DOC_ORG_ID]').val(), MESS_NO:str};

	var options = {
			 dataObj : dataObj
			,url : urlAction.processAcceptDocrm
			,onAfterSuccess : function(jsonResult, status) {	
				processGenTxtByDocrm(jsonResult.data);
			}
		};
		
		formUtil.submitTo(options);	
}

function tranferDocrm(valObj, str){
	var trObj = valObj.parent().parent();
	var dataObj = {DOC_ORG_ID:trObj.find('select[name=DOC_ORG_ID]').val(), MESS_NO:str};

	if(confirm("案件是否移轉?")){
		var options = {
				 dataObj : dataObj
				,url : urlAction.processTranferDocrm
				,onAfterSuccess : function(jsonResult, status) {
					uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
				}
			};
			
			formUtil.submitTo(options);	
	} 
}

function processGenTxtByDocrm(jsonResult){

	var options = {
			 dataObj : jsonResult
			,url : urlAction.processGenTxtByDocrm
			,onAfterSuccess : function(jsonResult, status) {	
				uiFrameset.switchFrameWithExecute('v1', 'processQuery');
			}
		};
		
		formUtil.submitTo(options);	
}

</script>
</head>

<content tag="topBlock">
		<input type="button" name="btnQuery" class="btn" value="查　詢" onclick="processQuery();">
</content>

<content tag="queryBlock">
	<form id="queryForm" name="queryForm" method="POST">

		<table class="table-redmond" width="100%">		
              <tr>
                <th>稅目別</th>
                <td>
                	<select name="TAX_TP"></select>
                </td>
			    <th>案件種類</th>
                <td>
                	<select name="DOCRM_TP"></select>
                </td>
              </tr>   
              <tr>
                <th>申請人姓名</th>
                <td>
                	<input name="APPLY_NM" type="text" size="10">
                </td>
			    <th>身分證字號</th>
                <td>
                	<input name="APPLY_IDN" type="text" size="10">
                </td>
              </tr>  
              <tr>
                <th>申請日期</th>
                <td colspan='3'>
                	<input type="TEXT" name="START_APPLY_DATE" class="calendar"  >至<input type="TEXT" name="END_APPLY_DATE" class="calendar"  >
                </td>
              </tr>  
                                
        </table>
	</form>
</content>

<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div id="tableGridPaging"></div>
		<div id="tableGrid"></div>
	</form>
</content>

