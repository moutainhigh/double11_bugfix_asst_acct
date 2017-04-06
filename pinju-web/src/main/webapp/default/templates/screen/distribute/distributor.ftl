<title>申请云营销</title>
<!--分销页面样式-->
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css"/>
<link rel="stylesheet" href="http://static.pinju.com/css/shareBuying/shareBuying.css"/>
	
<div class="cf fx-banner">
	<div class="fl"><img src="http://static.pinju.com/images/figure-banner_03.gif" /></div>
	<div class="rge-box">
		<div class="t-box">
			<a id="applySupplier" name="applySupplier" href="javascript:;">申请成为供应商</a>
			<a id="applyAuthority" name="applyAuthority" href="javascript:;">申请成为分销商</a>
		</div>
		<div class="b-box"><img src="http://static.pinju.com/images/fx-x_06.gif" /></div>
	</div>
</div>
<div class="cf fx-int-box">
	<div class="txt-item">
		<div class="title"><span>爱分享介绍</span></div>
		<p class="txt">爱分享是品聚网向所有品聚会员提供的一个社区商铺，通过爱分享，品聚会员可以向其他社区用户展示并进行商品推荐。
如果您希望将您看到的优秀商品推荐给其他用户，只要愿意，都可以可以通过本平台申请爱分享其他商户优质的商品。
如果您是通过认证的商家会员的话，也可以通过云营销，将您的商品由社区推向更多的用户。</p>
	</div>
	<div class="txt-item">
		<div class="title"><span>申请条件</span></div>
		<p><span>供应商：</span>必须通过商户认证，拥有自己的店铺。</p>
		<p><span>分销商：</span>无，但需要在申请过程中绑定财付通账户</p>
	</div>
</div>
<div class="fx-circuit">
	<ul class="cf">
		<li><span>分销流程</span></li>
	</ul>
		<p class="fx-liuc"><img src="http://static.pinju.com/images/fx-liuc1_03.gif" /></p>
		<p class="fx-liuc"><img src="http://static.pinju.com/images/fx-liuc2_03.gif" /></p>
</div>
<script type="text/javascript">
	<#if errorMessage??>
	if('${errorMessage?js_string}'.length>0 && '${errorMessage?js_string}' != '错误'){
		alert('${errorMessage?js_string}');
	}
	</#if>
	$(function(){
	$('#welcome').addClass('count');
		$('#applySupplier').click(
			function(){
				$.ajax({
					dataType: "json",
					url:"/supplier/apply.htm",
					type:"POST",
					success:function(callback){
						if(callback.status){
							window.location.href = "/supplier/welcome.htm";
						}else{
							alert(callback.errorMessage);
						}
					},
					error:function(){
					}
				});
			}
		);
	});
	<!-- 分销商js start author:caiwei-->
	$(function(){
		$('#applyAuthority').click(
			function(){
				$.ajax({
					data:"",
					url:"/distributor/applyAuthority.htm",
					type:"POST",
					success:function(callback){
						if(callback.status){
							alert(callback.message);
							window.location.href = "/distributor/welcome.htm";
						}else{
							alert(callback.message);
							if(callback.needForward){
								window.location.href = "/distributor/bindAccount.htm";
							}
						}
					},
					error:function(){
					}
				});
			}
		);
	});
	<!-- 分销商js end -->
</script>