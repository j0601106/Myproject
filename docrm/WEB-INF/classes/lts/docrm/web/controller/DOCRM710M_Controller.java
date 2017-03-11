/*
@@程式代號 = DOCRM710M_Controller.java
@@程式名稱 = 系統別Controller
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.library.exception.LTSApplicationException;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.web.manager.DOCRM710M_Manager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM710M_Controller.do")
public class DOCRM710M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager docrmUtilManager;

	@Autowired
	private DOCRM710M_Manager docrm710M_Manager;

	/**
	 * processMain
	 * 
	 * @param requestBean
	 *            來源Bean
	 * @param responseBean
	 *            回傳Bean
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 *             Exception
	 */
	public void processMain(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map paramMap = requestBean.getRequestMap();

		String TAX_TP = HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"),
				"@{PHRASE_TITLE}", "@{PHRASE_KEY}");

		String DOCRM_TP = HtmlTagFactory.renderOptionHtml("DOCRM_TP", docrmUtilManager.processDDLMess(paramMap),
				"@{DOCRM_TP_NM}", "@{DOCRM_TP}");

		String ANS_STUS = HtmlTagFactory.renderOptionHtml("ANS_STUS", docrmUtilManager.processDDL("ANS_STUS"),
				"@{PHRASE_TITLE}", "@{PHRASE_KEY}");

		Map<String, Object> rtn = new HashMap<String, Object>();

		rtn.put("TAX_TP", TAX_TP);
		rtn.put("DOCRM_TP", DOCRM_TP);
		rtn.put("ANS_STUS", ANS_STUS);

		responseBean.setData(rtn);
	}

	/**
	 * processQuery 查詢
	 * 
	 * @param requestBean
	 *            來源Bean
	 * @param responseBean
	 *            回傳Bean
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 *             Exception
	 */
	public void processQuery(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		// 取得登入使用者資訊
		UserProfile userProfile = this.getUserProfile();
		String id = userProfile.getUserid();

		Map paramMap = requestBean.getRequestMap();
		paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "10" : paramMap.get("__PAGE_SIZE"));

		Map<String, ?> dataMap = docrm710M_Manager.processQuery(paramMap);
		responseBean.setData(dataMap);
	}

	/**
	 * processQueryA 查詢
	 * 
	 * @param requestBean
	 *            來源Bean
	 * @param responseBean
	 *            回傳Bean
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 *             Exception
	 */
	public void processQueryA(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		// 取得登入使用者資訊
		UserProfile userProfile = this.getUserProfile();
		String id = userProfile.getUserid();

		Map paramMap = requestBean.getRequestMap();
		paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "10" : paramMap.get("__PAGE_SIZE"));

		Map<String, ?> dataMap = docrm710M_Manager.processQuery(paramMap);
		responseBean.setData(dataMap);
	}

	/**
	 * processEdit 編輯
	 * 
	 * @param requestBean
	 *            來源Bean
	 * @param responseBean
	 *            回傳Bean
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 *             Exception
	 */
	public void processEdit(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map paramMap = requestBean.getRequestMap();

		Map<String, Object> rtn = new HashMap<String, Object>();
		Map<String, Object> dataMap = docrm710M_Manager.processEdit(paramMap);
		rtn.put("editData", dataMap.get("editData"));
		responseBean.setData(dataMap);
	}

	/**
	 * processEditSave
	 * 
	 * @param requestBean
	 *            來源Bean
	 * @param responseBean
	 *            回傳Bean
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 *             Exception
	 */
	public void processEditSave(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map paramMap = requestBean.getRequestMap();

		Map<String, ?> dataMap = docrm710M_Manager.processEditSave(paramMap);

		responseBean.setData(null);
	}
	@Override
	public String getProgramCd() {
		// TODO Auto-generated method stub
		return "DOCRM710M_";
	}
}
