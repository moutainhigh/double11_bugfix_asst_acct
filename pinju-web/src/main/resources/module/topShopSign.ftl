<#setting number_format="#">		
			<!--========================================================●店铺招牌开始-->
			<#if !_PREVIEW?exists || _PREVIEW!="true">
			<div class="notic"><!--模块提示-->
				<h2><a href="javascript:if(dialog){dialog.open('getEditModuleAction.htm?userPageId=${_USERPAGEID?if_exists?string("0")}&moduleId=7',550,500);}">店铺招牌（950px -）</a></h2>			
			</div>
			</#if>
			<!--===================================-->
			<div id="topic">
				<ol style="<#if height?exists>height:${height?if_exists}px;</#if> <#if hasShopImage?exists && hasShopImage=="on">background: url(${imageUrl?if_exists});</#if>">
					<#if hasShopLogo?exists && hasShopLogo=="on"><li><a href="#"><img src="<#if shopInfoDO?exists>${fileServer?if_exists}${shopInfoDO.shopLogo?if_exists}</#if>" width="100px;" height="70px;"/></a></li></#if>
					<#if hasShopName?exists && hasShopName=="on"><li><a href="#"><#if shopInfoDO?exists>${shopInfoDO.name?if_exists}</#if></a></li></#if>
				</ol>
				<ul>
					<li<#if !(_USERPAGEID??) || _USERPAGEID == _FIRST_USERPAGE> class="count"</#if>><a href="<#if !(_USERPAGEID??) || _USERPAGEID == _FIRST_USERPAGE><#if _PREVIEW?? && _PREVIEW == "true">http://www.pinju.com/shopDecoration/viewShop.htm?shopId=${shopId}<#else>javascript:;</#if><#else><#if _PREVIEW?? && _PREVIEW == "true">http://www.pinju.com/shopDecoration/viewShop.htm?shopId=${shopId}<#else>shopDecorationAction.htm?shopId=${shopId}</#if></#if>">首页</a></li>
					<!--<li><a href="#">消费者保障</a></li>-->
					<#if shopUserPageList ??>
					<#list shopUserPageList as entity>
					<li<#if _USERPAGEID?? && _USERPAGEID == entity.id> class="count"</#if>><a href="<#if _USERPAGEID?? && _USERPAGEID == entity.id>javascript:;<#else><#if _PREVIEW?? && _PREVIEW == "true">http://www.pinju.com/shopDecoration/viewShop.htm?shopId=${shopId}&userPageId=${entity.id}<#else>shopDecorationAction.htm?shopId=${shopId}&userPageId=${entity.id}</#if></#if>">${entity.title!?html}</a></li>
					</#list>
					</#if>
				</ul>
			</div>
			<!--========================================================●店铺招牌结束-->
			