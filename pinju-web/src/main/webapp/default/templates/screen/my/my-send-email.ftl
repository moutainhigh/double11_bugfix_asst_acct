<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>品聚 - 会员 - 验证邮箱</title>
</head>
<body>
 <link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" media="screen" />
 <form action="${base}/my/sendEmail.htm" method="post" id="emailForm">
	   <input name="pj0" type="hidden" value="${pj0}">
		<div class="mail-h cf">
			<h1>验证邮箱</h1>
		</div>
		<div class="row">
			<div class="y-point">
				<p>点击“发送验证邮件”，系统会向您填写的邮箱地址发送一封验证邮件。<br />

点击邮件中的验证链接，即可完成验证邮箱！</p>
			</div>
			<div class="email-m">
				<div class="e-row">邮箱：<input type="text" name="email" id="email" size="20" maxlength="100" title="常用的 E-mail 邮箱地址" value="${email?html}"/><span id="txt" class="txt"></span></div>
				<button id="submitBtn" class="btn send-em" type="submit">发送验证邮件</button>
			</div>
		
		</div>
  </form>
<input type="hidden" value="security-center" id="my-page" />
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate-min.js"></script>
<script type="text/javascript">

  $(document).ready(function() {
   (function() {
	  $('#emailForm').effectiveAndValidate({
	     submitHandler : function(form) {
		      window.onbeforeunload = function(){};
		      form.submit();
		      $("#submitBtn").attr("disabled", "true"); 
	     },
	
	     rules : {
	       'email' : {
	          required: true,
	          email : true,
	          remote: {
	              type : 'POST',
	              async : true,
	              url : '${base}/async/security/emailValidator.htm',
	              data : { email : function() { return $('#email').val();}},
	              dataType : 'json',
	              dataFilter : function(json) {
	                  var j = eval('(' + json + ')');
	                  if(!j) return false;
	                  return j.result + '';
	                }
	            }
	        }
	    },
	
	    messages : {
	      'email' : {
	          required : '邮箱不能为空',
	          email : '请填写正确的 E-mail 地址',
	          remote : '该邮箱已经被其他会员使用了'
	        }
	    },
	
	    errorPlacement : function(error, element) {
	        $("#txt").empty().append(error.text())
	    }
	  },
	  {wrapSelector : 'li'} 
	  );
	})();
  }); 
</script>
</body>
</html>