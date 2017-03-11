package lts.global.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.support.FDCTransactionManager;
import java.util.HashMap;
import java.util.Map;
import lts.global.core.dao.LTSDao;
import lts.global.web.manager.OpenWinManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;










@Service
@Scope("prototype")
public class OpenWinManagerImpl
  extends LTSWebManagerImpl
  implements OpenWinManager
{
  public Map<String, Object> processQuery(Map<String, Object> parameterMap)
  {
    Map criteriaMap = new HashMap();
    criteriaMap.putAll(parameterMap);
    parameterMap.put("_criteria", criteriaMap);
    String mysqlid = (String)parameterMap.get("sqlId");
    String dsType = (String)parameterMap.get("dsType");
    Integer pageSize = Integer.valueOf(10);
    if (parameterMap.get("__PAGE_SIZE") != null) {
      pageSize = Integer.valueOf((String)parameterMap.get("__PAGE_SIZE"));
    } else if (parameterMap.get("PAGE_SIZE") != null) {
      String ps = (String)parameterMap.get("PAGE_SIZE");
      if (ps.matches("10|25|50|100")) {
        pageSize = Integer.valueOf(ps);
      }
    }
    DBResultList queryResult = null;
    Query query = Query.createQuery().setSqlId(mysqlid).setParamMap(parameterMap).setPageSize(pageSize.intValue());
    
    if (dsType != null) {
      LTSDao dao = createDao(dsType);
      FDCTransactionManager tx = dao.beginTransaction();
      queryResult = dao.queryForDBResultList(query);
      tx.commit();
    } else {
      queryResult = getDao().queryForDBResultList(query);
    }
    
    Map<String, Object> rtn = new HashMap();
    rtn.put("PAGE_INFO", queryResult.getPageInfo());
    rtn.put("GRID_RESULT", queryResult.getDataList());
    return rtn;
  }
  
  public String getSystemCd()
  {
    return "FRAMEWORK";
  }
}
