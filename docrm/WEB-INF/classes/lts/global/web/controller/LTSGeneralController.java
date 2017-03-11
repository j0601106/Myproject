package lts.global.web.controller;

import com.acer.util2.MapUtil;
import gov.fdc.framework.core.util.FdcThreadHolder;
import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.controller.impl.ResponseBean.Status;
import gov.fdc.library.env.ApEnv;
import gov.fdc.library.exception.LTSApplicationException;
import gov.fdc.library.globalcache.GlobalCacheHandler;
import gov.fdc.library.log.logobj.Logdata;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lts.global.web.manager.LTSGeneralManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;











@Controller
@Scope("prototype")
@RequestMapping({"/global/LTSGeneralController.do"})
public class LTSGeneralController
  extends LTSController
{
  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private LTSGeneralManager ltsGeneralManager;
  
  public String getProgramCd()
  {
    return "LTSGeneral";
  }
  





  public void processSetGlobalCache(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    String cacheKey = MapUtil.getString(paramMap, "cacheKey", "");
    String cacheValue = MapUtil.getString(paramMap, "cacheValue", "");
    
    GlobalCacheHandler.addAPCacheObj(FdcThreadHolder.getLogdata().getUuid(), ApEnv.get("sys.cd"), cacheKey, cacheValue);
    




    paramMap = null;
    cacheKey = null;
    cacheValue = null;
  }
  





  public void processGetGlobalCache(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    String cacheKey = MapUtil.getString(paramMap, "cacheKey", "");
    
    Object obj = GlobalCacheHandler.getAPCacheObj(FdcThreadHolder.getLogdata().getUuid(), ApEnv.get("sys.cd"), cacheKey);
    


    Map rtn = new HashMap();
    rtn.put("cacheKey", cacheKey);
    rtn.put("cacheValue", obj);
    responseBean.setData(rtn);
    obj = null;
    cacheKey = null;
    paramMap = null;
  }
  








  public void processQueryForMap(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    
    String sqlId = (String)paramMap.get("_SQLID");
    if ((sqlId == null) || (sqlId.equals(""))) {
      throw new LTSApplicationException("不允許的操作。");
    }
    
    boolean isAllow = false;
    String[] sqlIdAllow = ApEnv.getStringArray("sqlutil.sqlid.allow");
    for (String s : sqlIdAllow)
    {
      if (s.equals(sqlId))
      {
        isAllow = true;
        break;
      }
    }
    
    if (!isAllow) {
      responseBean.setStatus(ResponseBean.Status.ERROR);
      responseBean.setMessageText("不允許的操作(不合法的SQLID)。");
      return;
    }
    
    paramMap.remove("_SQLID");
    Map rtn = this.ltsGeneralManager.processQueryForMap(sqlId, paramMap);
    
    responseBean.setData(rtn.get("map"));
  }
  








  public void processManagerCall(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
    throws Exception
  {
    Map paramMap = requestBean.getRequestMap();
    
    String[] manager = null;
    if (paramMap.containsKey("_MANAGER"))
    {
      manager = ((String)paramMap.get("_MANAGER")).split("\\.");
    }
    
    if ((manager == null) || (manager.length != 2))
    {
      throw new LTSApplicationException("不存在的ManagerCall (manager=" + manager + ")");
    }
    
    Object object = this.applicationContext.getBean(manager[0]);
    Method m = object.getClass().getMethod(manager[1], new Class[] { Map.class });
    Object rtn = m.invoke(object, new Object[] { paramMap });
    responseBean.setData(rtn);
  }
}
