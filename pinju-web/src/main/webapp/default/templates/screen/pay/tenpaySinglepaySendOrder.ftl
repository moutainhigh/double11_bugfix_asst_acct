<#setting classic_compatible=true>

<style type="text/css">
        body{text-align:center;}
        .container{text-align:left;margin:2px auto;width:800px;}
        input{display:block;}
        .center{text-align:center;}
        label{display:block;}
    </style>
<div class = "container">
	<label class = "center">
		已为您生成即时到账支付订单,网站订单号:${orderDO.orderId}。您将支付${orderDO.priceAmountByYuan}元,正在为您跳转收银台中,请耐心等待！......
		<br>${postUrl}
	</label>
	
</div>
<script type="text/javascript">
//window.location.href = "${postUrl}";
</script>
