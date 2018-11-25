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
<link rel="stylesheet" type="text/css" href="<%=path%>/css/main.css">
<%@ include file="/pages/include.jsp"%>
<script type="text/javascript">
var path = '<%=path%>';
var themes = [{value:'default',text:'默认'},
              {value:'metro-blue',text:'青色'},
              {value:'metro-gray',text:'灰色'},
              {value:'metro-green',text:'绿色'},
              {value:'metro-orange',text:'橙色'},
              {value:'metro-red',text:'红色'},
              {value:'ui-cupertino',text:'蓝色'},
              {value:'ui-dark-hive',text:'黑色'},
              {value:'ui-pepper-grinder',text:'沙滩'},
              {value:'ui-sunny',text:'阳光'}];
var userTheme = $.cookie('themeName');
if(userTheme == undefined || userTheme == 'undefined'){
	userTheme = 'default';
}
              
function addTab(title, url){
	if ($('#tabs').tabs('exists', title)){
		$('#tabs').tabs('select', title);//选中并刷新
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			});
		}
	} else {
		$('#tabs').tabs('add',{
			title:title,
			content:createFrame(url),
			closable:true
		});
	}
	
	tabClose();
}

function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%; height:99%;"></iframe>';
	return s;
}
		
function tabClose() {
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}

