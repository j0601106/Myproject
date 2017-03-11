package lts.global.core.util;

import gov.fdc.framework.core.dao.DBResultList;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.support.FDCTransactionManager;
import java.util.HashMap;
import java.util.Map;
import lts.global.core.dao.LTSDao;
import lts.global.core.dao.LTSDaoFactory;
import org.apache.commons.lang.StringUtils;









public class ProgramUtil
{
  public static final String[] CONFD_LVL = { "普通", "一般", "機密", "極機密" };
  

  public static final String PROG_TYPE_ONLINE = "0";
  

  public static final String PROG_TYPE_BATCH = "1";
  

  private static final String DEFAULT_PROGRAM_NM = "無";
  

  private static final String DEFAULT_CLASSIFIED = "無";
  

  public static Map getProgInfo(String dsType, String program_cd, String screen_cd)
  {
    Map program = new HashMap();
    try
    {
      if ((StringUtils.isNotEmpty(dsType)) && (StringUtils.isNotEmpty(program_cd))) {

        LTSDao dao = LTSDaoFactory.getDaoFactory().createDao(dsType);
        
        FDCTransactionManager tx = null;
        Map dataMap = new HashMap();
        try {
          tx = dao.beginTransaction();
          
          String sql = "SELECT prg_nm, prg_url, prg_dscpt, confd_lvl FROM ybdprg WHERE prg_cd = ? AND prg_type = ?";
          dataMap = dao.queryForDBResultList(Query.createQuery().setSql(sql).setParamObjs(new String[] { program_cd, "0" })).getFirstByMap();
          




          tx.commit();
        } catch (Exception e) {
          if (tx != null) {
            tx.rollback();
          }
        }
        
        if (dataMap == null) {
          program.put("PROGRAM_NM", "無");
          program.put("CLASSIFIED", "無");
          return program;
        }
        if ((dataMap.get("PRG_NM") != null) && (!"".equals(dataMap.get("PRG_NM"))))
        {
          program.put("PROGRAM_NM", dataMap.get("PRG_NM"));
        } else {
          program.put("PROGRAM_NM", "無");
        }
        
        if ((dataMap.get("CONFD_LVL") != null) && (!"".equals(dataMap.get("CONFD_LVL")))) {
          try
          {
            program.put("CLASSIFIED", CONFD_LVL[Integer.parseInt(dataMap.get("CONFD_LVL").toString())]);
          }
          catch (Exception ignore) {
            program.put("CLASSIFIED", dataMap.get("CONFD_LVL"));
          }
        } else {
          program.put("CLASSIFIED", "無");
        }
        

        return program;
      }
      program.put("PROGRAM_NM", "無");
      program.put("CLASSIFIED", "無");
      return program;
    }
    catch (Exception e) {
      e.printStackTrace();
      
      program.put("PROGRAM_NM", "無");
      program.put("CLASSIFIED", "無"); }
    return program;
  }
}
