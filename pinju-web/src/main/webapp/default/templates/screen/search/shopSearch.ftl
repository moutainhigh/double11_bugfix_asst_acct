<#setting classic_compatible=true>
<#setting locale="zh_cn">

<script src="http://static.pinju.com/js/search/shopSearch.js?t=20111212"></script>

<div class="wrap breadcrumbs">
	您的位置：
	<a href="http://www.pinju.com">品聚首页</a>&nbsp;<span>&gt;</span>
	店铺搜索
</div>

<script>
	$(function(){
		$("#type-1").click(function(){
			$("#filterActionShopName").attr("checked",true);
			$("#filterActionShopkeeper").attr("checked",true);
			$("#filterActionItemName").attr("checked",true);
			$("#shopSearchAction").submit();
		});
		$("#type-2").click(function(){
			$("#filterActionShopName").attr("checked",true);
			$("#filterActionShopkeeper").attr("checked",true);
			$("#filterActionItemName").attr("checked",false);
			$("#shopSearchAction").submit();
		});
		$("#type-3").click(function(){
			$("#filterActionShopName").attr("checked",false);
			$("#filterActionShopkeeper").attr("checked",false);
			$("#filterActionItemName").attr("checked",true);
			$("#shopSearchAction").submit();
		});
	})
</script>

