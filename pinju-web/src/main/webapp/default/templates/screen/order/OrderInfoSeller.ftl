<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/member.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/cart.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/js/wbox/css/wbox.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/pop/facebox.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/buy_sell_ui.css" type="text/css" media="screen" rel="stylesheet" />
<script type="text/javascript" src="http://static.pinju.com/js/wbox/wbox.js"></script>
<script type="text/javascript" src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<script type="text/javascript" src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/orderinfoseller.js?t=2011121515.js"></script>
<STYLE type="text/css">
	.mywBox {WIDTH: 410px;HEIGHT:100px;}
	
	.hong {color: #FF5500;}
</STYLE>
<title>品聚网</title>

<div class="sell-content">
<div class="sell_tx_box">
   	您的位置：
   <a href="http://www.pinju.com/my/sell.htm" class="mr10">我是卖家</a>>
   <a href="${base}/orderSeller/ordermanagesell.htm" class="mr10">已卖出的商品</a>>
   <span class="red">订单详情  </span>   
</div>
  <div class="cart-tip">
  	<ul class="order-zt">
  		<li><strong>当前订单状态：</strong>
   			<#if orderDO.orderState == 1>
  				<span id="deadlinePay">
	  				<#if isHaveTime == 0>
	  					<strong>等待买家付款</strong><br />
		  				买家还有<span id="deadline" leftDay="${leftDaySec?c}" class="hong"></span>来完成付款，逾期未付款，交易自动关闭<br />
		  				<form action="" id="updateForm" method="post">
		  				<#if memberAuth.hasAsstPerm("trade","price")>
			  				<#if postPay == 1>
			  					<button type="button" class="btn-sorange" onclick="document.getElementById('updateForm').action='${base}/orderSeller/toModifyPrice.htm?orderId=${orderDO.orderId!""}';document.getElementById('updateForm').submit();" id="modifyPriceBTN" >修改价格</button>&nbsp;
			  				<#else>
			  					<button type="button" class="btn-sorange" onclick="checkModifyPrice()" id="modifyPriceBTN" >修改价格</button>&nbsp;
			  				</#if>
		  				</#if>
		  				 <#if memberAuth.hasAsstPerm("trade","close")>
		  					<button type="button" class="btn-sorange" id="closeBTN" onclick="closeSell('${orderDO.orderId!""}')">取消订单</button>
		  				</#if>
		  				</form>
	  				<#else>
	  					<strong>交易关闭</strong><br />
	  					<font color='red'>逾期交易，交易已自动关闭</font>
	  				</#if>
  				</span>
  			<#elseif orderDO.orderState == 2>
  				<strong>买家已付款 等待卖家发货</strong><br />
  				<form id="sendForm" method="post">
  				<#if memberAuth.hasAsstPerm("trade","shipment") || memberAuth.hasAsstPerm("trade","set")><button type="button" class="btn-sorange" onclick="sendDelivery('${orderDO.orderId}')"/>发 货</button></#if></form>
  			<#elseif orderDO.orderState == 3>
  				<span id="deadLineReceive">
	  				<#if isHaveTime == 0>
	  					<#if orderDO.isRefund == 10001>
		  					<strong>卖家已发货，等待买家确认</strong><br />
		  					<from method="post" name="form1" id="form1">
		  					买家还有<span id="deadline" leftDay="${leftDaySec?c}" class="hong"></span>来完成本次交易的 “确认到货” <br />
		  					如果期间买家没有 “确认收货” ，也没有 “申请退款” ，本交易将自动结束，品聚将把货款支付给您<br />
		  					<#if memberAuth.hasAsstPerm("trade","extend-ship")><span class="imgbtn"><button type="button" class="btn-lorange" onclick="delayBuyerReceiveTime('${orderDO.orderId}');"/>延长收货时间</button></span></#if>
		  					</form>
		  				<#else>
		  					<font color='red'>该订单有处理中的退款，待退款完成/关闭后买家才可以确认收货.</font>
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
    <td style="border-bottom:1px solid #ccc;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="3"><strong>${(memberInfo.idCard!"")?html}</strong></td>
        </tr>
      <tr>
        <td width="33%">买家：<a target="_blank" href="http://sns.pinju.com/${orderDO.buyerId!""}">${(orderDO.buyerNick!"")?html}</a></td>
        <td width="33%">真实姓名：${(memberInfo.realName!"")?html}</td>
        <td width="33%">所在地区：${(memberInfo.province!"")?html} ${(memberInfo.city!"")?html} ${(memberInfo.district!"")?html} </td>
      </tr>
      <tr>
        <td>联系电话：${memberInfo.phone!""}</td>
        <td>邮件：${(memberInfo.email!"")?html}</td>
        <td></td>
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
        <td width="33%">订单编号：${orderDO.orderId!""}<input type="hidden" value="${orderDO.orderId!""}" id="shid" /></td>
        <td width="33%">成交时间：
        	<#if orderDO.gmtCreate?exists>
		 		${orderDO.gmtCreate?string('yyyy-MM-dd HH:mm:ss')}<input id="gmtdate" type="hidden" value="${orderDO.gmtCreate?string('yyyy-MM-dd HH:mm:ss')}">
		 	</#if>
         </td>
        <td>付款时间： <#if vouchPayDO.dealTime?exists>${vouchPayDO.dealTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
      </tr>
      <tr>
        <td></td>
        <td width="33%">发货时间：
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
    </table></td>
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
      <tr>
        <td>
        	<#if orderItemDO.snapId == 0>
        		<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm" class="pic"><img width="50px" height="50px" src="${imageServer!""}${orderItemDO.itemPicUrl!""}" /></a>
        		<a target="_blank" class="link" href="${base}/detail/${orderItemDO.itemId}.htm">${(orderItemDO.itemTitle!"")?html}</a>
        	<#else>
        		<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" class="pic"><img width="50px" height="50px" src="${imageServer!""}${orderItemDO.itemPicUrl!""}" /></a>
        		<a target="_blank" class="link" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}">${(orderItemDO.itemTitle!"")?html}</a>
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
      </#if>
      <tr>
      	<td colspan="7" align="right">
      		<#if couponInfo != "">
      			<span class="hong">* 店铺优惠券：${(couponInfo!"")?html}</span><br /><br />
      		</#if>
  			订单总价（不含运费）：${orderAountPriceNoCost!""}元<br />
  			退款总计：${refundAllPrice!""}元<br />
  			运费：${orderLogisticsDO.postPriceByYuan!""}元<br />
  			<hr style="width:200px; height:1px; color:#be0000; margin:5px 0; border:0px; border-top:1px solid #be0000; display: inline-block;" />
  			<br /><span>总计：${orderAllPrice!""}元</span>
  		</td>
       </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="order-wl">
      <tr>
        <td width="9%"><strong>物流信息</strong></td>
        <td width="91%">&nbsp;</td>
      </tr>
      <tr>
        <td align="right">收货地址：</td>
        <td>${(orderLogisticsDO.prov!"")?html} ${(orderLogisticsDO.city!"")?html} ${(orderLogisticsDO.area!"")?html} ${(orderLogisticsDO.address!"")?html}， ${(orderLogisticsDO.post!"")?html}， ${(orderLogisticsDO.fullName!"")?html}， ${(orderLogisticsDO.phone!"")?html}， ${(orderLogisticsDO.mobilePhone!"")?html}</td>
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
        		<#elseif orderLogisticsDO.consignmentMode== "5">
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
	        <#if orderLogisticsDO.consignmentMode== "5">
	        	<td>${(orderLogisticsDO.logisticsName!"")?html}</td>
	        <#else>
	        	<td>${(logisticsCorpDO.corpName!"")?html}</td>
	        </#if>
	       
	      </tr>
	      <tr>
	        <td align="right">运单号：</td>
	        <td>
	        	<#if orderDO.orderState != 1 && orderDO.orderState != 2>
	        		${(orderLogisticsDO.outLogisticsId!"")?html}&nbsp;&nbsp;
	        		<#if logisticsCorpDO?exists>
	        			<#if logisticsCorpDO.corpName?exists>
							<a target="_blank"  href="${base}/orderSeller/logisticsInfoSeller.htm?cd=${(orderLogisticsDO.outLogisticsId!"")?html}&exp=${(orderLogisticsDO.logisticsType!"")?html}&orderId=${orderDO.orderId!""}">查看物流信息</a>       		
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
    </table></td>
  </tr>
</table>
  </div>
</div>

