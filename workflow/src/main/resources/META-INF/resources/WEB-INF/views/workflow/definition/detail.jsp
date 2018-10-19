<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>流程详情</title>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">流程详情</h2>
	</div>
    <div class="col-md-12 table-responsive">
	    <div class="col-xs-4 col-sm-2">
	    	流程名称
	    </div>
	    <div class="col-xs-8 col-sm-10">
	    	${form.definition.name }
	    </div>
	    <div class="col-xs-4 col-sm-2">
	    	是否被禁用
	    </div>
	    <div class="col-xs-8 col-sm-10">
	    	<c:choose>
	    		<c:when test="${form.definition.suspended }">
	    			已禁用
	    		</c:when>
	    		<c:otherwise>正常</c:otherwise>
	    	</c:choose>
	    </div>
	    <div class="col-xs-12">
	    	<img alt="流程图无法获取" src="${ctx }/workflow/definition/image/${form.definition.id}"/>
	    </div>
	</div>
</body>
</html>