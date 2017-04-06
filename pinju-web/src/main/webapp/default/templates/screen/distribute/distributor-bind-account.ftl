<title>绑定财付通</title>
		<link href="http://static.pinju.com/css/distribution/dtb.css" rel="stylesheet" type="text/css" media="screen" />
		<div class="wrap-dtb">
			<div class="welcome">
				<p>你好，${nickName!''}，欢迎您进入云营销</p>
			</div>
			<div class="title">
				<h2>申请分销商</h2>
			</div>			
			<div class="content">
				<div class="tips">
					<p>抱歉，您没有绑定收款帐号，无法申请成为分销商，请您先绑定收款账号，谢谢。</p>
				</div>
				<ol class="step">
					<li class="step1">
						绑定您在品聚网的收款账号 
						<#if (bindAccount?? && bindAccount == true)>
						<span>您已完成</span>
						<#elseif (bindAccount??)>
						<button id="bindAccount" type="button" class="btn-orange-long">立即绑定账号</button>
						</#if>
					</li>
					<!--<li class="step2">
						浏览并签约“财付通委托退款服务”的各项条款 
						<#if (signArgeement?? && signArgeement == true)>
						<span>您已完成</span>
						<#elseif (signArgeement??)>
						<button id="signArgeement" type="button" class="btn-orange-long">去财付通签约</button>
						</#if>
					</li>-->
					<li class="bottom">
						<#if (bindAccount?? && bindAccount == false)>
						<div class="msg-error cf">
							<a class="close" href="###"><span>关闭</span></a>
							<p>您还没有绑定收款帐号，请立即绑定！</p>
						</div>
						</#if>
						<!--<#if (signArgeement?? && signArgeement == false)>
						<div class="msg-error cf">
							<a class="close" href="###"><span>关闭</span></a>
							<p>您还没有签约“财付通委托退款服务”的各项条款，请立即签约！</p>
						</div>
						</#if>-->
						<div class="btn-wrap">
							<button name="nextButton" type="button" class="btn-red-large">下一步</button>
							<p>如果您已经完成了以上设定，请点击“下一步”</p>
						</div>
					</li>
				</ol>
			</div>
		</div>
<script type="text/javascript">
$(function(){
	$('button[name="nextButton"]').click(
		function(){
			$('#welcome').addClass('count');
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
	$('#bindAccount').click(function(){window.open('/my/tenpay-account.htm');});
	//$('#signArgeement').click(function(){window.open('https://www.tenpay.com/cgi-bin/trust/showtrust_refund.cgi?spid=${merchantno!}');});
	$('.close').click(function(){$(this).parent('div').fadeOut("slow");});
});
</script>