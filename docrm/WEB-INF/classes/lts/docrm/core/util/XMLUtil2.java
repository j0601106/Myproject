/*
@@程式代號 = XMLUTIL.java
@@程式名稱 = XMLUTIL.java
@@程式版本 = V1.000
@@更新日期 = 2012/03/06
@@檢查碼  = 內容由YPM自動產生
 */
package lts.docrm.core.util;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.acer.util.DateUtil;
import com.acer.util2.MapUtil;

import javax.xml.parsers.*; 
import javax.xml.transform.*; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult; 
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

public class XMLUtil2 { 
 
	private static Document document; 
	private static String filename; 

	public XMLUtil2(String name) throws ParserConfigurationException{ 
		filename=name; 
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
 		DocumentBuilder builder=factory.newDocumentBuilder(); 
 		document=builder.newDocument(); 
	} 

	public void toSave(){ 
		try{ 
			TransformerFactory tf=TransformerFactory.newInstance(); 
			Transformer transformer=tf.newTransformer(); 
			DOMSource source=new DOMSource(document); 
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes"); 
			//PrintWriter pw=new PrintWriter(new FileOutputStream(filename)); 
			OutputStreamWriter pw = new OutputStreamWriter(new FileOutputStream(filename),"UTF-8");
			StreamResult result=new StreamResult(pw); 
			transformer.transform(source,result); 
		} catch(TransformerException mye){ 
			mye.printStackTrace(); 
		} catch(IOException exp){ 
			exp.printStackTrace(); 
		} 
	} 
	
	public static void ceateDOCRM200XML(Map parameterMap) throws ParserConfigurationException{ 

		String DOCRM_NO = MapUtil.getString(parameterMap, "DOCRM_NO","");
		String RDC_NO = MapUtil.getString(parameterMap, "RDC_NO","");
		String VERIFY_NO = MapUtil.getString(parameterMap, "VERIFY_NO","");
		String ADD_MK = MapUtil.getString(parameterMap, "ADD_MK","");
		String ADD_STUS = MapUtil.getString(parameterMap, "ADD_STUS","");
		String DOCRM_STUS = MapUtil.getString(parameterMap, "DOCRM_STUS","");
		String UPDATE_USER_ID = MapUtil.getString(parameterMap, "UPDATE_USER_ID","");
		
		String sysDate = DateUtil.getSysWDate();
		String sysTime = DateUtil.getSysTime();
		
		XMLUtil2 xml=new XMLUtil2("\\DOCRM\\intranet\\qia_add_schedule\\up\\"+DOCRM_NO+"_"+sysDate+sysTime+"_FUPI.xml"); 
		
		Element root=document.createElement("DOCRM"); 
		document.appendChild(root); 
		
		//處理DOCRMT200主檔
		Element DOCRM_NO_DON=document.createElement("DOCRM_NO"); 
		Element RDC_NO_DON=document.createElement("RDC_NO"); 
		Element VERIFY_NO_DON=document.createElement("VERIFY_NO");
		Element ADD_MK_DON=document.createElement("ADD_MK"); 
		Element ADD_STUS_DON=document.createElement("ADD_STUS");
		Element DOCRM_STUS_DON=document.createElement("DOCRM_STUS");
		Element SD_DATE_DON=document.createElement("SD_DATE");
		Element SD_TIME_DON=document.createElement("SD_TIME");
		Element UPDATE_USER_ID_DON=document.createElement("UPDATE_USER_ID");
		
		DOCRM_NO_DON.appendChild(document.createTextNode(DOCRM_NO)); 
		RDC_NO_DON.appendChild(document.createTextNode(RDC_NO)); 
		VERIFY_NO_DON.appendChild(document.createTextNode(VERIFY_NO));
		ADD_MK_DON.appendChild(document.createTextNode(ADD_MK));
		ADD_STUS_DON.appendChild(document.createTextNode(ADD_STUS)); 
		DOCRM_STUS_DON.appendChild(document.createTextNode(DOCRM_STUS)); 
		SD_DATE_DON.appendChild(document.createTextNode(sysDate));
		SD_TIME_DON.appendChild(document.createTextNode(sysTime));
		UPDATE_USER_ID_DON.appendChild(document.createTextNode(UPDATE_USER_ID));
		
		root.appendChild(DOCRM_NO_DON); 
		root.appendChild(RDC_NO_DON); 
		root.appendChild(VERIFY_NO_DON); 
		root.appendChild(ADD_MK_DON);		
		root.appendChild(ADD_STUS_DON);
		root.appendChild(DOCRM_STUS_DON);
		root.appendChild(SD_DATE_DON);
		root.appendChild(SD_TIME_DON);
		root.appendChild(UPDATE_USER_ID_DON);

		//處理DOCRMT230補件
		List resultList = (List) parameterMap.get("DOCRMT230LIST");
		
		for(int i = 0; i<resultList.size(); i++){
			Map map = (Map) resultList.get(i);
			
			String DOC_TP = MapUtil.getString(map, "DOC_TP","");
			String STUS = MapUtil.getString(map, "ADD_STUS","");
			
			Element ITEM_DON=document.createElement("ITEM");
			
			Element DOC_TP_DON=document.createElement("DOC_TP");
			Element STUS_DON=document.createElement("STUS");
			
			DOC_TP_DON.appendChild(document.createTextNode(DOC_TP)); 
			STUS_DON.appendChild(document.createTextNode(STUS));
			
			ITEM_DON.appendChild(DOC_TP_DON);
			ITEM_DON.appendChild(STUS_DON);
			
			root.appendChild(ITEM_DON);
		}
		xml.toSave();
	}	
	
