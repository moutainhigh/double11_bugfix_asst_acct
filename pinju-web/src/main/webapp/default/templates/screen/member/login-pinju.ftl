<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户登录</title>
<#include "/default/templates/layout/header.ftl">
<link rel="stylesheet" href="http://static.pinju.com/css/new/login.css" type="text/css" media="screen" />
</head>

<body>
  <div class="login_main">
    <#include "/default/templates/layout/laybaner-simple.ftl">
    <div class="login_box">
      <div class="user_tit">
        <span class="user_txt">用户登录</span>
      </div>
      <div class="login_b">
        <div class="l_box">
          <span class="err_box" id="message"<#if !invokeMessage??> style="display: none;"</#if>>${invokeMessage}</span>
          <form action="${base}/member/login-do.htm" method="post" id="login-form">
          <div class="login_list_box">
            <ul class="login_list cf">
              <li>
                <span class="login_t"><label for="loginName">登录账号：</label></span>
                <span class="login_i"><input type="text" name="loginName" id="loginName" maxlength="100" value="${loginName?html}" tabindex="1" placeholder="用户名/邮箱" title="注册用户名或者是邮箱地址" /></span>
              </li>
              <li>
                <span class="login_t"><label for="password">密码：</label></span>
                <span class="login_i"><input type="password" maxlength="16" id="password" name="password" value="" tabindex="2" title="账号密码" /></span>
                <a class="login_w" href="${base}/security/forgot-password.htm" title="忘记了密码，点击这里重置">忘记密码？</a>
              </li>
              <li id="captcha-block">
                <span class="login_t"><label for="captcha">验证码：</label></span>
                <span class="login_c" style="width:90px;"><input type="text" name="captcha" value="" id="captcha" style="width:75px;" maxlength="4" title="请输入图片中的字符（不区分大小写），看不清时可以点击图换一张" tabindex="3" /></span>
                <span>
                  <img width="80" height="30" basesrc="${base}/e/captcha.htm?sid=${sid?html}" style="border:1px solid #C4C4C4;margin:0 5px;cursor:pointer;" title="看不清，点击图片换一张" id="captcha-img" />
                </span>
                <span style="display:inline-block;">
                  <a class="login_h" href="javascript:void(0);" id="captcha-change" title="看不清，点击这里换一张">看不清<br />换一张</a>
                </span>
              </li>
            </ul>
            <span><input type="submit" class="login_but" value="" style="cursor:pointer;" id="pinju-login" /></span>            
            <input type="hidden" name="sid" value="${sid?html}" />
            <input type="hidden" name="referer" value="${referer?html}" />
            <input type="hidden" name="returnUrl" value="${returnUrl?html}" />
            <input type="hidden" name="param1" value="${param1?html}" />
            <input type="hidden" name="param2" value="${param2?html}" />
            <input type="hidden" name="checkCaptcha" value="false" id="checkCaptcha" />
          </div>
          </form>
        </div>

        <div class="r_box">
          <p class="p_1">立即免费注册为品聚用户，您可以</p>
          <p class="p_2">
            <span>买</span> 享受数亿优质商品和可靠服务
          </p>
          <p class="p_2">
            <span>卖</span> 在品聚开店，将商品卖给数亿盛大会员
          </p>
          <a class="zc_but" href="${base}/member/register.htm<#if returnUrl?? && returnUrl?length gt 0>?returnUrl=${returnUrl?url}</#if>"></a>
        </div>
        <div class="shadow1"></div>
        <div class="shadow2"></div>
        <img class="login_pic" width="173" height="125" src="http://static.pinju.com/images/login_b_pic_36.png" />
      </div>
    </div>
  </div>
<script type="text/javascript" src="http://static.pinju.com/jquery/jquery.placeholder-enhanced.min.js"></script>
<script type="text/javascript">
(function(){var v=$('#loginName').val();var n=0;for(var i=0,k=v.length;i<k;i++){n+=(v.charCodeAt(i)<0xff)?1:2;}var s=n<4?'#loginName':'#password';$(s).focus();$(s).select();<#if !showCaptcha?? || !showCaptcha>$('#captcha-block').hide();</#if>})();
var BASE='${base}';
$('input[placeholder]').placeholderEnhanced();
</script>
<script type="text/javascript" src="http://static.pinju.com/js/member/captcha-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/login-min.js?t=20111009"></script>
<#include "/default/templates/layout/footer.ftl">
</body>
</html>
