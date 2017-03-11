package lts.global.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import java.util.HashMap;
import java.util.Map;
import lts.global.core.dao.LTSDao;
import lts.global.web.manager.LTSGeneralManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;










@Service
@Scope("prototype")
public class LTSGeneralManagerImpl
  extends LTSWebManagerImpl
  implements LTSGeneralManager
{
  public Map<String, Object> processQueryForMap(String sqlId, Map<String, Object> parameterMap)
  {
    Query query = Query.createQuery().setSqlId(sqlId).setParamMap(parameterMap).setPageSize(1).setPageNo(1);
    DBResultList queryResult = getDao().queryForDBResultList(query);
    Map<String, Object> rtn = new HashMap();
    
    rtn.put("map", queryResult.getFirstByMap());
    return rtn;
  }
  
  public String getSystemCd()
  {
    return "FRAMEWORK";
  }
}
