/*
@@程式代號 = DOCRM730M_ManagerImpl.java
@@程式名稱 = DOCRM730M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM730M_Manager;
import lts.global.core.util.PhraseUtil;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM730M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM730M_Manager {
	@Override
	public Map<String, Object> processMain(Map<String, Object> parameterMap) {
		return null;
	}

	@Override
	public List<Map<String, Object>> processDDL(String code) {

		Map parameterMap = new HashMap();
		parameterMap.put("STUS", "Y");

		DBResultList searchResult = processSelectTableByAny("DOCRMT700", parameterMap, false);

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

		Query queryTable = Query.createQuery().setSqlId(DOCRMConstant.DOMAIN + strTableNM + DOCRMConstant.SELECT_BY_ANY)
				.setParamMap(criteriaMap).setPaging(isPage);
		DBResultList resultTable = this.getDao().queryForDBResultList(queryTable);

		return resultTable;
	}
	//連動下拉式選單
	@Override
	public Map<String, Object> QUS_RESULT(Map<String, Object> parameterMap) {
		Map<String, Object> rtn = new HashMap<String, Object>();
		// 抓標題動態
		Query query_QA = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryT710_RESULT")
				.setParamMap(parameterMap);
		DBResultList queryResult_QA = this.getDao().queryForDBResultList(query_QA);
		List<Map> queryList = (List<Map>) queryResult_QA.getDataList();
		List dataList = new ArrayList();
		// 答案
		String RESULT1 = "";
		String RESULT2 = "";
		String RESULT3 = "";
		String RESULT4 = "";
		// 代碼
		String RESULT_1 = "";
		String RESULT_2 = "";
		String RESULT_3 = "";
		String RESULT_4 = "";

		for (Map dataMap : queryList) {
			if ("1".equals(dataMap.get("RESULT_ID"))) {
				RESULT1 = dataMap.get("RESULT").toString();
				RESULT_1 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT1", RESULT1);
				rtn.put("RESULT_1", RESULT_1);
			} else if ("2".equals(dataMap.get("RESULT_ID"))) {
				RESULT2 = dataMap.get("RESULT").toString();
				RESULT_2 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT2", RESULT2);
				rtn.put("RESULT_2", RESULT_2);
			} else if ("3".equals(dataMap.get("RESULT_ID"))) {
				RESULT3 = dataMap.get("RESULT").toString();
				RESULT_3 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT3", RESULT3);
				rtn.put("RESULT_3", RESULT_3);
			} else {
				RESULT4 = dataMap.get("RESULT").toString();
				RESULT_4 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT4", RESULT4);
				rtn.put("RESULT_4", RESULT_4);
			}
		}
		return rtn;
	}
	
	// 查詢
	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {
		String DOC_ORG_ID = parameterMap.get("DOC_ORG_ID") != null ? (String) parameterMap.get("DOC_ORG_ID") : "";
		parameterMap.put("DOC_ORG_ID", DOC_ORG_ID);
		Map<String, Object> rtn = new HashMap<String, Object>();
		String Organ = MapUtil.getString(parameterMap, "Organ", "");
		String TAX_TP = MapUtil.getString(parameterMap, "TAX_TP", "");
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryDOCRMT730").setParamMap(parameterMap);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);
		
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
		return rtn;
	}
	@Override
	public Map<String, Object> processPrint(Map<String, Object> parameterMap) {
		Map rtn = new HashMap();

		String Organ = MapUtil.getString(parameterMap, "Organ", "");
		String TAX_TP = MapUtil.getString(parameterMap, "TAX_TP", "");
		String QUESTION_ID = MapUtil.getString(parameterMap, "QUESTION_ID", "");
		String DOC_ORG_ID = MapUtil.getString(parameterMap, "DOC_ORG_ID", "");
		String YY = MapUtil.getString(parameterMap, "APPLY_DATE_YEAR", "");
		String MM = MapUtil.getString(parameterMap, "APPLY_DATE_Mo", "");
		String YYMM = YY + MM;
		
		
		Query query2 = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryT710_RESULT").setParamMap(parameterMap)
				.setPaging(false);
		DBResultList queryResult2 = this.getDao().queryForDBResultList(query2);
		
		List<Map> queryList = (List<Map>) queryResult2.getDataList();
		List dataList = new ArrayList();
		//答案
		String RESULT1 = "";
		String RESULT2 = "";
		String RESULT3 = "";
		String RESULT4 = "";
		//代碼
		String RESULT_1 = "";
		String RESULT_2 = "";
		String RESULT_3 = "";
		String RESULT_4 = "";
		for (Map dataMap : queryList) {
			if ("1".equals(dataMap.get("RESULT_ID"))) {
				RESULT1 = dataMap.get("RESULT").toString();
				RESULT_1 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT1", RESULT1);
				rtn.put("RESULT_1", RESULT_1);
			} else if ("2".equals(dataMap.get("RESULT_ID"))) {
				RESULT2 = dataMap.get("RESULT").toString();
				RESULT_2 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT2", RESULT2);
				rtn.put("RESULT_2", RESULT_2);
			} else if ("3".equals(dataMap.get("RESULT_ID"))) {
				RESULT3 = dataMap.get("RESULT").toString();
				RESULT_3 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT3", RESULT3);
				rtn.put("RESULT_3", RESULT_3);
			} else {
				RESULT4 = dataMap.get("RESULT").toString();
				RESULT_4 = dataMap.get("RESULT_ID").toString();
				rtn.put("RESULT4", RESULT4);
				rtn.put("RESULT_4", RESULT_4);
			}
		}
		


		if (!"".equals(QUESTION_ID)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryQUESTION").setParamMap(parameterMap)
					.setPaging(false);
			DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
			Map temp = (HashMap) queryResult1.getDataList().get(0);
			rtn.put("QUESTION", temp.get("QUESTION").toString());// 取題目中文名稱
		}
		if (!"".equals(DOC_ORG_ID)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryT850ORG_ID")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
			Map temp = (HashMap) queryResult1.getDataList().get(0);
			rtn.put("ORG_ID", temp.get("ORG_ID").toString());// 取機關別中文名稱
		}
		if (!"".equals(TAX_TP)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryT850TAX_TP")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
			Map temp = (HashMap) queryResult1.getDataList().get(0);
			rtn.put("TAX_TP", temp.get("TAX_TP").toString());// 取稅目別中文名稱
		}

		parameterMap.put("DATE", YYMM);
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryDOCRMT730").setParamMap(parameterMap)
				.setPaging(false);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);
		Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM730M_.queryDOCRMT730").setParamMap(parameterMap)
				.setPaging(false);
		DBResultList queryResult1 = this.getDao().queryForDBResultList(query);
		rtn.put("datalist", queryResult.getDataList());
		rtn.put("1", queryResult1.getDataList());
		rtn.put("2", queryResult2.getDataList());
		return rtn;
	}

}
