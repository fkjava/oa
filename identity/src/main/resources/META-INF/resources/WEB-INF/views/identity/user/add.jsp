<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="/WEB-INF/layouts/main.jsp">
<title>用户列表</title>
<link rel="stylesheet" href="${ctx }/webjars/Eonasdan-bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css"/>

<%-- <script type="text/javascript" src="${ctx }/webjars/momentjs/2.10.3/min/moment.min.js"></script> --%>
<script type="text/javascript" src="${ctx }/webjars/momentjs/2.10.3/min/moment-with-locales.min.js"></script>
<script type="text/javascript" src="${ctx }/webjars/Eonasdan-bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body>
	<div class="col-md-12 sub-header">
		<h2 class="col-md-6">
			<c:choose>
				<c:when test="${not empty user }">
					修改用户
				</c:when>
				<c:otherwise>添加用户</c:otherwise>
			</c:choose>
		</h2>
		<span class="pull-right" style="margin-top: 20px;">
			<a class="btn btn-default" href="../user">返回</a>
		</span>
	</div>
    <div class="col-md-12">
	    <form action="../user" class="form-horizontal" method="post" id="detailForm" onsubmit="return submitForm()" >
			<input name="id" id="id" value="${user.id }" type="hidden"/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="form-group">
				<label for="inputName" class="col-sm-3 control-label">姓名</label>
				<div class="col-sm-9">
					<input class="form-control" 
						id="inputName" 
						placeholder="用户的姓名" 
						name="name"
						required="required"
						value="${user.name }"
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
							name="loginName" 
							required="required"
							onkeyup="checkLoginName()"
							title="最少需要3个字符"
							value="${user.loginName }"/>
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
							name="password" 
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
						name="birthday" 
						value='<fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd"/>'
						required="required"/>
				</div>
			</div>
			<div class="form-group">
				<label for="selectBirthday" 
					class="col-sm-3 control-label">选择角色</label>
				<div class="col-sm-9">
					<%-- 一个用户有多个角色 --%>
					<c:forEach items="${roles }" var="r">
						<c:if test="${not r.fixed }">
							<div class="checkbox col-md-3">
								<label>
									<input type="checkbox" 
										${user.roles.contains(r) ? 'checked="checked"' : '' } 
										name="roles[0].id" 
										value="${r.id }" />
									${r.name }(${r.roleKey })
								</label>
							</div>
						</c:if>
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
	// 设置生日的日期选择框
	$('#selectBirthday').datetimepicker({
		locale: 'zh-cn',
		//format: "L"
		format: "YYYY-MM-DD",
		defaultDate: "1970-01-01"
	});
	
	// 如果用user对象，那么需要改变输入框的提示信息
	<c:if test="${not empty user}">
	$(function(){
		setResultStatus("#loginNameResult", 2);
		setResultStatus("#passwordResult", 2);
		setResultStatus("#checkPasswordResult", 2);
	});
	</c:if>
	
	
	var submitForm = function(){
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
		
		// 判断两次输入的密码要一致
		// 还可以扩展：一边输入密码、一边检测密码的强度
		// 使用onkeyup事件来处理
		var checkResult = false;
		if( $("#passwordResult").parent().hasClass("has-success") ){
			checkResult = true;
		}
		
		if(checkResult && $("#loginNameResult").parent().hasClass("has-success")){
			checkResult = true;
		}
		else{
			checkResult = false;
		}

		//console.log($("#checkPasswordResult.alert-success").length);
		if(checkResult && $("#checkPasswordResult").parent().hasClass("has-success")){
			checkResult = true;
		}
		else{
			checkResult = false;
		}
		// ... 其他验证
		
		// 返回false不提交
		return checkResult;
	};
	
	var setResultStatus = function(selector, status){
		//console.log(selector);
		//console.log(status);
		if( status === 1 ){
			// 确认密码没有输入，应该是警告
			//$(selector).removeClass("alert-danger");
			//$(selector).removeClass("alert-success");
			//$(selector).addClass("alert-warning");
			$(selector).parent().removeClass("has-success");
			$(selector).parent().removeClass("has-error");
			$(selector).parent().addClass("has-warning");
			
			$(selector + " .glyphicon").removeClass("glyphicon-ok");
			$(selector + " .glyphicon").removeClass("glyphicon-remove");
			$(selector + " .glyphicon").addClass("glyphicon-warning-sign");
		}else if( status === 2 ){
			//$(selector).removeClass("alert-danger");
			//$(selector).removeClass("alert-warning");
			//$(selector).addClass("alert-success");
			$(selector).parent().removeClass("has-warning");
			$(selector).parent().removeClass("has-error");
			$(selector).parent().addClass("has-success");
			
			$(selector + " .glyphicon").removeClass("glyphicon-warning-sign");
			$(selector + " .glyphicon").removeClass("glyphicon-remove");
			$(selector + " .glyphicon").addClass("glyphicon-ok");
		}else{
			//$(selector).removeClass("alert-success");
			//$(selector).removeClass("alert-warning");
			//$(selector).addClass("alert-danger");
			$(selector).parent().removeClass("has-success");
			$(selector).parent().removeClass("has-warning");
			$(selector).parent().addClass("has-error");
			
			$(selector + " .glyphicon").removeClass("glyphicon-warning-sign");
			$(selector + " .glyphicon").removeClass("glyphicon-ok");
			$(selector + " .glyphicon").addClass("glyphicon-remove");
		}
	}
	var passwordOnChange = function(){
		// 获取两个密码框的值
		var password = $("#inputPassword").val();
		if( password.length >= 8 ){
			// ok
			setResultStatus("#passwordResult", 2);
		}else if( password.length === 0 ){
			// 警告
			setResultStatus("#passwordResult", 1);
		}else{
			// 错误
			setResultStatus("#passwordResult", 3);
		}
	};
	
	var checkPasswordOnChange = function(){
		// 获取两个密码框的值
		var password = $("#inputPassword").val();
		var checkPassword = $("#checkPassword").val();
		//console.log(password);
		//console.log(checkPassword);
		// 判断是否一样
		// 如果一样，则打钩；否则打叉
		if( checkPassword.length == 0 ){
			// 确认密码没有输入，应该是警告
			setResultStatus("#checkPasswordResult", 1);
		}else if( password === checkPassword ){
			setResultStatus("#checkPasswordResult", 2);
		}else{
			setResultStatus("#checkPasswordResult", 3);
		}
	};
	
	// 检查登录名是否被占用
	var checkLoginName = function(){
		// id在修改的时候才有
		var id = $("#id").val();
		var loginName = $("#inputLoginName").val();
		if(loginName.length < 3){
			return;
		}
		
		$.ajax({
			url: "../user/checkLoginName/" + loginName + "/" + id,
			method: "get",
			success: function(data, status, xhr){
				// 返回一个结果，通过结果来判断是否被占用
				setResultStatus("#loginNameResult", data.status);
			},
			error: function(data, status, xhr){
				alert("检查登录名出现问题：\n" + data.responseJSON.message);
			}
		});
	};
	
	</script>
</body>
</html>