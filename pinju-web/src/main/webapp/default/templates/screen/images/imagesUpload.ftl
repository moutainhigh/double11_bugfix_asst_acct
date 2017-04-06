<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>图片空间 - 图片上传</title>
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/imghost/style.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.form.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/imghost/imghost.js"></script>
<script>
	$(function() {
		var isLoading = false;
		function hideMsg(fileBox) {
			fileBox.closest('li').find('.tips-error,.tips-right').remove();
		}
		function showLoading(fileBox) {
			hideMsg(fileBox);
			fileBox.closest('li').find('.loading-msg').remove();
			fileBox.hide().after('<span class="loading-msg"><img src="http://static.pinju.com/img/page-loading.gif" width="15" height="15" />上传中，请稍候 ...</span>');
			$('#imageSubmit').css('cursor', 'not-allowed');
		}
		function hideLoading(fileBox) {
			fileBox.closest('li').find('.loading-msg').remove();
			fileBox.show();
			$('#imageSubmit').css('cursor', 'pointer');
		}
		function showSuccess(fileBox, fileName) {
			hideMsg(fileBox);
			fileBox.after('<div class="tips-right">' + fileName + ' 上传成功。</div>');
		}
		function showError(fileBox, msg) {
			hideMsg(fileBox);
			if (msg === '') {
				return;
			}
			fileBox.after('<div class="tips-error">' + msg + '</div>');
		}
		
		function uploadFile(fileBox, callback) {
			if (fileBox.val() === '') {
				callback({
					errorMsg : ''
				});
				return;
			}
			var categoryId = $('#J_categoryId').val();
			var form = $('<form action="/images/imagesUpLoadAction.htm?categoryId=' + categoryId + '" enctype="multipart/form-data" method="post"></form>');
			fileBox.before(form);
			form.append(fileBox);
			isLoading = true;
			showLoading(fileBox);
			form.ajaxSubmit({
				dataType : 'json',
				target : '#output',
				success : function(data) {
					isLoading = false;
					hideLoading(fileBox);
					form[0].reset();
					form.after(fileBox);
					form.remove();
					callback(data);
				}
			});
		}
		var fileBoxList = $('#J_imageUploadArea').find('[name=imageFiles]');
		$('#imageSubmit').click(function() {
			if (isLoading) {
				return;
			}
			// 清空提示
			fileBoxList.each(function() {
				hideMsg($(this));
			});
			// 一个都没选择时
			var count = 0;
			fileBoxList.each(function() {
				if (this.value === '') count++;
			});
			if (fileBoxList.length == count) {
				showError(fileBoxList.eq(0), '请选择文件。');
				return;
			}
			// 选择文件时
			function upload(i) {
				var fileBox = fileBoxList.eq(i);
				uploadFile(fileBox, function(data) {
					// 上传失败
					if ('errorMsg' in data) {
						showError(fileBox, data.errorMsg);
					// 上传成功
					} else {
						showSuccess(fileBox, data.fileName);
					}
					if (i + 1 < fileBoxList.length) {
						upload(i + 1);
					}
				});
			}
			upload(0);
		});
		fileBoxList.change(function() {
			fileBoxList.each(function() {
				hideMsg($(this));
			});
		});
	});
</script>
</head>

<body>
<div class="header">
	<div class="masthead cf">
		<div class="logo">
			<a href="http://www.pinju.com/">
				<img class="logo_pic" width="314" height="57" src="http://static.pinju.com/img/imghost/logo-imghost.png" />
			</a>
		</div>
	</div>
</div>

<!-- nav imghost -->
<div class="wrap nav-imghost">
	<ul class="eye">
		<li><a href="http://service.pinju.com/cms/html/2011/bisapp_0921/60.html" title="使用帮助">使用帮助</a></li>
	</ul>
	<ul class="nav-wrap">
		<li><a href="http://www.pinju.com/images/queryStorageAction.htm" title="图片管理">图片管理</a></li>
		<li class="current"><a href="http://www.pinju.com/images/viewImagesUpLoadAction.htm" title="图片上传">图片上传</a></li>
		<li><a href="http://www.pinju.com/images/imagesCategoryAction.htm" title="分类管理">分类管理</a></li>
	</ul>	
