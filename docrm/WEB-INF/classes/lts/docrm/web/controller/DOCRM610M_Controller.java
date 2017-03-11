/*
@@程式代號 = DOCRM610M_Controller.java
@@程式名稱 = 系統別Controller
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.controller.impl.ResponseBean.ReturnType;
import gov.fdc.library.jasper.ProcessReport;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.web.manager.DOCRM610M_Manager;

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
@RequestMapping("/docrm/DOCRM610M_Controller.do")
public class DOCRM610M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
    @Autowired
    private DOCRM610M_Manager docrm610M_Manager;

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
    public void processMain(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
    	
        Map paramMap = requestBean.getRequestMap();
		paramMap.put("Rootorgid", this.getUserProfile().getRootorgid());
		paramMap.put("Date_Y", DateUtil.getSysWDate());
		String Date_Y = DateUtil.getSysWDate();
		String Date_MMM = Date_Y.substring(3, 5);
		String Datr_YYY = Date_Y.substring(0, 3);
		String Day = Date_Y.substring(5, 7);

		String TAX_TP =
				HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"),
				"@{PHRASE_TITLE}", "@{PHRASE_KEY}");
		String DOC_ORG_ID = "";

		DOC_ORG_ID = HtmlTagFactory.renderOptionHtml("ORG_ID", docrmUtilManager.processDDL("ORG_ID"), "",
				"@{PHRASE_TITLE}", "@{PHRASE_KEY}", true);
        
        Map<String, Object> rtn = new HashMap<String, Object>();
        rtn.put("APPLY_DATE_YEAR", Datr_YYY);
        rtn.put("APPLY_DATE_Mo", Date_MMM);
        rtn.put("DOC_ORG_ID", DOC_ORG_ID);
        rtn.put("TAX_TP", TAX_TP);
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
    public void processQuery(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        // 取得登入使用者資訊
		UserProfile userProfile = this.getUserProfile();
		String id = userProfile.getUserid();
		 
        Map paramMap = requestBean.getRequestMap();
        paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "100" : paramMap.get("__PAGE_SIZE"));


        
        Map<String, ?> dataMap = docrm610M_Manager.processQuery(paramMap);
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
		Map<String, Object> dataMap = docrm610M_Manager.processPrint(paramMap);
		// 判斷印出的報表種類
		String Organ = MapUtil.getString(paramMap, "Organ", "");
		String TAX_TP = MapUtil.getString(paramMap, "TAX_TP", "");
		String DOC_ORG_ID = MapUtil.getString(paramMap, "DOC_ORG_ID", "");// 機關別

		String fileName = "DOCRM610R_01R";
		if ("Single".equals(Organ) && "".equals(TAX_TP)) {
			fileName = "DOCRM610R_02R";
		} else if ("Single".equals(Organ) && !"".equals(TAX_TP)) {
			fileName = "DOCRM610R_03R";
		}

		// 自定變數
		String ORG_NM = "高雄市稅捐稽徵處";// 機關名稱
		String MONTH = MapUtil.getString(paramMap, "APPLY_DATE_Mo", "");
		String YEAR = MapUtil.getString(paramMap, "APPLY_DATE_YEAR", "");
		String DATE = YEAR + "年" + MONTH + "月";// 結案年月
		String SYS = DateUtil.getRocFullDate();
		String A = SYS.substring(0, 3).toString();
		String B = SYS.substring(4, 6).toString();
		String C = SYS.substring(7, 9).toString();
		String SYS_DATE = A + "/" + B + "/" + C; //製表日期
		
		Map<String, String> jasper_map = new HashMap<String, String>();
		List<Map<String, Object>> jasper_list = (List<Map<String, Object>>) dataMap.get("datalist");
		
		//		
		if (!"".equals(TAX_TP)) {
			jasper_map.put("TAX_TP", dataMap.get("TAX_TP").toString());// 稅目別
		} else {
			jasper_map.put("TAX_TP", "");
		}
		if (!"".equals(DOC_ORG_ID)) {
			jasper_map.put("DOC_ORG_ID", dataMap.get("ORG_ID").toString());// 單位別
		} else {
			jasper_map.put("DOC_ORG_ID", "");
		}
		// 使自定變數印出
		jasper_map.put("ORG_NM", ORG_NM);// 機關名稱
		jasper_map.put("DATE", DATE);// 結案年月
		jasper_map.put("SYS_DATE", SYS_DATE);// 製表日期
				
		if (jasper_list.size() == 0) {
			responseBean.setStatus(ResponseBean.Status.WARN);
			responseBean.setReturnType(ReturnType.WEB_REPORT);
			responseBean.setMessageText("無符合列印資料!!");
		} else {
			ProcessReport report = new ProcessReport();
			report.doWebReport(response, report.XLS, fileName, jasper_map, jasper_list);
		}
	}
	

	@Override
	public String getProgramCd() {
		// TODO Auto-generated method stub
		return "DOCRM610M_";
	}
}
