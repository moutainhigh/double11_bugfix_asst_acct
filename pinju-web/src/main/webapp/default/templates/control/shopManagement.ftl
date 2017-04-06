<h3>店铺管理</h3>
<li name="red_shopOpen"><a href="${base}/shop/iWillOpenShopAction.htm">我要开店</a></li>
<li name="red_shop"><a href="${base}/shopDecoration/viewShop.htm" target="_blank">查看我的店铺</a></li>
<li name="red_shopImages"><a href="${base}/images/queryStorageAction.htm" target="_blank">图片空间</a></li>
<li name="red_shopDecoration"><a href="${base}/shopDecoration/shopDecorationAction.htm" target="_blank">店铺装修</a></li>
<li name="red_shopCategory"><a href="${base}/shop/shopCategory.htm">店铺分类管理</a></li>
<li name="red_shopInfo"><a id="shopBaseInfoId" href="${base}/shop/showShopInfo.htm">店铺基本设置</a></li>
<li name="red_shopDomain"><a href="${base}/shopDomain/showDomainSettingPageAction.htm">域名设置</a></li>
<li name="red_shopLabel"><a href="${base}/shopLabel/toShopLabel.htm">店铺标签设置 <img src="http://static.pinju.com/img/icon-new.gif" /></a></li>
<#if memberAuth.isMasterAccount()><li name="red_asstMember"><a href="${base}/asst/asst-relation-list.htm">子账号管理 <img src="http://static.pinju.com/img/icon-new.gif" /></a></li></#if>