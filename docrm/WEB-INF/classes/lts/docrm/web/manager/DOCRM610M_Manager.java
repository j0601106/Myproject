/*
@@程式代號 = DOCRM610M_Manager.java
@@程式名稱 = 系統別Manager
@@程式版本 = V1.000
@@更新日期 = 2016/10/19
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager;

import java.util.List;
import java.util.Map;

public interface DOCRM610M_Manager extends DOCRMWebManager {

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

}