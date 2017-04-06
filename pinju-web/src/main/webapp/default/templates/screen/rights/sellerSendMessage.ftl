<div class=" boxh0 top10 dajin0629-juzhong">    <!--固定用框架开始 -->
	<div class=" boxh0 top10 dajin0629-juzhong">    <!--固定用框架开始 -->
		<div class=" boxh0 top10 t2 dajin0628-boxoverflow paddingtb15"><!--外框架边线开始 -->
			<form id='sendMsgForm' name='sendMsgForm' action="${base}/rights/sellerSendMessage.htm" method="post" enctype="multipart/form-data"> 
				<ul>
					<li class="zi_shenhui"><strong>发表留言/凭证信息</strong></li>
					<li class="zi_shenhui">
						<input type="hidden" name="rightsId" value="${rightsId!}" />
						<span style="vertical-align:top;"><strong>填写留言：</strong></span>
						<textarea style="overflow:hidden;width:550px;height:150px;" id="content" name="content" onpropertychange="limitInputLength(this,600);" maxLength='600' >${content!}</textarea>
						<p style="padding-left:70px" id='msgFont'>留言内容请勿超过300个汉字长度</p>
					</li>
					<li class="zi_shenhui">
						<strong>上传凭证：</strong>
						<b class='hui'>可选项，每张图片不超过500K，最多3张  支持GIF、JPG、PNG格式</b>
					</li>
					<li class="shuomingbox">
						<div id="fileone">凭证文件1：<input type="file" name="voucherPic" size="50"/>
							<a href="#" class="lan02" id="showfile">增加上传</a>		
	                    	<font id="msgSpan" color="red"></font>
							<input type="hidden" value="0" id="temp1"/>
							<input type="hidden" value="0" id="temp2"/>
						</div>
					</li>
					<li class="shuomingbox" id="filetwo" style="display:none">
						<div>
						凭证文件2：<input type="file" name="voucherPic" size="50"/>
						<a href="javascript:void(0)" class="lan02" onclick="hiddenFileTwo()">取消此凭证</a>
						</div>
					</li>
					<li class="shuomingbox" id="filethree" style="display:none">
						<div>
						凭证文件3：<input type="file" name="voucherPic" size="50"/>
						<a href="javascript:void(0)" class="lan02" onclick="hiddenFileThree()">取消此凭证</a>
						</div>
					</li>
					<li style='text-align:center;'>
						<input id='sendMsgBtn' type="button" value="发表留言" onclick='btnForm();' class="rigths_btn-four" />&nbsp;
						<!--<input type="button" value="取消" onclick='returnrightlist();' class="rigths_btn-four" />-->
						<#if sendMsg??>
							<font color="red">${sendMsg!}</font>
						</#if>
					</li>
				</ul>
			</from>
	  	</div><!--5、结束-->
	</div><!--外框架边线开始结束-->         
</div><!--固定用框架开始结束 -->
<script language="JavaScript" type="text/javascript">
function btnForm(){
	if(validateForm()){
		$("#sendMsgForm").submit();	
	}
}
function validateForm() { 
	var content = $("#content");
	if($.trim(content.val())=="") { 
		alert("请认真填写留言信息!");
		return ; 
	}
	return true;
}
//取得字符串长度，一个中文字符长度为2
function getLength(s){
	if(s == ""){
		return 0;
	}
	var len = 0;
	for(i = 0; i < s.length; i++){
		var c = s.substr(i, 1);
		var ts = escape(c);
		if(ts.substring(0, 2) == "%u"){
			len += 2;
		} else {
			if(ts.substring(0, 3) == "%D7"){
				len += 2;
			} else {
				len += 1;
			}
		}
	}
	return len;
}
// 限制文本域中输入的长度
function limitInputLength(obj,a_limit){
	var objValue=$.trim(obj.value);
	if(getLength(objValue) > a_limit){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出300个汉字长度，请尽量描述简洁，以方便查看。</font>";
	}else if(getLength(objValue) > a_limit){
		document.getElementById('msgFont').innerHTML="<font color='red'>请勿超出300个汉字长度，请尽量描述简洁，以方便查看。</font>";
	}else{
		document.getElementById('msgFont').innerHTML="<b class='hui'>（注：留言内容请控制在300字内）</b>";
	}
}
function hiddenFileTwo(){
	$('#msgSpan').html('');
	$("#filetwo").hide();
	$('#temp1').val('0');
}
function hiddenFileThree(){
	$('#msgSpan').html('');
	$("#filethree").hide();
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
      