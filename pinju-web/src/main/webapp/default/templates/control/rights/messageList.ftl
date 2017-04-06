
<form name="pageForm" id="pageForm" action="">
<div class="msg_box">
	<ul class="msg_list">
	<#if query??>
		<#if query.items=0>
			<li style='text-align:center;'><font color="red">该维权没有评论信息</font></li>
		<#else>
			<#if query.rightsMessageDOs??>
	        <#assign rightsMessageDOs = query.rightsMessageDOs>
	        <#list rightsMessageDOs as rightsMessageDO>
			<li>
				<div class="user_name"><span>${((rightsMessageDO.userNick)?html)!}</span></div>
				<p class="text">
					<#if rightsMessageDO.voucherPic1??>
		        	<a href="${imageServer!}${(rightsMessageDO.voucherPic1)!}" target="_blank"><img src="${imageServer!}${(rightsMessageDO.voucherPic1)!}" width="50" height="50" /></a>
		        	</#if>
					<#if rightsMessageDO.voucherPic2??>
		        	<a href="${imageServer!}${(rightsMessageDO.voucherPic2)!}" target="_blank"><img src="${imageServer!}${(rightsMessageDO.voucherPic2)!}" width="50" height="50" /></a>
		        	</#if>
					<#if rightsMessageDO.voucherPic3??>
		        	<a href="${imageServer!}${(rightsMessageDO.voucherPic3)!}" target="_blank"><img src="${imageServer!}${(rightsMessageDO.voucherPic3)!}" width="50" height="50" /></a>
		        	</#if>
		        	<br>
		        	${((rightsMessageDO.content)?html)!}
			    </p>
				<span class="date">${rightsMessageDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</span>
			</li>
			</#list>
			<li><#include "/default/templates/control/pagination.ftl"></li>
			<#else>
				<li style='text-align:center;'><font color="red">加载维权列表信息失败</font></li>
			</#if>
		</#if>
	</#if>
	</ul>
</div>  
</form>
