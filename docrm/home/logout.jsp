<%
/*
@@程式代號 = logout.jsp
@@程式名稱 = 示範用登出頁
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gov.fdc.framework.core.dao.support.DataSourceContextHolder" %>
<%
		//clean setup
		DataSourceContextHolder.resetDefaultDataSource();
		%>
			<script>
			parent.location.href = "<%=request.getContextPath()%>/index.jsp";
			</script>
		<%
%>