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
<link rel="stylesheet" href="${ctx }/static/css/note-read.css"/>
</head>
<body>
	<div class="row panel panel-default">
		<div class="panel-body">
			<div class="note-title">
				${read.note.title }
			</div>
			<div class="note-writer">
				<div class="note-author">
					发文：<span class="author-name">${read.note.writeUser.name }</span>
				</div>
				<div class="note-write-time">
					发文时间：<span class="write-time">${read.note.writeTime }</span>
				</div>
			</div>
			<div class="note-content">
				${read.note.content }
			</div>
		</div>
		<div class="panel-footer" style="text-align: right ;">
			<form action="${ctx }/note/read/${read.note.id}" method="post">
				<c:if test="${empty read.reader }">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<button class="btn btn-primary btn-readed" disabled="disabled" type="submit">已读(10)</button>
				</c:if>
				<a class="btn btn-default" href="${ctx }/note/read">返回</a>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="${ctx }/static/js/note-read.js" charset="UTF-8"></script>
</body>
</html>