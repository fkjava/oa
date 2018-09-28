$(function() {
	// 通过AJAX读取菜单
	$.ajax({
		url : contextPath + "/system/menu/tree",
		success : function(data, status, xhr) {
			// 返回List类型，本身就是一个集合，不需要再使用responseJSON
			// data = data.responseJSON;

			for (var i = 0; i < data.length; i++) {
				// 一级菜单
				var menu = data[i];
				var html = "<span class='nav-sidebar-title'>" + menu.name
						+ "</span>\n";
				html += "<ul class='nav nav-sidebar'>\n";

				var subMenus = menu.children;
				for (var j = 0; j < subMenus.length; j++) {
					var subMenu = subMenus[j];
					html += "    <li><a href='" + contextPath +  subMenu.url + "'>"
							+ subMenu.name + "</a></li>\n";
				}
				html += "</ul>\n"
				// 把生成的HTML追加到菜单的显示位置
				$(html).appendTo($(".menu-tree"));
			}
			// 获取当前的URL
			var url = document.location.href;
			// console.log(url);

			if (url.indexOf("?") > 0) {
				// url有查询字符串，截取掉
				url = url.substring(0, url.indexOf("?"));
			}

			if (url.indexOf(";") > 0) {
				// url里面包含了URL重写的参数
				// 在使用Session的时候，检测到没有cookie，会加上分号、把SessionID加入URL的后面
				url = url.substring(0, url.indexOf(";"));
			}

			// 获取所有的菜单URL，判断当前URL是否以菜单URL开头，如果是则加上class='active'
			var links = $(".menu-tree ul li a");
			for (var i = 0; i < links.length; i++) {
				var href = $(links[i]).attr("href");
				// 检查url里面是否包含了href的值
				if (url.indexOf(href) > 0) {
					$(links[i]).parent().addClass("active");
					break;
				}
			}
		},
		error : function(data, status, xhr) {

		}
	});

	// 解决CSRF的请求头问题
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	// ajaxSend是在发送AJAX之前要调用的代码
	$(document).ajaxSend(function(e, xhr, options) {
		// 当没有Spring Security生效的时候，就没有CSRF的参数
		// 没有参数就不能添加请求头，否则会报错
		if (token && header) {
			xhr.setRequestHeader(header, token);
		}
	});
	
	// 修改排序列的图标
	$(".table thead tr th[onclick]").each(function(){
		// 排序属性
		var name = $("#commonSearchForm input[name='orderBy']").val();
		// 排序方向
		var orderByDirection = $("#commonSearchForm input[name='orderByDirection']").val();
		// 获取列中的排序属性名，通过onclick属性来获取，避免侵入到表格里面
		var propertyName = $(this).attr("onclick");
		
		// 可以改为正则表达式截取
		if( propertyName.indexOf("'") > 0 ){
			propertyName = propertyName.substring( propertyName.indexOf("'") + 1 );
			propertyName = propertyName.substring( 0, propertyName.indexOf("'") );
		}else{
			propertyName = propertyName.substring( propertyName.indexOf('"') + 1 );
			propertyName = propertyName.substring( 0, propertyName.indexOf('"') );
		}

		if($("span", this).length === 0){
			$("<span></span>").appendTo($(this));
		}
		$("span", this).addClass("glyphicon glyphicon-sort");
		if( name === propertyName ){
			$("span", this).removeClass("glyphicon-sort");
			if( orderByDirection === "" || orderByDirection === "asc" ){
				$("span", this).addClass("glyphicon-sort-by-alphabet");
			}else
			{
				$("span", this).addClass("glyphicon-sort-by-alphabet-alt");
			}
		}
	});
	
	// 修改系统的alert函数
	window.alert = function(message){
		$("#alertDailog .message").text(message);
		$("#alertDailog").modal();
	};
});

// 把排序的属性，放入统一的搜索框中，然后提交搜索框
var orderBy = function(propertyName){
	var name = $("#commonSearchForm input[name='orderBy']").val();
	var orderByDirection = $("#commonSearchForm input[name='orderByDirection']").val();
	// 每次点击的时候，都把原本的排序方向反过来
	if( name === propertyName ){
		if( orderByDirection === "" || orderByDirection === "desc" ){
			orderByDirection = "asc";
		}else
		{
			orderByDirection = "desc";
		}
	}else{
		orderByDirection = "asc";
	}
	$("#commonSearchForm input[name='orderBy']").val(propertyName);
	$("#commonSearchForm input[name='orderByDirection']").val(orderByDirection);
	$("#commonSearchForm").submit();
}

