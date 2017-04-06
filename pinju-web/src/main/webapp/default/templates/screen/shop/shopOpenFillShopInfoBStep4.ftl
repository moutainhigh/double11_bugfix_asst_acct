<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shop.js"></SCRIPT>-->

<#setting classic_compatible=true>
            	<div class="steper"><h2 class="steper02"></h2></div>
                <div class="info">
                    <ul class="tab-main">
                    	<li><a href="javascript:void(0)">店铺信息</a></li>
                    	<li><a href="javascript:void(0)">企业信息</a></li>
                    	<li><a href="javascript:void(0)">品牌相关</a></li>
                    	<li class="count"><a href="javascript:void(0)">联系方式</a></li>
                    </ul>
     
   <div class="shop_pointer_style">
     	<a class=" fr" href="http://service.pinju.com/cms/html/2011/teach_0825/53.html" target="_blank">了解更多开店详情</a>
     	<p class="red bd">以下资料附带 * 的都为必填项目,请详细填写</p>
     </div> 
      
 <form id="shopInfoForm" action="${base}/shop/saveBusinessShopInfoAction.htm?fillStep=4" method="post" onsubmit="return checkBusinessShopStep4()">    
   <input type="hidden" name="pj0" value="${pj0}" />
   
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <#if shopBusinessInfoDO?exists>
   <input type="hidden" id="myProvB" name="myProvC" value="${shopBusinessInfoDO.province}"/>
	<input type="hidden" id="myCityB" name="myCityC" value="${shopBusinessInfoDO.city}"/>
      <!--shopInfo4 begin-->
      	<#if errorMessage?? && errorMessage!="">
		<div class="shop_pointer_style">
			<p class="red">${errorMessage}</p>
		</div>
	</#if> 
	<!--<h4><strong>联系人信息</strong></h4>
                    <table>
                    	<tr>
                        	<th>联系人姓名</th>
                        	<td><input type="text" maxlength="20" id="contactName" name="shopBusinessInfoDO.contactName"  value="${shopBusinessInfoDO.contactName}" onblur="checkOnblur('#contactName','#contactNametip');spCheck('contactName','#contactNametip');" class="text"><span id="contactNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>联系人电话</th>
                        	<td><input type="text" id="contactPhone" name="shopBusinessInfoDO.contactPhone" value="${shopBusinessInfoDO.contactPhone}" maxlength="20" onblur="spCheck('contactPhone','#contactPhonetip')" class="text"><span id="contactPhonetip" class="tip"></span><p>手机号码或固定电话。例如：12345678901或021-12345678</p></td>
                        </tr>
                    	<tr>
                        	<th>联系地址</th>
                        	<td><input type="text" id="contactAddress" name="shopBusinessInfoDO.contactAddress" value="${shopBusinessInfoDO.contactAddress}" onblur="checkOnblur('#contactAddress','#contactAddresstip')" class="text"><span id="contactAddresstip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>邮政编码</th>
                        	<td><input type="text" id="contactPostalCode" name="shopBusinessInfoDO.contactPostalCode" value="${shopBusinessInfoDO.contactPostalCode}" maxlength="6" onblur="spCheck('contactPostalCode','#contactPostalCodetip')" class="text"><span id="contactPostalCodetip" class="tip"></span><p>6位邮政编码</p></td>
                        </tr>
                     </table>  -->
                	<h4><strong>店铺联系人信息</strong></h4>
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>姓名</th>
                        	<td><input type="text" maxlength="20" id="shopManagerName" name="shopBusinessInfoDO.shopManagerName" value="${shopBusinessInfoDO.shopManagerName?html}" onblur="checkOnblur('#shopManagerName','#shopManagerNametip');spCheck('shopManagerName','#shopManagerNametip');" class="text"><span id="shopManagerNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>电话</th>
                        	<td><input type="text" id="shopManagerTelephone" name="shopBusinessInfoDO.shopManagerTelephone" value="${shopBusinessInfoDO.shopManagerTelephone?html}" maxlength="20" onblur="spCheck('shopManagerTelephone','#shopManagerTelephonetip')" class="text"><span id="shopManagerTelephonetip"  class="tip"></span><p>固定电话号码,例如：021-12345678</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>手机</th>
                        	<td><input type="text" id="shopManagerMobile" name="shopBusinessInfoDO.shopManagerMobile" value="${shopBusinessInfoDO.shopManagerMobile?html}"  maxlength="20" onblur="spCheck('shopManagerMobile','#shopManagerMobiletip')" class="text"><span id="shopManagerMobiletip"  class="tip"></span><p>手机号码,例如：12345678901</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>邮箱</th>
                        	<td><input type="text" id="shopManagerEmail" name="shopBusinessInfoDO.shopManagerEmail" value="${shopBusinessInfoDO.shopManagerEmail?html}" onblur="spCheck('shopManagerEmail','#shopManagerEmailtip')" class="text"><span id="shopManagerEmailtip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>传真</th>
                        	<td><input type="text" id="shopManagerFax" name="shopBusinessInfoDO.shopManagerFax" value="${shopBusinessInfoDO.shopManagerFax?html}" maxlength="20"  class="text"><span id="shopManagerFaxtip"  class="tip"></span><p>传真号码,例如:021-12345678(选填)</p></td>
                        </tr>
                        <tr>
                        	<th>QQ</th>
                        	<td><input type="text" id="qq" name="shopBusinessInfoDO.qq" value="${shopBusinessInfoDO.qq?html}" maxlength="20"  class="text"><span id="qqtip"  class="tip"></span></td>
                        </tr>
                        <tr>
                        	<th>MSN</th>
                        	<td><input type="text" id="msn" name="shopBusinessInfoDO.msn" value="${shopBusinessInfoDO.msn?html}" maxlength="50"  class="text"><span id="msntip"  class="tip"></span></td>
                        </tr>
                     </table>  
                	<!--<h4><strong>紧急联系人信息</strong></h4>
                    <table>
                    	<tr>
                        	<th>姓名</th>
                        	<td><input maxlength="20" type="text" id="emergencyContactName" name="shopBusinessInfoDO.emergencyContactName" value="${shopBusinessInfoDO.emergencyContactName}" onblur="checkOnblur('#emergencyContactName','#emergencyContactNametip');spCheck('emergencyContactName','#emergencyContactNametip');" class="text"><span id="emergencyContactNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>手机</th>
                        	<td><input type="text" id="emergencyContactPhone" name="shopBusinessInfoDO.emergencyContactPhone" value="${shopBusinessInfoDO.emergencyContactPhone}" maxlength="20" onblur="spCheck('emergencyContactPhone','#emergencyContactPhonetip')" class="text"><span id="emergencyContactPhonetip" class="tip"></span><p>手机号码,例如：12345678901</p></td>
                        </tr>
                     </table>-->
                     
      <!--shopInfo4 end-->
    
    <#else>
			      
			      <!--shopInfo4 begin-->
			      	
			      	<#if errorMessage?? && errorMessage!="">
		<div class="shop_pointer_style">
			<p class="red">${errorMessage}</p>
		</div>
	</#if>
	<!--<h4><strong>联系人信息</strong></h4>
                    <table>
                    	  	<tr>
                        	<th>联系人姓名</th>
                        	<td><input type="text" maxlength="20" id="contactName" name="shopBusinessInfoDO.contactName"   onblur="checkOnblur('#contactName','#contactNametip')" class="text"><span id="contactNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>联系人电话</th>
                        	<td><input type="text" id="contactPhone" name="shopBusinessInfoDO.contactPhone"  maxlength="20" onblur="spCheck('contactPhone','#contactPhonetip')" class="text"><span id="contactPhonetip" class="tip"></span><p>手机号码或固定电话。例如：12345678901或021-12345678</p></td>
                        </tr>
                    	<tr>
                        	<th>联系地址</th>
                        	<td><input type="text" id="contactAddress" name="shopBusinessInfoDO.contactAddress"  onblur="checkOnblur('#contactAddress','#contactAddresstip')" class="text"><span id="contactAddresstip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>邮政编码</th>
                        	<td><input type="text" id="contactPostalCode" name="shopBusinessInfoDO.contactPostalCode"  maxlength="6" onblur="spCheck('contactPostalCode','#contactPostalCodetip')" class="text"><span id="contactPostalCodetip" class="tip"></span><p>6位邮政编码</p></td>
                        </tr>
                     </table> --> 
                	<h4><strong>店铺联系人信息</strong></h4>
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>姓名</th>
                        	<td><input type="text" maxlength="20" id="shopManagerName" name="shopBusinessInfoDO.shopManagerName"  onblur="checkOnblur('#shopManagerName','#shopManagerNametip')" class="text"><span id="shopManagerNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>电话</th>
                        	<td><input type="text" id="shopManagerTelephone" name="shopBusinessInfoDO.shopManagerTelephone"  maxlength="20" onblur="spCheck('shopManagerTelephone','#shopManagerTelephonetip')" class="text"><span id="shopManagerTelephonetip"  class="tip"></span><p>固定电话号码,例如：021-12345678</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>手机</th>
                        	<td><input type="text" id="shopManagerMobile" name="shopBusinessInfoDO.shopManagerMobile"  maxlength="20" onblur="spCheck('shopManagerMobile','#shopManagerMobiletip')" class="text"><span id="shopManagerMobiletip"  class="tip"></span><p>手机号码,例如：12345678901</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>邮箱</th>
                        	<td><input type="text" id="shopManagerEmail" name="shopBusinessInfoDO.shopManagerEmail"  onblur="spCheck('shopManagerEmail','#shopManagerEmailtip')" class="text"><span id="shopManagerEmailtip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>传真</th>
                        	<td><input type="text" id="shopManagerFax" name="shopBusinessInfoDO.shopManagerFax"  maxlength="20"  class="text"><span id="shopManagerFaxtip"  class="tip"></span><p>传真号码,例如:021-12345678(选填)</p></td>
                        </tr>
                        <tr>
                        	<th>QQ</th>
                        	<td><input type="text" id="qq" name="shopBusinessInfoDO.qq" maxlength="20"  class="text"><span id="qqtip"  class="tip"></span></td>
                        </tr>
                        <tr>
                        	<th>MSN</th>
                        	<td><input type="text" id="msn" name="shopBusinessInfoDO.msn" maxlength="50"  class="text"><span id="msntip"  class="tip"></span></td>
                        </tr>
                     </table>  
                	<!--<h4><strong>紧急联系人信息</strong></h4>
                    <table>
                    	<tr>
                        	<th>姓名</th>
                        	<td><input type="text" maxlength="20" id="emergencyContactName" name="shopBusinessInfoDO.emergencyContactName"  onblur="checkOnblur('#emergencyContactName','#emergencyContactNametip')" class="text"><span id="emergencyContactNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>手机</th>
                        	<td><input type="text" id="emergencyContactPhone" name="shopBusinessInfoDO.emergencyContactPhone"  maxlength="" onblur="spCheck('emergencyContactPhone','#emergencyContactPhonetip')" class="text"><span id="emergencyContactPhonetip" class="tip"></span><p>手机号码,例如：12345678901</p></td>
                        </tr>
                     </table>-->
                     
			      <!--shopInfo4 end-->
    
    </#if>
						<ul class="button-main">
                    	<li>
                    	<p id="submitError" style="color:red"></p>
                    	<a class="button" href="/shop/prevStepAction.htm?prevStep=3">上一步</a>
                    	<input id="step4Submit" type="submit" value="提交" class="button">
                    	</li>
                    </ul>
                </div>
                <div class="cf"></div>
                <input type="hidden" name="_token_" value="${_token_}" />
                </form>
