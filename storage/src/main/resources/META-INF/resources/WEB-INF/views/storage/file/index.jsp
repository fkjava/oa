<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>通用存储</title>
</head>
<body>
	<button type="button" data-toggle="modal" data-target="#uploadDialog">上传</button>
	<div class="table-responsive">
		<table class="table table-bordered table-hover table-striped">
			<thead>
				<tr>
					<th onclick="orderBy('name')">文件名</th>
					<th>文件类型</th>
					<th>文件大小</th>
					<th onclick='orderBy("uploadTime")'>上传时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<%-- page.content == page.getContent() --%>
				<c:forEach items="${page.content }" var="f">
				<tr>
					<td>${f.name }</td>
					<td>${f.contentType }</td>
					<td>${f.contentLength }字节</td>
					<td>${f.uploadTime }</td>
					<td>
						下载
						删除
					</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5" style="text-align: center;">
						<my:spring-page url="?name=${param.name }&orderBy=${param.orderBy }"/>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
	<div class="modal fade" tabindex="-1" role="dialog" id="uploadDialog">
	    <div class="modal-dialog" role="document">
	        <form action="" method="post" enctype="multipart/form-data">
	        	<div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
		                </button>
		                <h4 class="modal-title">上传文件</h4>
		            </div>
		            <div class="modal-body">
							<input name="file" type="file"/>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		                <button type="submit" class="btn btn-primary">上传</button>
		            </div>
		        </div><!-- /.modal-content -->
			</form>
	    </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
</html>