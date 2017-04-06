<#if (query.itemTagMetaInfo)?exists>
<#assign itemTagMetaInfo = query.itemTagMetaInfo>
</#if>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<meta name="keywords" content="品聚网 购物商城 店铺,${((itemTagMetaInfo.itemTitle)!'')?html},${((itemTagMetaInfo.itemCategory)!'')?html}"/>
<meta name="description" content="欢迎来到品聚,${((itemTagMetaInfo.shopType)!'')?html},${((itemTagMetaInfo.shopCategoryStr)!'')?html},${((itemTagMetaInfo.itemTitle)!'')?html},<#if (itemTagMetaInfo.shopLabel)?exists>${((itemTagMetaInfo.shopLabel)!'')?html}<#else>品聚 </#if>."/>
<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css?t=20111208.css" />
<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>

<link rel="stylesheet" href="http://static.pinju.com/css/goods/sp.css?t=20111214.css" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/page/pagination.css?t=201111221445.css" rel="stylesheet" /> 

<script src="http://static.pinju.com/js/item/detail-util-min.js?t=201111221445.js"></script>
<script src="http://static.pinju.com/js/jquery.pagination.js?t=201111221451.js"></script>
<script src="http://static.pinju.com/js/jquery.lazyload.mini.js?t=201111221451.js"></script>
<script src="http://static.pinju.com/js/item/itemdetail-min.js?t=20111214.js"></script>
<script src="http://static.pinju.com/js/coupon/getCoupon.js?t=20111208.js"></script>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>

</head>

<body>
<!-- top nav  -->
<#include "../site-nav.ftl">
<#include "../laybaner.ftl">
<!--<#include "/default/templates/control/shopDecoration/shopHeadControl.ftl">-->
<!-- // top nav  -->

<div class="shopsplit"></div>

<!-- content -->
<div class="wrap950 cf">
${body}
</div>
<!-- // content -->

<!-- footer -->
<#include "../footer.ftl">
<!-- // footer -->
</body>
</html>