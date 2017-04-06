<#setting classic_compatible=true>
<#setting locale="zh_cn">
<script src="http://static.pinju.com/js/search/searchUrl.js?t=20111124"></script>
<script>
	$(function() {
	
		var showFacetField = false;
		var s = new SearchUrl();
		s.url = "/hot/hot.htm";
		
		var pages = Number($("#pages").val());
		var currPage = Number($("#currPage").val());
		if (pages > 100) {
			pages = 100;
		}
		$("#Pagination").pagination(pages * 40, {
			current_page : currPage - 1,
			items_per_page : 40,
			num_edge_entries : 3,
			num_display_entries : 3,
			callback : function(p) {
				window.location.href = s.getCustomURL("start", Number(p) + 1);
			}
		});
	
		$("#pagebutton").click(function() {
			var p = getPageNum($("#pagenum").val(), Number(pages));
			window.location.href = s.getCustomURL("start", p);
		});
		
		$(".filter-cname").click(function() {
			$(this).attr("href", s.getCustomURL("catetoryName", $(this).attr("cname")));
		});
		
		$(".price-f").click(function(){
			s.prop.startPrice = 0;
			s.prop.endPrice = 0;
			var min = $(this).attr("min");
			var max = $(this).attr("max");
			if(min){
				s.prop.startPrice = min;
			}
			if(max){
				s.prop.endPrice = max;
			}
			window.location.href = s.getURL();
		});
		
		$("#allResult").click(function(){
			s.prop.catetoryName="";
			window.location.href = s.getURL();
		});
		
		//img delay load
		$(".item_img").lazyload( {
			placeholder : "http://static.pinju.com/img/blank.gif"
		});
		
		$("#allFacetField").click(function() {
			if (!showFacetField) {
				$(".h").removeClass("hide");
				$(this).removeClass("showall");
				$(this).addClass("showpart");
				$(this).html("收起");
				
				showFacetField = true;
			} else {
				$(".h").addClass("hide");
				$(this).removeClass("showpart");
				$(this).addClass("showall");
				$(this).html("显示全部分类");
				showFacetField = false;
			}
		});
		
		
	});
</script>

<input type="hidden" id="prop_searchServer" value="${searchServer}" />
<input type="hidden" id="prop_q" value="${q?html}" /> 
<input type="hidden" id="prop_filterExclude" value="${filterExclude?html}" /> 
<input type="hidden" id="prop_features" value="${features}" /> 
<input type="hidden" id="prop_cp" value="${cp}" /> 
<input type="hidden" id="prop_catetoryName" value="${catetoryName}" /> 
<input type="hidden" id="prop_startPrice" value="${startPrice}" /> 
<input type="hidden" id="prop_endPrice" value="${endPrice}" /> 
<input type="hidden" id="prop_start" value="${start}">
<input type="hidden" id="prop_facetLimit" value="${facetLimit}">
<input type="hidden" id="pages" value="${query.pages?default(0)}">
<input type="hidden" id="currPage" value="${query.page?default(0)}">

