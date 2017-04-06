<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码</title>
</head>
<body>
<!--[if IE 6]>
<script type="text/javascript" src="http://static.pinju.com/js/DD_belatedPNG-min.js" ></script>
<script type="text/javascript">
DD_belatedPNG.fix('.err_box');
</script>
<![endif]-->
<link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" media="screen" />
<div class="wrap">
	<div class="getp-h">
		<div class="pic"></div>
	</div>
	<div class="getp-body">
	  <form action="${base}/security/retrieve-password-do.htm" method="post">
	   <#if invokeMessage??>
         <div class="err_box">${invokeMessage}</div>
       </#if>
		<div class="phone-get">
				<ul class="list">
					<li><span>新密码：</span><input type="password" name="newPassword" id="newPassword" title="密码为大小写英文字母、数字、符号的 6～16 个字符" maxlength="16" />
					   <b class="<#if (validator['newPassword'])??>err</#if>">${(validator['newPassword'])!}</b>
					</li>
					<li><span>确认新密码：</span><input type="password" name="confirm" title="请再次输入密码" maxlength="16" />
					    <b class="<#if (validator['confirm'])??>err</#if>">${(validator['confirm'])!}</b>
					</li>
				</ul>
			</div>
			<button class="btn btnsty2" type="submit">找回密码</button>
      <input type="hidden" name="param3" value="${param3?html}" />
      <input type="hidden" name="param4" value="${param4?html}" />
      <input type="hidden" name="param5" value="${param5?html}" />
      <input type="hidden" name="param6" value="${param6?html}" />
	</div>
</form>
</div>
<script type="text/javascript">
(function(){document.getElementById('newPassword').focus();})();
</script>
</body>
</html>
