<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/js/refund/checkApply.js"></script>
<script src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script src="http://static.pinju.com/js/refund/leftDay.js"></script>
<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<title>品聚网-退款管理- 退款详情</title>
    <div class=" main20110627 cf">
	 		  <div class="positionbanner t4">您的位置：
	 		 	<a href="${base}/orderBuyer/orderManager.htm" class="mr10">已买到的商品 <i></i></a>&gt;
				<a href="${base}/refund/buyerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">退款详情</span>
				</div><!--顶部按钮条结束 -->
             
            <!--左侧 -->
			<#include "/default/templates/screen/refund/orderInfo.ftl">
 		    <!--左侧结束 -->
 		    
			<div class=" boxh760 floatright top10 "><!--右侧 -->	    
                <!--1、申请退货。。。进程图 -->
                <div class=" boxh37 ">
                    <img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" />
						<img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" />
						<img src="http://static.pinju.com/images/refund/11_09.jpg" width="125" height="37" />
					</div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          
          
         <div class=" boxh0 top10 paddingbottom20 t2">
            
       	   <div class="boxpadding t3 t7 bottom10 bd"><!--退款编号title -->退款编号：${refundDO.id}</div>
			<ul>
              		 <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
				   	<li class="zi_shenhui">您还有<span class="hong" id="deadline" leftDay="${outDateSec?c}"></span> 处理本次退款协议</span></li>
				   	<li class="zi_shenhui">如果您未在卖家拒绝退款申请后的5天内申请客服介入处理，那么系统将自动默认认为您同意了卖家拒绝退款申请操作，此退款申请将被关闭且无法再次申请。</li>
           </ul>
             
			<div class="boxh0 top10">
              <ul>
             	<li class="cf">
             	
  				<!-- 卖家拒绝退款-->	
                <span class="li01"><a href="${base}/refund/cancelRefundApply.htm?refundId=${refundDO.id}" 
               	 onclick="if(confirm('请您确认要关闭该退款申请，关闭后您将无法再次发起退款申请，请确认。')){return true;}return false;">
                <img src="http://static.pinju.com/images/refund/20_03.gif" width="147" height="29" /></a></span>
                
                <span class="li01"><a href="${base}/refund/custProcess.htm?refundId=${refundDO.id}"
                onclick="if(confirm('您确定申请客服介入吗？')){return true;}return false;">
                <img src="http://static.pinju.com/images/refund/11_14.jpg" width="147" height="29" /></a></span>
                <span class="li01"><a href="#leaveword"><img src="http://static.pinju.com/images/refund/11_13.jpg" width="147" height="29" /></a></span>
                </li>
                 
              </ul>
              </div>
         </div> 
         <!------按钮以下第0部分结束 ------>
         <!---提示信息开始-->
       
         <div class="refund_tip_box hong">
        
		 	 <dl class="refund_tip">
		 	 	<dt>小提示：</dt>
		 	 	<dd>1.请及时申请客服介入，避免超过5天系统默认您认可卖家的拒绝，退款关闭后您将无法再次申请退款。</dd>
		 	 	<dd>2.如果您不希望关闭退款，请不要点击确认关闭退款申请按钮，否则退款关闭，交易恢复正常。</dd>
		 	 </dl>
		</div> 
 
		  <!---提示信息结束-->
		      
           <!------按钮以下第一部分 ------>
                <#include "/default/templates/screen/refund/refundInfo.ftl">
          <!--按钮以下第一部分结束 -->
          
          
          
		  <div class="boxh0 top10 paddingbottom20 t3 t2 cf">
            <#include "/default/templates/screen/refund/LeaveWordList.ftl">
		  	 <form action="${base}/refund/saveLeaveWord.htm" id="leaveWordform" name="leaveWord_form"    
          				enctype="multipart/form-data" method="post" target="leaveWord_hidden_frame">  
		  		<input type="hidden" name="userType" value="1"/>
  				<#include "/default/templates/screen/refund/LeaveWord.ftl">
  		  	</form>
		  </div>
       
        <!--按钮以下第四部分结束 -->
		<div class="boxh0 top10 paddingbottom20 t2 t3">
            
            <A NAME="leaveword"></a>
            
            	<div class="boxpadding zi_14size t3 bd"><!--留言/凭证记录title --><strong>退款协议状态</strong></div>
                
      			<div class="boxh2 t5 bottom10"><div class="boxred floatleft"></div> </div>
      			 <div class="bd refund_pact">
      			 	卖家 （${order.sellerNick}）已经拒绝退款申请。
      			</div>
      		</div>
            
<!--代表月亮消灭ie6 -->
 		<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>           
            
      </div>

</div><!--右侧结束 -->
      
</div><!--bd结束 -->     	