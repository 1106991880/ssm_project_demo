<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%String path = request.getContextPath();%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title></title>
<%@ include file="/pages/include.jsp"%>
</head>
<body style="background: #FFFFFF">
	<form id="addForm" action="<%=path %>/role/saveRole" method="post">
		<table width="100%">
			<tr>
				<td align="right">角色名称：</td>
				<td align="left">
					<form:input path="role.roleName" id="roleName" cssClass="easyui-textbox"
						data-options="required:true" missingMessage="角色名称必须填写"/>
				</td>
			</tr>
			<tr>
				<td align="right">角色描述：</td>
				<td align="left">
					<form:input path="role.roleDesc" id="roleDesc" cssClass="easyui-textbox"
						data-options="multiline:true" style="width:200px;height:70px"/>
				</td>
			</tr>
			<tr>
				<td align="right">菜单权限： </td>
				<td align="left">
					<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="loadMenu();">添加</a>
				</td>
			</tr>
			<tr><td colspan="2" align="left" style="padding-left: 30px"><span id="haveMenus"></span></td></tr>
		</table>
	</form>
</body>
</html>