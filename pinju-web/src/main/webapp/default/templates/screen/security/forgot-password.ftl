<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>找回密码</title>
</head>
<body>
 <link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" media="screen" />
<div class="wrap">
	<div class="getp-h">
		<div class="pic"></div>
	</div>
	<form action="${base}/security/forgot-password-do.htm" id="meform" method="post">
	<div class="getp-body">
		<dl>
			<dt>请输入您的会员名</dt>
			<dd class="cf"><span class="name">会员名</span><input class="w185 txt-bar" type="text" id="loginName" name="loginName" value="${loginName?html}" tabindex="1" title="请输入会员名" maxlength="20"/>
			 <span class="<#if (validator['loginName'])??>err<#else>point</#if>"><#if (validator['loginName'])??>${validator['loginName']}<#else>请输入会员名</#if></span>
			</dd>
			<dt>选择密码找回方式</dt>
			<dd>
				<div class="cf">
					<span class="name"><input id="phone" name="sel" type="radio" <#if sel??><#if sel == "2">checked="checked"</#if><#else>checked="checked"</#if> value="2"/><label for="phone" id="mobileRadio">手机</label></span>
					<input class="w185 txt-bar sel" type="text" id="mobile" name="mobile" value="${mobile?html}" title="请输入验证后的手机号码" tabindex="2" maxlength="11" />
					<span class="<#if (validator['mobile'])??>err</#if>">${(validator['mobile'])!}</span>
				</div>
				<div class="cf">
					<span class="name"><input id="mail" name="sel" type="radio" <#if sel == "1">checked="checked"</#if> value="1"/><label for="mail"  id="emailRadio">邮箱</label></span>
					<input class="w185 txt-bar sel" type="text" id="email" name="email" value="${email?html}" title="请输入验证后的邮箱地址" tabindex="3" maxlength="100"/>
					<span class="<#if (validator['email'])??>err</#if>">${(validator['email'])!}</span>
				</div>
			</dd>
			<dt>请输入图片中的验证码</dt>
			<dd class="cf"><span class="name">验证码</span><input class="w85 txt-bar" type="text" id="captcha" name="captcha" maxlength="4" tabindex="4"/><span class="code" style="cursor:pointer;">
			 <img width="80" height="30" src="${base}/e/captcha.htm?sid=${sid?html}" basesrc="${base}/e/captcha.htm?sid=${sid?html}" id="captcha-img" title="看不清，点击图片换一张" />
			</span><a class="change" href="javascript:void(0);" id="captcha-change">换一张</a>
				<span id="captcha-message" class="<#if (validator['captcha'])??>err<#else>point</#if>"><#if (validator['captcha'])??>${validator['captcha']}<#else>请输入验证码</#if></span>
			</dd>
		</dl>
	<button class="btn" type="submit">找回密码</button>
	</div>
	<input type="hidden" id="sid" name="sid" value="${sid?html}" />
  </form>
</div>
<script>
$(function(){
	$(".getp-body dd").hover(function(){
		$(this).css("background","#feffef")
	},function(){
		$(this).css("background","")
	})
	//背景变色
	
	$(".sel").focus(function(){
		$(this).closest("div").find(".name").find("input").attr("checked","checked");
	})
})
</script>
<script type="text/javascript">var BASE='${base}';</script>
<script type="text/javascript" src="http://static.pinju.com/js/member/captcha-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/forgotpwd-min.js?t=20111209.js"></script>
</body>
</html>
