<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jq.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shopOpen.js"></SCRIPT>

<style>
	.err_box {
    background: url("http://static.pinju.com/images/err_5_03.gif") no-repeat scroll 0 0 transparent;
    color: #BE0000;
    float: left;
    font-size: 14px;
    font-weight: bold;
    height: 30px;
    margin: 0 0 30px 60px;
    padding: 15px 0 0 60px;
}
</style>

    <#if invokeMessage??>
    <div class="err_box">
    	${invokeMessage}
    	<br/><br/><br/>
    	<p><a href= "${base}/shop/showShopOpenAction.htm"><img src="http://static.pinju.com/images/store_but_10.gif"></a><p>
    </div>
    
    </#if>
    

<#if !invokeMessage?? && !validator??>(function(){$('#pay-tips').hide();})();</#if>


