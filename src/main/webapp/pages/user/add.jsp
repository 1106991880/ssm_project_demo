<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>add</title>
<%@ include file="/pages/include.jsp"%>
<style scoped="scoped">
.textbox {
	height: 20px;
	margin: 0;
	padding: 0 2px;
	box-sizing: content-box;
}
</style>
<script type="text/javascript">
	
	function save(){
        var validate = $("#form1").form('enableValidation').form('validate');
        if(validate){
        	$.ajax({
				async: false,
		        cache: false,
		        type: "POST",
		        url: "<%=path%>/user/checkUserName",
		        dataType: "json",
		        data: "userName="+$("#userName").val(),
		        global: false,
		        success: function (data){
		        	var count = data.count;
		        	if(count == 0){
		        		$("#form1").submit();
		        		waiting();
		        	}else{
		        		$.messager.alert('温馨提示','用户名重复，请选择其它用户名！', 'info');
		        	}
		        },
				error:function(){
					$.messager.alert('温馨提示','系统异常，请联系管理员！', 'info');
				}
		    });
        }else{
        	return;
        }
	}

	function returnQuery(){
		var form = document.forms[1];
		form.action = "<%=path%>/user/queryUser";
		form.submit();
		waiting();
	}
</script>
</head>
<body style="background: #FFFFFF">
	<form id="form1" name="form1" method="post"
		action="<%=path%>/user/saveUser" enctype="multipart/form-data">
		<fieldset class="fieldset-self">
			<legend>添加用户信息</legend>
			<table width="100%">
				<tr>
					<td align="right">用户名：</td>
					<td align="left">
						<form:input path="user.userName"
							id="userName" cssClass="easyui-textbox"
							data-options="required:true,validType:'length[6,20]'"
							missingMessage="用户名必须填写" invalidMessage="用户名长度应介于6和20之间"
							onkeyup="value=value.replace(/[^\w\/]/ig,'')" />
					</td>
					<td align="right">电子邮件：</td>
					<td align="left">
						<form:input path="user.mail" id="mail"
							cssClass="easyui-textbox" missingMessage="电子邮件必须填写"
							data-options="required:true,validType:'email'" />
					</td>
					<td align="right">电话号码：</td>
					<td align="left">
						<form:input path="user.phone" id="phone" cssClass="easyui-textbox"
							data-options="required:false,validType:'mobile'"
							invalidMessage="手机号码格式不正确" />
					</td>
					<td align="right">用户级别：</td>
					<td align="left">
						<!-- 
						<form:select path="user.userLevel" items="${map }" id="userLevel"
							name="userLevel">
						</form:select>
						--> 
						<form:select path="user.userLevel" id="userLevel"
							cssClass="easyui-combobox" cssStyle="width:150px;"
							data-options="required:true,editable:false" missingMessage="级别必须填写">
							<form:options items="${userLevelMap }" />
						</form:select>
					</td>
				</tr>
				<tr>
					<td align="right">姓名：</td>
					<td align="left">
						<form:input path="user.trueName" id="trueName" cssClass="easyui-textbox"
							data-options="required:true" missingMessage="姓名必须填写" />
					</td>
					<td align="right">所在部门：</td>
					<td align="left">
						<input id="deptName" class="easyui-validatebox textbox" readonly="readonly" 
						data-options="required:true" missingMessage="所在部门必须填写"/>
						<form:hidden path="user.deptId" id="deptId" />
						<a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="plain:true,iconCls:'icon-folder_open'"
							onclick="openDeptTree();"></a>
					</td>
					<td align="right">附件：</td>
					<td align="left" colspan="3">
						<input class="easyui-filebox" name="fileData"
							data-options="prompt:'选择文件',buttonText:'选择文件'" style="width:100%"/>
					</td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save" onclick="save();">保存</a>&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="returnQuery();">取消</a>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form></form>
	<div id="dialog">
		<div id="tree"></div>
		<div id="dlg-toolbar" style="display: none;">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-ok',plain:true"
				onclick="selectDept('deptId','','deptName',true);">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel',plain:true"
				onclick="closeDialog('dialog');">关闭</a>
		</div>
	</div>
</body>
</html>
