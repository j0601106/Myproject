<%
/*
@@程式代號 = docrm200m_.jsp
@@程式名稱 = 收件及移轉作業
@@程式版本 = V1.000
@@更新日期 = 2011/02/10
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ include file="../../global/util/commonFunction.jsp"%>
<META name="decorator" content="template_frame_1_main">

<content tag="frameSet">
[
	{
		frameName:'v1', frameSrc:'./docrm200m_v1.jsp'
	},
	{
		frameName:'v2', frameSrc:'./docrm200m_v2.jsp'
	},
	{
		frameName:'v3', frameSrc:'./docrm200m_v3.jsp'
	}
]
</content>