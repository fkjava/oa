$(function() {
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
				+ "' title='添加子部门' onfocus='this.blur();'></span>";

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
					name : "新的部门"
				});

				// 增加以后，还需要把新增的节点选中
				zTree.selectNode(newNodes[0]);

				return false;
			});
		}
	}
	;
	/**
	 * 删除自定义按钮
	 */
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	}
	;

	var showRemoveButton = function(treeId, treeNode) {
		if (treeNode.children && treeNode.children.length > 0) {
			return false;
		} else {
			return true;
		}
	};

	// 在表单里面显示部门的详细信息
	var showDetailInForm = function(treeId, treeNode) {
		$("#departmentForm #id").val(treeNode.id);
		$("#departmentForm #inputName").val(treeNode.name);
		// 设置select元素的值
		if (treeNode.owner) {
			$("#departmentForm #selectOwner").val(treeNode.owner.id);
		}else{
			$("#departmentForm #selectOwner").val("");
		}
		var parentNode = treeNode.getParentNode();
		if (parentNode) {
			$("#departmentForm #parentId").val(treeNode.getParentNode().id);
			$("#departmentForm #parentName").text(treeNode.getParentNode().name);
		} else {
			$("#departmentForm #parentId").val("");
			$("#departmentForm #parentName").text("");
		}
	};

	// 删除部门
	var removeDepartment = function(treeId, treeNode) {
		// 1.发送AJAX请求给服务器，删除菜单节点
		$.ajax({
			url: "./department/" + treeNode.id,
			method: "delete",
			success: function(data, status, xhr){
				// 2.当返回成功的时候，把节点从页面中删除，并且重置表单
				// 删除失败，则应该显示一个对话框提示一下
				if( data.status == 2 )
				{
					// 删除
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.removeNode(treeNode);
					
					// 重置表单
					resetForm();
				}

				alert(data.message);
			},
			error: function(data, status, xhr){
				if( data.responseJSON ){
					alert(data.responseJSON.message);
				}else{
					alert(data);
				}
			}
		});
		return false;
	};
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
			beforeRemove : removeDepartment,
			onSelected : showDetailInForm
		}
	};

	// 初始化的代码
	$.fn.zTree.init($("#departmentTree"), setting, departments);
});

// 在提交表单之前
var beforeSubmit = function() {

};

// 重置表单
var resetForm = function() {

};