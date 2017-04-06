$(function() {
	$.fn.modify_price = function(opts) {
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

			if (code == 37 || code == 39 || code == 45) {
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
				return (code >= 48 && code <= 57) || code == 46 || code == 189;
			}
		});
		this.bind("blur", function() {
			if (this.value.length==0) return false;
			if(this.value==0) this.value="0.00";
			var d = $(this).val().indexOf(".");
			if (isNaN(this.value)){
				this.value = "0.00";
			}else if (d == -1 && this.value !=0) {
				this.value = this.value+".00";
			} else if(d != -1 && $(this).val().split(".")[1].length == 0){
				this.value = this.value+"00";
			}else if(d != -1 && $(this).val().split(".")[1].length >= 3){
				this.value=this.value.substring(0,d+3);
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
			var match;
			if ((match = /^([^\-]+|-+[^\-]*)-+([^\-]*)$/.exec($(this).val()))) {
				this.value = match[1].replace(/-+/g, '-') + match[2];
			}
		});
	};
});


$(function() {
	$.fn.modify_postprice = function(opts) {
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
			if(this.value==0) this.value="0.00";
			var d = $(this).val().indexOf(".");
			if (isNaN(this.value)){
				this.value = "0.00";
			}else if (d == -1 && this.value !=0) {
				this.value = this.value+".00";
			} else if(d != -1 && $(this).val().split(".")[1].length == 0){
				this.value = this.value+"00";
			}else if(d != -1 && $(this).val().split(".")[1].length >= 3){
				this.value=this.value.substring(0,d+3);
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
	$("input[mdf*=mdf]").each(function(){
		$(this).modify_price({
			decimalPlace:0, 
	 		maxLength:8
		});
		
	});
 

	$("#modifyLogistics").each(function(){
		$(this).modify_postprice({
			decimalPlace:0, 
	 		maxLength:8
		});
		
	});
});





function updatePrice(obj,id){
	var unitPrice = eval($(id).val());
	if($(obj).val() == ""){
		$(obj).val("0.00");
	}
	var newPrice = parseFloat(obj.value);
	if(Math.abs(newPrice)*2 >= unitPrice){
		$(obj).val("0.00");
		alert("价格调整过大，请重新输入！");
	}else{
		var showallprice = parseFloat($(obj).val())+"";
		if(showallprice.indexOf(".") > 0){
			showallprice = showallprice.substring(0,$(obj).val().toString().indexOf(".")+3)
		}
		if(eval(showallprice) == 0){
			showallprice = "0.00";
		}
		$(obj).val(showallprice);
	}
	displaytotal();
}
		
		function displaytotal(){
			var updatePrice = 0.00;
			$('input[modifyPriceArry*=modifyPriceArry]').each(function(){
 				updatePrice += eval($(this).val());
 			});
 			
 			updatePrice = updatePrice.toFixed(2);
 			if(eval(updatePrice) >= 0){
 				$('#displayUpdatePrice').html(" + "+updatePrice);
 			}else{
 				$('#displayUpdatePrice').html(updatePrice);
 			}
 			
 			var updatePostPrice = $('#modifyLogistics').val();
 			updatePostPrice = eval(updatePostPrice).toFixed(2);
 			if(eval(updatePostPrice) >= 0){
 				$("#displayLogisticsPrice").html(" + "+updatePostPrice);
 			}else{
 				$("#displayLogisticsPrice").html(updatePostPrice);
 			}
 			var totalPrice = eval($("#orderOriginalPrice").val());
 			totalPrice += eval(updatePrice)+eval(updatePostPrice);
 			totalPrice = totalPrice.toFixed(2);
 			$("#displayTotalPrice").html(totalPrice);
		}
		
		function updateLogistics(){
			//修改的运费值
			var modifyLogistics = $('#modifyLogistics').val();
			
			if(modifyLogistics == ""){
				 $('#modifyLogistics').val("0.00");
				 modifyLogistics = "0.00";
			}
			
			modifyLogistics = parseFloat(modifyLogistics);

			if(eval(modifyLogistics) >= 1000){
				alert("运费不能超过1000，请重新输入！");
				$('#modifyLogistics').val("0.00");
				modifyLogistics = "0.00" ;
			}
			
			$('#modifyLogistics').val(eval(modifyLogistics).toFixed(2));
			
			displaytotal();
		}
		
		function checkModifyPrice(){			
			var arrItemPrice = new Array();
			var orderItemIds = new Array();
			
	 		$('input[orderItemIdArry*=orderItemIdArry]').each(function(){
 				orderItemIds.push($(this).val());
 			});
 			
 			$('input[modifyPriceArry*=modifyPriceArry]').each(function(){
 				arrItemPrice.push(eval($(this).val())*100);
 			});
 			
	 		var orderPostPrice = $('#modifyLogistics').val();
	 		if(confirm("您确定要修改订单价格吗？")){
				$.ajax({
					data:$('#form1').serialize(),
	 				type: "POST",
	 				url: "/orderSeller/modifyPrice.htm" ,
	 				dataType: 'xml',
	 				success: function(msg){ 
		 				if($(msg).find("isSuccess").text()== "true")
					    {
		 					alert('修改价格成功！');
		 					window.location.href="/orderSeller/ordermanagesell.htm";
		 				}else{
		 					var resultCode = $(msg).find("resultCode").text();
							if(resultCode == 104){
								alert("您无权限修改该订单的价格！");
								return;
							}
							if(resultCode == 108){
								alert("订单状态已更改，您不能修改价格！");
								return;
							}
							if(resultCode == 111){
								alert("买家已进入网银流程，您不能修改价格！");
								return;
							}
							if(resultCode == 222){
								alert("抱歉，未找到您要的订单！");
								return;
							}
							else{
								alert("订单状态已更改，您不能修改价格！");
								return;
							}
		 				}
	 				} 
				}); 
			}
		}
	
	
