/*
@@程式代號 = DOCRMWebManagerImpl.java
@@程式名稱 = DOCRMWebManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2011/01/12
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.library.log.LTSLogConstant;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRMWebManager;
import lts.global.web.manager.impl.LTSWebManagerImpl;

/**
 * @author allen
 *
 */
public abstract class DOCRMWebManagerImpl extends LTSWebManagerImpl implements DOCRMWebManager {

    @Override
    public String getSystemCd() {
		return DOCRMConstant.sysid;
    }
}
