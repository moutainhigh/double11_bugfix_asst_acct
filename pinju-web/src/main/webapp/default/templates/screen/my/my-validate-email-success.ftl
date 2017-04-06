<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>品聚 - 会员 - 验证邮箱</title>
<link rel="stylesheet" href="http://static.pinju.com/css/base.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/new/register.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/member/member.css" type="text/css" media="screen" />
</head>
<body>
<!--[if IE 6]>
<script type="text/javascript" src="http://static.pinju.com/js/DD_belatedPNG-min.js" ></script>
<script type="text/javascript">
DD_belatedPNG.fix('.infocat,.btn_orange');
</script>
<![endif]-->
<div class="login_main">
    <div class="login_box">
    	<div class="user_tit">
        	<span class="user_txt">验证邮箱</span>
        </div>
    	<div class="login_b mbmsg">
			<div class="tipswrap">
				<div class="infocat msgemail"></div>
				<div class="content">
					<h3><strong>邮箱验证成功!</strong></h3>
					<h3>您的用户名是: <strong>${loginName}</strong></h3>
					<h3>您的邮箱是: <strong>${email}</strong></h3>
					<label>您可以使用邮箱登录品聚网，享受快乐购物的乐趣</label>
					<p><span id="countdown">5</span> 秒后自动跳转至 <a href="http://www.pinju.com" title="品聚首页">品聚首页</a></p>
                   <div class="button"><a class="btn_orange" href="http://www.pinju.com">品聚首页</a></div>
				</div>
			</div>
			<div class="shadow1"></div>
			<div class="shadow2"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
(function(){var t=setInterval(function(){var c=document.getElementById('countdown');var n=parseInt(c.innerHTML);if(n<=1){location.href='http://www.pinju.com';clearInterval(t);return;}n-=1;c.innerHTML=n;},1000);})();
</script>
</body>
</html>
