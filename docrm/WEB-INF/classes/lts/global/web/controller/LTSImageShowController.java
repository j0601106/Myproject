package lts.global.web.controller;

import com.acer.util2.MapUtil;
import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.controller.impl.ResponseBean.ReturnType;
import gov.fdc.library.env.ApEnv;
import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;





















@Controller
@Scope("prototype")
@RequestMapping({"/global/LTSImageShowController.do"})
public class LTSImageShowController
  extends LTSController
{
  public void processImageShow(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    responseBean.setReturnType(ResponseBean.ReturnType.DOWNLOAD);
    
    Map requestMap = requestBean.getRequestMap();
    
    String pathType = MapUtil.getString(requestMap, "pathType");
    String file = MapUtil.getString(requestMap, "file");
    String customFileNM = MapUtil.getString(requestMap, "fileNM");
    
    file = "/" + file;
    


    if (("temp".equals(pathType)) || ("perm".equals(pathType))) {
      File f = new File((ApEnv.get(new StringBuilder().append("shared.").append(pathType).append(".path").toString(), new String[] { getUserProfile().getRootorgid() }) + file).toLowerCase());
      

      if (customFileNM == null) {
        pushToClient(response, f, false);
      } else {
        String[] suffixes = file.split("\\.");
        String suffix = suffixes[(suffixes.length - 1)];
        
        pushToClient(response, f, customFileNM + "." + suffix, false);
      }
    }
  }
  
  public String getProgramCd()
  {
    return "FRAMEWORK";
  }
}
