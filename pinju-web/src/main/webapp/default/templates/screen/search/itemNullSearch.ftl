<div class="wrap sp-search cf">
	<ul class="breadcrumbs-s">
		<li>所有分类</li>
		<li class="total">找到相关商品 <strong>0</strong> 件</li>
	</ul>

	<div class="plwrap cf">
	
		<div class="bar-left">
			<#include "/default/templates/control/item/searchCate.ftl">
		</div>
		<div class="contents">

			<div class="tips-text">
				<#if q?? && q!="">
					<p>抱歉，没有找到与 “<strong>${q?html}</strong>” 相关的商品</p>
				<#else>
					<p>抱歉，没有找到相关商品</p>
				</#if>
			</div>
			
		</div>
	</div>
</div>