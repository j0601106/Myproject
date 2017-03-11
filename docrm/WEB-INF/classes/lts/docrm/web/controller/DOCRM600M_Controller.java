/*
@@程式代號 = DOCRM600M_Controller.java
@@程式名稱 = 系統別Controller
@@程式版本 = V1.000
@@更新日期 = 2016/10/19
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.controller.impl.ResponseBean.ReturnType;
import gov.fdc.library.jasper.ProcessReport;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.web.manager.DOCRM600M_Manager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;
import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM600M_Controller.do")
public class DOCRM600M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager docrmUtilManager;
	
    @Autowired
    private DOCRM600M_Manager docrm600M_Manager;

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
     *            Exception
     */
    public void processMain(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        Map paramMap = requestBean.getRequestMap();        

    	Calendar c = Calendar.getInstance();
		String YEAR = String.valueOf(c.get(Calendar.YEAR) - 1911);
		YEAR = YEAR.length() == 2 ? "0" + YEAR : YEAR;
		String MONTH = String.valueOf(c.get(Calendar.MONTH) + 1);
		MONTH = MONTH.length() == 1 ? "0" + MONTH : MONTH;
		
        String TAX_TP =
    			HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP")
    			,"@{PHRASE_TITLE}","@{PHRASE_KEY}");
        String DOC_ORG_ID =
    			HtmlTagFactory.renderOptionHtml("ORG_ID", docrmUtilManager.processDDL("ORG_ID")
    			,"@{PHRASE_TITLE}","@{PHRASE_KEY}");
        String DOC_ORG_ID2 =
    			HtmlTagFactory.renderOptionHtml("ORG_ID", docrmUtilManager.processDDL("ORG_ID")
    			,"@{PHRASE_TITLE}","@{PHRASE_KEY}");
                
        Map<String, Object> rtn = new HashMap<String, Object>();
        rtn.put("TAX_TP", TAX_TP);
        rtn.put("DOC_ORG_ID", DOC_ORG_ID);   
        rtn.put("DOC_ORG_ID2", DOC_ORG_ID2); 
        rtn.put("MONTH",MONTH);
        rtn.put("YEAR", YEAR);
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
     *            Exception
     */
    public void processQuery(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        // 取得登入使用者資訊
		UserProfile userProfile = this.getUserProfile();
		String id = userProfile.getUserid();

        Map paramMap = requestBean.getRequestMap();
        paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "25" : paramMap.get("__PAGE_SIZE"));
        Map<String, ?> dataMap = docrm600M_Manager.processQuery(paramMap);
        responseBean.setData(dataMap);
    }
	/**
	 * processPrint
	 * 
	 * @param requestBean the request bean
	 * @param responseBean the response bean
	 * @param response the response
	 * @throws Exception the exception
	 */
	public void processPrint(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map<String, Object> paramMap = requestBean.getRequestMap();
		Map<String, Object> dataMap = docrm600M_Manager.processPrint(paramMap);
		String DST = MapUtil.getString(paramMap, "DST","");
		String TAX_TP = MapUtil.getString(paramMap, "TAX_TP","");
		String DST_CD = MapUtil.getString(paramMap, "DST_CD","");
		String DOC_ORG_ID = MapUtil.getString(paramMap, "DOC_ORG_ID","");
		String DOC_ORG_ID2 = MapUtil.getString(paramMap, "DOC_ORG_ID2","");
		String fileName = "DOCRM600R_01R";
		if (!"A".equals(DST) && "B".equals(DST_CD) && "".equals(TAX_TP)) {
			fileName = "DOCRM600R_02R";
		} else if (!"A".equals(DST) && "B".equals(DST_CD) && !"".equals(TAX_TP)) {
			fileName = "DOCRM600R_03R";
		}
		Map<String, String> jasper_map = new HashMap<String, String>();
		List<Map<String, Object>> jasper_list = (List<Map<String, Object>>) dataMap.get("datalist");
		
		if (!"0".equals(Integer.toString(dataMap.size()))) {
			if (!"".equals(TAX_TP)) {
				jasper_map.put("TAX_TP", dataMap.get("TAX_TP").toString());// 稅目別
			} else {
				jasper_map.put("TAX_TP", "");
			}
			if (!"".equals(DOC_ORG_ID) || !"".equals(DOC_ORG_ID2)) {
				jasper_map.put("DOC_ORG_ID", dataMap.get("ORG_ID").toString());// 單位別
			} else {
				jasper_map.put("DOC_ORG_ID", "");
			}
		}
		String ORG_NM = "高雄市稅捐稽徵處";
		String SYS = DateUtil.getRocFullDate();
		String A = SYS.substring(0, 3).toString();
		String B = SYS.substring(4, 6).toString();
		String C = SYS.substring(7, 9).toString();
		String SYS_DATE = A + "/" + B + "/" + C;
		String MONTH = MapUtil.getString(paramMap, "MONTH", "");
		String YEAR = MapUtil.getString(paramMap, "YEAR", "");
		String DATE = YEAR + MONTH;
		
		jasper_map.put("ORG_NM", ORG_NM);// 機關名稱
		jasper_map.put("DATE", DATE);// 結案年月
		jasper_map.put("SYS_DATE", SYS_DATE);// 印表日期
		
		if (jasper_list.size() == 0) {
			responseBean.setStatus(ResponseBean.Status.WARN);
			responseBean.setReturnType(ReturnType.WEB_REPORT);
			responseBean.setMessageText("無符合列印資料!!");
		} else {
			ProcessReport report = new ProcessReport();
			report.doWebReport(response, report.XLS, fileName, jasper_map,jasper_list);
		}
	}
	
	@Override
	public String getProgramCd() {
		return "DOCRM600M_";
	}
}
