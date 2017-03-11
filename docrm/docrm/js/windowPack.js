/**
 * 註冊此系統提供的所有開窗程式
 */
var windowPack = {

	qic501m : function(selector)  {
		var url = '../qicz/qicOrgOpenWin_v1.jsp?choosetype=checkbox';
		uiCustomWindow.open(url, 600, 500, selector, 'qicOrgOpenWin');
	},
	
	qic701m : function(selector)  {
		var url = '../qic7/qic701m_window.jsp';
		//param: url, width, height, selector, openedWindowName
		uiCustomWindow.open(url, 600, 500, selector, 'qic701m');
	},
	
	qic302m : function(selector, _index, _slryDistYm, _slryPayKd,_projCd,_slryKd)  {
		var url = '../qic3/qic302m_window.jsp?choosetype=radio&_index=' + _index + '&_slryDistYm=' + _slryDistYm 
					+ '&_slryPayKd=' + _slryPayKd+ '&_projCd=' + _projCd+ '&_slryKd=' + _slryKd;
		uiCustomWindow.open(url, 600, 500, selector, 'qic302m_window');
	},
	
	qic510m : function(selector, _index)  {
		var url = '../qic5/qic510m_window.jsp?choosetype=radio&_index=' + _index;
		uiCustomWindow.open(url, 600, 500, selector, 'qic510m_window');
	},
	 
	qic510mCheck : function(selector, _index)  {
		var url = '../qic5/qic510m_window.jsp?choosetype=checkbox&_index=' + _index;
		uiCustomWindow.open(url, 600, 500, selector, 'qic510m_window');
	},
	
	qic608r : function(selector)  {
		var url = '../qic6/qic608r_window.jsp?choosetype=checkbox';
		uiCustomWindow.open(url, 600, 500, selector, 'qic608r_window');
	},
	
	qic608rSctnCd : function(selector)  {
		var url = '../qic6/qic608r_window.jsp?choosetype=leftFrameCheckbox';
		uiCustomWindow.open(url, 600, 500, selector, 'qic608r_window');
	},
	
	qic608rRadio : function(selector, _slryGroupCd)  {
		var url = '../qic6/qic608r_window.jsp?choosetype=radio&slryGroupCd=' + _slryGroupCd;
		uiCustomWindow.open(url, 600, 500, selector, 'qic608r_window');
	},
	
	//組織圖開窗
	qicOrgOpenWin : function(selector, _index, _slryGroupCd, _slryPayKd)  {
		var url = '../qicz/qicOrgOpenWin_v1.jsp?choosetype=radio&_index=' + _index + '&_slryGroupCd=' + _slryGroupCd + '&_slryPayKd=' + _slryPayKd;
		uiCustomWindow.open(url, 600, 500, selector, 'qicOrgOpenWin');
	},
	
	//修改身分證開窗
	qicModifyIDNManager : function(selector, callback) {
		var IDN = selector['IDN']
			,EMPL_CD = selector['EMPL_CD']
			,PROGRAM_CD = selector['PROGRAM_CD']
			,IDN_UPD_KD = selector['IDN_UPD_KD'];
			
		var url = '../qicz/qicModifyIDNManager.jsp?IDN=' + IDN + '&EMPL_CD=' + EMPL_CD + '&PROGRAM_CD=' 
			+ PROGRAM_CD + '&IDN_UPD_KD=' + IDN_UPD_KD;
		
		uiCustomWindow.open(url, 600, 500, callback, 'qicModifyIDNManager');
	},
	
	//修改員工編號開窗
	qicModifyEmplCdManager : function(selector, callback) {
		var EMPL_CD = selector['EMPL_CD']
			 ,PROGRAM_CD = selector['PROGRAM_CD'];
			
		var url = '../qicz/qicModifyEmplCdManager.jsp?EMPL_CD=' + EMPL_CD + '&PROGRAM_CD=' 
			+ PROGRAM_CD;
		
		uiCustomWindow.open(url, 600, 500, callback, 'qicModifyEmplCdManager');
	},
	
	//開窗-員工姓名
	qicEmplNM : function(id, formObj, bindNM, options){
		
		var myOptions = {
				buttonObj : formObj.find('#btn'+id)
				,bindFormObj : formObj
				,pageSize : 50
				,columnMeta : 
						[
							{ title:'選取', dataPatternKey:'_radio'}
							,{ title:'科室', index:'SCTN_NM' }
							,{ title:'職稱', index:'JTITLE_NM'}
							,{ title:'員工編號', index:'EMPL_CD' }
							,{ title:'員工姓名', index:'EMPL_NM'}
							,{ title:'職等', index:'JOBRANK',dataAttr:"style='display:none'", titleAttr:"style='display:none'" }
							,{ title:'薪別', index:'SLRY_TP',dataAttr:"style='display:none'", titleAttr:"style='display:none'" }
							,{ title:'薪別', index:'SLRY_TP_NM',dataAttr:"style='display:none'", titleAttr:"style='display:none'" }
							,{ title:'職級', index:'GRD',dataAttr:"style='display:none'", titleAttr:"style='display:none'" }
							,{ title:'權理職等', index:'DUTYRANK_JOBRANK',dataAttr:"style='display:none'", titleAttr:"style='display:none'" }
							,{ title:'權理職等', index:'DUTYRANK_JOBRANK_NM',dataAttr:"style='display:none'", titleAttr:"style='display:none'" }
							,{ title:'俸點數', index:'PNT',dataAttr:"style='display:none'", titleAttr:"style='display:none'" }
						]
				,querySqlId : 'qic.dao.QICUtil.queryEmployeeData'
				,params : [ { key : 'AP_GROUP_CD',value : getApGroupCd() } ]
				,queryColumns : [ {type:'text', value:'EMPL_NM', title:'員工姓名'} ]
				
				//*********以下給onblur用***********
//				,manager : 'QICUtilManagerImpl.processQueryEmployeeData'
//				,stringPattern : '@{'+bindNM+'}'
			};
		
//		if(formObj.find('#'+id).val() != '' ) {
//			myOptions['params'].push({key : 'EMPL_NM', value : formObj.find('#'+id).val() } );
//		}	
		
		this.paramsMerge(options, myOptions);
		
		options = $.extend({}, myOptions, options);
		this.openWindowRender(id, formObj, bindNM, options);
	},
	
	//開窗-職稱代碼
	qicJTitleCD : function(id, formObj, bindNM, options){
		var myOptions = {
				buttonObj : formObj.find('#btn'+id)
				,bindFormObj : formObj
				,pageSize : 50,
				
				columnMeta : 
						[
							{ title:'選取', dataPatternKey:'_radio'}
							,{ title:'職稱代碼', index:'JTITLE_CD' }
							,{ title:'職稱名稱', index:'JTITLE_NM'}
						]
				,querySqlId : 'qic.dao.QICUtil.queryJtitleCD'
				,params : [ { key : 'AP_GROUP_CD',value : getApGroupCd()} ]
						
				//*********以下給onblur用***********
				,manager : 'QICUtilManagerImpl.processQueryJTitleCD'
				,stringPattern : '@{'+bindNM+'}'
			};
			
		this.paramsMerge(options, myOptions);
		
		options = $.extend({}, myOptions, options);
		this.openWindowRender(id, formObj, bindNM, options);
	},
	
	//開窗-科室代碼
	qicSctnCD : function(id, formObj, bindNM, options){
		var myOptions = {
			//**********以下給開窗用*************
				buttonObj : formObj.find('#btn'+id)
				,bindFormObj : formObj
				,pageSize : 50,
				
			columnMeta : 
					[
						{ title:'選取', dataPatternKey:'_radio'}
						,{ title:'科室代碼', index:'SCTN_CD' }
						,{ title:'科室名稱', index:'SCTN_NM'}
					]
			,querySqlId : 'qic.dao.QICUtil.querySctnCD'
			,params : [ { key : 'AP_GROUP_CD',value : getApGroupCd()} ]
			
			//*********以下給onblur用***********
			,manager : 'QICUtilManagerImpl.processQuerySctnCD'
			,stringPattern : '@{'+bindNM+'}'
		};
		
		this.paramsMerge(options, myOptions);
		
		options = $.extend({}, myOptions, options);
		this.openWindowRender(id, formObj, bindNM, options);
	},
	
	//開窗-專業加給
	qicProflAllwKD : function(id, formObj, bindNM, options){
		var myOptions = {
			//**********以下給開窗用*************
				buttonObj : formObj.find('#btn'+id)
				,bindFormObj : formObj
				,pageSize : 50,
				
			columnMeta : [
							{ title:'選取', dataPatternKey:'_radio'}
							,{ title:'專業加給代號', index:'PROFL_ALLW_KD' }
							,{ title:'專業加給名稱', index:'PROFL_ALLW_NM'}
						]
			,querySqlId : 'qic.dao.QICUtil.queryProflAllwType'
			,params : [ { key : 'AP_GROUP_CD',value : getApGroupCd()} ]
			
			//*********以下給onblur用***********
			,manager : 'QICUtilManagerImpl.processQueryProflAllw'
			,stringPattern : '@{'+bindNM+'}'
		};
		
		this.paramsMerge(options, myOptions);
		
		options = $.extend({}, myOptions, options);
		this.openWindowRender(id, formObj, bindNM, options);
	},
	
	//開窗-補扣代號
	qicBackdedCD : function(id, formObj, bindNM, options){
		var myOptions = {
			//**********以下給開窗用*************
				buttonObj : formObj.find('#btn'+id)
				,bindFormObj : formObj
				,pageSize : 50,
				
			columnMeta : 
				[
						{ title:'選取', dataPatternKey:'_radio'}
						,{ title:'補扣項目代號', index:'BACKDED_CD' }
						,{ title:'補扣項目名稱', index:'BACKDED_NM'}
				]
			,querySqlId : 'qic.domain.QICC032.selectByAny'
			,params : [ { key : 'AP_GROUP_CD',value : getApGroupCd()} ]
			
			//*********以下給onblur用***********
			,manager : 'QICUtilManagerImpl.processQueryBackded'
			,stringPattern : '@{'+bindNM+'}'
		};
		
		this.paramsMerge(options, myOptions);
		
		options = $.extend({}, myOptions, options);
		this.openWindowRender(id, formObj, bindNM, options);
	},
	
	//開窗-給付代號
	qicPaymentCD : function(id, formObj, bindNM, options){
		var myOptions = {
			//**********以下給開窗用*************
				buttonObj : formObj.find('#btn'+id)
				,bindFormObj : formObj
				,pageSize : 50,
				
			columnMeta : [
							{ title:'選取', dataPatternKey:'_radio'}
							,{ title:'給付項目代號', index:'PAYMENT_CD' }
							,{ title:'給付項目名稱', index:'PAYMENT_NM'}
							,{ title:'法院扣款註記', index:'COURT_DED_MK'}
						]
			,querySqlId : 'qic.domain.QICC031.selectByAny'
			,params : [ { key : 'AP_GROUP_CD',value : getApGroupCd()} ]
			
			//*********以下給onblur用***********
			,manager : 'QICUtilManagerImpl.processQueryPayment'
			,stringPattern : '@{'+bindNM+'}'
		};
		
		this.paramsMerge(options, myOptions);
		
		options = $.extend({}, myOptions, options);
		this.openWindowRender(id, formObj, bindNM, options);
	},
	
	//開窗-眷屬身分證字號
	qicRltvIdn : function(id, formObj, bindNM, options){
		var myOptions = {
			//**********以下給開窗用*************
				buttonObj : formObj.find('#btn'+id)
				,bindFormObj : formObj
				,pageSize : 50,
				
			columnMeta : [
							{ title:'選取', dataPatternKey:'_radio'}
							,{ title:'眷屬身分證字號', index:'RLTV_IDN' }
							,{ title:'眷屬姓名', index:'RLTV_NM'}
						]
			,querySqlId : 'qic.dao.QIC214M_.queryRtlvNM'
			,params : [ { key : 'AP_GROUP_CD',value : getApGroupCd()} ]
			
			//*********以下給onblur用***********
//			,manager : 'QICUtilManagerImpl.processQueryPayment'
//			,stringPattern : '@{'+bindNM+'}'
		};
		
		this.paramsMerge(options, myOptions);
		
		options = $.extend({}, myOptions, options);
		this.openWindowRender(id, formObj, bindNM, options);
	},
	
	nothing : null
};






