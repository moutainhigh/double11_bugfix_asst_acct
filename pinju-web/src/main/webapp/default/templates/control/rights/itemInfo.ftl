
<div class="rights_box cf" style="padding-bottom:0px">
	<div class="tit"><span class="txt">商品信息</span></div>
	<div class="rights_goods">
	<table class="rights_goods_tab">
		<tr>
			<th class="bla1">商品</th>
			<th class="bla2">单价(元)</th>
			<th class="bla3">数量</th>
			<th class="bla4">优惠(元)</th>
			<th class="bla5">小计(元)</th>
		</tr>
		<tr>
		<#if query.orderItemDO??>
			<#assign orderItemDO=query.orderItemDO>
			<td>
				<div class="goods" style='text-align:center'>
					<div class="pic_box">
						<#if orderItemDO?? && orderItemDO.itemPicUrl??>
						<a href="${imageServer!}${(orderItemDO.itemPicUrl)?html}" target="_blank"><img width="88" height="88" src="${imageServer!}${(orderItemDO.itemPicUrl)?html}" /></a>
						</#if>
					</div>
					<span class="bd">
					<#if orderItemDO?? && orderItemDO.itemId?? && orderItemDO.itemTitle??>
					<a target="_blank" class="lh20 items_link" href="${base}/snapshot/itemSnapshot.htm?arg1=${(orderItemDO.snapId)!}">${((orderItemDO.itemTitle)?html)!}</a>
					</#if>
					</span>
            		<#if orderItemDO?? && orderItemDO.itemSkuAttributes?? && orderItemDO.itemSkuAttributes != "0">
            			<p class='hui'>${orderItemDO.itemSkuAttributes}<p>
            		</#if>								
				</div>
			</td>
			<td>
				<span class="bd">${(orderItemDO.originalPriceByYuan)!''}</span>
			</td>
			<td><span class="bd">${((orderItemDO.buyNum)?html)!}</span></td>
			<td><span class="bd">${((orderItemDO.discountAmountByYuan)!)}</span></td>
			<td><span class="bd">${(orderItemDO.totalAmountByYuan)!}</span></td>
		<#else>
			<td colspan='5'><span class="bd"><font color='red'>加载商品详情信息失败！</font></span></td>
		</#if>
		</tr>
	</table>
	</div>
</div>
	