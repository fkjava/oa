<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>岗位管理</title>
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
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${positions }" var="r">
					<tr data-id="${r.id }">
						<td class="name">${r.name }</td>
						<td>
							<a class="btn btn-primary btn-xs" 
								onclick="showToForm(this.parentNode.parentNode)">
								<span class="glyphicon glyphicon-edit"></span>
								修改
							</a>
							<a class="btn btn-danger btn-xs"
								onclick="deletePosition(this.parentNode.parentNode)">
								<span class="glyphicon glyphicon-remove"></span>
								删除
							</a>
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
				<label for="inputName" class="col-sm-3 control-label">岗位名称</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputName" 
						placeholder="岗位名称" 
						name="name"
						required="required"/>
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
	
	var showToForm = function(tr){
		var id = $(tr).attr("data-id");
		var name = $(".name", tr).text();
		$("#detailForm #id").val(id);
		$("#detailForm #inputName").val(name);
	}
	
	// 复位表单
	var resetForm = function(event){
		$("#detailForm #id").val("");
		$("#detailForm #inputName").val("");
	};
	
	var deletePosition = function(tr){
		var id = $(tr).attr("data-id");

		var name = $(".name", tr).text();
		if(confirm("您确定要删除【"+name+"】岗位吗？\n如果岗位已经有员工使用，不能删除成功！")){
			var url = "./position/" + id;
			$.ajax({
				url: url,
				method: "DELETE",
				success: function(data, status, xhr){
					alert(data);
					document.location.href="./position";
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