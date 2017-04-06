<#setting classic_compatible=true>
<script type="text/javascript" src="http://static.pinju.com/js/util.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/pcd.js?t=20111013"></script>
<script type="text/javascript" src="http://static.pinju.com/js/linkage-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/orderBase.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/LastTimeSettling-min.js?t=20111212.js"></script>


<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/rao/buy.css" rel="stylesheet" type="text/css" media="screen" />

<title>品聚网</title>

<div class="buy">
<div class="buy-nav" style="margin-top:10px;text-indent: 12px"><a class="help" href="http://service.pinju.com" target="_blank"></a>您的位置：<a href="http://www.pinju.com" style="color: #333333;">首页</a> &gt; <a href="http://www.pinju.com/my/info.htm" style="color: #333333;">我的品聚</a> &gt; <span style="color: #BE0000;">确认订单信息</span></div>
<ul class="buy-title">
<li class="step1 step1c">1.确认订单信息</li>
<li class="step2">2.付款到担保账户</li>
<li class="step2">3.确认收货</li>
<li class="step3">4.评价商品</li>
</ul>


<div class="buy-left">
<h3>商品信息</h3>
<ul class="goods">
<li><a href="${base}/detail/${immediatelySellCreation.itemDO.id?c}.htm" title="点击此处可以查看商品详情" target="_blank"><img style="width:129px !important; height:129px !important;" src="${imageServer}${immediatelySellCreation.itemDO.picUrl}"/></a></li>
<li><span class="left">商品名称：</span><span class="right" title="商品名称"><a href="${base}/detail/${immediatelySellCreation.itemDO.id?c}.htm" title="点击此处可以查看商品详情" target="_blank"><font class="lan">${(immediatelySellCreation.itemDO.title)?html}</font></a></span></li>
<li><span class="left">商品单价：</span><span class="right" title="商品单价">${immediatelySellCreation.getRatePrice()}元</span></li>

<#if immediatelySellCreation.skuAttributes??>
	<li><span class="left">购买信息：</span><span class="right">${(immediatelySellCreation.skuAttributes)?html}</span></li>
</#if>

<li><span class="left">付款方式：</span><span class="right" title="财付通">财付通</span></li>
<li><span class="left">所在地：</span><span class="right" title="商品所在地"><#if immediatelySellCreation.itemDO.provinces == immediatelySellCreation.itemDO.city>${immediatelySellCreation.itemDO.provinces}<#elseif immediatelySellCreation.itemDO.provinces != immediatelySellCreation.itemDO.city>${immediatelySellCreation.itemDO.provinces}${immediatelySellCreation.itemDO.city}</#if></span></li>
<li><span class="left">卖家：</span><span class="right">${(immediatelySellCreation.sellerMember.nickname)?html}</span></li>
<input type="hidden" id="TemplateId" value="${immediatelySellCreation.itemDO.freeTemplateId}"/>
<input type="hidden" id="mail" value="${immediatelySellCreation.itemDO.mailCosts}"/>
<input type="hidden" id="delivery" value="${immediatelySellCreation.itemDO.deliveryCosts}"/>
<input type="hidden" id="ems" value="${immediatelySellCreation.itemDO.emsCosts}"/>
<input type="hidden" id="" value="${immediatelySellCreation.itemDO.id}"/>
<input type="hidden" id="shopId" value="${immediatelySellCreation.shopId}">
</ul> 
</div>

<div class="buy-right">
<h3 style="font-weight:bold">确认收货地址</h3>
<div class="line">
<div class="line-hong"></div></div>
 <ul class="address">
 
