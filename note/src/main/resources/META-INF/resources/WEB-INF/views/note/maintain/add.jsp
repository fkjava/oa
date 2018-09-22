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
		<form action="" method="post" id="noteForm">
			<div class="panel-body">
				
				<input name="id" id="id" value="" type="hidden"/>
				<%-- 当富文本编辑器改变的时候，把内容存储在content字段里面，以便提交到服务器 --%>
				<input name="content" id="content" value="" type="hidden"/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">
					<label for="inputTitle" class="col-sm-2 control-label">标题</label>
					<div class="col-sm-10">
						<input class="form-control" 
							id="inputTitle" 
							placeholder="标题" 
							name="title"
							required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label for="selectType" class="col-sm-2 control-label">公告类型</label>
					<div class="col-sm-10">
						<select name="type[id]" id="typeId" class="form-control">
							<option value="">-- 请选择类型 --</option>
							<c:forEach items="${types }" var="t">
							<option value="${t.id }">${t.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="selectType" class="col-sm-2 control-label">公告内容</label>
					<div class="col-sm-10">
						<%-- 用于生成富文本编辑器的 --%>
						<div id="noteContent">
						</div>
					</div>
				</div>
			</div>
			<div class="panel-footer">
				<div style="text-align: right;">
					<button type="button" class="btn btn-primary save-button">保存</a>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript" src="${ctx }/webjars/jquery.serializeJSON/2.8.1/jquery.serializejson.min.js"></script>
	<!-- 注意， 只需要引用 JS，无需引用任何 CSS ！！！-->
    <script type="text/javascript" src="${ctx }/webjars/wangEditor/3.1.1/release/wangEditor.min.js"></script>

	<script type="text/javascript" src="${ctx }/static/js/note-maintain.js" charset="UTF-8"></script>
</body>
</html>