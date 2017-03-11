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
<%@ page import="gov.fdc.framework.core.dao.support.DataSourceContextHolder" %>
<%@ page import="gov.fdc.framework.web.support.GlobalSession" %>
<%@ page import="gov.fdc.framework.core.common.UserProfile" %>
<%@ page import="gov.fdc.framework.core.log.LoggerUtil" %>
<%!
	List<UserProfile> testUsrsList = new ArrayList<UserProfile>();

//--- SSO完成前，先行模擬使用者與對應的DataSource
	{
		UserProfile user = new UserProfile();
		user.setCountyid("E");
		user.setCounty("高雄市/西區");
		user.setOrgid("E77");
		user.setOrg("局1");
		user.setDivisionid("div1");
		user.setDivision("科1");
		user.setSectionid("sec1");
		user.setSection("分局1");
		user.setUserid("LE00000005");
		user.setUsernm("測試員3");
		user.setEmail("test3@test.net");
		user.setUserremark("Wast");
		user.setCV05("R_ADMIN");
        user.setRootorgid("E77");
		testUsrsList.add(user);
	}

	{
		UserProfile user = new UserProfile();
		user.setCountyid("A");
		user.setCounty("台北市");
		user.setOrgid("A17");
		user.setOrg("台北市總處");
		user.setDivisionid("01");
		user.setDivision("科室1");
		user.setSectionid("01");
		user.setSection("課股1");
		user.setUserid("LA00000001");
		user.setUsernm("測試員1");
		user.setEmail("test1@test.net"); 
        user.setRootorgid("A17");
		testUsrsList.add(user);
	}
	{
		UserProfile user = new UserProfile();
		user.setCountyid("B");
		user.setCounty("台中市");
		user.setOrgid("B54");
		user.setOrg("台中市總處");
		user.setDivisionid("01");
		user.setDivision("科室1");
		user.setSectionid("01");
		user.setSection("課股1");
		user.setUserid("LB00100162");
		user.setUsernm("測試員1");
		user.setEmail("test1@test.net");
        user.setRootorgid("B54");
		testUsrsList.add(user);
	}
	{
		UserProfile user = new UserProfile();
		user.setCountyid("B");
		user.setCounty("新北市");
		user.setOrgid("B54");
		user.setOrg("新北市總處");
		user.setDivisionid("01");
		user.setDivision("科室1");
		user.setSectionid("01");
		user.setSection("課股1");
		user.setUserid("LA00000004");
		user.setUsernm("測試員1");
		user.setEmail("test1@test.net");
        user.setRootorgid("B54");
		testUsrsList.add(user);
	}
	{
		UserProfile user = new UserProfile();
		user.setCountyid("E");
		user.setCounty("高雄市/東區");
		user.setOrgid("E76");
		user.setOrg("局1");
		user.setDivisionid("div1");
		user.setDivision("科1");
		user.setSectionid("sec1");
		user.setSection("分局1");
		user.setUserid("LE00000004");
		user.setUsernm("測試員2");
		user.setEmail("test2@test.net");
		user.setUserremark("East");
        user.setRootorgid("E76");
		testUsrsList.add(user);
	}
	
	{
		UserProfile user = new UserProfile();
		user.setCountyid("A");
		user.setCounty("台北市");
		user.setOrgid("A17");
		user.setOrg("局1");
		user.setDivisionid("div1");
		user.setDivision("科1");
		user.setSectionid("sec1");
		user.setSection("分局1");
		user.setUserid("LA00000002");
		user.setUsernm("測試員4");
		user.setEmail("test4@test.net");
        user.setRootorgid("A17");
		testUsrsList.add(user);
	}
	
	{
		UserProfile user = new UserProfile();
		user.setCountyid("A");
		user.setCounty("台北市");
		user.setOrgid("A17");
		user.setOrg("局1");
		user.setDivisionid("div1");
		user.setDivision("科1");
		user.setSectionid("sec1");
		user.setSection("分局1");
		user.setUserid("LA00000003");
		user.setUsernm("測試員5");
		user.setEmail("test5@test.net");
        user.setRootorgid("A17");
		testUsrsList.add(user);
	}
	//--- SSO完成前，先行模擬使用者與對應的DataSource
%>
<%

	String userId = request.getParameter("userId") == null 
						? null 
						: request.getParameter("userId");

	if (userId != null) {
		
		for ( UserProfile u : testUsrsList) {
			if (u.getUserid().equals(userId)) {
				String sessionId = request.getSession().getId();
				LoggerUtil.debug(this.getClass(), 
						" ### [login.jsp] login."
						+ " sessionId:[" + sessionId + "], "
						+ " userId:[" + userId + "] "
						+ "]  ###");
				
				u.setUuid( sessionId );
				u.setLogindatetime(Long.toString(System.currentTimeMillis()));
				GlobalSession.getInstance().userLogin(u);
				break;
			}
		}
		%>
			<script>parent.location.href = "./index.jsp";</script>
		<%
		return;
	}
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>財稅中心</title>
		
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/CommonUI.css" />
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fdc.css" />

		<script>
			function doLogin() {
				document.queryForm.action = './login.jsp';
				document.queryForm.submit();
			}
		</script>
	</head>

	<body>
	
		<table align="center" border="0" style='width:300px;height:100%'>
			<tr>
				<td>
					<div id='queryDiv' class="block" style='width:100%' >
						<!-- cui:portlet -->
						<div class="portlet" style="clear:both;">
							<div class="portlet-header">
								<span class="portlet-title">登入</span>
							</div>
							<div class="portlet-content">
								<form id="queryForm" name="queryForm" method="POST">
									<table class="table-redmond" style='width:100%'>
										<tr>
											<th>人員:</th>
											<td>
												<select name="userId">
													<%
													for ( UserProfile u : testUsrsList) {
														%>
														<option value="<%=u.getUserid()%>"><%=u.getCounty()%>[<%=u.getCountyid()%>] / <%=u.getUsernm()%>[<%=u.getUserid()%>]</option>
														<%
													}
													%>
												</select>
											</td>
										</tr>
										<tr>
											<td colspan="2" align="right">
												<center><input type="button" id="btn_login" name="btn_login" value="登入" class="btn" onclick="doLogin();"></center>
											</td>
										</tr>
									</table>
								</form>
					      	</div>
						</div>
					    <!-- cui:portlet -->
					 	<div class='separatorEmpty'></div>
					</div>
				</td>
			</tr>
		</table>
	
	</body>
</html>