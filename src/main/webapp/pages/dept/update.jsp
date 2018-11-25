<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>add</title>
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
<form id="form1" action="<%=path%>/dept/updateDept" method="POST">
	<fieldset class="fieldset-self">
		<legend>更新部门信息</legend>
		<form:hidden path="dept.deptId" id="deptId" />
		<table width="100%">
			<tr>
				<td align="right">部门编号：</td>
				<td align="left">
					<form:input path="dept.deptNo" id="deptNo" cssClass="easyui-textbox" readonly="true" />
				</td>
				<td align="right">部门名称：</td>
				<td align="left">
					<form:input path="dept.deptName" id="deptName" cssClass="easyui-textbox"
						data-options="required:true" missingMessage="部门必须填写" />
				</td>
				<td align="right">创立时间：</td>
				<td align="left">
					<form:input path="dept.establishTime" id="establishTime" cssStyle="150px;"
						data-options="required:true" missingMessage="创立必须填写"/>
					<script type="text/javascript">
						$(function() {
							$("#establishTime").datebox();
							$(".datebox :text").attr("readonly", "readonly");
						});
					</script>
				</td>
			</tr>
			<tr>
				<td align="right">部门经理：</td>
				<td align="left">
					<form:select path="dept.deptManager" id="deptManager" name="takeId"
						cssClass="easyui-combobox"
						cssStyle="width:155px;" data-options="required:false">
						<form:option value="" label="" />
						<form:options items="${userList }" itemValue="userId" itemLabel="trueName" />
					</form:select>
				</td>
				<td align="right">上级部门：</td>
				<td align="left">
					<input type="text" id="superDeptName" class="easyui-textbox" value="${superDeptName }"
						readonly="readonly" />
					<form:hidden path="dept.superId" id="superId" />
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td align="left" colspan="6">部门描述：</td>
			</tr>
			<tr>
				<td align="left" colspan="6">
					<form:textarea path="dept.deptDesc" id="deptDesc" cssClass="easyui-textbox"
						data-options="multiline:true" cssStyle="height:100px;width:99.5%" />
				</td>
			</tr>
			<tr>
				<td colspan="6" align="center">
					<form:hidden path="dept.ifLeaf" id="ifLeaf"/>
					<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="updateDept();">保存</a>&nbsp;
				</td>
			</tr>
		</table>
	</fieldset>
</form>
<script type="text/javascript">
function updateDept() {
	//var validate = $("#form1").form('enableValidation').form('validate');
	//if(validate){
		var options = {
			success: function (data){
				var result = $.parseJSON(data).result;
	        	if(result == '1'){
	        		$.messager.alert('温馨提示','部门信息更新成功！', 'info', function(){
	        			window.location.href = '<%=path%>/dept/turnToDeptTree';
	        		});
	        	}else{
	        		$.messager.alert('温馨提示','部门信息更新失败！', 'info');
	        	}
	        },
			error:function(){
				$.messager.alert('温馨提示','操作失败！', 'error');
			}
		};
		$('#form1').form('submit', options);
	//}
}
</script>
</body>
</html>
