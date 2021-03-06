/*
@@程式代號 = DOCRM730M_Manager.java
@@程式名稱 = 系統別Manager
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager;

import java.util.List;
import java.util.Map;

import gov.fdc.framework.core.dao.DBResultList;

public interface DOCRM730M_Manager extends DOCRMWebManager {

	/**
	 * processMain
	 * 
	 * @param parameterMap
	 *            參數集
	 * @return 結果集
	 */
	Map<String, Object> processMain(Map<String, Object> parameterMap);

	/**
	 * processQuery 工作查詢
	 * 
	 * @param parameterMap
	 *            參數集
	 * @return 結果集
	 */
	Map<String, Object> processQuery(Map<String, Object> parameterMap);

	/**
	 * processPrint 列印報表
	 * 
	 * @param parameterMap
	 *            參數集
	 * @return 結果集
	 */
	Map<String, Object> processPrint(Map<String, Object> parameterMap);

	/**
	 * processDDL 下拉選單.
	 * 
	 * @param Code
	 *            代號
	 * @return 結果集
	 */
	List<Map<String, Object>> processDDL(String Code);

	/**
	 * 查詢table.
	 * 
	 * @param strTableNM
	 *            資料表名稱
	 * @param queryMap
	 *            查詢條件
	 * @param isPage
	 *            是否分頁
	 * @return 結果集
	 */
	DBResultList processSelectTableByAny(String strTableNM, Map<String, Object> queryMap, boolean isPage);

	/**
	 * QUS_RESULT 連動下拉式選單
	 * 
	 * @param parameterMap
	 *            參數集
	 * @return 結果集
	 */
	Map<String, Object> QUS_RESULT(Map<String, Object> parameterMap);
}