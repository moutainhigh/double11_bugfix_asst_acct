<#setting classic_compatible=true>
<title>店铺--开店</title>


<link href="http://static.pinju.com/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js?t=20111207.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shop.js"></SCRIPT>-->

<iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe> 

<!-- START <普通店资料提交> -->
		<div class="openshop">
			<div class="ptitle">
				<h1>上传企业证书</h1>
				<span class="eye"><a href="/shop/choiceFillIndexAction.htm">返回</a></span>
			</div>

			
			<div class="stepwrap">
							
				<#if errorMessage?? && errorMessage!="">
					<div class="shop_pointer_style">
						<p class="red">${errorMessage}</p>
					</div>
				</#if>
				<#if ShopIshopInfoDO.idCard?exists || ShopIshopInfoDO.creation1?exists || ShopIshopInfoDO.creation2?exists>
					<div class="title"><h2>当前已上传的证书</h2></div>
					<div class="container" style="display:block;">
						<ul class="comcered cf">
							<#if ShopIshopInfoDO.idCard?exists>
								<li>
									<a href="${imageServer}${ShopIshopInfoDO.idCard}" target="_blank"><img src="${imageServer}${ShopIshopInfoDO.idCard}_80x80.jpg" alt="身份证复印件" /></a>
									<p>身份证复印件</p>
								</li>
							</#if>
							<#if ShopIshopInfoDO.creation1?exists>
								<li>
									<a href="${imageServer}${ShopIshopInfoDO.creation1}" target="_blank"><img src="${imageServer}${ShopIshopInfoDO.creation1}_80x80.jpg" alt="个人作品" /></a>
									<p>个人作品</p>
								</li>
							</#if>
							<#if ShopIshopInfoDO.creation2?exists>
								<li>
									<a href="${imageServer}${ShopIshopInfoDO.creation2}" target="_blank"><img src="${imageServer}${ShopIshopInfoDO.creation2}_80x80.jpg" alt="个人作品" /></a>
									<p>个人作品</p>
								</li>
							</#if>
						</ul>
					</div>
				
				<div class="title topen"><h2>重新上传企业证书</h2></div>
				<#else>
					<div class="title topen"><h2>上传企业证书</h2></div>
				</#if>
				
				<form id="shopInfoForm" action="${base}/shop/saveIShopUploadInfoAction.htm" method="post" onsubmit="return checkShopUploadInfo('iShopUpload', 3)"  enctype ="multipart/form-data">
					<input type="hidden" name="pj0" value="${pj0}" />
				    <input type="hidden" value="red_shopOpen" id="my-page" />
				    <input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
				    <input type="hidden" value="red_shopOpen" id="my-page" />
				    <input type="hidden" id="myProvC" name="myProvC" value="${ShopIshopInfoDO.province}"/>
					<input type="hidden" id="myCityC" name="myCityC" value="${ShopIshopInfoDO.city}"/>
					<div class="container info" style="display:block;">
						<fieldset>
							<table>
								<tr>
									<th>身份证复印件:</th>
									<td>
										<input type="file" name="idCard" id="idCard">
										<span id="idCardhui" class="hui"></span>
									</td>
								</tr>
								<tr>
									<th>个人作品:</th>
									<td>
										<input type="file" name="creation1" id="creation1">
										<span id="myFileB2hui" class="hui"></span>
									</td>
								</tr>
								<tr>
									<th>个人作品:</th>

									<td>
										<input type="file" name="creation2" id="creation2">
										<span id="myFileB3hui" class="hui"></span>
										<p>支持jpg\gif\png格式，500k以内的图片</p>
									</td>
								</tr>
								<tr>
									<th>&nbsp;</th>
									<td class="btn-imgtext"><button type="submit" id="iShopUpload" class="btn-upload"><span>上传</span></button></td>

								</tr>
								<tr>
									<th>&nbsp;</th>
									<!--<td><a href="#" class="demo">上传已完成弹出 demo</a></td>-->
								</tr>
							</table>
						</fieldset>
					</div>
					<input type="hidden" name="_token_" value="${_token_}" />
				</form>
			</div>
		</div>
<script>
$(function() {
	$(".demo").click(function(){
		var dialog = art.dialog({
			title: '品聚网信息提示',
			content: '上传成功！',
			icon: 'succeed',
			button: ({
						name: '继续上传',
						focus: true
					}),
			width:200
		});

	});
})
</script>

