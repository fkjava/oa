<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>公告管理</title>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
</head>
<body>
	<div class="row panel panel-default">
		<div class="panel-body">
			<table class="table">
				<thead>
					<tr>
						<th>标题</th>
						<th>类型</th>
						<th>作者</th>
						<th>编写时间</th>
						<th>发布时间</th>
						<th>状态</th>
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
		<div class="panel-footer">
			<div style="text-align: right;">
				<a href="${ctx }/note/maintain/add" class="btn btn-default">添加</a>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript" src="${ctx }/static/js/note-maintain.js" charset="UTF-8"></script>
</body>
</html>