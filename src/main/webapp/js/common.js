function formatDate(date) {
	var date = new Date(date);
	var year = date.getFullYear();
	var month = ("0" + (date.getMonth() + 1)).slice(-2);
	var day = ("0" + date.getDate()).slice(-2);
	var h = ("0" + date.getHours()).slice(-2);
	var m = ("0" + date.getMinutes()).slice(-2);
	var s = ("0" + date.getSeconds()).slice(-2);
	return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
}

function easyPage(pageNumber, pageSize) {
	$("#currentPage").val(pageNumber);
	$("#pageSize").val(pageSize);
	$("#page").submit();
	waiting();
}

function waiting() {
	$("<div class=\"datagrid-mask\"></div>").css({
		display : "block",
		width : "100%",
		height : $(window).height()
	}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍后...").appendTo("body").css({
		display : "block",
		left : ($(document.body).outerWidth(true) - 190) / 2,
		top : ($(window).height() - 45) / 2,
		"font-size" : "12px"
	});
}

/**
 * 部门选择弹出窗口
 */
function openDeptTree() {
	$('#dialog').dialog({
		title : '选择部门',
		width : 300,
		height : 400,
		toolbar : '#dlg-toolbar',
		closed : true,
		cache : false,
		modal : true
	});
	$("#tree").tree({
		url : '/ssm_template/dept/getDeptTree',
		method : 'post',
		animate : true,
		checkbox : false,
		// lines:true,
		onBeforeExpand : function(node) {// 点击展开按钮事件
			$('#tree').tree('options').url = '/ssm_template/dept/getDeptTree?superId=' + node.id;
		}
	});
	$('#dialog').dialog('open');
}

/**
 * 部门树选择事件
 * 
 * @param deptId
 *            部门Id，参数为文本框id，不想获取该属性，传参时传''
 * @param deptNo
 *            部门编号，参数为文本框id，不想获取该属性，传参时传''
 * @param deptName
 *            部门名称，参数为文本框id，不想获取该属性，传参时传''
 * @param getLeaf
 *            是否只能选择末级节点，是：true，否：false
 */
function selectDept(deptId, deptNo, deptName, getLeaf) {
	var node = $('#tree').tree('getSelected');
	if (node) {
		var id = node.id;
		var name = node.text;
		var dno = node.attributes.deptNo;
		var ifLeaf = node.attributes.ifLeaf;
		if (getLeaf) {
			if (ifLeaf == '1') {
				if (deptId != null && deptId != '') {
					$("#" + deptId).val(id);
				}
				if (deptNo != null && deptNo != '') {
					$("#" + deptNo).val(dno);
				}
				if (deptName != null && deptName != '') {
					$("#" + deptName).val(name);
				}
				$('#dialog').dialog('close');
			} else {
				$.messager.alert('温馨提示', '只能选择叶子节点！', 'info');
			}
		} else {
			if (deptId != null && deptId != '') {
				$("#" + deptId).val(id);
			}
			if (deptNo != null && deptNo != '') {
				$("#" + deptNo).val(dno);
			}
			if (deptName != null && deptName != '') {
				$("#" + deptName).val(name);
			}
			$('#dialog').dialog('close');
		}
	} else {
		$.messager.alert('温馨提示', '请选择部门！', 'info');
	}
}

function closeDialog(domId) {
	$('#' + domId).dialog('close');
}

/**
 * mess 待显示消息码
 */
function showMess(mess) {

	var showMess = "";
	if ("0" == mess) {
		showMess = "操作成功！";
	}
	if ("1" == mess) {
		showMess = "操作失败！";
	}
	$.messager.show({
		title : '提示',
		msg : showMess,
		showType : 'show',
		timeout : 3000
	});
}


/**
 * 加载combobox
 * 
 * @param domId
 * @param dictId
 */
function loadDictCombobox(domIds, dictCode) {
	$.each(domIds, function(index, domId){
		$('#'+domId).combobox({
		    url : path + '/common/loadDictCombobox?dictCode=' + dictCode,
		    method : 'get',
		    valueField : 'dictDetailCode',
		    textField : 'dictDetailName'
		});
	});
}