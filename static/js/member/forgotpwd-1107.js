;(function() {
	$("#loginName").focus();
     var msg = function(target, message, type) {
		 var container = target.next();
		 container.empty().removeClass().addClass('err');
		 container.text(message);
		 container.show();
		 if (type == 'submit') {
             target.focus();
		 }
		 return false;
	  };

	  var capMsg = function(target, inputObj, message, type) {
         var container = target.next();
		 container.empty().removeClass().addClass('err');
		 container.text(message);
		 container.show();
		  if (type == 'submit') {
             inputObj.focus();
		 }
		 return false;
	  }

	  var codeMsg = function(errorCode) {
         if (errorCode == "no-loginName") {
             return msg($("#loginName"), "您输入的会员名不存在", "submit");
         }
		 if (errorCode == "no-email") {
			 return msg($("#email"), "您输入的邮箱不存在", "submit");
		 }
		 if(errorCode == "no-mobile") {
             return msg($("#mobile"), "您输入的手机号码不存在", "submit");
		 }
		 if (errorCode == "no-equal-eamil") {
			 return msg($("#loginName"), "该会员与所绑定的邮箱不一致", "submit");
		 }
		 if (errorCode == "no-equal-mobile") {
			 return msg($("#loginName"), "该会员与所绑定的手机号码不一致", "submit");
		 }
		 return false;
	  }

     var name = function(target, loginName, type) {
          if (loginName.length==0) {
			return msg(target, '请输入会员名');
		  }
		  if (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(loginName)) {
			  var length = 0;
			  for (var i = 0, k = loginName.length; i < k; i++) {
				length += (loginName.charCodeAt(i) < 0xff) ? 1 : 2;   
			  }
			  if (length < 4 || length > 20) {
				return msg(target, '会员名为 4～20 个字母、数字、“-” 和下划线或者 2～10 个汉字', type);
			  }
			  if (!/^[\u4e00-\u9faf_0-9a-zA-Z-]+$/.test(loginName)) {
				return msg(target, '会员名应由英文字母、数字、汉字、“-” 和下划线组成', type);
			  }
			  if (!/^[\u4e00-\u9faf0-9a-zA-Z].*$/.test(loginName)) {
				return msg(target, '会员名应以英文字母、数字或汉字开始', type);
			  }
		  }
		  target.next().empty().removeClass();
		  return true;
	 };

	 var emailFun = function(target, email, type) {
         if(!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(email)) {
           return msg(target, '邮箱地址不正确', type);
	     }
		 target.next().empty().removeClass();
		 return true;
	 };
	 var mobileFun = function(target, mobile, type) {
         if(!/^1[3458][0-9]{9}$/.test(mobile)) {
           return msg(target, '请输入正确的手机号码', type);
	     }
		 target.next().empty().removeClass();
	     return true;
	 };
	 var captchaFun = function(target, captcha, type) {
		  if (captcha.length == 0) {
			 return capMsg(target, $("#captcha"), '请输入验证码', type);
		  }
		  if (captcha.length != 4) {
             return capMsg(target, $("#captcha"), '验证码应为 4 个字符', type);
		  }
		  if (!/^[0-9A-Za-z]{4}$/.test(captcha)) {
             return capMsg(target, $("#captcha"), '验证码应由英文字母和数字组成', type);
		  }
		  target.next().empty().removeClass();
		  return true;
	 };

	 var alterEvent = function() {
         var radioVal = $("input[type=radio]:checked").val();
		 if (radioVal == '1') {
			  $("#mobile").next().empty().removeClass();
			  emailFun($("#email"), $("#email").val());
		 } else if (radioVal == '2') {
			  $("#email").next().empty().removeClass();
			  mobileFun($("#mobile"), $("#mobile").val());
		 }
	 };

	 var validateLem = function(loginName,mobile,email,sel,captcha, sid) {
		 var ret = true;
		 $.ajax({
			  type : 'POST',
              async : false,
              url : BASE + '/async/member/forgot-password.htm',
              data : { loginName : loginName, mobile : mobile, email : email, sel:sel, captcha:captcha, sid:sid},
              dataType : 'json',
              dataFilter : function(json) {
                  var j = eval('(' + json + ')');
				  var errorCode = j.errorCode;
				  if (errorCode == 'success') {
					  return true;
				  }
                  ret = codeMsg(errorCode);
               }
		 });
		 return ret;
	 };

   $("#loginName").bind('keyup', function(){
       return name($(this), $.trim($(this).val()));
   }).bind('focusout', function() {
	   return name($(this), $.trim($(this).val()));
   }).bind('focus', function() {
	   $(this).select();
   });
   
   $("#email").bind('keyup', function() {
	   return emailFun($(this), $.trim($(this).val()));
   }).bind('focusout', function() {
	   return emailFun($(this), $.trim($(this).val()));
   }).bind('focus', function() {
	   $(this).select();
   });

   $("#mobile").bind('keyup', function() {
	   return mobileFun($(this), $.trim($(this).val()));
   }).bind('focusout', function() {
	   return mobileFun($(this), $.trim($(this).val()));
   }).bind('focus',function() {
	  $(this).select();
   });


   $("#captcha").bind('keyup', function() {
	   var captcha = $.trim($(this).val());
	   return captchaFun($("#captcha-change"), captcha);
   }).bind('focus', function() {
	  $(this).select();
   }); 

   $("#mobileRadio").click(function() {
	   alterEvent();
   });

   $("#emailRadio").click(function() {
	   alterEvent();
   });

   $("#phone").click(function() {
	   alterEvent();
   });

   $("#mail").click(function() {
	   alterEvent();
   });

   $("#mobile").click(function() {
	   alterEvent();
   });

   $("#email").click(function() {
	   alterEvent();
   });

   $("#meform").submit(function() {
	    var loginName = $.trim($("#loginName").val());
		if (!name($("#loginName"), loginName, 'submit')) {
           return false;
		}
		var email = $.trim($("#email").val());
		var mobile = $.trim($("#mobile").val());
		var radioVal = $("input[type=radio]:checked").val();
		if (radioVal == '1') {
		   if (!emailFun($("#email"), email, 'submit')) {
		       return false;
			}
		 } else if (radioVal == '2') {
			if (!mobileFun($("#mobile"), mobile, 'submit')) {
			   return false;
			}
		 }
		 var captcha = $.trim($("#captcha").val());
		 if (!captchaFun($("#captcha-change"), captcha, 'submit')) {
              return false;
		 }
         if(!validateLem(loginName, mobile, email, radioVal)) {
              return false;
		 }
		 return true;
   });
})();