<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>流程列表</title>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">流程列表</h2>
		<span class="pull-right" style="margin-top: 20px;">
			<a class="btn btn-default" data-toggle="modal" data-target="#uploadDialog">新增</a>
		</span>
	</div>
    <div class="col-md-12 table-responsive">
	    <table class="table table-striped">
	        <thead>
	            <tr>
	                <th style="width: 10%" onclick="orderBy('category')">分类</th>
	                <th style="width: 30%" onclick="orderBy('name')">流程名称</th>
	                <th style="width: 30%" onclick="orderBy('key')">流程KEY</th>
	                <th style="width: 10%">版本号</th>
	                <th style="width: 20%">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${page.content }" var="u">
	            <tr data-id="${u.id }">
	                <td>${u.category }</td>
	                <td>${u.name }</td>
	                <td>${u.key }</td>
	                <td>${u.version }</td>
	                <td class="actions-btn">
	                	<%-- 建议禁用、激活使用AJAX来实现 --%>
	                	<c:if test="${u.suspended }">
	                		<a class="btn btn-xs btn-success"
	                			href="${ctx }/workflow/definition/active/${u.id}">
	                			<span class="glyphicon glyphicon-play"></span>激活
                			</a>
	                	</c:if>
	                	<%-- 建议confirm改为使用Bootstrap的modal --%>
	                	<c:if test="${not u.suspended }">
	                		<a class="btn btn-xs btn-danger" 
	                			onclick="return confirm('确定要禁用【${u.name}】流程吗？\n禁用以后不能再启动新的流程实例。');"
	                			href="${ctx }/workflow/definition/suspend/${u.id}">
	                			<span class="glyphicon glyphicon-ban-circle"></span>禁用
                			</a>
	                	</c:if>
	                	详情
	                </td>
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
	<div class="modal fade" tabindex="-1" role="dialog" id="uploadDialog">
		<form method="post" enctype="multipart/form-data">
		    <div class="modal-dialog" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">&times;</span></button>
		                <h4 class="modal-title">上传流程定义文件</h4>
		            </div>
		            <div class="modal-body">
		                <p>请选择文件上传，只能上传zip格式的压缩文件</p>
		                <%-- accept表示过滤文件类型，application/zip表示只把zip文件显示出来 --%>
		                <input name="file" type="file" accept="application/zip"/>
		                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		                <button type="submit" class="btn btn-primary">确定</button>
		            </div>
		        </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
	    </form>
	</div><!-- /.modal -->
</body>
</html>