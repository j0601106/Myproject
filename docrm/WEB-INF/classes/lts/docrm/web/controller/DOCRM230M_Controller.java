/*
@@程式代號 = DOCRM230M_Controller.java
@@程式名稱 = 補件作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.support.UploadFile;
import gov.fdc.library.env.ApEnv;
import gov.fdc.library.exception.LTSApplicationException;
import gov.fdc.library.jasper.ProcessReport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.core.util.DOCRMUtil;
import lts.docrm.web.manager.DOCRM230M_Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;
import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM230M_Controller.do")
public class DOCRM230M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;

	@Autowired
	private DOCRM230M_Manager	docrm230M_Manager;
	
	@Override
	public String getProgramCd() {
		return "DOCRM230M_";
	}

	/**
	 * processMain.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processMain(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map paramMap = requestBean.getRequestMap();
		
        String TAX_TP =
    			HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
        String ADD_STUS =
    			HtmlTagFactory.renderOptionHtml("ADD_STUS", docrmUtilManager.processDDL("ADD_STUS"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
        String DOCRM_TP =
    			HtmlTagFactory.renderOptionHtml("DOCRM_TP", docrmUtilManager.processDDLMess(paramMap), "@{DOCRM_TP_NM}",
    				"@{DOCRM_TP}");
        
        Map<String, Object> rtn = new HashMap<String, Object>();

        rtn.put("TAX_TP", TAX_TP);
        rtn.put("ADD_STUS", ADD_STUS);
        rtn.put("DOCRM_TP", DOCRM_TP);
        
        responseBean.setData(rtn);
	}

	/**
	 * processQuery.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQuery(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm230M_Manager.processQuery(paramMap);		
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryN.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryN(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm230M_Manager.processQueryN(paramMap);		
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm230M_Manager.processQueryDetail(paramMap);
		
        String ADD_STUS =
    			HtmlTagFactory.renderOptionHtml("ADD_STUS", docrmUtilManager.processDDL("ADD_STUS"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
        dataMap.put("ADD_STUS", ADD_STUS);
		
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDocTp.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDocTp(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm230M_Manager.processQueryDocTp(paramMap);		
		responseBean.setData(dataMap);
	}
	
	/**
	 * processAddSave.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processAddSave(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		Map paramMap = requestBean.getRequestMap();	
		Map queryMap = new HashMap();
		
		queryMap.put("DOCRM_NO", paramMap.get("DOCRM_NO").toString());
		
		Map resultMap  = docrmUtilManager.processSelectTableByAny("DOCRMT200", queryMap, false).getFirstByMap();
		
		if("Y".equals(resultMap.get("ADD_STUS").toString())){
			throw new LTSApplicationException("補件進度已完成不可再新增");
		}
		
		List resultList = (List) docrm230M_Manager.processQueryDetail(queryMap).get(DOCRMConstant.WEB_GRID_RESULT);
		
		if(resultList.size()>=10){
			throw new LTSApplicationException("已超過補件上限");
		}
		
		paramMap.put("ADD_STUS", "N");
		
		docrmUtilManager.processInsertTableByAny("DOCRMT230", paramMap);
	}
	
	/**
	 * processEdit.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEdit(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {

		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm230M_Manager.processEdit(paramMap);
        
		responseBean.setData(dataMap);
	}
	
	/**
	 * processEditD.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEditD(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {

		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm230M_Manager.processEditD(paramMap);
        
		responseBean.setData(dataMap);
	}
	
	/**
	 * processEditSaveD.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEditSaveD(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		Map paramMap = requestBean.getRequestMap();
		
		String DOC_TP = paramMap.get("DOC_TP").toString();
		String DOC_TP2 = paramMap.get("DOC_TP2").toString();
		String DOCRM_NO = paramMap.get("DOCRM_NO").toString();
		
		if(!DOC_TP.equals(DOC_TP2)){
			Map queryMap = new HashMap();
			queryMap.put("DOC_TP", DOC_TP);
			queryMap.put("DOCRM_NO", DOCRM_NO);
			Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT230", queryMap, false).getFirstByMap();

			if(resultMap!=null){
				throw new LTSApplicationException("已有相同缺件明細");
			}
		}
		
		Map<String, ?> dataMap = docrm230M_Manager.processEditSaveD(paramMap);
		
		List<UploadFile> fileList = requestBean.getMultiFileMap().get("COMP");
		
		if (fileList != null && fileList.size() > 0) {

			UploadFile uploadFile = fileList.get(0);
			
			String fileName = paramMap.get("MESS_NO").toString().concat("_").concat(uploadFile.getOrgName());
			String filePath = ApEnv.get("qiaAddfile");
			
			uploadFile.moveFile(new File(filePath + "/" + fileName));
			
			paramMap.put("FILE_NO", docrmUtilManager.getNextFileNo(paramMap.get("MESS_NO").toString()));
			paramMap.put("FILE_NM", fileName);
			paramMap.put("FILE_PATH", filePath);
			paramMap.put("SOURCE",  this.getUserProfile().getOrgid());
			paramMap.put("SD_DATE", DOCRMUtil.getRocSysdate());
			paramMap.put("SD_TIME", DateUtil.getSysTime());
			paramMap.put("FILE_STUS",  "3");

			docrmUtilManager.processInsertTableByAny("DOCRMT210", paramMap);
			docrmUtilManager.processInsertTableByAny("DOCRMT310", paramMap);
		}
	}
	
	/**
	 * processEditSaveN.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEditSaveN(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {
		
		requestBean.reSetField(RequestBean.Type.Array, new String[] { "gridDeleteCheck" });
		
		Map paramMap = requestBean.getRequestMap();

		if (paramMap.get("gridDeleteCheck") != null) {
			String[] cpks = (String[]) paramMap.get("gridDeleteCheck");
			
				paramMap.put("PKNO", cpks);
				paramMap.put("ADD_MK", "Y");
				
				Map<String, ?> dataMap = docrm230M_Manager.processEditSaveN(paramMap);
				responseBean.setData(dataMap);
		}
	}
	
	/**
	 * processEditSaveN2.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEditSaveN2(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		Map paramMap = requestBean.getRequestMap();

		Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT200", paramMap, false).getFirstByMap();

		String ADD_STUS = MapUtil.getString(resultMap, "ADD_STUS", "N");
		
		if("Y".equals(ADD_STUS)){
			throw new LTSApplicationException("已執行");
		}
		
		Map<String, ?> dataMap = docrm230M_Manager.processEditSaveN2(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processEditSaveN2B.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEditSaveN2B(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		requestBean.reSetField(RequestBean.Type.Array, new String[] {"gridDeleteCheck" });
		
		Map paramMap = requestBean.getRequestMap();
		
		Map<String, ?> dataMap = new HashMap();
		
		if (paramMap.get("gridDeleteCheck") != null) {
			String[] cpks = (String[]) paramMap.get("gridDeleteCheck");
			
			for(int i = 0; i<cpks.length; i++){
				
				String[] cpks1 = cpks[i].toString().split(",");
				
				paramMap.put("DOCRM_NO", cpks1[0]);
				paramMap.put("MESS_NO", cpks1[1]);
				
				Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT200", paramMap, false).getFirstByMap();
				
				String ADD_STUS = MapUtil.getString(resultMap, "ADD_STUS", "N");
				
				if("N".equals(ADD_STUS)){
					dataMap = docrm230M_Manager.processEditSaveN2(paramMap);
				}
			}	
		}
		responseBean.setData(dataMap);
	}
	
	/**
	 * processDelete.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processDelete(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		// reSetField()必須在getRequestMap()前執行
		requestBean.reSetField(RequestBean.Type.Array, new String[] {"gridDeleteCheck" });
		
		Map paramMap = requestBean.getRequestMap();

		if (paramMap.get("gridDeleteCheck") != null) {
			String[] cpks = (String[]) paramMap.get("gridDeleteCheck");
			
			for(int i = 0; i<cpks.length; i++){
				
				String[] cpks1 = cpks[i].toString().split(",");
				
				paramMap.put("MESS_NO", cpks1[0]);
				paramMap.put("DOC_TP", cpks1[1]);
				
				List dataList = docrmUtilManager.processSelectTableByAny("DOCRMT210", paramMap, false).getDataList();
				
				for(int j = 0; j<dataList.size();j++ ){
					Map dataMap = (Map) dataList.get(j);
					Map queryMap = new HashMap();
					queryMap.put("FILE_NO", dataMap.get("FILE_NO"));
					docrmUtilManager.processDeleteTableByAny("DOCRMT310", queryMap);
				}
				
				docrmUtilManager.processDeleteTableByAny("DOCRMT210", paramMap);
				docrmUtilManager.processDeleteTableByAny("DOCRMT230", paramMap);
			}	
		}
	}
	
	/**
	 * processSyn.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processSyn(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		Map paramMap = requestBean.getRequestMap();
		
		String DOCRM_NO = MapUtil.getString(paramMap, "DOCRM_NO","");
		
		docrmUtilManager.processCreate200XML(DOCRM_NO);
	}
}
