<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shop.js"></SCRIPT>-->

<#setting classic_compatible=true>
<iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe>
<form id="shopInfoForm" action="${base}/shop/saveCustomerShopInfoAction.htm" onsubmit="return checkShopCInfo()" method="post" enctype ="multipart/form-data">
<input type="hidden" name="pj0" value="${pj0}" />
<input type="hidden" value="red_shopOpen" id="my-page" />
<input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
<input type="hidden" value="red_shopOpen" id="my-page" />
<#if shopCustomerInfoDO?exists>
<input type="hidden" id="myProvC" name="myProvC" value="${shopCustomerInfoDO.province}"/>
<input type="hidden" id="myCityC" name="myCityC" value="${shopCustomerInfoDO.city}"/>
            	<div class="steper"><h2 class="steper02"></h2></div>
                <div class="info">
                	<div class="shop_pointer_style">
				     	<a class=" fr" href="http://service.pinju.com/cms/html/2011/teach_0825/53.html" target="_blank">了解更多开店详情</a>
				     	<p class="red bd">以下资料附带 * 的都为必填项目,请详细填写</p>
				     </div>
                	<h4><strong>店铺信息</strong></h4>
				     <#if errorMessage?? && errorMessage!="">
				     <div class="shop_pointer_style">
				     	<p class="red">${errorMessage}</p>
				     </div>
				     </#if>
				     
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>店铺名称</th>
                        	<td>
                        	<input type="text" maxlength="20" id="shopName" name="shopCustomerInfoDO.name" value="${shopCustomerInfoDO.name?html}" onblur="checkRename('#shopName','#shopNametip')" class="text">
                        	<span id="shopNametip" class="tip"></span>
                        	<p><a href="http://service.pinju.com/cms/html/2011/bis_mng_0823/47.html" target="_blank">店铺命名须知</a></p>
                        	</td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>店铺类目</th>
                        	<td>
                            	<select id="shopCategory" name="shopCustomerInfoDO.shopCategory" onblur="spCheck('shopCategory','#shopCategorytip')">
							         <option value="">选择店铺类目</option>
							         	<#if shopCategoryList??>
							      		<#list shopCategoryList.keySet() as shopCategory>${shopCategory},
							      			<#if shopCategory?? && shopCategory != "">
								      			<#if shopCustomerInfoDO.shopCategory ==shopCategory>
													<option value="${shopCategory}" selected>${shopCategoryList.get(shopCategory)}</option>
												<#else>
													<option value="${shopCategory}" >${shopCategoryList.get(shopCategory)}</option>
												</#if>
											</#if>
							      		</#list>
							      		</#if>
							      </select>
                                <span id="shopCategorytip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>卖家类型</th>
                        	<td>
                            	<select id="shopNature" name="shopCustomerInfoDO.shopNature">
							       <#list shopNatureListC as shopNatureC>
							      			<#if shopCustomerInfoDO.shopNature == shopNatureC_index>
												<option value="${shopNatureC_index}" selected>${shopNatureC}</option>
											<#else>
												<option value="${shopNatureC_index}" >${shopNatureC}</option>
											</#if>
							      		</#list>
							      </select>
                                 <span id="shopNaturetip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店铺介绍</th>
                        	<td>
	                            <textarea id="descriptionC"  name="shopCustomerInfoDO.description" onblur="checkOnblur('#descriptionC','#descriptionCtip');">${shopCustomerInfoDO.description?html}</textarea>
	                            <span id="descriptionCtip" class="tip"></span>
	                            <p>请控制在500字以内</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店标</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile1">
                                <span id="myFile1tip" class="hui"></span>
                                <p>图片尺寸100px×100px， 文件大小80K以内，  请使用jpg、gif、png格式</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>主要货源</th>
                        	<td>
                            	<#list goodsSourceList as goodsSource>
									<#if shopCustomerInfoDO.goodsSource == goodsSource_index>
										<label>
											<input id="goodsSource" name="shopCustomerInfoDO.goodsSource" type="radio" value="${goodsSource_index}" checked="checked" />${goodsSource}
										</label>
									<#else>
										<label>
											<input id="goodsSource" <#if goodsSource_index == 7>checked="checked"</#if> name="shopCustomerInfoDO.goodsSource" type="radio" value="${goodsSource_index}" />${goodsSource}
										</label>
									</#if>
								</#list>
								<span id="goodsSourcetip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>企业所在地</th>
                        	<td>
                            	<span id="shopCityC"></span><span id="shopCustomerInfoDOprovincetip" class="tip"></span>
                            </td>
                        </tr>
                    </table>
                    
                     <h4><strong>商户信息</strong></h4>
                    <table>
                        <tr>
                        	<th><font color="red"> * </font>是否有其他网店</th>
                        	<td>
                        	<#list isOpenOuterShopList as isOpenOuterShop>
                                 <#if isOpenOuterShop_index==0>
									<label>
										<input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}"  onclick="spCheck('isHaveOuterShop','.shopCustInfo')" checked="checked" />${isOpenOuterShop}
									</label>
								 <#else>
								      <label>
	                                     <input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}" onclick="spCheck('isHaveOuterShop','.shopCustInfo')"/>${isOpenOuterShop}
							          </label>
								</#if>
							  </#list>
                            </td>
                        </tr>
                    </table>
                    <div class="shopCustInfo"> 
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>网上店铺地址</th>
                        	<td><input id="shopAddressUrl" name="shopCustomerInfoDO.outerShopAddressUrl" type="text" class="text" value="${shopCustomerInfoDO.outerShopAddressUrl?html}" onblur="spCheck('shopAddressUrl','#shopAddressUrltip')"/><span id="shopAddressUrltip" class="tip"></span>
                            <p>淘宝、拍拍等网站的店铺地址。例如：http://shop.xxx.com/123456或者http://123456.xxx.com</p>
                            </td>
                        </tr>
                    	<tr>
                        	<th>店铺等级</th>
                        	<td>
                              <#list shopLevelList as shopLevel>
                                 <#if shopCustomerInfoDO.outerShopLevel == shopLevel_index>
									<label>
										<input id="shopLevel" name="shopCustomerInfoDO.outerShopLevel" type="radio" value="${shopLevel_index}"  checked="checked" />${shopLevel}
									</label>
								 <#else>
								      <label>
	                                     <input id="shopLevel" name="shopCustomerInfoDO.outerShopLevel" type="radio" value="${shopLevel_index}" />${shopLevel}
							          </label>
								</#if>
							  </#list>
							  <span id="shopLeveltip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>年销售规模</th>
                        	<td>
                             <#list saleScopeList as saleScope>
                                 <#if shopCustomerInfoDO.outerShopSaleScope == saleScope_index>
									<label>
										<input id="saleScope" name="shopCustomerInfoDO.outerShopSaleScope" type="radio" value="${saleScope_index}" checked="checked" />${saleScope}
									</label>
								 <#else>
								      <label>
	                                     <input id="saleScope" name="shopCustomerInfoDO.outerShopSaleScope" type="radio" value="${saleScope_index}" />${saleScope}
							          </label>
								</#if>
							  </#list>
							   <span id="saleScopetip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>是否入驻过B2C</th>
                        	<td>
                            <#list isEnterB2cList as isEnterB2c>
                                 <#if shopCustomerInfoDO.isEnterB2c == isEnterB2c_index>
									<label>
										<input id="isEnterB2c" name="shopCustomerInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" checked="checked" />${isEnterB2c}
									</label>
								 <#else>
								      <label>
	                                     <input id="isEnterB2c" name="shopCustomerInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" />${isEnterB2c}
							          </label>
								</#if>
							  </#list>
							   <span id="isEnterB2ctip" class="tip"></span>
							   <p>例如：京东、当当、麦考林、易迅等大型B2C平台</p>
                            </td>
                        </tr>
                    </table>
                    </div>
                	<h4><strong>企业证件</strong>文件大小500K以内，  请使用jpg、gif、png格式</h4>
                    <table>
                   		<tr>
                        	<th><font color="red"> * </font>企业营业执照</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile2"><span id="myFile2tip" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>组织机构代码证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile3"><span id="myFile3tip" class="hui"></span>
                            </td>
                        </tr>
                        <tr>
                        	<th><font color="red"> * </font>税务登记证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile4"><span id="myFile4tip" class="hui"></span>
                            </td>
                        </tr>
                    </table>
                   
                    
                    <ul class="button-main">
                    	<li>
                    		<p id="submitError" style="color:red"></p>
                    		<input id="shopCSubmit" type="submit" value="提交" class="button"/>
                    	</li>          	
                    </ul>
                </div>
