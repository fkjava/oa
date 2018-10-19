<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>启动流程</title>
<link rel="stylesheet" href="${ctx }/webjars/Eonasdan-bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css"/>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">启动【${form.definition.name }】流程</h2>
	</div>
    <div class="col-md-12 table-responsive">
	    <form action="${ctx }/workflow/instance/${form.definition.id}" 
	    	method="post" 
	    	enctype="multipart/form-data"
	    	onsubmit="return checkOnSubmit()">
	    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    	<c:if test="${not empty form.definition.description }">
    			<div class="alert alert-info" role="alert">
    				${form.definition.description }
    			</div>
	    	</c:if>
	    	<c:if test="${not empty form.content }">
	    	${form.content }
	    	</c:if>
	    	<c:if test="${empty form.content and not empty form.formKey }">
	    		<c:catch var="ex">
	    			<jsp:include page="/WEB-INF/views/process/${form.definition.key }/${form.formKey }"></jsp:include>
	    		</c:catch>
	    	</c:if>
	    	<%-- 包含JSP文件出现异常，或者没有formKey --%>
	    	<%-- 没有formKey，肯定没有表单内容 --%>
    		<c:if test="${not empty ex or empty form.formKey }">
    			<div class="alert alert-warning" role="alert">
    				没有找到流程的业务表单，无法显示表单内容
    			</div>
    		</c:if>
	    	<fieldset class="col-xs-12 col-sm-12">
	    		<legend>备注</legend>
	    		<%-- 备注可以使用富文本编辑器 --%>
	    		<textarea name="remark" rows="10" class="form-control"></textarea>
	    	</fieldset>
	    	<fieldset class="col-xs-12 col-sm-12">
	    		<legend>操作</legend>
	    		<div style="text-align: right;">
	    			<button type="submit" class="btn btn-primary">提交</button>
	    		</div>
	    	</fieldset>
	    </form>
	</div>
	<script type="text/javascript" src="${ctx }/webjars/momentjs/2.10.3/min/moment-with-locales.min.js"></script>
	<script type="text/javascript" src="${ctx }/webjars/Eonasdan-bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/workflow.js" charset="UTF-8"></script>
</body>
</html>