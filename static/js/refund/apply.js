$(function() {
	$.fn.numeral_apply = function(opts) {
		opts = jQuery.extend( {
			decimalPlace : 0,
			maxLength : 30
		}, opts || {});
		$(this).css("ime-mode", "disabled");
		this.bind("keypress", function(e) {
			var code = (e.keyCode ? e.keyCode : e.which);
			if (!$.browser.msie && (e.keyCode == 0x8)) {
				return ;
			}
			if (code == 37 || code == 39) {
				return;
			}
			if ($(this).val().length >= opts.maxLength) {
				return false;
			}
			if(code == 46 && ($(this).val().indexOf("."))!= -1){
				return false;
			}
			
			if (opts.decimalPlace == 0) {
				return (code >= 48 && code <= 57) || code == 46;
			} else {
				if (code == 46 && this.value == "") {
					return false;
				}
				if (code == 46 && this.value.length + 1 == opts.maxLength) {
					return false;
				}
				return (code >= 48 && code <= 57) || code == 46;
			}
		});
		this.bind("blur", function() {
			if (this.value.length==0) return false;
			if(this.value==0) this.value="";
			var d = $(this).val().indexOf(".");
			if (isNaN(this.value)){
				this.value = "";
			}else if (d == -1 && this.value !=0) {
				this.value = this.value+".00";
			} else if(d != -1 && $(this).val().split(".")[1].length == 0){
				this.value = this.value+"00";
			}else if(d != -1 && $(this).val().split(".")[1].length >= 3){
				this.value=this.value.substring(0,d+3);
			}else if(d != -1 && $(this).val().split(".")[1].length == 1){
				this.value=this.value+"0";
			}
		});
		this.bind("paste", function() {
			var s = clipboardData.getData('text');
			if (isNaN(s)) return false;
			return false;
		});
		this.bind("dragenter", function() {
			return false;
		});
		
		this.bind("keyup", function() {
			if (opts.decimalPlace > 0) {
				var d = $(this).val().indexOf(".");
				if (d != -1 && $(this).val().length - d > opts.decimalPlace + 1) {
					this.value = this.value.substring(0, d + opts.decimalPlace + 1);
				}
			}
		});
	};
});


$(document).ready(function(){
	$("#applySum").each(function(){
		$(this).numeral_apply({
			decimalPlace:0, 
	 		maxLength:14
		});
		
	});
	initLeaveWord();
	$("#applySum").each(function(){
		$(this).blur(function() {
			var value = $(this).val();
			var xiaoji = $("#xiaoji").val();
			if(value){
				value = parseFloat(value);
				var _val = value+"";
				if(_val.indexOf(".") != -1 && _val.split(".")[1].length == 1){
					_val = _val+"0";
				}
				$(this).val(_val);
				var orifinal = $("#originalApplySum").val();
				if(eval(value) > eval(xiaoji)){
					var tip = "您申请的退款金额"+value+"元已经超过" + xiaoji + "元";
					popdialog(tip);
					if(orifinal == "0.00" ||orifinal == "" ){
						$(this).val(xiaoji);
						var needPaySum = $("#needPayMax").val();
						var a = (eval(needPaySum) - eval($(this).val())).toFixed(2);
						$("#paysum").html(a);
					}else{
						$(this).val(orifinal);
					}
					return;
				}
				var needPaySum = $("#needPayMax").val();
				var a = (eval(needPaySum) + eval(orifinal) - value).toFixed(2);
				$("#paysum").html(a);
			}
		});
	});

 /*********************************************************
 * * Author: mayuanchao
 * * Date : 2011-08-12
 * * Update Date: 2011-08-15
 ********************************************************/
	var newApply=new apply();
	newApply.init();
	
/***********End**********************/
	
	//是否需要退货
	$(".needSalesReturn").each(function(){
		$(this).click(function(){
			
			var ar = $("#applyReason");
			
			ar.find("option").each(function() {
				$(this).remove();
			});

			ar.append("<option value=''>----填写退款原因----</option>"); 

			//不需要退货
			if($(this).attr("value") == "0"){
				ar.append("<option value='1'>未收到商品</option>");
				ar.append("<option value='2'>卖家发货不及时</option>");
				ar.append("<option value='3'>与卖家协商一致退款</option>");

			//需要退货
			}else if($(this).attr("value") == "1"){
				ar.append("<option value='7'>收到的货物出现质量问题</option>");
				ar.append("<option value='8'>收到的货物描述不符</option>");
				ar.append("<option value='9'>7天无理由退货</option>");
			}
		});
	});
	
	//修改时设置下拉选项
	$("#_needSalesReturn").each(function(){
		var needSalesReturn = $(this).attr("value");
		if(needSalesReturn == "")
			return;
		
		$(".needSalesReturn").each(function(){
			if($(this).attr("value") == needSalesReturn){
				$(this).click();
				
				var applyReason = $("#applyReason");
				var createdApplyReason = $("#createdApplyReason").attr("value");
				
				applyReason.find("option").each(function() {
					if($(this).attr("value") == createdApplyReason){
						$(this).attr("selected", true)
					}
				});
				
			}
		});
	});
	
	
	//提交申请
	$('#hrefApplyRefund').click(function(){
		/**
		//if(validateApply()){
		//if(confirm('您仅能针对该商品提交一次退款申请，请您确认退款要求是否合适.')){
		//	$('#form').attr({
		//		method: 'post',
		//		action : '/refund/saveApply.htm',
		//		enctype : "multipart/form-data"
		//	});
		//	$('#form').submit();
		//	$(this).hide();
		//	$("#submitLoad").show();
	//	}else{
	//		return;
	//	}
	//	}else{
	//		return;
	//	}
	 **/
	 	if(validateApply()){
		art.dialog.confirm('您仅能针对该商品提交一次退款申请，请您确认退款要求是否合适?', function(){
			$('#form').attr({
				method: 'post',
				action : '/refund/saveApply.htm',
				enctype : "multipart/form-data"
			});
			$.showFullLoading({
				description : '数据正在提交中，请稍候...'
			});
			$('#form').submit();
		}, function(){
			return;
		});
	}else{
		return;
	}
});

	//修改退款申请
	$('#updateApplyRefund').click(function(){
		if(validateApply()){
			if(confirm('您仅能针对该商品提交一次退款申请，请确认您修改的退款要求是否合适.')){
				$('#form').submit();
				$(this).hide();
				$("#submitLoad").show();
			}else{
				return;
			}
			}else{
				return;
			}
	});
	
	var value = newApply.applySum,
	needPaySum = newApply.needPaySum,
	refundId = $("#createdRefundId").val();
	if(value !="" && refundId != ""){
		$("#paysum").html(needPaySum);
	}else if(value !=""){
		$("#paysum").html((needPaySum - value).toFixed(2));
	}else{
		$("#paysum").html("*******");
	}
	
	$("#applyMemo").bind("blur",function(e) {
		var objValue=$.trim($(this).val());
		if(getLength(objValue) > 300){
			$('#msgFont').html("<font color='red'>您输入的字符长度已经超过300个汉字长度，请尽量描述简洁，以方便卖家查看。</font>");
			$(this).val(objValue);
		}else{
			$('#msgFont').html("(注：请详细描述您的理由，以便卖家和客服人员判断，限300字)");
		}
	});
	
});

