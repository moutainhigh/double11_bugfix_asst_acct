<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>

<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<script src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script src="http://static.pinju.com/js/refund/leftDay.js"></script>
<script src="http://static.pinju.com/js/refund/sellerRefundManage.js"></script>
<title>品聚网-退款管理- 退款详情</title>

<form name="form1" id="form1" action="" enctype="multipart/form-data" method="post">
<input name="refundId" type="hidden" value="${refundDO.id}" />
</form>

<div class="main20110627 cf">
		<div class="positionbanner t4">您的位置：
	 		 	<a href="${base}/orderSeller/ordermanagesell.htm" class="mr10">已卖出的商品 <i></i></a>&gt;
				<a href="${base}/refund/sellerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">退款详情</span>
		</div><!--顶部按钮条结束 -->
 	   
	<#include "/default/templates/screen/refund/orderInfo.ftl">
 		    
	<div class=" boxh760 floatright top10 "><!--右侧 -->
		<div class=" boxh37">
        	<img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" />
            <img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" />
            <img src="http://static.pinju.com/images/refund/11_09.jpg" width="131" height="37" />
  		</div><!--1、申请退货。。。进程图结束 -->
          
          
        <div class=" top10 paddingbottom20 t2 cf">
	        
	        <div class="boxpadding  zi_14size t3 bd bottom10">退款编号：${refundId.toString()}</div>
			<ul class="refund_text">
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
              <li class=" zi_shenhui">
              		您还有 <span class="hong" id="deadline" leftDay="${leftDaySec?c}"> </span>处理本次退款协议
              </li>
              <li class=" zi_shenhui">买家已将退还的商品发出，等待您确认收货完成退款，如果您未在<#if tradeRefundLogisticsDO.logisticsType == 1>30<#else>10</#if>天内收到退货或您收到的货物有问题，您可以点击申请客服介入，客服人员会在5个工作日内介入该笔退款申请。</li>

           </ul>
           <div class="boxh0 top10">
           <ul>
              <li>
              <span class="li01"><a href="#"><img  id="confirmReceiveGoods" src="http://static.pinju.com/images/refund/13_05.gif" /></a></span>
              <span class="li01"><a href="#"><img  id="sellerApplyCustProcess" src="http://static.pinju.com/images/refund/12_05.gif" /></a></span>
              <span class="li01"><a href="#leaveword" ><img src="http://static.pinju.com/images/refund/11_13.jpg" /></a></span>
              </li>
           </ul>
           </div>
			  
       </div> 
       
		<div class="refund_tip_box hong">
			<dl class="refund_tip">
				<dt>小提示：</dt>
				<dd>1.请确保您的物流凭证保存完好，如果进入客服介入，可能需要您提供相关证据。</dd>
			</dl>
		
		</div>
          
          
          <#include "/default/templates/screen/refund/refundInfo.ftl">
		  
	  <!--按钮以下第四部分 -->
		<div class="boxh0 top10 paddingbottom20 t3 t2 cf">
            <#include "/default/templates/screen/refund/LeaveWordList.ftl">
		  <form action="${base}/refund/saveLeaveWord.htm" id="leaveWordform" name="leaveWord_form"    
          				enctype="multipart/form-data" method="post" target="leaveWord_hidden_frame">  
		  		<input type="hidden" name="userType" value="2"/>
  				<#include "/default/templates/screen/refund/LeaveWord.ftl">
  		  	</form>
		</div> 
		
        <div class="boxh0 top10 t3 t2">
        	<div class="boxpadding zi_14size t3 bd"><strong>退款协议状态</strong></div>
            <div class="boxh2 t5"><div class="boxred floatleft"></div>
        </div>
       	<div class="bd refund_pact">
	       	<p>您已经同意了买家的退款申请，退款协议达成。</p>
	       	<p>&nbsp;</p>
	       	<p>买家已发出退还商品，等待卖家确认收货。</p>
       	</div>

       </div> 
       
       <#include "/default/templates/screen/refund/refundLogisticSection.ftl">  
            




<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>
</div>
</div><!--右侧结束 -->
</div><!--bd结束 -->
</div>

