$().ready(function() {
	couponChange();
});

jQuery.fn.outerHtml = function(oh){
   return $('<div></div>').append($("div[name='couponDiv']").clone()).html();
}

function couponChange() {
	var money = $("input[name='tcouponMoney']").val();
	var cond = $("input[name='tuseCondition']").val();
	
	var spread = $("select[name='tcb.spreadShow']").val();
	var restrict = $("select[name='tcb.restrictAmount']").val();

	var relcount = $("input[name='tcb.releaseCount']").val();
	
	var incrcount = $("input[name='increaseReleaseCount']").val();

	if (/^(\d+)$/.test(incrcount)) {
		relcount = parseInt(relcount)+parseInt(incrcount);
	}

	$("#money").text(money);

	if (spread == 10) {
		if (restrict == 0) {
			$("#spread").text("单笔订单满["+cond+"]元可使用此优惠券一张，限量发行["+relcount+"]张");
		} else {
			$("#spread").text("单笔订单满["+cond+"]元可使用此优惠券一张，限量发行["+relcount+"]张，每人限领["+restrict+"]张");
		}
	} else {
		$("#spread").text("限量发行["+relcount+"]张");
	}
}

function isSubmit() {
	if (validate()) {
		var divCode = $("div[name='couponDiv']").outerHtml();
		$("input[name='tcb.couponCode']").val(divCode);
		
		$("#frm").attr( {
			action : "/coupon/updateCouponBatch.htm",
			method : "post"
		}).submit();
	}
}

function cancel() {
	$("#goback").attr( {
		action : "/coupon/couponBatchAll.htm",
		method : "post"
	}).submit();
}

$(function(){
	$(".yl-list>li").click(function(){
		var i=$(this).index()+1;
		$("#yh-box").css("background-image","url(http://static.pinju.com/images/yh-bg"+i+".gif)");
		$("input[name='tcb.skinNum']").val("http://static.pinju.com/images/yh-bg"+i+".gif");
	})
});

function validate() {
	var releaseCount = $("input[name='increaseReleaseCount']").val();
	var tcbReleaseCount = $("input[name='tcb.releaseCount']").val();
	
	var releaseCountflag = false;
	
	if (releaseCount != "") {
		if (!/^(\d+)$/.test(releaseCount)) {
			$("#condition").show();
			$("#condition").text("增加发放数量只能是整数！");
			releaseCountflag = false;
		} else if ((parseInt(releaseCount)+parseInt(tcbReleaseCount)) > 10000) {
			$("#condition").show();
			$("#condition").text("原发放数量和增加发放数量相加不能超过10000！");
			releaseCountflag = false;
		} else {
			$("#condition").hide();
			releaseCountflag = true;
		}
	} else {
		$("#condition").hide();
		releaseCountflag = true;
	}
	
	return releaseCountflag;
}