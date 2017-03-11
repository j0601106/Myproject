/*
@@ç¨‹å?ä»£??? = DOCRM830M_Manager.java
@@ç¨‹å?å?ç¨± = ç³»çµ±?ˆ¥Manager
@@ç¨‹å?ç?ˆæœ¬ = V1.000
@@?›´?–°?—¥??? = 2015/12/01
@@æª¢æŸ¥ç¢?  = ?…§å®¹ç”±YPM?‡ª??•ç”¢???
 */
package lts.docrm.web.manager;

import java.util.Map;

public interface DOCRM300M_Manager extends DOCRMWebManager {

    /**
     * processGetMESS_NO
     * 
     * @param parameterMap
     *            ??ƒæ•¸???
     * @return çµæ?œé??
     */
    Map<String, Object> processGetMESS_NO(Map<String, Object> parameterMap);
    
    /**
     * processImportDOCRM
     * 
     * @param parameterMap
     *            ??ƒæ•¸???
     * @return çµæ?œé??
     */
    Map<String, Object> processImportDOCRM(Map<String, Object> parameterMap);

}