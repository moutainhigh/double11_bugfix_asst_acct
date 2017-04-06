
<div class="rights_box cf" style="padding-bottom:0px">
	<div class="tit"><span class="txt">退货物流信息</span></div>
	<div class="rights_goods">
	<form></form>
	<form id="logisticsForm" name="logisticsForm" action="${base}/rights/buyerAgreeRights.htm" method="post">
	<table class="rights_goods_tab">
		<tr>
			<th class="bla2">物流名称</th>
			<th class="bla3">运单号码</th>
			<th class="bla1">备注</th>
			<th class="bla4">操作</th>
		</tr>
		<tr>
			<#if rightsDO.buyerRequire=1 && (rightsDO.state=1 || rightsDO.state=3 || rightsDO.state=11 || rightsDO.state=12)>
				<#if rightsLogisticsDO??>
				<td><span class="bd">${((logisticsName)?html)!}</span></td>
				<td><span class="bd">${((billNo)?html)!}</span></td>
				<td><span class="bd">${(conments?html)!}</span></td>
				<td><span class="bd">--</span></td>
				<#--
				<td>
					<input type="hidden" id="rightsId" name="rightsId" value="${rightsId!}"/>
					<#if logisticsCorpDOs??>
					<select id="logisticsName" name="logisticsName">
						<option value="">请选择物流方式</option>
						<#list logisticsCorpDOs as logisticsCorpDO>
						<option value="${((logisticsCorpDO.logisticsType)?html)!}:${((logisticsCorpDO.corpName)?html)!}">${((logisticsCorpDO.corpName)?html)!}</option>
						</#list>
					</select>
					<#else>
					<font color='red'>加载物流方式失败！</font>
					</#if>
				</td>
				<td><span class="bd"><input type="text" id="billNo" name="billNo" value="${(billNo!)?html}" title='请输入运单号码'/></span></td>
				<td><span class="bd"><input type="text" name="conments" value="${(conments!)?html}" size="65" onKeyUp='removeHTMLTag(this);' title='请输入备注'/></span></td>
				<td><input type='button' onClick="delivery();" id="refundInfoBtn" class="rigths_btn-four" style="margin:1px 1px 0px 0px" value='退货商品发出'/></td>
				-->
				<#else>
				<td>
					<input type="hidden" id="rightsId" name="rightsId" value="${rightsId!}"/>
					<#if logisticsCorpDOs??>
					<select id="logisticsName" name="logisticsName">
						<option value="">请选择物流方式</option>
						<#list logisticsCorpDOs as logisticsCorpDO>
						<option value="${((logisticsCorpDO.logisticsType)?html)!}:${((logisticsCorpDO.corpName)?html)!}">${((logisticsCorpDO.corpName)?html)!}</option>
						</#list>
					</select>
					<#else>
					<font color='red'>加载物流方式失败！</font>
					</#if>
				</td>
				<td><span class="bd"><input type="text" id="billNo" name="billNo" value="${(billNo!)?html}" title='请输入运单号码' maxLength='30'/></span></td>
				<td><span class="bd"><input type="text" id='conments' name="conments" value="${(conments!)?html}" size="65" title='请输入备注'/ maxLength='300'></span></td>
				<td><input type='button' onClick="delivery();" id="refundInfoBtn" class="rigths_btn-four" style="margin:0px 0px 0px 0px" value='退货商品发出'/></td>
				</#if>
			</#if>
		</tr>
	</table>
	</form>
	</div>
</div>

<script language="JavaScript" type="text/javascript">
function delivery(){//买家同意发货
	if(checkForm()){
		if(confirm('请您确认输入的物流资料是否正确，该信息一经提交不可修改！')){
			$("#logisticsForm").submit();		
		}
	}
}
function checkForm(){ //校验买家发货信息
	var name = $('#logisticsName');
	if($.trim(name.val())==""){
		alert("请选择物流方式！");
		return ;
	}
	var billNo = $('#billNo');
	if(billNo.val()==""){
		alert('请认真填写运单号码,填写后将不能修改！');
		return ;
	}
	if(getLength($.trim(billNo.val())) > 30){
		alert("运单号码超限，请输入正确的运单号吗，填写后不能更改");
		return ;
	}
	var conments=$.trim($('#conments').val());
	if(conments != "" && getLength(conments) > 100){
		alert("请勿超出150个汉字(300个字符)长度，请尽量描述简洁，以方便卖家查看");
		return ;
	}
	return true;
}
</script>
