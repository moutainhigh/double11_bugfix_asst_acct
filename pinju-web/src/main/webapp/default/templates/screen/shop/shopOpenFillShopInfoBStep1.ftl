<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />


<!--<link href="http://static.pinju.com/css/shop/open.css" rel="stylesheet" type="text/css" media="screen" />-->
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
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
                    	<li class="count"><span href="javascript:void(0)">店铺信息</span></li>
                    	<li ><span href="javascript:void(0)">企业信息</span></li>
                    	<li><span href="javascript:void(0)">品牌相关</span></li>
                    	<li><span href="javascript:void(0)">联系方式</span></li>
                    </ul>
     
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
<iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe>
 <form id="shopInfoForm" action="${base}/shop/saveBusinessShopInfoAction.htm?fillStep=1" method="post" onsubmit="return checkBusinessShopStep1();" enctype ="multipart/form-data">    
   <input type="hidden" name="pj0" value="${pj0}" />
   
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
   <#if shopBusinessInfoDO?exists>
   <input type="hidden" id="myProvB" name="myProvC" value="${shopBusinessInfoDO.province}"/>
	<input type="hidden" id="myCityB" name="myCityC" value="${shopBusinessInfoDO.city}"/>
     <!--shopInfo1 begin-->
     
     <table>
                    	<tr>
                        	<th><font color="red"> * </font>店铺名称</th>
                        	<td>
                        	<input type="text" id="shopNameB" name="shopBusinessInfoDO.name" value="${shopBusinessInfoDO.name?html}" maxlength="20" onblur="checkRename('#shopNameB','#shopNameBtip')" class="text"><span  id="shopNameBtip" class="tip"></span>
                        	<p><a href="http://service.pinju.com/cms/html/2011/bis_mng_0823/47.html" target="_blank">店铺命名须知</a></p>
                        	</td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>店铺类目</th>
                        	<td>
                        		<select id="shopCategory" name="shopBusinessInfoDO.shopCategory" onblur="spCheck('shopCategory','#shopCategorytip')">
                            	<#if shopCategoryList??>
                            	<#list shopCategoryList.keySet() as shopCategory>
					      			<#if shopBusinessInfoDO.shopCategory == shopCategory>
										<option value="${shopCategory}" selected>${shopCategoryList.get(shopCategory)}</option>
									<#else>
										<option value="${shopCategory}" >${shopCategoryList.get(shopCategory)}</option>
									</#if>
									
					      		</#list>
					      		</#if>
					      		</select><span  id="shopCategorytip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>卖家类型</th>
                        	<td>
                            	<select id="shopNature" name="shopBusinessInfoDO.shopNature">
							       <#list shopNatureListB as shopNatureB>
							      			<#if shopBusinessInfoDO.shopNature == shopNatureB_index>
												<option value="${shopNatureB_index}" selected>${shopNatureB}</option>
											<#else>
												<option value="${shopNatureB_index}" >${shopNatureB}</option>
											</#if>
							      		</#list>
							      </select><span  id="shopNature" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店铺介绍</th>
                        	<td>
                            	<textarea  id="descriptionB" name="shopBusinessInfoDO.description" maxlength="500" onblur="checkOnblur('#descriptionB','#descriptionBtip');">${shopBusinessInfoDO.description?html}</textarea><span id="descriptionBtip" class="tip"></span>
                            	<p>请控制在500字以内</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店标</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileInfoB"><span id="myFileInfoBtip" class="hui"></span>
                            	<p>图片尺寸100px×100px， 文件大小80K以内，  请使用jpg、gif、png格式</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>主要货源</th>
                        	<td>
                        		<#list goodsSourceList as goodsSource>
									<#if shopBusinessInfoDO.goodsSource == goodsSource_index>
										<label>
											<input id="goodsSource" name="shopBusinessInfoDO.goodsSource" type="radio" value="${goodsSource_index}" checked="checked" />${goodsSource}
										</label>
									<#else>
										<label>
											<input id="goodsSource" name="shopBusinessInfoDO.goodsSource" <#if goodsSource_index == 7>checked="checked"</#if> type="radio" value="${goodsSource_index}" />${goodsSource}
										</label>
									</#if>
								</#list><span id="goodsSourcetip" class="tip"></span>
                            </td>
                        </tr>
                   		<!--<tr>
                        	<th>企业所在地</th>
                        	<td>
                            	<span id="shopCityB"></span><span id="shopBusinessInfoDOprovincetip" class="tip"></span>
                            </td>
                        </tr>-->
                   		<!--<tr>
                        	<th>选择店铺性质</th>
                        	<td>
                            	<select id="sellerNature" name="shopBusinessInfoDO.sellerNature">
							       <#list sellerNatureListB as sellerNatureB>
							      			<#if shopBusinessInfoDO.sellerNature == sellerNatureB_index>
												<option value="${sellerNatureB_index}" selected>${sellerNatureB}</option>
											<#else>
												<option value="${sellerNatureB_index}" >${sellerNatureB}</option>
											</#if>
							      		</#list>
							      </select>
							      <span id="sellerNaturetip" class="tip"></span>
							      <p><a target="_blank" href="http://service.pinju.com/cms/html/2011/recruit_0809/9.html">店铺性质说明</a></p>
                              </td>
                        </tr>-->
                    </table> 
                    
   <!--shopInfo1 end-->
    <#else>
    			<#if errorMessage?? && errorMessage!="">
		<div class="shop_pointer_style">
			<p class="red">${errorMessage}</p>
		</div>
	</#if>
			    <!--shopInfo1 begin-->
			     <table>
                    	<tr>
                        	<th><font color="red"> * </font>店铺名称</th>
                        	<td>
                        	<input  type="text" id="shopNameB" name="shopBusinessInfoDO.name" class="text" maxlength="20" onblur="checkRename('#shopNameB','#shopNameBtip')"><span  id="shopNameBtip" class="tip"></span>
                        	<p><a href="http://service.pinju.com/cms/html/2011/bis_mng_0823/47.html" target="_blank">店铺命名须知</a></p>
                        	</td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>店铺类目</th>
                        	<td>
                            	<select id="shopCategory" name="shopBusinessInfoDO.shopCategory" onblur="spCheck('shopCategory','#shopCategorytip')">
							        <option value="" selected>选择店铺类目</option>
							        <#if shopCategoryList??>
							        <#list shopCategoryList.keySet() as shopCategory>
									    <option value="${shopCategory}" >${shopCategoryList.get(shopCategory)}</option>
				      		       </#list>
				      		       </#if>
							      </select>
							      <span  id="shopCategorytip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>卖家类型</th>
                        	<td>
                            	<select id="shopNature" name="shopBusinessInfoDO.shopNature">
							       <#list shopNatureListB as shopNatureB>
										<option value="${shopNatureB_index}" >${shopNatureB}</option>
						      		</#list>
							      </select>
							      <span  id="shopNaturetip" class="tip"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店铺介绍</th>
                        	<td>
	                            <textarea id="descriptionB" name="shopBusinessInfoDO.description" maxlength="500" onblur="checkOnblur('#descriptionB','#descriptionBtip');"></textarea>
	                            <span  id="descriptionBtip" class="tip"></span>
	                            <p>请控制在500字以内</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>店标</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileInfoB"><span id="myFileInfoBtip" class="hui"></span>
                            	<p>图片尺寸100px×100px， 文件大小80K以内，  请使用jpg、gif、png格式</p>
                            </td>
                        </tr>
                   		<tr>
                        	<th>主要货源</th>
                        	<td>
                        		<#list goodsSourceList as goodsSource>
                        			<#if goodsSource_index = 7> 
	                        			<label>
											<input name="shopBusinessInfoDO.goodsSource" type="radio" checked="checked" value="${goodsSource_index}" />${goodsSource}
										</label>
									<#else>
										<label>
											<input name="shopBusinessInfoDO.goodsSource" type="radio" value="${goodsSource_index}" />${goodsSource}
										</label>
									</#if>
								</#list>
								<span id="goodsSourcetip" class="tip"></span>
                            </td>
                        </tr>
                   		<!--<tr>
                        	<th>企业所在地</th>
                        	<td>
                            	<span id="shopCityB"></span><span id="shopBusinessInfoDOprovincetip" class="tip"></span>
                            </td>
                        </tr>-->
                   		<!--<tr>
                        	<th>选择店铺性质</th>
                        	<td>
                            	<select  id="sellerNature" name="shopBusinessInfoDO.sellerNature">
							       <#list sellerNatureListB as sellerNatureB>
											<option value="${sellerNatureB_index}" >${sellerNatureB}</option>
						      		</#list>
							      </select>
							      <span id="sellerNaturetip" class="tip"></span>
							      <p><a target="_blank" href="http://service.pinju.com/cms/html/2011/recruit_0809/9.html">店铺性质说明</a></p>
                              </td>
                        </tr>-->
                    </table> 
                    
			   <!--shopInfo1 end-->
			   
    </#if>
     <ul class="button-main">
                    	<li>
                    		<p id="submitError" style="color:red"></p>
                    		<input id="step1Submit" type="submit" value="下一步" class="button">
                    	</li> 
                    </ul>
                </div>
                <div class="cf"></div>
      <input type="hidden" name="_token_" value="${_token_}" />
      </form>
<script>

/*$(document).ready(
	function() {
		addShopInfoToCookie1();
});
var time = 0;
function addShopInfoToCookie1(){
	time = time + 1;
	if(time % 3 == 0){
		addShopInfoToCookieStep1();
	}
	setTimeout('addShopInfoToCookie1()',1000);
}	*/

function addShopInfoToCookieStep1(form, callback){
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

addShopInfoToCookieStep1($('#shopInfoForm'), function(){
	var name = $("#shopNameB").val();
	var shopCategory = $("#shopCategory").val();
	var shopNature = $("#shopNature").val();
	name = "name="+encodeURIComponent(name);
	shopCategory = "shopCategory="+shopCategory;
	shopNature = "shopNature="+shopNature;
	var strCookie = name + "," + shopCategory + "," +shopNature;
	PinjuCookie.writeCookie('PJ1', strCookie, 'www.pinju.com', '/shop', 3);
});
	
</script>
