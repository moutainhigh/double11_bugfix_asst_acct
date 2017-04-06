
<div class=" boxh0 top10 dajin0629-juzhong">
	<div class=" boxh0 top10 dajin0629-juzhong">
		<!-- 发表留言/凭证信息边线开始   -->
		<div class=" boxh0 top10 t2 dajin0628-boxoverflow paddingtb15">
			<form id='sendMsgForm' name='sendMsgForm' action="${base}/rights/sendMessage.htm" method="post" enctype="multipart/form-data"> 
				<ul>
					<li class="zi_shenhui"><strong>发表留言/凭证信息</strong></li>
					<li class="zi_shenhui">
						<input type="hidden" name="rightsId" value="${rightsId!}" />
						<span style="vertical-align:top;"><strong>填写留言：</strong></span>
						<textarea style="width:550px;height:150px;" id="content" name="content" onpropertychange="limitInputLength(this,600);" maxLength='600' title='请输入留言内容'></textarea>
						<p style="padding-left:70px" id='msgFont'>留言内容请勿超过300个汉字长度</p>
					</li>
					<li class="zi_shenhui">
						<strong>上传凭证：</strong>
						<b class='hui'>可选项，每张图片不超过500K，最多3张  支持GIF、JPG、PNG格式</b>
					</li>
					<li class="shuomingbox">
						<div id="fileone">凭证文件：<input type="file" name="voucherPic" size="50"/>
							<a href="#" class="lan02" id="showfile">增加上传</a>		
	                    	<font id="msgSpan" color="red"></font>
							<input type="hidden" value="0" id="temp1"/>
							<input type="hidden" value="0" id="temp2"/>
						</div>
					</li>
					<li class="shuomingbox" id="filetwo" style="display:none">
						<div>
						凭证文件：<input type="file" id='voucherPic2' name="voucherPic" size="50"/>
						<a href="javascript:void(0)" class="lan02" onclick="hiddenFileTwo()">取消此凭证</a>
						</div>
					</li>
					<li class="shuomingbox" id="filethree" style="display:none">
						<div>
						凭证文件：<input type="file" id='voucherPic3' name="voucherPic" size="50"/>
						<a href="javascript:void(0)" class="lan02" onclick="hiddenFileThree()">取消此凭证</a>
						</div>
					</li>
					<li style='text-align:center;'>
						<input id='sendMsgBtn' type="button" value="发表留言" onclick='btnForm();' class="rigths_btn-four" />&nbsp;
						<font color="red">${sendMsg!}</font>
					</li>
				</ul>
			</form>
	  	</div>
	  	<!-- 发表留言/凭证信息边线开始   -->
	</div>       
</div>

<script language="JavaScript" type="text/javascript">
function btnForm(){
	if(validateForm()){
		$("#sendMsgForm").submit();	
	}
}
function validateForm() { 
	var content = $("#content").val();
	if($.trim(content)=="") { 
		alert("请认真填写留言信息!");
		return ; 
	}
	if(getLength(content)>600){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出150个汉字长度，请尽量描述简洁，以方便卖家查看。</font>";
		return ;
	}
	return true;
}
// 限制文本域中输入的长度
function limitInputLength(obj,a_limit){
	var objValue=$.trim(obj.value);
	if(getLength(objValue) > a_limit){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出300个汉字长度，请尽量描述简洁，以方便查看。</font>";
	}else if(getLength(objValue) > a_limit){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出300个汉字长度，请尽量描述简洁，以方便查看。</font>";
	}else{
		document.getElementById('msgFont').innerHTML="<b class='hui'>留言内容请勿超过300个汉字长度</b>";
	}
}
function hiddenFileTwo(){
	$('#msgSpan').html('');
	$("#filetwo").hide();
	document.getElementById("voucherPic2").outerHTML += '';
	$('#voucherPic2').val('');
	$('#temp1').val('0');
}
function hiddenFileThree(){
	$('#msgSpan').html('');
	$("#filethree").hide();
	document.getElementById("voucherPic3").outerHTML += '';
	$('#voucherPic3').val('');
	$('#temp2').val('0');
}
$(function(){
	var temp1 = $('#temp1');
	var temp2 = $('#temp2');
	$('#showfile').toggle(function() {
		$('#msgSpan').html('');
	  	$('#filetwo').show();
		temp1.val('1');
	},function() {
		$('#msgSpan').html('');
	  	$('#filethree').show();
		temp2.val('1');
	},function() {
		if(temp1.val()==1 && temp2.val()==1){
	  		$('#msgSpan').html('最多只能上传3个!');
		}
	});
});
function returnrightlist(){
	document.location.href="${base}/rights/rightsDetail.htm?rightsId=${rightsId!}";	
}
</script>
