<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/page/pagination.css" type="text/css" media="screen" rel="stylesheet" />  
<link href="http://static.pinju.com/js/wbox/css/wbox.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/buy_sell_ui.css" type="text/css" media="screen" rel="stylesheet"/>

<script type="text/javascript" src="http://static.pinju.com/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${base}/default/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/wbox/wbox.js"></script>
<script type="text/javascript" src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<script type="text/javascript" src="http://static.pinju.com/js/countdown.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/sellManage.js?t=2011121515.js"></script>

<STYLE type="text/css">
	.mywBox {WIDTH: 420px;HEIGHT:100px;}
</STYLE>

<title>品聚网</title>

<div class="sell_left"></div>

<div class="buy_sell_box">
   <div class="sell_tx_box">您的位置：
       <a href="http://www.pinju.com/my/sell.htm" class="mr10">我是卖家</a>>
       	<span class="red">已卖出的商品</span> 
     
   </div>
   <form action="${base}/orderSeller/ordermanagesell.htm" method="post" id="searchForm">
	   <div class="sell_search_box imgbtn">
		 <p>
		 	成交时间：<input type="text" name="beginDate" value="${beginDate!""}" id="beginDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <input type="text" name="endDate" id="endDate" value="${endDate!""}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		 <input type="hidden" id="refundState" name="refundState"/>
		 </p>
		  <p>买家昵称：<input type="text" class="" name="buyerNick" value="${(buyerNick!"")?html}"/>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单状态：
		  	<select name="orderState" id="orderState" style="width:123px">
		  		<option value="">全部订单</option>
		  		<option value="1" <#if orderState == '1'>selected</#if>>等待买家付款</option>
				<option value="2" <#if orderState == '2'>selected</#if>>等待卖家发货</option>
				<option value="3" <#if orderState == '3'>selected</#if>>卖家已发货</option>
				<option value="4" <#if orderState == '4'>selected</#if>>退款中的订单</option> 
				<option value="5" <#if orderState == '5'>selected</#if>>交易成功</option>
				<option value="6" <#if orderState == '6'>selected</#if>>交易关闭</option>
		  	</select>  
		  </p>
		  <p>
		           订单编号：<input type="text" name="orderId" value="${(orderId!"")?html}" class=""/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品名称：&nbsp;<input type="text" name="itemTitle" value="${(itemTitle!"")?html}"/>
		  	&nbsp;&nbsp;<button type="button" class="btn-sorange" onclick="searchOrder()">搜 索</button>
		  	<button type="button" class="btn-sorange" onclick="importCSV()">导出数据</button>
		  </p>
	  </div>
	  
	  <input type="hidden" id="shid" name="shid"/>
	  <input type="hidden" id="shcm" name="shcm"/>
	  <input type="hidden" id="tgState" name="tgState" value="${tgState!""}"/>
	  <input type="hidden" id="pageSSS" name="pageSSS" value="${pageSSS!"1"}">
	  <input type="hidden" name="paginator.page" id="currPage" value="${paginator.page}">
	  <input type="hidden" id="pages" value="${paginator.pages}">
	  <input type="hidden" name="paginator.itemsPerPage" value="20"/>
  </form>
  
