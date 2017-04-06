<#setting classic_compatible=true>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>注册成功</title>
<#include "/default/templates/layout/header.ftl">
<link rel="stylesheet" href="http://static.pinju.com/css/new/register.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/member/member.css" type="text/css" media="screen" />
<!--[if IE 6]>
<script type="text/javascript" src="http://static.pinju.com/js/DD_belatedPNG-min.js" ></script>
<script type="text/javascript">
DD_belatedPNG.fix('.infocat,.btn_orange');
</script>
<![endif]-->
</head>
<body>
<#include "/default/templates/layout/site-nav.ftl">
<div class="login_main">
<#include "/default/templates/layout/laybaner-simple.ftl">
<div class="login_box">
  <div class="user_tit">
    <span class="user_txt">注册成功</span>
  </div>
  <div class="login_b welcome">
    <div class="tipswrap cf">
      <div class="infocat msg"></div>
      <div class="content">
        <h3 class="key">恭喜您注册成功！</h3>
        <p>您的品聚会员名为：<strong>${loginName?html}</strong><br />您可以使用此会员名登录品聚网享受安心便捷的购物及交流的乐趣。</p>
        <div class="button"><a class="btn_orange" href="${returnUrl?html}" title="返回注册前的页面" id="back">返回注册前的页面(10)</a></div>
      </div>
    </div>
    <div class="col2">
      <div class="tipswrap cf">
        <div class="infocat msgemail"></div>
        <div class="content">
          <h3 class="key">验证邮箱</h3>
          <p>邮箱可以登录品聚网或找回密码，<br />并且提高账户安全等级</p>
          <div class="button"><a class="btn_orange" href="${base}/my/verify-email.htm">现在就验证邮箱</a></div>
        </div>
      </div>      
      <div class="tipswrap last cf">
        <div class="infocat msgmphone"></div>
        <div class="content">
          <h3 class="key">验证手机号码</h3>
          <p>手机号码可以登录品聚网或找回密码，<br />并且提高账户安全等级</p>
          <div class="button"><a class="btn_orange" href="${base}/my/verify-mobile.htm">现在就验证手机号码</a></div>
        </div>
      </div>      
    </div>
  </div>
</div>
</div>
<script type="text/javascript">
(function(){var b=document.getElementById('back');b.countdown=10;var t=setInterval(function(){if(b.countdown==1){location.href='${returnUrl?js_string}';clearInterval(t);return;}b.innerHTML=b.title+'('+(--b.countdown)+')';},1000);})();
</script>
<#include "/default/templates/layout/footer.ftl">
</body>