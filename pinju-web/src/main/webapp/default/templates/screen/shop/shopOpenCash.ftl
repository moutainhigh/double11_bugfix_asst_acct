<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/new/bail.css" type="text/css" media="screen" />

<!--<form action="${base}/shop/setShopIsOpenAction.htm">-->
<form action="${base}/shop/payMarginAction.htm" method="post">
<input type="hidden" value="100" name="exchangeMargin">
<input type="hidden" value="red_shopOpen" id="my-page" />
<input type="hidden" value="${categoryId}" name="categoryId">
<input type="hidden" value="${selectedCategory}" name="selectedCategory">
<input type="hidden" value="${exchangePrice}" name="exchangePrice">

<div class="openshop">
			<div class="ptitle">
				<h1>缴纳保证金</h1>
				<span class="eye"><a href="/shop/iWillOpenShopAction.htm">返回</a></span>
			</div>
			<div class="infowrap">
				<#if errorMessage?? && errorMessage!="">
					<div class="shop_pointer_style">
						<p class="red">${errorMessage}</p>
					</div>
				</#if>
				<ol class="paylevelinfo">
					<li>您的店铺类目是：<strong>${selectedCategory}</strong></li>
					<li>此类目需要缴纳保证金：<strong>￥${exchangePrice}</strong></li>
					<li>
					<button type="submit" class="btn-paybzj">
						<span>缴纳保证金</span>
					</button>
					<li>缴纳保证金表示您同意加入<em>基础消保服务</em>.</li>

				</ol>
				
				<div class="cservice">
					<span class="icon-cservice"></span>
					<p><strong>基础消保服务</strong> 指根据您的店铺的主营类目向买家提供的基础服务项目，包括但不限于“7天退换货”、“假一赔三服务”、“72小时发货服务”和“30天维修服务”。品聚网将不时公示新增的服务项目或对原服务项目内容进行修订。</p>
				</div>
			</div>
</div>
