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
		<div class="buy-nav" style="margin-top:10px;text-indent: 12px">您的位置：<a href="http://www.pinju.com" style="color:#3399FF">首页</a> &gt; <a href="http://www.pinju.com/management/buy.htm" style="color:#3399FF">我的品聚</a> &gt; 付款成功</div>
		<ul class="buy-title">
		<li class="step1">1.确认订单信息</li>
		<li class="step2 step1c">2.付款</li>
		<li class="step3">3.发货</li>
		</ul>
		<p class="title15">付款成功</p>
		<div class="line">
			<div class="line-hong"></div>
		</div>
		<div class="pay-success">
			<img src="http://static.pinju.com/images/ok.gif" />
			<ul class="pay-news">
				<li><h3>您已经成功付款<span class="cheng">${priceAmount!""}</span>元！</h3></li>
				
				<li>订单编号：
					<#list orderId as orderId>
						<a target="_blank"  class="lan" href="${base}/orderSeller/orderinfoBuy.htm?oid=${orderId}">${orderId}</a> &nbsp;&nbsp;  
					</#list>
				</li>
				<li>您现在可以：</li>
				<li><a href="http://www.pinju.com/orderBuyer/orderManager.htm" class="lan">返回我已买到的商品</a></li>
			</ul>
		</div>
	</div>
	
		