<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/tdomain/tdomain.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shopOpen.js?t=20111202.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shopOpen.js"></SCRIPT>-->
<#setting classic_compatible=true> 
	<div id="doshop">
	    <form id="openShopForm" name="openShopForm" action="${base}/shop/shopShowAgreementAction.htm" method="post">
	   		<input type="hidden" value="red_shopOpen" id="my-page" />
	   		<#if shopOpenFlowDO??>
	   			<#if shopOpenFlowDO.sellerType == 0>
		   			<div class="tips">
						<p>每个账号只能开设一家店铺，<br />
						如果您需要开设多家不同种类的店铺，您可以通过注册新账号来建立新的店铺。
						</p>
					</div>
		         <div class="outages_page">
					<div class="outages_box">
						<p class="black bd">我们发现，您在上一次开店过程中因故中断。</p>
						
						<p class="red bd">我们为您保存了大部分的填写资料。</p>
						<div><span class=" black bd">您可以选择：</span><input class="store_but" onclick="shopOpenBegin(0)" type="button" />
						如您需要重新开店，<a target="_blank" href="http://www.pinju.com/cms/html/about/contact/">请联系客服</a>。</div>
					</div>
				</div>
		        <#elseif  shopOpenFlowDO.sellerType == 1>
			        <div class="tips">
						<p>每个账号只能开设一家店铺，<br />
						如果您需要开设多家不同种类的店铺，您可以通过注册新账号来建立新的店铺。
						</p>
					</div>
		          <div class="outages_page">
					<div class="outages_box">
						<p class="black bd">我们发现，您在上一次开店过程中因故中断。</p>
						
						<p class="red bd">我们为您保存了大部分的填写资料。</p>
						<div><span class=" black bd">您可以选择：</span><input class="store_but" onclick="shopOpenBegin(1)" type="button" />
						如您需要重新开店，<a target="_blank" href="http://www.pinju.com/cms/html/about/contact/">请联系客服</a>。</div>
					</div>
				</div>
				<#elseif  shopOpenFlowDO.sellerType == 2>
					 <div class="tips">
						<p>每个账号只能开设一家店铺，<br />
						如果您需要开设多家不同种类的店铺，您可以通过注册新账号来建立新的店铺。
						</p>
					</div>
		          <div class="outages_page">
					<div class="outages_box">
						<p class="black bd">我们发现，您在上一次开店过程中因故中断。</p>
						
						<p class="red bd">我们为您保存了大部分的填写资料。</p>
						<div><span class=" black bd">您可以选择：</span><input class="store_but" onclick="shopOpenBegin(2)" type="button" />
						如您需要重新开店，<a target="_blank" href="http://www.pinju.com/cms/html/about/contact/">请联系客服</a>。</div>
					</div>
				</div>
		        </#if>
		    <#else>
		    	<#if sellerType??>
		    		<#if sellerType == 0>
		    			 <div class="tips">
							<p>每个账号只能开设一家店铺，<br />
							如果您需要开设多家不同种类的店铺，您可以通过注册新账号来建立新的店铺。
							</p>
						</div>
		    	  		<div class="outages_page">
							<div class="outages_box">
								<p class="black bd">我们发现，您在上一次开店过程中因故中断。</p>
								
								<p class="red bd">我们为您保存了大部分的填写资料。</p>
								<div><span class=" black bd">您可以选择：</span><input class="store_but" onclick="shopOpenBegin(0)" type="button" />
								如您需要重新开店，<a target="_blank" href="http://www.pinju.com/cms/html/about/contact/">请联系客服</a>。</div>
							</div>
						</div>
		    	  	<#elseif sellerType == 1>
		    	  		 <div class="tips">
							<p>每个账号只能开设一家店铺，<br />
							如果您需要开设多家不同种类的店铺，您可以通过注册新账号来建立新的店铺。
							</p>
						</div>
		          		<div class="outages_page">
							<div class="outages_box">
								<p class="black bd">我们发现，您在上一次开店过程中因故中断。</p>
								
								<p class="red bd">我们为您保存了大部分的填写资料。</p>
								<div><span class=" black bd">您可以选择：</span><input class="store_but" onclick="shopOpenBegin(1)" type="button" />
								如您需要重新开店，<a target="_blank" target="_blank" href="http://www.pinju.com/cms/html/about/contact/">请联系客服</a>。</div>
							</div>
						</div>
					<#elseif sellerType == 2>
						 <div class="tips">
							<p>每个账号只能开设一家店铺，<br />
							如果您需要开设多家不同种类的店铺，您可以通过注册新账号来建立新的店铺。
							</p>
						</div>
		          		<div class="outages_page">
							<div class="outages_box">
								<p class="black bd">我们发现，您在上一次开店过程中因故中断。</p>
								
								<p class="red bd">我们为您保存了大部分的填写资料。</p>
								<div><span class=" black bd">您可以选择：</span><input class="store_but" onclick="shopOpenBegin(2)" type="button" />
								如您需要重新开店，<a target="_blank" href="http://www.pinju.com/cms/html/about/contact/">请联系客服</a>。</div>
							</div>
						</div>
		          	<#else>
		          		<div class="kaidian-tip">
								<h3>开店小提示：</h3>
				
								<p>1.请根据以下店铺资质介绍选择最适合您的店铺类型；</p>
								<p>2.每个账号只能开设一家店铺，如果您需要开设多家不同类型的店铺，请注册新账号开设新的店铺；</p>
								<p>3.提交审核资料之后将不能随意更改您的店铺类型，请谨慎操作。</p>
							</div>
							<table class="kaidian-table">
								<tr>
									<th>店铺类型</th>
				
									<th>需提交的企业证件</th>
									<th>企业证件提交方式</th>
									<th>需缴纳保证金额度</th>
									<th></th>
								</tr>
								<tr>
									<td>
				
										<div class="type qijian">
											<h3>旗舰店</h3>
											<span>指商户以企业自有品牌（R或TM）在品聚网通过提交企业资质审核后，所开设的店铺。<span>
										</div>
									</td>
									<td>
										<ul>
											<li>企业营业执照</li>
				
											<li>组织机构代码证</li>
											<li>税务登记证</li>
											<li>品牌授权书或品牌商标注册证书</li>
											<li>商品质量检验证书</li>
										</ul>
									</td>
									<td>
				
										<ul>
											<li>线上提交证件图</li>
											<li>线下提交证件复印件</li>
										</ul>
									</td>
									<td><span class="price">10,000元</span></td>
									<td><a id="ruzhu2" class="ruzhu" href="#"></a></td>
				
								</tr>
								<tr class="bg">
									<td>
										<div class="type pinpai">
											<h3>品牌店</h3>
											<span>指商户持企业正规品牌授权书，在品聚网通过提交企业资质审核后，所开设的店铺。<span>
										</div>
									</td>
				
									<td>
										<ul>
											<li>企业营业执照</li>
											<li>组织机构代码证</li>
											<li>税务登记证</li>
											<li>品牌授权书或品牌商标注册证书</li>
											<li>商品质量检验证书</li>
				
										</ul>
									</td>
									<td>
										<ul>
											<li>线上提交证件图</li>
											<li>线下提交证件复印件</li>
										</ul>
									</td>
				
									<td><span class="price">10,000元</span></td>
									<td><a id="ruzhu1" class="ruzhu" href="#"></a></td>
								</tr>
								<tr>
									<td>
										<div class="type putong">
											<h3>普通店</h3>
											<span>指有基本企业资质的商户在品聚网经营类目许可下申请开设的店铺。<span>
				
										</div>
									</td>
									<td>
										<ul>
											<li>企业营业执照</li>
											<li>组织机构代码证</li>
											<li>税务登记证</li>
				
										</ul>
									</td>
									<td>
										<ul>
											<li>线上提交证件图</li>
										</ul>
									</td>
									<td>
				
										<ul>
											<li>线上提交证件图</li>
										</ul>
										<a href="http://service.pinju.com/cms/html/2011/recruit_0901/12.html" target="_blank">查看类目保证金</a>
									</td>
									<td><a id="ruzhu0" class="ruzhu" href="#"></a></td>
								</tr>
								<tr>
									<td>
										<div class="type ixiaopu">
											<h3>i小铺</h3>
											<span>指有基本企业资质的商户在品聚网经营类目许可下申请开设的店铺。<span>
										</div>

									</td>
									<td>
										<ul>
											<li>身份证复印件</li>
											<li>个人作品</li>
										</ul>
									</td>
									<td>
										<ul>
											<li>线上提交证件图</li>
										</ul>
									</td>
									<td>
				
										<ul>
											<li>线上提交证件图</li>
										</ul>
										<a href="http://service.pinju.com/cms/html/2011/recruit_0901/12.html" target="_blank">查看类目保证金</a>
									</td>
									<td><a id="ruzhu3" class="ruzhu" href="#"></a></td>
								</tr>
							</table>
				
							<div class="kaidian-qa">
								<h3 class="title">开店常见问题：</h3>
								<p class="list">
									<strong>1.入驻品聚网流程是怎样的？</strong>
									<span>品聚天使：</span>您好，1. 在线提交入驻申请并签署相关协议条款； 2. 线下提交成为品聚网商户所需资质资料，并等待品聚网人工审核。
								</p>
								<p class="list">
									<strong>2.个体工商户可以申请品聚开店吗?</strong>
				
									<span>品聚天使：</span>您好，很抱歉，目前品聚招商的对象只针对企业性质的企业或者公司。
								</p>
								<p class="list">
									<strong>3.申请入驻品聚网之后，企业相关信息变动，该怎么办？</strong>
									<span>品聚天使：</span>您好，首先商家取得工商局的核准变更证明后申请第三方支付 账户对应公司名称的变更；其次品聚账户对应公司名完成变更后，应及时到品聚网或与品聚网联系办理资料变更手续。否则，商户对因此导致的损失自行承担责任。
								</p>
								<!-- <p class="list">
									<strong>4.开店审核通常要多久可以完成？</strong>
									<span>品聚天使：</span>目前开店审核通常需要3-5个工作日；
								</p>	
								<p class="list">
									<strong>5.我只要开店审核通过了就可以正式开店了是吗？</strong>
									<span>品聚天使：</span>审核通过了之后您还需要缴纳保证金，缴纳保证金成功后您就可以正式开店了。
								</p>-->
							</div>
		          	</#if>
		          	</ul>
		        </#if>
		    </#if>
   		</form>	
 </div>   