<br/>
<div class="wrap sp-search cf">

	<div class="plwrap cf">

		<div class="bar-left">
			<h2 class="bar-topic">商品分类</h2>
					<ul class="result-pcat">
						<#if (query.facetField??) >
							<#assign i=0>
							<#list query.facetField as facet>
									<#if facet?substring(facet?last_index_of("(")+1,facet?last_index_of(")")) != "0" >
										<#if (i>10)> <li class="hide h"> <#else> <li> </#if>
												<a href="javascript:" class="filter-cname<#if (i>10)> hide h</#if>" cname="${facet?substring(0,facet?last_index_of("(")-1)}" title="">
													${facet!}
												</a>
										</li>
										<#assign i=i+1>
									</#if>						
							</#list>
								<!--
								<li><a href="" title="" class="current">手机通讯</a></li>
								-->
							<#if (i>10)>
							<li><b><a class="showall" href="javascript:" id="allFacetField">显示其他分类</a></b></li>
							</#if>
						</#if>	
					</ul>
		</div>
		<div class="contents">

			<div class="plist-hotag cf">
				<ul>
					<li>
						<a href="javascript:" id="allResult">${q}</a>
					</li>
					<#if catetoryName?? && catetoryName!=""><li><a href="javascript:">${catetoryName}</a></li></#if>
					
				</ul>
			</div>

			<dl class="relatedprod">
				<dt>
					价格区间:
				</dt>
				<dd>
					<a class="price-f" title="全部" href="#">全部</a>
				</dd>
				<dd>
					<a class="price-f" max="30" title="30元以下" href="#">30元以下</a>
				</dd>
				<dd>
					<a class="price-f" min="30" max="70" title="30-70" href="#">30-70</a>
				</dd>
				<dd>
					<a class="price-f" min="70" max="150" title="70-150" href="#">70-150</a>
				</dd>
				<dd>
					<a class="price-f" min="150" max="270" title="150-270" href="#">150-270</a>
				</dd>
				<dd class="last">
					<a class="price-f" min="270" title="270以上" href="#">270以上</a>
				</dd>
			</dl>

			<div class="plist-view pv-large plist-hot">
				<#if query.resultList?? && (query.resultList?size>0) >
				<ul class="cf">
				<#list query.resultList as item>
					<li class="item">
						<div class="itemwrap">
							<div class="img">
								<a target="_blank" href="${base}/detail/${item.id}.htm" title="${item.title}">
									<img class="item_img" src="${imageServer}${item.picUrl}_160x160.jpg"/>
								</a>
							</div>
							<ul>
								<li class="price">
									￥ <strong>${item.priceStr}</strong> <!--<del>￥99999.99</del>-->
								</li>
								<li class="saleamount">
									最近成交:<strong><#if item.salesNum??>${item.salesNum}<#else>0</#if></strong>笔<!--<em>(共999人评)</em>-->
								</li>
								<li class="titletext">
									<a target="_blank" href="${base}/detail/${item.id}.htm" title="${item.title}">
										<#if item.hlTitle??>${item.hlTitle}<#else>${item.title}</#if> 
									</a>
								</li>
								<li class="shipinfo">
									<span>运费：${item.freightStr}</span>
									<span class="local">
										<#if item.provinces!=item.city>
											${item.provinces}&nbsp;&nbsp;
										</#if>
											${item.city}
									</span>
								</li>
								<li class="shoptitle">
									<a target="_blank" href="${base}/shopDecoration/viewShop.htm?sellerId=${item.sellerId}" title="${item.sellerNick}">
										${item.sellerNick}
									</a>
								</li>
							</ul>
						</div>
					</li>
				</#list>
				</ul>
				<div class="cbottom imgbtn">
					<div class="skipto">
							到第 <input class="pagenum" name="pagenum" value="${query.page}" id="pagenum"> 页 <button class="btn-sgray" id="pagebutton">确定</button>
						</div>
					<div id="Pagination" class="pagination">
					</div>
				</div>
				<#else>
					<div class="tips-text">
						<#if q?? && q!="">
							<p>非常抱歉，没有找到与 “<strong>${q?html}</strong>” 相关的商品</p>
						<#else>
							<p>非常抱歉，没有找到相关商品</p>
						</#if>
						<br/>&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;1、看看输入的文字是否有误<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;2、调整关键字，如：“nokiann9”改成“nokia n9”<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;3、去掉可能不必要的字词，如“的”、“什么”等
					</div>
					<#if query.recommendationList?? || (query.recommendationList?size>0) >
						<div class="stitle">
							<h2>畅销精品</h2>
						</div>
						<div class="plist-view pv-large">
								<ul class="lslist cf">
									<#list query.recommendationList as item>
									<li class="item">				
										<div class="itemwrap">
											<div class="img">
												<a target="_blank" href="${base}/detail/${item.id}.htm" title="${item.title}">
													<img class="item_img" src="${imageServer}${item.picUrl}_160x160.jpg"/>
												</a>
											</div>
											<ul>
												<li class="titletext">
													<a target="_blank" href="${base}/detail/${item.id}.htm" title="${item.title}">
														<#if item.hlTitle??>${item.hlTitle}<#else>${item.title}</#if> 
													</a>
												</li>
												<li class="price">
													￥ <strong>${item.priceStr}</strong> 
													<#if item.oldPriceStr!="0"><del>￥${item.oldPriceStr}</del></#if>
												</li>
												<li class="saleamount">
													最近成交:<strong><#if item.salesNum??>${item.salesNum}<#else>0</#if></strong>笔<!--<em>(共999人评)</em>-->
												</li>
												<li class="shipinfo">
													<span>运费：${item.freightStr}</span>
													<span class="local">
														<#if item.provinces!=item.city>
															${item.provinces}
														</#if>
															${item.city}
													</span>
												</li>
												<li class="shoptitle">
													<a target="_blank" href="${base}/shopDecoration/viewShop.htm?sellerId=${item.sellerId}" title="${item.sellerNick}">
														${item.sellerNick}
													</a>
												</li>
											</ul>
										</div>
									</li>
									</#list>
							</ul>
						</div>
					</#if>
				</#if>
			</div>
		</div>
	</div>
</div>
