<#setting classic_compatible=true>
<link rel="icon" href="http://static.pinju.com/img/favicon.ico"
	  type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico"
	  type="image/x-icon" />
<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet"
	  type="text/css" media="screen" />
<link href="http://static.pinju.com/css/rao/drape.css" rel="stylesheet"
	  type="text/css" media="screen" />
	  
<script type="text/javascript" src="http://static.pinju.com/js/coupon/sellerCoupons.js"></script>
<script type="text/javascript" src="${base}/default/js/datePicker/WdatePicker.js"></script>
  
<style type="text/css">
	html {
	  margin: 0;
	}
	h3 {font-size: 12px;}
	.buy-nav {
	    background: none repeat scroll 0 0 #FFFFE0;
	    border: 1px solid #F9DFB2;
	    height: 28px;
	    line-height: 28px;
	    margin-bottom: 9px;
	    padding: 0;
	    text-indent: 12px;
	}
</style>
<title>品聚网</title>
<STYLE type="text/css">
	.hong {color: #D72426;}
</STYLE>

<div class="drape-right">
  	<div class="buy-nav">
  		 您的位置：
  		<a href="http://www.pinju.com/my/sell.htm" class="mr10">我是卖家</a> > 
        <a href="http://www.pinju.com/my/sell.htm" class="mr10">营销中心</a> >  
        <span><a style="color:red;" href="/active/promotionManager.htm">促销管理</a></span>
  	</div>
  	<ul class="act">
      <li class=""><a href="/active/promotionManager.htm">限时打折</a></li>
	  <li class="here"><a href="/coupon/couponBatchAll.htm">店铺优惠券</a></li>
    </ul>
<form id="pageForm" action="${base}/sellerCoupon/sellerCoupon.htm" method="post">
	<input type="hidden" value="promotion-manager" id="my-page" />
	<input type="hidden" name="radioValue" id="radioValue" value="${radioValue!""}" />
	<input type="hidden" name="timeMark" value="0" />
  	<div class="y-module cf">
		<ul class="y-tab cf">
			<li><a href="${base}/coupon/couponBatchAll.htm">优惠券活动</a></li>
			<li class="hover"><a href="#">优惠券统计</a></li>
		</ul>
		<div class="help">
			<a target="_blank" href="http://service.pinju.com/cms/html/2011/mamark_1204/114.html">使用帮助</a>
		</div>
		<div class="y-bcon cf">
			<div class="y-cou"><b>*</b>优惠券面额：<input type="radio" name="isSearchCouponMoney" id="isSearchCouponBatch"  onclick="checkRadio('0')" <#if radioValue == 0>checked</#if>/>全部 <input type="radio" id="isSearchCouponBatch" name="isSearchCouponMoney" onclick="checkRadio('1')" <#if radioValue == 1>checked</#if>/>自定义<input type="text" name="couponMoney" <#if radioValue == 0>disabled</#if>  onkeyup="clearNoNum(this);" value="${(couponMoney!"")?html}" id="couponMoneyId" />元
			</div>
			<div class="y-cou">
				<b>*</b>发放类型：
				<select name="releaseType">
					<option value="">全部</option>
					<option value="10" <#if releaseType == 10>selected</#if>>买家领取</option>
				</select>
				&nbsp;&nbsp;&nbsp;按优惠券有效期：
				<input type="text" name="couponCreateDate" value="${couponCreateDate!""}" size="12px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				至<input type="text" name="couponInvalidDate" value="${couponInvalidDate!""}" size="12px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				<button class="tjbtn" onclick="searchCoupon();">统计</button>
			</div>	
			<div class="cou-table">
				<ul class="thead cf">
					<li class="n1">批次编号</li>
					<li class="n2">优惠券面额</li>
					<li class="n3">有效期至</li>
					<li class="n4">发放类型</li>
					<li class="n5">发放数量</li>
					<li class="n6">已使用</li>
					<li class="n7">未使用</li>
				</ul>
				<#if sellCouponList?exists>
					<#if sellCouponList.size() &gt; 0>
						<table class="cou-conttab">
							<#list sellCouponList as coupon>
								<tr>
									<td class="co1">${coupon.id!""}</td>
									<td class="co2">${coupon.couponMoneyByYuan!""}元</td>
									<td class="co3">${coupon.couponInvalidDate?string("yyyy-MM-dd")}</td>
									<td class="co4"><#if coupon.releaseType == 10>买家领取<#else>--</#if></td>
									<td class="co5"><span>${coupon.hasReceivedCount!""}</span>/${coupon.releaseCount!""}</td>
									<td class="co6">${coupon.usedCount!""}</td>
									<td class="co7">${coupon.noUsedCount!""}</td>
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
			<#include "/default/templates/control/bottomPage.ftl">	
		</div>
		
	</div>
	
</form>
</div>
