<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>商城--御网</title>
<#include "/default/templates/layout/header.ftl">
<LINK href="http://static.pinju.com/css/css.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/sell.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/member.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/goodsCategory.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/shopDecoration/base.css?t=20111203.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/rao/scores.css" rel="stylesheet" type="text/css" media="screen" />

<SCRIPT src="http://static.pinju.com/js/top.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/hd.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shopBaseInfo.js?t=201112061844.js"></SCRIPT>


<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>




<link rel="stylesheet" href="http://static.pinju.com/showloading/showLoading.css" />
<script charset="utf-8" src="http://static.pinju.com/showloading/jquery.showLoading.js"></script>
<link rel="stylesheet" href="http://static.pinju.com/kindeditor/themes/default/default.css" />
<script src="http://static.pinju.com/kindeditor/kindeditor-min.js"></script>
<script src="http://static.pinju.com/kindeditor/config.js"></script>
<script src="http://static.pinju.com/kindeditor/lang/zh_CN.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/category.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shopOpen.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/points/accountDetail.js"></SCRIPT>



<script src="http://static.pinju.com/image-preview/jquery.image-preview.js"></script>

<!------------------------------new--------------------------------------->




<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />

<style type="text/css">
	html {
		background: #FFF;
	}
</style>
</head>
<div class="top">
  <!-- 顶部导航  -->
  <#include "/default/templates/layout/site-nav.ftl">
  <!--logo和搜索-->
  <#include "/default/templates/layout/laybaner.ftl">
    <!--我的域网 -->
 <div class="wrap" id="Nav">
	 <ul>
     	<li class="left"></li>
	 	<li ><a href="${base}/management/buy.htm">我是买家</a></li>
	  	<li class="selected"><a href="javascript:">我是卖家</a></li>
	  	<li ><a href="${base}/management/account.htm">账号管理</a></li>
	 </ul>
  </div>

</div>


<!--left-->
<div class="wrap-border">
<div class="bar-left">
<h2 class="bar-topic"><a href="index.html">卖家后台</a></h2>
<ul class="bar-list">
 	<!--商品管理-->
 	<#include "/default/templates/control/itemManagement.ftl">
    <!--交易管理-->
    <#include "/default/templates/control/tradeManagement.ftl">
    <!--交易管理-->
    <#include "/default/templates/control/shopManagement.ftl">
    <!--分销管理-->
    <#include "/default/templates/control/supplierManagement.ftl">
    <!--积分管理-->
    <#include "/default/templates/control/pointsManagement.ftl">
    <!--退款维权-->
    <#include "/default/templates/control/rightsManagement.ftl">
    <!--营销中心 add by QHM 20110725-->
	<#include "/default/templates/control/activity/activityManagement.ftl">
   </ul>
</div>
<div class="contents">
	${body}
</div>
</div>
  
  <!--right-->
  
  
  <div class="cella"></div>
  <#include "/default/templates/layout/footer.ftl">
  </body>
  </html>
  
  
  