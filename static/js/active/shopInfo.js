String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
}

String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
}

	var validateFileExt = function(fileInput){
		var logo = fileInput.value;
		if($.trim(logo) != ""){
			// 判断文件类型是否允许上传 
			var exts = ".png,.gif,.jpg,.jpeg";
			var suffix =logo.substring(logo.lastIndexOf(".")).toLowerCase(); 
			if(exts.indexOf(suffix) == -1) { 
			    alert("\n文件类型不匹配，请上传图片格式类文件!");   
			    //fileInput.click();
			    return false;
			}
		}	
		return true;
	}

function checkShopForm(form) {
	var shopTitle = form.elements['activeRegt.shopTitle'].value;
	var shopPic = form.elements['logo'].value;
	var shopUrl = form.elements['activeRegt.shopUrl'].value;
	var shopInfo = form.elements['activeRegt.shopInfo'].value;
	if($.trim(shopTitle) == ""){
		alert("‘活动标题’不能为空");    
		form.elements["activeRegt.shopTitle"].focus();
		return ;
	}
	if(shopTitle.length > 80) {
		alert("‘活动标题’长度超出范围(最大80字)");    
		form.elements["activeRegt.shopTitle"].focus();
		return ;
	}
	if($.trim(shopPic) == ""){
		alert("‘活动图片’不能为空");    
		form.elements["logo"].focus();
		return ;
	}
	if(shopPic.length > 255) {
		alert("‘活动图片’长度超出范围(最大255字)");    
		form.elements["logo"].focus();
		return ;
	}
	if($.trim(shopPic) != ""){
		// 判断文件类型是否允许上传 
		var exts = ".png,.gif,.jpg,.bmp";
		var suffix =shopPic.substring(shopPic.lastIndexOf(".")).toLowerCase(); 
		if(exts.indexOf(suffix) == -1) { 
			alert("\n文件类型不匹配，请上传图片格式类文件!");   
			//form.elements["logo"].click();
			return ;
		}
	}
	if($.trim(shopUrl) == ""){
		alert("‘店铺地址’不能为空");    
		form.elements["activeRegt.shopUrl"].focus();
		return ;
	}
	if(shopUrl.length > 255) {
		alert("‘店铺地址’长度超出范围(最大255字)");    
		form.elements["activeRegt.shopUrl"].focus();
		return ;
	}
	if(!shopUrl.startWith("http://")){
		alert("‘店铺地址’必须以\"http://\"开头");    
		form0.elements["activeRegt.shopUrl"].focus();
		return ;
	}
	if($.trim(shopInfo) == ""){
		alert("‘其他信息’不能为空");    
		form.elements["activeRegt.shopInfo"].focus();
		return ;
	}
	if(shopInfo.length > 500) {
		alert("‘其他信息’长度超出范围(最大500字)");    
		form.elements["activeRegt.shopInfo"].focus();
		return ;
	}
	var points = $("#points").val();
	if(confirm("报名此次活动将会消费您" + points + "点积分，请确认？")){
		form.saveButton.disabled = true;	//禁用‘确定’按钮
		form.submit();
	}
}