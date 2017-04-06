<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户登录</title>
<script type="text/javascript">
if(window.top !== window.self) window.top.location.replace( window.self.location.href );
</script>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" type="text/css" media="screen" />
<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
</head>

<body>
  <div class="login_main">
    <#include "/default/templates/layout/laybaner-simple.ftl">
    <div class="login_box">
      <div class="tips-browser">
		<p><strong>友情提醒：</strong>如您在品聚网购买时遇到问题，可能是由于浏览器的兼容性问题造成的，建议您使用 IE 8.0</p>
	  </div>
      <div class="login-main">
        <div class="login-left-module">
          <div id="login-left-ad" class="login-left-ad">
            <#--
            +-------+-------+---+
            |   1   |   2   | 3 |
            +---+---+-------+---+
            | 4 | 5 |       | 6 |
            +---+---+  14   +---+
            | 7 | 8 |       | 9 |
            +---+---+---+---+---+
            |  10   | 11| 12| 13|
            |       |   |   |   |
            +---+---+---+---+---+
            -->

            <#-- 1..13 -->
            <#list 1..13 as index>
            <@load.loginpage link="${img.getImg(index).imgLink}" title="${img.getImg(index).imgTitle}" img="${img.getImg(index).imgUrl}" style="${img.getImg(index).divStyle}" class="${img.getImg(index).divClass}" width="${img.getImg(index).width}" height="${img.getImg(index).height}" imgid="img${index}"/>
            </#list>
            <#-- 14 -->
            <div class="b2 w-logo w-logo-g" id="w-logo" style="top:101px; left:202px;" title="天下无假，爱上网购"></div>
          </div>
          <div class="login-left-point" style="display:none;" id="login-hint">
            <h2>登录遇到问题了</h2>
            <ul class="point-list">
              <li>
                <h3><span>·</span>您是否输错会员名或密码？</h3>请您先检查会员名是否正确，您的输入密码位数是否超过 或少于您设置的密码长度，然后再重新输入密码再试试。
              </li>
              <li>
                <h3><span>·</span>您是否锁定了键盘的大写功能？</h3> 请检查您键盘上的"Caps Lock"或"A"灯是否亮着，如果是，请先按一下"Caps Lock"键然后重新输入。
              </li>
              <li>
                <h3><span>·</span>您是否忘记了密码？</h3>您可以通过您验证过的邮箱找回您的密码。
              </li>
              <li>
                <h3><span>·</span>依然无法登录？</h3>您可以联系我们客服，只要提供会员名证明资料，我们品聚网客服将会帮您解决。
              </li>
            </ul>
          </div>
        </div>
        <div class="login-right-module">
        <h3 class="user-d">会员登录</h3>
        <div class="login-module">
         <div class="point-box<#if invokeMessage??> error</#if>" id="message">${invokeMessage}</div>
          <form action="${base}/member/login-do.htm" method="post" id="login-form" autocomplete="off">
            <ul class="login-list">
              <li><span>账户名：</span><input type="text" name="loginName" id="loginName" maxlength="100" value="${loginName?html}" tabindex="1" placeholder="会员名/邮箱/手机号码" title="输入会员名、邮箱地址或者手机号码作为登录账户名" /></li>
              <li><span>密　码：</span><input type="password" maxlength="16" id="password" name="password" value="" tabindex="2" title="账号密码" /></li>
              <li class="code">
                <div id="captcha-block">
                  <span>验证码：</span><input type="text" name="captcha" value="" id="captcha" maxlength="4" title="请输入图片中的字符（不区分大小写），看不清时可以点击图换一张" tabindex="3"/>
                  <a href="javascript:;" hidefocus="hidefocus" class="c-img">
                    <img width="80" height="30"<#if showCaptcha> src="${base}/e/captcha.htm?sid=${sid?html}"</#if> basesrc="${base}/e/captcha.htm?sid=${sid?html}" title="看不清，点击图片换一张" id="captcha-img" alt="验证码" />
                  </a><a class="login_h" href="javascript:void(0);" id="captcha-change" title="看不清，点击这里换一张">换一张</a>
                </div>
              </li>
              <li class="lBtn" id="login-box"><button type="button" title="登录" id="pinju-login">登录</button> <a href="${base}/security/forgot-password.htm" title="密码忘记了？点击这里看看能不能找回来！">忘记密码</a></li>
            </ul>
            <input type="hidden" name="sid" value="${sid?html}" />
            <input type="hidden" name="tid" value="" />
            <input type="hidden" name="referer" value="${referer?html}" />
            <input type="hidden" name="returnUrl" value="${returnUrl?html}" />
            <input type="hidden" name="param1" value="${param1?html}" />
            <input type="hidden" name="param2" value="${param2?html}" />
            <input type="hidden" name="checkCaptcha" value="<#if showCaptcha>true<#else>false</#if>" id="checkCaptcha" />
          </form>
        </div>
        <div class="register-box">还不是品聚会员？点击这里 <a class="register-btn" href="${base}/member/register.htm<#if returnUrl?? && returnUrl?length gt 0>?returnUrl=${returnUrl?url}</#if>" title="没有账号？点击这里来注册一个吧！">免费注册</a></div>
        <div class="hz-login">您还可以用合作伙伴账号登录品聚 <a href="https://api.weibo.com/oauth2/authorize?client_id=${sinaClientid}&response_type=code&redirect_uri=http://www.pinju.com/member/sina-weibo-login.htm&state=<#if returnUrl?? && returnUrl?length gt 0>${returnUrl?url}</#if>" target="_blank" title="使用新浪微博账号登录">新浪微博</a></div>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="http://static.pinju.com/jquery/jquery.placeholder-enhanced.min.js"></script>
