<#setting classic_compatible=true>
<title>店铺--开店</title>

<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<script src="http://static.pinju.com/js/shop/preshop.js?t=20111205.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js?t=20111207.js"></SCRIPT>

<!--<SCRIPT src="${base}/default/js/shop.js"></SCRIPT>-->
<iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe>
<form id="shopInfoForm" action="${base}/shop/saveBusinessBrandInfoAction.htm?brandSaveType=0" method="post"  enctype ="multipart/form-data">
<input type="hidden" name="pj0" value="${pj0}" />
<input type="hidden" value="red_shopOpen" id="my-page" />
<input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
<input type="hidden" value="red_shopOpen" id="my-page" />
<input type="hidden" id="myProvC" name="myProvC" value="${shopBusinessInfoDO.province!}"/>
<input type="hidden" id="myCityC" name="myCityC" value="${shopBusinessInfoDO.city!}"/>
<input type="hidden" id="brandSeq" name="brandSeq" value="${brandSeq!}"/>
<input type="hidden" id="currentBrand" name="currentBrand" value="${currentBrand!}"/>
<input type="hidden" id="brandSave" name="brandSave" value="${brandSaveType!}"/>
<div class="openshop">
			<div class="ptitle">
				<h1>添加品牌信息 <em>( <span class="required">*</span> 为必填项 )</em></h1>
				<span class="eye"><a href="/shop/choiceFillIndexAction.htm">返回</a></span>
			</div>
			<p class="exinfo">以下信息会影响审核结果，请务必仔细填写。<a href="http://service.pinju.com/cms/html/help/selleropen/" target="_blank">开店帮助</a></p>
			
			<div class="stepwrap">
			<#list 0..brandSeq as t>
				
					<#if t != currentBrand &&  -1 lt t>
							<div class="title" style="cursor:pointer"  onclick=showBrand('${t}')>
								<h2 id="brandNo">品牌信息${t+1}</h2>
								<span class="showhide"></span>
							</div>
					<#else>
					<#if -1 lt t>
						<#if errorMessage?? && errorMessage!="">
							<div class="shop_pointer_style">
								<p class="red">${errorMessage}</p>
							</div>
						</#if>
				<div class="title topen"  style="cursor:pointer">
					<h2 id="brandNo">品牌信息${t+1}</h2>
					<span class="showhide"></span>
				</div>
				
				<div class="container info" style="display:block;">
					<fieldset>
						<table>
							<tr>
								<th><span class="required">*</span> 品牌名称:</th>
								<td>

									<input onblur=checkBrandOnBlur('brandName') id="brandName" 
									name="shopBusinessInfoDO.brandName" type="text" class="text"
									 value="${shopBusinessInfoDO.brandName}">
									 <span id="brandNametip" class="brandNametip"></span>
								</td>
							</tr>
							<tr>
								<th>品牌英文名:</th>
								<td>
									<input onblur=checkBrandOnBlur('brandEnglishName') id="brandEnglishName" 
									name="shopBusinessInfoDO.brandEnglishName" type="text" class="text"
									 value="${shopBusinessInfoDO.brandEnglishName}">
									 <span id="brandEnglishNametip" class="brandEnglishNametip"></span>
								</td>

							</tr>
							<tr>
								<th><span class="required">*</span> 商标注册证号(申请号）:</th>
								<td>
									<input onblur=checkBrandOnBlur('trademarkNumber') 
									id="trademarkNumber" name="shopBusinessInfoDO.trademarkNumber" type="text" class="text" 
									value="${shopBusinessInfoDO.trademarkNumber}">
									<span id="trademarkNumbertip" class="trademarkNumbertip"></span>
								</td>
							</tr>

							<tr>
								<th><span class="required">*</span> 品牌Logo</th>
								<td>
									<input id="brandLogo" name="brandLogo" type="file">
									<span id='brandLogohui' class="brandLogohui"></span>
									<p>支持jpg\gif\png格式，80k以内，尺寸小于500×500px的图片</p>
								</td>
							</tr>

							<tr>
								<th><span class="required">*</span> 品牌故事</th>
								<td>
								<textarea onblur=checkBrandOnBlur('brandStory') id="brandStory" 
								name="shopBusinessInfoDO.brandStory" cols="" rows="">${shopBusinessInfoDO.brandStory}</textarea>
								<p><span id="brandStoryhui" class="brandStoryhui"></span></p>
								<p>(500字以内)</p>
								</td>

							</tr>
							<tr>
								<th><span class="required">*</span> 品牌授权书或品牌商标注册证书</th>
								<td>
									<input id="brandCertificate" name="brandCertificate" type="file">
									<span id='brandCertificatehui' class="brandCertificatehui"></span>
								</td>
							</tr>

							<tr>
								<th><span class="required">*</span> 商品质量检验证书</th>
								<td>
									<input id="qualityCertificate" name="qualityCertificate" type="file">
									<span id='qualityCertificatehui' class="qualityCertificatehui"></span>
									<p>支持jpg\gif\png格式，500k以内的图片</p>
								</td>
							</tr>

							<tr>
								<th>&nbsp;</th>
								<td class="btn-imgtext">
									<button id="saveBrandBtn" type="submit" class="btn-csave"><span>确认保存</span></button>
									<#if shopBusinessInfoDO.brandName>
										<a id="delBrandInfo" class="btn-delbrand" href="#" onclick=deleteBrandInfo('${t}')><span>删除品牌</span></a>
									</#if>
								</td>
							</tr>
						</table>
					</fieldset>

				</div>
				<input type="hidden" name="_token_" value="${_token_}" />
								
			</div>
			
			
					</#if>
				</#if>
			</#list>
			<div class="btn-imgtext exbtnrow">
				<!--<button id="addBrand" type="submit" class="btn-addbrand"><span>添加品牌</span></button>-->
				<a id="addBrand" class="btn-addbrand" href="javascript:void(0);" onclick="addAndSaveBrand()"><span>添加品牌</span></a>
				<span>目前最多可添加6个品牌</span>
			</div>

		</form>	
		</div>
		
<script>

$(document).ready(function(){
	var brandSave = $("#brandSave").val();
	if(brandSave){
		if(brandSave == 1 || brandSave == 0){
			saveSuccessAlert();
		}
	}
});

function saveSuccessAlert(){
	var dialog = art.dialog({
			title: '品聚网信息提示',
			content: '品牌信息已保存成功！',
			icon: 'succeed',
			button: [
						{
							name: '完成',
							callback: function () {
								dialog.close();
								editComplete();
								return false;
							},
							focus: true
						},
						{
							name: '继续添加',
							callback: function () {
								dialog.close();
								notSaveBrandAndNew(2);
								return false;
							}
						}
					],
			width:350,
			lock: true
		});
}
function editComplete(){
	window.location.href = "/shop/choiceFillIndexAction.htm";
}

function deleteBrandInfo(currentBrand){
	var brandSeq = $("#brandSeq").val();
	if(brandSeq == 0){
		alert("您必须保留一个品牌信息!");
		return false;
	}
	var sellerType = $("#sellerType").val();
	var action = "/shop/deleteBrandInfoAction.htm?currentBrand="+currentBrand;
	$("#shopInfoForm").attr("action",action);
	saveFlag = false;
	$("#shopInfoForm").submit();
}
function showBrand(currentBrand){
	$("#currentBrand").val(currentBrand);
	var sellerType = $("#sellerType").val();
	var action = "/shop/queryShopInfoAction.htm?fillType=3&sellerType="+sellerType+"&currentBrand="+currentBrand;
	$("#shopInfoForm").attr("action",action);
	saveFlag = false;
	$("#shopInfoForm").submit();
}
var saveFlag = true;
$(function() {
	$("#shopInfoForm").submit(function(e) {
		e.preventDefault();
		if (saveFlag) {
			if (checkBrandConfirm('saveBrandBtn')) {
				this.submit();
			} else {
				var action = "/shop/saveBusinessBrandInfoAction.htm?brandSaveType=0";
				$(this).attr("action",action);
			}
		} else {
			this.submit();
		}
	});
});
function addAndSaveBrand(){
	var brandSeq = $("#brandSeq").val();
	if(brandSeq == 5){
		alert("最多只能添加6个品牌!");
		return false;
	}
	var dialog = art.dialog({
			title: '品聚网信息提示',
			content: '是否要保存正在编辑的品牌信息?',
			icon: 'succeed',
			button: [
						{
							name: '保存',
							callback: function () {
								dialog.close();
								saveBrandAndNew();
								return false;
							},
							focus: true
						},
						{
							name: '不保存',
							callback: function () {
								dialog.close();
								notSaveBrandAndNew(1);
								return false;
							}
						}
					],
			width:500,
			lock: true
		});
}

function saveBrandAndNew(){
	var brandSeq = $("#brandSeq").val();
	if(brandSeq == 6){
		alert("最多只能添加6个品牌!");
		return false;
	}
	var action = "/shop/saveBusinessBrandInfoAction.htm?brandSaveType=1&currentBrand="+(brandSeq+1);
	$("#shopInfoForm").attr("action",action);
	saveFlag = true;
	$("#shopInfoForm").submit();
}

function notSaveBrandAndNew(type){
	var brandSeq = $("#brandSeq").val();
	var count = 5;
	if(type == 2){
		count = 6
	}
	if(brandSeq == 5){
		alert("最多只能添加6个品牌!");
		return false;
	}
	var brandSeq = $("#brandSeq").val();
	$("#currentBrand").val(brandSeq);
	var action = "/shop/saveBusinessBrandInfoAction.htm?brandSaveType=2&currentBrand="+(brandSeq);
	$("#shopInfoForm").attr("action",action);
	saveFlag = false;
	$("#shopInfoForm").submit();
}

function addShopInfoToCookieBrand(form, callback){
     bindInputEventToForm($('#shopInfoForm'), function(){
		var trademarkNumber = "";
		var brandName = "";
		var brandNameEach = $.trim($("#brandName").val());
		var trademarkNumberEach =$.trim( $("#trademarkNumber").val());
		trademarkNumber += encodeURIComponent(trademarkNumberEach);
		brandName += encodeURIComponent(brandNameEach);
			
		trademarkNumber = "trademarkNumber="+trademarkNumber;
		brandName = "brandName="+brandName;
		
		var strCookie = trademarkNumber + "," + brandName ;
		PinjuCookie.writeCookie('PJ2', strCookie, 'www.pinju.com', '/shop', 3);
	});
}	

</script>