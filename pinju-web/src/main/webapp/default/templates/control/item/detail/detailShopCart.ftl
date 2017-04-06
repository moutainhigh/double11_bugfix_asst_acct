<#if !query.activityDiscount??&&!(query.isLimitBuyItem??&&query.isLimitBuyItem==true)>
		<div class="shop_cart_pop" id="J_CartInfo">
				<div class="tips-right">
						<span class="colse">关闭</span>
						<p class="p1">商品已成功添加到购物车</p>
						<p>购物车共有<strong id="cart_num">0</strong>种商品，合计：<strong id="cart_price">1</strong>元</p>
						<a title="去购物车结算" href="${base}/cart/shoppingCartDetail.htm">去购物车结算</a>
						<a href="javascript:;" class="otherclose">再逛逛</a>
				</div>
				<div class="tips-error"  >
						<span class="colse">关闭</span>
						<p class="p1">您的购物车内超过20种商品，无法再加入商品</p>
						<a title="去购物车结算" href="${base}/cart/shoppingCartDetail.htm">去购物车</a>
				</div>
		</div>
			
		<a href="javascript:;" class="btn_addshopcart btn" ></a>
<#else>
		<a href="javascript:;" class="btn_grayadd btn" ></a>
</#if>
    	