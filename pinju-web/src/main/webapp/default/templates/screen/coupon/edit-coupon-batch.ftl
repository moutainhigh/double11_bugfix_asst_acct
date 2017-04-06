<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/buy_sell_ui.css" type="text/css" media="screen" rel="stylesheet" /> 
<link href="http://static.pinju.com/css/rao/drape.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet" type="text/css" media="screen" />
<script src="${base}/default/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/coupon/edit-coupon-batch.js"></script>
<title>品聚网</title>
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
		<div class="y-bcon">
		<form id="frm" action="/coupon/updateCouponBatch.htm" methon="post">
			<h3 class="tit">编辑优惠券</h3>
			<ul class="cou-li">
				<li><span class="t-spn"><b>*</b>面额：</span><input class="w80" type="text" value="${tcb.couponMoneyByYuan?substring(0,tcb.couponMoneyByYuan?index_of('.'))}" disabled> 元
				</li>
				<li>
					<span class="t-spn"><b>*</b>有效期至：</span><input class="w80"  type="text" value="${tcb.couponInvalidDate?string("yyyy-MM-dd")}" disabled>
				</li>
				<li>
					<span class="t-spn"><b>*</b>发放总量：</span><input class="w80" type="text" name="tcb.releaseCount" value="${tcb.releaseCount}" disabled>张 
					<#if tcb.releaseCount < 10000>
						增加<input class="w80" type="text" name="increaseReleaseCount" value="0" onblur="couponChange()">张 
					<#else>
						<input type="hidden" name="increaseReleaseCount" value="0">
					</#if>
					<em id="condition" class="point" style="display:none"></em>
				</li>
				<li><span class="t-spn"><b>*</b>每人限领：</span>
					<select name="tcb.restrictAmount" onchange="couponChange()">
						<option value="0" <#if tcb.restrictAmount == 0>selected</#if>>不限</option>
						<option value="1" <#if tcb.restrictAmount == 1>selected</#if>>1</option>
						<option value="2" <#if tcb.restrictAmount == 2>selected</#if>>2</option>
						<option value="3" <#if tcb.restrictAmount == 3>selected</#if>>3</option>
						<option value="4" <#if tcb.restrictAmount == 4>selected</#if>>4</option>
						<option value="5" <#if tcb.restrictAmount == 5>selected</#if>>5</option>
					</select>张
				</li>
				<li>
					<span class="t-spn"><b>*</b>使用条件：</span>　订单满 ${tcb.useConditionByYuan?substring(0,tcb.useConditionByYuan?index_of('.'))}元，可使用。
				</li>
				<li><span class="t-spn"><b>*</b>显示推广文案：</span>
					<select name="tcb.spreadShow" onchange="couponChange()">
						<option value="10" <#if tcb.spreadShow == 10>selected</#if>>显示</option>
						<option value="20" <#if tcb.spreadShow == 20>selected</#if>>不显示</option>
					</select>
				</li>
				<li>
					<span class="t-spn fl"><b>*</b>选择优惠券皮肤：</span>
					<div class="fl" style="position:relative;">
						<ul class="yl-list">
							<li><a class="bg1" href="javascript:;"><img src="http://static.pinju.com/images/ylbg1.gif" /></a></li>
							<li><a class="bg2" href="javascript:;"><img src="http://static.pinju.com/images/ylbg2.gif" /></a></li>
							<li><a class="bg3" href="javascript:;"><img src="http://static.pinju.com/images/ylbg3.gif" /></a></li>
							<li><a class="bg4" href="javascript:;"><img src="http://static.pinju.com/images/ylbg4.gif" /></a></li>
							<li><a class="bg5" href="javascript:;"><img src="http://static.pinju.com/images/ylbg5.gif" /></a></li>
							<li><a class="bg6" href="javascript:;"><img src="http://static.pinju.com/images/ylbg6.gif" /></a></li>
						</ul>
						<div name="couponDiv" id="yh-box" style="line-height:1;  width:190px; height:148px; text-align: left; background:url(${tcb.skinNum}) no-repeat; overflow:hidden;">
							<p id="money" style="color:#FFF; font-size:24px; margin:26px 0 20px 28px; line-height:1;">${tcb.couponMoneyByYuan?substring(0,tcb.couponMoneyByYuan?index_of('.'))}</p>
							<a href="http://www.pinju.com/async/coupon/buyerObtainCoupon.htm?bid=${tcb.id}" style="padding:0; margin:0 0 0 10px;color:#0066cc; font-size:12px; font-weight:bold; line-height:2.5;cursor:pointer;">立即领取</a>
							<p id="spread" style="padding:0; margin:0 10px;  line-height:1.2">								
							</p>
						</div>
					</div>
				</li>
			</ul>
			
			<input type="hidden" name="tcb.id" value="${tcb.id}"/>
			
			<input type="hidden" name="tcouponMoney" value="${tcb.couponMoneyByYuan?substring(0,tcb.couponMoneyByYuan?index_of('.'))}"/>
			<input type="hidden" name="tuseCondition" value="${tcb.useConditionByYuan?substring(0,tcb.useConditionByYuan?index_of('.'))}"/>
			
			<input type="hidden" name="tcb.releaseCount" value="${tcb.releaseCount}"/>
			<input type="hidden" name="tcb.surplusAmount" value="${tcb.surplusAmount}"/>
			<input type="hidden" name="tcb.skinNum" value="${tcb.skinNum}"/>
			<input type="hidden" name="tcb.couponLink" value="http://www.pinju.com/async/coupon/buyerObtainCoupon.htm?bid=${tcb.id}"/>
			<input type="hidden" name="tcb.couponCode" value=""/>
			
			</form>
			<form id="goback"></form>
			<div class="y-btn-b"><button class="btn submit" onclick="isSubmit()">确认</button><button class="btn cancel" onclick="cancel()">取消	</button></div>
		</div>
	</div>
</div>

