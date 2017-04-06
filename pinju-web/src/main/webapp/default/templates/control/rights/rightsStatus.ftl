
<script src="http://static.pinju.com/countdown/jquery.countdown.js"></script>

<!-- 1、申请消保。。。进程图-Start -->
<div class="rights_course">
<#if rightsDO.state == 0 || rightsDO.state == 2 || rightsDO.state=3 || rightsDO.state == 4 || rightsDO.state=11>
	<span class="course1_b"></span>
	<span class="course2_a"></span>
	<span class="course3_b"></span>
<#elseif rightsDO.state == 1 || rightsDO.state == 9 || rightsDO.state == 12>
	<#if rightsDO.buyerRequire=1>
		<p class="refund_status">
			<span class="course1_b"></span>
			<span class="course2_a"></span>
			<span class="course3_b"></span>
		</p>
	<#elseif rightsDO.buyerRequire=0>
		<span class="course1_b"></span>
		<span class="course2_b"></span>
		<span class="course3_a"></span>
	</#if>
<#elseif rightsDO.state == 5 || rightsDO.state == 6 || rightsDO.state == 7 || rightsDO.state == 8 || rightsDO.state == 10>
	<span class="course1_b"></span>
	<span class="course2_b"></span>
	<span class="course3_a"></span>
</#if>
</div>
<!-- 1、申请退货。。。进程图结束-End -->

