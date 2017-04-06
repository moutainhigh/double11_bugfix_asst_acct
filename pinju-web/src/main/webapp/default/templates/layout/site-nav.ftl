<script src="http://static.pinju.com/js/pinju-global-min.js?t=20111220.js"></script>
<script src="http://static.pinju.com/js/cookie-min.js?t=20111111.js"></script>
<script src="http://static.pinju.com/js/cart/loadcart.js?t=20111214.js"></script>

<link rel="stylesheet" href="http://static.pinju.com/css/cart.css" type="text/css" media="screen" />

<div class="top_bar cf">
  <div class="top_bar_box cf">
    <div class="login_status">
      <script>
        var pc = new PinjuCookie('http://www.pinju.com');
        pc.showLoginNav();
        var image_server = '${image_server}';
      </script>
    </div>
    <div class="liaison" id="J_topNavi">
      <ul>
	      <li><a href="http://www.pinju.com/my/info.htm" title="我的品聚">我的品聚</a></li>
	      <li class="menu-item">
	      	<div class="menu">
	      		<a class="menu-hd" href="http://www.pinju.com/orderBuyer/myBuyer.htm" title="我是买家"><span>我是买家</span></a>
	      		<ul class="menu-bd">
					<li><a href="http://www.pinju.com/orderBuyer/orderManager.htm" title="已买到的商品">已买到的商品</a></li>
					<li><a href="http://www.pinju.com/favorite/queryFavoriteItemListAction.htm" title="收藏夹">收藏夹</a></li>
				</ul>
			</div>
	      </li>
	      <li><a class="cart" href="${base}/cart/shoppingCartDetail.htm" rel="nofollow" id="mycart" title="购物车"><span>购物车<strong id="carts">0</strong>件</span></a></li>
	      <li><a href="http://www.pinju.com/my/sell.htm" title="我是卖家">我是卖家</a></li>
	      <li><a href="http://job.pinju.com/" target="_blank" title="加入品聚">加入品聚</a></li>
	      <li><a href="http://service.pinju.com/" target="_blank" title="帮助中心">帮助中心</a></li>
      </ul>
      <input type="hidden" id="cart_url_base" value="${base}" />
    </div>
	<script>
		$(function() {
			$('#J_topNavi').find('.menu').hover(function() {
				$(this).addClass('hover');
			}, function() {
				$(this).removeClass('hover');
			});
		});
	</script>
    <div id="cart_pop" class="cart_pop"  style="background:#fff; display:none; position:static; border:1px solid #dadada;"></div>
    
  </div>
</div>

<script type="text/javascript">
(function(){$('#carts').text(pc.getShoppingCartCount());})()
</script>
