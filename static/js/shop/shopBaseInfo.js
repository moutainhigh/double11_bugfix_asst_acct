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

function checkShopFill() {
	var shopName = $("#shopName").val();
	var title = $("#title").val();
	var description = $("#description").val();
	if ($.trim(shopName) == "") {
		$("#shopName").focus();
		$("#shopNametip").html('<font color="red">请填写店铺名称!</font>');
		return false;
	}else{
		if (shopName.length>20) {
		   $("#shopName").focus();
		   $("#shopNametip").html('<font color="red">店铺名称不能超过20个字符</font>');
		   return false;
		 }
	}
	if ($.trim(title) == "") {
		//$("#title").focus();
		$("#titletip").html('<font color="red">请填写店铺简介!</font>');
		return false;
	}else{
		if(title.length>300){
	     		//$("#title").focus();
				$("#titletip").html('<font color="red">店铺简介不能超过300个字符!</font>');
				return false;
	     	}else{
				$("#titletip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	     	}
	}
	if ($.trim(description) == "") {
		$("#descriptionC").focus();
		$("#descriptionCtip").html('<font color="red">请填写店铺介绍!</font>');
		return false;
	}else{
		if (description.length>500) {
			$("#description").focus();
			$("#descriptionCtip").html('<font color="red">店铺介绍过长，只能在500字以内!</font>');
			return false;
	    }
	}
	var isHaveOuterShop=$(":radio[id='isHaveOuterShop']:checked").val();
	if($.trim(isHaveOuterShop)==0){	
   	    var shopAddUrl = $("#shopAddressUrl").val();
		if ($.trim(shopAddUrl) == "") {
			 $("#shopAddressUrltip").html('<font color="red">请输入网上店铺地址(Url)!</font>');
			 ifError();
			 return false;
		}else{
			if(!shopAddUrl.isAddUrl()){
				$("#shopAddressUrltip").html('<font color="red">请输入正确的网上店铺地址(Url)!</font>');
				ifError();
				return false;
			}else{
				$("#shopAddressUrltip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			}
	   }
	  }
	  if($.trim($("#shopManagerName").val()) == ""){
		$("#shopManagerName").focus();
		$("#shopManagerNametip").html('<font color="red">请填写店铺联系人姓名!</font>');
		ifError();
		return false;
	}else{
		if($("#shopManagerName").val().length>20){
			$("#shopManagerName").focus();
			$("#shopManagerNametip").html('<font color="red">店铺联系人姓名不能超过20个字符</font>');
			ifError();
			return false;
		}
		if(!$("#shopManagerName").val().isNotNum()) {
			$("#shopManagerName").focus();
			$("#shopManagerNametip").html('<font color="red">请输入正确的店铺联系人姓名[只能输入中文字符、英文字符!]</font>');
			ifError();
			return false;
	     }else{
			$("#shopManagerNametip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
		}
	}
	if ($.trim($("#shopManagerTelephone").val()) == "") {
		$("#shopManagerTelephone").focus();
		$("#shopManagerTelephonetip").html('<font color="red">请填写电话号码!</font>');
		ifError();
		return false;
	}else{
		$("#shopManagerTelephonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if ($.trim($("#shopManagerMobile").val()) == "") {
		$("#shopManagerMobile").focus();
		$("#shopManagerMobiletip").html('<font color="red">请填写手机号码!</font>');
		ifError();
		return false;
	}else{
		$("#shopManagerMobiletip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
	if (!$("#shopManagerEmail").val().isEmail()) {
		$("#shopManagerEmail").focus();
		$("#shopManagerEmailtip").html('<font color="red">请填写正确的邮箱!</font>');
		ifError();
		return false;
	}else{
		$("#shopManagerEmailtip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
	}
		if($.trim($("#contactAddress").val()) == ""){
		$("#contactAddress").focus();
		$("#contactAddresstip").html('<font color="red">联系人地址不能为空!</font>');
		error3 = true;
		if(!error1 && !error2){showAccordion2(2);}
		ifError();
		enableSubmit(submitStr);
		return false;
	}else{
		if($("#contactAddress").val().length>50){
			$("#contactName").focus();
			$("#contactAddresstip").html('<font color="red">联系人地址不能超过50个字符</font>');
			error3 = true;
			if(!error1 && !error2){showAccordion2(2);}
			ifError();
			enableSubmit(submitStr);
			return false;
		}else{
			$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
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
  var contentNotNull=[['#shopManagerName','联系人姓名不能为空'],['#contactAddress','联系人地址不能为空'],];
  var contentOutLen=[['#descriptionC','500,店铺介绍不能超过500个字符'],['#descriptionB','500,店铺介绍不能超过500个字符'],
	['#contactName','20,联系人姓名不能超过20个字符'],['#contactAddress','50,联系人地址不能超过50个字符']];
	
	
function checkShopInfoOnBlur(id){
	if(id=='title'){
		if ($.trim($("#title").val())=="") {
			//$("#title").focus();
			$("#titletip").html('<font color="red">请填写店铺简介!</font>');
			return false;
	     }else{
	     	if($("#title").val().length>300){
	     		//$("#title").focus();
				$("#titletip").html('<font color="red">店铺简介不能超过300个字符!</font>');
				return false;
	     	}else{
				$("#titletip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				return true;
	     	}
		}
	}if(id=='description'){
		if ($.trim($("#description").val())=="") {
			//$("#title").focus();
			$("#descriptiontip").html('<font color="red">请填写店铺介绍!</font>');
			return false;
	     }else{
	     	if($("#description").val().length>500){
	     		//$("#title").focus();
				$("#descriptiontip").html('<font color="red">店铺介绍不能超过500个字符!</font>');
				return false;
	     	}else{
	     		$("#descriptiontip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				return true;
	     	}
			
		}
	}	
}
function setSubmitErrorEmpty(){
	$("#submitError").html("");
}


function spCheck(id,error){	
setSubmitErrorEmpty();
	var spValue=$("#"+id).val();
	if(id == 'shopAddressUrl'){
		if (spValue == "") {
			$(error).html('<font color="red">请输入网上店铺地址(Url)!</font>');
			return false;
		}else{
			if(!spValue.isAddUrl()){
				$(error).html('<font color="red">请输入正确的网上店铺地址(Url)!</font>');
				return false;
			}else{
				$(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
				return true;
			}
			
		}
	}
	if(id == 'shopLevel'){
		if ($(":radio[id='shopLevel']:checked").val() == "" || $(":radio[id='shopLevel']:checked").val()=="undefined") {
			$(error).html('<font color="red">选择选择店铺等级!</font>');
			return false;
		}else{
		     $(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
	if(id == 'saleScope'){
		if ($(":radio[id='saleScope']:checked").val() == "" || $(":radio[id='saleScope']:checked").val()=="undefined") {
			$(error).html('<font color="red">请选择年销售规模(Url)!</font>');
			return false;
		}else{
		     $(error).html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}
		if(id=='contactPhone'){
		if ($.trim($("#contactPhone").val()) == "") {
			$("#contactPhonetip").html('<font color="red">请填写联系人电话!</font>');
			return false;
	     }else{
			$("#contactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='contactPostalCode'){
		if (!$("#contactPostalCode").val().isPostal()) {
			$("#contactPostalCodetip").html('<font color="red">请填写正确的邮编!</font>');
			return false;
		}else{
			$("#contactPostalCodetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='shopManagerTelephone'){
		if ($.trim($("#shopManagerTelephone").val()) == "") {
			$("#shopManagerTelephonetip").html('<font color="red">请填写电话号码!</font>');
			return false;
		}else{
			$("#shopManagerTelephonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='shopManagerMobile'){
		if ($.trim($("#shopManagerMobile").val()) == "") {
			$("#shopManagerMobiletip").html('<font color="red">请填写手机号码!</font>');
			return false;
		 }else{
			$("#shopManagerMobiletip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}
	}if(id=='shopManagerEmail'){
		if (!$("#shopManagerEmail").val().isEmail()) {
		   $("#shopManagerEmailtip").html('<font color="red">请填写正确的邮箱!</font>');
		   return false;
	    }else{
			$("#shopManagerEmailtip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;

		}
	}
	if(id=='emergencyContactPhone'){
	   if ($.trim($("#emergencyContactPhone").val()) == "") {
			$("#emergencyContactPhonetip").html('<font color="red">请填写手机号码!</font>');
			return false;
		}else{
			$("#emergencyContactPhonetip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
		}	
	}
		if(id=='contactAddress'){
	   if ($.trim($("#contactAddress").val()) == "") {
			$("#contactAddresstip").html('<font color="red">请填写联系地址!</font>');
			return false;
		}else{
			$("#contactAddresstip").html('<img src="http://static.pinju.com/img/icon-succeed.png"/>');
			return true;
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
}

 function checkShopName(id,error){
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


$(document).ready(function() {
	$.imagePreview({
		file : $('#shopLogoFile'),
		img : $('#shopLogoImg'),
		maxWidth : 80,
		maxHeight : 80
	});
});

