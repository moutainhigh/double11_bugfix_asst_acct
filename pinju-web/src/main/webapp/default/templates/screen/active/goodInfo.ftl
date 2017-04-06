<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>商品信息</title>
</head>

<body>
	<link href="http://static.pinju.com/css/platform.css" rel="stylesheet" />
	<script src="http://static.pinju.com/image-preview/jquery.image-preview.js"></script>
	<div class="active-nav">
      	您的位置：我是卖家 > 营销中心 > <span class="hong"><a href="/active/listActive.htm">活动管理</a></span>
    </div>
	<form method="post" action="${base}/active/regist.htm" id="goodForm1" name="goodForm1" enctype="multipart/form-data">
	<input type="hidden" name="activeRegt.activityId" value="${(activeDesc.activityId)!0}"/>
	<input type="hidden" name="activeRegt.registType" value="${(activeDesc.registType)!}"/>
	<input type="hidden" name="activeInfo.id" value="${(activeDesc.activityId)!0}"/>
	<input type="hidden" name="activeInfo.registType" value="${(activeDesc.registType)!}"/>
	<input type="hidden" id="points" value="${(points)!0}"/>
	<div class="pl-aleft" style="text-align: left;">
		<ul class="pl-store">
		   <li>
		   	<span class="pl-lileft">商品URL：</span>
		   	<input type="text" id="auctionUrl" name="activeRegt.auctionUrl" value="${(activeRegt.auctionUrl)!?html}" maxlength="255"/>
		   	<span style="color: red;">${(activeDesc.auctionUrl)!?html}</span>
		   </li>
		   <li>
		   	<span class="pl-lileft">商品名称：</span>
		   	<input type="text" id="auctionTitle" name="activeRegt.auctionTitle" value="${(activeRegt.auctionTitle)!?html}" maxlength="80"/>
		   	<span style="color: red;">${(activeDesc.auctionTitle)!?html}</span>
		   </li>
		   <li>
		   	<span class="pl-lileft">促销图片：</span>
		   	<!--
		   	<input type="text" id="salePic" name="activeRegt.salePic"/>
		   	<input class="pl-btn-hui" value="浏览"type="button" />
		   	-->
		   	<input id="logo" name="logo" type="file" maxlength="255"/>
		   	<span style="color: red;">${(activeDesc.salePic)!?html}</span>
		   	<div style="padding-left:115px;">
		   		<img id="logoPreview" src="http://static.pinju.com/img/pic-none.jpg" alt="" />
			</div>
		   </li>
		   <li>
		   	<span class="pl-lileft">原价：</span>
		   	<input type="text" id="oriPrice" name="oriPrice" onkeyup="clearNoNum(this);" value="${(oriPrice)!}" maxlength="9"/>
		   	<span style="color: red;">${(activeDesc.oriPrice)!?html}</span>
		   </li>
		   <li>
		   	<span class="pl-lileft">促销价：</span>
		   	<input type="text" id="salePrice" name="salePrice" onkeyup="clearNoNum(this);" value="${(salePrice)!}" maxlength="9"/>
		   	<span style="color: red;">${(activeDesc.salePrice)!?html}</span>
		   </li>
		   <li>
		   	<span class="pl-lileft">数量：</span>
		   	<input type="text" id="auctionNum" name="activeRegt.auctionNum" maxlength="9"
		   		onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}" 
				onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}"
		   		value="${(activeRegt.auctionNum)!}"/>
		   	<span style="color: red;">${(activeDesc.auctionNum)!?html}</span>
		   </li>
		   <li>
		   	<span class="pl-lileft">是否包邮：</span>
		   	<span>
		   	<input type="radio" style="margin:5px;" name="activeRegt.isIsbn" value="1" checked="checked" <#if (activeRegt.isIsbn)?exists && activeRegt.isIsbn==1>checked=checked</#if>/>是
		   	</span>
		   	<span style="margin:30px;">
		   	<input type="radio" style="margin:5px;" name="activeRegt.isIsbn" value="0" <#if (activeRegt.isIsbn)?exists && activeRegt.isIsbn==0>checked=checked</#if>/>否
		   	</span>
		   	<span style="color: red;">${(activeDesc.isIsbn)!?html}</span>
		   </li>
		   <li>
		   	<span class="pl-lileft">其他信息：</span>
		   	<textarea id="otherInfo" name="activeRegt.otherInfo">${((activeRegt.otherInfo)!)?html?trim}</textarea>
		   	<span style="color: red;">${(activeDesc.otherInfo)!?html}</span>
		   </li>
		</ul>
		<div class="pl-footer">
			<input type="button"  onclick="checkGoodForm(document.goodForm1);" name="saveButton" value="确定" class="pl-btn-juse"/>
			<input type="button" value="取消" onclick="document.location.href='${base}/active/listActive.htm'" class="pl-btn-hui"/>&nbsp;<span style="color: red;">${(errorMessage)!}</span>
		</div>
	</div>
</form>
<script type="text/javascript" src="http://static.pinju.com/js/active/goodInfo.js"></script>
<input type="hidden" value="list-active" id="my-page" />
<script>
(function(){
	$.imagePreview({
		file : $('#logo'),
		img : $('#logoPreview'),
		maxWidth : 160,
		maxHeight : 160
	});
	
	$("#logo").live("change",
		function(){
			var exts = ".png,.gif,.jpg,.jpeg";
			var suffix = $(this).val().substring($(this).val().lastIndexOf(".")).toLowerCase(); 
			if(exts.indexOf(suffix) == -1) { 
				alert("\n文件类型不匹配，请上传图片格式类文件!");   
				$(this).focus();
				return;
			}
		}
	);
})();
</script>
</body>
</html> 