
<div class="rights_box cf">
	<div class="tit"><span class="txt">维权申请协议</span></div>			
	<ul class="rights_list rights_app">
		<li>
			<span>申请时间：</span>
			<div>${(rightsDO.applyDate)?string('yyyy-MM-dd HH:mm:ss')}</div>
		</li>
		<li>
			<span>维权原因：</span>
			<div>
				<#if rightsDO.voucherType=0>7天无理由退货
				<#elseif rightsDO.voucherType=1>有质量问题
				<#else>假一赔三</#if>
			</div>
		</li>
		<li>
			<span>维权要求：</span>
			<div><#if rightsDO.buyerRequire=0>退款<#else>退款退货</#if></div>
		</li>
		<li>
			<span>维权状态：</span>
			<div class="bd red">
				<#if rightsDO.state=0>等待卖家处理
	       		<#elseif rightsDO.state=1>
           			<#if rightsDO.buyerRequire=0>维权协议已达成，等待打款
           			<#elseif rightsDO.buyerRequire=1>等待买家退还商品</#if>	       		
	       		<#elseif rightsDO.state=2>维权申请被卖家拒绝
	       		<#elseif rightsDO.state=3 || rightsDO.state=11>等待卖家确认收货
	       		<#elseif rightsDO.state=4>客服介入中(客服会在5个工作日内处理)
	       		<#elseif rightsDO.state=5 || rightsDO.state=7>维权成功
	       		<#elseif rightsDO.state=6 || rightsDO.state=8>维权关闭
           		<#elseif rightsDO.state=9>
           			维权协议已达成，等待打款
           			<#--
           			<#if rightsDO.buyerRequire=0>维权协议已达成，等待打款
           			<#elseif rightsDO.buyerRequire=1>客服已同意，等待买家退还商品</#if>
           			-->
           		<#elseif rightsDO.state=10>
           			维权协议已达成，等待打款
           		<#elseif rightsDO.state=11>
           			客服已同意，等待卖家确认收货
           		<#elseif rightsDO.state=12>
           			客服已同意，等待买家退还商品
           		</#if> 
			</div>
		</li>
		<li><span>维权金额：</span><div>${(((rightsDO.buyerApplyPrice)!0)/100)?string('0.00')}元</div></li>
		<li><span>维权说明：</span><div>${((rightsDO.buyerReason)?html)!}</div></li>
		<li><span>账户名：</span><div><#if rightsDO.buyerBankAccountName??>${rightsDO.buyerBankAccountName?html}</#if></div></li>
		<li><span>卡号：</span><div><#if rightsDO.buyerBankAccountNo??>${rightsDO.buyerBankAccountNo?html}</#if></div></li>
		<li><span>开户行：</span><div><#if rightsDO.buyerOpenBankName??>${rightsDO.buyerOpenBankName?html}</#if></div></li>			
		<li>
			<span>附件：</span>
			<div class="picbox">
				<#if rightsDO.voucherPic1?? && rightsDO.voucherPic1 !="">
				<a href="${imageServer!}${(rightsDO.voucherPic1)!}" target="_blank"><img src="${imageServer!}${(rightsDO.voucherPic1)!}" width="94" height="94"/></a>
				</#if>
				<#if rightsDO.voucherPic2?? && rightsDO.voucherPic2 != "">
				<a href="${imageServer!}${(rightsDO.voucherPic2)!}" target="_blank"><img src="${imageServer!}${(rightsDO.voucherPic2)!}" width="94" height="94"/></a>
				</#if>
				<#if rightsDO.voucherPic3?? && rightsDO.voucherPic3 != "">
				<a href="${imageServer!}${(rightsDO.voucherPic3)!}" target="_blank"><img src="${imageServer!}${(rightsDO.voucherPic3)!}" width="94" height="94"/></a>
				</#if>				
			</div>
		</li>
	</ul>	
</div>
