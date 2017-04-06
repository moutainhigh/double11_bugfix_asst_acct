<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>财付通账号管理-绑定</title>
</head>

<body>
<link media="screen" type="text/css" href="http://static.pinju.com/css/member/member.css" rel="stylesheet" />

		<div class="ptitle">
			<h1>绑定财付通账户</h1>
			<div class="eye">
				<p><a href="http://service.qq.com/info/37930.html" target="_blank">如何注册财付通账户</a></p>
			</div>
		</div>
		<div class="tips-text">
			<div class="eye">
				<p><strong>如果您还没有财付通账号，请立即<a href="https://www.tenpay.com/zft/wap/new_register.shtml" target="_blank">注册财付通账户>></strong></a></p>
			</div>
			<p>绑定账户不可随意更换，请务必谨慎操作。</p>
		</div>
		<div class="row bdtenpay">
			<form action="${base}/my/tenpay-bound-account.htm" method="post" id="pay-bound">
                <input name="pj0" type="hidden" value="${pj0}">
                <input id="account" name="pa.account" type="hidden">
                <input id="username" name="pa.username" type="hidden">
				<div class="item">
					<div class="title"><label for="busAccountType"><input type="radio" id="busAccountType" name="pa.accountType" <#if pa.accountType !=1>checked="checked"</#if> value="0"/> 绑定财付通<strong>企业账户</strong></label>
					  <div class="tipswrap">
							<div class="text">
								<p>带商户号的财付通企业账号无法绑定</p>
								<span class="close"></span>
							</div>
							<span class="arrow"></span>
						</div>
					</div>
					<div class="row cf">
						<label>财付通企业账号：</label><input type="text" name="busAccount" id="busAccount" maxlength="50" <#if pa.accountType==0>value="${pa.account?html}"</#if>/>
					</div>
					<div class="row cf">
						<label>账号对应真实姓名：</label><input type="text" name="busUsername" id="busUsername" maxlength="50" <#if pa.accountType==0>value="${pa.username?html}"</#if>/>
					</div>
					<div class="row tips" id="pay-tips-0">
					    <#if invokeMessage??><p>${invokeMessage}</p></#if>
					    <#if (validator['account'])??><p>${validator['account']}</p></#if>
					    <#if (validator['username'])??><p>${validator['username']}</p></#if>
					    <#if (validator['agreement'])??><p>${validator['agreement']}</p></#if>
					</div>
				</div>
				
				<div class="item last disabled-wrap">
					<div class="title"><label for="personAccountType"><input type="radio" id="personAccountType" name="pa.accountType" <#if pa.accountType==1>checked="checked"</#if> value="1"/> 绑定财付通<strong>个人账户</strong></label></div>
					<div class="row cf">
						<label>财付通个人账号：</label><input type="text" name="personAccount" id="personAccount" maxlength="50" <#if pa.accountType==1>value="${pa.account?html}"</#if>/>
					</div>
					<div class="row cf">
						<label>账号对应真实姓名：</label><input type="text" name="personUsername" id="personUsername" maxlength="50"  <#if pa.accountType==1>value="${pa.username?html}"</#if>/>
					</div>
					<div class="row tips" id="pay-tips-1">
					    <#if invokeMessage??><p>${invokeMessage}</p></#if>
					    <#if (validator['account'])??><p>${validator['account']}</p></#if>
					    <#if (validator['username'])??><p>${validator['username']}</p></#if>
					    <#if (validator['agreement'])??><p>${validator['agreement']}</p></#if>
					</div>
				</div>
				
				<div class="btnrow btn-imgtext">
					<input type="checkbox" id="agreement" name="pa.agreement" value="yes" <#if pa.agreement=='yes'>checked="checked"</#if>/> <label>我已经了解了<a href="#bindtips">绑定须知</a></label> 
					<button class="confirmbind" id="submitBtn" type="submit"><span>确定绑定</span></button>
				</div>
				
			</form>	
			<div class="row" name="knowlege-node">
				<div class="title"><a name="bindtips"></a><h3>绑定须知：</h3></div>
				<ul>
					<li><strong>1.品聚账号能绑定几个财付通账户？</strong><br />
						<span>品聚天使</span>：一个品聚账号只能绑定一个财付通账户。</li>
					<li><strong>2.我是普通商户，必须要绑定财付通企业账户吗？</strong><br />
						<span>品聚天使</span>：是的，为了保障企业利益，我们建议所有商户绑定财付通的企业账户。</li>
					<li><strong>3.我绑定错了财付通账号，可以解绑吗？</strong><br /> 
						<span>品聚天使</span>：很抱歉，暂时不可以。</li>
					<li><strong>4. 我的财付通账号是商户号，绑定时报错了，为什么？怎么办？</strong><br /> 
						<span>品聚天使</span>：目前财付通账号为商户号的企业账户无法绑定品聚网。建议您可以另外申请一个普通的财付通企业账户来绑定品聚网。</li>
					<li><strong>5.系统提示“您的财付通账号无效”，为什么？怎么办？</strong><br /> 
						<span>品聚天使</span>：请先进行以下三种情况确认：
                                                                     一、确认已成功注册财付通账号；
                                                                     二、确认已成功激活财付通账号；
                                                                     三、确认未注销财付通账号。
                                                        如果以上三点确认后仍然无法绑定您的财付通账号,请登录财付通官网www.tenpay.com进行验证或者拨打财付通客服热线0755-86013860确认账号有效性。</li>
				</ul>
			</div>
		</div>

