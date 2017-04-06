<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>品聚会员 - 手机解绑</title>
</head>
<body>
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link rel="stylesheet" href="http://static.pinju.com/css/member/user_login.css?t=20111213.css" media="screen" />
<!-- content -->
<form action="${base}/my/unbland-unmobile.htm" method="post" id="unblandForm">
		<div class="phone-h cf">
			<h1>解绑验证手机</h1>
		</div>
		<div class="row">
			<div class="y-point">
				<p>请您输入验证短信中的6位数字验证码。点击“下一步”可以解绑该手机号码。若您一分钟内未收到短信，可以点击“重新获取”按钮再次发送验证短信，并输入新的验证码。如果您的已验证手机号码无法接收验证码，请与客服联系：<span class="red bd">4008-211-588</span></p>
			</div>
			<div class="phone-m">
				<ul class="list">
					<#if invokeMessage??>
						<div class="cf"><b class="point">${invokeMessage!}</b></div>
					</#if>
					<li><span>手机号码：</span><b>${(mem.getHideMobile())!}</b></li>
					<li><span>短信验证码：</span><button type="button" class="btn" id="get-code" title="获取短信验证码">获取短信验证码(60)</button></li>
					<li><span>输入验证码：</span><input type="text" name="code" id="code" maxlength="6"/></li>
				</ul>
			</div>
			<input type="hidden" name="mobile" id="mobile" value="${(mem.mobile)!}"/>
			<input type="hidden" name="mid" id="mid" value=""/>
			<input type="hidden" name="pj0" value="${(pj0)!}" />
			<button type="button" class="n-btn" id="unbland_id">下 一 步</button>
		</div>
<!-- footer -->
<input type="hidden" value="security-center" id="my-page" />
<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<script>
(function(){$("#code").focus(function(){$(this).select()});$("#get-code").bind("click",load);$("#unbland_id").bind("click",confims);kup("code");disEnter("code");})();function confims(){if(checkCode()){var a=$("#mid").val();$.trim(a)==""?errs("\u8bf7\u91cd\u65b0\u83b7\u53d6\u9a8c\u8bc1\u7801"):$("#unblandForm").submit()}}
function codeAjax(){$.ajax({url:"http://www.pinju.com/async/mobile/code.htm",type:"post",dataType:"json",data:{mobile:$("#mobile").val(),type:3},timeout:15E3,success:function(a){var b=a.mid,c=a.status;a=a.message;parseInt(c)==1&&errs(a);if(parseInt(c)==0){$("#mid").val(b);suc("\u624b\u673a\u9a8c\u8bc1\u7801\u53d1\u9001\u6210\u529f\uff0c\u8bf7\u6ce8\u610f\u67e5\u6536")}},error:function(){errs("\u8bf7\u6c42\u8d85\u65f6,\u7a0d\u540e\u8bf7\u91cd\u65b0\u83b7\u53d6\u9a8c\u8bc1\u7801")}})}
function errs(a){art.dialog({esc:false,lock:true,opacity:0.6,title:"\u9519\u8bef",content:a,icon:"warning",button:[{name:"\u786e\u5b9a",focus:true}]})}function err(a,b){art.dialog({esc:false,lock:true,opacity:0.6,title:"\u9519\u8bef",content:a,icon:"warning",button:[{name:"\u786e\u5b9a",focus:true}],close:function(){$("#"+b).focus()}})}
function suc(a){art.dialog({esc:false,lock:true,opacity:0.6,content:a,icon:"succeed",button:[{name:"\u786e\u5b9a",focus:true}],close:function(){$("#code").focus()}})}function load(){if(checkMobile()){codeAjax();var a=$("#get-code");a.unbind("click");a.addClass("gbtn");var b=60,c=setInterval(function(){b--;if(b<=0){a.text(a.attr("title")+"(60)");a.removeClass("gbtn");a.bind("click",load);clearInterval(c)}else a.text(a.attr("title")+"("+b+")")},1E3)}}
function checkMobile(){var a=$("#mobile").val();if($.trim(a)==""){err("\u624b\u673a\u53f7\u7801\u4e0d\u80fd\u4e3a\u7a7a","mobile");return false}if(!/^1[3458][0-9]{9}$/.test(a)){err("\u624b\u673a\u53f7\u7801\u683c\u5f0f\u4e0d\u6b63\u786e","mobile");return false}return true}
function checkCode(){var a=$("#code").val();if($.trim(a)==""){err("\u9a8c\u8bc1\u7801\u4e0d\u80fd\u4e3a\u7a7a","code");return false}if(!/^[0-9]{6}$/.test(a)){err("\u9a8c\u8bc1\u7801\u683c\u5f0f\u4e0d\u6b63\u786e,\u5fc5\u987b\u662f6\u4f4d\u6574\u6570","code");return false}return true}
function checkCodes(){var a=$("#code").val();if($.trim(a)==""){$("#error-id").text("\u9a8c\u8bc1\u7801\u4e0d\u80fd\u4e3a\u7a7a");$("#error-id").attr("class","err");$("#error-id").show();$("#code").focus();return false}if(!/^[0-9]{6}$/.test(a)){$("#error-id").text("\u9a8c\u8bc1\u7801\u683c\u5f0f\u4e0d\u6b63\u786e,\u5fc5\u987b\u662f6\u4f4d\u6574\u6570");$("#error-id").attr("class","err");$("#error-id").show();$("#code").focus();return false}return true}
function kup(a){$("#"+a).keyup(function(){$("#"+a).val($("#"+a).val().replace(/[^0-9]/g,""))})}function mMove(a,b){$("#"+a).mousemove(function(){$("#"+b).hide()})};	
function disEnter(a){$('#'+a).keydown(function(b){var curKey=b.which;if(curKey==13){if(!checkMobile()){return false;}if(!checkCode()){return false;}}})}
</script>
</body>
</html>
