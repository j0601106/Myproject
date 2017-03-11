/*
@@程式代號 = DOCRM120M_ManagerImpl.java
@@程式名稱 = 權限設定
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
import lts.docrm.web.manager.DOCRM120M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;

@Service
@Scope("prototype")
public class DOCRM120M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM120M_Manager {

	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.domain.DOCRMT120.selectByAny").setParamMap(parameterMap);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.domain.DOCRMT121.selectByAny").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail2(Map<String, Object> parameterMap) {

		String CHK = new String();
		
		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM120M_.queryGROUPFUMCTION").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		for(int i = 0;i<DBResultList.getTotalCount();i++){
			Map map = (Map) DBResultList.getDataList().get(i);
			String FUNCTION_ID = map.get("FUNCTION_ID").toString();
			String H_FUNCTION_ID = map.get("H_FUNCTION_ID").toString();

			if(FUNCTION_ID.equals(H_FUNCTION_ID)){
				CHK = "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='"+FUNCTION_ID+"' checked>";
			} else {
				CHK = "<input type='checkbox' id='gridDeleteCheck' name='gridDeleteCheck' value='"+FUNCTION_ID+"'>";
			}
			map.put("CHK", CHK);
		}
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}

	@Override
	public Map<String, Object> processEdit(Map<String, Object> parameterMap) {

		Query query = 
				 Query.createQuery().setSqlId("docrm.domain.DOCRMT120.selectByPKNO").setParamMap(parameterMap).setPaging(false);
	    DBResultList queryResult = this.getDao().queryForDBResultList(query);
	        
	    Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,
					queryResult.getFirstByMap() == null ? new HashMap() : queryResult.getFirstByMap());
	     
		return rtn;
	}
}
