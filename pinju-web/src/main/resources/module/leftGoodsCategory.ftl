<#setting number_format="#">
				<!--========================================================●商品分类开始-->
				<#if !_PREVIEW?exists || _PREVIEW!="true">
				<div class="notic">
					<h2><a href="/shop/shopCategory.htm">商品分类</a></h2>
				</div>
				</#if>
				<!--===================================-->
				<div class="box">
					<h3 class="title">商品分类</h3>
					<ul id="ShopCategory" class="ShopCategory cf">
					    <li class="viewall"><a href="http://www.pinju.com/detail/shopItemList.htm?shopId=${SHOP_ID}&categoryId=">查看所有商品&gt;&gt;&gt;</a></li>
						<#if shopCategoryList?exists>
						<#list  shopCategoryList as shopCategory>
						<li class="list1"><strong><a href="<#if (shopCategory.subCategoryList?size > 0)>#<#else>http://www.pinju.com/detail/shopItemList.htm?shopId=${SHOP_ID}&categoryId=${shopCategory.id}</#if>"><img src="http://static.pinju.com/images/shop/sc-sub-11.gif">${shopCategory.categoryName!?html}</a></strong></li>
						<#if shopCategory.subCategoryList?exists>
						<#list  shopCategory.subCategoryList  as subCategory>
						<li><a href="http://www.pinju.com/detail/shopItemList.htm?shopId=${SHOP_ID}&categoryId=${subCategory[0]}">${subCategory[1]!?html}</a></li>
						</#list>
						</#if>
						</#list>
						</#if>		
					</ul>
				</div>
				<!--========================================================●商品分类结束-->