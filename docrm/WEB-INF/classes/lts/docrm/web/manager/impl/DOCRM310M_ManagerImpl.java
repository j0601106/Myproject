/*
@@程�?�代??? = DOCRM310M_ManagerImpl.java
@@程�?��?�稱 = DOCRM310M_ManagerImpl
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM300M_Manager;
import lts.docrm.web.manager.DOCRM310M_Manager;
import lts.docrm.web.manager.DOCRM830M_Manager;
import lts.global.core.dao.LTSDao;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;

@Service
@Scope("prototype")
public class DOCRM310M_ManagerImpl extends DOCRMWebManagerImpl implements DOCRM310M_Manager {

    public Map<String, Object> processGenTxtByDocrm(Map<String, Object> parameterMap) {
    	
    	Map<String, Object> rtn = new HashMap<String, Object>();
    	    	
    	String DataSource = "ds_SSR_E77A";
    	
    	LTSDao dao = null;
		FDCTransactionManager tx = null;
		    	
    	String fileName = parameterMap.get("DOCRM_NO").toString();
    			
    	dao = this.createDao(DataSource);
		tx = dao.beginTransaction(LTSDao.PROPAGATION_REQUIRES_NEW);
		
		try {
			//�����ץ�D�ɨõ���F0~FB���
			Query query = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM310M_.queryDOCRMT200")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList resultList = dao.queryForDBResultList(query);
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultList.getDataList();
			Map<String, Object> txtMap = null;

			if (dataList != null && dataList.size() > 0) {
				txtMap = new HashMap<String, Object>();

				for (Map<String, Object> dataMap : dataList) {

					StringBuffer txt = new StringBuffer();

					txt.append("[online_in]\r\n");

					for (int j = 0; j < dataMap.size(); j++) {
						String fieldName = null;

						if (j < 10) {
							fieldName = "F" + j;
						} else {
							char c = (char) (j + 55);
							fieldName = "F" + c;
						}
						txt.append(fieldName + "=\"" + dataMap.get(fieldName) + "\"\r\n");
					}

					txtMap.put(fileName, txt.toString());
				}
			}

			//�NF0~FB�g�JTXT�ɨé�m /DOCRM/intranet/qia_receive/
			String onlinePath = ApEnv.get("qiaTo1U");
						
			File filecheck = new File(onlinePath);
			
			if(!filecheck.exists()){
				filecheck.mkdirs();
			}
			
			Iterator<String> iter = txtMap.keySet().iterator();
			while (iter.hasNext()) {
				String fileKey = iter.next();
				String txtFile = fileName + ".txt";
				String txt = txtMap.get(fileKey).toString();

				if (!"".equals(txt)) {
					FileUtils.writeStringToFile(new File(onlinePath + txtFile), txt,
						"UTF-8");
				}
			}
			
			//�����ץ�����ɸ�ơA�����ɦW��W�� �ץ�s��_�y����
			Query queryAttach = Query.createQuery()
					.setSqlId("docrm.dao.DOCRM310M_.queryDOCRMT210")
					.setParamMap(parameterMap).setPaging(false);
			DBResultList resultListAttach = dao.queryForDBResultList(queryAttach);
			
			File AttachFileBefore;
			File AttachFileAfter;
			
			if(resultListAttach.getDataList().size() != 0){
				for (int j = 0; j < resultListAttach.getDataList().size(); j++) {
					Map map = (Map) resultListAttach.getDataList().get(j);
					String AttachFilePath = map.get("FILE_PATH").toString();					
					String AttachFileNM = map.get("FILE_NM").toString();								
					String AttachFileEXNM = AttachFileNM.substring(AttachFileNM.lastIndexOf("."), AttachFileNM.length());
												
					//copy DOCRMT210���ɸ��|��/DOCRM/intranet/qia_receive/
					FileUtils.copyFileToDirectory(new File(AttachFilePath + "/" + AttachFileNM), new File(onlinePath));
					
					AttachFileBefore = new File(onlinePath + "/" + AttachFileNM);					
					AttachFileAfter = new File(onlinePath + "/" + fileName + "_" + (j + 1) + AttachFileEXNM);	
					
					//DOCRMT210���ɭ��s�R�W�� ����s��_�y����
					AttachFileBefore.renameTo(AttachFileAfter);
				}
			}
			
			parameterMap.put("DOCRM_STUS", "3");
			parameterMap.put("MSG_STUS", "1");
			parameterMap.put("SD_DATE", DateUtil.getSysWDate());
			parameterMap.put("SD_TIME", DateUtil.getSysTime());
			parameterMap.put("CREATE_DATE", DateUtil.getSysWDate());
			parameterMap.put("CREATE_TIME", DateUtil.getSysTime());
			parameterMap.put("CREATE_USER_ID", "SYS");
			
			//����DOCRM_STUS���A��3 �ݱ���
			Query updateDOCRMT200 = Query.createQuery().setSqlId("docrm.dao.DOCRM310M_.updateDOCRMT200").setParamMap(parameterMap);
	    	dao.update(updateDOCRMT200); 
	    	
	    	Query insertDOCRMT300 = Query.createQuery().setSqlId("docrm.dao.DOCRM310M_.insertDOCRMT300").setParamMap(parameterMap);
	    	dao.insert(insertDOCRMT300); 
	    	
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
}
