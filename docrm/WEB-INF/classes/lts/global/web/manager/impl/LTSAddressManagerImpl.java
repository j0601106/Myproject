package lts.global.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lts.global.core.dao.LTSDao;
import lts.global.web.manager.LTSAddressManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;






@Service
@Scope("prototype")
public class LTSAddressManagerImpl
  extends LTSWebManagerImpl
  implements LTSAddressManager
{
  public List<Map<String, Object>> processWSST820(Map<String, Object> parameterMap)
  {
    String sql = "select HSN_CD,HSN_NM from WSST820 order by HSN_CD";
    
    Query query = Query.createQuery().setSql(sql).setPaging(false);
    DBResultList queryResult = getDao().queryForDBResultList(query);
    
    return (List<Map<String, Object>>) queryResult.getDataList();
  }
  
  public List<Map<String, Object>> processWSST840(Map<String, Object> parameterMap)
  {
    String sql = "select R1.TOWN_CD,R1.TOWN_NM,R1.ZIP_CD from WSST820 M left join WSST840 R1 on M.HSN_CD=R1.HSN_CD where M.HSN_NM=? order by R1.TOWN_NM";
    String[] paramObjs = { (String)parameterMap.get("HSN_NM") };
    
    Query query = Query.createQuery().setSql(sql).setParamObjs(paramObjs).setPaging(false);
    DBResultList queryResult = getDao().queryForDBResultList(query);
    
    return (List<Map<String, Object>>) queryResult.getDataList();
  }
  
  public List<Map<String, Object>> processHOUC008(Map<String, Object> parameterMap)
  {
    String sql = "select M.VILL_CD, M.VILL_NM from HOUC008 M left join WSST820 R1 on M.HSN_CD=R1.HSN_CD left join WSST840 R2 on M.TOWN_CD=R2.TOWN_CD where R1.HSN_NM=? and R2.TOWN_NM=? order by M.VILL_NM";
    String[] paramObjs = { (String)parameterMap.get("HSN_NM"), (String)parameterMap.get("TOWN_NM") };
    
    Query query = Query.createQuery().setSql(sql).setParamObjs(paramObjs).setPaging(false);
    DBResultList queryResult = getDao().queryForDBResultList(query);
    
    return (List<Map<String, Object>>) queryResult.getDataList();
  }
  

  public Map<String, Object> processHOUC010(Map<String, Object> parameterMap)
  {
    String sql = "select distinct ROAD_NM from HOUC010 M left join WSST820 R1 on M.HSN_CD=R1.HSN_CD left join WSST840 R2 on M.TOWN_CD=R2.TOWN_CD left join HOUC008 R3 on M.VILL_CD=R3.VILL_CD where R1.HSN_NM=? and R2.TOWN_NM=? order by ROAD_NM";
    
    String[] paramObjs = { (String)parameterMap.get("HSN_NM"), (String)parameterMap.get("TOWN_NM") };
    



















    Query query = Query.createQuery().setSql(sql).setParamObjs(paramObjs).setPaging(false);
    DBResultList result = getDao().queryForDBResultList(query);
    
    Map<String, Object> rtn = new HashMap();
    rtn.put("PAGE_INFO", result.getPageInfo());
    rtn.put("GRID_RESULT", result.getDataList());
    return rtn;
  }
  
  public String getSystemCd()
  {
    return "FRAMEWORK";
  }
}
