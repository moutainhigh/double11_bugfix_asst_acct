<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<script src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script src="http://static.pinju.com/js/refund/leftDay.js"></script>
<script src="http://static.pinju.com/js/refund/sellerRefundManage.js"></script>
<title>品聚网-退款管理- 退款详情</title>

<div class="main20110627 cf">
	 		 <div class="positionbanner t4">您的位置：
	 		 	<a href="${base}/orderSeller/ordermanagesell.htm" class="mr10">已卖出的商品 <i></i></a>&gt;
				<a href="${base}/refund/sellerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">退款详情</span>
				</div><!--顶部按钮条结束 -->
 		    
 		    <#include "/default/templates/screen/refund/orderInfo.ftl">
 		    
	  <div class=" boxh760 floatright top10 "> 
                 
                <div class=" boxh37">
                    <img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_09.jpg" width="131" height="37" />
  </div>  
          
          
          <form id="checkForm" method="post" >
          <input type="hidden" id="refundId" name="refundId" value="${refundDO.id}" />
          </form>
         <div class=" top10 paddingbottom20 t2 cf">
            
       	   <div class="boxpadding  zi_14size t3 bd bottom10">退款编号：${refundId.toString()}</div>
       	   
       	   
			<ul class="refund_text">
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
                    <li class=" zi_shenhui">卖家（${refundDO.sellerNick}）您还有 <span class="hong" id="deadline" leftDay="${leftDaySec?c}"> </span> 处理本次退款协议</li>
                    <li class=" zi_shenhui">如果您未在该期限内与买家 （${refundDO.buyerNick}）就退款协议达成一致或未拒绝本次退款申请，本次退款将自动达成协议，买家将会收到退款或进入退还商品状态。</li>
				   <li class="zi_shenhui">请您在进行同意或拒绝操作前，尽快充分与买家沟通达成一致，避免误解。</li>
                    
           </ul>
             
			  <div class="boxh0 top10">
              <ul>
             	<li class="cf">
                <span class="li01"> <a href="javascript:void(0);"><img id="sellerAgreeRefundApply" src="http://static.pinju.com/images/refund/11_11.jpg" width="102" height="29" /></a></span>
                <span class="li01"><a href="javascript:void(0);"><img id="sellerRejectRefundApply" src="http://static.pinju.com/images/refund/11_12.jpg" width="103" height="29" /></a></span>
                <span class="li01"><a href="#leaveword" id="leavewords"><img src="http://static.pinju.com/images/refund/11_13.jpg" width="147" height="29" /></a></span>
                
                </li>
                 
              </ul>
              </div>
       </div> 
		<div class="refund_tip_box hong">
			<dl class="refund_tip">
				<dt>小提示：</dt>
				<dd>1.请您注意退款处理时限，避免超时导致损失产生。<dd>
				<dd>2.如果您对退款有异议，建议您在期限内积极与买家进行沟通协商，可以让买家修改退款申请。<dd>
			</dl>
		
		</div>
		
		
		<#include "/default/templates/screen/refund/refundInfo.ftl">
		  


		<div class="boxh0 top10 paddingbottom20 t3 t2 cf">
            <#include "/default/templates/screen/refund/LeaveWordList.ftl">
		  <form action="${base}/refund/saveLeaveWord.htm" id="leaveWordform" name="leaveWord_form"    
          				enctype="multipart/form-data" method="post" target="leaveWord_hidden_frame">
		  		<input type="hidden" name="userType" value="2"/>
  				<#include "/default/templates/screen/refund/LeaveWord.ftl">
  		  	</form>
		  </div>
		  
       

           <div class="  boxh0 top10 paddingbottom20 t3 t2">
            	<div class="boxpadding zi_14size t3 bd"><strong>退款协议状态</strong></div>
                <div class="boxh2  t5"><div class="boxred floatleft"></div></div>
       		<div class="bd refund_pact">
       			买家（${refundDO.buyerNick}）已申请退款，等待卖家处理。
       		<div>
       </div> 
       
<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>
</div>
</div><!--右侧结束 -->
</div><!--bd结束 -->
</div>
</div>

<script src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>