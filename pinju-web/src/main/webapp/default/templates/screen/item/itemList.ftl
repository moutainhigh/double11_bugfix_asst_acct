<#setting classic_compatible=true>
<#setting number_format="#">

<#if itemQuery.type==2>
	<title>仓库中的商品</title>
<#elseif itemQuery.type==0>
	<title>出售中的商品</title>
</#if>

<link href="http://static.pinju.com/css/open.css?t=20111215.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>

<link href="http://static.pinju.com/css/page/pagination.css" rel="stylesheet" />
<script src="http://static.pinju.com/js/item/itemList.js"></script>
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>

<!--<div class="contents">-->
	<div class="shop-set cf">
		<!--<ul class="tips">
			<p>
				<strong>品聚友情提醒：</strong>请核对您发布的商品是否符合
				<a href="javascript:" target="_blank">最新商品发布规则</a>，商品累计被扣除12分者将受到
				<a href="javascript:" target="_blank">店铺屏蔽</a>12天的处罚。
			</p>
			<p>
				<strong>品聚最新通知：</strong><a href="javascript:" target="_blank">对于出售假冒商品的卖家，我们将作出严肃处理，商家请及时自查。</a>
			</p>
		</ul>-->
		
		<#if itemQuery.resultMsg?exists>
			<#if (itemQuery.resultMsg?size>0) >
				<ul class="tips">
				<#list itemQuery.resultMsg as msg>
					<p>${msg}</p>
				</#list>
				</ul>
			</#if>
		</#if>
				
		
		<ul class="tips2">
		
			<li>
				<#if itemQuery.type==2>
					仓库中的商品
				<#elseif itemQuery.type==0>
					出售中的商品
				</#if>
				<span>(${itemQuery.items})</span>
			</li>
			<!--<li>
				橱窗推荐总数
				<span>(2)</span>
			</li>
			<li>
				已推荐商品
				<span>(4)</span>
			</li>
			<li>
				<a href="javascript:" target="_blank">推荐规则</a>
			</li>-->
		</ul>
		
		<#if itemQuery.type==0>
			<input type="hidden" value="sell_item" id="my-page" />
			<ul class="tab-main">
				<li class="count">
					<a href="##">出售中的商品</a>
				</li>
			</ul>
		<#elseif itemQuery.type==2>
			<input type="hidden" value="storage_item" id="my-page" />
			<ul class="tab-main" id="filterUl">
	           	<li type="0" <#if itemQuery.filterType==0>class="count"</#if>><a href="javascript:">等待上架的商品</a></li>
	           	<li type="1" <#if itemQuery.filterType==1>class="count"</#if>><a href="javascript:">卖完的商品</a></li>
	           	<li type="2" <#if itemQuery.filterType==2>class="count"</#if>><a href="javascript:">导入的商品</a></li>
	           	<!--<li><a href="##">违规商品</a></li>
	           	<li><a href="##">历史记录</a></li>
	           	-->
	        </ul>
		</#if>
		
		<form id="itemListForm" >
			<div class="search-table cf">
				<table>
					<tr>
						<th style="width:80px;text-align: right;">
							商品名称：
						</th>
						<td>
							<input id="item_search_title" name="itemQuery.title" value="${itemQuery.title?html}"  type="text" class="text">
						</td>
						<th style="width:80px;text-align: right;">
							商家编码：
						</th>
						<td>
							<input id="item_search_code" name="itemQuery.code" value="${itemQuery.code}" type="text" class="text">
						</td>
						<th>&nbsp;</th><td>&nbsp;</td>
						<!--
						<th>
							一级类目：
						</th>
						<td>
							<select name="">
								<option>
									全部类目
								</option>
								<option>
									未分类商品
								</option>
							</select>
						</td>
						-->
					</tr>
					<tr>
						<th style="width:80px;text-align: right;">
							价格：
						</th>
						<td class="short">
							<input id="item_search_minPrice" name="itemQuery.minPriceInput" value="${itemQuery.minPriceInput}" type="text" class="text">
							<span>到</span>
							<input id="item_search_maxPrice" name="itemQuery.maxPriceInput" value="${itemQuery.maxPriceInput}" type="text" class="text">
						</td>
						<th style="width:80px;text-align: right;">
							30天销量：
						</th>
						<td class="short">
							<input id="item_search_minSales" name="itemQuery.minSalesInput" value="${itemQuery.minSalesInput}" type="text" class="text">
							<span>到</span>
							<input id="item_search_maxSales" name="itemQuery.maxSalesInput" value="${itemQuery.maxSalesInput}" type="text" class="text">
						</td>
						<th>&nbsp;</th><td>&nbsp;</td>
						<!--<th>
							<input name="" type="checkbox" value="">
						</th>
						
						<td>
							参与会员折扣搜索
						</td>-->
					</tr>
				</table>
				<cite><input type="button" id="btnSearch" value="搜索">
				</cite>
			</div>
		
			<div class="sale">
				<table class="sale-title">
					<tr>
						<th class="t01" style="text-align: center;width:35%">
							商品名称
						</th>
						<th class="t02" style="text-align: center;width:20%">
							价格
						</th>
						<th class="t03" style="text-align: center;width:10%">
							库存
						</th>
						<th class="t04" style="text-align: center;width:10%">
							30天销量
						</th>
						<th class="t05" style="text-align: center;width:10%">
							发布时间
						</th>
						<th style="text-align: center;width:15%">
							操作
						</th>
					</tr>
				</table>
				<!-- START <批处理> -->
				<table class="sale-option">
					<tr>
						<td>
							<cite>
								<#if itemQuery.type==0>
									<button name="delShelfItem" class="btn-single-sgray" type="button">下架</button>
								<#elseif itemQuery.type==2&&!itemQuery.shopStatus&&itemQuery.filterType!=2>
									<button name="addShelfItem" class="btn-single-sgray" type="button">上架</button>
								<#elseif itemQuery.shopStatus>
									<span style="color:#CC0000;">店铺屏蔽中暂不能做上架商品的操作</span>
								</#if>
								<button name="delItem" class="btn-single-sgray" type="button">删除</button>
							</cite>
							<input id="all" type="checkbox">
							全选
						</td>
					</tr>
				</table>
				<!-- END -->
				
				<!-- START <开店> -->
				<div id="item-List">
					<#if itemList?exists>
			    		<#if (itemList?size>0) >
					    	<#list itemList as item>
					    		<table class="sale-lister">
									<caption>
										<#if item.status ==-9>
										<input name="itemQuery.ids" type="checkbox" disabled="disabled" value="${item.id}">
										<#elseif item.status ==-3>
										<input name="itemQuery.ids" type="checkbox" value="${item.id}"><input name="noAddShelfItem-${item.id}" id="noAddShelfItem-${item.id}" type="hidden" value="${item.id}">
										<#elseif item.status ==2>
										<input name="itemQuery.ids" type="checkbox" value="${item.id}"><input name="noAddShelfItemAPI-${item.id}" id="noAddShelfItemAPI-${item.id}" type="hidden" value="${item.id}">
										<#else>
										<input name="itemQuery.ids" type="checkbox" value="${item.id}">
										</#if>
										商家编码：<#if item.code?exists>${item.code}<#else>无</#if>
									</caption>
									<tr>
										<td class="c01-new" style="width:35%" >
											<#if itemQuery.type==0>
												<div class="pic-wrap"><a href="${base}/detail/${item.id}.htm" target="_blank"><img src="${imageServer}${item.picUrl}_80x80.jpg"></a></div>
												<a href="${base}/detail/${item.id}.htm" target="_blank">${item.title?html}</a>
												<#if item.featuresMap['itemxiangou']>
													<img src="http://static.pinju.com/images/detail/tegong.png" class="autosize"/>
												</#if>
												<#if item.featuresMap['limitDiscount']>
													<img src="http://static.pinju.com/images/detail/discount.png" class="autosize"/>
												</#if>
											<#elseif itemQuery.type==2>
												<div class="pic-wrap"><a href="${base}/detail/${item.id}.htm?key=${item.itemKey}" target="_blank"><img src="${imageServer}${item.picUrl}_80x80.jpg"></a></div>
												<a href="${base}/detail/${item.id}.htm?key=${item.itemKey}" target="_blank">${item.title?html}</a>
												<#if item.featuresMap['itemxiangou']>
													<img src="http://static.pinju.com/images/detail/tegong.png" class="autosize"/>
												</#if>
												<#if item.featuresMap['limitDiscount']>
													<img src="http://static.pinju.com/images/detail/discount.png" class="autosize"/>
												</#if>
											</#if>
										</td>
										<td class="c02" style="text-align: center;width:20%">
											￥<strong>${item.priceStr}</strong>
										</td>
										<td class="c03" style="text-align: center;width:10%;">
											${item.curStock}
										</td>
										<td class="c04" style="text-align: center; width:10%">
											${item.sales}
										</td>
										<td class="c05" style="width:10%">
											<span>${item.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</span>
										</td>
										<td style="width:15%">
											<#if item.status ==-9>
												<span style="color:#CC0000;">已冻结</span>
											<#elseif item.status ==-3>
												<span style="color:#CC0000;">运营下架</span>
												<span><a href="${base}/itemReleased/${item.id}.htm" href="javascript:">编辑商品</a>
													</span>
													<span>
													<#if itemQuery.type==0>
													<input type="button" class="btn-single-sgray" onclick="copyToClipboard('${base}/detail/${item.id}.htm');" value="复制链接">
													<#elseif itemQuery.type==2>
													<input type="button" class="btn-single-sgray" onclick="copyToClipboard('${base}/detail/${item.id}.htm?key=${item.itemKey}');" value="复制链接">
													</#if>
												</span>
											<#else>
												<span><a href="${base}/itemReleased/${item.id}.htm" href="javascript:">编辑商品</a>
													</span>
													<span>
													<#if itemQuery.type==0>
													<input type="button" class="btn-single-sgray" onclick="copyToClipboard('${base}/detail/${item.id}.htm');" value="复制链接">
													<#elseif itemQuery.type==2>
													<input type="button" class="btn-single-sgray" onclick="copyToClipboard('${base}/detail/${item.id}.htm?key=${item.itemKey}');" value="复制链接">
													</#if>
												</span>
											</#if>
										</td>
									</tr>
								</table>
					    	</#list>
					    <#else>
				    		<table class="sale-lister"><tr><td colspan="7" align='center'><label class="hong">没有找到符合条件的商品！</label></td></tr></table>
				    	</#if>
				    	<#else>
				    		<table class="sale-lister"><tr><td colspan="7" align='center'><label class="hong">没有找到符合条件的商品！</label></td></tr></table>
				   	</#if>
			   	</div>
				<!-- END -->
				<!-- START <开店>
				<table class="sale-lister" id="count">
					<caption>
						<input name="" type="checkbox" value="">
						商品编号：123456
					</caption>
					<tr>
						<td class="c01">
							<img src="img/pic-none.jpg">
							控制在20个字内
							<textarea name="" cols="" rows=""></textarea>
							<input name="" type="button" class="button" value="保存">
						</td>
						<td class="c02">
							<span><input name="" type="text" class="text">
							</span>
							<span><input name="" type="button" class="button"
									value="保存">
							</span>
						</td>
						<td class="c03">
							<span><input name="" type="text" class="text">
							</span>
							<span><input name="" type="button" class="button"
									value="保存">
							</span>
						</td>
						<td class="c04">
							44
						</td>
						<td class="c05">
							<span>2011-07-26</span>
							<span>09:20</span>
						</td>
						<td>
							<span><a href="##">编辑商品</a>
							</span>
							<span><input name="" type="button" class="button"
									value="复制链接">
							</span>
						</td>
					</tr>
				</table>
				 -->
				<!-- END -->
				<!-- START <批处理> -->
				<table class="sale-option">
					<tr>
						<td>
							<cite>
							 	<#if itemQuery.type==0>
									<input name="delShelfItem" value="下架" class="btn-single-sgray" type="button">
								<#elseif itemQuery.type==2&&!itemQuery.shopStatus&&itemQuery.filterType!=2>
									<input name="addShelfItem" type="button" class="btn-single-sgray" value="上架">${itemQuery.shopStatus}
								</#if>
								<input name="delItem" value="删除" class="btn-single-sgray" type="button">
							</cite>
						</td>
					</tr>
				</table>
				<!-- END -->
				<input type="hidden" id="searchFilterType" id="filterType" name="itemQuery.filterType" value="${itemQuery.filterType}">
				<input type="hidden" name="itemQuery.type" id="listType" value="${itemQuery.type}">
				<input type="hidden" name="itemQuery.page" id="currPage" value="${itemQuery.page}">
				<input type="hidden" name="" id="pages" value="${itemQuery.pages}">
				<!--
				<li class="page-coder">
					共${itemQuery.items}条记录&nbsp;|&nbsp;当前第<span>${itemQuery.page}</span>页&nbsp;|&nbsp;共<span>${itemQuery.pages}</span>页&nbsp;|&nbsp;到第
					<input name="" type="text">
					页
					<input name="" type="button" value="确定">
				</li>
				-->
				<div id="Pagination" class="pagination"></div>
			</div>
		</form>
	</div>
<!--</div>-->