$(document).ready(function(){

	var radioValue = $("#radioValue").val();
	checkRadio(radioValue);
});


function clearNoNum(obj){
	//�Ȱѷ����ֵĶ��滻�����������ֺ�.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//���뱣֤��һ��Ϊ���ֶ�����.
	obj.value = obj.value.replace(/^\./g,"");
	//��ֻ֤�г���һ��.��û�ж��.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	
	//��֤.ֻ����һ�Σ������ܳ�����������
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	//ֻ��Ϊ2λС��
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

		