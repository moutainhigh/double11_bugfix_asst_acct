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
                    <img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" />
                    <img src="http://static.pinju.com/images/refund/11_09.jpg" width="131" height="37" />
  </div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          
          
          
         <div class=" top10 paddingbottom20 t2 cf">
            
       	   <div class="boxpadding  zi_14size t3 bd bottom10">退款编号：${refundId.toString()}</div>
			<ul class="refund_text">
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
                    <li class=" zi_shenhui">客服将会根据买卖双方提交的凭证及留言记录对本退款进行裁决，裁决结果为最终结果，如果买卖双方对于裁决结果有异议，可以拨打客服电话（4008-211-588）对客服人员的裁决结果进行申诉。</li>
                     
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
				<dd>1.客服人员会在5个工作日内进入本退款申请，请耐心等待。</dd>
				<dd>2.客服会在买卖双方都同意的情况下修改退款金额，所以买家卖家可以在本页面进行留言沟通，确定金额后通知客服修改退款金额。</dd>
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
		  
		 <!--第四部分结束 -->
       

<#include "/default/templates/screen/refund/refundLogisticSection.ftl">  


           <div class="  boxh0 top10 paddingbottom20 t3 t2">
            	<div class="boxpadding zi_14size t3 bd"><strong>退款协议状态</strong></div>
                <div class="boxh2  t5"><div class="boxred floatleft"></div></div>
       		<div class="bd refund_pact">
       				客服介入中。
       		<div>
       </div> 

<div class=" floatleft boxh0"><span style="display:none;">hidden</span></div>
</div>
</div><!--右侧结束 -->
</div><!--bd结束 -->
</div>
</div>