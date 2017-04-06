    <div class="cf top10">
    	<div class="cf t2" >
	<div class="boxpadding  zi_14size t3 bd">
				<strong>退款申请协议 </strong>
          </div>
          <div class="boxh2 t5">
          	<div class="boxred floatleft"></div>
          </div>
          
      <div class="cf top10">
                      
  <table>
            <tr align="center">
              <td width="80px">&nbsp;</td>
              <td width="262" height="30px" align="center">商品名称</td>
              <td width="125">单价（元）</td>
              <td width="92">数量</td>
              <td width="113">商品优惠（元）</td>
              <td width="113">小计（元）</td>
            </tr>
	
	  <tr align="center">
             <td colspan="6"><span class=" left20"><img src="http://static.pinju.com/images/refund/shuiping01.gif" width="708" height="1" /></span></td>
     	  </tr>
          
            <tr align="center">
              <td width="80px" height="85px" ><div class="items_pic_box"><a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItem.snapId}"><img  src="${imageServer}${itemDO.picUrl}"  width="80" height="80" /></a></div></td>
			  <td width="262px" align="center">
			<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItem.snapId}">${orderItem.itemTitle?default("")?html}</a>
			<p class="hui">
		       			<#if itemPropertyList?exists>
	        				<#list itemPropertyList as itemProperty>
	        				${itemProperty.name?default("")?html}：${itemProperty.value?default("")?html}&nbsp;&nbsp;&nbsp;
	        				</#list>
	        			</#if>
	        </p>
            </td>
              <td><strong><#if orderItem.originalPrice??>${orderItem.originalPriceByYuan}</#if></strong></td>
              <td><strong>${orderItem.buyNum}</strong></td>
              <td ><strong>${orderItem.discountAmountByYuan}</strong></td>
              <td ><strong>${orderItem.totalAmountByYuan}</strong></td>
            </tr>
    </table> 
      
      <div class=" boxh1 top10 t5"></div>

      <div class="boxh0 paddingtb20 t3"><!--退换货信息 -->
      	<div class="boxw610 zi_shenhui">
              <ul class="refund_state cf"> 
                  <li><span>退款申请时间：</span>${refundDO.gmtCreate?datetime}</li>
                  <li><span>退款原因：</span>${refundDO.applyReasonDisplay}</li>
                  <li><span>是否需要退货：</span>
                  	<#if refundDO.needSalesReturn == 1>需要退货
                  	<#elseif refundDO.needSalesReturn == 0>不需要退货
                  	</#if>
                  </li>
                  <li><span>退款状态：</span><strong class="hong bd">${refundDO.refundStateDisplay}</strong></li>
                  <li><span>退款金额：</span>${refundDO.applySumByYuan}元</li>
                  <li><span>支付给卖家金额：</span><#if priceMap??>${priceMap.get('needPaySellerSum')!"0.00"} 元</#if></li>
                   <!--
                  <li><span>要求赔偿金额：</span></li>
                  <li><span>要求赔偿理由：</span></li>
                  -->
                  <li><span>退款说明：</span>${refundDO.applyMemo}</li>
                  <li><span>附件：</span>
                  	<#if refundDO.pic1?exists><img  src="${imageServer}${refundDO.pic1}"  width="80" height="80" class="showBigImage" title="点击图片可看大图"/></#if>
                  	<#if refundDO.pic2?exists><img  src="${imageServer}${refundDO.pic2}"  width="80" height="80" class="showBigImage" title="点击图片可看大图"/></#if>
                  	<#if refundDO.pic3?exists><img  src="${imageServer}${refundDO.pic3}"  width="80" height="80" class="showBigImage" title="点击图片可看大图"/></#if>
                  </li>
              </ul>
      	</div>
      
      </div>
</div>
</div>
