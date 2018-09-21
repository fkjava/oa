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
<title>菜单配置</title>
<link href="${ctx }/webjars/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
<link href="${ctx }/static/css/menu.css" rel="stylesheet"/>
</head>
<body>
	<div class="row">
		<div class="col-md-4 menu-list">
			<ul id="menuTree" class="ztree"></ul>
		</div>
		<div class="col-md-8">
			<form class="form-horizontal" method="post" id="menuForm"
				onsubmit="return beforeSubmit()">
				<input name="id" id="id" value="" type="hidden"/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">上级菜单</label>
					<div class="col-sm-9">
						<span id="parentName"></span>
						<input name="parent.id" id="parentId" type="hidden"/>
					</div>
				</div>
				<div class="form-group">
					<label for="inputName" class="col-sm-3 control-label">菜单名称</label>
					<div class="col-sm-9">
						<input class="form-control" 
							id="inputName" 
							placeholder="菜单名称" 
							name="name"
							required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label for="inputUrl" 
						class="col-sm-3 control-label">URL</label>
					<div class="col-sm-9">
						<input class="form-control" 
							id="inputUrl" 
							placeholder="访问菜单的URL，【功能】是完整的URL、操作可以是正则表达式或者URL匹配" 
							name="url" 
							required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label 
						class="col-sm-3 control-label">请求方法</label>
					<div class="col-sm-9">
						<label class="radio-inline">
							<input type="radio" name="method" value="GET" />
							GET
						</label>
						<label class="radio-inline">
							<input type="radio" name="method" value="POST" />
							POST
						</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-12">
						<div class="col-md-5 ">
							已选择的角色
							<div class="role-list selected-role-list">
								
							</div>
						</div>
						<div class="col-md-2 buttons">
							<a class="btn btn-default add-selected">&lt;添加所选</a>
							<a class="btn btn-default add-all">&lt;&lt;添加全部</a>
							<a class="btn btn-default remove-selected">&gt;删除所选</a>
							<a class="btn btn-default remove-all">&gt;&gt;删除全部</a>
						</div>
						<div class="col-md-5 ">
							已选择的角色
							<div class="role-list unselect-role-list">
								<c:forEach items="${roles }" var="r">
								<label>
									<input type="checkbox" value="${r.id }"/>${r.name }
								</label>
								</c:forEach>
							</div>
						</div>
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
	<script type="text/javascript" src="${ctx }/static/js/menu.js" charset="UTF-8"></script>
</body>
</html>