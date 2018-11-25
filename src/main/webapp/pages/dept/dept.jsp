<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title></title>
<%@ include file="/pages/include.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#tree").tree({
		url: '<%=path%>/dept/getDeptTree',
		method: 'post',
		animate: true,
		checkbox: false,
		//lines:true,
		onBeforeExpand: function(node){//点击展开按钮事件
			$('#tree').tree('options').url = '<%=path%>/dept/getDeptTree?superId='+node.id;
        }, 
		onClick: function(node){//点击节点事件
			//$('#tree').tree('options').url = '<%=path%>/dept/getDeptTree?superId='+node.id;
			//alert(node.state);
            //$(this).tree('toggle', node.target);//点击节点展开子节点
            $("#detail").panel({
            	title: '查看部门信息',
            	href: '<%=path%>/dept/getDeptByDeptId?deptId='+node.id
            });
        },
        onContextMenu: function(e,node){//右键点击事件
            e.preventDefault();
            $(this).tree('select',node.target);
            var ifLeaf = node.attributes.ifLeaf;
            if(node.id == 'd10000'){
            	$('#mmRoot').menu('show',{
                    left: e.pageX,
                    top: e.pageY
                });
            }else{
            	if(ifLeaf == '0'){
               	 $('#mm0').menu('show',{
                        left: e.pageX,
                        top: e.pageY
                    });
               }else if(ifLeaf == '1'){
               	$('#mm1').menu('show',{
                       left: e.pageX,
                       top: e.pageY
                   });
               }
            }
        }
	});
})
function add(){
	 var t = $('#tree');
     var node = t.tree('getSelected');
     $("#detail").panel({
    	 title: '添加部门信息',
    	 href: '<%=path%>/dept/addDept?superId='+node.id
	});
}
function edit(){
	 var t = $('#tree');
     var node = t.tree('getSelected');
     $("#detail").panel({
    	 title: '编辑部门信息',
    	 href: '<%=path%>/dept/editDept?deptId='+node.id
	});
}
function del() {
	var t = $('#tree');
	var node = t.tree('getSelected');
	$.messager.confirm('温馨提示', '确定删除此部门?', function(y){
		if(y){
			$.ajax({
		        url: '<%=path%>/dept/deleteDept',
				data : 'deptId=' + node.id,
				type : 'POST',
				dataType : 'json',
				timeout : 5000,
				async : false,
				cache : false,
				global : false,
				success : function(data) {
					if (data.result == '1') {
						$.messager.alert('温馨提示', '部门信息删除成功！', 'info', function(){
							window.location.reload();
						});
					} else if (data.result == '2') {
						$.messager.alert('温馨提示', '无法删除，请先删除下级部门！', 'warning');
					} else {
						$.messager.alert('温馨提示', '部门信息删除失败！', 'error');
					}
				},
				error : function() {
					$.messager.alert('温馨提示', '操作失败！', 'error');
				}
			});
		}
	});
}
function expand() {
	var node = $('#tree').tree('getSelected');
	$('#tree').tree('expand', node.target);
}
function collapse() {
	var node = $('#tree').tree('getSelected');
	$('#tree').tree('collapse', node.target);
}
</script>
</head>
<body>
	<div class="easyui-layout" fit="true" style="margin: 5px;">
		<div id="dept" data-options="region:'west',collapsible:false"
			title="组织机构树" style="width: 25%; padding: 5px">
			<ul id="tree" class="easyui-tree"></ul>
		</div>
		<div id="detail" data-options="region:'center'" title=""
			style="width: 100%; padding: 5px"></div>
	</div>
	<div id="mmRoot" class="easyui-menu" style="width: 120px;">
		<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
	</div>
	<div id="mm0" class="easyui-menu" style="width: 120px;">
		<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
		<div class="menu-sep"></div>
		<div onclick="expand()">展开</div>
		<div onclick="collapse()">关闭</div>
	</div>
	<div id="mm1" class="easyui-menu" style="width: 120px;">
		<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="del()" data-options="iconCls:'icon-remove'">删除</div>
	</div>
</body>
</html>