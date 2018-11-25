<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>add</title>
<%@ include file="/pages/include.jsp"%>
<script type="text/javascript">
$(function () {
    $("#establishTime").datetimebox();
    $(".datebox :text").attr("readonly","readonly");
});

</script>
</head>
<body>
	<fieldset class="fieldset-self">
	<legend>部门详细信息</legend>
	<form:hidden path="dept.deptId" id="deptId"/>
	<table width="100%">
		<tr>
			<td align="right">部门编号：</td>
			<td align="left">${dept.deptNo }</td>
			<td align="right">部门名称：</td>
			<td align="left">${dept.deptName }</td>
			<td align="right">创立时间：</td>
			<td align="left"><fmt:formatDate value="${dept.establishTime }" pattern="yyyy-MM-dd"/></td>
		</tr>
		<tr>
			<td align="right">部门经理：</td>
			<td align="left">${trueName}</td>
			<td align="right">上级部门：</td>
			<td align="left">${superDeptName }</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">部门描述：</td>
			<td align="left" colspan="5">${dept.deptDesc}</td>
		</tr>
	</table>
	</fieldset>
</body>
</html>
