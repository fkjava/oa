<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<title>公告管理</title>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
</head>
<body>
	<div class="row panel panel-default">
		<div class="panel-heading">
			<div style="text-align: right;">
				<a href="${ctx }/note/maintain/add" class="btn btn-default btn-sm">添加</a>
			</div>
		</div>
		<div class="panel-body">
			<%-- 小屏幕才有响应式效果 --%>
			<div class="table-responsive">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th style="width: 20%;">标题</th>
							<th style="width: 10%;">类型</th>
							<th style="width: 10%;">作者</th>
							<th style="width: 15%;">编写时间</th>
							<th style="width: 15%;">发布时间</th>
							<th style="width: 10%;">状态</th>
							<%-- 可以参考树型结构，鼠标放上去才显示按钮，可以减少一列 --%>
							<th style="width: 20%;">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content }" var="note">
							<tr data-id="${note.id }">
								<td  scope="row" class="title">${note.title }</td>
								<td class="type">
									<input type="hidden" name="title" value="${note.title }"/>
									<input type="hidden" name="type.id" value="${note.type.id }"/>
									${note.type.name }
								</td>
								<td>
									${note.writeUser.name }
								</td>
								<td>
									<fmt:formatDate value="${note.writeTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${note.publishTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
									<c:choose>
										<c:when test="${note.status eq 'DRAFT' }">草稿</c:when>
										<c:when test="${note.status eq 'PUBLISHED' }">已发布</c:when>
										<c:when test="${note.status eq 'REVOKED' }">已撤回</c:when>
									</c:choose>
								</td>
								<td>
									<c:if test="${note.status eq 'DRAFT' or note.status eq 'PUBLISHED' }">
										<c:if test="${note.status eq 'DRAFT' or (note.status eq 'PUBLISHED' and note.type.modifiable) }">
											<a class="btn btn-primary btn-xs">
												<span class="glyphicon glyphicon-edit"></span>
												修改
											</a>
										</c:if>
										<c:if test="${note.status eq 'DRAFT' or (note.status eq 'PUBLISHED' and note.type.deletable) }">
											<a class="btn btn-danger btn-xs">
												<span class="glyphicon glyphicon-remove"></span>
												删除
											</a>
										</c:if>
									</c:if>
									<c:if test="${note.status ne 'REVOKED' and note.status ne 'PUBLISHED' }">
										<a class="btn btn-success btn-xs publish-note">
											<span class="glyphicon glyphicon-play"></span>
											发布
										</a>
									</c:if>
									<c:if test="${note.status eq 'PUBLISHED' and note.type.revocable}">
										<a class="btn btn-danger btn-xs revoke-note">
											<span class="glyphicon glyphicon-stop"></span>
											撤回
										</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel-footer" style="text-align: center;">
			<my:spring-page url=""/>
		</div>
	</div>
	
	<%-- 撤回公告的确认对话框，输入撤回的原因 --%>
	<div class="modal fade revoke-dialog" tabindex="-1" role="dialog">
	    <div class="modal-dialog" role="document">
	        <form action="${ctx}/note/maintain/revoke" method="get"
	        	class="form-horizontal">
	            <input name="id" value=""/>
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                    <h4 class="modal-title">确定要撤回公告吗？</h4>
	                </div>
	                <div class="modal-body">
	                    <p id="title">公告标题</p>
	                    <div class="form-group">
	                        <label for="revokeRemark" class="col-xs-2 control-label">
	                            撤回原因
	                        </label>
	                        <div class="col-xs-10">
	                        	<input type="text" class="form-control" 
	                        		id="revokeRemark" placeholder="撤回原因" 
	                        		name="revokeRemark"/>
	                        </div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                    <button type="submit" class="btn btn-primary">撤回</button>
	                </div>
	            </div><!-- /.modal-content -->
	        </form>
	    </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
	<script type="text/javascript" src="${ctx }/static/js/note-maintain.js" charset="UTF-8"></script>
</body>
</html>