package lts.global.core.util;

import java.util.Map;

public class UiFactory
{
  public static String renderEmpty()
  {
    return "";
  }
  





  /**
   * @deprecated
   */
  public static String renderFileUpload(String name, String acceptType, String maxlength)
  {
    String result = " <input type='file' id='" + name + "' name= '" + name + "' accept='" + acceptType + "' maxlength='" + maxlength + "' />";
    

    String oldFileTag = "<span style='font-size:12px;display:none;' id='filespan_" + name + "'> 原檔案 : <span id='" + name + "_SRCNAME'></span>";
    
    oldFileTag = oldFileTag + " <input type='hidden' id='" + name + "_URI' name='" + name + "_URI' value='' />";
    oldFileTag = oldFileTag + " <input type='checkBox' id='DELETE_FLAG_" + name + "' name='DELETE_FLAG_" + name + "' value='1'>刪除 </span>";
    

    return result + oldFileTag;
  }
  







  public static String renderFileUploadV2(String tagName, String acceptType, String maxlength)
  {
    String htmlFile = " <input type='file' id='" + tagName + "' name= '" + tagName + "'" + " accept='" + acceptType + "' maxlength='" + maxlength + "' />";
    


    String htmlOriFile = "<span style='font-size:smaller;' class='file-orifile' id='_ORIFILE_" + tagName + "'></span>";
    

    return htmlFile + htmlOriFile;
  }
  






  public static String renderAddress(String prefix)
  {
    String result = "<!-- Address Begin --><table id='" + prefix + "Address' class='table-redmond' style='width:100%'>" + "<tr><td>" + "特殊或國外地址<input type='checkbox' value='2' name='" + prefix + "SPC_ADDR_TP'/>" + "</td></tr>" + "<tr><td>" + "縣市：" + "<select name='" + prefix + "ADDR_HSN'>" + "</select>" + "　鄉鎮市區：" + "<select name='" + prefix + "ADDR_TOWN'>" + "</select>" + "　郵遞區號：" + "<input name='" + prefix + "ZIP_CD1' size='3' maxlength='3' onkeypress='onlyAllowNumPress1(event)'>" + "<input name='" + prefix + "ZIP_CD2' size='2' maxlength='2' onkeypress='onlyAllowNumPress1(event)'>" + "</td></tr>" + "<tr><td>" + "村里：" + "<select name='" + prefix + "ADDR_VILL'>" + "</select>" + "　鄰：<input name='" + prefix + "ADDR_LIN' size='4' maxlength='4' onblur='UiAddress.correctLin(event)'>" + "　街路：<input name='" + prefix + "ADDR_ROAD' size='10' readonly> <img name='btn" + prefix + "ADDR_ROAD' class='window-chosen'>" + "　路段：<input name='" + prefix + "ADDR_SEC' size='4' maxlength='4' onblur='UiAddress.correctSec(event)'>" + "</td></tr>" + "<tr><td>" + "巷弄門牌：<input name='" + prefix + "ADDR_OTH' size='60' maxlength='80' onblur='toFullStr(event)'> " + "<img name='btn" + prefix + "ADDR_OTH' class='window-chosen' onclick='alert(\"TODO:連結至MDM\")'>" + "<font size='-1'>地址為「臺北市中正區黎明里００７鄰重慶南路一段５７號十四樓之３」時，巷弄門牌僅需要輸入「５７號十四樓之３」</font>" + "</td></tr>" + "</table><!-- Address End -->";
    




























    return result;
  }
  










  public static Map filterAddress(String prefix, Map parameterMap)
  {
    if (!parameterMap.containsKey(prefix + "SPC_ADDR_TP"))
    {
      parameterMap.put(prefix + "SPC_ADDR_TP", "1");
    }
    else
    {
      parameterMap.put(prefix + "SPC_ADDR_TP", "2");
      parameterMap.put(prefix + "ADDR_HSN", "");
      parameterMap.put(prefix + "ADDR_TOWN", "");
      parameterMap.put(prefix + "ADDR_VILL", "");
      parameterMap.put(prefix + "ADDR_LIN", "");
      parameterMap.put(prefix + "ZIP_CD1", "");
      parameterMap.put(prefix + "ZIP_CD2", "");
      parameterMap.put(prefix + "ADDR_ROAD", "");
      parameterMap.put(prefix + "ADDR_SEC", "");
    }
    return parameterMap;
  }
}
