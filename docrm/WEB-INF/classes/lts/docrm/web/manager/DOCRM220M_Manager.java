/*
@@程式代號 = DOCRM220M_Manager.java
@@程式名稱 = 列印回執聯作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface DOCRM220M_Manager extends DOCRMWebManager {

	/**
	 * processQuery.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQuery(Map<String, Object> parameterMap);
	
	/**
	 * processQueryDetail1.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQueryDetail1(Map<String, Object> parameterMap);

	/**
	 * processQueryDetail2.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQueryDetail2(Map<String, Object> parameterMap);
	
	/**
	 * processQueryDetail3.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQueryDetail3(Map<String, Object> parameterMap);
	
	/**
	 * processQueryDetail4.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQueryDetail4(Map<String, Object> parameterMap);
	
	/**
	 * processEdit.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processEdit(Map<String, Object> parameterMap);
}
