<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<script src="http://static.pinju.com/js/refund/sellerRefundManage.js"></script>
<script src="http://static.pinju.com/js/refund/inputRefundManual.js"></script>
<title>品聚网-退款管理- 退款详情</title>
<div class="main20110627 cf">
	 		 <div class="positionbanner t4">您的位置： 
	 		 	<a href="${base}/orderBuyer/orderManager.htm" class="mr10">已买到的商品 <i></i></a>&gt;
				<a href="${base}/refund/buyerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">退款详情</span>
			</div><!--顶部按钮条结束 -->
 		   
 		    <#include "/default/templates/screen/refund/orderInfo.ftl">
 		    
	  <div class=" boxh760 floatright top10 "><!--右侧 -->	    
                <!--1、申请退货。。。进程图 -->
                <div class=" boxh37">
                    <img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_09.jpg" width="131" height="37" />
  </div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          
          
          
         <div class=" top10 paddingbottom20 t2 cf">
            
       	   <div class="boxpadding  t3 t7 bottom10 bd">退款编号：${refundId.toString()}</div>
			<ul class="refund_text">
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
                     
           </ul>

       </div> 
		<div class="refund_tip_box hong">
			<dl class="refund_tip">
				<dt>小提示：</dt>
				<dd>1.很抱歉，由于支付系统问题造成退款失败，在买家提交收款信息后工作人员将尽快完成退款。</dd>
				<dd>客服电话：4008-211-588</dd>
			</dl>
		
		</div>
		
		<form name="failForm" id="failForm" action="saveWorkOrder.htm" method="post">
		<input id="refundId" name="refundId" type="hidden" value="${refundId?c}" />
		<div class="refund-failure-pop">
	<div class="tit">
		<span class="txt" >退款失败补填信息</span>
	</div>
	<ul class="refund-failure-content">
		<li>
			<span>银行户名：</span>
			<div>
				<input type="text" name="bankUsername" id="bankUsername" />
			</div>
		</li>
		<li><span>开户行：</span><div><input type="text" name="bankName" id="bankName"><b>请填写您的开户银行 如：工商银行上海分行漕宝路支行</b></div></li>
		<li><span>银行账号：</span><div><input name="bankAccount" id="bankAccount" type="text"></div></li>
		<li>
			<span>补充说明：</span>
			<div>
			
			<p class="red">请认真正确填写您的银行卡信息，我们会在收到您的信息后尽快将您申请的退款金额打给您。</p>
			</div>
		</li>
		<li>
			<span>　</span>
			<div>
				<button type="button" class="refund-failure-btn submit" id="confirmSaveRefundManual"/>确　认</button>
			</div>
		</li>
	</ul>

	</div>
	</form>



          
          
          <#include "/default/templates/screen/refund/refundInfo.ftl">
		  
	  <!--按钮以下第四部分 -->
		  <div class="boxh0 top10 paddingbottom20 t3 t2 cf">
            <#include "/default/templates/screen/refund/LeaveWordList.ftl">	
		  </div>


<#include "/default/templates/screen/refund/refundLogisticSection.ftl">  


           <div class="  boxh0 top10 paddingbottom20 t3 t2">
            	<div class="boxpadding zi_14size t3 bd"><strong>退款协议状态</strong></div>
                <div class="boxh2  t5"><div class="boxred floatleft"></div></div>
       		<div class="bd refund_pact">
       			系统问题，退款失败。
       		<div>
       </div> 

<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>
</div>
</div><!--右侧结束 -->
</div><!--bd结束 -->
</div>
</div>