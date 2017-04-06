<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<title>错误提示</title>
<link href="http://static.pinju.com/css/shopcart/buycart.css" rel="stylesheet" />
<!-- content -->
<div class="wrap cf">
  <!--内容页面开始-->
  <div class="main_1000px page_center">

    <div class="cart_container">
      <div class="title">
        <h2>错误提示</h2>
        <div class="horizen_gray"><span class="horizen_red"></span></div>
      </div>

      <div class="ratewrap">
        <div class="tipswrap">
          <div class="infocat error"></div>
          <div class="content">
            <h3 class="key"><strong>没有权限进行<#if __permission__??><span style="color:gray;margin:0 5px;">${__permission__.targetDesc} &raquo; ${__permission__.actionDesc}</span><#else>该</#if>操作</strong></h3>
            <p><br />您现在可以：
            <br />点击 <a href="${returnUrl?html}" title="重试一下">这里</a> 重试一下
            <br />点击 <a href="http://www.pinju.com/member/logout.htm?returnUrl=http%3A%2F%2Fwww.pinju.com%2Fmember%2Flogin.htm%3FreturnUrl%3D${returnUrl?url?url}" title="重新登录">这里</a> 退出后换一个账号重新进行操作
            <br />联系该账号之主账号所有者分配该操作所对应的权限
            <br />如果已为该账号分配了拥有该权限的角色，请 <a href="http://www.pinju.com/member/logout.htm?returnUrl=http%3A%2F%2Fwww.pinju.com%2Fmember%2Flogin.htm%3FreturnUrl%3D${returnUrl?url?url}" title="重新登录">重新登录</a> 后再进行操作
            <br />返回 <a href="${history?html}" title="上一个页面">上一个页面</a>
            <br />返回 <a href="http://www.pinju.com/my/sell.htm" title="我是卖家">我是卖家</a>
            <br />返回 <a href="http://www.pinju.com" title="品聚首页">品聚首页</a>
            <br />品聚网客服电话：4008-211-588</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>