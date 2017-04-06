$(document).ready(function(){
	//处理<a href="#" name="coupon" bid="144">点击领取</a>
	$("a[name=coupon]").bind("click", function() {
		var bid = $(this).attr("bid");
		
		getCoupon(bid);
	});
	
	
	//处理http://www.pinju.com/1234.coupon
	$("a[href$=coupon]").each(function(){
		var _href = $(this).attr("href");
		_href = _href.replace("http://www.pinju.com/", "");
		var bid = _href.replace(".coupon", "");
		$(this).attr("href", "javascript:void(0);");
		$(this).attr("target", "_self");
		$(this).attr("name", "coupon");
		$(this).attr("bid", bid);
		
		$(this).bind("click", function() {
			getCoupon(bid);
		});
		
	});
	
	
});


function processResult(message, isSuccess){
	var _icon = "";
	if(isSuccess){
		_icon = "succeed";
	}else{
		_icon = "error";
	}

	var dialog = art.dialog({
					title: '信息提示',
					content: message,
					icon: _icon,
					lock: true,
					button: [
						{
							name: '关闭提示',
							focus: true
						},
						{
							name: '查看我的优惠券',
							callback: function () {
								window.location.href = "http://www.pinju.com/buyerCoupon/buyerCoupon.htm";
								return false;
							}
						}
					]

				});
}

function getCoupon(bid){
	var couponUrl = "http://www.pinju.com/async/coupon/buyerGet.htm?bid=" + bid;
	
	$.ajax({
		method: "POST",
		url:couponUrl,
		dataType: "JSONP",
		jsonp: "callBack",
		cache: false,
		success:function(data){
			if(data[0]){
			
				if(!data[0].isLogin){
					var _href = encodeURIComponent(window.location.href);
					window.location.href = "http://www.pinju.com/member/login.htm?returnUrl=" + _href;
					return;
				}
				
				var message = "";
				
				if(data[0].isSuccess){
					message = "优惠券领取成功";
				}else{
					message = data[0].errorMessage;
				}
				
				processResult(message, data[0].isSuccess);
			}
			
		}
	});
}