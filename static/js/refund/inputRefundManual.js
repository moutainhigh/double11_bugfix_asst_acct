$(function() {
	//提交
	$('#confirmSaveRefundManual').click(function(){
		if($('#bankUsername').attr("value") == ""){
			alert("请输入银行户名");
			return;
		}
		
		if($('#bankName').attr("value") == ""){
			alert("请输入开户行");
			return;
		}
		
		if($('#bankAccount').attr("value") == ""){
			alert("请输入银行账号");
			return;
		}
		
		
		$('#failForm').submit();
	});
});
