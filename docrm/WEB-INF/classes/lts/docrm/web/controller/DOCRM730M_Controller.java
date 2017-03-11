/*
@@程式代號 = DOCRM730M_Controller.java
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
import gov.fdc.library.exception.LTSApplicationException;
import gov.fdc.library.jasper.ProcessReport;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.web.manager.DOCRM730M_Manager;

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
@RequestMapping("/docrm/DOCRM730M_Controller.do")
public class DOCRM730M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager docrmUtilManager;

	@Autowired
	private DOCRM730M_Manager docrm730M_Manager;

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
		paramMap.put("Rootorgid", this.getUserProfile().getRootorgid());
		paramMap.put("Date_Y", DateUtil.getSysWDate());
		String Date_Y = DateUtil.getSysWDate();
		String Date_MMM = Date_Y.substring(3, 5);
		String Datr_YYY = Date_Y.substring(0, 3);
		String Day = Date_Y.substring(5, 7);

		String TAX_TP = HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"), "",
				"@{PHRASE_TITLE}", "@{PHRASE_KEY}", true);
		String DOC_ORG_ID = "";

		DOC_ORG_ID = HtmlTagFactory.renderOptionHtml("ORG_ID", docrmUtilManager.processDDL("ORG_ID"), "",
				"@{PHRASE_TITLE}", "@{PHRASE_KEY}", true);

		String QUESTION_ID = HtmlTagFactory.renderOptionHtml("QUESTION_ID", docrm730M_Manager.processDDL("QUESTION_ID"),
				"", "@{QUESTION}", "@{QUESTION_ID}", false);

		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("APPLY_DATE_YEAR", Datr_YYY);
		rtn.put("APPLY_DATE_Mo", Date_MMM);
		rtn.put("DOC_ORG_ID", DOC_ORG_ID);
		rtn.put("QUESTION_ID", QUESTION_ID);
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
	public void processQuery(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		// 取得登入使用者資訊
		UserProfile userProfile = this.getUserProfile();
		String id = userProfile.getUserid();

		Map paramMap = requestBean.getRequestMap();
		String MONTH = MapUtil.getString(paramMap, "APPLY_DATE_Mo", "");
		String YEAR = MapUtil.getString(paramMap, "APPLY_DATE_YEAR", "");
		String DATE = YEAR + MONTH;// 結案年月
		paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "100" : paramMap.get("__PAGE_SIZE"));
		paramMap.put("DATE", DATE);

		Map<String, ?> dataMap = docrm730M_Manager.processQuery(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * QUS_RESULT 連動下拉式選單
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
	public void QUS_RESULT(RequestBean requestBean,ResponseBean responseBean, HttpServletResponse response)
		throws Exception {

		Map paramMap = requestBean.getRequestMap();
		Map<String, Object> dataMap = (Map<String, Object>) docrm730M_Manager.QUS_RESULT(paramMap);
		responseBean.setData(dataMap);
		// 抓取700user輸入的欄位
		String RESULT_ID1 = MapUtil.getString(dataMap, "RESULT_1", "");// 答案一代號
		String RESULT_ID2 = MapUtil.getString(dataMap, "RESULT_2", "");// 答案二代號
		String RESULT_ID3 = MapUtil.getString(dataMap, "RESULT_3", "");// 答案三代號
		String RESULT_ID4 = MapUtil.getString(dataMap, "RESULT_4", "");// 答案四代號
		String RESULT_1 = MapUtil.getString(dataMap, "RESULT1", "");// 答案一中文
		String RESULT_2 = MapUtil.getString(dataMap, "RESULT2", "");// 答案二中文
		String RESULT_3 = MapUtil.getString(dataMap, "RESULT3", "");// 答案三中文
		String RESULT_4 = MapUtil.getString(dataMap, "RESULT4", "");// 答案四中文
		// 報表答案中文顯示
		String RESULT_QA1 = RESULT_1;
		String RESULT_QA2 = RESULT_2;
		String RESULT_QA3 = RESULT_3;
		String RESULT_QA4 = RESULT_4;
		String RESULT_QA5 = RESULT_1;
		String RESULT_QA6 = RESULT_2;
		String RESULT_QA7 = RESULT_3;
		String RESULT_QA8 = RESULT_4;

		Map rtn = new HashMap();
		rtn.put("RESULT_QA1", RESULT_QA1);
		rtn.put("RESULT_QA2", RESULT_QA2);
		rtn.put("RESULT_QA3", RESULT_QA3);
		rtn.put("RESULT_QA4", RESULT_QA4);
		rtn.put("RESULT_QA5", RESULT_QA5);
		rtn.put("RESULT_QA6", RESULT_QA6);
		rtn.put("RESULT_QA7", RESULT_QA7);
		rtn.put("RESULT_QA8", RESULT_QA8);
		responseBean.setData(rtn);
	}
	
	

	/**
	 * processPrint
	 * 
	 * @param requestBean
	 *            the request bean
	 * @param responseBean
	 *            the response bean
	 * @param response
	 *            the response
	 * @throws Exception
	 *             the exception
	 */
	public void processPrint(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map<String, Object> paramMap = requestBean.getRequestMap();
		Map<String, Object> dataMap = docrm730M_Manager.processPrint(paramMap);
		String fileName = "DOCRM730R_01R";//報表檔案名稱
		
		// 自定變數
		String ORG_NM = "高雄市稅捐稽徵處";// 機關名稱
		String MONTH = MapUtil.getString(paramMap, "APPLY_DATE_Mo", "");
		String YEAR = MapUtil.getString(paramMap, "APPLY_DATE_YEAR", "");
		String DATE = YEAR + "年" + MONTH + "月";// 結案年月
		String DOC_ORG_ID = MapUtil.getString(paramMap, "DOC_ORG_ID", "");// 機關別
		String TAX_TP = MapUtil.getString(paramMap, "TAX_TP", "");// 稅目別
		String QUESTION_ID = MapUtil.getString(paramMap, "QUESTION_ID", "");
		String SYS = DateUtil.getRocFullDate();
		String A = SYS.substring(0, 3).toString();
		String B = SYS.substring(4, 6).toString();
		String C = SYS.substring(7, 9).toString();
		String SYS_DATE = A + "/" + B + "/" + C; // 製表日期
		String Y_DATE = YEAR + MONTH;
		
		Map<String, String> jasper_map = new HashMap<String, String>();
		List<Map<String, Object>> jasper_list = (List<Map<String, Object>>) dataMap.get("datalist");
		
		// 抓取700user輸入的欄位
		String RESULT_ID1 = MapUtil.getString(dataMap, "RESULT_1", "");//答案一代號
		String RESULT_ID2 = MapUtil.getString(dataMap, "RESULT_2", "");//答案二代號
		String RESULT_ID3 = MapUtil.getString(dataMap, "RESULT_3", "");//答案三代號
		String RESULT_ID4 = MapUtil.getString(dataMap, "RESULT_4", "");//答案四代號
		String RESULT_1 = MapUtil.getString(dataMap, "RESULT1", "");// 答案一中文
		String RESULT_2 = MapUtil.getString(dataMap, "RESULT2", "");// 答案二中文
		String RESULT_3 = MapUtil.getString(dataMap, "RESULT3", "");// 答案三中文
		String RESULT_4 = MapUtil.getString(dataMap, "RESULT4", "");// 答案四中文
		// 報表答案中文顯示
		String RESULT_A = RESULT_1 + "(當月)";
		String RESULT_B = RESULT_2 + "(當月)";
		String RESULT_C = RESULT_3 + "(當月)";
		String RESULT_D = RESULT_4 + "(當月)";
		String RESULT_5 = RESULT_1 + "(跨月)";
		String RESULT_6 = RESULT_2 + "(跨月)";
		String RESULT_7 = RESULT_3 + "(跨月)";
		String RESULT_8 = RESULT_4 + "(跨月)";

		// 判斷報表上的答案欄位要顯示什麼，已及印出哪張報表
		if (!"".equals(RESULT_ID4)) {
			fileName = "DOCRM730R_01R";
			jasper_map.put("RESULT_ID1", RESULT_A);
			jasper_map.put("RESULT_ID2", RESULT_B);
			jasper_map.put("RESULT_ID3", RESULT_C);
			jasper_map.put("RESULT_ID4", RESULT_D);
			jasper_map.put("RESULT_ID5", RESULT_5);
			jasper_map.put("RESULT_ID6", RESULT_6);
			jasper_map.put("RESULT_ID7", RESULT_7);
			jasper_map.put("RESULT_ID8", RESULT_8);
		} else if (!"".equals(RESULT_ID3)) {
			fileName = "DOCRM730R_02R";
			jasper_map.put("RESULT_ID1", RESULT_A);
			jasper_map.put("RESULT_ID2", RESULT_B);
			jasper_map.put("RESULT_ID3", RESULT_C);
			jasper_map.put("RESULT_ID5", RESULT_5);
			jasper_map.put("RESULT_ID6", RESULT_6);
			jasper_map.put("RESULT_ID7", RESULT_7);
		} else if (!"".equals(RESULT_ID2)) {
			fileName = "DOCRM730R_03R";
			jasper_map.put("RESULT_ID1", RESULT_A);
			jasper_map.put("RESULT_ID2", RESULT_B);
			jasper_map.put("RESULT_ID5", RESULT_5);
			jasper_map.put("RESULT_ID6", RESULT_6);
		} else if (!"".equals(RESULT_ID1)) {
			fileName = "DOCRM730R_04R";
			jasper_map.put("RESULT_ID1", RESULT_A);
			jasper_map.put("RESULT_ID5", RESULT_5);
		} else {
			throw new LTSApplicationException("該筆資料有錯誤無法列印！");
		}
		
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
		if (!"".equals(QUESTION_ID)) {
			jasper_map.put("QUESTION_ID", dataMap.get("QUESTION").toString());// 題目
		} else {
			jasper_map.put("QUESTION_ID", "");
		}

		// 使自定變數印出
		jasper_map.put("ORG_NM", ORG_NM);// 機關名稱
		jasper_map.put("DATE", DATE);// 結案年月
		jasper_map.put("SYS_DATE", SYS_DATE);// 製表日期
		// 自訂變數 Parameter

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
		return "DOCRM730M_";
	}

}