<!-- 维权状态及操作信息-Start -->
<div class="rights_box cf">
	<div class="tit">
		<span class="rights_code">维权编号：<span>${(rightsId?html)!}</span></span>
	</div>
	<div class="rights_status">
		<#if rightsDO.state=0>
			<p class="red bd">当前维权状态：等待卖家处理</p>
			<p>卖家（${((rightsDO.sellerNick)?html)!}）还有：
				<b class="timeleft" data-diff="${(rightsDateTime!)?html}" style="color:red;width:120px"></b>
				<b id="statusTime" style="color:red;width:120px"></b>处理本次维权协议<br/>
			如果在该期限内卖家（${((rightsDO.sellerNick)?html)!}）没有做出同意维权申请或者拒绝维权申请的答复，本次维权申请将会自动达成协议，
			并根据是否需要退货将退款返回给您或需要您将商品退还卖家。<br/>
			您还可以在卖家确认此维权申请前修改此协议金额或内容。<br/>
			如果您想放弃本次维权申请，可以点击撤销维权申请按钮。</p>
   		<#elseif rightsDO.state=1>
   			<#if rightsDO.buyerRequire=0>
   			<p class="red bd">当前维权状态：维权协议已达成，等待打款</p>
   			<#elseif rightsDO.buyerRequire=1>
   			<p class="red bd">当前维权状态：卖家已同意，等待买家退还商品</p>
   			<p>您还有：<b class="timeleft" data-diff="${(rightsDateTime!)?html}" style="color:red;width:120px"></b>
   				<b id="statusTime" style="color:red;width:120px"></b>处理本次维权协议
   			卖家已经同意了您的维权申请，维权协议达成，请您在5天内将商品发出退还卖家，否则系统默认关闭维权，请及时操作。</p>
   			</#if>
   		<#elseif rightsDO.state=2>
   			<p class="red bd">当前维权状态：维权申请被卖家拒绝<!--卖家以拒绝,等待买家处理--></p>
			<p>您还有：<b class="timeleft" data-diff="${(rightsDateTime!)?html}" style="color:red;width:120px"></b>
				<b id="statusTime" style="color:red;width:120px"></b>处理本次维权协议<br/>
			如果您未在卖家拒绝维权申请后5天内申请客服介入处理，那么系统将默认认为您同意了卖家拒绝维权申请操作，<br/>
			此维权申请将被关闭并且无法再次申请。</p>
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>
   		<#elseif rightsDO.state=3>
   			<p class="red bd">当前维权状态：等待卖家确认收货</p>
   		<#elseif rightsDO.state=4>
   			<p class="red bd">当前维权状态：客服介入中(客服会在5个工作日内处理)</p>
   			<p>客服将会根据买卖双方提交的凭证及留言记录对本维权进行裁决，裁决结果为最终结果，如果买卖双方对于客服裁决结果有异议，
			可以拨打客服电话(4008-211-588)对客服人员的裁决结果进行申诉。</p>
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>
   		<#elseif rightsDO.state=5 || rightsDO.state=7>
   			<p class="red bd">当前维权状态：维权成功</p>
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>
   		<#elseif rightsDO.state=6 || rightsDO.state=8>
   			<p class="red bd">当前维权状态：维权关闭</p>
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>
   		<#elseif rightsDO.state=9>
   			<p class="red bd">当前维权状态：维权协议已达成，等待打款</p>
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>
   		<#elseif rightsDO.state=10>
   			<p class="red bd">当前维权状态：维权协议已达成，等待打款</p>
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>
   		<#elseif rightsDO.state=11>
   			<p class="red bd">当前维权状态：客服已同意，等待卖家确认收货</p>
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>
   		<#elseif rightsDO.state=12>
   			<p class="red bd">当前维权状态：客服已同意，等待买家退还商品</p>
   			<p>您还有：<b class="timeleft" data-diff="${(rightsDateTime!)?html}" style="color:red;width:120px"></b>
   				<b id="statusTime" style="color:red;width:120px"></b>处理本次维权协议<br/>
   			客服已经同意了您的维权申请，维权协议达成，请您在5天内将商品发出退还卖家，否则系统默认关闭维权，请及时操作。</p> 
			<#if rightsDO.sellerRefuseReason??>
				<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
			</#if>			
   		</#if> 
		<#if rightsDO.state=0>
			<#if outOfDate?? && outOfDate='false'>
			<a class="location_btn" href="javascript:void(0);" onclick="modify();" id='updateRightsA'>修              改 </a>
			</#if>
		</#if>
		<#if rightsDO.state=0 || rightsDO.state=2>
			<a class="location_btn" href="javascript:void(0);" onclick="buyerCancelRights()">撤销维权申请</a>
		</#if>
		<#if rightsDO.state=2>
			<a class="location_btn" href="javascript:void(0);" onclick="buyerApply()">申请客服介入</a>
		</#if>
		<#if rightsDO.buyerRequire=1 && (rightsDO.state=1 || rightsDO.state=12)>
			<a class="location_btn" href="javascript:void(0);" onClick="refundInfoFocus();">填写退货信息</a>
		</#if>
		<#if rightsDO.buyerRequire=0>
			<#if rightsDO.state=0 || rightsDO.state=2 || rightsDO.state=3 || rightsDO.state=11 || rightsDO.state=4 || rightsDO.state=11>
				<a class="location_btn" href="javascript:void(0);" onClick='sendMessageFocus();'>发表留言/上传凭证</a>
			</#if>
		<#elseif rightsDO.buyerRequire=1>
			<#if rightsDO.state=0 || rightsDO.state=1 || rightsDO.state=2 || rightsDO.state=3 || rightsDO.state=4 || rightsDO.state=11 || rightsDO.state=12>
				<a class="location_btn" href="javascript:void(0);" onClick='sendMessageFocus();'>发表留言/上传凭证</a>
			</#if>
		</#if>
		<h1></h1>
   		<p style='line-height:65px'><font color="red">${errorMsg!}</font></p>			
	</div>
</div>
<!-- 维权状态及操作信息-End -->

