<#setting classic_compatible=true>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
<script src="http://static.pinju.com/js/refund/apply.js"></script>
<script src="http://static.pinju.com/js/refund/verifyPics.js"></script>
<script src="http://static.pinju.com/js/refund/showBigImage.js"></script>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<script src="http://static.pinju.com/artdialog/jquery.iframeTools.js?skin=pj"></script>
<script src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
<title>品聚网-退款管理- 申请退款</title>

<div class="main20110627 cf">
	<div class="positionbanner t4">您的位置： 
	 		 	<a href="${base}/orderBuyer/orderManager.htm" class="mr10">已买到的商品 <i></i></a>&gt;
				<a href="${base}/refund/buyerRefundList.htm" class="mr10">退款管理<i></i></a>&gt;
				<span class="red">申请退款</span>
			</div><!--顶部按钮条结束 -->
  
		<!--左侧 -->
    		<#include "/default/templates/screen/refund/orderInfo.ftl">
    	<!--左侧结束 -->
  
	 <div class=" boxh0 boxh760 floatright top10 "><!--右侧 -->	    
            <!--1、申请退货。。。进程图 -->
            <div class="boxh37">
                <img src="http://static.pinju.com/images/refund/11_02.gif" width="131" height="37"/>
                <img src="http://static.pinju.com/images/refund/11_03.gif" width="131" height="37" />
                <img src="http://static.pinju.com/images/refund/11_09.jpg" width="131" height="37" />
			</div><!--1、申请退货。。。进程图结束 -->
            
                <!------按钮以下部分 ------>
          <div class=" floatleft boxh0 t2 top10">
				<div class="boxpadding zi_14size t3 bd"><!--退款协议title -->
      				<strong>退款协议 </strong>
                </div>
                <div class="boxh2 t5">
                	<div class="boxred floatleft"></div>
                </div>
                
           <div class="cf top10"><!--退 的商品信息 -->
                         
	        <table>
	                  <tr align="center">
	                  	<td width="80px">&nbsp;</td>
	                    <td width="182" height="30px" align="center"><strong>商品名称</strong></td>
	                    <td width="125"><strong>单价（元）</strong></td>
	                    <td width="92"><strong>数量</strong></td>
	                    <td width="113"><strong>商品优惠（元）</strong></td>
	                    <td width="113"><strong>小计（元）</strong></td>
	                  </tr>
	                  <tr align="center">
	                    <td colspan="6"><span class="left20"><img src="http://static.pinju.com/images/refund/shuiping01.gif" width="708" height="1" /></span></td>
	         		  </tr>
	                  <tr align="center">
	                    <td width="80px" height="60px" ><div class="items_pic_box"><a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItem.snapId}"><img  src="${imageServer}${itemDO.picUrl}"  width="80" height="80" /></a></div></td>
						<td width="182px" align="center">
							<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItem.snapId}">${orderItem.itemTitle?default("")?html}</a>
								<p class="hui">
					       			<#if itemPropertyList?exists>
				                    	
				        				<#list itemPropertyList as itemProperty>
				        				${itemProperty.name?default("")?html}：${itemProperty.value?default("")?html}&nbsp;&nbsp;&nbsp;
				        				</#list>
				        				
				        			</#if>
				        		</p>
	                    </td>
	                    <td>${orderItem.originalPriceByYuan}</td>
	                    <td>${orderItem.buyNum}</td>
	                    <td >${orderItem.discountAmountByYuan} </td>
	                    <td >${orderItem.totalAmountByYuan}</td>
	                  </tr>
	          </table>                      
        </div>	<!--退 的商品信息 结束-->


		
        <div class=" floatleft boxh500 t6 t3"><!--提交表单 -->
        <form name="form" id="form" action="${base}/refund/saveApply.htm" enctype="multipart/form-data" method="post">
        <input id="_needSalesReturn" type="hidden" value="${(refundDO.needSalesReturn)!""}" />
        <input id="createdApplyReason" type="hidden" value="${(refundDO.applyReason)!""}" />
        <input id="createdRefundId" name="refundDO.id" type="hidden" value="${(refundDO.id?c)!""}" />
        <input id="orderItemId" name="orderItemId" type="hidden" value="${(orderItem.orderItemId)}" />
        <input id="xiaoji"  type="hidden" value="${maxValue}" />
        <input id="needPayMax" type="hidden" value="${needPayMax}" />
        <input type="hidden" name="${tokenName}" value="${token}">
        <input type="hidden" id="originalApplySum" name="originalApplySum" value="${(refundDO.applySumByYuan)!"0.00"}">
        
              <div class="boxh200x29 left280 top10"><!--退货选择框 -->
                     <input class="needSalesReturn" name="refundDO.needSalesReturn" type="radio" value="0" checked />&nbsp;不需要退货
                  &nbsp;&nbsp;&nbsp;
                 <#if  (order.orderState == 3)>
                  	 <input class="needSalesReturn" name="refundDO.needSalesReturn" type="radio" value="1" />&nbsp;需要退货
                  <#else>
                     <input class="needSalesReturn" name="refundDO.needSalesReturn" type="radio" value="1" disabled="disabled"/>&nbsp;需要退货
                 </#if>
                 
              </div><!--退货选择框结束 -->
              
              
                <ul>
                  <li>
                  <span>退款原因<font color='red'>*</font>：</span>
                    <select id="applyReason" name="refundDO.applyReason">
                      <option value="">----填写退款原因----</option>
                      <option value="1">未收到商品</option>
                      <option value='2'>卖家发货不及时</option>
                      <option value="3">与卖家协商一致退款</option>
                    </select>
                  </li>
                     <li>买家申请退款金额<font color='red'>*</font>：</span><input id="applySum" name="applySum" type="text" size="10" value="${(refundDO.applySumByYuan)!""}"/>&nbsp;元&nbsp;（退款金额不能超过<span class="cheng">${maxValue!"0.00"}元</span>）</li>
                     <li id="notReturnItem">支付给卖家金额：<span id="paysum">*******</span>元</li>
                    
                     <li><span>退款说明<font color='red'>*</font>：</span></li>
                     <p></p>
                     <li class="shuomingbox"><textarea id="applyMemo" name="refundDO.applyMemo"   style="width:400px;height:80px"  maxLength='300'>${(refundDO.applyMemo)!""}</textarea>
          				<p id='msgFont'class='hui'>（注：请详细描述您的理由，以便卖家和客服人员判断，限300字)</p>
                     </li>
                      <#if actionErrors?size gt 0> 
                    	 <#list actionErrors as message>  
       						<li style='padding-left: 65px; line-height: 20px;color:#BE0000;background:url("http://static.pinju.com/images/err_33.gif") no-repeat scroll 45px 5px'>
       						 ${message?default("")?html?replace("\r\n","<br>")}</li>  
       						
    					</#list> 
                     </#if> 
                     
                <#if refundDO??>
               		<#if refundDO.id?? && refundDO.pic1??>
						<li><label>附　件：</label>（点击图片可看大图）</li>
			 			<li class="left20">
			 				<div class="dajin0628-weiquanpicall left40">			
			       					<#if refundDO.pic1?? && refundDO.pic1 !="">
			   							<img src="${imageServer}${(refundDO.pic1)!}" width="94" height="94" class="showBigImage"/>
									</#if>
			   						<#if refundDO.pic2?? && refundDO.pic2 != "">
			   							<img src="${imageServer}${(refundDO.pic2)!}" width="94" height="94" class="showBigImage"/>
			   						</#if>
			   						<#if refundDO.pic3?? && refundDO.pic3 != "">
			   							<img src="${imageServer}${(refundDO.pic3)!}" width="94" height="94" class="showBigImage"/>
			   						</#if>
			   				</div>
						</li>
					</#if>
				</#if>
                <li>上传凭证：<span class="hui">可选项，每张图片大小不超过500K，最多3张  支持GIF、JPG、PNG格式</span></li>
                     <li class="shuomingbox"><span class="floatleft">1.<input id="file1" name="voucherPic" type="file" size="45"></span>&nbsp;&nbsp;</li>
                      	 <li class="shuomingbox"><span class="floatleft">2.<input id="file2" name="voucherPic" type="file" size="45"></span>&nbsp;&nbsp;</li>
                         <li class="shuomingbox"><span class="floatleft">3.<input id="file3" name="voucherPic" type="file" size="45"></span>&nbsp;&nbsp;</li>
                      	 <li>
                      	 	<iframe name="uploadIframe_file1" id="uploadIframe_file1" style="display:none;VISIBILITY: hidden"></iframe>
							<iframe name="uploadIframe_file2" id="uploadIframe_file2" style="display:none;VISIBILITY: hidden"></iframe>
							<iframe name="uploadIframe_file3" id="uploadIframe_file3" style="display:none;VISIBILITY: hidden"></iframe>
                      	 </li>
	
                         <li class="left280 top10"><a id="hrefApplyRefund" href="#" class="chackTextarea-btn"><img src="http://static.pinju.com/images/refund/applyRefund.gif" width="103" height="29" /></a></li>
						<li class="left280 top10" id="imageVerify" style="display: none; font-weight: normal; clear: both; text-align: left; margin: -15px 15px 30px 147px;">
							<img align="absmiddle" src="http://static.pinju.com/images/ajax/loadding.gif"> 
							图片验证中，请稍等...</li>
                </ul>    
                </form>
        </div><!--提交表单结束 -->
       
	</div><!--右侧退款提交表单结束 -->
  
  </div><!--右侧结束 -->
  
</div><!--bd结束 -->  
</div>
	
	
 <script type="text/javascript">
  $("#applyReason").val(${applyReason!""});
 
</script>