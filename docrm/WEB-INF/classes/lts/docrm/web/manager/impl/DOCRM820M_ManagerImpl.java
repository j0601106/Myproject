/*
@@程式代號 = DOCRM820M_ManagerImpl.java
@@程式名稱 = DOCRM820M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2015/12/01
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.library.exception.LTSApplicationException;

import java.util.HashMap;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM820M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;

@Service
@Scope("prototype")
public class DOCRM820M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM820M_Manager {
	@Override
	public Map<String, Object> processMain(Map<String, Object> parameterMap) {
		return null;
	}

	// 查詢
	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {

		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM820M_.queryDOCRMT820").setParamMap(parameterMap);

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
		parameterMap.put("CREATE_DATE", curr_date);
		parameterMap.put("CREATE_TIME", curr_time);
		parameterMap.put("CREATE_USER_ID", this.getUserProfile().getUserid());
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM820M_.insertDOCRMT820").setParamMap(parameterMap);

		this.getDao().insert(query);

		return null;
	}

	@Override
	public Map<String, Object> processEdit(Map<String, Object> parameterMap) {

		// 自動帶入批號
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM820M_.queryDOCRMT820").setParamMap(parameterMap)
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

		Query query = null;
		// 與T200的資料比對

		query = Query.createQuery().setSqlId("docrm.dao.DOCRM820M_.querylOOKfORtO200").setParamMap(parameterMap);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);

		query = Query.createQuery().setSqlId("docrm.dao.DOCRM820M_.querylOOKT200").setParamMap(parameterMap);
		DBResultList queryResult1 = this.getDao().queryForDBResultList(query);

		// 檢核DOCRMT200是否已有使用，若為重新輸入的值卻可以更改使用
		if (queryResult.getDataList().isEmpty()) {

		} else {
			throw new LTSApplicationException("代碼重覆無法修改！");
		}

		if (queryResult1.getDataList().isEmpty()) {

		} else {
			throw new LTSApplicationException("此代碼已被使用無法更改！");
		}

		parameterMap.put("UPDATE_DATE", curr_date);
		parameterMap.put("UPDATE_TIME", curr_time);
		parameterMap.put("UPDATE_USER_ID", this.getUserProfile().getUserid());

		query = Query.createQuery().setSqlId("docrm.dao.DOCRM820M_.updateDOCRMT820").setParamMap(parameterMap);

		this.getDao().update(query);

		return null;
	}

	@Override
	public Map<String, Object> processDelete(Map<String, Object> parameterMap) {
		String curr_date = DateUtil.getSysWDate();
		String curr_time = DateUtil.getSysTime();
		parameterMap.put("UPDATE_DATE", curr_date);
		parameterMap.put("UPDATE_TIME", curr_time);
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM820M_.deleteDOCRMT820").setParamMap(parameterMap);

		this.getDao().delete(query);

		return null;
	}

}
