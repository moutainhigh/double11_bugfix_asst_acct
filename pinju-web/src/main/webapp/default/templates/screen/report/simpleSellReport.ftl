<link rel="stylesheet" href="http://static.pinju.com/css/new/sell_report.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/page/pagination.css" media="screen" />
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<script src="${base}/default/js/datePicker/WdatePicker.js"></script>
<script src="http://static.pinju.com/js/util.js"></script>
<script src="http://static.pinju.com/js/report/simpleSellReport.js"></script>
<title>销售简表-销售报表-品聚网</title>

<form id="form1" name="frm" method="post" action="sellReport/simpleReport.htm">
<div class="sell_report cf">
	<div class="report_links sell_tx_box"> 您的位置：
  	<a href="http://www.pinju.com/my/sell.htm">我是卖家</a> > <span>销售报表</span></div>
  	
	<div class="sell_statistics">
		<p class="black bd">简易销售统计</p>
		<p>在这里您可以获得各种时间段里的销售统计，所有销售统计均以订单支付成功为准</p>
		<div id="bytime" <#if flag == "year">style="display:none"</#if>>
			<span class="txt">按</span>
			<select id="sel1" onchange="isShowCondition(this.value)">
				<option value="bytime" selected>时间段</option>
				<option value="byyear">年份</option>
			</select>
			<select id="selDate" name="timegp" onchange="selDateChange(this.value);">
				<option value="0">请选择</option>
				<option value="1" <#if timegp=="1">selected</#if>>昨天</option>
				<option value="3" <#if timegp=="3">selected</#if>>近三天</option>
				<option value="7" <#if timegp=="7">selected</#if>>近一周</option>
				<option value="30" <#if timegp=="30">selected</#if>>近一个月</option>
				<option value="90" <#if timegp=="90">selected</#if>>近三个月</option>
			</select>
			<span class="txt">精确时间</span>
			<input type="text"  class="Wdate" name="startDate" value="${startDate!""}" id="startDate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-90}',maxDate:'%y-%M-{%d-1}'})" readOnly="true" onblur="$('#selDate').attr('value','0')">
			<span class="txt">至</span>
			<input type="text"  class="Wdate" name="endDate" value="${endDate!""}" id="startDate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-90}',maxDate:'%y-%M-{%d-1}'})" readOnly="true" onblur="$('#selDate').attr('value','0')">
			<input class="statistics_but" type="button" value="统计" onclick="isSubmit('day',1)"/>
		</div>
		<div id="byyear" <#if flag == "day">style="display:none"</#if>>
			<span class="txt">按</span>
			<select id="sel2" onchange="isShowCondition(this.value)">
				<option value="bytime">时间段</option>
				<option value="byyear" selected>年份</option>
			</select>
			<input type="text"  class="Wdate" name="year" value="${year!""}" id="startDate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy',minDate:'{%y}'})" readOnly="true">
			<span class="txt">精确到月</span>
			<select name="month">
				<option value="">请选择</option>
				<option value="0" <#if month=="0">selected</#if>>1月</option>
				<option value="1" <#if month=="1">selected</#if>>2月</option>
				<option value="2" <#if month=="2">selected</#if>>3月</option>
				<option value="3" <#if month=="3">selected</#if>>4月</option>
				<option value="4" <#if month=="4">selected</#if>>5月</option>
				<option value="5" <#if month=="5">selected</#if>>6月</option>
				<option value="6" <#if month=="6">selected</#if>>7月</option>
				<option value="7" <#if month=="7">selected</#if>>8月</option>
				<option value="8" <#if month=="8">selected</#if>>9月</option>
				<option value="9" <#if month=="9">selected</#if>>10月</option>
				<option value="10" <#if month=="10">selected</#if>>11月</option>
				<option value="11" <#if month=="11">selected</#if>>12月</option>
			</select>
			<input class="statistics_but" type="button" value="统计" onclick="isSubmit('year',1)"/>
		</div>
	</div>
	<div class="view_box bd">
		您正在查看 <span>${startDate!""}</span>至<span>${endDate!""}</span>的统计数据
	</div>
	
	<div class="report_tab">
		<ul class="report_tab_list cf">
			<li class="focus">销售简表</li>
			<li onclick="window.location.href='/sellReport/hotSellerCategoryStatistics.htm'">热卖分类</li>
			<li onclick="window.location.href='/sellReport/hotSellItemsReportStatistics.htm'">热卖商品</li>
		</ul>
	</div>
	<#if buyNumSum gt 0 >
		<#if buyNumSum != 0>
		<div class="report_data">在此期间，您总计销售了<span>${buyNumSum}</span>件商品，共计<span>${orderItemPriceSum}</span>元</div>
		</#if>
		<div class="report_table_box">
			<table class="report_table">
				<thead>
				<tr>
					<td class="td01">日期</td>
					<#assign buyNumImg="http://static.pinju.com/images/report/order_dis.gif"/>
					<#assign orderItemPriceImg="http://static.pinju.com/images/report/order_dis.gif"/>
					<#if orderField == "sum(buy_num)" && orderStatus =="desc">
						<#assign buyNumImg="http://static.pinju.com/images/report/order_desc.gif"/>
					</#if>
					<#if orderField == "sum(buy_num)" && orderStatus =="asc">
						<#assign buyNumImg="http://static.pinju.com/images/report/order_asc.gif"/>
					</#if>
					<#if orderField == "sum(order_item_price)" && orderStatus =="desc">
						<#assign orderItemPriceImg="http://static.pinju.com/images/report/order_desc.gif"/>
					</#if>
					<#if orderField == "sum(order_item_price)" && orderStatus =="asc">
						<#assign orderItemPriceImg="http://static.pinju.com/images/report/order_asc.gif"/>
					</#if>
					<td class="td02">销售量 <img id="buyNum" src="${buyNumImg}" onclick="orderSubmit('sum(buy_num)');" /></td>
					<td class="td03">销售金额(元) <img id="orderItemPrice" src="${orderItemPriceImg}" onclick="orderSubmit('sum(order_item_price)');"/></td>
				</tr>
				
				</thead>
				<tbody>
				<#if reportList??>
				<#list reportList as ReportQueryDO>
					<#if (flag != "year" && month == "") || (flag == "year" && month != "")>
						<#assign date=ReportQueryDO.stateModifyTime?date("yyyy-MM-dd")/>
					</#if>
					<tr>
						<td>${date!ReportQueryDO.stateModifyTime}</td>
						<td>${ReportQueryDO.buyNumCount}</td>
						<td>${ReportQueryDO.orderItemPriceCount}</td>
					</tr>
				</#list>
				</#if>
				</tbody>
			</table>
		</div>	
		<a class="get_table_but" href="#" onclick="importcsv();">导出电子表格</a>
		<div id="Pagination" class="pagination"></div>
	</#if>
	<#if buyNumSum lte 0>
		<div class="err_style">没有符合条件的数据！</div>
	</#if>
	<input type="hidden" id="flag" name="flag" value="${flag}">
	<input type="hidden" id="orderField" name="orderField" value="${orderField}">
	<input type="hidden" id="orderStatus" name="orderStatus" value="${orderStatus}">
	<input type="hidden" id="pages" value="${paginator.pages}">
	<input type="hidden" name="paginator.page" id="currPage" value="${paginator.page}">
	<input type="hidden" name="paginator.itemsPerPage" value="20"/>
</div>
</form>
<input type="hidden" value="simpleReport-manager" id="my-page" />
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<script src="http://static.pinju.com/artdialog/jquery.iframeTools.js?skin=chrome"></script>
