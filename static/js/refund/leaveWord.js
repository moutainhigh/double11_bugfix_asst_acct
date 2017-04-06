/**  
 * 页面加载时完成操作  
 */  
$(document).ready(function() {   

	//initLeaveWord();
	// 设置上传外观图   
   $('#saveLeveWord').click(function(){
   		if($('#texLeave').attr("value") == ""){
		popdialog("留言内容不能为空，请填写留言内容！");
		return false;
	}
	if(getLength($('#texLeave').attr("value")) > 300){
		popdialog("您输入的字符长度已经超过300个汉字长度，请尽量描述简洁，以方便双方查看。");
		return false;
	}
		$("#pageUrl").val(document.location.href);
		$("#leaveWordform").submit();
		$(this).hide();
		$("#submitLoad").show();
   	});
	
	$("#texLeave").bind("blur",function(e) {
		var objValue=$.trim($(this).val());
		if(getLength(objValue) > 300){
			$('#msgFont').html("<font color='red'>您输入的字符长度已经超过300个汉字长度，请尽量描述简洁，以方便双方查看。</font>");
			$(this).val(objValue);
		}else{
			$('#msgFont').html("(注：请详细描述您的理由，以便卖家和客服人员判断，限300字)");
		}
	});
});   

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

/**  
 * iframe上传外观图片的返回操作  
 *  msg 返回验证是否成功
 */  
function callbackLeaveWord(pageUrl,msg) {   
	if(msg !=null && msg !=""){
		popdialog(msg);
	 $('#saveLeveWord').show();
	 $("#submitLoad").hide();
	}else{
		self.location=pageUrl;	
	}
    // 重置上传按钮，使之为空   
    cleanFile("file1");
    cleanFile("file2");
    cleanFile("file3");   
}   
/**  
 * 重置上传按钮，使之为空     
 */ 
function cleanFile(id){
    var _file = document.getElementById(id);
    if(_file.files){
        _file.value = "";
    }else{
        if (typeof _file != "object"){ return null; }
        var _span = document.createElement("span");
        _span.id = "__tt__";
        _file.parentNode.insertBefore(_span,_file);
        var tf = document.createElement("form");
        tf.appendChild(_file);
        document.getElementsByTagName("body")[0].appendChild(tf);
        tf.reset();
        _span.parentNode.insertBefore(_file,_span);
        _span.parentNode.removeChild(_span);
        _span = null;
        tf.parentNode.removeChild(tf);
    }
}

var  popdialog=function(_content){
	//art.dialog({time:6,lock: true,background: '#F6F6F6',width:'280px', // 背景色
	//				opacity: 0.5,	// 透明度
	//				content:_content ,icon: 'warning',
	//				ok: true
	//});
	art.dialog.alert(_content);
}
