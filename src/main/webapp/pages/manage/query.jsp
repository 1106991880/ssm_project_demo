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
<style type="text/css">

</style>
<script type="text/javascript">
function add(){
	var form = document.getElementById("form1");
	form.action = "<%=path%>/manageMoney/addManageMoney";
	form.submit();
	waiting();
}

function update(){
	var flag = 0;
	var ids = document.getElementsByName("cb");
	for(var i = 0; i < ids.length; i++){
		if(ids[i].checked){
			$("#manageId").val(ids[i].value);
			flag++;
		}
	}
	if(flag != 1){
		$.messager.alert("温馨提示", "请选择一条记录！", "info");
		return false;
	}
	var form = document.getElementById("form1");
	form.action = "<%=path%>/manageMoney/editManageMoney";
	form.submit();
	waiting();
}

function del(){
	var manageId = "";
	var ids = document.getElementsByName("cb");
	for(var i = 0; i < ids.length; i++){
		if(ids[i].checked){
			manageId = manageId + "," + ids[i].value;
		}
	}
	if(manageId == ""){
		$.messager.alert("温馨提示", "请选择记录！", "info");
		return false;
	}
	$("#manageId").val(manageId);
	$.messager.confirm('温馨提示','是否删除所选记录？',function(y){
		if(y){
			var form = document.getElementById("form1");
			form.action = "<%=path%>/manageMoney/deleteManageMoney";
			form.submit();
			waiting();
		}else {
			return false;
		}
	}); 
}

function selectAll() {
	var cbFlag = document.getElementsByName("cbFlag");
	var ids = document.getElementsByName("cb");
	for ( var i = 0; i < ids.length; i++) {
		if (cbFlag[0].checked) {
			ids[i].checked = true;
		} else {
			ids[i].checked = false;
		}
	}
}
</script>
</head>
<body style="background: #FFFFFF">
	<table>
		<tr><td>
			<a href="#" class="easyui-linkbutton" icon="icon-add" onclick="add();">添加</a>&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-edit" onclick="update();">修改</a>&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-remove" onclick="del();">删除</a>&nbsp;
		</td></tr>
	</table>
	<form id="form1" name="form1" action="#" method="post">
		<input type="hidden" id="manageId" name="manageId" value="">
	</form>
	<table class="table" width="100%" align="center">
	  <thead>
	  	<tr>
	      <th><input type="checkbox" id="cbFlag" name="cbFlag" onclick="selectAll();"></th>
	      <th>收入/支出</th>
	      <th>金额</th>
	      <th>事由</th>
	      <th>收入/支出时间</th>
	      <th>是否必要</th>
	      <th>当事人</th>
	      <th>创建时间</th>
	      <th>创建人</th>
	    </tr>
	  </thead>
	  <tbody id="tab">
	  	<c:forEach items="${manageList }" var="manageMoney">
			<tr onmouseover="this.bgColor='#EAF2FF'" onmouseout="this.bgColor='#FFFFFF'" align="center">
				<td><input type="checkbox" id="cb" name="cb" value="${manageMoney.manageId }"></td>
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
				<td><fmt:formatDate value="${manageMoney.createTime }" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
				<td>${manageMoney.createId }</td>
			</tr>
		</c:forEach>
	  </tbody>
	  <tfoot>
	    <tr>
	      <td class="td_foot" colspan="9">
	      	<form id="page" action="<%=path%>/manageMoney/queryManageMoney" method="post">
				<div class="easyui-pagination"
			        data-options="
			        	total : ${pageInfo.totalRecords },
			        	pageSize: ${pageInfo.pageSize},
			        	pageNumber: ${pageInfo.currentPage},
			        	layout:['list','sep','first','prev','links','next','last','sep','refresh'],
			        	onSelectPage: function(pageNumber, pageSize){   
			                easyPage(pageNumber, pageSize);
			            }
			        ">
			    </div>
				<form:hidden path="pageInfo.currentPage" id="currentPage"/>
				<form:hidden path="pageInfo.pageSize" id="pageSize"/>
			</form>
	      </td>
	    </tr>
	  </tfoot>
	</table>
	</body>
</html>
