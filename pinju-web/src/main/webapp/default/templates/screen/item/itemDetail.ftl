<#setting number_format="#">
<#assign itemDO = query.itemDO>
<title>${itemDO.title!?html}-品聚网</title>
<link rel="stylesheet" href="http://static.pinju.com/jqzoom/css/jquery.jqzoom.css" />
<script src="http://static.pinju.com/jqzoom/js/jquery.jqzoom-core.js"></script>

       <!--内容页面开始-->
<div class="sell-content">
	<#include "/default/templates/control/item/detail/detailLeft.ftl">
	<div class="sell_right">
    <div class="cf mb25">
		<div class="pd_tit">
			<a href="http://service.pinju.com/" class="buyguide" target="_blank"><span>使用帮助</span></a>
			<h3>${itemDO.title?html}<#if query.isLimitBuyItem??&&query.isLimitBuyItem==true>&nbsp;&nbsp;<img src="http://static.pinju.com/images/detail/tegong.png" width="52" height="16" /></#if></h3>
		</div>
        <div class="pd_pic_box">
            <div class="b_pic_box"><a href="${imageServer}${itemDO.picUrl!}" hidefocus="true" rel="gal1" style="cursor:auto;" id="J_itemPhoto"><img src="${imageServer}${itemDO.picUrl!}_310x310.jpg" /></a></div>
            <div class="sp_list_box">
                <ul class="sp_list cf">
                    <li><a class="selected" hidefocus="true" href="javascript:;" rel="{gallery: 'gal1', smallimage: '${imageServer}${itemDO.picUrl!}_310x310.jpg',largeimage: '${imageServer}${itemDO.picUrl!}'}"><img src="${imageServer}${itemDO.picUrl!}_40x40.jpg" /></a></li>
                	<#if itemDO.itemPicList??>
                		<#list itemDO.itemPicList as picDO>
                    	<li><a hidefocus="true" href="javascript:;" rel="{gallery: 'gal1', smallimage: '${imageServer}${picDO.picUrl!}_310x310.jpg',largeimage: '${imageServer}${picDO.picUrl!}'}"><img src="${imageServer}${picDO.picUrl}_40x40.jpg" /></a></li>
                    	</#list>
 					</#if>
                </ul>
            </div>
        </div>
        <div class="pd_info_box">
			<ul class="pd_normal">
			<#if !query.activityDiscount??&&!groupbuy??>
            	 <li class="pd_value">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：<span class="value" id="SpecPrice">${query.getPriceCentToDollar(itemDO.price)!}</span>元</li>
            <#elseif !query.activityDiscount??&&groupbuy??>
            	 <li class="pd_value">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：<del id="SpecPrice">${query.getPriceCentToDollar(itemDO.price)!}</del>元</li>
            	 <li class="pd_value">促&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销：<span class="tuan_cupon"></span><span class="value" id="saleValue">${query.getPriceCentToDollar(itemDO.price)!}</span>元</li>
            <#else>
            	 <li class="pd_value">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：<del id="SpecPrice">${query.getPriceCentToDollar(itemDO.price)!}</del>元</li>
            	 <li class="pd_value"><span class="sale_time"  id="sale_time"></span>促&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销：<span class="<#if groupbuy??&&groupbuy==true>tuan_cupon<#else>time_cupon</#if>"></span><span class="value" id="saleValue">${query.getDiscount(itemDO.price,query.activityDiscount)}</span>元</li>
            </#if>
            	<li class="pd_value">购买记录： <span class="trade_buy_count count-num">0</span>件</li>
				<li class="pd_express">
					<#include "/default/templates/control/item/detail/detailLogistics.ftl">
				</li>
                <!-- <li class="last"><a href="#pd_tabar" title="查看本商品购买记录" id="buy_record_navigate">点击查看本商品购买记录</a></li> -->
			</ul>
            <div class="sort_box">
            	<#if query.isShelves??&&query.isShelves==true>
					<#include "/default/templates/control/item/detail/detailSku.ftl">
					<div class="sort cf">
						<#include "/default/templates/control/item/detail/detailForm.ftl">
	                </div>
	                <div class="pd_action cf">
	                    <a href="#this" class="btn_buynow btn" id="submitBuyNow"></a>
              			<!--加入购物车层-->
						<#include "/default/templates/control/item/detail/detailShopCart.ftl">  
	                    <#if query.isLimitBuyItem??&&query.isLimitBuyItem==true>
			              		<!--限购层-->
								<#include "/default/templates/control/item/detail/detailLimitBuy.ftl">  
						</#if>
	                </div>
				<#else>
					此商品已下架
	            </#if>
				  <div class="assure">
					<div class="pic"></div>
					<p class="txt">品聚网使用担保交易，确保您的交易安全</p>
				</div>    
				
            </div>
            <ul class="pd_normal_bottom">	
					 <li >所在地区：<span ><#if itemDO.provinces??&&itemDO.provinces!=itemDO.city>${itemDO.provinces!}</#if>${itemDO.city!}</span></li>
					 <li >商品类型：<span ><#if itemDO.itemDegree??&&itemDO.itemDegree==1>全新
					                    	<#elseif itemDO.itemDegree??&&itemDO.itemDegree==2>
					                    		二手
					                    	<#elseif itemDO.itemDegree??&&itemDO.itemDegree==3>
					                    		个人闲置
					                       </#if>
							       </span>
					</li>
			</ul>
        </div>
    </div>
    
    <!-- BShare Button BEGIN -->
	<div style="margin:10px 0;" class="cf">
		<div class="bshare-custom"><a title="分享到QQ空间" class="bshare-qzone" href="javascript:void(0);"></a><a title="分享到新浪微博" class="bshare-sinaminiblog" href="javascript:void(0);"></a><a title="分享到腾讯微博" class="bshare-qqmb" href="javascript:void(0);"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到开心网" class="bshare-kaixin001" href="javascript:void(0);"></a><a title="更多平台" class="bshare-more bshare-more-icon"></a></div><#include "/default/templates/screen/favorite/item_favorite.ftl"><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=8401b976-af0f-404f-a68f-542807f0b012&amp;pophcol=1&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
	</div>
	<!-- BShare Button END -->
    
    <div class="pd_sort_box">
    	<ul class="sort_li cf">
           <#include "/default/templates/control/item/detail/detailCatePro.ftl">
        </ul>
    </div>
    
    <ul id="pd_tabar" class="tabar">
        <li class="selected"><a href="#buy_description">商品详情</a></li>
        <li class="" id="buy_record"><a href="#buy_recorder">购买记录(<span class="trade_buy_count">0</span>)</a></li>
        <li class="" id="buy_rate_view"><a href="#buy_review">交易评价(<span class="trade_comment_count">0</span>)</a></li>
        <li class="" ><a href="#buy_indemnity">保障须知</a></li>
        <li class="" ><a href="#buy_pay">支付方式</a></li>
    </ul>
    <div class="tab_container pd_content_box" id="buy_description">
    	<div id="pd_description">
	    	${itemDO.description!}
    	</div>
    	
    	<!-- 商品评论 -->
    	<div class="title-bar" id="J_commentTitleBar"><span class="title-bar-icon"></span><h4>商品评论(<span class="trade_comment_count">0</span>)</h4></div>
    	<div class="cf">
	    	<ul class="review_list" id="J_contentReviewList">
				正在读取数据，请稍候 ...
			</ul>
			<div id="J_contentRatePagination" class="pagination"></div>
		</div>
		<!-- 成交记录 -->
    	<div class="title-bar" id="J_buyTitleBar"><span class="title-bar-icon"></span><h4>成交记录(<span class="trade_buy_count">0</span>)</h4></div>
    	<div class="cf">
	        <table id="J_contentBuyTable" class="recorder_data" width="100%" border="0" cellspacing="0" cellpadding="0">
	          <thead>
	              <tr>
	                <th>买家</th>
	                <th>价格</th>
	                <th>购买数量</th>
	                <th>拍下时间</th>
	                <th>款式和型号</th>
	              </tr>
	          </thead>
	          <tbody class="already_buy_list">
	              
	          </tbody>
	        </table>    
	        <div id="J_contentBuyPagination" class="pagination"></div>
	    </div>
	    <!-- 保障须知 -->
    	<div class="title-bar" id="J_indemnityNotice"><span class="title-bar-icon"></span><h4>保障须知</h4></div>
    	<div class="buy_indemnity cf" id="J_indemnityNoticeContent" style="display:none;">
    		<#include "/default/templates/control/item/detail/securityNotice.ftl">
    	</div>
    	<!-- 支付方式 -->
    	<div class="title-bar" id="J_payType"><span class="title-bar-icon"></span><h4>支付方式</h4></div>
    	<div class="buy_pay cf" id="J_payTypeContent" style="display:none;">
    		<#include "/default/templates/control/item/detail/paywayInfo.ftl">
    	</div>
	    <!-- 卖家宝贝推荐 -->
    	<div class="title-bar" id="J_recommendItems"><span class="title-bar-icon"></span><h4>浏览了此商品的会员还浏览了</h4></div>
    	<div class="cf">
    		<div class="detail-recommend-items">
			<ul class="cf" id="J_recommendItemsContent">
				<li style="margin-bottom:20px;">正在读取数据，请稍候 ...</li>
			</ul>
			</div>
    	</div>
	    <!-- 安全提示 -->
	    <div class="tipswrap sp-tipswrap">
			<ul>
				<li class="row cf">
					<dl>
						<dt><span class="icon-tips"></span>安全提示：</dt>
	
						<dd>
							<p>交易过程中请勿随意接收卖家发送的可疑文件，请勿点击不明来源的链接，付款前请务必详细核对支付信息。</p>
							<p class="browser">推荐安全软件：IE 8.0</p>
						</dd>
					</dl>
				</li>
				<li class="row cf last">
					<dl>
						<dt><span class="icon-tips"></span>免责声明：</dt>
						<dd>品聚网所展示的商品供求信息由买卖双方自行提供，其真实性、准确性和合法性由信息发布人负责。品聚网不提供任何保证，并不承担任何法律责任。品聚网友情提醒：为保障您的利益，请您使用财付通担保交易。 </dd>
	
					</dl>
				</li>
			</ul>
		</div>
		<!--
		<div class="row">
			<p><span class="icon-comment"></span>我对"商品详情页"有意见或建议，<a href="mailto:kf@pinju.com">对品聚说两句</a></p>
		</div>
		-->
    </div>
    <div class="tab_container" id="buy_recorder" style="display:none;">
		 <#include "/default/templates/control/item/detail/detailBuyDetail.ftl">
    </div>
    <div class="tab_container" id="buy_review" style="display:none;">
		 <#include "/default/templates/control/item/detail/detailRate.ftl">
    </div>
    <div class="tab_container buy_indemnity" id="buy_indemnity" style="display:none;">
		 <#include "/default/templates/control/item/detail/securityNotice.ftl">
    </div>
    <div class="tab_container buy_pay" id="buy_pay" style="display:none;">
		 <#include "/default/templates/control/item/detail/paywayInfo.ftl">
    </div>
    
    <div class="pd_centent_box mb10">
    	<p></p>
    </div>
 
</div>
</div>
 <#include "/default/templates/control/item/detail/detailInit.ftl">
<!-- 回顶部开始 -->
<div class="go-to-top" id="J_goToTop" style="display:none;"></div>
<script>
 	$(function() {
 		var goToTop = $('#J_goToTop');
 		function display() {
 			if ($(window).scrollTop() < 10) {
 				goToTop.hide();
 			} else {
 				goToTop.show();
 			}
 		}
 		goToTop.click(function() {
 			$(window).scrollTop(0);
 		});
 		$(window).scroll(function() {
 			display();
 			if ($.browser.msie && $.browser.version < 7) {
 				goToTop[0].className = goToTop[0].className;
 			}
 		});
 		display();
 	});
 </script>
 <!-- 回顶部结束 -->
