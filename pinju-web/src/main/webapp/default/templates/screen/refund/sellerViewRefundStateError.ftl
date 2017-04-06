<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />

<div class="wrap cf">
		<div class="tipswrap">
			<h2>友情提示</h2>
			<div class="infocat warn"></div>
			<div class="content">
				<h3>${errorMessage?default("您不能进行此操作！")}</h3>
				<p>您现在可以：</p>
				<p><a href="${base}/orderSeller/ordermanagesell.htm">返回我已卖出的商品</a></p>
				<p><a href="/refund/sellerRefundList.htm">返回退款列表</a></p>
				<p><a href="${returnUrl!}">查看最近的退款详情</a></p>
			</div>
		</div>

</div>