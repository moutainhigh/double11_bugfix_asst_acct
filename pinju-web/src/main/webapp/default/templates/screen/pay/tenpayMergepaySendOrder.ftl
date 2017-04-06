<#setting classic_compatible=true>

<title>品聚网</title>
<link href="http://static.pinju.com/css/buyer.css" rel="stylesheet" type="text/css" media="screen">
<link href="http://static.pinju.com/css/rao/buy.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/js/order/OrderTenPay.js?t=20111212.js"></script>

<style type="text/css">
        .container{text-align:left;margin:2px auto;width:800px;}
        input{display:block;}
        .center{text-align:center;}
        .mr10{margin-right: 8px;color: #333333;}
        .red { color:#BE0000; }
        label{display:block;}
</style>
		
		<div class="sell_tx_box" style="width:100%;">
		
			</br>
			<a class="help" target="_blank" style="background: url(http://static.pinju.com/images/help_icon_03.gif) no-repeat scroll 0 0 ;cursor: help;float: right;height: 19px;width: 70px;" href="http://service.pinju.com"></a>
			您的位置：<a href="http://www.pinju.com" class="mr10">首页</a> &gt; 
			<a href="http://www.pinju.com/my/info.htm" class="mr10">我的品聚</a> &gt; 
			<span class="red">付款到担保账户</span><a class="help" href="http://service.pinju.com" ></a>
		</div>
		<ul class="buy-title">
			<li class="step1">1.确认订单信息</li>
			<li class="step2 step2c">2.付款到担保账户</li>
			<li class="step2">3.确认收货</li>
			<li class="step3">4.评价商品</li>
		</ul>
		

		<div style="margin-bottom:5px">
			<h1>订单已提交，请您尽快付款！</h1>
		</div>
		
		<div style="background-color: #D4D4D4;clear: both;float: left;height: 2px;margin: 0px 5px 10px 0px;width: 100%;">
			<div style="background: none repeat scroll 0 0 #D72426;float: left;height: 2px;width: 150px;"></div>
		</div>
		
		<div class="row">
			<div class="tips-text">
			<p><strong>
				<#if requestNoError>
					您本次下单超过10笔，系统自动支付前10笔，其余订单请在“已买到的商品”中进行支付<br>
				</#if>
					尊敬的客户，您的订单已经生成，如需修改价格请在点击“继续支付”前与卖家联系，否则将无法修改价格。</strong><br />
					普通订单将为您保留72小时（自下单之日起），72小时后若仍未付款，系统将自动关闭该订单。<p>
					<font style="color:red">限时折扣订单，请在30分钟内完成付款；体验购订单，请在3小时内完成付款。</font>
			</div>
			<div class="orderlist-view">
			
				<table>
					<thead>
						<tr>
							<th style="height:20px" colspan="4">支付信息</th>
						</tr>
					</thead>
					<tbody>
					<form action="${base}/orderPay/pay.htm" method="post" id="refreshFrom">
					<#assign i=0>
					<#list paramList as singlepayParam>
					
					<#if (i < 10) >
					<input type="hidden" name="orderId" value="${singlepayParam.spBillno}">
					
						<tr>
							<td>订单号：<a href="/orderSeller/orderinfoBuy.htm?oid=${singlepayParam.spBillno}">${singlepayParam.spBillno}</a></td>
							<td>卖家：<a href="http://sns.pinju.com/${singlepayParam.sellerId}" target="_blank">${singlepayParam.sellerNick}</a></td>
							
							<#if (i == 0) >
							<td rowspan="${paramList.size()}"><span style="font-weight:bold;">支付金额：<strong style=" font-size:18px;">${totalFee}</strong></span></td>
							<td rowspan="${paramList.size()}">
								若有价格变化，请<a style="font-weight:bold;" class="refresh" href="#">刷新</a>
							</td>
							</#if>
						</tr>
					</#if>
					
					<#assign i = i + 1>
					
					</#list>
					
						
					</form>
					<input type="hidden" value = "${totalFee}" id = "totalFee">
					</tbody>
				</table>
				
				<div class="btnrow btn-imgtext">
				<form name="frm" id="payForm" action="${postUrl}" method="post">
					<input type="hidden" name="request_no" value="${requestNO}" />
					<input type="hidden" name="spid" value="${spid}" />
					<input type="hidden" name="return_url" value="${returnUrl}" />
					<input type="hidden" name="sign" value="${sign}" />
					<button class="continuepay" id="btnPay" type="button" onclick="f_postPay()"><span>继续支付</span></button>
					<button class="continuepay" id="jiPay" type="button" style="display:none"><span>继续支付</span></button>
					<a href="#" id="backToOrderList" class="back-to-list-btn">返回订单列表</a>
					<#assign key=0>
						<#list requestList as request>
						<#if (key < 10) >
						<#if (key == 0) >
							<input type="hidden" name="request" value="${request}" />
						<#else>
							<input type="hidden" name="request${key}" value="${request}" />
						</#if>
						</#if>
						
					<#assign key = key + 1>
					</#list>
				</form>
					<br>
					<div class="assure">
						<div class="pic" ></div>
						<p class="txt" >品聚网使用担保交易，确保您的交易安全</p>
					</div>
					<center>
						<div style="display:none;width:400px" class="tips-text" id="errorDiv">
						</div>

						</br>
						<img src="http://static.pinju.com/images/pay.png">

					</center>
				
				</div>
			</div>
		
		</div>

