<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>菜单配置</title>
<link href="${ctx }/webjars/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>

<style type="text/css">
.menu-list
{
	height: 300px;
	border-right: 1px solid #000000;
	display: inline-block;
}

.ztree li span.button.add {
	margin-left:2px; 
	margin-right: -1px; 
	background-position:-144px 0; 
	vertical-align:top; 
	*vertical-align:middle
}

.role-list
{
	height: 140px;
	border: 1px solid #000;
	overflow: auto;
	padding-left: 5px;
	padding-top: 5px;
}
.role-list label
{
	display: block;
}
.buttons
{
	padding-top: 20px;
	padding-left: 7px;
}
.buttons .btn
{
	width: 99px;
}
</style>
<script type="text/javascript">

/**
 * 添加自定义按钮的函数
 */
var addedNode = false;
function addHoverDom(treeId, treeNode) {
	// 找到鼠标所在位置的节点
	var sObj = $("#" + treeNode.tId + "_span");
	
	// 判断节点上面是否有添加按钮，如果有则不要添加重复的按钮
	if (addedNode || treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0){
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
	if (btn){
		// 当点击的时候，增加一个子节点给树！
		btn.bind("click", function(){
			// 标记已经添加了一个节点
			addedNode = true;
			// 删除添加按钮
			removeHoverDom(treeId, treeNode);

			// 获取整棵树
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			// 调用树的方法，添加节点
			// 第1个参数表示目标节点
			// 第2个参数表示要添加的节点的内容，包括 id、pId、name
			var newNodes = zTree.addNodes(treeNode, {name:"新的菜单"} );
			
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
//function showRemoveButton(treeId, treeNode){
var showRemoveButton = function(treeId, treeNode){
	//console.log(treeNode);
	// 如果没有下级节点，显示删除按钮，否则不显示！
	if(treeNode.children){
		return false;
	}else{
		return true;
	}
};

var showDetailInForm = function(treeId, treeNode){
	$("#menuForm #id").val(treeNode.id);
	$("#menuForm #inputName").val(treeNode.name);
	$("#menuForm input[name='url']").val(treeNode.url);
	var parentNode = treeNode.getParentNode();
	if(parentNode){
		$("#menuForm #parentId").val(treeNode.getParentNode().id);
		$("#menuForm #parentName").text(treeNode.getParentNode().name);
	}else{
		$("#menuForm #parentId").val("");
		$("#menuForm #parentName").text("");
	}
};

//------------------------------------------------------------------

//模拟的数据
var nodes = [
	{name: "父节点1", 
	 	url: "父节点的URL",
		open: true,// 默认打开
		children: [
			{name: "子节点1", url: "子节点1的URL"},
			{name: "子节点2"}
		]
	}
];

//对树型结构的设置
var setting = {
	view: {
		// 当鼠标放到节点上面的时候，显示自定义按钮
		addHoverDom: addHoverDom,
		// 当鼠标离开节点的时候，删除自定义按钮
		removeHoverDom: removeHoverDom,
		// 不能多选
		selectedMulti: false
	},
	edit: {
		// 可以编辑
		enable: true,
		// 编辑的时候选择全部内容
		editNameSelectAll: true,
		// 显示删除按钮，可以是boolean，也可以是函数
		showRemoveBtn: showRemoveButton,
		// 显示重命名按钮（修改）
		showRenameBtn: false
	},
	data: {
		simpleData: {
			// 是否为简单数据，JSON为false
			// 简单数据是通过pId来决定上下级的
			enable: false
		}
	},
	// 所有回调都是函数，如果不需要则设置为false
	callback: {
		// 在拖动节点之前执行的回调
		beforeDrag: false,
		// 在编辑名称之前的回调
		beforeEditName: false,
		// 在删除的时候执行的回调
		beforeRemove: false,
		// 在重命名的时候执行的回调
		beforeRename: false,
		// 当节点删除以后的回调
		onRemove: false,
		// 当节点重命名以后的回调
		onRename: false,
		onSelected: showDetailInForm
	}
};
// $(function(){
// });
//$(document).ready(function(){
$(function(){
	// 初始化树形结构
	$.fn.zTree.init($("#menuTree"), setting, nodes);
	
	// 绑定角色选取按钮的事件
	$(".add-selected").click(function(){
		$(".unselect-role-list label input:checked").each(function(index, input){
// 			console.log(index);
// 			console.log(input);
// 			$(input).parent().appendTo($(".selected-role-list"));
			// this表示each函数处理的当前对象
			$(this).parent().appendTo($(".selected-role-list"))
			// 取消选中
			$(this).attr("checked", false);
		});
	});
	$(".add-all").click(function(){
		// 把右边（未选中）全部label找出来，添加到左边（已选中）。
		// 加入左边以后，右边的会自动删除掉（相当于是移动）。
		$(".unselect-role-list label").appendTo($(".selected-role-list"));
	});
	
	$(".remove-selected").click(function(){
		$(".selected-role-list label input:checked").each(function(index, input){
			$(this).parent().appendTo($(".unselect-role-list"))
			// 取消选中
			$(this).attr("checked", false);
		});
	});
	$(".remove-all").click(function(){
		$(".selected-role-list label").appendTo($(".unselect-role-list"));
	});
});

</script>
</head>
<body>
	<div class="row">
		<div class="col-md-4 menu-list">
			<ul id="menuTree" class="ztree"></ul>
		</div>
		<div class="col-md-8">
			<form class="form-horizontal" method="post" id="menuForm">
				<input name="id" id="id" value="" type="hidden"/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">上级菜单</label>
					<div class="col-sm-9">
						<span id="parentName"></span>
						<input name="parent.id" id="parentId" type="hidden"/>
					</div>
				</div>
				<div class="form-group">
					<label for="inputName" class="col-sm-3 control-label">菜单名称</label>
					<div class="col-sm-9">
						<input class="form-control" 
							id="inputName" 
							placeholder="菜单名称" 
							name="name"
							required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label for="inputUrl" 
						class="col-sm-3 control-label">URL</label>
					<div class="col-sm-9">
						<input class="form-control" 
							id="inputUrl" 
							placeholder="访问菜单的URL，【功能】是完整的URL、操作可以是正则表达式或者URL匹配" 
							name="url" 
							required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label 
						class="col-sm-3 control-label">请求方法</label>
					<div class="col-sm-9">
						<label class="radio-inline">
							<input type="radio" name="method" value="GET" />
							GET
						</label>
						<label class="radio-inline">
							<input type="radio" name="method" value="POST" />
							POST
						</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-12">
						<div class="col-md-5 ">
							已选择的角色
							<div class="role-list selected-role-list">
								
							</div>
						</div>
						<div class="col-md-2 buttons">
							<a class="btn btn-default add-selected">&lt;添加所选</a>
							<a class="btn btn-default add-all">&lt;&lt;添加全部</a>
							<a class="btn btn-default remove-selected">&gt;删除所选</a>
							<a class="btn btn-default remove-all">&gt;&gt;删除全部</a>
						</div>
						<div class="col-md-5 ">
							已选择的角色
							<div class="role-list unselect-role-list">
								<c:forEach items="${roles }" var="r">
								<label>
									<input type="checkbox" value="${r.id }"/>${r.name }
								</label>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">保存</button>
						<button type="reset" class="btn btn-default">复位</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx }/webjars/zTree/3.5.28/js/jquery.ztree.all.min.js"></script>
</body>
</html>