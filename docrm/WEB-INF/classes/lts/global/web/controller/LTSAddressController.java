package lts.global.web.controller;

import com.acer.html.HtmlEnum;
import com.acer.html.HtmlEnum.Location;
import com.acer.html.HtmlTagFactory;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lts.global.web.manager.LTSAddressManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@Scope("prototype")
@RequestMapping({"/global/LTSAddressController.do"})
public class LTSAddressController
  extends LTSController
{
  @Autowired
  private LTSAddressManager ltsAddressManager;
  
  public String getProgramCd()
  {
    return "LTSAddress";
  }
  







  public void processWSST820(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    
    String prefix = (String)paramMap.get("_PREFIX_");
    if (prefix == null) {
      prefix = "";
    }
    Map parameterMap = new HashMap();
    parameterMap.put("_PREFIX_", prefix);
    String ADDR_TOWN = HtmlTagFactory.renderOptionHtml(prefix + "ADDR_HSN", this.ltsAddressManager.processWSST820(parameterMap), "@{HSN_NM}", "@{HSN_NM}");
    

    responseBean.setData(ADDR_TOWN);
  }
  







  public void processWSST840(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    
    String prefix = (String)paramMap.get("_PREFIX_");
    if (prefix == null) {
      prefix = "";
    }
    Map parameterMap = new HashMap();
    parameterMap.put("HSN_NM", paramMap.get(prefix + "ADDR_HSN"));
    parameterMap.put("_PREFIX_", prefix);
    
    List<Map<String, Object>> dataList = null;
    if (parameterMap.get("HSN_NM").equals("")) {
      dataList = new ArrayList();
    } else {
      dataList = this.ltsAddressManager.processWSST840(parameterMap);
    }
    Map attributes = new HashMap();
    attributes.put("zip_cd", "@{ZIP_CD}");
    String ADDR_TOWN = HtmlTagFactory.renderOptionHtml(prefix + "ADDR_TOWN", dataList, "", "", "", HtmlEnum.Location.Top, "@{TOWN_NM}", "@{TOWN_NM}", true, attributes);
    

    responseBean.setData(ADDR_TOWN);
  }
  







  public void processHOUC008(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    
    String prefix = (String)paramMap.get("_PREFIX_");
    if (prefix == null) {
      prefix = "";
    }
    Map parameterMap = new HashMap();
    parameterMap.put("HSN_NM", paramMap.get(prefix + "ADDR_HSN"));
    parameterMap.put("TOWN_NM", paramMap.get(prefix + "ADDR_TOWN"));
    parameterMap.put("_PREFIX_", prefix);
    
    List<Map<String, Object>> dataList = null;
    if ((parameterMap.get("HSN_NM").equals("")) && (parameterMap.get("TOWN_NM").equals(""))) {
      dataList = new ArrayList();
    } else {
      dataList = this.ltsAddressManager.processHOUC008(parameterMap);
    }
    String ADDR_VILL = HtmlTagFactory.renderOptionHtml(prefix + "ADDR_VILL", dataList, "@{VILL_NM}", "@{VILL_NM}");
    

    responseBean.setData(ADDR_VILL);
  }
  








  public void processHOUC010(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    
    Map<String, ?> dataMap = this.ltsAddressManager.processHOUC010(paramMap);
    responseBean.setData(dataMap);
  }
}