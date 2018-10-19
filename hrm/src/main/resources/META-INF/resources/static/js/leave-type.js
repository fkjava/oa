// 绑定复选框的事件，有些复选框是互斥的
$(function(){
	// =================================================
	var showToForm = function(){
		var tr = $(this).parent().parent();
		var id = tr.attr("data-id");
		var name = $("input[name='name']", tr).val();
		
		$("#detailForm #id").val(id);
		$("#detailForm #inputName").val(name);
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
			url: contextPath + "/hrm/leave/type/" + id,
			method: "delete",
			success: function(data, status, xhr){
				alert(data.message);
				// 重定向
				//document.location.href = contextPath + "/hrm/leave/type";
				$(tr).remove();
			},
			error: function(data, status, xhr){
				if(data.responseJSON){
					alert(data.responseJSON.message);
				}else{
					alert(data);
				}
				//document.location.href = contextPath + "/hrm/leave/type";
			}
		});
	};
	// 绑定删除和修改按钮
	$(".show-to-form").click(showToForm);
	$(".remove-type").click(removeType);
});