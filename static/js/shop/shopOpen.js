function shopOpenBegin(sellerType, isNextStep){
	var action = $("#openShopForm").attr("action");
	if(isNextStep){
		action = action + "?sellerType=" +sellerType+"&isNextStep=1";
	}else{
		action = action + "?sellerType=" +sellerType;
	}
	$("#openShopForm").attr("action",action);
	$("#openShopForm").submit();
}



$(function(){
	$(".tab-main>li").each(function(i){
		$(this).click(function(){
			$(".tab-main>li").attr("class","_count");
			$(this).attr("class","count");
			$(".agreement-box>div").hide();
			$(".agreement-box>div").eq(i).show();
		});
	});
});
function showAgreement(seq,seq2,idshow,idhide){
	
	//$(".agreement-box>div").hide(0,
	//	function(){
	//		$("#"+idshow).show();
	//	})
	
	
	//$("#"+idshow).show();
	//$("#"+idhide).hide();
	//$("#agree"+seq).removeClass("_count").addClass("count");
	//$("#agree"+seq2).removeClass("count").addClass("_count");
	
	
	//$("#agree"+seq).css("class","count");
	//$("#agree"+seq2).css("class","_count");
	
	$("#agree"+seq).attr("class","count")
	$("#agree"+seq2).attr("class","_count")
	
}

function isAgreementCheck(id,flag){
		if($("#"+id).attr("checked") == "checked"){
		  $("#agreementButton").attr("disabled","disabled");
			return true;
		}else{
			return false;
		}
}

function  tabShow(number){
	for(var i=1;i<=6;i++){
		if(i == number){
			$("#shopInfo"+number).css("display","block");
			$("#shopInfo"+number).attr("className","selected");
		}else{
			$("#shopInfo"+i).css("display","none");
			$("#shopInfo"+number).attr("class","");
		}
	}
}
$(document)
		.ready(
				function() {
					$('#ruzhu0').click(function(e) {
							e.preventDefault();
					        shopOpenBegin(0);
					});
					
					$('#ruzhu1').click(function(e) {
					      	e.preventDefault();
					        shopOpenBegin(1);
					});
					
					$('#ruzhu2').click(function(e) {
					      	e.preventDefault();
					        shopOpenBegin(2);
					});
					$('#ruzhu3').click(function(e) {
					      	e.preventDefault();
					        shopOpenBegin(3);
					});
	});
	
	
	function changeSellyType(){
    var r=confirm("是否真的需要修改店铺类型，修改将删除之前填写的信息");
            if (r==true){
               $("#openShopForm").attr({ action:"/shop/changeSellTypeAction.htm",method:"post" });
               $("#openShopForm").submit();
            }
  }