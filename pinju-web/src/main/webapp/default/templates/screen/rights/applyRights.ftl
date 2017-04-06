<script src="http://static.pinju.com/js/rights/validateNum.js" type="text/javascript"></script>

<title>维权申请页</title>

<#if query??>
<!-- main-Start -->
<div class="main20110627">
	<div class="rights_map">
		您的位置： <span><a href='http://www.pinju.com'>首页</a></span> &gt; 
		<span><a href='http://www.pinju.com/orderBuyer/myBuyer.htm'>我是买家</a></span> &gt; 
		<span><a href='http://www.pinju.com/orderBuyer/orderManager.htm'>已买到的商品</a></span> &gt; 
		<span class="bd red"> 维权申请</span>
	</div>
	
	<!-- 左侧订单信息-Start -->
	<#include "/default/templates/control/rights/orderInfo.ftl">
	<!-- 左侧订单信息-End --> 	
	
	<!-- 1、申请消保。。。进程图-Start -->
    <div class="floatleft" style="padding-left:10px;padding-bottom:8px">
    	<img src="http://static.pinju.com/images/xiaobao_1.jpg" width="131" height="37" />
    	<img src="http://static.pinju.com/images/xiaobao_2_none.jpg" width="131" height="37" />
    	<img src="http://static.pinju.com/images/xiaobao_3_none.jpg" width="125" height="37" />
    </div>
    <!-- 1、申请退货。。。进程图结束-End -->	
    
    <!-- 右侧-Start -->
  	<div class="right_layout">
  		<!-- 维权申请协议-Start -->
        <div class="rights_box cf">
			<div class="tit"><span class="txt">维权申请协议</span></div>
			<#if query.orderItemDO??>
			<#assign orderItemDO=query.orderItemDO>
			<!-- 商品信息-Start -->
			<div class="rights_goods">
			<table class="rights_goods_tab">
				<tr>
					<th class="bla1">商品</th>
					<th class="bla2">单价(元)</th>
					<th class="bla3">数量</th>
					<th class="bla4">优惠(元)</th>
					<th class="bla5">小计(元)</th>
				</tr>
				<tr>
					<td>
						<div class="goods">
							<div class="pic_box">
								<#if orderItemDO?? && orderItemDO.itemPicUrl??>
								<a href="${imageServer!}${(orderItemDO.itemPicUrl)?html}" target="_blank"><img width="88" height="88" src="${imageServer!}${(orderItemDO.itemPicUrl)?html}" /></a>
								</#if>
							</div>
							<span class="gray size">
							<#if orderItemDO?? && orderItemDO.itemId?? && orderItemDO.itemTitle??>
							<a target="_blank" class="lh20 items_link" href="${base}/detail/detail.htm?itemId=${(orderItemDO.itemId)!}">${((orderItemDO.itemTitle)?html)!}</a>
							</#if>
		            		<#if orderItemDO?? && orderItemDO.itemSkuAttributes?? && orderItemDO.itemSkuAttributes != "0">
		            			${orderItemDO.itemSkuAttributes}
		            		</#if>								
							</span>
						</div>
					</td>
					<td><span class="bd">${(((orderItemDO.orderItemPrice)!0)/100)?string('0.00')}</span></td>
					<td><span class="bd">${((orderItemDO.buyNum)?html)!}</span></td>
					<td><span class="bd">${((orderItemDO.discountAmountByYuan)!)?html}</span></td>
					<td>
						<span class="bd">${(orderItemDO.totalAmountByYuan)!}</span>
						<input type="hidden" id="orderItemAmount" name="orderItemAmount" value="${(orderItemDO.totalAmountByYuan)!}" readOnly="true"/>
					</td>
				</tr>
			</table>
			</div>
			<!-- 商品信息-End -->	 
			</#if>
			<form> </form>   
        	<form id="applyRightsForm" name="applyRightsForm" action="${base}/rights/applyRights.htm" method="post" enctype="multipart/form-data">
        	<#if orderItemDO?? && orderItemDO.orderItemId??>      
          	<input type="hidden" name="orderItemId" value="${((orderItemDO.orderItemId)?html)!}"/>
          	</#if>
			<ul class="rights_list">
				<li>
					<span>是否需要退货<font color='red'>*</font>：</span>
					<div>
						<input type="radio" id="buyerRequire0" name="buyerRequire" value="0" onClick='chgRadioNoGoods();' <#if buyerRequire?? && buyerRequire=0>checked='true'</#if>/><b>不需要退货</b>
						<input type="radio" id="buyerRequire1" name="buyerRequire" value="1" onClick='chgRadioGoods();' <#if buyerRequire?? && buyerRequire=1>checked='true'</#if>/><b>需要退货</b>
						<input type="hidden" id='buyerRequire' value='${buyerRequire!}'>
					</div>
				</li>			
         		<li>
                    <span>维权类型<font color='red'>*</font>：</span>
                    <div>
                    <input type="hidden" id='voucherTypeHidden' name='voucherTypeHidden' value='${voucherType!}'>
                    <select id="voucherType" name="voucherType" onChange='changeVoucher();'>
                      	<option <#if voucherType?? && voucherType=0>selected="selected"</#if> value="0">7天无理由退货</option>
                      	<option <#if voucherType?? && voucherType=1>selected="selected"</#if> value="1">有质量问题</option>
                      	<option <#if voucherType?? && voucherType=2>selected="selected"</#if> value="2">假一赔三</option>
                    </select>
                    </div>
              	</li>                 
          		<li>
             		<span>交易金额<font color='red'>*</font>：</span>
                 	<div>
                 		<#if orderItemDO?? && orderItemDO.totalAmountByYuan??> 
                 		<input type="text" id="tradePrice" name="tradePrice" value="${(orderItemDO.totalAmountByYuan)!}" readOnly="true"/>元
                 		</#if>
                 	</div> 
               	</li>
          		<li>
             		<span>维权金额<font color='red'>*</font>：</span>
                 	<div>
	             		<input id="buyerApplyPrice" name="buyerApplyPrice" value="${(buyerApplyPrice?html)!}" type="text" onkeyup="clearNoNum(this);" maxLength="12"/>元
	             		<b id='msgb'></b>
                     	<p class="red">提示：只有您申请假一赔三服务或因卖家责任导致需要退货退款才能申请赔偿，请勿随意发起要求赔偿以免影响您正常的退款申请。</p>
						<p class="red">维权金额将直接打入您的银行卡，请正确填写您的银行卡信息。</p>
					</div>
               	</li>
				<li><span>户名<font color='red'>*</font>：</span><div><input type="text" id="buyerAccountName" name="buyerAccountName" value="${(buyerAccountName?html)!}" maxLength='20' size='40'/></div></li>
				<li>
					<span>开户行<font color='red'>*</font>：</span>
					<div>
						<input type="text" id="buyOpenAccount" name="buyOpenAccount" value="${(buyOpenAccount?html)!}" maxLength='60' size='60'/>
						<p class='hui'>请填写您的开户银行 如：工商银行上海分行漕宝路支行</p>
					</div>
				</li>               	
				<li><span>银行账号<font color='red'>*</font>：</span><div><input type="text" id="buyerAccount" name="buyerAccount" value="${(buyerAccount?html)!}" onkeyup="clearNoLong(this);" maxLength="20" size='40'/></div></li>
          		<li>
          			<span>详细说明<font color='red'>*</font>：</span>
           			<div>
          				<textarea id="buyerReason" style="width:480px;height:80px" name="buyerReason" class="rights_mse" onPropertyChange="limitInputLength(this,300);" maxLength='300'>${(buyerReason?html)!}</textarea>-
          				<p id='msgFont'><b class='hui'>（注：请详细描述您的理由，以便卖家和客服人员判断，限150字）</b></p>
          			</div>
          		</li>
				<li>
					<span>上传凭证：</span>
					<div class="add_upload_box">
						<p class="hui">可选项，每张图片大小不超过500K，最多3张  支持GIF、JPG、PNG格式</p>
						<p id="fileone"><b class="mr10 bd"></b><input type="file" name="voucherPic" size="50"/></p>
						<p id="filetwo" style="display:none">
							<b class="mr10 bd"></b><input type="file" id='voucherPic2' name="voucherPic" size="50"/>
							<a href="javascript:void(0)" onclick="hiddenFileTwo()">取消此凭证</a>
						</p>
						<p id="filethree" style="display:none">
							<b class="mr10 bd"></b><input type="file" id='voucherPic3' name="voucherPic" size="50"/>
							<a href="javascript:void(0)" onclick="hiddenFileThree()">取消此凭证</a>
						</p>
						<a class="ml20" href="javascript:void(0);" id="showfile">增加凭证</a>
						<font id="msgSpan" color="red"></font>
						<input type="hidden" value="0" id="temp1"/>
						<input type="hidden" value="0" id="temp2"/>
					</div>
				</li>
				<li style='text-align:center;'>
		          	<input class="rigths_btn-four" type="button" value="确认提交" onclick="applyRights();"/>
		            <font color="red">${errorMsg!}</font>
				</li> 
          	</ul>
		</form>
		</div>
		<!-- 维权申请协议-End -->
	</div>
	<!-- 右侧-End -->
