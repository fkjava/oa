// 绑定复选框的事件，有些复选框是互斥的
$(function() {
	$(".table-hover > tbody > tr").click(function(){
		var id = $(this).attr("data-id");
		document.location.href = contextPath + "/note/read/" + id;
	});
	
	// 使用一个倒计时，时间到了以后就修改已读的按钮状态，改为可以点击
	var counter = 10;
	var clickCounter = setInterval(function(){
		counter --;
		if( counter <= 0 )
		{
			// 取消计时
			clearInterval(clickCounter);
			$(".btn-readed").attr("disabled", false);
			$(".btn-readed").prop("disabled", false);
			$(".btn-readed").text("已读");
		}else{
			var text = "已读(" + counter + ")";
			$(".btn-readed").text(text);
		}
	}, 1000);
});
