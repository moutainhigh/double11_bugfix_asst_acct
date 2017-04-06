<#setting classic_compatible=true>
<form id="tenSendForm" action="${postUrl!}" method="post">
<div class="tipswrap">
	<h2>支付提示</h2>
	<div class="infocat"></div>
	<div class="content">
		<h3>您将支付${amount}元，正在为您转接财付通收银台，请耐心等待！... ...</h3>
	</div>
</div>
	<input type="hidden" value="${sign_type}" name="sign_type"/>
	<input type="hidden" value="${input_charset}" name="input_charset"/>
	<input type="hidden" value="${bank_type}" name="bank_type"/>
	<input type="hidden" value="${body}" name="body"/>
	<input type="hidden" value="${attach}" name="attach"/>
	<input type="hidden" value="${return_url}" name="return_url"/>
	<input type="hidden" value="${notify_url}" name="notify_url"/>
	<input type="hidden" value="${partner}" name="partner"/>
	<input type="hidden" value="${out_trade_no}" name="out_trade_no"/>
	<input type="hidden" value="${total_fee}" name="total_fee"/>
	<input type="hidden" value="${fee_type}" name="fee_type"/>
	<input type="hidden" value="${spbill_create_ip}" name="spbill_create_ip"/>
	<input type="hidden" value="${time_start}" name="time_start"/>
	<input type="hidden" value="${time_expire}" name="time_expire"/>
	<input type="hidden" value="${sign}" name="sign"/>
</form>
<script type="text/javascript">jQuery(document).ready(function(){$("#tenSendForm").submit();});</script>