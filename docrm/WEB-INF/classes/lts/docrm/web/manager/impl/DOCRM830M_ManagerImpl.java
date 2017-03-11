/*
@@程式代號 = TBD101M_ManagerImpl.java
@@程式名稱 = TBD101M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2015/12/01
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;


import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;

import java.util.HashMap;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM830M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;

@Service
@Scope("prototype")
public class DOCRM830M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM830M_Manager {
	@Override
    public Map<String, Object> processMain(Map<String, Object> parameterMap) {
        return null;
    }

    // 查詢
	@Override
    public Map<String, Object> processQuery(Map<String, Object> parameterMap) {
                
        Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM830M_.queryDOCRMT830").setParamMap(parameterMap);
        
        DBResultList queryResult = this.getDao().queryForDBResultList(query);
        
        Map<String, Object> rtn = new HashMap<String, Object>();
        rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
        rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
        return rtn;
    }
	@Override
    public Map<String, Object> processAddSave(final Map<String, Object> parameterMap) {
    	String curr_date = DateUtil.getSysWDate();
		String curr_time = DateUtil.getSysTime();
		String Date_ms = curr_time.substring(0, 6);
		parameterMap.put("CREATE_DATE", curr_date);
		parameterMap.put("CREATE_TIME", Date_ms);
		parameterMap.put("CREATE_USER_ID", this.getUserProfile().getUserid());
    	Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM830M_.insertDOCRMT830").setParamMap(parameterMap);
    	
    	this.getDao().insert(query);  
    	
    	return null;    	
    }
    
    

	@Override
    public Map<String, Object> processEdit(Map<String, Object> parameterMap) {
		// 自動帶入批號
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM830M_.queryDOCRMT830").setParamMap(parameterMap)
				.setPaging(false);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);

		Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,
				queryResult.getFirstByMap() == null ? new HashMap() : queryResult.getFirstByMap());
        return rtn;
    }
	@Override
	public Map<String, Object> processEditSave(Map<String, Object> parameterMap) {
		String curr_date = DateUtil.getSysWDate();
		String curr_time = DateUtil.getSysTime();
		String Date_ms = curr_time.substring(0, 6);
		parameterMap.put("UPDATE_DATE", curr_date);
    	parameterMap.put("UPDATE_TIME", Date_ms);
    	parameterMap.put("UPDATE_USER_ID", this.getUserProfile().getUserid());
    	Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM830M_.updateDOCRMT830").setParamMap(parameterMap);
		
		this.getDao().update(query);
		        
        return null;
    }
	@Override
	public Map<String, Object> processDelete(Map<String, Object> parameterMap) {

		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM830M_.deleteDOCRMT830").setParamMap(parameterMap);
        
        this.getDao().delete(query);
        
        return null;
    }
    
}
