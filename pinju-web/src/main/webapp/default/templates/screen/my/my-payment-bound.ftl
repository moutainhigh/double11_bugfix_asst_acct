<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>盛付通账号管理-绑定</title>
</head>

<body>
<h1 class="topic"><strong>绑定盛付通账户</strong></h1>
<div class="sft">
  <ul class="notic-linker">
    <h3>您尚未设置盛付通账户</h3>
    <p>“盛付通账户”可以帮助您实现安全、快速的网络支付。推荐您在品聚设置并保存您的盛付通账户。<a href="#">什么是“盛付通账户”</a> </p>
    <p><a href="https://www.shengpay.com/reg/main.aspx" target="_blank"><strong>立刻注册盛付通账号&gt;&gt;</strong></a></p>
  </ul>
  <ul class="notic-linker">
    <h3>如果您已有一个盛付通账户</h3>
    <p>请在下面表单中填入您在“盛付通账户”中设置的登录账户和登录密码：</p>
  </ul>

  <ul class="tips" id="pay-tips">
    <#if invokeMessage??><li>${invokeMessage}</li></#if>
    <#if (validator['account'])??><li>${validator['account']}</li></#if>
    <#if (validator['password'])??><li>${validator['password']}</li></#if>
  </ul>

  <form action="${base}/my/bound-pay-account.htm" method="post" id="pay-bound">
  <table>
    <tbody>
      <tr>
        <th>盛付通账号名：</th>
        <td><input name="account" class="text" type="text" id="account" maxlength="30"></td>
      </tr>
      <tr>
        <th>盛付通密码：</th>
        <td><input name="password" class="text" type="password" id="password" maxlength="30"></td>
      </tr>
    </tbody>
  </table>
  <ul class="button-main">
    <li><input type="submit" value="确定" class="button"></li>            
  </ul>
  <input type="hidden" name="_token_" value="${_token_}">
  </form>
</div>
<input type="hidden" value="pay-account" id="my-page" />
<script type="text/javascript">
<#if !invokeMessage?? && !validator??>(function(){$('#pay-tips').hide();})();</#if>
(function(){
  $('input').focus(function(){
    $(this).select();
  })
  $('#account').focus();
})();
$(document).ready(function(){
  $('#pay-bound').submit(function(){
    $('#pay-tips').empty().hide();
    if($.trim($('#account').val()).length==0){
      s('#account','请填写盛付通账号名');
      return false;
    }
    if($.trim($('#password').val()).length==0){
      s('#password', '请填写盛付通密码');
      return false;
    }
    return true;
  });
  function s(n,m){
    $('#pay-tips').append($('<li/>').text(m)).show();
    $(n).focus().select();
  }
});
</script>
</body>
</html>
