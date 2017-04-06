<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>财付通账号管理-解绑</title>  
</head>

<body>
<h1 class="topic"><strong>财付通账号管理</strong></h1>
<div class="sft">
  <#if invokeMessage??>
  <ul class="tips" id="pay-tips">
    <li>${invokeMessage}</li>
  </ul>
  </#if>

  <table>
    <caption>您的财付通账号已绑定!</caption>
    <tbody>
      <tr>
        <th>账户：</th>
        <td><strong><#if payment?exists>${payment.accountNO}</#if></strong></td>
      </tr>
      <tr>
        <th>状态：</th>
        <td><span>已绑定品聚网</span></td>
      </tr>
    </tbody>
  </table>
</div>
<input type="hidden" value="pay-account" id="my-page" />
<#if !invokeMessage??>
<script type="text/javascript">
(function(){$('#pay-tips').hide();})();
</script>
</#if>
</body>
</html>
