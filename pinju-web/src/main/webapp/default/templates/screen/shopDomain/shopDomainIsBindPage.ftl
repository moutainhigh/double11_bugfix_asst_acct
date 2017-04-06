<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/tdomain/tdomain.css" rel="stylesheet" type="text/css" media="screen" />
<title>域名设置</title>
<input type="hidden" value="red_shopDomain" id="my-page" />
			<div class="domain-set-tip cf">
				<img class="img-tip" src="http://static.pinju.com/img/tdomain/domain-set-tip.gif" />
				<div class="word-tip">
					<span>您可以在这里为您的商铺绑定一个简单好记的二级域名</span>
					<a href="#">使用帮助</a>
				</div>
			</div>

			<div class="domain-box-modify">
				<#if settingType?? && (settingType == 0 || settingType == 2)>
					<div class="binding-ok cf">
						<img src="http://static.pinju.com/img/tdomain/binding-ok.gif">
						<h3>绑定成功！新二级域名大约会在8小时后完全生效！</h3>
					</div>
				</#if>
				<div class="domain-normal cf">
					<span class="common ft14">您店铺目前的二级域名是<em>：</em></span>
					<span class="red ft14">${domain!}</span>
					<!--<a id="changeDomain" class="modidy-btn" onclick="changeDomain()" href="#">更改域名</a>-->
					<!--<a id="unbind" onclick="unbind()" href="">解除二级域名</a>-->

				</div>
				<div class="domain-normal cf">
					<span class="common">绑定时间：</span>
					<span>${shopInfoDO.domainCreate?string("yyyy-MM-dd HH:mm:ss")}</span>
				</div>
				<div class="domain-normal cf">
					<span class="common">您店铺的初始域名是：</span>
					<#if shopId??>
						<span class="red">shop${shopId!}.pinju.com</span>
					</#if>
					<span class="gray">（此域名仍可使用）</span>
				</div>
				<div class="domain-box-after">二级域名一旦绑定便不可解绑或换绑，请谨慎选择您的二级域名。</div>
			</div>
<script>
	function unbind(){
		if(confirm("解除后您将不再拥有这个域名的使用权，您确定要解除当前的二级域名吗？")){
			$("#unbind").attr("href", "/shopDomain/settingDomainAction.htm?settingType=1");
		}
	}
	
	function changeDomain(){
		$("#changeDomain").attr("href", "/shopDomain/showChangeDomainPageAction.htm?settingType=2");
	}
</script>