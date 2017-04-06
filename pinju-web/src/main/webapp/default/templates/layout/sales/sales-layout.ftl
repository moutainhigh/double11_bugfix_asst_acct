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
  <div class="contents">
    <!-- right -->
    ${body}
  </div>
</div>
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
  