function validateApply(){
	//验证是否选择退款原因
	var newApply=new apply();
	newApply.applyReason=$("#applyReason").val();
	newApply.applySum=$("#applySum").val();
	newApply.needSalesReturn=$(".needSalesReturn").val();
	newApply.applyMemo=$("#applyMemo").val();
	if(newApply.applyReason==""){
		popdialog(newApply.tipMessage[3]);
		return false;
	}
	if(newApply.applySum==""){
		popdialog(newApply.tipMessage[4]);
		$('#applySum').focus();
		return false;
	}else if(newApply.applySum != ""){
		var value = newApply.applySum;
		var xiaoji = $("#xiaoji").val();
		
		if(eval(value) > eval(xiaoji)){
			var tip = "退款金额不能超过" + xiaoji + "元";
			popdialog(tip);
			return false;
		}
	}
	
	if(newApply.applyMemo == ""){
		popdialog(newApply.tipMessage[5]);
		return false;
	}
	if(getLength(newApply.applyMemo) > 300){
		popdialog("您输入的字符长度已经超过300个汉字长度，请尽量描述简洁，以方便卖家查看。");
		return false;
	}
	return true;
}
function apply(){
	this.applyReason=$("#applyReason").val()
	this.applySum=$("#applySum").val()
	this.needSalesReturn=$(".needSalesReturn").val()
	this.applyMemo=$("#applyMemo").val()
	this.xiaoji= $("#xiaoji").val(),
	this.needPaySum = $("#needPayMax").val(),
	this.tipMessage=new Array("提示：无理由退货不能申请赔偿，请您自行承担退货产生的运费。",
							   "您申请最高限额为双倍赔偿及退货运费，请勿超出赔偿上限标准。",
							   "请选择要求赔偿的原因",
							   "请选择退款原因",
							   "请填写申请退款金额！",
							   "请填写退款说明");
	this.init=function(){
		var ar = $("#applyReason");
		ar.find("option").each(function() {
			$(this).remove();
		});

		ar.append("<option value=''>----填写退款原因----</option>"); 

		//不需要退货
		var item = $('input[@name=needSalesReturn]:checked').val(); 
		if(item == "0"){
			ar.append("<option value='1'>未收到商品</option>");
			ar.append("<option value='2'>卖家发货不及时</option>");
			ar.append("<option value='3'>与卖家协商一致退款</option>");

		//需要退货
		}else if(item == "1"){
			ar.append("<option value='7'>收到的货物出现质量问题</option>");
			ar.append("<option value='8'>收到的货物描述不符</option>");
			ar.append("<option value='9'>7天无理由退货</option>");
		}
	}
}

var  popdialog=function(_content){
	//art.dialog({time:6,lock: true,background: '#F6F6F6',width:'280px', // 背景色
	//			opacity: 0.1,	// 透明度
	//			content:_content ,
	//			icon: 'warning',
	//			id: 'Alert',
	//			ok: true
	//});
	art.dialog.alert(_content);
}
function getLength(s){
	if(s == ""){
		return 0;
	}
	var len = 0;
	for(i = 0; i < s.length; i++){
		var c = s.substr(i, 1);
		var ts = escape(c);
		if(ts.substring(0, 2) == "%u"){
			len += 2;
		} else {
			if(ts.substring(0, 3) == "%D7"){
				len += 2;
			} else {
				len += 1;
			}
		}
	}
	return len;
}