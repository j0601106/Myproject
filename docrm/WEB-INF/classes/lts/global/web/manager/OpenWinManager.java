package lts.global.web.manager;

import gov.fdc.framework.web.manager.BaseWebManager;
import java.util.Map;

public abstract interface OpenWinManager
  extends BaseWebManager
{
  public abstract Map<String, Object> processQuery(Map<String, Object> paramMap);
}
