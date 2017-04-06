$(document).ready(function(){
	$("#errorDiv").hide();
	
	$('#backToOrderList').click(function(){
		window.location.href = "http://www.pinju.com/orderBuyer/orderManager.htm";
	});
	
	$('.refresh').click(function(){
		$("#refreshFrom").submit();
	});
		
});

function f_postPay(){
	var orderId = $("input[name=orderId]");
	var details = "&all";
	for(var i = 0; i < orderId.length; i++){
		var id = $(orderId[i]).val();
		details += "&orderId=".concat(id);
	}
	if(ajaxUpdate(details)){
		$("#btnPay").hide();
		$("#jiPay").show();
		$("#payForm").submit();
	}
}
	
function f_refresh(){
	$("#refreshFrom").submit();
}
	
var ajaxUpdate = function(details){
	var result = true;  
	var sid = $("#sid").val();
	var code = $("#validateCode").val();
	var total = $("#totalFee").val();
	$.ajax({
		type: "post",
		timeout: 15000, 
		cache: false,
		dataType: 'json',
		async : false,
		url: "/orderBuyer/ajaxupdateorder.htm?totalFee=".concat(total).concat(details),
		success: function(responseData){
			if(responseData != null && responseData!=""){
			   if(responseData["error"]!=undefined){
				   $("#btnPay").hide();
				   $("#jiPay").show();
				   $("#errorDiv").html("<p style=\"color:red\">网络原因，请求未被处理，请<a style=\"font-weight:bold;\" href=\"#\" onclick=\"f_refresh()\">刷新</a>当前页面，再重试！<p>")
				   $("#errorDiv").show();
				   result = false;
			   }else if(responseData["input"]!=undefined){
				   $("#btnPay").hide();
				   $("#jiPay").show();
				   $("#errorDiv").html("<p style=\"color:red\">网络原因，请求未被处理，请<a style=\"font-weight:bold;\" href=\"#\" onclick=\"f_refresh()\">刷新</a>当前页面，再重试！<p>")
				   $("#errorDiv").show();
				   result = false;
			   }else if(responseData["pricerror"]!=undefined){
				   $("#btnPay").hide();
				   $("#jiPay").show();
				   $("#errorDiv").hide();
				   alert("订单价格有变化，请重新确认后继续支付！");
				   f_refresh();
				   result = false;
			   }
			}else{
			   $("#btnPay").hide();
			   $("#jiPay").show();
			   $("#errorDiv").html("<p style=\"color:red\">网络原因，请求未被处理，请<a style=\"font-weight:bold;\" href=\"#\" onclick=\"f_refresh()\">刷新</a>当前页面，再重试！<p>")
			   $("#errorDiv").show();
			   result = false;
			}
		},
		error: function() {
		  $("#btnPay").hide();
		  $("#jiPay").show();
	      $("#errorDiv").html("<p style=\"color:red\">网络原因，请求未被处理，请<a style=\"font-weight:bold;\" href=\"#\" onclick=\"f_refresh()\">刷新</a>当前页面，再重试！<p>")
	      $("#errorDiv").show();
	      result = false;
		}
	});
	return result;
}