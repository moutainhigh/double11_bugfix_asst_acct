<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<#include "/default/templates/layout/header.ftl">
<link rel="stylesheet" href="http://static.pinju.com/css/rights.css" type="text/css" media="screen" />
<script src="http://static.pinju.com/js/rights/popup_layer.js"></script>
<link href="http://static.pinju.com/css/refund.css" rel="stylesheet"/>
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
pinju.Person.link();
</script>
</body>
</html>
  