<div class="wrap sp-search cf">
	<div class="bar-left">
		<div class="box">
			<h2 class="bar-topic">店铺</h2>
			<div class="ct">
				<!-- filter form start -->
				<form name="filterForm" id="shopSearchAction" method="post">
					<input type="hidden" id="pages" value="<#if result??>${result.pages?default(0)}</#if>">
					<input type="hidden" id="currPage" value="<#if result??>${result.page?default(0)}</#if>">
					<input type="hidden" id="start" name="start" value="${start}">
					<input type="hidden" id="city" name="city" value="${city}">
					<input type="hidden" id="s-type" name="type" value="${type}">
					<fieldset>
						<ul class="fullwidth">
							<li>
								<label>关键字</label> <input type="text" value="${q?html}" name="q" id="filterKeywords" size="10" maxLength="50">
							</li>
							<!--<li>
								<label>类　目</label> <select gtbfieldid="13" name="cat" id="filterCategorySelector">
										<option value="">所有类目</option>
										<option value="">女装/女士精品</option>
										<option value="">男装</option>
										<option value="">女鞋</option>
									</select>
								<input type="hidden" id="filterCategoryName" value="" name="catName">
								<input type="hidden" name="rootCat" id="filterCategoryParent" value="">
							</li>
							-->
						</ul>
						<ul>
							<#if conditions??>
								<#list conditions as i>
									<#if i=='name'><#assign namecheck="checked='checked'" /></#if>
									<#if i=='nickName'><#assign nicknamecheck="checked='checked'" /></#if>
									<#if i=='title'><#assign titlecheck="checked='checked'" /></#if>
								</#list>
							</#if>
							<li><input type="checkbox" value="name" ${namecheck} name="conditions" id="filterActionShopName"><label for="filterActionShopName">店铺名</label></li>
							<li><input type="checkbox" value="nickName" ${nicknamecheck} name="conditions" id="filterActionShopkeeper"><label for="filterActionShopkeeper">掌柜名</label></li>
							<li><input type="checkbox" value="title" ${titlecheck} name="conditions" id="filterActionItemName"><label for="filterActionItemName">店铺简介</label></li>
						</ul>
					</fieldset>
					<!-- 
					<fieldset>
						<legend>消保类型</legend>
						<ul>
							<li><input type="checkbox" value="1" name="isprepay" id="filterProtectionTruth"><label for="filterProtectionTruth">如实描述</label></li>
							<li><input type="checkbox" value="4" name="filterProtection1" id="filterProtectionReturn"><label for="filterProtectionReturn">7天退换</label></li>
							<li><input type="checkbox" value="2" name="filterProtection2" id="filterProtectionAuthentic"><label for="filterProtectionAuthentic">假一赔三</label></li>
							<li><input type="checkbox" value="1" name="isb" id="filterProtectionQuality"><label for="filterProtectionQuality">正品保障</label></li>
							<li><input type="checkbox" value="16" name="filterProtection4" id="filterProtectionGuarantee"><label for="filterProtectionGuarantee">30天维修</label></li>
							<li><input type="checkbox" value="8" name="filterProtection3" id="filterProtectionThunder"><label for="filterProtectionThunder">闪电发货</label></li>
						</ul>
					</fieldset>
					-->
					<fieldset class="center">
						<input type="submit" value="确　定" class="btn">
					</fieldset>
				</form>
				<!-- filter form end -->
			</div>
		</div>
	</div>
		<div class="contents">
			<div class="sfilter">
				<dl class="mtitle">
					<dt></dt>
					<dd class="tabbar">
						<ul class="item tab">
							<li <#if type==1>class="current"</#if>><span id="type-1">所有店铺</span></li>
							<li <#if type==2>class="current"</#if>><span id="type-2">叫"<#if (q?length > 8)>${q?substring(0,8)?html}...<#else>	${q?html}</#if>"的店铺</span></li>
							<li <#if type==3>class="current"</#if>><span id="type-3">卖"<#if (q?length > 8)>${q?substring(0,8)?html}...<#else>	${q?html}</#if>"的店铺</span></li>
						</ul>
					</dd>
					<dd class="pageitem">
						<div id="Pagination1" class="pagination">
							<span>
								${result.page!0}/<#if result.pages?? && (result.pages>100)>100<#else>${result.pages!100}</#if>
							</span>
							<#if result.page??>
								<#if ((result.page-1)>0)>
									<a class="prev search-prev" id="topprv" href="javascript:void(0);" title="上一页">
									</a>
								<#else>
									<span class="prev" title="上一页"></span>
								</#if>
							 	<#if (result.page<100) && (result.page<result.pages)>
									<a class="next search-next" id="topnext" href="javascript:void(0);" title="下一页"></a>  			
							  	<#else>
							  		<span class="next" title="下一页"></span>
								</#if>	
							</#if>
						</div>
					</dd>
				</dl>
			</div>
			
			<#if !result?if_exists || (result.items<1)>
			<div style="padding:20px;"></div><br/>
			<div class="tips-text">
				<#if q?? && q!="">
					<p>非常抱歉，没有找到与 “<strong>${q?html}</strong>” 相关的店铺信息</p>
				<#else>
					<p>非常抱歉，没有找到相关的店铺信息</p>
				</#if>
				<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;1、看看输入的文字是否有误<br/>
			</div>
			
			<#else>
			<ul class="shoplist-title cf">
				<li class="mode">店铺信息</li>
				<li class="amount">
					<span>商品数量</span>
				</li>
				<li class="shopowner">
					<span>卖家</span>
				</li>
				<li class="location">
					<select gtbfieldid="11" id="fq-city" name="loc">
								<option selected="" value="">所在地</option>
											<option value="北京">北京</option>
											<option value="重庆">重庆</option>
											<option value="浙江">浙江</option>
											<option value="深圳">深圳</option>
											<option value="广西">广西</option>
											<option value="宁夏">宁夏</option>
											<option value="江西">江西</option>
											<option value="青岛">青岛</option>
											<option value="江浙沪">江浙沪</option>
											<option value="南昌">南昌</option>
											<option value="郑州">郑州</option>
											<option value="福州">福州</option>
											<option value="长沙">长沙</option>
											<option value="沈阳">沈阳</option>
											<option value="四川">四川</option>
											<option value="温州">温州</option>
											<option value="大连">大连</option>
											<option value="宁波">宁波</option>
											<option value="河北">河北</option>
											<option value="西藏">西藏</option>
											<option value="云南">云南</option>
											<option value="甘肃">甘肃</option>
											<option value="湖南">湖南</option>
											<option value="南京">南京</option>
											<option value="海外">海外</option>
											<option value="厦门">厦门</option>
											<option value="黑龙江">黑龙江</option>
											<option value="合肥">合肥</option>
											<option value="新疆">新疆</option>
											<option value="哈尔滨">哈尔滨</option>
											<option value="广东">广东</option>
											<option value="天津">天津</option>
											<option value="成都">成都</option>
											<option value="内蒙古">内蒙古</option>
											<option value="安徽">安徽</option>
											<option value="贵州">贵州</option>
											<option value="陕西">陕西</option>
											<option value="苏州">苏州</option>
											<option value="辽宁">辽宁</option>
											<option value="山西">山西</option>
											<option value="杭州">杭州</option>
											<option value="港澳台">港澳台</option>
											<option value="青海">青海</option>
											<option value="无锡">无锡</option>
											<option value="广州">广州</option>
											<option value="江苏">江苏</option>
											<option value="珠三角">珠三角</option>
											<option value="福建">福建</option>
											<option value="吉林">吉林</option>
											<option value="上海">上海</option>
											<option value="南宁">南宁</option>
											<option value="海南">海南</option>
											<option value="湖北">湖北</option>
											<option value="山东">山东</option>
											<option value="西安">西安</option>
											<option value="河南">河南</option>
											<option value="常州">常州</option>
											<option value="东莞">东莞</option>
											<option value="武汉">武汉</option>
											<option value="石家庄">石家庄</option>
											<option value="济南">济南</option>
											<option value="">所有地区</option>
											<option value="other">其它</option>
					</select>
				</li>
			</ul>
			
			<ul class="shoplist-view cf">
				<#if result?exists>
					<#if result.resultList?? && (result.resultList?size>0)>
						<#list result.resultList as shop>
							<li class="item">
								<div class="mode">
									<div class="img">
										
										<a target="_blank" href="${base}/shopDecoration/viewShop.htm?sellerId=${shop.userId}">
											<img width="70" height="70" class="shop_img" alt="${shop.baseName}" src="${imageServer}${shop.shopLogo}_80x80.jpg">
										</a>
									</div>
									<dl>
										<dt>
										<a target="_blank" <#if shop.baseName?exists>title="${shop.baseName}"</#if> href="${base}/shopDecoration/viewShop.htm?sellerId=${shop.userId}">
											<#if shop.name?exists>${shop.name}</#if></a>
										</dt>
										<dd>店铺简介： <#if shop.title?exists>${shop.title}<#else>无</#if></dd>
									</dl>
									<dd>
									<ul class="exinfo">
										<li><span class="q_p"></span></li>
										<li><span class="q_7"></span></li>
									</ul>
								</dd>
									
								</div>
								<div class="amount">
									<span>
										<#if shop.itemCount?exists>
											${shop.itemCount}
										<#else>
											0
										</#if>
									</span>
								</div>
								<div class="shopowner">
									<a title="text" href="${base}/credit/${encoder.fixedEncodeStrMid(shop.userId)}.htm" target="_blank">${shop.nickName}</a>
								</div>
								<div class="location">
									<span>
										<#if shop.province==shop.city >
											${shop.city}
										<#else>
											${shop.province}&nbsp;${shop.city}
										</#if>
									</span>
								</div>
							</li>
						</#list>
					</#if>
				</#if>
			</ul>
			<div class="cbottom imgbtn">
				<div class="skipto">
					到第 <input class="pagenum" name="pagenum" value="${result.page}" id="pagenum"> 页 <button class="btn-sgray" id="pagebutton">确定</button>
				</div>
				<div id="Pagination" class="pagination">
				</div>
			</div>
		</#if>
	</div>
</div>
