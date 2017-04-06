/**
 * 卖家后台管理 JS
 * 
 */
$(document).ready(function() {
	
	 $.datepicker.setDefaults( $.datepicker.regional[ 'zh-CN' ] );
	    $("#startTime" ).datepicker({
	    changeMonth: true,
	    changeYear: true,
	    autoSize: true
	  	});
	  	$("#endTime" ).datepicker({
	    changeMonth: true,
	    changeYear: true,
	    autoSize: true
	  	});
	  		
		var pages = $("#pages").val();

		var currPage = $("#currPage").val();
		$("#Pagination").pagination(pages, {
			current_page : currPage - 1,
			num_edge_entries : 2,
			num_display_entries : 4,
			callback : function(page) {
				$("#shcm").val("pag");
				$("#currPage").val(++page);
				$("#searchForm").attr( {
					action : "ordermanagesell.htm?pageNum=" + (++page),
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
		
		//切换tab样式
		var val=$("#orderState").val();
		if(val == null || val == ""){
				$("#tg").addClass("cur");
				return;
			}
		$("#switch_tab_tg").find("li").each(function(){
			//$(this).removeClass("cur");
			liVal = $(this).attr("liVal");
			if(liVal == val){
				 $("#tg"+val).addClass("cur");
			}else{
				$(this).removeClass("cur");
			}
			
		});
});

function prolong(oid,da) {
	if(confirm("您确定要延长买家收货时间吗？")){
		wBox.close();
		$.ajax({
			type: "POST",
			url: "/orderSeller/ordermanagesell_pro.htm?shid="+oid+"&message="+da,
			dataType: 'xml',
			success: function(msg){ 
				if($(msg).find("isSuccess").text()== "true")
			    {	
			    	alert("延长买家收货时间操作成功!");
					//window.location.href="/orderSeller/ordermanagesell.htm"; 
				}else{
					alert("系统繁忙，请稍后重试！");
				}
			} 
		});
	}
}

function closeWobx(){
	wBox.close();
}
function tagSwitch(tg) {
	
	$('#searchForm').attr("action", "");
	
	$(".selected").addClass('');
	document.getElementById('orderState').className='selected';
	$("#currPage").val("1");
	$("#orderState").val(tg);
	$("#shcm").val('tg');


$("#searchForm").attr( {
		action : "/orderSeller/ordermanagesell.htm",
		method : "post"
	}).submit();
}
function closeSell(oid) {
	$.ajax({
		type: "POST",
		url: "/orderSeller/ordermanagesell_check.htm?orderId="+oid,
		dataType: 'xml',
		success: function(msg){ 
			if($(msg).find("isSuccess").text()== "true")
			{	
				var dialog = art.dialog({id: 'N3690',title: false,height: '120px',width: '400px'});
				var content='';
				content +='<p id="inputError" class="hong"></p>';
				content +='<p>取消订单后，此笔交易将关闭，买家无法付款。如果您单方面关闭订单，可能会引起买家投诉，进而影响您在品聚网的信誉。</p><br />';
				content +='<strong>请选择关闭该交易的理由：</strong>';
				content +='<select id="failReason" name="failReason"><option value="">请选择关闭理由</option><option value="2001">买家不想买了</option><option value="2002">联系不到买家</option><option value="2003">暂时缺货</option><option value="2004">已同城见面交易</option><option value="2006">买家已经通过银行汇款</option><option value="2007">买家已经通过网上银行直接付款</option><option value="2005">其他原因</option></select><br /><br />';

				content += '<span style="color:#BE0000">提醒：拍下后减库存的商品，在关闭交易后，系统会自动恢复商品库存，但不会影响已下架商品的状态。</span>';

				content +='<script type="text/dialog">';
				content +="var api = this; ";
				content +="api.title('取消订单').button({name: '确定',callback: function ()";
				content +="{var failreason = $('#failReason').val(); if(failreason == null || failreason == ''){art.dialog({content: '请选择关闭理由！', lock: true,ok:true,height: '60px',width: '200px',icon:'warning',padding:'0 80px 0 0'});return false; }else{$.ajax({type:'post',url:'/orderSeller/ordermanagesell_cls.htm?tgState="+oid+"&failReason='+$('#failReason').val(),dataType:'xml',success:function(msg){if($(msg).find('isSuccess').text()=='true'){window.location.reload();}else{alert($(msg).find('errorConstant').text());window.location.reload();}}});}},";
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

function callbackdel(date) {
	alert(date);
}

function sendDelivery(orderId){
	$("#form1").attr( {
		action : "sellerDelivery.htm?sendDelivery.orderId=" + orderId,
		target :"_blank",
		method : "post"
	}).submit();
}

var divVal;
$(function(){
$(".user_name").mousemove(function(e){
    divVal = $(this).attr("myId");
	$(".message_board"+divVal).show(0,function(){
		var ie6=!window.XMLHttpRequest;
		this.style.position=(ie6)?"absolute":"fixed";
		this.style.zIndex=999999;
		this.style.top=(ie6)?e.pageY+10+"px":e.clientY+10+"px";
		this.style.left=e.clientX+30+"px";					
	})
});
$(".user_name").mouseout(function(){
	$(".message_board"+divVal).fadeOut(100);
});
})

function checkRefund(){
	if($('#orderState').val() == 4)
		$('#refundState').val("1");
	else
		$('#refundState').val("");
}


function importCSV(){
	$("#searchForm").attr( {
		action : "/orderSeller/ordermanagesellimport.htm",
		method : "get"
	}).submit();
}

function searchOrder(){
	$('#searchForm').attr("action", "");
	$("#currPage").val("1");

	$("#searchForm").attr( {
		action : "/orderSeller/ordermanagesell.htm",
		method : "post"
	}).submit();
}



var wBox=$("#wbox1").wBox({
	title: "延长买家收货时间",
	html: "<div class='mywBox'><table width='100%' height='100%'><tr><td colspan='2'>延长收货时间可以让买家有更多时间来“确认收货”，而不会急于去申请退款。</td></tr><tr><td align='right'><strong>延长本交易的“确认收货”期限为：</strong></td><td><select id='spro' name='spro'><option value='3'>3天</option><option value='5'>5天</option><option value='7'>7天</option><option value='10'>10天</option></select></td></tr><tr><td align='right'><input type='button' id='J_postshopcart' onclick='prolong($(\"#shid\").val(),$(\"#spro\").val());' value=' 确定 '></td><td width='20%' align='center'><input type='button' value=' 关闭 ' onclick='closeWobx()'/></td></tr></table></form></div>"
});

function modifyPrice(isPostPay,orderid){
	if(isPostPay == 0){
		alert("买家已进入网银流程，您不能修改价格！");
		return;
	}
	$("#form1").attr( {
		action : "/orderSeller/toModifyPrice.htm?orderId="+orderid,
		method : "post",
		target : "_blank"
	}).submit();
}

//卖家延长买家收货时间
function delayBuyerReceiveTime(orderId){
	$.ajax({
		type:'post',
		url:'/orderSeller/ordermanagesell_procheck.htm?shid='+orderId,
		dataType:'xml',
		success:function(checkResult){
			if($(checkResult).find('isSuccess').text()=='false')
			{
				alert($(checkResult).find('resultCode').text());
				window.location.href="/orderSeller/ordermanagesell.htm";
				return;
			}
			var dialog = art.dialog({id: 'N3690',title: false});
			var content='<form id="delayForm" action="/orderSeller/ordermanagesell_pro.htm" method="post">';
			content +='<p id="inputError" class="hong"></p>';
			content +='<p>延长收货时间可以让买家有更多时间来“确认收货”，而不会急于去申请退款。</p>';
			content +='<strong>延长本交易的“确认收货”期限为：</strong>';
			content +='<select id="message" name="message"><option value="3">3天</option><option value="5">5天</option><option value="7">7天</option><option value="10">10天</option></select>';
			content +='<input type="hidden" name="shid" value="';
			content +=orderId;
			content +='"></form>';
			content +='<script type="text/dialog">';
			content +="var api = this; ";
			content +="api.title('延长买家收货时间').button({name: '确定',callback: function ()";
			content +="{if(!confirm('您确定要延长买家收货时间吗？')) return;$.ajax({type:'post',url:'/orderSeller/ordermanagesell_pro.htm?shid="+orderId+"&message='+$('#message').val(),dataType:'xml',success:function(msg){if($(msg).find('isSuccess').text()=='true'){alert('延长买家收货时间操作成功!');}else{alert($(msg).find('resultCode').text());window.location.href='/orderSeller/ordermanagesell.htm';}}});},";
			content +="focus: true},{name: '取消'}).lock();";
			content +=' </script>';
			dialog.content(content);
		}
	});
} 
