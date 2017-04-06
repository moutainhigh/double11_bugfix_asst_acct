<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>第三方登录</title>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css?t=20111121.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" type="text/css" media="screen" />
<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>

</head>

<body>
  <div class="login_main">
    <#include "/default/templates/layout/laybaner-simple.ftl">
      <form action="${base}/member/sina-register.htm<#if returnUrl?? && returnUrl?length gt 0>?returnUrl=${returnUrl?url}</#if>" method="post" id="sina-register-form">
          <div class="wrap-bor cf">
 			<div class="crea-name">
				<div class="tit"><span>错误信息</span></div>
				<div class="n-box">			
					<div class="tipswrap">
						<div id="errorico" class="infocat error"></div>
						<div class="content">
							<h3 id="errorMessage">新浪微博登录出现异常，可能是连接超时，请您重新登录。</h3>
						</div>
					</div>
				</div>
				 <a class="btn return-login" href="http://www.pinju.com/member/login.htm?returnUrl=http%3A%2F%2Fwww.pinju.com%2Findex.htm"></a>
			</div>
        </div>
          </form>
  </div>
<#include "/default/templates/layout/footer.ftl">
</body>
</html>
