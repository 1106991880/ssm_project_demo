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
function loadDataGrid(params){
	$('#MemGrid').datagrid({
	    url: '<%=path%>/memorandum/queryMemorandum',
		queryParams : params,
		method : 'POST',
		title : '备忘录管理',
		loadMsg : '正在加载，请稍后...',
		striped : true,
		fitColumns : true,
		autoRowHeight : false,
		singleSelect : true,
		pagination : true,
		rownumbers : true,
		columns : [[ 
			{field : 'memorandumDate', title : '日期', align : 'center', width : 80},
			{field : 'memorandumTitle', title : '标题', align : 'center', width : 180},
			{field : 'memorandumContent', title : '内容', align : 'center', width : 300},
			{field : 'memorandumComplete', title : '状态', align : 'center', width : 50},
			{field : 'createUser', title : '创建人', align : 'center', width : 80},
			{field : 'createTime', title : '创建时间', align : 'center', width : 120,
				formatter : function(value, row, index) {
					if (value != null && value != '') {
						return formatDate(value);
					} else {
						return "";
					}
				}
			},
			{field : 'memorandumId', title : '操作', align : 'center', width : 50,
				formatter : function(value, row, index) {
					var dom = '<a href="javascript:void(0);" onClick="optionAction(1, \''+value+'\');"><img src="<%=path%>/themes/icons/pencil.png"/></a>';
					dom += '&nbsp;&nbsp;';
					dom += '<a href="javascript:void(0);" onClick="optionAction(2, \''+value+'\');"><img src="<%=path%>/themes/icons/cancel.png"/></a>';
					return dom;
				}
			}
		]],
		onDblClickRow: function(rowIndex, rowData){
			optionAction(1, rowData.memorandumId);
		}
	});
}
//使用jq的ajax会自动进行Json转化为对象，所以可以直接取出
function optionAction(type, id){
	var reqURL = '';
	var params = {'memorandumId' : id};
	if(type == 1){
		reqURL = '<%=path%>/memorandum/getMemorandum';
    	$.post(reqURL, params, function(data){
    		var object = $.parseJSON(data).memorandum;
    		$('#memorandumId').val(object.memorandumId);
	    	$('#memorandumDate').datebox('setValue', object.memorandumDate);
	    	$('#memorandumComplete').combobox('setValue', object.memorandumComplete);
	    	$('#memorandumTitle').textbox('setValue', object.memorandumTitle);
	    	$('#memorandumContent').textbox('setValue', object.memorandumContent);
	    	openMemorandum();
    	});
	}
	if(type == 2){
		$.messager.confirm('温馨提示', '确认要删除这条记录？', function(r){
		    if(r){
				reqURL = '<%=path%>/memorandum/deleteMemorandum';
		    	$.post(reqURL, params, function(data){
		    		$.messager.alert('温馨提示', '记录删除成功！', 'info', function(){
	    				loadDataGrid({});
	    			});
		    	});
		    }
		});
	}
}

function openMemorandum(){
	$('#memorandum').dialog({
		title: '待办内容',
		closed: false,
		cache: false,
		modal: true,
		resizable: false,
		draggable: false,
		buttons: [{
			iconCls: 'icon-save',
			text: '保存',
			handler: function(){
				saveMemorandum();
			}
		}]			
	});
}
//submit提交的方式没有有进行json的转换为对象，所以必须先转化好后才可以取出
function saveMemorandum(){
	$("#memorandum_form").form('submit', {
		url: '<%=path%>/memorandum/saveMemorandum',
		success: function(data){
			if(JSON.parse(data).status == 0){
				$.messager.alert('温馨提示','备忘录保存成功！', 'info', function(){
					$("#memorandum_form").form("clear");
					$('#memorandum').window('close');
					loadDataGrid({});
        		});
			}
			if(JSON.parse(data).status == 1){
				$.messager.alert('温馨提示','备忘录保存失败，请联系管理员！', 'error');
			}
		}
	});
}
	
$(document).ready(function() {
	loadDataGrid({});
	
	var domIds = ['memorandumCompleteCon', 'memorandumComplete'];
	loadDictCombobox(domIds, 'memorStatus');
	
	$("#search").click(function(){
		var params = {};
		params.memorandumDate = $('#memorandumDateCon').datebox('getValue');
		params.memorandumComplete = $('#memorandumCompleteCon').combobox('getValue');
		loadDataGrid(params);
	});

	$("#reload").click(function(){
		$('#memorandumDateCon').datebox('setValue', '');
		$('#memorandumCompleteCon').combobox('setValue', '');
		loadDataGrid({});
	});
	$("#add").click(function(){
		openMemorandum();
	});
});
</script>
</head>
<body>
	<fieldset class="fieldset-self">
		<legend>查询条件</legend>
		<table class="table_info">
			<tr>
				<th>日期：</th>
				<td>
					<input type="text" id="memorandumDateCon" name="memorandumDateCon" class="easyui-datebox">
				</td>
				<th>状态：</th>
				<td>
					<input type="text" id="memorandumCompleteCon" name="memorandumCompleteCon" class="easyui-combobox">
				</td>
				<td>
					<a id="search" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					&nbsp;&nbsp;
					<a id="reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a>
					&nbsp;&nbsp;
					<a id="add" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
					
				</td>
			</tr>
		</table>
	</fieldset>
	<table id="MemGrid"></table>
	<div id="memorandum" class="easyui-dialog" data-options="closed: true">
		<form id="memorandum_form" method="post">
			<input type="hidden" id="memorandumId" name="memorandumId">
			<table style="margin: 10px;">
				<tr>
					<th align="right"><h4>日期：</h4></th>
					<th align="left">
						<input type="text" id="memorandumDate" name="memorandumDate" class="easyui-datebox" 
							data-options="required:true" missingMessage="日期必须填写" style="width: 100px">
					</th>
					<th align="right"><h4>状态：</h4></th>
					<th align="left">
						<input type="text" id="memorandumComplete" name="memorandumComplete" class="easyui-combobox"
							data-options="required:true" missingMessage="状态必须填写" style="width: 100px">
					</th>
				</tr> 
				<tr>
					<th align="right"><h4>标题：</h4></th>
					<th align="left" colspan="3">
						<input type="text" id="memorandumTitle" name="memorandumTitle" class="easyui-textbox" data-options="required:true" 
							missingMessage="标题必须填写" style="width: 300px;">
					</th>
				</tr>
				<tr>
					<th align="right"><h4>内容：</h4></th>
					<th align="left" colspan="3">
						<input type="text" id="memorandumContent" name="memorandumContent" class="easyui-textbox" data-options="multiline:true" style="width: 300px; height: 100px;">
					</th>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>