	public static void ceateDOCRM700XML(Map parameterMap) throws ParserConfigurationException{ 
		
		String sysDate = DateUtil.getSysWDate();
		String sysTime = DateUtil.getSysTime();
		
		XMLUtil2 xml=new XMLUtil2("\\DOCRM\\intranet\\question\\up\\"+sysDate+sysTime+"_QUS.xml"); 
		
		Element root=document.createElement("DOCRM"); 
		document.appendChild(root); 
		
		//處理DOCRMT700
		List result700List = (List) parameterMap.get("DOCRMTLIST");
		
		for(int i = 0; i<result700List.size(); i++){
			Map map = (Map) result700List.get(i);
			
			String QUESTION_ID = MapUtil.getString(map, "QUESTION_ID","");
			String QUESTION = MapUtil.getString(map, "QUESTION","");
			
			Element ITEM_DON=document.createElement("ITEM");
			
			Element QUESTION_ID_DON = document.createElement("QUESTION_ID"); 
			QUESTION_ID_DON.appendChild(document.createTextNode(QUESTION_ID));
			ITEM_DON.appendChild(QUESTION_ID_DON);
			
			Element QUESTION_DON = document.createElement("QUESTION");
			QUESTION_DON.appendChild(document.createTextNode(QUESTION));
			ITEM_DON.appendChild(QUESTION_DON);
			
			//處理DOCRMT710
			List result710List = (List) map.get("DOCRMT710LIST");

			for(int j=0; j < result710List.size(); j++){
				Map smap = (Map) result710List.get(j);
				
				String RESULT_ID = MapUtil.getString(smap, "RESULT_ID","");
				String RESULT = MapUtil.getString(smap, "RESULT","");
				
				Element RESULT_ID_DON = document.createElement("RESULT_ID"+(j+1));
				RESULT_ID_DON.appendChild(document.createTextNode(RESULT_ID));
				ITEM_DON.appendChild(RESULT_ID_DON);
				
				Element RESULT_DON = document.createElement("RESULT"+(j+1));
				RESULT_DON.appendChild(document.createTextNode(RESULT));
				ITEM_DON.appendChild(RESULT_DON);
			}
			
			root.appendChild(ITEM_DON);
		}
		xml.toSave();
	}
	
	public static void ceateDOCRM710XML(Map parameterMap) throws ParserConfigurationException{ 
		
		String DOCRM_NO = MapUtil.getString(parameterMap, "DOCRM_NO","");
		String sysDate = DateUtil.getSysWDate();
		String sysTime = DateUtil.getSysTime();
		
		XMLUtil2 xml=new XMLUtil2("\\DOCRM\\intranet\\question\\up\\"+DOCRM_NO+"_"+sysDate+sysTime+"_ANS.xml"); 
		
		Element root=document.createElement("DOCRM"); 
		document.appendChild(root); 
		
		List resultList = (List) parameterMap.get("DOCRMT720LIST");

		for(int i = 0; i<resultList.size(); i++){
			Map map = (Map) resultList.get(i);
			
			String QUESTION_ID = MapUtil.getString(map, "QUESTION_ID","");
			String RESULT_ID = MapUtil.getString(map, "RESULT_ID","");
			String RESPOND_DATE = MapUtil.getString(map, "RESPOND_DATE","");
			
			Element ITEM_DON=document.createElement("ITEM");
			
			Element DOCRM_NO_DON=document.createElement("DOCRM_NO");
			Element QUESTION_ID_DON=document.createElement("QUESTION_ID");
			Element RESULT_ID_DON=document.createElement("RESULT_ID");
			Element RESPOND_DATE_DON=document.createElement("RESPOND_DATE");
			
			DOCRM_NO_DON.appendChild(document.createTextNode(DOCRM_NO));
			QUESTION_ID_DON.appendChild(document.createTextNode(QUESTION_ID)); 
			RESULT_ID_DON.appendChild(document.createTextNode(RESULT_ID));
			RESPOND_DATE_DON.appendChild(document.createTextNode(RESPOND_DATE));
			
			ITEM_DON.appendChild(DOCRM_NO_DON);
			ITEM_DON.appendChild(QUESTION_ID_DON);
			ITEM_DON.appendChild(RESULT_ID_DON);
			ITEM_DON.appendChild(RESPOND_DATE_DON);
			
			root.appendChild(ITEM_DON);
		}
		xml.toSave();
	}
	
