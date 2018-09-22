// 绑定复选框的事件，有些复选框是互斥的
$(function() {
	// 初始化富文本编辑器
	var E = window.wangEditor;
	if (E) {
		// document.querySelector("#noteContent")
		var editor = new E('#noteContent');
		// 或者 var editor = new E( document.getElementById('editor') )

		// 自定义内容改变事件的处理
		editor.customConfig.onchange = function(html) {
			// console.log(html);
			// 监控变化，同步更新到 textarea
			$("#noteForm #content").val(html);
		};

		// 自定义文件上传的参数
		editor.customConfig.uploadImgServer = '/storage/file/wangEditor';
		editor.customConfig.uploadFileName = 'file';
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		editor.customConfig.uploadImgHeaders = {};
		// 对象.属性 = xxx;
		// 对象[属性] = xxx;
		// 属性名称不确定的时候，使用中括号方式
		editor.customConfig.uploadImgHeaders[header] = token;

		editor.customConfig.uploadImgHooks = {
			// 自定义图片插入的方式
			customInsert : function(insertImg, result, editor) {
				// data[0]表示返回的第一个路径，如果有多个路径需要循环！
				var url = contextPath + result.data[0];
				insertImg(url);
			}
		};

		editor.create();
	}

	// 最简单的JSON就是自己拼接字符串，{属性名称: 属性值...}
	// 优雅一些的做法，使用一些JS控件把整个表单序列化成JSON字符串
	// 使用jquery.serializeJSON框架把表单序列化的JSON字符串，然后再提交到服务器

	$("#noteForm .save-button").click(function() {

		// 把表单转换为JavaScript对象
		var json = $("#noteForm").serializeJSON();
		// 把JavaScript对象转换为JSON字符串
		json = JSON.stringify(json);
		// console.log(json);

		// 把JSON对象发送给服务器
		$.ajax({
			url : contextPath + "/note/maintain",
			method : "post",
			data : json,
			contentType : "application/json;charset=UTF-8",
			success : function(data, status, xhr) {
				if (data.message) {
					alert(data.message);
				} else {
					alert("添加成功");
				}
				document.location.href = contextPath + "/note/maintain";
			},
			error : function(data, status, xhr) {
				alert(data);
			}
		});
	});
});
