<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<title>品聚网-退款管理</title>
<div class="wrap cf">
		<div class="tipswrap">
			<h2>友情提示</h2>
			<div class="infocat warn"></div>
			<div class="content">
				<h3>${errorMessage?default("您不能进行此操作！")}</h3>
				<p>您现在可以：</p>
				<p><a href="${base}/orderBuyer/orderManager.htm">返回我已买到的商品</a></p>
				<p><a href="/refund/buyerRefundList.htm">返回退款列表</a></p>
				<p><a href='${returnUrl!}'>查看最近的退款详情</a></p>
			</div>
		</div>

</div>