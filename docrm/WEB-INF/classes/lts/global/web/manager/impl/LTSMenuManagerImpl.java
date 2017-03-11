package lts.global.web.manager.impl;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import java.util.HashMap;
import java.util.Map;
import lts.global.web.manager.LTSMenuManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope("prototype")
public class LTSMenuManagerImpl
  extends LTSWebManagerImpl
  implements LTSMenuManager
{
  public Map<String, Object> processQueryMenu(Map<String, Object> parameterMap)
  {
    Map<String, Object> rtn = new HashMap();
    
    Query query = Query.createQuery().setSqlId("lts.dao.LTSMenu.query_menu1").setParamMap(parameterMap).setPaging(false);
    DBResultList queryResult = getDao().queryForDBResultList(query);
    rtn.put("treedata_menu1", queryResult.getDataList());
    
    Query query2 = Query.createQuery().setSqlId("lts.dao.LTSMenu.query_menu2").setParamMap(parameterMap).setPaging(false);
    DBResultList queryResult2 = getDao().queryForDBResultList(query2);
    rtn.put("treedata_menu2", queryResult2.getDataList());
    
    Query query3 = Query.createQuery().setSqlId("lts.dao.LTSMenu.query_menu3").setParamMap(parameterMap).setPaging(false);
    DBResultList queryResult3 = getDao().queryForDBResultList(query3);
    rtn.put("treedata_menu3", queryResult3.getDataList());
    
    return rtn;
  }
  

  public Map<String, Object> processQueryMenuAgent(Map<String, Object> parameterMap)
  {
	Map<String, Object> rtn = new HashMap();
	  
    Query query = Query.createQuery().setSqlId("lts.dao.LTSMenu.query_menu1_Agent").setParamMap(parameterMap).setPaging(false);
    DBResultList queryResult = getDao().queryForDBResultList(query);

    rtn.put("treedata_menu1", queryResult.getDataList());
    Query query2 = Query.createQuery().setSqlId("lts.dao.LTSMenu.query_menu2_Agent").setParamMap(parameterMap).setPaging(false);
    DBResultList queryResult2 = getDao().queryForDBResultList(query2);

    rtn.put("treedata_menu2", queryResult2.getDataList());
    Query query3 = Query.createQuery().setSqlId("lts.dao.LTSMenu.query_menu3_Agent").setParamMap(parameterMap).setPaging(false);
    DBResultList queryResult3 = getDao().queryForDBResultList(query3);

    rtn.put("treedata_menu3", queryResult3.getDataList());
    
    return rtn;
  }
  

  public Map<String, Object> processQueryMenuDebug(Map<String, Object> parameterMap)
  {
	Map<String, Object> rtn = new HashMap();
	  
    Query query = Query.createQuery().setSql("select SYS_CD,SYS_NM from YBDSYS where SYS_CD=?").setParamObjs(new String[] { (String)parameterMap.get("SYS_CD") }).setPaging(false);
    DBResultList queryResult = getDao().queryForDBResultList(query);

    rtn.put("treedata_menu1", queryResult.getDataList());
    Query query2 = Query.createQuery().setSql("select distinct substring(PRG_CD,1,3) as SYS_CD,substring(PRG_CD,4,1) as LEVEL_CODE,substring(PRG_CD,1,4) as LEVEL_NM from YBDPRG where substring(PRG_CD,1,3)=? order by substring(PRG_CD,1,3)").setParamObjs(new String[] { (String)parameterMap.get("SYS_CD") }).setPaging(false);
    DBResultList queryResult2 = getDao().queryForDBResultList(query2);

    rtn.put("treedata_menu2", queryResult2.getDataList());
    Query query3 = Query.createQuery().setSql("select substring(PRG_CD,1,3) as SYS_CD,substring(PRG_CD,4,1) as LEVEL_CODE,substring(PRG_CD,4,3) LABEL_CD, substring(PRG_CD,1,8) PRG_CD,PRG_NM from YBDPRG where substring(PRG_CD,1,3)=? order by PRG_CD").setParamObjs(new String[] { (String)parameterMap.get("SYS_CD") }).setPaging(false);
    DBResultList queryResult3 = getDao().queryForDBResultList(query3);

    rtn.put("treedata_menu3", queryResult3.getDataList());
    
    return rtn;
  }
  

  public String getSystemCd()
  {
    return null;
  }
}
