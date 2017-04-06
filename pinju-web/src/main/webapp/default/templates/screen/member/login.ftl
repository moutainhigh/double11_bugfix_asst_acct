<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<title>登录</title>
<#include "/default/templates/layout/header.ftl">
<LINK href="http://static.pinju.com/css/css.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/member.css" rel="stylesheet" />
<script type="text/javascript" src="http://static.pinju.com/js/domain.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member-login.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/pinju-global.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/cookie.js"></script>
</head>
<body>

<script type="text/javascript">
var p = new PinjuCookie('${base}');
if( p.isLogin() ) {
  location.href = '${returnUrl}';
}
</script>

<div class="laybaner">
  <div class="laybanner-in">
   <h1 class="index-logo"><a href="http://www.pinju.com${base}">品聚-御网</a></h1>
  </div>
</div>

<!--头部结束-->
<!--内容页面开始-->
<div class="login-content">
 <div class="login-pic"><img src="http://static.pinju.com/images/login.jpg" width="435" height="276" /></div>
 <div class="login-form">
   <div class="form-hd">盛大通行证登录</div>
   <div style="margin:50px 0 0 20px;" id="login">
     <script type="text/javascript">app.writeLogin('${returnUrl}')</script>
   </div>
    
<div class="login-other">
 <h3>使用合作网站帐号登录</h3>
  <a>新浪</a><a>人人</a><a>新浪微薄</a><a>开心</a>
</div>

  </div>
 <div class="cella"></div>
</div>

<script type="text/javascript">
if(navigator.cookieEnabled && !p.isLogin()) {
  p.writeSdoChecker();
}
var search = location.search;
if(search) {
  search = decodeURIComponent(search);
  if(search.indexOf('?r=0') > -1 || search.indexOf('&r=0') > -1) {
    alert('登录暂时不可用，请过几分钟之后再重试');
  }
}
</script>
<script type="text/javascript" src="http://ipic.staticsdo.com/external/install_beacon.js"></script>
<!--底部-->
<#include "../../layout/footer.ftl">
</body>
</html>