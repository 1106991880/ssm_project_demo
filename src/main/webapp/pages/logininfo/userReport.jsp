<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>query</title>
<%@ include file="/pages/include.jsp"%>
<style scoped="scoped">
.textbox {
	height: 20px;
	margin: 0;
	padding: 0 2px;
	box-sizing: content-box;
}
</style>
</head>
<body style="background: #FFFFFF">

	<table width="100%">
		<tr><td align="center">
		   	<b>总共:</b><font color="red">${sum }</font><b>个人登陆</b>
		</td></tr>
	</table>
	<form id="refresh" action="<%=path %>/manageMoney/manageMoneyReport" method="post"></form>
	<table class="table" align="right">
	  <thead>
	  	<tr>
	      <th>用户ip</th>
	      <th>用户地址</th>
	      <th>登陆时间</th>

	    </tr>
	  </thead>
	  <tbody>
	  	<c:forEach items="${usersList}" var="users">
			<tr onmouseover="this.bgColor='#EAF2FF'" onmouseout="this.bgColor='#FFFFFF'" align="center">
				<td>
				${users.loginuser_ip} 
				</td>
				<td>${users.loginuser_address}</td>
				<td>${users.loginuser_logintime}</td>
			</tr>
		</c:forEach>
	  </tbody>
	</table>
	</body>
</html>
