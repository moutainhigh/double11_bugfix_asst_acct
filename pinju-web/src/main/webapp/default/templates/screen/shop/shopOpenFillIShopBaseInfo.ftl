<title>店铺--开店</title>
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js?t=20111207.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shop.js"></SCRIPT>-->
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<script src="http://static.pinju.com/js/shop/preshop.js?t=20111206.js"></script>
<#setting classic_compatible=true>

<iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe>
<form id="shopInfoForm" action="${base}/shop/saveIShopBaseInfoAction.htm" onsubmit="return checkShopBaseInfo('iShopSubmit',3)" method="post" enctype ="multipart/form-data">
<input type="hidden" name="pj0" value="${pj0}" />
<input type="hidden" value="red_shopOpen" id="my-page" />
<input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
<input type="hidden" value="red_shopOpen" id="my-page" />
<#if shopIshopInfoDO?exists>
<input type="hidden" id="myProvC" name="myProvC" value="${shopIshopInfoDO.province}"/>
<input type="hidden" id="myCityC" name="myCityC" value="${shopIshopInfoDO.city}"/>
        
<!-- START <普通店资料提交> -->
		<div class="openshop">
			<div class="ptitle">
				<h1>填写基本开店信息 <em>( <span class="required">*</span> 为必填项 )</em></h1>
				<span class="eye"><a href="/shop/choiceFillIndexAction.htm">返回</a></span>
			</div>
			<p class="exinfo">以下信息会影响审核结果，请务必仔细填写。<a href="http://service.pinju.com/cms/html/help/selleropen/" target="_blank">开店帮助</a></p>
			<div class="stepwrap accordion">
			
			<#if errorMessage?? && errorMessage!="">
				<div class="shop_pointer_style">
					<p class="red">${errorMessage}</p>
				</div>
			</#if>
			
				<ul class="tabs cf">
					<li class="current"><a title="店铺信息" href="#tabcon-a">店铺信息</a></li>
					<li><a title="企业信息" href="#tabcon-b" class="">联系方式</a></li>

				</ul>
				
				
				<div class="title topen">
					<h2>填写店铺信息</h2>
					<span class="showhide"></span>
				</div>
				<div class="container info" style="display:block;">
					<fieldset>

						<table>
							<tr>
								<th><span class="required">*</span> 店铺名称</th>
								<td>
									<input type="text" maxlength="20" id="shopName" name="shopIshopInfoDO.name" value="${shopIshopInfoDO.name?html}" onblur="checkRename('#shopName','#shopNametip')" class="text">
									<span id="shopNametip" class="tip"></span>
									<p>0-20位字符(由中文、英文、数字及“_”组成) 
										<a  href="http://service.pinju.com/cms/html/2011/bis_mng_0823/47.html" target="_blank">店铺命名须知</a>
									</p>
								</td>
							</tr>
							<tr>
								<th><span class="required">*</span> 店铺类目</th>
								<td>
									<select id="shopCategory" name="shopIshopInfoDO.shopCategory" onblur="spCheck('shopCategory','#shopCategorytip')">
							         <option value="">选择店铺类目</option>
							         	<#if shopCategoryList??>
							      		<#list shopCategoryList.keySet() as shopCategory>${shopCategory},
							      			<#if shopCategory?? && shopCategory != "">
								      			<#if shopIshopInfoDO.shopCategory ==shopCategory>
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
								<th><span class="required">*</span> 卖家类型</th>
								<td>
									<select id="shopNature" name="shopIshopInfoDO.shopNature">
							       <#list shopNatureListC as shopNatureC>
							      			<#if shopIshopInfoDO.shopNature == shopNatureC_index>
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
								<th>店铺简介</th>
								<td>
								<textarea id="descriptionC"  name="shopIshopInfoDO.description" onblur="checkOnblur('#descriptionC','#descriptionCtip');">${shopIshopInfoDO.description?html}</textarea>
								<p>(500字以内)</p>

								</td>
							</tr>
					
							<tr>
								<th>主要货源</th>
								<td>
									<#list goodsSourceList as goodsSource>
									<#if shopIshopInfoDO.goodsSource == goodsSource_index>
										<label>
											<input id="goodsSource" name="shopIshopInfoDO.goodsSource" type="radio" value="${goodsSource_index}" checked="checked" />${goodsSource}
										</label>
									<#else>
										<label>
											<input id="goodsSource" <#if goodsSource_index == 7>checked="checked"</#if> name="shopIshopInfoDO.goodsSource" type="radio" value="${goodsSource_index}" />${goodsSource}
										</label>
									</#if>
								</#list>
								<span id="goodsSourcetip" class="tip"></span>
								</td>
							</tr>
							<tr>
								<th>&nbsp;</th>
								<td>

									<a class="btn-tonext"><span>填写下一项</span></a>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				
				<div class="title">

					<h2>填写联系方式</h2>
					<span class="showhide"></span>
				</div>
				<div class="container info">
					<fieldset>
						<table>
							<tr>
								<th><span class="required">*</span> 姓名:</th>
								<td>
									<input type="text" maxlength="20" id="shopManagerName" 
									name="shopIshopInfoDO.shopManagerName" value="${shopIshopInfoDO.shopManagerName?html}" 
									onblur="checkOnblur('#shopManagerName','#shopManagerNametip');spCheck('shopManagerName','#shopManagerNametip');" 
									class="text">
									<p><span id="shopManagerNametip" class="tip"></span></p>
								</td>
							</tr>
							<tr>
								<th><span class="required">*</span> 电话:</th>
								<td>
									<input type="text" id="shopManagerTelephone" name="shopIshopInfoDO.shopManagerTelephone" 
									value="${shopIshopInfoDO.shopManagerTelephone?html}" maxlength="20" 
									onblur="spCheck('shopManagerTelephone','#shopManagerTelephonetip')" class="text">
									<span id="shopManagerTelephonetip"  class="tip"></span>
									<p>如：021-12345678</p>
								</td>
							</tr>
							<tr>
								<th><span class="required">*</span> 手机:</th>
								<td>
									<input type="text" id="shopManagerMobile" name="shopIshopInfoDO.shopManagerMobile" 
									value="${shopIshopInfoDO.shopManagerMobile?html}"  maxlength="20" 
									onblur="spCheck('shopManagerMobile','#shopManagerMobiletip')" class="text">
									<span id="shopManagerMobiletip"  class="tip"></span>
								</td>
							</tr>
							<tr>
								<th><span class="required">*</span> 邮箱:</th>
								<td>
									<input type="text" id="shopManagerEmail" name="shopIshopInfoDO.shopManagerEmail" 
									value="${shopIshopInfoDO.shopManagerEmail?html}" 
									onblur="spCheck('shopManagerEmail','#shopManagerEmailtip')" class="text">
									<span id="shopManagerEmailtip"  class="tip"></span>
								</td>
							</tr>
							<tr>
								<th><span class="required">*</span> 联系地址:</th>
								<td>
									<input type="text" id="contactAddress" name="shopIshopInfoDO.contactAddress" 
									value="${shopIshopInfoDO.contactAddress}" 
									onblur="checkOnblur('#contactAddress','#contactAddresstip')" class="text">
									<span id="contactAddresstip" class="tip"></span>
								</td>

							</tr>
							<tr>
								<th>传真:</th>
								<td>
									<input type="text" id="shopManagerFax" name="shopIshopInfoDO.shopManagerFax" 
									value="${shopIshopInfoDO.shopManagerFax?html}" maxlength="20"  class="text">
									<span id="shopManagerFaxtip"  class="tip"></span>
								</td>
							</tr>
							<tr>

								<th>QQ:</th>
								<td>
									<input type="text" id="qq" name="shopIshopInfoDO.qq" value="${shopIshopInfoDO.qq?html}" 
									maxlength="20"  class="text">
									<span id="qqtip"  class="tip"></span>
								</td>
							</tr>
							<tr>
								<th>MSN:</th>
								<td>
									<input type="text" id="msn" name="shopIshopInfoDO.msn" 
									value="${shopIshopInfoDO.msn?html}" maxlength="50"  class="text">
									<span id="msntip"  class="tip"></span>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			
				<div class="btnrow btn-imgtext">
					<p id="submitError" style="color:red"></p>

					<button title="信息完整，可以提交！" id="iShopSubmit" type="submit" class="btn-ossactive">
						<span>我已填完以上信息,确认保存</span>
					</button>
				</div>
				<input type="hidden" name="_token_" value="${_token_}" />
			</form>
			</div>

		</div>