</div>

<!-- content -->
<div class="wrap cf">
	<div class="sidebar">
		<div class="box">
			<div class="title"><h2>图片空间统计</h2></div>
			<div class="content">
				<p>已用空间：<strong><#if imagesCategoryDO?exists>${(imagesCategoryDO.userSize/1048576)?string("0.00")}</#if></strong> M<br />
				共 <strong>${imagesCount?if_exists}</strong> 张</p>
			</div>
		</div>
		<div class="box">
			<div class="title"><h2>图片分类</h2></div>
			<div class="content cf">
				<ul class="imgcat">
					<li class="active">
					<a href="${base}/images/queryStorageAction.htm" title="所有分类">所有分类</a>
					</li>
					<#if imagesList?exists>
					<#list imagesList as imagesCa>
					  <#if imagesCa.secondCategoryList.size() == 0> 
						<li>
							<#else>
							<li>
							<span class="accordion"></span>
							</#if>
						<a href="${base}/images/queryStorageByCateorgIdAction.htm?imageCategoryId=${imagesCa.firstCategoryId}" title="${(imagesCa.firstCategoryName)?if_exists?html}">${(imagesCa.firstCategoryName)?if_exists?html}</a>
					<#if imagesCa.secondCategoryList?exists>
						<ul>
					<#list imagesCa.secondCategoryList as secondImageCa>
						<li><a href="${base}/images/queryStorageByCateorgIdAction.htm?imageCategoryId=${secondImageCa[0]}" title="${(secondImageCa[1])?if_exists?html}">${(secondImageCa[1])?if_exists?html}</a></li>
					</#list>
						</ul>
					</#if>
						</li>
					</#list>
					</#if>
				</ul>
			</div>
		</div>
	</div>
  
	<div class="contents">
		<div class="cf"> 
			<div class="pic-space">
				<p>空间剩余：<font color="red"><#if imagesCategoryDO?exists>${((1073741824-imagesCategoryDO.userSize)/1048576)?string("0.00")}</#if>M</font>/1024M 还能上传大约 <#if imagesCategoryDO?exists>${((1073741824-imagesCategoryDO.userSize)/512000)}</#if> 张 500K 左右的图片</p>
			</div>
			
			<div class="image-upload">
				<div class="image-upload-sort">
					<p>
						上传到
						<select name="categoryId" id="J_categoryId">
						<#if imagesList?exists>
						<#list imagesList as imagesCa>
						<option value="${imagesCa.firstCategoryId?if_exists}" <#if categoryId?exists && imagesCa.firstCategoryId?exists && categoryId?string == imagesCa.firstCategoryId> selected</#if>>${(imagesCa.firstCategoryName)?if_exists?html}</option>
						<#if imagesCa.secondCategoryList?exists>
						<#list imagesCa.secondCategoryList as secondImageCa>
							<option value="${secondImageCa[0]?if_exists}" <#if categoryId?exists && secondImageCa[0]?exists && categoryId?string == secondImageCa[0]?string> selected</#if>>&nbsp;&nbsp;&nbsp;&nbsp;|_${(secondImageCa[1])?if_exists?html}</option>
						</#list>
						</#if>
						</#list>
						</#if>
						</select>
					</p>
				</div>
					
				<div class="image-upload-bar" id="J_imageUploadArea">
					<ul>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><input class="image-file" name="imageFiles" type="file" value="选择图片"></li>
						<li><button id="imageSubmit" name="imageSubmit" class="image-upload-btn" type="button">立即上传</button></li>
						<li><font color="red">${errorMsg?if_exists}</font></li>
					</ul>
					
					<div class="tips-text">
						<strong class="bd">小提示</strong>
						<p>超过1M 的图片无法上传</p>
						<p>支持jpg、jpeg、png、gif格式</p> 
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<!-- // content -->

</body>
</html>

