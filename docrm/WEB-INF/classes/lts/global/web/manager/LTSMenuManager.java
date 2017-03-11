package lts.global.web.manager;

import java.util.Map;

public abstract interface LTSMenuManager
  extends LTSWebManager
{
  public abstract Map<String, Object> processQueryMenu(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> processQueryMenuAgent(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> processQueryMenuDebug(Map<String, Object> paramMap);
}