//绑定右键菜单事件
function tabCloseEven() {
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			})
		}
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t != '首页') {
				$('#tabs').tabs('close',t);
			}
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();		
		if(prevall.length>0){
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		if(nextall.length>0) {
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		return false;
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

function setCookie(name,value) {//两个参数，一个是cookie的名子，一个是值
    var Days = 30; //此 cookie 将被保存 30 天
    var exp = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function getCookie(name) {//取cookies函数        
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return unescape(arr[2]); return null;
}

function dateFormat(date){
	if(date < 10){
		return "0" + date;
	}else{
		return date;
	}
}

/* 修改密码 */
function updatePWD(){
	$('#updatePassword').window('close');
	$.messager.alert('温馨提示','抱歉，修改密码接口在后台已经实现，不过不允许你现在修改！', 'error');
//	$("#pwd_form").form('submit', {
//		url: '<%=path%>/common/updatePwd',
//		success: function(data){
//			var result = $.parseJSON(data);
//			if(result == 'success'){
//				$.messager.alert('温馨提示','密码修改成功！', 'info', function(){
//					$("#pwd_form").form("clear");
//					$('#updatePassword').window('close');
//       		});
//			}
//			if(result == 'error'){
//				$.messager.alert('温馨提示','原始密码错误！', 'error', function(){
//					$("#pwd_form").form("clear");
 //       		});
//			}
//			if(result == 'fail'){
//				$.messager.alert('温馨提示','系统出现错误，请联系管理员！', 'error', function(){
//					$("#pwd_form").form("clear");
//					$('#updatePassword').window('close');
 //       		});
//			}
//		}
//	});
}

/* 加载备忘录 */
function loadMemorandum(){
	loadDictCombobox(['memorandumComplete'], 'memorStatus');
	loadDataGrid();
	var url = '<%=path%>/memorandum/loadMemorandum';
	$.post(url, {}, function(dataArray){
		window.sessionStorage.removeItem("memorandumList");
		window.sessionStorage.setItem("memorandumList", dataArray);
		/* 备忘录 */
		$('#cc').calendar({
		    current: new Date(),
		    formatter: function(date){
				var cDate = date.getFullYear() + "-" + dateFormat((date.getMonth()+1)) + "-" + dateFormat(date.getDate());
		    	var result = date.getDate();
		    	var dom = false;
				$.each(JSON.parse(dataArray), function(index, object){
		    		var sDate = object.memorandumDate;
		    		if(cDate == sDate){
		    			dom = true;
		    		}
		    	});
				if(dom){
					result = '<div class="icon-clock md">' + date.getDate() + '</div>';
				}
		    	return result;
			},
		    onSelect: function(date){
		    	var selectDate = date.getFullYear() + "-" + dateFormat((date.getMonth()+1)) + "-" + dateFormat(date.getDate());
		    	/* var memorandumArray = window.sessionStorage.getItem("memorandumList");
		    	var memorandum = {
	    			"memorandumId" : "",
	    			"memorandumDate" : selectDate,
	    			"memorandumComplete" : "memorStatus0",
	    			"memorandumTitle" : "",
	    			"memorandumContent" : ""
		    	};
		    	$.each(JSON.parse(memorandumArray), function(index, object){
		    		if(selectDate == object.memorandumDate){
		    			memorandum = object;
		    		}
		    	});
	    		$('#memorandumId').val(memorandum.memorandumId);
		    	$('#memorandumDate').datebox('setValue', memorandum.memorandumDate);
		    	$('#memorandumComplete').combobox('setValue', memorandum.memorandumComplete);
		    	$('#memorandumTitle').textbox('setValue', memorandum.memorandumTitle);
		    	$('#memorandumContent').textbox('setValue', memorandum.memorandumContent); */
		    	$('#memorandumDate').datebox('setValue', selectDate);
		    	$('#memorandumComplete').combobox('setValue', 'memorStatus0');
		    	$('#memorandumTitle').textbox('setValue', '');
		    	$('#memorandumContent').textbox('setValue', '');
				openMemorandum();
			}
		});
	});
}

/* 打开备忘录 */
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

/* 保存备忘录 */
function saveMemorandum(){
	$("#memorandum_form").form('submit', {
		url: '<%=path%>/memorandum/saveMemorandum',
		success: function(data){
			if(JSON.parse(data).status == 0){
				$.messager.alert('温馨提示','备忘录保存成功！', 'info', function(){
					$("#memorandum_form").form("clear");
					$('#memorandum').window('close');
					loadMemorandum();
        		});
			}
			if(JSON.parse(data).status == 1){
				$.messager.alert('温馨提示','备忘录保存失败，请联系管理员！', 'error');
			}
		}
	});
}

/* 加载当日待办事项 */
function loadDataGrid(){
	$('#memGrid').datagrid({
	    url: '<%=path%>/memorandum/getTodayMemorandum',
		method : 'POST',
		title : '今日待办事项',
		loadMsg : '正在加载，请稍后...',
		striped : true,
		fitColumns : true,
		autoRowHeight : false,
		singleSelect : true,
		width: 243,
		columns : [[ 
			{field : 'memorandumTitle', title : '内容', align : 'center', width: 243}
		]],
		onDblClickRow: function(rowIndex, rowData){
	    	$('#memorandumDate').datebox('setValue', rowData.memorandumDate);
	    	$('#memorandumComplete').combobox('setValue', rowData.memorandumComplete);
	    	$('#memorandumTitle').textbox('setValue', rowData.memorandumTitle);
	    	$('#memorandumContent').textbox('setValue', rowData.memorandumContent);
			$('#memorandum').dialog({
				title: '待办内容',
				closed: false,
				cache: false,
				modal: true,
				resizable: false,
				draggable: false,
				buttons: null
			});
		}
	});
}

/* 展开/折叠选项卡 */
function openOrClostTabs(option){
	var icon = '';
	var newOption = '';
	if(option == 'collapse'){
		icon = 'icon-arrow_expand';
		newOption = 'expand';
	}
	if(option == 'expand'){
		icon = 'icon-arrow_contract';
		newOption = 'collapse';
	}
	$('#tabs').tabs({
		tools: [{
	        iconCls: icon,
	        handler: function(){
	        	$('#mainLayout').layout(option, 'north');
	    		$('#mainLayout').layout(option, 'west');
	    		$('#mainLayout').layout(option, 'east');
	    		openOrClostTabs(newOption);
	        }
	    }]
	});
}

$(document).ready(function(){
	/* 重写校验方法，添加新规则 */
	$.extend($.fn.validatebox.defaults.rules, {
	    equals: {
	        validator: function(value,param){
	            return value == $(param[0]).val();
	        },
	        message: '两次新密码不一致!'
	    }
	});
	
	/* 加载右键菜单 */
	tabCloseEven();
	
	/* 加载备忘录 */
	loadMemorandum();
	
	/* 展开/折叠选项卡 */
	openOrClostTabs('collapse');
	
	/* 加载系统菜单 */
	$("#systemMenu").tree({
		url: '<%=path%>/common/getSystemMenu',
		method: 'post',
		//queryParams: {"id" : 0},
		animate: true,
		checkbox: false,
		onClick: function(node){
			if(node.id != ''){
				addTab(node.text, '${mvc}'+node.attributes.url);
			}
		}
	});
	
	/* 加载快速链接 */
	$("#webMenu").tree({
		url: '<%=path%>/common/getWebMenu',
		method: 'post',
		//queryParams: {"id" : 0},
		animate: true,
		checkbox: false,
		onClick: function(node){
			if(node.id != ''){
				addTab(node.text, node.attributes.url);
			}
		}
	});
	
	/* 修改密码 */
	$("#updatePass").click(function(){
		$('#updatePassword').dialog({
			title: '修改密码',
			width: 300,
			closed: false,
			cache: false,
			modal: true,
			resizable: false,
			draggable: false,
			buttons: [{
				iconCls: 'icon-save',
				text: '确定',
				handler: function(){
					updatePWD();
				}
			}]			
		});
	});
	
	/* 退出系统 */
	$("#exitSystem").click(function(){
		$.messager.confirm('温馨提示','确定退出系统？', function(y){
			if(y){
				var exitURL = "<%=path%>/common/exitSystem";
				var param = {};
				$.post(exitURL, param, function(data){
					window.location.href = "<%=path%>/pages/login.jsp";
				});
			}else {
				return false;
			}
		});
	});
	
	$('#themes').combobox({
		data: themes,
		valueField: 'value',
		textField: 'text',
		value: userTheme,
		editable: false,
		panelHeight: 'auto',
		width: 75,
		onSelect: function(record){
			var themeName = record.value;
			$('#css_theme').attr('href', '${mvc}/themes/'+themeName+'/easyui.css');
			var iframe = $('iframe');
			if(iframe.length >0){
				for (var i = 0; i< iframe.length; i++) {
					var ifr = iframe[i];
					$(ifr).contents().find('#css_theme').attr('href', '${mvc}/themes/'+themeName+'/easyui.css');
				}
			}
			$.cookie('themeName', themeName, {expires : 30, path : '${mvc}'});
		}
	});
	
	$.messager.show({
        title: '欢迎登陆',
        msg: '欢迎'+$('#trueName').val()+'登陆系统！',
        timeout: 5000,
        showType: 'slide'
    });
	
	/* 折叠右边窗口 */
	$('#mainLayout').layout('collapse', 'east');
	
});
</script>
</head>
<body>
	<div id="mainLayout" class="easyui-layout hidden" fit="true">
		<div region="north" border="true" class="cs-north">
			<form:hidden path="userInfo.trueName" id="trueName" />
			<div style="float: right;">
				<input type="text" id="themes" class="easyui-combobox" /> <a
					id="updatePass" href="javascript:void(0);"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-man',plain:true">修改密码</a> <a
					id="exitSystem" href="javascript:void(0);"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-exit',plain:true">退出系统</a>
			</div>
		</div>
		<div region="west" border="true" split="true" title="菜单栏"
			class="cs-west">
			<div class="easyui-accordion" fit="true" border="false">
				<div title="系统菜单">
					<ul id="systemMenu"></ul>
				</div>
				<div title="快速链接">
					<ul id="webMenu"></ul>
				</div>
			</div>
		</div>
		<div id="mainPanle" region="center" border="true" border="false">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
				<div title="首页"></div>
			</div>
		</div>
		<div region="east" border="true" split="true" title="日历"
			class="cs-east">
			<div>
				<div id="cc" style="float:right; width:250px; height:250px;"></div>
			</div>
			<div>
				<table id="memGrid"></table>
			</div>
		</div>
		<div region="south" border="false" id="south">
			<center>EasyUI&SSM Demo</center>
		</div>

		<div id="updatePassword" class="easyui-dialog"
			data-options="closed: true">
			<form id="pwd_form" method="post">
				<table width="100%;" align="center" border="0" style="padding: 5px;">
					<tr>
						<td align="right">旧密码：</td>
						<td><input type="password" id="oldpwd" name="oldpwd"
							class="easyui-textbox" data-options="required:true"
							missingMessage="旧密码必须填写">
						</td>
					</tr>
					<tr>
						<td align="right">新密码：</td>
						<td><input type="password" id="newpwd" name="newpwd"
							class="easyui-textbox" data-options="required:true"
							missingMessage="新密码必须填写">
						</td>
					</tr>
					<tr>
						<td align="right">确认新密码：</td>
						<td><input type="password" id="newpwd2" name="newpwd2"
							class="easyui-textbox" data-options="required:true"
							missingMessage="确认新密码必须填写" validType="equals['#newpwd']">
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="memorandum" class="easyui-dialog" data-options="closed: true">
			<form id="memorandum_form" method="post">
				<input type="hidden" id="memorandumId" name="memorandumId">
				<table style="margin: 10px;">
					<tr>
						<th align="right"><h4>日期：</h4>
						</th>
						<th align="left"><input type="text" id="memorandumDate"
							name="memorandumDate" class="easyui-datebox"
							data-options="required:true" missingMessage="日期必须填写"
							style="width: 100px"></th>
						<th align="right"><h4>状态：</h4>
						</th>
						<th align="left"><input type="text" id="memorandumComplete"
							name="memorandumComplete" class="easyui-combobox"
							data-options="required:true" missingMessage="状态必须填写"
							style="width: 100px"></th>
					</tr>
					<tr>
						<th align="right"><h4>标题：</h4>
						</th>
						<th align="left" colspan="3"><input type="text"
							id="memorandumTitle" name="memorandumTitle"
							class="easyui-textbox" data-options="required:true"
							missingMessage="标题必须填写" style="width: 300px;"></th>
					</tr>
					<tr>
						<th align="right"><h4>内容：</h4>
						</th>
						<th align="left" colspan="3"><input type="text"
							id="memorandumContent" name="memorandumContent"
							class="easyui-textbox" data-options="multiline:true"
							style="width: 300px; height: 100px;"></th>
					</tr>
				</table>
			</form>
		</div>
		<div id="mm" class="easyui-menu cs-tab-menu">
			<div id="mm-tabupdate">刷新</div>
			<div class="menu-sep"></div>
			<div id="mm-tabclose">关闭</div>
			<div id="mm-tabcloseother">关闭其它</div>
			<div id="mm-tabcloseall">关闭全部</div>
		</div>
	</div>
</body>
</html>
