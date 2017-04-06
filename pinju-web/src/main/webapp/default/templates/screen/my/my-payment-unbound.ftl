<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>盛付通账号管理-解绑</title>  
</head>

<body>
<h1 class="topic"><strong>盛付通账号管理</strong></h1>
<div class="sft">
  <ul class="notic-linker">
    <li><a href="#">盛付通密码忘记了怎么办？</a></li>
    <li><a href="#">品聚和盛付通账号为什么无法解绑？</a></li>
    <li><a href="#">查看更多问题 &raquo;</a></li>
  </ul>

  <#if invokeMessage??>
  <ul class="tips" id="pay-tips">
    <li>${invokeMessage}</li>
  </ul>
  </#if>

  <table>
    <caption>您的盛付通账号已绑定!</caption>
    <tbody>
      <tr>
        <th>账户：</th>
        <td><strong>${payment.accountNO}</strong></td>
      </tr>
      <tr>
        <th>状态：</th>
        <td><span>已绑定品聚网</span></td>
      </tr>
      <tr>
        <th>账户类型：</th>
        <td><#if payment.accountType == 1>个人<#elseif payment.accountType == 2>企业</#if>账户</td>
      </tr>
      <tr>
        <th>可用余额：</th>
        <td><a href="#">查询</a><a href="#">充值</a></td>
      </tr>
    </tbody>
  </table>
  <ul class="button-main">
    <li><input name="" value="查看账户" class="button" type="button"></li>
    <li class="checkbox"><a href="${base}/my/unbound-pay-account.htm?_token_=${_token_}">解除账户绑定</a></li>
  </ul>
</div>
<input type="hidden" value="pay-account" id="my-page" />
<#if !invokeMessage??>
<script type="text/javascript">
(function(){$('#pay-tips').hide();})();
</script>
</#if>
</body>
</html>
