<#setting classic_compatible=true>
<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link href="http://static.pinju.com/css/pay.css" rel="stylesheet" type="text/css" media="screen" /> 

<style type="text/css">
	html {
		margin: 0;
	}
</style>
	<title>品聚网</title>
		
<div class="pay-body">	
<div class="pay-nav" style="text-indent: 12px">您的位置：<a href="http://www.pinju.com" style="color: #333333;">首页</a> &gt; <a href="http://www.pinju.com/my/info.htm" style="color: #333333;">我的品聚</a> &gt; <span style="color: #BE0000;">确认收货</span></div>

<ul class="buy-title">
	<li class="step1">1.确认订单信息</li>
	<li class="step2">2.付款到担保账户</li>
	<li class="step2 step2c">3.确认收货</li>
	<li class="step3">4.评价商品</li>
</ul>



<p class="title15">付款成功</p>
<div class="line">
	<div class="line-hong"></div>
</div>
	<div class="pay-success">
		<img src="http://static.pinju.com/images/ok.gif" />
		<ul class="pay-news">
			<li><h3>交易成功，商家${(orderDO.sellerNick!"")?html}将收到您的货款</h3></li>
			<li>您现在可以：</li>
			<li><a href="http://www.pinju.com/orderBuyer/orderManager.htm" class="lan">返回我已买到的商品</a></li>
			<li><a target="_blank" class="lan" href="${base}/rate/buyer.htm?oid=${orderDO.orderId}"> <b>现在就去评价商品 </b></a>（您的商品评价将会分享到社区中，为社区中的朋友们提供宝贵参考）</li>
		</ul>
	</div>
</div>
</div>
		