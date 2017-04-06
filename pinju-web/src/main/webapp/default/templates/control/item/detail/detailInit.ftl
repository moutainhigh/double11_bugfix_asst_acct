 <#if query.jsSkuInit??>
    <script type="text/javascript" src="http://static.pinju.com/js/item/detailsku.js"></script>
  </#if>
    <script type="text/javascript">
    var base="${base?js_string}";
    var templateId="${itemDO.freeTemplateId!?js_string}";
    var sellerMemberId="${itemDO.sellerId!?js_string}";
    var itemId="${itemDO.id!?js_string}";
    var buyNowAction=base+"/orderBuyer/lastTimeBuySell.htm";
    var logisticsAction=base+"/logisticsAjax/queryAreaCarriage.htm";
    var shopCartAction=base+"/cart/addItem.htm";
    var limitBuyAction=base+"/sales/validateCode.htm";
    var isActDiscount=false;
    var isSku=false;
    var isLimitBuyItem=false;
    var isClickBuyRecord=false;	
	//var snsRateUrl="http://cuttle.pinju.com:8080";										//购买评价地址
	var isLoadRate=false;
	var ratePageSize = 10;																	//购买评价显示条数
	var buyPageSize = 10;     																//购买记录每页显示条数
	var IMG_SERVER = '${imageServer}';														//图片服务器
	var RATE_DEFAULT_IMG = 'http://img.pinju.com/face-default/face_50x50.jpg';				//评价默认图片
	<#if query.activityDiscount??>
		isActDiscount=true;
		var actStartHour = ${query.actHour!?js_string};
		var actStartMinute = ${query.actMinute!?js_string}+1; 
		var actDiscount=${query.activityDiscount.toString()!?js_string};
	</#if>
	<#if query.isLimitBuyItem??&&query.isLimitBuyItem==true>
		isLimitBuyItem=true;
	</#if>
	
    <#if query.jsSkuInit??>
    	isSku=true;
        var sku = new SmartSKU(".J_skuList", {
            'PNC': 1,
            'upper-limit': '${query.upperPrice!?js_string}',
            'lower-limit': '${query.lowerPrice!?js_string}',
            <#if query.jsSkuInit??>
				<#assign jsSkuInit = query.jsSkuInit>
					<#list jsSkuInit as skuJsMap>
							'${skuJsMap.JsSkuPv?js_string}': ['${skuJsMap.JsSkuPrice?js_string}', ${skuJsMap.JsSkuCurrentStock?js_string},${skuJsMap.JsSkuId?js_string}]<#if skuJsMap_has_next>,</#if>
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
			    			$("#current_value").text("获取物流信息失败");
			        	}
			    }
			)
		});
	 </#if>

    </script>
