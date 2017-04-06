<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<!-- 818重构样式 -->
<#include "/default/templates/layout/header.ftl">
<link media="screen" type="text/css" rel="stylesheet" href="http://static.pinju.com/css/open.css?t=20111201" />
<link href="http://static.pinju.com/css/goods.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
  html {
    background: #FFF;
  }
</style>
    
<!--[if lt IE 9]><script src="http://static.pinju.com/js/html5.js"></script><![endif]-->
<script type="text/javascript" src="http://static.pinju.com/js/jqui.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/sell.js"></script>

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
${body}
</div>
<!-- // content -->

<!-- footer -->
<#include "/default/templates/layout/footer.ftl">
<!-- // footer -->

<script type="text/javascript">
pinju.Person.focusNav('seller-nav');
</script>
</body>
</html>
