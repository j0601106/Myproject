/*
@@程式代號 = DOCRM710M_ManagerImpl.java
@@程式名稱 = DOCRM710M_ManagerImpl
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.library.exception.LTSApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM710M_Manager;

import org.apache.tools.ant.taskdefs.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Service
@Scope("prototype")
public class DOCRM710M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM710M_Manager {
	@Override
	public Map<String, Object> processMain(Map<String, Object> parameterMap) {
		return null;
	}

	// 查詢
	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {

		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM710M_.queryDOCRMT710").setParamMap(parameterMap);

		DBResultList queryResult = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();

		rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
		return rtn;
	}
	@Override
	public Map<String, Object> processQueryA(Map<String, Object> parameterMap) {

		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM710M_.queryDOCRMT710_QUESTION")
				.setParamMap(parameterMap);

		DBResultList queryResult = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();

		rtn.put(DOCRMConstant.WEB_PAGE_INFO, queryResult.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
		return rtn;
	}
	@Override
	public Map<String, Object> processEdit(Map<String, Object> parameterMap) {

		String HTML_NAME = "";
		String HTML_VALUE = "";
		String HTML_TEXT = "";
		String C_TEXT = "";
		// 自動帶入批號
		Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM710M_.queryDOCRMT710_QUESTION")
				.setParamMap(parameterMap).setPaging(false);
		DBResultList queryResult = this.getDao().queryForDBResultList(query);

		// 動態的方式產生問卷
		for (int j = 0; j < queryResult.getDataList().size(); j++) {
			Map map = (Map) queryResult.getDataList().get(j);
			Query Query_DOCRM710 = Query.createQuery().setSqlId("docrm.dao.DOCRM710M_.queryDOCRMT710_DETIAL")
					.setParamMap(map).setPaging(false);
			DBResultList queryResult_DOCRM710 = this.getDao().queryForDBResultList(Query_DOCRM710);
			map.put("QUESTION_HTML", "");
			map.put("DOCRM_NO", parameterMap.get("DOCRM_NO").toString());

			for (int i = 0; i < queryResult_DOCRM710.getDataList().size(); i++) {
				Map map710 = (Map) queryResult_DOCRM710.getDataList().get(i);
				HTML_NAME = map.get("QUESTION_ID").toString();
				HTML_VALUE = map710.get("RESULT_ID").toString();
				HTML_TEXT = map710.get("RESULT").toString();
				C_TEXT = parameterMap.get("DOCRM_NO").toString();
				// 撈取已填寫過問卷的資料
				Query query_ = Query.createQuery().setSqlId("docrm.dao.DOCRM710M_.queryDOCRMT710_Person")
						.setParamMap(map).setPaging(false);
				DBResultList queryResult_ = this.getDao().queryForDBResultList(query_);
				List<Map> queryList = (List<Map>) queryResult_.getDataList();
				List dataList = new ArrayList();
				String RESULT_ID = "";
				for (Map dataMap : queryList) {
					RESULT_ID = dataMap.get("RESULT_ID").toString();
				}

				// map.put("QUESTION_HTML", map.get("QUESTION_HTML") + "<input
				// type='radio' name='" + HTML_NAME +"' value='"+ HTML_VALUE
				// +"'>"+ HTML_TEXT + "<br/>" );

				if (!"".equals(RESULT_ID)) {
					if (HTML_VALUE.equals(RESULT_ID)) {
						map.put("QUESTION_HTML", map.get("QUESTION_HTML") + "<input type='radio' name='" + HTML_NAME
								+ "' value='" + HTML_VALUE + "'checked='CHECKED'>" + HTML_TEXT + "<br/>");
					} else {
						map.put("QUESTION_HTML", map.get("QUESTION_HTML") + "<input type='radio' name='" + HTML_NAME
								+ "' value='" + HTML_VALUE + ">" + HTML_TEXT + "<br/>");
					}
				} else {
					map.put("QUESTION_HTML", map.get("QUESTION_HTML") + "<input type='radio' name='" + HTML_NAME
							+ "' value='" + HTML_VALUE + "'checked='CHECKED'>" + HTML_TEXT + "<br/>");
				}
			}

		}

		Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT, parameterMap);// 回傳jsp
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, queryResult.getDataList());
		return rtn;
	}

	private Object get(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> processEditSave(Map<String, Object> parameterMap) {

		// 取得題目
		Query QUESTION = Query.createQuery().setSqlId("docrm.dao.DOCRM710M_.queryDOCRMT710_QUESTION")
				.setParamMap(parameterMap).setPaging(false);
		DBResultList queryResult = this.getDao().queryForDBResultList(QUESTION);

		for (int i = 0; i < queryResult.getDataList().size(); i++) {
			Map map = (Map) queryResult.getDataList().get(i);

			String QUESTION_ID = map.get("QUESTION_ID").toString();
			String RESULT_ID2 = MapUtil.getString(parameterMap, map.get("QUESTION_ID"), "");
			if (!"".equals(RESULT_ID2)) {
				parameterMap.put("RESULT_ID", RESULT_ID2);
				String curr_date = DateUtil.getSysWDate();
				String curr_time = DateUtil.getSysTime();
				parameterMap.put("CREATE_DATE", curr_date);
				parameterMap.put("CREATE_TIME", curr_time);
				parameterMap.put("RESPOND_DATE", curr_date);
				parameterMap.put("CREATE_USER_ID", this.getUserProfile().getUserid());
				parameterMap.put("QUESTION_ID", QUESTION_ID);
				Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM710M_.insertDOCRMT720")
						.setParamMap(parameterMap);
				this.getDao().insert(query);
			}

		}

		return null;
	}

}
