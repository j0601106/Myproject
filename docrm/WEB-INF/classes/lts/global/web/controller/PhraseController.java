package lts.global.web.controller;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.util.FdcThreadHolder;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.library.phrase.CodeBase;
import gov.fdc.library.phrase.CodeKey;
import gov.fdc.library.phrase.Phrase;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
@RequestMapping({"/global/PhraseController.do"})
public class PhraseController
  extends LTSController
{
  public String getProgramCd()
  {
    return "Phrase";
  }
  
  public void processQuery(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    String phraseTypeKey = (String)requestBean.getRequestMap().get("phraseTypeKey");
    String phraseKey = (String)requestBean.getRequestMap().get("phraseKey");
    
    CodeBase codeBase = Phrase.getInstance().getCodeBaseList(FdcThreadHolder.getUserProfile().getSchemaName(), phraseTypeKey);
    if (codeBase != null) {
      CodeKey codeKey = codeBase.getCodeKey(phraseKey);
      
      responseBean.setData(codeKey);
    } else {
      responseBean.setData(null);
    }
  }
}
