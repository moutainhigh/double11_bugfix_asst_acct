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
     <form id="openShopForm" name="openShopForm" action="${base}/shop/showShopOpenAction.htm" method="post">
	      	<div class="ptitle">
				<h1>您现在正在申请开店流程中……</h1>
				<span class="eye"><a href="http://service.pinju.com/cms/html/help/selleropen/" target="_blank">开店帮助</a></span>
			</div>
			<p class="exinfo">完成以下操作即可成功开店。</p>
			<ul class="stepshow">
				<li class="item">
					<div class="title"><h2>选择店铺类型<br>签订开店协议</h2></div>					
					<div class="text">
						<p></p>
						<p>提交了开店信息或者缴纳了保证金后不可更改店铺类型</p>
					</div>
					<div class="btnrow"><a href="/shop/shopChoiceShopAction.htm" class="btn-opstart"><span>开始</span></a></div>
				</li>
				<li class="item disabled">
					<div class="title"><h2>交易账户设定</h2></div>					
					<div class="text">
						<ul class="subitem">
							<li>收款账号绑定</li>
							<li>委托退款服务协议签订</li>
						</ul>
					</div>
					<div class="btnrow"><a href="#" class="btn-opstart"><span>开始</span></a></div>
				</li>
				<li class="item disabled">
					<div class="title"><h2>提交开店信息</h2></div>					
					<div class="text">
						<ul class="subitem">
							<li>基本开店信息</li>
							<#if shopFlowInfoDO?exists && shopFlowInfoDO.sellerType!=0 && shopFlowInfoDO.sellerType!=3>
						  		<li>品牌信息</li>
							</#if>
							<li>企业证书</li>
						</ul>
					</div>
					<div class="btnrow"><a href="#" class="btn-opstart"><span>开始</span></a></div>
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
