<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<#include "/default/templates/layout/header.ftl">
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
	 	 <!-- 交易管理 -->
		 <#include "/default/templates/control/buy/buy-trade-management.ftl">
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
  