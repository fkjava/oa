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