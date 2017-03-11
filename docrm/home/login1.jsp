<%
/*
@@程式代號 = login.jsp
@@程式名稱 = 示範用登入頁
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Base64" %>
<%@ page import="gov.fdc.framework.core.dao.support.DataSourceContextHolder" %>
<%@ page import="gov.fdc.framework.web.support.GlobalSession" %>
<%@ page import="gov.fdc.framework.core.common.UserProfile" %>
<%@ page import="gov.fdc.framework.core.log.LoggerUtil" %>

<%
    request.setCharacterEncoding("UTF-8");

    String command = request.getParameter("command") == null ? new String() : request.getParameter("command");
	String userprofile = request.getParameter("userprofile") == null ? null : request.getParameter("userprofile");
	if ("processSSO".equals(command) && userprofile != null ) {
		
// 		Base64.Decoder decoder = Base64.getUrlDecoder();
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		userprofile = new String(decoder.decodeBuffer(userprofile), "UTF-8");
		String[] user = userprofile.split(",");
		
		UserProfile u = new UserProfile();
		
		u.setUserid(user[0]);
		u.setUsernm(user[1]);
		u.setOrgid(user[2]);
		u.setCountyid(user[3]);
		u.setRootorgid(user[4]);
		u.setOrg(user[5]);
	    u.setCV05(user[6]);
// 		u.setDivisionid(user[7]);
// 		u.setDivision(user[8]);
		u.setUuid( request.getSession().getId() );
		u.setLogindatetime(Long.toString(System.currentTimeMillis()));
		GlobalSession.getInstance().userLogin(u);
		
		%>
			<script>parent.location.href = "./index.jsp";</script>
		<%
		return;
	}
%>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>高雄市稅捐稽徵處</title>
		
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CommonUI.css" />
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fdc.css" />
	</head>
</html>