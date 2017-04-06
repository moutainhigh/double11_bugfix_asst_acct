<#setting classic_compatible=true>
<div class="cart_content">
<LINK href="http://static.pinju.com/css/new/cart.css" rel="stylesheet" />
<script src="http://static.pinju.com/js/cart/cart.js"></script>

<title>购物车</title>

<#if needtip>
<#if itemList?exists>

<div class="item_status" id="cart-tips">
	<p class="item_status_t">以下商品放入购物车后已发生变化</p>
	
    <ul class="item_status_list cf">
    	<#list itemList as item>
    	<#assign key='key'+(item.id?c)>
	   	<#assign propkey = key + (item.skuid)>
    	
    	<#if (item.status !=0 && item.status !=1) || (item.favCountInt > item.curStockInt) >
        <li id="catuion_${item.id?c}_${item.skuid}"><h3 class="item_name">${item.title} </h3><span class="status">不能购买。存货不足或者已下架或被移除，请从购物车删除</span> 
        <a  class="del_but" onclick="javascript:delItem(${item.id?c}, '${item.skuid}');" title="删除">删除</a>
        <input type="hidden"  value="${item.id?c} ${item.skuid}" id="status_not_${item.id?c}_${item.skuid}">
              <#if itemPropertyMap[propkey]?exists>
                    <div class="item_status_sku" >
        		<#list itemPropertyMap[propkey] as itemProperty>
        		<span>${(itemProperty.name)?html}：${(itemProperty.value)?html}</span>
        		</#list>
        		</div>
        	</#if>
        </li>
        
        <#elseif item.favPrice?exists>
        
         	<#if (item.lowerPrices ==2) >	 
	  			<li id="catuion_${item.id?c}_${item.skuid}"><h3 class="item_name">${item.title}() </h3><span class="status">价格从${item.favPrice} 升到了${item.priceByYuan}</span>
	  			 
	  			  <#if itemPropertyMap[propkey]?exists>
                   <div class="item_status_sku" >
        		<#list itemPropertyMap[propkey] as itemProperty>
        		<span>${(itemProperty.name)?html}：${(itemProperty.value)?html}</span>
        		</#list>
        		</div>
        	</#if>
	  			</li>
	  		<#elseif (item.lowerPrices ==1) >
	  			<li id="catuion_${item.id?c}_${item.skuid}"><h3 class="item_name">${item.title} </h3><span class="status">价格从${item.favPrice} 降到了${item.priceByYuan}</span> 
	  			
	  			 <#if itemPropertyMap[propkey]?exists>
                    <div class="item_status_sku" >
        		<#list itemPropertyMap[propkey] as itemProperty>
        		<span>${(itemProperty.name)?html}：${(itemProperty.value)?html}</span>
        		</#list>
        		</div>
        	</#if>
	  			</li>
	  	   </#if>
	  	   
        </#if> 
        
        
        </#list>
    </ul>
</div>


</#if> 
</#if> 



<form name="form" id="form" action="${base}/orderBuyer/settlement.htm" method="post">
<input type="hidden" name="method" value="settlement">

<div class="checkout cf">
    <div class="cart_checkbox">
        <b>购物车</b>
        <span><input name="" id="selectall" type="checkbox" value="" checked=true /> <label for="selectall">全选</label></span>
    </div>
    <a class="checkout_but go_settlement" style="cursor:pointer;" title="点击此处进行结算">结算</a>
    <p class="value">商品总价（不含运费）:<span class="jies_jg">${totalPrice}</span>元</p>

</div>


<#if sellerList?exists>
   <#list sellerList as seller>
   <#assign shopId='key'+seller.sellerId>
   <#assign shop=shopMap[shopId]>
