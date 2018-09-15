<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<style type="text/css">
	.role-list
	{
		height: 300px;
		border-right: 1px solid #000000;
		overflow: auto;
	}
	.role-form
	{
		height: 300px;
	}
</style>
</head>
<body>
	<div class="col-md-6 role-list">
		<table class="table">
			<thead>
				<tr>
					<th>名称</th>
					<th>KEY</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${roles }" var="r">
					<tr data-id="${r.id }">
						<td class="name">${r.name }</td>
						<td class="roleKey">${r.roleKey }</td>
						<td>
						<c:if test="${not r.fixed}">
							<a class="btn btn-primary btn-xs" 
								onclick="showToForm(this.parentNode.parentNode)">
								<span class="glyphicon glyphicon-edit"></span>
								修改
							</a>
							<a class="btn btn-danger btn-xs"
								onclick="deleteRole(this.parentNode.parentNode)">
								<span class="glyphicon glyphicon-remove"></span>
								删除
							</a>
						</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="col-md-6 role-form">
		<form class="form-horizontal" method="post" id="detailForm">
			<input name="id" id="id" value="" type="hidden"/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="form-group">
				<label for="inputName" class="col-sm-3 control-label">角色名称</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputName" 
						placeholder="角色名称" 
						name="name"
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputRoleKey" 
					class="col-sm-3 control-label">KEY</label>
				<div class="col-sm-9">
					<%-- 正则表都是限制KEY不能是ROLE_开头的，(?!)用于取反 --%>
					<input class="form-control" 
						id="inputRoleKey" 
						placeholder="角色的唯一键" 
						name="roleKey" 
						onkeypress="onKeyInput(event)"
						required="required"
						pattern="(?!ROLE_).*"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" onclick="resetForm(event)">复位</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	var onKeyInput = function(event){
		// 取消事件的默认行为
		event.preventDefault();
		// 判断输入的字符是否为字母、下划线
		var keyCode = event.keyCode;
		var key = event.key;
		if( (keyCode >= 65 && keyCode <= 90)//大写字母
				|| (keyCode >= 97 && keyCode <= 122) //小写字母
				|| keyCode === 95// 下划线，===表示不转换数据类型判断，在明确数据类型的情况下比较快
			)
		{
			// 转换输入的字符为大写
			key = key.toUpperCase();
			// 把输入的合法字符，追加到现有字符之后
			var value = $(event.target).val();
			value = value + key;
			$(event.target).val(value);
		}
	};
	
	// 在表单中显示要修改的角色
	var showToForm = function(tr){
		var id = $(tr).attr("data-id");
		// 选择器的第二个参数，表示在哪个元素里面根据选择器查询子元素
		var name = $(".name", tr).text();
		var roleKey = $(".roleKey", tr).text();
		$("#detailForm #id").val(id);
		$("#detailForm #inputName").val(name);
		$("#detailForm #inputRoleKey").val(roleKey);
	}
	
	// 复位表单
	var resetForm = function(event){
		$("#detailForm #id").val("");
		$("#detailForm #inputName").val("");
		$("#detailForm #inputRoleKey").val("");
	};
	
	// 删除角色
	var deleteRole = function(tr){
		var id = $(tr).attr("data-id");

		var name = $(".name", tr).text();
		if(confirm("您确定要删除【"+name+"】角色吗？\n如果角色已经有用户使用，不能删除成功！")){
			var url = "./role/" + id;
			$.ajax({
				url: url,
				method: "DELETE",
				success: function(data, status, xhr){
					alert(data);
					document.location.href="./role";
				},
				error: function(data, status, xhr){
					alert(data.responseJSON.message);
				}
			});
		}
	}
	</script>
</body>
</html>