	public static Map readDOCRM400XML(String file) throws ParserConfigurationException, SAXException, IOException{
		File f=new File(file); 
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder=factory.newDocumentBuilder(); 
		Document doc = builder.parse(f); 
		
		List docrmt401 = new ArrayList();
		
		NodeList nl = doc.getElementsByTagName("ITEM");  

		//處理DOCRMT401
		for (int i=0;i<nl.getLength();i++){ 
			Map map = new HashMap();
			
			String DOCRM_NO = (doc.getElementsByTagName("DOCRM_NO").item(i).getFirstChild()==null? "":doc.getElementsByTagName("DOCRM_NO").item(i).getFirstChild().getNodeValue());
			String DOC_TP = (doc.getElementsByTagName("DOC_TP").item(i).getFirstChild()==null? "":doc.getElementsByTagName("DOC_TP").item(i).getFirstChild().getNodeValue());
			String FILE_NM = (doc.getElementsByTagName("FILE_NM").item(i).getFirstChild()==null? "":doc.getElementsByTagName("FILE_NM").item(i).getFirstChild().getNodeValue());
			String FILE_PATH = (doc.getElementsByTagName("FILE_PATH").item(i).getFirstChild()==null? "":doc.getElementsByTagName("FILE_PATH").item(i).getFirstChild().getNodeValue());
			String SD_DATE = (doc.getElementsByTagName("SD_DATE").item(i).getFirstChild()==null? "":doc.getElementsByTagName("SD_DATE").item(i).getFirstChild().getNodeValue());
			String SD_TIME = (doc.getElementsByTagName("SD_TIME").item(i).getFirstChild()==null? "":doc.getElementsByTagName("SD_TIME").item(i).getFirstChild().getNodeValue());
			
			map.put("DOCRM_NO", DOCRM_NO);
			map.put("DOC_TP", DOC_TP);
			map.put("FILE_NM", FILE_NM);
			map.put("FILE_PATH", FILE_PATH);
			map.put("SD_DATE", SD_DATE);
			map.put("SD_TIME", SD_TIME);

			docrmt401.add(map);			
		}
		Map parameterMap = new HashMap();
		parameterMap.put("DOCRMT401LIST", docrmt401);

		return parameterMap;
	} 

	public static Map readDOCRM710XML(String file) throws ParserConfigurationException, SAXException, IOException{
		File f=new File(file); 
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder=factory.newDocumentBuilder(); 
		Document doc = builder.parse(f); 
		
		List docrmt720 = new ArrayList();
		
		NodeList nl = doc.getElementsByTagName("ITEM");  

		//處理DOCRMT700
		for (int i=0;i<nl.getLength();i++){ 
			Map map = new HashMap();
			
			String DOCRM_NO = (doc.getElementsByTagName("DOCRM_NO").item(i).getFirstChild()==null? "":doc.getElementsByTagName("DOCRM_NO").item(i).getFirstChild().getNodeValue());
			String QUESTION_ID = (doc.getElementsByTagName("QUESTION_ID").item(i).getFirstChild()==null? "":doc.getElementsByTagName("QUESTION_ID").item(i).getFirstChild().getNodeValue());
			String RESULT_ID = (doc.getElementsByTagName("RESULT_ID").item(i).getFirstChild()==null? "":doc.getElementsByTagName("RESULT_ID").item(i).getFirstChild().getNodeValue());
			String RESPOND_DATE = (doc.getElementsByTagName("RESPOND_DATE").item(i).getFirstChild()==null? "":doc.getElementsByTagName("RESPOND_DATE").item(i).getFirstChild().getNodeValue());
			
			map.put("DOCRM_NO", DOCRM_NO);
			map.put("QUESTION_ID", QUESTION_ID);
			map.put("RESULT_ID", RESULT_ID);
			map.put("RESPOND_DATE", RESPOND_DATE);

			docrmt720.add(map);			
		}
		Map parameterMap = new HashMap();
		parameterMap.put("DOCRMT720LIST", docrmt720);

		return parameterMap;
	} 
	
	public static void main(String args[]){ 
		try{ 

			Map map = new HashMap();
//			map.put("DOCRM_NO", "123");
//			map.put("RDC_NO", "123");
//			ceateDOCRM200XML(map);
			
			//map = readDOCRM200XML("E:\\E105101778001_1051106023426_FUPI.xml");
			map = readDOCRM400XML("E:\\E105101778001_1051107220456_FUPI.xml");

		} catch(ParserConfigurationException exp){ 
			exp.printStackTrace(); 
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	} 
} 

