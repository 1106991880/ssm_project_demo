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
function loadDictDetailGrid(params){
	$('#dictDetailGrid').datagrid({
	    url: '<%=path%>/dictionary/getDictDetailByDictId',
		queryParams : params,
		method : 'POST',
		title : '字典明细管理',
		loadMsg : '正在加载，请稍后...',
		striped : true,
		fitColumns : true,
		autoRowHeight : false,
		singleSelect : true,
		pagination : true,
		rownumbers : true,
		columns : [[ 
			{field : 'dictDetailName', title : '名称', align : 'center', width : 120},
			{field : 'dictDetailCode', title : '编码', align : 'center', width : 80},
			{field : 'dictDetailStatus', title : '状态', align : 'center', width : 80},
			{field : 'createTime', title : '创建时间', align : 'center', width : 120,
				formatter : function(value, row, index) {
					if (value != null && value != '') {
						return formatDate(value);
					} else {
						return "";
					}
				}
			},
			{field : 'creator', title : '创建人', align : 'center', width : 80},
			{field : 'dictDetailId', title : '操作', align : 'center', hidden : 'ture'}
		]],
		toolbar: [{
				iconCls: 'icon-back',
				text: '返回',
				handler: function(){
					window.location.href = "<%=path%>/dictionary/initDictionary";
				}
			},{
				iconCls: 'icon-add',
				text: '添加',
				handler: function(){
					openEdit('添加字典明细', 'add');
				}
			},{
				iconCls: 'icon-edit',
				text: '修改',
				handler: function(){
					var dict = $('#dictDetailGrid').datagrid('getSelected');
					if(dict != null){
						setDictDetailValues(dict.dictDetailId);
						openEdit('修改字典明细', 'edit');
					}else{
						$.messager.alert('温馨提示','请选择一条记录!', 'info');
					}
				}
			},{
				iconCls: 'icon-ok',
				text: '启用',
				handler: function(){
					var dict = $('#dictDetailGrid').datagrid('getSelected');
					if(dict != null){
						var url = '<%=path%>/dictionary/updateDictDetailStatus';
						var params = {
							"dictDetailId" : dict.dictDetailId,
							"dictDetailStatus" : "0"
						};
						$.post(url, params, function(json){
							if(JSON.parse(json).status == 0){
								$.messager.alert('温馨提示','字典项已启用!', 'info');
								loadDictDetailGrid({"dictId" : $("#dictId").val()});
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
					var dict = $('#dictDetailGrid').datagrid('getSelected');
					if(dict != null){
						var url = '<%=path%>/dictionary/updateDictDetailStatus';
						var params = {
							"dictDetailId" : dict.dictDetailId,
							"dictDetailStatus" : "1"
						};
						$.post(url, params, function(json){
							if(JSON.parse(json).status == 0){
								$.messager.alert('温馨提示','字典项已被禁用!', 'info');
								loadDictDetailGrid({"dictId" : $("#dictId").val()});
							}else{
								$.messager.alert('温馨提示','禁用失败!', 'info');
							}
						})
					}else{
						$.messager.alert('温馨提示','请选择一条记录!', 'info');
					}
				}
			}
		]
	});
}

function openEdit(title, action){
	if(action == 'add'){
		clearForm();
	}
	$('#dictDetail').dialog({
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
				saveOrUpdate();
			}
		}]			
	});
}

function saveOrUpdate(){
	$("#dictDetail_form").form('submit', {
		url: '<%=path%>/dictionary/saveOrUpdateDictDetail',
		success: function(data){
			if(JSON.parse(data).status == 0){
				$.messager.alert('温馨提示','字典明细保存成功!', 'info', function(){
					$('#dictDetail').window('close');
					clearForm();
					loadDictDetailGrid({"dictId" : $("#dictId").val()});
        		});
			}
			if(JSON.parse(data).status == 1){
				$.messager.alert('温馨提示','字典明细保存失败，请联系管理员!', 'error');
			}
		}
	});
}

function setDictDetailValues(dictDetailId){
	var url = '<%=path%>/dictionary/getDictDetail';
	var param = {"dictDetailId" : dictDetailId};
	$.get(url, param, function(json){
		var obj = JSON.parse(json).data;
		$("#dictDetailId").val(obj.dictDetailId);
		$("#dictDetailStatus").val(obj.dictDetailStatus);
		$("#dictDetailName").textbox("setValue", obj.dictDetailName);
	});
}

function clearForm(){
	$("#dictDetailId").val('');
	$("#dictDetailStatus").val('0');
	$("#dictDetailName").textbox('clear');
}

$(document).ready(function() {
	loadDictDetailGrid({"dictId" : $("#dictId").val()});
});
</script>
</head>
<body>
	<table id="dictDetailGrid"></table>
	<div id="dictDetail" class="easyui-dialog" data-options="closed: true" style="padding: 10px;">
		<form id="dictDetail_form" method="post">
			<input type="hidden" id="dictId" name="dictId" value="${dictId }">
			<input type="hidden" id="dictCode" name="dictCode" value="${dictCode }">
			<input type="hidden" id="dictDetailId" name="dictDetailId">
			<input type="hidden" id="dictDetailStatus" name="dictDetailStatus" value="0">
			<table class="table_info">
				<tr>
					<th>明细名称：</th>
					<td>
						<input type="text" id="dictDetailName" name="dictDetailName" class="easyui-textbox" 
							data-options="required:true" missingMessage="明细名称必须填写">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>