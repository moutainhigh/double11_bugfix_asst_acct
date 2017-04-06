<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/buy_sell_ui.css" type="text/css" media="screen" rel="stylesheet" /> 
<link rel="stylesheet" href="http://static.pinju.com/css/page/pagination.css" media="screen" />
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<link href="http://static.pinju.com/css/rao/drape.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/js/coupon/getCoupon.js?t=20111208.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/coupon/coupon-batch-main.js"></script>
<script src="http://static.pinju.com/js/ZeroClipboard.js"></script>
<title>品聚网</title>
<STYLE type="text/css">
	.hong {color: #D72426;}
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
</STYLE>

<div class="drape-right">
	<input type="hidden" value="promotion-manager" id="my-page" />
  	<div class="buy-nav">
     	 您的位置：我是卖家 > 营销中心 > <span><a style="color:red;" href="/active/promotionManager.htm">促销管理</a></span>
    </div>

    <ul class="act">
      <li class=""><a href="/active/promotionManager.htm">限时打折</a></li>
	  <li class="here"><a href="/coupon/couponBatchAll.htm">店铺优惠券</a></li>
    </ul>
	<div class="y-module cf">
		<ul class="y-tab cf">
			<li class="hover"><a href="/coupon/couponBatchAll.htm">优惠券活动</a></li>
			<li><a href="${base}/sellerCoupon/sellerCoupon.htm">优惠券统计</a></li>
		</ul>
		<div class="help">
			<a target="_blank" href="http://service.pinju.com/cms/html/2011/mamark_1204/114.html">使用帮助</a>
		</div>
 	<form id="frm" action="${base}/coupon/couponBatchAll.htm" method="post">
		<div class="y-bcon">
			<a href="/coupon/addCouponBatch.htm" class="ylist-btn crbtn">创建店铺优惠券</a>
			<a href="#" class="ylist-btn delbtn" onclick="closeCouponBatch()">删除所有失效优惠券</a>
			<ul class="list-t cf">
				<li class="l1">优惠券预览</li>
				<li class="l2">优惠券描述</li>
				<li class="l3">活动状态</li>
				<li class="l4">有效期</li>
				<li class="l5">操作</li>
			</ul>
			<table class="y-table">
				<#if couponBatchList?exists && couponBatchList.size() &gt; 0>
					<#list couponBatchList as couponBatch>
					<tr>
						<td class="t1">
							<#if (couponBatch.couponCode?index_of("bid="+couponBatch.id) >= 1)>
								${couponBatch.couponCode}
							<#else>
								${couponBatch.couponCode?replace("bid=","bid=${couponBatch.id}")}
							</#if>
						</td>
						<td class="t2">
							<p>批次编号：<span>${couponBatch.id}</span></p>
							<p>面额：<span>${couponBatch.couponMoneyByYuan?substring(0,couponBatch.couponMoneyByYuan?index_of('.'))}</span>元</p>
							<p>使用条件：满<span>${couponBatch.useConditionByYuan?substring(0,couponBatch.useConditionByYuan?index_of('.'))}</span>元</p>
							<p>每人限领：<#if couponBatch.restrictAmount == 0><span>不限</span><#else><span>${couponBatch.restrictAmount}</span>张</p></#if>
							<p>剩余：<span>${couponBatch.surplusAmount}</span>/${couponBatch.releaseCount}</p>
						</td>
						<td class="t3">
							<#if couponBatch.batchStatus == 10>领取中</#if>
							<#if couponBatch.batchStatus == 30 || couponBatch.batchStatus == 20><span>失效</span></#if>
						</td>
						<td class="t4">${couponBatch.couponInvalidDate?string("yyyy-MM-dd")}</td>
						<td class="t5">
							<#if couponBatch.batchStatus == 10>
								<p>
									<a href="#" onclick="editCoupon('couponId${couponBatch_index}')">编辑</a> | 
									<a href="#" onclick="invalidCoupon('couponId${couponBatch_index}')">失效</a>
								</p>
								<p>
									<a href="#" id="btn_couponCode${couponBatch_index}">复制代码</a> | 
									<a href="#" id="btn_couponLink${couponBatch_index}" onclick="copyCode('couponLink${couponBatch_index}')">复制链接</a>
								</p>
							<#elseif couponBatch.batchStatus == 20 || couponBatch.batchStatus == 30>
								<p style="display:none">
									<a href="#" id="btn_couponCode${couponBatch_index}">复制代码</a>
									<a href="#" id="btn_couponLink${couponBatch_index}">复制链接</a>
								</p>
								<p><a href="#" onclick="closeCoupon('couponId${couponBatch_index}')">删除</a></p>
							<#else>
								<p style="display:none">
									<a href="#" id="btn_couponCode${couponBatch_index}">复制代码</a>
									<a href="#" id="btn_couponLink${couponBatch_index}">复制链接</a>
								</p>
							</#if>
						</td>
					</tr>
					
					<input type="hidden" id="couponId${couponBatch_index}" value="${couponBatch.id}">
					
					<div id="couponLink${couponBatch_index}" style="display:none">http://www.pinju.com/async/coupon/buyerObtainCoupon.htm?bid=${couponBatch.id}</div>
					<#if (couponBatch.couponCode?index_of("bid="+couponBatch.id) >= 1)>
						<div id="couponCode${couponBatch_index}" style="display:none">${couponBatch.couponCode}</div>
					<#else>
						<div id="couponCode${couponBatch_index}" style="display:none">${couponBatch.couponCode?replace("bid=","bid=${couponBatch.id}")}</div>
					</#if>
					</#list>
				<#else>
					<tr>
						<td class="t1" colspan="5"><div class="y-tcx">没有创建过优惠券活动！</div></td>
					</tr>
				</#if>		
			</table>
		</div>
		<!--共  ${allCount} 个活动-->
		
		<input type="hidden" name="tcb.id" value="">
		
		<input type="hidden" id="pages" value="${tcb.pages}">
		<input type="hidden" name="tcb.page" id="currPage" value="${tcb.page}">
		<input type="hidden" name="tcb.itemsPerPage" value="10"/>
		
		<#if couponBatchList?exists && couponBatchList.size() &gt; 0>
			<div id="Pagination" class="pagination"></div>
	 		<#include "/default/templates/control/bottomPage.ftl">
 		</#if>
 		
 		
 	</form>
 </div>
 </div>
<script>
	$(document).ready(function() {
		ZeroClipboard.setMoviePath('http://static.pinju.com/js/ZeroClipboard.swf');
		var bSize = ${couponBatchList.size()};
		
		for (i=0; i<bSize; i++) {
			addCouponLinkListener(i);
			addCouponCodeListener(i);
		}
	});
	
	function addCouponLinkListener(id) {
		var clip = new ZeroClipboard.Client();
		clip.addEventListener('mousedown', function(client) {
			clip.setText($("#couponLink"+id).html());
		});
		clip.addEventListener('complete', function(client, text) {
			alert("链接复制成功");
		});
		clip.glue('btn_couponLink' + id);
	}
	
	function addCouponCodeListener(id) {
		var clip = new ZeroClipboard.Client();
		clip.addEventListener('mousedown', function(client) {
			clip.setText($("#couponCode"+id).html());
		});
		clip.addEventListener('complete', function(client, text) {
			alert("代码复制成功");
		});
		clip.glue('btn_couponCode' + id);
	}
</script>