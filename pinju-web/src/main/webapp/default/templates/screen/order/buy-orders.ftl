<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/buy_sell_ui.css" type="text/css" media="screen" rel="stylesheet" />
<link href="http://static.pinju.com/css/page/pagination.css" type="text/css" media="screen" rel="stylesheet" />  
<script type="text/javascript" src="http://static.pinju.com/js/jquery.pagination.js"></script> 
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${base}/default/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/buyerOrders.js?t=20111214.js"></script>
<title>品聚网</title>

 <div class="buy_sell_box">
 
	 <div class="sell_tx_box">您的位置：
       <a href="http://www.pinju.com/orderBuyer/myBuyer.htm" class="mr10">我是买家</a>> 
       	<span class="red">已买到的商品</span>
	 </div>
 
<form action="${base}/orderBuyer/orderManager.htm" method="POST" id="searchForm" >
<input type="hidden" value="Orderbuy-tool" id="my-page" />
	<input type="hidden" id="refundState" name="refundState"/>
	<input type="hidden" name="query.page" id="currPage" value="${query.page}">
  	<input type="hidden" id="pages" value="${query.pages}">
  	<input type="hidden" name="query.itemsPerPage" value="10"/>
  	<input type="hidden" name="searchTy" value="1" />
  	
  	
	<ul class="order_tab_list cf ">
    	<li class="cur"><a href="#">全部订单</a></li>
    </ul>
    
    
    <div class="sell_search_box imgbtn">
		 <p>
		 	起始时间：<input type="text" name="startDate" value="${startDate!""}" id="startDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;截止时间：&nbsp;<input type="text" name="endDate" id="endDate" value="${endDate!""}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		 </p>
		  <p>卖家昵称：<input type="text" class="" name="sellerNick" value="${(sellerNick!"")?html}"/>
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单状态：
		  	<select name="orderItemState" id="orderItemState">
 				<option value="">全部订单</option>
          		<option value="1" <#if orderItemState=="1">selected</#if>>等待买家付款</option>
    			<option value="2" <#if orderItemState=="2">selected</#if>>买家已付款</option>
    			<option value="3" <#if orderItemState=="3">selected</#if>>卖家已发货</option>
    			<option value="4" <#if orderItemState=="4">selected</#if>>退款中的订单</option>
    			<option value="5" <#if orderItemState=="5">selected</#if>>交易成功</option>
    			<option value="6" <#if orderItemState=="6">selected</#if>>交易关闭</option>
 			</select>  
		  </p>
		  <p>
		           订单编号：<input type="text" name="cancelOrderId" value="${(cancelOrderId!"")?html}" class=""/> &nbsp;&nbsp;&nbsp;&nbsp;
		           评价状态：
		           <select name="isBuyerRate" id="isBuyerRate">(评价系统未实现)
	    			<option value="">全部</option>
	    			<option value="1" <#if isBuyerRate== 1>selected</#if>>已评</option>
	    			<option value="0" <#if isBuyerRate== 0>selected</#if>>未评</option>
	    		</select>
				  </p>
				<p>
		        	商品名称：<input size="62" class="" type="text" name="itemTitle" id="itemTitle" value="${(itemTitle!"")?html}" />&nbsp;&nbsp;
		        	<button type="button" class="btn-sorange" onclick="searchByItemTitle()">搜 索</button>
		        </p>
	  </div>
	
</form>  

 <form action="${base}/orderPay/pay.htm" method="post" id="mergepayFrom">
   		<input type="hidden" id="type" name ="type" value="1"/>
   		<input type="hidden" name="${tokenName}" value="${token}">
   	
 	<div class="order_box">
      <table class="order_table">
          <tr>
              <th class="order_bar1">商品	</th>
              <th class="order_bar2">单价(元)</th>
              <th class="order_bar3">数量</th>
              <th class="order_bar4">售后</th>  
              <th class="order_bar5">实付款(元)</th>
              <th class="order_bar6">
              	<select name="orderState" id="orderState" onchange="changeState()">
              		<option value="">全部订单</option>
              		<option value="1" <#if orderItemState=="1">selected</#if>>等待买家付款</option>
	    			<option value="2" <#if orderItemState=="2">selected</#if>>买家已付款</option>
	    			<option value="3" <#if orderItemState=="3">selected</#if>>卖家已发货</option>
	    			<option value="4" <#if orderItemState=="4">selected</#if>>退款中的订单</option>
	    			<option value="5" <#if orderItemState=="5">selected</#if>>交易成功</option>
	    			<option value="6" <#if orderItemState=="6">selected</#if>>交易关闭</option>
              	</select>
              </th>
              <th class="order_bar7">操作</th>
     	 </tr>
      </table>
   </div>
