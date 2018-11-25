<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="mvc" value="${pageContext.request.contextPath}" />

<link id="css_theme" rel="stylesheet" type="text/css" href="${mvc}/themes/<c:out value="${cookie.themeName.value}" default="default"/>/easyui.css" />
<link rel="stylesheet" type="text/css" href="${mvc}/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${mvc}/css/public.css" />
<link rel="stylesheet" type="text/css" href="${mvc}/css/table.css" />
<script type="text/javascript" src="${mvc}/js/jquery.min.js"></script>
<script type="text/javascript" src="${mvc}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${mvc}/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${mvc}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${mvc}/js/common.js"></script>
<script type="text/javascript">
	//全局的AJAX访问，处理AJAX清求时SESSION超时
	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		complete : function(XMLHttpRequest, textStatus) {
			//通过XMLHttpRequest取得响应头，sessionstatus           
			var sessionstatus = XMLHttpRequest
					.getResponseHeader("sessionstatus");
			if (sessionstatus == "timeout") {
				//跳转的登录页面
				window.location.replace('${mvc}/pages/login.jsp');
			}
		}
	});
</script>