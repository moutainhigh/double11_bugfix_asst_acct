function bindInputEventToForm(form, callback){
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

function setSelectedStyle(id, className) {
	document.getElementById(id).className = className;
}

function goIndex(id){
	$('#'+id).attr('action', '/item/categoryList.htm');
	$("#"+id).submit();
}
var seqBrand = 0;
var fileType='jpg,jpeg,gif,png';
$(document)
		.ready(
				function() {
					
					//店标
					initImageValidate('shopLogo','shopLogoC', 80, 'myFile1tip', 0);
					initImageValidate('shopLogo','shopLogoB', 80, 'myFile1tip', 1);
					initImageValidate('shopLogo','shopLogoI', 80, 'myFile1tip', 2);
					//i小铺上传信息
					initImageValidate('idCard','idCard', 500, 'idCardhui', 3);
					initImageValidate('creation1','creation1', 500, 'myFileB2hui', 3);
					initImageValidate('creation2','creation2', 500, 'myFileB3hui', 3);
					//C和B店上传信息
					initImageValidate('businessLicense','businessLicenseC', 500, 'businessLicenseCtip', 4);
					initImageValidate('organizationCode','organizationCodeC', 500, 'organizationCodeCtip', 4);
					initImageValidate('taxPass','taxPassC', 500, 'taxPassCtip', 4);
					
					initImageValidate('businessLicense','businessLicenseB', 500, 'businessLicenseBtip', 5);
					initImageValidate('organizationCode','organizationCodeB', 500, 'organizationCodeBtip', 5);
					initImageValidate('taxPass','taxPassB', 500, 'taxPassBtip', 5);
					
					initImageValidate('brandLogo','brandLogo', 80, 'brandLogohui', 6);
					initImageValidate('brandCertificate','brandCertificate', 500, 'brandCertificatehui', 6);
					initImageValidate('qualityCertificate','qualityCertificate', 500, 'qualityCertificatehui', 6);
					//initImageValidate('myFileB4', 500, 'myFileB4hui', 2);
					//initImageValidate('myFileB5', 500, 'myFileB5hui', 2);
					var selProvC = $("#myProvC").val();
					var selProvB = $("#myProvB").val();
					var selCityC = $("#myCityC").val();
					var selCityB = $("#myCityB").val();
					//带参数
					$("#shopCityC").selectprov( {
						provBuildName : "shopCustomerInfoDO.province",
						cityBuildName : "shopCustomerInfoDO.city",
						currProv : selProvC,
						currCity : selCityC

					});

					$("#shopCityB").selectprov( {
						provBuildName : "shopBusinessInfoDO.province",
						cityBuildName : "shopBusinessInfoDO.city",
						currProv : selProvB,
						currCity : selCityB
					});
					
					$("#shopCustomerInfoDOprovince").blur(function(){
						spCheck('shopCustomerInfoDOprovince','#shopCustomerInfoDOprovincetip');
					});
					
					$("#shopBusinessInfoDOprovince").blur(function(){
						spCheck('shopBusinessInfoDOprovince','#shopBusinessInfoDOprovincetip');
					});
					
					
					$(
							"#shopManagerMobile,#emergencyContactPhone")
							.numeral( {
								
							});

					$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
					$("#businessLicenseEndDate").datepicker( {
						changeMonth : true,
						changeYear : true,
						minDate:new Date(),
						maxDate: '+90y',
						autoSize : false,
						yearRange : '0:+90',
						dateFormat: 'yy-mm-dd'
					});
					
				});



var brandInfoCount = 0;

function checkBrandAndConfirm() {
	disableSubmit("step3Submit");
	if ($('.kd-tjlist').size() == 0) {
		alert("请至少添加一个品牌!");
		enableSubmit("step3Submit");
		return false;
	}
	var trademarkNumber = "";
	var brandName = "";
	var brandEnglishName = "";
	var brandStory = "";
	var flag = 1;
	$(".kd-tjlist").each(function(){
		var brandNameEach = $.trim($(this).find(".brandName").val());
		var trademarkNumberEach =$.trim( $(this).find(".trademarkNumber").val());
		var brandEnglishNameEach = $.trim($(this).find(".brandEnglishName").val());
		var brandStoryEach = $.trim($(this).find(".brandStory").val());
		var myfile1=$(this).find(".myFile1").val();
		var myfile2=$(this).find(".myFile2").val();
		var myfile3=$(this).find(".myFile3").val();
		if(trademarkNumberEach == ""){
			$(this).find(".trademarkNumber").focus();
			$(this).find(".trademarkNumbertip").html('<font color="red">请输入商标注册证号（申请号）!</font>');
			flag = -1;
		}else{
			if (trademarkNumberEach.length>50 ) {
				$(this).find(".trademarkNumber").focus();
				$(this).find(".trademarkNumbertip").html('<font color="red">商标注册证号（申请号）不能超过50个字符!</font>');
				flag = -1;
			}else{
				$(this).find(".trademarkNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
		}
		if(brandNameEach == ""){
			$(this).find(".brandName").focus();
			$(this).find(".brandNametip").html('<font color="red">请输入品牌名称!</font>');
			flag = -1;
		}else{
			if (brandNameEach.length>50) {
				$(this).find(".brandName").focus();
				$(this).find(".brandNametip").html('<font color="red">品牌名称不能超过50个字符!</font>');
				flag = -1;
			}
			/*else if(!brandNameEach.isZZS()){
				$(this).find(".brandName").focus();
				$(this).find(".brandNametip").html('<font color="red">只能输入中文、英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！</font>');
				flag = -1;
			}*/
			else{
				$(this).find(".brandNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
			
		}
		if(brandEnglishNameEach == ""){
			//$(this).find(".brandEnglishName").focus();
			//$(this).find(".brandEnglishNametip").html('<font color="red">请输入品牌英文名!</font>');
			//flag = -1;
		}else{
			if (brandEnglishNameEach.length>50) {
				$(this).find(".brandEnglishName").focus();
				$(this).find(".brandEnglishNametip").html('<font color="red">品牌英文名不能超过50个字符!</font>');
				flag = -1;
			}else if(!brandEnglishNameEach.isSN2()){
				$(this).find(".brandEnglishName").focus();
				$(this).find(".brandEnglishNametip").html('<font color="red">只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</font>');
				flag = -1;
			}else{
			  $(this).find(".brandEnglishNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
		}
		if(myfile1 == ""){
			$(this).find(".myFile1").focus();
			$(this).find(".myFilehui1").html('<font color="red">请选择品牌logo!</font>');
			flag = -1;
		}else{
			var i=myfile1.lastIndexOf('.');
			if(i==-1){
				$(this).find(".myFile1").focus();
				$(this).find(".myFilehui1").html('<font color="red">请上传正确logo图片格式!</font>');
				flag = -1;
			}else{
				var str=myfile1.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$(this).find(".myFile1").focus();
					$(this).find(".myFilehui1").html('<font color="red">请上传正确logo图片格式!</font>');
					flag = -1;
				}else{
					$(this).find(".myFilehui1").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		if(brandStoryEach == ""){
			$(this).find(".brandStory").focus();
			$(this).find(".brandStorytip").html('<font color="red">请输入品牌故事!</font>');
			flag = -1;
		}else{
			if (brandStoryEach.length>500) {
				$(this).find(".brandStory").focus();
				$(this).find(".brandStorytip").html('<font color="red">品牌故事不能超过500个字符!</font>');
				flag = -1;
			}else{
				$(this).find(".brandStorytip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
			
		}
		
		if(myfile2 == ""){
			$(this).find(".myFile2").focus();
			$(this).find(".myFilehui2").html('<font color="red">请选择品牌授权书或品牌商标注册证书!</font>');
			flag = -1;
		}else{
			var i=myfile2.lastIndexOf('.');
			if(i==-1){
				$(this).find(".myFile2").focus();
				$(this).find(".myFilehui2").html('<font color="red">请上传正确的品牌授权书或品牌商标注册证书图片格式!</font>');
				flag = -1;
			}else{
				var str=myfile2.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$(this).find(".myFile2").focus();
					$(this).find(".myFilehui2").html('<font color="red">请上传正确的品牌授权书或品牌商标注册证书格式!</font>');
					flag = -1;
				}else{
					$(this).find(".myFilehui2").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		
		if(myfile3 == ""){
			$(this).find(".myFile3").focus();
			$(this).find(".myFilehui3").html('<font color="red">请选择商品质量检验证书	!</font>');
			flag = -1;
		}else{
			var i=myfile3.lastIndexOf('.');
			if(i==-1){
				$(this).find(".myFile3").focus();
				$(this).find(".myFilehui3").html('<font color="red">请上传正确的商品质量检验证书格式!</font>');
				flag = -1;
			}else{
				var str=myfile3.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$(this).find(".myFile3").focus();
					$(this).find(".myFilehui3").html('<font color="red">请上传正确的商品质量检验证书!</font>');
					flag = -1;
				}else{
					$(this).find(".myFilehui3").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		
		trademarkNumber += trademarkNumberEach+"|";
		brandName += brandNameEach+"|";
		if(!brandEnglishNameEach){
			brandEnglishNameEach = " ";
		}
		brandEnglishName += brandEnglishNameEach+"|";
		brandStory += brandStoryEach+"|";
		
		
	});
	if(flag == -1){
		ifError();
		enableSubmit("step3Submit");
		return false;
	}
	trademarkNumber = trademarkNumber.substring(0,trademarkNumber.length-1);
	brandName = brandName.substring(0,brandName.length-1);
	brandEnglishName = brandEnglishName.substring(0,brandEnglishName.length-1);
	brandStory = brandStory.substring(0,brandStory.length-1);
	
	$("#trademarkNumberHidden").val(trademarkNumber);
	$("#brandNameHidden").val(brandName);
	$("#brandEnglishNameHidden").val(brandEnglishName);
	$("#brandStoryHidden").val(brandStory);

	//alert($("#trademarkNumberHidden").val());
	//alert($("#brandNameHidden").val());
	//alert($("#brandEnglishNameHidden").val());
	//alert($("#brandStoryHidden").val());
	//$("#step3Submit").attr("disabled","disabled");
	return true;
}
function checkBrandImage(id, seqBrand){
	if(id=='myFile'){
		var temp = seqBrand.substring(1,2);
		var k = 0;
		if(temp == '1'){
			k = 80;
		}else if(temp == '2' || temp == '3'){
			k = 500;
		}
		var _id = '#myFile'+seqBrand;
		var picname = $(_id).val();
		var obj = $(_id);
		var obj2 = $(".myFilehui"+seqBrand);
		var form = $('#shopInfoForm'),
			iframe = $('#uploadIframe');
		$.showFullLoading({description : '图片正在校验中，请稍候...'
		});
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
			if (data) {
				var imagFlag = data.imagFlag;
				if (imagFlag == 1) {
					alert("缺少商品图片");
				}else if (imagFlag == 2){
					alert("错误的格式");
				}else if (imagFlag == 3){
					alert("图片大小不能超过"+k+"K。");
				}
//				if (imagFlag > 0) {
//					$("#myFile").val("");
//					$(this).find(".myFile").val('');
//				} 
				if (imagFlag > 0) {
					//obj.val('');
					setValEmpty(_id);
					obj2.html('');
				} else{
					obj2.html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					//form.removeAttr('action');
					//form.removeAttr('target');
					//form.attr('action','${base}/shop/saveBusinessShopInfoAction.htm?sellerType=1&fillStep=3');
					setSubmitErrorEmpty();
				}
				form.removeAttr('action');
				form.removeAttr('target');
				form.attr('action','${base}/shop/saveBusinessShopInfoAction.htm?sellerType=1&fillStep=3');
				$.hideFullLoading();
			}
			//form.removeAttr('method');
			//form.removeAttr('action');
			//form.removeAttr('target');
		});
		form.attr({
			method: 'post',
			action : 'validateUploadImage.htm?max='+k+'&picName='+picname,
			target : 'uploadIframe',
			enctype : "multipart/form-data"
		});
		form[0].submit();
	   }	
}

function checkBrandSp(id,seqBrand) {
	setSubmitErrorEmpty();
	if ($('.kd-tjlist').size() == 0) {
		alert("请至少添加一个品牌!");
		return false;
	}
	var trademarkNumber = "";
	var brandName = "";
	var brandEnglishName = "";
	var brandStory = "";
	$(".kd-tjlist").each(function(){
		if(id=='trademarkNumber'){
			var trademarkNumberEach =$.trim( $(this).find(".trademarkNumber").val());
			if(trademarkNumberEach == ""){
		        //$(this).find(".trademarkNumber").focus();
				$(this).find(".trademarkNumbertip").html('<font color="red">请输入商标注册证号（申请号）!</font>');
				flag = -1;
			}else{
				if (trademarkNumberEach.length>50 ) {
					//$(this).find(".trademarkNumber").focus();
					$(this).find(".trademarkNumbertip").html('<font color="red">商标注册证号（申请号）不能超过50个字符!</font>');
					flag = -1;
				}else{
					$(this).find(".trademarkNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
	   }
	   if(id=='brandName'){
	   	var brandNameEach = $.trim($(this).find(".brandName").val());
			if(brandNameEach == ""){
				//$(this).find(".brandName").focus();
				$(this).find(".brandNametip").html('<font color="red">请输入品牌名称!</font>');
				flag = -1;
			}else{
				if (brandNameEach.length>50) {
					//$(this).find(".brandName").focus();
					$(this).find(".brandNametip").html('<font color="red">品牌名称不能超过50个字符!</font>');
					flag = -1;
				}
				/*else if(!brandNameEach.isZZS2()){
					//$(this).find(".brandName").focus();
					$(this).find(".brandNametip").html('<font color="red">对不起,品牌名中不能含有"%"</font>');
					flag = -1;
				}*/
				else{
					$(this).find(".brandNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
				
			}
	   }
	   if(id=='brandEnglishName'){
	   	var brandEnglishNameEach = $.trim($(this).find(".brandEnglishName").val());
			if(brandEnglishNameEach == ""){
				//$(this).find(".brandEnglishName").focus();
				//$(this).find(".brandEnglishNametip").html('<font color="red">请输入品牌英文名!</font>');
				//flag = -1;
			}else{
				if (brandEnglishNameEach.length>50) {
					//$(this).find(".brandEnglishName").focus();
					$(this).find(".brandEnglishNametip").html('<font color="red">品牌英文名不能超过50个字符!</font>');
					flag = -1;
				}else if(!brandEnglishNameEach.isSN2()){
					//$(this).find(".brandEnglishName").focus();
					$(this).find(".brandEnglishNametip").html('<font color="red">只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</font>');
					flag = -1;
				}else{
				  $(this).find(".brandEnglishNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
	   }
	   if(id=='brandStory'){
	   	var brandStoryEach = $.trim($(this).find(".brandStory").val());
			if(brandStoryEach == ""){
				//$(this).find(".brandStory").focus();
				$(this).find(".brandStorytip").html('<font color="red">请输入品牌故事!</font>');
				flag = -1;
			}else{
				if (brandStoryEach.length>500) {
					//$(this).find(".brandStory").focus();
					$(this).find(".brandStorytip").html('<font color="red">品牌故事不能超过500个字符!</font>');
					flag = -1;
				}else{
					$(this).find(".brandStorytip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
				
			}
	   }
	   	
	});
}

String.prototype.Trim = function() {
	var m = this.match(/^\s*(\S+(\s+\S+)*)\s*$/);
	return (m == null) ? "" : m[1];
}

String.prototype.isMobile = function() {
	//return (/^(?:13\d|15[89])-?\d{5}(\d{3}|\*{3})$/.test(this.Trim()));
	return (/^((1(3[4-9]|5[012789]|8[278])\d{8})|(18[09]\d{8})|(1(3[0-2]|5[56]|8[56]|4[57])\d{8})|(1[35]3\d{8}))$/.test(this.Trim()));
}

String.prototype.isTel = function() {
	//"兼容格式: 国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"  
	return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3}))?$/.test(this.Trim()));
}

String.prototype.isEmail = function() {
	return (/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(this.Trim()));
}

String.prototype.isPostal = function() {
	return (/^\d{6}$/.test(this.Trim()));
}
String.prototype.isDate = function(){
	return (/^((((19|20|21)\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\d|30))|(((19|20|21)\d{2})-(0?[13578]|1[02])-31)|(((19|20|21)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|((((19|20|21)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$/.test(this.Trim()));
}

//只能是 中文 英文 数字
String.prototype.isZZS=function(){
	return (/^[a-z A-Z 0-9\u4e00-\u9fa5!@#$^&*\.,\-'"\{\}\[\]=_\+\(\)\?<>]+$/).test(this.Trim());
}

//不能输入%
String.prototype.isZZS2=function(){
	if(this.Trim().indexof("%")!=-1){
		return true;
	}
	return false;
	//return (/^[a-z A-Z 0-9\u4e00-\u9fa5!@#$^&*\.,\-'"\{\}\[\]=_\+\(\)\?<>]+$/).test(this.Trim());
}

//只能是英文 数字
String.prototype.isSN=function(){
	//return (/^[a-z A-Z 0-9]+$/).test(this.trim());
	return (/^[a-z A-Z 0-9]+$/).test(this.Trim());
}

//组织机构代码证
String.prototype.isSN3=function(){
	return (/[A-Z0-9]{8}\-[A-Z0-9]{1}/).test(this.Trim());
}

//英文 数字 特殊字符
String.prototype.isSN2=function(){
	return (/^[a-z A-Z 0-9!@#$^&*\.,\-'"\{\}\[\]=_\+\(\)\?<>]+$/).test(this.Trim());
}

//只能 是英文 
String.prototype.isEnglish=function(){
	  return (/^([A-Z a-z!@#$^&*\.,\-'"\{\}\[\]=_\+\(\)\?<>])+$/).test(this.Trim());
}
//只能是中文 、英文 
String.prototype.isNotNum=function(){
	return (/^[a-z A-Z\u4e00-\u9fa5]+$/).test(this.Trim());
}
//全部都是数字
String.prototype.isNum=function(){
	return (/^\d+(\.{1}\d+)?$/).test(this.Trim());
}

String.prototype.isAddUrl=function(){
	return (/^https?:\/\/(([a-zA-Z0-9_-])+(\.)?)*(:\d+)?(\/((\.)?(\?)?=?&?[a-zA-Z0-9_-](\?)?)*)*$/).test(this.Trim());
}


function checkBusinessShopStep4() {
	disableSubmit("step4Submit");
	var contactName=$("#contactName").val();
	var contactAddress=$("#contactAddress").val();
	var shopManagerName=$("#shopManagerName").val();
	var emergencyContactName=$("#emergencyContactName").val();
	
	/*if($.trim(contactName) == ""){
		$("#contactName").focus();
		$("#contactNametip").html('<font color="red">请填写联系人姓名!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		if($("#contactName").val().length>20){
			$("#contactName").focus();
			$("#contactNametip").html('<font color="red">联系人姓名不能超过20个字符</font>');
			ifError();
			enableSubmit("step4Submit");
			return false;
		}
		if(!$("#contactName").val().isNotNum()) {
			$("#contactName").focus();
			$("#contactNametip").html('<font color="red">请输入正确的联系人姓名[只能输入中文字符、英文字符!]</font>');
			ifError();
			enableSubmit("step4Submit");
			return false;
	     }else{
			$("#contactNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		}
	}*/
	
	/*if (!($("#contactPhone").val().isMobile() || $("#contactPhone").val().isTel())) {
		$("#contactPhone").focus();
		$("#contactPhonetip").html('<font color="red">请填写正确的联系人电话!</font>');
		return false;
	}else{
		$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}*/
	/*if ($.trim($("#contactPhone").val()) == "") {
		$("#contactPhone").focus();
		$("#contactPhonetip").html('<font color="red">请填写联系人电话!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	
	if($.trim(contactAddress) == ""){
		$("#contactAddress").focus();
		$("#contactAddresstip").html('<font color="red">联系人地址不能为空!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		if($("#contactAddress").val().length>50){
			$("#contactName").focus();
			$("#contactAddresstip").html('<font color="red">联系人姓名不能超过50个字符</font>');
			ifError();
			enableSubmit("step4Submit");
			return false;
		}else{
			$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		}
	}
	
	if (!$("#contactPostalCode").val().isPostal()) {
		$("#contactPostalCode").focus();
		$("#contactPostalCodetip").html('<font color="red">请填写正确的邮编!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		$("#contactPostalCodetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}*/
	
	if($.trim(shopManagerName) == ""){
		$("#shopManagerName").focus();
		$("#shopManagerNametip").html('<font color="red">请填写店铺联系人姓名!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		if($("#shopManagerName").val().length>20){
			$("#shopManagerName").focus();
			$("#shopManagerNametip").html('<font color="red">店铺联系人姓名不能超过20个字符</font>');
			ifError();
			enableSubmit("step4Submit");
			return false;
		}
		if(!$("#shopManagerName").val().isNotNum()) {
			$("#shopManagerName").focus();
			$("#shopManagerNametip").html('<font color="red">请输入正确的店铺联系人姓名[只能输入中文字符、英文字符!]</font>');
			ifError();
			enableSubmit("step4Submit");
			return false;
	     }else{
			$("#shopManagerNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		}
	}
	
	if ($.trim($("#shopManagerTelephone").val()) == "") {
		$("#shopManagerTelephone").focus();
		$("#shopManagerTelephonetip").html('<font color="red">请填写电话号码!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		$("#shopManagerTelephonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if ($.trim($("#shopManagerMobile").val()) == "") {
		$("#shopManagerMobile").focus();
		$("#shopManagerMobiletip").html('<font color="red">请填写手机号码!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		$("#shopManagerMobiletip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if (!$("#shopManagerEmail").val().isEmail()) {
		$("#shopManagerEmail").focus();
		$("#shopManagerEmailtip").html('<font color="red">请填写正确的邮箱!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		$("#shopManagerEmailtip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	/*if ($("#shopManagerFax").val()!="" && !$("#shopManagerFax").val().isTel()) {
		$("#shopManagerFax").focus();
		$("#shopManagerFaxtip").html('<font color="red">请填写正确的传真号码!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}*/
	/*if($.trim(emergencyContactName) == ""){
		$("#emergencyContactName").focus();
		$("#emergencyContactNametip").html('<font color="red">请填写紧急联系人姓名!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		if($("#emergencyContactName").val().length>20){
			$("#emergencyContactName").focus();
			$("#emergencyContactNametip").html('<font color="red">紧急联系人姓名不能超过20个字符</font>');
			ifError();
			enableSubmit("step4Submit");
			return false;
		}
		if(!$("#emergencyContactName").val().isNotNum()) {
			$("#emergencyContactName").focus();
			$("#emergencyContactNametip").html('<font color="red">请输入正确的紧急联系人姓名[只能输入中文字符、英文字符!]</font>');
			ifError();
			enableSubmit("step4Submit");
			return false;
	     }else{
			$("#emergencyContactNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		}
	}
	
	if ($.trim($("#emergencyContactPhone").val()) == "") {
		//alert($("#emergencyContactPhone").val());
		$("#emergencyContactPhone").focus();
		$("#emergencyContactPhonetip").html('<font color="red">请填写手机号码!</font>');
		ifError();
		enableSubmit("step4Submit");
		return false;
	}else{
		$("#emergencyContactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}*/
	$("#step4Submit").attr("disabled","disabled");
	return true;
}

function disableSubmit(id){
	$("#"+id).attr("disabled","disabled");
	
	//alert($("#"+id).attr("disabled"));
}

function enableSubmit(id){
	//$("#"+id).attr("enable","enable");
	//$("#"+id).attr("disabled","");
	$("#"+id).removeAttr("disabled");
	//alert($("#"+id).attr("disabled"));
}

function checkShopCInfo() {
	disableSubmit("shopCSubmit");
	var shopName = $("#shopName").val();
	var description = $("#descriptionC").val();
	var prov = $("#shopCustomerInfoDOprovince").val();
	var cate=$("#shopCategory").val();
    var isHaveOuterShop=$(":radio[id='isHaveOuterShop']:checked").val();
	
	if ($.trim(shopName) == "") {
		$("#shopName").focus();
		$("#shopNametip").html('<font color="red">请填写店铺名称!</font>');
		ifError();
		enableSubmit("shopCSubmit");
		return false;
	}else{
		if (shopName.length>20) {
		   $("#shopName").focus();
		   $("#shopNametip").html('<font color="red">店铺名称不能超过20个字符</font>');
		   ifError();
		   enableSubmit("shopCSubmit");
		   return false;
		 }
	}
	if ($.trim(cate) == "") {
		$("#shopCategorytip").html('<font color="red">选择店铺类目!</font>');
		ifError();
		enableSubmit("shopCSubmit");
		return false;
	}
//	if ($.trim(description) == "") {
//		$("#descriptionC").focus();
//		$("#descriptionCtip").html('<font color="red">请填写店铺介绍!</font>');
//		ifError();
//		enableSubmit("shopCSubmit");
//		return false;
//	}
	if (description.length>500) {
		$("#description").focus();
		$("#descriptionCtip").html('<font color="red">店铺介绍过长，只能在500字以内!</font>');
		ifError();
		enableSubmit("shopCSubmit");
		return false;
	}
	if ($.trim(prov) == "") {
		$("#shopCustomerInfoDOprovincetip").html('<font color="red">选择企业所在地!</font>');
		ifError();
		enableSubmit("shopCSubmit");
		return false;
	}
	
	if(isHaveOuterShop==0){
		var shopAddUrl=$("#shopAddressUrl").val();
		var shopLevel=$(":radio[id='shopLevel']:checked").val();
		var saleScope=$(":radio[id='saleScope']:checked").val();
		var isEnterB2c=$(":radio[id='isEnterB2c']:checked").val();
		if ($.trim(shopAddUrl) == "") {
			//$("#shopAddressUrl").focus();
			 $("#shopAddressUrltip").html('<font color="red">请输入网上店铺地址(Url)!</font>');
			 ifError();
			 enableSubmit("shopCSubmit");
			 return false;
		}else{
			if(!shopAddUrl.isAddUrl()){
				//$("#shopAddressUrl").focus();
				$("#shopAddressUrltip").html('<font color="red">请输入正确的网上店铺地址(Url)!</font>');
				ifError();
				enableSubmit("shopCSubmit");
				return false;
			}else{
				//$("#shopAddressUrl").focus();
				$("#shopAddressUrltip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
	    }
		
//		if ($.trim(shopLevel) == "" || shopLevel=="undefined") {
//			$("#shopLeveltip").html('<font color="red">请选择店铺等级!</font>');
//			ifError();
//			enableSubmit("shopCSubmit");
//			return false;
//		}
//		if ($.trim(saleScope) == "" || saleScope=="undefined") {
//			$("#saleScopetip").html('<font color="red">请选择年销售规模!</font>');
//			ifError();
//			enableSubmit("shopCSubmit");
//			return false;
//		}
//		if ($.trim(isEnterB2c) == "" || isEnterB2c=="undefined") {
//			$("#isEnterB2ctip").html('<font color="red">请选择是否入住过B2C!</font>');
//			ifError();
//			enableSubmit("shopCSubmit");
//			return false;
//		}
	}
	for(var i=2;i<5;i++){
		var myfile=$("#myFile"+i).val();
		if($("#myFile"+i).val() == ""){
			$("#myFile"+i).focus();
			$("#myFile"+i+"tip").html('<font color="red">'+alertStrC[i]+'!</font>');
			ifError();
			enableSubmit("shopCSubmit");
			return false;
		}else{
				var fileIndex=myfile.lastIndexOf('.');
				if(fileIndex==-1){
					$("#myFile"+i).focus();
					$("#myFile"+i+"tip").html('<font color="red">请上传正确图片格式!</font>');
					ifError();
					enableSubmit("shopCSubmit");
					return false;
				}else{
					var str=myfile.substring(fileIndex+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						$("#myFile"+i).focus();
						$("#myFile"+i+"tip").html('<font color="red">请上传正确图片格式!</font>');
						ifError();
						enableSubmit("shopCSubmit");
						return false;
					}else{
						$("#myFile"+i+"tip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					}
				}
		}
	}
	return true;
}

function checkBusinessShopStep1() {
	disableSubmit("step1Submit");
	var shopName = $("#shopNameB").val();
	var description = $("#descriptionB").val();
	
	var cate=$("#shopCategory").val();
	if ($.trim(shopName) == "") {
		$("#shopNameB").focus();
		$("#shopNameBtip").html('<font color="red">请填写店铺名称!</font>');
		ifError();
		enableSubmit("step1Submit");
		return false;
	}
	if ($.trim(cate) == "") {
		$("#shopCategorytip").html('<font color="red">选择店铺类目!</font>');
		ifError();
		enableSubmit("step1Submit");
		return false;
	}else{
		$("#shopCategorytip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	
	if ($.trim(description) == "") {
		/*$("#descriptionB").focus();
		$("#descriptionBtip").html('<font color="red">请填写店铺介绍!</font>');
		ifError();
		enableSubmit("step1Submit");
		return false;*/
	}else{
		if(description.length>500){
			$("#descriptionB").focus();
		    $("#descriptionBtip").html('<font color="red">店铺介绍不能超过500个字符!</font>');
		    ifError();
		    enableSubmit("step1Submit");
		    return false;
		}
		
	}
    if($("#myFileInfoB").val() == ""){
			/*$("#myFileInfoB").focus();
			$("#myFileInfoBtip").html('<font color="red">请选择店标!</font>');
			ifError();
			enableSubmit("step1Submit");
			return false;*/
	}else{
		   var i=$("#myFileInfoB").val().lastIndexOf('.');
				if(i==-1){
					$("#myFileInfoB").focus();
					$("#myFileInfoBtip").html('<font color="red">请上传正确店标图片格式!</font>');
					ifError();
					enableSubmit("step1Submit");
					return false;
				}else{
					var str=$("#myFileInfoB").val().substring(i+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						$("#myFileInfoB").focus();
						$("#myFileInfoBtip").html('<font color="red">请上传正确店标图片格式!</font>');
						ifError();
						enableSubmit("step1Submit");
						return false;
					}else{
						$("#myFileInfoBtip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					}
				}
	}

	$("#step1Submit").attr("disabled","disabled");
	return true;

}

function checkBusinessShopStep2() {
	disableSubmit("step2Submit");
	var enterpriseName = $("#enterpriseName").val();
	var businessLicenseNumber = $("#businessLicenseNumber").val();
	var organizationCodeNumber = $("#organizationCodeNumber").val();
	var business = $("#business").val();
	var businessLicenseEndDate = $("#businessLicenseEndDate").val();
	var legalName = $("#legalName").val();
	var registAddress = $("#registAddress").val();
	var isHaveOuterShop=$(":radio[id='isHaveOuterShop']:checked").val();
	var prov = $("#shopBusinessInfoDOprovince").val();
	
	if ($.trim(enterpriseName) == "") {
		$("#enterpriseName").focus();
		$("#enterpriseNametip").html('<font color="red">请填写企业名称!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}else{
		if (enterpriseName.length>30) {
			$("#enterpriseName").focus();
			$("#enterpriseNametip").html('<font color="red">企业名称不能超过30个字!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}
		$("#enterpriseNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if ($.trim(businessLicenseNumber)=="") {
		$("#businessLicenseNumber").focus();
		$("#businessLicenseNumbertip").html('<font color="red">请填写工商营业执照注册号!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}else{
		if (businessLicenseNumber.length>18) {
			$("#businessLicenseNumber").focus();
			$("#businessLicenseNumbertip").html('<font color="red">工商营业执照注册号不能超过18位!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		 }else if(!businessLicenseNumber.isNum()){
		 	$("#businessLicenseNumber").focus();
			$("#businessLicenseNumbertip").html('<font color="red">工商营业执照注册只能为数字!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}
		$("#businessLicenseNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}

	if ($.trim(organizationCodeNumber) == "") {
		$("#organizationCodeNumber").focus();
		$("#organizationCodeNumbertip").html('<font color="red">请填写组织机构代码证号!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}else{
			if (organizationCodeNumber.length>10) {
				$("#organizationCodeNumber").focus();
				$("#organizationCodeNumbertip").html('<font color="red">请输10位的数字、字母和"-"组成的代码证号!</font>');
				ifError();
				enableSubmit("step2Submit");
				return false;
			}else if(!organizationCodeNumber.isSN3()){
				$("#organizationCodeNumber").focus();
				$("#organizationCodeNumbertip").html('<font color="red">请输10位的数字、字母和"-"组成的代码证号!</font>');
				ifError();
				enableSubmit("step2Submit");
				return false;
			}
		$("#organizationCodeNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}

	if ($.trim(business) == "") {
		$("#business").focus();
		$("#businesstip").html('<font color="red">请填写经营范围!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}else{
		if (business.length>50) {
			$("#business").focus();
			$("#businesstip").html('<font color="red">经营范围不能超过50个字符!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}
		$("#businesshui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}

	if ($.trim(businessLicenseEndDate) == "") {
		$("#businessLicenseEndDate").focus();
		$("#businessLicenseEndDatetip").html('<font color="red">请填写营业执照有效期限!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}else{
			var arr = $("#businessLicenseEndDate").val().split('/');
			var myDate=new   Date();
			var today = new   Date();
			myDate.setFullYear(arr[2],arr[1]-1,arr[0]);
		   	if(myDate <= today){
		   		$("#businessLicenseEndDate").focus();
				$("#businessLicenseEndDatetip").html('<font color="red">选择日期必须大于当前日期！</font>');
				ifError();
				enableSubmit("step2Submit");
				return false;
			}
		   	else if (!businessLicenseEndDate.isDate()) {
			    $("#businessLicenseEndDate").focus();
				$("#businessLicenseEndDatetip").html('<font color="red">请填写正确的日期!</font>');
				ifError();
				enableSubmit("step2Submit");
			    return false;
			}
			else{
				$("#businessLicenseEndDatetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
      }
	if ($.trim(legalName) == "") {
		$("#legalName").focus();
		$("#legalNametip").html('<font color="red">请填写法人代表姓名!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}else{
		if (legalName.length>20) {
			$("#legalName").focus();
			$("#legalNametip").html('<font color="red">法人代表姓名不能超过20个字!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}else if(!legalName.isNotNum()){
			$("#legalName").focus();
			$("#legalNametip").html('<font color="red">法人代表姓名只能是汉字或英文字符 </font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}
			$("#legalNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if ($.trim(registAddress) == "") {
		$("#registAddress").focus();
		$("#registAddresstip").html('<font color="red">请填写企业注册地址!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}else{
		if (registAddress.length>50) {
			$("#registAddress").focus();
		    $("#registAddresstip").html('<font color="red">企业注册地址不能超过50个字符!</font>');
		    ifError();
		    enableSubmit("step2Submit");
		    return false;
		}
	}
	if ($.trim(prov) == "") {
		$("#shopBusinessInfoDOprovincetip").html('<font color="red">选择企业所在地!</font>');
		ifError();
		enableSubmit("step2Submit");
		return false;
	}
   if(isHaveOuterShop==0){	
   	    var shopAddUrl = $("#shopAddressUrl").val();
		var shopLevel=$(":radio[id='shopLevel']:checked").val();
		var saleScope=$(":radio[id='saleScope']:checked").val();
		var isEnterB2c=$(":radio[id='isEnterB2c']:checked").val();
		if ($.trim(shopAddUrl) == "") {
			//$("#shopAddressUrl").focus();
			 $("#shopAddressUrltip").html('<font color="red">请输入网上店铺地址(Url)!</font>');
			 ifError();
			 enableSubmit("step2Submit");
			 return false;
		}else{
			if(!shopAddUrl.isAddUrl()){
				//$("#shopAddressUrl").focus();
				$("#shopAddressUrltip").html('<font color="red">请输入正确的网上店铺地址(Url)!</font>');
				ifError();
				enableSubmit("step2Submit");
				return false;
			}else{
				//$("#shopAddressUrl").focus();
				$("#shopAddressUrltip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
	  }
	  /*if ($.trim(shopLevel) == "" || shopLevel=='undefined') {
			$("#shopLeveltip").html('<font color="red">选择店铺等级!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}
		if ($.trim(saleScope) == "" || saleScope=='undefined') {
			$("#saleScopetip").html('<font color="red">选择年销售规模!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}
		if ($.trim(isEnterB2c) == "" || isEnterB2c=='undefined') {
			$("#isEnterB2ctip").html('<font color="red">选择是否入驻过B2C!</font>');
			ifError();
			enableSubmit("step2Submit");
			return false;
		}*/
   }
	//for(var i=1;i<8;i++){
	for(var i=1;i<4;i++){
		//if(i == 4){
		//	continue;
		//}
		var myfile=$("#myFileB"+i).val();
		if($("#myFileB"+i).val() == ""){
			$("#myFileB"+i).focus();
			$("#myFileB"+i+"hui").html('<font color="red">'+alertStr[i]+'!</font>');
			enableSubmit("step2Submit");
			return false;
		}else{
				var fileIndex=myfile.lastIndexOf('.');
				if(fileIndex==-1){
					$("#myFileB"+i).focus();
					$("#myFileB"+i+"hui").html('<font color="red">请上传正确图片格式!</font>');
					enableSubmit("step2Submit");
					return false;
				}else{
					var str=myfile.substring(fileIndex+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						$("#myFileB"+i).focus();
						$("#myFileB"+i+"hui").html('<font color="red">请上传正确图片格式!</font>');
						enableSubmit("step2Submit");
						return false;
					}else{
						$("#myFileB"+i+"hui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					}
				}
		}
	}
	$("#step2Submit").attr("disabled", "disabled");
	return true;
}
var alertStr = ['','请上传企业营业执照','请上传组织机构代码证','请上传税务登记证'];
//var alertStr = ['','请上传企业营业执照','请上传组织机构代码证','请上传品牌授权书或品牌商标注册证书','请上传商品质量检验证书','请上传税务登记证','请上传生产许可证','请上传经营许可证'];
var alertStrC = ['','请上传店标','请上传企业营业执照','请上传组织机构代码证','请上传税务登记证'];


     function checkRename(id,error){
     	setSubmitErrorEmpty();
    	 if ($.trim($(id).val()) == "" || $.trim($(id).val()).length==0) {
	    	   //$(id).focus();
			   $(error).html('<font color="red">请填写店铺名称</font>');
			   return false;
		 } 
		 if ($.trim($(id).val()).length>20) {
			  // $(id).focus();
			   $(error).html('<font color="red">店铺名称不能超过20个字符</font>');
			   return false;
		 }else{
		 	$.ajax({
					// 后台处理程序
					url : "shop/queryAjaxShopAction.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "json",
					// 要传递的数据
					data : {
						shopName:$.trim($(id).val())
					},
					// 回传函数
					success : function callBack(result) {
						var json = eval("(" + result + ")");
						if(json.isRename==1){
							// $(id).focus();
							 $(error).html(json.message);
							 return false;
						}else{
							 $(error).html(json.message);
						}
						
					}

				});
		 }
	}
			
function spCheck(id,error){	
	setSubmitErrorEmpty();
	var spValue=$("#"+id).val();
	if(id == 'shopAddressUrl'){
		if (spValue == "") {
			//$("#shopAddressUrl").focus();
			$(error).html('<font color="red">请输入网上店铺地址(Url)!</font>');
			return false;
		}else{
			if(!spValue.isAddUrl()){
				//$("#shopAddressUrl").focus();
				$(error).html('<font color="red">请输入正确的网上店铺地址(Url)!</font>');
				return false;
			}else{
				//$("#shopAddressUrl").focus();
				$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				return true;
			}
			
		}
	}
	if(id == 'shopLevel'){
		if ($(":radio[id='shopLevel']:checked").val() == "" || $(":radio[id='shopLevel']:checked").val()=="undefined") {
			//$("#shopLevel").focus();
			$(error).html('<font color="red">选择选择店铺等级!</font>');
			return false;
		}else{
		    //$("#shopLevel").focus();
		     $(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id == 'saleScope'){
		if ($(":radio[id='saleScope']:checked").val() == "" || $(":radio[id='saleScope']:checked").val()=="undefined") {
			//$("#saleScope").focus();
			$(error).html('<font color="red">请选择年销售规模(Url)!</font>');
			return false;
		}else{
		    //$("#saleScope").focus();
		     $(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id == 'isEnterB2c'){
		if ($(":radio[id='isEnterB2c']:checked").val() == "" || $(":radio[id='isEnterB2c']:checked").val()=="undefined") {
			//$("#isEnterB2c").focus();
			$(error).html('<font color="red">请选择是否入住过B2C</font>');
			return false;
		}else{
		    //$("#isEnterB2c").focus();
		     $(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id == 'shopBusinessInfoDOprovince'){
		if (spValue == "") {
			//$("#shopBusinessInfoDOprovince").focus();
			$(error).html('<font color="red">选择企业所在地!</font>');
			return false;
		}else{
			//$("#shopBusinessInfoDOprovince").focus();
			$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id == 'shopCustomerInfoDOprovince'){
		if (spValue == "") {
			//$("#shopBusinessInfoDOprovince").focus();
			$(error).html('<font color="red">选择企业所在地!</font>');
			return false;
		}else{
			$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	
	if(id == 'shopCategory'){
		if (spValue == "") {
			//$("#shopCategory").focus();
			$(error).html('<font color="red">选择店铺类目!</font>');
			return false;
		}else{
			$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id == 'myFile1'){
		if (spValue == "") {
			//$("#myFile1").focus();
			$(error).html('<font color="red">选择店标!</font>');
			return false;
		}else{
			var i=spValue.lastIndexOf('.');
				if(i==-1){
					//$("#myFile1").focus();
					$(error).html('<font color="red">请上传正确店标图片格式!</font>');
					return false;
				}else{
					var str=spValue.substring(i+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						//$("#myFile1").focus();
						$(error).html('<font color="red">请上传正确店标图片格式!</font>');
						return false;
					}else{
						$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
						return true;
					}
				}
		}
	}
	if(id=='isHaveOuterShop'){
		var isHaveOuterShop=$(":radio[id='isHaveOuterShop']:checked").val();
		if(isHaveOuterShop==0){
			 $("#hasOtherShop").css({display:"block"});
			 return true;
		}else{
			 $("#hasOtherShop").css({display:"none"});
			 return true;
		}
	}
	if(id == 'myFileInfoB'){
		
		if (spValue == "") {
			//$("#myFileInfoB").focus();
			$(error).html('<font color="red">选择店标!</font>');
			return false;
		}else{
			var i=spValue.lastIndexOf('.');
				if(i==-1){
					//$("#myFileInfoB").focus();
					$(error).html('<font color="red">请上传正确店标图片格式!</font>');
					return false;
				}else{
					var str=spValue.substring(i+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						//$("#myFileInfoB").focus();
						$(error).html('<font color="red">请上传正确店标图片格式!</font>');
						return false;
					}else{
						$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
						return true;
					}
				}
		}
	}
	if(id == 'myFile2'){
		if (spValue == "") {
			//$("#myFile2").focus();
			$(error).html('<font color="red">选择企业营业执照!</font>');
			return false;
		}else{
			var i=spValue.lastIndexOf('.');
				if(i==-1){
					//$("#myFile2").focus();
					$(error).html('<font color="red">请上传正确企业营业执照图片格式!</font>');
					return false;
				}else{
					var str=spValue.substring(i+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						//$("#myFile2").focus();
						$(error).html('<font color="red">请上传正确企业营业执照图片格式!</font>');
						return false;
					}else{
						$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
						return true;
					}
				}
		}
	}
	if(id == 'myFile3'){
		if (spValue == "") {
			//$("#myFile3").focus();
			$(error).html('<font color="red">选择组织机构代码证 !</font>');
			return false;
		}else{
				var i=spValue.lastIndexOf('.');
				if(i==-1){
					//$("#myFile3").focus();
					$(error).html('<font color="red">请上传正确企业营业执照图片格式!</font>');
					return false;
				}else{
					var str=spValue.substring(i+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						//$("#myFile3").focus();
						$(error).html('<font color="red">请上传正确企业营业执照图片格式!</font>');
						return false;
					}else{
						$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
						return true;
					}
				}
		}
	}
	
   if(id == 'businessLicenseEndDate'){
	   if ($.trim(spValue)=="") {
	   	   // $("#businessLicenseEndDate").focus();
			$(error).html('<font color="red">请选择日期!</font>');
			return false;
	   }else{
	   	   if(!spValue.isDate()){
	   	   	  //$("#businessLicenseEndDate").focus();
				$(error).html('<font color="red">请输入正确的日期格式!</font>');
				return false;
	   	   }else{
				var arr = $('#'+id).val().split('-');
				var myDate=new   Date();
				var today = new   Date();
				myDate.setFullYear(arr[0],arr[1]-1,arr[2]);
			   	if(myDate <= today){
			   		//$("#businessLicenseEndDate").focus();
					$(error).html('<font color="red">选择日期必须大于当前日期！</font>');
					return false;
				}else{
					$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					return true;
				}
	   	   }
       }
	}
	if(id=='businessLicenseNumber'){
	   if ((!spValue.isNum()) || (spValue.length>18) ) {
	   	    //$("#businessLicenseNumber").focus();
			$(error).html('<font color="red">请输入小于18位的数字!</font>');
			return false;
	   }else{
			$("#businessLicenseNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id=='organizationCodeNumber'){
		if ((!$("#organizationCodeNumber").val().isSN3()) || ($("#organizationCodeNumber").val().length>10) ) {
			 //$("#organizationCodeNumber").focus();
			$("#organizationCodeNumbertip").html('<font color="red">请输10位的数字、字母和"-"组成的代码证号!</font>');
			return false;
		}else{
			$("#organizationCodeNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id=='contactName'){
		if(!$("#contactName").val().isNotNum()) {
			//$("#contactName").focus();
			$("#contactNametip").html('<font color="red">请输入正确的联系人姓名[只能输入中文字符、英文字符!]</font>');
			return false;
	     }else{
			$("#contactNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	
	if(id=='shopManagerName'){
		if(!$("#shopManagerName").val().isNotNum()) {
			//$("#contactName").focus();
			$("#shopManagerNametip").html('<font color="red">请输入正确的店铺负责人姓名[只能输入中文字符、英文字符!]</font>');
			return false;
	     }else{
			$("#shopManagerNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id=='emergencyContactName'){
		if(!$("#emergencyContactName").val().isNotNum()) {
			//$("#contactName").focus();
			$("#emergencyContactNametip").html('<font color="red">请输入正确的紧急联系人姓名[只能输入中文字符、英文字符!]</font>');
			return false;
	     }else{
			$("#emergencyContactNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id=='legalName'){
		if(!$("#legalName").val().isNotNum()) {
			//$("#contactName").focus();
			$("#legalNametip").html('<font color="red">请输入正确的法人代表姓名[只能输入中文字符、英文字符!]</font>');
			return false;
	     }else{
			$("#legalNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id=='contactPhone'){
		/*if (!($("#contactPhone").val().isMobile() || $("#contactPhone").val().isTel()) ) {
			//$("#contactPhone").focus();
			$("#contactPhonetip").html('<font color="red">请填写正确的联系人电话!</font>');
			return false;
	     }else{
			$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}*/
		if ($.trim($("#contactPhone").val()) == "") {
			//$("#contactPhone").focus();
			$("#contactPhonetip").html('<font color="red">请填写联系人电话!</font>');
			return false;
	     }else{
			$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='contactPostalCode'){
		if (!$("#contactPostalCode").val().isPostal()) {
			//$("#contactPostalCode").focus();
			$("#contactPostalCodetip").html('<font color="red">请填写正确的邮编!</font>');
			return false;
		}else{
			$("#contactPostalCodetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='shopManagerTelephone'){
		if ($.trim($("#shopManagerTelephone").val()) == "") {
			//$("#shopManagerTelephone").focus();
			$("#shopManagerTelephonetip").html('<font color="red">请填写电话号码!</font>');
			return false;
		}else{
			$("#shopManagerTelephonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='shopManagerMobile'){
		if ($.trim($("#shopManagerMobile").val()) == "") {
			//$("#shopManagerMobile").focus();
			$("#shopManagerMobiletip").html('<font color="red">请填写手机号码!</font>');
			return false;
		 }else{
			$("#shopManagerMobiletip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='shopManagerEmail'){
		if (!$("#shopManagerEmail").val().isEmail()) {
			//$("#shopManagerEmail").focus();
		   $("#shopManagerEmailtip").html('<font color="red">请填写正确的邮箱!</font>');
		   return false;
	    }else{
			$("#shopManagerEmailtip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;

		}
	}
	/*if(id=='shopManagerFax'){
		if($("#shopManagerFax").val()!=""){
		  	if (!$("#shopManagerFax").val().isTel()) {
		  	   // $("#shopManagerFax").focus();
				$("#shopManagerFaxtip").html('<font color="red">请填写正确的传真号码!</font>');
				return false;
		 	}else{
				$("#shopManagerFaxtip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				return true;
			}	
		}
	}*/
	if(id=='emergencyContactPhone'){
	   if ($.trim($("#emergencyContactPhone").val()) == "") {
	   	   // $("#emergencyContactPhone").focus();
			$("#emergencyContactPhonetip").html('<font color="red">请填写手机号码!</font>');
			return false;
		}else{
			$("#emergencyContactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}	
	}
	
	
	var myfileName=id.substring(0,7);
	if(myfileName=="myFileB")
	{
		var count=id.substring(7);
	    var myfile=$("#myFileB"+count).val();
		if($("#myFileB"+count).val() == ""){
			// $("#myFileB"+count).focus();
			$("#myFileB"+count+"hui").html('<font color="red">'+alertStr[count]+'!</font>');
			return false;
		}else{
				var fileIndex=myfile.lastIndexOf('.');
				if(fileIndex==-1){
					//$("#myFileB"+count).focus();
					$("#myFileB"+count+"hui").html('<font color="red">请上传正确图片格式!</font>');
					return false;
				}else{
					var str=myfile.substring(fileIndex+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						//$("#myFileB"+count).focus();
						$("#myFileB"+count+"hui").html('<font color="red">请上传正确图片格式!</font>');
						return false;
					}else{
						$("#myFileB"+count+"hui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					}
				}
		}
	}
	return true;
}
			
			
			
			
			
			    
function checkOnblur(id,error){
   checkLength(id,error);
}

function checkLength(id,error){
	setSubmitErrorEmpty();
	for(var i=0;i<contentNotNull.length;i++){
		
	   var arr=new Array();
	   arr=contentNotNull[i];
	   if(arr[0]==id){
		  if($.trim($(id).val())=="" && id != '#descriptionB' && id != '#descriptionC'){
		  	// $(id).focus();
			 $(error).html('<font color="red">'+arr[1]+'</font>');
			 return false;
		  }else{
		     var lenArr=new Array();
		     lenArr=contentOutLen[i];
		     var lenMessage=new Array();
		     lenMessage=lenArr[1].split(",");
		     if($.trim($(id).val()).length>lenMessage[0]){
		     	// $(id).focus();
		         $(error).html('<font color="red">'+lenMessage[1]+'</font>');
		         return false;
		      }else{
		    	  $(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		    	  return true;
		      }
		  }
	  }
   }
} 

function ifError(){
	$("#submitError").text("您提交的信息有误，请查看页面上的错误提示！");
}
var contentNotNull=[['#descriptionC','店铺介绍不能为空'],['#descriptionB','店铺介绍不能为空'],
	['#contactName','联系人姓名不能为空'],['#contactAddress','联系人地址不能为空'],
	['#shopManagerName','店铺负责人姓名不能为空'],['#emergencyContactName','紧急联系人姓名不能为空'],['#enterpriseName','企业名称不能为空'],
	['#business','经营范围不能为空'],['#legalName','法人代表姓名不能为空'],['#registAddress','企业注册地址不能为空']];
var contentOutLen=[['#descriptionC','500,店铺介绍不能超过500个字符'],['#descriptionB','500,店铺介绍不能超过500个字符'],
	['#contactName','20,联系人姓名不能超过20个字符'],['#contactAddress','50,联系人地址不能超过50个字符'],
	['#shopManagerName','20,店铺负责人姓名不能超过20个字符'],['#emergencyContactName','20,紧急联系人姓名不能超过20个字符'],['#enterpriseName','30,企业名称不能超过30个字符'],
	['#business','50,经营范围不能超过50个字符'],['#legalName','20,法人代表姓名不能超过20个字符'],['#registAddress','50,企业注册地址不能超过50个字符']];
	
/*--------------------图片验证-----------------------*/	
function initImageValidate(name,id, max, tip, seq){
	$("#"+id).change(function(){
		$.showFullLoading({description : '图片正在校验中，请稍候...'
		});
		
		var picname=$("#"+id).val();
		var form = $('#shopInfoForm'),
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
			if (data) {
				$.hideFullLoading();
				
				var imagFlag = data.imagFlag;
				if (imagFlag == 1) {
					alert("缺少商品图片");
				}else if (imagFlag == 2){
					alert("错误的格式");
				}else if (imagFlag == 3){
					alert("图片大小不能超过"+max+"K。");
				}
				if (imagFlag > 0) {
					setValEmpty("#"+id);
					//$("#"+id).val("");
					$("#"+tip).html('');
				} else{
					$("#"+tip).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					//form.removeAttr('action');
					//form.removeAttr('target');
					//form.attr('action',actionStr[seq]);
					setSubmitErrorEmpty();
				}
				form.removeAttr('action');
				form.removeAttr('target');
				form.attr('action',actionStr[seq]);
				
			}
			//form.removeAttr('method');
			//form.removeAttr('action');
			//form.removeAttr('target');
		});
		form.attr({
			method: 'post',
			action : 'validateUploadImage.htm?max='+max+'&picName='+picname+"&id="+name,
			target : 'uploadIframe',
			enctype : "multipart/form-data"
		});
		form[0].submit();
	});
}
//saveCustomerBaseInfoAction
//saveCustomerUploadInfoAction
//saveBusinessBaseInfoAction
//saveBusinessUploadInfoAction
//saveIShopBaseInfoAction
//saveIShopUploadInfoAction
var actionStr = ['${base}/shop/saveCustomerBaseInfoAction.htm',
				'${base}/shop/saveBusinessBaseInfoAction.htm',
				'${base}/shop/saveIShopBaseInfoAction.htm',
				'${base}/shop/saveIShopUploadInfoAction.htm',
				'${base}/shop/saveCustomerUploadInfoAction.htm',
				'${base}/shop/saveBusinessUploadInfoAction.htm',
				'${base}/shop/saveBusinessBrandInfoAction.htm?brandSaveType=0'
				];
				
function setValEmpty(fileId){
	var file = $(fileId);
	//alert(fileId+"    "+file);
	
	var tempForm = document.createElement('form');  
	file.before(tempForm);  
	$(tempForm).append(file);  
	tempForm.reset();  
	$(tempForm).after(file);  
	$(tempForm).remove(); 
}

function setSubmitErrorEmpty(){
	$("#submitError").html("");
}
//-------------------------------------------------------------------------------------------------------------------------//
//提交时验证C基本信息 2.0 新
function checkShopBaseInfo(submitStr,sellerType) {
	var error1 = false;
	var error2 = false;
	var error3 = false;
	disableSubmit(submitStr);
	//店铺信息
	var shopName = $("#shopName").val();
	var description = $("#descriptionC").val();
	var prov = "";
	if(sellerType == 0){
		prov = $("#shopCustomerInfoDOprovince").val();
	}else if(sellerType == 1){
		prov = $("#shopBusinessInfoDOprovince").val();
	}
	
	var cate=$("#shopCategory").val();
  var isHaveOuterShop=$(":radio[id='isHaveOuterShop']:checked").val();
  //企业信息
  var enterpriseName = $("#enterpriseName").val();
	var businessLicenseNumber = $("#businessLicenseNumber").val();
	var organizationCodeNumber = $("#organizationCodeNumber").val();
	var business = $("#business").val();
	var businessLicenseEndDate = $("#businessLicenseEndDate").val();
	var legalName = $("#legalName").val();
	var registAddress = $("#registAddress").val();
	//联系人信息
	var contactAddress=$("#contactAddress").val();
	var shopManagerName=$("#shopManagerName").val();
	
	//店铺信息
	if ($.trim(shopName) == "") {
		$("#shopName").focus();
		$("#shopNametip").html('<font color="red">请填写店铺名称!</font>');
		error1 = true;
		showAccordion2(0);
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if (shopName.length>20) {
		   $("#shopName").focus();
		   $("#shopNametip").html('<font color="red">店铺名称不能超过20个字符</font>');
		   error1 = true;
		   showAccordion2(0);
		   ifError();
		   enableSubmit(submitStr);
		   return false;
		 }
	}
	if ($.trim(cate) == "") {
		$("#shopCategorytip").html('<font color="red">选择店铺类目!</font>');
		error1 = true;
		showAccordion2(0);
		ifError();
		enableSubmit(submitStr);
		return false;
	}
	if (description.length>500) {
		$("#description").focus();
		$("#descriptionCtip").html('<font color="red">店铺介绍过长，只能在500字以内!</font>');
		error1 = true;
		showAccordion2(0);
		ifError();
		enableSubmit(submitStr);
		return false;
	}
	if(sellerType == 1 || sellerType == 0){
	if ($.trim(prov) == "") {
		if(sellerType == 0){
			$("#shopCustomerInfoDOprovincetip").html('<font color="red">选择企业所在地!</font>');
		}else if(sellerType == 1){
			$("#shopBusinessInfoDOprovincetip").html('<font color="red">选择企业所在地!</font>');
		}
		$("#shopCustomerInfoDOprovincetip").html('<font color="red">选择企业所在地!</font>');
		error2 = true;
		if(!error1){
			showAccordion2(1);
		}
		ifError();
		enableSubmit(submitStr);
		return false;
	}
	
	if(isHaveOuterShop==0){
		var shopAddUrl=$("#shopAddressUrl").val();
		var shopLevel=$(":radio[id='shopLevel']:checked").val();
		var saleScope=$(":radio[id='saleScope']:checked").val();
		var isEnterB2c=$(":radio[id='isEnterB2c']:checked").val();
		if ($.trim(shopAddUrl) == "") {
			//$("#shopAddressUrl").focus();
			 $("#shopAddressUrltip").html('<font color="red">请输入网上店铺地址(Url)!</font>');
			 error2 = true;
			 if(!error1){showAccordion2(1);}
			 ifError();
			 enableSubmit(submitStr);
			 return false;
		}else{
			if(!shopAddUrl.isAddUrl()){
				//$("#shopAddressUrl").focus();
				$("#shopAddressUrltip").html('<font color="red">请输入正确的网上店铺地址(Url)!</font>');
				error2 = true;
			 	if(!error1){showAccordion2(1);}
				ifError();
				enableSubmit(submitStr);
				return false;
			}else{
				//$("#shopAddressUrl").focus();
				$("#shopAddressUrltip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
	  }
	}

	//企业信息
	if ($.trim(enterpriseName) == "") {
		$("#enterpriseName").focus();
		$("#enterpriseNametip").html('<font color="red">请填写企业名称!</font>');
		if(!error1){showAccordion2(1);}
		error2 = true;
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if (enterpriseName.length>30) {
			$("#enterpriseName").focus();
			$("#enterpriseNametip").html('<font color="red">企业名称不能超过30个字!</font>');
			error2 = true;
			if(!error1){showAccordion2(1);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}
		$("#enterpriseNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if ($.trim(businessLicenseNumber)=="") {
		$("#businessLicenseNumber").focus();
		$("#businessLicenseNumbertip").html('<font color="red">请填写工商营业执照注册号!</font>');
		error2 = true;
		if(!error1){showAccordion2(1);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if (businessLicenseNumber.length>18) {
			$("#businessLicenseNumber").focus();
			$("#businessLicenseNumbertip").html('<font color="red">工商营业执照注册号不能超过18位!</font>');
			error2 = true;
			if(!error1){showAccordion2(1);}
			ifError();
			enableSubmit(submitStr);
			return false;
		 }else if(!businessLicenseNumber.isNum()){
		 	$("#businessLicenseNumber").focus();
			$("#businessLicenseNumbertip").html('<font color="red">工商营业执照注册只能为数字!</font>');
			error2 = true;
			if(!error1){showAccordion2(1);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}
		$("#businessLicenseNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}

	if ($.trim(organizationCodeNumber) == "") {
		$("#organizationCodeNumber").focus();
		$("#organizationCodeNumbertip").html('<font color="red">请填写组织机构代码证号!</font>');
		error2 = true;
		if(!error1){showAccordion2(1);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
			if (organizationCodeNumber.length>10) {
				$("#organizationCodeNumber").focus();
				$("#organizationCodeNumbertip").html('<font color="red">请输10位的数字、字母和"-"组成的代码证号!</font>');
				error2 = true;
				if(!error1){showAccordion2(1);}
				ifError();
				enableSubmit("step2Submit");
				return false;
			}else if(!organizationCodeNumber.isSN3()){
				$("#organizationCodeNumber").focus();
				$("#organizationCodeNumbertip").html('<font color="red">请输10位的数字、字母和"-"组成的代码证号!</font>');
				error2 = true;
				if(!error1){showAccordion2(1);}
				ifError();
				enableSubmit(submitStr);
				return false;
			}
		$("#organizationCodeNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}

	if ($.trim(business) == "") {
		$("#business").focus();
		$("#businesstip").html('<font color="red">请填写经营范围!</font>');
		error2 = true;
		if(!error1){showAccordion2(1);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if (business.length>50) {
			$("#business").focus();
			$("#businesstip").html('<font color="red">经营范围不能超过50个字符!</font>');
			error2 = true;
			if(!error1){showAccordion2(1);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}
		$("#businesshui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}

	if ($.trim(businessLicenseEndDate) == "") {
		$("#businessLicenseEndDate").focus();
		$("#businessLicenseEndDatetip").html('<font color="red">请填写营业执照有效期限!</font>');
		error2 = true;
		if(!error1){showAccordion2(1);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
			var arr = $("#businessLicenseEndDate").val().split('/');
			var myDate=new   Date();
			var today = new   Date();
			myDate.setFullYear(arr[2],arr[1]-1,arr[0]);
		   	if(myDate <= today){
		   		$("#businessLicenseEndDate").focus();
				$("#businessLicenseEndDatetip").html('<font color="red">选择日期必须大于当前日期！</font>');
				error2 = true;
				if(!error1){showAccordion2(1);}
				ifError();
				enableSubmit(submitStr);
				return false;
			}
		   	else if (!businessLicenseEndDate.isDate()) {
			    $("#businessLicenseEndDate").focus();
				$("#businessLicenseEndDatetip").html('<font color="red">请填写正确的日期!</font>');
				error2 = true;
				if(!error1){showAccordion2(1);}
				ifError();
				enableSubmit(submitStr);
			    return false;
			}
			else{
				$("#businessLicenseEndDatetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
      }
	if ($.trim(legalName) == "") {
		$("#legalName").focus();
		$("#legalNametip").html('<font color="red">请填写法人代表姓名!</font>');
		error2 = true;
		if(!error1){showAccordion2(1);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if (legalName.length>20) {
			$("#legalName").focus();
			$("#legalNametip").html('<font color="red">法人代表姓名不能超过20个字!</font>');
			error2 = true;
			if(!error1){showAccordion2(1);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}else if(!legalName.isNotNum()){
			$("#legalName").focus();
			$("#legalNametip").html('<font color="red">法人代表姓名只能是汉字或英文字符 </font>');
			error2 = true;
			if(!error1){showAccordion2(1);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}
			$("#legalNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if ($.trim(registAddress) == "") {
		$("#registAddress").focus();
		$("#registAddresstip").html('<font color="red">请填写企业注册地址!</font>');
		error2 = true;
		if(!error1){showAccordion2(1);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if (registAddress.length>50) {
			$("#registAddress").focus();
		    $("#registAddresstip").html('<font color="red">企业注册地址不能超过50个字符!</font>');
		    error2 = true;
		    if(!error1){showAccordion2(1);}
		    ifError();
		    enableSubmit(submitStr);
		    return false;
		}
	}
	}
	
	var t = 2;
	if(sellerType == 3){
		t = 1;
	}
	//联系人信息
	if($.trim(shopManagerName) == ""){
		$("#shopManagerName").focus();
		$("#shopManagerNametip").html('<font color="red">请填写店铺联系人姓名!</font>');
		error3 = true;
		if(!error1 && !error2){
			showAccordion2(t);
		}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if($("#shopManagerName").val().length>20){
			$("#shopManagerName").focus();
			$("#shopManagerNametip").html('<font color="red">店铺联系人姓名不能超过20个字符</font>');
			error3 = true;
			if(!error1 && !error2){showAccordion2(t);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}
		if(!$("#shopManagerName").val().isNotNum()) {
			$("#shopManagerName").focus();
			$("#shopManagerNametip").html('<font color="red">请输入正确的店铺联系人姓名[只能输入中文字符、英文字符!]</font>');
			error3 = true;
			if(!error1 && !error2){showAccordion2(t);}
			ifError();
			enableSubmit(submitStr);
			return false;
	     }else{
			$("#shopManagerNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		}
	}
	
	if ($.trim($("#shopManagerTelephone").val()) == "") {
		$("#shopManagerTelephone").focus();
		$("#shopManagerTelephonetip").html('<font color="red">请填写电话号码!</font>');
		error3 = true;
		if(!error1 && !error2){showAccordion2(t);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		$("#shopManagerTelephonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if ($.trim($("#shopManagerMobile").val()) == "") {
		$("#shopManagerMobile").focus();
		$("#shopManagerMobiletip").html('<font color="red">请填写手机号码!</font>');
		error3 = true;
		if(!error1 && !error2){showAccordion2(t);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		$("#shopManagerMobiletip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if (!$("#shopManagerEmail").val().isEmail()) {
		$("#shopManagerEmail").focus();
		$("#shopManagerEmailtip").html('<font color="red">请填写正确的邮箱!</font>');
		error3 = true;
		if(!error1 && !error2){showAccordion2(t);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		$("#shopManagerEmailtip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if($.trim(contactAddress) == ""){
		$("#contactAddress").focus();
		$("#contactAddresstip").html('<font color="red">联系人地址不能为空!</font>');
		error3 = true;
		if(!error1 && !error2){showAccordion2(t);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if($("#contactAddress").val().length>50){
			$("#contactName").focus();
			$("#contactAddresstip").html('<font color="red">联系人地址不能超过50个字符</font>');
			error3 = true;
			if(!error1 && !error2){showAccordion2(t);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}else{
			$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		}
	}
	
	//店标
	/*var myfile=$("#shopLogo").val();
		if(myfile != ""){
				var fileIndex=myfile.lastIndexOf('.');
				if(fileIndex==-1){
					$("#shopLogo").focus();
					$("#shopLogotip").html('<font color="red">请上传正确图片格式!</font>');
					ifError();
					enableSubmit(submitStr);
					return false;
				}else{
					var str=myfile.substring(fileIndex+1).toLowerCase();
					if(fileType.indexOf(str)==-1){
						$("#shopLogo").focus();
						$("#shopLogotip").html('<font color="red">请上传正确图片格式!</font>');
						ifError();
						enableSubmit(submitStr);
						return false;
					}else{
						$("#shopLogotip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
					}
				}
		}*/
	return true;
}

//提交时校验上传图片 2.0 新
function checkShopUploadInfo(submitStr,type){
	disableSubmit(submitStr);
	var alertStr = ['请上传营业执照','请上传组织机构代码证','请上传税务登记证','请上传身份证复印件'];
	var idArr = [];
	if(type == 1 || type == 2){
		if(type == 1){
			idArr = ['businessLicenseC','organizationCodeC','taxPassC'];
		}else if(type == 2){
			idArr = ['businessLicenseB','organizationCodeB','taxPassB'];
		}
		for(var i=0;i<idArr.length;i++){
			var myfile=$("#"+idArr[i]).val();
			if(myfile == ""){
				$("#"+idArr[i]).focus();
				$("#"+idArr[i]+"tip").html('<font color="red">'+alertStr[i]+'!</font>');
				enableSubmit(submitStr);
				return false;
			}
			if(myfile != ""){
					var fileIndex=myfile.lastIndexOf('.');
					if(fileIndex==-1){
						$("#"+idArr[i]).focus();
						$("#"+idArr[i]+"tip").html('<font color="red">请上传正确图片格式!</font>');
						ifError();
						enableSubmit(submitStr);
						return false;
					}else{
						var str=myfile.substring(fileIndex+1).toLowerCase();
						if(fileType.indexOf(str)==-1){
							$("#"+idArr[i]).focus();
							$("#"+idArr[i]+"tip").html('<font color="red">请上传正确图片格式!</font>');
							ifError();
							enableSubmit(submitStr);
							return false;
						}else{
							$("#"+idArr[i]+"tip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
						}
					}
			}
		}
	}else if(type == 3){
		idArr = ['idCard'];
		if($("#idCard").val() == ""){
				$("#idCard").focus();
				$("#idCardhui").html('<font color="red">'+alertStr[3]+'!</font>');
				enableSubmit(submitStr);
				return false;
		}
		for(var i=0;i<idArr.length;i++){
			var myfile=$("#"+idArr[i]).val();
			if(myfile != ""){
					var fileIndex=myfile.lastIndexOf('.');
					if(fileIndex==-1){
						$("#"+idArr[i]).focus();
						$("#"+idArr[i]+"tip").html('<font color="red">请上传正确图片格式!</font>');
						ifError();
						enableSubmit(submitStr);
						return false;
					}else{
						var str=myfile.substring(fileIndex+1).toLowerCase();
						if(fileType.indexOf(str)==-1){
							$("#"+idArr[i]).focus();
							$("#"+idArr[i]+"tip").html('<font color="red">请上传正确图片格式!</font>');
							ifError();
							enableSubmit(submitStr);
							return false;
						}else{
							$("#"+idArr[i]+"tip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
						}
					}
			}
		}
	}
	return true;
}



//提交时校验品牌信息 2.0 新 废弃
function checkBrandInfoAndConfirm() {
	disableSubmit("shopBSubmit");
	if ($('.kd-tjlist').size() == 0) {
		alert("请至少添加一个品牌!");
		enableSubmit("shopBSubmit");
		return false;
	}
	var trademarkNumber = "";
	var brandName = "";
	var brandEnglishName = "";
	var brandStory = "";
	var flag = 1;
	$(".kd-tjlist").each(function(){
		var brandNameEach = $.trim($(this).find(".brandName").val());
		var trademarkNumberEach =$.trim( $(this).find(".trademarkNumber").val());
		var brandEnglishNameEach = $.trim($(this).find(".brandEnglishName").val());
		var brandStoryEach = $.trim($(this).find(".brandStory").val());
		var myfile1=$(this).find(".myFile1").val();
		var myfile2=$(this).find(".myFile2").val();
		var myfile3=$(this).find(".myFile3").val();
		if(trademarkNumberEach == ""){
			$(this).find(".trademarkNumber").focus();
			$(this).find(".trademarkNumbertip").html('<font color="red">请输入商标注册证号（申请号）!</font>');
			flag = -1;
		}else{
			if (trademarkNumberEach.length>50 ) {
				$(this).find(".trademarkNumber").focus();
				$(this).find(".trademarkNumbertip").html('<font color="red">商标注册证号（申请号）不能超过50个字符!</font>');
				flag = -1;
			}else{
				$(this).find(".trademarkNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
		}
		if(brandNameEach == ""){
			$(this).find(".brandName").focus();
			$(this).find(".brandNametip").html('<font color="red">请输入品牌名称!</font>');
			flag = -1;
		}else{
			if (brandNameEach.length>50) {
				$(this).find(".brandName").focus();
				$(this).find(".brandNametip").html('<font color="red">品牌名称不能超过50个字符!</font>');
				flag = -1;
			}
			/*else if(!brandNameEach.isZZS()){
				$(this).find(".brandName").focus();
				$(this).find(".brandNametip").html('<font color="red">只能输入中文、英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！</font>');
				flag = -1;
			}*/
			else{
				$(this).find(".brandNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
			
		}
		if(brandEnglishNameEach == ""){
			//$(this).find(".brandEnglishName").focus();
			//$(this).find(".brandEnglishNametip").html('<font color="red">请输入品牌英文名!</font>');
			//flag = -1;
		}else{
			if (brandEnglishNameEach.length>50) {
				$(this).find(".brandEnglishName").focus();
				$(this).find(".brandEnglishNametip").html('<font color="red">品牌英文名不能超过50个字符!</font>');
				flag = -1;
			}else if(!brandEnglishNameEach.isSN2()){
				$(this).find(".brandEnglishName").focus();
				$(this).find(".brandEnglishNametip").html('<font color="red">只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</font>');
				flag = -1;
			}else{
			  $(this).find(".brandEnglishNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
		}
		if(myfile1 == ""){
			$(this).find(".myFile1").focus();
			$(this).find(".myFilehui1").html('<font color="red">请选择品牌logo!</font>');
			flag = -1;
		}else{
			var i=myfile1.lastIndexOf('.');
			if(i==-1){
				$(this).find(".myFile1").focus();
				$(this).find(".myFilehui1").html('<font color="red">请上传正确logo图片格式!</font>');
				flag = -1;
			}else{
				var str=myfile1.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$(this).find(".myFile1").focus();
					$(this).find(".myFilehui1").html('<font color="red">请上传正确logo图片格式!</font>');
					flag = -1;
				}else{
					$(this).find(".myFilehui1").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		if(brandStoryEach == ""){
			$(this).find(".brandStory").focus();
			$(this).find(".brandStorytip").html('<font color="red">请输入品牌故事!</font>');
			flag = -1;
		}else{
			if (brandStoryEach.length>500) {
				$(this).find(".brandStory").focus();
				$(this).find(".brandStorytip").html('<font color="red">品牌故事不能超过500个字符!</font>');
				flag = -1;
			}else{
				$(this).find(".brandStorytip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
			
		}
		
		if(myfile2 == ""){
			$(this).find(".myFile2").focus();
			$(this).find(".myFilehui2").html('<font color="red">请选择品牌授权书或品牌商标注册证书!</font>');
			flag = -1;
		}else{
			var i=myfile2.lastIndexOf('.');
			if(i==-1){
				$(this).find(".myFile2").focus();
				$(this).find(".myFilehui2").html('<font color="red">请上传正确的品牌授权书或品牌商标注册证书图片格式!</font>');
				flag = -1;
			}else{
				var str=myfile2.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$(this).find(".myFile2").focus();
					$(this).find(".myFilehui2").html('<font color="red">请上传正确的品牌授权书或品牌商标注册证书格式!</font>');
					flag = -1;
				}else{
					$(this).find(".myFilehui2").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		
		if(myfile3 == ""){
			$(this).find(".myFile3").focus();
			$(this).find(".myFilehui3").html('<font color="red">请选择商品质量检验证书	!</font>');
			flag = -1;
		}else{
			var i=myfile3.lastIndexOf('.');
			if(i==-1){
				$(this).find(".myFile3").focus();
				$(this).find(".myFilehui3").html('<font color="red">请上传正确的商品质量检验证书格式!</font>');
				flag = -1;
			}else{
				var str=myfile3.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$(this).find(".myFile3").focus();
					$(this).find(".myFilehui3").html('<font color="red">请上传正确的商品质量检验证书!</font>');
					flag = -1;
				}else{
					$(this).find(".myFilehui3").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		
		trademarkNumber += trademarkNumberEach+"|";
		brandName += brandNameEach+"|";
		if(!brandEnglishNameEach){
			brandEnglishNameEach = " ";
		}
		brandEnglishName += brandEnglishNameEach+"|";
		brandStory += brandStoryEach+"|";
		
		
	});
	if(flag == -1){
		ifError();
		enableSubmit("step3Submit");
		return false;
	}
	trademarkNumber = trademarkNumber.substring(0,trademarkNumber.length-1);
	brandName = brandName.substring(0,brandName.length-1);
	brandEnglishName = brandEnglishName.substring(0,brandEnglishName.length-1);
	brandStory = brandStory.substring(0,brandStory.length-1);
	
	$("#trademarkNumberHidden").val(trademarkNumber);
	$("#brandNameHidden").val(brandName);
	$("#brandEnglishNameHidden").val(brandEnglishName);
	$("#brandStoryHidden").val(brandStory);

	//alert($("#trademarkNumberHidden").val());
	//alert($("#brandNameHidden").val());
	//alert($("#brandEnglishNameHidden").val());
	//alert($("#brandStoryHidden").val());
	//$("#step3Submit").attr("disabled","disabled");
	return true;
}

//失去焦点时校验品牌信息表单 -- 2.0 新
function checkBrandOnBlur(id) {
	setSubmitErrorEmpty();
	var trademarkNumber = "";
	var brandName = "";
	var brandEnglishName = "";
	var brandStory = "";
		if(id=='trademarkNumber'){
			var trademarkNumberEach =$.trim( $("#trademarkNumber").val());
			if(trademarkNumberEach == ""){
		        //$(this).find(".trademarkNumber").focus();
				$("#trademarkNumbertip").html('<font color="red">请输入商标注册证号（申请号）!</font>');
				flag = -1;
			}else{
				if (trademarkNumberEach.length>50 ) {
					//$(this).find(".trademarkNumber").focus();
					$("#trademarkNumbertip").html('<font color="red">商标注册证号（申请号）不能超过50个字符!</font>');
					flag = -1;
				}else{
					$("#trademarkNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
	   }
	   if(id=='brandName'){
	   	
	   	var brandNameEach = $.trim( $("#brandName").val());
			if(brandNameEach == ""){
				//$(this).find(".brandName").focus();
				$("#brandNametip").html('<font color="red">请输入品牌名称!</font>');
				flag = -1;
			}else{
				if (brandNameEach.length>50) {
					//$(this).find(".brandName").focus();
					$("#brandNametip").html('<font color="red">品牌名称不能超过50个字符!</font>');
					flag = -1;
				}
				/*else if(!brandNameEach.isZZS2()){
					//$(this).find(".brandName").focus();
					$(this).find(".brandNametip").html('<font color="red">对不起,品牌名中不能含有"%"</font>');
					flag = -1;
				}*/
				else{
					$("#brandNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
				
			}
	   }
	   if(id=='brandEnglishName'){
	   	var brandEnglishNameEach = $.trim( $("#brandEnglishName").val());
			if(brandEnglishNameEach == ""){
				//$(this).find(".brandEnglishName").focus();
				//$(this).find(".brandEnglishNametip").html('<font color="red">请输入品牌英文名!</font>');
				//flag = -1;
			}else{
				if (brandEnglishNameEach.length>50) {
					//$(this).find(".brandEnglishName").focus();
					$("#brandEnglishNametip").html('<font color="red">品牌英文名不能超过50个字符!</font>');
					flag = -1;
				}else if(!brandEnglishNameEach.isSN2()){
					//$(this).find(".brandEnglishName").focus();
					$("#brandEnglishNametip").html('<font color="red">只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</font>');
					flag = -1;
				}else{
				  $("#brandEnglishNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
	   }
	   if(id=='brandStory'){
	   	var brandStoryEach = $.trim( $("#brandStory").val());
			if(brandStoryEach == ""){
				//$(this).find(".brandStory").focus();
				$("#brandStoryhui").html('<font color="red">请输入品牌故事!</font>');
				flag = -1;
			}else{
				if (brandStoryEach.length>500) {
					//$(this).find(".brandStory").focus();
					$("#brandStoryhui").html('<font color="red">品牌故事不能超过500个字符!</font>');
					flag = -1;
				}else{
					$("#brandStoryhui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
				
			}
	   }
	   	
}

//提交时校验品牌信息 -- 2.0 新
function checkBrandConfirm(submit) {
	disableSubmit(submit);
	var trademarkNumber = "";
	var brandName = "";
	var brandEnglishName = "";
	var brandStory = "";
	var flag = 1;
		var brandNameEach = $.trim( $("#brandName").val());
		var trademarkNumberEach =$.trim( $("#trademarkNumber").val());
		var brandEnglishNameEach = $.trim( $("#brandEnglishName").val());
		var brandStoryEach = $.trim( $("#brandStory").val());
		var myfile1=$("#brandLogo").val();
		var myfile2=$("#brandCertificate").val();
		var myfile3=$("#qualityCertificate").val();
		if(trademarkNumberEach == ""){
			$("#trademarkNumber").focus();
			$("#trademarkNumbertip").html('<font color="red">请输入商标注册证号（申请号）!</font>');
			flag = -1;
		}else{
			if (trademarkNumberEach.length>50 ) {
				$("#trademarkNumber").focus();
				$("#trademarkNumbertip").html('<font color="red">商标注册证号（申请号）不能超过50个字符!</font>');
				flag = -1;
			}else{
				$("#trademarkNumbertip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
		}
		if(brandNameEach == ""){
			$("#brandName").focus();
			$("#brandNametip").html('<font color="red">请输入品牌名称!</font>');
			flag = -1;
		}else{
			if (brandNameEach.length>50) {
				$("brandName").focus();
				$("brandNametip").html('<font color="red">品牌名称不能超过50个字符!</font>');
				flag = -1;
			}
			/*else if(!brandNameEach.isZZS()){
				$(this).find(".brandName").focus();
				$(this).find(".brandNametip").html('<font color="red">只能输入中文、英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！</font>');
				flag = -1;
			}*/
			else{
				$("brandNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
			
		}
		if(brandEnglishNameEach == ""){
			//$(this).find(".brandEnglishName").focus();
			//$(this).find(".brandEnglishNametip").html('<font color="red">请输入品牌英文名!</font>');
			//flag = -1;
		}else{
			if (brandEnglishNameEach.length>50) {
				$("#brandEnglishName").focus();
				$("#brandEnglishNametip").html('<font color="red">品牌英文名不能超过50个字符!</font>');
				flag = -1;
			}else if(!brandEnglishNameEach.isSN2()){
				$("#brandEnglishName").focus();
				$("#brandEnglishNametip").html('<font color="red">只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</font>');
				flag = -1;
			}else{
			  $("#brandEnglishNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
		}
		if(myfile1 == ""){
			$("#brandLogo").focus();
			$("#brandLogohui").html('<font color="red">请选择品牌logo!</font>');
			flag = -1;
		}else{
			var i=myfile1.lastIndexOf('.');
			if(i==-1){
				$("#brandLogo").focus();
				$("#brandLogohui").html('<font color="red">请上传正确logo图片格式!</font>');
				flag = -1;
			}else{
				var str=myfile1.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$("#brandLogo").focus();
					$("#brandLogohui").html('<font color="red">请上传正确logo图片格式!</font>');
					flag = -1;
				}else{
					$("#brandLogohui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		if(brandStoryEach == ""){
			$("#brandStory").focus();
			$("#brandStoryhui").html('<font color="red">请输入品牌故事!</font>');
			flag = -1;
		}else{
			if (brandStoryEach.length>500) {
				$("#brandStory").focus();
				$("#brandStoryhui").html('<font color="red">品牌故事不能超过500个字符!</font>');
				flag = -1;
			}else{
				$("#brandStoryhui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
			
		}
		
		if(myfile2 == ""){
			$("#brandCertificate").focus();
			$("#brandCertificatehui").html('<font color="red">请选择品牌授权书或品牌商标注册证书!</font>');
			flag = -1;
		}else{
			var i=myfile2.lastIndexOf('.');
			if(i==-1){
				$("#brandCertificate").focus();
				$("#brandCertificatehui").html('<font color="red">请上传正确的品牌授权书或品牌商标注册证书图片格式!</font>');
				flag = -1;
			}else{
				var str=myfile2.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$("#brandCertificate").focus();
					$("#brandCertificatetip").html('<font color="red">请上传正确的品牌授权书或品牌商标注册证书格式!</font>');
					flag = -1;
				}else{
					$("#brandCertificatetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		
		if(myfile3 == ""){
			$("#qualityCertificate").focus();
			$("#qualityCertificatehui").html('<font color="red">请选择商品质量检验证书	!</font>');
			flag = -1;
		}else{
			var i=myfile3.lastIndexOf('.');
			if(i==-1){
				$("#qualityCertificate").focus();
				$("#qualityCertificatehui").html('<font color="red">请上传正确的商品质量检验证书格式!</font>');
				flag = -1;
			}else{
				var str=myfile3.substring(i+1).toLowerCase();
				if(fileType.indexOf(str)==-1){
					$("#qualityCertificate").focus();
					$("#qualityCertificatehui").html('<font color="red">请上传正确的商品质量检验证书!</font>');
					flag = -1;
				}else{
					$("#qualityCertificatehui").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				}
			}
		}
		
	if(flag == -1){
		ifError();
		enableSubmit(submit);
		return false;
	}
	return true;
}

//删除品牌 -- 2.0 新
function deleteBrandInfo(){
	var inputa = $("#delBrandInfo");
	var brandSeq = $("#brandSeq").val();
	if(brandSeq == 0){
		alert("您至少要有一个品牌信息!");
		return;
	}
	inputa.href="/shop/deleteBrandInfoAction.htm?brandSeq="+brandSeq;
}


