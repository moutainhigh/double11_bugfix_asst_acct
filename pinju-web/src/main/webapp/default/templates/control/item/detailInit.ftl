 <#if query.jsSkuInit??>
    <script type="text/javascript" src="http://static.pinju.com/js/item/detailsku.js"></script>
  </#if>
    <script type="text/javascript">
    var base="${base}";
    var templateId="${itemDO.freeTemplateId!}";
    var sellerMemberId="${itemDO.sellerId!}";
    var buyNowAction=base+"/orderBuyer/lastTimeBuySell.htm";
    var logisticsAction=base+"/logisticsAjax/queryAreaCarriage.htm";
    var shopCartAction=base+"/cart/addItem.htm";
    <#if query.jsSkuInit??>
        var sku = new SmartSKU(".J_skuList", {
            'PNC': 1,
            'upper-limit': '${query.upperPrice!}',
            'lower-limit': '${query.lowerPrice!}',
            <#if query.jsSkuInit??>
				<#assign jsSkuInit = query.jsSkuInit>
					<#list jsSkuInit as skuJsMap>
							'${skuJsMap.JsSkuPv}': ['${skuJsMap.JsSkuPrice}', ${skuJsMap.JsSkuCurrentStock},${skuJsMap.JsSkuId}]<#if skuJsMap_has_next>,</#if>
					</#list>
			</#if>
        });
        sku.getSKU();
    </#if>
	<#if itemDO.freeTemplateId??&&itemDO.freeTemplateId!=0>
		$(document).ready(function(){
			postUrl=base+"/async/logistics/getLocalLogistics.htm?templateId="+templateId;
			jQuery.post(postUrl,
			    function(data){ //回传函数
			    		var postInfos = eval(data); 
				    	if(postInfos.sucess==true){
				    		var cityName=postInfos.cityName;
				    		var logisticsStr=postInfos.logisticsStr;
				    		$("#current_city").text(cityName);
				    		$("#current_value").text(logisticsStr);
				    	}else{
			    			alert('获取物流信息失败');
			        	}
			    }
			)
		});
	 </#if>

			
    </script>
