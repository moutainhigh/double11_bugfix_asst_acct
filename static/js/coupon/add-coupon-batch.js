Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

$().ready(function() {
	couponChange();
	var date = new Date();
	date.setDate(date.getDate()+1);
	var sdate = date.format("yyyy-MM-dd");
	$("input[name='tcb.couponInvalidDate']").val(sdate);
});

//jQuery.fn.outer = function() {
//	return $($("div[name='couponDiv']").html(this.clone())).html();
//}
jQuery.fn.outerHtml = function(oh){
   return $('<div></div>').append($("div[name='couponDiv']").clone()).html();
}

$(function(){
	$(".yl-list>li").click(function(){
		var i=$(this).index()+1;
		$("#yh-box").css("background-image","url(http://static.pinju.com/images/yh-bg"+i+".gif)");
		$("input[name='tcb.skinNum']").val("http://static.pinju.com/images/yh-bg"+i+".gif");
	})
});

function moneyChange() {
	var money = $("input[name='tcb.couponMoney']").val();
	if (/^(\d+)$/.test(money)) {
		$("input[name='tcb.useCondition']").val(parseInt(money)+1);
	}
}

function couponChange() {
	var money = $("input[name='tcb.couponMoney']").val();
	var cond = $("input[name='tcb.useCondition']").val();
	var spread = $("select[name='tcb.spreadShow']").val();
	var restrict = $("select[name='tcb.restrictAmount']").val();
	var relcount = $("input[name='tcb.releaseCount']").val()==""?"0":$("input[name='tcb.releaseCount']").val();
	if (/^(\d+)$/.test(money)) {
		$("#money").text(money);
	}
	
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
	couponChange();
	if (validate()) {
		var divCode = $("div[name='couponDiv']").outerHtml();
		$("input[name='tcb.couponCode']").val(divCode);
		$("#frm").attr( {
			action : "/coupon/saveCouponBatch.htm",
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

function validate() {
	var money = $("input[name='tcb.couponMoney']").val();
	var invdate = $("input[name='tcb.couponInvalidDate']").val();
	var relcount = $("input[name='tcb.releaseCount']").val();
	var cond = $("input[name='tcb.useCondition']").val();
	
	var moneyflag = false;
	var invdateflag = false;
	var relcountflag = false;
	var condflag = false;
	
	if (money == "") {
		$("#couponMoneyg").hide();
		$("#couponMoney").show();
		$("#couponMoney").text("优惠券面额不能为空！");
		moneyflag = false;
	} else if (!/^(\d+)$/.test(money)) {
		$("#couponMoneyg").hide();
		$("#couponMoney").show();
		$("#couponMoney").text("优惠券面额只能是1-1000的整数！");
		moneyflag = false;
	} else if (money <= 0 || money > 1000) {
		$("#couponMoneyg").hide();
		$("#couponMoney").show();
		$("#couponMoney").text("优惠券面额请设置在1-1000元范围内！");
		moneyflag = false;
	} else {
		$("#couponMoneyg").show();
		$("#couponMoney").hide();
		moneyflag = true;
	}
	
	if (invdate == "") {
		$("#invalidDate").show();
		$("#invalidDate").text("请选择优惠券有效期！");
		invdateflag = false;
	} else {
		$("#invalidDate").hide();
		invdateflag = true;
	}
	
	if (relcount == "") {
		$("#releaseCountg").hide();
		$("#releaseCount").show();
		$("#releaseCount").text("发放总量不能为空！");
		relcountflag = false;
	} else if (!/^(\d+)$/.test(relcount)) {
		$("#releaseCountg").hide();
		$("#releaseCount").show();
		$("#releaseCount").text("发放总量只能是1-10000的整数！");
		relcountflag = false;
	} else if (relcount <= 0 || relcount > 10000) {
		$("#releaseCountg").hide();
		$("#releaseCount").show();
		$("#releaseCount").text("发放总量请设置在1-10000张范围内！");
		relcountflag = false;
	} else {
		$("#releaseCountg").show();
		$("#releaseCount").hide();
		relcountflag = true;
	}
	
	if (cond == "") {
		$("#useConditiong").hide();
		$("#useCondition").show();
		$("#useCondition").text("使用条件不能为空！");
		condflag = false;
	} else if (!/^(\d+)$/.test(cond)) {
		$("#useConditiong").hide();
		$("#useCondition").show();
		$("#useCondition").text("只能是1-9999的整数！");
		condflag = false;
	} else if (cond < 0 || cond > 9999) {
		$("#useConditiong").hide();
		$("#useCondition").show();
		$("#useCondition").text("设置在1-9999元范围内！");
		condflag = false;
	} else if (parseInt(cond) <= parseInt(money)) {
		$("#useConditiong").hide();
		$("#useCondition").show();
		$("#useCondition").text("必须大于优惠券面额！");
		condflag = false;
	} else {
		$("#useConditiong").show();
		$("#useCondition").hide();
		condflag = true;
	}
	
	return moneyflag && invdateflag && relcountflag && condflag;
} 