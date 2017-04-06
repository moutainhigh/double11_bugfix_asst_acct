<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<!--内容页面开始-->
<title>错误提示</title>

<div class="wrap cf">
	<div class="tipswrap">
		<h2>错误提示</h2>
		<div class="infocat error"></div>
		<div class="content">
			<h3>
				${errorMessage}
			    <#if errorOrderId?exists>
			                 订单号为：&nbsp;<font style="color:red">${errorOrderId}</font>
			    </#if>
			</h3>
			<p>您现在可以：</p>
			<#if tp?exists>
				<#if tp==2>
					<p><a href="http://www.pinju.com/orderSeller/ordermanagesell.htm">返回我已卖出的商品</a></p>
				<#else>
					<p><a href="http://www.pinju.com/orderBuyer/orderManager.htm">返回我已买到的商品</a></p>
				</#if>
			<#else>
				<p><a href="http://www.pinju.com/orderBuyer/orderManager.htm">返回我已买到的商品</a></p>
			</#if>
		</div>
	</div>
</div>