<form id="form1" method="post" action="${base}/orderSeller/ordermanagesell.htm">
  <div class="sell_tab_box">
   		<div>
			<ul class="sell_tab_list cf " id="switch_tab_tg">
            	<li liVal="0" id="tg"><a href="javascript:tagSwitch('')">全部订单</a></li>
            	<li liVal="1" id="tg1"><a href="javascript:tagSwitch('1')">等待买家付款</a></li>
                <li liVal="2" id="tg2"><a href="javascript:tagSwitch('2')">等待发货</a></li>
                <li liVal="3" id="tg3"><a href="javascript:tagSwitch('3')">已发货</a></li>
                <li liVal="4" id="tg4"><a href="javascript:tagSwitch('4')">退款中</a></li> 
                <li liVal="6" id="tg6"><a href="javascript:tagSwitch('6')">交易关闭</a></li>
                <li liVal="5" id="tg5"><a href="javascript:tagSwitch('5')">成功订单</a></li>
            </ul>
        </div>
  </div>
  <div class="order_box">
      <table class="order_table">
          <tr>
              <th class="order_bar1">商品	</th>
              <th class="order_bar2">单价(元)</th>
              <th class="order_bar3">数量</th>
              <th class="order_bar4">售后</th>  
              <th class="order_bar5">实付款(元)</th>
              <th class="order_bar6">交易状态 </th>
              <th class="order_bar7">操作</th>
      </tr>
      </table>
   </div>
	<#if orderAllList?exists>
		<#if orderAllList.size() &gt; 0>
			<#list orderAllList as orderAll> 
				<div class="item_list_box">
		       	  <table class="item_table">
		       		<tbody>
		                <tr class="item_order">
		                  <td colspan="8" class="">
		                       <div class="order_num">
		                          <span>订单编号：<a target="_blank" href="${base}/orderSeller/orderinfoSell.htm?oid=${orderAll.orderDO.orderId}">${orderAll.orderDO.orderId}</a></span>
		                      </div>
		                      <span class="order_time">成交时间：${orderAll.orderDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</span>
		                      <span class="shop_info">
		                      		买家：<a target="_blank" href="http://sns.pinju.com/${orderAll.orderDO.buyerId!""}">${(orderAll.orderDO.buyerNick!"")?html}</a>
		                      		<#if orderAll.orderDO.buyerMeMo?exists>
		                      			<img src="http://static.pinju.com/images/sns/fatcow_252.gif" class="user_name" myId="${orderAll.orderDO.orderId}">
		                      			<div class="message_board${orderAll.orderDO.orderId}" style="word-wrap:break-word; line-height:1.5; padding:5px;background-color:#F6F6F6;border:2px solid #C4C4C4;display:none;width:300px;">
											<span><img src="http://static.pinju.com/images/sns/fatcow_252.gif"><span class="bd">买家留言:</span></span>
										   ${(orderAll.orderDO.buyerMeMo!"")?html}
										</div>
		                      		</#if>
		                      </span>
		                      
		                  </td>
		                </tr>
		                <#list orderAll.orderItemList as orderItemDO>
		                <#assign propkey="">
			                <tr>
			                  <td class="order_bar1 pt20 nobor">
			                        <div class="items">
			                        	<div class="items_pic fl">
			                        		<#if orderItemDO.snapId == 0>
			                        			<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm">
				                                	<img src="${imageServer!""}${orderItemDO.itemPicUrl!""}" width="82px" height="82px"/>
				                                </a>
			                        		<#else>
			                        			<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}">
				                                	<img src="${imageServer!""}${orderItemDO.itemPicUrl!""}" width="82px" height="82px"/>
				                                </a>
			                        		</#if>
			                            </div>
			                        	<div class="items_txt fl">
			                        		<#if orderItemDO.snapId == 0>
			                        			<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm">${(orderItemDO.itemTitle!"")?html}</a><br />
			                        		<#else>
			                        			<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}">${(orderItemDO.itemTitle!"")?html}</a><br />
			                        		</#if>
			                            	<#if orderItemDO.itemSkuAttributes?exists>
			                            		<span class="lh20 col1">${(orderItemDO.itemSkuAttributes!"")?html}</span>
			                            	</#if>
			                            </div>
			                        </div>
			                  </td>
			                  <td class="order_bar2 pt20 nobor">
			                  	${orderItemDO.originalPriceByYuan!""}
			                  </td>
			                  <td class="order_bar3 pt20 nobor col3">${orderItemDO.buyNum}</td>
			                   <td class="order_bar4 pt20">
			                  
			                 		<#if orderItemDO.refundState == 1>
				                    	<a target="_blank" href="${base}/refund/sellerCheck.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 2>
				                    	<a target="_blank" href="${base}/refund/sellerViewWaitGoodsReturn.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 3>
				                    	<a target="_blank" href="${base}/refund/sellerViewConfirmGoods.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 4>
				                    	<a target="_blank" href="${base}/refund/sellerViewRefundClosed.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 5>
				                    	<a target="_blank" href="${base}/refund/sellerViewRefundSuccess.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 6>
				                    	<a target="_blank" href="${base}/refund/viewSellerReject.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 7>
				                    	<a target="_blank" href="${base}/refund/sellerViewRefundCustProcess.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 8>
				                    	<a target="_blank" href="${base}/refund/sellerViewRefundFail.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                    <#if orderItemDO.refundState == 9>
				                    	<a target="_blank" href="${base}/refund/sellerViewRefundProtocalAgree.htm?oid=${orderItemDO.orderItemId}" class="lan">${orderItemDO.getRefundStateDisplay()}</a>
				                    </#if>
				                
			                  </td>  
			                  <#assign propkey = orderAll.orderDO.countDown(orderItemDO.bussinessType,orderAll.logisticsDO.consignmentMode)>
	 				   		<#if orderItemDO_index+1==1>
		                  		<td class="order_bar5 pt20" rowspan="${orderAll.orderItemList.size()}">
				                  	<span class="bd">${orderAll.orderDO.priceAmountByYuan}</span>
				                    <#if orderAll.logisticsDO??>
				                    	<#if orderAll.logisticsDO.consignmentMode!="4">
				                   			 <span class="col1">(含运费:${orderAll.logisticsDO.postPriceByYuan} )</span>
				                   		<#else>
				                   			 <span class="col1">卖家承担运费</span>
				                   		</#if>
				                    </#if>
				                    <#if orderAll.orderDO.orderState == 1>
				                    	<#if memberAuth.hasAsstPerm("trade","price")>
					                    	<#if propkey == "交易关闭" || propkey == "交易成功">
					                    	<#else>
					                    		<a href="javascript:modifyPrice('${orderAll.postPay?c}','${orderAll.orderDO.orderId!""}')">修改价格</a>
					                    	</#if>
				                    	</#if>
		                    		</#if>
			                  </td>
			                  <td class="order_bar6 pt20" rowspan="${orderAll.orderItemList.size()}">
		                  		
		                  		<#if propkey == "交易关闭" || propkey == "交易成功">
		                  			 <p class="hong">${propkey}</p>
		                  		<#else>
		                  			 <p class="hong">${orderAll.orderDO.getOrderStateDisplay()}</p>
		                  		</#if>
		                  		
			          			<#if orderAll.orderDO.orderState == 3>
				          			<#if memberAuth.hasAsstPerm("trade","extend-ship")>
				          				<#if propkey == "卖家已发货">
				 							<a href="javascript:delayBuyerReceiveTime('${orderAll.orderDO.orderId.toString()}');">延长收货时间</a>
				 						</#if> 
				 					</#if>
			 						<a target="_blank" href="${base}/orderSeller/logisticsInfoSeller.htm?cd=${(orderAll.logisticsDO.outLogisticsId!"")?html}&exp=${(orderAll.logisticsDO.logisticsType!"")?html}&orderId=${orderAll.orderDO.orderId!""}">查看物流</a>	
			          			</#if>	
			          			<#if orderAll.orderDO.orderState == 5>
			          				<a target="_blank" href="${base}/orderSeller/logisticsInfoSeller.htm?cd=${(orderAll.logisticsDO.outLogisticsId!"")?html}&exp=${(orderAll.logisticsDO.logisticsType!"")?html}&orderId=${orderAll.orderDO.orderId!""}">查看物流</a>
			          			</#if>							
			          			<a target="_blank" href='${base}/orderSeller/orderinfoSell.htm?oid=${orderAll.orderDO.orderId}'>订单详情</a></p><p>
				          	 </td>
			                  <td class="order_bar7 pt20 nobor imgbtn" rowspan="${orderAll.orderItemList.size()}">
			                   	<#if orderAll.orderDO.orderState == 1>
				                   	<#if memberAuth.hasAsstPerm("trade","close")>
				                 		<#if propkey == "等待买家付款">
					                   		<p>
					                   			<a href="javascript:closeSell('${orderAll.orderDO.orderId}')" >取消订单</a>
					                   		</p>
				                 		</#if>
				                 	</#if>
			                   	</#if>
			                   	<#if orderAll.orderDO.orderState == 2>
			                   		<#if memberAuth.hasAsstPerm("trade","shipment") || memberAuth.hasAsstPerm("trade","set")>
			                   			<button type="button" class="btn-sorange" onclick="sendDelivery('${orderAll.orderDO.orderId}')">发 货</button>
			                   		</#if>
			                   	</#if>
			                </td>
				           </#if>
				           
						</tr>
		                </#list> 
		            </tbody>
		           </table>
		   		</div>
			</#list>
			
		<#else>
			<#if itemTitle?exists>
				<div class="item_list_box" align="center">
					<table class="item_table">
						<tr>
							<td style="border:0 none;padding:10px;text-align: center;"><span class="tips-text">没有找到符合条件的订单！</span></td>
						</tr>
					</table>
				</div>
			<#else>
				<div class="item_list_box" align="center">
					<table class="item_table">
						<tr>
							<td style="border:0 none;padding:10px;text-align: center;"><span class="tips-text">暂无订单！</span></td>
						</tr>
					</table>
				</div>
			</#if>
		</#if>
		<div id="Pagination" class="pagination"></div>
	</#if>
   <input type="hidden" value="Orderbuy-tool" id="my-page" />
</form>
 </div>
 
 <div class="cella"></div>