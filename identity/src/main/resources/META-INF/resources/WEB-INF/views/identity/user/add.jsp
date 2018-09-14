<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>用户列表</title>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">添加用户</h2>
		<span class="pull-right" style="margin-top: 20px;">
			<a class="btn btn-default" href="../user">返回</a>
		</span>
	</div>
    <div class="col-md-12">
	    <form action="../user" class="form-horizontal" method="post" id="detailForm" onsubmit="return setRolesName( this )">
			<input name="id" id="id" value="" type="hidden"/>
			<div class="form-group">
				<label for="inputName" class="col-sm-3 control-label">姓名</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputName" 
						placeholder="用户的姓名" 
						name="name"
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputLoginName" 
					class="col-sm-3 control-label">登录名</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputLoginName" 
						placeholder="用户登录的唯一标识" 
						name="loginName" 
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" 
					class="col-sm-3 control-label">密码</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputPassword" 
						placeholder="用户密码，最少8位" 
						name="password" 
						type="password"
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="checkPassword" 
					class="col-sm-3 control-label">确认密码</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="checkPassword" 
						placeholder="重复输入一次前面的密码" 
						name="checkPassword" 
						type="password"
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="selectBirthday" 
					class="col-sm-3 control-label">生日</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="selectBirthday" 
						placeholder="请选择生日" 
						name="birthday" 
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="selectBirthday" 
					class="col-sm-3 control-label">选择角色</label>
				<div class="col-sm-9">
					<%-- 一个用户有多个角色 --%>
					<c:forEach items="${roles }" var="r">
					<div class="checkbox col-md-3">
						<label>
							<input type="checkbox" name="roles[0].id" value="${r.id }" />
							${r.name }(${r.roleKey })
						</label>
					</div>
					</c:forEach>
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
	<script type="text/javascript">
	var setRolesName = function(form){
		// 找到所有选中的roles的输入框
		// CSS选择器，找到值是以某些字符开头的
		var roles = $("input[type='checkbox'][name^='roles']:checked");
		//console.log(roles);
		// 把中括号里面的0，替换成0、1、2
		roles.each(function(index, input){
			//console.log(input);
			// roles[0].id
			var regex = /\d+/;
			var name = $(input).attr("name");
			name = name.replace(regex, index);
			//console.log(name);
			//console.log(regex.test(name));
			$(input).attr("name", name);

			//$(input).attr("name", "roles["+index+"].id");
		});
		
		return true;
	};
	</script>
</body>
</html>