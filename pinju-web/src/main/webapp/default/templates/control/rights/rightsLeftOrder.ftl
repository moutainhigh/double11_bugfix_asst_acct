            <div class="tuikuanleft"><!--左侧 -->
		    	<div class="boxred202"></div>
                <div class="tuikuanleft-2 ">
					<div class="tuikuanleft19 paddingtb10 t3">
	                    <center><strong>订单详情</strong></center>
	                </div>
					<ul>
						<#if query.orderDO??>
						<#assign orderDO = query.orderDO>
			                <li>订单编号：${orderDO.orderId}</li>
			                <li>成交时间：${orderDO.stateModifyTime?string("yyyy-MM-dd HH-mm-ss")}</li>
			                <li>订单状态：<#if orderDO.orderState=1>未付款
											<#elseif orderDO.orderState=2>已付款
											<#elseif orderDO.orderState=3>已发货
											<#elseif orderDO.orderState=5>交易成功
											<#elseif orderDO.orderState=6>交易关闭
										</#if>
										
							</li>
			                <li>卖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 家：${orderDO.sellerNick}</li>
			                <li>商品总价：<#if totalPrice??> ${totalPrice}<#else>0.00</#if> 元</li>
			                <li>运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 费：<#if query.orderPostPrice??>${query.orderPostPrice}</#if>(元)
							</li>
							<li>订单优惠：${couponMoneyByYuan}元</li>
			                <li>订单总计：${orderDO.priceAmountByYuan}(元)</li>
			                <li>实&nbsp;付&nbsp; 款：${query.realPayMoney}(元)</li>
		                <#else>
		                	${orderErrorMsg}
		                </#if>
            		</ul>
      			</div>
      		</div>