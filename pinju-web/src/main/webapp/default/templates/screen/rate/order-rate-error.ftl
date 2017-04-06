<#setting classic_compatible=true>
<!-- content -->
<div class="wrap cf">
<link href="http://static.pinju.com/css/shopcart/buycart.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/rao/buy.css" rel="stylesheet" type="text/css" media="screen" />

  <!--内容页面开始-->
  <div class="main_1000px page_center">
    <#include "/default/templates/layout/rate/order-rate-common.ftl">  

    <div class="cart_container">
      <div class="title">
        <h2>评价商品</h2>
        <div class="horizen_gray"><span class="horizen_red"></span></div>
      </div>

      <div class="ratewrap">
        <div class="tipswrap">
          <div class="infocat error"></div>
          <div class="content">
            <h3 class="key"><strong>${invokeMessage}</strong></h3>
            <@load.orderRateMessage mid="${mid!}" msg="" />
          </div>
        </div>
      </div>
    </div>
  </div>
</div>