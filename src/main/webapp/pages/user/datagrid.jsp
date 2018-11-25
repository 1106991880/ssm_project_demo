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
	$('#userList').datagrid({
        striped : true,//设置为true将交替显示行背景
        singleSelect: true,//为true时只能选择单行
        remoteSort : false,
        fitColumns: true,
        loadMsg : '数据加载中,请稍后......',
        pagination : true,//分页
        rownumbers : true,//行数 
		method :'POST',
		url :'<%=path%>/user/queryForJson',
		onLoadSuccess:function(data){
			
		},
		columns:[[
		  	//{field:'userId', width:$(this).width() * 0.36, align:'center', title:'用户id'},
		  	{field:'userName', width:$(this).width() * 0.2, align:'center', title:'用户名'},
		  	{field:'trueName', width:$(this).width() * 0.2, align:'center', title:'姓名'},
		  	{field:'mail', width:$(this).width() * 0.3, align:'center', title:'邮箱'},
		  	{field:'phone', width:$(this).width() * 0.2, align:'center', title:'电话'},
		  	{field:'createTime', width:$(this).width() * 0.2, align:'center', title:'创建时间',
		  		formatter:function(value,row){
		  			if(value != null && value != ''){
		  				return formatDate(value);
		  			}else{
		  				return "";
		  			}
			    }	
		  	},
		  	{field:'updateTime', width:$(this).width() * 0.2, align:'center', title:'修改时间',
		  		formatter:function(value,row){
		  			if(value != null && value != ''){
		  				return formatDate(value);
		  			}else{
		  				return "";
		  			}
			    }		
		  	},
		  	{field:'userLevel', width:$(this).width() * 0.1, align:'center', title:'级别'}
		]]
	});
});
</script>
</head>
<body style="background: #FFFFFF">
	<table id="userList" title="用户列表"></table>
</body>
</html>