<script type="text/javascript">
<#if !invokeMessage?? && !validator??>
   (function(){$('#pay-tips-0').hide();$('#pay-tips-1').hide();})();
 <#elseif pa.accountType==0>
    (function(){$('#pay-tips-1').empty().hide();})();
 <#elseif pa.accountType==1>
    (function(){$('#pay-tips-0').empty().hide();})();
</#if>
(function(){
	$('input').focus(function(){
	    $(this).select();
	})
	$('#account').focus();
  
    $('.bdtenpay input:radio').parents('.item').addClass('disabled-wrap');
	$('.bdtenpay input:radio:checked').parents('.item').removeClass('disabled-wrap');
    $('.disabled-wrap :text').prop('disabled', true).val('');
	$('.bdtenpay input:radio').bind('click',function(){
		$('.bdtenpay .item').addClass('disabled-wrap');
		$(this).parents('.item').removeClass('disabled-wrap');
		$('.disabled-wrap :text').prop('disabled', true).val('');
		$(this).parents('.item').removeClass('disabled-wrap').find(".tipswrap").show();
		$(this).parents('.item').find(':text').prop('disabled', false);
		$(this).val()==0?$("#pay-tips-1").empty().hide():$("#pay-tips-0").empty().hide();
	});
	$(".tipswrap .close").bind('click',function(){
		$(this).parents(".tipswrap").hide();
	});
})();

$(document).ready(function(){
   $('#pay-bound').submit(function(){
	    if ($("#personAccountType").attr("checked") == undefined) {
	        if($.trim($('#busAccount').val()).length==0){
		      s('#busAccount','请填写财付通账号名','#pay-tips-0');
		      return false;
		    } 
		    $("#account").val($.trim($('#busAccount').val()));
		    if($.trim($('#busUsername').val()).length==0){
		      s('#busUsername', '请填写账号对应真实姓名','#pay-tips-0');
		      return false;
		    }
		    $("#username").val($.trim($('#busUsername').val()));
		    if ($("#agreement").attr("checked") == undefined) {
		         s('', '请了解财付通账户绑定须知', '#pay-tips-0');
		        return false;
	        }
		    
	    }
	    
	    if ($("#busAccountType").attr("checked") == undefined) {
	        if($.trim($('#personAccount').val()).length==0){
		      s('#personAccount','请填写财付通账号名', '#pay-tips-1');
		      return false;
		    } 
		    $("#account").val($.trim($('#personAccount').val()));
		    
		    if($.trim($('#personUsername').val()).length==0){
		      s('#personUsername', '请填写账号对应真实姓名', '#pay-tips-1');
		      return false;
		    } 
		    $("#username").val($.trim($('#personUsername').val()));
		    if ($("#agreement").attr("checked") == undefined) {
		       s('', '请了解财付通账户绑定须知', '#pay-tips-1');
		       return false;
		    } 
	    }
	    $("#submitBtn").attr("disabled", "true"); 
	    return true;
  });
  function s(n,m,v){
      $(v).text(m).show();
      if (n != '') {
         $(n).focus().select();
      }
  }
});
</script>
</body>
</html>
