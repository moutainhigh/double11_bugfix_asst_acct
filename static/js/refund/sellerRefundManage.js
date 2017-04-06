$(document).ready(function(){
	//倒计时
	$('#deadline').each(function(){
		showLeftDay(this);
	});
	
	//卖家确认收货
	$('#confirmReceiveGoods').click(function(){
		if(!confirm("你确定收到退货了吗？")) return;
		$('#form1').attr('action','sellerConfirmReceiveGoods.htm');
		$('#form1').submit();
		
	});

	
	
	//卖家申请客服介入
	$('#sellerApplyCustProcess').click(function(){
		if(!confirm("您确认要申请客服介入吗？")) return;
		$('#form1').attr('action','sellerApplyCustProcess.htm');
		$('#form1').submit();
		
	});
	
	
	
	//卖家同意退款
	$('#sellerAgreeRefundApply').click(function(){
		art.dialog.confirm('您确认要同意买家的退款申请吗?', function(){
			$('#checkForm').attr('action','sellerAgreeRefundApply.htm');
			$.showFullLoading({
			description : '数据正在提交中，请稍候...'
			});
			$('#checkForm').submit();
		}, function(){
			return;
		});
	});
	
	//卖家拒绝退款
	$('#sellerRejectRefundApply').click(function(){
		//if($("#texLeave").attr("value") == ""){
		//	alert("您必须输入拒绝理由才能拒绝买家退款申请");
		//	return;
		//}
		
		//if(!confirm("您确认要拒绝买家退款申请吗？建议您谨慎操作，多与买家沟通避免误解。")) return;
		//$('#leaveWordform').attr('action','saveLeaveWordBeforeSellerReject.htm');
		//$('#leaveWordform').removeAttr("target");
		//$('#leaveWordform').submit();
		art.dialog.confirm('您确认要拒绝买家退款申请吗？建议您谨慎操作，多与买家沟通避免误解。', function(){
			leaveWorld();
		}, function(){
			return;
		});
	});
	
});

function leaveWorld(){
	var dialog = art.dialog({id: 'N3690',title: false});
			var content='<form id="leaveworld-form" action="/refund/saveLeaveWordBeforeSellerReject.htm" method="post">';
			content +='<p id="inputError" class="hong"></p>';
			content +='<textarea style="width:480px;height:180px" id="LeaveText" name="texLeave"></textarea>';
			content +='<input type="hidden" name="userType" value="2"/>';
			content +='<input type="hidden" name="refundId" value="';
			content +=refundId;
			content +='"></form>';
			content +='<script type="text/dialog">';
			content +="var api = this; ";
			content +="api.title('留言').button({name: '留言',callback: function ()";
			content +="{var leaveText =$('#LeaveText').val();if(leaveText != '' && getLength(leaveText) <= 300){$('#leaveworld-form').submit();} if(leaveText ==''){$('#inputError').html('请输入留言信息，留言信息的长度不能超过300个汉字长度');return false; }if(getLength(leaveText) >= 300){$('#inputError').html('您输入的字符已经超出300个汉字长度，请尽量描述简洁，以方便买家查看');return false;}},";
			content +="focus: true},{name: '取消'}).lock();";
			content +=' function getLength(s){if(s == ""){return 0;}var len = 0;for(i = 0; i < s.length; i++){var c = s.substr(i, 1);var ts = escape(c);if(ts.substring(0, 2) == "%u"){';
			content +='len += 2;} else {if(ts.substring(0, 3) == "%D7"){len += 2;} else {len += 1;}}}return len;}';
			content +=' </script>';
			//alert(content);
			dialog.content(content);
} 

