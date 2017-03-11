/*
@@程�?�代??? = TBD101M_ManagerImpl.java
@@程�?��?�稱 = TBD101M_ManagerImpl
@@程�?��?�本 = V1.000
@@?��?��?��??? = 2015/12/01
@@檢查�?  = ?��容由YPM?��??�產???
 */
package lts.docrm.web.manager.impl;


import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.support.FDCTransactionManager;
import gov.fdc.library.env.ApEnv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM300M_Manager;
import lts.docrm.web.manager.DOCRM830M_Manager;
import lts.global.core.dao.LTSDao;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;

@Service
@Scope("prototype")
public class DOCRM300M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM300M_Manager {

    public Map<String, Object> processGetMESS_NO(Map<String, Object> parameterMap) {
    	
    	Map<String, Object> rtn = new HashMap<String, Object>();
    	
    	parameterMap.put("SYS_DATE", DateUtil.getSysWDate());
    	
    	String DataSource = "ds_SSR_E77A";
    	
    	LTSDao dao = null;
		FDCTransactionManager tx = null;
		String ORG_ID = parameterMap.get("ORG_ID").toString();
		String SYS_DATE = parameterMap.get("SYS_DATE").toString();
    	String MESS_NO = "";
    	String SEQ = "";
		
    	dao = this.createDao(DataSource);
		tx = dao.beginTransaction(LTSDao.PROPAGATION_REQUIRES_NEW);
		
		try {
			Query queryDOCRMT860 = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM300M_.queryDOCRMT860")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList queryResultDOCRMT860 = dao
					.queryForDBResultList(queryDOCRMT860);
			
			if(queryResultDOCRMT860.getDataList().size() != 0){
				Query queryDOCRMT860_MAXNO = Query.createQuery()
						.setSqlId("docrm.dao.DOCRM300M_.queryDOCRMT860_MAXNO")
						.setParamMap(parameterMap).setPaging(false);
				DBResultList queryResultDOCRMT860_MAXNO = dao
						.queryForDBResultList(queryDOCRMT860_MAXNO);
				
				SEQ = queryResultDOCRMT860_MAXNO.getFirstByMap().get("MESS_MAX_NO").toString();
			}else{
				SEQ = "001";				
			}
			
			MESS_NO = ORG_ID + SYS_DATE + SEQ;
			parameterMap.put("MESS_NO", MESS_NO);
			
			Query insertDOCRMT860 = Query.createQuery().setSqlId("docrm.dao.DOCRM300M_.insertDOCRMT860").setParamMap(parameterMap);
	    	dao.insert(insertDOCRMT860); 
			
			tx.commit();
			System.out.println(""
					+ " SQL commit");

		} catch (Exception e) {
			tx.rollback();
			System.out.println(""
					+ " SQL Error");

		} finally {
			tx.release();
			tx = null;
		}
		
		rtn.put("MESS_NO", MESS_NO);

        return rtn;
    }
    
    public Map<String, Object> processImportDOCRM(Map<String, Object> parameterMap) {
    	
    	Map<String, Object> rtn = new HashMap<String, Object>();
    	
    	Map<String, Object> map = (Map)parameterMap.get("DOCRM_DATA");
    	
    	//������ �� MESS_NO E771051013001 �e�T�X
    	map.put("ACP_CD", map.get("MESS_NO").toString().substring(0, 3)); 
    	map.put("DOC_ORG_ID", map.get("MESS_NO").toString().substring(0, 3));
    	map.put("DOCRM_STUS", "0");
    	map.put("APPLY_DATE", DateUtil.getSysWDate());
    			
    	//Base64�ѪR���mMESS���ɸ��| /DOCRM/intranet/formMESS/
		String messSourcepath = ApEnv.get("messSourcefile");
		
		//MESS���ɥHMESS_NO�R�W�A�éT�w��PDF��
		String fileName = map.get("MESS_NO").toString() + "_申請書.pdf";
		
		map.put("SOURCE" , map.get("MESS_NO").toString().substring(0, 3));
		map.put("FILE_STUS" , "3");
		map.put("FILE_NM" , fileName);
		map.put("FILE_PATH" , messSourcepath);		
		map.put("SD_DATE", DateUtil.getSysWDate());
		map.put("SD_TIME", DateUtil.getSysTime());
		map.put("CREATE_DATE", DateUtil.getSysWDate());
		map.put("CREATE_TIME", DateUtil.getSysTime());
		map.put("CREATE_USER_ID", "SYS");
				
		//MESS���ɸѪRdecode
		try {			
			String base64File = map.get("CASEFILE").toString();
	    	
			byte[] decoded = Base64.decodeBase64(base64File.getBytes());
			FileOutputStream fos = null;
			
			
			File filecheck = new File(messSourcepath);
			if(!filecheck.exists()){
				filecheck.mkdirs();
			}

			File receivedFile = new File(messSourcepath + fileName);
			fos = new FileOutputStream(receivedFile);

			fos.write(decoded);			
			fos.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//�g�JT200��T210		
    	String DataSource = "ds_SSR_E77A";
    	
    	LTSDao dao = null;
		FDCTransactionManager tx = null;
		
    	dao = this.createDao(DataSource);
		tx = dao.beginTransaction(LTSDao.PROPAGATION_REQUIRES_NEW);
		
		// ���oFILE_NO�g�JDOCRMT210
		String fileNo = getNextFileNo(map.get("MESS_NO").toString(),dao);
		
		map.put("FILE_NO", fileNo);
		
		try {
						
			Query insertDOCRMT200 = Query.createQuery().setSqlId("docrm.dao.DOCRM300M_.insertDOCRMT200").setParamMap(map);
	    	dao.insert(insertDOCRMT200); 
	    	
	    	Query insertDOCRMT210 = Query.createQuery().setSqlId("docrm.dao.DOCRM300M_.insertDOCRMT210").setParamMap(map);
	    	dao.insert(insertDOCRMT210); 
			
	    	Query insertDOCRMT300 = Query.createQuery().setSqlId("docrm.dao.DOCRM300M_.insertDOCRMT300").setParamMap(map);
	    	dao.insert(insertDOCRMT300); 
	    	
	    	Query insertDOCRMT310 = Query.createQuery().setSqlId("docrm.dao.DOCRM300M_.insertDOCRMT310").setParamMap(map);
	    	dao.insert(insertDOCRMT310); 
	    	
			tx.commit();
			System.out.println(""
					+ " SQL commit");

		} catch (Exception e) {
			tx.rollback();
			System.out.println(""
					+ " SQL Error");

		} finally {
			tx.release();
			tx = null;
		}
				
        return rtn;
    }
    
    public String getNextFileNo(String MESS_NO,LTSDao dao) {

		String fileNo = new String();

		Map parameterMap = new HashMap();
		
		parameterMap.put("FILE_NO",  MESS_NO);
		
		Query query = 
				 Query.createQuery().setSqlId("docrm.dao.DOCRMUtil.queryMaxFileNo").setParamMap(parameterMap).setPaging(false);
	    Map map = dao.queryForDBResultList(query).getFirstByMap();
	    
	    if(map == null){
	    	fileNo = MESS_NO + "001";
	    } else {
	    	String part1 = map.get("FILE_NO").toString().substring(0, 13);
	    	int part2 = Integer.parseInt(map.get("FILE_NO").toString().substring(13)) + 1;
	    	fileNo = part1 + String.format("%03d", part2);
	    }
		
		return fileNo;
	}
}
