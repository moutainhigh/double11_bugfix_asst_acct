<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<#include "/default/templates/layout/header.ftl">
<link media="screen" type="text/css" rel="stylesheet" href="http://static.pinju.com/css/open.css?t=20111201" />
<link href="http://static.pinju.com/css/member.css?20110811" rel="stylesheet" />
</head>

<body>
<!-- top nav  -->
<#include "/default/templates/layout/site-nav.ftl">
<!-- // top nav  -->

<!-- logo -->
<#include "/default/templates/layout/laybaner.ftl">
<!-- // logo -->

<!-- nav -->
<#include "/default/templates/layout/nav.ftl">
<!-- // nav -->

<!-- content -->
<div class="wrap cf">
<div class="wrap-border cf">
  <div class="bar-left">
    <!-- left -->
    <ul class="bar-list">
      <h3>账号管理</h3>
      <li name="security-center"><a href="${base}/my/security-center.htm">安全中心 <img src="http://static.pinju.com/img/icon-new.gif"></a></a></li>
      <li name="my-info"><a href="${base}/my/info.htm">个人资料</a></li>
      <li name="change-password"><a href="${base}/my/change-password.htm">修改密码</a></li>
      <li name="address-manager"><a href="${base}/my/address.htm">收货地址</a></li>
      <li name="city-station"><a href="${base}/my/city-station.htm" >分站数据查询</a></li>
      <li name="my-sns-info"><a href="${base}/my/sns.htm" >社区资料</a></li>
    </ul>
  </div>
  <div class="contents">
    <!-- right -->
    ${body}
  </div>
</div>
</div>
<!-- // content -->

<script type="text/javascript">
pinju.Person.link();
</script>

<!-- footer -->
<#include "/default/templates/layout/footer.ftl">
<!-- // footer -->
</body>
</html>