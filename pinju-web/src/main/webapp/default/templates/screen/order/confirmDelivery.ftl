<#setting classic_compatible=true>
<LINK href="http://static.pinju.com/css/rao/wuliu.css" rel="stylesheet" />
<script type="text/javascript" src="http://static.pinju.com/js/pcd.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/linkage-min.js"></script>
<script src="http://static.pinju.com/js/jquery.simplemodal.1.4.2.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/order/confirmDelivery.js?t=20120105.js"></script>

<title>品聚网</title>
<div class="wl-main">
<div class="wl-right">
<p class="title15">交易订单详情</p>
<ul class="wl-dingd">
<li>
订单编号：${sendDelivery.orderId}<br/>
</li>
<#list orderMap.keySet() as orderDO> 
<#list orderMap.get(orderDO) as orderItemDO>
<li>
	<div class="imgwrap">
		<a class="pic" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" target="_blank">
			<img src="${imageServer}${orderItemDO.itemPicUrl}" width="84px" height="84px"/>
		</a>
	</div>
</li>
<li>商品名称：<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" class="lan">${orderItemDO.itemTitle?html}</a></li>
<Li>${orderItemDO.itemSkuAttributes}</Li>
<Li>单价：${orderItemDO.originalPriceByYuan}元</Li>
<li>数量：${orderItemDO.buyNum}件</li>
</#list> 
</#list>
<input type="hidden" id="consignmentMode" value="${orderLogisticsDO.consignmentMode}"/>
<input type="hidden"  defMd="defaultmemberDeliveries" value="${deliveriesId}" />
</ul>
</div>
<div class="wl-left">
<P class=" title15">确认发货</P>
<P class="wl-h1"><strong>确认发货信息 </strong></P>

<div id="memberDeliveries">
<ul class="wl-chuangj">
<div id="myTab" style="text-indent: 12px">
	<#list memberDeliveriesList as memberDeliveryDO>
		<li>
	 	<#if memberDeliveryDO.isDefault()>
			<input name="memberDeliveryId" onclick='radioClickColor()' type="radio" value="${memberDeliveryDO.id.toString()}" checked style="cursor:pointer;" title="选择地址"/>
		<#elseif !memberDeliveryDO.isDefault()>
			<input name="memberDeliveryId" onclick='radioClickColor()' type="radio" value="${memberDeliveryDO.id.toString()}" style="color: blue;cursor:pointer;" title="选择地址"/>
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

	<li style="text-indent: 12px">
		<input type="radio" name="memberDeliveryId" areaValue="add" value="add" id="addNewAddress" onmousedown="addAddress();" style="cursor:pointer;" onclick='radioClickColor()' title="使用新地址">
		<span id="otherAddress"><label for="addNewAddress">使用新地址</label></span>
		<span id=countspan class="tip-red" style="margin:2px;display:none">
				发货地址中已有 10 条记录，不能再次增加！&nbsp;&nbsp;&nbsp;<a href="${base}/my/address.htm" style="cursor:pointer;" target="_blank"/><font class="lan">管理发货地址</font></a>
		</span>
	</li>
	
	<li>	
		<form id="editAddFrom">
		    <table id="editAddressTable" border="1px" style="margin-top:5px;display:none;">
		      <tr style="background-color:#f8f8f8;">
		        <td width="20%"align="right">发货人姓名<span class="hong"> *</span>：</td>
		        <td width="89%"><input type="text" value="" memberDelivery="receiverName" name="memberDelivery.receiverName" maxlength="20" style="width:200px" title="请输入收货人的姓名"/></td>
		      </tr>
		      <tr style="background-color:#f8f8f8;">
		        <td align="right">所在地区<span class="hong"> *</span>：</td>
		        <td>
		          <select id="province" title="请选择送货的地区"></select><span class="hong">*</span>
		          <select id="city" title="请选择送货的地区"></select><span class="hong">*</span>
		          <select id="district" title="请选择送货的地区"></select>
		        </td>
		      </tr>
		      <tr style="background-color:#f8f8f8;">
		        <td align="right">街道地址<span class="hong"> *</span>：</td>
		        <td>
		        	<textarea cols="45" rows="2" style="height: 56px;width: 449px;resize: none;" memberDelivery="address" name="memberDelivery.address" title="请输入您的送货地址，不需要重复填写省/市/区"></textarea>
		        	<span class="hong">不需要重复填写省/市/区</span>
		        </td>
		      </tr>
		      <tr style="background-color:#f8f8f8;">
		        <td align="right">邮政编码<span class="hong"> *</span>：</td>
		        <td><input type="text" memberDelivery="zipCode" name="memberDelivery.zipCode" style="width:200px" maxlength="6" title="请输入邮政编码"/><span class="beizhu"></span></td>
		      </tr>
		      <tr style="background-color:#f8f8f8;">
		        <td align="right">电话号码      ：</td>
		        <td>
		        	<input type="hidden" memberDelivery="phone" name="memberDelivery.phone"/>
	        		<input type="text" id="area" name="phone.area" style="width:39px" maxlength="4" title="请输入收货人的区号"/>
			        - <input type="text" id="tel" name="phone.tel" style="width:90px" maxlength="8" title="请输入收货人的电话号码"/>
			        - <input type="text" id="ext" name="phone.ext" style="width:39px" maxlength="8" title="请输入收货人的分机号"/>
		        	<span class="hong">请以（区号-电话号码-分机）的方式填写</span>
		        </td>
		      </tr>
		      <tr style="background-color:#f8f8f8;">
		        <td align="right">手机号码      ：</td>
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
		      <tr style="background-color:#f8f8f8;">
		      	<td align="right">设为默认<span class="hong">&nbsp;&nbsp;</span>：</td>
		      	<td><input name="memberDelivery.status" type="checkbox" id="statusCheckbox" value="1" title="是否将该地址作为默认的收货地址"/></td>
      		  </tr>
		      <tr style="background-color:#f8f8f8;">
		        <td>&nbsp;</td>
		        <td>
		        	<input type="button" value="保存" class="pl-btn-juse" onmousedown="submitEditDelivery(1);" title="保存"/>
		        	<input name="button" type="button" class="pl-btn-hui" value="取消" onmousedown="cancelAddAddress();" title="取消">
		        </td>
		      </tr>
		    </table>
		</form>		
	</li>
