function initLeaveWord(){
		$("#file1").change(function(){
		$("#imageVerify").show();
		$('#hrefApplyRefund').hide();
			var form = $('#form'),
			iframe = $('#uploadIframe_file1');
			iframe.bind('load', function() {
			iframe.unbind();
			var iframeDoc = iframe[0].contentDocument || iframe[0].contentWindow.document;
			var data, str = iframeDoc.body.innerHTML;
			//alert(str);
			str = str.replace(/<.+?>/g, '');
			//alert(str);
			try {
				data = $.parseJSON(str);
			} catch (e) {
				//alert(str);
			}
			if (data) {
				var imagFlag = data.imagFlag;
				if (imagFlag !="" && imagFlag.length > 0 && imagFlag !="1") {
					cleanFile("file1");
					popdialog(imagFlag);
				}
			}
			$("#imageVerify").hide();
			$('#hrefApplyRefund').show();
			form.removeAttr('method');
			form.removeAttr('action');
			form.removeAttr('target');
//			form.removeAttr('enctype');
		});
		form.attr({
			method: 'post',
			action : '/refund/validateRefundUploadImage.htm',
			target : 'uploadIframe_file1',
			enctype : "multipart/form-data"
		});
		form[0].submit();
	}); 
	
	$("#file2").change(function(){
			$("#imageVerify").show();
			$('#hrefApplyRefund').hide();
			var form = $('#form'),
			iframe = $('#uploadIframe_file1');
			iframe.bind('load', function() {
			iframe.unbind();
			var iframeDoc = iframe[0].contentDocument || iframe[0].contentWindow.document;
			var data, str = iframeDoc.body.innerHTML;
			//alert(str);
			str = str.replace(/<.+?>/g, '');
			//alert(str);
			try {
				data = $.parseJSON(str);
			} catch (e) {
				//alert(str);
			}
			if (data) {
				var imagFlag = data.imagFlag;
				if (imagFlag !="" && imagFlag.length > 0 && imagFlag !="1") {
					cleanFile("file2");
					popdialog(imagFlag);
					//alert(imagFlag);
				}
			}
			$("#imageVerify").hide();
			$('#hrefApplyRefund').show();
			form.removeAttr('method');
			form.removeAttr('action');
			form.removeAttr('target');
//			form.removeAttr('enctype');
		});
		form.attr({
			method: 'post',
			action : '/refund/validateRefundUploadImage.htm',
			target : 'uploadIframe_file1',
			enctype : "multipart/form-data"
		});
		form[0].submit();
	}); 
	
	$("#file3").change(function(){
			$("#imageVerify").show();
			$('#hrefApplyRefund').hide();
			var form = $('#form'),
			iframe = $('#uploadIframe_file1');
			iframe.bind('load', function() {
			iframe.unbind();
			var iframeDoc = iframe[0].contentDocument || iframe[0].contentWindow.document;
			var data, str = iframeDoc.body.innerHTML;
			//alert(str);
			str = str.replace(/<.+?>/g, '');
			//alert(str);
			try {
				data = $.parseJSON(str);
			} catch (e) {
				//alert(str);
			}
			if (data) {
				var imagFlag = data.imagFlag;
				if (imagFlag !="" && imagFlag.length > 0 && imagFlag !="1") {
					cleanFile("file3");
					popdialog(imagFlag);
			}
			}
			$("#imageVerify").hide();
			$('#hrefApplyRefund').show();
			form.removeAttr('method');
			form.removeAttr('action');
			form.removeAttr('target');
//			form.removeAttr('enctype');
		});
		form.attr({
			method: 'post',
			action : '/refund/validateRefundUploadImage.htm',
			target : 'uploadIframe_file1',
			enctype : "multipart/form-data"
		});
		form[0].submit();
	}); 
}

var  popdialog=function(_content){
	//art.dialog({time:6,lock: true,background: '#F6F6F6',width:'280px', // ±³¾°É«
	//			opacity: 0.5,	// Í¸Ã÷¶È
	//			content:_content ,
	//			icon: 'warning',
	//			id: 'Alert',
	//			ok: true
	//});
	art.dialog.alert(_content);
}
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