package lts.global.core.util;

import gov.fdc.framework.core.util.ApplicationContextSingleton;
import org.springframework.context.ApplicationContext;









public class LTSManagerUtil
{
  public static Object getManager(String name)
  {
    return ApplicationContextSingleton.getInstance().getBean(name);
  }
}
