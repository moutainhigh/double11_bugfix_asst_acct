
  
/****** 投诉维权入口及校验  Add By ShiXing@2011.07.07 ******/
var rightsComplaintFn = function(obj,orderItemId){
	var result = false;  
		$.ajax({
		type: "post",
		timeout: 10000000, 
		cache: false,
		dataType: 'json',
		async : false,
		url: "/rights/checkRightsRecode.htm?orderItemId=".concat(orderItemId),
		success: function(responseData){ 
			if(responseData!=null && responseData!=""){
				if(responseData["already"]!=undefined){
					alert(responseData["already"]);    //重复维权
				}else if(responseData["outofdate"]!=undefined){
					alert(responseData["outofdate"]);  //过期
				}else if(responseData["error"]!=undefined){
					alert(responseData["error"]);
				}else if(responseData["success"]!=undefined){
					result = true;
				}
			}
		} 
	});
	return result;
}

/****** ShiXing End! ******/
  
$(document).ready(function() {
	var pages = $("#pages").val();
	var currPage = $("#currPage").val();
	$("#Pagination").pagination(pages, {
		current_page : currPage - 1,
		num_edge_entries : 2,
		num_display_entries : 4,
		callback : function(page) {
			$("#currPage").val(++page);
			$("#searchForm").attr( {
				action : "/orderBuyer/orderManager.htm",
				method : "post"
			}).submit();
		},
		items_per_page : 1
	});
});


		
function searchByItemTitle(){
	$("#currPage").val("1");
	$('#searchForm').submit();
}
  	
    
function setCancelOrderId(orderId){
  $("#cancelOrderId").val(orderId);
}
	

function cancel(cancelOrderId){
	$.ajax({
		type: "POST",
		url: "/orderBuyer/orderCancelCheck.htm?cancelOrderId="+cancelOrderId,
		dataType: 'xml',
		success: function(msg){ 
			if($(msg).find("isSuccess").text()== "true")
			{	
				var dialog = art.dialog({id: 'N3690',title: false,height: '120px',width: '400px'});
				var content='';
				content +='<p id="inputError" class="hong"></p>';
				content +='<p>您确定要取消该订单吗？取消订单后，不能恢复。</p><br />';
				content +='<strong>请选择取消订单的理由：</strong>';
				content +='<select id="failReason" name="failReason"><option value="">请选择关闭理由</option><option value="1001">我不想买了</option><option value="1002">信息填写错误，重新下单</option><option value="1003">卖家缺货</option><option value="1004">同城见面交易</option><option value="1005">其他原因</option></select>';

				content +='<script type="text/dialog">';
				content +="var api = this; ";
				content +="api.title('取消订单').button({name: '确定',callback: function ()";
				content +="{var failreason = $('#failReason').val(); if(failreason == null || failreason == ''){art.dialog({content: '请选择关闭理由！', lock: true,ok:true,height: '60px',width: '200px',icon:'warning',padding:'0 80px 0 0'});return false;}else{$.ajax({type:'post',url:'/orderBuyer/orderCancel.htm?cancelOrderId="+cancelOrderId+"&failReason='+$('#failReason').val(),dataType:'xml',success:function(msg){if($(msg).find('isSuccess').text()=='true'){window.location.reload();}else{alert($(msg).find('errorConstant').text());window.location.reload();}}});}},";
				content +="focus: true},{name: '取消'}).lock();";
				content +=' </script>';
				dialog.content(content);
			}
			else
			{
				alert($(msg).find("errorConstant").text());
				window.location.reload();
			}
		} 
	}); 
}

function affirmPay(){
	var data = $("#affirmPay").formToArray(); 
	$.ajax({
		type: "POST",
		url: "/orderPay/affirmPay.htm",
		data: data,
		dataType: 'xml',
		success: function(msg){ 
			if($(msg).find("isSuccess").text()== "true")
			{
				var str= $(msg).find("orderId").text();
				var id = str.split(",");
				for(var i=0 ;i<id.length;i++){
					var classId = "."+id[i];
					var htmlString = "<p class='hong'>交易成功</p>";
					$(document).find(classId).html(htmlString);
					classId = "."+id[i]+"info";
					var htmlString = "<a href=/orderSeller/orderinfoBuy.htm?oid="+id[i]+">订单详情</a>";
					$(document).find(classId).html(htmlString);
				}
			}
		} 
	}); 
}
	
