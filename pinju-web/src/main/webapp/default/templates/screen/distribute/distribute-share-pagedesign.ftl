<title>爱分享订单</title>
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<link rel="stylesheet" type="text/css" media="screen" href="http://static.pinju.com/css/shareBuying/shareBuying.css" />
		<form id="shareBuyForm" name="shareBuyForm" action="${base}/distributor/setShareDesign.htm" method="post"  enctype="multipart/form-data">
			<div class="shareBuying cf">
				<div class="shareBuying-setting">
					<h3 class="title">设置页面头部</h3>
					<span class="hard">设置遇到困难？</span>
					<a class="tutorials-link" id="tutorials-link" href="#">点击查看教程</a>
				</div>
				<div class="shareBuying-logo">
					<h3 class="title">店标LOGO</h3>
					<div class="img-box">
						<img id="shareBuyingImgLogo" src="http://static.pinju.com/img/shareBuying/set-bg1.gif" />
					</div>
					<div class="button">
						<input id="shareBuyingBtnLogo" type="file" name="shopIndex" />
						<button type="button"></button>
					</div>
					<p class="info">店标LOGO图片宽度为161px高度为77px，图片大小控制在100KB以内</p>
				</div>
				<div class="shareBuying-banner">
					<h3 class="title">横幅</h3>
					<div class="img-box">
						<img id="shareBuyingImgBanner" src="http://static.pinju.com/img/shareBuying/set-bg2.gif" />
					</div>
					<div class="button">
						<input id="shareBuyingBtnBanner" type="file" name="bannersImg" />
						<button type="button"></button>
					</div>
					<p class="info">横幅图片宽度为489px高度为77px，图片大小控制在100KB以内</p>
				</div>
				<div class="shareBuying-bigger">
					<div class="left">
						<h3 class="title">宣传大图</h3>
						<div class="img-box">
							<img id="shareBuyingImgBigger" src="http://static.pinju.com/img/shareBuying/set-bg3.gif" />
						</div>
					</div>
					<div class="right">
						<div class="button">
							<input id="shareBuyingBtnBigger" type="file" name="adImg" />
							<button type="button"></button>
						</div>
						<p class="info">宣传大图宽度为489px高度为367px，图片大小控制在300KB以内</p>
					</div>
				</div>
				<div class="shareBuying-copy">
					<h3 class="title">个性文案</h3>
					<p class="info">文字</p>
					<div class="textarea">
						<textarea name="descript" id="shareBuyingTextarea">${((shareDesigner.descript)!"您最多可以输入20个汉字")?html}</textarea>
					</div>
					<input class="submit" type="button" onclick="goSubmit()" />
				</div>
			</div>
		</form>
<div class="dialog">
	<div class="images">
		<img src="http://static.pinju.com/img/shareBuying/img1.jpg" alt="" />
		<img src="http://static.pinju.com/img/shareBuying/img2.jpg" alt="" />
		<img src="http://static.pinju.com/img/shareBuying/img3.jpg" alt="" />
		<img src="http://static.pinju.com/img/shareBuying/img4.jpg" alt="" />
		<img src="http://static.pinju.com/img/shareBuying/img5.jpg" alt="" />
	</div>
	<div class="prev"></div>
	<div class="next"></div>
	<div class="close"></div>
