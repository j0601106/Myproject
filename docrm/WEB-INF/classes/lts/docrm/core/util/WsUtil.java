package lts.docrm.core.util;

import gov.fdc.library.log.LTSLog;
import gov.fdc.library.log.logobj.Logdata;
import gov.fdc.library.log.logobj.Processdata;
import gov.fdc.library.log.logobj.Userdata;
import gov.fdc.library.log.util.LogUtil;

public class WsUtil {

	/**
	 * ���ogdata
	 * 
	 * @return
	 */
	public static Logdata getLogData() {
		LTSLog logobj = new LTSLog();
		Logdata logdata = null;
		Userdata userdata = null;
		Processdata processdata = null;
		logdata = logobj.getLogdata();
		userdata = logdata.getUserdata();
		if (userdata == null) {
			userdata = logobj.getUserdata();
		}
		
		userdata.setCountyid("");
		userdata.setCounty("");
		userdata.setOrgid("");
		userdata.setOrg("");
		userdata.setDivisionid("");
		userdata.setDivision("");
		userdata.setSectionid("");
		userdata.setSection("");
		userdata.setUserid("");
		userdata.setUsernm("");
		userdata.setEmail("");

		logdata.setUserdata(userdata);

		processdata = logdata.getProcessdata();
		if (processdata == null) {
			processdata = logobj.getProcessdata();
		}
		processdata.setClientip(LogUtil.getCurrentIP());
		processdata.setClientname(LogUtil.getCurrentHost());
		processdata.setUrl("");
		logdata.setProcessdata(processdata);

		return logdata;
	}
}
