<title>店铺--开店</title>
<link href="http://static.pinju.com/css/tdomain/tdomain.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />

<input type="hidden" value="red_shopOpen" id="my-page" />


<#setting classic_compatible=true>
<div class="ptitle">
				<h1>审核状态</h1>
				<span class="eye"><a href="/shop/iWillOpenShopAction.htm">返回</a></span>
			</div>
<div class="openshop">
	<#if shopOpenFlowDO?? && (shopOpenFlowDO.sellerType==1 || shopOpenFlowDO.sellerType==2 )>
			<#if shopOpenFlowDO.auditStatus == 0>
				<div class="pending-title">开店申请已提交，正在审核，请耐心等待...</div>
					<div class="pending-tip">
						<h3>审核进程：</h3>
					<ul>
						<#if shopOpenFlowDO.isOnlineAuditEnd == 1>
							<li>在线信息审核<span class="gray">已完成</span></li>
						<#else>
							<li>在线信息审核<span class="red">审核中...</span></li>
						</#if>
						<#if shopOpenFlowDO.isPostalAuditEnd == 1>
							<li>邮寄资料审核<span class="gray">已完成</span></li>
						<#else>
							<li>邮寄资料审核<span class="red">审核中...</span></li>
						</#if>
					</ul>
				</div>
			<#elseif  shopOpenFlowDO.auditStatus == 1>
				<div class="pending-title checkOkTitle"></div>
				<div class="pending-tip">
					<h3>审核进程：</h3>
					<ul>
						<li>在线信息审核<span class="gray">已完成</span></li>
						<li>邮寄资料审核<span class="gray">已完成</span></li>
					</ul>
				</div>
			<#elseif  shopOpenFlowDO.auditStatus == -1>
				<div class="tipstitle">
					<span class="icon-ossfail"></span><h1>很遗憾，您的开店信息未通过审核！</h1>
				</div>
				<div class="tipstext">
					<dl>
						<dt>审核进程：</dt>
						<dd>
							<ul>
								<#if shopOpenFlowDO.isOnlineAuditEnd == 1>
									<li>在线信息审核<span class="green">已通过</span></li>
								<#else>
									<li>在线信息审核<span class="red">未通过</span></li>
								</#if>
								<#if shopOpenFlowDO.isPostalAuditEnd == 1>
									<li>邮寄资料审核<span class="green">已通过</span></li>
								<#else>
									<li>邮寄资料审核<span class="red">未通过</span></li>
								</#if>
							</ul>
								<dl>
									<dt>未通过理由：</dt>
									<dd>
										<ol>
											<li>${shopOpenFlowDO.noPassReason}</li>
										</ol>
										<div class="btnrow">
											<a href="/shop/reopenShopAction.htm" class="btn-resubmit" type="submit"><span>重新提交</span></a>
		
										</div>
									</dd>
								</dl>
						</dd>
					</dl>
				</div>
			</#if>
	<#else>
	 	<#if shopOpenFlowDO.auditStatus == 0>
				<div class="pending-title">开店申请已提交，正在审核，请耐心等待...</div>
			<#elseif  shopOpenFlowDO.auditStatus == 1>
				<div class="pending-title checkOkTitle"></div>
				<div class="pending-tip">
					<h3>审核进程：已完成</h3>
				</div>
			<#elseif  shopOpenFlowDO.auditStatus == -1>
				<div class="tipstitle">
					<span class="icon-ossfail"></span><h1>很遗憾，您的开店信息未通过审核！</h1>
				</div>
				<div class="tipstext">
								<dl>
									<dt>未通过理由：</dt>
									<dd>
										<ol>
											<li>${shopOpenFlowDO.noPassReason}</li>
										</ol>
										<div class="btnrow">
											<a href="/shop/reopenShopAction.htm" class="btn-resubmit" type="submit"><span>重新提交</span></a>
										</div>
									</dd>
								</dl>
				</div>
			</#if>
	</#if>
</div>