</div>
<iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe>
<script src="http://static.pinju.com/js/shareBuying/jquery.dialog.js"></script>
<script src="http://static.pinju.com/image-preview/jquery.image-preview.js"></script>
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
<script language="javascript" type="text/javascript">
<!--
	$(function() {
		$('#decorateDistributor').addClass('count');
	
		//弹出框
		$('#tutorials-link').click(function() {
			$.dialog();
			return false;
		});
		
		//textarea文本处理
		var shTextarea = $('#shareBuyingTextarea');
		if(shTextarea.val() == '您最多可以输入20个汉字') {
			shTextarea.removeClass('change');
		} else {
			shTextarea.addClass('change');
		}
		shTextarea.focus(function() {
			if($(this).val() == '您最多可以输入20个汉字') {
				$(this).val('');
			} else {
				$(this).addClass('change');
			}
		});
		shTextarea.keyup(function() {
			$(this).addClass('change');
		});
		shTextarea.blur(function() {
			if($(this).val() == '') {
				$(this).val('您最多可以输入20个汉字').removeClass('change');
			} else {
				$(this).addClass('change');
			}
		});
		
		//图片预览
		$.imagePreview({
			file : $('#shareBuyingBtnLogo'),
			img : $('#shareBuyingImgLogo'),
			maxWidth : 101,
			maxHeight : 47
		});
		$.imagePreview({
			file : $('#shareBuyingBtnBanner'),
			img : $('#shareBuyingImgBanner'),
			maxWidth : 311,
			maxHeight : 47
		});
		$.imagePreview({
			file : $('#shareBuyingBtnBigger'),
			img : $('#shareBuyingImgBigger'),
			maxWidth : 311,
			maxHeight : 233
		});
	});
	
	String.prototype.lenB = function(){return this.replace(/[^\x00-\xff]/g, "** ").length;} 
	
	/*--------------------数据提交-----------------------*/
	function goSubmit() {
		$(function() {
			var textarea = $('#shareBuyingTextarea').val();
			if(textarea != "" && textarea.lenB()>60) {
				alert('个性文案最多可以输入20个汉字');
				return false;
			}
			if(textarea == '您最多可以输入20个汉字')
				$('#shareBuyingTextarea').val('');
			var form = $('#shareBuyForm'),
				iframe = $('#uploadIframe');
			iframe.bind('load', function() {
				iframe.unbind();
				var iframeDoc = iframe[0].contentDocument || iframe[0].contentWindow.document;
				var data, str = iframeDoc.body.innerHTML;
				str = str.replace(/<.+?>/g, '');
				try {
					data = $.parseJSON(str);
				} catch (e) {
					alert(str);
				}
				$.hideFullLoading();
				if(data.message != '')
					alert(data.message);
				switch(data.status) {
				case 0:
					document.location.href = '${base}/distributor/getShareDesign.htm';
					break;
				case 2:
					document.location.href = '${base}/distributor/index.htm';
					break;
				}
				return;
			});
			form.attr({
				method: 'post',
				action : '${base}/distributor/setShareDesign.htm',
				target : 'uploadIframe',
				enctype : "multipart/form-data"
			});
			if(confirm('确认提交吗？')) {
				$.showFullLoading({description : '数据正在提交中，请稍候...'
				});
				form.submit();
			}
			return;
		});
	}
	
	/*--------------------图片验证-----------------------*/	
	function initImageValidate(id, width, height){
		$("#shareBuyingBtn"+id).change(function(){
			$.showFullLoading({description : '图片正在校验中，请稍候...'
			});
			var picname=$("#shareBuyingBtn"+id).val();
			var form = $('#shareBuyForm'),
				iframe = $('#uploadIframe');
			iframe.bind('load', function() {
				iframe.unbind();
				var iframeDoc = iframe[0].contentDocument || iframe[0].contentWindow.document;
				var data, str = iframeDoc.body.innerHTML;
				str = str.replace(/<.+?>/g, '');
				$.hideFullLoading();
				if(str.indexOf("{") != 0) {
					alert('您上传的图片太大。');
					resetFile(id);
				} else {
					try {
						data = $.parseJSON(str);
						if (!data.status) {
							alert(data.message);
							resetFile(id);
						}
					} catch (e) {
						alert(str);
					}
				}
				form.removeAttr('action');
				form.removeAttr('target');
				return;
			});
			form.attr({
				method: 'post',
				action : '${base}/distributor/checkUploadImage.htm?uploadType=' + id,
				target : 'uploadIframe',
				enctype : "multipart/form-data"
			});
			form.submit();
			return;
		});
	}
	
	function resetFile(id) {
		var file = $("#shareBuyingBtn"+id);
		var tempForm = document.createElement('form');
		file.before(tempForm);
		$(tempForm).append(file);
		tempForm.reset();
		$(tempForm).after(file);
		$(tempForm).remove(); 
		if(id == 'Logo') {
			document.getElementById('shareBuyingImgLogo').src = logo;
			$('#shareBuyingImgLogo').css({'width': '98px', 'height': '47px'});
		} else if(id == 'Banner') {
			document.getElementById('shareBuyingImgBanner').src = banner;
			$('#shareBuyingImgBanner').css({'width': '298px', 'height': '47px'});
		} else if(id == 'Bigger') {
			document.getElementById('shareBuyingImgBigger').src = ad;
			$('#shareBuyingImgBigger').css({'width': '310px', 'height': '233px'});
		}
	}
	
	var logo = '${shareDesigner.shopIndex!"http://static.pinju.com/img/shareBuying/set-bg1.gif"}';
	var ad = '${shareDesigner.adImg!"http://static.pinju.com/img/shareBuying/set-bg3.gif"}';
	var banner = '${shareDesigner.bannerImg!"http://static.pinju.com/img/shareBuying/set-bg2.gif"}';
	
	$(document).ready(function() {
		initImageValidate('Logo', 98, 47);
		initImageValidate('Banner', 298, 47);
		initImageValidate('Bigger', 310, 233);
		document.getElementById('shareBuyingImgLogo').src = logo;
		$('#shareBuyingImgLogo').css({'width': '98px', 'height': '47px'});
		document.getElementById('shareBuyingImgBanner').src = banner;
		$('#shareBuyingImgBanner').css({'width': '298px', 'height': '47px'});
		document.getElementById('shareBuyingImgBigger').src = ad;
		$('#shareBuyingImgBigger').css({'width': '310px', 'height': '233px'});
	});
//-->
</script>
