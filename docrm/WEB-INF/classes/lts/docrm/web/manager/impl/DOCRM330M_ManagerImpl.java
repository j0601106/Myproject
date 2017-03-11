/*
@@程�?�代??? = DOCRM340M_ManagerImpl.java
@@程�?��?�稱 = DOCRM340M_ManagerImpl
@@程�?��?�本 = V1.000
@@?��?��?��??? = 2015/12/01
@@檢查�?  = ?��容由YPM?��??�產???
 */
package lts.docrm.web.manager.impl;


import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.support.FDCTransactionManager;
import gov.fdc.library.env.ApEnv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;

import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.core.util.FileAccess;
import lts.docrm.web.manager.DOCRM300M_Manager;
import lts.docrm.web.manager.DOCRM330M_Manager;
import lts.docrm.web.manager.DOCRM830M_Manager;
import lts.global.core.dao.LTSDao;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acer.util.DateUtil;

@Service
@Scope("prototype")
public class DOCRM330M_ManagerImpl extends DOCRMWebManagerImpl implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		Map<String, Object> rtn = new HashMap<String, Object>();
		
		System.out.println("AAAAAAAAA");
		
		String Source1Upath = ApEnv.get("1UToDocrmSchedul");
		
//		File Dir = new File(Source1Upath);
		
		File Dir_1U = new File(Source1Upath);
		if(!Dir_1U.exists()){
			Dir_1U.mkdirs();
		}
    	  
		
		String[] filenameList;
		Map<String, Object> dataMap = null;
		File receivedFile;
		File receivedFileDone = new File(Dir_1U + "/Done/");

//		if(Dir.isDirectory()){
//			filenameList = Dir.list();
//			for (int i = 0 ; i < filenameList.length ; i++) {
//				try {
//					String[] FileEx = filenameList[i].split("\\.");
//					
//					if(FileEx.length > 1){
//				    	  if(FileEx[1].toLowerCase().equals("zip")){
//				    		  FileUtils.copyFileToDirectory(new File(Dir + "/" + filenameList[i]), Dir_1U);
//				    	  }
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		
		if(Dir_1U.isDirectory()){
//			filenameList = Dir_1U.list();
//			for (int i = 0 ; i < filenameList.length ; i++){                                                                                                                    
//			      System.out.println("File=" + filenameList[i]);
//			      
//			      String[] FileEx = filenameList[i].split("\\.");
//			      
//			      receivedFile = new File(Dir_1U + "/" + filenameList[i]);
//			      
//			      if(FileEx.length > 1){
//			    	  if(FileEx[1].toLowerCase().equals("zip")){
//			    		  try {
//					    	  FileAccess.unzipCn(receivedFile.toString(), Dir_1U.toString() + "/");
//					    	  
//					    	  if(!receivedFileDone.exists()){
//					    		  receivedFileDone.mkdirs();
//					  		  }
//					    	  
//					    	  FileUtils.copyFileToDirectory(receivedFile, receivedFileDone);
//					    	  receivedFile.delete();
//					    	  
//					      } catch (Exception e) {
//					    	  // TODO Auto-generated catch block
//					    	  e.printStackTrace();
//					      }
//			    	  }
//			      }
//			 }
			 
			 filenameList = Dir_1U.list();
			 for (int j = 0 ; j < filenameList.length ; j++){                                                                                                                    

			      receivedFile = new File(Dir_1U + "/" + filenameList[j]);
			      
			      String[] FileEx = filenameList[j].split("\\.");
			      			      
			      if(FileEx.length > 1){
			    	  if(FileEx[1].toLowerCase().equals("txt")){			    		  
			    		  try {
							dataMap = onlineParser(receivedFile.toString(), "utf-8");
							
							String F0 = dataMap.get("F0").toString();	//F0
							String F1 = dataMap.get("F1").toString();	//F1
							String F2 = dataMap.get("F2").toString();	//F2 
							String F3 = dataMap.get("F3").toString();	//F3
							String F4 = dataMap.get("F4").toString();	//F4
							String F5 = dataMap.get("F5").toString();	//F5
							String F6 = dataMap.get("F6").toString();	//F6
							String F7 = dataMap.get("F7").toString();	//F7
							String F8 = dataMap.get("F8").toString();	//F8 
							
							if(F5.equals("")){
								dataMap.put("DOCRM_STUS", "4");			//DOCRM_STUS
							}else{
								dataMap.put("DOCRM_STUS", "5");			//DOCRM_STUS
							}
							
							dataMap.put("DOCRM_NO" , F0);
							dataMap.put("RDC_NO" , F1);
							dataMap.put("DOC_STF_NM" , F4);
							dataMap.put("CPL_DATE" , F5);
							dataMap.put("DOC_STF_CD" , F7);
							dataMap.put("LMT_DATE" , F8);
							
							String DataSource = "ds_SSR_E77A";
					    	
					    	LTSDao dao = null;
							FDCTransactionManager tx = null;
							
					    	dao = this.createDao(DataSource);
							tx = dao.beginTransaction(LTSDao.PROPAGATION_REQUIRES_NEW);
							
							try {
								Query updateDOCRMT200 = Query.createQuery().setSqlId("docrm.dao.DOCRM330M_.updateDOCRMT200").setParamMap(dataMap);
						    	dao.update(updateDOCRMT200); 
											
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
													
							FileUtils.copyFileToDirectory(receivedFile, receivedFileDone);
					    	
							receivedFile.delete();
						  } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						  }
			    	  }
			      }
			 }
		}    	
	}    
	
	/**
	 * �ѪR�u�W�ӿ�txt��
	 * 
	 * @param filePath filePath
	 * @param charset charset
	 * @return map map
	 * @throws IOException IOException
	 */
	public Map<String, Object> onlineParser(String filePath, String charset) throws IOException {
		if (charset == null || "".equals(charset.trim())
			|| (!"utf-8".equals(charset.toLowerCase()) && !"utf8".equals(charset.toLowerCase()))) {
			charset = "MS950"; 
		}
		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(filePath)), charset);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		Map<String, Object> result = new HashMap<String, Object>();
		while ((line = br.readLine()) != null) {
			String[] data = line.split("=");
			if (data != null && data.length == 2) {
				result.put(data[0], data[1].replaceAll("\"", ""));
			}
		}
		isr.close();
		br.close();

		return result;
	}
}
