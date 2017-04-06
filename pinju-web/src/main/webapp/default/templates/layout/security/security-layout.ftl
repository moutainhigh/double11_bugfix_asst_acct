<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<#include "/default/templates/layout/header.ftl">
<link rel="stylesheet" href="http://static.pinju.com/css/new/login.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/new/register.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/member/member.css" type="text/css" media="screen" />
</head>
<body>
<#include "/default/templates/layout/site-nav.ftl">
<div class="login_main">
  <#include "/default/templates/layout/laybaner-simple.ftl">
  ${body}
</div>
<#include "/default/templates/layout/footer.ftl">
</body>
</html>