<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<title>退款超时提示</title>
<div class="wrap cf">
		<div class="tipswrap">
			<h2>友情提示</h2>
			<div class="infocat warn"></div>
			<div class="content">
				<h3>${errorMessage?default("买家5天超时没有响应，系统自动默认关闭买家的退款申请。")} </h3>
				<p>您现在可以：</p>
				<p><a href="${buyerReplyTimeoutUrl}">查看最近的退款详情</a></p>
			</div>
		</div>
</div>
