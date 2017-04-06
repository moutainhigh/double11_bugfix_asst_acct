<title>店铺基本设置</title>
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<script src="http://static.pinju.com/js/shop/preshop.js?t=20111205.js"></script>
<script src="http://static.pinju.com/image-preview/jquery.image-preview.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shopBaseInfo.js?t=20111206.js"></SCRIPT>

<#setting classic_compatible=true>
<form action="${base}/shop/saveShopInfo.htm" method="post" onsubmit="return checkShopFill()" enctype ="multipart/form-data">
<input type="hidden" value="red_shopInfo" id="my-page" />
<input type="hidden" name="${tokenName}" value="${token}">

		<div class="openshop">

			<div class="ptitle">
				<h1>店铺基本设置 <em>( <span class="required">*</span> 为必填项 )</em></h1>
				<span class="eye"><a href="#">返回</a></span>
			</div>
			<p class="exinfo">您可以在以下设置中编辑您的店铺信息。</p>
			<#if errorMessage?? && errorMessage!="">
				<div class="shop_pointer_style">
					<p class="red">${errorMessage}</p>
				</div>
			</#if>
					
			<#if successMessage?exists && successMessage!="">
				<div style="padding:10px 0; margin:10px 0;text-align:center; border:1px solid green; color:green; font-size:18px; font-weight:bold; background:#FFFFBB; display:none;" id="smessage">${successMessage}</div>
				<script type="text/javascript">
					$('#smessage').fadeIn(2000);
				</script>
			</#if>
		<#if shopInfoDO?exists>
		<input type="hidden" value="${shopInfoDO.sellerType}" name="shopInfoDO.sellerType">
            <div class="stepwrap">       	
            <div class="info shopinfo">
            <div class="updatelogo">
              <input type="hidden" value="${shopInfoDO.shopLogo}" name="logoStr">
              <div class="logoph">
	               <#if shopInfoDO.shopLogo??>
	            		<li>
	            		  <img id="shopLogoImg" name="shopLogoImg" src="${imageServer}${shopInfoDO.shopLogo}_80x80.jpg"/>
	            		</li>
	            	<#else>
	            		<li><img id="shopLogoImg" name="shopLogoImg" src="http://static.pinju.com/img/shop_default_logo.png"/></li>
	            	</#if>
            	</div>
			 <a herf="##">上传图片<input type="file" id="shopLogoFile" name="shopLogoFile" value="" /></a>
			</div>
             <fieldset>
						<table>
							<tr>
                            	<th>店铺名</th>
                                <td>
	                                <input  disabled="true" id="shopName"  type="text" value="${shopInfoDO.name?html}" class="text"  maxlength="20" >
	                                <input  name="shopInfoDO.name" type="hidden" value="${shopInfoDO.name?html}">
	                                <span id="shopNametip" class="tip"></span>
                                </td>
                            </tr>
                        	<tr>
                            	<th>店铺类目</th>
                                <td>
                                <input type="hidden" id="shopCategory" name="shopInfoDO.shopCategory" value="${shopInfoDO.shopCategory}" />
                                	<select name="shopInfoDO.shopCategory" disabled="true" >
                                	<#if shopCategoryList??>
                            	<#list shopCategoryList.keySet() as shopCategory>
					      			<#if shopInfoDO.shopCategory == shopCategory>
										<option value="${shopCategory}" selected>${shopCategoryList.get(shopCategory)}</option>
									<#else>
										<option value="${shopCategory}" >${shopCategoryList.get(shopCategory)}</option>
									</#if>
									
					      		</#list>
					      		</#if>
					      		</select>
                                </td>
                            </tr>
                        	<tr>
                            	<th>卖家类型</th>
                                <td>
                                	<select name="shopInfoDO.shopNature" disabled="true">
							      	<#if sellTypeList?exists>
							      		<#list sellTypeList as sellType>
							      			<#if shopInfoDO.shopNature == sellType_index>
												<option value="${sellType_index}" selected>${sellType}</option>
											<#else>
												<option value="${sellType_index}">${sellType}</option>
											</#if>
							      		</#list>
							      		
							      	</#if>
							      </select>
                                </td>
                            </tr>
                             <tr>
                            	<th><font color="red"> * </font>店铺简介</th>
                                <td><input id="title" name="shopInfoDO.title" type="text" value="${shopInfoDO.title?html}" class="text" maxlength="300" onblur="checkShopInfoOnBlur('title')"><span id="titletip" class="tip"></span></td>
                            </tr>
                        	<tr>
                            	<th><font color="red"> * </font>主要货源</th>
                                <td class="mcheckbox">
                                    <#if goodsSourceList?exists>
										<#list goodsSourceList as goodsSource>
											<#if goodsSource_index == 0 || goodsSource_index == 4>
												<div>
											</#if>
											<#if goodsSource_index == 3 || goodsSource_index == 7>
												</div>
											</#if>
											<#if shopInfoDO.goodsSource == goodsSource_index>
												<label><input name="shopInfoDO.goodsSource" type="radio" value="${goodsSource_index}" checked="checked" />${goodsSource}</label>
											<#else>
												<label><input name="shopInfoDO.goodsSource" type="radio" value="${goodsSource_index}" />${goodsSource}</label>
											</#if>
										</#list>
									</#if>
                                </td>
                            </tr>
                           
                            <tr>
								<th><font color="red"> * </font>店铺介绍</th>
								<td>
								<textarea id="description" name="shopInfoDO.description" maxlength="500" onblur="checkShopInfoOnBlur('description')" >${shopInfoDO.description?html}</textarea>
								<span id="descriptiontip" class="tip"></span><p>(500字以内)</p>
								</td>
							</tr>
                    </table>
					</fieldset>
                    <fieldset>
                    <#if shopInfoDO.sellerType==0>
                    	<legend>商户信息</legend>
                    	<table>
							<tr>
								<th><span class="required">*</span> 是否有其他网店:</th>
								<td>
									<#list isOpenOuterShopList as isOpenOuterShop>
		                            	<#if isOpenOuterShop_index==0>
											<label  for="elseshopYes">
												<input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" 
												value="${isOpenOuterShop_index}"  
												onclick="spCheck('isHaveOuterShop','.shopCustInfo')" 
												 checked/>${isOpenOuterShop}
											</label>
										<#else>
										      <label for="elseshopNo">
			                                     <input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" 
			                                     value="${isOpenOuterShop_index}" 
			                                     onclick="spCheck('isHaveOuterShop','.shopCustInfo')" />${isOpenOuterShop}
									          </label>
										</#if>
									</#list>
								</td>
							</tr>
						</table>
						<div id="hasOtherShop">
                     	<table>
							<tbody><tr>
	                        	<th><font color="red"> * </font>网上店铺地址</th>
	                        	<td><input id="shopAddressUrl" name="shopInfoDO.outerShopAddressUrl" type="text" class="text" value="${shopInfoDO.outerShopAddressUrl?html}" onblur="spCheck('shopAddressUrl','#shopAddressUrltip')"/><span id="shopAddressUrltip" class="tip"></span>
	                            <p>淘宝、拍拍等网站的店铺地址。例如：http://shop.xxx.com/123456或者http://123456.xxx.com</p>
	                            </td>
	                        </tr>
	                    	<tr>
	                        	<th>店铺等级</th>
	                        	<td>
	                              <#list shopLevelList as shopLevel>
		                                 <#if shopInfoDO.outerShopLevel == shopLevel_index>
											<label>
												<input id="shopLevel" name="shopInfoDO.outerShopLevel" type="radio" 
												value="${shopLevel_index}"  checked="checked" />${shopLevel}
											</label>
										 <#else>
										      <label>
			                                     <input id="shopLevel" name="shopInfoDO.outerShopLevel" type="radio" 
			                                     value="${shopLevel_index}" />${shopLevel}
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
		                                 <#if shopInfoDO.outerShopSaleScope == saleScope_index>
											<label>
												<input id="saleScope" name="shopInfoDO.outerShopSaleScope" type="radio" 
												value="${saleScope_index}" checked="checked" />${saleScope}
											</label>
										 <#else>
										      <label>
			                                     <input id="saleScope" name="shopInfoDO.outerShopSaleScope" type="radio" 
			                                     value="${saleScope_index}" />${saleScope}
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
		                                 <#if shopInfoDO.isEnterB2c == isEnterB2c_index>
											<label>
												<input id="isEnterB2c" name="shopInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" checked="checked" />${isEnterB2c}
											</label>
										 <#else>
										      <label>
			                                     <input id="isEnterB2c" name="shopInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" />${isEnterB2c}
									          </label>
										</#if>
									  </#list>
								   <span id="isEnterB2ctip" class="tip"></span>
								   <p>例如：京东、当当、麦考林、易迅等大型B2C平台</p>
	                            </td>
	                        </tr>
	                    </tbody></table>
	                    </div>
               </fieldset>
               	<fieldset>
						<legend>店铺联系人信息</legend>
                      	<table>
							<tbody><tr>
                        	<th><font color="red"> * </font>姓名</th>
                        	<td><input type="text" maxlength="20" id="shopManagerName" name="shopCustomerInfoDO.shopManagerName" value="${shopCustomerInfoDO.shopManagerName?html}" onblur="checkOnblur('#shopManagerName','#shopManagerNametip');spCheck('shopManagerName','#shopManagerNametip');" class="text"><span id="shopManagerNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>电话</th>
                        	<td><input type="text" id="shopManagerTelephone" name="shopCustomerInfoDO.shopManagerTelephone" value="${shopCustomerInfoDO.shopManagerTelephone?html}" maxlength="20" onblur="spCheck('shopManagerTelephone','#shopManagerTelephonetip')" class="text"><span id="shopManagerTelephonetip"  class="tip"></span><p>固定电话号码,例如：021-12345678</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>手机</th>
                        	<td><input type="text" id="shopManagerMobile" name="shopCustomerInfoDO.shopManagerMobile" value="${shopCustomerInfoDO.shopManagerMobile?html}"  maxlength="20" onblur="spCheck('shopManagerMobile','#shopManagerMobiletip')" class="text"><span id="shopManagerMobiletip"  class="tip"></span><p>手机号码,例如：12345678901</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>邮箱</th>
                        	<td><input type="text" id="shopManagerEmail" name="shopCustomerInfoDO.shopManagerEmail" value="${shopCustomerInfoDO.shopManagerEmail?html}" onblur="spCheck('shopManagerEmail','#shopManagerEmailtip')" class="text"><span id="shopManagerEmailtip"  class="tip"></span></td>
                        </tr>
                        <tr>
                        	<th><font color="red"> * </font>联系地址</th>
                        	<td><input type="text" id="contactAddress" name="shopCustomerInfoDO.contactAddress" value="${shopCustomerInfoDO.contactAddress?html}" onblur="spCheck('contactAddress','#contactAddresstip')" class="text"><span id="contactAddresstip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>传真</th>
                        	<td><input type="text" id="shopManagerFax" name="shopCustomerInfoDO.shopManagerFax" value="${shopCustomerInfoDO.shopManagerFax?html}" maxlength="20"  class="text"><span id="shopManagerFaxtip"  class="tip"></span><p>传真号码,例如:021-12345678(选填)</p></td>
                        </tr>
                        <tr>
                        	<th>QQ</th>
                        	<td><input type="text" id="qq" name="shopCustomerInfoDO.qq" value="${shopCustomerInfoDO.qq?html}" maxlength="20"  class="text"><span id="qqtip"  class="tip"></span></td>
                        </tr>
                        <tr>
                        	<th>MSN</th>
                        	<td><input type="text" id="msn" name="shopCustomerInfoDO.msn" value="${shopCustomerInfoDO.msn?html}" maxlength="50"  class="text"><span id="msntip"  class="tip"></span></td>
                        </tr></tbody>
                     </table>
                  </fieldset>
	                <#elseif (shopInfoDO.sellerType!=3)>
	                <fieldset>
						<legend>商户信息</legend>
						<table>
							<tr>
								<th><span class="required">*</span> 是否有其他网店:</th>
								<td>
									<#list isOpenOuterShopList as isOpenOuterShop>
		                            	<#if isOpenOuterShop_index==0>
											<label  for="elseshopYes">
												<input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" 
												value="${isOpenOuterShop_index}"  
												onclick="spCheck('isHaveOuterShop','.shopCustInfo')" 
												 checked/>${isOpenOuterShop}
											</label>
										<#else>
										      <label for="elseshopNo">
			                                     <input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" 
			                                     value="${isOpenOuterShop_index}" 
			                                     onclick="spCheck('isHaveOuterShop','.shopCustInfo')" />${isOpenOuterShop}
									          </label>
										</#if>
									</#list>
								</td>
							</tr>
						</table>
						<div id="hasOtherShop">
	                 <table><tbody>
	                    	<tr>
	                        	<th><font color="red"> * </font>网上店铺地址</th>
	                        	<td><input id="shopAddressUrl" name="shopInfoDO.outerShopAddressUrl" type="text" class="text" value="${shopInfoDO.outerShopAddressUrl?html}" onblur="spCheck('shopAddressUrl','#shopAddressUrltip')"/><span id="shopAddressUrltip" class="tip"></span>
	                            <p>淘宝、拍拍等网站的店铺地址。例如：http://shop.xxx.com/123456或者http://123456.xxx.com</p>
	                            </td>
	                        </tr>
	                    	<tr>
	                        	<th>店铺等级</th>
	                        	<td>
	                        	<#list shopLevelList as shopLevel>
		                                 <#if shopInfoDO.outerShopLevel == shopLevel_index>
											<label>
												<input id="shopLevel" name="shopInfoDO.outerShopLevel" type="radio" 
												value="${shopLevel_index}"  checked="checked" />${shopLevel}
											</label>
										 <#else>
										      <label>
			                                     <input id="shopLevel" name="shopInfoDO.outerShopLevel" type="radio" 
			                                     value="${shopLevel_index}" />${shopLevel}
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
		                                 <#if shopInfoDO.outerShopSaleScope == saleScope_index>
											<label>
												<input id="saleScope" name="shopInfoDO.outerShopSaleScope" type="radio" 
												value="${saleScope_index}" checked="checked" />${saleScope}
											</label>
										 <#else>
										      <label>
			                                     <input id="saleScope" name="shopInfoDO.outerShopSaleScope" type="radio" 
			                                     value="${saleScope_index}" />${saleScope}
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
		                                 <#if shopInfoDO.isEnterB2c == isEnterB2c_index>
											<label>
												<input id="isEnterB2c" name="shopInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" checked="checked" />${isEnterB2c}
											</label>
										 <#else>
										      <label>
			                                     <input id="isEnterB2c" name="shopInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" />${isEnterB2c}
									          </label>
										</#if>
									  </#list>
								   <span id="isEnterB2ctip" class="tip"></span>
								   <p>例如：京东、当当、麦考林、易迅等大型B2C平台</p>
	                            </td>
	                        </tr></tbody>
	                    </table>
	                    </div>
	                 </fieldset>
	                 <fieldset>
						<legend>店铺联系人信息</legend>
                      <table><tbody>
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
                        	<th><font color="red"> * </font>联系地址</th>
                        	<td><input type="text" id="contactAddress" name="shopBusinessInfoDO.contactAddress" value="${shopBusinessInfoDO.contactAddress?html}" onblur="spCheck('contactAddress','#contactAddresstip')" class="text"><span id="contactAddresstip"  class="tip"></span></td>
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
                        </tr></tbody>
                     </table>
                  	</fieldset>
	                <#else>
	                 <fieldset>
						<legend>店铺联系人信息</legend>
	                <table><tbody>
                    	<tr>
                        	<th><font color="red"> * </font>姓名</th>
                        	<td><input type="text" maxlength="20" id="shopManagerName" name="shopIshopInfoDO.shopManagerName" value="${shopIshopInfoDO.shopManagerName?html}" onblur="checkOnblur('#shopManagerName','#shopManagerNametip');spCheck('shopManagerName','#shopManagerNametip');" class="text"><span id="shopManagerNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>电话</th>
                        	<td><input type="text" id="shopManagerTelephone" name="shopIshopInfoDO.shopManagerTelephone" value="${shopIshopInfoDO.shopManagerTelephone?html}" maxlength="20" onblur="spCheck('shopManagerTelephone','#shopManagerTelephonetip')" class="text"><span id="shopManagerTelephonetip"  class="tip"></span><p>固定电话号码,例如：021-12345678</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>手机</th>
                        	<td><input type="text" id="shopManagerMobile" name="shopIshopInfoDO.shopManagerMobile" value="${shopIshopInfoDO.shopManagerMobile?html}"  maxlength="20" onblur="spCheck('shopManagerMobile','#shopManagerMobiletip')" class="text"><span id="shopManagerMobiletip"  class="tip"></span><p>手机号码,例如：12345678901</p></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>邮箱</th>
                        	<td><input type="text" id="shopManagerEmail" name="shopIshopInfoDO.shopManagerEmail" value="${shopIshopInfoDO.shopManagerEmail?html}" onblur="spCheck('shopManagerEmail','#shopManagerEmailtip')" class="text"><span id="shopManagerEmailtip"  class="tip"></span></td>
                        </tr>
                        <tr>
                        	<th><font color="red"> * </font>联系地址</th>
                        	<td><input type="text" id="contactAddress" name="shopIshopInfoDO.contactAddress" value="${shopIshopInfoDO.contactAddress?html}" onblur="spCheck('contactAddress','#contactAddresstip')" class="text"><span id="contactAddresstip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th>传真</th>
                        	<td><input type="text" id="shopManagerFax" name="shopIshopInfoDO.shopManagerFax" value="${shopIshopInfoDO.shopManagerFax?html}" maxlength="20"  class="text"><span id="shopManagerFaxtip"  class="tip"></span><p>传真号码,例如:021-12345678(选填)</p></td>
                        </tr>
                        <tr>
                        	<th>QQ</th>
                        	<td><input type="text" id="qq" name="shopIshopInfoDO.qq" value="${shopIshopInfoDO.qq?html}" maxlength="20"  class="text"><span id="qqtip"  class="tip"></span></td>
                        </tr>
                        <tr>
                        	<th>MSN</th>
                        	<td><input type="text" id="msn" name="shopIshopInfoDO.msn" value="${shopIshopInfoDO.msn?html}" maxlength="50"  class="text"><span id="msntip"  class="tip"></span></td>
                        </tr>
                     </table></tbody>
	                   </fieldset>
                    </#if>
                    <ul class="button-main">
                    	<li><input  type="submit" name="save" value="保存信息" class="button"></li>  
                    </ul>
        	</div>
	       </div>
   </#if>
 </div>

 <script type="text/javascript">
		//$(document).ready(function() {
		//	KE.show( {
		//				id : 'description',
		//				resizeMode : 1,
		//				allowPreviewEmoticons : false,
		//				allowUpload : false,
		//				items : [ 'fontname', 'fontsize', '|', 'textcolor',
		//						'bgcolor', 'bold', 'italic', 'underline',
		//						'removeformat', '|', 'justifyleft',
		//						'justifycenter', 'justifyright',
		//						'insertorderedlist', 'insertunorderedlist',
		//						'|', 'emoticons' ]
		//			});
		//});
		
	$(function() {
		$.imagePreview({
			file : $('#shopLogoFile'),
			img : $('#shopLogoImg'),
			maxWidth : 80,
			maxHeight : 80
		});
	});
		
</script>
 </form>