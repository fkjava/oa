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
            <li><a href="index.html#">Help</a></li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- 导航条结束 -->

	<!-- 内容主体 -->
    <div class="container-fluid">
      <div class="row">
        
        <!-- 业务展示主体 -->
        <div class="col-sm-12 main">
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
  </body>
</html>