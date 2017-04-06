


        <style type="text/css">
	        body{text-align:center;}
	        .container{text-align:left;margin:2px auto;width:800px;}
	        input{display:block;}
	        .center{text-align:center;}
	        label{display:block;}
        </style>

    <div class = "container">
    	<label class = "center">我的购物篮</label>
    	
        <form id="paymentSubmit" name="paymentSubmit" action="<%= Configuration.paymentGateWayURL %>" method="post">
        <table>
        	<tr>
        		<td>金额 </td>
        		<td> </td>
        		<td> </td>
        		<td> 订单时间</td>
        		<td> </td>
        		<td>商品描述</td>
        		<td>商品备注1</td>
        		<td>商品备注2</td>
        		<td> </td>
        		<td> </td>
        		<td>操作</td>
            </tr>
        	<tr>
        		<td>
            		<input type="text" name="Amount" value="<%=_amount%>"/>
            	</td>
        		<td>
            		<input type="hidden" name="MerchantUserId" value="<%=_merchantUserId%>"/>
            	</td>
        		<td>
            		<input type="hidden" name="OrderNo" value="<%=_orderNo%>"/>
            	</td>
        		<td>
            		 <input type="text" name="OrderTime" value="<%=_orderNo%>"/>
            	</td>
        		<td>
            		<input type="hidden" name="ProductNo" value="<%=_productNo%>"/>
            	</td>
        		<td>
            		<input type="text" name="ProductDesc" value="<%=_productDesc%>"/>
            	</td>
        		<td>
            		<input type="text" name="Remark1" value="<%=_remark1%>"/>
            	</td>
        		<td>
            		<input type="text" name="Remark2" value="<%=_remark2%>"/>
            	</td>
        		<td>
            		<input type="hidden" name="ProductURL" value="<%=_productURL%>"/>
            	</td>
        		<td>
            		<input type="hidden" name="BankCode" value="<%=_bankCode%>"/>
            	</td>
        		<td>
            		<input type="button" name="v_action" value="付款" 
            	   		   onClick="document.forms['paymentSubmit'].submit();">
            	</td>
            </tr>
        </table>
        
         	<input type="hidden" name="Version" value="<%=Configuration.version%>"/>
            <input type="hidden" name="MerchantNo" value="<%=Configuration.merchantNo%>"/>
            <input type="hidden" name="PayChannel" value="<%=Configuration.payChannel%>"/>
            <input type="hidden" name="PostBackURL" value="<%=Configuration.postBackURL%>"/>
            <input type="hidden" name="NotifyURL" value="<%=Configuration.notifyURL%>"/>
            <input type="hidden" name="BackURL" value="<%=Configuration.backURL%>"/>
            <input type="hidden" name="CurrencyType" value="<%=Configuration.currencyType%>"/>
            <input type="hidden" name="NotifyURLType" value="<%=Configuration.notifyURLType%>"/>
            <input type="hidden" name="SignType" value="<%=Configuration.signType%>"/>
            <input type="hidden" name="DefaultChannel" value="<%=Configuration.defaultChannel%>"/>
            <input type="hidden" name="MAC" value="<%=_mac%>"/>
         </form>
    </div>

