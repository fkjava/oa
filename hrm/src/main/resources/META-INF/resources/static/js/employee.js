// 设置生日的日期选择框
$('#selectBirthday').datetimepicker({
	locale : 'zh-cn',
	// format: "L"
	format : "YYYY-MM-DD",
	defaultDate : "1970-01-01"
});

var submitForm = function() {
	// 找到所有选中的roles的输入框
	// CSS选择器，找到值是以某些字符开头的
	var roles = $("input[type='checkbox'][name^='roles']:checked");
	// console.log(roles);
	// 把中括号里面的0，替换成0、1、2
	roles.each(function(index, input) {
		// console.log(input);
		// roles[0].id
		var regex = /\d+/;
		var name = $(input).attr("name");
		name = name.replace(regex, index);
		// console.log(name);
		// console.log(regex.test(name));
		$(input).attr("name", name);

		// $(input).attr("name", "roles["+index+"].id");
	});

	// 判断两次输入的密码要一致
	// 还可以扩展：一边输入密码、一边检测密码的强度
	// 使用onkeyup事件来处理
	var checkResult = false;
	if ($("#passwordResult").parent().hasClass("has-success")) {
		checkResult = true;
	}

	if (checkResult && $("#loginNameResult").parent().hasClass("has-success")) {
		checkResult = true;
	} else {
		checkResult = false;
	}

	// console.log($("#checkPasswordResult.alert-success").length);
	if (checkResult
			&& $("#checkPasswordResult").parent().hasClass("has-success")) {
		checkResult = true;
	} else {
		checkResult = false;
	}
	// ... 其他验证

	// 返回false不提交
	return checkResult;
};

var setResultStatus = function(selector, status) {
	// console.log(selector);
	// console.log(status);
	if (status === 1) {
		// 确认密码没有输入，应该是警告
		// $(selector).removeClass("alert-danger");
		// $(selector).removeClass("alert-success");
		// $(selector).addClass("alert-warning");
		$(selector).parent().removeClass("has-success");
		$(selector).parent().removeClass("has-error");
		$(selector).parent().addClass("has-warning");

		$(selector + " .glyphicon").removeClass("glyphicon-ok");
		$(selector + " .glyphicon").removeClass("glyphicon-remove");
		$(selector + " .glyphicon").addClass("glyphicon-warning-sign");
	} else if (status === 2) {
		// $(selector).removeClass("alert-danger");
		// $(selector).removeClass("alert-warning");
		// $(selector).addClass("alert-success");
		$(selector).parent().removeClass("has-warning");
		$(selector).parent().removeClass("has-error");
		$(selector).parent().addClass("has-success");

		$(selector + " .glyphicon").removeClass("glyphicon-warning-sign");
		$(selector + " .glyphicon").removeClass("glyphicon-remove");
		$(selector + " .glyphicon").addClass("glyphicon-ok");
	} else {
		// $(selector).removeClass("alert-success");
		// $(selector).removeClass("alert-warning");
		// $(selector).addClass("alert-danger");
		$(selector).parent().removeClass("has-success");
		$(selector).parent().removeClass("has-warning");
		$(selector).parent().addClass("has-error");

		$(selector + " .glyphicon").removeClass("glyphicon-warning-sign");
		$(selector + " .glyphicon").removeClass("glyphicon-ok");
		$(selector + " .glyphicon").addClass("glyphicon-remove");
	}
}
var passwordOnChange = function() {
	// 获取两个密码框的值
	var password = $("#inputPassword").val();
	if (password.length >= 8) {
		// ok
		setResultStatus("#passwordResult", 2);
	} else if (password.length === 0) {
		// 警告
		setResultStatus("#passwordResult", 1);
	} else {
		// 错误
		setResultStatus("#passwordResult", 3);
	}
};

var checkPasswordOnChange = function() {
	// 获取两个密码框的值
	var password = $("#inputPassword").val();
	var checkPassword = $("#checkPassword").val();
	// console.log(password);
	// console.log(checkPassword);
	// 判断是否一样
	// 如果一样，则打钩；否则打叉
	if (checkPassword.length == 0) {
		// 确认密码没有输入，应该是警告
		setResultStatus("#checkPasswordResult", 1);
	} else if (password === checkPassword) {
		setResultStatus("#checkPasswordResult", 2);
	} else {
		setResultStatus("#checkPasswordResult", 3);
	}
};

// 检查登录名是否被占用
var checkLoginName = function() {
	// id在修改的时候才有
	var id = $("#id").val();
	var loginName = $("#inputLoginName").val();
	if (loginName.length < 3) {
		setResultStatus("#loginNameResult", 1);
		return;
	}

	$.ajax({
		url : contextPath + "/identity/user/checkLoginName/" + loginName + "/" + id,
		method : "get",
		success : function(data, status, xhr) {
			// 返回一个结果，通过结果来判断是否被占用
			setResultStatus("#loginNameResult", data.status);
		},
		error : function(data, status, xhr) {
			alert("检查登录名出现问题：\n" + data.responseJSON.message);
		}
	});
};
// ============================================
var setting = {
	view : {
		selectedMulti : false
	},
	check : {
		enable : true, // 可以选择
		chkStyle : "radio",// 单选框
		radioType : "all" // 都可以选择
	},
	callback : {
		onClick : onClick,
		onCheck : onCheck
	}
};

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var nodes = zTree.getCheckedNodes(true);
	
	var departmentId = "";
	var departmentName = "";
	if (nodes.length > 0) {
		var node = nodes[0];
		departmentId = node.id;
		departmentName = node.name;
	}
	
	$("#selectDepartment").val(departmentName);
	$("#departmentId").val(departmentId);
}

function showMenu() {
	var departmentElement = $("#selectDepartment");
	var departmentElementOffset = $("#selectDepartment").offset();
	$("#menuContent").css({
		left : (departmentElementOffset.left - 220) + "px",
		top : (departmentElementOffset.top - 18) + "px",
		width : departmentElement.outerWidth() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
			|| event.target.id == "menuContent" || $(event.target).parents(
			"#menuContent").length > 0)) {
		hideMenu();
	}
}

$(document).ready(function() {
	$.fn.zTree.init($("#departmentTree"), setting, departments);
});