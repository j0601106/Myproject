/*
@@程式代號 = DOCRM810M_Controller.java
@@程式名稱 = 系統別Controller
@@程式版本 = V1.000
@@更新日期 = 2016/10/26
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.library.exception.LTSApplicationException;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.web.manager.DOCRM810M_Manager;

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
@RequestMapping("/docrm/DOCRM810M_Controller.do")
public class DOCRM810M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
    @Autowired
    private DOCRM810M_Manager docrm810M_Manager;

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

        String TAX_TP =
    			HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        String STUS =
    			HtmlTagFactory.renderOptionHtml("STUS", docrmUtilManager.processDDL("STUS"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
        Map<String, Object> rtn = new HashMap<String, Object>();

        rtn.put("TAX_TP", TAX_TP);
        rtn.put("STUS", STUS);        
        
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
        paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "10" : paramMap.get("__PAGE_SIZE"));
        Map<String, ?> dataMap = docrm810M_Manager.processQuery(paramMap);
        responseBean.setData(dataMap);
    }
    
    /**
     * processAddSave 新增存檔
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
    public void processAddSave(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        Map paramMap = requestBean.getRequestMap();
    	String MANDATE_NO = paramMap.get("MANDATE_NO").toString();
    	String TAX_TP = paramMap.get("TAX_TP").toString();
    	String STUS = paramMap.get("STUS").toString();
    	
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("MANDATE_NO", MANDATE_NO);
		Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT810", queryMap, false).getFirstByMap();

		if (resultMap != null) {
			throw new LTSApplicationException("已有相同編號");
		}
		if("Y".equals(STUS)){
			Map<String, Object> queryMap1 = new HashMap<String, Object>();
			queryMap1.put("TAX_TP", TAX_TP);
			queryMap1.put("STUS", STUS);
			Map resultMap1 = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT810", queryMap1, false).getFirstByMap();

			if (resultMap1 != null) {
				throw new LTSApplicationException("該稅目別已被啟用");
			}
		}
		Map<String, ?> dataMap = docrm810M_Manager.processAddSave(paramMap);
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
    public void processEdit(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        Map paramMap = requestBean.getRequestMap();

        String TAX_TP =
    			HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"),"", "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}",true);
        String STUS =
    			HtmlTagFactory.renderOptionHtml("STUS", docrmUtilManager.processDDL("STUS"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        Map<String, Object> rtn = new HashMap<String, Object>();
        Map<String, Object> dataMap = docrm810M_Manager.processEdit(paramMap);

        rtn.put("editData",  dataMap.get("editData"));
        rtn.put("TAX_TP", TAX_TP);
        rtn.put("STUS", STUS); 
        responseBean.setData(rtn);
    }

    /**
     * processEditSave
     * 
     * @param requestBean 來源Bean
     * @param responseBean 回傳Bean
     * @param response HttpServletResponse
     * @throws Exception Exception
     */
    public void processEditSave(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
        throws Exception {
        Map paramMap = requestBean.getRequestMap();
    	String MANDATE_NO = paramMap.get("MANDATE_NO").toString();
    	String MANDATE_NO2 = paramMap.get("MANDATE_NO2").toString();
    	String TAX_TP = paramMap.get("TAX_TP").toString();
    	String TAX_TP2 = paramMap.get("TAX_TP2").toString();
    	String STUS = paramMap.get("STUS").toString();
    	
		if (!MANDATE_NO.equals(MANDATE_NO2)) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("MANDATE_NO", MANDATE_NO);
			Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT810", queryMap, false).getFirstByMap();

			if (resultMap != null) {
				throw new LTSApplicationException("已有相同編號");
			}
		}
		if (!TAX_TP.equals(TAX_TP2)) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("TAX_TP", TAX_TP);
			queryMap.put("STUS", STUS);
			Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT810", queryMap, false).getFirstByMap();

			if (resultMap != null) {
				throw new LTSApplicationException("該稅目別已被啟用");
			}
		}
        Map<String, ?> dataMap = docrm810M_Manager.processEditSave(paramMap);
        responseBean.setData(dataMap);
    }

    /**
     * processDelete
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
    public void processDelete(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
    	
    	requestBean.reSetField(RequestBean.Type.Array,
				new String[] { "gridDeleteCheck" });
    	
		Map paramMap = requestBean.getRequestMap();

		if (paramMap.get("gridDeleteCheck") != null) {
			String[] cpks = (String[]) paramMap.get("gridDeleteCheck");

			paramMap.put("PKNO", cpks);
		}

        Map<String, ?> dataMap = docrm810M_Manager.processDelete(paramMap);

        responseBean.setData(dataMap);
    }
    
	public String getProgramCd() {
		// TODO Auto-generated method stub
		return "DOCRM810M_";
	}
}
