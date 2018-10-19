var setDateTimePicker = function() {
	$('input[name$="Time"]').each(function(){
		$(this).attr("readonly", false);
	});
	// 绑定所有Time结尾的输入框，让它们能够选择日期时间
	$('input[name$="Time"]').datetimepicker({
		locale: 'zh-cn',
		//format: "L"
		format: "YYYY-MM-DD HH:mm",
		defaultDate: new Date()
	});
};

var setDatePicker = function() {
	// ~= 表示包含
	// $= 以X值结尾
	// ^= 以X值开头
	$('input[name$="Date"]').each(function(){
		$(this).attr("readonly", false);
	});
	// 绑定所有Date结尾的输入框，让它们能够选择日期
	$('input[name$="Date"]').datetimepicker({
		locale: 'zh-cn',
		//format: "L"
		format: "YYYY-MM-DD",
		defaultDate: new Date()
	});
};
// 在提交表单的时候，写在form的onsubmit事件中
var checkOnSubmit = function(){
	try
	{
		// 调用业务表单的方法
		return checkForm();
	}catch(e){
		// 如果业务表单没有checkForm方法，直接出现异常，此时表示不需要表单验证
		return true;
	}
};

// 给业务表单使用的，用于解决提交多个记录的时候，替换中括号里面的数字
var setInputName = function(){
	// 1.获取所有的行
	var trs = $(".data-list tbody tr");
	trs.each(function(index, element){
		//console.log(index);
		//console.log(element);
		var tr = $(element);
		
		// 2.获取行里面所有的输入框（input、select、textarea）
		try{
			var inputs = $("input", tr);
			var selects = $("select", tr);
			var textareas = $("textarea", tr);
			// 把所有的输入框都合并到一个集合
			selects.each(function(){
				inputs.push(this);
			});
			textareas.each(function(){
				inputs.push(this);
			});
			// 3.判断这些输入框里面name属性如果有中括号，需要把里面的数字替换成行的索引（0开始的）
			inputs.each(function(i, ele){
				var name = $(ele).attr("name");
				if(/\[\d+\]/.test(name)){
					name = name.replace(/\[\d+\]/, "[" + index + "]");
					$(ele).attr("name", name);
				}
			});
		}catch(e){
			console.error(e);
		}
	});
	return true;
};