<script>  

              
function addShopInfoToCookieStep4(form, callback){
     $('input,textarea', form).each(function() {
        var input = this;
		if ("onpropertychange" in input){ //IE6/IE7/IE8
			input.onpropertychange = function(){
				if (window.event.propertyName == "value"){
					callback.call(this, window.event);
				}
			}
		} else {
			// Fix Firefox Bug: https://bugzilla.mozilla.org/show_bug.cgi?id=195696
			input.addEventListener("input", callback, false);
		}
	});
	$('select', form).change(function() {
		callback.call(this, window.event);
	});
}
addShopInfoToCookieStep4($('#shopInfoForm'), function(){
	var shopManagerName = $("#shopManagerName").val();
	var shopManagerTelephone = $("#shopManagerTelephone").val();
	var shopManagerMobile = $("#shopManagerMobile").val();
	var shopManagerEmail = $("#shopManagerEmail").val();
	var shopManagerFax = $("#shopManagerFax").val();
	
	shopManagerName = "shopManagerName="+encodeURIComponent(shopManagerName);
	shopManagerTelephone = "shopManagerTelephone="+shopManagerTelephone;
	shopManagerMobile = "shopManagerMobile="+shopManagerMobile;
	shopManagerEmail = "shopManagerEmail="+shopManagerEmail;
	shopManagerFax = "shopManagerFax="+shopManagerFax;
	
	var strCookie = shopManagerName + "," + shopManagerTelephone + "," +shopManagerMobile + "," + shopManagerEmail + "," + shopManagerFax + ",";
	PinjuCookie.writeCookie('PJ4', strCookie, 'www.pinju.com', '/shop', 3);
});
</script>                
