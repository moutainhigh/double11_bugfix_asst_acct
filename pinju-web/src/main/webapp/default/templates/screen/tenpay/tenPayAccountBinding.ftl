<title>店铺--开店</title>
<link rel="stylesheet" href="http://static.pinju.com/css/open.css?t=20111201" type="text/css" media="screen" />
<SCRIPT src="http://static.pinju.com/js/shop/shopOpen.js"></SCRIPT>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>


<input type="hidden" value="red_shopOpen" id="my-page" />
<form id="accountForm" name="accountForm" action="" method="post">
<input type="hidden" value="${returnModule!}" name="returnModule">
<input type="hidden" value="${merchantno!}" name="merchantno">
<input type="hidden" value="${sellerType!}" name="sellerType">



<div class="openshop">

			<div class="ptitle">
				<h1>交易账号设定</h1>
				<span class="eye"><a href="/shop/iWillOpenShopAction.htm"">返回</a></span>
			</div>

			<p class="exinfo">以下操作关系到您在交易中的货款收支，请务必完成。<a href="http://service.pinju.com/cms/html/help/selleropen/" target="_blank">开店帮助</a></p>
			<#if tenPayError != "">
				<div class="infowrap">
					<div class="tipsarea">
						<#if tenPayError == "2" || tenPayError == "1">
							<div class="row">
								<a target="_blank" href="${base}/my/tenpay-account.htm" class="btn-bindaccount">
									<span>立即绑定收款账号</span>
								</a>
							</div>
						</#if>
						<#if tenPayError == "3" || tenPayError == "1">
							<div class="row">
								<a target="_blank" href="https://www.tenpay.com/cgi-bin/trust/showtrust_refund.cgi?spid=${merchantno!}" 
								class="btn-signtenpay">
									<span>去财付通签约</span>
								</a> 
								<em>浏览并签约“财付通委托退款服务”的各项条款</em>
							</div>
						</#if>
					</div>
				</div>
			</#if>
					<#if tenPayError??>
						<#if tenPayError == "">
							<div class="infowrap">
								<div class="tipsarea">
									<div class="row"><p><span class="icon-ok">收款账号绑定<em>已完成</em></span> </div>
									<div class="row"><span class="icon-ok">财付通委托退款服务签约 <em>已完成</em></span> </div>
								</div>
							</div>
						<#elseif tenPayError == "1"> <!--未绑定未签约-->
							
						<#elseif tenPayError == "2"> <!--未绑定-->
							<div class="infowrap">
								<div class="tipsarea">
									<div class="row"><span class="icon-ok">财付通委托退款服务签约 <em>已完成</em></span> </div>
								</div>
							</div>
						<#elseif tenPayError == "3"> <!--未签约-->
							<div class="infowrap">
								<div class="tipsarea">
									<div class="row"><p><span class="icon-ok">收款账号绑定<em>已完成</em></span> </div>
								</div>
							</div>
							
						</#if>
					<#else>	
					
					</#if>
				</div>
			</div>
</div>
</form>
<a id="hasQuestion" href="" target="_blank"></a>
<script>
$(function() {
	$(".btn-signtenpay").click(function(){
		var dialog = art.dialog({
			title: '品聚网信息提示',
			content: '您正在浏览并签约“财付通委托退款服务”的各项条款的流程中。',
			icon: 'succeed',
			button: [
						{
							name: '已完成签约',
							callback: function () {
								accountNext();
								return false;
							},
							focus: true
						},
						
						{
							name: '遇到问题',
							callback: function () {
								hasQuestion();
								return false;
							}
						}
					],
			width:500,
			lock: true
		});
	});
	
	$(".btn-bindaccount").click(function(){
		var dialog = art.dialog({
			title: '品聚网信息提示',
			content: '您正在绑定收款账号（财付通账号）的流程中。',
			icon: 'succeed',
			button: [
						{
							name: '已完成绑定',
							callback: function () {
								accountNext();
								return false;
							},
							focus: true
						},
						{
							name: '遇到问题',
							callback: function () {
								hasQuestion();
								return false;
							}
						}
					],
			width:500,
			lock: true
		});
	});
})
	
	function closeDiv(){
		$("#closeDiv").hide();
	}
	
	function hasQuestion(){
		window.open("http://service.pinju.com/cms/html/2011/maexplain_1024/81.html");
	}
	
	function accountNext(){
		var action = "/tenpay/accountNextAction.htm";
		$("#accountForm").attr("action",action);
		$("#accountForm").submit();
	}
</script>
