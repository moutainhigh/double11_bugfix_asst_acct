<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>品聚 - 会员 - 安全中心</title>
</head>
<body>
  <link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" media="screen" />
 <form action="${base}/my/sendEmail.htm" method="post" id="emailForm">
	   <input name="pj0" type="hidden" value="${pj0}">
		<div class="mc-head cf">
			<div class="user-pic">
				<span><img src="<@load.avatars userMemberId '50' version/>" alt="" /></span>
			</div>
		  <#if memberSecurityCenter.lastLoginTime?exists>
			<p>亲爱的<b>${memberSecurityCenter.loginName}</b>用户，欢迎您来到品聚网安全中心。</p>
			<p>您上次登录是：<span>${memberSecurityCenter.lastLoginTime?string("yyyy-MM-dd HH:mm:ss")}</span>，登录IP为：<span>${memberSecurityCenter.lastLoginIp}</span></p>
		   <#else>
		    <p>亲爱的<b>${memberSecurityCenter.loginName}</b>用户，欢迎您第一次来到品聚网安全中心。</p>
		 </#if>
		</div>
		<div class="mc-cont">
			<div class="t"><div class="tred"></div></div>
			<div class="safe-lv">
			<#if memberSecurityCenter.level == 0>
			  <div class="safe-pic"></div>
				您的安全等级:
				<span class="safe-img">
					<span class="lv0"></span>
				</span>
				<em class="red">您可以通过验证手机及邮箱提升您的账号安全性</em>
			 <#else>
			        您的安全等级:
				<span class="safe-img">
					<span class="lv${memberSecurityCenter.level}"></span>
				</span>
				<i class="lv${memberSecurityCenter.level}"><#if memberSecurityCenter.level == 1>低<#elseif  memberSecurityCenter.level == 2>中<#elseif  memberSecurityCenter.level == 3>高</#if></i>
				<em>您可以通过验证手机及邮箱提升您的账号安全性</em>
			</#if>
			</div>

			<ul class="bind-list">
				<li><span class="name">您的手机号码：</span><#if memberSecurityCenter.mobile?exists><b>${memberSecurityCenter.mobile}</b>(<a href="${base}/my/verify-unmobile.htm">我要解绑</a>)<#else><b>未绑定</b>(<a href="${base}/my/verify-mobile.htm">我要绑定</a>)</#if></li>
				<li><span class="name">您的邮箱地址：</span><#if memberSecurityCenter.email?exists><b>${memberSecurityCenter.email}</b>(<a href="${base}/my/go-unbound-eamil.htm">我要解绑</a>)<#else><b>未绑定</b>(<a href="${base}/my/verify-email.htm">我要绑定</a>)</#if></li>
				<!--
				<li><span class="name">您的密保：</span><b>未绑定</b>(<a href="#">我要绑定</a>)</li>
				-->

			</ul>
			<div class="txt-box">
			您可以在此页面检查您的账户安全性。<br /><br />
建议为了保证您的账户安全，请您绑定手机及邮箱，这样不仅可以方便您使用手机号码及邮箱地址登录品聚网，也便于您忘记密码后找回。<br />
如果您要对已经绑定的手机号码或邮箱进行变更，需要您的解绑手机或邮箱必须可以接收解绑信息，否则您需要联系客服（4008-211-588）提供相关资料，由品聚客服人员确认后帮您解绑。
			
			</div>
			
		</div>
	</div>
  </form>
<input type="hidden" value="security-center" id="my-page" />
</body>
</html>
