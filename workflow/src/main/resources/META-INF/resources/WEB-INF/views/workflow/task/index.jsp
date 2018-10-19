<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>待办列表</title>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">待办列表</h2>
	</div>
    <div class="col-md-12 table-responsive">
	    <table class="table table-striped">
	        <thead>
	            <tr>
	                <th style="width: 10%">分类</th>
	                <th style="width: 30%">流程名称</th>
	                <th style="width: 15%">流程创建时间</th>
	                <th style="width: 30%" onclick="orderBy('taskName')">任务名称</th>
	                <th style="width: 15%" onclick="orderBy('createTime')">任务创建时间</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${page.content }" var="tf">
	            <tr data-id="${tf.task.id }">
	                <td>${tf.definition.category }</td>
	                <td>${tf.definition.name }</td>
	                <td><fmt:formatDate value="${tf.instance.startTime }" pattern="yyyy-MM-dd HH:mm"/></td>
	                <td>${tf.task.name }</td>
	                <td><fmt:formatDate value="${tf.task.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
	            </tr>
	            </c:forEach>
	        </tbody>
	        <tfoot>
	        	<tr>
	        		<td colspan="5" style="text-align: center;">
	        			<%-- 使用自定义的标签库，并且传入参数 --%>
						<my:spring-page url=""/>
	        		</td>
	        	</tr>
	        </tfoot>
	    </table>
	</div>
	<style type="text/css">
		tbody tr
		{
			cursor: pointer;
		}
	</style>
	<script type="text/javascript">
		$("tbody tr").click(function(){
			var tr = $(this);
			// 获取任务的id
			var id = tr.attr("data-id");
			document.location.href = "${ctx}/workflow/task/" + id;
		});
	</script>
</body>
</html>