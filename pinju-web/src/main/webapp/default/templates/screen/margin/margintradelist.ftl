<link rel="stylesheet" href="http://static.pinju.com/css/new/bail.css" type="text/css" media="screen" />

<div class="bail_status">
	<ul class="bail_status_tab cf">
		<li class="focus"><span>品聚消保金交易明细</span></li>
	</ul>
   <div class="bail_status_table_box">
		<table class="bail_status_table">
			<thead>
				<tr>
					<td class="bail_status_td01">交易时间</td>
					<!--
					<td class="bail_status_td03">操作前保证金余额</td>
					<td class="bail_status_td04">操作后保证金余额 </td>
					-->
					<td class="bail_status_td05">支付订单号</td>
					<td class="bail_status_td05">交易金额</td>
					<td class="bail_status_td06">交易类型</td>
				</tr>
			</thead>
			<tbody>
			<#if query??>
				<#if query.marginSellerOrderDOs??>
				<#assign marginSellerOrderDOs = query.marginSellerOrderDOs>
                <#list marginSellerOrderDOs as marginSellerOrderDO>
				<tr>
					<td>${(marginSellerOrderDO.gmtCreate)?string("yyyy-MM-dd HH:mm:ss")}</td>
					<td>${(marginSellerOrderDO.payOrderId)!}</td>
					<td>
						<#if marginSellerOrderDO.operateType =100>
							<span>+<span>
							${(((marginSellerOrderDO.amount)!)/100)?string('0.00')}
						<#elseif marginSellerOrderDO.operateType =200>
							<span>-<span>
							${(((marginSellerOrderDO.amount)!)/100)?string('0.00')}
						</#if>
					</td>
					<td>
						<#if marginSellerOrderDO.operateType =100>充值
						<#elseif marginSellerOrderDO.operateType =200>扣款
						</#if>
					</td>
				</tr>
                </#list>
				</#if>
				<#if query.items !=0>
				<tr>
					<td colspan="4">
						<#if errorMsg ??><font color="red">${errorMsg!}</font>
						<#else>
							<form method="post" name="pageForm" id="pageForm">
								<#include "/default/templates/control/pagination.ftl">
							</form>	
						</#if>
					</td>
				</tr>
				<#else>
				<tr>
					<td colspan="4">
						<font color="red">没有记录</font>
					</td>
				</tr>
				</#if>		
			</#if>
			</tbody>
		</table>
	</div>
</div>
 <input type="hidden" value="mymargin" id="my-page" />
	