<#if orderAllList?exists>
<#if orderAllList.size() &gt; 0>
	<div class="select_all imgbtn">
		<input id="orderIdAlla" type="checkbox" value="" class="noinput" onclick="checkalls(this.checked,'orderId');"/> 全选
		<input name="sub" type="button" value="合并付款" onclick="allPay('orderId');" />
	</div>
	
	<#list orderAllList as orderAll> 
	<div class="item_list_box">
     	<table class="item_table">
			
       		<tbody>
                <tr class="item_order">
                  <td colspan="8" class="">
                       <div class="order_num">
                          <span><input name="orderId" checkName="orderId" type="checkbox" value="${orderAll.orderDO.orderId}" class="noinput"  <#if orderAll.orderDO.orderState != 1>disabled</#if>/></span> 
                          <span>订单编号：<a target="_blank" href="${base}/orderSeller/orderinfoBuy.htm?oid=${orderAll.orderDO.orderId}">${orderAll.orderDO.orderId}</a></span>
                          
                      </div>
                      <span class="order_time">成交时间：${orderAll.orderDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</span>
                      <span class="shop_info">店铺：<a target="_blank" href="http://shop${orderAll.orderDO.shopId!""}.pinju.com">${(orderAll.orderDO.shopName!"")?html}</a>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      		店主： <a target="_blank" href="http://sns.pinju.com/${orderAll.orderDO.sellerId!""}">${(orderAll.orderDO.sellerNick!"")?html}</a> 
                      </span>
                  </td>
                </tr>

                <#list orderAll.orderItemList as orderItemDO>
                <#assign propkey="">
                <#assign propkey = orderAll.orderDO.countDown(orderItemDO.bussinessType,orderAll.logisticsDO.consignmentMode)>
	                <tr>
	                  <td class="order_bar1 pt20 nobor">
	                        <div class="items">
	                        	<div class="items_pic fl">
	                        		<#if orderItemDO.snapId == 0>
	                        			<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm">
		                                	<img width="84px" height="84px" src="${imageServer!""}${orderItemDO.itemPicUrl}" />
		                                </a>
	                        		<#else>
	                        			<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}">
		                                	<img width="84px" height="84px" src="${imageServer!""}${orderItemDO.itemPicUrl}" />
		                                </a>
	                        		</#if>
	                            	
	                            </div>
	                        	<div class="items_txt fl">
	                        		<#if orderItemDO.snapId == 0>
	                        			<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm">${(orderItemDO.itemTitle!"")?html}</a><br />
	                        		<#else>
	                        			<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}">${(orderItemDO.itemTitle!"")?html}</a><br />
	                        		</#if>
	                            	<span class="lh20 col1">
	                            		<#if orderItemDO.itemSkuAttributes != "0">
	                            			${(orderItemDO.itemSkuAttributes!"")?html}
	                            		</#if>
	                            	</span>
	                            </div>
	                        </div>
	                  </td>
	                  <td class="order_bar2 pt20 nobor">
	                  	${orderItemDO.originalPriceByYuan!""}
	                  </td>
	                  <td class="order_bar3 pt20 nobor col3">${orderItemDO.buyNum}</td>
	                  <td class="order_bar4 pt20">
	                 
	                 
	                  <#if orderItemDO.isRefund()>
		                  	<#if propkey == "交易关闭" || propkey == "交易成功">
	                  			 
	                  		<#else>
	                  			<span>
									<a target="_blank" href = "${base}/refund/apply.htm?oid=${orderItemDO.orderItemId}" > 申请退款 </a>
								</span>
	                  		</#if>
							
						</#if>
						<#if orderAll.orderDO.orderState==5>
							<span>
								<a target="_blank" href="${base}/rights/showApplyRights.htm?orderItemId=${orderItemDO.orderItemId}" onclick="return rightsComplaintFn(this,${orderItemDO.orderItemId})">售后维权</a>
							</span>
					    </#if>
						
						<#if orderItemDO.refundState == 1>
								<span>
									<a target="_blank" href = "${base}/refund/checkApply.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
								</span>	
								
						<#elseif orderItemDO.refundState == 2>
							<span>
								<a target="_blank" href = "${base}/refund/buyerViewWaitGoodsReturn.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
							</span>	
				
						<#elseif orderItemDO.refundState == 3>
								<span >
									<a target="_blank" href = "${base}/refund/buyerWaitSellerDeliveryGoods.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
								</span>	
								
						<#elseif orderItemDO.refundState == 4>
								<span >
									<a target="_blank" href="${base}/refund/buyerViewRefundClosed.htm?oid=${orderItemDO.orderItemId}">${orderItemDO.getRefundStateDisplay()}</a>
								</span>			
						<#elseif orderItemDO.refundState == 5>
								<span>
									<a target="_blank" href = "${base}/refund/buyerViewRefundSuccess.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
								</span>	
	   
						<#elseif orderItemDO.refundState == 6>
								<span>
									<a target="_blank" href = "${base}/refund/buyerViewSellerRefuseRefund.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
								</span>
							
						<#elseif orderItemDO.refundState == 7>
								<span >
									<a target="_blank" href = "${base}/refund/buyerViewCustProcessRefund.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
								</span>	
								
						<#elseif orderItemDO.refundState == 8>
								<span >
									<a target="_blank" href = "${base}/refund/buyerViewRefundFail.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
								</span>	
								
						<#elseif orderItemDO.refundState == 9>
								<span >
									<a target="_blank" href = "${base}/refund/buyerViewRefundProtocalAgree.htm?oid=${orderItemDO.orderItemId}" >${orderItemDO.getRefundStateDisplay()}</a>
								</span>	
						</#if>
						
	                  </td> 
		              		<#if orderItemDO_index+1==1>
		                  		<td class="order_bar5 pt20" rowspan="${orderAll.orderItemList.size()}">
				                  	<span class="bd">${orderAll.orderDO.priceAmountByYuan}</span>
				                  	<#if orderAll.logisticsDO??>
				                    	<#if orderAll.logisticsDO.consignmentMode!="4">
				                    		 <span class="col1">(含运费:${orderAll.logisticsDO.postPriceByYuan} 元)</span>
					                    <#else>
					                    	 <span class="col1">卖家承担运费</span>
					                    </#if>
				                    </#if>
			                  </td>
			                  <td class="order_bar6 pt20 imgbtn" rowspan="${orderAll.orderItemList.size()}">
			                 	
			                  		<#if propkey == "交易关闭" || propkey == "交易成功">
			                  			 <p class="hong">${propkey}</p>
			                  		<#else>
			                  			 <p class="hong">${orderAll.orderDO.getOrderStateDisplay()}</p>
			                  		</#if>
			                  		
			                  		<#if orderAll.orderDO.orderState == 3 || orderAll.orderDO.orderState == 5>
				 						<a  target="_blank" href="${base}/orderSeller/logisticsInfoBuyer.htm?cd=${(orderAll.logisticsDO.outLogisticsId!"")?html}&exp=${(orderAll.logisticsDO.logisticsType!"")?html}&orderId=${orderAll.orderDO.orderId}">查看物流</a>
				          			</#if>
				          			<a target="_blank" href="${base}/orderSeller/orderinfoBuy.htm?oid=${orderAll.orderDO.orderId}">订单详情</a>
			                  </td>
			                  <td class="order_bar7 pt20 nobor imgbtn" rowspan="${orderAll.orderItemList.size()}">
				                   <#if orderAll.orderDO.orderState == 1>
			                   		    <#if propkey == "等待买家付款">
			                   		    	<button type="button" class="btn-sorange" onclick="singlePay('${orderAll.orderDO.orderId}')" >付  款</button>
								          	<p style="padding-top:5px;">
								          		<a href="javascript:cancel('${orderAll.orderDO.orderId}')">取消订单</a>
								          	</p>							        	
					                  	</#if>
							      </#if>
							          
						         <#if orderAll.orderDO.orderState == 3>
						         	<#if propkey == "卖家已发货">
						         	<button type="button" class="btn-sorange" onclick="checkOrderItemState('${orderAll.orderDO.orderId}','${orderAll.orderDO.isRefund}')">确认收货</button>
							        </#if>
						          </#if>
						     	  <#if orderAll.orderDO.isBuyerRate()>
						         	<p style="padding-top:5px;"><a target="_blank" href="${base}/rate/buyer.htm?oid=${orderAll.orderDO.orderId}"  >订单评价</a></p>
						          </#if>
						          
			                  </td>
		              </#if>
	                </tr>
	             </#list> 
            </tbody>
           </table>
          </div>
  	</#list>
  	   
  <div class="item_list_box">
     	<table class="item_table">
       		<tbody>
                <tr class="item_order">
                  <td colspan="8" class="">
                       <div class="order_num">
                       		<span>
                       			<input id="orderIdAllb" type="checkbox" value="" class="noinput" onclick="checkalls(this.checked,'orderId');"/> 全选
     							<input name="sub" type="button" value="合并付款" onclick="allPay('orderId');" />
                       		</span>
                       </div>
                  </td>
                </tr>
             </tbody>
          </table>
   </div>

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
</#if>
 </form>
 <div id="Pagination" class="pagination"></div>
  </div>
  	
 </div>


 <div class="cella"></div>
</div>
 <div id="cancelBat"  style="height:100px;display:none;">
 <p>
 您确定要取消该订单吗？关闭订单后，不能恢复。
 </p>
 <p>
 请选择关闭订单的理由：
 </P>
  <p>
   <form action="" method="" id="cancelFrom">
 		<select name="failReason">
 				<option value="NOSELECT">请选择关闭理由</option>
    			<option value="1">我不想买了</option>
    			<option value="2">信息填写错误,重新拍</option>
    			<option value="3">卖家缺货</option>
    			<option value="4">同城见面交易</option>
    			<option value="5">其它原因</option>
    	</select>
    	<p>
    		<input type="hidden" id="cancelOrderId" name ="cancelOrderId" />
    		<input type="button" value=" 确定 " onclick="cancel();" />
    		<input type="reset" value=" 取消 " />
    	</p>
    </form>
   </P>
 </div>
