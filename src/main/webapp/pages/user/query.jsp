<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>query</title>
<%@ include file="/pages/include.jsp"%>
<style scoped="scoped">
.textbox{
    height:20px;
    margin:0;
    padding:0 2px;
    box-sizing:content-box;
}
</style>
<script type="text/javascript">
function add(){
	var form = $("#form2");
	form.attr("action", "<%=path%>/user/addUser");
	form.submit();
	waiting();
}

function update(){
	var ids = [];
	var user_level = []; //获取被选中的对象的身份
	$('input[name="cb"]:checked').each(function(){
		ids.push($(this).val().split("\+")[0]);
		user_level.push($(this).val().split("\+")[1]);
	});
	if(ids.length != 1){
		$.messager.alert("操作提示", "请选择一条记录！", "info");
		return false;
	}else{
		$("#userId").val(ids);
	}
	//判断自己的身份
	var own_userName = '${own_user.userName}';
	//system身份的超级管理员具备所有功能
	if(own_userName!='system')
	{
		if(user_level=='系统管理员')
		{
			$.messager.alert("操作提示", "你不是超级管理员，没有修改系统管理员的权限！", "warning");
			return false;
		}
	}
	var form = $("#form2");
	form.attr("action", "<%=path%>/user/editUser");
	form.submit();
	waiting();
}

function del(){
	//判断自己的身份
	var own_userName = '${own_user.userName}';
	var flag = false;
	var ids = [];
	var user_level = []; //获取被选中的对象的身份
	$('input[name="cb"]:checked').each(function(){
		var id = $(this).val().split("\+")[0];
		if($('#'+id).html() == 'system'){
			flag = true;
		}
		ids.push(id);
		user_level.push($(this).val().split("\+")[1]);
	});
	if(ids.length == 0){
		$.messager.alert("操作提示", "请选择记录！", "info");
		return;
	}else if(flag){
		$.messager.alert("操作提示", "system不允许删除！", "warning");
		return;
	}else{
		if(own_userName!='system'&&user_level=='系统管理员')
		{
			$.messager.alert("操作提示", "你不是超级管理员，没有删除系统管理员的权限！", "warning");
			return;
		}
		$("#userId").val(ids);
	}
	$.messager.confirm('温馨提示','是否删除所选记录？', function(y){
		if(y){
			var form = $("#form2");
			form.attr("action", "<%=path%>/user/deleteUser");
			form.submit();
			waiting();
		}else {
			return false;
		}
	});
}

