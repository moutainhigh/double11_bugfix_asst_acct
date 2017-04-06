<title>商品收藏</title>
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/tdomain/favorites.css" rel="stylesheet" type="text/css" media="screen" />
<input type="hidden" value="red_sellfavorite" id="my-page" />
<form name="searchForm" action="/favorite/deleteFavoriteItemAction.htm" method="post" id="pageForm">
<input type="hidden" value="${categoryId!}" name="categoryId" />
			<div class="domain-map">
				<span>您的位置：</span><a href="/orderBuyer/myBuyer.htm">我是买家</a> > <span class="current">我的收藏</span>
			</div>
			<ul class="favorites-tabs cf">
				<li class="goodsFav current"><a href="/favorite/queryFavoriteItemListAction.htm">商品收藏</a></li>
				<li class="shopFav"><a href="/favorite/queryFavoriteShopListAction.htm">店铺收藏</a></li>
			</ul>
			<div class="favoritesGoods">
				<div class="favorites-category">
					<h3 class="title">收藏分类</h3>
					<div class="list-box cf">
						<#if categoryId?exists>
							  <span class="nameSpan">所有商品</span>
							   <a class="nameLink show" href="/favorite/queryFavoryItemByCategoyAction.htm">所有商品</a>
						<#else>
						    <span class="nameSpan show">所有商品</span>
							<a class="nameLink" href="#">所有商品</a> 
						</#if>
						<ul class="list">
							<#if categoryList?exists>
							  <#list categoryList as category>
									<li><a href="/favorite/queryFavoryItemByCategoyAction.htm?categoryId=${category.categoryId!}">${category.categoryName!}</a></li>
								</#list>
							</#if>
						</ul>
					</div>
				</div>
				<div class="favoritesGoods-info cf">
					<div class="favorites-checkall cf">
						<div class="left">
							<label for="favoritesGoodsCheckallTop">
								<input id="favoritesGoodsCheckallTop" class="favoritesGoodsCheckall" type="checkbox" />全选
							</label>
							<a class="favoritesGoodsDel" href="javascript:delect()">删除</a>
						</div>
				   	<ul class="favorites-titles">
						<li class="long">商品信息</li>
						<li>单价（元）</li>
						<li>收藏时间</li>
						<li>操作</li>
					</ul>
					<ul class="favorites-list" id="favoritesGoodsList">
					<#if favoriteItemList?exists>
					<#list favoriteItemList as favoriteItem>
							<li>
								<input class="chk" type="checkbox" name="idCheck" value="${favoriteItem.id}"/>
								<dl class="list">
									<dt>
										<div class="img"><a href="${base}/detail/${favoriteItem.itemDO.id}.htm"><img src="${imageServer}${favoriteItem.itemDO.picUrl!}_80x80.jpg"/></a></div>
										<div class="text">
											<a class="name" href="${base}/detail/${favoriteItem.itemDO.id}.htm">${favoriteItem.itemDO.title?html}</a>
											<span class="shop">店铺：<a href="http://shop${favoriteItem.shopId}.pinju.com">${favoriteItem.shopName}</a></span>
											<span class="num">已售出：<em>${favoriteItem.itemDO.sales!}</em>件</span>
										</div>
									</dt>
									<dd>
										<span class="price">￥<strong>${favoriteItem.itemDO.priceByYuan}</strong></span>
									</dd>
									<dd>
										<span class="time">${favoriteItem.favoriteDate?string("yyyy-MM-dd")}</span>
									</dd>
									<dd>
										<!-- <a class="add" href="${base}/detail/${favoriteItem.itemDO.id}.htm"></a> -->
										<a class="goodsDel" href="javascript:;" data-goods-id="${favoriteItem.id!}">删除</a>
										<a class="buy" href="${base}/detail/${favoriteItem.itemDO.id}.htm">立刻购买</a>
									</dd>
								</dl>
							</li>
						</#list>
						</ul>
							<div class="favorites-checkall cf">
								<div class="left">
									<label for="favoritesGoodsCheckallBottom">
										<input id="favoritesGoodsCheckallBottom" class="favoritesGoodsCheckall" type="checkbox" />全选
									</label>
									<a class="favoritesGoodsDel" href="javascript:delect()">删除</a>
								</div>
								<div class="right">
									<div class="skipto">
									    <#if favoriteItemList?exists && favoriteItemList.size() &gt; 0>
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
	
	/* 全选效果 */
	$('.favoritesGoodsCheckall').change(function() {
		if($(this).attr('checked') == 'checked') {
			$('#favoritesGoodsList').find('.chk').attr('checked', 'checked');
			$('.favoritesGoodsCheckall').attr('checked', 'checked');
		} else {
			$('#favoritesGoodsList').find('.chk').removeAttr('checked');
			$('.favoritesGoodsCheckall').removeAttr('checked');
		}
	});
	$('#favoritesGoodsList .chk').change(function() {
		var length = $('#favoritesGoodsList .chk').length;
		var lengthchk = $('#favoritesGoodsList .chk:checked').length;
		if(length == lengthchk) {
			$('.favoritesGoodsCheckall').attr('checked', 'checked');
		} else {
			$('.favoritesGoodsCheckall').removeAttr('checked');
		}
	});
});

$(".goodsDel").click(function(e) {
	var favoriteItemId = $(this).attr('data-goods-id');
   	var r=confirm("确定要删除收藏的商品吗?");
    if (r==true){
        $("#pageForm").attr({ action:"/favorite/deleteFavoriteItemAction.htm?id="+favoriteItemId,method:"post" });
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
            var r=confirm("确定要删除收藏的商品吗?");
            if (r==true){
               $("#pageForm").attr({ action:"/favorite/deleteFavoriteItemAction.htm",method:"post" });
               $("#pageForm").submit();
            }
         }else if(count>1){
            var r=confirm("确定要批量删除收藏的商品吗?");
            if (r==true){
                $("#pageForm").attr({ action:"/favorite/deleteFavoriteItemAction.htm",method:"post" }); 
                 $("#pageForm").submit();
            }
         }else if(count==0){
           alert("请选择要删除的收藏商品！");
         }
       }
</script>