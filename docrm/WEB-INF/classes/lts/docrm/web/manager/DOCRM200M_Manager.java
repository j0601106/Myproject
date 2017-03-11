/*
@@程式代號 = DOCRM200M_Manager.java
@@程式名稱 = 收件及移轉作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager;

import java.util.List;
import java.util.Map;

public interface DOCRM200M_Manager extends DOCRMWebManager {

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
	 * processEdit.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processEdit(Map<String, Object> parameterMap);
	
    /**
     * processAcceptDocrm 收件
     * 
     * @param parameterMap 參數集
     * @return 結果集
     */
    Map<String, Object> processAcceptDocrm(Map<String, Object> parameterMap);
    
    /**
     * processAcceptDocrm 移轉
     * 
     * @param parameterMap 參數集
     * @return 結果集
     */
    Map<String, Object> processTranferDocrm(Map<String, Object> parameterMap);
}
