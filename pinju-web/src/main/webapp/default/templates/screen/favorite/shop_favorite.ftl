    	<div class="favoritesSide">
			<span class="businessInfoFavShop">收藏本店铺</span>
			<!-- 店铺收藏成功-弹出框 -->
			<div class="pop favorites-shopOk">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>店铺收藏成功！</h3>
						<p>自动分类到"<span id="favorite_shopType"></span>"</p>
						<p>您已收藏<em class="red"><span id="favoriteShop_count"></span></em>个店铺<a class="look" href="http://www.pinju.com/favorite/queryFavoriteShopListAction.htm">查看收藏夹&gt;&gt;</a></p>
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
						<p><a class="look" href="http://www.pinju.com/favorite/queryFavoriteShopListAction.htm">查看收藏夹&gt;&gt;</a></p>
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
			<!-- 店铺当天已收藏超过100-弹出框 -->
			<div class="pop favorites-shop100">
				<div class="info cf">
					<div class="icon"></div>
					<div class="text">
						<h3>当天收藏店铺数已超过上限!</h3>
						<p><a class="look" href="http://www.pinju.com/favorite/queryFavoriteShopListAction.htm">管理收藏夹&gt;&gt;</a></p>
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
						<p><a class="look" href="http://www.pinju.com/favorite/queryFavoriteShopListAction.htm">管理收藏夹&gt;&gt;</a></p>
					</div>
				</div>
				<span class="close"></span>
			</div>
		</div>
<script>
 	$(function() {
		$('.businessInfoFavShop').click(function() {
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
						    $("#favorite_shopType").text(json.favoriteCategoryName);
				    		$("#favoriteShop_count").text(json.favoriteShopCount);
							$('.favoritesSide .favorites-shopOk').show();
						}else if(json.isInsert==-1){
							$('.favoritesSide .favorites-shopError').show();
						}else if(json.isInsert==-2){
							$('.favoritesSide .favorites-shop100').show();
						}else if(json.isInsert==-3){
							$('.favoritesSide .favorites-shop1000').show();
						}else if(json.isInsert==-100){
						    var current = encodeURIComponent(location.href);
						    location.href = 'http://www.pinju.com/member/login.htm?returnUrl=' + current;
						}else{
						   $('.favoritesSide .favorites-shopOld').show();
						}
					}
				});
		});
		// 已收藏店铺代码 $('.favoritesSide .favorites-shopOld').show();
		// 已收藏商品代码 $('.favoritesSide .favorites-goodsOld').show();
		$('.favoritesSide .pop .close').click(function() {
			$(this).closest('.pop').hide();
		});
 	});
 </script>