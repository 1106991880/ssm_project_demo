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
<script type="text/javascript">
function queryData(){
	$("#form1").submit();
	waiting();
}
function returnQuery(){
	$("#refresh").submit();
	waiting();
}
$(function () {
    $("#beginTime").datebox();
    $("#endTime").datebox();
    $(".datebox :text").attr("readonly","readonly");
});
</script>
</head>
<body style="background: #FFFFFF">
	<form id="form1" action="<%=path %>/manageMoney/manageMoneyReport" method="post">
	<fieldset class="fieldset-self">
	<legend>理财报表</legend>
	<table width="100%">
		<tr>
			<td>开始时间：</td>
			<td>
				<input type="text" id="beginTime" name="beginTime" value ="${beginTime }"/>
			</td>
			<td>结束时间：</td>
			<td>
				<input type="text" id="endTime" name="endTime" value="${endTime }"/>
			</td>
			<c:if test="${user.userLevel == '0'}">
			<td>当事人：</td>
			<td>
				<form:select path="manageMoney.takeId" id="takeId" name="takeId" 
					cssClass="easyui-combobox" cssStyle="width:150px;" 
					data-options="required:false">
					<form:option value="" label=""/>
					<form:options items="${userList }" itemValue="userId" itemLabel="trueName"/>
				</form:select>
			</td>
			<td colspan="4">
				<div align="left">
					<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="queryData();">查询</a>&nbsp;
					<a href="#" class="easyui-linkbutton" icon="icon-reload" onclick="returnQuery();">重置</a>&nbsp;
				</div>
			</td>
			</c:if>
			<c:if test="${user.userLevel != '0'}">
			<td colspan="4">
				<div align="left">
					<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="queryData();">查询</a>&nbsp;
					<a href="#" class="easyui-linkbutton" icon="icon-reload" onclick="returnQuery();">重置</a>&nbsp;
				</div>
			</td>
			</c:if>
		</tr>
	</table>
	</fieldset>
	</form>
	<table width="100%">
		<tr><td align="center">
			<b>收入合计:</b><font color="red">${inSum }</font>&nbsp;&nbsp;
		   	<b>支出合计:</b><font color="red">${outSum }</font>&nbsp;&nbsp;
		   	<b>总合计:</b><font color="red">${sum }</font>
		</td></tr>
	</table>
	<form id="refresh" action="<%=path %>/manageMoney/manageMoneyReport" method="post"></form>
	<table class="table" width="100%" align="center">
	  <thead>
	  	<tr>
	      <th>收入/支出</th>
	      <th>金额</th>
	      <th>事由</th>
	      <th>收入/支出时间</th>
	      <th>是否必要</th>
	      <th>当事人</th>
	    </tr>
	  </thead>
	  <tbody>
	  	<c:forEach items="${manageList }" var="manageMoney">
			<tr onmouseover="this.bgColor='#EAF2FF'" onmouseout="this.bgColor='#FFFFFF'" align="center">
				<td>
					<c:if test="${manageMoney.inOrOut == 1}">收入</c:if>
					<c:if test="${manageMoney.inOrOut == 2}">支出</c:if>
				</td>
				<td>${manageMoney.howMuch }</td>
				<td>${manageMoney.incident }</td>
				<td><fmt:formatDate value="${manageMoney.takeTime }" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
				<td>
					<c:if test="${manageMoney.ifTake == 0}">必要</c:if>
					<c:if test="${manageMoney.ifTake == 1}">非必要</c:if>
				</td>
				<td>${manageMoney.takeId }</td>
			</tr>
		</c:forEach>
	  </tbody>
	</table>
	</body>
</html>
