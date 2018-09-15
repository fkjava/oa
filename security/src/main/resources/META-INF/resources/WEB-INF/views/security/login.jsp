<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/simple.jsp">
<title>登录</title>
<link rel="stylesheet" href="${ctx }/static/css/login.css"/>
</head>
<body>
	<form class="form-signin" action="do-login" method="post">
		<%-- 判断Session里面是否有Spring Security登录失败的信息 --%>
		<c:if test="${empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
		    <div class="alert alert-info">
		    	<%-- 没有失败信息，则显示欢迎登录 --%>
		        欢迎登录
		    </div>
		</c:if>
		<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
		    <div class="alert alert-danger">
		    	<%-- 否则显示失败原因 --%>
		        ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
		    </div>
		</c:if>
		<%-- 登录失败的信息，放Session里面，在不需要的时候自己删除掉 --%>
		<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>

	    <label for="inputLoginName" class="sr-only">登录名</label>
	    <input id="inputLoginName" 
	        class="form-control" 
	        placeholder="登录名" 
	        required="required" 
	        autofocus="autofocus"
	        name="loginName"/>
	    <label for="inputPassword" class="sr-only">密码</label>
	    <input type="password" 
	        id="inputPassword" 
	        class="form-control" 
	        placeholder="登录密码" 
	        required="required"
	        name="password"/>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
	</form>
</body>
</html>