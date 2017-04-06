<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<title>品聚网-退款管理- 退款详情</title>
	<div class="main20110627 cf">
	 		 <div class="positionbanner t4">您的位置：
	 		 	<a href="${base}/orderBuyer/orderManager.htm" class="mr10">已买到的商品 <i></i></a>&gt;
				<a href="${base}/refund/buyerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">退款详情</span>
				</div><!--顶部按钮条结束 -->
				
			<!--左侧 -->
			<#include "/default/templates/screen/refund/orderInfo.ftl">
 		    <!--左侧结束 -->
      		
	 <div class="  boxh760 floatright top10 "><!--右侧 -->	   
                <!--1、申请退货。。。进程图 -->
                <div class="boxh37">
				<img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" />
                <img src="http://static.pinju.com/images/refund/11_03.gif" width="131" height="37" />
				<img src="http://static.pinju.com/images/refund/11_10.jpg" width="125" height="37" />
				</div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          

         <div class="boxh0 top10 paddingbottom20 t2">
            
       	   <div class="boxpadding t3 t7 bottom10 bd">退款编号：${refundDO.id?c}</div>
			<ul>
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
       		  <li class="zi_shenhui">如果当前订单下的所有退款都协议达成或关闭，订单将会继续，请您注意检查订单超时限制。</li>
           </ul>
     </div> 
         <!------按钮以下第0部分结束 ------>  
          <!--提示信息开始 -->
          
           <div class="refund_tip_box hong">
         		<dl class="refund_tip">
		 	 	<dt>小提示：</dt>
		 	 	<dd>1.卖家已经同意您的退款协议，系统已记录。</dd>
		 	 	<dd>2.在您确认收货后，系统会自动退款给您。</dd>
		 	 	<dd>3.关于此退款如果您有任何疑问，请及时联系品聚官方客服。</dd>
		 	 	<dd></dd>
		 	 	</dl>
			</div>
			      
                <!------按钮以下第一部分 ------>
                
                <#include "/default/templates/screen/refund/refundInfo.ftl">
          		<!--按钮以下第一部分结束 -->
		  
		
           <!--按钮以下第二部分 -->
		  
           <div class="  boxh0 top10 paddingbottom20 t3 t2 cf">
            <#include "/default/templates/screen/refund/LeaveWordList.ftl">
           </div>
           
       <!--按钮以下第二部分结束 -->
		<input type="hidden" name="refundId" value="${refundDO.id?c}">
        <!--按钮以下第三部分 -->        
		<div class=" boxh0 top10 paddingbottom20 t2 t3">
      	<div class="boxpadding zi_14size t3 bd"> <strong>退款协议状态</strong></div>
	  	<div class="boxh2 t5 bottom10"><div class="boxred floatleft"></div> </div>
			<div class="bd refund_pact">
           		  退款协议达成。
           </div>
       
		</div><!--按钮以下第三部分结束 -->
            
            
          <!--按钮以下第四部分 -->
           <#include "/default/templates/screen/refund/refundLogisticSection.ftl">  
       <!--按钮以下第四部分结束 -->
            
            
            
            
<!--代表月亮消灭ie6 -->
 		<div class="boxh0"><span style="display:none;">hidden</span></div>             
      </div>     
</div><!--右侧结束 -->
      
</div><!--bd结束 -->      
