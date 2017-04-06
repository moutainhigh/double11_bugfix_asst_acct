<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/member.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/cart.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/pop/facebox.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/buy_sell_ui.css" type="text/css" media="screen" rel="stylesheet" />
<script type="text/javascript" src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/buyOrderManager.js?t=20111214.js"></script>

<title>品聚网</title>
<STYLE type="text/css">
		.hong {color: #FF5500;}
</STYLE>
	
<div class="sell-content">
<div class="sell_tx_box">您的位置：
       <a href="http://www.pinju.com/orderBuyer/myBuyer.htm" class="mr10">我是买家</a>>
       <a href="${base}/orderBuyer/orderManager.htm" class="mr10">已买到的商品</a>>
     	  <span class="red">订单详情</span>
   </div>
  <div class="cart-tip">
  	<ul class="order-zt">
  		<li><strong>当前订单状态：</strong>
   			<#if orderDO.orderState == 1>
  				
  				<span id="deadlinePay">
  					<#if isHaveTime == 0>
  						<strong>等待买家付款</strong><br />
		  				您还有<span id="deadline" leftDay="${leftDaySec?c}" class="hong"></span>来完成付款，逾期未付款，交易自动关闭<br />
		  				<form action="" id="controlForm" name="controlForm" method = "post">
			   				<input type="hidden" name="orderId" value="${orderDO.orderId!""}">
			          		<button class="btn-sorange" onclick="document.getElementById('controlForm').action='${base}/orderPay/pay.htm';document.getElementById('controlForm').submit();">付款</button>
			          		<button type="button" class="btn-sorange" onclick="closeSell('${orderDO.orderId!""}')">取消订单</button>
			          	</form>
			        <#else>
			       		<strong>交易关闭</strong><br />
  						<font color='red'>逾期交易，交易已自动关闭</font>
		          	</#if>
	          	</span>
  			<#elseif orderDO.orderState == 2>
  				<strong>买家已付款，等待卖家发货</strong><br />
  				如果您想申请退款，请返回到 <a href="${base}/orderBuyer/orderManager.htm" class="lan">订单列表页</a> 申请退款  
  			<#elseif orderDO.orderState == 3>
  				<span id="deadLineReceive">
	  				<#if isHaveTime == 0>
	  					<#if orderDO.isRefund == 10001>
		  				   	<strong> 卖家已发货，等待买家确认</strong><br />
			  				您还有<span id="deadline" leftDay="${leftDaySec?c}" class="hong"></span>来完成本次交易的 “确认收货”<br />
			  				如果期间内您没有 “确认收货”，也没有 “申请退款”，本次交易将自动结束，品聚将把货款支付给卖家  &nbsp;
			  				如果您想申请退款，请回到 <a href="${base}/orderBuyer/orderManager.htm" class="lan">订单列表页</a> 申请退款<br />  
			  				<form action="${base}/orderPay/receiveItem.htm" id="affirmPay" method = "post">
				   				<input type="hidden" name="orderId" value="${orderDO.orderId!""}">
				          		<button type="button" class="btn-sorange" onclick="checkRefundState()" >确认收货</button>
				          	</form>
			          	<#else>
			          		<font color='red'>该订单有处理中的退款，待退款完成/关闭后再收货.</font>
			          	</#if>
			        <#else>
			        	<strong>交易成功</strong><br />
			        	<font color='red'>超时确认收货，系统默认买家确认收货</font>
		          	</#if>
	          	</span>
  			<#elseif orderDO.orderState == 5>
  				<strong>交易成功</strong>
  			<#elseif orderDO.orderState == 6>
  				<strong>交易关闭</strong><br />
  				<#if closeType != "">
  					关闭类型：${closeType!""}<br />
  				</#if>
  				<#if failReason != "">
  					原因：${failReason!""}
  				</#if>
  			</#if>
   		</li>
   	</ul>
  </div>
  <div class="tabs-panels">
  	<h3 class="order-h3"><span class="bd">订单信息</span></h3>
  	<input type="hidden" value="${orderDO.isRefund!""}" id="isRefund"/>
  	<table width="98%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td style="border-bottom:1px solid #ccc;">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		     	<tr>
		      		<td colspan="2"><strong>${(memberInfo.idCard!"")?html}</strong></td>
		        </tr>
		        <tr>
		        	<td width="30%">店铺： <a target="_blank" href="http://shop${orderDO.shopId!""}.pinju.com">${(orderDO.shopName!"")?html}</a></td> 
		        	<td>店主：  <a target="_blank" href="http://sns.pinju.com/${orderDO.sellerId!""}">${(orderDO.sellerNick!"")?html}</a> </td> 
		        </tr>
		    </table>
		</td>
	  </tr>
	  <tr>
	    <td>
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	     		<tr>
	        		<td colspan="3"><strong>订单信息</strong></td>
	       	    </tr>
	      		<tr>
	       			 <td width="33%">订单编号：${orderDO.orderId!""}</td>
	       			 <td width="33%">成交时间：
	       			 	<#if orderDO.gmtCreate?exists>
	       			 		${orderDO.gmtCreate?string('yyyy-MM-dd HH:mm:ss')}<input id="gmtdate" type="hidden" value="${orderDO.gmtCreate?string('yyyy-MM-dd HH:mm:ss')}">
	       			 	</#if>
	       			 </td>
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
			        <td>
			        	确认时间：
			        	<#if orderDO.orderState == 5>
			        		<#if orderDO.stateModifyTime?exists>
			        			${orderDO.stateModifyTime?string('yyyy-MM-dd HH:mm:ss')}
			        		</#if>
			        	</#if>
			        </td>
			   </tr>
	   		</table>
	    </td>
	  </tr>
	  <tr>
	    <td>
	    	<#if delineDate?exists>
	    		<input type="hidden" value="${delineDate?string('yyyy-MM-dd HH:mm:ss')}" id="delineDate"/>
	    	</#if>
	    	<input type="hidden" value="${orderDO.orderState!""}" id="orderState"/>
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ordertable">
		      <tr class="hd">
		        <td width="20%">商品</td>
		        <td width="20%">商品属性</td>
		        <td width="10%">单价(元)</td>
		        <td width="10%">数量</td>
		      	<td width="20%">优惠(元)</td>  
		      	<td width="10%">已退款(元)</td>
		        <td width="10%">应付总价(元)</td>
		      </tr>
	      
		      
		      <#if orderItemList?exists>
			    <#list orderItemList as orderItemDO>
			    	<input type="hidden" id="isRefund" value="${orderDO.isRefund!""}" />
			      <tr>
			        <td>
			        	<div>
			        		<#if orderItemDO.snapId == 0>
			        			<a href="${base}/detail/${orderItemDO.itemId}.htm" target="_blank" class="pic"><img width="50px" height="50px" src="${imageServer!""}${orderItemDO.itemPicUrl!""}" /></a>
			        		<#else>
			        			<a href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" target="_blank" class="pic"><img width="50px" height="50px" src="${imageServer!""}${orderItemDO.itemPicUrl!""}" /></a>
			        		</#if>
			        	<div>
			        	<#if orderItemDO.snapId == 0>
			        		<a target="_blank" class="link"  href="${base}/detail/${orderItemDO.itemId}.htm">${(orderItemDO.itemTitle!"")?html}</a>
			        	<#else>
			        		<a target="_blank" class="link"  href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}">${(orderItemDO.itemTitle!"")?html}</a>
			        	</#if>
			        </td>
			        <td align="center">
			        	<#if orderItemDO.itemSkuAttributes?exists>
			        		<#if orderItemDO.itemSkuAttributes != "0">
				        		${(orderItemDO.itemSkuAttributes!"")?html}
				        	<#else>
				        		--
				        	</#if>
				        <#else>
				        	--
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
		      				<span class="hong">* 店铺优惠券：${(couponInfo!"")?html}</span><br /><br />
		      			</#if>
	      				订单总价（不含运费）：${orderAountPriceNoCost!""}元<br />
		      			退款总计：${refundAllPrice!""}元<br />
		      			<#if orderLogisticsDO??>
		      				运费：${orderLogisticsDO.postPriceByYuan!""}元<br />
		      			</#if>
		      			<hr style="width:200px; height:1px; color:#be0000; margin:5px 0; border:0px; border-top:1px solid #be0000; display: inline-block;" />
		      			<br />总计：${orderAllPrice!""}元
		      		</td>
		      	</tr>
		      </#if>
	    	</table>
	    </td>
	  </tr>
	  <tr>
	    <td>
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="order-wl">
		      <tr>
		        <td width="9%"><strong>物流信息</strong></td>
		        <td width="91%">&nbsp;</td>
		      </tr>
		      <tr>
		        <td align="right">收货地址：</td>
		        <td>${(orderLogisticsDO.prov!"")?html} ${(orderLogisticsDO.city!"")?html} ${(orderLogisticsDO.area!"")?html} ${(orderLogisticsDO.address!"")?html}， ${(orderLogisticsDO.post!"")?html}， ${(orderLogisticsDO.fullName!"")?html}， ${(orderLogisticsDO.phone!"")?html}， ${(orderLogisticsDO.mobilePhone!"")?html} </td>
		      </tr>
		      <tr>
		        <td align="right">运送方式：</td>
		        <td>
		        	<#if orderLogisticsDO.consignmentMode?exists>
	        			<#if orderLogisticsDO.consignmentMode == "1">
		        			平邮
		        		<#elseif orderLogisticsDO.consignmentMode == "2">
		        			快递
	        			<#elseif orderLogisticsDO.consignmentMode == "3">
	        				EMS
		        		<#elseif orderLogisticsDO.consignmentMode == "4">
		        			卖家承担运费
		        		<#elseif orderLogisticsDO.consignmentMode == "5">
		        			自定义物流
		        		<#else>
		        			无需物流
		        		</#if>		
		        	</#if>
		        </td>
		      </tr>
		      <#if orderLogisticsDO.consignmentMode != "6">
			      <tr>
			        <td align="right">物流公司：</td>
			        <#if orderLogisticsDO.consignmentMode == "5">
			        	<td>${(orderLogisticsDO.logisticsName!"")?html} </td>
			        <#else>
			        	<td>${(logisticsCorpDO.corpName!"")?html} </td>
			        </#if>
			      </tr>
			      <tr>
			        <td align="right">运单号：</td>
			        <td>
			        	<#if orderDO.orderState != 1 && orderDO.orderState != 2>
		        			${(orderLogisticsDO.outLogisticsId!"")?html}&nbsp;&nbsp;
		        			<#if logisticsCorpDO?exists>
		        				<#if logisticsCorpDO.corpName?exists>
		        					<a target="_blank" href="logisticsInfoBuyer.htm?cd=${(orderLogisticsDO.outLogisticsId!"")?html}&exp=${(orderLogisticsDO.logisticsType!"")?html}&logisticsId=${(orderLogisticsDO.logisticsType!"")?html}&orderId=${orderDO.orderId!""}">查看物流信息</a>		        		
			        			</#if>
			        		</#if>
			        	</#if>
			        </td>
			      </tr>
		      </#if>
		      <tr>
		        <td align="right">买家留言：</td>
		        <td>${(orderDO.buyerMeMo!"")?html}</td>
		      </tr>
		    </table>
	    </td>
	  </tr>
	</table>
  </div>
</div>
