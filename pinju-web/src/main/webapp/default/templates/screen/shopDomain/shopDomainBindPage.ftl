<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/tdomain/tdomain.css" rel="stylesheet" type="text/css" media="screen" />
<title>域名设置</title>
<input type="hidden" value="red_shopDomain" id="my-page" />
<form action="" method="post" id="domainBindForm">
<div class="domain-set-tip cf">
				<img class="img-tip" src="http://static.pinju.com/img/tdomain/domain-set-tip.gif">
				<div class="word-tip">
					<a href="http://service.pinju.com/cms/html/2011/maexplain_1024/83.html" target="_blank">使用帮助</a>
				</div>
			</div>
			<div class="domain-box-after">二级域名一旦绑定便不可解绑或换绑，请谨慎选择您的二级域名。</div>			
			<div class="domain-box-modify">
				<div class="domain-modify cf">
					
					<div class="input">
						<input maxlength="32" type="text" value="${domain!}" id="domain" name="domain" onkeyup="checkDomain()">
					</div>
					<span>.pinju.com</span>
					<a class="inquiry" href="javascript:void(0);" onclick="checkDomainAjax('domain')"/>检查域名</a>
					
					<div class="tip-yellow">
						域名前缀可以输入长度为5~32个字符的英文、数字或者"-"，且"-"不能出现在最前面和最后面
					</div>
					
					<#if domain??>
						<div class="tip-green" id="tipDivId" style="display:""">
							${domain!}.pinju.com
						</div>
					<#else>
						<div class="tip-green" id="tipDivId" style="display:none">
						</div>
					</#if>
					<#if settingType?? && settingType == 2>
						<a class="banding" style="display:none" id="settingDomain" href="#" onclick="bind(${settingType!})">更改域名</a>
					<#else>
						<a  class="banding" style="display:none" id="settingDomain" href="#" onclick="bind(0)">绑定域名</a>
					</#if>
				</div>
				
			</div>
</form>
			
<script>
	var flag = 0;
	String.prototype.isSN=function(){
		return (/^([a-z0-9]{1,2}|[a-z0-9][a-z0-9\-]+[a-z0-9])$/i).test($.trim(this));
	}
	function checkDomain(){
		flag = 0;
		var domain = $.trim($("#domain").val());
		if(domain.length > 32){
			alert("域名前缀可以输入长度为5~32个字符");
			flag = 1;
			//return false;
		}
		if(domain){
			$("#tipDivId").css("display","");
			if(domain.isSN()){
				$("#tipDivId").attr("class","tip-green");
				$("#tipDivId").html("");
				$("#tipDivId").html(domain + ".pinju.com");
				return true;
			}else{
				$("#tipDivId").attr("class","tip-red");
				$("#tipDivId").html("");
				$("#tipDivId").html(domain + ".pinju.com" + "<em>此域名无法显示，请重新设置</em>");
				$("#settingDomain").css("display","none");
				return false;
			}
		}else if(domain == ""){
			$("#tipDivId").css("display","none");
			$("#settingDomain").css("display","none");
		}
	}
	
	function bind(type){
		if(checkDomain()){
			if($.trim($("#domain").val()).length < 5){
				alert("域名前缀可以输入长度为5~32个字符！");
				return false;
			}
			if(type == 2){
				if(confirm("您确定要解除当前的二级域名，启用新的二级域名吗？完全替换大约需要几个小时，您确定要替换吗？")){
					$("#domainBindForm").attr("action", "/shopDomain/settingDomainAction.htm?settingType=2");
					$("#domainBindForm").submit();
				}
			}else{
				$("#domainBindForm").attr("action", "/shopDomain/showDomainBindAgreePageAction.htm");
				$("#domainBindForm").submit();
			}
		}else{
			if(!flag){
				alert("请输入附和规范的域名！");
			}
		}
	}
	
	function checkDomainAjax(id){
		var domain = $.trim($("#"+id).val());
		if(!domain.isSN()){
			alert("请按照规则提示输入域名！");
			flag = 1;
			return false;
		}
		if(domain.length > 32 || domain.length < 5){
			alert("域名前缀可以输入长度为5~32个字符");
			flag = 1;
			return false;
		}
    	 if (domain == "" || domain.length==0) {
    	 	alert("请先输入域名！");
    	 	$("#settingDomain").css("display","none");
			   return false;
		 } 
		 
		 	$.ajax({
					// 后台处理程序
					url : "/shopDomain/checkDomainIsBindAction.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "json",
					// 要传递的数据
					data : {
						domain:domain
					},
					// 回传函数
					success : function callBack(result) {
						if(result == "unbind"){
							$("#settingDomain").css("display","block");
						}else if(result == "bind"){
							alert("您输入的域名已经被其他用户绑定，请重新输入一个域名。");
							$("#settingDomain").css("display","none");
						}else if(result == "wordFilter"){
							alert("您输入的域名含有敏感词，请重新输入一个域名。");
							$("#settingDomain").css("display","none");
						}else if(result == "defaultName"){
							alert("您输入的域名是保留域名，请重新输入一个域名。");
							$("#settingDomain").css("display","none");
						}
					}

				});
		 
	}
</script>