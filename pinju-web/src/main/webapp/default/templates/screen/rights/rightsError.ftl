<title>错误页面</title>
<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<div class="wrap cf">
	<div class="tipswrap">
		<h2>错误提示</h2>
		<div class="infocat error"></div>
		<div class="content">
			<#--
			<#if status??>
				<h3>你请求的页面出错了！目前维权状态为：
					<#if status == 0>
						维权申请中
					<#elseif status == 1>
						<#if isGoods?? && isGoods==1>
							等待买家退还商品
						<#elseif isGoods?? && isGoods==0>
							卖家同意,等待打款
						</#if>
					<#elseif status == 12>
						等待买家退还商品
					<#elseif status == 2>
						维权申请被拒绝
					<#elseif status == 3 ||status == 11>
						等待卖家确认收货
					<#elseif status == 4>
						客服介入中
					<#elseif status == 5>
						卖家同意,维权成功
					<#elseif status == 6>
						维权关闭
					<#elseif status == 7>
						客服仲裁维权成功
					<#elseif status == 8>
						客服仲裁维权关闭
					<#elseif status == 9>
						客服仲裁成功,等待打款
					<#elseif status == 10>
						维权协议已达成，等待打款
					</#if>
				</h3>
			</#if> 
			-->
			<#if errorMessage??><h3>${errorMessage}</h3></#if>
			<p>您现在可以：</p>
			<#if isSeller?? && isSeller =="true">
				<p><a href="/rights/sellerRightsList.htm">返回维权列表</a></p>
			<#else>
				<p><a href="/rights/rightsList.htm">返回维权列表</a></p>
			</#if>
		</div>
	</div>
</div>