</#if>	

<script>

$(function() {
	$('.stepwrap .tabs li').click(function() {
		$(this).toggleClass("current");
	});
	var accordionOsStep = function(){
		$('.stepwrap .title').bind('click',function() {
			$(this).toggleClass("topen");
			$(this).next().toggle();
			return false;
		});
	};
	accordionOsStep();
	
	$('#elseshopNo:checked').parents('fieldset').find("#othershop").hide();
	$('#elseshopYes').bind('click',function(){
		$("#othershop").show();
	});
	$('#elseshopNo').bind('click',function(){
		$("#othershop").hide();
	});
});


function addShopInfoToCookieStepI(form, callback){
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
addShopInfoToCookieStepI($('#shopInfoForm'), function(){
	//基本信息
	var name = $("#shopName").val();
	var shopCategory = $("#shopCategory").val();
	var shopNature = $("#shopNature").val();
	
	//联系人信息
	var shopManagerName = $("#shopManagerName").val();
	var shopManagerTelephone = $("#shopManagerTelephone").val();
	var shopManagerMobile = $("#shopManagerMobile").val();
	var shopManagerEmail = $("#shopManagerEmail").val();
	var shopManagerFax = $("#shopManagerFax").val();
	var contactAddress = $("#contactAddress").val();
	
	
	//基本信息
	name = "name="+encodeURIComponent(name);
	shopCategory = "shopCategory="+shopCategory;
	shopNature = "shopNature="+shopNature;
	
	
	//联系人信息
	shopManagerName = "shopManagerName="+encodeURIComponent(shopManagerName);
	shopManagerTelephone = "shopManagerTelephone="+shopManagerTelephone;
	shopManagerMobile = "shopManagerMobile="+shopManagerMobile;
	shopManagerEmail = "shopManagerEmail="+shopManagerEmail;
	shopManagerFax = "shopManagerFax="+shopManagerFax;
	contactAddress = "contactAddress="+encodeURIComponent(contactAddress);
	
	var strCookie = name + "," + shopCategory + "," +shopNature + "," 
					+ shopManagerName + "," + shopManagerTelephone + "," +shopManagerMobile + ","
					+ shopManagerEmail + "," + shopManagerFax + "," + contactAddress;
	PinjuCookie.writeCookie('PJ1', strCookie, 'www.pinju.com', '/shop', 3);
});

function showAccordion2(index) {
	var titleList = $('.accordion .title');
	var titleDiv = titleList.eq(index);
	titleDiv.addClass("topen");
	titleDiv.next().show();
	titleList.each(function() {
		if (this != titleDiv[0]) {
			$(this).removeClass('topen');
			$(this).next().hide();
		}
	});
} 
</script>
