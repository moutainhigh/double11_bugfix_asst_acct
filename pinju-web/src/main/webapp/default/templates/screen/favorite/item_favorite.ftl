<div class="favorites">
			<span class="favorites-fav">收藏此商品</span>
			<div class="pop favorites-goodsOk">
				<!-- 商品收藏成功-弹出框 -->
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>商品收藏成功！</h3>
						<p>自动分类到 "<span id="favorite_itemType"></span>"</p>
						<p>您已收藏<em class="red"><span id="favoriteItem_count"></span></em>件商品<a class="look" href="/favorite/queryFavoriteItemListAction.htm">查看收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<div class="other">
					<a class="favorites-shop" href="#">收藏该店铺</a>
				</div>
				<span class="close"></span>
			</div>
			<!-- 店铺收藏成功-弹出框 -->
			<div class="pop favorites-shopOk">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>店铺收藏成功！</h3>
						<p>自动分类到"<span id="favorite_shopType2"></span>"</p>
						<p>您已收藏<em class="red"><span id="favoriteShop_count2"></span></em>个店铺<a class="look" href="/favorite/queryFavoriteShopListAction.htm">查看收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
			<!-- 您已收藏过该商品-弹出框 -->
			<div class="pop favorites-goodsOld">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>您已收藏过该商品。</h3>
						<p><a class="look" href="/favorite/queryFavoriteItemListAction.htm">查看收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
			<!-- 您已收藏过该店铺-弹出框 -->
			<div class="pop favorites-shopOld">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>您已收藏过该店铺。</h3>
						<p><a class="look" href="/favorite/queryFavoriteShopListAction.htm">查看收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
			<!-- 商品收藏失败-弹出框 -->
			<div class="pop favorites-goodsError">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>商品收藏失败！</h3>
					</div>
				</div>
				<span class="close"></span>
			</div>
			<!-- 店铺收藏失败-弹出框 -->
			<div class="pop favorites-shopError">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>店铺收藏失败！</h3>
					</div>
				</div>
				<span class="close"></span>
			</div>
				<!-- 商品当天已收藏超过100-弹出框 -->
			<div class="pop favorites-goods100">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>当天收藏商品数已超过上限！</h3>
						<p><a class="look" href="/favorite/queryFavoriteItemListAction.htm">管理收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
			<!-- 店铺当天已收藏超过100-弹出框 -->
			<div class="pop favorites-shop100">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>当天收藏店铺数已超过上限！</h3>
						<p><a class="look" href="/favorite/queryFavoriteShopListAction.htm">管理收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
			<!-- 商品总收藏超过1000-弹出框 -->
			<div class="pop favorites-goods1000">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>商品总收藏数超过上限！</h3>
						<p><a class="look" href="/favorite/queryFavoriteItemListAction.htm">管理收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
			<!-- 店铺总收藏超过1000-弹出框 -->
			<div class="pop favorites-shop1000">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>店铺总收藏数超过上限！</h3>
						<p><a class="look" href="/favorite/queryFavoriteShopListAction.htm">管理收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
		</div>

<script>
 	$(function() {
	    //favorites pop 收藏  
		$('.favorites-fav').click(function() {
		     $.ajax({
					// 后台处理程序
					url : "${base}/favorite/saveFavoriteItemAction.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "json",
					// 要传递的数据
					data : {
						itemId:${itemDO.id!},
						shopId:${sellerQuality.shopId},
						shopName:'${sellerQuality.shopName?js_string}',
						categoryId:#{itemDO.catetoryId}
					},
					// 回传函数
					success : function callBack(result) {
						var json = eval("(" + result + ")");
						if(json.isInsert==1){
							$("#favorite_itemType").text(json.categoryName);
				    		$("#favoriteItem_count").text(json.favoriteItemCount);
							$('.favorites .favorites-goodsOk').show();
						}else if(json.isInsert==-1){
							$('.favorites .favorites-goodsError').show();
						}else if(json.isInsert==-2){
							$('.favorites .favorites-goods100').show();
						}else if(json.isInsert==-3){
							$('.favorites .favorites-goods1000').show();
						}else if(json.isInsert==-100){
						    var current = encodeURIComponent(location.href);
						    location.href = 'http://www.pinju.com/member/login.htm?returnUrl=' + current;
						}else{
						    $('.favorites .favorites-goodsOld').show();
						}
					}
				});
			
		});
		$('.favorites-shop').click(function() {
		 $.ajax({
					// 后台处理程序
					url : "http://www.pinju.com/favorite/saveFavoriteShopAction.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "jsonp",
					jsonp : "callback",
					// 要传递的数据
					data : {
						shopId:${sellerQuality.shopId},
						shopName:'${sellerQuality.shopName?js_string}',
						categoryId:${sellerQuality.categoryId}
					},
					// 回传函数
					success : function callBack(json) {
						if(json.isInsert==1){
						    $("#favorite_shopType2").text(json.favoriteCategoryName);
				    		$("#favoriteShop_count2").text(json.favoriteShopCount);
							$('.favorites .favorites-goodsOk').hide();
							$('.favorites .favorites-shopOk').show();
						}else if(json.isInsert==-1){
						    $('.favorites .favorites-goodsOk').hide();
							$('.favorites .favorites-shopError').show();
						}else if(json.isInsert==-2){
						    $('.favorites .favorites-goodsOk').hide();
							$('.favorites .favorites-shop100').show();
						}else if(json.isInsert==-3){
						    $('.favorites .favorites-goodsOk').hide();
							$('.favorites .favorites-shop1000').show();
						}else if(json.isInsert==-100){
						    var current = encodeURIComponent(location.href);
						    location.href = 'http://www.pinju.com/member/login.htm?returnUrl=' + current;
						}else{
						   $('.favorites .favorites-goodsOk').hide();
						   $('.favorites .favorites-shopOld').show();
						}
					}
				});
		});
		// 已收藏店铺代码 $('.favorites .favorites-shopOld').show();
		// 已收藏商品代码 $('.favorites .favorites-goodsOld').show();
		$('.favorites .pop .close').click(function() {
			$(this).closest('.pop').hide();
		});
 	});
 </script>