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
DD_belatedPNG.fix('.infocat,.btn_orange');
</script>
<![endif]-->
<div class="login_box">
  <div class="user_tit">
    <span class="user_txt">找回密码</span>
  </div>
  <div class="login_b mbmsg">
    <div class="tipswrap">
      <div class="infocat msg"></div>
      <div class="content">
        <h3 class="key">您已成功找回密码</h3>
        <h3>您已经可以用新密码登录品聚网了</h3>
        <p><span id="countdown">5</span> 秒后自动跳转至 <a href="${base}/member/login.htm?loginName=${loginName?url}" title="立即登录">登录页面</a></p>
        <div class="button"><a class="btn_orange" href="${base}/member/login.htm?loginName=${loginName?url}">现在登录</a></div>
      </div>
    </div>
    <div class="shadow1"></div>
    <div class="shadow2"></div>
  </div>
</div>
<script type="text/javascript">
(function(){var t=setInterval(function(){var c=document.getElementById('countdown');var n=parseInt(c.innerHTML);if(n<=1){location.href='${base}/member/login.htm?loginName=${loginName?url}';clearInterval(t);return;}n-=1;c.innerHTML=n;},1000);})();
</script>
</body>
</html>
