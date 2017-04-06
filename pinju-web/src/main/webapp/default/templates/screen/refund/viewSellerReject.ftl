<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>

<script src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
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
 		    
	  <div class=" boxh760 floatright top10 "><!--右侧 -->	    
                <!--1、申请退货。。。进程图 -->
                <div class=" boxh37">
                    <img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_09.jpg" width="131" height="37" />
 	  </div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          
          
          
         <div class=" top10 paddingbottom20 t2 cf">
            
       	   <div class="boxpadding  zi_14size t3 bd bottom10">退款编号：${refundId.toString()}</div>
			<ul class="refund_text">
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
                    <li class=" zi_shenhui">买家（${refundDO.buyerNick}）还有 <span class="hong" id="deadline" leftDay="${leftDaySec?c}"> </span>处理本次退款协议</li>
                    <li class=" zi_shenhui">如果买家 未在您   拒绝退款申请后的5天内申请客服介入处理，那么系统将自动默认买家同意了您的拒绝操作，此退款申请将被关闭且无法再次申请。</li> 
           </ul>
             
			  <div class="boxh0 top10">
              <ul>
             	<li class="cf">
                <span class="li01"><a href="#leaveword" ><img src="http://static.pinju.com/images/refund/11_13.jpg" width="147" height="29" /></a></span>
                
                </li>
                 
              </ul>
              </div>
       </div> 
		<div class="refund_tip_box hong">
			<dl class="refund_tip">
				<dt>小提示：</dt>
				<dd>1.您应该保管好您的证据，买家可能会在被您拒绝退款后申请客服介入，届时需要您提供相应的凭证。</dd>
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
		  
       
       <!--按钮以下第四部分结束 -->
       
       

           <div class="  boxh0 top10 paddingbottom20 t3 t2">
            	<div class="boxpadding zi_14size t3 bd"><strong>退款协议状态</strong></div>
                <div class="boxh2  t5"><div class="boxred floatleft"></div></div>
       		<div class="bd refund_pact">
       			卖家（${refundDO.sellerNick}）已经拒绝了退款申请。
       		<div>
       </div> 

<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>
</div>
</div><!--右侧结束 -->
</div><!--bd结束 -->
</div>
</div>

