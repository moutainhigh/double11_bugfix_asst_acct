		<#if itemDO.freeTemplateId??&&itemDO.freeTemplateId!=0>
		至<a id="express_ar" class="express_ar" href="#">
		<!-- query.logisticsMap.cityName?default("全部") query.logisticsStr! -->
		<span id="current_city">全部</span></a>：
		<span id="current_value">物流信息加载中..</span>
		<div id="national_list" class="national_list">
			<a href="javascript:;">全部</a>
			<a href="javascript:;">北京 </a>
			<a href="javascript:;">天津 </a>
			<a href="javascript:;">河北 </a>
			<a href="javascript:;">山西 </a>
			<a href="javascript:;">内蒙古</a>
			<a href="javascript:;">辽宁</a>
			<a href="javascript:;">吉林</a>
			<a href="javascript:;">黑龙江</a>
			<a href="javascript:;">上海  </a>
			<a href="javascript:;">江苏  </a>
			<a href="javascript:;">浙江  </a>
			<a href="javascript:;">安徽  </a>
			<a href="javascript:;">福建  </a>
			<a href="javascript:;">江西  </a>
			<a href="javascript:;">山东  </a>
			<a href="javascript:;">河南  </a>
			<a href="javascript:;">湖北  </a>
			<a href="javascript:;">湖南  </a>
			<a href="javascript:;">广东  </a>
			<a href="javascript:;">广西  </a>
			<a href="javascript:;">海南  </a>
			<a href="javascript:;">重庆  </a>
			<a href="javascript:;">四川  </a>
			<a href="javascript:;">贵州  </a>
			<a href="javascript:;">云南  </a>
			<a href="javascript:;">西藏  </a>
			<a href="javascript:;">陕西  </a>
			<a href="javascript:;">甘肃  </a>
			<a href="javascript:;">青海  </a>
			<a href="javascript:;">宁夏  </a>
			<a href="javascript:;">新疆  </a>
			<a href="javascript:;">台湾  </a>
			<a href="javascript:;">香港  </a>
			<a href="javascript:;">澳门  </a>
			<a href="javascript:;">海外  </a>
		</div>
		<#elseif (itemDO.emsCosts??&&itemDO.emsCosts!=0)||(itemDO.deliveryCosts??&&itemDO.deliveryCosts!=0)||(itemDO.mailCosts??&&itemDO.mailCosts!=0)>
			<span id="current_city">运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费</span>：
			<span id="current_value">
			<#if itemDO.emsCosts??&&itemDO.emsCosts!=0>EMS：${(itemDO.emsCosts/100)?string("0.00")}元   </#if>
			<#if itemDO.deliveryCosts??&&itemDO.deliveryCosts!=0>快递：${(itemDO.deliveryCosts/100)?string("0.00")}元   </#if>
			<#if itemDO.mailCosts??&&itemDO.mailCosts!=0>平邮：${(itemDO.mailCosts/100)?string("0.00")}元   </#if></span>
		<#else>
			<span>运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费</span>：卖家承担运费
		</#if>