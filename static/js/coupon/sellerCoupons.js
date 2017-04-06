$(document).ready(function(){

	var radioValue = $("#radioValue").val();
	checkRadio(radioValue);
});


function clearNoNum(obj){
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	//只能为2位小数
	//if(obj.value.indexOf('.') != -1){
	//	obj.value = obj.value.substring(0, obj.value.indexOf('.') + 3);
	//}
}

	
function searchCoupon(){
	$("#currentPage").val(1);
	$("#pageForm").attr( {
		action : "/sellerCoupon/sellerCoupon.htm",
		method : "post"
	}).submit();
}

function clearNum(){
	var couponMoney = $("#couponMoneyId").val();
	if(couponMoney != ""){
		$("#couponMoneyId").val(parseFloat(couponMoney));
	}
}

function checkRadio(radioValue){
	if(radioValue == "1"){
		document.getElementById("couponMoneyId").disabled = false;
		$("#radioValue").val("1");
	}else{
		$("#radioValue").val("0");
		$("#couponMoneyId").val("");
		document.getElementById("couponMoneyId").disabled = true;
	}
}

		