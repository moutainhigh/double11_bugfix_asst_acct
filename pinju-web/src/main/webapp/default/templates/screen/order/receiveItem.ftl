<#setting classic_compatible=true>
<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link href="http://static.pinju.com/css/shopcart/buycart.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/pay.css" rel="stylesheet" type="text/css" media="screen" rel="stylesheet" /> 
<link href="http://static.pinju.com/css/member.css" type="text/css" media="screen" rel="stylesheet" />
<script type="text/javascript" src="http://static.pinju.com/js/order/buyOrderReceive.js?t=20111122.js"></script>
<title>品聚网</title>
	
<div class="pay-body">
	
	<div class="pay-nav" style="text-indent: 12px">
	<a class="help" target="_blank" style="background: url(http://static.pinju.com/images/help_icon_03.gif) no-repeat scroll 0 0 transparent;cursor: help;float: right;height: 19px;width: 70px; position:relative; top:8px;" href="http://service.pinju.com"></a>
	您的位置：<a href="http://www.pinju.com" style="color: #333333;">首页</a> &gt; <a href="http://www.pinju.com/my/info.htm" style="color: #333333;">我的品聚</a> &gt; <span style="color: #BE0000;">确认收货</span></div>
	
	<ul class="buy-title">
		<li class="step1">1.确认订单信息</li>
		<li class="step2">2.付款到担保账户</li>
		<li class="step2 step2c">3.确认收货</li>
		<li class="step3">4.评价商品</li>
	</ul>
	
	
	<p class="title15">我已收到货，同意付款给卖家</p>
	<div class="line">
		<div class="line-hong"></div>
	</div>
	<div class="tabs-panels" style="margin-top:15px;">
  <h3 class="order-h3">订单信息</h3>
   <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="border-bottom:1px solid #ccc;">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    	<tr><td colspan="2"><strong>卖家信息</strong></td></tr>
	        <tr>
	        	<td width="30%">店铺： <a target="_blank" title="点击此处可以进入卖家店铺" href="http://shop${orderDO.shopId!""}.pinju.com">${(orderDO.shopName!"")?html}</a>  </td>
	        	<td>店主： <a target="_blank" href="http://sns.pinju.com/${orderDO.sellerId!""}">${(orderDO.sellerNick!"")?html}</a></td>
	        </tr>
	    </table>
	</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="3"><strong>订单信息</strong></td>
      </tr>
      <tr>
        <td width="33%">订单编号：<a target="_blank" title="点击此处可以查看订单详情" href="${base}/orderSeller/orderinfoBuy.htm?oid=${orderDO.orderId}">${orderDO.orderId!""}</a></td>

        <td width="33%">成交时间：${orderDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}<input id="gmtdate" type="hidden" value="${orderDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}"></td>
        <td width="33%">付款时间： <#if vouchPayDO.dealTime?exists>${vouchPayDO.dealTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
      </tr>
      <tr>
      	<td></td>
        <td>发货时间：
        	<#if orderLogisticsDO.consingTime?exists>
        		${orderLogisticsDO.consingTime?string('yyyy-MM-dd HH:mm:ss')}
        		<input type="hidden" value="${orderLogisticsDO.consingTime?string('yyyy-MM-dd HH:mm:ss')}" id="consingtime" />
        	</#if>
        	
        </td>
        <td>确认时间：</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="ordertable">
      <tr class="hd">
        <td width="20%">商品</td>
        <td width="20%">商品属性</td>
        <td width="10%">单价(元)</td>
        <td width="10%">数量</td>
        <td width="20%">优惠</td>
        <td width="10%">已退款(元)</td>
        <td width="10%">应付总价(元)</td>
      </tr>
      
      <#if orderItemList?exists>
	    <#list orderItemList as orderItemDO>
	  			<tr>
				    <td>
				    	<div class="pic"><a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" title="点击此处可以查看商品详情"><img width="50px" height="50px" src="${imageServer!""}${orderItemDO.itemPicUrl}" class="pay-goods"/></a></div>
				    	<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" title="点击此处可以查看商品详情"><span>${(orderItemDO.itemTitle!"")?html}</span></a>
				    </td>
				    <td align="center">
				    	<#if orderItemDO.itemSkuAttributes?exists>
			        		<#if orderItemDO.itemSkuAttributes != "0">
				        		${(orderItemDO.itemSkuAttributes!"")?html}
				        	</#if>
			        	</#if>
				    </td>
				    <td align="center">${orderItemDO.originalPriceByYuan!""}</td>
				    <td align="center">${orderItemDO.buyNum!""}</td>
				    
				   <td align="center">
			        	<#if descaMap??>
			        		<#if descaMap.size() &gt; 0 >
					        	<#list descaMap.keySet() as key>
					        		<#if orderItemDO.orderItemId == key>
				        				<#list descaMap.get(key) as descValue>
				        					${descValue}<br />
				        				</#list>
					        		</#if>
					        	</#list>
					        <#else>
				        		--
				        	</#if>
				        </#if>
			        </td>
				   
				    <td align="center">
				    	<#if orderItemDO.refundState == 9 || orderItemDO.refundState == 5>
				    		${orderItemDO.refundPriceByYuan!""} 
				    	<#else>
				    		--
				    	</#if>
				    </td>
				    <td align="center">${orderItemDO.totalAmountByYuan!""}</td>
				  </tr>
	  		</#list>
      	<tr>
      		<td colspan="7" align="right">
      			<#if couponInfo != "">
      				<span class="cheng">* 店铺优惠券：${(couponInfo!"")?html}</span><br /><br />
      			</#if>
	      		订单总价（不含运费）：<span class="cheng">${orderOriginalPrice!""}</span>元<br />
	      		退款金额：<span class="cheng">${refundTotalPrice!""}</span>元<br />
				运费：<span class="cheng">${orderLogisticsDO.postPriceByYuan!""}</span>元<br />
				<hr style="width:200px; height:1px; color:#be0000; margin:5px 0; border:0px; border-top:1px solid #be0000; display: inline-block;" />		
				<br />总计：<span class="cheng">${orderTotalPrice!""}</span>元<br /><br /><input type="hidden" id="orderTotalPrice" value="${orderTotalPrice!""}" />
      		</td>
      	</tr>
      </#if>
      		
    </table></td>
  </tr>
  <tr>
  	<td>
  		<form name="receiveForm" id="receiveForm" action="${base}/orderPay/affirmPay.htm" method="post">
			<input type="hidden" value="${orderDO.orderId}" name="orderId"/>
			<input type="hidden" name="tid" id="tid" value="" />
			<input style="display:none">
			<ul class="pay-sure" >
				<li><em class="hong">√</em> <strong class="cheng">请收到货之后，再确认收货！否则您可能钱货两空！</strong></li>
				<li><em>√</em> 如果您想申请退款，请返回到 <a href="${base}/orderBuyer/orderManager.htm" class="lan" title="点击此处可以查看订单列表">订单列表页</a> 申请退款</li>
				<li>请输入您的品聚密码：</li>
				<li><input type="password" name="passWord" id="payPassword" title="请输入品聚密码" maxlength="16"/>
			 	 </li>
				<li id="submitButton" style="line-height:33px;">
				     <input type="button" id="pay-button" value="我已确认，立即付款" class="btn-juselong" style="cursor:pointer;" title="我已确认，立即付款"/>
			 		 <a href="${base}/security/forgot-password.htm" class="lan" title="点击此处可以去找回品聚密码">找回品聚密码</a>
			 	</li>
			 	
			 	<li class="left280 top10" id="submitLoad" style="display: none; margin-top:10px">
					<img align="absmiddle" src="http://static.pinju.com/images/ajax/loadding.gif"> 正在提交中，请稍后......
				</li>
			</ul>
		</form>
  	</td>
  </tr>
  
</table>
  </div>
</div>

<script type="text/javascript" src="http://static.pinju.com/js/member/key.js?t=20111213.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/buyOrderReceive.js?t=20111213.js"></script>
		