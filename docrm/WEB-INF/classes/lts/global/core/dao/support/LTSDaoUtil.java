package lts.global.core.dao.support;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.util.FdcThreadHolder;
import gov.fdc.library.exception.LTSDefineException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lts.global.core.dao.LTSDao;



















public class LTSDaoUtil
{
  public static int insertWithTrans(String progcd, LTSDao dao, String mainTable, String destTable, Map paramMap)
  {
    String sysCd = mainTable.substring(0, 3).toLowerCase();
    String sqlId = sysCd + ".domain." + mainTable + ".selectByAny";
    Map criteriaMap = new HashMap();
    criteriaMap.putAll(paramMap);
    paramMap.put("_criteria", criteriaMap);
    Query query = Query.createQuery().setSqlId(sqlId).setParamMap(paramMap);
    List dataList = dao.queryForDBResultList(query).getDataList();
    
    int rtnCount = 0;
    for (int i = 0; i < dataList.size(); i++) {
      String tranSqlId = sysCd + ".domain." + destTable + ".insert";
      Query transQuery = Query.createQuery().setSqlId(tranSqlId).setParamMap((Map)dataList.get(i));
      int[] rtn = dao.insert(transQuery);
      
      if (rtn[0] <= 0) {
        throw new LTSDefineException(progcd, "LTSD021", null, new String[] { sqlId });
      }
      rtnCount += rtn[0];
    }
    return rtnCount;
  }
  










  public static int insertWithTrans(String progcd, LTSDao dao, String sqlId, Map paramMap)
  {
    Query transQuery = Query.createQuery().setSqlId(sqlId);
    transQuery.setParamMap(paramMap);
    int[] rtn = dao.insert(transQuery);
    
    if (rtn[0] <= 0) {
      throw new LTSDefineException(FdcThreadHolder.getProgramCd(), "LTSD021", null, new String[] { sqlId });
    }
    
    return rtn[0];
  }
}
