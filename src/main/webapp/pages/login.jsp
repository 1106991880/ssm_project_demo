<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8" />
<title>CompanyManager</title>
<%@ include file="/pages/include.jsp"%>
<style type="text/css">
body {
	background: url(<%=path%>/images/background.jpg);
	background-repeat:no-repeat;
        background-position:center;
      
}
</style>
<script type="text/javascript">
function login(){
	var options = {
		success: function (data){
        	if($.parseJSON(data).result == 'success'){
        		if($("#remember").is(':checked')){
        			saveUserInfo();
        		}
        		window.location.href = '<%=path%>/common/toMain';
				} else if($.parseJSON(data).result == 'already'){
					$.messager.alert('温馨提示', '目前该账号有人在登录，请检查该账号是否已经被人窃取！', 'info');
				}else {
					$.messager.alert('温馨提示', '用户名或密码错误！', 'info');
				}
			},
			error : function() {
				$.messager.alert('温馨提示', '操作失败！', 'error');
			}
		};
		$('#loginForm').form('submit', options);
	}

	function saveUserInfo() {
		window.localStorage.removeItem("userInfo");
		var userInfo = {
			"userName" : $("#userName").textbox('getValue'),
			"password" : $("#password").textbox('getValue')
		};
		window.localStorage.setItem("userInfo", JSON.stringify(userInfo));
	}

	$(document).ready(function() {
		var cache = window.localStorage.getItem("userInfo");
		var userInfo = JSON.parse(cache);
		if (userInfo != null) {
			$('#userName').textbox('setValue', userInfo.userName);
			$('#password').textbox('setValue', userInfo.password);
			$("#remember").attr('checked', 'checked');
		}
		$(window).keydown(function(event) {
			if (event.keyCode == 13) {
				login();
			}
		});
	});
</script>
</head>
<body>
	<div id="dialog_login" style="float: right; margin: 100px;">
		<form id="loginForm" action="<%=path%>/common/login" method="POST">
			<table style="padding:30px">
				<tr>
					<td align="center">
						<div style="margin-bottom:10px">
							<input type="text" class="easyui-textbox" id="userName"
								name="userName" style="width:200px;height:40px;padding:12px"
								data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38,required:true,validType:'length[6,20]'"
								missingMessage="用户名必须填写" invalidMessage="用户名长度应介于6和20之间"
								onkeyup="value=value.replace(/[^\w\/]/ig,'')">
						</div>
						<div style="padding: 10px;"></div>
						<div style="margin-bottom:20px">
							<input type="password" class="easyui-textbox" id="password"
								name="password" style="width:200px;height:40px;padding:12px"
								data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38,required:true"
								missingMessage="密码不能为空">
						</div>
						<div style="margin-bottom:20px;">
							<input type="checkbox" id="remember"
								style="height: 15px;width: 15px;"> <span
								style="font-size: 20px;">记住我</span>
						</div>
						<div>
							<a href="#" class="easyui-linkbutton"
								data-options="iconCls:'icon-ok'"
								style="padding:5px;width:200px;" onclick="login();"> <span
								style="font-size:14px;">登录</span> </a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>

