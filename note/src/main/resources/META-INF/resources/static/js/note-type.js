// 绑定复选框的事件，有些复选框是互斥的
$(function(){
	// 选中标记的时候要处理的事件绑定
	var checkFlag = function(){
		// 选中了可修改的，就需要把可撤回的取消
		$("input[name='modifiable']").click(function(){
			// 当前按钮是选中的，那么就需要把可撤回的按钮取消选中
			var checked = $(this).prop("checked");
			if(checked)
			{
				$("input[name='revocable']").prop("checked", false);
			}
		});
		
		// 可删除按钮选中，也是要把可撤回取消
		$("input[name='deletable']").click(function(){
			// 当前按钮是选中的，那么就需要把可撤回的按钮取消选中
			var checked = $(this).prop("checked");
			if(checked)
			{
				$("input[name='revocable']").prop("checked", false);
			}
		});
		

		$("input[name='revocable']").click(function(){
			// 当前按钮是选中的，那么就需要把可撤回的按钮取消选中
			var checked = $(this).prop("checked");
			if(checked)
			{
				$("input[name='deletable']").prop("checked", false);
				$("input[name='modifiable']").prop("checked", false);
			}
		});
	};
	
	// 绑定复选框的事件
	checkFlag();
	
	
	// =================================================
	var showToForm = function(){
		var tr = $(this).parent().parent();
		var id = tr.attr("data-id");
		var name = $("input[name='name']", tr).val();
		var deletable = $("input[name='deletable']", tr).val();
		var modifiable = $("input[name='modifiable']", tr).val();
		var revocable = $("input[name='revocable']", tr).val();
		var number = $("input[name='number']", tr).val();
		
		$("#detailForm #id").val(id);
		$("#detailForm #inputName").val(name);
		$("#detailForm #inputNumber").val(number);
		// 因为在前面获取的三个布尔值，其实是String类型的true或者false
		// 使用===比较的时候，判断是否为'true'，如果值确实为'true'，返回的布尔值就是true
		$("#detailForm input[name='deletable']").prop("checked", deletable === 'true');
		$("#detailForm input[name='modifiable']").prop("checked", modifiable === 'true');
		$("#detailForm input[name='revocable']").prop("checked", revocable === 'true');
	};
	var removeType = function(){
		var tr = $(this).parent().parent();
		var id = tr.attr("data-id");
		var name = $("input[name='name']", tr).val();
		if(!confirm("确定要删除【" + name + "】吗？"))
		{
			return;
		}
		
		$.ajax({
			url: contextPath + "/note/type/" + id,
			method: "delete",
			success: function(data, status, xhr){
				alert(data.message);
				// 重定向
				document.location.href = contextPath + "/note/type";
			},
			error: function(data, status, xhr){
				if(data.responseJSON){
					alert(data.responseJSON.message);
				}else{
					alert(data);
				}
				document.location.href = contextPath + "/note/type";
			}
		});
	};
	// 绑定删除和修改按钮
	$(".show-to-form").click(showToForm);
	$(".remove-type").click(removeType);
});