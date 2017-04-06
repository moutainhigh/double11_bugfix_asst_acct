<#setting classic_compatible=true>
<#setting number_format="#">
<title>我是卖家</title>
<link href="${staticServer}/css/mypj/mypj.css?t=20111206.css" rel="stylesheet" type="text/css" media="screen" />
<!--<style>
	.wrap-border {
		border-right-width: 0px;
	}
	.contents {padding-right:0; width:838px; }
</style>-->
<script>
function updateMyPageType(){
	$.ajax({
		  url: "/my/updateMyPageType.htm",
		  type:"post",
		  async:false,
		  success: function(){
		  	alert("设置成功！");
		  }
		});
}

function edit_discount(index){
	$("#discountForm_" + index).submit();
} 

function edit_coupon(index){
	$("#couponForm_" + index).submit();
} 
</script>
		

<!--<div class="fullcontents">-->
	<div class="main">
		<div class="toptip cf">
			<!--<iframe src="${base}/my/iframePJTip.htm" width="442" height="29">-->
			<iframe src="http://news.pinju.com/cms/html/newstips" width="585" height="29" frameborder="no" class="newstips">
				<p>您的浏览器不支持此模块。</p>
			</iframe>
			<!--<div class="tsub"><a class="btn_orange" href="javascript:updateMyPageType();" title="设“我是卖家”为首页"><span>设“我是卖家”为首页</span></a></div>-->
		</div>
		<div class="container">
			<div class="content cf">
				<#if shopInfoDO.shopLogo??>
                    <div class="storelogo"><img src="${imageServer}${shopInfoDO.shopLogo}_80x80.jpg" height="80" width="80"/></div>
                <#else>
                    <div class="storelogo"><img src="${staticServer}/img/shop_default_logo.png" height="80" width="80"/></div>
                </#if>
				<div class="storedetails">
					<h2>
					<#if level??>
						<span class="storelevel_${(sellerQualityDO.level)!'4'}" title="${(sellerQualityDO.levelName)!'五品'}"></span>
					</#if>
						${shopInfoDO.name?html}<span>（创立于${shopInfoDO.gmtCreate?string("yyyy-MM-dd")}）</span></h2>
						<p>店铺性质：${shopInfoDO.sellerType} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;店铺所属类目：${shopInfoDO.shopCategory}</p>
						<p>
							<a href="${base}/points/showAccountInfo.htm" title="text">查看我的卖家积分</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="${base}/shopLabel/toShopLabel.htm" title="text">设置我的店铺关键词</a>
						</p>
						<!--<p>已绑定盛付通帐号：<a href="#" title="alfsandy@zba.com">alfsandy@zba.com</a> <a class="btn_gray" href="#" title=""><span>更换绑定帐号</span></a></p>-->
						<p>我的保证金余额：<strong>${(currentMargin/100)?string("0.00")}元</strong></p><!-- modify on 2011-10-14 -->
				</div>
			</div>
		</div>
		<div class="container">
			<div class="title">
				<h3>交易提醒</h3>
				<div class="exinfo">
					<a href="${base}/sellReport/simpleReport.htm" title="一周销售报表">一周销售报表</a>
				</div>
			</div>
			<div class="content orderform cf">						
				<ul>
					<li>
						<dl>
							<dt>待处理订单：</dt>
							<dd><a href="${base}/orderSeller/ordermanagesell.htm?orderState=1&shcm=1" title="">待付款的订单<strong>（${items[2]}）</strong></a></dd>
							<dd><a href="${base}/orderSeller/ordermanagesell.htm?orderState=2&shcm=1" title="">待发货的订单<strong>（${items[0]}）</strong></a></dd>
							<dd><a href="${base}/orderSeller/ordermanagesell.htm?orderState=4&shcm=1" title="">退款中的订单<strong>（${items[1]}）</strong></a></dd> 
						</dl>
					</li>
					<li>
						<dl>
							<dt>快捷商品管理：</dt>
							<dd><a href="${base}/item/itemList.htm?itemQuery.type=2" title="">待上架的商品<strong>（${items[3]}）</strong></a> 
							<a class="btn_orange" href="${base}/itemReleased/categoryList.htm"><span>发布新商品</span></a></dd>
						</dl>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="title">
				<h3>店铺推广</h3>
			</div>
			<div class="content">						
				<ul class="inlinerow">
					<!--<li><a href="#" title="">热卖位</a></li>-->
					<li><a href="${base}/active/listActive.htm" title="">报名参加更多品聚商务活动</a></li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="title">
				<h3>店铺活动</h3>
			</div>
			<div class="content promotion">						
				<dl>
					<#if allActivityList?exists && (allActivityList?size>0)>
						<#list allActivityList as activity>
							<#if activity.type == 1>
							<form id="discountForm_${activity_index}" method="post" action="${base}/active/updateActivityDiscount.htm">
								<dt>[限时打折]</dt>
								<dd>
									<input type="hidden" name="pj0" value="${(pj0)!}"/>
									<input type="hidden" name="id" value="${activity.activityId}"/>
									<a href="javascript:edit_discount(${activity_index});"
										title="${(activity.title)!}">
										<#if (activity.title)!?length <= 15>
											${(activity.title)!}
										<#else>
											${(activity.title)!?substring(0, 15)}...
										</#if>
										（${(activity.startTime)?string("yyyy-MM-dd HH:mm:ss")}
										至
										${(activity.endTime)?string("yyyy-MM-dd HH:mm:ss")}）
									</a>
								</dd>
							</form>
							<#elseif activity.type == 2>
							<form id="couponForm_${activity_index}" method="post" action="${base}/coupon/queryCouponBatchById.htm">
								<dt>[店铺优惠券]</dt>
								<dd>
									<input type="hidden" name="tcb.id" value="${activity.activityId}"/>
									<a href="javascript:edit_coupon(${activity_index});"
										title="${(activity.title)?number}元店铺优惠券">
										${(activity.title)!?number}元店铺优惠券
										（至 ${(activity.endTime)?string("yyyy-MM-dd")}）
									</a>
								</dd>
							</form>
							</#if>
						</#list>
					<#else>
						<span>暂无促销活动信息</span>
					</#if>
				</dl>
				<p>创建更多促销活动：
					<a href="${base}/active/promotionManager.htm" title="限时打折">限时打折</a> | 
					<a href="${base}/coupon/couponBatchAll.htm" title="店铺优惠券">店铺优惠券 </a>
					<!--<a href="${base}/active/promotionManager.htm" title="限时打折">限时打折 | </a>
					<a href="#" title="满就送">满就送 | </a>
					<a href="#" title="搭配套餐">搭配套餐</a>-->
				</p>
			</div>
		</div>
	</div>
	<div class="sidebar">
		<!--<iframe src="${base}/my/iframePJNews.htm" width="240" height="195">-->
		<iframe src="http://news.pinju.com/cms/html/newslister" width="240" height="195" frameborder="no" class="newslister">
			<p>您的浏览器不支持此模块。</p>
		</iframe>
		<iframe src="http://news.pinju.com/cms/html/newslister/adgroup" width="240" height="238" frameborder="no" class="cupon" scrolling="no">
			<p>您的浏览器不支持此模块。</p>
		</iframe>
		<!--<div class="container">
			<a href="${base}/shopDecoration/viewShop.htm?shopId=13107" title=""><img src="${staticServer}/media/my-index-img1-228x90.jpg" width="228" height="90" alt="" /></a>
		</div>
		<div class="container">
			<a href="${base}/detail/shopItemList.htm?shopId=11051&categoryId=13443" title=""><img src="${staticServer}/media/my-index-img2-228x55.jpg" width="228" height="55" alt="" /></a>
		</div>-->
		<div class="container">
			<div class="title">
				<h3>品聚商户</h3>
			</div>
			<div class="content cf">						
				<ul class="col2">
					<!--<li><a href="#" title="">品聚商学院</a></li>-->
					<li><a href="http://service.pinju.com/" target="_blank" title="帮助中心">帮助中心</a></li>
					<li><a href="http://sjbbs.pinju.com/" target="_blank" title="商户论坛">商户论坛</a></li>
				</ul>
				<!--<p>商户热线：021-64110119</p>-->
			</div>
		</div>
	<!--</div>-->
</div>
<input type="hidden" value="" id="my-page" />