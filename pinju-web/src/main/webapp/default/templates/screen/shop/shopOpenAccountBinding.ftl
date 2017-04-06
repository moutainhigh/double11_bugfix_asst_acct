<title>店铺--开店</title>
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/open.css?t=20111201" type="text/css" media="screen" />
<SCRIPT src="http://static.pinju.com/js/shop/shopOpen.js"></SCRIPT>
<input type="hidden" value="red_shopOpen" id="my-page" />
<form id="openShopForm" name="openShopForm" action="${base}/shop/shopOpenBeginAction.htm" method="post">
<div class="open-pocess">
			<div class="title">
				<h2>请完成以下支付账号设定</h2>
				<div class="horizen_gray"><span class="horizen_red"></span></div>
			</div>
			<div class="bindwrap">
				<div class="acc_set set1">
					<a class="btn" target="_blank" href="${base}/my/tenpay-account.htm">立即绑定账号</a>
					<span class="tips-normal">绑定您在品聚网的支付账号</span>
				</div>
				
				<div class="acc_set set2">
					<a class="btn" target="_blank" href="https://www.tenpay.com/cgi-bin/trust/showtrust_refund.cgi?spid=${merchantno!}">去财付通签约</a>
					<span class="tips-normal">浏览并签约“财付通委托退款服务”的各项条款</span>
				</div>
			</div>
			<div class="next-btn-box">
				<#if sellerType == 0>
					<a class="next-btn" href="javascript:;" onclick="shopOpenBegin(0,1)">下一步</a>
				<#elseif sellerType == 1>
					<a class="next-btn" href="javascript:;" onclick="shopOpenBegin(1,1)">下一步</a>
				<#elseif sellerType == 2>
					<a class="next-btn" href="javascript:;" onclick="shopOpenBegin(2,1)">下一步</a>
				</#if>
				<#if tenPayError?? && tenPayError!="">
					<div class="tips-error" id="closeDiv"><a class="close" href="javascript:;" onclick="closeDiv()"></a>${tenPayError}</div>
				</#if>
				<span>如果您已经完成了以上设定，请点击“下一步”</span>
			</div>
</div>
</form>
<script>
	function closeDiv(){
		$("#closeDiv").hide();
	}
</script>