<h3>交易管理</h3>
<#if memberAuth.hasAsstPerm('trade', 'logistics')>
<li name="logistics-tool"><a href="${base}/logistics/listTemplate.htm">物流工具</a></li>
</#if>
<li name="Orderbuy-tool"><a href="${base}/orderSeller/ordermanagesell.htm">已卖出的商品</a></li>

<#if 1==2>
<!-- <li><a href="${base}/orderSeller/ordermanagesell.htm">已经卖出的商品</a></li> -->
<li><a href="${base}/orderSeller/ordermanagesell.htm">发货</a></li>
<li><a href="#">发货设置</a></li>
<li style="border-bottom:none;"><a href="#">评价管理</a></li>
</#if>