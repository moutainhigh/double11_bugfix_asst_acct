//获取手机验证码
function codeAjax(type){
	$.ajax({
   		url: "http://www.pinju.com/async/mobile/code.htm",
   		type: "post",
   		dataType: 'json',
   		data: {'mobile':$('#mobile').val(),'type':type},
   		timeout:15000,
   		success:function(data){
   			var mid = data.mid;
   			var status = data.status;
   			var message = data.message;
   			if(parseInt(status)==1){
   				errs(message);
   			}
   			if(parseInt(status)==0){
   				$('#mid').val(mid);
   				suc('成功获取手机验证码,请注意查收');
   			}
   		},
   		error:function(error){
   			errs("请求超时,稍后请重新获取验证码");
   		}
	});
}

//错误提示框
function errs(str){
	var dialog = art.dialog({
		lock: true,
    	opacity: 0.6,	// 透明度
	    title: '错误',
	    content: str,
	    icon: 'error',
	    width: 250,
    	height: 80,
	    button: [
	        {
	            name: '确定',
	            focus: true
	        }
	    ]    
	});
}

//错误提示框
function err(str,ids){
	var dialog = art.dialog({
		lock: true,
    	opacity: 0.6,	// 透明度
	    title: '错误',
	    content: str,
	    icon: 'error',
	    width: 250,
    	height: 80,
	    button: [
	        {
	            name: '确定',
	            focus: true,
	            callback:function(){$('#'+ids).focus();}
	        }
	    ]    
	});
}

//正确提示框
function suc(str){
	var dialog = art.dialog({
		lock: true,
    	opacity: 0.6,	// 透明度
	    content: str,
	    icon: 'succeed',
    	height: 90,
	    button: [
	        {
	            name: '确定',
	            focus: true,
              callback:function(){
                $('#code').focus();
              }
	        }
	    ]    
	});
}

//倒计时加载获取手机验证码ajax
function load(cs,type){
    if(!checkMobile()){
      return;
    }
		codeAjax(type);
		var gc = $('#get-code');
		gc.unbind('click');
		gc.addClass(cs);
		var secs = 60;
		var timer = setInterval(function() {
		   secs--;
		   if (secs <= 0) {
		      gc.text(gc.attr('title') + '(60)');
		      gc.removeClass(cs);
		      gc.bind('click', load);
		      clearInterval(timer);
		      return;
		   }
		   gc.text(gc.attr('title') + '(' + secs + ')');
		}, 1000);
}

//检查手机
function checkMobile(){
	var mobile = $("#mobile").val();
	if($.trim(mobile)==""){
		err("手机号码不能为空","mobile");
		return false;
	}
	
	if(!/^1[3458][0-9]{9}$/.test(mobile)){
		err("手机号码格式不正确","mobile");
		return false;
	}
	
	return true;
}

//检查手机验证码
function checkCode(){
	var code = $('#code').val();
	if($.trim(code)==""){
		err('手机验证码不能为空','code');
		return false;
	}
	
	if(!/^[0-9]{6}$/.test(code)){
		err('验证码格式不正确,必须是6位整数','code');
		return false;
	}
	return true;
}

//手机找回密码检查手机验证码
function checkCodes(){
		var code = $('#code').val();
		if($.trim(code)==""){
			$('#error-id').text('验证码不能为空');
			$('#error-id').attr('class','err');
      		$('#error-id').show();
      		$('#code').focus();
			return false;
		}
		
		if(!/^[0-9]{6}$/.test(code)){
			$('#error-id').text('验证码格式不正确,必须是6位整数');
			$('#error-id').attr('class','err')
      		$('#error-id').show();
      		$('#code').focus();
			return false;
		}
		return true;
	}
//只允许输入数字
function kup(ids){
  $('#'+ids).keyup(function(){
      $('#'+ids).val($('#'+ids).val().replace(/[^0-9]/g,''));
  });
}

function mMove(id1,id2){
  $('#'+id1).mousemove(function(){
	    $('#'+id2).hide();
	});
}