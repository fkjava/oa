<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>阅读公告</title>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<style type="text/css">
.table-hover > tbody > tr:hover {
	background-color: #9999ff;
}
</style>
</head>
<body>
	<div class="row panel panel-default">
		<div class="panel-body">
			<%-- 小屏幕才有响应式效果 --%>
			<div class="table-responsive">
				<table class="table table-bordered table-hover table-striped">
					<thead>
						<tr>
							<th style="width: 55%;">标题</th>
							<th style="width: 10%;">类型</th>
							<th style="width: 10%;">作者</th>
							<th style="width: 15%;">发布时间</th>
							<th style="width: 10%;">是否已读</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content }" var="nr">
							<tr data-id="${nr.note.id }" style="cursor: pointer;">
								<td  scope="row" class="title">
<%-- 									<a href="${ctx }/note/read/${nr.note.id}">${nr.note.title }</a> --%>
									${nr.note.title }
								</td>
								<td class="type">
									${nr.note.type.name }
								</td>
								<td>
									${nr.note.writeUser.name }
								</td>
								<td>
									<fmt:formatDate value="${nr.note.publishTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<c:if test="${ empty nr.reader }">
										未读
									</c:if>
									<c:if test="${ not empty nr.reader }">
										已读
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel-footer" style="text-align: center;">
			<my:spring-page url=""/>
		</div>
	</div>
	<script type="text/javascript" src="${ctx }/static/js/note-read.js" charset="UTF-8"></script>
</body>
</html>