<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>菜单管理</title>
<%@ include file="/pages/include.jsp"%>
<style type="text/css">
.md {
	background-position: 2px center;
}
</style>
<script type="text/javascript">
var path = '<%=path%>';
function loadMenuGrid(params){
	$('#menuGrid').datagrid({
	    url: '<%=path%>/menu/queryMenus',
		queryParams: params,
		method: 'POST',
		title: '菜单查询',
		loadMsg: '正在加载，请稍后...',
		striped: true,
		fitColumns: true,
		autoRowHeight: false,
		singleSelect: true,
		pagination: true,
		rownumbers: true,
		columns: [[
			{field: 'menuId', hidden: true},
			{field: 'menuName', title: '菜单名称', align: 'center', width: 100,
				formatter:	function(value, row, index) {
					return '<div class="'+row.menuIcon+' md">'+value+'</div>';
				}
			},
			{field: 'menuUri', title: '菜单地址', align: 'center', width: 200},
			/* {field: 'menuDesc', title: '菜单描述', align: 'center', width: 200}, */
			{field: 'menuType', title: '菜单类型', align: 'center', width: 50,
				formatter : function(value, row, index) {
					if (value == 'system') {
						return '系统菜单';
					} else {
						return '网站链接';
					}
				}
			},
			{field: 'createName', title: '创建人', align: 'center', width: 50},
			{field: 'createTime', title : '创建时间', align : 'center', width : 100,
				formatter: function(value, row, index) {
					if (value != null && value != '') {
						return formatDate(value);
					} else {
						return "";
					}
				}
			}
		]],
		toolbar: [{
				iconCls: 'icon-add',
				text: '添加',
				handler: function(){
					$("#menu_form").form("clear");
					openEdit('添加菜单', 'add');
				}
			},{
				iconCls: 'icon-edit',
				text: '修改',
				handler: function(){
					var menu = $('#menuGrid').datagrid('getSelected');
					if(menu != null){
						setMenuValues(menu);
						openEdit('编辑菜单', 'edit');
					}else{
						$.messager.alert('温馨提示','请选择一条记录!', 'info');
					}
				}
			},{
				iconCls: 'icon-remove',
				text: '删除',
				handler: function(){
					var menu = $('#menuGrid').datagrid('getSelected');
					if(menu != null){
						deleteMenu(menu.menuId);
					}else{
						$.messager.alert('温馨提示','请选择一条记录!', 'info');
					}
				}
			}
		]
	});
}

function openEdit(title, action){
	$('#menuDialog').dialog({
		title: title,
		closed: false,
		cache: false,
		modal: true,
		resizable: false,
		draggable: false,
		buttons: [{
			iconCls: 'icon-save',
			text: '保存',
			handler: function(){
				saveMenuInfo();
			}
		}]			
	});
}

function saveMenuInfo(){
	$("#menu_form").form('submit', {
		url: '<%=path%>/menu/saveOrUpdateMenu',
		success: function(data){
			if(JSON.parse(data).status == 0){
				$.messager.alert('温馨提示','菜单保存成功！', 'info', function(){
					$("#menu_form").form("clear");
					$('#menuDialog').dialog('close');
					loadMenuGrid({});
        		});
			}
			if(JSON.parse(data).status == 1){
				$.messager.alert('温馨提示','菜单保存失败，请联系管理员！', 'error');
			}
		}
	});
}

function deleteMenu(menuId){
	$.messager.confirm('温馨提示', '确认要删除这条记录？', function(r){
	    if(r){
	    	var url = '<%=path%>/menu/deleteMenuById';
			var params = {"menuId": menuId};
			$.post(url, params, function(json){
				if(JSON.parse(json).status == 0){
					$.messager.alert('温馨提示','菜单删除成功!', 'info');
					loadMenuGrid({});
				}else{
					$.messager.alert('温馨提示','菜单删除失败!', 'info');
				}
			});
	    }
	});
}

function setMenuValues(menu){
	$('#menuId').val(menu.menuId);
	$('#menuName').textbox('setValue', menu.menuName);
	$('#menuType').combobox('setValue', menu.menuType);
	$('#menuIcon').combobox('setValue', menu.menuIcon);
	$('#menuUri').textbox('setValue', menu.menuUri);
	$('#menuDesc').textbox('setValue', menu.menuDesc);
}

function loadIcons(){
	var iconNames = ['man', 'book', 'note', 'money', 'organisation', 'menu', 'role', 'home', 'globe', 'exit', 'cabin', 'download', 'forward', 'backward', 'notice', 'clock', 'feed'];
	var icons = [];
	$.each(iconNames, function(index, name){
		var icon = {};
		icon.value = 'icon-'+name;
		icon.text = name;
		icons.push(icon);
	});
	$('#menuIcon').combobox({
		data: icons,
		valueField: 'value',
		textField: 'text',
		formatter: function(row){
			return '<div class="'+row.value+' md"><span style="margin-left: 25px;">'+row.text+'</span></div>';
		}
	});
}

function queryData(value, name){
	var param = {};
	param.menuName = value;
	loadMenuGrid(param);
}

$(document).ready(function() {
	loadMenuGrid({});
	loadIcons();
});
</script>
</head>
<body>
	<fieldset class="fieldset-self">
		<legend>查询菜单信息</legend>
		<table>
			<tr>
				<td align="right">菜单名称：</td>
				<td align="left"><input type="text" id="menuNameCon" class="easyui-searchbox" data-options="searcher:queryData"/></td>
			</tr>
		</table>
	</fieldset>
	<table id="menuGrid"></table>
	<div id="menuDialog" class="easyui-dialog" data-options="closed: true" style="padding: 10px;">
		<form id="menu_form" method="POST">
			<input type="hidden" id="menuId" name="menuId">
			<table class="table_info">
				<tr>
					<th>菜单名称：</th>
					<td>
						<input type="text" id="menuName" name="menuName" class="easyui-textbox" 
							data-options="required:true" missingMessage="菜单名称必须填写">
					</td>
					<th>菜单类型：</th>
					<td>
						<select id="menuType" name="menuType" class="easyui-combobox" style="width: 150px;"
							data-options="required:true" missingMessage="菜单类型必须填写" >
							<option value="system">系统菜单</option>
							<option value="web">网站链接</option>  
						</select>
					</td>
					<th>菜单图标：</th>
					<td>
						<input type="text" id="menuIcon" name="menuIcon" style="width: 150px;">
					</td>
				</tr>
				<tr>
					<th>菜单地址：</th>
					<td colspan="5">
						<input type="text" id="menuUri" name="menuUri" class="easyui-textbox" style="width: 650px;"
							data-options="required:true" missingMessage="菜单地址必须填写">
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<b>菜单描述：</b><br>
						<input type="text" id="menuDesc" name="menuDesc" class="easyui-textbox" data-options="multiline:true" style="width: 730px; height: 100px;">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>