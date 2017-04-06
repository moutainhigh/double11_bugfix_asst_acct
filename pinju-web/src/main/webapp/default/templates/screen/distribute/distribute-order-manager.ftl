<title>爱分享订单</title>
<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<!--right -->
	<div class="right_850px floatright"><!--右侧开始 -->

  	<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01">您好，${userName}，欢迎进入云营销</div>
        
      <div class="box_806 padding_10px ltr_1px_gray bg_white"><span class="fontgray_03">我是<#if type==1>卖<#else>买</#if>家 > 云营销 ></span> <span class="fontred">爱分享订单</span></div>
      <div class="box_806 padding_10px border_1px_orange bg_yellow01 margin_bottom">
	  	<div class="floatleft"><#if type == 1>分销中的商品<#else>已分销的商品</#if><span class="fontred">（${itemCount}） </span></div>
	    <div class="floatleft margin_left20">合作中的<#if type == 1>分销商<#else>爱分享</#if> <span class="fontred">（${distributeCount}） </span></div>
	    <div class="floatleft margin_left20">近三月分销量<span class="fontred"> （${selleredCount!"0"}） </span></div>
	  </div>
        
        
		<div class="box_806 lable_bg_style padding_lr_10px">
	    <div class="lable_present">分销记录</div>
		</div><!--标签 end -->

        <form id="pageForm" name="pageForm" method="post" action="${base}/<#if type == 1>supplier<#else>distributor</#if>/getOrders.htm">	      
        <input type="hidden" name="type" value="${type}" />
        <div class="box_826"> <table width="826" border="0">

              <tr class="fontbold">
                <td width="246">分销商品</td>
                <td width="60">商品价格</td>
                <td width="40">数量</td>
                <!--<td width="60">售后</td>-->
                <td width="60">修改价格</td>
                <td width="60">最终价格</td>
              <td width="80">
              		<select id="statusSelect" name="queryParam.orderState" class="box_width_80px" onchange="query()">
						<option value="">全部订单</option>
						<option value="1">等待买家付款</option>
						<option value="2">等待卖家发货</option>
						<option value="3">卖家已发货</option>
						<option value="4">退款中的订单</option>
						<option value="5">交易成功</option>
						<option value="6">交易关闭</option>
                    </select>
	          </td>
              <td width="80">
              	<select id="memberSelect" name=<#if type==1>"queryParam.channelIds"<#else>"queryParam.supplierId"</#if> class="box_width_80px" onchange="query()">
              		<option value=""><#if type==1>全部分销商<#else>全部店铺</#if></option>
              		<#if (selectList??)>
	              		<#list selectList as item>
		              			<#if type==1>
			      					<#if (item??)>
				                    		<option value="${item.memberId!''}">${item.nickName!''}</option>
			            			</#if>
		                    	<#else>
		                    		<option value="${item.memberId!''}">${(shopInfoMap.get(item.shopId).name)!''}</option>
		                    	</#if>
	            		</#list>
                    </#if>
                </select>
              </td>
              <td width="80">分成状态</td>
              </tr>
			<!-------------商品列表------------------>        
			<#if pageList?exists && (pageList?size > 0)>
  				<#list pageList as item>  
	             <tr class="top_1px_gray">
	                <td colspan="9"></td>
	              </tr>
	              
	      		 <tr class=" bg_gray01 border_1px_gray">
	                <td><div class="floatleft"></div><div class="floatleft margin_top2">&nbsp;&nbsp;订单编号：${item.orderId}</div>
					</td>
	                <td colspan="4">成交时间：${item.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</td>
	                <td colspan="4">顾客：${item.buyerNick}</td>
	              </tr>
	
	              <tr  class="lbr_1px_gray">
	                <td>
	                	<div class="floatleft">
			                <div class="d_goods"><img width="50" height="50" src="${imageServer}${item.itemPicUrl}"></div>
			                <div class="floatleft box_width_150px padding_lr_10px">
			                  <p>
							    <a href="/snapshot/itemSnapshot.htm?arg1=${(item.snapId)!0}" target="_blank">
						          	${item.itemTitle?html}
					          	</a>
					          </p>
			                  <p>&nbsp;</p>
			                  <p class=" fontgray_01">${item.itemSkuAttributes!""}</p>
	               			 </div>
	                	</div>
	              	</td>
	                <td>${(item.originalPrice/100)?string('0.00')}</td>
	                <td>${item.buyNum}</td>
	                <!--<td>投诉维权</td>-->
	                <td>${(item.sellerMarginPriceByYuan)!"0.00"}</td>
	                <td>${((item.orderItemPrice * item.buyNum + (item.sellerMarginPrice)!0 - (item.refundPrice!0))/100)?string('0.00')}</td>
	                <td align="center"><p class="fontred">${item.orderItemStateDisplay}</p>
	                <p><#if type==1><a href="${base}/orderSeller/orderinfoSell.htm?oid=${item.orderId}" target="_blank">订单详情</a></#if></p></td>
	                <td align="center"><#if type==1>${item.channelNickName}<#else>${(shopInfoMap.get(item.shopId).name)!''}</#if></td>
	                <td align="center">
	                <p><#if item.orderItemState==5><p>${(((item.reAmount)!0)/100)?string('0.00')}</p>已分成<#else>未分成</#if></p></td>
	          	  </tr>
	          	</#list>
	          <#else>
	          		<tr><td colspan="7" align='center'><label class="hong">您还没有分销成功的订单！</label></td></tr>
	          </#if>
	          
      </table></div>
      <div class="cbottom imgbtn">
      	<#include "/default/templates/control/bottomPage.ftl">
      </div>
      </form>
      </div><!--分销大框架结束 -->
</div><!--右侧结束 -->

<script type="text/javascript">
	$("#memberSelect").attr("value","<#if type==1>${(queryParam.channelIds!"")?js_string}<#else>${(queryParam.supplierId!"")?js_string}</#if>");
	$("#statusSelect").attr("value","${(queryParam.orderState!"")?js_string}");
	$('#orderManager').addClass("count");
	function query(){
		$("#pageForm").submit();
	}
</script>