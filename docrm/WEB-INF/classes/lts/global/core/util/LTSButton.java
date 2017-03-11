package lts.global.core.util;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.support.DataSourceContextHolder;
import gov.fdc.framework.core.util.FdcThreadHolder;
import gov.fdc.library.env.ApEnv;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lts.global.core.dao.LTSDao;
import lts.global.core.dao.LTSDaoFactory;

public class LTSButton
{
  private String programCd = null;
  

  private Map<String, Map> programBtnMap = null;
  




  private LTSButton() {}
  



  public LTSButton(String programCd)
  {
    try
    {
      String enabled = ApEnv.get("ybd.button.perm.enabled");
      if (!"true".equals(enabled)) {
        return;
      }
      this.programCd = programCd;
      
      String USER_ID = FdcThreadHolder.getUserProfile().getUserid();
      
      String ds = DataSourceContextHolder.getTargetDataSource();
      LTSDao dao = LTSDaoFactory.getDaoFactory().createDao(ds);
      
      Query q = Query.createQuery().setSql("select * from YBDPRGBTN where PRG_CD=?").setParamObjs(new String[] { programCd }).setPaging(false);
      

      List<Map> list = (List<Map>) dao.queryForDBResultList(q).getDataList();
      
      if (list.size() == 0) {
        return;
      }
      this.programBtnMap = new HashMap();
      for (Map map : list) {
        this.programBtnMap.put((String)map.get("BTN_NM"), map);
      }
    }
    catch (Exception e)
    {
      this.programBtnMap = null;
      e.printStackTrace();
    }
  }
  




















  public String create(String buttonId, String buttonValue, String onClickEvent, String optionalHtmlProperties)
  {
    if (this.programBtnMap == null) {
      return "";
    }
    
    if (isPermitted(buttonId)) {
      if (buttonValue == null) {
        if (this.programBtnMap != null) {
          Map map = (Map)this.programBtnMap.get(buttonId);
          if (map != null) {
            buttonValue = (String)map.get("BTN_TOOLTIP");
          } else
            buttonValue = "";
        } else {
          buttonValue = "";
        }
      }
      if (onClickEvent != null) {
        onClickEvent = "onclick=\"" + onClickEvent + "\" ";
      }
      
      return "<input type='button' class='btn btnYBD' name=\"" + buttonId + "\" value=\"" + buttonValue + "\" " + onClickEvent + optionalHtmlProperties + ">";
    }
    
    return "";
  }
  






  public boolean isPermitted(String buttonId)
  {
    if (this.programBtnMap == null) {
      return false;
    }
    
    if (this.programBtnMap.containsKey(buttonId)) {
      return true;
    }
    return false;
  }
}
