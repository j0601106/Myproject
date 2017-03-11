/*
@@程式代號 = DOCRM820M_Manager.java
@@程式名稱 = 系統別Manager
@@程式版本 = V1.000
@@更新日期 = 2016/10/17
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager;

import java.util.Map;

public interface DOCRM820M_Manager extends DOCRMWebManager {

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
     * processAddSave 存檔
     * 
     * @param parameterMap
     *            參數集
     * @return 結果集
     */
    Map<String, Object> processAddSave(Map<String, Object> parameterMap);

    /**
     * processEdit 編輯
     * 
     * @param parameterMap
     *            參數集
     * @return 結果集
     */
    Map<String, Object> processEdit(Map<String, Object> parameterMap);

    /**
     * processEditSave 編輯存檔
     * 
     * @param parameterMap
     *            參數集
     * @return 結果集
     */
    Map<String, Object> processEditSave(Map<String, Object> parameterMap);
    
    /**
     * processDelete 刪除
     * 
     * @param parameterMap
     *            參數集
     * @return 結果集
     */
    Map<String, Object> processDelete(Map<String, Object> parameterMap);

}