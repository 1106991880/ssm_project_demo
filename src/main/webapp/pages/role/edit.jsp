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
	<form id="editForm" action="<%=path %>/role/updateRole" method="post">
		<table width="100%">
			<tr>
				<td align="right">角色名称：</td>
				<td align="left">
					<form:input path="role.roleName" id="roleName" cssClass="easyui-textbox"
						data-options="required:true" missingMessage="角色名称必须填写"/>
					<form:hidden path="role.roleId" id="roleId"/>
					<form:hidden path="role.createTime" id="createTime"/>
					<form:hidden path="role.creator" id="creator"/>
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
			<tr>
				<td colspan="2" align="left" style="padding-left: 30px">
					<c:forEach items="${role.menus }" var="menu" varStatus="status">
						<span id="${menu.menu.menuId }">${menu.menu.menuName }
							<input type="hidden" id="menu0" name="menuIds" value="${menu.menu.menuId }"/>
							<a href="#" class="easyui-linkbutton" plain="true" icon="icon-clear" onclick='delSpan("${menu.menu.menuId }");'></a>
						</span>
						<c:if test="${(status.index + 1)%4 == 0}"><br></c:if>
					</c:forEach>
					<input type="hidden" id="rl" value="${role.menus.size() }">
					<span id="haveMenus"></span>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>