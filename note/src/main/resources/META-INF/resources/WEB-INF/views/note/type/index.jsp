<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>公告类型</title>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
</head>
<body>
	<div class="col-md-6 role-list">
		<table class="table">
			<thead>
				<tr>
					<th>类型名称</th>
					<th>标记</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${types }" var="t">
					<tr data-id="${t.id }">
						<td class="name">${t.name }</td>
						<td class="roleKey">
							<input type="hidden" name="name" value="${t.name }"/>
							<input type="hidden" name="modifiable" value="${t.modifiable }"/>
							<input type="hidden" name="deletable" value="${t.deletable }"/>
							<input type="hidden" name="revocable" value="${t.revocable }"/>
							<input type="hidden" name="number" value="${t.number }"/>
							${t.modifiable ? "可修改" : "" }
							${t.deletable ? "可删除" : "" }
							${t.revocable ? "可撤回" : "" }
						</td>
						<td>
						<c:if test="${not r.fixed}">
							<a class="btn btn-primary btn-xs show-to-form">
								<span class="glyphicon glyphicon-edit"></span>
								修改
							</a>
							<a class="btn btn-danger btn-xs remove-type">
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
				<label for="inputName" class="col-sm-3 control-label">类型名称</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputName" 
						placeholder="类型名称" 
						name="name"
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="checkTypeFlag" class="col-sm-3 control-label">标记</label>
				<div class="col-sm-9">
					<div class="checkbox">
						<label><input type="checkbox" name="modifiable" value="true"/>可修改</label>
						<label><input type="checkbox" name="deletable" value="true"/>可删除</label>
						<label><input type="checkbox" name="revocable" value="true"/>可撤回</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="inputNumber" class="col-sm-3 control-label">排序的序号</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputNumber" 
						placeholder="排序的序号" 
						name="number"
						required="required"
						type="number"/>
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
	
	<script type="text/javascript" src="${ctx }/static/js/note-type.js" charset="UTF-8"></script>
</body>
</html>