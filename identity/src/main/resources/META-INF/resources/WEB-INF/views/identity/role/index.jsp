<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>角色管理</title>

<link href="${ctx }/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx }/webjars/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
	<!-- 占满屏幕 -->
	<div class="container-fluid">
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
					<tr>
						<td>网络管理员</td>
						<td>NET_MANAGER</td>
						<td>
						修改
						删除
						</td>
					</tr>
					<tr>
						<td>系统管理员</td>
						<td>SYS_MANAGER</td>
						<td>
						修改
						删除
						</td>
					</tr>
					<tr>
						<td>项目经理</td>
						<td>PROJECT_MANAGER</td>
						<td>
						修改
						删除
						</td>
					</tr>
					<tr>
						<td>普通用户</td>
						<td>USER</td>
						<td>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="col-md-6 role-form">
			<form class="form-horizontal">
				<div class="form-group">
					<label for="inputName" class="col-sm-2 control-label">角色名称</label>
					<div class="col-sm-10">
						<input class="form-control" id="inputName" placeholder="角色名称" name="name">
					</div>
				</div>
				<div class="form-group">
					<label for="inputRoleKey" class="col-sm-2 control-label">KEY</label>
					<div class="col-sm-10">
						<input class="form-control" id="inputRoleKey" placeholder="角色的唯一键" name="roleKey">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default">保存</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>