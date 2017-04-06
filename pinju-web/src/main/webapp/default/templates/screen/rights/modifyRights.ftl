<LINK href="http://static.pinju.com/css/rights/xiaobao.css" rel="stylesheet" />
<script src="http://static.pinju.com/js/rights/validateNum.js" type="text/javascript"></script>
<script src="http://static.pinju.com/js/rights/globalMoney.js"></script>

<title>维权修改页</title>

<#if query??>
<!-- main-Start -->
<div class="main20110627">
	<!-- 顶部引导条-Start --> 
	<div class="positionbanner t4">
		您的位置：<a href="${base}/orderBuyer/orderManager.htm">我是买家 <i></i></a>&gt;
		<a href="${base}/rights/rightsList.htm">维权管理<i></i></a>&gt;
		<span class="bd red">维权详情</span>
	</div>
	<!-- 顶部引导条-End -->
	 
	<!-- 左侧订单信息-Start -->
	<#include "/default/templates/control/rights/orderInfo.ftl">
	<!-- 左侧订单信息-End --> 
	
	<!-- 1、申请消保。。。进程图-Start -->
	<div class="boxh37 floatleft" style="padding-left:10px">
		<img src="http://static.pinju.com/images/xiaobao_1_none.jpg" width="131" height="37" />
		<img src="http://static.pinju.com/images/xiaobao_2.jpg" width="131" height="37" />
		<img src="http://static.pinju.com/images/xiaobao_3_none.jpg" width="125" height="37" />
   	</div>
   	<!-- 1、申请退货。。。进程图结束-End -->   
   	
   	<!-- 右侧   -->   
	<div class="right_layout" style="width:750px">	    
		<#if query.rightsDO??>
		<#assign rightsDO=query.rightsDO>
     	<form id="modifyRightsForm" name="modifyRightsForm" action="${base}/rights/modifyRights.htm" method="post" enctype="multipart/form-data">
     		<!-- 申请消保内容框架  -->      
      		<div class="floatleft boxh0 t2 t3 top10 paddingtb10"> 
            	<ul>
                	<li>
                		<input type="hidden" name="rightsId" value="${rightsId!}" />
                    	<label><strong>维权类型：</strong></label>
                    	<#if rightsDO.voucherType=0>7天无理由退货
                    	<#elseif rightsDO.voucherType=1>有质量问题
                    	<#elseif rightsDO.voucherType=2>假一赔三</#if>
                    	<input type="hidden" id="voucherType" name='voucherType' value="${(rightsDO.voucherType)!}" readOnly="true"/>

                  	</li>
                   	<li>
                    	<label><strong>维权要求：</strong></label>
                    	<#if rightsDO.buyerRequire = 0>退款<#elseif rightsDO.buyerRequire = 1>退货</#if>
                  	</li>                     
                    <li>
                    	<label><strong>维权金额：</strong></label>
                		<input type="hidden" id="tradePrice" name="tradePrice" value="${(rightsDO.price)!}" readOnly="true"/>
                		<#if query.orderItemDO??>
						<#assign orderItemDO=query.orderItemDO>
                		<#if orderItemDO?? && orderItemDO.totalAmountByYuan??>
                		<input type="hidden" id="orderItemAmount" name="orderItemAmount" value="${(orderItemDO.totalAmountByYuan)!}" readOnly="true"/>
                		</#if>
                		</#if>
                		${globalMoney!}&nbsp;元&nbsp;&nbsp;修改为&nbsp;
                    	<input type="text" id="globalMoney" name="globalMoney" value="${globalMoney!}" maxLength="12" onKeyUp='clearNoNum(this);' title='请输入维权金额'/>元
                    </li>
                    <li>
                    	<label><strong>详细说明：</strong></label>
                    	<span class="hui" id='msgFont'>（请详细描述您的理由，以便卖家和客服人员判断，限150字）</span>
                    </li>
                    <p></p>
                    <li class="shuomingbox">
                    	<textarea style="width:600px;height:80px" name="buyerReason" id='buyerReason' onpropertychange="limitInputLength(this,300)" maxLength='300' title='请输入详细说明'>${(rightsDO.buyerReason)?html}</textarea>
                    	<p><font color='red' id='msgFont'></font></p>
                	</li>
                	<#if imageServer??>
					<li><label><strong>附　件：</strong></label>（点击图片可看大图）</li>
			 		<li class="left10">
			 			<div class="dajin0628-weiquanpicall">
			   				<div class="">
			       				<#if rightsDO.voucherPic1?? && rightsDO.voucherPic1 !="">
			   					<a href="${(imageServer?html)!}${(rightsDO.voucherPic1)!}" target="_blank"><img src="${imageServer}${(rightsDO.voucherPic1)!}" width="94" height="94"/></a>
								</#if>
			   					<#if rightsDO.voucherPic2?? && rightsDO.voucherPic2 != "">
			   					<a href="${(imageServer?html)!}${(rightsDO.voucherPic2)!}" target="_blank"><img src="${imageServer}${(rightsDO.voucherPic2)!}" width="94" height="94"/></a>
			   					</#if>
			   					<#if rightsDO.voucherPic3?? && rightsDO.voucherPic3 != "">
			   					<a href="${(imageServer?html)!}${(rightsDO.voucherPic3)!}" target="_blank"><img src="${imageServer}${(rightsDO.voucherPic3)!}" width="94" height="94"/></a>
			   					</#if>
			   				</div>
			   			</div>
					</li>
					</#if>
                    <li><label><strong>上传凭证：</strong></label><b class='hui'>可选项，每张图片不超过150K，最多3张  支持GIF、JPG、PNG格式</b></li>
                	<li class="shuomingbox" align="left">
                		<span> 
                			凭证文件 <input type="file" name="voucherPic" size="50"/>
	                    	<a href="javascript:void(0)" class="lan02" id="showfile">增加上传</a>			
	                    	<font id="msgSpan" color="red"></font>
							<input type="hidden" value="0" id="temp1"/>
							<input type="hidden" value="0" id="temp2"/>                			
                		</span>
                	</li>
                	<li class="shuomingbox" id="filetwo" style="display:none">
                		<span>凭证文件 <input type="file" id='voucherPic2' name="voucherPic" size="50"/>
						<a href="javascript:void(0)" class="lan02" onclick="hiddenFileTwo()">取消此凭证</a></span>
					</li>
                	<li class="shuomingbox" id="filethree" style="display:none">
                		<span>凭证文件 <input type="file" id='voucherPic3' name="voucherPic" size="50"/>
						<a href="javascript:void(0)" class="lan02" onclick="hiddenFileThree()">取消此凭证</a></span>
					</li>
		            <li style='text-align:center;'>
		            	<input type="button" value="保存" onclick="modifyRights();" class="dajin0628-redbutton01 bordernone0628 zi_14size dajin0628-zi-baise dajin0628-zi-jiacu dajin0701-buttonhand" />&nbsp;
		            	<input type="button" value="取消" onclick='returnRightsList();' class="dajin0628-redbutton01 bordernone0628 zi_14size dajin0628-zi-baise dajin0628-zi-jiacu dajin0701-buttonhand" />
		            	<font color="red">${errorMsg!}</font>
		            </li>
          		</ul>   
			</div>
			<!-- 申请消保内容框架结束 -->	
		</form>
		</#if>
	</div>
	<!-- 右侧结束  -->	