<div id="myTab">
 <#list immediatelySellCreation.buyMemberDeliveryList as memberDeliveryDO>
	 <li style="width:97%">
	 	<#if memberDeliveryDO.isDefault()>
			<input name="memberDeliveryId" onclick='radioClickColor()' checkName="${memberDeliveryDO.id.toString()}" areaName="${memberDeliveryDO.province}" areaValue="${memberDeliveryDO.pcdCode}" type="radio" value="${memberDeliveryDO.id.toString()}" checked style="cursor:pointer;" title="选择地址"/>
		<#elseif !memberDeliveryDO.isDefault()>
			<input name="memberDeliveryId" onclick='radioClickColor()' checkName="${memberDeliveryDO.id.toString()}" areaName="${memberDeliveryDO.province}" areaValue="${memberDeliveryDO.pcdCode}" type="radio" value="${memberDeliveryDO.id.toString()}" style="color: blue;cursor:pointer;" title="选择地址"/>
		</#if>
	 	${(memberDeliveryDO.receiverName)?html}&nbsp;&nbsp;&nbsp;&nbsp;${(memberDeliveryDO.province)?html}&nbsp;
		${(memberDeliveryDO.city)?html}&nbsp;${(memberDeliveryDO.district)?html}${(memberDeliveryDO.address)?html}，${memberDeliveryDO.zipCode}，
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
		&nbsp;&nbsp;&nbsp;
		<span id="addressSpan${memberDeliveryDO.id}" style="display:none;">
			<a href="#" onmousedown="editAddress(${memberDeliveryDO.id},${memberDeliveryDO.pcdCode});" class="lan" title="点击后编辑框中进行修改">修改</a>
			<#if memberDeliveryDO.status == 0>
		       	<span>
					<a href="#" class="lan" onmousedown="defAddress(${memberDeliveryDO.id},${memberDeliveryDO.pcdCode});" title="将此条地址设为默认收货地址">
						&nbsp;&nbsp;设为默认
					</a>
				</span>
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
</div>

<li style="width:97%">
	<input type="radio" name="memberDeliveryId" areaValue="add" value="add" id="addNewAddress" onmousedown="addAddress();" style="cursor:pointer;" onclick='radioClickColor()' title="使用新地址">
	<span id="otherAddress">使用新地址</span>
	<span id=countspan class="tip-red" style="margin:2px;display:none">
			收货地址中已有 10 条记录，不能再次增加！&nbsp;&nbsp;&nbsp;<a href="${base}/my/address.htm" style="cursor:pointer;" target="_blank"/><font class="lan">管理收货地址</font></a>
	</span>
</li>
  	
