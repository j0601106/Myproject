/*
@@程式代號 = DOCRMAdapterHelper.java
@@程式名稱 = 系統別AdapterHelpter
@@程式版本 = V1.000
@@更新日期 = 2013/07/10
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.api.adapter;

import gov.fdc.framework.api.BaseAdapter.SOURCE;
import gov.fdc.framework.api.impl.AdapterHelper;

import org.springframework.stereotype.Component;

@Component
public class DOCRMAdapterHelper extends AdapterHelper {
	
	/**
	 * 取得DOCRM260DOWNAdapter
	 * 
	 * @param source 呼叫來源
	 * @return DOCRMConvertAdapter
	 */
	public DOCRM260DOWNAdapter createDOCRM260DOWNAdapter(SOURCE source) {
		return (DOCRM260DOWNAdapter) this.getAdapter("DOCRM260DOWNAdapter", source);
	}
	
	/**
	 * 取得DOCRM260UPAdapter
	 * 
	 * @param source 呼叫來源
	 * @return DOCRMConvertAdapter
	 */
	public DOCRM260UPAdapter createDOCRM260UPAdapter(SOURCE source) {
		return (DOCRM260UPAdapter) this.getAdapter("DOCRM260UPAdapter", source);
	}
}
