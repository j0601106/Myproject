package lts.global.web.manager;

import java.util.List;
import java.util.Map;

public abstract interface LTSAddressManager
  extends LTSWebManager
{
  public abstract List<Map<String, Object>> processWSST820(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> processWSST840(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> processHOUC008(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> processHOUC010(Map<String, Object> paramMap);
}
