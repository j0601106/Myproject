/*
@@程式代號 = DOCRM230M_ManagerImpl.java
@@程式名稱 = 補件作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.core.util.DOCRMUtil;
import lts.docrm.web.manager.DOCRM230M_Manager;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;
import com.hazelcast.client.Connection;
//import com.mysql.jdbc.PreparedStatement;


@Service
@Scope("prototype")
public class DOCRM230M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM230M_Manager {
	
	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM230M_.queryDOCRMT200").setParamMap(parameterMap);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryN(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM230M_.queryDOCRMT200N").setParamMap(parameterMap);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail(Map<String, Object> parameterMap) {

		Query query1 =
				Query.createQuery().setSqlId("docrm.dao.DOCRM230M_.queryDOCRMT230").setParamMap(parameterMap);
		DBResultList DBResultList1 = this.getDao().queryForDBResultList(query1);

		for(int i = 0;i<DBResultList1.getTotalCount();i++){
			Map map = (Map) DBResultList1.getDataList().get(i);
			Map queryMap = new HashMap();
			queryMap.put("MESS_NO", map.get("MESS_NO").toString());
			queryMap.put("DOC_TP", map.get("DOC_TP").toString());
			//List dataList = docrmUtilManager.processSelectTableByAny("DOCRMT210", queryMap, false).getDataList();
			
			Query query2 =
					Query.createQuery().setSqlId("docrm.dao.DOCRM230M_.queryDOCRMT210").setParamMap(queryMap).setPaging(false);
			List dataList = this.getDao().queryForDBResultList(query2).getDataList();
			
			String FILE_LIST = new String();
			
			for(int j = 0; j<dataList.size();j++ ){
				Map dataMap = (Map) dataList.get(j);
				FILE_LIST = FILE_LIST +dataMap.get("FILE_NM");
				FILE_LIST = (j != dataList.size()-1)? FILE_LIST + "<br>":FILE_LIST;
			}
			map.put("FILE_LIST", FILE_LIST);
		}
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList1.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList1.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDocTp(Map<String, Object> parameterMap) {

		
		Map criteriaMap = new HashMap();
		criteriaMap.put("PHRASE_TYPE_KEY", "DOC_TP");
		parameterMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, criteriaMap);
		
		Query query =
				Query.createQuery().setSqlId("docrm.domain.DOCRMT850.selectByAny").setParamMap(parameterMap);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_PAGE_INFO, DBResultList.getPageInfo());
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processEdit(Map<String, Object> parameterMap) {

		Query query = 
				 Query.createQuery().setSqlId("docrm.dao.DOCRM230M_.queryDOCRMT200").setParamMap(parameterMap).setPaging(false);
	    DBResultList queryResult = this.getDao().queryForDBResultList(query);
	        
	    Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,
					queryResult.getFirstByMap() == null ? new HashMap() : queryResult.getFirstByMap());
	     
		return rtn;
	}
	
	@Override
	public Map<String, Object> processEditD(Map<String, Object> parameterMap) {

		Query query = 
				 Query.createQuery().setSqlId("docrm.dao.DOCRM230M_.queryDOCRMT230").setParamMap(parameterMap).setPaging(false);
	    DBResultList queryResult = this.getDao().queryForDBResultList(query);
	        
	    Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,
					queryResult.getFirstByMap() == null ? new HashMap() : queryResult.getFirstByMap());
	     
		return rtn;
	}
	
	@Override
	public Map<String, Object> processEditSaveD(Map<String, Object> parameterMap) {
		int[] rtn = null;

		String ADD_STUS = parameterMap.get("ADD_STUS").toString();
		
		if("N".equals(ADD_STUS)){
			parameterMap.put("CPL_DATE", "");
		} else {
			parameterMap.put("CPL_DATE", DOCRMUtil.getRocSysdate());
		}
		
		//改補件主檔
		Map criteriaMap = new HashMap();
		criteriaMap.put("PKNO", parameterMap.get("PKNO"));
		parameterMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, criteriaMap);
		
		Query query = Query.createQuery().setSqlId("docrm.domain.DOCRMT230.updateByPKNO").setParamMap(parameterMap);
		rtn = this.getDao().update(query);
		
		//改附件明細
		Map criteriaMap2 = new HashMap();
		criteriaMap2.put("DOC_TP", parameterMap.get("DOC_TP2"));
		criteriaMap2.put("DOCRM_NO", parameterMap.get("DOCRM_NO"));
		parameterMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, criteriaMap2);
		
		docrmUtilManager.processUpdateTableByAny("DOCRMT210", parameterMap);
		
		return null;
	}
	
	@Override
	public Map<String, Object> processEditSaveN(Map<String, Object> parameterMap) {
		int[] rtn = null;

		if (parameterMap.get("PKNO") != null) {
			Query query = Query.createQuery().setSqlId("docrm.domain.DOCRMT200.updateByPKNOs").setParamMap(parameterMap);
			rtn = this.getDao().update(query);
		}
		return null;
	}
	
	@Override
	public Map<String, Object> processEditSaveN2(Map<String, Object> parameterMap) {
		int[] rtn = null;

		//主檔狀態
		Map criteriaMap = new HashMap();
		criteriaMap.put("DOCRM_NO", parameterMap.get("DOCRM_NO"));

		parameterMap.put("ADD_STUS", "Y");
		parameterMap.put("ADD_CPL_DATE", DOCRMUtil.getRocSysdate());
		parameterMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, criteriaMap);
		
		docrmUtilManager.processUpdateTableByAny("DOCRMT200", parameterMap);
		
		//附件歷程
		Map queryMap = new HashMap();
		queryMap.put("MESS_NO", parameterMap.get("MESS_NO"));

		List dataList = docrmUtilManager.processSelectTableByAny("DOCRMT210", queryMap, false).getDataList();
		
		for(int i=0; i < dataList.size();i++){
			Map map = (Map) dataList.get(i);
			Map insertMap = new HashMap();

			insertMap.put("FILE_NO", map.get("FILE_NO"));
			insertMap.put("SD_DATE", DOCRMUtil.getRocSysdate());
			insertMap.put("SD_TIME", DateUtil.getSysTime());
			insertMap.put("ADD_STUS", "Y");
			insertMap.put("FILE_STUS", "4");
			
			docrmUtilManager.processInsertTableByAny("DOCRMT310", insertMap);
		}
		
		return null;
	}
}
