<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/modify_price.css" type="text/css" media="screen" rel="stylesheet"  /> 
<script type="text/javascript" src="http://static.pinju.com/js/order/modifyPrice.js?t=20111117.js"></script>
<title>品聚网</title>

<form action="${base}/orderSeller/modifyPrice.htm" method="post" id="form1">
<input type="hidden" id="orderOriginalPrice" value="${orderOriginalPrice!""}" />
<input type="hidden" id="orderId" value="${orderId!""}" />
	<div class="mdp_box">
		<p class="mdp_p1"><strong>订单原价(不含运费)：<span>${orderOriginalPrice!""}元</span></strong></p>
		<#if orderItemList?exists>
			<table class="mdp_table">
			<tr>
				<th class="first"></th>
				<th>商品</th>
				<th>单价（元）</th>
				<th>数量</th>
				<th>应付总额（元）</th>
				<th><span class="mdp_tip_outer">涨价或减价<em></em></span></th>
				<th class="last">邮费(元)</th>
			</tr>
			<#list orderItemList as orderItemDO>
				<#if orderItemDO.sellerMarginPrice!"">
					<input type="hidden" value="${orderItemDO.sellerMarginPrice !""}" sellerMarginPrice="sellerMarginPrice"/>
				</#if>
				<input type="hidden" id="obj${orderItemDO.orderItemId}" value="${orderItemDO.getTotalAmountByYuanModifyPrice()}" />
				<input type="hidden" value="${orderItemDO.orderItemId}" orderItemIdArry="orderItemIdArry" name="orderItemId"/>
				<input type="hidden" value="${orderItemDO.sellerMarginPrice!""}" name="modifyBefore" />
				<tr>
					<td class="td1"><a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}"><img height="84px" width="84px" src="${imageServer!""}${orderItemDO.itemPicUrl}" alt="" /></a></td>
					<td class="td2"><span class="title"><a target="_blank" class="lh20 items_link" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}">${(orderItemDO.itemTitle!"")?html}</a></span></td>
					<td class="td3">${orderItemDO.originalPriceByYuan}</td>
					<td class="td4">${orderItemDO.buyNum}</td>
					<td class="td5">${orderItemDO.getTotalAmountByYuanModifyPrice()}</td>
					<td class="td6">
						<span class="input">
							<#if orderItemDO.sellerMarginPrice?exists>
								<input type="text" id="modifyPrice" mdf="mdf" name="modifyPrice" value="${orderItemDO.sellerMarginPriceByYuan}" modifyPriceArry="modifyPriceArry" onblur="updatePrice(this,'#obj${orderItemDO.orderItemId}')"/>
							<#else>
								<input type="text" id="modifyPrice" mdf="mdf" name="modifyPrice" value="0.00" modifyPriceArry="modifyPriceArry" onblur="updatePrice(this,'#obj${orderItemDO.orderItemId}')"/>
							</#if>
						</span>
					</td>
					<#if orderItemDO_index+1==1>
						<td class="td7" rowspan="${orderItemList.size()}">
							<#if orderLogisticsDO.consignmentMode ==2 >
								快递：
							<#elseif orderLogisticsDO.consignmentMode ==1>
								平邮：
							<#elseif orderLogisticsDO.consignmentMode == 3>
								EMS：
							<#else>
								卖家承担运费<input type="hidden" value="${orderLogisticsDO.postPriceByYuan}" id="modifyLogistics" name="modifyLogistics" />
							</#if>
								<#if orderLogisticsDO.consignmentMode != 4>
									<span class="input">
										<input type="text" onblur="updateLogistics()" name="modifyLogistics" value="${orderLogisticsDO.postPriceByYuan}" id="modifyLogistics"/>
									</span>
								</#if>
						</td>
					</#if>
				</tr>
			</#list>
			</table>
			<input type="hidden" value="${order.orderId}" name="orderId" id="orderId" />
			<p class="mdp_p2">收货地址：${(orderLogisticsDO.prov!"")?html} ${(orderLogisticsDO.city!"")?html} ${(orderLogisticsDO.area!"")?html} ${(orderLogisticsDO.address!"")?html}</p>
			<p class="mdp_p3">买家实付：
			<span>${originalAllPrice}</span>
			<span id="displayLogisticsPrice"> + ${orderLogisticsDO.postPriceByYuan}</span>
			<span id="displayUpdatePrice">${sellerAllUpdatePrice}</span>  
			<span id="displayUpdatePrice"> - ${couponPriceByYuan} </span> = <span id="displayTotalPrice">${orderTotalPrice}</span><span>元</span>
			</p>
			<p class="mdp_p4">买家实付 = （商品总额1+商品总额2+……+商品总额n） + 运费 + 涨价或减价 - 促销优惠</p>
			<div><input class="mdp_submit" type="button" value="" onclick="checkModifyPrice()" /></div>
		</#if>
	</div>
	</form>
	
	
		