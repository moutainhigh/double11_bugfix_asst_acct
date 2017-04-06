<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<title>品聚网-领取优惠券</title>
<div class="wrap cf">
		<div class="tipswrap">
			<h2>领取提示</h2>
			<div class="infocat warn"></div>
			<div class="content">
				<h3><#if isSuccess>
						优惠券领取成功！
					<#else>
						${errorMessage}
					</#if>
				</h3>
				<p>您现在可以：</p>
				<p><a href="http://www.pinju.com/buyerCoupon/buyerCoupon.htm">返回优惠券列表</a></p>
			</div>
		</div>

</div>