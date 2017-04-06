<#setting classic_compatible=true>
<#setting number_format="#">
				<#if !_PREVIEW?exists || _PREVIEW!="true">
				<div class="notic"><!--=模块提示-->
					<h2><a href="javascript:if(dialog){dialog.open('getEditModuleAction.htm?shopId=${_SHOPID}&userPageId=${_USERPAGEID}&moduleId=11',900,550);}">掌柜推荐商品</a></h2>			
				</div>
				</#if>
				<!--===================================-->
				<div class="box prolister cf theme${picSize}"   >
					<#if isShowTitle?? && isShowTitle == "1" && promoteTitle??>
					<h3 class="title"><#if promoteTitle?exists>${promoteTitle?html}</#if></h3>
					</#if>
					<#if __ISRELEASE?exists && __ISRELEASE=="TRUE">
						<div class="prolister_m">
							<ul class="cf">
								<#if query ?? && query.resultList ??>
								<#list query.resultList as item>
								<li class="item">				
									<div class="itemwrap">
										<div class="img">
											<a href="http://www.pinju.com/detail/${item.id}.htm"><img src="${picServer}${item.picUrl!""}_160x160.jpg" /></a>
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
					<#else>
						<div class="prolister_${DIMENSION!"m"}">
								<#if ITEMLIST?exists>
									<#list ITEMLIST as entity>
											<ul class="sitemlist">
												<li class="img-outer"><a target="_blank" href="http://www.pinju.com/detail/${entity.id}.htm"><img src="${picServer}${entity.picUrl+'_160x160.jpg'}"/></a></li>
												<li class="ptitle"><a target="_blank" href="http://www.pinju.com/detail/${entity.id}.htm">${entity.title?html}</a></li>
												<li class="price"><em>${entity.priceByYuan}</em>元</li>
											</ul>
										
									</#list>
								</#if>
							
						</div>
					</#if>
				</div>	
