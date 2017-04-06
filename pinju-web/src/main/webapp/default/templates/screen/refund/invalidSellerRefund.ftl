<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<!--内容页面开始-->
<title>错误提示</title>

<div class="wrap cf">
	<div class="tipswrap">
		<h2>错误提示</h2>
		<div class="infocat warn"></div>
		<div class="content">
			<h3> ${errorMessage?default("您访问的地址不存在！")}</h3>
			<p>您现在可以：</p>
			   <p><a href="${base}/orderSeller/ordermanagesell.htm">返回我已卖出的商品</a></p>
				<p><a href="${base}/refund/sellerRefundList.htm">返回我收到的退款</a></p>
		</div>
	</div>
</div>