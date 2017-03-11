package lts.global.web.manager;

import java.util.Map;

public abstract interface LTSGeneralManager
  extends LTSWebManager
{
  public abstract Map<String, Object> processQueryForMap(String paramString, Map<String, Object> paramMap);
}
