<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>品聚会员注册</title>
<script type="text/javascript">
if(window.top !== window.self) window.top.location.replace( window.self.location.href );
</script>
<#include "/default/templates/layout/header.ftl">
<link rel="stylesheet" href="http://static.pinju.com/css/new/register.css?t=20111024.css" type="text/css" media="screen" />
</head>
<body>
  <#include "/default/templates/layout/site-nav.ftl">
  <div class="login_main">
    <#include "/default/templates/layout/laybaner-simple.ftl">
    <div class="login_box">
      <div class="user_tit">
        <span class="user_txt">品聚会员注册</span>
      </div>
      <form action="${base}/member/register-do.htm<#if returnUrl?? && returnUrl?length gt 0>?returnUrl=${returnUrl?url}</#if>" method="post" id="register-form">
      <div class="login_b">
        <div class="zc_box cf">
          <ul class="zc_list">
            <li class="err_li">
              <i class="font1">填写账户信息</i>
              <i class="font2">(以下信息均为必填)</i>
              <div class="err_box">
                <#if invokeMessage??>
                <span class="point"> ${invokeMessage}</span>
                </#if>
              </div>
            </li>
            <li>
              <span class="li_t">会员名：</span>
              <span class="li_i"><input type="text" name="loginName" id="loginName" value="${loginName?html}" maxlength="20" title="会员名为 4～20 个英文字母、数字、“-” 和下划线或者 2～10 个汉字" class="<#if (validator['loginName'])??>error</#if>" /></span>
              <div class="li_e">
                <span class="error<#if (validator['loginName'])??> point<#else> hidden</#if>">${(validator['loginName'])!}</span>
              </div>
            </li>
            <li>
              <span class="li_t">登录密码：</span>
              <span class="li_i"><input type="password" name="password" id="password" maxlength="16" title="密码为大小写英文字母、数字、符号的 6～16 个字符" class="<#if (validator['password'])??>error</#if>" /></span>
              <div class="li_e">
                <span class="error<#if (validator['password'])??> point<#else> hidden</#if>">${(validator['password'])!}</span>
              </div>
            </li>
            <li>
              <span class="li_t">确认登录密码：</span>
              <span class="li_i"><input type="password" name="confirm" id="confirm" maxlength="16" title="请再次输入密码" class="<#if (validator['confirm'])??>error</#if>" /></span>
              <div class="li_e">
                <span class="error<#if (validator['confirm'])??> point<#else> hidden</#if>">${(validator['confirm'])!}</span>
              </div>
            </li>
            <li>
              <span class="li_t"><label for="captcha">验证码：</label></span>
              <span class="li_i code_s"><input type="text" class="code <#if (validator['captcha'])??>error</#if>" name="captcha" id="captcha" maxlength="4" title="请输入图片中的字符（不区分大小写），看不清时可以点击图换一张" /></span>
              <a href="javascript:void(0);" class="li_pic" hidefocus="hidefocus"><img width="80" height="30" src="${base}/e/captcha.htm?sid=${sid?html}" basesrc="${base}/e/captcha.htm?sid=${sid?html}" id="captcha-img" title="看不清，点击图片换一张" /></a>
              <a href="javascript:void(0);" class="li_a" id="captcha-change">看不清？ 换一张！</a>
              <div class="li_e">
                <span class="error<#if (validator['captcha'])??> point<#else> hidden</#if>">${(validator['captcha'])!}</span>
              </div>
            </li>
            <li class="last">
              <span class="li_t"></span>
              <p class="li_c font3" style="width:228px">
                <input type="checkbox" name="agreement" id="agreement" title="阅读协议并接受后请勾选"<#if agreement == '1'> checked="true"</#if> value="1" /> <label for="agreement">我已阅读并接受</label> <a href="http://service.pinju.com/cms/html/2011/bis_mng_0819/46.html" target="_blank">用户协议</a>
              </p>
              <div class="li_e">
                <span id="agreement-msg" class="error<#if (validator['agreement'])??> point<#else> hidden</#if>">${(validator['agreement'])!}</span>
              </div>
            </li>
          </ul>
          <input type="hidden" name="sid" value="${sid?html}" />
          <input type="hidden" name="tid" value="" />
          <input type="hidden" name="referer" value="${referer?html}" />
          <input type="hidden" name="p" value="${code?html}" />
        </div>
        <span><input type="submit" class="zc_but" style="cursor:pointer;" value="" id="pinju-register" /></span>
        <div class="shadow1"></div>
        <div class="shadow2"></div>
        <img width="988" height="5" class="shadow3" src="http://static.pinju.com/images/shadow3_13.gif" />
      </div>
      </form>
    </div>
  </div>

<script type="text/javascript">var BASE='${base}';</script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/key.js?t=20111130.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/captcha-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/register-min.js?t=20111130.js"></script>
<#include "/default/templates/layout/footer.ftl">
</body>
</html>