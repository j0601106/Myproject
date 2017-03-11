package lts.global.core.util;

import gov.fdc.framework.core.common.UserProfile;
import gov.fdc.framework.core.util.FdcThreadHolder;
import gov.fdc.library.phrase.Phrase;
import java.util.List;
import java.util.Map;
import org.springframework.util.Assert;









public class PhraseUtil
{
  public static List<Map<String, Object>> getForListMap(String phraseTypeKey)
    throws Exception
  {
    Assert.notNull(FdcThreadHolder.getUserProfile(), "UserProfile is null.");
    Assert.notNull(FdcThreadHolder.getUserProfile().getSchemaName(), "UserProfile schema name is null.");
    

    List<Map<String, Object>> accountList = getForListMap(FdcThreadHolder.getUserProfile().getSchemaName(), phraseTypeKey);
    
    return accountList;
  }
  





  public static List<Map<String, Object>> getForListMap(String acount, String phraseTypeKey)
    throws Exception
  {
    return Phrase.getInstance().getForList(acount, phraseTypeKey);
  }
  




  public static String[] getAllKey(String account)
    throws Exception
  {
    return Phrase.getInstance().getAllKey(account);
  }
  





  public static Map<String, Object> getForMap(String phraseTypeKey, String phraseKey)
    throws Exception
  {
    List<Map<String, Object>> dataList = getForListMap(phraseTypeKey);
    for (Map<String, Object> dataMap : dataList) {
      if (phraseKey.equals(dataMap.get("PHRASE_KEY").toString())) {
        return dataMap;
      }
    }
    return null;
  }
  






  public static Map<String, Object> getForMap(String acount, String phraseTypeKey, String phraseKey)
    throws Exception
  {
    List<Map<String, Object>> dataList = getForListMap(acount, phraseTypeKey);
    for (Map<String, Object> dataMap : dataList) {
      if (phraseKey.equals(dataMap.get("PHRASE_KEY").toString())) {
        return dataMap;
      }
    }
    return null;
  }
  






  public static String getForString(String phraseTypeKey, String phraseKey)
    throws Exception
  {
    List<Map<String, Object>> dataList = getForListMap(phraseTypeKey);
    for (Map<String, Object> dataMap : dataList) {
      if (phraseKey.equals(dataMap.get("PHRASE_KEY").toString())) {
        if (dataMap.containsKey("PHRASE_TITLE")) {
          return dataMap.get("PHRASE_TITLE").toString();
        }
        return "";
      }
    }
    return "";
  }
  






  public static String getForString(String acount, String phraseTypeKey, String phraseKey)
    throws Exception
  {
    List<Map<String, Object>> dataList = getForListMap(acount, phraseTypeKey);
    for (Map<String, Object> dataMap : dataList) {
      if (phraseKey.equals(dataMap.get("PHRASE_KEY").toString())) {
        if (dataMap.containsKey("PHRASE_TITLE")) {
          return dataMap.get("PHRASE_TITLE").toString();
        }
        return "";
      }
    }
    return "";
  }
}
