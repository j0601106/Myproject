package lts.global.core.dao.impl;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;
import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.impl.AbstractDao;
import gov.fdc.framework.core.util.FdcThreadHolder;
import gov.fdc.library.exception.LTSDataAccessException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lts.global.core.dao.LTSDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceUtils;







@Scope("prototype")
public class LTSDaoImpl
  extends AbstractDao
  implements LTSDao
{
  public int[] insert(Query q)
    throws LTSDataAccessException
  {
    String timestamp = DateUtil.getWTimeStamp();
    String date = timestamp.substring(0, 7);
    String time = timestamp.substring(7, 15);
    






    if (q.getParamMap() != null) {
      q.getParamMap().put("UPDATE_DATE", date);
      q.getParamMap().put("UPDATE_TIME", time);
      if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
        q.getParamMap().put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
      }
    }
    
    if ((q.getBatchList() != null) && (q.getBatchList().size() > 0)) {
      for (Object obj : q.getBatchList()) {
        if ((obj instanceof Map)) {
          Map _dataMap = (Map)obj;
          _dataMap.put("UPDATE_DATE", date);
          _dataMap.put("UPDATE_TIME", time);
          if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
            _dataMap.put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
          }
        }
      }
    }
    
    return _insert(q);
  }
  
  public int[] update(Query q)
    throws LTSDataAccessException
  {
    boolean checkCount = false;
    
    String org_date = null;
    String org_time = null;
    String org_update_user_id = null;
    
    String timestamp = DateUtil.getWTimeStamp();
    String date = timestamp.substring(0, 7);
    String time = timestamp.substring(7, 15);
    






    if (q.getParamMap() != null)
    {
      org_date = MapUtil.getString(q.getParamMap(), "UPDATE_DATE", null);
      org_time = MapUtil.getString(q.getParamMap(), "UPDATE_TIME", null);
      org_update_user_id = MapUtil.getString(q.getParamMap(), "UPDATE_USER_ID", null);
      

      boolean timpstampEnableCheckFlag = false;
      
      boolean timpstampIgnoreCheckFlag = false;
      

      if ("Y".equalsIgnoreCase(MapUtil.getString(q.getParamMap(), "ENABLE_TIMESTAMP_CHECK", "--"))) {
        timpstampEnableCheckFlag = true;
      }
      

      if ("Y".equalsIgnoreCase(MapUtil.getString(q.getParamMap(), "IGNORE_TIMESTAMP_CHECK", "--"))) {
        timpstampIgnoreCheckFlag = true;
      }
      

      if ((timpstampEnableCheckFlag) && (!timpstampIgnoreCheckFlag))
      {
        checkCount = true;
        
        if (q.getParamMap().get("UPDATE_DATE") != null)
        {
          Map _frameworkMap = new HashMap();
          

          _frameworkMap.put("UPDATE_DATE", q.getParamMap().get("UPDATE_DATE"));
          _frameworkMap.put("UPDATE_TIME", q.getParamMap().get("UPDATE_TIME"));
          

          q.getParamMap().put("_framework", _frameworkMap);
        }
        else
        {
          Map _frameworkMap = new HashMap();
          

          _frameworkMap.put("UPDATE_DATE", "9999999");
          _frameworkMap.put("UPDATE_TIME", "99999999");
          

          q.getParamMap().put("_framework", _frameworkMap);
        }
        

        q.getParamMap().remove("UPDATE_DATE");
        q.getParamMap().remove("UPDATE_TIME");
        
        q.getParamMap().put("UPDATE_DATE", date);
        q.getParamMap().put("UPDATE_TIME", time);
        
        if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
          q.getParamMap().put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
        }
        
      }
      else if ((timpstampEnableCheckFlag) && (timpstampIgnoreCheckFlag)) {
        q.getParamMap().put("UPDATE_DATE", date);
        q.getParamMap().put("UPDATE_TIME", time);
        
        if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
          q.getParamMap().put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
        }
      }
      else
      {
        if (MapUtil.getString(q.getParamMap(), "UPDATE_DATE", null) != null) {
          q.getParamMap().remove("UPDATE_DATE");
          q.getParamMap().put("UPDATE_DATE", date);
        } else {
          q.getParamMap().put("UPDATE_DATE", date);
        }
        
        if (MapUtil.getString(q.getParamMap(), "UPDATE_TIME", null) != null) {
          q.getParamMap().remove("UPDATE_TIME");
          q.getParamMap().put("UPDATE_TIME", time);
        } else {
          q.getParamMap().put("UPDATE_TIME", time);
        }
        
        if (MapUtil.getString(q.getParamMap(), "UPDATE_USER_ID", null) != null) {
          q.getParamMap().remove("UPDATE_USER_ID");
          
          if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
            q.getParamMap().put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
          }
        }
        else if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
          q.getParamMap().put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
        }
      }
    }
    


    if ((q.getBatchList() != null) && (q.getBatchList().size() > 0)) {
      for (Object obj : q.getBatchList()) {
        if ((obj instanceof Map)) {
          Map _dataMap = (Map)obj;
          if (MapUtil.getString(_dataMap, "UPDATE_DATE", null) != null) {
            _dataMap.remove("UPDATE_DATE");
            _dataMap.put("UPDATE_DATE", date);
          } else {
            _dataMap.put("UPDATE_DATE", date);
          }
          
          if (MapUtil.getString(_dataMap, "UPDATE_TIME", null) != null) {
            _dataMap.remove("UPDATE_TIME");
            _dataMap.put("UPDATE_TIME", time);
          } else {
            _dataMap.put("UPDATE_TIME", time);
          }
          
          if (MapUtil.getString(_dataMap, "UPDATE_USER_ID", null) != null) {
            _dataMap.remove("UPDATE_USER_ID");
            
            if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
              _dataMap.put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
            }
          }
          else if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
            _dataMap.put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
          }
        }
      }
    }
    

    timestamp = null;
    date = null;
    time = null;
    
    int[] rtn = _update(q);
    
    if (checkCount) {
      for (int i : rtn) {
        if (i <= 0) {
          throw new LTSDataAccessException(FdcThreadHolder.getProgramCd(), "LTSD003", null);
        }
      }
    }
    
    if (org_date != null) {
      q.getParamMap().put("UPDATE_DATE", org_date);
    }
    else if (q.getParamMap() != null) {
      q.getParamMap().remove("UPDATE_DATE");
    }
    

    if (org_time != null) {
      q.getParamMap().put("UPDATE_TIME", org_time);
    }
    else if (q.getParamMap() != null) {
      q.getParamMap().remove("UPDATE_TIME");
    }
    

    if (org_update_user_id != null) {
      q.getParamMap().put("UPDATE_USER_ID", org_update_user_id);
    }
    else if (q.getParamMap() != null) {
      q.getParamMap().remove("UPDATE_USER_ID");
    }
    

    org_date = null;
    org_time = null;
    org_update_user_id = null;
    
    return rtn;
  }
  
  public int[] delete(Query q)
    throws LTSDataAccessException
  {
    String timestamp = DateUtil.getWTimeStamp();
    String date = timestamp.substring(0, 7);
    String time = timestamp.substring(7, 15);
    






    if (q.getParamMap() != null) {
      q.getParamMap().put("UPDATE_DATE", date);
      q.getParamMap().put("UPDATE_TIME", time);
      
      if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
        q.getParamMap().put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
      }
    }
    
    if ((q.getBatchList() != null) && (q.getBatchList().size() > 0)) {
      for (Object obj : q.getBatchList()) {
        if ((obj instanceof Map)) {
          Map _dataMap = (Map)obj;
          _dataMap.put("UPDATE_DATE", date);
          _dataMap.put("UPDATE_TIME", time);
          if ((FdcThreadHolder.getUserProfile() != null) && (FdcThreadHolder.getUserProfile().getUserid() != null)) {
            _dataMap.put("UPDATE_USER_ID", FdcThreadHolder.getUserProfile().getUserid());
          }
        }
      }
    }
    
    int[] rtn = _delete(q);
    return rtn;
  }
  
  public Connection getConnection()
  {
    return DataSourceUtils.getConnection(getDataSource());
  }
}