<#else>
            	<div class="steper"><h2 class="steper02"></h2></div>
                <div class="info">
                <div class="shop_pointer_style">
				     	<a class=" fr" href="http://service.pinju.com/cms/html/2011/teach_0825/53.html" target="_blank">了解更多开店详情</a>
				     	<p class="red bd">以下资料均为必填项目,请详细填写</p>
				     </div>
                	<h4><strong>店铺信息</strong></h4>
                	
                	<#if errorMessage?? && errorMessage!="">
				     <div class="shop_pointer_style">
				     	<p class="red">${errorMessage}</p>
				     </div>
				     </#if>
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>店铺名称</th>
                        	<td>
                        		<input  id="shopName" maxlength="20"  name="shopCustomerInfoDO.name" type="text" class="text" onblur="checkRename('#shopName','#shopNametip')"/><span id="shopNametip" class="tip"></span></li>
                        		<p><a href="http://service.pinju.com/cms/html/2011/bis_mng_0823/47.html" target="_blank">店铺命名须知</a></p>
                        	</td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>店铺类目</th>
                        	<td>
                            	<select id="shopCategory" name="shopCustomerInfoDO.shopCategory" onblur="spCheck('shopCategory','#shopCategorytip')">
							       <option value="" selected>选择店铺类目</option>
							       <#if shopCategoryList??>
							       <#list shopCategoryList.keySet() as shopCategory>
										 <option value="${shopCategory}" >${shopCategoryList.get(shopCategory)}</option>
							        </#list>
							        </#if>
							      </select>
							     <span id="shopCategorytip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>卖家类型</th>
                        	<td>
                            	<select id="shopNature" name="shopCustomerInfoDO.shopNature">
							       <#list shopNatureListC as shopNatureC>
										<option value="${shopNatureC_index}" >${shopNatureC}</option>
							      	</#list>
							      </select>
                                <span id="shopNaturetip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店铺介绍</th>
                        	<td>
                            <textarea id="descriptionC" name="shopCustomerInfoDO.description" onblur="checkOnblur('#descriptionC','#descriptionCtip');"></textarea>
                            <span id="descriptionCtip" class="tip"></span>
                            <p>请控制在500字以内</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店标</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile1">
                                <span id="myFile1tip" class="hui"></span>
                                <p>图片尺寸100px×100px， 文件大小80K以内，  请使用jpg、gif、png格式</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>主要货源</th>
                        	<td>
                            	<#list goodsSourceList as goodsSource>
									<#if goodsSource_index == 7>
										<label>
											<input id="goodsSource" name="shopCustomerInfoDO.goodsSource" type="radio" value="${goodsSource_index}" checked="checked" />${goodsSource}
										</label>
									<#else>
										<label>
											<input id="goodsSource" name="shopCustomerInfoDO.goodsSource" type="radio" value="${goodsSource_index}" />${goodsSource}
										</label>
									</#if>
								</#list>
								<span id="goodsSourcetip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>企业所在地</th>
                        	<td>
                            	<span id="shopCityC"></span><span id="shopCustomerInfoDOprovincetip" class="tip"></span>
                            </td>
                        </tr>
                    </table>
                     
                     <h4><strong>商户信息</strong></h4>
                    <table>
                       <tr>
                        	<th><font color="red"> * </font>是否有其他网店</th>
                        	<td>
                        	<#list isOpenOuterShopList as isOpenOuterShop>
                                 <#if isOpenOuterShop_index==0>
									<label>
										<input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}"  onclick="spCheck('isHaveOuterShop','.shopCustInfo')" checked="checked" />${isOpenOuterShop}
									</label>
								 <#else>
								      <label>
	                                     <input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}" onclick="spCheck('isHaveOuterShop','.shopCustInfo')"/>${isOpenOuterShop}
							          </label>
								</#if>
							  </#list>
                            </td>
                        </tr>
                    </table>
                    <div class="shopCustInfo">
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>网上店铺地址</th>
                        	<td><input id="shopAddressUrl" name="shopCustomerInfoDO.outerShopAddressUrl" type="text" class="text" onblur="spCheck('shopAddressUrl','#shopAddressUrltip')"><span id="shopAddressUrltip" class="tip"></span>
                            <p>淘宝、拍拍等网站的店铺地址。例如：http://shop.xxx.com/123456或者http://123456.xxx.com</p>
                            </td>
                        </tr>
                    	<tr>
                        	<th>店铺等级</th>
                        	<td>
                            <#list shopLevelList as shopLevel>
								<label>
								  <input id="shopLevel" name="shopCustomerInfoDO.outerShopLevel" type="radio" value="${shopLevel_index}" onblur="spCheck('shopLevel','#shopLeveltip')"/>${shopLevel}
								</label>
							</#list>
							<span id="shopLeveltip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>年销售规模</th>
                        	<td>
                              <#list saleScopeList as saleScope>
									<label>
										<input id="saleScope" name="shopCustomerInfoDO.outerShopSaleScope" type="radio" value="${saleScope_index}" onblur="spCheck('saleScope','#saleScopetip')"/>${saleScope}
									</label>
							</#list>
							<span id="saleScopetip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>是否入驻过B2C</th>
                        	<td>
                              <#list isEnterB2cList as isEnterB2c>
									<label>
										<input id="isEnterB2c" name="shopCustomerInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" onblur="spCheck('isEnterB2c','#isEnterB2ctip')" />${isEnterB2c}
									</label>
							</#list>
							<span id="isEnterB2ctip" class="tip"></span>
							<p>例如：京东、当当、麦考林、易迅等大型B2C平台</p>
                            </td>
                        </tr>
                    </table> 
                    </div>
                	<h4><strong>企业证件</strong>文件大小500K以内，  请使用jpg、gif、png格式</h4>
                    <table>
                   		<tr>
                        	<th><font color="red"> * </font>企业营业执照</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile2"><span id="myFile2tip" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>组织机构代码证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile3"><span id="myFile3tip" class="hui"></span>
                            </td>
                        </tr>
                        <tr>
                        	<th><font color="red"> * </font>税务登记证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFile4"><span id="myFile4tip" class="hui"></span>
                            </td>
                        </tr>
                    </table>
                    
                    <ul class="button-main">
                    	<li>
                    		<p id="submitError" style="color:red"></p>
                    		<input id="shopCSubmit" type="submit" value="提交" class="button"/>
                    	</li>         	
                    </ul>
                </div>
</#if>
<input type="hidden" name="_token_" value="${_token_}" />
</form>

<script>

function addShopInfoToCookieStepC(form, callback){
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
addShopInfoToCookieStepC($('#shopInfoForm'), function(){
	var name = $("#shopName").val();
	var shopCategory = $("#shopCategory").val();
	var shopNature = $("#shopNature").val();
	var province = $("#shopCustomerInfoDOprovince").val();
	var city = $("#shopCustomerInfoDOcity").val();
	var shopAddressUrl = $("#shopAddressUrl").val();
	
	name = "name="+encodeURIComponent(name);
	shopCategory = "shopCategory="+shopCategory;
	shopNature = "shopNature="+shopNature;
	province = "province="+encodeURIComponent(province);
	city = "city="+encodeURIComponent(city);
	shopAddressUrl = "shopAddressUrl="+encodeURIComponent(shopAddressUrl);
	var strCookie = name + "," + shopCategory + "," +shopNature + "," + province + "," + city + "," + shopAddressUrl;
	PinjuCookie.writeCookie('PJC', strCookie, 'www.pinju.com', '/shop', 3);
});
	
</script>



