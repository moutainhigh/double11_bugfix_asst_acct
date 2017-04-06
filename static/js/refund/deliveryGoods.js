$(document).ready(function(){
	//倒计时
	$('#deadline').each(function(){
		showLeftDay(this);
	});
	
	//Loading Wuliu Infomation
	$("#loadWuliuInfo").click(function(){
		if($("#loadWuliuInfo").text()=="修改"){
			$("#tableId tbody").find("tr").last().show();
			$("#loadWuliuInfo").text("取消");
		}else{
			$("#tableId tbody").find("tr").last().hide();
			$("#loadWuliuInfo").text("修改");
		}
	});
	
	//修改物流
	$("#updateWuliu").click(function(){
		if ($("#logisticsId").val()== "") {
			alert("请选择退货物流");
			return;
		}
		//填写号码
		if($('#logisticsNumber').attr("value") == ""){
			alert("请填写运单号码！");
			return;
		}
		//$('#form').submit();
		
		var buyerMemo=$("#buyerMemo").val();
		var logisticsId=$("#logisticsId").val();
		var logisticsName=$("#logisticsId").find("option:selected").text();
		var logisticsNumber=$("#logisticsNumber").val();
		var refundId=$("#refundId").val();
		
		var url ="/refund/updateGoodsWuliuInfo.htm";
		$.get(url,{"buyerMemo":buyerMemo,"logisticsId":logisticsId,
				   "logisticsNumber":logisticsNumber,"logisticsName":logisticsName,"refundId":refundId}
		,function(data){
			if(data.message=="0"){
				alert(tipMessage[0]);
			}else{
				var tr=$("#tableId tbody").find("tr").eq(2);
				var a='<a href="';
				a+='/orderSeller/logisticsInfo.htm?cd=';
				a+=logisticsNumber+"&exp=";
				a+=data.tradeRefundLogisticsDO["logisticsId"];
				a+=" target=_blank>";
				a+=data.tradeRefundLogisticsDO["logisticsNumber"];
				a+="</a>"
			     tr.children('td').eq(0).html(data.tradeRefundLogisticsDO["logisticsName"]);
			     tr.children('td').eq(1).html(a);
			     tr.children('td').eq(2).html(data.tradeRefundLogisticsDO["buyerMemo"]);
			     alert(tipMessage[1]);
			     $("#loadWuliuInfo").click();
			}
		});
		
	});
	//******************
});
var tipMessage=new Array();
tipMessage[0]="更新退款物流信息失败,请稍后再试！";
tipMessage[1]="退款物流信息更新成功！";
//确认提交
function DeliveryGoods(){
	var noLogisticsId = false;
	$("#logisticsId").find("option").each(function() {
		if($(this).attr("selected") && $(this).attr("value") == "" ){
			noLogisticsId = true;
			alert("请选择退货物流");return;
		}
	});
	if(noLogisticsId) return;
	//填写号码
	if($('#logisticsNumber').attr("value") == ""){
		alert("请填写运单号码！");
		return;
	}
	$('#form').submit();
}
