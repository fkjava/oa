<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>用户列表</title>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">用户列表</h2>
		<span class="pull-right" style="margin-top: 20px;">
			<a class="btn btn-default" href="./user/add">新增</a>
		</span>
	</div>
    <div class="col-md-12 table-responsive">
	    <table class="table table-striped">
	        <thead>
	            <tr>
	                <th>姓名</th>
	                <th>登录名</th>
	                <th>生日</th>
	                <th>角色</th>
	                <th>操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr>
	                <td>张三丰</td>
	                <td>zsf</td>
	                <td>1800-10-01</td>
	                <td>武林宗师、武当掌门、太极创始人</td>
	                <td>
	                    停用、离职
	                    修改
	                </td>
	            </tr>
	        </tbody>
	    </table>
	</div>
</body>
</html>