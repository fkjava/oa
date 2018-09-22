<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>公告管理</title>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
</head>
<body>
	<div class="row panel panel-default">
		<div class="panel-heading">
			<div style="text-align: right;">
				<a href="${ctx }/note/maintain/add" class="btn btn-default btn-sm">添加</a>
			</div>
		</div>
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
					<c:forEach items="${page.content }" var="note">
						<tr data-id="${note.id }">
							<td class="title">${note.title }</td>
							<td class="type">
								<input type="hidden" name="title" value="${note.title }"/>
								<input type="hidden" name="type.id" value="${note.type.id }"/>
								${note.type.name }
							</td>
							<td>
								${note.writeUser.name }
							</td>
							<td>
								<fmt:formatDate value="${note.writeTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<fmt:formatDate value="${note.publishTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${note.status eq 'DRAFT' }">草稿</c:when>
									<c:when test="${note.status eq 'PUBLISHED' }">已发布</c:when>
									<c:when test="${note.status eq 'REVOKE' }">已撤回</c:when>
								</c:choose>
							</td>
							<td>
								<c:if test="${note.status eq 'DRAFT' or note.status eq 'PUBLISHED' }">
									<c:if test="${note.status eq 'DRAFT' or (note.status eq 'PUBLISHED' and note.type.modifiable) }">
										<a class="btn btn-primary btn-xs show-to-form">
											<span class="glyphicon glyphicon-edit"></span>
											修改
										</a>
									</c:if>
									<c:if test="${note.status eq 'DRAFT' or (note.status eq 'PUBLISHED' and note.type.deletable) }">
										<a class="btn btn-danger btn-xs remove-type">
											<span class="glyphicon glyphicon-remove"></span>
											删除
										</a>
									</c:if>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="panel-footer" style="text-align: center;">
			<my:spring-page url=""/>
		</div>
	</div>
	
	
	<script type="text/javascript" src="${ctx }/static/js/note-maintain.js" charset="UTF-8"></script>
</body>
</html>