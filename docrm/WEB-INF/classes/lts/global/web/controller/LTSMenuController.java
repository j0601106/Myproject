package lts.global.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.controller.impl.ResponseBean.ReturnType;
import gov.fdc.library.env.ApEnv;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lts.global.web.manager.LTSMenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;










@Controller
@Scope("prototype")
@RequestMapping({"/global/LTSMenuController.do"})
public class LTSMenuController
  extends LTSController
{
  @Autowired
  private LTSMenuManager ltsMenuManager;
  
  public String getProgramCd()
  {
    return "LTSMenu";
  }
  







  public void processQueryMenu(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    responseBean.setReturnType(ResponseBean.ReturnType.SERVER_FORWARD_PAGE);
    responseBean.setReturnPage("../home/menu.jsp");
        
    Map paramMap = requestBean.getRequestMap();
    
    String SYS_CD = ApEnv.get("sys.cd").toUpperCase();
    String USER_ID = (getUserProfile() == null) || (getUserProfile().getUserid() == null) ? "" : getUserProfile().getUserid();
    String BE_AGENT = (getUserProfile() == null) || (getUserProfile().getCV01() == null) ? "" : getUserProfile().getCV01();
    String GROUP_ID = (getUserProfile() == null) || (getUserProfile().getCV05() == null) ? "" : getUserProfile().getCV05();
    
    paramMap.put("SYS_CD", SYS_CD);
    paramMap.put("USER_ID", USER_ID);
    paramMap.put("BE_AGENT", BE_AGENT);
    paramMap.put("GROUP_ID", GROUP_ID);
    
    Map<String, Object> rtn = new HashMap();
    
    String _DEBUG = (String)paramMap.get("_DEBUG");
    
    rtn.put("tree", this.ltsMenuManager.processQueryMenu(paramMap));

    responseBean.setData(rtn);
  }
}
