/*
@@程�?�代??? = DOCRM830M_Controller.java
@@程�?��?�稱 = 系統?��Controller
@@程�?��?�本 = V1.000
@@?��?��?��??? = 2015/12/01
@@檢查�?  = ?��容由YPM?��??�產???
 */
package lts.docrm.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.ApiParams;
import lts.docrm.core.util.WsUtil;
import lts.docrm.web.manager.DOCRM300M_Manager;
import lts.docrm.web.manager.DOCRM830M_Manager;
import lts.docrm.web.manager.impl.DOCRM300M_ManagerImpl;
import lts.global.ws.consumer.CallWebService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM300M_Controller.do")
public class DOCRM300M_Controller extends DOCRMController {

    /**
     * processMain
     * 
     * @param requestBean
     *            來�?�Bean
     * @param responseBean
     *            ??�傳Bean
     * @param response
     *            HttpServletResponse
     * @throws Exception
     *             Exception
     */
    public void processGetMESS_NO(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        Map paramMap = requestBean.getRequestMap();
        
        Map<String, Object> rtn = new HashMap<String, Object>();

        DOCRM300M_ManagerImpl docrm300 = new DOCRM300M_ManagerImpl();
        Map<String, ?> dataMap = docrm300.processGetMESS_NO(paramMap);
                
        responseBean.setData(dataMap);
    }
    
    /**
     * processImport
     * 
     * @param requestBean
     *            來�?�Bean
     * @param responseBean
     *            ??�傳Bean
     * @param response
     *            HttpServletResponse
     * @throws Exception
     *             Exception
     */
    public void processImportDOCRM(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
    	//Web Service 測試用
    	
        Map paramMap = requestBean.getRequestMap();
        
        // 準備Web Service物件
     	CallWebService callWebService = CallWebService.createCallWebService("http://localhost:8080/docrm/ltsws/LTSService", "ds_SSR_E77A", WsUtil.getLogData());

     	// 準備上傳資料
     	Map<String, String> requestMap = new HashMap<String, String>();

     	Map<String, Object> map = new HashMap<String, Object>();
     	
     	map.put("MESS_NO", "E771051101001");
     	map.put("APPLY_NM", "張三");
     	map.put("APPLY_SUBJECT", "TEST");
     	map.put("MOBILE_NO", "0912345678");
     	map.put("EMAIL", "abc@gmail.com");
     	map.put("DOCRM_TP", "006");
     	
     	File uploadFile = new File("/DOCRM/test.pdf");
     	byte[] bytes = FileUtils.readFileToByteArray(uploadFile);
		byte[] encoded = Base64.encodeBase64(bytes);
		String base64File = new String(encoded, "UTF-8");
		
     	map.put("CASEFILE", base64File);
     	     	
		 try {		
			 requestMap.put("map", ApiParams.mapToXML(map));
		 } catch (Exception e) {
		
		 }

     	// Call Web Service並取得response
     	Map responseMap = null;
     	try {
     		responseMap = (Map) callWebService.invokeWebService("DOCRM", "E77", "lts.docrm.ws.provider.DOCRMSPWSProvider", (Object) requestMap);
     	} catch (Exception e) {
     		responseMap = null;
     	}
     	
     	if (responseMap != null) {
     		if ("0".equals(responseMap.get("code"))) {
     		} else {
     		}
     	} else {
     	}
                
        responseBean.setData(null);
    }
    
	public String getProgramCd() {
		// TODO Auto-generated method stub
		return "DOCRM300M_";
	}
}
