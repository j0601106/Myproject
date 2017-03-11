/*
@@ç¨‹å?ä»£??? = DOCRM340M_Manager.java
@@ç¨‹å?å?ç¨± = ç³»çµ±?ˆ¥Manager
@@ç¨‹å?ç?ˆæœ¬ = V1.000
@@?›´?–°?—¥??? = 2015/12/01
@@æª¢æŸ¥ç¢?  = ?…§å®¹ç”±YPM?‡ª??•ç”¢???
 */
package lts.docrm.web.manager;

import java.util.Map;

public interface DOCRM330M_Manager extends DOCRMWebManager {

    /**
     * processUpdateIn
     * 
     * @param parameterMap
     *            ??ƒæ•¸???
     * @return çµæ?œé??
     */
    Map<String, Object> processUpdateIn(Map<String, Object> parameterMap);
    
}