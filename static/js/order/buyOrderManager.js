$(document).ready(function() {
	var orderState = $('#orderState').val();
	if(orderState == 1){	
		$("#deadline").countdown({
			attrName : 'leftDay',
			tmpl : '<span>%{d}</span>天<span>%{h}</span>小时<span>%{m}</span>分<span>%{s}</span>秒',
			afterEnd : function() {
				$('#deadlinePay').html("<font color='red'>逾期交易，交易已自动关闭</font>");
			}
		});
	}

	if(orderState == 3){
		$("#deadline").countdown({
			attrName : 'leftDay',
			tmpl : '<span>%{d}</span>天<span>%{h}</span>小时<span>%{m}</span>分<span>%{s}</span>秒',
			afterEnd : function() {
				$('#deadLineReceive').html("<font color='red'>超时确认收货，系统默认买家确认收货</font>");
			}
		});
	}	
});


function closeSell(orderId){
	$.ajax({
		type: "POST",
		url: "/orderBuyer/orderCancelCheck.htm?cancelOrderId="+orderId,
		dataType: 'xml',
		success: function(msg){ 
			if($(msg).find("isSuccess").text()== "true")
			{	
				var dialog = art.dialog({id: 'N3690',title: false,height: '120px',width: '400px'});
				var content='';
				content +='<p id="inputError" class="hong"></p>';
				content +='<p>您确定要取消该订单吗？取消订单后，不能恢复。</p><br />';
				content +='<strong>请选择取消订单的理由：</strong><br />';
				content +='<select id="failReason" name="failReason"><option value="">请选择关闭理由</option><option value="1001">我不想买了</option><option value="1002">信息填写错误，重新下单</option><option value="1003">卖家缺货</option><option value="1004">同城见面交易</option><option value="1005">其他原因</option></select>';

				content +='<script type="text/dialog">';
				content +="var api = this; ";
				content +="api.title('取消订单').button({name: '确定',callback: function ()";
				content +="{var failreason = $('#failReason').val(); if(failreason == null || failreason == ''){art.dialog({content: '请选择关闭理由！', lock: true,ok:true,height: '60px',width: '200px',icon:'warning',padding:'0 80px 0 0'});return false;}";
				content +="else{$.ajax({type:'post',url:'/orderBuyer/orderCancel.htm?cancelOrderId="+orderId+"&failReason='+$('#failReason').val(),dataType:'xml',success:function(msg){if($(msg).find('isSuccess').text()=='true'){window.location.reload();}else{alert($(msg).find('errorConstant').text());window.location.reload();}}});}},";
				content +="focus: true},{name: '取消'}).lock();";
				content +=' </script>';
				dialog.content(content);
			}
			else
			{
				alert($(msg).find("errorConstant").text());
				window.location.reload();
			}
		} 
	}); 
}

function checkRefundState(){
	var isRefund = $("#isRefund").val();
	if(isRefund == 10002){
		alert("您有商品正在退款，请完成退款或关闭退款后再确认收货!");
		return false;
	}

	$('#affirmPay').submit();
}