//************************ _default **************************
windowPack.openWindowRender = function (id, formObj, bindNM, options) {
	if(bindNM != undefined && bindNM != ''){
		formObj.find('#'+id).change(function() {
			if($(this).val() == ''){
				return;
			}
			var strValue = managerUtil.getString(
					{
						manager : options['manager']
						,param : $.parseJSON('{"'+ id +'" : "'+ $(this).val() +'"}')
					 	,stringPattern : options['stringPattern']
					}
				);
			if(bindNM.indexOf(',') > 0 && strValue.indexOf(',') > 0) {
				var nms = bindNM.split(','), vals = strValue.split(',');
				
				for(var i in nms) {
					var obj = formObj.find('#'+ nms[i]);
					
					if(obj.attr('type') == 'radio') {
						obj.filter(function() {
						    if(this.value == vals[i]) {
						    	this.checked = true;
						    	return this;
						    }
						});
					} else {
						obj.val(vals[i]);
					}
				}
			} else {
				formObj.find('#'+bindNM).val(strValue);
			}
		});
	}
	
//	var defOption = {
//			buttonObj : formObj.find('#btn'+id)
//			,bindFormObj : formObj
//			,pageSize : 50
//		};
	
//	openWindowUtil.render($.extend({}, defOption, options));
	openWindowUtil.render(options);
};

windowPack.paramsMerge = function(options, myOptions){
	options = options == undefined ? {} : options;
	
	var	params = options['params'];
	var columnMeta = options['columnMeta'];
	
	if(params != undefined){
		if($.isArray(params)){
			$.merge(params, myOptions['params']); 
		}
	}
	if(columnMeta != undefined){
		if($.isArray(columnMeta)){
			$.extend(myOptions['columnMeta'], columnMeta); 
		}
	}
	
};

var _AP_GROUP_CD = '';

function getApGroupCd() {
	if(_AP_GROUP_CD == undefined || _AP_GROUP_CD == '' || _AP_GROUP_CD == null){
		_AP_GROUP_CD = managerUtil.getString(
				{
					manager : 'QICUtilManagerImpl.processQueryApGroupCD'
				 	,stringPattern : '@{AP_GROUP_CD}'
				}
			);
	}
	return _AP_GROUP_CD;
}