<script type="text/javascript">
(function(){var v=$('#loginName').val();var n=0;for(var i=0,k=v.length;i<k;i++){n+=(v.charCodeAt(i)<0xff)?1:2;}var s=n<4?'#loginName':'#password';$(s).focus();$(s).select();<#if !showCaptcha?? || !showCaptcha>$('#captcha-block').hide();</#if>})();
var BASE='${base}';
</script>
<script type="text/javascript" src="http://static.pinju.com/js/member/key.js?t=20111130.js"></script>
<script type="text/javascript">
$("#captcha").keyup(function(){var a=$(this);a.val(a.val().toUpperCase())});var changeCaptcha=function(){var a=$("#captcha-img");if($.browser.msie&&$.browser.version==6){setTimeout(function(){a.attr("src",a.attr("basesrc")+"&r="+Math.random())},0)}else{a.attr("src",a.attr("basesrc")+"&r="+Math.random())}$("#captcha").val("").focus()};$("#captcha-img").click(changeCaptcha);$("#captcha-change").click(changeCaptcha);
(function(){$("input[placeholder]").placeholderEnhanced();$("#loginName,#password,#captcha").focus(function(){$(this).select()})})();
(function(){var b=$("#login-left-ad img"),g=$("#img10"),f=null;b.css("opacity",".4");g.css("opacity","1");$("#w-logo").hover(function(){$(this).removeClass("w-logo-g");clearTimeout(f);b.css("opacity",".4")},function(){$(this).addClass("w-logo-g")});b.hover(function(){clearTimeout(f);b.css("opacity",".4");$(this).css("opacity","1")},function(){b.css("opacity",".4");f=setTimeout(function(){g.css("opacity","1")},100)});$(".login-left-ad a").attr("hidefocus","hidefocus")})();
(function(){$("#loginName,#password,#captcha").bind({copy:function(){return false},cut:function(){return false},paste:function(){return false},contextmenu:function(){return false}});var b=function(a,c){var b=$("#message");b.addClass("error");b.text(c);b.show();a&&(a.focus(),a.select());return false},g=function(a,b,e){for(var a=$(a).serializeArray(),d=0,k=a.length;d<k;d++)if(a[d].name=="password")a[d].value=Security.encryptHex(a[d].value,b,e);return a},f=function(a){if(a){for(var a=$.trim(a),b=0,e=
0,d=a.length;e<d;e++)b+=a.charCodeAt(e)<255?1:2;b<4||$.ajax({type:"POST",url:BASE+"/async/member/captcha.htm",data:{check:a},success:function(a){var b=$("#checkCaptcha").val()=="true";$("#checkCaptcha").val(!!a.result);a.result?(b||changeCaptcha(),$("#captcha-block").show()):$("#captcha-block").hide()}})}},i=function(){$("#pinju-login").bind("click",h).attr("title","\u767b\u5f55").text("\u767b\u5f55");$("#login-box").removeClass("loadding")},j=function(a){b($("#password"),a);$("#password").val("");
$("#captcha").val("");$("#checkCaptcha").val()=="true"&&changeCaptcha();i()},h=function(){var a=$("#loginName"),c=$.trim(a.val());if(c.length==0)return b(a,"\u8bf7\u8f93\u5165\u767b\u5f55\u8d26\u53f7");if(!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(c)){for(var e=
0,d=0,f=c.length;d<f;d++)e+=c.charCodeAt(d)<255?1:2;if(e<4||e>20)return b(a,"\u767b\u5f55\u8d26\u53f7\u4e3a 4\uff5e20 \u4e2a\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u201c-\u201d \u548c\u4e0b\u5212\u7ebf\u6216\u8005 2\uff5e10 \u4e2a\u6c49\u5b57\u6216\u8005\u9a8c\u8bc1\u540e\u7684 Email \u5730\u5740\u3001\u624b\u673a\u53f7\u7801");if(!/^[\u4e00-\u9faf_0-9a-zA-Z-]+$/.test(c))return b(a,"\u767b\u5f55\u8d26\u53f7\u5e94\u7531\u82f1\u6587\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u6c49\u5b57\u3001\u201c-\u201d \u548c\u4e0b\u5212\u7ebf\u7ec4\u6210");
if(!/^[\u4e00-\u9faf0-9a-zA-Z].*$/.test(c))return b(a,"\u767b\u5f55\u8d26\u53f7\u5e94\u4ee5\u82f1\u6587\u5b57\u6bcd\u3001\u6570\u5b57\u6216\u6c49\u5b57\u5f00\u59cb")}a=$("#password");c=a.val();if(c.length==0)return b(a,"\u8bf7\u8f93\u5165\u5bc6\u7801");if(c.length<6||c.length>16)return b(a,"\u5bc6\u7801\u5e94\u4e3a 6\uff5e16 \u4e2a\u5b57\u7b26\u5185");if(!/^[\x21-\x7e]+$/.test(c))return b(a,"\u5bc6\u7801\u4ec5\u5141\u8bb8\u6570\u5b57\u3001\u82f1\u6587\u5b57\u6bcd\u53ca\u7b26\u53f7");if($("#checkCaptcha").val()==
"true"){a=$("#captcha");c=a.val();if(c.length==0)return b(a,"\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801");if(c.length!=4)return b(a,"\u9a8c\u8bc1\u7801\u9700\u8981\u6709 4 \u4e2a\u5b57\u7b26");if(!/^[0-9A-Za-z]{4}$/.test(c))return b(a,"\u9a8c\u8bc1\u7801\u5e94\u7531\u82f1\u6587\u5b57\u6bcd\u548c\u6570\u5b57\u7ec4\u6210")}$("#pinju-login").unbind("click").attr("title","\u767b\u5f55\u4e2d").text("\u767b\u5f55\u4e2d");$("#login-box").addClass("loadding");Encrypt.encrypt({timeout:15E3,success:function(a,c,
d){$('input[name="tid"]').val(d.tid);$.ajax({type:"POST",url:"http://www.pinju.com/member/login-dox.htm",data:g("#login-form",a,c,d),timeout:15E3,success:function(a){if(a)if(a.result==0)top.location.href=a.returnUrl||"http://www.pinju.com/";else{$("#login-left-ad").hide();$("#login-hint").show();var c=$("#checkCaptcha");c.val(a.showCaptcha||"false");var d=$("#password");d.val("");$("#captcha").val("");a.showCaptcha&&c.val()=="true"&&($("#captcha-block").show(),changeCaptcha());b(d,a.message);i()}else b(null,
"\u767b\u5f55\u5931\u8d25\uff0c\u8bf7\u7a0d\u5019\u91cd\u8bd5")},error:function(){j("\u7f51\u7edc\u8fde\u63a5\u8d85\u65f6\uff0c\u767b\u5f55\u5931\u8d25")}})},error:function(a,b,c){j(c?"\u7f51\u7edc\u901a\u4fe1\u5f02\u5e38\uff0c\u767b\u5f55\u5931\u8d25":"\u767b\u5f55\u8fde\u63a5\u8d85\u65f6\uff0c\u767b\u5f55\u5931\u8d25")}});return true};$("#loginName").change(function(){f($(this).val())});$("#pinju-login").bind("click",h);$(document).keydown(function(a){a.keyCode==13&&$("#pinju-login").click()})})();
</script>
<#include "/default/templates/layout/footer.ftl">
</body>
</html>
