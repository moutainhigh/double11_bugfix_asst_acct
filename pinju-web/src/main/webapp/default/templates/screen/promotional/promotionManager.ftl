	<title>限时打折活动列表</title>
	<link rel="icon" href="http://static.pinju.com/img/favicon.ico"
	  type="image/x-icon" />
	<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico"
	  type="image/x-icon" />
	<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet"
	  type="text/css" media="screen" />
	<link href="http://static.pinju.com/css/rao/drape.css" rel="stylesheet"
	  type="text/css" media="screen" />
    <script src="${base}/default/js/datePicker/WdatePicker.js"></script>
	<style type="text/css">
	html {
	  margin: 0;
	}
	h3 {font-size: 12px;}
	.buy-nav {
	    background: none repeat scroll 0 0 #FFFFE0;
	    border: 1px solid #F9DFB2;
	    height: 28px;
	    line-height: 28px;
	    margin-bottom: 9px;
	    padding: 0;
	    text-indent: 12px;
	}
	</style>
	
<div class="drape-right">
  	<div class="buy-nav">
     	 您的位置：我是卖家 > 营销中心 > <span><a style="color:red;" href="${base}/active/promotionManager.htm">促销管理</a></span>
    </div>
    <ul class="act">
      <li class="here"><a href="${base}/active/promotionManager.htm">限时打折</a></li>
      <li class=""><a href="/coupon/couponBatchAll.htm">店铺优惠券</a></li>
    </ul>
    <form id="searchForm" method="post" action="${base}/active/promotionManager.htm">
	    <ul>
	      <li style="margin-top: 10px; margin-bottom: 5px;">
	      	<span style=" float:right;">
	      			<a href="http://service.pinju.com/cms/html/2011/bisapp_0921/59.html" title="使用帮助" target="_blank" style="color:#2953a6;">使用帮助</a>
	      		</span>
	      	<input value="创建活动" type="button" 
	      		<#if (((activityDiscountStat.usedTime)!0) = 500) || (((activityDiscountStat.usedNum)!0) == 50)>disabled="disabled" class="pl-btn-hui"
	      		<#else>class="pl-btn-juse"</#if>
	      		onClick="window.location.href='${base}/active/addDiscount.htm'"/>
	        	<#if (((activityDiscountStat.usedTime)!0) = 500) || (((activityDiscountStat.usedNum)!0) == 50)>
	      			<span class="tip">您本月的活动时长或活动数量已用完，无法继续创建限时打折活动</span>
	      		<#else>
	      			<span class="tip">您本月还可以创建总时长为${500-(activityDiscountStat.usedTime)!0}个小时的${50-(activityDiscountStat.usedNum)!0}个限时打折活动</span>
	      		</#if>
	      		
	      </li>	
	      <li>活动状态：
		      		<select name="activityDiscountQuery.status" style="width: 80px;">
						<option value="">请选择</option>
						<option value="0" <#if (query.status)?exists && query.status == 0>selected="selected"</#if>>未开始</option>
						<option value="1" <#if (query.status)?exists && query.status == 1>selected="selected"</#if>>进行中</option>
						<option value="2" <#if (query.status)?exists && query.status == 2>selected="selected"</#if>>已结束</option>
					</select>
	                    活动时间：
	                     从<input class="Wdate" type="text" id="startTime" name="activityDiscountQuery.startTime" value="${((activityDiscountQuery.startTime)?string("yyyy-MM-dd 00:00:00"))!}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})">
			到<input class="Wdate" type="text" id="endTime" name="activityDiscountQuery.endTime" value="${((activityDiscountQuery.endTime)?string("yyyy-MM-dd 23:59:59"))!}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'})"/>
			<input type="submit" value="查询" class="pl-btn-hui"/>
			<#if errorMessage??><span class="tip-red">${errorMessage}</span></#if>
	    </ul>
	    <input type="hidden" value="promotion-manager" id="my-page" />
    </form>
    	<table class="drape-table">
      		<tbody>
		        <tr>
			         <th style="width:250px;">活动名称</th>
			         <th>活动时间</th>
			         <th>时长（小时）</th>
			         <th>商品数</th>
			         <th>状态</th>
			         <th width="80px;">操作</th>
		        </tr>
        		<#if activityDiscountList?exists>
					<#if (activityDiscountList?size>0) >
						<#list activityDiscountList as activityDiscount>
							<#if activityDiscount.status != 3>
							<tr>
							    <td>${(activityDiscount.actName)!?html}</td>
							    <td>
							    	${(activityDiscount.startTime)?string("yyyy-MM-dd HH:mm:ss")} -- ${(activityDiscount.endTime)?string("yyyy-MM-dd HH:mm:ss")}
							    </td>
							    <td>${(durations[activityDiscount_index])!0}</td>
							    <td>${(activityDiscount.itemCount)!}</td>
							    <td>
									<#if activityDiscount.status = 0>未开始
									<#elseif activityDiscount.status = 1>进行中
									<#elseif activityDiscount.status = 2>已结束
									</#if>
								</td>
							    <td>
							    	<#if activityDiscount.status = 0>
							    		<a href="${base}/active/updateActivityDiscount.htm?id=${activityDiscount.id}&pj0=${pj0}" class="lan">修改</a>
							    		<a href="javascript:doDelete(${activityDiscount.id}, '${pj0}');" class="lan">删除</a>
									<#elseif activityDiscount.status = 1>
										<a href="${base}/active/updateActivityDiscount.htm?id=${activityDiscount.id}&pj0=${pj0}" class="lan">修改</a>
									<#elseif activityDiscount.status = 2>
										<a href="javascript:doDelete(${activityDiscount.id}, '${pj0}');" class="lan">删除</a>
									<#else>
										
									</#if>
							    </td>
							</tr>
							</#if>
						</#list>
					<#else>
						<tr>
							<td colspan="6">无对应的查询结果</td>
						</tr>
					</#if>
				</#if>
      		</tbody>
    	</table>
    	<div class="sel-ye">
	      <form action="" method="post" id="pageForm">
	      	<#include "/default/templates/control/pagination.ftl">
	      </form>
    	</div>
  	</div>

<script type="text/javascript">
	function doDelete(id, pj0){
		if(confirm("删除后活动时长不会返还，您确定要删除此活动吗？")){
			document.location.href = "${base}/active/deleteActivityDiscount.htm?id=" + id + "&pj0=" + pj0;
		}
	}
</script>