function affirmAllPay(){
	var arr = new Array();
	$('input[checkName*=orderId]').each(function(){
		if($(this).attr('checked')== 'checked'){
			arr.push({'name':'orderId','value':$(this).val()});
		}
	});
	$.ajax({
		type: "POST",
		url: "/orderPay/affirmPay.htm",
		data: arr,
		dataType: 'xml',
		success: function(msg){ 
	
			if($(msg).find("isSuccess").text()== "true")
			{
				var str= $(msg).find("orderId").text();
				var id = str.split(",");
				for(var i=0 ;i<id.length;i++){
					var classId = "."+id[i];
					var htmlString = "<p class='hong'>交易成功</p>";
					$(document).find(classId).html(htmlString);
					classId = "."+id[i]+"info";
					var htmlString = "<a href=/orderSeller/orderinfoBuy.htm?oid="+id[i]+">订单详情</a>";
					$(document).find(classId).html(htmlString);
				}
			}
		} 
	}); 
}
	
	
function allPay(fieldName){
	var field=document.getElementsByName(fieldName);
	var count = 0;
	for (i = 0; i < field.length; i++) {
		if (field[i].checked == true) {
			count++;
		}
	}
	if (count <= 0) {
		alert("请选择您要支付的订单！");
		return false;
	}
	
	if(count > 10){
		alert("您最多一次可以支付10笔订单！");
		return false;
	}
	
	var arr = new Array();

	$('input[checkName*=orderId]').each(function(){
		if($(this).attr('checked')== 'checked'){
			if(arr.length < 10){
			   arr.push({'name':'orderId','value':$(this).val()});
			}
		}
	});
	
	$("#mergepayFrom").attr( {
		action : "/orderPay/pay.htm?type=1",
		target :"_blank",
		method : "post"
	}).submit();
	
		
}


function pageselectCallback(page_index, jq){
	var itemTitle = $("#itemTitle").val();
	var orderItemState = $('#orderItemState').val();
		
	 //重写页码内容条
	location.replace( "/orderBuyer/orderManager.htm?page="+(page_index+1)+"&itemTitle="+itemTitle+"&orderItemState="+orderItemState);
}
	
function checkalls(flag,fieldName){
	var field=document.getElementsByName(fieldName);
	
	if (flag) {
		for (i = 0; i < field.length; i++) {
			if (field[i].disabled != true) {
				field[i].checked = true;
			}
		}
		document.getElementById("orderIdAlla").checked=true;
		document.getElementById("orderIdAllb").checked=true;
		return "Uncheck All"; 
	} else {
		for (i = 0; i < field.length; i++) {
			field[i].checked = false; 
		}
		document.getElementById("orderIdAlla").checked=false;
		document.getElementById("orderIdAllb").checked=false;
		return "Check All"; 
	}
}

function onchangeState(){
	var orderItemState = $('#orderItemState').val();
	if(orderItemState == 4){
		$('#refundState').val("1");
	}else{
		$('#refundState').val(null);
	}
}
	
function changeState(){
	var orderItemState = $('#orderState').val();
	$("#orderItemState").val(orderItemState);
	$("#currPage").val("1");
	$("#searchForm").attr( {
		action : "/orderBuyer/orderManager.htm",
		method : "post"
	}).submit();
}
	
function checkOrderItemState(orderId,isRefund){
	$('input[checkName*=orderId]').each(function(){
		if($(this).attr('checked')== 'checked'){
			$(this).attr('checked', false);
		}
	});

	if(isRefund == 10002){
			alert("您有商品正在退款，请完成退款或关闭退款后再确认收货!");
			return false;
	}

	$("#mergepayFrom").attr( {
		action : "/orderPay/receiveItem.htm?orderId=" + orderId,
		target :"_blank",
		method : "post"
	}).submit();
	
}
	
function singlePay(orderid){
	$('input[checkName*=orderId]').each(function(){
		
		if($(this).attr('checked')== 'checked'){
			$(this).attr('checked', false);
		}
		
		if($(this).attr('value') == orderid){
			$(this).attr('checked', true);
		}
	});

	$("#mergepayFrom").attr( {
		action : "/orderPay/pay.htm",
		target :"_blank",
		method : "get"
	}).submit();
}
	
	
		