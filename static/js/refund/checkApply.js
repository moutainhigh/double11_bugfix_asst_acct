$(document).ready(function(){
	
	//倒计时
	$('#deadline').each(function(){
		showLeftDay(this);
	});
	
	//确认提交
	$('#hrefSaveLeaveWord').click(function(){
		if($('#texLeave').attr("value") == ""){
			alert("请填写留言内容！");
			return;
		}

		if($('#texLeave').attr("value").length > 300){
			alert("留言内容长度超过300");
			return;
		}
		
		$('#form').submit();
	});

});

function check(refundId,orderState) {
	if(orderState == 2){
		alert("卖家还未发货，您无法确认收货！");
		return;
	}

	if(!confirm("请确认您已收到商品并验收无误，确认收货将关闭退款申请并完成交易！")) return;
	
	$("#form1").attr( {
		action : "${base}/refund/confirmReceiveGoods.htm?refundId="+refundId,
		method : "post"
	}).submit();
}