<!-- 小提示-Start -->
<div class="rights_map">
	<span class="fl red">小提示：</span>
	<#if rightsDO.state=0>
	<p class="red">1.该订单中针对此商品仅能申请一次维权，请准确填写维权要求，多与卖家沟通，及时修改协议内容，避免无效维权申请。</p>
	<p class="red">2.如果您不想撤销维权申请，请勿点击撤销维权申请按钮，否则您将无法再次发起维权申请。</p>
	<#elseif rightsDO.state=1>
		<#if rightsDO.buyerRequire=0>
		<p class="red">1.维权协议已达成，等待打款。</p>
		<#elseif rightsDO.buyerRequire=1>
		<p class="red">1.请即时将商品退还给卖家并提交物流信息，避免超过5天维权关闭，维权关闭后您将无法再次申请维权。</p>
		<p class="red">2.如果您希望关闭维权，您可以及时向卖家留言，不进行退货流操作，操作5天维权关闭。</p>
		</#if>
	<#elseif rightsDO.state=3>
	<p class="red">1.买家已退还商品，等待卖家确认收货。</p>
	<#elseif rightsDO.state=2>
	<p class="red">1.请及时申请客服介入，避免超过5天系统默认您认可卖家的拒绝，维权关闭后您将无法再次申请维权。</p>
	<p class="red">2.如果您希望关闭维权，请点击确认关闭维权申请按钮。</p>
	<#elseif rightsDO.state=4>
	<p class="red">1.客服会在五个工作日内处理本维权申请，请耐心等待。</p>
	<p class="red">2.客服会在买卖双方都同意的情况下修改维权金额，所以买家卖家可以在本页面进行留言沟通，确定金额后通知客服修改维权金额。</p>
	<#elseif rightsDO.state=5 || rightsDO.state=7>
	<p class="red">1.该订单中针对此商品仅能申请一次维权，维权申请关闭后将无法再次发起维权申请。</p>
	<#elseif rightsDO.state=6 || rightsDO.state=8>
	<p class="red">1.该订单中针对此商品仅能申请一次维权，维权申请关闭后将无法再次发起维权申请。</p>
	<#elseif rightsDO.state=9>
    	<p class="red">1.客服裁决成功，等待打款。</p>
		<#--
		<#if rightsDO.buyerRequire=0>
    	<p class="red">1.客服裁决成功，等待打款。</p>
    	<#elseif rightsDO.buyerRequire=1>
    	<p class="red">1.请即时将商品退还给卖家并提交物流信息，避免超过5天维权关闭，维权关闭后您将无法再次申请维权。</p>
    	<p class="red">2.如果您希望关闭维权，您可以及时向卖家留言，不进行退货流操作，操作5天维权关闭。</p>
    	</#if>
    	-->
    <#elseif rightsDO.state=10>
    <p class="red">1.卖家已确认收货，等待打款。</p>
    <#elseif rightsDO.state=11>
    <p class="red">1.买家已退还商品，等待卖家确认收货。</p>
    <#elseif rightsDO.state=12>
	<p class="red">1.请即时将商品退还给卖家并提交物流信息，避免超过5天维权关闭，维权关闭后您将无法再次申请维权。</p>
	<p class="red">2.如果您希望关闭维权，您可以及时向卖家留言，不进行退货流操作，操作5天维权关闭。</p>
	</#if>
</div>
<!-- 小提示-End-->

<script language="JavaScript" type="text/javascript">
/**********************倒计时**Start************************/
$(function() {
	$('.timeleft').countdown({
		tmpl : '<span>%{d}</span>天<span>%{h}</span>小时<span>%{m}</span>分<span>%{s}</span>秒',
		//end : '已结束',
		afterEnd : function(){
			// document.getElementById("updateRightsA").style.visibility="hidden"; // 隐藏
			// document.getElementById("updateRightsA").style.display="none"; // 隐藏
			<#if rightsDO.state==0>
				$('#statusTime').html("处理超时，请关闭界面稍后再试...");
			<#elseif rightsDO.state=1>
				<#if rightsDO.buyerRequire=1>
				$('#statusTime').html("处理超时，请关闭界面稍后再试...");
				</#if>
			<#elseif rightsDO.state=2>
				$('#statusTime').html("处理超时，请关闭界面稍后再试...");
			<#elseif rightsDO.state=12>
				$('#statusTime').html("处理超时，请关闭界面稍后再试...");
			</#if>			
		}
	});
});
 /**********************倒计时**End************************/
 
function returnrightlist(){// 返回买家维权列表
	document.location.href="${base}/rights/rightsList.htm";	
}
function modify(){//买家修改维权信息
	document.location.href="${base}/rights/showModifyRightsPage.htm?rightsId=${rightsId!}";
}
function buyerCancelRights(){//买家撤销维权
	if(confirm("确定要撤销维权吗？")) {
		document.location.href="${base}/rights/buyerCancelRights.htm?rightsId=${rightsId!}";
   	}
}
function buyerAgreeRights(){//买家同意维权
	document.location.href="${base}/rights/buyerAgreeRights.htm?rightsId=${rightsId!}";
}
function buyerApply(){//申请客服介入处理
	if(confirm("确定申请客服介入吗？")){
		document.location.href="${base}/rights/buyerApply.htm?rightsId=${rightsId!}";
	}
}
function sendMessageFocus(){
	$("#sendMsgBtn").focus();
}
function refundInfoFocus(){
	if($("#refundInfoBtn").val()==undefined){
		alert('您已发货，请耐心等待');
	}
	$("#refundInfoBtn").focus();
}
</script>   
