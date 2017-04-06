<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${title}</title>
<#include "/default/templates/layout/header.ftl">
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>

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
      <!--商品管理-->
      <#include "/default/templates/control/itemManagement.ftl">
      <!--交易管理-->
      <#include "/default/templates/control/tradeManagement.ftl">
      <!--店铺管理-->
      <#include "/default/templates/control/shopManagement.ftl">
      <!--分销管理-->
      <#include "/default/templates/control/supplierManagement.ftl">
       <!--积分管理-->
      <#include "/default/templates/control/pointsManagement.ftl">
      <!--退款维权-->
      <#include "/default/templates/control/rightsManagement.ftl">
      <!-- 保证金管理 -->
      <#include "/default/templates/control/marginManagement.ftl">
      <!--营销中心-->
      <#include "/default/templates/control/activity/activityManagement.ftl">
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
  