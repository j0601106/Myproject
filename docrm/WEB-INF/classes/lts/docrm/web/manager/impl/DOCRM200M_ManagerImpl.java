/*
@@程式代號 = DOCRM200M_ManagerImpl.java
@@程式名稱 = 收件及移轉作業
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

import javax.xml.parsers.ParserConfigurationException;

import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.core.util.DOCRMUtil;
import lts.docrm.core.util.XMLUtil2;
import lts.docrm.web.manager.DOCRM120M_Manager;
import lts.docrm.web.manager.DOCRM200M_Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;

@Service
@Scope("prototype")
public class DOCRM200M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM200M_Manager {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {

		parameterMap.put("DOC_ORG_ID", this.getUserProfile().getOrgid());
		
		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM200M_.queryDOCRMT200").setParamMap(parameterMap);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		String FUNCTION = new String();
		
		for(int i = 0;i<DBResultList.getTotalCount();i++){
			Map map = (Map) DBResultList.getDataList().get(i);
			String MESS_NO = map.get("MESS_NO").toString();
			FUNCTION = "<input type='button' id='FUNCTION' name='FUNCTION' value='移轉' onclick=tranferDocrm($(this),'"+MESS_NO+"')>"+
					"<input type='button' id='FUNCTION' name='FUNCTION' value='收件' onclick=acceptDocrm($(this),'"+MESS_NO+"')>";
			map.put("FUNCTION", FUNCTION);
		}
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}

	@Override
	public Map<String, Object> processQueryDetail1(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM200M_.queryDOCRMT300").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail2(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM200M_.queryDOCRMT210").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		String FUNCTION = new String();
		
		for(int i = 0;i<DBResultList.getTotalCount();i++){
			Map map = (Map) DBResultList.getDataList().get(i);
			
			String FILE_NO = map.get("FILE_NO").toString();
			String MESS_NO = map.get("MESS_NO").toString();
			String FILE_NM = map.get("FILE_NM").toString();
			String SOURCE_NM = map.get("SOURCE_NM").toString();
			String str = "{FILE_NO:'"+FILE_NO+"',MESS_NO:'"+MESS_NO+"',FILE_NM:'"+FILE_NM+"',SOURCE_NM:'"+SOURCE_NM+"'}";
			
			FUNCTION = "<input type='button' id='FUNCTION' name='FUNCTION' value='查看歷程' onclick=processQueryDetail3("+str+")>";
			map.put("FUNCTION", FUNCTION);
		}
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail3(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM200M_.queryDOCRMT310").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		String FUNCTION = new String();
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processEdit(Map<String, Object> parameterMap) {

		parameterMap.put("DOC_ORG_ID", this.getUserProfile().getOrgid());
		
		Query query = 
				 Query.createQuery().setSqlId("docrm.dao.DOCRM200M_.queryDOCRMT200").setParamMap(parameterMap).setPaging(false);
	    DBResultList queryResult = this.getDao().queryForDBResultList(query);
	        
	    Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,
					queryResult.getFirstByMap() == null ? new HashMap() : queryResult.getFirstByMap());
	     
		return rtn;
	}
	
	@Override
    public Map<String, Object> processAcceptDocrm(Map<String, Object> parameterMap) {

    	//取得案件編號
    	String docrmNo = docrmUtilManager.getNextDocrmNo();
    	
    	//取得驗證碼
    	String verifyNo = DOCRMUtil.getVerifyNo("", 5);
    	
		Map criteriaMap = new HashMap();
		criteriaMap.put("MESS_NO", parameterMap.get("MESS_NO"));
		parameterMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, criteriaMap);
		parameterMap.put("DOCRM_NO", docrmNo);
		parameterMap.put("DOCRM_STUS", "2");
		parameterMap.put("VERIFY_NO", verifyNo);
    	
		docrmUtilManager.processUpdateTableByAny("DOCRMT200", parameterMap);
		
		parameterMap.put("SD_DATE", DOCRMUtil.getRocSysdate());
		parameterMap.put("SD_TIME", DateUtil.getSysTime());
		
    	docrmUtilManager.processInsertTableByAny("DOCRMT300", parameterMap);
    	
    	docrmUtilManager.processCreate200XML(docrmNo);
    	
        return parameterMap;
    }
    
	@Override
    public Map<String, Object> processTranferDocrm(Map<String, Object> parameterMap) {

		Map criteriaMap = new HashMap();
		criteriaMap.put("MESS_NO", parameterMap.get("MESS_NO"));
		parameterMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, criteriaMap);
		parameterMap.put("DOCRM_STUS", "1");
		parameterMap.put("TRN_ORG_ID", this.getUserProfile().getOrgid());
    	
    	docrmUtilManager.processUpdateTableByAny("DOCRMT200", parameterMap);
     
    	parameterMap.put("SD_DATE", DOCRMUtil.getRocSysdate());
		parameterMap.put("SD_TIME", DateUtil.getSysTime());
		
		docrmUtilManager.processInsertTableByAny("DOCRMT300", parameterMap);
		
        return null;
    }
}
