<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%String path = request.getContextPath();%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title></title>
<%@ include file="/pages/include.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$('#roleList').datagrid({
        striped : true,//设置为true将交替显示行背景
        singleSelect: true,//为true时只能选择单行
        remoteSort : false,
        fitColumns: true,
        loadMsg : '数据加载中,请稍后...',
        pagination : true,//分页
        rownumbers : true,//行数 
        toolbar : '#toolbar',
		method :'POST',
		url : '<%=path%>/role/queryRoles',
		onLoadSuccess: function(data){
			
		},
		columns:[[
		  	{field:'roleId', checkbox:'true', align:'center'},
		  	{field:'roleName', width:$(this).width() * 0.2, align:'center', title:'角色名称 '},
		  	{field:'roleDesc', width:$(this).width() * 0.4, align:'center', title:'角色描述'},
		  	{field:'createTime', width:$(this).width() * 0.3, align:'center', title:'创建时间',
		  		formatter:function(value,row){
		  			if(value != null && value != ''){
		  				return formatDate(value);
		  			}else{
		  				return "";
		  			}
			    }
		  	},
		  	{field:'creator', width:$(this).width() * 0.1, align:'center', title:'创建人'}
		]]
	});
});

function formatDate(date){
	var date = new Date(date);
	var year = date.getFullYear();
	var month = ("0" + (date.getMonth() + 1)).slice(-2);
	var day = ("0" + date.getDate()).slice(-2);
	var h = ("0" + date.getHours()).slice(-2);
	var m = ("0" + date.getMinutes()).slice(-2);
	var s = ("0" + date.getSeconds()).slice(-2);
	return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
}

function loadMenu(){
	$('#selectMenu').dialog('open');
	$("#menuList").datagrid({
		url :'<%=path %>/menu/getAllMenus',
		method :'POST',
        loadMsg : '数据加载中......',
		singleSelect : false,
		striped : true,
		fitColumns : true,
		autoRowHeight : false,
		pagination : true,
		rownumbers : true,
		onLoadSuccess:function(data){
			
		},
		columns:[[
			{field:'menuId', checkbox:'true', align:'center'},
		  	{field:'menuName', align:'center', width : 50, title:'菜单名称'},
		  	{field:'menuDesc', align:'center', width : 150, title:'菜单描述'}
		]]
	});
}
var selected;
function getSelections(){
    var menuName = [];
    var info = [];
    var rows = $('#menuList').datagrid('getSelections');
    selected = rows.length;
    if(rows.length == 0){
    	$.messager.alert('温馨提示', '没有选择任何菜单!', 'error');
    	return;
    }
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        var flag = checkRepeat(row.menuId, row.menuName);
        if(flag){
        	menuName.push('<span id="'+row.menuId+'">'+row.menuName+
                    '<input type="hidden" id="menu'+i+'" name="menuIds" value="'+row.menuId+'"/>'+
                    '<a href="#" onclick=\'delSpan("'+row.menuId+'");\'>'+
                    '<img src="<%=path %>/themes/icons/clear.png"/></a></span>');
        	menuName.push('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
        	if((i+1) % 4 == 0){
        		menuName.push('<br>');
        	}
        }else{
        	info.push(row.menuName+'，已经添加，不能重复添加！');
        }
    }
    if(info == ""){
       	var targetObj = $("#haveMenus").append(menuName);
       	$.parser.parse(targetObj);
       	$('#selectMenu').dialog('close');
    }else{
    	$.messager.alert('温馨提示', info.join('<br/>'), 'error');
    }
}

function checkRepeat(id, name){
	var flag = true;
	$("input[name='menuIds']").each(function(){
		if($(this).val() == id){
			flag = false;
		}
	});
	return flag;
}

function doSearch(value){
	$('#menuList').datagrid('load',{
		menuName : value
		}
	);
}

function doSearch2(){
	$('#menuList').datagrid('load',{
		menuName : $("#menuName").val()
		}
	);
}

function delSpan(spanId){
	//$.messager.alert('info', spanId);
	$('#'+spanId).remove();
}

function addRole(){
	$('#addRole').dialog({
		title: '添加角色',
		modal: true,
		buttons: '#saveButton',
		href: '<%=path %>/role/toAddRole'
	});
	$('#addRole').dialog('open');
}

