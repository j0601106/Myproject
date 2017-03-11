/*
@@ç¨‹å?ä»£??? = DOCRM310M_Manager.java
@@ç¨‹å?å?ç¨± = ç³»çµ±?ˆ¥Manager
@@ç¨‹å?ç?ˆæœ¬ = V1.000
@@?›´?–°?—¥??? = 2015/12/01
@@æª¢æŸ¥ç¢?  = ?…§å®¹ç”±YPM?‡ª??•ç”¢???
 */
package lts.docrm.web.manager;

import java.util.Map;

public interface DOCRM310M_Manager extends DOCRMWebManager {

    /**
     * processGenTxtByDocrm
     * 
     * @param parameterMap
     *            ??ƒæ•¸???
     * @return çµæ?œé??
     */
    Map<String, Object> processGenTxtByDocrm(Map<String, Object> parameterMap);

}