/*
@@程式代號 = DOCRM240M_ManagerImpl.java
@@程式名稱 = 附件下載
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.util.FdcThreadHolder;

import java.util.HashMap;

import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM240M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM240M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM240M_Manager {

	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {

		parameterMap.put("DOC_STF_CD", this.getUserProfile().getUserid());
		
		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM240M_.queryDOCRMT210").setParamMap(parameterMap);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		for (int i = 0; i < DBResultList.getDataList().size(); i++) {
			String str = new String();

			Map map = (Map) DBResultList.getDataList().get(i);

			String FILE_NM = MapUtil.getString(map, "FILE_NM", "");
			String FILE_PATH = MapUtil.getString(map, "FILE_PATH", "");
			
			str =
				str.concat("<a href='#' onclick='processDownload(\"" + FILE_NM + "\",\""
					+ FILE_PATH + "\");'>" + FILE_NM.substring(14) + "</a>");

			map.put("FILE_NM", str);
		}
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
}