<div class="cart_box">
    <table class="cart_table">
		<tr id="${seller.sellerId?c}" class="hd">
            <th class="cart_table_row1">
            		店铺：<#if shop != null><a href="http://shop${shop.shopId}.pinju.com">${(shop.name)?html}</a></#if>
            </th>
            <th class="cart_table_row3">单价</th>
            <th class="cart_table_row4"></th>
            <th class="cart_table_row5">数量</th>
            <th class="cart_table_row6">小计</th>
            <th class="cart_table_row7"></th>
        </tr>
        
        <#if itemList?exists>
	        <#list itemList as item>
	   		<#assign key='key'+(item.id?c)>
	   		<#assign propkey = key + (item.skuid)>
	   		<#if seller.sellerId == item.sellerId>
   		
    	<tr id="${item.sellerId?c}_${item.id?c}_${item.skuid}" >
    	
    	<input type="hidden" name="orderCreation.itemId" value="${item.id?c}">
        <input type="hidden" name="orderCreation.bussinessType" value="200">      
        <input type="hidden" name="orderCreation.price" value="${item.price?c}">
        <input type="hidden" name="orderCreation.itemSkuId" value="${item.skuid}">
        <input type="hidden" name="orderCreation.itemSkuAttributes" value="${item.skuDesc}">
        <input type="hidden" name="orderCreation.channelId" value="${item.channelId}">
        <input type="hidden" name="orderCreation.ad" value="${item.ad}">
      
            <td>
            	<div class="sp_box cf">
                    <span class="prompt_box"><input class="itemList" type="checkbox" value="${item.id?c} ${item.skuid}"  onClick="javascript:checkSelectall(this)" checked=true /></span>
                    <div class="pic_box"><a href="${base}/detail/${item.id?c}.htm" title="点击此处可以查看商品详情"><img width="80px" height="80px" src="${imageServer}${item.picUrl}"/></a></div>
                    <a class="sp_name" href="${base}/detail/${item.id?c}.htm" title="点击此处可以查看商品详情">${(item.title)?html}</a>
					<#if itemPropertyMap[propkey]?exists>
                    <div class="item_sku" >
        			<#list itemPropertyMap[propkey] as itemProperty>
        			<span>${(itemProperty.name)?html}：${(itemProperty.value)?html}</span>
        		</#list>
        		</div>
        	</#if>
        	
        	
                </div>
                
            </td>
            
            <td>
            	<#if item.favPrice != item.priceByYuan>
            	<del>￥${item.favPrice}</del>
            	</#if>
            	<span>￥${item.priceByYuan}</span>
            	<input type="hidden" id="currentPrice_${item.id?c}_${item.skuid}" value="${item.priceByYuan}">
            	<#if item.favPrice != item.priceByYuan>
            	<#if (item.lowerPrices ==2) >
            	<span class="ts_sty">升 ${item.leftPrices}</span>
            	<#elseif (item.lowerPrices ==1) >
            	<span class="ts_sty">降 ${item.leftPrices}</span>
            	</#if>
            	</#if>
            </td>
            <td></td>
            <td>
            	<div>
            	<input type="hidden" name="_curStock" value="${item.curStock?c}">
            	<input type="hidden" name="_curItemId" value="${item.id?c}">
            	<input type="hidden" name="_skuid" value="${item.skuid}">
                <a onclick="addCount(this, false, ${item.id?c}, '${item.skuid}');" class="cut" style="cursor:pointer;" title="点击此处可以减少购买数量"><img width="9" height="9" src="http://static.pinju.com/images/cut_99.gif" /></a>
                <input  class="quantity" type="text" id="num" name="orderCreation.num" value="${item.itemCount}"/>
                <a onclick="addCount(this, true, ${item.id?c}, '${item.skuid}');" class="add" style="cursor:pointer;"  title="点击此处可以增加购买数量"><img width="9" height="9" src="http://static.pinju.com/images/add_102.gif" /></a>
                </div>
               
            	<#if (item.minStocks == 0 ) ><span class="ts_sty">库存紧张</span>
            	<#else>
            	 <span></span>
            	</#if>
         
            </td>
            <td>
            	<span id="xiaoji_${item.id?c}_${item.skuid}" class="cart_tab_jg">￥${item.singleTotal}</span>
            	<input type="hidden" id="_danjia_${item.id?c}_${item.skuid}" value="${item.priceByYuan}">
            </td>
            <td>
            	<a href="javascript:delItem(${item.id?c}, '${item.skuid}')" class="del_but" title="点击此处可以删除购买的商品">删除</a>
            </td>
        </tr>
        
          		</#if>
           </#list>
	</#if> 
    
    </table>
    
</div>
   </#list>
</#if> 



<div class="checkout2 cf">
    <input id="btnBatchDel" class="btn-gray" type="button" value="批量删除" title="点击此处可以批量删除购买的商品" style="cursor:pointer;"/>
    
    <div class="jies_box">
        <p>商品总价（不含运费）:<span class="jies_jg">${totalPrice}</span>元</p>
        <p>总计<span class="jies_jg">${totalPrice}</span>元</p>
        <a class="checkout_but go_settlement" title="点击此处进行结算">结算</a>
    </div>
</div>



</div>
</div>

</form>
<input type="hidden" id="now-item-total" value="${itemList?size}">

<form name="delForm" id="delForm" action="${base}/cart/deleteItem.htm">
<input type="hidden" name="delItemId" id="delItemId">
</form>

<input type="hidden" name="shoppingCartDetail" id="shoppingCartDetail" value="1">
</div>