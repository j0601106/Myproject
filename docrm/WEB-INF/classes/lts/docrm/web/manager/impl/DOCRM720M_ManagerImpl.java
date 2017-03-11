/*
@@程式代號 = TBD101M_ManagerImpl.java
@@程式名稱 = TBD101M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2016/11/03
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;


import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM720M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM720M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM720M_Manager {

	@Override
    public Map<String, Object> processMain(Map<String, Object> parameterMap) {
        return null;
    }

	//題目下拉式選單
    @Override
    public List<Map<String, Object>> processDDL(String code) {
		Map parameterMap = new HashMap();
		parameterMap.put("PHRASE_TYPE_KEY", code);
		DBResultList searchResult = processSelectTableByAny("DOCRMT700",parameterMap, false);
		return (List<Map<String, Object>>) searchResult.getDataList();
	}
	
	@Override
	public DBResultList processSelectTableByAny(String strTableNM, Map queryMap, boolean isPage) {

		Map criteriaMap = new HashMap();
		if (MapUtil.getMap(queryMap, DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME) == null) {
			criteriaMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, queryMap);
		} else {
			criteriaMap = queryMap;
		}
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM720M_.queryDOCRMT700")
				.setParamMap(criteriaMap);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);
		return queryResult;
			
	}
    
    // 查詢 
	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {
		
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM720M_.queryDOCRMT720")
				.setParamMap(parameterMap);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
			
		return rtn;
	}
	
	//連動下拉式選單
	@Override
	public List<Map<String, Object>> processAnswer(Map<String, Object> parameterMap) {

		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM720M_.queryDOCRMT710")
				.setParamMap(parameterMap).setPaging(false);
		List<Map<String, Object>> queryResult = (List<Map<String, Object>>)
				this.getDao().queryForDBResultList(query).getDataList();
		return queryResult;
	}
	
	//列印
	@Override
	public Map<String, Object> processPrint(Map<String, Object> parameterMap) {
		
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM720M_.queryDOCRMT720")
				.setParamMap(parameterMap).setPaging(false);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);
				
		Map rtn = new HashMap();
		rtn.put("datalist", queryResult.getDataList());
		return rtn;
	}
}
