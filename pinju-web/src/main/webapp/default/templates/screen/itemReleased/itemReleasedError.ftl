<body>
	<div style="text-align:center;width:100%;font:normal 16px 宋体;padding:100px 0 150px 0;">
		<ul>
		<#if invokeMessage?exists>
			<li>${invokeMessage}</li>
		</#if>
		<#if validator?exists>
			<#list validator.keySet() as key>
				<li>${validator.get(key)}</li>
			</#list>
		</#if>
		</ul>
	</div>
</body>