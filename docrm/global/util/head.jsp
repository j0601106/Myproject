<%
/*
@@程式代號 = head.jsp
@@程式名稱 = JSP共用函式頁--include js
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--  
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.16.custom.css" />
-->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/redmond/jquery-ui-1.8.2.custom.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/ui.multiselect.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CommonUI.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fdc.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.4.js"></script>
<!-- 
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/form/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/blockui/jquery.blockUI.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/form/form2object.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/multiplefile/jquery.MultiFile.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validation/jquery.metadata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validation/additional-methods.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/autotab/jquery.autotab-1.1b.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/culture/globalize.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/culture/globalize.culture.en-US.js"></script>

<!-- acer.validate -->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/acer.validate/acer.checkutil-1.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/acer.validate/acer.formvalidate.util-2.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/acer.validate/acer.formvalidate-2.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/acer.validate/acer.stringutil-1.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/acer.validate/acer.event-1.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/acer.validate/acer.message-1.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/acer.validate/event.js"></script>

<%// !!  jqGrid/i18n 必須在 jquery.jqGrid.min.js 之前 !! %>
<!-- 
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqGrid/i18n/grid.locale-zh_TW.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqGrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/multiselect/ui.multiselect.js"></script>
 -->
 
<!-- dtree -->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/dtree/dtree.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dtree/dtree.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/stringUtil.js"></script>

<%// !! 訊息資料載入 !! %>
<script type="text/javascript" src="<%=request.getContextPath()%>/servlet/messageServlet"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/Logger.js"></script>

<%// !!  pageController.js 必須在 uiCommon.js 之前 !! %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/pageController.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiCommon.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/formUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/checkBoxUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/radioUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/selectOptionUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/fdcBlock.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiLayout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiProcess.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiPlugins.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiTableGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiFrameset.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/fdcButton.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/openWindowUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/PhraseUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiCascadeDDL-2.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiCustomWindow.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiPortlet.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiTextBound.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiAutotab.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/globalCacheUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/managerUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/fdc/uiFileUpload.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/lts/uiAddress.js"></script>
