/*
@@程式代號 = DOCRM830M_Controller.java
@@程式名稱 = 系統別Controller
@@程式版本 = V1.000
@@更新日期 = 2016/11/23
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.web.manager.DOCRM830M_Manager;
import lts.global.core.util.PhraseUtil;

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
@RequestMapping("/docrm/DOCRM830M_Controller.do")
public class DOCRM830M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;
	
    @Autowired
    private DOCRM830M_Manager docrm830M_Manager;

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

        String ROLE_ID =
    			HtmlTagFactory.renderOptionHtml("ROLE_ID", docrmUtilManager.processDDL("ROLE_ID"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        String ORG_ID =
    			HtmlTagFactory.renderOptionHtml("ORG_ID", docrmUtilManager.processDDL("ORG_ID"), "", "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}",true);
        Map<String, Object> rtn = new HashMap<String, Object>();
        rtn.put("ORG_ID", ORG_ID);
        rtn.put("ROLE_ID", ROLE_ID);
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
        paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "50" : paramMap.get("__PAGE_SIZE"));

        Map<String, ?> dataMap = docrm830M_Manager.processQuery(paramMap);
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
        
        Map<String, ?> dataMap = docrm830M_Manager.processAddSave(paramMap);
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
        String ROLE_ID =
    			HtmlTagFactory.renderOptionHtml("ROLE_ID", docrmUtilManager.processDDL("ROLE_ID"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        String ORG_ID =
    			HtmlTagFactory.renderOptionHtml("ORG_ID", docrmUtilManager.processDDL("ORG_ID"), "", "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}",true);
        Map<String, Object> rtn = new HashMap<String, Object>();

        Map<String, Object> dataMap = docrm830M_Manager.processEdit(paramMap);

        rtn.put("ORG_ID", ORG_ID);
        rtn.put("editData",  dataMap.get("editData"));
        rtn.put("ROLE_ID", ROLE_ID);
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

        Map<String, ?> dataMap = docrm830M_Manager.processEditSave(paramMap);
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

        Map<String, ?> dataMap = docrm830M_Manager.processDelete(paramMap);

        responseBean.setData(dataMap);
    }
    @Override
	public String getProgramCd() {
		// TODO Auto-generated method stub
		return "DOCRM830M_";
	}
}
