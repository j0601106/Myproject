/*
@@程式代號 = TBD101M_ManagerImpl.java
@@程式名稱 = TBD101M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2016/10/26
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;


import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;

import java.util.HashMap;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM810M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM810M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM810M_Manager {

    public Map<String, Object> processMain(Map<String, Object> parameterMap) {
        return null;
    }

    // 查詢 
    public Map<String, Object> processQuery(Map<String, Object> parameterMap) {
    	Map<String, Object> rtn = new HashMap<String, Object>();
    	
    		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM810M_.queryDOCRMT810")
    				.setParamMap(parameterMap);
            DBResultList queryResult = this.getDao().queryForDBResultList(query);
            
            rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
            rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
    	
        return rtn;
    }
	   
    public Map<String, Object> processAddSave(final Map<String, Object> parameterMap) {
    	String date = DateUtil.getSysWDate();
    	String time = DateUtil.getSysTime();
    	parameterMap.put("CREATE_DATE", date);
    	parameterMap.put("CREATE_TIME", time);
    	parameterMap.put("CREATE_USER_ID", this.getUserProfile().getUserid());
    	Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM810M_.insertDOCRMT810").setParamMap(parameterMap);
    	this.getDao().insert(query);  
    	
    	return null;    	
    }

    public Map<String, Object> processEdit(Map<String, Object> parameterMap) {
        // 自動帶入批號
        Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM810M_.queryDOCRMT810").setParamMap(parameterMap).setPaging(false);
        DBResultList queryResult = this.getDao().queryForDBResultList(query);
        
		Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,
				queryResult.getFirstByMap() == null ? new HashMap() : queryResult.getFirstByMap());
        return rtn;
    }

    public Map<String, Object> processEditSave(Map<String, Object> parameterMap) {
    	String STUS = MapUtil.getString(parameterMap, "STUS","");
    	String date = DateUtil.getSysWDate();
    	String time = DateUtil.getSysTime();
    	parameterMap.put("UPDATE_DATE", date);
    	parameterMap.put("UPDATE_TIME", time);
    	parameterMap.put("UPDATE_USER_ID", this.getUserProfile().getUserid());
    	if("Y".equals(STUS)){
    		Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM810M_.updateSUTS")
    				.setParamMap(parameterMap);
    		this.getDao().update(query1);
    	}
    	Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM810M_.updateDOCRMT810").setParamMap(parameterMap);
		this.getDao().update(query);
		        
        return null;
    }

    public Map<String, Object> processDelete(Map<String, Object> parameterMap) {
    	
        Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM810M_.deleteDOCRMT810").setParamMap(parameterMap);
        
        this.getDao().delete(query);
        
        return null;
    }
      
}
