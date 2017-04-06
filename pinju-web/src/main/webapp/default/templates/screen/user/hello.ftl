<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="layout" content="main"/>
<title>品聚网</title>
</head>
<body>
    Hello ${message!}<br />
<div style="text-align:center;" align="center">
<ul>
<#if items??>
  <#list items as item>
  <li><a href="${base}/detail/${item.id}.htm" target="_blank">${item.title}</a>&nbsp;&nbsp;${(item.price/100)?string('0.00')}元</li>
  </#list>
 </#if>
</ul>
</div>
<script type="text/javascript">
pinju.Person.focusNav('main-nav');
</script>
</body>
</html>