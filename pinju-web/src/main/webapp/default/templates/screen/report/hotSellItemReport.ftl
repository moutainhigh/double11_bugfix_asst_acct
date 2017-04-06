
<link rel="stylesheet" href="http://static.pinju.com/css/new/sell_report.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/page/pagination.css" media="screen" />
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<script src="http://static.pinju.com/js/report/hotItemReport.js"></script>
<title>热卖商品-销售报表-品聚网</title>
<div class="sell_report cf">
	<form id="hotItemsForm" >
	<div class="report_links sell_tx_box"> 您的位置：
  	<a href="http://www.pinju.com/my/sell.htm">我是卖家</a> > <span>销售报表</span></div>
  	
	<div class="sell_statistics">
		<p class="black bd">简易销售统计</p>
		<p>在这里您可以获得各种时间段里的销售统计，所有销售统计均以订单支付成功为准</p>
		<div>
			<span class="txt">按时间段</span>
			<select name="sellReportQuery.dateCode" selVaule="${sellReportQuery.dateCode!""}">
				<option value="1">昨天</option>
				<option value="2">近三天</option>
				<option value="3">近一周</option>
				<option value="4">近一个月</option>
			</select>
			<input class="statistics_but" type="button" value="统计" id="statistics_but"/>
		</div>
	</div>
	<div class="view_box" style="font-weight:bold">
		您正在查看 <span>${sellReportQuery.displayBeginDate!""}</span>
		至<span>${sellReportQuery.displayEndDate!""}&nbsp;</span>的统计数据
	</div>
	
	<div class="report_tab">
		<ul class="report_tab_list cf">
			<li onclick="window.location.href='/sellReport/simpleReport.htm'" >销售简表</li>
			<li onclick="window.location.href='/sellReport/hotSellerCategoryStatistics.htm'">热卖分类</li>
			<li class="focus">热卖商品</li>
		</ul>
	</div>
	<#if (sellReportQuery.isHasData !false) >
	<div class="report_point">最多只能统计到近一个月的热卖商品销售报表</div>
	<div class="report_table_box">
		<table class="report_table">
			<thead>
			<tr>
				<td class="td01">商品名称</td>
				<#assign buyNumImg="http://static.pinju.com/images/report/order_dis.gif"/>
					<#assign orderItemPriceImg="http://static.pinju.com/images/report/order_dis.gif"/>
					<#if sellReportQuery.orderByKey == "ITEM_SUM" && sellReportQuery.sort =="DESC">
						<#assign buyNumImg="http://static.pinju.com/images/report/order_desc.gif"/>
					</#if>
					<#if sellReportQuery.orderByKey == "ITEM_SUM" && sellReportQuery.sort =="ASC">
						<#assign buyNumImg="http://static.pinju.com/images/report/order_asc.gif"/>
					</#if>
					<#if sellReportQuery.orderByKey == "PRICE_AMOUNT" && sellReportQuery.sort =="DESC">
						<#assign orderItemPriceImg="http://static.pinju.com/images/report/order_desc.gif"/>
					</#if>
					<#if sellReportQuery.orderByKey == "PRICE_AMOUNT" && sellReportQuery.sort =="ASC">
						<#assign orderItemPriceImg="http://static.pinju.com/images/report/order_asc.gif"/>
					</#if>
				
				<td class="td02">销售量 <img id="buyNum" src="${buyNumImg}" 
				onclick="orderSubmit('ITEM_SUM');" /></td>
					<td class="td03">销售金额(元) <img id="orderItemPrice" 
					src="${orderItemPriceImg}" onclick="orderSubmit('PRICE_AMOUNT');"/>
					</td>
			</tr>
			
			</thead>
			<tbody>
			<#if sellItemsReportDOList?exists>
			<#list sellItemsReportDOList as itemReport>
			<tr>
				<td>
					<div class="items_name_box cf">
						<div class="items_pic ">
							<a href="${base}/detail/${itemReport.itemId?c}.htm">
								<img src="${imageServer!""}${itemReport.picUrl!""}" width="56" height="56"/>
							</a>
						</div>
						<div class="items_name_value">
							<a href="${base}/detail/${itemReport.itemId?c}.htm" class="items_name">${itemReport.title!""}</a>
							<span class="items_value"></span>
						</div>
					</div>
				</td>
				<td>${itemReport.itemSum?c}</td>
				<td>${itemReport.amountByYuan!""}</td>
			</tr>
			</#list>
			</#if>
			</tbody>
		</table>
	</div>	
	 	<input type="hidden" name="${tokenName}" value="${token}">
		<input type="hidden" name="sellReportQuery.page" id="currPage" value="${sellReportQuery.page}">
		<input type="hidden" name="" id="pages" value="${sellReportQuery.pages}">
		<input type="hidden" name="sellReportQuery.orderByKey"  value="${sellReportQuery.orderByKey}" id="sortKey">
		<input type="hidden" name="sellReportQuery.sort" value="${sellReportQuery.sort}" id="orderStatus">
		<input type="hidden" name="sellReportQuery.itemsPerPage" value="20"/>
		<input type="hidden" name="sellReportQuery.status">
	<a class="get_table_but" href="#" onclick="importCSV();">导出电子表格</a>
	<div id="Pagination" class="pagination"></div>
	
	<#else>
	<div class="err_style">没有符合条件的数据！</div>
	</#if>
  </form>
</div>
<input type="hidden" value="simpleReport-manager" id="my-page" />
<script>
	var _val=$("select[name='sellReportQuery.dateCode']").attr("selVaule");
	$("select[name='sellReportQuery.dateCode']").val(_val);
</script>