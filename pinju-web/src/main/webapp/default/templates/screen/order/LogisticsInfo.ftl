<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/wuliu.css" type="text/css" media="screen" rel="stylesheet" />
<title>物流详情页</title>

<form method="post" id="logisticsForm">
<input type="hidden" name="cd" value="${(orderLogisticsDO.outLogisticsId!"")?html}" />
<input type="hidden" name="exp" value="${(orderLogisticsDO.logisticsType!"")?html}" />
<input type="hidden" name="corpHCode" value="${(logisticsCorpDO.corpHCode!"")?html}" />

<table >
	<tr>
		<td valign="top" bgcolor="#f5f5f5">
			<div class="wl-right">
				<p class="title15">交易订单详情</p>
				<ul class="wl-dingd">
				<li>订单编号：${orderDO.orderId!""}</li>
					<#if orderItemList?exists>
						<#list orderItemList as orderItemDO>
							<li style="text-align:center">
								<#if orderItemDO.snapId == 0>
									<a class="pic" target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm"><img src="${imageServer!""}${orderItemDO.itemPicUrl!""}" width="84px" height="84px" /></a>
								<#else>
									<a class="pic" target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}"><img src="${imageServer!""}${orderItemDO.itemPicUrl!""}" width="84px" height="84px" /></a>
								</#if>
							</li>
							<li>
								商品名称：
								<#if orderItemDO.snapId == 0>
									<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm" class="lan">${(orderItemDO.itemTitle!"")?html}</a>
								<#else>
									<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" class="lan">${(orderItemDO.itemTitle!"")?html}</a>
								</#if>
							</li>
							<li>
								<span class="lh20 col1">
									<#if orderItemDO.itemSkuAttributes?exists>
	                        		<#if orderItemDO.itemSkuAttributes != "0">
	                        			${(orderItemDO.itemSkuAttributes!"")?html}
	                        		</#if>
	                        		</#if>
	                        	</span>
	                        </li>
							<Li>单价：${orderItemDO.originalPriceByYuan!""} 元</Li>
							<li>数量：${orderItemDO.buyNum!""} 件</li>
						</#list>
					</#if>
				</ul>
				</div>
		</td>
		<td style="width:1%"></td>
		<td valign="top" style="width:840px;">
			<div class="wl-left">
				<P class=" title15">物流详情</P>
				<P class="wl-h1">物流信息</P>
				
				<#if orderLogisticsDO.consignmentMode == 5>
					<ul class="wl-chuangj">
						<li>发货方式：自定义物流</li>
						<li>物流编号：${(orderLogisticsDO.orderLogisticsId!"")?html}</li>
						<li>物流公司：${(orderLogisticsDO.logisticsName!"")?html}</li>
						<li>运单号码：${(orderLogisticsDO.outLogisticsId!"")?html}</li>
					</ul>
				<#elseif orderLogisticsDO.consignmentMode == 6>
					<ul class="wl-chuangj">
						<li>发货方式：无需物流</li>
					</ul>
				<#else>	
					<ul class="wl-chuangj">
					<li>发货方式：自己联系</li>
					<li>物流编号：${(orderLogisticsDO.orderLogisticsId!"")?html}</li>
					<li>物流公司：${(logisticsCorpDO.corpName!"")?html}</li>
					<li>运单号码：${(orderLogisticsDO.outLogisticsId!"")?html}</li>
					<li>物流跟踪：
					<span id="logisticsInfoSpan">
					  <li class="left280 top10 hui" id="submitLoad" style="display: line; margin-top:10px;padding-left:85px">
						<img align="absmiddle" src="http://static.pinju.com/images/ajax/loadding.gif"> &nbsp;正在获取物流信息......
					 </li>
					</span>
					</ul>
				</#if>
				<#if orderLogisticsDO.consignmentMode != 6>
					<P class="wl-h1"><strong>收货信息</strong></P>
					<ul class="wl-chuangj">
					<li>${(orderLogisticsDO.prov!"")?html} ${(orderLogisticsDO.city!"")?html} ${(orderLogisticsDO.area!"")?html} ${(orderLogisticsDO.address!"")?html}， ${(orderLogisticsDO.post!"")?html}， ${(orderLogisticsDO.fullName!"")?html}， ${(orderLogisticsDO.phone!"")?html}， ${(orderLogisticsDO.mobilePhone!"")?html} </li>
					</ul>
					<P class="wl-h1"><strong>发货信息</strong></P>
					<ul class="wl-chuangj">
					<li>【<a href="http://shop${orderDO.shopId!""}.pinju.com" class="lan" target="_blank">${(orderDO.shopName!"")?html}</a>】 ${(orderLogisticsDO.sendAddress!"")?html}， ${(orderLogisticsDO.sendPost!"")?html}， ${(orderLogisticsDO.sendName!"")?html}， ${(orderLogisticsDO.sendPhone!"")?html}， ${(orderLogisticsDO.sendMobilePhone!"")?html}</li>
					</ul>
					</div>
				</#if>
		</td>
	</tr>
</table>
</form>
<script>
	$(document).ready(function() {
		var cmode = ${orderLogisticsDO.consignmentMode};
		if(cmode == 5 || cmode == 6){
			return;
		}
		$.ajax({
			data:$('#logisticsForm').serialize(),
			type: "post",
			url: "${base}/orderSeller/logisticsInfoAjax.htm",
			dataType: 'json',
			success: function(data){ 
				$("#submitLoad").hide();
				var showInfo = "<span class='bor-yellow'>！无法获取有效信息，请至 <a target='_blank' href='${(logisticsCorpDO.corpUrl!'')?html}' class='lan'>【${(logisticsCorpDO.corpName!'')?html}】</a>查询</span>";
		    	var logistics = data.root;
		    	if(logistics != null){
		    		var steps =logistics.step;
		    		 if(steps && steps.length !=0 ){
		    		 	 showInfo = "<span class='bor-yellow'>！以下信息由物流公司提供，如有疑问请咨询 <a target='_blank' href='${(logisticsCorpDO.corpUrl!'')?html}' class='lan'>【${(logisticsCorpDO.corpName!'')?html}】</a></span><ul class='wl-genzh'>";
		    		 	
	 	     			for(i = 0; i < steps.length; i++){
	 	     				showInfo +="<li>"+steps[i].datetime+"&nbsp;&nbsp;"+steps[i].remark+"</li>";
	 	     			}
	 	     			showInfo += "</ul>";
	 	     		}
		    	}
	    		$("#logisticsInfoSpan").html(showInfo);		
		    	    	
			} 
		});
	});
</script>


