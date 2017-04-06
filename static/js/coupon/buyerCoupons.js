  
$(document).ready(function() {
	
});

	
function changeState(){
	$('#pageForm').attr("action", "");
	$("#currentPage").val(1);
	$("#pageForm").attr( {
		action : "/buyerCoupon/buyerCoupon.htm",
		method : "post"
	}).submit();
}

function toUseCoupon(shopId){
	$("#pageForm").attr( {
		action : "http://shop"+shopId+".pinju.com",
		target :"_blank",
		method : "post"
	}).submit();
}
	



function closeCoupon(couponId){
	if(!confirm("您确认要删除这张优惠券吗？")){return;}
	$.ajax({
		type: "POST",
		url: "/buyerCoupon/buyerCloseCoupon.htm?couponId="+couponId,
		dataType: 'xml',
		success: function(msg){ 
			if($(msg).find("isSuccess").text()== "true")
			{
				alert("操作成功！");
				window.location.href="/buyerCoupon/buyerCoupon.htm";
			}
			else
			{
				alert($(msg).find("errorConstant").text());
			}
		} 
	}); 
}
	
	
		