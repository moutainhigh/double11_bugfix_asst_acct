<#setting classic_compatible=true>
<#setting locale="zh_cn">

<br/>

<script src="http://static.pinju.com/js/search/searchUrl.js?t=20111124"></script>
<script src="http://static.pinju.com/js/search/itemSearch.js?t=20111124"></script>

<input type="hidden" id="prop_searchServer" value="${searchServer}" />
<input type="hidden" id="prop_q" value="${q?html}" /> 
<input type="hidden" id="prop_filterExclude" value="${filterExclude?html}" /> 
<input type="hidden" id="prop_features" value="${features}" /> 
<input type="hidden" id="prop_cp" value="${cp}" /> 
<input type="hidden" id="prop_sort" value="${sort}" /> 
<input type="hidden" id="prop_city" value="${city}" /> 
<input type="hidden" id="prop_catetoryName" value="${catetoryName}" /> 
<input type="hidden" id="prop_startPrice" value="${startPrice}" /> 
<input type="hidden" id="prop_endPrice" value="${endPrice}" /> 
<input type="hidden" id="prop_start" value="${start}">
<input type="hidden" id="prop_facetLimit" value="${facetLimit}">
<input type="hidden" id="pages" value="${query.pages?default(0)}">
<input type="hidden" id="currPage" value="${query.page?default(0)}">
<input type="hidden" id="prop_style" value="${style?default('grid')}">

