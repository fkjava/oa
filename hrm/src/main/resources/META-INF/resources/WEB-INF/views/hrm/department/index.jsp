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
<title>部门管理</title>
<link href="${ctx }/webjars/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
<link href="${ctx }/static/css/department.css" rel="stylesheet"/>
<link href="${ctx }/static/css/menu.css" rel="stylesheet"/>
<script type="text/javascript">
var departments = ${json};
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-4 menu-list">
			<ul id="departmentTree" class="ztree"></ul>
		</div>
		<div class="col-md-8">
			<form class="form-horizontal" method="post" id="departmentForm"
				onsubmit="return beforeSubmit()">
				<input name="id" id="id" type="hidden"/>
				<input name="number" id="number" type="hidden"/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">上级部门</label>
					<div class="col-sm-9">
						<span id="parentName"></span>
						<input name="parent.id" id="parentId" type="hidden"/>
					</div>
				</div>
				<div class="form-group">
					<label for="inputName" class="col-sm-3 control-label">部门名称</label>
					<div class="col-sm-9">
						<input class="form-control" 
							id="inputName" 
							placeholder="部门名称" 
							name="name"
							required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label for="selectOwner" class="col-sm-3 control-label">负责人</label>
					<div class="col-sm-9">
						<%-- 使用一个下拉框选择 --%>
						<select class="form-control" 
							id="selectOwner"
							name="owner.id"
							required="required">
							<option value="">-- 请选择责任人 --</option>
							<c:forEach items="${allNormalUsers }" var="u">
								<option value="${u.id }">${u.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">保存</button>
						<button type="button" onclick="resetForm()" class="btn btn-default">复位</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx }/webjars/zTree/3.5.28/js/jquery.ztree.all.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/department.js" charset="UTF-8"></script>
</body>
</html>