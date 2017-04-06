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
      		
	 <div class=" boxh0 boxh760 floatright top10 "><!--右侧 -->	   
                <!--1、申请退货。。。进程图 -->
                <div class="boxh37">
		<img src="http://static.pinju.com/images/refund/11_01.gif" width="131" height="37" /><img src="http://static.pinju.com/images/refund/11_04.gif" width="131" height="37" /><img src="http://static.pinju.com/images/refund/11_09.jpg" width="125" height="37" /></div><!--1、申请退货。。。进程图结束 -->
                <!------按钮以下第0部分 ------>          

         <div class=" top10 paddingbottom20 t2">
            
       	   <div class="boxpadding  zi_14size t3 bd bottom10">退款编号：${refundId.toString()}</div>
			<ul>
       		  <li class="hong"><strong class="bd">当前退款状态：${refundDO.refundStateDisplay}</strong></li>
       		    <li class="zi_shenhui">您还有<span> <span class="hong" id="deadline" leftDay="${outDateSec?c}"></span> 处理本次退款协议</span></li>
       		  	<li class="zi_shenhui">卖家已经同意了您的退款申请，退款协议达成，请您在5天内将商品发出退还给卖家，否则系统默认关闭退款，请及时操作。</li>   
           </ul>
           
            <div class="boxh0 top10">
              <ul>
             	<li class="cf">
                 	<span class="li01"><a href="#leaveword"><img src="http://static.pinju.com/images/refund/20_02.gif" width="99" height="28" /></a></span>
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
				<dd>1.请及时将商品退还给卖家并提交物流信息，避免超过5天退款关闭，退款关闭后您将无法再次申请退款。</dd>
				<dd>2.如果您希望关闭退款，您可以及时向卖家留言，不进行物流退货操作，超过5天后退款将关闭。</dd>
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
          
	<form name="form" id="form" action="${base}/refund/deliverGoods.htm" method="post">
		<input type="hidden" name="refundId" value="${refundDO.id?c}">
        <!--按钮以下第三部分 -->        
		<div class=" boxh0 top10 paddingbottom20 t2 t3">
      	<div class="boxpadding zi_14size t3 bd"> <strong>退款协议状态</strong></div>
	  	<div class="boxh2 t5 bottom10"><div class="boxred floatleft"></div> </div>
                
			<div class="bd refund_pact">
			卖家（${order.sellerNick}）已经同意了您的退款申请，退款协议达成。<br/>
			买家（${order.buyerNick}）需要在5天内将商品发出退还卖家，否则系统将关闭本退款申请，请您及时操作。
           </div>
       
		</div><!--按钮以下第三部分结束 -->
            
            
            
            <!--按钮以下第四部分 -->
            <div class="boxh0 top10 paddingbottom20  t2">
                <div class="boxpadding zi_14size t3 bd"><!--退款协议title --><strong>退货物流信息</strong></div>
                <div class="boxh2  t5">
                <div class="boxred floatleft"></div>
                </div>
                
             <div class="boxh0 top10"><!--退 的商品信息 -->
                            
            		<table id="tableId">
                      <tr align="center">
                        <td width="183" height="30px" valign="middle">物流名称</td>
                        <td width="225" valign="middle">运单号码</td>
                        <td width="160" valign="middle">备注</td>
                        <td width="169" valign="middle">操作</td>
                      </tr>
                      <tr align="center">
                        <td colspan="4" valign="middle"><img src="http://static.pinju.com/images/refund/shuiping01.gif" width="708" height="1" /></td>
             		  </tr>
                       <#include "/default/templates/screen/refund/wuliuInfo.ftl"> 
              </table> 
                                   
            </div>   
                
            </div><!--按钮以下第四部分结束 -->
            
            
            
            
            
<!--代表月亮消灭ie6 -->
 		<div class="boxh0"><span style="display:none;">hidden</span></div>             
      </div>
         
        
</div><!--右侧结束 -->
      
</div><!--bd结束 -->      
</form>
