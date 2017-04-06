<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/buy_sell_ui.css" type="text/css" media="screen" rel="stylesheet" /> 
<link rel="icon" href="http://static.pinju.com/img/favicon.ico"
	  type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico"
	  type="image/x-icon" />
<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet"
	  type="text/css" media="screen" />
<link href="http://static.pinju.com/css/rao/drape.css" rel="stylesheet"
	  type="text/css" media="screen" />

<script type="text/javascript" src="http://static.pinju.com/js/coupon/buyerCoupons.js?t=20111215.js"></script>

<title>品聚网</title>
<STYLE type="text/css">
	.hong {color: #D72426;}
</STYLE>

 <div class="buy_sell_box">
	 
 	<form id="pageForm" action="${base}/buyerCoupon/buyerCoupon.htm" method="post">
 		<input type="hidden" value="myCoupon-tool" id="my-page" />
 		<input type="hidden" value="10" name="defaultSearchTy"/>
 		
 		<div class="drape-right">
		<h3 class="mycon-t">我的优惠卡券</h3>
		<div class="mycou">
			<ul class="thead cf">
				<li class="n1">可抵现金</li>
				<li class="n2">优惠券编号</li>
				<li class="n3">店铺名称</li>
				<li class="n4">有效期至</li>
				<li class="n5">使用条件</li>
				<li class="n6">来源</li>
				<li class="n7">
					<select name="useStatus" onchange="changeState()">
 						<option value="">全部</option>
 						<option value="10" <#if useStatus == 10>selected</#if>>未使用</option>
 						<option value="20" <#if useStatus == 20>selected</#if>>已使用</option>
 						<option value="30" <#if useStatus == 30>selected</#if>>已过期</option>
 					</select>
					
				</li>
				<li class="n8">操作</li>
			</ul>
			<#if couponList?exists>
				<#if couponList.size() &gt; 0>
					<table class="mycou-ct">
						<#list couponList as coupon>
							<tr>
								<td class="t1"><span>${coupon.couponMoneyByYuan!""}</span>元</td>
								<td class="t2">${coupon.id!""}</td>
								<td class="t3"><a target="_blank" href="http://shop${coupon.shopId!""}.pinju.com">${coupon.shopName?html}</a></td>
								<td class="t4">${coupon.invalidDate?string("yyyy-MM-dd")}</td>
								<td class="t5">
									<#if coupon.useCondition == 0>
			 							不限
			 						<#else>
			 							订单满${coupon.useConditionByYuan}元<br />
			 							(不包含邮费)
			 						</#if>
								</td>
								<td class="t6">卖家</td>
								<td class="t7">
									<#if coupon.useStatus == 20>
			 							已使用
			 						<#else>
			 							<#if coupon.couponStatus == 20>
			 								已过期
			 							<#else>
			 								未使用
			 							</#if>
			 						</#if>
								</td>
								<td class="t8">
									<#if coupon.useStatus == 10>
			 							<#if coupon.couponStatus == 10>
			 								<a class="btn" href="javascript:toUseCoupon('${coupon.shopId!""}');">马上使用</a>
			 							<#elseif coupon.couponStatus == 20>
			 								<a href="javascript:closeCoupon('${coupon.id!""}')">删除</a>
			 							<#else>
			 								--
			 							</#if>
			 						<#else>
		 								<a href="javascript:closeCoupon('${coupon.id!""}')">删除</a>&nbsp;&nbsp;
		 								<a target="_blank" href="${base}/orderSeller/orderinfoBuy.htm?oid=${coupon.orderId!""}">查看详情</a>
			 						</#if>
								</td>
							</tr>
						</#list>
					</table>
				<#else>
					<div class="y-tcx">没有符合条件的数据！</div><br />
				</#if>
			<#else>
				<div class="y-tcx">没有符合条件的数据！</div><br />
			</#if>
		</div>	
	</div>
 	<#include "/default/templates/control/bottomPage.ftl">
 	</form>
 </div>

