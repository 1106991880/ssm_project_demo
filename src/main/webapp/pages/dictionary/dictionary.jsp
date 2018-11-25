<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>Memorandum</title>
<%@ include file="/pages/include.jsp"%>
<script type="text/javascript">
var path = '<%=path%>';
function loadDictGrid(params){
	$('#dictGrid').datagrid({
	    url: '<%=path%>/dictionary/queryDictionary',
		queryParams : params,
		method : 'POST',
		title : '字典分类管理',
		loadMsg : '正在加载，请稍后...',
		striped : true,
		fitColumns : true,
		autoRowHeight : false,
		singleSelect : true,
		pagination : true,
		rownumbers : true,
		columns : [[ 
			{field : 'dictName', title : '名称', align : 'center', width : 120},
			{field : 'dictCode', title : '编码', align : 'center', width : 80},
			{field : 'dictStatus', title : '状态', align : 'center', width : 80},
			{field : 'creator', title : '创建人', align : 'center', width : 80},
			{field : 'createTime', title : '创建时间', align : 'center', width : 120,
				formatter : function(value, row, index) {
					if (value != null && value != '') {
						return formatDate(value);
					} else {
						return "";
					}
				}
			},
			{field : 'dictId', title : '编辑', align : 'center', width : 50,
				formatter : function(value, row, index) {
					var dom = '<a href="'+path+'/dictionary/initDictionaryDetail?dictId='+value+'&dictCode='+row.dictCode+'">字典项</a>';
					return dom;
				}
			}
		]],
		toolbar: [{
				iconCls: 'icon-add',
				text: '添加',
				handler: function(){
					openEdit('添加字典类', 'add');
				}
			},{
				iconCls: 'icon-edit',
				text: '修改',
				handler: function(){
					var dict = $('#dictGrid').datagrid('getSelected');
					if(dict != null){
						setDictValues(dict.dictId);
						openEdit('修改字典类', 'edit');
					}else{
						$.messager.alert('温馨提示','请选择一条记录!', 'info');
					}
				}
			},{
				iconCls: 'icon-ok',
				text: '启用',
				handler: function(){
					var dict = $('#dictGrid').datagrid('getSelected');
					if(dict != null){
						var url = '<%=path%>/dictionary/updateDictStatus';
						var params = {
							"dictId" : dict.dictId,
							"dictStatus" : "0"
						};
						$.post(url, params, function(json){
							if($.parseJSON(json).status == 0){
								$.messager.alert('温馨提示','字典分类已启用!', 'info');
								loadDictGrid({});
							}else{
								$.messager.alert('温馨提示','启用失败!', 'info');
							}
						})
					}else{
						$.messager.alert('温馨提示','请选择一条记录!', 'info');
					}
				}
			},{
				iconCls: 'icon-clear',
				text: '禁用',
				handler: function(){
					var dict = $('#dictGrid').datagrid('getSelected');
					if(dict != null){
						var url = '<%=path%>/dictionary/updateDictStatus';
						var params = {
							"dictId" : dict.dictId,
							"dictStatus" : "1"
						};
						$.post(url, params, function(json){
							if($.parseJSON(json).status == 0){
								$.messager.alert('温馨提示','字典分类已被禁用!', 'info');
								loadDictGrid({});
							}else{
								$.messager.alert('温馨提示','禁用失败!', 'info');
							}
						})
					}else{
						$.messager.alert('温馨提示','请选择一条记录!', 'info');
					}
				}
			}
		],
		onDblClickRow: function(rowIndex, rowData){
			window.location.href = path + '/dictionary/initDictionaryDetail?dictId=' + rowData.dictId + '&dictCode=' + rowData.dictCode;
		}
	});
}

function openEdit(title, action){
	if(action == 'add'){
		clearForm();
		$("#dictCode").textbox("readonly", false);
	}else{
		$("#dictCode").textbox("readonly", true);
	}
	$('#dict').dialog({
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
				checkDictCode();
			}
		}]			
	});
}

function checkDictCode(){
	var reg = /^[A-Za-z]+$/;
	if(reg.test($("#dictCode").textbox("getValue"))){
		var url = '<%=path%>/dictionary/getDictByCode';
		var param = {'dictCode' : $("#dictCode").textbox("getValue")};
		$.post(url, param, function(json){
			if(JSON.parse(json).data == 0){
				saveOrUpdate();
			}else{
				$.messager.alert('温馨提示','分类编码重复!', 'info');
			}
		});
	}else{
		$.messager.alert('温馨提示','分类编码只能输入英文字母!', 'info');
	}
}

function saveOrUpdate(){
	$("#dict_form").form('submit', {
		url: '<%=path%>/dictionary/saveOrUpdateDict',
		success: function(data){
			if(JSON.parse(data).status == 0){
				$.messager.alert('温馨提示','字典类保存成功!', 'info', function(){
					$('#dict').window('close');
					clearForm();
					loadDictGrid({});
					//loadDictTree({});
        		});
			}
			if(JSON.parse(data).status == 1){
				$.messager.alert('温馨提示','字典类保存失败，请联系管理员!', 'error');
			}
		}
	});
}

function setDictValues(dictId){
	var url = '<%=path%>/dictionary/getDict';
	var param = {"dictId" : dictId};
	$.get(url, param, function(json){
		var obj = JSON.parse(json).data;
		$("#dictId").val(obj.dictId);
		$("#dictStatus").val(obj.dictStatus);
		$("#dictName").textbox("setValue", obj.dictName);
		$("#dictCode").textbox("setValue", obj.dictCode);
	});
}

function clearForm(){
	$("#dictId").val('');
	$("#dictStatus").val('0');
	$("#dictName").textbox('clear');
	$("#dictCode").textbox('clear');
}

$(document).ready(function() {
	loadDictGrid({});
});
</script>
</head>
<body>
	<table id="dictGrid"></table>
	<div id="dict" class="easyui-dialog" data-options="closed: true" style="padding: 10px;">
		<form id="dict_form" method="post">
			<input type="hidden" id="dictId" name="dictId">
			<input type="hidden" id="dictStatus" name="dictStatus" value="0">
			<table class="table_info">
				<tr>
					<th>分类名称：</th>
					<td>
						<input type="text" id="dictName" name="dictName" class="easyui-textbox" 
							data-options="required:true" missingMessage="分类名称必须填写">
					</td>
				</tr>
				<tr>
					<th>分类编码：</th>
					<td>
						<input type="text" id="dictCode" name="dictCode" class="easyui-textbox" 
							data-options="required:true" missingMessage="分类编码必须填写" >
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>