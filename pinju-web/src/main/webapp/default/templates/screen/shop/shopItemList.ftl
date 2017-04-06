<title>${shopName}</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<script>
	var base="${base?js_string}";
	var itemId="";
	var isActDiscount=false;
	var isLimitBuyItem=false;
</script>
<#setting number_format="#">
	 <#include "/default/templates/control/item/detail/detailLeft.ftl">
	 
	 <form name="shopItemListForm" action="/detail/shopItemList.htm" method="post" id="pageForm">
	 <input type="hidden" name="shopId" value="${shopId!""}" />
	 <input type="hidden" name="categoryId" value="${categoryId!""}" />
	 <div class="wrap_main">
		<div class="bor prolister cf">
			<h3 class="title">商品列表</h3> 
			<div class="prolister_m">
			<ul class="cf">
				<#if query ?? && query.resultList ??>
				<#list query.resultList as item>
				<li class="item">				
					<div class="itemwrap">
						<div class="img">
							<a href="http://www.pinju.com/detail/${item.id}.htm"><img src="${imageServer}${item.picUrl!""}_160x160.jpg" /></a>
						</div>
						<ul>
							<li class="titletext">
								<a href="http://www.pinju.com/detail/${item.id}.htm">${item.title}</a>
							</li>
							<li class="price">
								￥ <strong>${(item.price / 100.0)?string('0.00')}</strong>
							</li>
							<#if item.oldPriceStr!="0">
							<li class="price">
								<del>￥${item.oldPriceStr}</del>
							</li>
							</#if>
							<li class="saleamount">
								最近成交:<strong><#if item.salesNum??>${item.salesNum}<#else>0</#if></strong>笔
							</li>
							<li class="shipinfo">
								<span class="local">
									<#if item.provinces!=item.city>
										${item.provinces}
									</#if>
										${item.city}
								</span>
								<span>运费：${item.freightStr}</span>
							</li>
						</ul>
					</div>
				</li>
				</#list>
				</#if>
			</ul>
			</div>
			<#include "/default/templates/control/bottomPage.ftl">
		</div>
	</div>
	</form>
</div>	

