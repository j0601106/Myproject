<%
/*
@@程式代號 = ACH_V1.jsp
@@程式名稱 = 可申請撤案明細頁面(處理單)_(明細)
@@程式版本 = V1.000
@@更新日期 = 2012/10/03
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="../../global/util/commonFunction.jsp"%>

<head>
<meta name="decorator" content="template_1_2_main">
<%
Layout layout= Layout.createLayout(Layout.LayoutBaseType.UserBased);
//layout.setBreadCrumb("情境維護", "一般", true);
layout.addPortlet("queryBlock", false, "", false,false);
layout.addPortlet("editBlock", false, "", false, false);
layout.addPortlet("listBlock", true, "需求單-撤案", false);
layout.saveLayout(pageContext, layout);
%>
<%
String PROGRAM_CD	= "ACH";
String SCREEN_CD	= "ACH_V1";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>

<script type="text/javascript">
var _actionMode	= null;	//畫面目前的模式: Add, Edit
var _mainData	= null; //查詢暫存用
var _editData	= null; //編輯暫存用
var uiFrameset	= parent.uiFrameset; //frameset物件
var uiLayout	= new UiLayout();  //建立區塊切換物件
//--------以上必須存在------------

var urlAction = {
	 processMain      : "../apply_proc_channel_detail_Controller.do?command=processMain"
	,processQuery     : "../apply_proc_channel_detail_Controller.do?command=processQuery"
	,processDelete    : "../apply_proc_channel_detail_Controller.do?command=processDelete"
    ,processFileDownload: "../apply_proc_channel_detail_Controller.do?command=processFileDownload" //download

};

$(document).ready(function()
{
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1());
	
	<%=getDDValidation(PROGRAM_CD, SCREEN_CD, "queryForm")%>
	
	processQuery();
});

function processMain(mode)
{
	var options = {
			url : urlAction.processMain
			,onAfterSuccess : function(jsonResult, status) {
				_mainData = jsonResult;	

				formUtil.bindFormData({
					targetForm : $('#listForm')
					,elementOfHtml : ['CITY_CODE', 'BLOOD_CODE']
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
			,dataPattern : {
				// 底層已有預設, 以PKNO為預設; 可再自訂
				_checkbox : "<input type='checkbox' name='gridDeleteCheck' value='@{PROC_PRJ_ID},@{PROC_ORG_COD},@{PROC_SYS_COD},@{PROC_CATG_COD},@{PROC_SN}'>"
				,_button:"<input type='button' class='btn' value='撤　案' "
				          +"onclick=\"processAdd({PROC_TKT:'@{PROC_TKT}',PROC_PRJ_ID :'@{PROC_PRJ_ID}',PROC_ORG_COD:'@{PROC_ORG_COD}',SYS_NM:'@{SYS_NM}',PROC_SYS_COD:'@{PROC_SYS_COD}'"
				        	                      +",PROC_CATG_COD:'@{PROC_CATG_COD}',PROC_SN:'@{PROC_SN}',EVENT_TITL:'@{EVENT_TITL}',EVENT_DTL:'@{EVENT_DTL}',PROC_TP_COD:'ACH'})\"></span>"
				        	                      ,_button_1:"<input type='button' class='btn' value='下　載' onclick=\"processDownload({ORGNAME:'@{ORGNAME}',FILENAME:'@{FILENAME}',PROC_CATG_COD:'@{PROC_CATG_COD}'})\"></span>" //download

			}
			,columnMeta : [
                /* { title:null, dataPatternKey:'_checkbox', dataAttr:"style='text-align:center'"}
                ,*/ 
             //   { title:'處理單類別', index:'PROC_CATG_DESC', dataAttr:"style='text-align:center'"}
                 { title:'需求管制單號', index:'PROC_TKT', dataAttr:"style='text-align:center'"}
                ,{ title:'機關名稱', index:'ORG_NM', dataAttr:"style='text-align:center'"}

        		,{ title:'提案帳號', index:'PRPS_ACCT', dataAttr:"style='text-align:center'"} 
    			
				,{ title:'系統', index:'SYS_NM', dataAttr:"style='text-align:center'"}
				,{ title:'需求狀態', index:'PROC_TP_NM', dataAttr:"style='text-align:center'"}
				,{ title:'需求類別', index:'EVENT_TITL', dataAttr:"style='text-align:center'"}
				,{ title:'需求說明', index:'EVENT_DTL'}
				,{ title:'提案日', index:'PRPS_DT', dataAttr:"style='text-align:center'"}
			//	,{ title:'上傳檔案名稱', index:'ORGNAME'}
				,{ title:'檔案下載', indexPattern:"<a href=javascript:processDownload({ORGNAME:'@{ORGNAME}',FILENAME:'@{FILENAME}',PROC_CATG_COD:'@{PROC_CATG_COD}'})>@{ORGNAME}" ,dataAttr:"style='text-align:center'" }

				//,{ title:'應結案日期', index:'SHD_CLS_DT', dataAttr:"style='text-align:center'"}
				,{ title:'申請', dataPatternKey:'_button', dataAttr:"style='text-align:center'"}
			]
			,onInitLayout : function(jsonResult, status) {
				uiLayout.queryResultLayout();
			}
		};

	uiProcess.processQuery(options); 
}

/*function processAdd(jsonResult)
{
	uiFrameset.switchFrameWithExecute('v3', 'processQuery', jsonResult);
}*/

function processAdd(jsonResult)
{
	//alert("跳下一頁"+JSON.stringify(jsonResult));
	uiFrameset.switchFrameWithExecute('v2', 'processAdd', jsonResult);
}

function processDelete()
{
	uiProcess.processDelete($('#listForm input[name=gridDeleteCheck]:checked'));
}

function processView(jsonResult)
{
	alert("跳下一頁"+JSON.stringify(jsonResult));
	uiFrameset.switchFrameWithExecute('v2', 'processQuery', jsonResult);
}
function processDownload(obj)
{
	 formUtil.download(obj, urlAction.processFileDownload);
}
</script>
</head>
<content tag="topBlock">

</content>

<content tag="queryBlock">
    <form id="queryForm" name="queryForm" method="POST">
        <input type="hidden" id="PROC_CATG_COD" name="PROC_CATG_COD" value="D">
		<input type="hidden" id="PROC_TP_COD" name="PROC_TP_COD" value="ACH">
		<table class="table-redmond" style='width:100%'>
			
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

<!-- <content tag="listBlock"> -->
<!-- 	<form id="listForm" name="listForm"> -->
<!-- 		<div class='buttons' style='clear: both' align='left'> -->
<!-- 		</div> -->
<!-- 		<div id="tableGridPaging"></div> -->
<!-- 		<div id="tableGrid"></div> -->
<!-- 	</form> -->
<!-- 	</content> -->
