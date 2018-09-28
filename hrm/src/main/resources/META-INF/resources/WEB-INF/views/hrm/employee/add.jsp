<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>员工信息</title>
<link rel="stylesheet" href="${ctx }/webjars/Eonasdan-bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css"/>

<%-- <script type="text/javascript" src="${ctx }/webjars/momentjs/2.10.3/min/moment.min.js"></script> --%>
<script type="text/javascript" src="${ctx }/webjars/momentjs/2.10.3/min/moment-with-locales.min.js"></script>
<script type="text/javascript" src="${ctx }/webjars/Eonasdan-bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"></script>
<link href="${ctx }/webjars/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">
			<c:choose>
				<c:when test="${not empty employee }">
					修改员工信息
				</c:when>
				<c:otherwise>员工入职</c:otherwise>
			</c:choose>
		</h2>
		<span class="pull-right" style="margin-top: 20px;">
			<a class="btn btn-default" href="../employee">返回</a>
		</span>
	</div>
    <div class="col-md-12">
	    <form action="../employee" 
	    	class="form-horizontal" 
	    	method="post" 
	    	id="detailForm" 
	    	onsubmit="return submitForm()" >
			<input name="id" id="id" value="${employee.id }" type="hidden"/>
			<input name="user.id" id="userId" value="${employee.user.id }" type="hidden"/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="form-group">
				<label for="inputName" class="col-sm-3 control-label">姓名</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputName" 
						placeholder="用户的姓名" 
						name="user.name"
						required="required"
						value="${employee.user.name }"
						/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputLoginName" 
					class="col-sm-3 control-label">登录名</label>
				<div class="col-sm-9">
					<div class="input-group">
						<input class="form-control" 
							id="inputLoginName" 
							placeholder="用户登录的唯一标识" 
							name="user.loginName" 
							required="required"
							onkeyup="checkLoginName()"
							title="最少需要3个字符"
							value="${employee.user.loginName }"/>
							<div id="loginNameResult" class="input-group-addon alert-warning">
								<span class="glyphicon glyphicon-warning-sign"></span>
							</div>
					</div>
					<div class="clearfix">最少需要3个字符</div>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" 
					class="col-sm-3 control-label">密码</label>
				<div class="col-sm-9">
					<div class="input-group">
						<input class="form-control" 
							id="inputPassword" 
							placeholder="用户密码，最少8位${not empty user ? '，如果不改不要填写' : '' }" 
							name="user.password" 
							type="password"
							onkeyup="passwordOnChange()"/>
						<div id="passwordResult" class="input-group-addon alert-warning">
							<span class="glyphicon glyphicon-warning-sign"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="checkPassword" 
					class="col-sm-3 control-label">确认密码</label>
				<div class="col-sm-9">
					<div class="input-group">
						<input class="form-control" 
							id="checkPassword" 
							placeholder="重复输入一次前面的密码${not empty user ? '，如果不改不要填写' : '' }" 
							name="checkPassword" 
							type="password"
							onkeyup="checkPasswordOnChange()"/>
						<div id="checkPasswordResult" class="input-group-addon alert-warning">
							<span class="glyphicon glyphicon-warning-sign"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="selectBirthday" 
					class="col-sm-3 control-label">生日</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="selectBirthday" 
						placeholder="请选择生日" 
						name="user.birthday" 
						value='<fmt:formatDate value="${employee.user.birthday }" pattern="yyyy-MM-dd"/>'
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label
					class="col-sm-3 control-label">选择角色</label>
				<div class="col-sm-9">
					<%-- 一个用户有多个角色 --%>
					<c:forEach items="${roles }" var="r">
						<c:if test="${not r.fixed }">
							<div class="checkbox col-md-3">
								<label>
									<input type="checkbox" 
										${user.roles.contains(r) ? 'checked="checked"' : '' } 
										name="user.roles[0].id" 
										value="${r.id }" />
									${r.name }(${r.roleKey })
								</label>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label
					class="col-sm-3 control-label">选择岗位</label>
				<div class="col-sm-9">
					<c:forEach items="${positions }" var="r">
						<div class="checkbox col-md-3">
							<label>
								<input type="radio" 
									${employee.position.id eq r.id ? 'checked="checked"' : '' } 
									name="position.id" 
									value="${r.id }" />
								${r.name }
							</label>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label for="selectDepartment" 
					class="col-sm-3 control-label">选择部门</label>
				<div class="col-sm-9">
					<input id="selectDepartment" 
						readonly="readonly" 
						required="required" 
						class="form-control" 
						onclick="showMenu()"/>
					<input id="departmentId" name="department.id" type="hidden" />
				</div>
			</div>
			<div class="form-group">
				<label for="inputPhone" class="col-sm-3 control-label">手机号码</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputPhone" 
						placeholder="手机号码" 
						name="phone"
						required="required"
						value="${employee.phone }"
						/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputAddress" class="col-sm-3 control-label">通讯地址</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputAddress" 
						placeholder="通讯地址" 
						name="address"
						required="required"
						value="${employee.address }"
						/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">保存</button>
					<button type="reset" class="btn btn-default">复位</button>
				</div>
			</div>
		</form>
	</div>
	<!-- 显示树型部门结构 -->
	<div id="menuContent" class="menuContent" 
		style="display:none; 
		position: absolute; 
		background-color: #fff; 
		overflow: auto;
		width: 300px">
		<ul id="departmentTree" class="ztree" style="margin-top:0; height: 100px;"></ul>
	</div>
	
	<script type="text/javascript">
	var departments = ${departments};
	// 如果用user对象，那么需要改变输入框的提示信息
	<c:if test="${not empty user}">
	$(function(){
		setResultStatus("#loginNameResult", 2);
		setResultStatus("#passwordResult", 2);
		setResultStatus("#checkPasswordResult", 2);
	});
	</c:if>

	</script>
	<script type="text/javascript" src="${ctx }/static/js/employee.js"></script>
	<script type="text/javascript" src="${ctx }/webjars/zTree/3.5.28/js/jquery.ztree.all.min.js"></script>
</body>
</html>