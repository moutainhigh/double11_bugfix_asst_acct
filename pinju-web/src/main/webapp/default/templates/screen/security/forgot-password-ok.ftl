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
    <span class="user_txt">验证邮箱</span>
  </div>
  <div class="login_b mbmsg">
    <div class="tipswrap">
      <div class="infocat msgemail"></div>
      <div class="content">
        <h3>已向您的邮箱 <strong>${hiddenEmail}</strong> 发送找回密码邮件，请查收</h3>
        <p>&nbsp;</p>
        <p><a class="btn_orange" target="_blank" id="go">去收邮件</a></p>
      </div>
    </div>
    <div class="shadow1"></div>
    <div class="shadow2"></div>
  </div>
</div>
<script type="text/javascript" src="http://static.pinju.com/js/member/goemail-min.js"></script>
<script type="text/javascript">
(function(){GoEmail.go('${hiddenEmail}','go');})();
</script>
</body>
</html>
