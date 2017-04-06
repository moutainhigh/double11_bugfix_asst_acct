<script src="http://static.pinju.com/js/domain.js"></script>
<script src="http://static.pinju.com/js/pinju-global.js"></script>
<script src="http://static.pinju.com/js/cookie.js"></script>
<script src="http://static.pinju.com/js/cart/loadcart.js"></script>

<script type=”text/javascript” src=”http://ipic.staticsdo.com/external/install_beacon.js”></script>
<div id="site-nav">
  <div id="site-nav-bd">

    <script type="text/javascript">
      var pc = new PinjuCookie('${base}');
      pc.showLoginNav();
    </script>

    <UL class="quick-menu">
      <LI class="home"><A href="${base}/itemReleased/categoryList.htm">发布商品</A> </LI>
      <LI class="mytaobao menu-item">
        <div class="menu">
          <A class="menu-hd" href="${base}/management/buy.htm" target=_top rel=nofollow>我的御网<B></B></A> 
          <div class="menu-bd">
            <div class="menu-bd-panel">
              <div>
                <A href="${base}/orderBuyer/orderManager.htm" rel=nofollow>已买到的商品</A>
                <A href="${base}/orderSeller/ordermanagesell.htm" rel=nofollow>已卖出的商品</A> 
              </div>
            </div>
            <S class="r"></S><S class="rt"></S><S class=lt></S><S class=b></S><S class="b b2"></S><S class=rb></S><S class=lb></S>
          </div>
        </div>
      </LI>
      <LI class="cart" id="cart">
        <div class="menu">
          <A href="${base}/cart/shoppingCartDetail.htm" rel=nofollow id="mycart"><b class="mini-cart-line"></b><s></s>购物车<b class="mc-pt3" id="carts">0</b>件<!--<i></i>--> </A>
          
		  <div class="menu-bd">
            <div class="menu-bd-panel" id="menu-bd-panel">
            <input type="hidden" id="cart_url_base" value="${base}/">
            <!-- ---------------------------------------------------------->
             <div class="minicart" id="minicart"></div>
             
             
             <!-- ---------------------------------------------------------->
             
            <S class="r"></S><S class="rt"></S><S class=lt></S><S class=b></S><S class="b b2"></S><S class=rb></S><S class=lb></S>
          </div>


        </div>
        
      </LI>
      <LI class="mytaobao menu-item">
        <div class=menu>
          <A class=menu-hd href="#" target=_top rel=nofollow>收藏夹<B></B></A> 
          <div class="menu-bd">
            <div class=menu-bd-panel>
              <div>
                <A href="#" rel=nofollow>收藏的商品</A>
                <A href="#" rel=nofollow>收藏的店铺</A> 
              </div>
            </div>
            <S class=r></S><S class=rt></S><S class=lt></S><S class=b></S><S class="b b2"></S><S class=rb></S><S class=lb></S>
          </div>
        </div>
      </LI>
      <LI class="search menu-item">
        <div class=menu><SPAN class=menu-hd><S></S>搜索<B></B></SPAN> 
          <div class=menu-bd>
            <div class=menu-bd-panel>
              <FORM name=topSearch action=http://s.taobao.com/search?ssid=s0><INPUT 
              maxLength=60 name=q><BUTTON type=submit>搜 索</BUTTON> <INPUT type=hidden 
              value=newsearch name=shopf> </FORM>
            </div>
          <S class=r></S><S class=rt></S><S class=lt></S><S class=b></S><S class="b b2"></S><S class=rb></S><S class=lb></S>
          </div>
        </div>
      </LI>
    </UL>
  </div>
<script type="text/javascript">
// if(navigator.cookieEnabled && !pc.isLogin() && pc.isSdoTimeout()) {
//   pc.writeSdoChecker();
// }
// var search = location.search;
// if(search && (search.indexOf('?r=0') > -1 || search.indexOf('&r=0') > -1)) {
//   alert('登录暂时不可用，请过几分钟之后再重试');
// }
</script>

<script type="text/javascript">
(function(){$('#carts').text(pc.getShoppingCartCount());})()
</script>
</div>