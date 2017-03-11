/*
@@程式代號 = DOCRM240M_Controller.java
@@程式名稱 = 附件下載
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.controller.impl.ResponseBean.ReturnType;
import gov.fdc.library.exception.LTSApplicationException;
import gov.fdc.library.jasper.ProcessReport;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import lts.global.core.util.PhraseUtil;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM240M_Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM240M_Controller.do")
public class DOCRM240M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
	@Autowired
	private DOCRM240M_Manager	docrm240M_Manager;


	@Override
	public String getProgramCd() {
		return "DOCRM240M_";
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
		
        String TAX_TP =
    			HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
        String DOCRM_TP =
    			HtmlTagFactory.renderOptionHtml("DOCRM_TP", docrmUtilManager.processDDLMess(paramMap), "@{DOCRM_TP_NM}",
    				"@{DOCRM_TP}");

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
		Map<String, ?> dataMap = docrm240M_Manager.processQuery(paramMap);
		responseBean.setData(dataMap);
	}

	/**
	 * processDownload
	 * 
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

		try {
			String FILE_NM = (String) paramMap.get("FILE_NM");
			String FILE_PATH = (String) paramMap.get("FILE_PATH");
			String DOWNLOADNM = FILE_NM.substring(14);
			
			response.setHeader(DOCRMConstant.HTTP_HEADER_CONTENT_TYPE, DOCRMConstant.HTTP_MINI_TYPE_OCTETSTREAM);
			response.setCharacterEncoding("UTF-8");
			
			File f = new File(FILE_PATH + "/" + FILE_NM); // "/" 為分隔字元 or "\\" 為分隔字元
			super.pushToClient(response, new FileInputStream(f), f.length(), URLEncoder.encode(DOWNLOADNM, "UTF-8"),
				true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			throw e;
		}
		responseBean.setReturnType(ReturnType.DOWNLOAD);
	}
}
