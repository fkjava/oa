<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <title>捷途智能OA--<sitemesh:write property="title"/></title>

   <link href="${ctx }/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${ctx }/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${ctx }/static/css/main.css" rel="stylesheet">
    <script src="${ctx }/static/js/ie-emulation-modes-warning.js"></script>

	<script type="text/javascript" src="${ctx }/webjars/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<%-- _csrf_header、_csrf是为了解决AJAX结合Spring Security的时候，出现403错误的。 --%>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<meta name="_csrf" content="${_csrf.token}"/>
	<sitemesh:write property="head"/>
  </head>

  <body>
	<!-- 导航条开始 -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.html#">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="index.html#">Dashboard</a></li>
            <li><a href="index.html#">Settings</a></li>
            <li><a href="index.html#">Profile</a></li>
            <li><a href="index.html#">Help</a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>
    <!-- 导航条结束 -->

	<!-- 内容主体 -->
    <div class="container-fluid">
      <div class="row">
      	<!-- 侧边栏 -->
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li class="active"><a href="${ctx }/identity/role">角色管理 <span class="sr-only">(current)</span></a></li>
            <li><a href="${ctx }/identity/user">用户管理</a></li>
            <li><a href="${ctx }/system/menu">菜单配置</a></li>
          </ul>
        </div>
        
        <!-- 业务展示主体 -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <sitemesh:write property="body"/>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="${ctx }/static/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="${ctx }/static/js/ie10-viewport-bug-workaround.js"></script>
    
    <script type="text/javascript">
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        // ajaxSend是在发送AJAX之前要调用的代码
        $(document).ajaxSend(function(e, xhr, options) {
        	if( token && header){
    	    	xhr.setRequestHeader(header, token);
        	}
        });
    });
    </script>
  </body>
</html>