function allotRole(){
	var ids = [];
	$('input[name="cb"]:checked').each(function(){
		ids.push($(this).val());
	});
	if(ids.length != 1){
		$.messager.alert("操作提示", "请选择一条记录！", "info");
		return;
	}else{
		$.ajax({
    		type: 'GET',// 默认为get
    		url: '<%=path %>/user/allotUserRole',
    		data: {userId: ids[0]},
    		dataType: 'json',
    		global: false,// 默认为true,是否触发全局ajax事件
    		cache: false,// 默认为true（当dataType为script时，默认为false），设置为false将不会从浏览器缓存中加载请求信息。
    		async: true,// 默认为true,是否是异步请求
    		success: function(data){
    			var user = data.userInfo;
    			$('#userId_role').val(user.userId);
    			$('#userName_role').html(user.userName);
    			$('#trueName_role').html(user.trueName);
    			$('#mail_role').html(user.mail);
    			if(user.userLevel == 0){
    				$('#userLevel_role').html('系统管理员');
    			}else{
    				$('#userLevel_role').html('普通用户');
    			}
    			var html_r = [];
    			if(user.userName == 'system'){
    				$.messager.alert("操作提示", "system是超级管理员，拥有所有权限.", "info");
    			}else{
    				$.each(data.roles, function(index, role){
        				if(role['userHave'] == 1){
        					html_r.push('<input type="checkbox" name="roleIds" value="'+role['roleId']+'" checked="checked"/>'+role['roleName']);
               				html_r.push('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
        				}else{
        					html_r.push('<input type="checkbox" name="roleIds" value="'+role['roleId']+'"/>'+role['roleName']);
               				html_r.push('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
        				}
        				if((index+1) % 4 == 0){
        					html_r.push('<br>');
        	        	}
        			});
    				$('#allotRole').dialog({
       		    		title: '权限分配',
       		    		modal: true,
       		    		buttons: '#allotButton',
        		    });
	    			$('#roles').html(html_r);
	    			$('#allotRole').dialog('open');
    			}
         	},
         	error:function(){
    			$.messager.alert('温馨提示','操作失败！', 'error');
    		}
        });
	}
}

function getAllRole() {
	var flag = $("#getAllRole").prop('checked');
	$("input[name='roleIds']").each(function(){
		this.checked = flag;
	});
}

function saveUserRole(){
	var options = {
		success: function (data){
			var result = $.parseJSON(data).result;
	      	if(result == 1){
	      		$.messager.alert('温馨提示','权限分配成功！', 'info');
	      		$('#allotRole').dialog('close');
	       	}else{
	       		$.messager.alert('温馨提示','权限分配失败！', 'info');
	       	}
		},
		error:function(){
			$.messager.alert('温馨提示','操作失败！', 'error');
		}
	};
	$('#allorForm').form('submit', options);
}

function selectAll() {
	var flag = $("#cbFlag").prop('checked');
	//$("input[name='cb']").attr("checked",true);
	//$("input[name='cb']").removeAttr("checked");
	$("input[name='cb']").each(function(){
		this.checked = flag;
	});
}

function queryData(){
	document.forms[0].submit();
	waiting();
}

function returnQuery(){
	var form = document.forms[3];
	form.action = "<%=path%>/user/queryUser";
	form.submit();
	waiting();
}
</script>
</head>
<body style="background: #FFFFFF">
	<form method="post" action="<%=path%>/user/queryUser">
	<fieldset class="fieldset-self">
	<legend>查询用户信息</legend>
	<table width="100%" align="center">
		<tr>
			<td align="right">姓名：</td>
			<td align="left"><form:input path="user.trueName" id="trueName" cssClass="easyui-textbox"/></td>
			<td align="right">邮件：</td>
			<td align="left"><form:input path="user.mail" id="mail" cssClass="easyui-textbox"/></td>
			<td align="right">部门：</td>
			<td align="left">
				<!-- 这个地方如果不使用validatebox，选择部门后，部门名称无法填充input中去，应该算是EasyUI的bug -->
				<form:input path="dept.deptName" id="deptName" cssClass="easyui-validatebox textbox" readonly="true"/>
				<form:hidden path="dept.deptNo" id="deptNo" />
				<a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-folder_open'" onclick="openDeptTree();"></a>
			</td>
			<td align="right">级别：</td>
			<td align="left">
				<!-- 
				<form:select path="user.userLevel" items="${map }" id="userLevel"
					name="userLevel">
				</form:select>
				--> 
				<form:select path="user.userLevel" id="userLevel" 
					cssClass="easyui-combobox" cssStyle="width:150px;">
					<form:option value="" label="" />
					<form:options items="${userLevelMap }" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td colspan="8" align="right">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onclick="queryData();">查询</a>&nbsp;
				<a href="#" class="easyui-linkbutton" icon="icon-reload" onclick="returnQuery();">重置</a>&nbsp;
			</td>
		</tr>
	</table>
	</fieldset>
	</form>
	<form id="form2" method="post">
		<input type="hidden" id="userId" name="userId" value="">
	</form>
	<table>
		<tr><td>
	    	<a href="#" class="easyui-linkbutton" icon="icon-add" onclick="add();">添加</a>&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-edit" onclick="update();">修改</a>&nbsp;
			<a href="#" class="easyui-linkbutton" icon="icon-remove" onclick="del();">删除</a>&nbsp;
			<%--<c:if test="${userLevel == '0'}">
				<a href="#" class="easyui-linkbutton" icon="icon-man" onclick="allotRole();">分配权限</a>&nbsp;
			</c:if>
		--%></td></tr>
	</table>
	<table class="table" width="100%" align="center">
	  <thead>
	  	<tr>
	      <th><input type="checkbox" id="cbFlag" name="cbFlag" onclick="selectAll();"></th>
	      <th>序号</th>
	      <th>用户名</th>
	      <th>真实姓名</th>
	      <th>电子邮件</th>
	      <th>电话</th>
	      <th>所在部门</th>
	      <th>所在部门编号</th>
	      <th>注册时间</th>
	      <th>级别</th>
	      <th>附件</th>
	    </tr>
	  </thead>
	  <tbody>
	  	<c:forEach items="${list }" var="user" begin="0" varStatus="status">
			<tr onmouseover="this.bgColor='#EAF2FF'" onmouseout="this.bgColor='#FFFFFF'" align="center">
				<td><input type="checkbox" id="cb" name="cb" value="${user.userId }+${user.userLevel }"></td>
				<td>${status.index + 1}</td>
				<td><span id="${user.userId }">${user.userName }</span></td>
				<td>${user.trueName }</td>
				<td>${user.mail }</td>
				<td>${user.phone }</td>
				<td>${user.dept.deptName }</td>
				<td>${user.dept.deptNo }</td>
				<td><fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${user.userLevel }</td>
				<td><a href="
					<c:url value="/user/downloadFile">
					<c:param name="userId" value="${user.userId }"/>
					</c:url>
					">${user.fileName }</a>
				</td>
			</tr>
		</c:forEach>
	  </tbody>
	  <tfoot>
	      <td colspan="11">
	      	<form id="page" action="<%=path%>/user/queryUser" method="post">
		        <div class="easyui-pagination"
			        data-options="
			        	total : ${pageInfo.totalRecords },
			        	pageSize: ${pageInfo.pageSize},
			        	pageNumber: ${pageInfo.currentPage},
			        	layout: ['list','sep','first','prev','links','next','last','sep','refresh'],
			        	onSelectPage: function(pageNumber, pageSize){   
			                easyPage(pageNumber, pageSize);
			            },
			            buttons: [{
				            iconCls:'icon-add',
				            handler:function(){
				                add();
				            }
				        },{
				            iconCls:'icon-edit',
				            handler:function(){
				               update();
				            }
				        },{
				            iconCls:'icon-remove',
				            handler:function(){
				               del();
				            }
				        }]
			        ">
			    </div>
				<form:hidden path="pageInfo.currentPage" id="currentPage"/>
				<form:hidden path="pageInfo.pageSize" id="pageSize"/>
				<form:hidden path="user.userName"/>
				<form:hidden path="user.mail"/>
				<form:hidden path="user.phone"/>
				<form:hidden path="user.userLevel"/>
				<form:hidden path="dept.deptNo"/>
				<form:hidden path="dept.deptName"/>
			</form>
	      </td>
	  </tfoot>
	</table>
	<form></form>
	<div id="dialog">
		<div id="tree"></div>
		<div id="dlg-toolbar" style="display: none;">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-ok',plain:true"
				onclick="selectDept('','deptNo','deptName',false);">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel',plain:true"
				onclick="closeDialog('dialog');">关闭</a>
		</div>
	</div>
	<div id="allotRole" class="easyui-dialog" data-options="closed: true, cache: false, width: 450, top:100">
		<form id="allorForm" action="<%=path %>/user/saveUserRole" method="post">
		<table class="table" align="center" width="100%">
			<tr>
				<td align="right">用户名：</td>
				<td align="left"><span id="userName_role"></span></td>
				<td align="right">姓名：</td>
				<td align="left"><span id="trueName_role"></span></td>
			</tr>
			<tr>
				<td align="right">邮箱：</td>
				<td align="left"><span id="mail_role"></span></td>
				<td align="right">级别：</td>
				<td align="left"><span id="userLevel_role"></span></td>
			</tr>
			<tr>
				<td align="left" colspan="4">
					<input type="hidden" id="userId_role" name="userId_role"/>
					<div style="padding: 5px 20px 5px 20px"><span id="roles"></span></div>
				</td>
			</tr>
		</table>
		</form>
		<div id="allotButton">
			全选：<input type="checkbox" id="getAllRole" onclick="getAllRole();"/>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveUserRole();">保存</a>
		</div>
	</div>
</body>
</html>
