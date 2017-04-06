<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>商城--御网</title>

</head>

<body>
<!--头部开始-->
<!--顶部状态-->
<div class="top">

</div>
<!--头部结束-->

<!--内容页面开始-->
<div class="cart-content">
 
  <div style="text-align:center;">
  </br>
   <form action="${base}/orderPay/pay.htm" id="pay" method = "post">
   <input type="hidden" name="${tokenName}" value="${token}">
	 <#if orderIdList??>
	   <#list orderIdList as orderId>
	   		<input type = "hidden" name="orderId" value="${orderId}">
	   	</#list>
	 </#if>	
  	<input type="submit" value="确认支付"/>
   </form>
  </div>
 </div>

</body>
</html>