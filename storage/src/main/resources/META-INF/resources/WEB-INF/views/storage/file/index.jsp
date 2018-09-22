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
	<!-- 上传文件的表单 -->
	<!-- 在action没有写值的情况下，其实就是上传/提交到当前URL -->
	<!-- 现在访问index.jsp的完整URL= http://127.0.0.1:8080/storage/file -->
	<form action="" method="post" enctype="multipart/form-data">
		<input name="file" type="file"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<button type="submit">上传</button>
	</form>
	<!-- 显示文件列表 -->
	<form action="" method="get">
		文件名<input name="name" value="${param.name }"/><br/>
		排序：
		<label>文件名<input type="radio" name="orderBy" value="name" ${param.orderBy eq 'name' ? "checked='checked'" : "" }/></label>
		<label>上传时间<input type="radio" name="orderBy" value="uploadTime" ${param.orderBy eq 'uploadTime' ? "checked='checked'" : "" }/></label><br/>
		<button type="submit">搜索</button>
	</form>
	<table>
		<thead>
			<tr>
				<th>文件名</th>
				<th>文件类型</th>
				<th>文件大小</th>
				<th>上传时间</th>
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
				<td colspan="5">
					<my:spring-page url="?name=${param.name }&orderBy=${param.orderBy }"/>
				</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>