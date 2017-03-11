<%
/*
@@程式代號 = responseHtml.jsp
@@程式名稱 = 回傳值處理頁-HTML類
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./commonFunction.jsp"%>
<% 
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Pragma" content="no-cache"> 
	<meta http-equiv="Cache-Control" content="no-cache"> 
	<meta http-equiv="Expires" content="0"> 
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fdc.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/blockui/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiCommon.js"></script>
	<title></title>

	<script type="text/javascript">
	function processResult()
	{
		//訊息處理
		var jsonResult = <%=responseBean.toJsonString() %>;
		try {
			try {
				//假設來源form的target為開窗
				window.close();
				opener.uiCommon.uiCommonUpdateMsgOpMsg(jsonResult);
			}
			catch (e) {
				try
				{
					//假設來源form的target為iframe
					parent.uiCommon.uiCommonUpdateMsgOpMsg(jsonResult);
				}
				catch (e) {
					try {
						//假設來源form的target為本身
						uiCommon.uiCommonUpdateMsgOpMsg(jsonResult);
					}
					catch (e) {
						//以上皆非
						alert("PROGRAM=" + jsonResult.programId + "." + jsonResult.functionId + "\n\n" + jsonResult.errorStack[0]);
					}
				}
				
			}
		} catch(e) {
			alert(e);
		}
		
		//TODO callback function
	}
	</script>

</head>
<body onload='processResult();'>
</body>
</html>
