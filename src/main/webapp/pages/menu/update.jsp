<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>update</title>
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
function returnQuery(){
	var form = document.forms[1];
	form.action = "<%=path%>/menu/queryMenu";
	form.submit();
	waiting();
}
function updateMenu(){
	var validate = $("#form1").form('enableValidation').form('validate');
	if(validate){
		$('#form1').form('submit',{
			success: function (data){
				var result = $.parseJSON(data).result;
	        	if(result == 1){
	        		$.messager.alert('温馨提示','菜单更新成功！', 'info', function(){
	        			window.location.href = '<%=path%>/menu/queryMenu';
	        		});
	        	}else{
	        		$.messager.alert('温馨提示','菜单更新失败！', 'info');
	        	}
	        },
			error:function(){
				$.messager.alert('温馨提示','操作失败！', 'error');
			}
        });
	}
}

function setMenuType(){
	var type = $('#menuUri').val();
	if(type.substring(0,4) == 'http'){
		$('#menuType').combobox('setValue','web');
	}else{
		$('#menuType').combobox('setValue','system');
	}
}
</script>
</head>
<body style="background: #FFFFFF">
	<form id="form1" name="form1" method="post" action="<%=path%>/menu/updateMenu">
	<fieldset class="fieldset-self">
	<legend>更新菜单信息</legend>
			<table width="100%">
				<tr>
					<td>菜单名称：</td>
					<td><form:input path="menu.menuName" id="menuName" cssClass="easyui-textbox" data-options="required:true"
							missingMessage="菜单名称必须填写" /></td>
					<td>菜单URI：</td>
					<td colspan="3"><form:input path="menu.menuUri" id="menuUri" cssClass="easyui-validatebox textbox"
							data-options="required:true" cssStyle="width:350px" missingMessage="菜单URI必须填写" onchange="setMenuType();" /></td>
					<td>菜单类型：</td>
					<td><form:select path="menu.menuType" id="menuType" cssClass="easyui-combobox" cssStyle="width:150px;">
							<form:options items="${menuTypes }" />
						</form:select> <form:hidden path="menu.menuOrder" /></td>
				</tr>
				<tr>
					<td colspan="8">菜单描述：</td>
				</tr>
				<tr>
					<td colspan="8"><form:textarea path="menu.menuDesc" id="menuDesc" cssClass="easyui-textbox"
							data-options="multiline:true" cssStyle="height:100px;width:99.5%" /></td>
				</tr>
				<tr>
					<td colspan="8" align="center"><form:hidden path="menu.menuId" id="menuId" /> <a href="#"
						class="easyui-linkbutton" icon="icon-save" onclick="updateMenu();">保存</a>&nbsp; <a href="#"
						class="easyui-linkbutton" icon="icon-cancel" onclick="returnQuery();">取消</a>&nbsp;</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form></form>
</body>

</html>
