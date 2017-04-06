<link href="http://static.pinju.com/css/sell.css?t=20111214.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/shopDecoration/base.css?t=20111203.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/shopDecoration/style/<#if query.shopFitment??&&query.shopFitment!?exists>${query.shopFitment!}<#else>red</#if>.css" rel="stylesheet" type="text/css" />

<!--${query.shopFitment!}-->
${query.shopUpHtml!}
<div class="sell-left">
<!-- 卖家资质信息 -->
<#include "/default/templates/control/member/seller-quality-info.ftl">
<div class="box">
	<h3 class="title">店铺标签</h3>
	<div id="J_labelArea"></div>
</div>
<textarea id="J_labelData" style="display:none;">
    <#if (query.itemTagMetaInfo.shopLabelList)??>
	<#list query.itemTagMetaInfo.shopLabelList as shopLabels>
		${(shopLabels!'')?html} 
	</#list>
	<#else>
	品聚
	</#if>
</textarea>
<script>
	$(function() {
		var html = '<ul>';
		$.each($.trim($('#J_labelData').val()).split(/\s+/), function(i, val) {
			var num = Math.floor(Math.random() * 3);
			html += '<li><span class="shop-label-word-' + num + '">' + val + '</span></li>';
		});
		html += '</ul>';
		$('#J_labelArea').html(html);
	});
</script>

<div class="box-swear">
		<div class="title">品聚诚信宣言</div>
		<ul>
			<li>抵制贩卖假货  拒绝虚假标价</li>
			<li>对待顾客友善  提高服务能力</li>
			<li>倡导诚实经营  谨防恶性欺诈</li>
			<li>打造无假环境  坚持道德经商</li>
			<li>诚信人人有责  共筑百年辉煌</li>
		</ul>
</div>
${query.shopLeftHtml!}
</div>

