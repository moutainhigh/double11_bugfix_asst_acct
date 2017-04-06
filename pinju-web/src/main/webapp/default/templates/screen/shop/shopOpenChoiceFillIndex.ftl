<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<#setting classic_compatible=true>
<input type="hidden" value="red_shopOpen" id="my-page" />
<input type="hidden" value="${shopFlowInfoDO.isFillComplete}" id="isFillComplete" />
<div class="openshop">
			<div class="ptitle">
				<h1>提交开店信息</h1>
				<span class="eye"><a href="/shop/iWillOpenShopAction.htm"">返回</a></span>
			</div>

			<p class="exinfo">以下三部分资料都保存提交后才能进入审核流程，请完整提交。<a href="http://service.pinju.com/cms/html/help/selleropen/" target="_blank">开店帮助</a></p>
			<#if shopFlowInfoDO??>
				<div class="infowrap">
					<!-- 普通店 -->
					<#if shopFlowInfoDO.sellerType?? && shopFlowInfoDO.sellerType == 0>
						<ul>
							<li class="finished"><span class="icon-shop"></span>
								<#if shopFlowInfoDO.isFillStep1?? &&  shopFlowInfoDO.isFillStep1 == 0> 
									<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=0">填写基本店铺信息</a>
								<#else>
									<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=0">填写基本店铺信息<em>已完成</em></a>
								</#if>
							</li>
							<li class="finished"><span class="icon-qycer"></span>
								<#if shopFlowInfoDO.isFillStep2?? &&  shopFlowInfoDO.isFillStep2 == 0> 
									<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=0">上传企业证书</a>
								<#else>
									<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=0">上传企业证书<em>已完成</em></a>
								</#if>	
							</li>
						</ul>
					</#if>
					<!-- 品牌店 -->
					<#if shopFlowInfoDO.sellerType?? && shopFlowInfoDO.sellerType == 1>
						<ul>
								<#if shopFlowInfoDO.isFillStep1?? &&  shopFlowInfoDO.isFillStep1 == 0> 
									<li><span class="icon-shop"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=1">填写基本店铺信息</a>
									</li>
								<#else>
									<li class="finished">
										<span class="icon-shop"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=1">填写基本店铺信息<em>已完成</em></a>
									</li>
								</#if>
							
								<#if shopFlowInfoDO.isFillStep2?? &&  shopFlowInfoDO.isFillStep2 == 0> 
									<li><span class="icon-qycer"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=1">上传企业证书</a>
									</li>
								<#else>
									<li class="finished">
										<span class="icon-qycer"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=1">上传企业证书<em>已完成</em></a>
									</li>
								</#if>	
							
								<#if shopFlowInfoDO.isFillStep3?? &&  shopFlowInfoDO.isFillStep3 == 0> 
									<li class="last"><span class="icon-brand"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=3&sellerType=1">添加品牌信息</a>
									</li>
								<#else>
									<li class="finished last">
										<span class="icon-brand"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=3&sellerType=1">添加品牌信息<em>已完成</em></a>
									</li>
								</#if>	
							
						</ul>
					</#if>
					<!-- 旗舰店 -->
					<#if shopFlowInfoDO.sellerType?? && shopFlowInfoDO.sellerType == 2>
						<ul>
								<#if shopFlowInfoDO.isFillStep1?? &&  shopFlowInfoDO.isFillStep1 == 0> 
									<li><span class="icon-shop"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=2">填写基本店铺信息</a>
									</li>
								<#else>
									<li class="finished">
										<span class="icon-shop"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=2">填写基本店铺信息<em>已完成</em></a>
									</li>
								</#if>
							
								<#if shopFlowInfoDO.isFillStep2?? &&  shopFlowInfoDO.isFillStep2 == 0> 
									<li><span class="icon-qycer"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=2">上传企业证书</a>
									</li>
								<#else>
									<li class="finished">
										<span class="icon-qycer"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=2">上传企业证书<em>已完成</em></a>
									</li>
								</#if>	
							
								<#if shopFlowInfoDO.isFillStep3?? &&  shopFlowInfoDO.isFillStep3 == 0> 
									<li class="last"><span class="icon-brand"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=3&sellerType=2">添加品牌信息</a>
									</li>
								<#else>
									<li class="finished">
										<span class="icon-brand"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=3&sellerType=2">添加品牌信息<em>已完成</em></a>
									</li>
								</#if>	
							
						</ul>
					</#if>
					<!-- i小铺 -->
					<#if shopFlowInfoDO.sellerType?? && shopFlowInfoDO.sellerType == 3>
						<ul>
							
								<#if shopFlowInfoDO.isFillStep1?? &&  shopFlowInfoDO.isFillStep1 == 0> 
									<li><span class="icon-shop"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=3">填写基本店铺信息</a>
									</li>
								<#else>
									<li class="finished"><span class="icon-shop"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=1&sellerType=3">填写基本店铺信息<em>已完成</em></a>
									</li>
								</#if>
							
								<#if shopFlowInfoDO.isFillStep2?? &&  shopFlowInfoDO.isFillStep2 == 0> 
									<li class="last"><span class="icon-qycer"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=3">上传身份证扫描件和个人作品</a>
									</li>
								<#else>
									<li class="finished">
										<span class="icon-qycer"></span>
										<a href="/shop/queryShopInfoAction.htm?fillType=2&sellerType=3">上传身份证扫描件和个人作品<em>已完成</em></a>
									</li>
								</#if>	
						</ul>
					</#if>
					
					
					
					
					<form id="shopInfoForm" onsubmit="return onSubmitInfo()" action="${base}/shop/submitShopOpenAskAction.htm" method="post">
						<div class="btnrow">
							<div><input type="checkbox"  onclick="setChecked()" name="ckbox" id="ckbox" /><label for="ckbox"> 以上资料我已经输入、上传完毕</label></div>
		
							<div class="btn-imgtext">
								<button id="btn" class="submitreview" type="submit"><span>提交审核</span></button>
							</div>
						</div>
					</form>
				</div>
			</#if>
			
		</div>
		
<script>
	function setChecked(){
		var ckbox = $("#ckbox");
		if(!ckbox.attr("checked")){
			ckbox.removeAttr("checked");
		}else{
			ckbox.attr("checked","false");
		}
	}
	function onSubmitInfo(){
		var ckbox = $("#ckbox");
		var isFillComplete = $("#isFillComplete").val();
		if(!ckbox.attr("checked")){
			return false;
		}
		if(isFillComplete == "1"){
			if(confirm("您确认提交资料吗？")){
				return true;
			}else{
				return false;
			}
		}else{
			alert("请先完成资料填写");
			return false;
		}
		
	}
</script>