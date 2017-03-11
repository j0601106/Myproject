/*
@@程式代號 = DOCRM230M_Manager.java
@@程式名稱 = 補件作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface DOCRM230M_Manager extends DOCRMWebManager {

	/**
	 * processQuery.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQuery(Map<String, Object> parameterMap);
	
	/**
	 * processQueryN.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQueryN(Map<String, Object> parameterMap);
	
	/**
	 * processQueryDetail.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQueryDetail(Map<String, Object> parameterMap);
	
	/**
	 * processQueryDocTp.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processQueryDocTp(Map<String, Object> parameterMap);
	
	/**
	 * processEdit.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processEdit(Map<String, Object> parameterMap);

	/**
	 * processEditD.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processEditD(Map<String, Object> parameterMap);
	
	/**
	 * processEditSaveD.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processEditSaveD(Map<String, Object> parameterMap);
	
	/**
	 * processEditSaveN.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processEditSaveN(Map<String, Object> parameterMap);
	
	/**
	 * processEditSaveN2.
	 * 
	 * @param parameterMap 參數集
	 * @return 結果集
	 */
	Map<String, Object> processEditSaveN2(Map<String, Object> parameterMap);

}
