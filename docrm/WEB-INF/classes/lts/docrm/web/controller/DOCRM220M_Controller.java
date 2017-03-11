/*
@@程式代號 = DOCRM220M_Controller.java
@@程式名稱 = 列印回執聯作業
@@程式版本 = V1.000
@@更新日期 = 2012/03/09
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.web.controller;

import gov.fdc.framework.web.controller.impl.RequestBean;
import gov.fdc.framework.web.controller.impl.ResponseBean;
import gov.fdc.framework.web.controller.impl.ResponseBean.ReturnType;
import gov.fdc.library.env.ApEnv;
import gov.fdc.library.exception.LTSApplicationException;
import gov.fdc.library.jasper.ProcessReport;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import lts.docrm.core.manager.DOCRMUtilManager;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.web.manager.DOCRM220M_Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acer.html.HtmlTagFactory;
import com.acer.util2.MapUtil;
import com.swetake.util.Qrcode;

@Controller
@Scope("prototype")
@RequestMapping("/docrm/DOCRM220M_Controller.do")
public class DOCRM220M_Controller extends DOCRMController {

	@Autowired
	private DOCRMUtilManager		docrmUtilManager;

	@Autowired
	private DOCRM220M_Manager	docrm220M_Manager;
	
	@Override
	public String getProgramCd() {
		return "DOCRM220M_";
	}

	/**
	 * processMain.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processMain(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map paramMap = requestBean.getRequestMap();
		
        String DOCRM_STUS =
    			HtmlTagFactory.renderOptionHtml("DOCRM_STUS", docrmUtilManager.processDDL("DOCRM_STUS"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
		
        String TAX_TP =
    			HtmlTagFactory.renderOptionHtml("TAX_TP", docrmUtilManager.processDDL("TAX_TP"), "@{PHRASE_TITLE}",
    				"@{PHRASE_KEY}");
        
        String DOCRM_TP =
    			HtmlTagFactory.renderOptionHtml("DOCRM_TP", docrmUtilManager.processDDLMess(paramMap), "@{DOCRM_TP_NM}",
    				"@{DOCRM_TP}");
        
        Map<String, Object> rtn = new HashMap<String, Object>();

        rtn.put("DOCRM_STUS", DOCRM_STUS);
        rtn.put("TAX_TP", TAX_TP);
        rtn.put("DOCRM_TP", DOCRM_TP);
        
        responseBean.setData(rtn);
	}

	/**
	 * processQuery.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQuery(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm220M_Manager.processQuery(paramMap);
		
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail1.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail1(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, ?> dataMap = docrm220M_Manager.processQueryDetail1(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail2.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail2(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, ?> dataMap = docrm220M_Manager.processQueryDetail2(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail3.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail3(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, ?> dataMap = docrm220M_Manager.processQueryDetail3(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processQueryDetail4.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processQueryDetail4(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		
		Map paramMap = requestBean.getRequestMap();
		Map<String, ?> dataMap = docrm220M_Manager.processQueryDetail4(paramMap);
		responseBean.setData(dataMap);
	}
	
	/**
	 * processEdit.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	public void processEdit(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {

		Map paramMap = requestBean.getRequestMap();
		Map dataMap = docrm220M_Manager.processEdit(paramMap);

		responseBean.setData(dataMap);
	}
	
	/**
	 * processPrint.
	 * 
	 * @param requestBean 來源Bean
	 * @param responseBean 回傳Bean
	 * @param response HttpServletResponse
	 * @throws Exception Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processPrint(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		responseBean.setReturnType(ReturnType.WEB_REPORT);
		Map paramMap = requestBean.getRequestMap();

		String reportNm = "DOCRM220R_01R";

		Map dataMap = docrm220M_Manager.processQuery(paramMap);
		Map reportMap = new HashMap();
		
		List datalist = (List) dataMap.get(DOCRMConstant.WEB_GRID_RESULT);
		
		if(datalist.size()!=0){
			Map map = (Map) datalist.get(0);
			Map queryMap = new HashMap();
			queryMap.put("TAX_TP", map.get("TAX_TP"));
			queryMap.put("STUS", "Y");
			
			queryMap.put("MESS_NO", map.get("MESS_NO"));
			List list = docrmUtilManager.processSelectTableByAny("DOCRMT230", queryMap, false).getDataList();
			
			if(list.size()>0){
				String DOC_TP = new String();
				for(int i =0; i<list.size(); i++){
					Map map2 = (Map) list.get(i);
					DOC_TP = DOC_TP + map2.get("DOC_TP");
					DOC_TP = (i != list.size()-1)? DOC_TP + "、":DOC_TP;
				}
				reportMap.put("DOC_TP", DOC_TP);
			}
			
			Map resultMap = docrmUtilManager.processSelectTableByAny("DOCRMT810", queryMap, false).getFirstByMap();
						
			String SUBJECT = (resultMap!=null? MapUtil.getString(resultMap, "SUBJECT", ""):"");
			String CONTENT = (resultMap!=null? MapUtil.getString(resultMap, "CONTENT", ""):"");

			reportMap.put("SUBJECT", SUBJECT);
			reportMap.put("CONTENT", CONTENT);
			reportMap.put("HOMEPAGE", DOCRMConstant.HOMEPAGE);
		}
//        try {  
//            BufferedImage bufImg = this.qRCodeCommon(DOCRMConstant.HOMEPAGE, "jpg", 3);  
//              
//            File imgFile = new File("E:\\HOMEPAGE.jpg");  
//            ImageIO.write(bufImg, "jpg", imgFile);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
		
		ProcessReport report = new ProcessReport(); 

		report.doWebReport(response, report.PDF, reportNm, reportMap, datalist);
	}
	
	/**
	 * @param requestBean
	 *        來源Bean
	 * @param responseBean
	 *        回傳Bean
	 * @param response
	 *        HttpServletResponse
	 * @throws Exception
	 *         Exception
	 */
	public void processDownload(RequestBean requestBean, ResponseBean responseBean, HttpServletResponse response)
		throws Exception {
		Map paramMap = requestBean.getRequestMap();

		String MESS_NO = MapUtil.getString(paramMap, "MESS_NO", "");
		String fileName = MESS_NO.concat("_申請書.pdf");
		String filePath = ApEnv.get("messSourcefile");
		String downloadName = fileName.replace(MESS_NO.concat("_"), "");
		
		File f = new File(filePath + "/" + fileName);
		
		if(!f.exists()){
			throw new LTSApplicationException("檔案不存在");
		}
		
		super.pushToClient(response, new FileInputStream(f), f.length(), URLEncoder.encode(downloadName, "UTF-8"),true);
	}
	
    private BufferedImage qRCodeCommon(String content, String imgType, int size) {  
        BufferedImage bufImg = null;  
        try {  
            Qrcode qrcodeHandler = new Qrcode();  
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
            qrcodeHandler.setQrcodeErrorCorrect('M');  
            qrcodeHandler.setQrcodeEncodeMode('B');  
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            qrcodeHandler.setQrcodeVersion(size);  
            // 获得内容的字节数组，设置编码格式  
            byte[] contentBytes = content.getBytes("utf-8");  
            // 图片尺寸  
            int imgSize = 67 + 12 * (size - 1);  
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            // 设置背景颜色  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, imgSize, imgSize);  
  
            // 设定图像颜色> BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量，不设置可能导致解析出错  
            int pixoff = 2;  
            // 输出内容> 二维码  
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                        }  
                    }  
                }  
            } else {  
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");  
            }  
            gs.dispose();  
            bufImg.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bufImg;  
    }  
}
