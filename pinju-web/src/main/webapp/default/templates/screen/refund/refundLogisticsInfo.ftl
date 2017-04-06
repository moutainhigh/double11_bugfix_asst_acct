<link href="http://static.pinju.com/css/wuliu.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<title>物流详细页</title>

<div class="tuikuanleft top10">
  	<div class="boxred202"></div>
    <div class="tuikuanleft-2">
				<div class="tuikuanleft19 paddingtb10 t3">
          <center><strong>订单详情</strong></center>
       </div>
      
      <ul> 
      <li>订单编号：${order.orderId?c}</li>
      <li>成交时间：${order.gmtCreate?datetime}</li>
      <li>订单状态：${order.orderStateDisplay}</li>
      <li>商品总价：<#if priceMap??>${priceMap.get('totalPrice')!"0.00"} 元</#if></li>
      <li>卖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：${order.sellerNick}</li>
      <li>买&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：${order.buyerNick}</li>
      <li>运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 费：${orderLogisticsDO.postPriceByYuan}元</li>
      <li>订单总计：${order.priceAmountByYuan}元</li>
      <li>实付款：<#if priceMap??>${priceMap.get('realPayPrice')!"0.00"} 元 </#if></li>
  </ul>
</div>
</div>
<div class=" boxh0 boxh760 floatright top10 ">
<P class=" title15">物流详情</P>
<P class="wl-h1">物流信息</P>
<ul class="wl-chuangj">
<li>发货方式：自己联系</li>
<li>物流方式：
	<#if logisticsCorpDO??>
		<#if logisticsCorpDO.logisticsType ==1>平邮
		<#elseif logisticsCorpDO.logisticsType ==2>快递
		<#elseif logisticsCorpDO.logisticsType ==3>EMS
		</#if>
	</#if>
</li>
<li>物流公司：<#if logisticsCorpDO??> ${logisticsCorpDO.corpName!""}<#if logisticsCorpDO.corpUrl??>(<a target="_blank"  href="${logisticsCorpDO.corpUrl!"about:blank"}"  class="lan">${logisticsCorpDO.corpUrl!""})</a></#if></#if></li>
<li>运单号码：${cd!""}</li>
<li>物流跟踪：
<#if logisticsCorpDO??>
    <#if logisticsServiceDO.step?exists>
      <#if (logisticsServiceDO.step?size>0) >
      	<span class="bor-yellow">！以下信息由物流公司提供，如有疑问请咨询 <a target="_blank" href="${logisticsCorpDO.corpUrl!"about:blank"}"  class="lan">【${logisticsCorpDO.corpName!""}】</a></span>
     	 <ul class="wl-genzh">
    	  <#list logisticsServiceDO.step as lst>
    	   <#if !lst_has_next>
    	   		<li class="hong">${lst.datetime!""} &nbsp;&nbsp; ${lst.remark!""}</li>
    		<#else>
    			<li>${lst.datetime!""} &nbsp;&nbsp; ${lst.remark!""}</li>
    		</#if>
    	  	
          </#list>
          </ul>
      <#else>
    		<span class="bor-yellow">！无法获取有效信息，请至  <a target="_blank"  href="${logisticsCorpDO.corpUrl!"about:blank"}"  class="lan">【${logisticsCorpDO.corpName!""}】</a>查询</span>
      </#if>
   <#else>  
   		<span class="bor-yellow">！无法获取有效信息，请至  <a target="_blank"  href="${logisticsCorpDO.corpUrl!"about:blank"}"  class="lan">【${logisticsCorpDO.corpName!""}】</a>查询</span>
   </#if>
 </#if>
 </li>
</ul>
</div>