/*
@@程�?�代??? = DOCRM340M_Manager.java
@@程�?��?�稱 = 系統?��Manager
@@程�?��?�本 = V1.000
@@?��?��?��??? = 2015/12/01
@@檢查�?  = ?��容由YPM?��??�產???
 */
package lts.docrm.web.manager;

import java.util.Map;

public interface DOCRM330M_Manager extends DOCRMWebManager {

    /**
     * processUpdateIn
     * 
     * @param parameterMap
     *            ??�數???
     * @return 結�?��??
     */
    Map<String, Object> processUpdateIn(Map<String, Object> parameterMap);
    
}