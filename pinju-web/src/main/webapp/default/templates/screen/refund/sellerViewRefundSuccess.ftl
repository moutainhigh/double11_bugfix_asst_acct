<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>

<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<script src="http://static.pinju.com/js/refund/sellerRefundManage.js"></script>
<title>品聚网-退款管理- 退款详情</title>
<div class="main20110627 cf">

	 <div class="positionbanner t4">您的位置：
	 		 	<a href="${base}/orderSeller/ordermanagesell.htm" class="mr10">已卖出的商品 <i></i></a>&gt;
				<a href="${base}/refund/sellerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">退款详情</span>
		</div><!--顶部按钮条结束 -->
 		   
 	<#include "/default/templates/screen/refund/orderInfo.ftl">
 		    
	  <div class=" boxh760 floatright top10 "><!--右侧 -->	    
                <!--1、申请退货。。。进程图 -->
                <div class=" boxh37">
                    <img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_03.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_10.jpg" width="131" height="37" />
  </div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          
          
          
         <div class=" top10 paddingbottom20 t2 cf">
            
       	   <div class="boxpadding  zi_14size t3 bd bottom10">退款编号：${refundId.toString()}</div>
			<ul class="refund_text">
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
                     
           </ul>

       </div> 

          
          
          <#include "/default/templates/screen/refund/refundInfo.ftl">
		  
	  <!--按钮以下第四部分 -->

           <div class="boxh0 top10 paddingbottom20 t3 t2 cf">
            <#include "/default/templates/screen/refund/LeaveWordList.ftl">	
		  </div>

           <div class="  boxh0 top10 paddingbottom20 t3 t2">
            	<div class="boxpadding zi_14size t3 bd"><strong>退款协议状态</strong></div>
                <div class="boxh2  t5"><div class="boxred floatleft"></div></div>
       		<div class="bd refund_pact">
       			您已经同意了买家的退款申请，退款协议达成，请检查您的支付账户。
       		<div>
       </div> 

<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>
</div>
</div><!--右侧结束 -->
</div><!--bd结束 -->

</div>
</div>