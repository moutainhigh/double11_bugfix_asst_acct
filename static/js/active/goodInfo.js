var $$=function(idName){
	return document.getElementById(idName);
}

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
	
	//判断是否数字及大小区间
	var validateNum = function(num,min,max,obj){
 		var fval = parseFloat(num);
    		if(isNaN(fval)){
    			alert(obj+'不是数字');
    			return false;
   			 }else{
    			var sval = new String(fval);
                var i = sval.indexOf(".");
                if(i>=0){
                    var ssval = sval.substring(i+1);
                        if(ssval.length > 2){
                       	 alert(obj+'只能是小于两位小数点的数');
                       	 return false;
                        }
    			}
    			 if(fval<min || fval> max){
            		alert(obj+"必须是大于"+min+"及小于"+max+"的数字");
            		return false;
            	}
			}
			return true;
 	}
 	
function clearNoNum(obj){
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	if(obj.value.startWith('0')){
		obj.value = '';
	}
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	//只能为2位小数
	if(obj.value.indexOf('.') != -1){
		obj.value = obj.value.substring(0, obj.value.indexOf('.') + 3);
	}
}

function checkGoodForm(form0) {
	var auctionUrl = form0.elements['activeRegt.auctionUrl'].value;
	var auctionTitle = form0.elements['activeRegt.auctionTitle'].value;
	var logo = form0.elements['logo'].value;
	var oriPrice = form0.elements['oriPrice'].value;
	var salePrice = form0.elements['salePrice'].value;
	var auctionNum = form0.elements['activeRegt.auctionNum'].value;
	var isIsbn = form0.elements['activeRegt.isIsbn'].value;
	var otherInfo = form0.elements['activeRegt.otherInfo'].value;
	if($.trim(auctionUrl) == ""){
		alert("‘商品URL’不能为空");    
		form0.elements["activeRegt.auctionUrl"].focus();
		return ;
	}
	if(!auctionUrl.startWith("http://")){
		alert("‘商品URL’必须以\"http://\"开头");    
		form0.elements["activeRegt.auctionUrl"].focus();
		return ;
	}
	if(auctionUrl.length > 255) {
		alert("‘商品URL’长度超出范围(最大255字)");    
		form0.elements["activeRegt.auctionUrl"].focus();
		return ;
	}
	if($.trim(auctionTitle) == ""){
		alert("‘商品名称’不能为空");    
		form0.elements["activeRegt.auctionTitle"].focus();
		return ;
	}
	if(auctionTitle.length > 80) {
		alert("‘商品名称’长度超出范围(最大80字)");    
		form0.elements["activeRegt.auctionTitle"].focus();
		return ;
	}
	if($.trim(logo) == ""){
		alert("‘商品图片’不能为空");    
		form0.elements['logo'].focus();
		return ;
	}
	if(logo.length > 255) {
		alert("‘商品图片’长度超出范围(最大255字)");    
		form0.elements['logo'].focus();
		return ;
	}
	if($.trim(logo) != ""){
		// 判断文件类型是否允许上传 
		var exts = ".png,.gif,.jpg,.bmp,.jpeg";
		var suffix =logo.substring(logo.lastIndexOf(".")).toLowerCase(); 
		if(exts.indexOf(suffix) == -1) { 
			alert("\n文件类型不匹配，请上传图片格式类文件!");   
			//form0.elements["logo"].click();
			return ;
		}
	}
	if($.trim(oriPrice) == ""){
		alert("‘商品原价’不能为空");    
		form0.elements["oriPrice"].focus();
		return ;
	}else{
		if(oriPrice.length > 20) {
			alert("‘商品原价’长度超出范围(最大20字)");    
			form0.elements["oriPrice"].focus();
			return ;
		}
		var rex = /^\d+(\.\d{1,2})?$/;
		if(!rex.test(oriPrice)) {
			alert("‘商品原价’格式不正确（例8、88.8、888.88）");    
			form0.elements["oriPrice"].focus();
			return ;
		}
		if(parseInt(oriPrice) <= 0) {
			alert("‘商品原价’不能小于1元");    
			form0.elements["oriPrice"].focus();
			return ;
		}
		if(oriPrice.length > 10) {
			alert("‘商品原价’数值过大");    
			form0.elements["oriPrice"].focus();
			return ;
		}
	}
	if($.trim(salePrice) == ""){
		alert("‘商品促销价’不能为空");    
		form0.elements["salePrice"].focus();
		return ;
	}else{
		if(salePrice.length > 20) {
			alert("‘商品促销价’长度超出范围(最大20字)");    
			form0.elements["salePrice"].focus();
			return ;
		}
		if(parseFloat(salePrice) > parseFloat(oriPrice)){
			alert("‘商品促销价’不能大于‘商品原价’");    
			form0.elements["salePrice"].focus();
			return ;
		}
		var rex = /^\d+(\.\d{1,2})?$/;
		if(!rex.test(salePrice)) {
			alert("‘商品促销价’格式不正确（例8、88.8、888.88）");    
			form0.elements["salePrice"].focus();
			return ;
		}
		if(parseInt(salePrice) <= 0) {
			alert("‘商品促销价’不能小于1元");    
			form0.elements["salePrice"].focus();
			return ;
		}
		if(salePrice.length > 10) {
			alert("‘商品促销价’数值过大");    
			form0.elements["salePrice"].focus();
			return ;
		}
	}
	
	if($.trim(auctionNum) == ""){
		alert("‘商品数量’不能为空");    
		form0.elements["activeRegt.auctionNum"].focus();
		return ;
	}else{
		var rex = /^[0-9]+$/;
		if(!rex.test(auctionNum)||parseInt(auctionNum) <= 0) {
			alert("‘商品数量’必须是正整数");    
			form0.elements["activeRegt.auctionNum"].focus();
			return ;
		}
		if(auctionNum.length > 8) {
			alert("‘商品数量’数值过大");    
			form0.elements["activeRegt.auctionNum"].focus();
			return ;
		}
		//var canRegist = $('#canRegist').val();
		//if($.trim(canRegist)!=""&&parseInt(canRegist)<parseInt(auctionNum)){
			//alert("'商品数量'必须小于剩余报名数量，剩余报名数量为" + canRegist + "个");
			//form0.elements["activeRegt.auctionNum"].focus();
			//return ;
		//}
	}
	if($.trim(otherInfo) == ""){
		alert("‘其他信息’不能为空");    
		form0.elements["activeRegt.otherInfo"].focus();
		return ;
	}
	if(otherInfo.length > 500) {
		alert("‘其他信息’长度超出范围(最大500字)");    
		form0.elements["activeRegt.otherInfo"].focus();
		return ;
	}
	var points = $("#points").val();
	if(confirm("报名此次活动将会消费您" + points + "点积分，请确认？")){
		form0.saveButton.disabled = true;	//禁用'确定'按钮
		form0.submit();
	}
	//form0.reset();
}