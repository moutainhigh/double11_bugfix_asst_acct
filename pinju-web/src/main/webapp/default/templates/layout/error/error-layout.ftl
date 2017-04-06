<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>${title}</title>
<#include "/default/templates/layout/header.ftl">
<link href="http://static.pinju.com/css/classlist.css" rel="stylesheet" />
<SCRIPT src="http://static.pinju.com/js/top.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/hd.js"></SCRIPT>
<script src="http://static.pinju.com/js/util.js"></script>
</head>

<body>

<div class="top">
  <!-- 顶部导航  -->
  <#include "/default/templates/layout/site-nav.ftl">
  <!--logo和搜索-->
  <#include "/default/templates/layout/laybaner.ftl">
  <!-- 导航标签 
  <#include "/default/templates/layout/nav.ftl">-->
</div>

<!-- 内容 -->
<div style="height:500px; width:500px; margin:0 auto;">${body}</div>

<!--底部-->
<#include "/default/templates/layout/footer.ftl">
</body>
</html>