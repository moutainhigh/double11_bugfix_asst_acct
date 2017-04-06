<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script src="http://static.pinju.com/js/refund/leftDay.js"></script>
<script src="http://static.pinju.com/js/refund/checkApply.js"></script>
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
          
         <div class=" top10 paddingbottom20 t2 cf">
            
       	   <div class="boxpadding t3 t7 bottom10 bd"><!--退款编号title -->退款编号：${refundDO.id}</div>
			<ul class="refund_text">
              		<li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
                     <li class="zi_shenhui">卖家（${order.sellerNick}）还有<span> <span class="hong" id="deadline" leftDay="${outDateSec?c}"></span> 处理本次退款协议</span></li>
                    <li class="zi_shenhui">如果您未能在该期限内与卖家（${order.sellerNick}）就此退款协议做出同意退款申请或者拒绝退款申请的确认。本次退款申请将会自动达成协议， 并根据是否需要退货将退款给您或需要您将商品退还卖家。</li>
				    <li class="zi_shenhui">您可以在卖家确认此退款申请前修改此协议金额和内容。</li>
				    <li class="zi_shenhui">如果您想放弃退款申请继续完成交易，可以直接点击关闭退款按钮，此退款将会被关闭。</li>
				 
           </ul>
             
			<div class="boxh0 top10">
              <ul>
             	<li class="cf">
             		<!--退款申请中-->
                <span class="li01"> <a href="${base}/refund/updateApply.htm?oid=${(orderItem.orderItemId)}"><img src="http://static.pinju.com/images/refund/13_03.gif" width="103" height="29" /></a></span>
                <span class="li01"><a href="${base}/refund/cancelRefundApply.htm?refundId=${refundDO.id}" 
                onclick="if(confirm('退款申请只能针对该商品发起一次，如果您关闭该退款申请，您将无法再次发起，但您可以在交易完成后发起维权。')){return true;}return false;">
                <img src="http://static.pinju.com/images/refund/20_08.gif" width="103" height="28" title="关闭退款"/></a></span>
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
				<dd>1.该订单中针对此商品仅能申请一次退款，请您准确填写退款要求，多与卖家沟通，及时修改退款协议内容，避免无效退款申请。<dd>
				<dd>2.如果您不想撤销退款申请，请勿点击关闭退款按钮，否则您将无法继续发起退款申请，如果万一关闭了退款申请，您还可以在交易成功后发起售后维权。<dd>
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
      				买家（${order.buyerNick}）已 申请退款，等待卖家处理。
      			</div>
      		</div>
            
<!--代表月亮消灭ie6 -->
 		<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>           
            
      </div>

</div><!--右侧结束 -->
      
</div><!--bd结束 -->  	