<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/js/refund/checkApply.js"></script>
<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<title>品聚网-退款管理- 退款详情</title>
    <div class=" main20110627 cf">
	 		 <div class="positionbanner t4">您的位置：
	 		  <a href="${base}/orderBuyer/orderManager.htm" class="mr10">已买到的商品 <i></i></a>&gt;
				<a href="${base}/refund/buyerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">退款详情</span>	</div>
				<!--顶部按钮条结束 -->
             
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
				<li class="zi_shenhui">客服将会根据买卖双方提交的凭证及留言记录对本退款进行裁决，裁决结果为最终结果，如果买卖双方对于客服裁决结果有异议，可以拨打客服电话（4008-211-588）对客服人员的裁决结果进行申诉。</li>
           </ul>
             
			<div class="boxh0 top10">
              <ul>
             	<li class="cf">
                 <!-- 客服介入-->
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
		 	 	<dd>1.客服工作人员会在5个工作日内进入本退款申请，请耐心等待。</dd>
		 	 	<dd>2.客服人员会在买卖双方都同意的情况下修改退款金额，所以买家和卖家可以在本页面进行留言沟通，双方确定一致后客服人员即会按照双方要求修改退款金额。</dd>
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
      			  	客服介入中。
      			</div>
      		</div>
            
            
	        <!--按钮以下第四部分 -->
	        
	           <#include "/default/templates/screen/refund/refundLogisticSection.ftl">  
	           
	       <!--按钮以下第四部分结束 -->  
	             
<!--代表月亮消灭ie6 -->
 		<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>           
            
      </div>

</div><!--右侧结束 -->
      
</div><!--bd结束 -->  