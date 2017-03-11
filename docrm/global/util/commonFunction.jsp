<%
/*
@@程式代號 = commonFunction.jsp
@@程式名稱 = JSP共用函式頁--include jsp
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="gov.fdc.framework.web.controller.impl.RequestBean"%>
<%@ page import="gov.fdc.framework.web.controller.impl.ResponseBean"%>
<%@ page import="gov.fdc.library.webui.*"%>
<%@ page import="lts.global.core.util.UiFactory"%>
<%@ page import="lts.global.core.util.LTSButton"%>
<%@ page import="com.acer.dd.DDValidationManager"%>

<%!
	/**
	*/
	public void initPage(String PROGRAM_CD, String SCREEN_CD, PageContext pageContext) {
		pageContext.getRequest().setAttribute("PROGRAM_CD", PROGRAM_CD);
		pageContext.getRequest().setAttribute("SCREEN_CD", SCREEN_CD);
	}

	/**
	*/
	public String getDDValidation(String PROGRAM_CD, String SCREEN_CD, String formName) {
		try {
			return
				"createValidateMarkSpan(document." + formName + ");\r\n" + 
				DDValidationManager.getInstance().genHtmlDDValidation(PROGRAM_CD, SCREEN_CD, formName, formName);
		} catch (Exception e) {
			e.printStackTrace();
			return "alert('產生檢核錯誤');";
		}
	}

	/**
	 * not support from v1.4
	 */
	public String fileUpload(String name, String acceptType, String maxlength) {
		
		String result = " <input type='file' id='" + name + "' name= '" + name + "' accept='" + acceptType + "' maxlength='" + maxlength + "' />";

		String oldFileTag = "<span style='font-size:12px;display:none;' id='filespan_" + name  + "'> 原檔案 : <span id='" + name + "_SRCNAME'></span>";
		oldFileTag += " <input type='hidden' id='" + name + "_URI' name='" + name + "_URI' value='' />";
		oldFileTag += " <input type='checkBox' id='DELETE_FLAG_" + name + "' name='DELETE_FLAG_" + name + "' value='1'>刪除 </span>";

		return result + oldFileTag;
	}

	/**
	 * not support from v1.4
	 */
	public String tagUniformAddress(String name) {
		String result = 
			"<input type='text' name='" + name + "_ADDR_HSN' size='3' readonly class='readonly'/> " + 
			"<input type='text' name='" + name + "_ADDR_TOWN' size='5' readonly class='readonly'/> " +
			"<input type='text' name='" + name + "_ADDR_VILL' size='5' readonly class='readonly'/> " +
			"<input type='text' name='" + name + "_ADDR_LIN' size='5' readonly class='readonly'/> " +
			"<input type='text' name='" + name + "_ADDR_ROAD' size='12' readonly class='readonly'/> " +
			"<input type='text' name='" + name + "_ADDR_SEC' size='2' readonly class='readonly'/><br/>" +
			"<input type='text' name='" + name + "_ADDR_OTH' size='40' readonly class='readonly'/> ";

		result += "<img class='window-chosen'><br/>";

		result +=
			"<input type='text' name='" + name + "_ZIP_CD1' size='3' readonly class='readonly'/> " +
			"<input type='text' name='" + name + "_ZIP_CD2' size='2' readonly class='readonly'/> " +
			"<input type='text' name='" + name + "_SPC_ADDR_TP' size='10' readonly class='readonly'/> ";

		return result;
	}

	/**
	 *
	 */
	public String getRequestParamaterAsQueryString(HttpServletRequest request) throws Exception {
		return getMapToQueryString(getRequestParamater(request));
	}
	
	/**
	 *
	 */
	public String getMapToQueryString( Map<? extends Object,? extends Object> dataMap ) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		for ( Entry<? extends Object,? extends Object> e : dataMap.entrySet() ) {
			if (!flag) {
				flag = true;
				sb.append("?");
			} else {
				sb.append("&");
			}
			
			if ( e.getValue() instanceof String[]) {
				for (String str : (String[])e.getValue()) {
					sb.append(e.getKey());
					sb.append("=");
					sb.append(str);
					sb.append("&");
				}
			} else {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue().toString());
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 
	public Map<String, String[]> getRequestParamater(HttpServletRequest request) throws Exception  {
		Map<String, Object> pMap = request.getParameterMap();
		Map<String, String[]> rtn = new HashMap<String, String[]>();
		StringBuffer sb = new StringBuffer();
		for ( Entry<String, Object> e : pMap.entrySet() ) {
			List<String> strs = new ArrayList<String>();
			if ( e.getValue() instanceof String) {
				if( e.getValue() != null ) {
					strs.add( new String(((String)e.getValue()).getBytes("ISO-8859-1"), "UTF-8") );
				}
			} else if ( e.getValue() instanceof String[]) {
				for (String str : (String[])e.getValue()) {
					strs.add( new String(str.getBytes("ISO-8859-1"), "UTF-8") );
				}
			}
			rtn.put(e.getKey(), strs.toArray(new String[0]));
		}
		return rtn;
	}*/
	
	public Map<String, String[]> getRequestParamater(HttpServletRequest request) throws Exception  {
		Map pMap = request.getParameterMap();
		Map<String, String[]> rtn = new HashMap<String, String[]>();
		StringBuffer sb = new StringBuffer();
		for ( Iterator it = pMap.entrySet().iterator() ; it.hasNext() ; ) {
			Entry e = (Entry)it.next();
			List<String> strs = new ArrayList<String>();
			if ( e.getValue() instanceof String) {
				if( e.getValue() != null ) {
					strs.add( new String(((String)e.getValue()).getBytes("ISO-8859-1"), "UTF-8") );
				}
			} else if ( e.getValue() instanceof String[]) {
				for (String str : (String[])e.getValue()) {
					strs.add( new String(str.getBytes("ISO-8859-1"), "UTF-8") );
				}
			}
			rtn.put((String)e.getKey(), (String[])strs.toArray(new String[0]));
		}
		return rtn;
	}
%>
<%
request.setCharacterEncoding("UTF-8");
ResponseBean responseBean = request.getAttribute("ResponseBean") == null 
	? null 
	: (ResponseBean)request.getAttribute("ResponseBean");
%>