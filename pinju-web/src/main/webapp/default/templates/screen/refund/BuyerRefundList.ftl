<#setting classic_compatible=true>
<SCRIPT src="http://static.pinju.com/js/refund/BuyerRefundList.js"></SCRIPT>
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<script src="${base}/default/js/datePicker/WdatePicker.js"></script>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<link href="http://static.pinju.com/css/sell.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/page/pagination.css" rel="stylesheet" />
<title>品聚网-退款管理</title>

<!--内容页面开始-->
<form id="form1" method="post" action="">
<div class="main062702">
  <div class="positionbanner t4"> 您的位置：
  <a href="${base}/orderBuyer/myBuyer.htm" class="mr10">我是买家</a>&gt;
  &nbsp;<span class="red">退款管理</span></div><!--顶部引导条结束 -->
  
  
<ul class="t2 paddingbot5">
<li style="padding:12px 0 0 8px">
	订单编号：
		<input type="text" maxlength="100" name="orderId" value="${orderId}"/>
		从
		<input type="text" name="beginDate" value="${beginDate!""}"  style="width:160px"class="Wdate" id="beginDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		到
	    <input type="text" name="endDate" id="endDate" value="${endDate!""}" style="width:160px" class="Wdate"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
</li>
 <li style="padding:12px 0 0 8px">
  	退款编号：
  	<input type="text" maxlength="100" name="refundId" value="${refundId!""}"/>　
           退款状态：
      <select name="seltState" id="seltState" onchange="javascript:$('#form1').submit();">
           <option value="all">全部</option>
           <option value="1">等待卖家处理</option>
           <option value="2">等待买家退还商品</option>
           <option value="3">等待卖家确认收货</option>
           <option value="6">卖家拒绝退款</option>
           <option value="7">客服仲裁</option>
           <option value="5">退款成功</option>
           <option value="4">退款关闭</option>
           <option value="8">退款失败</option>
           <option value="9">退款协议达成</option>
      </select>　　
　　<span class="imgbtn"><button type="button" class="btn-sgray" onclick="$('#form1').submit();">搜索</button>

  </li></br>
</ul>
<ul class="tab-nav"><li id="tg1"><a href="${base}/refund/sellerRefundList.htm">我收到的退款(${sellerRefundCount!"0"})</a></li><li id="tg2" class="selected"><a href="${base}/refund/buyerRefundList.htm" >我申请的退款(${paginator.items!"0"})</a></li></ul><!--状态信息结束 -->

  <table class="sel-tb1 a" border="0" cellpadding="0" cellspacing="0" style="nowrap:nowrap;" width="100%">
  <tr class="pj-gray">
    <th width="9%">退款编号</th>
    <th width="15%"></th>
    <th width="20%">订单编号/商品信息</th>
    <th width="8%">卖家</th>
    <th width="7%">交易金额</th>
    <th width="7%">退款金额</th>
    <th width="9%">申请时间</th>
    <th width="9%">超时时间</th>
    <th width="11%">退款状态</th>
    <th width="5%">操作</th>
  </tr>
  <tr>
  <td colspan="9">&nbsp;</td>
  </tr>
  
<#if refundList?exists>
  <#if (refundList?size>0) >
  <#list refundList as refund>
  <#assign itemId = orderItemMap.get(refund.orderItemId).itemId >
  <#assign propkey = 'key' + (itemId) + orderItemMap.get(refund.orderItemId).itemSkuId>
  
  <tr class="center">
    <td>${refund.id.toString()}</td>
   <td width="auto" height="115px" class="center">
	    	<div class="floatleft">
	    		<div class="items_pic_box"><a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemMap.get(refund.orderItemId).snapId}"><img src="${imageServer}${itemMap.get(itemId).picUrl}" width="80px" height="80px" /></a></div>
	    	</div>
	    </td>
	    <td class="center">
	    	<p class="w145">${refund.orderId?c}</p>
	    	<p class="w145"><a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemMap.get(refund.orderItemId).snapId}">${itemMap.get(itemId).title?default("")?html}</a></p>
	       		<p class="hui w145">
	       			<#if itemPropertyMap[propkey]?exists>
                    	
        				<#list itemPropertyMap[propkey] as itemProperty>
        				${itemProperty.name?default("")?html}：${itemProperty.value?default("")?html}&nbsp;&nbsp;&nbsp;
        				</#list>
        				
        			</#if>
        	</p>
    </td>
    <td>${refund.sellerNick}</td>
    <td class="hui">￥${refund.tradeSumByYuan!"0.00"}</td>
    <td class="hui">￥${refund.applySumByYuan!"0.00"}</td>
    <td>${refund.applyDate?datetime}</td>
    <td>
    
    	${timesOut.get(refund.id)?default(refund.gmtModified)?datetime}
    
    </td>
    <td><p class="hong">${refund.refundStateDisplay}</p></td>
    <td>
    <#if (refund.refundState =5)>
    	<a href="buyerViewRefundSuccess.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
    <#elseif (refund.refundState =2)>
    	<a href="buyerViewWaitGoodsReturn.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
    <#elseif (refund.refundState =3)>
    	<a href="buyerWaitSellerDeliveryGoods.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
    <#elseif (refund.refundState =4)>
     	<a href="buyerViewRefundClosed.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
     <#elseif (refund.refundState =6)>
      	<a href="buyerViewSellerRefuseRefund.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
     <#elseif (refund.refundState = 7)>
     	<a href="buyerViewCustProcessRefund.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
     <#elseif (refund.refundState = 1)>
     	<a href="checkApply.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
     <#elseif (refund.refundState = 8)>
     	<a href="buyerViewRefundFail.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
     <#elseif (refund.refundState = 9)>
     	<a href="buyerViewRefundProtocalAgree.htm?id=${refund.id}" class="lan" target="_blank">查看</a>
    </#if>
    </td>
  </tr>
  </#list>
  <tr>
    <td colspan="9"><div id="Pagination" class="pagination" style="float:right;width=40px;"></div></td>
  </tr>
  <#else>
    	<tr><td colspan="7" align='center'><label class="hong">没有找到符合条件的退款信息！</label></td></tr>
  </#if>
  <#else>
 	<tr heigth="40px"><td colspan="7" align='center'> 没有记录</td></tr>
</#if>
</table><!--订单详细信息结束 -->
</div>

  <input type="hidden" name="paginator.page" id="currPage" value="${paginator.page}">
  <input type="hidden" id="pages" value="${paginator.pages}">
  <input type="hidden" name="paginator.itemsPerPage" value="10"/>
</form>

<script language="JavaScript" type="text/javascript">
	$('#seltState').val('${seltState!"all"}');
</script>

 <input type="hidden" value="buyer-refund-management" id="my-page" />