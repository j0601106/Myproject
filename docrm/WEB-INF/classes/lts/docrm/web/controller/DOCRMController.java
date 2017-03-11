/*
@@程式代號 = DOCRMController.java
@@程式名稱 = 系統別Controller
@@程式版本 = V1.000
@@更新日期 = 2011/01/12
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.library.log.LTSLogConstant;
import lts.docrm.core.util.DOCRMConstant;
import lts.global.web.controller.LTSController;

public abstract class DOCRMController extends LTSController {

    @Override
    public String getSystemCd() {
        return DOCRMConstant.sysid;
    }

}
