<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/tdomain/tdomain.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shopOpen.js?t=20111202.js"></SCRIPT>
<#setting classic_compatible=true>
   <form id="openShopForm" name="openShopForm" action="${base}/shop/shopOpenBeginAction.htm" method="post">
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <#if shopOpenFlowDO?exists>
		 <#if shopOpenFlowDO.auditStatus == 2>
	                 <ul class="index-checking">
                	<li><img src="http://static.pinju.com/img/index-succeed.png" border="0" usemap="#Map"/>
                        <map name="Map">
                        <area target="_blank" shape="rect" coords="436,23,606,84" href="/shopDecoration/shopDecorationAction.htm">
                        <area target="_blank" shape="rect" coords="437,97,607,159" href="/itemReleased/categoryList.htm">
                        <area shape="poly" coords="519,58" href="sale-manager.html">
                        </map>
                    </li>
                </ul>
                <div class="shop_pointer930">
						<p class="p2">未完成以下操作会导致您的店铺商品无法交易，请务必尽快完成</p>
						<ul>
							<li>
								1、绑定您在品聚网的支付账号。
								<span>绑定之后您的网上店铺可以实现收款功能</span>
								<#if bondStatus == 1>
									<span class="right"></span>
								<#else>
									<a class="btn" target="_blank" href="http://www.pinju.com/my/tenpay-account.htm">立即绑定账号</a>
								</#if>
								<a target="_blank" href="http://service.pinju.com/cms/html/2011/teach_0921/58.html">了解更多>></a>
							</li>
							<li>
								2、浏览并签约“财付通委托退款服务”的各项条款。
								<span>签约后可以实现您店铺商品的正常交易</span>
								<#if signAgreement == 1>
									<span class="right"></span>
								<#else>
									<a class="btn" target="_blank" href="https://www.tenpay.com/cgi-bin/trust/showtrust_refund.cgi?spid=${merchantno!}">去财付通签约</a>
								</#if>
								<a target="_blank" href="https://www.tenpay.com/cgi-bin/trust/showtrust_refund.cgi?spid=${merchantno!}">了解更多>></a>
							</li>
							<li>
								3、缴纳与您店铺类目相应的保证金。
								<span>缴纳保证金将更好地建立您店铺的诚信机制，树立诚信品质的形象</span>
								<a target="_blank" href="http://www.pinju.com/margin/mymargin.htm" class="btn">缴纳保证金</a>
								<a target="_blank" href="http://service.pinju.com/cms/html/2011/recruit_0901/12.html">了解更多>></a>
							</li>
						</ul>
					</div>
                
		 <#else>
		   <div class="kd-wtg">
			    <#if shopOpenFlowDO.auditStatus == 0>
				    <#if shopOpenFlowDO.isFillInfo == 0 && shopOpenFlowDO.sellerType == 0>
				    	<div style="height:100px; width:400px; border:1px solid #ccc; padding:10px;">
				    		您尚未填写店铺信息。
				    	</div>
				    	<div class="kdinput">
				    		
						   		<input type="button" onclick="shopOpenBegin(0)" value="填写店铺基本信息" />&nbsp;&nbsp;&nbsp;&nbsp;
						   	
		    			</div>
		    		<#elseif shopOpenFlowDO.isFillInfo < 4 && shopOpenFlowDO.sellerType == 1>
		    			<div style="height:100px; width:400px; border:1px solid #ccc; padding:10px;">
				    		您尚未填写店铺信息。
				    	</div>
				    	<div class="kdinput">
				    		
						   	<input type="button" onclick="shopOpenBegin(1)" value="填写店铺基本信息" />
						   	
		    			</div>
				    <#else>
				                <#if shopOpenFlowDO.sellerType == 1 ||  shopOpenFlowDO.sellerType == 2>
											<div class="pending-title">开店申请已提交，正在审核，请耐心等待...</div>
											<div class="pending-tip">
												<h3>审核进程：</h3>
												<ul>
													<#if shopOpenFlowDO??>
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
													</#if>
												</ul>
											</div>
											<div class="pending-info">
												<h3>您需要提交以下实物资料:</h3>
												<ul>
													<li>样品——您所销售的样品</li>
												</ul>
												<p class="ti">关于实物样品审核：品聚有权视实际情况要求商户寄送样品进行审核。样品资质审核完毕，商户可选择该样品进入品聚样品陈列库，进行备案，或者选择由品聚网在30个工作日内寄还商户。</p>
								
												<ul>
													<li>证件——<span>请参考<a href="http://news.pinju.com/cms/html/join_in/" target="_blank">店铺入驻资质</a> 说明</span></li>
												</ul>
												<h3>邮寄地址:</h3>
												<p>地址：上海市浦东新区福山路388号宏嘉大厦8楼 商务部收
													<br />邮编：200122
												</p>
								
											</div>
								<#else>
									 <ul class="index-checking">
						                	<li><img src="http://static.pinju.com/img/index-checking.png"/></li>
						                </ul>
				                </#if>
				                <input type="hidden" name="_token_" value="${_token_}" />
				    </#if>
			    <#elseif shopOpenFlowDO.auditStatus == -1>
					<ul class="index-checking" id="index-reopen">
                	<h3>很抱歉，您的开店审核未被通过！</h3>
                    <p>${shopOpenFlowDO.noPassReason}</p>
                    <#if shopOpenFlowDO.sellerType == 0>
						<li class="button"><input name="" type="button" onclick="shopOpenBegin(0)" value="重新开店"></li>
					<#elseif shopOpenFlowDO.sellerType == 1>
						<li class="button"><input name="" type="button" onclick="shopOpenBegin(1)" value="重新开店"></li>
					<#elseif shopOpenFlowDO.sellerType == 2>
						<li class="button"><input name="" type="button" onclick="shopOpenBegin(2)" value="重新开店"></li>
					</#if>
                    
                </ul>
				<#elseif shopOpenFlowDO.auditStatus == 1>
							
							<div class="pending-title checkOkTitle"></div>
							<div class="pending-tip">
								<h3>审核进程：</h3>
								<ul>
									<li>在线信息审核<span class="gray">已完成</span></li>
									<li>邮寄资料审核<span class="gray">已完成</span></li>
				
								</ul>
							</div>
							
							<#if shopOpenFlowDO.sellerType == 0>
								<a class="jiaonabzj" href="#" title="“缴纳保证金后即可开启您的品聚网店了”" onclick="shopOpenBegin(0)"></a>
			                <#elseif  shopOpenFlowDO.sellerType == 1>
			                	<a class="jiaonabzj" href="#" title="“缴纳保证金后即可开启您的品聚网店了”" onclick="shopOpenBegin(1)"></a>
			                <#else>
			                	<a class="jiaonabzj" href="#" title="“缴纳保证金后即可开启您的品聚网店了”" onclick="shopOpenBegin(2)"></a>
			                </#if>
			              
			    </#if>
			    </div>
		 </#if> 
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
					<td><a class="ruzhu" href="#"></a></td>

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
					<td><a class="ruzhu" href="#"></a></td>
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
					<td><a class="ruzhu" href="#"></a></td>
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
				<p class="list">
					<strong>4.开店审核通常要多久可以完成？</strong>
					<span>品聚天使：</span>目前开店审核通常需要3-5个工作日；
				</p>	
				<p class="list">
					<strong>5.我只要开店审核通过了就可以正式开店了是吗？</strong>
					<span>品聚天使：</span>审核通过了之后您还需要缴纳保证金，缴纳保证金成功后您就可以正式开店了。
				</p>
			</div>
						
						
   </#if>
   </div>
   </form>
  