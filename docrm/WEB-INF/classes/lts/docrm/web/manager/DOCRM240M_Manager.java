/*
@@程式代號 = DOCRM240M_Manager.java
@@程式名稱 = 附件下載
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager;

import java.util.List;
import java.util.Map;

public interface DOCRM240M_Manager extends DOCRMWebManager {

	/**
	 * processQuery.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQuery(Map<String, Object> parameterMap);

}
