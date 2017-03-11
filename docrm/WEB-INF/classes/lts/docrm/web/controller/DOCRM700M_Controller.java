/*
@@程式代號 = DOCRM700M_Controller.java
@@程式名稱 = 系統別Controller
@@程式版本 = V1.000
@@更新日期 = 2016/10/20
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.library.exception.LTSApplicationException;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.web.manager.DOCRM700M_Manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;
import com.acer.util2.MapUtil;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM700M_Controller.do")
public class DOCRM700M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager docrmUtilManager;
	
    @Autowired
    private DOCRM700M_Manager docrm700M_Manager;

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
    public void processMain(RequestBean requestBean, ResponseBean responseBean,HttpServletResponse response) 
    	throws Exception { Map paramMap = requestBean.getRequestMap();

        String STUS = HtmlTagFactory.renderOptionHtml("STUS", docrmUtilManager.processDDL("STUS")
    			,"@{PHRASE_TITLE}","@{PHRASE_KEY}");
        
        Map<String, Object> rtn = new HashMap<String, Object>();
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
     *            Exception
     */
    public void processQuery(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        // 取得登入使用者資訊
		UserProfile userProfile = this.getUserProfile();
		String id = userProfile.getUserid();

        Map<String, Object> paramMap = requestBean.getRequestMap();
        paramMap.put("__PAGE_SIZE", paramMap.get("__PAGE_SIZE") == null ? "10" : paramMap.get("__PAGE_SIZE"));
        Map<String, ?> dataMap = docrm700M_Manager.processQuery(paramMap);
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
        Map<String, Object> paramMap = requestBean.getRequestMap();
        String QUESTION_ID = MapUtil.getString(paramMap, "QUESTION_ID","");
        String STUS = MapUtil.getString(paramMap, "STUS","");
        
        Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("QUESTION_ID", QUESTION_ID);
		Map resultMap = (Map) docrmUtilManager.processSelectTableByAny("DOCRMT700", queryMap, false).getFirstByMap();

		if (resultMap != null) {
			throw new LTSApplicationException("已有相同題目代碼");
		}
		//檢核
		if ("Y".equals(STUS)) {
			Map<String, Object> queryMap2 = new HashMap<String, Object>();
			queryMap2.put("STUS", STUS);
			queryMap2.put("QUESTION_ID", QUESTION_ID);
			Map<String, ?> dataMap1 = docrm700M_Manager.processNuclear(paramMap);
			List<Map<String, Object>> list = (List<Map<String, Object>>) dataMap1.get("datalist");
			String B = Integer.toString(list.size());
			
			if ("3".equals(B)) {
				throw new LTSApplicationException("已達啟用上限");
			}
        }
		
        Map<String, ?> dataMap = docrm700M_Manager.processAddSave(paramMap);
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
        Map<String, Object> paramMap = requestBean.getRequestMap();

        String STUS =
    			HtmlTagFactory.renderOptionHtml("STUS", docrmUtilManager.processDDL("STUS"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
        Map<String, Object> rtn = new HashMap<String, Object>();
        Map<String, Object> dataMap = docrm700M_Manager.processEdit(paramMap);
        
        rtn.put("editData",  dataMap.get("editData"));
        rtn.put("ANS", dataMap.get("ANS"));
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
        Map<String, Object> paramMap = requestBean.getRequestMap();
        String STUS = MapUtil.getString(paramMap, "STUS");
        String QUESTION_ID = MapUtil.getString(paramMap, "QUESTION_ID2","");
        paramMap.put("QUESTION_ID", QUESTION_ID);
        //檢核
        if ("Y".equals(STUS)) {
			Map<String, Object> queryMap2 = new HashMap<String, Object>();
			queryMap2.put("STUS", STUS);
			queryMap2.put("QUESTION_ID", QUESTION_ID);
			Map<String, ?> dataMap1 = docrm700M_Manager.processNuclear(queryMap2);
			List<Map<String, Object>> list = (List<Map<String, Object>>) dataMap1.get("datalist");
			String B = Integer.toString(list.size());
			
			if ("3".equals(B)) {
				throw new LTSApplicationException("已達啟用上限");
			}
        }
        Map<String, ?> dataMap = docrm700M_Manager.processEditSave(paramMap);
        responseBean.setData(dataMap);
    }
    
    @Override
	public String getProgramCd() {
		return "DOCRM700M_";
	}
	
}
