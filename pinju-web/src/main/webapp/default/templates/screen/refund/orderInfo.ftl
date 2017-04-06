<div class="tuikuanleft top10">
  	<div class="boxred202"></div>
    <div class="tuikuanleft-2">
				<div class="tuikuanleft19 paddingtb10 t3">
          <center><strong>订单详情</strong></center>
       </div>
      
      <ul> 
      <li>订单编号：${order.orderId}</li>
      <li>成交时间：${order.gmtCreate?datetime}</li>
      <li>订单状态：${order.orderStateDisplay}</li>
      <li>商品总价：<#if priceMap??>${priceMap.get('totalPrice')!"0.00"} 元</#if> </li>
      <li>卖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：${order.sellerNick!}</li>
      <li>买&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：${order.buyerNick}</li>
      <li>运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 费：${orderLogisticsDO.postPriceByYuan}元</li>
      <li>订单优惠：${couponMoneyByYuan}元</li>
      <li>订单总计：${order.priceAmountByYuan}元</li>
      <li>实付款：
      <#if priceMap??>${priceMap.get('realPayPrice')!"0.00"} 元 </#if></li>
  </ul>
</div>
</div>