<title>店铺--开店</title>
<link href="http://static.pinju.com/css/member/member.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<input type="hidden" value="red_shopOpen" id="my-page" />
<div class="openshop">

			<div class="ptitle">
				<h1>您现在正在申请开店流程中……</h1>
				<span class="eye"><a href="/shop/showPayMarginPageAction.htm">返回</a></span>
			</div>
			
			<div class="infowrap">
				<ol class="osstatus">
					<li><img src="http://static.pinju.com/img/openshop-status.png" alt="缴纳保证金" /></li>
					<li class="icon-lighter">
						<span></span><p>如果您已经完成了缴纳保证金，请点击“进入卖家中心”按钮完成开店流程！</p>
					</li>
					<li><a id="endOpenFlow" onclick="endOpenFlow()" class="btn-2seller"><span>进入卖家中心</span></a></li><br><br><br>
					<#if errorMessage?? && errorMessage!="">
						<li><span></span><p>${errorMessage}</p></li>
					</#if>
				</ol>

			</div>
		</div>
		
		<div class="bdtenpay">
			<div class="row">

				<div class="title"><a name="bindtips"></a><h3>绑定常见问题：</h3></div>
				<ul>
					<li><strong>1.入驻品聚网流程是怎样的？</strong><br>
						<span>品聚天使</span>：您好，1. 在线提交入驻申请并签署相关协议条款； 2. 线下提交成为品聚网商户所需资质资料，并等待品聚网人工审核。</li>
					<li><strong>2.个体工商户可以申请品聚开店吗?</strong><br>
						<span>品聚天使</span>：您好，很抱歉，目前品聚招商的对象只针对企业性质的企业或者公司。</li>

					<li><strong>3.申请入驻品聚网之后，企业相关信息变动，该怎么办？</strong><br> 
						<span>品聚天使</span>：您好，首先商家取得工商局的核准变更证明后申请第三方支付 账户对应公司名称的变更；其次品聚账户对应公司名完成变更后，应及时到品聚网或与品聚网联系办理资料变更手续。否则，商户对因此导致的损失自行承担责任。</li>
				</ul>
			</div>
		</div>
		
<script>
	function endOpenFlow(){
		$("#endOpenFlow").attr("onclick","");
		window.location.href = "/shop/shopOpenEndAction.htm?messageType=1";
		
	}
</script>