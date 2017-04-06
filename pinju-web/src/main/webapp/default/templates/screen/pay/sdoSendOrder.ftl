<#setting classic_compatible=true>
<!-- 
<script type="text/javascript" src="http://static.pinju.com/js/trade/directPay.js"></script>
-->
        <style type="text/css">
	        body{text-align:center;}
	        .container{text-align:left;margin:2px auto;width:800px;}
	        input{display:block;}
	        .center{text-align:center;}
	        label{display:block;}
        </style>
    <div class = "container">
    	<label class = "center">
    		已为您生成即时到账支付订单,网站订单号:${OrderNo}。您将支付${Amount}元,正在为您跳转收银台中,请耐心等待......
    	</label>
    	
        <form id="sdoDirectPaySubmit" name="sdoDirectPaySubmit" action="${paymentGateWayURL}" method="post">
        <table>

        	<tr>
        		<td>
        		
            	</td>
        		<td>
            		<input type="button" name="v_action" value="付款" 
            	   		   onClick="document.forms['sdoDirectPaySubmit'].submit();">
            	</td>
            </tr>
        </table>
        	Amount<input type="text" name="Amount" value="${Amount}"/>
        	OrderNo<input type="text" name="OrderNo" value="${OrderNo}"/>
        	OrderTime<input type="text" name="OrderTime" value="${OrderTime}"/>
         	Version<input type="text" name="Version" value="${Version}"/>
            MerchantNo<input type="text" name="MerchantNo" value="${MerchantNo}"/>
            PostBackURL<input type="text" name="PostBackURL" value="${PostBackURL}"/>
            NotifyURL<input type="text" name="NotifyURL" value="${NotifyURL}"/>
            CurrencyType<input type="text" name="CurrencyType" value="${CurrencyType}"/>
            NotifyURLType<input type="text" name="NotifyURLType" value="${NotifyURLType}"/>
            SignType<input type="text" name="SignType" value="${SignType}"/>
            CharSet<input type="text" name="CharSet" value="${CharSet}"/>
            PayChannel<input type="text" name="PayChannel" value="${PayChannel}"/>
            ProductNo<input type="text" name="ProductNo" value="${ProductNo}"/>
            MAC<input type="text" name="MAC" value="${MAC}"/>
         </form>
    </div>

