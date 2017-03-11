package lts.global.web.controller;

import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lts.global.web.manager.OpenWinManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@Scope("prototype")
@RequestMapping({"/global/OpenWinController.do"})
public class OpenWinController
  extends LTSController
{
  @Autowired
  private OpenWinManager openWinManager;
  
  public String getProgramCd()
  {
    return "OpenWin";
  }
  


  public void processQuery(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map<String, ?> dataMap = this.openWinManager.processQuery(requestBean.getRequestMap());
    
    responseBean.setData(dataMap);
  }
}
