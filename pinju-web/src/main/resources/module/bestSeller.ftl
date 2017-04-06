<#setting number_format="#">
				<#if !_PREVIEW?exists || _PREVIEW!="true">
				<div class="notic"><!--=模块提示-->
					<h2><a href="javascript:if(dialog){dialog.open('getEditModuleAction.htm?userPageId=${_USERPAGEID?if_exists?string("0")}&moduleId=9',530,260);}">商品排行</a></h2>
				</div>
				</#if>
				<!--===================================-->
				<div class="box">
					<#if DISPLAYTITLE?? && DISPLAYTITLE == "Y" && TITLE??>
					<h3 class="title"><#if TITLE?exists>${TITLE?html}</#if></h3>
					</#if> 
					<ul class="topproducts_tab">
						<li<#if !(DEFAULTDISPLAY ??) || DEFAULTDISPLAY == "0"> class="count"</#if>><a href="javascript:void(0);">本月热销排行</a></li>
						<!--<li><a href="#">热门收藏排行</a></li>-->
					</ul>
					<div class="topproducts_list">
						<#if SELLER_DATA??>
						<#list SELLER_DATA as entity>
						<ul class="cf">
							<li class="img"><a href="http://www.pinju.com/detail/${entity.itemId}.htm"><img src="${picServer}${entity.itemPicUrl?if_exists+'_40x40.jpg'}"></a></li>
							<li class="text">
								<a href="http://www.pinju.com/detail/${entity.itemId}.htm">${entity.itemName?html}</a>
								<span class="gray">￥<b>${entity.priceByYuan}</b></span>
								<#if DISPLAYCOUNT?? && DISPLAYCOUNT == "Y">
								<span><img src="http://static.pinju.com/images/shop/sc-car-11.gif">已售出 ${entity.itemSaleCount} 笔</span>
								</#if>
							</li>
						</ul>
						</#list>
						</#if>
						<form name="_moreItemForm" action="http://www.pinju.com/detail/shopItemList.htm" method="get" target="_blank">
							<input type="hidden" name="shopId" value="${_SHOPID?string('0')}" />
							<input type="hidden" name="categoryId" value="" />
						</form>
						<ol><input name="" type="button" value="查看更多商品" onclick="javascript:goMoreItem();"></ol>
					</div>
				</div>
				<#if COLLECT_DATA?? && COLLECT_DATA.size > 0>
				<div class="box">
					<h3 class="title">商品排行</h3> 
					<ul class="topproducts_tab">
						<li><a href="#">本月热销排行</a></li>
						<li<#if DEFAULTDISPLAY?? && DEFAULTDISPLAY == "1"> class="count"</#if>><a href="#">热门收藏排行</a></li>
					</ul>
					<div class="topproducts_list">
						<#list COLLECT_DATA as entity>
						<ul>
							<li><a href="http://www.pinju.com/detail/${entity.itemId}.htm"><img src="${picServer}${entity.itemPicUrl+'_40x40.jpg'}"></a></li>
							<li>
								<a href="http://www.pinju.com/detail/${entity.itemId}.htm">${entity.itemName?html}</a>
								<span class="gray">￥<b>${entity.priceByYuan}</b></span>
								<#if DISPLAYCOUNT?? && DISPLAYCOUNT == "Y">
								<span><img src="http://static.pinju.com/images/shop/sc-car-11.gif">收藏人气 ${entity.itemSaleCount}</span>
								</#if>
							</li>
						</ul>
						</#list>
						<ol><input name="" type="button" value="收藏本店"></ol>
					</div>
				</div>
				</#if>
				<script language="javascript" type="text/javascript">
			    <!--
			  		function goMoreItem() {
			  			document._moreItemForm.submit();
			  			return true;
			  		}
			    //-->
			    </script>