<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>品聚会员 - 手机已经绑定</title>
</head>
<body>
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" media="screen" />
<!-- content -->
		<div class="phone-h cf">
			<h1>手机绑定</h1>
		</div>
		<div class="pBind cf">
			<div class="icon suc"></div>
			<div class="reMes">
				<div>您的手机已经绑定！</div>
				<p>您的会员名是：${(mem.loginName)!}</p>
				<p>您绑定的手机号码是：<span class="red bd">${(mem.getHideMobile())!}</span></p>
				<p>您可以使用手机号码登录品聚网，享受快乐购物乐趣！</p>
			</div>
		</div>
	<input type="hidden" value="security-center" id="my-page" />
<!-- footer -->
</body>
</html>
