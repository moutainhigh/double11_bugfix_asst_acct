<title>${itemSnapshotDO.title!?html}-品聚网</title>
<div class="wrap">
	<div class="sail-out-title">
		${itemSnapshotDO.title!?html}
	</div>
	<div class="sail-out-content cf">
		<div class="sail-pic-box">
			<a  href="${base}/detail/${(itemSnapshotDO.itemId)!}.htm" target="_blank">
				<img src="${imageServer}${(itemSnapshotDO.picUrl)!}_310x310.jpg" />
			</a>
		</div>
		<div class="pd_info_box">
			<ul class="pd_normal">
				<li class="pd_value">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：<span class="value" id="SpecPrice">${((itemSnapshotDO.price/100)?string('0.00'))!}</span>元</li>
				<li class="pd_value">运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：
					<#if (itemSnapshotDO.emsCosts??&&itemSnapshotDO.emsCosts!=0)||(itemSnapshotDO.deliveryCosts??&&itemSnapshotDO.deliveryCosts!=0)||(itemSnapshotDO.mailCosts??&&itemSnapshotDO.mailCosts!=0)>
						<#if (itemSnapshotDO.emsCosts)?exists&&itemSnapshotDO.emsCosts!=0>EMS：${(itemSnapshotDO.emsCosts/100)?string('0.00')}元</#if>
						<#if (itemSnapshotDO.deliveruCosts)?exists&&itemSnapshotDO.deliveruCosts!=0>快递：${(itemSnapshotDO.deliveruCosts/100)?string('0.00')}元</#if>
						<#if (itemSnapshotDO.mailCosts)?exists&&itemSnapshotDO.mailCosts!=0>平邮：${(itemSnapshotDO.mailCosts/100)?string('0.00')}元</#if>
					<#else>
						卖家承担运费
					</#if>	
				</li>
			</ul>
			<#--<#if skulList??>
				<div class="pd_info_choice">
					<#list skulList as sku>
						<p>${sku}</p>
					</#list>
				</div><br/>
			</#if>-->
			<div class="pd_info_tip">
				<p class="has-img"><img src="http://static.pinju.com/img/goods/pd_info_tip.gif" />
					您目前看到的是<a href="#">商品快照</a></p>
				<p>该商品已经于${((itemSnapshotDO.lastModified)?string('yyyy年MM月dd日 hh:mm:ss'))!}被修改。</p>
				<p class="look-info"><a target="_blank" href="${base}/detail/${(itemSnapshotDO.itemId)!}.htm" title="点击查看最新商品详情">查看最新商品信息</a></p>
			</div>
			<br/><ul class="pd_normal">
				<li class="pd_value">所在地区：${itemSnapshotDO.provinces!}${itemSnapshotDO.city!}</li>
				<li class="pd_value">商品类型：
					<#if itemSnapshotDO.itemDegree??>
						<#if itemSnapshotDO.itemDegree == 1>全新</#if>
						<#if itemSnapshotDO.itemDegree == 2>二手</#if>
						<#if itemSnapshotDO.itemDegree == 3>闲置</#if>
					</#if>
				</li>
			</ul>
		</div>
		<div class="fr">
			<#include "/default/templates/control/member/seller-quality-info.ftl">
		</div>
	</div>
	<ul class="info-tabs cf">
		<li class="current">
			<a href="#"><span>商品详情</span></a>
		</li>
	</ul>
	<div class="info-content cf">
		<#if (cateProlist?exists&&cateProlist?size!=0) || (spuProList?exists&&spuProList?size!=0)>
			<div class="info-detail">
				<ul class="cf">
					<#if cateProlist??>
						<#list cateProlist as cate>
							<li>${cate.cateProName}： ${cate.cateProValue}</li>
						</#list>
					</#if>
					<#if spuProList??>
						<#list spuProList as spu>
							<li>${spu.cateProName}： ${spu.cateProValue}</li>
						</#list>
					</#if>
				</ul>
			</div></br>
		</#if>
		${(itemSnapshotDO.description)!}
	</div>
</div>

