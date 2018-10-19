<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>任务详情</title>
<link rel="stylesheet" href="${ctx }/webjars/Eonasdan-bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css"/>
</head>
<body>
	<script type="text/javascript">
	// 使用一个JavaScript变量存储业务数据
	var businessData = ${json};
	</script>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">处理任务</h2>
	</div>
	<%-- 整个完成任务的页面，基本上就是启动流程实例的页面 --%>
	<%-- 要改的主要就是action的值，完成任务提交到另外一个控制方法 --%>
    <div class="col-md-12 table-responsive">
	    <form action="${ctx }/workflow/task/${form.task.id}" 
	    	method="post" 
	    	enctype="multipart/form-data"
	    	onsubmit="return checkOnSubmit()">
	    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    	<%-- 业务数据的主键值要存储起来，否则后面只会不断新增数据、不能修改数据 --%>
	    	<input type="hidden" name="id" value="${form.instance.businessKey }"/>
	    	<c:if test="${not empty form.definition.description }">
    			<div class="alert alert-info" role="alert">
    				<p>${form.definition.description }</p>
    				<c:if test="${not empty form.task.description }">
    				<p>${form.task.description }</p>
    				</c:if>
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
	    		<textarea name="remark" rows="10" class="form-control" required="required"></textarea>
	    	</fieldset>
	    	<fieldset class="col-xs-12 col-sm-12">
	    		<legend>操作</legend>
	    		<div style="text-align: right;">
	    			<%-- 显示操作选项 --%>
	    			<%-- TaskForm.getData().getFormProperties() --%>
	    			<c:forEach items="${form.data.formProperties }" var="fp">
	    				<%-- 只处理枚举类型 --%>
	    				<c:if test="${fp.type.name eq 'enum' }">
<%-- 	    					${fp.name } --%>
							<%-- FormProperty.getType.getInformation('values') --%>
							<c:forEach items="${fp.type.getInformation('values') }" var="kv">
								<label>
									${kv.key }
									<input type="radio" name="${fp.id }" value="${kv.value }" required="required"/>
								</label>
							</c:forEach>
	    				</c:if>
	    			</c:forEach>
	    		
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