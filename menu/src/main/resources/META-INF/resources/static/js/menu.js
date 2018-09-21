/**
 * 添加自定义按钮的函数
 */
var addedNode = false;
function addHoverDom(treeId, treeNode) {
	// 找到鼠标所在位置的节点
	var sObj = $("#" + treeNode.tId + "_span");

	// 判断节点上面是否有添加按钮，如果有则不要添加重复的按钮
	if (addedNode || treeNode.editNameFlag
			|| $("#addBtn_" + treeNode.tId).length > 0) {
		return;
	}
	// 自定义按钮的HTML代码，注意id属性的值，要跟删除的时候保持一致
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加菜单' onfocus='this.blur();'></span>";

	// 把自定义按钮，添加到鼠标所在位置的节点
	sObj.after(addStr);

	// 找到刚刚添加的按钮
	var btn = $("#addBtn_" + treeNode.tId);

	// 给按钮绑定事件
	if (btn) {
		// 当点击的时候，增加一个子节点给树！
		btn.bind("click", function() {
			// 标记已经添加了一个节点
			addedNode = true;
			// 删除添加按钮
			removeHoverDom(treeId, treeNode);

			// 获取整棵树
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			// 调用树的方法，添加节点
			// 第1个参数表示目标节点
			// 第2个参数表示要添加的节点的内容，包括 id、pId、name
			var newNodes = zTree.addNodes(treeNode, {
				name : "新的菜单"
			});

			// 增加以后，还需要把新增的节点选中
			zTree.selectNode(newNodes[0]);

			return false;
		});
	}
};
/**
 * 删除自定义按钮
 */
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};

// var showRemoveButton = function()
// 使用var声明的函数，必须要执行到声明变量的地方，才会有Function对象。
// 使用function直接声明的函数，会在最开始解析的时候，就有Function对象。
// function showRemoveButton(treeId, treeNode){
var showRemoveButton = function(treeId, treeNode) {
	// console.log(treeNode);
	// 如果没有下级节点，显示删除按钮，否则不显示！
	if (treeNode.children && treeNode.children.length > 0) {
		return false;
	} else {
		return true;
	}
};

// 当选中某个节点的时候，在表单里面显示详情
var showDetailInForm = function(treeId, treeNode) {
	// 判断是否有id，没有id的时候，需要把id置为空
	// 如果不处理，那么点击已经存在的节点，然后再来新增的时候，会把新增作为修改。
	if (treeNode.id) {
		$("#menuForm #id").val(treeNode.id);
	} else {
		$("#menuForm #id").val("");
	}
	$("#menuForm #inputName").val(treeNode.name);
	$("#menuForm input[name='url']").val(treeNode.url);

	// 先把所有选中的请求方法去掉，如果是复选框就一定要进行处理
	// 单选框因为只会选中一个，所以不去掉也没有问题
	// $("#menuForm input[name='method']").prop("checked", false);
	// 然后再根据节点的请求方法选中特定的单选框
	$("#menuForm input[name='method'][value='" + treeNode.method + "']").prop(
			"checked", true);

	// 处理选中的角色
	// 首先要把所有原本选中的放到【未选中】的框里面
	// click函数，如果有参数表示绑定事件；没有参数则表示触发事件，相当于写代码点击按钮
	$(".remove-all").click();

	// 根据根据节点的roles把右侧角色选中、移动到左侧
	if (treeNode.roles) {
		for (var i = 0; i < treeNode.roles.length; i++) {
			var role = treeNode.roles[i];
			var input = $(".unselect-role-list input[value='" + role.id + "']");
			input.prop("checked", true);
		}
	}
	$(".add-selected").click();

	var parentNode = treeNode.getParentNode();
	if (parentNode) {
		$("#menuForm #parentId").val(treeNode.getParentNode().id);
		$("#menuForm #parentName").text(treeNode.getParentNode().name);
	} else {
		$("#menuForm #parentId").val("");
		$("#menuForm #parentName").text("");
	}
};

// 删除菜单，无论如何都返回false
// 通过AJAX删除服务器的数据成功以后，再手动删除页面的菜单节点，并重置表单
var removeMenu = function(treeId, treeNode) {

};

// ------------------------------------------------------------------

// 模拟的数据
// var nodes = [
// {name: "父节点1",
// url: "父节点的URL",
// open: true,// 默认打开
// children: [
// {name: "子节点1", url: "子节点1的URL"},
// {name: "子节点2"}
// ]
// }
// ];
// 直接把JSON字符串当做代码使用
// var nodes = ${json};

