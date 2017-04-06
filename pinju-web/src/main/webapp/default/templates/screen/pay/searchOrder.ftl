<#setting classic_compatible=true>
<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/trade/directPay.js"></script>
<style type="text/css">
        body{text-align:center;}
        .container{text-align:left;margin:2px auto;width:800px;}
        input{display:block;}
        .center{text-align:center;}
        label{display:block;}
    </style>
<div class = "container">
	<label class = "center">
		查询订单中,请耐心等待！......
	</label>
	
</div>
<script type="text/javascript">
jQuery(document).ready(function($){goTenPay("${signUrl!""}");});
</script>
