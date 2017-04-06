<title>店铺收藏</title>
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/tdomain/favorites.css" rel="stylesheet" type="text/css" media="screen" />
<input type="hidden" value="red_sellfavorite" id="my-page" />
<form name="searchForm" action="/favorite/deleteFavoriteShopAction.htm" method="post" id="pageForm">
<input type="hidden" value="${categoryId!}" name="categoryId" />
			<div class="domain-map">
				<span>您的位置：</span><a href="/orderBuyer/myBuyer.htm">我是买家</a> > <span class="current">我的收藏</span>
			</div>
			<ul class="favorites-tabs cf">
				<li class="goodsFav"><a href="/favorite/queryFavoriteItemListAction.htm">商品收藏</a></li>
				<li class="shopFav current"><a href="/favorite/queryFavoriteShopListAction.htm">店铺收藏</a></li>
			</ul>
			<div class="favoritesShop">
				<div class="favorites-category">
					<h3 class="title">收藏分类</h3>
					<div class="list-box cf">
					 <#if categoryId?exists>
						  <span class="nameSpan">所有店铺</span>
						   <a class="nameLink show" href="/favorite/queryFavoryShopByCategoyAction.htm">所有店铺</a>
					<#else>
					    <span class="nameSpan show">所有店铺</span>
						<a class="nameLink" href="#">所有店铺</a> 
					</#if>
						<ul class="list">
							<#if categoryList?exists && categoryList.size() &gt; 0>
							  <#list categoryList as category>
									<li><a href="/favorite/queryFavoryShopByCategoyAction.htm?categoryId=${category.categoryId!}">${category.categoryName!}</a></li>
								</#list>
							</#if>
						</ul>
					</div>
				</div>
				<div class="favoritesShop-info cf">
					<div class="favorites-checkall cf">
						<div class="left">
							<label for="favoritesShopCheckallTop">
								<input id="favoritesShopCheckallTop" class="favoritesShopCheckall" type="checkbox" />全选
							</label>
							<a class="favoritesShopDel" href="javascript:delect()">删除</a>
					   </div>
					<ul class="favorites-titles">
						<li class="long">店铺信息</li>
						<li>商品数量</li>
						<li>收藏时间</li>
						<li>操作</li>
					</ul>
					<ul class="favorites-list" id="favoritesShopList">
					 <#if favoriteShopList?exists>
					   <#list favoriteShopList as favoriteShop>
							<li>
								<input class="chk" type="checkbox" name="idCheck" value="${favoriteShop.id}"/>
								<dl class="list">
									<dt>
										<div class="img"><a href="http://shop${favoriteShop.shopId}.pinju.com"">
										<#if favoriteShop.shopInfoDO.shopLogo??>
	            		 				 	<img id="shopLogoImg" name="shopLogoImg" src="${imageServer}${favoriteShop.shopInfoDO.shopLogo}_80x80.jpg"/>
	            						<#else>
	            						  <img id="shopLogoImg" name="shopLogoImg" src="http://static.pinju.com/img/shop_default_logo.png"/>
	            						</#if></a></div>
										<div class="text">
											<span class="shopName">店铺：<a href="http://shop${favoriteShop.shopId}.pinju.com">${favoriteShop.shopName}</a></span>
											<span class="shopType">店铺类型：
											<#if favoriteShop.shopInfoDO?exists && favoriteShop.shopInfoDO.sellerType=="0">
												<img src="http://static.pinju.com/img/goods/icon-general.png" />
											<#elseif favoriteShop.shopInfoDO.sellerType=="1">
											   <img src="http://static.pinju.com/img/goods/icon-brand.png" />
											<#elseif favoriteShop.shopInfoDO.sellerType=="2">
											   <img src="http://static.pinju.com/img/goods/icon-flagship.png" />
											<#else>
											   <img src="http://static.pinju.com/img/goods/icon-ishop.png" />
											</#if>
											</span>
										</div>
									</dt>
									<dd>
										${favoriteShop.goodsCount}
									</dd>
									<dd>
										<span class="time">${favoriteShop.favoriteDate?string("yyyy-MM-dd")}</span>
									</dd>
									<dd>
										<a class="shopDel" href="javascript:;" data-shop-id="${favoriteShop.id!}">删除</a>
									</dd>
								</dl>
							</li>
					 </#list>
				 </ul>
					<div class="favorites-checkall cf">
						<div class="left">
							<label for="favoritesShopCheckallBottom">
								<input id="favoritesShopCheckallBottom" class="favoritesShopCheckall" type="checkbox" />全选
							</label>
							<a class="favoritesGoodsDel" href="javascript:delect()">删除</a>
						</div>
						<div class="right">
							<div class="skipto">
							 <#if favoriteShopList?exists && favoriteShopList.size() &gt; 0>
							       <div id="Pagination" class="pagination"></div>
							 		<#include "/default/templates/control/bottomPage.ftl">
							 </#if>
							</div>
						   </div>
						</div>
			    <#else>
			      		目前分类下没有收藏
			   </#if>
			</div>
		</div>
</form>
<script>
$(function() {
	/* tab切换--商品售出 店铺收藏 
	$('.favorites-tabs .goodsFav').click(function() {
		$('.favoritesShop').hide();
		$('.favoritesGoods').show();
		$('.favorites-tabs li').removeClass('current');
		$(this).addClass('current');
	});
	$('.favorites-tabs .shopFav').click(function() {
		$('.favoritesGoods').hide();
		$('.favoritesShop').show();
		$('.favorites-tabs li').removeClass('current');
		$(this).addClass('current');
	});*/
	
	/* 全选效果 */	
	$('.favoritesShopCheckall').change(function() {
		if($(this).attr('checked') == 'checked') {
			$('#favoritesShopList').find('.chk').attr('checked', 'checked');
			$('.favoritesShopCheckall').attr('checked', 'checked');
		} else {
			$('#favoritesShopList').find('.chk').removeAttr('checked');
			$('.favoritesShopCheckall').removeAttr('checked');
		}
	});
	$('#favoritesShopList .chk').change(function() {
		var length = $('#favoritesShopList .chk').length;
		var lengthchk = $('#favoritesShopList .chk:checked').length;
		if(length == lengthchk) {
			$('.favoritesShopCheckall').attr('checked', 'checked');
		} else {
			$('.favoritesShopCheckall').removeAttr('checked');
		}
	});
});

$(".shopDel").click(function(e) {
	var favoriteshopId = $(this).attr('data-shop-id');
   	var r=confirm("确定要删除收藏的店铺吗?");
    if (r==true){
        $("#pageForm").attr({ action:"/favorite/deleteFavoriteShopAction.htm?id="+favoriteshopId,method:"post" });
        $("#pageForm").submit();
    }
    return false;
});

function delect(){
       var count=0;
         var oj = $(".chk");
         for (var i=0;i<oj.length;i++ ){
            if (oj[i].checked){
              count++;
           }
          }
         if(count==1){
            var r=confirm("确定要删除收藏的店铺吗?");
            if (r==true){
               $("#pageForm").attr({ action:"/favorite/deleteFavoriteShopAction.htm",method:"post" });
               $("#pageForm").submit();
            }
         }else if(count>1){
            var r=confirm("确定要批量删除收藏的店铺吗?");
            if (r==true){
                $("#pageForm").attr({ action:"/favorite/deleteFavoriteShopAction.htm",method:"post" }); 
                 $("#pageForm").submit();
            }
         }else if(count==0){
           alert("请选择要删除的收藏店铺！");
         }
       }
</script>