</div>
<!-- main-End --> 
</#if>
<script type="text/javascript">
function applyRights(){
   	if(validateForm()){
   		var voucherType=$('#voucherType').val();
   		$('#voucherTypeHidden').val(voucherType);
   		document.getElementById('applyRightsForm').submit();
   	}
}
var validateForm = function(){
	var buyerRequire0 = document.getElementById("buyerRequire0");
	var buyerRequire1 = document.getElementById("buyerRequire1");
	if(!buyerRequire0.checked && !buyerRequire1.checked){
		alert('请选择是否需要退换货！');
		return ;
	}
	var buyerApplyPrice=$("#buyerApplyPrice").val();
	if(buyerApplyPrice==""){
		alert("请填写维权金额！");
		return ;
	}
	var orderItemAmount=$('#orderItemAmount').val();
	var voucherType=$("#voucherType").val();
	if(voucherType==0){//七天无理由退货要求退款上限为买家为该商品实际付款的金额
		if(parseFloat(buyerApplyPrice) > parseFloat(orderItemAmount)){
			alert("买家申请退款金额超限！");
			return ;
		}
	}else if(voucherType==1){//有质量问题求退款上限为该商品实际付款的金额加100元可能的运费
		if(parseFloat(buyerApplyPrice) > (parseFloat(orderItemAmount) + 100)){
			alert("买家申请退款金额超限！");
			return ;
		}
	}else {//假一赔三时不允许超过商品价格三倍加100元的可能运费
		if(parseFloat(buyerApplyPrice) > (parseFloat(orderItemAmount) * 3 + 100)){
			alert("申请维权退还金额最高不能超过商品价格的三倍！");
			return ;
		}	
	}
	var buyerAccountName = $('#buyerAccountName').val();
	if($.trim(buyerAccountName)==""){
		alert('户名不能为空！');
		return ;
	}
	var buyOpenAccount = $('#buyOpenAccount').val();
	if($.trim(buyOpenAccount)==""){
		alert('开户行不能为空！');
		return ;
	}
	var buyerAccount = $('#buyerAccount').val();
	if($.trim(buyerAccount)==""){
		alert('银行帐号不能为空！');
		return ;
	}
	var buyerReason = $('#buyerReason').val();
	buyerReason=$.trim(buyerReason);
	if(buyerReason==""){
		alert('请认真填写维权详细说明！');
		return ;
	}
	if(getLength(buyerReason)>300){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出150个汉字长度，请尽量描述简洁，以方便卖家查看。</font>";
		return ;
	}	
	return true;
} 
var chgRadioNoGoods=function(){
	$('#voucherType').val('1');
	document.getElementById("voucherType").disabled=true;
}
var chgRadioGoods=function(){
	document.getElementById("voucherType").disabled=false;
}
function changeVoucher(){
	var voucherType=$("#voucherType").val();
	if(voucherType == 2){
		$('#msgb').html('(申请维权退还金额最高不能超过商品价格的三倍)');
	}
}
function hiddenFileTwo(){
	$('#msgSpan').html('');
	$('#voucherPic2').val('');
	document.getElementById("voucherPic2").outerHTML += '';
	$("#filetwo").hide();
	$('#temp1').val('0');
}
function hiddenFileThree(){
	$('#msgSpan').html('');
	$('#voucherPic3').val('');
	document.getElementById("voucherPic3").outerHTML += '';
	$("#filethree").hide();
	$('#temp2').val('0');
}
$(function(){

	var buyerRequire = $('#buyerRequire').val();
	if(buyerRequire==undefined || buyerRequire==""){
		document.getElementById("buyerRequire0").checked = false;
		document.getElementById("buyerRequire1").checked = false;
	}else if(buyerRequire == 0){
		document.getElementById("buyerRequire0").checked = true;
		document.getElementById("voucherType").disabled = true;
	}else if(buyerRequire == 1){
		document.getElementById("buyerRequire1").checked = true;
	}
	
	var temp1 = $('#temp1');
	var temp2 = $('#temp2');
	$('#showfile').toggle(function() {
		$('#msgSpan').html('');
	  	$('#filetwo').show();
		temp1.val('1');
	},function() {
		$('#msgSpan').html('');
	  	$('#filethree').show();
		temp2.val('1');
	},function() {
		if(temp1.val()==1 && temp2.val()==1){
	  		$('#msgSpan').html('最多只能上传3个!');
		}
	});
});
//取得字符串长度，一个中文字符长度为2
function getLength(s){
	if(s == ""){
		return 0;
	}
	var len = 0;
	for(i = 0; i < s.length; i++){
		var c = s.substr(i, 1);
		var ts = escape(c);
		if(ts.substring(0, 2) == "%u"){
			len += 2;
		} else {
			if(ts.substring(0, 3) == "%D7"){
				len += 2;
			} else {
				len += 1;
			}
		}
	}
	return len;
}
// 限制文本域中输入的长度
function limitInputLength(obj,a_limit){
	var objValue=$.trim(obj.value);
	if(getLength(objValue) > a_limit){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出150个汉字长度，请尽量描述简洁，以方便卖家查看。</font>";
	}else if(getLength(objValue) > a_limit){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出150个汉字长度，请尽量描述简洁，以方便卖家查看。</font>";
	}else{
		document.getElementById('msgFont').innerHTML="<b class='hui'>（注：请详细描述您的理由，以便卖家和客服人员判断，限150字）</b>";
	}
}
</script>