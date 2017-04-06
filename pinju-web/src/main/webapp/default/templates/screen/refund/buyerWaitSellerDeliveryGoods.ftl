<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/js/refund/deliveryGoods.js"></script>
<script src="http://static.pinju.com/countdown/jquery.countdown.js"></script>
<script src="http://static.pinju.com/js/refund/leftDay.js"></script>
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
      		
	 <div class=" boxh760 floatright top10 "><!--右侧 -->	   
                <!--1、申请退货。。。进程图 -->
                <div class="boxh37">
		<img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" /><img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" /><img src="http://static.pinju.com/images/refund/11_09.jpg" width="125" height="37" /></div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          

         <div class="boxh0 top10 paddingbottom20 t2">
            
       	   <div class="boxpadding t3 t7 bottom10 bd">退款编号：${refundDO.id?c}</div>
			<ul>
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
       		  	 <li class="zi_shenhui">卖家还有<span> <span class="hong" id="deadline" leftDay="${outDateSec?c}"></span> 处理本次退款协议</span></li>
       		  	<li class="zi_shenhui">您的退还的商品已经发出，正在等待卖家（${order.sellerNick}）确认收货，如果卖家对您退还商品有异议申请客服介入或未在<#if tradeRefundLogisticsDO.logisticsType == 1>30<#else>10</#if>天内确认收货，客服工作人员会在5个工作日内介入该笔退款争议。</li>  
           </ul>
           
            <div class="boxh0 top10">
              <ul>
             	<li class="cf">
                	<span class="li01"><a href="#leaveword"><img src="http://static.pinju.com/images/refund/11_13.jpg" width="147" height="29" /></a></span>
                </li> 
              </ul>
              </div>
     </div> 
         <!------按钮以下第0部分结束 ------>  
          <!--提示信息开始 -->
          
           <div class="refund_tip_box hong">
			<dl class="refund_tip">
				<dt>小提示：</dt>
				<dd>1.请确保您的物流凭证保存完好，如果进入客服介入，可能需要您提供相关证据。<dd>
			</dl>
			</div>
			      
                <!------按钮以下第一部分 ------>
                
                <#include "/default/templates/screen/refund/refundInfo.ftl">
          		<!--按钮以下第一部分结束 -->
		  
		
           <!--按钮以下第二部分 -->
		  
           <div class="  boxh0 top10 paddingbottom20 t3 t2">
            <#include "/default/templates/screen/refund/LeaveWordList.ftl">
            
             <form action="${base}/refund/saveLeaveWord.htm" id="leaveWordform" name="leaveWord_form"    
          				enctype="multipart/form-data" method="post" target="leaveWord_hidden_frame">  
             	<input type="hidden" name="userType" value="1"/>
             	<#include "/default/templates/screen/refund/LeaveWord.ftl">
            </form>
           </div>
           
       <!--按钮以下第二部分结束 -->
          
	<form name="form" id="form" action="${base}/refund/updateGoodsWuliuInfo.htm" method="post">
		<input type="hidden" name="refundId" id="refundId" value="${refundDO.id?c}">
        <!--按钮以下第三部分 -->        
		<div class=" boxh0 top10 paddingbottom20 t2 t3">
      	<div class="boxpadding zi_14size t3 bd"> <strong>退款协议状态</strong></div>
	  	<div class="boxh2 t5 bottom10"><div class="boxred floatleft"></div> </div>
                
			<div class="bd refund_pact">
			您已退还商品，等待卖家确认收货。
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
</form>
