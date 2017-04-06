<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邮件链接</title>
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
      <span class="user_txt">邮件链接</span>
    </div>
    <div class="login_b mbmsg">
      <div class="tipswrap">
        <div class="infocat msgcaution"></div>
        <div class="content">
          <h3 class="key">邮件链接出错了......</h3>
          <p>您所请求的邮件链接地址不正确，或者已经过期</p>
          <div class="button"><a class="btn_orange" href="${base}/member/login.htm" title="立即登录" id="back">立即登录(5)</a></div>
        </div>
      </div>
      <div class="shadow1"></div>
      <div class="shadow2"></div>
    </div>
  </div>
<script type="text/javascript">
(function(){var b=document.getElementById('back');b.countdown=5;var t=setInterval(function(){if(b.countdown==1){location.href='${base}/member/login.htm';clearInterval(t);return;}b.innerHTML=b.title+'('+(--b.countdown)+')';},1000);})();
</script>
</body>
</html>
