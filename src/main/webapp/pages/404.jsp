<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8"/>
<title>404</title>
<script type="text/javascript" src="<%=path %>/js/jquery-1.9.1.min.js"></script>
<style type="text/css">
.error {
	background: url('<%=path %>/images/404.png') no-repeat;
	width: 490px;
	margin-top: 75px;
	padding-top: 65px;
}

.error h2 {
	font-size: 22px;
	padding-left: 154px;
}

.error p {
	padding-left: 154px;
	line-height: 35px;
	color: #717678;
}

.reindex {
	padding-left: 154px;
}

.reindex a {
	width: 115px;
	height: 35px;
	font-size: 14px;
	font-weight: bold;
	color: #fff;
	background: #3c95c8;
	display: block;
	line-height: 35px;
	text-align: center;
	border-radius: 3px;
	behavior: url(js/pie.htc);
	margin-top: 20px;
}
</style>
<script language="javascript">
	$(function() {
		$('.error').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 490) / 2
		});
		$(window).resize(function() {
			$('.error').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 490) / 2
			});
		})
	});
</script>
</head>
<body style="background: #edf6fa;">
	<div class="error">
		<h2>非常遗憾，您访问的页面不存在！</h2>
		<p>看到这个提示，就自认倒霉吧!</p>
		<div class="reindex">
			<!-- <a href="main.html" target="_parent">返回首页</a> -->
		</div>
	</div>
</body>
</html>