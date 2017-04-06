<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>品聚 - 会员 - 解绑邮箱</title>
</head>
<body>
  <link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" media="screen" />
 <form action="${base}/my/sendUnboundEmail.htm" method="post" id="emailForm">
	   <input name="pj0" type="hidden" value="${pj0}">
	   <input name="email" type="hidden" value="${email?html}">
		<div class="mail-h cf">
			<h1>解绑验证邮箱</h1>
		</div>
		<div class="row">
			<div class="y-point">
				<p>点击“发送验证邮件”，系统会向您已验证的邮箱地址发送一封验证邮件。点击邮件中的验证链接，即可进入下一步解绑该验证邮箱！
如果您的已验证邮箱无法使用，需要修改验证邮箱，请联系客服：<span class="bd red">4008-211-588</span></p>
			</div>
			<div class="email-m">邮箱：<span class="email">${email?html}</span> <button id="submitBtn" class="btn" type="submit">发送验证邮件</button></div>
		</div>
	</div>
  </form>
<input type="hidden" value="security-center" id="my-page" />
<script>
  $(function() {
      $("#emailForm").submit(function() {
           $("#submitBtn").attr("disabled", "true");
           return true;
      });
  });
 
</script>
</body>
</html>
