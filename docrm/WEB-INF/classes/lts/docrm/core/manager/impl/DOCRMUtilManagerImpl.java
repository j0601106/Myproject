/*
@@程式代號 = QICUtilManagerImpl.java
@@程式名稱 = QICUtilManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2011/08/20
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.core.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.library.exception.LTSApplicationException;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.core.util.DOCRMUtil;
import lts.docrm.core.util.XMLUtil2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRMUtilManagerImpl extends DOCRMManagerImpl implements DOCRMUtilManager {

	@Override
	public String getNextDocrmNo() {

		String orgId = this.getUserProfile().getOrgid();
		String sysDate = DOCRMUtil.getRocSysdate();
		String docrmNo = new String();

		Map parameterMap = new HashMap();
		
		parameterMap.put("DOCRM_NO", sysDate + orgId.substring(1));
		
		Query query = 
				 Query.createQuery().setSqlId("docrm.dao.DOCRMUtil.queryMaxDocrmNo").setParamMap(parameterMap).setPaging(false);
	    Map map = this.getDao().queryForDBResultList(query).getFirstByMap();
	    
	    if(map == null){
	    	docrmNo = "E" + sysDate + orgId.substring(1) + "001";
	    } else {
	    	String part1 = map.get("DOCRM_NO").toString().substring(0, 10);
	    	int part2 = Integer.parseInt(map.get("DOCRM_NO").toString().substring(10)) + 1;
	    	docrmNo = part1 + String.format("%03d", part2);
	    }
		
		return docrmNo;
	}
	
	@Override
	public String getNextFileNo(String MESS_NO) {

		String fileNo = new String();

		Map parameterMap = new HashMap();
		
		parameterMap.put("FILE_NO",  MESS_NO);
		
		Query query = 
				 Query.createQuery().setSqlId("docrm.dao.DOCRMUtil.queryMaxFileNo").setParamMap(parameterMap).setPaging(false);
	    Map map = this.getDao().queryForDBResultList(query).getFirstByMap();
	    
	    if(map == null){
	    	fileNo = MESS_NO + "001";
	    } else {
	    	String part1 = map.get("FILE_NO").toString().substring(0, 13);
	    	int part2 = Integer.parseInt(map.get("FILE_NO").toString().substring(13)) + 1;
	    	fileNo = part1 + String.format("%03d", part2);
	    }
		
		return fileNo;
	}
	
	@Override
	public List<Map<String, Object>> processDDLMess(Map<String, Object> parameterMap) {

		DBResultList searchResult =
			this.getDao().queryForDBResultList(
				Query.createQuery().setSqlId(DOCRMConstant.DOMAIN + "DOCRMT820" + DOCRMConstant.SELECT_BY_ANY)
					.setParamMap(parameterMap).setPaging(false));

		return (List<Map<String, Object>>) searchResult.getDataList();
	}
	
	@Override
	public List<Map<String, Object>> processDDL(String code) {
		
		Map parameterMap = new HashMap();
		parameterMap.put("PHRASE_TYPE_KEY", code);
			
		DBResultList searchResult = processSelectTableByAny("DOCRMT850", parameterMap, false);
		
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

		Query queryTable =
			Query.createQuery().setSqlId(DOCRMConstant.DOMAIN + strTableNM + DOCRMConstant.SELECT_BY_ANY)
				.setParamMap(criteriaMap).setPaging(isPage);
		DBResultList resultTable = this.getDao().queryForDBResultList(queryTable);

		return resultTable;
	}
	
	@Override
	public int[] processInsertTableByAny(String strTableNM, Map<String, Object> insertMap) {
		
		Query actionTable =
				Query.createQuery().setSqlId(DOCRMConstant.DOMAIN + strTableNM + ".insert").setParamMap(insertMap);
		int[] actTable = this.getDao().insert(actionTable);
		
		return actTable;
	}
	
	@Override
	public int[] processUpdateTableByAny(String strTableNM, Map<String, Object> updateMap) {

		Map criteriaMap = new HashMap();

		if (MapUtil.getMap(updateMap, DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME) == null) {
			criteriaMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, updateMap);
		} else {
			criteriaMap = updateMap;
		}

		Query actionTable =
			Query.createQuery().setSqlId(DOCRMConstant.DOMAIN + strTableNM + ".updateByAny").setParamMap(criteriaMap);

		int[] actTable = this.getDao().update(actionTable);

		return actTable;

	}
	
	@Override
	public int[] processDeleteTableByAny(String strTableNM, Map<String, Object> deleteMap) {

		Map criteriaMap = new HashMap();

		if (MapUtil.getMap(deleteMap, DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME) == null) {
			criteriaMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, deleteMap);
		} else {
			criteriaMap = deleteMap;
		}

		Query actionTable =
			Query.createQuery().setSqlId(DOCRMConstant.DOMAIN + strTableNM + ".deleteByAny").setParamMap(criteriaMap);
		int[] actTable = this.getDao().delete(actionTable);
		return actTable;
	}
	
	@Override
	public DBResultList processSelectTableByFunction(String strFuncNM, String strID, Map<String, Object> queryMap,
		boolean isPage) {

		Query queryTable =
			Query.createQuery().setSqlId(DOCRMConstant.DAO + strFuncNM + "." + strID).setParamMap(queryMap);
		if (!isPage) {
			queryTable.setPaging(isPage);
		}
		DBResultList resultTable = this.getDao().queryForDBResultList(queryTable);

		return resultTable;
	}
	
	@Override
	public void processCreate200XML(String DOCRM_NO) {

		Map queryMap = new HashMap();
		queryMap.put("DOCRM_NO", DOCRM_NO);
		
		Map resultMap = this.processSelectTableByAny("DOCRMT200", queryMap, false).getFirstByMap();

		List resultList = this.processSelectTableByFunction("DOCRM230M_", "queryDOCRMT230", queryMap, false).getDataList();
		
		resultMap.put("DOCRMT230LIST", resultList);
		
		try {
			XMLUtil2.ceateDOCRM200XML(resultMap);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void processCreate700XML() {

		Map resultMap = new HashMap();
		Map queryMap = new HashMap();
		
		List docrmt700List = new ArrayList(); 
		List docrmt710List = new ArrayList();
				
		queryMap.put("STUS", "Y");
		
		docrmt700List = this.processSelectTableByAny("DOCRMT700", queryMap, false).getDataList();
		
		if(docrmt700List.size()==0){
			throw new LTSApplicationException("無可同步資料");
		}
		
		for(int i=0; i<docrmt700List.size(); i++){
			Map map = (Map) docrmt700List.get(i);
			
			queryMap.put("QUESTION_ID", map.get("QUESTION_ID").toString());
			
			docrmt710List = this.processSelectTableByAny("DOCRMT710", queryMap, false).getDataList();

			map.put("DOCRMT710LIST", docrmt710List);
		}
		resultMap.put("DOCRMTLIST", docrmt700List);
		
		try {
			XMLUtil2.ceateDOCRM700XML(resultMap);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void processCreate710XML(String DOCRM_NO) {

		Map resultMap = new HashMap();
		
		resultMap.put("DOCRM_NO", DOCRM_NO);

		List resultList = this.processSelectTableByAny("DOCRMT720", resultMap, false).getDataList();
		
		resultMap.put("DOCRMT720LIST", resultList);
		
		try {
			XMLUtil2.ceateDOCRM710XML(resultMap);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
