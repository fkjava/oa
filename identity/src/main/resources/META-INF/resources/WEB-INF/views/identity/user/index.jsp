<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
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
	                <th style="width: 10%">姓名</th>
	                <th style="width: 10%">登录名</th>
	                <th style="width: 20%">生日</th>
	                <th style="width: 40%">角色</th>
	                <th style="width: 20%">操作</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${page.content }" var="u">
	            <tr data-id="${u.id }">
	                <td>${u.name }</td>
	                <td class="loginName">${u.loginName }</td>
	                <td>${u.birthday }</td>
	                <td class="roles">
	                	<c:forEach items="${u.roles }" var="r">
	                		<span class="label label-info">${r.name }</span>
	                	</c:forEach>
					</td>
	                <td class="actions-btn">
	                	<c:if test="${u.status ne 'SEPARATION' }">
		                    <a class="btn btn-xs btn-danger separation-btn" onclick="separation('${u.name}', '${u.id }')">
		                    	<span class="glyphicon glyphicon-remove"></span>离职
	                    	</a>
		                    <a href="${ctx }/identity/user/${u.id}" class="btn btn-primary btn-xs">
		                    	<span class="glyphicon glyphicon-edit"></span>修改
	                    	</a>
                    	</c:if>
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
	<div class="modal fade" tabindex="-1" role="dialog" id="separationDialog">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                <span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title">员工离职处理</h4>
	            </div>
	            <div class="modal-body">
	                是否要将<span class="label label-success">XXX</span>员工改为已经离职，离职后登录名会失效、分配的角色会清空。
	                <input id="userId" type="hidden"/>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="button" class="btn btn-primary" onclick="confirmSeparation()">确定</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
	<div class="modal fade" tabindex="-1" role="dialog" id="processResultDailog">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                <span aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title">处理结果</h4>
	            </div>
	            <div class="modal-body">
	                <p class="alert"></p>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script type="text/javascript">
	
	// 1.离职第一步：获取姓名、id，放入对话框，显示对话框
	var separation = function(name, id){
		// 首先需要一个对话框，询问是否把用户设置为离职
		// 点击确定按钮，再发送请求给服务器（AJAX）
		// 服务器返回状态以后，通过对话框提示，不需要刷新页面，只是把用户的状态修改一下
		
		$("#separationDialog .modal-body .label-success").text(name);
		$("#separationDialog .modal-body #userId").val(id);
		$("#separationDialog").modal();
	};
	
	// 2.离职第二步：执行离职
	var confirmSeparation = function(){
		var id = $("#separationDialog .modal-body #userId").val();
		$.ajax({
			url: "./user/separation/" + id,
			method: "POST",
			success: function(data, status, xhr){
				if( data.status == 2){
					// 成功
					$("#processResultDailog .modal-body p").text("离职操作执行成功");
					$("#processResultDailog .modal-body p").addClass("alert-success");
					// 页面去掉登录名、角色、离职按钮，修改显示效果
					var tr = $("tr[data-id='"+id+"']");
					
					// 把子元素全部干掉
					$(".loginName", tr).empty();
					$(".roles", tr).empty();
					
					// 删除元素本身
					//$(".separation-btn", tr).remove();
					$(".actions-btn", tr).empty();
				}
				else {
					// 失败
					$("#processResultDailog .modal-body p").text("离职操作执行失败：" + data.message);
					$("#processResultDailog .modal-body p").addClass("alert-danger");
				}
				$("#separationDialog").modal("hide");
				$("#processResultDailog").modal();
			},
			error: function(data, status, xhr){
				//alert("离职出现问题：\n" + data.responseJSON.message);
				$("#processResultDailog .modal-body p").text("离职操作执行失败：" + data.responseJSON.message);
				$("#processResultDailog .modal-body p").addClass("alert-danger");
				$("#separationDialog").modal("hide");
				$("#processResultDailog").modal();
			}
		});
	};
	</script>
</body>
</html>