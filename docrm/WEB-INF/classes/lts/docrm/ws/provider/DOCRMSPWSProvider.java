/*
@@程�?�代??? = QIASPWSProvider.java
@@程�?��?�稱 = QIA WebService Provide
@@程�?��?�本 = V1.010
@@?��?��?��??? = 2015/06/29
@@檢查�?  = ?��容由YPM?��??�產???
 */
package lts.docrm.ws.provider;

import gov.fdc.library.log.logobj.Logdata;
import lts.docrm.core.util.ApiParams;
import lts.docrm.web.manager.impl.DOCRM300M_ManagerImpl;

import java.util.HashMap;
import java.util.Map;

public class DOCRMSPWSProvider extends DOCRMWSProvider {

	@Override
	public Object execute(String targetDataSource, Logdata logdata, String fromSystemID, String toAreaID,
		String serviceID, Object svcobj) {
		Map<String, String> responseMap = new HashMap<String, String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		@SuppressWarnings("rawtypes")
		Map requestMap = (Map) svcobj;
				
		paramMap.put("DOCRM_DATA", ApiParams.xmlToMap(requestMap.get("map").toString()));
		
	    DOCRM300M_ManagerImpl docrm300 = new DOCRM300M_ManagerImpl();
	    Map<String, ?> dataMap = docrm300.processImportDOCRM(paramMap);
		
		
		if (requestMap != null) {
			responseMap.put("code", "A");
			responseMap.put("error_message", "SUCCESS");
		} else {
			responseMap.put("code", "B");
			responseMap.put("error_message", "ERROR");
		}

		return responseMap;
	}
}
