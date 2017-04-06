
<div class="tuikuanleft">
	<div class="boxred202"></div>
	<div class="tuikuanleft-2">
		<div class="tuikuanleft19 paddingtb10 t3">
        	<center><strong>订单详情</strong></center>
     	</div>
		<ul> 
		<#if query.orderDO??>
    		<#assign orderDO=query.orderDO>
    		<li>订单编号：${(orderDO.orderId)!}</li>
    		<li>成交时间：${(orderDO.stateModifyTime)?string("yyyy-MM-dd HH:mm:ss")!}</li>
    		<li>订单状态：<#if orderDO.orderState=1>未付款
    			<#elseif orderDO.orderState=2>已付款
    			<#elseif orderDO.orderState=3>已发货
    			<#elseif orderDO.orderState=5>交易成功
    			<#elseif orderDO.orderState=6>交易关闭</#if>
    		</li>
    		<li>卖家昵称：${((orderDO.sellerNick)?html)!}</li>
    		<li>商品总价：${(orderItemAmount?html)!} 元</li>
    		<li>运　　费：${(freight?html)!} 元</li>
    		<li>订单优惠：${(youhuifei!)?html} 元</li> 
    		<li>订单总计：${orderDO.priceAmountByYuan} 元</li>
    		<li>实   付   款：${(payment?html)!} 元</li><!--（注:实付款=商品总价+运费-优惠）-->	 
    	<#else>
    		<li><font color='red'>加载订单详情信息失败！</font></li>               
    	</#if>
    	</ul>
	</div>
</div>
