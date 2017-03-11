/*
@@程式代號 = DOCRM200M_Controller.java
@@程式名稱 = 收件及移轉作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.controller;


import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.support.UploadFile;
import gov.fdc.library.env.ApEnv;
import gov.fdc.library.exception.LTSApplicationException;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.core.util.DOCRMUtil;
import lts.docrm.web.manager.DOCRM200M_Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;
import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM200M_Controller.do")
public class DOCRM200M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;

	@Autowired
	private DOCRM200M_Manager	docrm200M_Manager;
	
	@Override
	public String getProgramCd() {
		return "DOCRM200M_";
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
        
        String DOCRM_TP =
    			HtmlTagFactory.renderOptionHtml("DOCRM_TP", docrmUtilManager.processDDLMess(paramMap), "@{DOCRM_TP_NM}",
    				"@{DOCRM_TP}");
        
        Map<String, Object> rtn = new HashMap<String, Object>();

        rtn.put("TAX_TP", TAX_TP);
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
		Map dataMap = docrm200M_Manager.processQuery(paramMap);
		
		Map editSource = new HashMap();
		List<Map<String, Object>> orgIdList = docrmUtilManager.processDDL("ORG_ID");
		List<Map<String, Object>> neworgIdList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> oldMap : orgIdList) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("TITLE", oldMap.get("PHRASE_TITLE"));
			newMap.put("KEY", oldMap.get("PHRASE_KEY"));
			newMap.put("TYPE_KEY", "PHRASE_TITLE");
			neworgIdList.add(newMap);
		}
		editSource.put("DOC_ORG_ID_SELECT", neworgIdList);
		dataMap.put(DOCRMConstant.WEB_GRID_EDIT_SOURCE, editSource);
		
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail1.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail1(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, ?> dataMap = docrm200M_Manager.processQueryDetail1(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail2.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail2(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, ?> dataMap = docrm200M_Manager.processQueryDetail2(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail3.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail3(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, ?> dataMap = docrm200M_Manager.processQueryDetail3(paramMap);
		responseBean.setData(dataMap);
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
		Map dataMap = docrm200M_Manager.processEdit(paramMap);
		
        String DOC_ORG_ID =
    			HtmlTagFactory.renderOptionHtml("DOC_ORG_ID", docrmUtilManager.processDDL("ORG_ID"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
		String ADD_MK =
				HtmlTagFactory.renderCheckBoxHtml("ADD_MK", getCheckboxList(), "@{ADD_MK_NM}", "@{ADD_MK}", 0);
        
        dataMap.put("DOC_ORG_ID", DOC_ORG_ID);
        dataMap.put("ADD_MK", ADD_MK);
        
		responseBean.setData(dataMap);
	}
	
	/**
	 * processAcceptDocrm.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processAcceptDocrm(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		Map paramMap = requestBean.getRequestMap();
		
		String orgId = this.getUserProfile().getOrgid();
		
		if(!orgId.equals(paramMap.get("DOC_ORG_ID").toString())){
			throw new LTSApplicationException("收件單位非所屬機關");
		}
		
        Map<String, ?> dataMap = docrm200M_Manager.processAcceptDocrm(paramMap);
        responseBean.setData(dataMap);
		
	}

	/**
	 * processTranferDocrm.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processTranferDocrm(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		Map paramMap = requestBean.getRequestMap();
		
		String orgId = this.getUserProfile().getOrgid();
		
		if(orgId.equals(paramMap.get("DOC_ORG_ID").toString())){
			throw new LTSApplicationException("收件單位同所屬機關");
		}
		
		if("".equals(paramMap.get("DOC_ORG_ID").toString())){
			throw new LTSApplicationException("請輸入收件單位");
		}
		
        Map<String, ?> dataMap = docrm200M_Manager.processTranferDocrm(paramMap);
        responseBean.setData(dataMap);
		
	}

	/**
	 * @param requestBean
	 *        來源Bean
	 * @param responseBean
	 *        回傳Bean
	 * @param response
	 *        HttpServletResponse
	 * @throws Exception
	 *         Exception
	 */
	public void processDownload(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map paramMap = requestBean.getRequestMap();

		String MESS_NO = MapUtil.getString(paramMap, "MESS_NO", "");
		String fileName = MESS_NO.concat("_申請書.pdf");
		String filePath = ApEnv.get("messSourcefile");
		String downloadName = fileName.replace(MESS_NO.concat("_"), "");
		
		File f = new File(filePath + "/" + fileName);
		
		if(!f.exists()){
			throw new LTSApplicationException("檔案不存在");
		}
		
		super.pushToClient(response, new FileInputStream(f), f.length(), URLEncoder.encode(downloadName, "UTF-8"),true);
	}
	
	/**
	 * processUpload.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processUpload(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {

		Map paramMap = requestBean.getRequestMap();
		
		List<UploadFile> fileList = requestBean.getMultiFileMap().get("COMP");
		
		if (fileList == null || fileList.isEmpty()) {
			throw new LTSApplicationException("請先選擇欲上傳檔案");
		}
		
		if (fileList != null && fileList.size() > 0) {

			UploadFile uploadFile = fileList.get(0);
			
			String fileName = paramMap.get("MESS_NO2").toString().concat("_").concat(uploadFile.getOrgName());
			String filePath = ApEnv.get("qiaAddfile");
			
			uploadFile.moveFile(new File(filePath + "/" + fileName));
			
			paramMap.put("MESS_NO", paramMap.get("MESS_NO2"));
			paramMap.put("FILE_NO", docrmUtilManager.getNextFileNo(paramMap.get("MESS_NO2").toString()));
			paramMap.put("FILE_NM", fileName);
			paramMap.put("FILE_PATH", filePath);
			paramMap.put("SOURCE",  this.getUserProfile().getOrgid());
			paramMap.put("ADD_STUS",  "Y");
			paramMap.put("FILE_STUS",  "3");
			paramMap.put("SD_DATE", DOCRMUtil.getRocSysdate());
			paramMap.put("SD_TIME", DateUtil.getSysTime());

			docrmUtilManager.processInsertTableByAny("DOCRMT210", paramMap);
			docrmUtilManager.processInsertTableByAny("DOCRMT310", paramMap);
		}
		
	}
	
	// 自塞值用
	private List<Map<String, String>> getCheckboxList() {

		List dataList = new ArrayList();
		Map data = new HashMap();
		data.put("ADD_MK", "Y");
		data.put("ADD_MK_NM", "是");
		dataList.add(data);

		return dataList;
	}
}
