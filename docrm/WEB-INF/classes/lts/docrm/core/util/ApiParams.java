/*
@@程式代號 = ApiParams.java
@@程式名稱 = WebSerivce 參數介接
@@程式版本 = V1.000
@@更新日期 = 2015/09/01
@@檢查碼  = 內容由YPM自動產生
 */

package lts.docrm.core.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

@XmlRootElement
public class ApiParams {

  //使用者代號 
    String userCd;
    
    //要呼叫的API名稱
    String apiName = "defaultApi";
    
    //參數
    Map<String, Object> params = new HashMap<String, Object>();

    //=========Inner class Start========    

    private static class ApiList {
        private List apiList = new ArrayList();
        /**
         * List給Map寫入
         * @param apimap ApiMap
         */
        public void add(ApiMap apiMap) {
            apiList.add(apiMap);
        }
        /**
         * 回傳整個List內容
         * @return wssList List
         */
        public List getContent() {
            return apiList;
        }    
    }
    
    private static class ApiMap {
        private Map<String, Object> apiMap;
        /**
         * 建構子
         * @param input Map<String, Object>
         */
        public ApiMap (Map<String, Object> input) {
            super();
            this.apiMap = input;        
        }
        /**
         * 取得map物件
         * @return Map<String, Object>
         */
        public Map<String, Object> getApiMap() {
            return this.apiMap;
        }
        /**
         * 傳入map物件
         * @param input Map<String, Object>
         */
        public void setApiMap(Map<String, Object> input) {
            this.apiMap = input;
        }
        /**
         * @return String
         */
        public String toString() {
            return "book : " + apiMap.toString();
        }
    }    
    //=========Inner class End==========
    
    /**
     * 將參數傳遞到 相關API使用
     */
    public ApiParams() {
    }
    
    /**
     * @return userCd
     */
    public String getUserCd() {
        return userCd;
    }

    /**
     * @param userCd 的設定的 userCd
     */
    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    /**
     * @return apiName
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * @param apiName 的設定的 apiName
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * @return params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params 的設定的 params
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ApiParams [userCd=" + userCd + ", apiName=" + apiName + ", params=" + params + "]";
    }
    
    /**
     * 從XML資料建立 ApiParams 物件
     * 
     * @param xmlString
     *            輸入之XML字串
     * @return 產出之 ApiParams 物件
     */
    public static ApiParams createByXml(String xmlString) {
        ApiParams apiParams = new ApiParams();
        try {
            JAXBContext context = JAXBContext.newInstance(ApiParams.class);

            Unmarshaller um = context.createUnmarshaller();
            StringReader sReader = new StringReader(xmlString);
            apiParams = (ApiParams) um.unmarshal(sReader);

        } catch (JAXBException e) {
            e.getStackTrace();
        }
        return apiParams;
    }

    /**
     * 將Map轉為XML之工具 
     * @param params 輸入之Map
     * @return 轉出之XML字串
     */
    public static String mapToXML(Map<String, Object> params) {
        ApiParams temParams = new ApiParams();

        temParams.setParams(params);
        String xmlString = temParams.toXMLString();

        return xmlString;
    }    
    /**
     * 將XML轉為Map
     * @param xmlString 輸入之XML字串
     * @return 轉出之Map物件
     */
    public static Map<String, Object> xmlToMap(String xmlString) {
        ApiParams temParams = createByXml(xmlString);
        Map<String, Object> params = temParams.getParams();

        return params;
    }
    
    /**
     * 將此參數物件轉為XML格式
     * @return 參數物件轉出之XML字串
     */
    public String toXMLString() {
        String xmlString = "";
        Marshaller m;
        StringWriter sWriter = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(ApiParams.class);

            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(this, sWriter);
            xmlString = sWriter.toString();
        } catch (JAXBException e) {
            e.getStackTrace();
        }
        return xmlString;
    }
        
    /**
     * List<Map<String, Object>> -> XML
     * @param inputList List<Map<String, Object>>
     * @return String
     */
    public static String listMapToXml(List<Map<String, Object>> inputList) {
        XStream xStream = new XStream(new StaxDriver());        
        xStream.alias("ApiMap", ApiMap.class);  
        xStream.alias("ApiList", ApiList.class);
        
        ApiList apiList = new ApiList();  
        for (int i = 0 ; i < inputList.size() ; i++) {
            ApiMap apiMap = new ApiMap(inputList.get(i));
            apiList.add(apiMap);  
        }
        return xStream.toXML(apiList);
    }
        
    /**
     * XML -> List<Map<String, Object>> 
     * @param xml String
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> xmlToListMap(String xml) {
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("ApiMap", ApiMap.class);  
        xStream.alias("ApiList", ApiList.class);
        
        ApiList returnList = (ApiList) xStream.fromXML(xml);
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < returnList.getContent().size(); i++) {  
            ApiMap apiMap = (ApiMap) returnList.getContent().get(i);
            result.add(apiMap.getApiMap());              
        }                        
        return result;
    }
    
    /**
     * setStr -> Set<String>
     * @param setStr String
     * @return Set<String>
     */
    public static Set<String> setStrToSet(String setStr) {
        Set<String> set; 
        if (setStr != null && setStr.length() > 0) {
            String[] array = setStr.replace("[", "").replace("]", "").split(", ");
            
            set = new HashSet<String>(Arrays.asList(array));
        } else {
            set = new HashSet<String>();
        }
        
        return set;
    }
    
}
