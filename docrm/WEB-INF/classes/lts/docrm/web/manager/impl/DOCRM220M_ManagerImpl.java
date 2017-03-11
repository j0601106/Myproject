/*
@@程式代號 = DOCRM220M_ManagerImpl.java
@@程式名稱 = 列印回執聯作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.manager.impl;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.support.GenerateResult;
import gov.fdc.framework.core.util.FdcThreadHolder;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM120M_Manager;
import lts.docrm.web.manager.DOCRM200M_Manager;
import lts.docrm.web.manager.DOCRM220M_Manager;
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
public class DOCRM220M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM220M_Manager {
	
	@Override
	public Map<String, Object> processQuery(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM220M_.queryDOCRMT200").setParamMap(parameterMap);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		String FUNCTION = new String();
		
		for(int i = 0;i<DBResultList.getTotalCount();i++){
			Map map = (Map) DBResultList.getDataList().get(i);
			String PKNO = map.get("PKNO").toString();
			FUNCTION = "<input type='button' id='FUNCTION' name='FUNCTION' value='列印回執聯' onclick=processPrint({PKNO:'"+PKNO+"'})>";
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
				Query.createQuery().setSqlId("docrm.dao.DOCRM220M_.queryDOCRMT300").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail2(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM220M_.queryDOCRMT300MSG").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail3(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM220M_.queryDOCRMT210").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		String FUNCTION = new String();
		
		for(int i = 0;i<DBResultList.getTotalCount();i++){
			Map map = (Map) DBResultList.getDataList().get(i);
			
			String DOCRM_NO = map.get("DOCRM_NO").toString();
			String FILE_NO = map.get("FILE_NO").toString();
			String MESS_NO = map.get("MESS_NO").toString();
			String FILE_NM = map.get("FILE_NM").toString();
			String SOURCE_NM = map.get("SOURCE_NM").toString();
			String str = "{DOCRM_NO:'"+DOCRM_NO+"',FILE_NO:'"+FILE_NO+"',MESS_NO:'"+MESS_NO+"',FILE_NM:'"+FILE_NM+"',SOURCE_NM:'"+SOURCE_NM+"'}";
			
			FUNCTION = "<input type='button' id='FUNCTION' name='FUNCTION' value='查看歷程' onclick=processQueryDetail4("+str+")>";
			map.put("FUNCTION", FUNCTION);
		}
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processQueryDetail4(Map<String, Object> parameterMap) {

		Query query =
				Query.createQuery().setSqlId("docrm.dao.DOCRM220M_.queryDOCRMT310").setParamMap(parameterMap).setPaging(false);
		DBResultList DBResultList = this.getDao().queryForDBResultList(query);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put(DOCRMConstant.WEB_GRID_RESULT, DBResultList.getDataList());

		return rtn;
	}
	
	@Override
	public Map<String, Object> processEdit(Map<String, Object> parameterMap) {

		Query query = 
				 Query.createQuery().setSqlId("docrm.dao.DOCRM220M_.queryDOCRMT200").setParamMap(parameterMap).setPaging(false);
	    DBResultList queryResult = this.getDao().queryForDBResultList(query);
	        
	    Map rtn = new HashMap();
		rtn.put(DOCRMConstant.WEB_EDIT_DATA_RESULT,
					queryResult.getFirstByMap() == null ? new HashMap() : queryResult.getFirstByMap());
	     
		return rtn;
	}
	
}
