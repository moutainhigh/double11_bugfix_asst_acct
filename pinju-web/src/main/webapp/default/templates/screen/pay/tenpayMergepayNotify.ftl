<#setting classic_compatible=true>

		<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
		<link href="http://static.pinju.com/css/pay.css" rel="stylesheet" type="text/css" media="screen" /> 
		
		<style type="text/css">
			html {margin: 0;}
		.container{text-align:left;margin:2px auto;width:800px;}
        input{display:block;}
        .center{text-align:center;}
        .mr10{margin-right: 8px;color: #333333;}
        .red { color:#BE0000; }
        label{display:block;}
		</style>
	<title>品聚网</title>
	
    <div class="pay-body">
<div class="sell_tx_box">
	您的位置：<a href="http://www.pinju.com" class="mr10">首页</a> &gt; 
	<a href="http://www.pinju.com/my/info.htm" class="mr10">我的品聚</a> &gt; 
	<span class="red">付款成功</span>
</div>
<ul class="buy-title">
<li class="step1">1.确认订单信息</li>
<li class="step2 step2c">2.付款成功</li>
<li class="step2">3.确认收货</li>
<li class="step3">4.评价商品</li>
</ul>
		<p class="title15">付款成功</p>
		<div class="line">
			<div class="line-hong"></div>
		</div>
		<div class="pay-success">
			<img src="http://static.pinju.com/images/ok.gif" />
			<ul class="pay-news">
				<li><h3>您已经成功付款<span class="cheng">${totalFee}</span>元！</h3></li>
				<li>您可以在已买的商品中，查看卖家发货及物流信息。</li>
				</br>
				<li>订单编号：
					<#list orderIdList as param>
						<a target="_blank"  class="lan" href="${base}/orderSeller/orderinfoBuy.htm?oid=${param.toString()}">${param.toString()}</a> &nbsp;&nbsp;  
					</#list>
				</li>
				<li>您现在可以：</li>
				<li><a href="http://www.pinju.com/orderBuyer/orderManager.htm" class="lan">返回我已买到的商品</a></li>
				<!-- <li><a href="#" class="lan">给我的钱包充值</a></li>
				<li><a href="#" class="lan">返回我的盛付通钱包首页</a></li> -->
			</ul>
		</div>
	</div>
	
		