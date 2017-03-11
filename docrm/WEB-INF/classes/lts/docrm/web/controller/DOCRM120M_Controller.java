/*
@@程式代號 = DOCRM120M_Controller.java
@@程式名稱 = 權限設定
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.library.exception.LTSApplicationException;
import gov.fdc.library.jasper.ProcessReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lts.global.core.util.PhraseUtil;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM120M_Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM120M_Controller.do")
public class DOCRM120M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
	@Autowired
	private DOCRM120M_Manager	docrm120M_Manager;


	@Override
	public String getProgramCd() {
		return "DOCRM120M_";
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
		Map<String, Object> rtn = new HashMap<String, Object>();
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
		Map<String, ?> dataMap = docrm120M_Manager.processQuery(paramMap);
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
		Map<String, ?> dataMap = docrm120M_Manager.processQueryDetail(paramMap);
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
		Map<String, ?> dataMap = docrm120M_Manager.processQueryDetail2(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processAdd.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processAdd(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, Object> rtn = new HashMap<String, Object>();
		responseBean.setData(rtn);
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
		
		requestBean.reSetField(RequestBean.Type.Array, new String[] { "gridDeleteCheck" });
		
		Map paramMap = requestBean.getRequestMap();
		
		Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT122", paramMap, false).getFirstByMap();

		if(resultMap!=null){
			throw new LTSApplicationException("已有相同權限代碼");
		}

		docrmUtilManager.processInsertTableByAny("DOCRMT120", paramMap);
		
		if (paramMap.get("gridDeleteCheck") != null) {
			String[] cpks = (String[]) paramMap.get("gridDeleteCheck");
			
			for(int i = 0; i<cpks.length; i++){
				paramMap.put("FUNCTION_ID", cpks[i]);
				docrmUtilManager.processInsertTableByAny("DOCRMT122", paramMap);
			}
		}
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
		Map dataMap = docrm120M_Manager.processEdit(paramMap);
		responseBean.setData(dataMap);
	}

	/**
	 * processEditSave.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEditSave(RequestBean requestBean, ResponseBean responseBean,
		HttpServletResponse response) throws Exception {
		
		requestBean.reSetField(RequestBean.Type.Array, new String[] { "gridDeleteCheck" });
		
		Map paramMap = requestBean.getRequestMap();
		
		String GROUP_ID = paramMap.get("GROUP_ID").toString();
		String GROUP_ID2 = paramMap.get("GROUP_ID2").toString();
		
		if(!GROUP_ID.equals(GROUP_ID2)){
			Map queryMap = new HashMap();
			queryMap.put("GROUP_ID", GROUP_ID);
			Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT122", queryMap, false).getFirstByMap();

			if(resultMap!=null){
				throw new LTSApplicationException("已有相同權限代碼");
			}
		}

		//刪除資料後再重新新增
		Map criteriaMap = new HashMap();
		criteriaMap.put("GROUP_ID", GROUP_ID2);
		paramMap.put(DOCRMConstant.DAO_DOMAIN_CRITERIA_NAME, criteriaMap);
		
		docrmUtilManager.processDeleteTableByAny("DOCRMT122", paramMap);
		
		if (paramMap.get("gridDeleteCheck") != null) {
			String[] cpks = (String[]) paramMap.get("gridDeleteCheck");
			
			for(int i = 0; i<cpks.length; i++){
				paramMap.put("FUNCTION_ID", cpks[i]);
				docrmUtilManager.processInsertTableByAny("DOCRMT122", paramMap);
			}
		}
		
		docrmUtilManager.processUpdateTableByAny("DOCRMT120", paramMap);
		
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
		requestBean.reSetField(RequestBean.Type.Array, new String[] {
			"gridDeleteCheck" });
		Map paramMap = requestBean.getRequestMap();

		if (paramMap.get("gridDeleteCheck") != null) {
			String[] cpks = (String[]) paramMap.get("gridDeleteCheck");
			
			for(int i = 0; i<cpks.length; i++){
				paramMap.put("GROUP_ID", cpks[i]);
				
				docrmUtilManager.processDeleteTableByAny("DOCRMT120", paramMap);
				docrmUtilManager.processDeleteTableByAny("DOCRMT122", paramMap);
			}	
		}
	}
}
