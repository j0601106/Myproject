/*
@@程式代號 = DOCRM700M_ManagerImpl.java
@@程式名稱 = DOCRM700M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2016/10/20
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;

import java.util.HashMap;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM700M_Manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM700M_ManagerImpl extends DOCRMWebManagerImpl implements
		DOCRM700M_Manager {

	@Override
	public Map<String, Object> processMain(Map<String, Object> parameterMap) {
		return null;
	}

	// 查詢
	@Override
	public Map<String, Object> processQuery (Map<String, Object> parameterMap) {
		Map<String, Object> rtn = new HashMap<String, Object>();

		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.querySTUS")
				.setParamMap(parameterMap);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);

		rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());

		return rtn;
	}

	// 新增儲存
	@Override
	public Map<String, Object> processAddSave(
			final Map<String, Object> parameterMap) {
		//答案
		String A1 = MapUtil.getString(parameterMap, "A1", "");
		String A2 = MapUtil.getString(parameterMap, "A2", "");
		String A3 = MapUtil.getString(parameterMap, "A3", "");
		String A4 = MapUtil.getString(parameterMap, "A4", "");
		//答案代碼
		String RESULT_ID1 = "1";
		String RESULT_ID2 = "2";
		String RESULT_ID3 = "3";
		String RESULT_ID4 = "4";
		//系統日期時間
		String date = DateUtil.getSysWDate();
		String time = DateUtil.getSysTime();
		parameterMap.put("CREATE_DATE", date);
		parameterMap.put("CREATE_TIME", time);
		parameterMap.put("CREATE_USER_ID", this.getUserProfile().getUserid());
		parameterMap.put("RESULT_ID1", RESULT_ID1);
		parameterMap.put("RESULT_ID2", RESULT_ID2);
		parameterMap.put("RESULT_ID3", RESULT_ID3);
		parameterMap.put("RESULT_ID4", RESULT_ID4);

		Query query = Query.createQuery()
				.setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT700")
				.setParamMap(parameterMap);
		this.getDao().insert(query);

		if (!"".equals(A1)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result1")
					.setParamMap(parameterMap);
			this.getDao().insert(query1);
		}
		if (!"".equals(A2)) {
			Query query2 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result2")
					.setParamMap(parameterMap);
			this.getDao().insert(query2);
		}
		if (!"".equals(A3)) {
			Query query3 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result3")
					.setParamMap(parameterMap);
			this.getDao().insert(query3);
		}
		if (!"".equals(A4)) {
			Query query4 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result4")
					.setParamMap(parameterMap);
			this.getDao().insert(query4);
		}

		return null;
	}

	// 編輯
	@Override
	public Map<String, Object> processEdit(Map<String, Object> parameterMap) {
		// 自動帶入批號
		String A1 = "";
		String A2 = "";
		String A3 = "";
		String A4 = "";
		String ANS = "";
		//查詢T710是否有資料
		Query ANSWER = Query.createQuery()
				.setSqlId("docrm.dao.DOCRM700M_.queryedit").setParamMap(parameterMap).setPaging(false);
		DBResultList queryResult2 = this.getDao().queryForDBResultList(ANSWER);
		if (!"0".equals(Integer.toString(queryResult2.getDataList().size()))) {
			ANS = "Y";
		} else {
			ANS = "N";
		}
		//只抓T700啟用資料
		Query query = Query.createQuery()
				.setSqlId("docrm.dao.DOCRM700M_.querySTUS").setParamMap(parameterMap).setPaging(false);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);
		//抓取T710答案欄位
		Query query1 = Query.createQuery()
				.setSqlId("docrm.dao.DOCRM700M_.query710").setParamMap(parameterMap).setPaging(false);
		DBResultList queryResult1 = this.getDao().queryForDBResultList(query1);
		
		//答案按照順序存取
		for (int i = 0; i < queryResult1.getDataList().size(); i++) {
			Map temp = (HashMap) queryResult1.getDataList().get(i);
			if ("1".equals(temp.get("RESULT_ID"))) {
				A1 = temp.get("RESULT").toString();
			} else if ("2".equals(temp.get("RESULT_ID"))) {
				A2 = temp.get("RESULT").toString();
			} else if ("3".equals(temp.get("RESULT_ID"))) {
				A3 = temp.get("RESULT").toString();
			} else if ("4".equals(temp.get("RESULT_ID"))) {
				A4 = temp.get("RESULT").toString();
			}
		}
		Map dataMap = (HashMap) queryResult.getDataList().get(0);// docrmt700只會有一筆資料
		dataMap.put("A1", A1);//答案一
		dataMap.put("A2", A2);//答案二
		dataMap.put("A3", A3);//答案三
		dataMap.put("A4", A4);//答案四
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,queryResult.getFirstByMap() == null ? new HashMap<Object, Object>()
						: queryResult.getFirstByMap());
		rtn.put("ANS", ANS);
		return rtn;
	}

	// 編輯儲存
	@Override
	public Map<String, Object> processEditSave(Map<String, Object> parameterMap) {
		//答案
		String A1 = MapUtil.getString(parameterMap, "A1", "");
		String A2 = MapUtil.getString(parameterMap, "A2", "");
		String A3 = MapUtil.getString(parameterMap, "A3", "");
		String A4 = MapUtil.getString(parameterMap, "A4", "");
		//答案代碼
		String RESULT_ID1 = "1";
		String RESULT_ID2 = "2";
		String RESULT_ID3 = "3";
		String RESULT_ID4 = "4";
		String date = DateUtil.getSysWDate();
		String time = DateUtil.getSysTime();
		parameterMap.put("RESULT_ID1", RESULT_ID1);
		parameterMap.put("RESULT_ID2", RESULT_ID2);
		parameterMap.put("RESULT_ID3", RESULT_ID3);
		parameterMap.put("RESULT_ID4", RESULT_ID4);
		parameterMap.put("UPDATE_DATE", date);
		parameterMap.put("UPDATE_TIME", time);
		parameterMap.put("UPDATE_USER_ID", this.getUserProfile().getUserid());
		parameterMap.put("CREATE_DATE", date);
		parameterMap.put("CREATE_TIME", time);
		parameterMap.put("CREATE_USER_ID", this.getUserProfile().getUserid());

		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.updateDOCRMT700")
				.setParamMap(parameterMap);
		this.getDao().update(query);
		// 刪除710資料
		Query querydelete = Query.createQuery()
				.setSqlId("docrm.dao.DOCRM700M_.deleteDOCRMT710").setParamMap(parameterMap);
		this.getDao().delete(querydelete);
		// 新建資料
		if (!"".equals(A1)) {
			Query query1 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result1")
					.setParamMap(parameterMap);
			this.getDao().insert(query1);
		}
		if (!"".equals(A2)) {
			Query query2 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result2")
					.setParamMap(parameterMap);
			this.getDao().insert(query2);
		}
		if (!"".equals(A3)) {
			Query query3 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result3")
					.setParamMap(parameterMap);
			this.getDao().insert(query3);
		}
		if (!"".equals(A4)) {
			Query query4 = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.insertDOCRMT710Result4")
					.setParamMap(parameterMap);
			this.getDao().insert(query4);
		}
		return null;
	}
	
	@Override
	public Map<String, Object> processNuclear(Map<String, Object> parameterMap) {
		// 檢核
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM700M_.queryNuclear")
				.setParamMap(parameterMap).setPaging(false);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("datalist", queryResult.getDataList());
		return rtn;
	}

}
