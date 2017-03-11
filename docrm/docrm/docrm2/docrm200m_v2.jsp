<%
/*
@@程式代號 = docrm200m_v2.jsp
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
String SCREEN_CD	= "DOCRM200M_02";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>
<%
//customize Layout
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
layout.addPortlet("editBlock1", true, "案件內容", true);
layout.addPortlet("editBlock2", true, "附件上傳", true);
layout.addPortlet("listBlock1", true, "案件歷程", false);
layout.addPortlet("listBlock2", true, "已夾帶附件", false);
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
	  processEdit            : "../DOCRM200M_Controller.do?command=processEdit"
	 ,processQueryDetail1    : "../DOCRM200M_Controller.do?command=processQueryDetail1"   
	 ,processQueryDetail2    : "../DOCRM200M_Controller.do?command=processQueryDetail2"   
	 ,processAcceptDocrm     : "../DOCRM200M_Controller.do?command=processAcceptDocrm"
	 ,processTranferDocrm    : "../DOCRM200M_Controller.do?command=processTranferDocrm"
	 ,processUpload          : "../DOCRM200M_Controller.do?command=processUpload"
	 ,processDownload        : "../DOCRM200M_Controller.do?command=processDownload"
};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate1());

	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm1")%>
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "editForm2")%>

	formUtil.enableFileUpload('editForm2');
});

function processEdit(paramObj, mode){
	_actionMode = 'Edit';
	uiLayout.editLayout();

	var options = {
		dataObj : paramObj
		,url : urlAction.processEdit
		,onAfterSuccess : function(jsonResult, status) {
			_editData = jsonResult;
		
			formUtil.clearFormData($('#editForm1'));
			formUtil.bindFormData({
				targetForm : $('#editForm1')
				,dataSet : jsonResult.data.editData
				,elementOfHtml : ['DOC_ORG_ID', 'ADD_MK']
				,dataSetOfHtml : jsonResult.data
			});		
			$('#TAX_TP_NM').html(jsonResult.data.editData.TAX_TP_NM);
			$('#DOCRM_TP_NM').html(jsonResult.data.editData.DOCRM_TP_NM);
			$('#ACP_CD_NM').html(jsonResult.data.editData.ACP_CD_NM);
			$('#APPLY_NM').html(jsonResult.data.editData.APPLY_NM);
			$('#APPLY_IDN').html(jsonResult.data.editData.APPLY_IDN);
			$('#APPLY_SUBJECT').html(jsonResult.data.editData.APPLY_SUBJECT);
			$('#APPLY_DATE').html(jsonResult.data.editData.APPLY_DATE);
			$('#MOBILE_NO').html(jsonResult.data.editData.MOBILE_NO);
			$('#EMAIL').html(jsonResult.data.editData.EMAIL);
			$('#DOCRM_STUS_NM').html(jsonResult.data.editData.DOCRM_STUS_NM);
			$('#MESS_NO2').val(jsonResult.data.editData.MESS_NO);
			processQueryDetail1();
			processQueryDetail2();
		}//end onAfterSuccess
	};


	if (mode == 'reset' && _editData != null) {
		options.onAfterSuccess(_editData);
	} else {
		formUtil.submitTo(options);
	}

}

function processQueryDetail1(){

	var options = {
			 formObj : $('#editForm1')
			,url : urlAction.processQueryDetail1
			,gridObj : $('#tableGrid1')
			,columnMeta : [
                     { title:'案件狀態', index:'DOCRM_STUS_NM'}					
					,{ title:'移轉機關', index:'TRN_ORG_ID_NM'}
					,{ title:'時間', index:'SD_DATE_TIME'}
				]
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processQueryDetail2(){

	var options = {
			 formObj : $('#editForm1')
			,url : urlAction.processQueryDetail2
			,gridObj : $('#tableGrid2')
			,columnMeta : [
                     { title:'附件檔名', index:'FILE_NM'}					
					,{ title:'來源', index:'SOURCE_NM'}
					,{ title:'時間', index:'SD_DATE_TIME'}
					,{ title:'進度', index:'FILE_STUS_NM'}
					,{ title:'功能', index:'FUNCTION', dataAttr:"style='text-align:center'"}
				]
		};
	  uiProcess.processQuery(options);      
	  formUtil.submitTo(options);
}

function processQueryDetail3(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v3', 'processQueryDetail3', jsonResult);
}

function acceptDocrm(){

	var options = {
			 formObj : $('#editForm1')
			,url : urlAction.processAcceptDocrm
			,onAfterSuccess : function(jsonResult, status) {
				uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
			}
		};
		
		formUtil.submitTo(options);	
}

function tranferDocrm(){
	
	if(confirm("案件是否移轉?")){
		var options = {
				 formObj : $('#editForm1')
				,url : urlAction.processTranferDocrm
				,onAfterSuccess : function(jsonResult, status) {
					uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
				}
			};
			
			formUtil.submitTo(options);	
	}
}

function download(){
  	formUtil.download({'MESS_NO':$('#MESS_NO').val()}, urlAction.processDownload);
}

function upload(){
	
	var options = {
			 formObj : $('#editForm2')
			,url : urlAction.processUpload
			,onAfterSuccess : function(jsonResult, status) {
				formUtil.clearFormData($('#editForm2'));
				uiFrameset.switchFrameWithExecute('v1', 'processQuery', jsonResult);
			}
		};
		
		formUtil.submitTo(options);	
}

function processBack()
{
	uiFrameset.switchFrame('v1');
}

</script>
</head>

<content tag="topBlock">
	<input type="button" name="btnBack" class="btn" value="回上頁" onclick="processBack();">
	<input type="button" name="btn"  class="btn" value="移　轉" onclick="tranferDocrm();">
	<input type="button" name="btn"  class="btn" value="收　件" onclick="acceptDocrm();">
	<input type="button" name="btn"  class="btn" value="查看申請書 " onclick="download();" >
</content>

<content tag="editBlock1">
	<form id="editForm1" name="editForm1" method="POST">
		<input type="hidden" name="MESS_NO">
		<table class="table-redmond" width="100%">
        	  <tr>
                <th width="30%" >案件編號</th>
                <td>
                	<label id="DOCRM_NO"></label>
                </td>
              </tr>
              <tr>
                <th>稅目別</th>
                <td>
                	<label id="TAX_TP_NM"></label>
                </td>
              </tr>
              <tr>
                <th>案件種類</th>
                <td>
                	<label id="DOCRM_TP_NM"></label>
                </td>
              </tr>
              <tr>
                <th>登錄機關</th>
                <td>
                	<label id="ACP_CD_NM"></label>
                </td>
              </tr>
              <tr>
                <th>公文文號</th>
                <td>
                </td>
              </tr>
              <tr>
                <th>驗證碼</th>
                <td>
                </td>
              </tr>
              <tr>
                <th>申請人姓名</th>
                <td>
                	<label id="APPLY_NM"></label>
                </td>
              </tr>
              <tr>
                <th>身分證字號</th>
                <td>
                	<label id="APPLY_IDN"></label>
                </td>
              </tr>
              <tr>
                <th>申請主旨</th>
                <td>
                	<label id="APPLY_SUBJECT"></label>
                </td>
              </tr>
              <tr>
                <th>申請日期</th>
                <td>
                	<label id="APPLY_DATE"></label>
                </td>
              </tr>
              <tr>
                <th>預計完成日期</th>
                <td>
                </td>
              </tr>
              <tr>
                <th>完成日期</th>
                <td>
                </td>
              </tr>
              <tr>
                <th>連絡電話</th>
                <td>
                	<label id="MOBILE_NO"></label>
                </td>
              </tr>
              <tr>
                <th>EMAIL郵件</th>
                <td>
                	<label id="EMAIL"></label>
                </td>
              </tr>
              <tr>
                <th>進度</th>
                <td>
                	<label id="DOCRM_STUS_NM"></label>
                </td>
              </tr>
              <tr>
                <th>是否需要補件</th>
                <td>
                	<input type='checkbox' name='ADD_MK' />
                </td>
              </tr>
              <tr>
                <th>補件狀況</th>
                <td>
                </td>
              </tr>
              <tr>
                <th>辦理機關</th>
                <td>
                	<select name="DOC_ORG_ID"></select>
                </td>
              </tr>
              <tr>
                <th>承辦人</th>
                <td>
                </td>
              </tr>
        </table>
	</form>
</content>

<content tag="listBlock1">
	<form id="listForm1" name="listForm1">
		<div id="tableGrid1"></div>
	</form>
</content>

<content tag="editBlock2">
	<form id="editForm2" name="editForm2" method="POST">
		<input type="hidden" name="MESS_NO2">
		<table class="table-redmond" width="100%">
			<tr>
				<td>
                	<%=fileUpload("COMP", "", "1")%>
                	<input type="button" name="btn"  class="btn" value="上   傳" onclick="upload();">
                </td>
            </tr>
        </table>
	</form>
</content>

<content tag="listBlock2">
	<form id="listForm2" name="listForm2">
		<div id="tableGrid2"></div>
	</form>
</content>
