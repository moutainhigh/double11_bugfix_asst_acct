<#setting classic_compatible=true>
<#escape x as x?html>
<title>分站数据查询</title>
<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<script src="${base}/default/js/datePicker/WdatePicker.js"></script>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
	select {width: 200px;}
	
	.Wdate {width: 100px;}
	
	.tip {
	    background: none repeat scroll 0 0 #FFFFE0;
	    border: 1px solid #F9DFB2;
	    color: #333333;
	    height: 18px;
	    line-height: 18px;
	    overflow: hidden;
	    padding: 2px 5px;
	}
	
	.tip-red {
	    background-color: #FFE5E6;
	    background-image: url("http://static.pinju.com/images/pot_03.gif");
	    background-position: 5px 5px;
	    background-repeat: no-repeat;
	    border: 1px solid #FF999A;
	    color: #BE0000;
	    height: 18px;
	    line-height: 18px;
	    overflow: hidden;
	    padding: 3px 8px 3px 25px;
	}
</style>
<h1 class="topic"><strong>分站数据查询 </strong></h1>
<div class="sft">
	<div class="Personal">
		<form id="queryForm" name="queryForm" action="${base}/my/city-station.htm" method="post">
		 	<div>
			 	<div>
			 		<span>城市名称：</span>
			 		<span>${(cityName)!}</span>
			 		&nbsp;
			 		<span>起止日期：</span>
			 		<span>
			 			从&nbsp;<input class="Wdate" type="text" id="startDate" name="startDate" value="${(startDate?string("yyyy-MM-dd"))!}" 
			 				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d-2}',isShowClear:false,readOnly:true});$('#tips').hide();">
			 				<!-- minDate:'#F{$dp.$D(\'endDate\',{M:-1});}', -->
						到&nbsp;<input class="Wdate" type="text" id="endDate" name="endDate" value="${(endDate?string("yyyy-MM-dd"))!}" 
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d-2}',isShowClear:false,readOnly:true});$('#tips').hide();"/>
			 		</span>
			 		<span class="imgbtn"><button type="button" id="searchButton" class="btn-sgray">查询</button></span>
			 		<span class="tip-red" style="display:none;" id="tips"></span>
			 	</div>
		 	</div>
		 	<div>
		 		<span class="tip">提示：由于数据库备份时间限制，您只能查询2日前的交易数据且时间跨度不能超过1个月。</span>
		 	</div>
		</form>
	</div>
	<div class="pers-adder">
		<strong class="huang">查询结果</strong>
		<hr/>
		<table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
			<thead>
				<tr class="adder-hd">
					<td width="11%">日期</td>
					<td width="11%">分站名称</td>
					<td width="13%">下单总数量</td>
					<td width="13%">下单总金额</td>
					<td width="13%">付款总数量</td>
					<td width="13%">付款总金额</td>
					<td width="13%">交易成功总数量</td>
					<td width="13%">交易成功总金额</td>
				</tr>
			</thead>
			<#if cityOrderList?exists && (cityOrderList?size > 0)>
				<tbody>
					<#list cityOrderList as cityOrder>
						<tr>
							<td>${(cityOrder.orderDate)?string("yyyy-MM-dd")}</td>
							<td>${(cityName)!}</td>
							<td>${cityOrder.orderBuyCount}</td>
							<td>${(cityOrder.orderBuyPrice/100)?string("0.00")}</td>
							<td>${cityOrder.orderPayCount}</td>
							<td>${(cityOrder.orderPayPrice/100)?string("0.00")}</td>
							<td>${cityOrder.orderSucCount}</td>
							<td>${(cityOrder.orderSucPrice/100)?string("0.00")}</td>
						</tr>
					</#list>
				</tbody>
				<tfoot>
					<tr>
						<td>合计</td>
						<td>${cityOrderList?size}</td>
						<td>${(totalBuyCount)?c}</td>
						<td>${(totalBuyPrice/100)?string("0.00")}</td>
						<td>${(totalPayCount)?c}</td>
						<td>${(totalPayPrice/100)?string("0.00")}</td>
						<td>${(totalSucCount)?c}</td>
						<td>${(totalSucPrice/100)?string("0.00")}</td>
					</tr>
				</tfoot>
			<#else>
				<tfoot><tr><td colspan="8">没有对应的查询结果</td></tr></tfoot>
			</#if>
		</table>
	</div>
</div>
<input type="hidden" value="city-station" id="my-page" />
<script type="text/javascript">
	$("#searchButton").bind("click", function(){
		var startDate = $.trim($("#startDate").val());
		var endDate = $.trim($("#endDate").val());
		if(startDate == ""){
			$("#tips").html("请选择查询开始日期");
			$("#tips").show();
			return;
		}
		if(endDate == ""){
			$("#tips").html("请选择查询结束日期");
			$("#tips").show();
			return;
		}
		if(startDate > endDate){
			$("#tips").html("开始日期不能晚于结束日期");
			$("#tips").show();
			return;
		}
		var start = new Date(Date.parse(startDate.replace(/-/g, "/")));
		var end = new Date(Date.parse(endDate.replace(/-/g, "/")));
		var days = parseInt(Math.abs(end - start) / 1000 / 60 / 60 / 24);
		if(days > 31){
			$("#tips").html("查询时间跨度不能超过1个月");
			$("#tips").show();
			return;
		}
		$("#queryForm").submit();
	});
	
</script>
</#escape>