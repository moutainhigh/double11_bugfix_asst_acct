<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<#include "/default/templates/layout/header.ftl">
</head>

<body>
<!-- top nav  -->
<#include "site-nav.ftl">
<!-- // top nav  -->

<!-- logo -->
<#include "laybaner.ftl">
<!-- // logo -->

<!-- nav -->
<#include "nav.ftl">
<!-- // nav -->

<!-- content -->
<div class="wrap cf">
${body}
</div>
<!-- // content -->

<!-- footer -->
<#include "footer.ftl">
<!-- // footer -->
</body>
</html>