// 对树型结构的设置
var setting = {
	view : {
		// 当鼠标放到节点上面的时候，显示自定义按钮
		addHoverDom : addHoverDom,
		// 当鼠标离开节点的时候，删除自定义按钮
		removeHoverDom : removeHoverDom,
		// 不能多选
		selectedMulti : false
	},
	edit : {
		// 可以编辑
		enable : true,
		// 编辑的时候选择全部内容
		editNameSelectAll : true,
		// 显示删除按钮，可以是boolean，也可以是函数
		showRemoveBtn : showRemoveButton,
		// 显示重命名按钮（修改）
		showRenameBtn : false
	},
	data : {
		simpleData : {
			// 是否为简单数据，JSON为false
			// 简单数据是通过pId来决定上下级的
			enable : false
		}
	},
	// 所有回调都是函数，如果不需要则设置为false
	callback : {
		// 在拖动节点之前执行的回调
		beforeDrag : false,
		// 在编辑名称之前的回调
		beforeEditName : false,
		// 在删除的时候执行的回调
		beforeRemove : removeMenu,
		// 在重命名的时候执行的回调
		beforeRename : false,
		// 当节点删除以后的回调
		onRemove : false,
		// 当节点重命名以后的回调
		onRename : false,
		onSelected : showDetailInForm
	},
	async : {
		enable : true,
		url : contextPath + "/system/menu/data",
		// 必须要加上get才可以使用，因为安全框架中解决了CSRF问题
		// 如果AJAX是POST的话，必须要加上一个请求头，否则禁止访问
		type : "get"
	// autoParam:["id", "name=n", "level=lv"],
	// otherParam:{"otherParam":"zTreeAsyncTest"},
	// dataFilter: filter
	}
};
// $(function(){
// });
// $(document).ready(function(){
$(function() {
	// 初始化树形结构
	// $.fn.zTree.init($("#menuTree"), setting, nodes);
	$.fn.zTree.init($("#menuTree"), setting);

	var addSelected = function() {
		$(".unselect-role-list label input:checked").each(
				function(index, input) {
					// console.log(index);
					// console.log(input);
					// $(input).parent().appendTo($(".selected-role-list"));
					// this表示each函数处理的当前对象
					$(this).parent().appendTo($(".selected-role-list"))
					// 取消选中
					$(this).prop("checked", false);
				});
	};

	// 绑定角色选取按钮的事件
	$(".add-selected").click(addSelected);
	$(".add-all").click(function() {
		// 把右边（未选中）全部label找出来，添加到左边（已选中）。
		// 加入左边以后，右边的会自动删除掉（相当于是移动）。
		$(".unselect-role-list label").appendTo($(".selected-role-list"));
		$(".selected-role-list input").prop("checked", false);
	});

	var removeSelected = function() {
		$(".selected-role-list label input:checked").each(
				function(index, input) {
					$(this).parent().appendTo($(".unselect-role-list"));
					// 取消选中
					$(this).prop("checked", false);
				});
	};
	$(".remove-selected").click(removeSelected);
	$(".remove-all").click(function() {
		$(".selected-role-list label").appendTo($(".unselect-role-list"));
		$(".unselect-role-list input").prop("checked", false);
	});
});

// 在form元素的onsubmit属性中调用
var beforeSubmit = function() {
	try {
		// 找到所有已选择的角色
		var roles = $(".selected-role-list input");
		roles.each(function(index, input) {
			// 1.勾选已选择的角色
			$(this).prop("checked", true);
			// 2.设置已选中的角色的name属性
			$(this).attr("name", "roles[" + index + "].id");

			console.log($(this).attr("checked"));
		});
		return true;
	} catch (e) {
		console.log(e);
		return false;
	}
};

// 重置表单
var resetForm = function() {
	$("#menuForm .remove-all").click();

	$("#menuForm #id").val("");
	$("#menuForm #parentId").val("");
	$("#menuForm #parentName").text("");
	$("#menuForm #inputName").val("");
	$("#menuForm #inputUrl").val("");
	$("#menuForm input[name='method']").prop("checked", false);

	// 取消树里面选中的节点
	var treeObj = $.fn.zTree.getZTreeObj("menuTree");
	treeObj.cancelSelectedNode();
};