<li style="width:97%;background-color:#f8f8f8;border:0px #f8f8f8 solid;margin-top:5px">
  <form id="editAddFrom" method="post">
    <table id="editAddressTable" editTableName="editAddressTable" style="display:none;margin-left:0px;" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="19%" align="right">收货人姓名<span class="hong">*</span>：</td>
        <td width="87%"><input type="text" value="" memberDelivery="receiverName" name="memberDelivery.receiverName" maxlength="20" style="width:200px" title="请输入收货人的姓名"/></td>
      </tr>
      <tr>
        <td align="right">所在地区<span class="hong">*</span>：</td>
        <td>
          <select id="province" title="请选择送货的地区"></select><span class="hong">*</span>
          <select id="city" title="请选择送货的地区"></select><span class="hong">*</span>
          <select id="district" title="请选择送货的地区"></select>
        </td>
      </tr>
      <tr>
        <td align="right">街道地址<span class="hong">*</span>：</td>
        <td>
        	<textarea cols="49" rows="3" memberDelivery="address" name="memberDelivery.address" style="height: 56px;width: 449px;resize: none;" title="请输入您的送货地址，不需要重复填写省/市/区"></textarea>
        	<span class="hong">不需要重复填写省/市/区</span>
        </td>
      </tr>
      <tr>
        <td align="right">邮政编码<span class="hong">*</span>：</td>
        <td><input type="text" memberDelivery="zipCode" class="pinju-fee" name="memberDelivery.zipCode" style="width:200px" maxlength="6" title="请输入邮政编码"/><span class="beizhu"></span></td>
      </tr>
      <tr>
        <td align="right">电话号码<span class="hong">&nbsp;&nbsp;</span>：</td>
        <td>
        
        	<input type="hidden" memberDelivery="phone" name="memberDelivery.phone"/>
    		<input type="text" id="area" name="phone.area" style="width:39px" maxlength="4" title="请输入收货人的区号"/>
	        - <input type="text" id="tel" name="phone.tel" style="width:94px" maxlength="8" title="请输入收货人的电话号码"/>
	        - <input type="text" id="ext" name="phone.ext" style="width:39px" maxlength="8" title="请输入收货人的分机号"/>
	        
	        
        	<span class="hong"> * 请以（区号-电话号码-分机）的方式填写</span></td>
      </tr>
      <tr>
        <td align="right">手机号码<span class="hong">&nbsp;&nbsp;</span>：</td>
        <td>
        	<input type="text" memberDelivery="mobile" name="memberDelivery.mobile" style="width:200px" maxlength="11" title="请输入收货人的手机号码"/>
        	<span class="hong">电话号码和手机号码请至少填写一项</span>
        	<input type="hidden" memberDelivery="deliveryId" name="memberDelivery.id"/>
            <input type="hidden" name="memberDelivery.province" id="mprovince" memberDelivery="province"/>
            <input type="hidden" name="memberDelivery.city"  id="mcity" memberDelivery="city"/>
            <input type="hidden" name="memberDelivery.district" id="mdistrict" memberDelivery="district"/>
            <input type="hidden" name="memberDelivery.pcdCode" id="mpcdCode" memberDelivery="pcdCode"/>
        </td>
      </tr>
      <tr>
      	<td align="right">设为默认<span class="hong">&nbsp;&nbsp;</span>：</td>
      	<td><input name="memberDelivery.status" type="checkbox" id="statusCheckbox" value="1" title="是否将该地址作为默认的收货地址"/></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
        	<input type="button" value="保 存" onmousedown="submitEditDelivery(1);" class="pl-btn-juse" style="cursor:pointer;" title="保存"/>
        	<input type="button" value="取 消" onmousedown="cancelAddAddress();" class="pl-btn-hui" style="cursor:pointer;" title="取消"/>
        </td>
      </tr>
    </table>
 </form>
</li>

<li class="nopadding" style="width:99%;margin-top:3px;">
<div class="point">请您正确填写您可以收到货物的有效地址，避免因错填或误填收货地址发生损失。</div>
</li>

  </ul>
<form action="${base}/orderBuyer/creationOrder.htm"  method = "post" onsubmit="return subOrder()">
<input type="hidden" name="${tokenName}" value="${token}">
<h3 style="font-weight:bold">确认购买信息</h3>
<div class="line">
<div class="line-hong"></div></div>

<ul class="news" style="width: 100%;">
<li><span class="left">购买数量：</span><span class="hong">*</span>
	<input type="text" id="guoMaiNum" name="orderCreation.num" class="text-amount" style="vertical-align:middle;width:50px" value="${immediatelySellCreation.buyCount}" title="请正确输入购买数量"/>
	<span id="disNum"></span>
	<span id="tipBuyNum" class="tip-red" style="display:none">购买数量不能大于限购数量</span>
</li>
	
<input type="hidden" name="orderCreation.sellerId" value="${immediatelySellCreation.itemDO.sellId}" />
<input type="hidden" name="orderCreation.channelId" value="${immediatelySellCreation.channelId}" />
<input type="hidden" name="xianGuoNum" value="${immediatelySellCreation.lastTimeNum}"/>
<input type="hidden" name="kuCunNum" value="${immediatelySellCreation.itemDO.curStock}"/>
<input type="hidden" id="yiGuoNum" value="${immediatelySellCreation.buyNum}"/>
	
<li><span class="left">运送方式：</span>
  <ul>
    <li>
	    <span class="hong">*</span>
	    <span id="temp"></span>
    </li>
    <span id="tempMethod"></span>
  </ul>
</li>

