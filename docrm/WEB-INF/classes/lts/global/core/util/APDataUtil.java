package lts.global.core.util;

import com.acer.apps.APData;
import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.util.FdcThreadHolder;







public class APDataUtil
{
  public static void init(String config, boolean reset)
    throws Exception
  {
    APData.init(config, reset);
  }
  



  public static String getProperty(String key)
  {
    return APData.getProperty(key + "." + FdcThreadHolder.getUserProfile().getSchemaName());
  }
  




  public static String getProperty(String account, String key)
  {
    return APData.getProperty(key + "." + account);
  }
}
