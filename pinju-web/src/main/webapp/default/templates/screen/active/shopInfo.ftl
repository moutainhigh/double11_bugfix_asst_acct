<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>店铺信息</title>
</head>

<body>
	<link href="http://static.pinju.com/css/platform.css" rel="stylesheet" />
	<script src="http://static.pinju.com/image-preview/jquery.image-preview.js"></script>
	<div class="active-nav">
      	您的位置：我是卖家 > 营销中心 > <span class="hong"><a href="/active/listActive.htm">活动管理</a></span>
    </div>
	<form method="post" action="${base}/active/regist.htm" id="shopForm" name="shopForm" enctype="multipart/form-data">
	<input type="hidden" name="activeRegt.activityId" value="${(activeInfo.id)!0}"/>
	<input type="hidden" name="activeRegt.registType" value="${(activeInfo.registType)!}"/>
	<input type="hidden" name="activeInfo.id" value="${(activeInfo.id)!0}"/>
	<input type="hidden" name="activeInfo.registType" value="${(activeInfo.registType)!}"/>
	<input type="hidden" id="points" value="${(points)!0}"/>
	<div class="pl-aleft" style="text-align: left;">
		<ul class="pl-store">
		   <li>
			   <span class="pl-lileft">活动标题：</span>
			   <input type="text" id="shopTitle" name="activeRegt.shopTitle" value="${(activeRegt.shopTitle)!?html}" maxlength="80"/>
			   <span style="color: red;">${(activeDesc.shopTitle)!?html}</span>
		   </li>
		   <li>
			   <span class="pl-lileft">活动图片：</span>
			   <!--
			   <input type="text" id="shopPic" name="shopPic"/><input class="pl-btn-hui" value="浏览"type="button" />
			   -->
		   	   <input id="logo" name="logo" type="file" maxlength="255"/>
			   <span style="color: red;">${(activeDesc.shopPic)!?html}</span><br/>
			   <div style="padding-left:115px;">
			   <img id="logoPreview" src="http://static.pinju.com/img/pic-none.jpg" alt="" />
			   </div>
		   </li>
		   <li>
			   <span class="pl-lileft">店铺地址：</span>
			   <input type="text" id="shopUrl" name="activeRegt.shopUrl" value="${(activeRegt.shopUrl)!?html}" maxlength="255"/>
			   <span style="color: red;">${(activeDesc.shopUrl)!?html}</span>
		   </li>
		   <li>
			   <span class="pl-lileft">其他信息：</span>
			   <textarea id="shopInfo" name="activeRegt.shopInfo">${((activeRegt.shopInfo)!)?html?trim}</textarea>
			   <span style="color: red;">${(activeDesc.shopInfo)!?html}</span>
		   </li>
		</ul>
		<div class="pl-footer">
			<input type="button" name="saveButton" value="确定" onclick="checkShopForm(document.shopForm)" class="pl-btn-juse"/>
			<input type="button" value="取消" onclick="document.location.href='${base}/active/listActive.htm'" class="pl-btn-hui"/>&nbsp;<span style="color: red;">${(errorMessage)!}</span>
		</div>
	</div>
</form>
<script type="text/javascript" src="http://static.pinju.com/js/active/shopInfo.js?t=20111125.js"></script>
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