</ul>
</div>

<P class="wl-h1"><strong>选择发货方式 </strong></P>
<input type="hidden" name="sendDelivery.orderId" value="${sendDelivery.orderId}"/>
<form id="add" name="add" action="orderSeller/addLogisticsSellerDelivery.htm" method="post"> 
	<input type="hidden" name="logistics.sellerId" value="${memberId}"/>
	<input type="hidden" name="logistics.companyId"/>
	<input type="hidden" name="logistics.companyName"/>
	<input type="hidden" name="logistics.logisticsType"/>
	<input type="hidden" name="sendDelivery.orderId" value="${sendDelivery.orderId}"/>
</form>
<form id="del" name="del" action="orderSeller/delLogisticsSellerDeliveryById.htm" method="post"> 
	<input type="hidden" name="logistics.id" value=""/>
	<input type="hidden" id="delId" name="sendDelivery.orderId" value="${sendDelivery.orderId}"/>
</form>

<ul class="pl-act">
  <li id="tabA" class="pl-here"><a href="##" onclick="switchTab(1);">自己联系物流</a></li>
  <li id="tabB"class="" show="#no-wl"><a href="##" onclick="switchTab(2);">无需物流</a></li>
</ul>

	<div id="wl-mod">
		<table id="ltab" class="wl-table" style="text-align:center">
			<tbody>
				<tr class="tr2">
					<td>操作</td>
					<td>物流公司</td>
					<td>输入运单号码</td>
					<td>确认发货</td>
				</tr>
				<tr>
					<td><a href="#" onClick="isSubmit();">设为默认</a></td>
					<td id="selectedCorp"><a class="wl-pop-btn" href="javascript:showLogisCorpDiv();">请选择</a></td>
					<td><input type="text" maxlength="50" logisticsname="logisticsId"></td>
					<td><input type="button" value="确认发货" onClick="confirmDelivery('','logisticsId');" style="cursor:pointer" class="pl-btn-juse"></td>
				</tr>
			</tbody>
		</table>
		<div class="no-wl" id="no-wl" style=" display:none;"> 此订单无需物流
			<button onclick="confirmDelivery('','','','')">确认发货</button>
		</div>
	</div>
	<div class="wl-error">
	自定义物流公司可能会造成订单物流信息无法追踪
	</div>
</div>
</div>

<div class="wl-pop" id="wl-pop" style="display:none;">
	<div class="head cf"><span class="tit">选择物流公司</span><a class="close" href="javascript:;"></a></div>
	<div class="select-wl cf">
		<span onclick="searchCorp('all')" class="h">全部</span>
		<span onclick="searchCorp('a,b,c,d')">ABCD</span>|<span onclick="searchCorp('e,f,g,h')">EFGH</span>|
		<span onclick="searchCorp('i,j,k,l')">IJKL</span>|<span onclick="searchCorp('m,n,o,p')">MNOP</span>|
		<span onclick="searchCorp('q,r,s,t')">QRST</span>|<span onclick="searchCorp('u,v,w')">UVW</span>|
		<span onclick="searchCorp('x,y,z')">XYZ</span>|
	</div>
	<ul class="select-wlname cf" id="logisCorp">
		<li><span class="h">EMS特快专递(e邮宝)</span></li>
		<li><span>EMS特快专递(e邮宝)</span></li>
	</ul>
	<div class="wlname-page">
		<a id="pre" class="l" href="javascript:previousOrNext('pre');"></a>
		<span id="pageDis">1/20</span>
		<a id="next" class="r" href="javascript:previousOrNext('next');"></a>
	</div>
	<div class="wl-more cf">
		<a id="ascertain" class="btn" href="javascript:selLogisCorp();">确认选择</a>
		<span>其他物流公司：<input type="text" id="otherLogisCorp"/></span>
	
	</div>
</div>

