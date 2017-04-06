<link rel="stylesheet" href="${staticServer}/css/new/bail.css" type="text/css" media="screen" />
<script src="${staticServer}/js/margin/margin.js"></script>
<div class="bail_status">
	<ul class="bail_status_tab cf">
		<li class="focus"><span>品聚保证金状态</span></li>
	</ul>
	<div class="bail_status_bar"><span>品质保障服务保证金管理</span></div>
  <#if pinjuMargin??>	
	<div class="bail_status_uesr_txt">
		您需要缴纳的保证金额度：<span>${(pinjuMargin!)?html}</span>&nbsp;元
	</div>
	<div class="bail_status_uesr_txt">
		您当前的保证金余额：<span>${(currentMargin!)?html}</span>&nbsp;元
	</div>
	<div class="bail_status_uesr_txt">
		<#--
	    <#if "${requireAmount!}"!="0.00">
		需补缴<span>${requireAmount!}</span>&nbsp;元
		</#if>  
		您还有<span class="red bd">8</span>天时间补缴消保金 
		<a class="add_bail_but" href="${base}/margin/backPayMargin.htm">补缴保证金</a>
		-->
	</div>
  </#if>
	<div class="cf but_box">
	    <form id="marginform" method="post" action="${base}/margin/backPayMargin.htm">
			请输入您要缴纳的金额：<input class="ml15" id="requireAmount" name="requireAmount" type="text" maxlength="8" value="${(requireAmount!)?html}"/>&nbsp;元&nbsp;<span class="red" id="errormsg"></span>
			<div class="h10"></div>
			<a id="payMargin" href="#this" class="view_bail_but">补缴保证金</a>
			<a href="${base}/margin/margintradelist.htm" class="view_bail_but">查看交易明细</a>
		</form>
	</div>
	<span class="err_text red">${errorMsg!}</span>
</div>
 <input type="hidden" value="mymargin" id="my-page" />
