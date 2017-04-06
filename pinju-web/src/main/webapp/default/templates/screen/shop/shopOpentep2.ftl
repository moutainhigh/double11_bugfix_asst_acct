<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/tdomain/tdomain.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shopOpen.js?t=20111202.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shopOpen.js"></SCRIPT>-->
<#setting classic_compatible=true> 
<input type="hidden" value="red_shopOpen" id="my-page" />
		<div class="openshop">
		<#if errorMessage?? && errorMessage!="">
				<div class="shop_pointer_style">
					<p class="red">${errorMessage}</p>
				</div>
        </#if>
     <form id="openShopForm" name="openShopForm" action="/shop/changeSellTypeAction.htm" method="post">
	      <div class="ptitle">
				<h1>您现在正在申请开店流程中……</h1>
				<span class="eye"><a href="http://service.pinju.com/cms/html/help/selleropen/" target="_blank">开店帮助</a></span>
			</div>
			<p class="exinfo">完成以下操作即可成功开店。</p>
			
				<ul class="stepshow">
				<li class="item finished">
					<div class="title"><h2>选择店铺类型<br>签订开店协议</h2></div>					
					<div class="text">
						<p><#if shopFlowInfoDO??><em>当前店铺类型：</em><strong><#if shopFlowInfoDO.sellerType==1>品牌店<#elseif shopFlowInfoDO.sellerType==2>旗舰店<#elseif shopFlowInfoDO.sellerType==0>普通店<#else>i小铺</#if></strong></#if></p>
						<p>提交了开店信息或者缴纳了保证金后不可更改店铺类型</p>
					</div>
					<div class="btnrow"><#if shopFlowInfoDO?exists && shopFlowInfoDO.auditStatus==-100><a href="#" class="btn-cshoptype" onclick="changeSellyType()"><span>更改店铺类型</span></#if></a></div>
					<span class="icon-fns"></span>
				</li>
				<li class="item">
					<div class="title"><h2>交易账户设定</h2></div>					
					<div class="text">
						<ul class="subitem">
						<#if shopFlowInfoDO?exists && shopFlowInfoDO.tenpayBind==1>
				           <li class="fns">收款账号绑定</li>
						<#else>
							<li>收款账号绑定</li>
						</#if>
						<#if shopFlowInfoDO?exists && shopFlowInfoDO.tenpaySign==1>
			                <li class="fns">委托退款服务协议签订</li>
						<#else>	
							<li>委托退款服务协议签订</li>
					     </#if>
						</ul>
					</div>
					<div class="btnrow">
						<#if shopFlowInfoDO?exists && shopFlowInfoDO.isAccountSet==0>
									<a href="${base}/tenpay/accountNextAction.htm" class="btn-opcontinue"><span>继续</span></a>
						<#elseif shopFlowInfoDO?exists && shopFlowInfoDO.isAccountSet==-1>
								<a href="${base}/tenpay/accountNextAction.htm" class="btn-opstart"><span>开始</span></a>
						</#if>
					</div>
				</li>
				<li class="item">
				
					<div class="title"><h2>提交开店信息</h2></div>
					<#if shopFlowInfoDO?exists && shopFlowInfoDO.auditStatus==-1>
					    <div class="text">
								<ul class="subitem">
					              <li class="fns">基本开店信息</li>
								  <li class="fns">品牌信息</li>
								  <li class="fns">企业证书</li>
							  </ul>
						</div>
						<div class="btnrow">
								<a href="/shop/showAuditStatusPageAction.htm" class="btn-opstatus"><span>查看审核状态</span></a>
						</div>
			  		<#else>				
						<div class="text">
								<ul class="subitem">
									<#if shopFlowInfoDO?exists && shopFlowInfoDO.isFillStep1==1>
							           <li class="fns">基本开店信息</li>
									<#else>
									  <li>基本开店信息</li>
									</#if>
									<#if shopFlowInfoDO?exists && shopFlowInfoDO.sellerType!=0 && shopFlowInfoDO.sellerType!=3>
										<#if shopFlowInfoDO?exists && shopFlowInfoDO.isFillStep3==1>
									       <li class="fns">品牌信息</li>
										<#else>
										  <li>品牌信息</li>
										</#if>
									</#if>
									<#if shopFlowInfoDO?exists && shopFlowInfoDO.isFillStep2==1>
								        <li class="fns">企业证书</li>
									<#else>
									      <li>企业证书</li>
									</#if>
							  </ul>
						</div>
						<div class="btnrow">
							  <#if shopFlowInfoDO?exists && shopFlowInfoDO.isFillComplete==0 || (shopFlowInfoDO.isFillComplete==1 && shopFlowInfoDO.auditStatus==-100)>
									<a href="/shop/choiceFillIndexAction.htm" class="btn-opcontinue"><span>继续</span></a>
								<#elseif shopFlowInfoDO?exists && shopFlowInfoDO.isFillComplete==-1>
									<#if shopFlowInfoDO?exists && shopFlowInfoDO.auditStatus==-1>
										<a href="/shop/showAuditStatusPageAction.htm" class="btn-opstatus"><span>查看审核状态</span></a>
									<#else>
										<a href="/shop/choiceFillIndexAction.htm" class="btn-opstart"><span>开始</span></a>
									</#if>
								<#elseif shopFlowInfoDO?exists && shopFlowInfoDO.auditStatus==0>
									<a href="/shop/showAuditStatusPageAction.htm" class="btn-opstatus"><span>查看审核状态</span></a>
								</#if>
						</div>
				  </#if>
				</li>
				<li class="item disabled">
					<div class="title"><h2>缴纳保证金</h2></div>					
					<div class="text">
						<p>您还没有缴纳保证金</p>
					</div>
					<div class="btnrow"><a href="#" class="btn-opstart"><span>开始</span></a></div>
				</li>
			</ul>
			
   		</form>	
   		</div>  
