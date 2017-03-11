/*
@@程�?�代??? = DOCRM310M_Controller.java
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
import lts.docrm.web.manager.DOCRM300M_Manager;
import lts.docrm.web.manager.DOCRM830M_Manager;
import lts.docrm.web.manager.impl.DOCRM300M_ManagerImpl;
import lts.docrm.web.manager.impl.DOCRM310M_ManagerImpl;

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
@RequestMapping("/docrm/DOCRM310M_Controller.do")
public class DOCRM310M_Controller extends DOCRMController {

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
    public void processGenTxtByDocrm(RequestBean requestBean, ResponseBean responseBean,
            HttpServletResponse response) throws Exception {
        Map paramMap = requestBean.getRequestMap();
        
        Map<String, Object> rtn = new HashMap<String, Object>();

        DOCRM310M_ManagerImpl docrm310 = new DOCRM310M_ManagerImpl();
        Map<String, ?> dataMap = docrm310.processGenTxtByDocrm(paramMap);
                
        responseBean.setData(dataMap);
    }
    
	public String getProgramCd() {
		// TODO Auto-generated method stub
		return "DOCRM310M_";
	}
}
