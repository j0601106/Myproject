/*
@@程式代號 = QICUtilManager.java
@@程式名稱 = QICUtilManager
@@程式版本 = V1.000
@@更新日期 = 2011/08/20
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.core.manager;

import gov.fdc.framework.core.dao.DBResultList;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DOCRMUtilManager extends DOCRMManager {
	
	/**
	 * getNextDocrmNo
	 * 
	 * @return String 案件編號
	 */
	String getNextDocrmNo();
	
	/**
	 * getNextDocrmNo
	 * 
	 * @param String MESS_NO
	 * @return String 附件編號
	 */
	String getNextFileNo(String str);
	
	/**
	 * processDDLMess
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	List<Map<String, Object>> processDDLMess(Map<String, Object> parameterMap);
	
	/**
	 * processDDL 下拉選單.
	 * 
	 * @param Code 代號
	 * @return 結果集
	 */
	List<Map<String, Object>> processDDL(String Code);
	
	/**
	 * 查詢table.
	 * 
	 * @param strTableNM 資料表名稱
	 * @param queryMap 查詢條件
	 * @param isPage 是否分頁
	 * @return 結果集
	 */
	DBResultList processSelectTableByAny(String strTableNM, Map<String, Object> queryMap, boolean isPage);
	
	/**
	 * 新增tableByAny.
	 * 
	 * @param strTableNM 資料表名稱
	 * @param insertMap 新增內容
	 * @return 結果集
	 */
	int[] processInsertTableByAny(String strTableNM, Map<String, Object> insertMap);

	/**
	 * 異動tableByAny.
	 * 
	 * @param strTableNM 資料表名稱
	 * @param updateMap 更新內容
	 * @return 結果集
	 */
	int[] processUpdateTableByAny(String strTableNM, Map<String, Object> updateMap);
	
	/**
	 * 刪除tableByAny.
	 * 
	 * @param strTableNM 資料表名稱
	 * @param deleteMap 刪除條件
	 * @return 結果集
	 */
	int[] processDeleteTableByAny(String strTableNM, Map<String, Object> deleteMap);
	
	/**
	 * 查詢tableByAny.
	 * 
	 * @param strFuncNM 功能(XML)名稱
	 * @param strID 查詢ID
	 * @param queryMap 查詢條件
	 * @param isPage 是否分頁
	 * @return 結果集
	 */
	DBResultList processSelectTableByFunction(String strFuncNM, String strID, Map<String, Object> queryMap,
		boolean isPage);
	
	/**
	 * 產生200XML文件檔
	 * 
	 * @param String 案件編號
	 */
	void processCreate200XML(String DOCRM_NO);
	
	/**
	 * 產生700XML文件檔
	 */
	void processCreate700XML();
	
	/**
	 * 產生710XML文件檔
	 * 
	 * @param String 案件編號
	 */
	void processCreate710XML(String DOCRM_NO);
	
}