<li style="height:51px"><span class="left">给卖家留言：</span>
	<textarea id="showText" name="orderCreation.buyerMemo" onpropertychange="if(this.value.length>100){this.value=this.value.slice(0,100)}" maxlength="100" style="width:500px;height:50px;font-size:12px;display:none;resize:none;" onblur="hideTextarea()"></textarea>
	<textarea id="hideText" style="width:500px;height:50px;font-size:12px;color:gray;resize:none;" onclick="showTextarea()">选填，可以告诉卖家您对商品的特殊要求，如：颜色、尺码等</textarea>
</li>
</ul>

<h3 style="font-weight:bold">请选择优惠方案</h3>

<div class="line">
<div class="line-hong"></div>
</div>

<div class="float-left">
	使用商品优惠：
	<select style="width:180px" name="orderCreation.bussinessType" id="selectFavo">
		<option id="disKo" value="200" discount="1" selected>无优惠</option>
	</select>
	<span class="hong" name="buyHong" style="display:none"></span>
	
	</br>
	使用店铺优惠：
	<select style="width:180px;margin-top:2px" name ="orderCreation.couponId" id="shopCouponSelect" onchange="sumPrice()">
		<option value="0" couponMoney ="0">无优惠</option>
	</select>
	<span class="hong" name="shopCouponTip"></span>
</div>

<h3 style="font-weight:bold">确认提交订单</h3>
<div class="line">
<div class="line-hong"></div></div>
<ul class="submit">

<li>
	<input type="checkbox" name="orderCreation.anonymousBuy" value="${immediatelySellCreation.itemDO.id}" title="使用匿名购买" id="anonymousBuy"><label for="anonymousBuy"> 匿名购买</label>
	<input type="hidden" name="orderCreation.orderKey" value="${immediatelySellCreation.itemDO.id}">
</li>

<li>实付款（含运费）：<strong class="cheng">${(immediatelySellCreation.itemDO.priceByYuan)!0}</strong>元</li>

<li style="line-height:33px;">校检码：<span class="hong">*</span>
  <input type="text" id="validateCode" autocomplete=off maxlength=4 style="width:40px" title="请输入图片中的字符（不区分大小写），看不清时可以点击图换一张"/>
  &nbsp;&nbsp;&nbsp;<img src="${base}/e/captcha.htm?sid=${sid?html}" basesrc="${base}/e/captcha.htm?sid=${sid?html}" id="captcha-img" style="cursor:pointer;position:relative; top:10px; " />
  <a href="javascript:void(0);" id="captcha-change" style="position:relative; top:8px;">看不清？ 换一张！</a>
  <input type="hidden" value="${sid?html}" id="sid"/>
<li>
  <input type="hidden" name="itemPrice" value="${immediatelySellCreation.itemDO.price}">
  <input type="hidden" value="${immediatelySellCreation.itemDO.id}" name="orderCreation.itemId" />
  <input type="hidden" value="${immediatelySellCreation.skuId}" name="orderCreation.itemSkuId" />
  <input type="hidden" value="${(immediatelySellCreation.skuAttributes)?html}" name="orderCreation.itemSkuAttributes" />
  <input type="hidden" id="bussinessType" value="${immediatelySellCreation.bussinessType}"/>
  <input type="hidden" id="logisticsPrice" name="orderCreation.logisticsPrice" />
  <input type="hidden" id="logisticsMode" name="orderCreation.logisticsMode"/>
  <input type="hidden" id="memberDeliveryId" name="orderCreation.memberDeliveryId"/>
  <input type="hidden" id="dicountRate" value="${immediatelySellCreation.dicountRate}">
  <input type="hidden" value="${immediatelySellCreation.discountCode}" name="orderCreation.discountCode" >
  <input type="hidden" value="${immediatelySellCreation.ad}" name="orderCreation.ad" >
<li style="margin-top:10px">
<input type="submit" value="我已确认，立即付款" class="btn-juselong" id="btn" title="我已确认，立即付款"/>
</li>
<li>
<span class="point">如价格变动，可以在提交订单后联系卖家修改价格，并查看已买到的商品。</span>
</li>
</ul>
</form>
</div>
</div>