</div>
<!-- main结束  --> 
</#if>	
<script type="text/javascript">
var modifyRights = function(){
	var form = $("#modifyRightsForm");
   	if(validateForm()){
      	form.submit();
   	}
}
var validateForm = function(){
	var orderItemAmount=$('#orderItemAmount').val();
	var buyerApplyPrice=$("#globalMoney").val();
	var voucherType=$("#voucherType").val();
	if(buyerApplyPrice==""){
		alert("请填写维权金额！");
		return ;
	}
	if(voucherType==0){//七天无理由退货要求退款上限为买家为该商品实际付款的金额
		if(parseFloat(buyerApplyPrice) > (parseFloat(orderItemAmount))){
			alert("买家申请退款金额超限！");
			return ;
		}
	}else if(voucherType==1){//有质量问题求退款上限为该商品实际付款的金额加100元可能的运费
		if(parseFloat(buyerApplyPrice) > (parseFloat(orderItemAmount) + 100)){
			alert("买家申请退款金额超限！");
			return ;
		}
	}else{//假一赔三时不允许超过商品价格三倍加100元的可能运费
		if(parseFloat(buyerApplyPrice) > (parseFloat(orderItemAmount) * 3 + 100)){
			alert("申请维权退还金额最高不能超过商品价格的三倍！");
			return ;
		}	
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
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出150个汉字长度，请尽量描述简洁，以便卖家和客服人员判断。</font>";
	}else if(getLength(objValue) > a_limit){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出150个汉字长度，请尽量描述简洁，以便卖家和客服人员判断。</font>";
	}else{
		document.getElementById('msgFont').innerHTML="<b class='hui'>（注：请详细描述您的理由，以便卖家和客服人员判断，限150字）</b>";
	}
}
function hiddenFileTwo(){
	$('#msgSpan').val('');
	$('#voucherPic2').html('');
	document.getElementById("voucherPic2").outerHTML += '';
	$("#filetwo").hide();
	$('#temp1').val('0');
}
function hiddenFileThree(){
	$('#msgSpan').html('');
	$('#voucherPic3').val('');
	document.getElementById("voucherPic2").outerHTML += '';
	$("#filethree").hide();
	$('#temp2').val('0');
}
$(function(){
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
	  		$('#msgSpan').html('最多只能上传3个！');
		}
	});
});
function returnRightsList(){
	document.location.href="${base}/rights/rightsDetail.htm?rightsId=${rightsId!}";	
}
</script>