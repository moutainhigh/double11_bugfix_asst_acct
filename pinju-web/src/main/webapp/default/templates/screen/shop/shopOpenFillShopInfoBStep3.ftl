<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shopInitBrand.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shop.js"></SCRIPT>-->
<!--<SCRIPT src="${base}/default/js/shopInitBrand.js"></SCRIPT>-->

<#setting classic_compatible=true>
            	<div class="steper"><h2 class="steper02"></h2></div>
                <div class="info">
                    <ul class="tab-main">
                    	<li><a href="javascript:void(0)">店铺信息</a></li>
                    	<li><a href="javascript:void(0)">企业信息</a></li>
                    	<li class="count"><a href="javascript:void(0)">品牌相关</a></li>
                    	<li><a href="javascript:void(0)">联系方式</a></li>
                    </ul>
     
  <div class="shop_pointer_style">
     	<a class=" fr" href="http://service.pinju.com/cms/html/2011/teach_0825/53.html" target="_blank">了解更多开店详情</a>
     	<p class="red bd">以下资料附带 * 的都为必填项目,请详细填写</p>
     </div>  
   <iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe>    
 <form id="shopInfoForm" action="${base}/shop/saveBusinessShopInfoAction.htm?fillStep=3" method="post" onsubmit="return checkBrandAndConfirm()"  enctype ="multipart/form-data">    
   <input type="hidden" name="pj0" value="${pj0}" />
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <#if shopBusinessInfoDO?exists>
   <input type="hidden" id="trademarkNumberHidden" name="shopBusinessInfoDO.trademarkNumber" value="${shopBusinessInfoDO.trademarkNumber?html}"/>
   <input type="hidden" id="brandNameHidden" name="shopBusinessInfoDO.brandName" value="${shopBusinessInfoDO.brandName?html}"/>
   <input type="hidden" id="brandEnglishNameHidden" name="shopBusinessInfoDO.brandEnglishName" value="${shopBusinessInfoDO.brandEnglishName?html}"/>
   <input type="hidden" id="brandStoryHidden" name="shopBusinessInfoDO.brandStory" value="${shopBusinessInfoDO.brandStory?html}"/>
    <h4><strong>添加品牌信息</strong><span>目前最多可添加6个品牌</span></h4>
    <#if errorMessage?? && errorMessage!="">
		<div class="shop_pointer_style">
			<p class="red">${errorMessage}</p>
		</div>
	</#if> 
	     <div id="brandInfo" class="brandInfo">
		     
	     </div>
     <ul class="add-brand"><input type="button" value="+添加品牌" id="addBrand" name="addBrand" onclick="addBrandInfo()"/></ul>
    
     <ul class="button-main"><p id="submitError" style="color:red"></p> <a class="button" href="/shop/prevStepAction.htm?prevStep=2">上一步</a><input id="step3Submit" type="submit" value="下一步" class="button"/></ul>
     
</#if>
<input type="hidden" name="_token_" value="${_token_}" />
</form> 
                </div>
                <div class="cf"></div>
<script>

</script>