function saveRole(){
	var options = {
		success: function (data){
			var result = $.parseJSON(data).result;
	      	if(result == 1){
	      		$.messager.alert('温馨提示','角色添加成功！', 'info', function(){
	      			window.location.reload(true);
	       		});
	       	}else{
	       		$.messager.alert('温馨提示','角色添加失败！', 'info');
	       	}
		},
		error:function(){
			$.messager.alert('温馨提示','操作失败！', 'error');
		}
	};
	var flag = 0;
	for(var i = 0; i < selected; i++){
		var value = $('#menu'+i).length;
		if(value > 0){
			flag = 1;
			break;
		}
	}
	if(flag == 1){
		$('#addForm').form('submit', options);
	}else{
		$.messager.alert('温馨提示','请选择菜单！', 'info');
	}
}

function editRole(){
	var row = $('#roleList').datagrid('getSelected');
    if(row){
    	var link = '<%=path %>/role/toEditRole?roleId='+row.roleId;
        $('#editRole').dialog({
    		title: '编辑角色',
    		modal: true,
    		buttons: '#editButton',
    		href: link
    	});
    	$('#editRole').dialog('open');
    }else{
    	$.messager.alert('温馨提示','请选择一条记录！', 'info');
    	return;
    }
}

function updateRole(){
	var options = {
		success: function (data){
			var result = $.parseJSON(data).result;
	      	if(result == 1){
	      		$.messager.alert('温馨提示','角色更新成功！', 'info', function(){
	      			window.location.reload(true);
	       		});
	       	}else{
	       		$.messager.alert('温馨提示','角色更新失败！', 'info');
	       	}
		},
		error:function(){
			$.messager.alert('温馨提示','操作失败！', 'error');
		}
	};
	var flag = 0;
	for(var i = 0; i < $('#rl').val(); i++){
		var value = $('#menu'+i).length;
		if(value > 0){
			flag = 1;
			break;
		}
	}
	if(flag == 1){
		$('#editForm').form('submit', options);
	}else{
		$.messager.alert('温馨提示','请选择菜单！', 'info');
	}
}

function deleteRole(){
	var row = $('#roleList').datagrid('getSelected');
    if(row){
    	$.messager.confirm('温馨提示','是否删除所选记录？', function(y){
    		if(y){
    			$.ajax({
    	    		type: 'POST',// 默认为get
    	    		url: '<%=path %>/role/deleteRole',
    	    		data: {roleId: row.roleId},
    	    		dataType: 'json',
    	    		global: false,// 默认为true,是否触发全局ajax事件
    	    		cache: false,// 默认为true（当dataType为script时，默认为false），设置为false将不会从浏览器缓存中加载请求信息。
    	    		async: true,// 默认为true,是否是异步请求
    	    		success: function(data){
    	    			var result = data.result;
    	    	      	if(result == 1){
    	    	      		$.messager.alert('温馨提示','角色删除成功！', 'info', function(){
    	    	      			window.location.reload(true);
    	    	       		});
    	    	       	}else{
    	    	       		$.messager.alert('温馨提示','角色删除失败！', 'info');
    	    	       	}
    	         	},
    	         	error:function(){
    	    			$.messager.alert('温馨提示','操作失败！', 'error');
    	    		}
    	        });
    		}else {
    			return;
    		}
    	});
    }else{
    	$.messager.alert('温馨提示','请选择一条记录！', 'info');
    	return;
    }
}
</script>
</head>
<body style="background: #FFFFFF">
	<table id="roleList" title="角色列表"></table>
	<div id="toolbar">
   		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRole();">添加</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRole();">修改</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRole();">删除</a>
	</div>
	<div id="addRole" class="easyui-dialog" data-options="closed: true, cache: false, width: 400, top:100">
		<div id="saveButton">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveRole();">保存</a>
		</div>
	</div>
	<div id="editRole" class="easyui-dialog" data-options="closed: true, cache: false, width: 400, top:100">
		<div id="editButton">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="updateRole();">保存</a>
		</div>
	</div>
	<div id="selectMenu" class="easyui-dialog"
		data-options="title: '菜单选择', closed: true, toolbar : '#menu_tb', cache: false, modal: true, width: 500, top:100">
    	<div id="menu_tb">
    		<table width="100%" border="0">
    			<tr>
    				<td align="left">
    					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="getSelections();">选择</a>
    				</td>
    				<td align="center">
    					<input class="easyui-searchbox" data-options="prompt:'菜单名称',searcher:doSearch" style="width:150px"/>
    				</td>
    				<td align="center">
    					<input type="text" id="menuName" name="menuName" class="easyui-textbox" style="width: 150px;"/>
	    				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch2();">查询</a>
    				</td>
    			</tr>
    		</table>
		</div> 
		<table id="menuList" title="菜单选择"></table>
	</div>
</body>
</html>