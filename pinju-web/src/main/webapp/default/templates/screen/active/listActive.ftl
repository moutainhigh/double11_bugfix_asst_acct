<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>可参与的活动</title>
<script type="text/javascript" src="http://static.pinju.com/js/jquery/jquery-1.6.1.min.js"></script>
</head>

<body>
	<link href="http://static.pinju.com/css/platform.css" rel="stylesheet" />
	<div class="active-nav">
      	您的位置：我是卖家 > 营销中心 > <span class="hong"><a href="/active/listActive.htm">活动管理</a></span>
    </div>
	<div class="pl-aleft">
		<ul class="pl-act">
		    <li class="pl-here"><a href="${base}/active/listActive.htm">可参与的活动</a></li>
		    <li><a href="${base}/active/query.htm">我报名的活动</a></li>
		    <li><a href="${base}/active/query.htm?query.checkStatus=1">已参与的活动</a></li>
		</ul>
	
		<table class="pl-table">
			<tr>
				<th>活动图片</th>
				<th style="width:300px;">活动内容</th>
				<th>类型</th>
				<th>收费类型</th>
				<th>操作</th>
			</tr>
			<#if activeInfoList?exists>
				<#if (activeInfoList?size>0) >
					<#list activeInfoList as activeInfo>
					<tr class="pl-actr">
						<td><img src="${imageServer}${(activeInfo.logoUrl)!}_160x160.jpg"/></td>
						<td>
							<span style="font-weight:bold;font-size:14px;">
							<a href="${base}/active/showActive.htm?activeInfoId=${activeInfo.id}" class="lan">${(activeInfo.name)!?html}</a>
							</span>
							<br />
							<span>报名时间：${activeInfo.registStartTime?string("yyyy-MM-dd")} 
											~~ ${activeInfo.registEndTime?string("yyyy-MM-dd")}</span>
							<br />
							<span>活动时间：${activeInfo.activeStartTime?string("yyyy-MM-dd")} 
											~~ ${activeInfo.activeEndTime?string("yyyy-MM-dd")}</span>
							<br />
							<span style="display:block; width:300px;">活动简介：<br />${(activeInfo.brief)!}</span>
						</td>
						<td><#if activeInfo.registType == 1>商品报名<#else>店铺报名</#if></td>
						<td>${(activeInfo.points)!} 积分</td>
						<td>
							<a href="${base}/active/registActive.htm?activeInfo.id=${activeInfo.id}&activeInfo.registType=${activeInfo.registType}">
								<img src="http://static.pinju.com/images/橘色.gif"/>
							</a>
							<br />
							<a href="${base}/active/showActive.htm?activeInfoId=${activeInfo.id}">
								活动详情
							</a>
						</td>
					</tr>
					</#list>
			  	<#else>
		    		<tr><td colspan="5" align='center'><label class="hong">没有数据！</label></td></tr>
				</#if>
			</#if>
		</table>
		<div class="sel-ye" style="float: right">
			<form action="" method="post" name="pageForm" id="pageForm">
				<#include "/default/templates/control/pagination.ftl">
			</form>
		</div>
	</div>
<input type="hidden" value="list-active" id="my-page" />
</body>
</html> 