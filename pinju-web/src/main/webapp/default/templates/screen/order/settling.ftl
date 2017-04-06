<#setting classic_compatible=true>

<LINK href="http://static.pinju.com/css/shopcart/buycart.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/rao/buy.css" rel="stylesheet" type="text/css" media="screen" />

<script type="text/javascript" src="http://static.pinju.com/js/pcd.js?t=20111114.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/linkage-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/orderBase.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/settling-min.js?t=20111130.js"></script>
<title>品聚网</title>

<!--内容页面开始-->
	<div class="buy-nav" style="text-indent: 12px;">您的位置：<a href="http://www.pinju.com" style="color: #333333;">首页</a> &gt; <a href="http://www.pinju.com/my/info.htm" style="color: #333333;">我的品聚</a> &gt; <span style="color: #BE0000;">确认订单信息</span></div>
    <div class="cart_step_nav cf">
		<ul class="buy-title">
			<li class="step1 step1c">1.确认订单信息</li>
			<li class="step2">2.付款到担保账户</li>
			<li class="step2">3.确认收货</li>
			<li class="step3">4.评价商品</li>
		</ul>
	</div>
		
    <div class="cart_container">
		<ul>
        	<li class="title">收货人信息</li>
            <li class="horizen_gray"><span class="horizen_red"></span></li>
    		<li id="myTab">
    			<ul>
			        <#list sellCreation.buyMemberDeliveryList as memberDeliveryDO>
			         <li>
						<#if memberDeliveryDO.isDefault()>
							<input name="memberDeliveryId" onclick='radioClickColor()' checkName="${memberDeliveryDO.id.toString()}" areaValue="${memberDeliveryDO.pcdCode}" type="radio" value="${memberDeliveryDO.id.toString()}" checked style="cursor:pointer;" title="选择地址" />
						<#elseif !memberDeliveryDO.isDefault()>
							<input name="memberDeliveryId" onclick='radioClickColor()' checkName="${memberDeliveryDO.id.toString()}" areaValue="${memberDeliveryDO.pcdCode}" type="radio" value="${memberDeliveryDO.id.toString()}" style="cursor:pointer;" title="选择地址" />
						</#if>
						${(memberDeliveryDO.receiverName)?html}，&nbsp;&nbsp;
						${(memberDeliveryDO.province)?html}，${(memberDeliveryDO.city)?html}，
						${(memberDeliveryDO.district)?html}，${(memberDeliveryDO.address)?html}，
						${memberDeliveryDO.zipCode}，
						<#if memberDeliveryDO.phone != null && memberDeliveryDO.mobile != null>
							${memberDeliveryDO.phone}，${memberDeliveryDO.mobile}
						<#elseif memberDeliveryDO.phone != null && memberDeliveryDO.mobile == null>
							${memberDeliveryDO.phone}
						<#elseif memberDeliveryDO.phone == null && memberDeliveryDO.mobile != null>
							${memberDeliveryDO.mobile}
						</#if>
						<#if memberDeliveryDO.status == 1>
							<span style="color:red">（默认）</span>
						</#if>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="addressSpan${memberDeliveryDO.id}" style="display:none;">
					  			<a href="#" onmousedown="editAddress(${memberDeliveryDO.id},${memberDeliveryDO.pcdCode});" class="lan" title="点击后编辑框中进行修改">修改&nbsp;&nbsp;</a>
							<#if memberDeliveryDO.status == 0>
								<a href="#" class="lan" onmousedown="defAddress(${memberDeliveryDO.id},${memberDeliveryDO.pcdCode});" title="将此条地址设为默认收货地址">
									设为默认
								</a>
							</#if>
						</span>
						<input type="hidden" value="${memberDeliveryDO.zipCode}" hidedenMemberDelivery="${memberDeliveryDO.id+'zipCode'}"/>
						<input type="hidden" value="${(memberDeliveryDO.address)?html}" hidedenMemberDelivery="${memberDeliveryDO.id+'address'}"/>
						<input type="hidden" value="${(memberDeliveryDO.receiverName)?html}" hidedenMemberDelivery="${memberDeliveryDO.id+'receiverName'}"/>
						<input type="hidden" value="${memberDeliveryDO.status}" hidedenMemberDelivery="${memberDeliveryDO.id+'status'}"/>
						<input type="hidden" value="${memberDeliveryDO.phone}" hidedenMemberDelivery="${memberDeliveryDO.id+'phone'}"/>
						<input type="hidden" value="${memberDeliveryDO.mobile}" hidedenMemberDelivery="${memberDeliveryDO.id+'mobile'}"/>
						<input type="hidden" value="${memberDeliveryDO.province}" hidedenMemberDelivery="${memberDeliveryDO.id+'province'}"/>
						<input type="hidden" value="${memberDeliveryDO.city}" hidedenMemberDelivery="${memberDeliveryDO.id+'city'}"/>
						<input type="hidden" value="${memberDeliveryDO.district}" hidedenMemberDelivery="${memberDeliveryDO.id+'district'}"/>
					</li>
			   		</#list>
		   	</ul>
	   	</li>
	   	
	  	 <li class="newAddress">
	  	 	<input name="memberDeliveryId" id="addNewAddress" onclick="radioClickColor()" type="radio" areaValue="add" value="add" onmousedown="addAddress();" style="cursor:pointer" title="使用新地址" / > 
			<span id="otherAddress">使用新地址</span>
			<span id=countspan class="tip-red" style="display:none">
				收货地址中已有 10 条记录，不能再次增加！&nbsp;&nbsp;&nbsp;<a href="${base}/my/address.htm" style="cursor:pointer;" target="_blank"/><font style="color:bule;">管理收货地址</font></a>
			</span>
	  	 </li>
      	 
      	<li>
			<form id="editAddFrom">
			    <table id="editAddressTable" border="0" style="display:none;margin-left:100px;" class="yellow">
			      <tr style="background-color:#f8f8f8;">
			        <td width="20%"align="right">收货人姓名<span class="hong"> *</span>：</td>
			        <td width="89%"><input type="text" value="" memberDelivery="receiverName" name="memberDelivery.receiverName" maxlength="20" title="请输入收货人的姓名"/></td>
			      </tr>
			      <tr style="background-color:#f8f8f8;">
			        <td align="right">所在地区<span class="hong"> *</span>：</td>
			        <td>
			          <select id="province" title="请选择送货的地区"></select><span class="beizhu">*</span>
			          <select id="city" title="请选择送货的地区"></select><span class="hong">*</span>
			          <select id="district" title="请选择送货的地区"></select>
			        </td>
			      </tr>
			      <tr style="background-color:#f8f8f8;">
			        <td align="right">街道地址<span class="hong"> *</span>：</td>
			        <td>
			        	<textarea style="height: 57px;width:480px;resize: none;" cols="50" rows="3" memberDelivery="address" name="memberDelivery.address" title="请输入您的送货地址，不需要重复填写省/市/区"></textarea>
			        	<span class="beizhu">不需要重复填写省/市/区</span>
			        </td>
			      </tr>
			      <tr style="background-color:#f8f8f8;">
			        <td align="right">邮政编码<span class="hong"> *</span>：</td>
			        <td><input type="text" memberDelivery="zipCode" name="memberDelivery.zipCode" maxlength="6" title="请输入邮政编码"/><span class="beizhu"></span></td>
			      </tr>
			      <tr style="background-color:#f8f8f8;">
			        <td align="right">电话号码      ：</td>
			        <td>
			        	<input type="hidden" memberDelivery="phone" name="memberDelivery.phone" style="width:200px;"/>
		        		<input type="text" id="area" name="phone.area" style="width:39px" maxlength="4" title="请输入收货人的区号"/>
				        - <input type="text" id="tel" name="phone.tel" style="width:95px" maxlength="8" title="请输入收货人的电话号码"/>
				        - <input type="text" id="ext" name="phone.ext" style="width:39px" maxlength="8" title="请输入收货人的分机号"/>
			        	
			        	<span class="beizhu">请以（区号-电话号码-分机）的方式填写</span>
			        </td>
			      </tr>
			      <tr style="background-color:#f8f8f8;">
			        <td align="right">手机号码      ：</td>
			        <td>
			        	<input type="text" memberDelivery="mobile" name="memberDelivery.mobile" maxlength="11" title="请输入收货人的手机号码"/>
						<span class="beizhu">电话号码和手机号码请至少填写一项</span>
			        	<input type="hidden" memberDelivery="deliveryId" name="memberDelivery.id"/>
			        	<input type="hidden" name="memberDelivery.province" id="mprovince" memberDelivery="province"/>
			            <input type="hidden" name="memberDelivery.city"  id="mcity" memberDelivery="city"/>
			            <input type="hidden" name="memberDelivery.district" id="mdistrict" memberDelivery="district"/>
			            <input type="hidden" name="memberDelivery.pcdCode" id="mpcdCode" memberDelivery="pcdCode"/>
			        </td>
			      </tr>
			      <tr style="background-color:#f8f8f8;">
			      	<td align="right">设为默认<span class="hong">&nbsp;&nbsp;</span>：</td>
			      	<td><input name="memberDelivery.status" type="checkbox" id="statusCheckbox" value="1" title="是否将该地址作为默认的收货地址"/></td>
      			  </tr>
			      <tr style="background-color:#f8f8f8;">
			        <td>&nbsp;</td>
			        <td>
			        	<input type="button" value="保存" class="orange_button_002" onmousedown="submitEditDelivery(1);" title="保存"/>
			        	<input name="button" type="button" class="gray_button_002 margin_left10" value="取消" onmousedown="cancelAddAddress();" title="取消">
			        </td>
			      </tr>
			    </table>
 			</form>		
 		</li>
 	</ul>
	
	<form action="${base}/orderBuyer/creationOrder.htm"  method = "post" onsubmit="return subOrder();">
	<input type="hidden" name="${tokenName}" value="${token}">
	<ul>
         <li class="title cf">
         	<span class="btn">
         		<input name="button" type="button" class="orange_button_005" value="返回购物车" onmousedown="javaScript:location.href='${base}/cart/shoppingCartDetail.htm'" title="返回购物车" />
         	</span>
         	<h3>确认订单</h3>
		</li>
         <li class="horizen_gray"><span class="horizen_red"></span></li>
          <#if sellCreation.orderMap??>
          <#list sellCreation.orderMap.keySet() as OrderDO>
           <#assign price=0 />
   		  <li class="orderItem">
            <table id="myTable">
              <tr class="bg_gray01">
              	<th colspan="2">店铺：<a href="http://shop${(OrderDO.shopId)!""}.pinju.com" target="_blank" title="点击此处可以查看卖家店铺信息">${(OrderDO.shopName)?html}</a></th>
                <th class="tcenter">单价（元）</th>
                <th class="tcenter">数量</th>
                <th class="tcenter">小计（元）</th>
                <th class="tcenter">匿名购买</th>
              </tr>
	          <!--存放拆单后的购买总数量-->
	          <#assign amount=0/>
	          <#assign commodityid="">
	          <#list sellCreation.orderMap.get(OrderDO) as orderItemDO>
              <tr id ="${orderItemDO.itemId.toString()}" class="mouse">
                <td class="img" align="center">
      		        <input type="hidden" value="${orderItemDO.sellerId}" name="orderCreation.sellerId" />
                	<a href="${base}/detail/${orderItemDO.itemId?c}.htm" title="点击此处可以查看商品详情" target="_blank"><img width="80px" height="80px" src="${imageServer}${orderItemDO.itemPicUrl}" /></a>
                </td>
                <td class="ititle" align="left">
                	<a href="${base}/detail/${orderItemDO.itemId?c}.htm" title="点击此处可以查看商品详情" target="_blank">${(orderItemDO.itemTitle)?html}</a><br>
                	<#if orderItemDO.itemSkuAttributes != 0>
                		${(orderItemDO.itemSkuAttributes)?html}
                	</#if>
                </td>
                <td class="price tcenter">
                	￥${orderItemDO.originalPriceByYuan}
                </td>
                <td class="buynum tcenter">
                	${orderItemDO.buyNum}
                	<input type="hidden" value="${orderItemDO.itemId}" name="orderCreation.itemId" />
    				<input type="hidden" value="${orderItemDO.originalPriceByYuan}" orderCreationPrice="orderCreationPrice"/>
    				<input type="hidden" value="${orderItemDO.buyNum}" name="orderCreation.num" /> 
    				<input type="hidden" value="${orderItemDO.itemSkuId}" name="orderCreation.itemSkuId"  >
    				<input type="hidden" value="${(orderItemDO.itemSkuAttributes)?html}" name="orderCreation.itemSkuAttributes" >
        			<input type="hidden" value="${orderItemDO.bussinessType}" name="orderCreation.bussinessType" >
    				<input type="hidden" value="${orderItemDO.buyNum}" buyNum="buyNum">
					<input type="hidden" value="${orderItemDO.channelId}" name="orderCreation.channelId"/>
                </td>
                <td class="sum tcenter">
                	￥${(orderItemDO.buyNum * orderItemDO.originalPrice/100)?string('0.00')}
                	<#assign price = price + orderItemDO.buyNum * orderItemDO.originalPrice />
                </td>
                <td class="nbuyer tcenter">
                	<input type="checkbox" title="使用匿名购买" value="${orderItemDO.itemId}" name="orderCreation.anonymousBuy">
                </td>
              </tr>
       		<#assign p=orderItemDO />
       		<#assign amount=amount+orderItemDO.buyNum />
       		<#assign commodityid = commodityid + orderItemDO.itemId + ",">
            </#list>
            </table>
        	<#assign param=0 />
              <#if sellCreation.itemDOList??>
               	<#list sellCreation.itemDOList as itemDO>
               	  <#assign param=param+1 />
      		        <#if itemDO.id == p.itemId>
      		        	<div id="myLi">
                  			<input type="hidden" id="paramId" value="${param}" style="width:60px">
            	 			<input type="hidden" id="templateId" value="${itemDO.freeTemplateId}" style="width:60px">
            	 			<input type="hidden" id="postPrice" value="${itemDO.mailCosts}" style="width:60px">
            	 			<input type="hidden" id="deliveryPrice" value="${itemDO.deliveryCosts}" style="width:60px">
            	 			<input type="hidden" id="emsPrice" value="${itemDO.emsCosts}" style="width:60px">
            	 			<input type="hidden" id="shopId" value="${(OrderDO.shopId)!""}" style="width:60px">
            	 			<input type="hidden" id="itemPrice" value="${p.itemId}:${price}">
            	 			<input type="hidden" id="itemId" value="${p.itemId}:${(OrderDO.shopId)}">
        	 			</div>
        	 			<#assign shopID=OrderDO.shopId/>
        	 			<#break>
                    </#if>
              	 </#list>
             </#if>
            
            <div style="text-align:right;margin-top:-6px" id = "myDiv">
            <div style="width:450px; text-align:left; float:left;" >
	            <span>给卖家留言：</span><span><input style="width:300px;" type="text" maxlength = "100" title="给卖家留言"/ name="orderCreation.buyerMemo"></span>
	            <input type="hidden" value="${commodityid?substring(0,commodityid?length-1)}" name="orderCreation.orderKey" style="margin-top:-200px"/>
	            </div>
            
            	店铺优惠：
            	<select style="width:150px;margin-top:2px" name="orderCreation.couponId" id="shopCouponSelect${shopID}" price="${price}" class="shopCoupinClass" data-shop-id="${shopID}" onchange='callCarriage()'>
					<option couponid='0' value='0' couponMoney='0'>无优惠</option>
				</select>
				<span style="color:red;width:80px" name="shopCouponTip"></span>
            </div>
			<div class="cf" style="margin-top:8px;">
	            <div id="mySelect${param}" myselect="myselect" style="float:right;">
	            	<input type="hidden" name="orderCreation.logisticsPrice" id="mySelect${param}Price" />
	            	<input type="hidden" name="orderCreation.logisticsMode" id="mySelect${param}Mode" />
	          		<input type="hidden" value="${amount}" id="mySelect${param}" />
	            </div>
          	</div>
          	
          </li>
          </#list>
 		</#if>
 		
 		<li>&nbsp;</li>
 		<input type="hidden" sendDeliveryId="sendDeliveryId" name="orderCreation.memberDeliveryId" />
    	<li class="right_infor">商品总价（不含运费）：<span class="red" title="商品总价（不含运费）">￥</span><span class="red" id="sumPrice" title="商品总价（不含运费）"> </span>元</li>
	    <li class="right_infor">运费：<span id="carriagePrice" class="red" title="物流总运费">￥</span>元</li>
	    <li class="right_infor">总计：<span class="red">￥</span><span class="red" id="anywayPrice" title="订单总计"></span>元</li>      
	    <li class="right_infor"><input name="button" type="submit" class="orange_button_005" value="确认无误，支付" title="确认无误，支付"></li>
      </ul>
	</form>
	    <input type="hidden" id="sumLogisticsPrice">
	    <input type="hidden" id="sumPriceItem">         
    </div>
    
    <input type="hidden" name="shoppingCartDetail" id="shoppingCartDetail" value="1">