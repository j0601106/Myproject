package lts.docrm.api.adapter;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.library.exception.LTSDataAccessException;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.XMLUtil2;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.acer.util2.MapUtil;

@Scope("prototype")
@Component("DOCRM260DOWNAdapter")
public class DOCRM260DOWNAdapter extends DOCRMAdapter{

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
	@Override
	public boolean start() throws Exception {

		boolean FUPO = true;
		boolean ANS = true;
		
		File targetFile1 = new File("\\DOCRM\\intranet\\qia_add_schedule\\down\\");
		File targetFile2 = new File("\\DOCRM\\intranet\\question\\down\\");

		if ((!targetFile1.exists() && !targetFile1.isDirectory()) || (targetFile1.listFiles().length == 0)) {
			FUPO = false;
		}
		if ((!targetFile2.exists() && !targetFile2.isDirectory()) || (targetFile2.listFiles().length == 0)) {
			ANS = false;
		}
		
		if(FUPO){
			File[] fileList = targetFile1.listFiles();
			
			for(int i=0; i<fileList.length; i++){
				
				String filePath = fileList[i].toString();
				File file = new File(filePath);
				String fileNM = file.getName();

				if(fileNM.lastIndexOf(".") == -1 || !"xml".equals(fileNM.substring(fileNM.lastIndexOf(".")+1))){
					continue;
				}
				if(fileNM.contains("FUPO")){
					Map map = XMLUtil2.readDOCRM400XML(filePath);
					this.processDocrm400(map);
				}
				File newfile = new File("\\DOCRM\\intranet\\qia_add_schedule\\backup\\"+fileNM);
				FileUtils.moveFile(file, newfile);
			}
		}

		if(FUPO){
			File[] fileList = targetFile2.listFiles();
			
			for(int i=0; i<fileList.length; i++){
				
				String filePath = fileList[i].toString();
				File file = new File(filePath);
				String fileNM = file.getName();

				if(fileNM.lastIndexOf(".") == -1 || !"xml".equals(fileNM.substring(fileNM.lastIndexOf(".")+1))){
					continue;
				}
				if(fileNM.contains("ANS")){
					Map map = XMLUtil2.readDOCRM710XML(filePath);
					this.processDocrm710(map);
				}
				File newfile = new File("\\DOCRM\\intranet\\question\\backup\\"+fileNM);
				FileUtils.moveFile(file, newfile);
			}
		}
		
		return true;
	}

	/**
	 * processDocrm400
	 * 
	 * @param Map parameterMap
	 */
	public void processDocrm400(Map parameterMap) {	
		List list = (List) parameterMap.get("DOCRMT401LIST");
		
		for(int i =0; i<list.size(); i++){
			Map map = (Map) list.get(i);
			
			Query query;
			
			query = Query.createQuery().setSqlId("docrm.dao.DOCRM260ConvertAdapter.selectDOCRMT200").setParamMap(map);

			map.put("MESS_NO", (String) this.getDao().queryForDBResultList(query).getFirstByMap().get("MESS_NO"));
			map.put("FILE_NO", docrmUtilManager.getNextFileNo(map.get("MESS_NO").toString()));
			map.put("ADD_STUS", "N");
			map.put("FILE_STUS", "2");
			
			query = Query.createQuery().setSqlId("docrm.dao.DOCRM260ConvertAdapter.insertDOCRMT210").setParamMap(map);
			this.getDao().insert(query);
			
			query = Query.createQuery().setSqlId("docrm.dao.DOCRM260ConvertAdapter.insertDOCRMT310").setParamMap(map);
			this.getDao().insert(query);
		}
	}
	
	/**
	 * processDocrm710
	 * 
	 * @param Map parameterMap
	 */
	public void processDocrm710(Map parameterMap) {	
		List list = (List) parameterMap.get("DOCRMT720LIST");
    	Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM260ConvertAdapter.insertDOCRMT720").setBatchParamater(list);
		this.getDao().insert(query);
	}
}
