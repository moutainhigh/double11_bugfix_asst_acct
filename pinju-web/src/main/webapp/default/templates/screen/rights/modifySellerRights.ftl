<LINK href="http://static.pinju.com/css/rights/xiaobao.css" rel="stylesheet" />
<script src="http://static.pinju.com/js/rights/globalMoney.js"></script>
<#if query??>
<div class=" main20110627">
<div class="positionbanner t4">您的位置： <strong>我是卖家 <i></i></strong>&gt;<span>维权管理<i></i></span>&gt;<span>维权修改</span></div><!--顶部引导条结束 -->
<div class="tuikuanleft top10"><!--左侧开始 -->
	<div class="tuikuanleft-2 ">
		<div class="tuikuanleft19 paddingtb10 t3">
        	<center><strong>订单详情</strong></center>
     	</div>
   		<#if query.rightsDO??>
     	<#assign rightsDO=query.rightsDO>
   			<#if query.orderItemDO??>
     		<#assign orderItemDO=query.orderItemDO>
			<ul>             		
        		<li>订单编号：${(orderItemDO.orderItemId)!}</li>
        		<li>成交时间：${orderItemDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")!}</li>
        		<li>订单状态：<#if orderItemDO.orderItemState=1>未付款
        			<#elseif orderItemDO.orderItemState=2>已付款
        			<#elseif orderItemDO.orderItemState=3>已发货
        			<#elseif orderItemDO.orderItemState=5>交易成功
        			<#elseif orderItemDO.orderItemState=6>交易关闭
        			</#if>
        		</li>
        		<li>卖家昵称：${(rightsDO.sellerNick)!}</li>
        		<li>商品总价：${orderItemAmountStr!}<!--（注:商品总价=子订单数量*价格）--></li>
        		<li>运输费：${freight!}</li>
        		<li>购买数量：${(orderItemDO.buyNum)!}</li>
        		<!--
                <li>订单总计：${priceAcountStr!}</li>
                -->
        		<li>实付款：${payment!}<!--（注:实付款=商品总价+运费-优惠）--></li>	
				<#--
        		<li>订单编号：${rightsDO.orderId}</li>
        		<li>成交时间：${orderItemDO.stateModifyTime?string("yyyy-MM-dd HH:mm:ss")}</li>
        		<li>订单状态：交易完成</li>
        		<li>卖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：${rightsDO.sellerNick}</li>
        		<li>商品总价：${orderItemAmountStr!}（注:商品总价=子订单数量*价格）</li>
        		<li>运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：从那取XXX</li>
        		<li>优&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;惠：从那取XXX</li>
				<li>订单数量：${(orderItemDO.buyNum)!}</li>
        		<li>订单总计：${priceAcountStr!}</li>
        		<li>&nbsp;实付款：${orderItemAmountStr!}（注:实付款=商品总价+运费-优惠）</li>
        		-->
        	</ul>
   			</#if>
   		</#if>
	</div>
</div><!--左侧结束 -->
<div class=" boxh0 floatright top10 piaofuceng"><!--右侧开始 -->	    
	<div class=" boxh37 floatleft" ><!--1、申请消保。。。进程图 -->
		<img src="http://static.pinju.com/images/xiaobao_1_none.jpg" width="131" height="37" />
		<img src="http://static.pinju.com/images/xiaobao_2.jpg" width="131" height="37" />
		<img src="http://static.pinju.com/images/xiaobao_3_none.jpg" width="125" height="37" />
   	</div><!--1、申请退货。。。进程图结束 -->
 	<form id="modifySellerRightsForm" action="${base}/rights/modifySellerRights.htm" method="post" enctype="multipart/form-data">      
  		<div class=" floatleft boxh0 t2 t3 top10 paddingtb10"><!--申请消保内容框架开始--> 
        	<ul>
			<#if query.rightsDO??>
				<#assign rightsDO=query.rightsDO>
            	<li>
            		<input type="hidden" name="id" value="${id!}" />
                	<label>维权类型：</label>
                   	<#if rightsDO.voucherType =  0>7天退换货
                	<#elseif rightsDO.voucherType = 1>72小时发货
                	<#elseif rightsDO.voucherType = 2>30天维修
                	<#elseif rightsDO.voucherType = 3>假一赔三</#if>
              	</li>
               	<li>
                	<label>维权要求：</label>
					<#if rightsDO.buyerRequire = 0>退款<#elseif rightsDO.buyerRequire = 1>退货</#if>
              	</li>                     
                <li>
                	<label>买家申请退款金额：</label>
            		<input type="hidden" id="tradePrice" name="tradePrice" value="${(rightsDO.price)!}" />
            		${globalMoney!}&nbsp;元&nbsp;&nbsp;修改为&nbsp;
                	<input type="text" id="globalMoney" name="globalMoney" value="${globalMoney!}">
					元<!--（退款金额不能超过<span class="cheng">5000.00元</span>-->
					<!--金额，如有疑问请查看<a href="#"><span class="lan02">帮助</span></a>）-->
                </li>
                <li>详细说明：<span class="hui">（请详细描述您的理由，以便卖家和客服人员判断，限500字）</span></li>
                <p></p>
                <li class="shuomingbox"><textarea id="buyerReason" name="buyerReason" cols="40" rows="8" style="overflow:hidden;width:480px;">${(rightsDO.buyerReason)!}</textarea></li>
                <li><strong>上传凭证：</strong><span class="hui">可选项，每张图片大小不超过2M，最多3张  支持GIF、JPG、PNG格式</span></li>
                <div id="fileone" style="display:block">
                	<li class="shuomingbox" >
                		<span class="floatleft">
                			<span class="floatleft">凭证文件1:<input type="file" name="voucherPic"/></span>
                		</span>
                		<a href="#" class="lan02" id="showfile">继续上传凭证</a> 
                	</li>
                </div>
                <div id="filetwo" style="display:none">
                	<li class="shuomingbox" >
                		<span class="floatleft">凭证文件2:<input type="file" name="voucherPic"/></span>
                	</li>
                </div>
                <div id="filethree" style="display:none">
                	<li class="shuomingbox" >
                		<span class="floatleft">凭证文件3:<input type="file" name="voucherPic"/></span>
                	</li>
                </div>
            </#if>
      		</ul>   
		</div><!--<!--申请消保内容框架结束 -->
		<div class="boxpadding floatleft t3 t6">	
			<ul>	
	            <li class="top10">
	            	<input type="button" value="保存" onclick="modifySellerRights();" class="dajin0628-redbutton01 bordernone0628 zi_14size dajin0628-zi-baise dajin0628-zi-jiacu dajin0701-buttonhand" />&nbsp;
	            	<input type="button" value="取消" onclick='returnrightlist();' class="dajin0628-redbutton01 bordernone0628 zi_14size dajin0628-zi-baise dajin0628-zi-jiacu dajin0701-buttonhand" />
	            	<font color="red">${errorMsg!}</font> 
	            </li>
			</ul>
		</div> 	
	</form>
</div><!--右侧结束 -->      
</div> 	
</#if>
<script type="text/javascript">
var modifySellerRights = function(){
   var form = $("#modifySellerRightsForm");
   if(validateForm()){
      form.submit();
   }
}
var validateForm = function(){
	var tradePrice=$("#tradePrice").val();
	var globalMoney=$("#globalMoney").val();
	if(parseFloat(globalMoney) > parseFloat(tradePrice)){
		alert("买家申请退款金额不能大于交易金额!");
		return false;
	}else{
   		return true;
	}
}
$(function(){
	//var voucherType=$("#voucherType");
	//voucherType.val(${(rightsDO.voucherType)!});
	//var buyerRequire=$("#buyerRequire");
	//buyerRequire.val(${(rightsDO.buyerRequire)!});

	$('#showfile').toggle(function() {
		$('#filetwo').show();
	},function() {
	  	$('#filethree').show();
	},function() {
	  	alert("最多只能上传3个");
	});
});
function returnrightlist(){
	document.location.href="${base}/rights/sellerRightsDetail.htm?rightsId=${id!}";	
}
</script>