<div class="wrap sp-search cf">
	<div class="plwrap cf">
		<div class="bar-left">
			<#if cp?? || (!query.resultList?? || (query.resultList?size<=0))>
				<#include "/default/templates/control/item/searchCate.ftl">
			<#else>
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
			</#if>
		</div>
		<div class="contents">
			
			<ul class="breadcrumbs-s"> 
				<#if cateResult?? && cp??>
					<li><a href="http://www.pinju.com">首页</a></li>
					<#if cateResult.oneLevelCateName??>
						<li><a href="${base}/search/search.htm?cp=${cateResult.oneLevelCateId}">${cateResult.oneLevelCateName}</a></li>
					</#if>
					<#if cateResult.twoLevelCateName??>
						<li><a href="${base}/search/search.htm?cp=${cateResult.twoLevelCateId}">${cateResult.twoLevelCateName}</a></li>
					</#if>
					<#if cateResult.threeLevelCateName??>
						<li><a href="${base}/search/search.htm?cp=${cateResult.threeLevelCateId}">${cateResult.threeLevelCateName}</a></li>
					</#if>
					<#if cateResult.fourLevleCateName??>
						<li><a href="${base}/search/search.htm?cp=${cateResult.fourLevleCateId}">${cateResult.fourLevleCateName}</a></li>
					</#if>
				<#else>
					<li><a href="javascript:" id="allResult">全部结果</a></li>
					<#if catetoryName?? && catetoryName!=""><li><a href="${base}/search/search.htm?catetoryName=${catetoryName}">${catetoryName}</a></li></#if>
				</#if>
				<li><#if q?? && q!="">"${q?html}"</#if></li>
				<li class="total">找到相关商品<strong>${query.getItems()}</strong>件</li>
			</ul>
			<ul class="pviewmode cf">
				<li class="pvmlarge"><a <#if !(!style?? || (style?? && style=='grid'))>class="current"</#if> href="javascript:" id="style-grid">大图模式</a></li>
				<li class="pvmlist"><a  <#if !(style?? && style=='list')>class="current"</#if> href="javascript:" id="style-list">列表模式</a></li>
			</ul>
			<div class="sfilter stitle cf">
                <dl class="subfilter">
					<dt>&nbsp;</dt>
					<dd class="orderd" >
						<a href="javascript:" class="sort-starttime <#if !sort?? || (sort?exists && sort=="startTime-desc")> active</#if>">默认排序</a>
					</dd>
					<dd class="ordersale">
						<a href="javascript:" class="sort-salesNum<#if sort?exists && sort=="salesNum-desc"> l2h</#if>">销量</a>
					</dd>
					<dd class="orderprice">
						<a <#if sort?exists && (sort=="price-asc" || sort=="price-desc")>class="active"</#if> href="javascript:">价格</a>
						<ul class="sortlist">
							<li><a title="价格从低到高" href="#l2h" class="sort-price-asc l2h">价格从低到高</a></li>
							<li><a title="价格从高到低" href="#h2l" class="sort-price-desc h2l">价格从高到低</a></li>
						</ul>
					</dd>
					<#if style?? && style=='list'>
					<dd class="pinfo"><span>商品信息</span></dd>
					<dd class="price"><span>价格</span></dd>
					</#if>
					<dd id="selectLocal" class="localwrap">
						<a src="javascript:" title="<#if city?exists && city!="" && city!="所有地区">${city}<#else>所在地</#if>" class="slocal"><#if city?exists && city!="" && city!="所有地区">${city}<#else>所在地</#if></a>
						<ul style="display: none;" id="selectLocal">
							<li>
								<a href="javascript:" p="1">所有地区</a>
							</li>
							<li>
								<a href="javascript:" p="1">江浙沪</a>
								<a href="javascript:" p="3">珠三角</a>
								<a href="javascript:" p="5">港澳台</a>
								<a href="javascript:" p="13">海外</a><br>
								<a href="javascript:" p="1">北京</a>
								<a href="javascript:" p="3">上海</a>
								<a href="javascript:" p="5">广州</a>
								<a href="javascript:" p="13">深圳</a>
							</li>
							<li>
								<a href="javascript:">杭州</a>
								<a href="javascript:">温州</a>
								<a href="javascript:">宁波</a>
								<a href="javascript:">南京</a>
								<a href="javascript:">苏州</a>
								<a href="javascript:">济南</a><br>
								<a href="javascript:">青岛</a>
								<a href="javascript:">大连</a>
								<a href="javascript:">无锡</a>
								<a href="javascript:">合肥</a>
								<a href="javascript:">天津</a>
								<a href="javascript:">长沙</a><br>
								<a href="javascript:">武汉</a>
								<a href="javascript:">郑州</a>
								<a href="javascript:">石家庄</a>
								<a href="javascript:">成都</a>
								<a href="javascript:">重庆</a><br>
								<a href="javascript:">西安</a>
								<a href="javascript:">昆明</a>
								<a href="javascript:">南宁</a>
								<a href="javascript:">福州</a>
								<a href="javascript:">厦门</a>
								<a href="javascript:">南昌</a><br>
								<a href="javascript:">东莞</a>
								<a href="javascript:">沈阳</a>
								<a href="javascript:">长春</a>
								<a href="javascript:">哈尔滨</a>
							</li>
							<li>
								<a href="javascript:">河北</a>
								<a href="javascript:">河南</a>
								<a href="javascript:">湖北</a>
								<a href="javascript:">湖南</a>
								<a href="javascript:">福建</a>
								<a href="javascript:">江苏</a><br>
								<a href="javascript:">江西</a>
								<a href="javascript:">广东</a>
								<a href="javascript:">广西</a>
								<a href="javascript:">海南</a>
								<a href="javascript:">浙江</a>
								<a href="javascript:">安徽</a><br>
								<a href="javascript:">吉林</a>
								<a href="javascript:">辽宁</a>
								<a href="javascript:">黑龙江</a>
								<a href="javascript:">山东</a>
								<a href="javascript:">山西</a><br>
								<a href="javascript:">陕西</a>
								<a href="javascript:">新疆</a>
								<a href="javascript:">内蒙古</a>
								<a href="javascript:">云南</a>
								<a href="javascript:">贵州</a><br>
								<a href="javascript:">四川</a>
								<a href="javascript:">甘肃</a>
								<a href="javascript:">宁夏</a>
								<a href="javascript:">青海</a>
								<a href="javascript:">西藏</a>
								<a href="javascript:">香港</a><br>
								<a href="javascript:">澳门</a>
								<a href="javascript:">台湾</a>
							</li>
						</ul>
					</dd>
					<#if style?? && style=='list'><dd class="order"><span>最近成交</span></dd></#if>
				</dl>
			</div>
			
			<#if !query.resultList?? || (query.resultList?size<=0) >
			<br/><div class="tips-text">
				<#if q?? && q!="">
					<p>非常抱歉，没有找到与 “<strong>${q?html}</strong>” 相关的商品</p>
				<#else>
					<p>非常抱歉，没有找到相关商品</p>
				</#if>
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：<br/>
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
			<#else>
			
			
				
			<div class="pdcat">		
					<#if !style?? || (style?? && style=='grid')>
					<div class="plist-view pv-large">
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
					<#else>	
					<ul class="plist-view cf" id="itemList">
							<#list query.resultList as item>
								<li class="item">				
									<div class="img list_img">
										<a target="_blank" href="${base}/detail/${item.id}.htm" title="${item.title}" rel="${imageServer}${item.picUrl}_310x310.jpg">
											<img class="item_img" src="${imageServer}${item.picUrl}_80x80.jpg" width="80" height="80"/>
										</a>
									</div>
									<dl  class="titletext">
										<dt>
											<a target="_blank" href="${base}/detail/${item.id}.htm" title="${item.title}">
												<#if item.hlTitle??>${item.hlTitle}<#else>${item.title}</#if> 
											</a>
										</dt>
										<dd>
											<span class="shopowner">
												<a target="_blank" href="${base}/shopDecoration/viewShop.htm?sellerId=${item.sellerId}" title="${item.sellerNick}">
													${item.sellerNick}
												</a>
											</span>
										</dd>
									</dl>
									<dl class="price">
										<dt>￥ <strong>${item.priceStr}</strong></dt>
										<#if item.oldPriceStr!="0"><dt><del>￥${item.oldPriceStr}</del></dt></#if>
										<dd>运费：${item.freightStr}</dd>
									</dl>
									<div class="location">
										<span>
											<#if item.provinces!=item.city>
												${item.provinces}&nbsp;&nbsp;
											</#if>
											${item.city}
										</span>
									</div>
									<div class="saleamount">
										<span>最近成交<#if item.salesNum??>${item.salesNum}<#else>0</#if>笔</span>
									</div>
								</li>
							</#list>
					</ul>
				</#if>
				<div class="cbottom imgbtn">
					<div class="skipto">
							到第 <input class="pagenum" name="pagenum" value="${query.page}" id="pagenum"> 页 <button class="btn-sgray" id="pagebutton">确定</button>
						</div>
					<div id="Pagination" class="pagination">
					</div>
				</div>
			</div>
			</#if>
		</div>
	</div>
</div>	
