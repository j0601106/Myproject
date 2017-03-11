/*
@@程式代號 = TBD101M_ManagerImpl.java
@@程式名稱 = TBD101M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2016/10/16
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;


import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;

import java.util.HashMap;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM600M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM600M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM600M_Manager {

	@Override
    public Map<String, Object> processMain(Map<String, Object> parameterMap) {
        return null;
    }

    // 查詢 
    @Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {
		Map<String, Object> rtn = new HashMap<String, Object>();
		String DST = MapUtil.getString(parameterMap, "DST", "");
		String TAX_TP = MapUtil.getString(parameterMap, "TAX_TP", "");
		String DST_CD = MapUtil.getString(parameterMap, "DST_CD","");
		if ("A".equals(DST)) {
			Query query = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM600M_.queryORG_ID").setParamMap(parameterMap);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
			rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());

		} else if (!"A".equals(DST) && "B".equals(DST_CD) && !"".equals(TAX_TP)) {
			Query query = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM600M_.queryDOCRM_TP_NM").setParamMap(parameterMap);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
			rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
			
		} else if (!"A".equals(DST) && "B".equals(DST_CD) && "".equals(TAX_TP)) {
			Query query = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM600M_.queryTAX_TP").setParamMap(parameterMap);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
			rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
		}
		return rtn;
	}
	
	//列印
	@Override
	public Map<String, Object> processPrint(Map<String, Object> parameterMap) {
		Map rtn = new HashMap();
		String DST = MapUtil.getString(parameterMap, "DST", "");
		String TAX_TP = MapUtil.getString(parameterMap, "TAX_TP", "");
		String DST_CD = MapUtil.getString(parameterMap, "DST_CD","");
		String DOC_ORG_ID = MapUtil.getString(parameterMap, "DOC_ORG_ID","");
		String DOC_ORG_ID2 = MapUtil.getString(parameterMap, "DOC_ORG_ID2","");
		
		if (!"".equals(DOC_ORG_ID) || !"".equals(DOC_ORG_ID2)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM600M_.queryT850ORG_ID")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
			Map temp = (HashMap) queryResult1.getDataList().get(0);
			rtn.put("ORG_ID", temp.get("ORG_ID").toString());// 取機關別中文名稱
		}
		if (!"".equals(TAX_TP)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM600M_.queryT850TAX_TP")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
			Map temp = (HashMap) queryResult1.getDataList().get(0);
			rtn.put("TAX_TP", temp.get("TAX_TP").toString());// 取稅目別中文名稱
		}
		if (!"A".equals(DST) && "B".equals(DST_CD) && "".equals(TAX_TP)) {
			Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM600M_.queryTAX_TP")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put("datalist", queryResult.getDataList());
		} else if (!"A".equals(DST) && "B".equals(DST_CD) && !"".equals(TAX_TP)) {
			Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM600M_.queryDOCRM_TP_NM")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put("datalist", queryResult.getDataList());
		} else if ("A".equals(DST)) {
			Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM600M_.queryORG_ID")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult = this.getDao().queryForDBResultList(query);
			rtn.put("datalist", queryResult.getDataList());
		}

		return rtn;
	}
}
