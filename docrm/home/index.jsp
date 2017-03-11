<%
/*
@@程式代號 = index.jsp
@@程式名稱 = 首頁
@@程式版本 = V1.000
@@更新日期 = 2012/07/08
@@檢查碼 = 內容由YPM自動產生
*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>高雄市稅捐稽徵處</title>

<script>

function closeWin() {
	window.open('', '_self', ''); 
	window.close(); 
}
</script>
</head>
<frameset name="main" id="main" rows="60,*" framespacing="0" frameborder="no" border="0">
	<frame name='titleFrame' id='titleFrame' src="./title.jsp?ts=<%=System.currentTimeMillis() %>" frameborder="no" scrolling="no" noresize marginwidth="0" marginheight="0"/>
	<frameset name="menu" id="menu" cols="240,*" framespacing="0" frameborder="no" border="0">
		<frame name='menuFrame' id='menuFrame' src="./menu.jsp" framespacing="0" frameborder="no" scrolling="yes" noresize marginwidth="0" marginheight="0"/>
		<frame name='mainFrame' id='mainFrame' src="./main.jsp" framespacing="0" frameborder="no" scrolling="no" noresize marginwidth="0" marginheight="0"/>
	</frameset>
</frameset>

<noframes><body>
</body></noframes>

</html>