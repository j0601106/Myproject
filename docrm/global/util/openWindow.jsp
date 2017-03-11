<%
/*
@@程式代號 = openWindow.jsp
@@程式名稱 = 共用開窗頁
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ include file="../../global/util/commonFunction.jsp"%>
<head>
<META name="decorator" content="template_1_1_main">

<%
String PROGRAM_CD	= "XYZ101M_";
String SCREEN_CD	= "XYZ101M_01";
initPage(PROGRAM_CD, SCREEN_CD, pageContext);
%>

 
<script>
var uiFrameset = parent.uiFrameset; //frameset controller
var _actionMode	= null;	//畫面目前的模式: Add, Edit
var _mainData	= null; //查詢暫存用
var _editData	= null; //編輯暫存用
var uiLayout = new UiLayout();  //建立區塊切換物件
//--------以上必須存在------------
var chooseId=''; 
var args = window.dialogArguments;
var allResult = null;


$(document).ready(function(){
	//初始化區塊切換物件
	uiLayout.init(getLayoutOption1(), getBlockTemplate4());
//	uiLayout.init(getLayoutOption1());

//	uiLayout.init(layoutOption1);
	processMain();
});


function processMain() {
	
	args = openWindowUtil.pre(args);
	
	//顯示麵包屑、機密等級
	if(args.showTitle == ''){
		$('#confidentialLevel').hide();
		$('#breadCrumbContent').hide();
	}else if(args.showTitle == 'confidentialLevel'){
		$('#breadCrumbContent').hide();
	}else if(args.showTitle == 'breadCrumb'){
		$('#confidentialLevel').hide();
	}
	$('#confidentialLevel').text(args.confidentialLevel);
	$('#functionTitle').text(args.breadCrumb);
	
	
	
	var sqlId = args.querySqlId;	
	if(args.params!=null){
		for(var i = 0; i < args.params.length; i++){
			var input = $('<input>');
			input.attr({
				type   : 'hidden'
				,id    : args.params[i].key
				,name  : args.params[i].key
				,value : args.params[i].value
			});
			$('#queryForm').append(input);
		}
	}
	
	
	
	if(args.queryColumns!=null){
		var tr = $('<tr>');
		
		for(var i = 0; i < args.queryColumns.length; i++) {
			
			var th = $('<th>');
			th.append(args.queryColumns[i].title);
			tr.append(th);
			var td = $('<td>');
			
			if (args.queryColumns[i].type == 'text') {
				var input = $('<input>');
				input.attr({
					type  : 'text'
					,id   : args.queryColumns[i].value
					,name : args.queryColumns[i].value
					//按下Enter時直接reutrn不submit
					,onKeyDown : 'if(window.event.keyCode==13){return false;}'
				});
				td.append(input);
			} else if (args.queryColumns[i].type == 'select'){
				var selectForm=$('<form>');
				var selectVar=args.queryColumns[i].value;
				var selectText=args.queryColumns[i].text;
				var selectSqlId=args.queryColumns[i].sqlId;
				var selectSqlIdInput=$('<input>');
				selectSqlIdInput.attr({
					id     : 'sqlId'
					,name  : 'sqlId'
					,value : selectSqlId
				});
				selectForm.attr({
					id     : 'selectForm'
					,name     : 'selectForm'
					,method     : 'POST'
				});
				
				if(args.params!=null){
					for(var j = 0; j < args.params.length; j++){
						var input = $('<input>');
						input.attr({
							type   : 'hidden'
							,id    : args.params[j].key
							,name  : args.params[j].key
							,value : args.params[j].value
						});
						selectForm.append(input);
					}
				}
				
				
				selectForm.append(selectSqlIdInput);
				
				
				var select = $('<select>');
				
				var valuePattern = selectVar;
				var ValueID = valuePattern.substring(valuePattern.indexOf("@{") + 2, valuePattern.indexOf("}"));
				valuePattern = valuePattern.replace("@{" + ValueID + "}", ValueID);
				if(valuePattern.indexOf("--") >= 0) {
					var valuePatternTemp = valuePattern.substring(valuePattern.indexOf("--")+2);
					var ValueIDS = new Array();
					ValueIDS = valuePatternTemp.split("@{");
					for (var k = 1 ; k < ValueIDS.length ; k++) {
						ValueIDS[k] = ValueIDS[k].substring(0, ValueIDS[k].indexOf("}"));
					}
					
					for (k = 1 ; k < ValueIDS.length ; k++) {
						valuePatternTemp = valuePatternTemp.replace("@{" + ValueIDS[k] + "}", ValueIDS[k]);
					}
					valuePattern = valuePattern.substring(0,valuePattern.indexOf("--")) + "--" + valuePatternTemp;
				}
				
				select.attr({
					id     : valuePattern
					,name  : valuePattern
				});
				
				var options  = {
						formObj : selectForm
						,url : args.url
						,onAfterSuccess : function(jsonResult, status) {
							selectOptionUtil.render({
								dataExtra : {value:'',text:'請選擇'} //json物件型態
								,dataExtraDirection : 'first' //or last 加在最前or最後
								,targetObj : select    //目標物件
								,dataObj : jsonResult.data.GRID_RESULT   //資料來源 json
								,textPattern : selectText        //文字樣板----NEW
								,valuePattern : selectVar      //資料樣板
							});
						}
						,uiBlockFlag : false
					};
				formUtil.submitTo(options); 
				selectForm.empty();
				td.append(select);
			}
			tr.append(td);
		}
		
		$('#queryTable').append(tr);
	}
	
	//加入sqlId
	var inputSQL=$('<input>');
	inputSQL.attr({
		type   : 'hidden'
		,id    : 'sqlId'
		,name  : 'sqlId'
		,value : sqlId
	});
	$('#queryTable').append(inputSQL);
	
	//加入pageSize
	var pageSize=$('<input>');
	pageSize.attr({
		type   : 'hidden'
			,id    : 'PAGE_SIZE'
			,name  : 'PAGE_SIZE'
			,value : args.pageSize
		});
	$('#queryTable').append(pageSize);
	
	
	if(args.dsType != null){
		var inputDsType=$('<input>');
		inputDsType.attr({
			type   : 'hidden'
			,id    : 'dsType'
			,name  : 'dsType'
			,value : args.dsType
		});
		$('#queryTable').append(inputDsType);
		
	}
	
	
	if (args.queryColumns == null) {
		$('#topBlock').fdcBlock('close');
		$('#queryBlock').fdcBlock('close');
	} 
	processQuery();
	
}


function gridChoose(){
	
	//刪除pkno(因會覆蓋掉editForm的pkno)
	for(var i=0;i<allResult.length;i++){
		delete allResult[i].PKNO;
	}
	var choosed =new Array();
	
	if(chooseId != ''){
		$(chooseId + ':checked').each(function(i){
			try {
				var org = $(this).val();
				var val = allResult[org];
				choosed.push(val);
			} catch(e) {
				alert(e);
			}
		});
	}

	if (choosed.length == 0) {
		alert('請選擇');
	} else {
		window.returnValue = choosed;
		self.close();
	}
	
}

function processQuery(){
	
	formUtil.submitTo({
		formObj :  $('#queryForm')
		,url : args.url
		,onAfterSuccess : function(jsonResult, status) {
			
			//change layout
			allResult = jsonResult.data.GRID_RESULT;
			//var columnOption = openWindowUtil.creatColumn(args);
			var dataPattern =	{  
				_radio: 	"<input type='radio' id='gridSelectRadio' name='gridSelectRadio' value='@{PKNO}' onclick='gridChoose()' />"
			};
			
			if(!args.showSelectBtn){
				args.dataPattern = $.extend({},dataPattern,args.dataPattern);
				
			}
			
			//加入序號
			var isAddSno = true;
			for(var i=0;i<args.columnMeta.length;i++){
				if(args.columnMeta[i].index == '_SNO'){
					isAddSno = false;
					break;
				}
			}
			if(isAddSno){
				args.columnMeta.unshift({ title:'項次', index:'_SNO'});
			}
			
			
			var queryOption = {
				gridObj : $('#tableGrid')
				, pagingObj : $('#tableGridPaging')
				, data : jsonResult.data.GRID_RESULT
				, dataPattern : args.dataPattern
				, tableMeta : "class='table-redmond-grid' width='100%'"
				, columnMeta : args.columnMeta
				, pageInfo :
					{
						targetPagingObj : $('#tableGridPaging')
						,queryFormObj : $('#queryForm')
						,pagingData : jsonResult.data.PAGE_INFO
					}

			};
			var uiTableGrid = new UiTableGrid();
			uiTableGrid.render(queryOption);
			
			//按下Enter時直接reutrn
			$('input[name$=PAGE_NO]').attr( 'onKeyDown' , 'if(window.event.keyCode==13){ return false;}');
			
			
			//將radio的值變為index
			var checkBoxCount=$('input[name=gridDeleteCheck]').length;
			var radioCount=$('input[name=gridSelectRadio]').length;
			if(radioCount > 0){
				chooseId = 'input[id=gridSelectRadio]';
				//隱藏radio
 				$('td[column$=null],th[column$=null]','#listForm').hide();
				//點tr時即選取此列
				$('tr','#listForm').each(function(){
					$(this).click(function(e){
						e.stopPropagation();
						$('input[type=radio]',$(this)).attr('checked','checked').click();
					});
				});
				//隱藏選取btn
				$('input[name=btnSelect]').hide();
			}else if (checkBoxCount > 0){
				chooseId = 'input[name=gridDeleteCheck]';
			}
			if(chooseId != ''){
				$(chooseId).each(function(i){
					$(this).val(i);
					
				});
			}
			
			
			args.onAfterSuccess(jsonResult, status);
			
			
			
		}
		,onInitLayout : function(jsonResult, status) {
			uiLayout.queryResultLayout();
		}
		, uiBlockFlag : false
	}); //end submit
	
}

</script>

</head>
<content tag="topBlock">
	<input type="button" name="btnQuery" class="btn" value="查　詢" onClick="processQuery()">
</content>

<content tag="queryBlock">
	<form id="queryForm" name="queryForm" method="POST">
		<table border="1" class="table-redmond" width='100%' id="queryTable">
		</table>
	</form>
</content>
	
</content>
<content tag="listBlock">
	<form id="listForm" name="listForm">
		<div class='buttons' style='clear: both' align='left'>
			<input type="button" name="btnSelect" class="btn" value="選　取" onClick="gridChoose()">
		</div>
		<div id='tableGrid'></div>
		<div id="tableGridPaging"></div>
	</form>
</content>

