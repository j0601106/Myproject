/*
@@程式代號 = DOCRM610M_ManagerImpl.java
@@程式名稱 = DOCRM610_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;


import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM610M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM610M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM610M_Manager {
	@Override
    public Map<String, Object> processMain(Map<String, Object> parameterMap) {
        return null;
    }

    // 查詢
	@Override
    public Map<String, Object> processQuery(Map<String, Object> parameterMap) {
        String DOC_ORG_ID = parameterMap.get("DOC_ORG_ID") != null ? (String) parameterMap.get("DOC_ORG_ID") : "";
        parameterMap.put("DOC_ORG_ID",DOC_ORG_ID);
        Map<String, Object> rtn = new HashMap<String, Object>();
        String Organ = MapUtil.getString(parameterMap, "Organ", "");
		String TAX_TP = MapUtil.getString(parameterMap, "TAX_TP", "");
        //判斷要查詢哪段SQL
        if ("All".equals(Organ)) {
			Query query = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM610M_.queryDOCRMT610").setParamMap(parameterMap);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
			rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());

		} else if ("".equals(Organ) || "Single".equals(Organ) && !"".equals(TAX_TP)) {
			Query query = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM610M_.queryDOCRMT610_DOCRM_TP").setParamMap(parameterMap);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
			rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
			
		} else if ("".equals(Organ) || "Single".equals(Organ) && "".equals(TAX_TP)) {
			Query query = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM610M_.queryDOCRMT610_TAX").setParamMap(parameterMap);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
			rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
		}
        
        return rtn;
    }
	@Override
    public Map<String, Object> processPrint(Map<String, Object> parameterMap) {
    	Map rtn = new HashMap();
    	String Organ = MapUtil.getString(parameterMap, "Organ", "");
		String TAX_TP = MapUtil.getString(parameterMap, "TAX_TP", "");
		String DOC_ORG_ID = MapUtil.getString(parameterMap, "DOC_ORG_ID", "");
		
		if (!"".equals(DOC_ORG_ID)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM610M_.queryT850ORG_ID")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
			Map temp = (HashMap) queryResult1.getDataList().get(0);
			rtn.put("ORG_ID", temp.get("ORG_ID").toString());// 取機關別中文名稱
		}
		if (!"".equals(TAX_TP)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM610M_.queryT850TAX_TP")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
			Map temp = (HashMap) queryResult1.getDataList().get(0);
			rtn.put("TAX_TP", temp.get("TAX_TP").toString());// 取稅目別中文名稱
		}
		if ("Single".equals(Organ) && "".equals(TAX_TP)) {
			Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM610M_.queryDOCRMT610_TAX")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put("datalist", queryResult.getDataList());
		} else if ("Single".equals(Organ) && !"".equals(TAX_TP)) {
			Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM610M_.queryDOCRMT610_DOCRM_TP")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put("datalist", queryResult.getDataList());
		} else if ("All".equals(Organ)) {
			
			Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM610M_.queryDOCRMT610")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put("datalist", queryResult.getDataList());
		}
    	
		return rtn;
	}
}
