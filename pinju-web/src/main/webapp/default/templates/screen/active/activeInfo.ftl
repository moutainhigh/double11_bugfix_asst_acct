<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>活动详情</title>
<script type="text/javascript" src="http://static.pinju.com/js/jquery/jquery-1.6.1.min.js"></script>
</head>

<body>
	<link href="http://static.pinju.com/css/platform.css" rel="stylesheet" />
	<div class="active-nav">
      	您的位置：我是卖家 > 营销中心 > <span class="hong"><a href="/active/listActive.htm">活动管理</a></span>
    </div>
	<div class="pl-aleft" style="text-align: left;">
		<p class="title15">${(activeInfo.name)!?html}</p>
		<ul class="pl-content">
			<li><span class="pl-lileft">报名时间</span>${activeInfo.registStartTime?string("yyyy-MM-dd")} 
								~~ ${activeInfo.registEndTime?string("yyyy-MM-dd")}</li>
			<li><span class="pl-lileft">活动时间</span>${activeInfo.activeStartTime?string("yyyy-MM-dd")} 
								~~ ${activeInfo.activeEndTime?string("yyyy-MM-dd")}</li>
			<li><span class="pl-lileft">报名类型</span><#if activeInfo.registType == 1>商品<#else>店铺</#if></li>
			<li><span class="pl-lileft">所需积分</span>${(activeInfo.points)!0}分 </li>
			<#if activeInfo.registType == 1>
				<li>
					<span class="pl-lileft">折扣限制</span><#if (activeInfo.discountRate)??>${(activeInfo.discountRate/100)?string("0.##")}</#if>折以下
				</li>
			</#if>
			<li>
				<span class="pl-lileft">您的报名状态</span>
				<#if isRegist>已报名	
					<#if canRegist != 0 >
						<a href="${base}/active/registActive.htm?activeInfo.id=${activeInfo.id}&activeInfo.registType=${activeInfo.registType}" 
							style="vertical-align:top;margin:0 5px;">
							<img src="http://static.pinju.com/images/橘色.gif" />
						</a>
					</#if>
				<span class="hui">
				<#if canRegist?exists && (canRegist > 0)>
					<#if activeInfo.registType == 1>
						本次活动您还可报名${canRegist}件商品，已报名${registed}件。
					<#else>
						本次活动您还可报名${canRegist}个店铺，已报名${registed}个。
					</#if>
				</#if>
				</span>
				<#else>未报名
					<a href="${base}/active/registActive.htm?activeInfo.id=${activeInfo.id}&activeInfo.registType=${activeInfo.registType}" 
						style="vertical-align:top;margin:0 5px;">
						<img src="http://static.pinju.com/images/橘色.gif" />
					</a>
				</#if>
			</li>
		</ul>
		<ul class="pl-act">
		  <li id="detail" class="pl-here">
		  	<a href="${base}/active/showActive.htm?activeInfoId=${activeInfo.id}&toWhere=activeInfo">活动详情</a></li>
		  <li id="regist">
		  	<a href="${base}/active/showActive.htm?activeInfoId=${activeInfo.id}&toWhere=registInfo">我的报名</a></li>
		</ul>
		<div class="pl-con">${(activeInfo.memo)!}</div>
	</div>
	<input type="hidden" value="list-active" id="my-page" />
</body>
</html> 