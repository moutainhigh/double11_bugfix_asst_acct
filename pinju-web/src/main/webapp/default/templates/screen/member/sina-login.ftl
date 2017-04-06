<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>第三方登录</title>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css?t=20111121.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" type="text/css" media="screen" />
<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>

</head>

<body>
  <div class="login_main">
    <#include "/default/templates/layout/laybaner-simple.ftl">
      <form action="${base}/member/sina-register.htm<#if returnUrl?? && returnUrl?length gt 0>?returnUrl=${returnUrl?url}</#if>" method="post" id="sina-register-form">
          <div class="wrap-bor cf">
 			<div class="crea-name">
				<div class="tit"><span>创建您的品聚用户名</span></div>
				<br/>
				<p class="txt">您好，欢迎您来到品聚网，为了您在品聚网的正常交易，请您填写一个您在品聚网的会员名，并且设置您的确认收货密码，<br/>以便在交易中使用，谢谢您的配合!</p>
				<div class="n-box">			
					<div class="tipswrap">
						<div id="errorico" class="infocat <#if invokeMessage??>error</#if>"></div>
						<div class="content">
							<h3 id="errorMessage"><#if invokeMessage??>${(invokeMessage)!}</#if></h3>
						</div>
					</div>
					<div class="uesr-name"><span>会员名:</span><input type="text" id="nickname" name="nickname" value="${nickname?html}" maxlength="20" title="会员名为 4～20 个英文字母、数字、“-” 和下划线或者 2～10 个汉字" /></div>
					<p class="txtp">会员名一经注册不能修改，可以使用字母及汉字作为开头，长度为4~20字符</p>
					<div class="uesr-name"><span>确认收货密码:</span><input type="password" name="password" id="password" maxlength="16" title="密码为大小写英文字母、数字、符号的 6～16 个字符" /></div>
					<div class="uesr-name"><span>重复密码:</span><input type="password" name="confirm" id="confirm" maxlength="16" title="请再次输入密码" /></div>
				</div>
				<button class="btn submit" type="submit"></button>
			</div>
          </div>
            <input type="hidden" name="aj0" value="${aj0?html}"/>
            <input type="hidden" name="aj1" value="${aj1?html}"/>
            <input type="hidden" name="tid" value="" />
          </form>
  </div>
<script type="text/javascript">var BASE='${base}';</script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/key.js?t=20111130.js"></script>
<script type="text/javascript">
(function(){$('input[type="text"],input[type="password"]').bind({"copy cut paste contextmenu":function(){return false},focus:function(){$(this).select()}})})();(function(){$("#nickname").focus();$("#nickname").select();var clearMessage=function(){$("#errorico").removeClass("error");$("#errorMessage").text("")};var showbox=function(message){$("#errorico").addClass("error");$("#errorMessage").text(message)};$("#sina-register-form").effectiveAndValidate({submitHandler:function(form){Encrypt.encrypt({timeout:15000,success:function(key,iv,res){$('input[name="tid"]').val(res.tid);var p=$("#password");p.val(Security.encryptHex(p.val(),key,iv));var c=$("#confirm");c.val(Security.encryptHex(c.val(),key,iv));form.submit()},error:function(key,iv,res){var message=res?"\u7f51\u7edc\u901a\u4fe1\u5f02\u5e38\uff0c\u5904\u7406\u5931\u8d25":"\u7f51\u7edc\u8fde\u63a5\u8d85\u65f6\uff0c\u5904\u7406\u5931\u8d25";showbox(message)}})},rules:{nickname:{required:true,byterangelength:[4,20],pattern:/^[\u4e00-\u9faf_0-9a-zA-Z-]+$/,pattern2:/^[\u4e00-\u9fafa-zA-Z].*$/,remote:{type:"POST",async:true,url:BASE+"/async/member/nickname.htm",data:{nickname:function(){return $.trim($("#nickname").val())}},dataType:"json",dataFilter:function(json){var j=eval("("+json+")");if(!j){return false}if(j.result){clearMessage()}return j.result+""}}},password:{required:true,rangelength:[6,16],pattern:/^[\x21-\x7e]+$/},confirm:{equalTo:'input[name="password"]'}},messages:{nickname:{required:"\u8bf7\u8f93\u5165\u4f1a\u5458\u540d",byterangelength:"\u4f1a\u5458\u540d\u4e3a {0}\uff5e{1} \u4e2a\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u201c-\u201d \u548c\u4e0b\u5212\u7ebf\u6216\u8005 2\uff5e10 \u4e2a\u6c49\u5b57",pattern:"\u4f1a\u5458\u540d\u7531\u82f1\u6587\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u6c49\u5b57\u3001\u201c-\u201d \u548c\u4e0b\u5212\u7ebf\u7ec4\u6210",pattern2:"\u4f1a\u5458\u540d\u9700\u8981\u4ee5\u82f1\u6587\u5b57\u6bcd\u6216\u6c49\u5b57\u5f00\u5934",remote:"\u8be5\u4f1a\u5458\u540d\u5df2\u7ecf\u88ab\u5176\u4ed6\u4f1a\u5458\u4f7f\u7528\u4e86"},password:{required:"\u8bf7\u8f93\u5165\u5bc6\u7801",rangelength:"\u5bc6\u7801\u5e94\u5728 {0}\uff5e{1} \u4e2a\u5b57\u7b26\u5185",pattern:"\u5bc6\u7801\u4ec5\u5141\u8bb8\u6570\u5b57\u3001\u82f1\u6587\u5b57\u6bcd\u53ca\u7b26\u53f7"},confirm:{equalTo:"\u786e\u8ba4\u5bc6\u7801\u4e0e\u5bc6\u7801\u4e0d\u4e00\u81f4"}},errorPlacement:function(error,element){$("#errorico").removeClass("error").addClass("error");$("#errorMessage").empty().append(error.text())}},{wrapSelector:"li"})})();
</script>
<#include "/default/templates/layout/footer.ftl">
</body>
</html>
